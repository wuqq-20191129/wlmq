package com.goldsign.commu.commu.thread;

import com.goldsign.commu.commu.env.BaseConstant;
import com.goldsign.commu.commu.util.DbHelper;
import com.goldsign.commu.commu.util.DbcpHelper;
import com.goldsign.commu.commu.util.SocketUtil;
import java.io.IOException;
import java.sql.*;
import java.util.Hashtable;
import org.apache.log4j.Logger;

public class ConnectionPoolListener
        extends Thread {

    private static Logger logger = Logger.getLogger(ConnectionPoolListener.class.getName());
    
    private int count = 0;
    private Hashtable test_config = null;
    private String testSql = "";
    public static Boolean isClosedServerSocket = new Boolean(false);        //SOCKET是否已关闭
    public static Boolean isRestartedServerSocket = new Boolean(false);     //是否需要重启SOCKET服务
    public static Boolean isConnectForDataConnection = new Boolean(false);  //
    public static Boolean isSqlConExceptionClose = new Boolean(false);

    public ConnectionPoolListener() {
    }

    public ConnectionPoolListener(Hashtable config) {
        
        this.test_config = config;
        this.testSql = BaseConstant.TestSqlForData;
 
    }

    public void run() {

        while (true) {
            try {

                this.testConnectionPools();

                if (this.isOkForAllConnect()) {
                    if (this.isNeedRestartServerSocket()) {
                        //准备重新启动服务器端口，禁止多次重新启动服务器端口 
                        this.setRestartedStatus(false);
                        this.restartServerSocket();         //重启服务
                    }
                }
                this.sleep(BaseConstant.CPListenerThreadSleepTime);
            } catch (InterruptedException e) {
                logger.error("连接池监听线程将终止........." + e);
            }

        }
    }

    private boolean isNeedRestartServerSocket() {
        synchronized (isRestartedServerSocket) {
            return this.isRestartedServerSocket.booleanValue();
        }
    }

    private void setRestartedStatus(boolean status) {
        synchronized (this.isRestartedServerSocket) {
            this.isRestartedServerSocket = new Boolean(status);
        }
    }

    private boolean isOkForAllConnect() {
        synchronized (isConnectForDataConnection) {
            if (this.isConnectForDataConnection.booleanValue()) {
                return true;
            } else {
                return false;
            }
        }

    }

    private void testConnectionPools() {
        //测试连接
        if (!this.testConnection()) {

            //设置连接不可用
            this.setConnectStatus(false);

            //关闭监听服务器
            synchronized (this.isClosedServerSocket) {
                if (!this.isClosedServerSocket.booleanValue()) {
                    this.closeServerSocket();
                    this.isClosedServerSocket = new Boolean(true);
                }
            }
            ++count;
            logger.info("尝试重建连接池" + count + "次.....");
            return;
        }

        //连接可用,正常运行测试连接无问题
        //每次数据库连接池重置时，count置0
        if (count == 0) {
            this.setConnectStatus(true);

        } else {

            logger.info("连接中断后已可用.....");
            //重建连接池
            if (this.resetConnectonPool()) {

                //设置连接池可用标志
                this.setConnectStatus(true);

                // 设置可以重新启动服务器SOCKET端口
                this.setRestartedStatus(true);

            }
        }
    }

    private void setConnectStatus(boolean status) {

        synchronized (isConnectForDataConnection) {
            this.isConnectForDataConnection = new Boolean(status);
        }
    }

    private boolean testConnectionPool(DbcpHelper dbcp) {
        boolean result = false;
        DbHelper dbHelper = null;
        try {

            dbHelper = new DbHelper("data", dbcp.getConnection());

            result = dbHelper.getFirstDocument(testSql);

            return result;

        } catch (SQLException e) {
            logger.error("数据库异常,错误代码:" + e.getErrorCode() + " 消息:" + e.getMessage());
            return false;
        } finally {
            try {
                if (dbHelper != null) {
                    dbHelper.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private boolean testConnection() {
        Connection con = null;
        ResultSet rs = null;
        Statement stmt = null;

        try {
            Class.forName((String) test_config.get("Driver"));
            DriverManager.setLoginTimeout(10);
            con = DriverManager.getConnection((String) test_config.get("URL"),
                    (String) test_config.get("User"),
                    (String) test_config.get("Password"));
            stmt = con.createStatement();
            rs = stmt.executeQuery(this.testSql);
            if (rs.next()) {
                return true;
            }

        } catch (ClassNotFoundException e) {
            logger.error("数据库异常:" + e.getMessage());
            return false;
        } catch (SQLException e) {
            logger.error("数据库异常,错误代码:" + e.getErrorCode() + " 消息:" + e.getMessage());
            return false;

        } finally {
            try {
                if (con != null) {
                    if (rs != null) {
                        rs.close();
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                    con.close();
                }

            } catch (SQLException e) {
                return false;
            }
        }
        return false;
    }

    private boolean resetConnectonPool() {
        DbcpHelper cp = null;

        try {
            cp = new DbcpHelper((String) test_config.get(
                    "Driver"), (String) test_config.get("URL"),
                    (String) test_config.get("User"),
                    (String) test_config.get("Password"),
                    Integer.parseInt((String) test_config.get(
                    "MaxActive")),
                    Integer.parseInt((String) test_config.get("MaxIdle")),
                    Integer.parseInt((String) test_config.get("MaxWait")));

            if (!this.testConnectionPool(cp)) {
                logger.warn("重建的连接池不可用.....");
                return false;
            }

           
            synchronized (BaseConstant.DATA_DBCPHELPER) {
                if (BaseConstant.DATA_DBCPHELPER != null) {
                    BaseConstant.DATA_DBCPHELPER.close();
                }

                BaseConstant.DATA_DBCPHELPER = cp;
            }

            logger.info("重设连接池.....");
            
            this.count = 0;
            
            return true;
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
            return false;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }

    }

    private void closeServerSocket() {

        try {
            if (SocketUtil.serverSocket != null) {
                synchronized (this.isSqlConExceptionClose) {
                    this.isSqlConExceptionClose = new Boolean(true);
                }

                SocketUtil.serverSocket.close();
                SocketUtil.serverSocket = null;
                SocketUtil.closeConnectedSockets();

                logger.warn("数据库连接异常,关闭监听服务器");
                return;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

    private boolean restartServerSocket() {
        
        SocketUtil socketUtil = new SocketUtil();
        logger.info("数据库连接恢复,重新启动监听服务器");
        try {
            socketUtil.startSocketListener();
            //恢复监听器监听功能
            this.restoreControl();
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }

    }

    private void restoreControl() {
        logger.info("恢复控制参数");
        synchronized (isClosedServerSocket) {
            isClosedServerSocket = new Boolean(false);
        }
        synchronized (isRestartedServerSocket) {
            isRestartedServerSocket = new Boolean(false);
        }

        synchronized (isConnectForDataConnection) {
            isConnectForDataConnection = new Boolean(false);
        }
    }
}
