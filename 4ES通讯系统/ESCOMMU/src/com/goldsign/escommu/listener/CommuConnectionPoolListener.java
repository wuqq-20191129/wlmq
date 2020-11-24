package com.goldsign.escommu.listener;

import com.goldsign.escommu.connection.CommuConnection;
import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.dbutil.DbcpHelper;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.ConfigTagConstant;
import com.goldsign.escommu.thread.CommuMessageHandleThread;
import com.goldsign.escommu.thread.CommuServerThread;
import com.goldsign.escommu.thread.CommuThreadManager;
import com.goldsign.escommu.util.CommuExceptionBufferUtil;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.SocketUtil;
import com.goldsign.escommu.vo.CommuHandledMessage;
import java.io.IOException;
import java.sql.*;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class CommuConnectionPoolListener
        extends Thread {

    private static Logger logger = Logger.getLogger(CommuConnectionPoolListener.class.getName());
    private int count = 0;
//  private static DbcpHelper test_cp = null;
    private Hashtable test_config = null;
    private String msgFlag = "";
    private int flag = -1;      //测试SQL选择票识
    private String testSql = "";
    public static Boolean isClosedServerSocket = new Boolean(false);        //SOCKET是否已关闭
    public static Boolean isRestartedServerSocket = new Boolean(false);     //是否需要重启SOCKET服务
    public static Boolean isConnectForDataConnection = new Boolean(false);  //
    public static Boolean isSqlConExceptionClose = new Boolean(false);

    public CommuConnectionPoolListener() {
    }

    /**
     * 创建连结监听
     * 
     * @param config
     * @param msgFlag
     * @param flag 
     */
    public CommuConnectionPoolListener(Hashtable config, String msgFlag, int flag) {
        
        this.test_config = config;
        this.msgFlag = msgFlag;
        this.flag = flag;
        switch (this.flag) {
            case 0: {
                this.testSql = AppConstant.TestSqlForData;
                break;
            }
            /*
             * case 1: { this.testSql = ApplicationConstant.TestSqlForLog;
             * break; } case 2: { this.testSql =
             * ApplicationConstant.TestSqlForFlow; break; } case 3: {
             * this.testSql = ApplicationConstant.TestSqlForEmergent; break; }
             */

        }
    }

    /**
     * 运行
     * 
     */
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
                this.sleep(AppConstant.CPListenerThreadSleepTime);
            } catch (InterruptedException e) {
                DateHelper.screenPrintForEx("连接池监听线程将终止........." + e);
            }

        }
    }

    /**
     * 是否需要重启SOCKET
     * 
     * @return 
     */
    private boolean isNeedRestartServerSocket() {
        synchronized (isRestartedServerSocket) {
            return this.isRestartedServerSocket.booleanValue();
        }
    }

    /**
     * 设置SOCKET重启动状态
     * 
     * @param status 
     */
    private void setRestartedStatus(boolean status) {
        synchronized (this.isRestartedServerSocket) {
            this.isRestartedServerSocket = new Boolean(status);
        }
    }

    /**
     * 是否数据库连接正常
     * 
     * @return 
     */
    private boolean isOkForAllConnect() {
        synchronized (isConnectForDataConnection) {
            if (this.isConnectForDataConnection.booleanValue()) {
                return true;
            } else {
                return false;
            }

        }

    }

    /**
     * 测试数据库连接池
     * 
     */
    private void testConnectionPools() {
        //测试连接
        if (!this.testConnection()) {

            //设置连接不可用
            this.setConnectStatus(flag, false);

            //关闭监听服务器
            synchronized (this.isClosedServerSocket) {
                if (!this.isClosedServerSocket.booleanValue()) {
                    this.closeServerSocket();
                    this.isClosedServerSocket = new Boolean(true);
                }
            }
            ++count;
            DateHelper.screenPrintForEx("尝试重建" + msgFlag + "连接池" + count + "次.....");
            return;
        }

        //连接可用,正常运行测试连接无问题
        //每次数据库连接池重置时，count置0
        if (count == 0) {
            this.setConnectStatus(flag, true);

        } else {

            DateHelper.screenPrintForEx(msgFlag + "连接中断后已可用.....");
            //重建连接池
            if (this.resetConnectonPool()) {

                //设置连接池可用标志
                this.setConnectStatus(this.flag, true);

                // 设置可以重新启动服务器SOCKET端口
                this.setRestartedStatus(true);

            }
        }
    }

    /**
     * 设置数据库连接状态
     * 
     * @param flag
     * @param status 
     */
    private void setConnectStatus(int flag, boolean status) {
        switch (this.flag) {
            case 0: {
                synchronized (isConnectForDataConnection) {
                    this.isConnectForDataConnection = new Boolean(status);
                }
                break;
            }

        }
    }

    /**
     * 测试数据库连接池
     * 
     * @param dbcp
     * @return 
     */
    private boolean testConnectionPool(DbcpHelper dbcp) {
        boolean result = false;
        DbHelper dbHelper = null;
        try {

            dbHelper = new DbHelper("data", dbcp.getConnection());

            result = dbHelper.getFirstDocument(testSql);

            return result;

        } catch (SQLException e) {
            DateHelper.screenPrintForEx(msgFlag + "数据库异常,错误代码:" + e.getErrorCode()
                    + " 消息:" + e.getMessage());
            return false;
        } finally {
            try {
                if (dbHelper != null) {
                    dbHelper.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试数据库连接
     * 
     * @return 
     */
    private boolean testConnection() {
        Connection con = null;
        ResultSet rs = null;
        Statement stmt = null;

        try {
            Class.forName((String) test_config.get(ConfigTagConstant.Driver_TAG));
            DriverManager.setLoginTimeout(10);
            con = DriverManager.getConnection((String) test_config.get(ConfigTagConstant.URL_TAG),
                    (String) test_config.get(ConfigTagConstant.User_TAG),
                    (String) test_config.get(ConfigTagConstant.Password_TAG));
            stmt = con.createStatement();
            rs = stmt.executeQuery(this.testSql);
            if (rs.next()) {
                return true;
            }

        } catch (ClassNotFoundException e) {
            return false;
        } catch (SQLException e) {
            DateHelper.screenPrintForEx(msgFlag + "数据库异常,错误代码:" + e.getErrorCode()
                    + " 消息:" + e.getMessage());
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

    /**
     * 重置数据库连接池
     * 
     * @return 
     */
    private boolean resetConnectonPool() {
        DbcpHelper cp = null;

        try {
            cp = new DbcpHelper((String) test_config.get(
                    ConfigTagConstant.Driver_TAG), 
                    (String) test_config.get(ConfigTagConstant.URL_TAG),
                    (String) test_config.get(ConfigTagConstant.User_TAG),
                    (String) test_config.get(ConfigTagConstant.Password_TAG),
                    Integer.parseInt((String) test_config.get(ConfigTagConstant.MaxActive_TAG)),
                    Integer.parseInt((String) test_config.get(ConfigTagConstant.MaxIdle_TAG)),
                    Integer.parseInt((String) test_config.get(ConfigTagConstant.MaxWait_TAG)));

            if (!this.testConnectionPool(cp)) {
                DateHelper.screenPrintForEx("重建的" + msgFlag + "连接池不可用.....");
                return false;
            }

            switch (this.flag) {
                case 0: {
                    synchronized (AppConstant.DATA_DBCPHELPER) {
                        if (AppConstant.DATA_DBCPHELPER != null) {
                            AppConstant.DATA_DBCPHELPER.close();
                        }
                        AppConstant.DATA_DBCPHELPER = cp;
                    }
                    break;
                }
            }

            DateHelper.screenPrintForEx("重设" + msgFlag + "连接池.....");
            this.count = 0;
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        } catch (SQLException e) {
            return false;
        }

    }

    /**
     * 关闭SOCKET通讯
     * 
     */
    private void closeServerSocket() {

        try {
            if (SocketUtil.serverSocket != null) {
                synchronized (this.isSqlConExceptionClose) {
                    this.isSqlConExceptionClose = new Boolean(true);
                }

                SocketUtil.serverSocket.close();
                SocketUtil.serverSocket = null;
                SocketUtil.closeConnectedSockets();

                this.dumpThreadUnHandledMsgs();
                DateHelper.screenPrintForEx(this.msgFlag + "数据库连接异常,关闭监听服务器");
                return;
            }
        } catch (IOException e) {
            DateHelper.screenPrintForEx(e.getMessage());

        }

    }

    /**
     * 重启SOCKET服务
     * 
     * @return 
     */
    private boolean restartServerSocket() {
        CommuServerThread server = new CommuServerThread();
        DateHelper.screenPrintForEx("数据库连接恢复,重新启动监听服务器");
        try {
            this.handleSqlBufferMsgs();
            server.start();
            //恢复监听器监听功能
            this.restoreControl();
            return true;
        } catch (InterruptedException e) {
            return false;
        }

    }

    /**
     * 恢复控制参数
     * 
     */
    private void restoreControl() {
        DateHelper.screenPrintForEx("恢复控制参数");
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

    /**
     * 处理SQL缓存消息
     * 
     * @throws InterruptedException 
     */
    private void handleSqlBufferMsgs() throws InterruptedException {
        Vector unHandleSqlMsgs = CommuExceptionBufferUtil.getSqlUnHandleMsgs();
        if (unHandleSqlMsgs == null || unHandleSqlMsgs.isEmpty()) {
            return;
        }
        CommuHandledMessage chm = null;
        CommuConnection con = new CommuConnection();
        DateHelper.screenPrintForEx("由于Sql连接异常需重新处理的消息记录为" + unHandleSqlMsgs.size()
                + "条");
        for (int i = 0; i < unHandleSqlMsgs.size(); i++) {
            chm = (CommuHandledMessage) unHandleSqlMsgs.get(i);
            con.processReaderResult(chm.getIp(), chm.getReadResult());
            this.sleep(AppConstant.SqlMsgHandleSleepTime);

        }
        DateHelper.screenPrintForEx("成功重新处理由于Sql连接异常的消息记录为" + unHandleSqlMsgs.size()
                + "条");
    }

    /**
     * 备份线程未处理消息
     * 
     */
    public static void dumpThreadUnHandledMsgs() {
        CommuMessageHandleThread t = null;
        Vector msgs = null;
        CommuHandledMessage msg = null;
        for (int i = 0; i < CommuThreadManager.handleThreads.size(); i++) {
            t = (CommuMessageHandleThread) CommuThreadManager.handleThreads.get(i);
            if (t == null) {
                DateHelper.screenPrint("第" + i + "个线程已是NULL值");
                continue;
            }
            //由于数据库连接异常关闭时,将线程未处理的消息放入待处理缓存
            msgs = t.getAllMsgs();

            DateHelper.screenPrintForEx("线程" + t.getName() + "未处理消息" + msgs.size());
            for (int j = 0; j < msgs.size(); j++) {
                msg = (CommuHandledMessage) msgs.get(j);
                CommuExceptionBufferUtil.setSqlUnHandleMsgs(msg.getIp(),
                        (byte[]) (msg.getReadResult().get(1)));
            }
        }
    }
}
