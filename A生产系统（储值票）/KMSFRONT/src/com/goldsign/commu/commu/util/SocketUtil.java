package com.goldsign.commu.commu.util;

import com.goldsign.commu.commu.application.BaseApplication;
import com.goldsign.commu.commu.connection.CommuConnection;
import com.goldsign.commu.commu.env.BaseConstant;
import com.goldsign.commu.commu.env.CommuConstant;
import com.goldsign.commu.commu.exception.CommuException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class SocketUtil {

    private static Logger logger = Logger.getLogger(SocketUtil.class.getName());
    
    public static ServerSocket serverSocket;
    public static HashMap<String, CommuConnection> connectedSockets = new HashMap<String, CommuConnection>();
    public static int connectID = 0;

    /*
     * 关闭所以连接
     */
    public static void closeConnectedSockets() {
        
        synchronized (connectedSockets) {
            if (connectedSockets.isEmpty()) {
                return;
            }
            Set keys = connectedSockets.keySet();
            Iterator it = keys.iterator();
            Integer key = null;
            CommuConnection con = null;

            while (it.hasNext()) {
                key = (Integer) it.next();
                con = (CommuConnection) connectedSockets.get(key);
                con.closeConnection();
            }
            connectedSockets.clear();

        }
    }

    /*
     * 启动SOCKET服务
     */
    public void startSocketListener() throws BindException, IOException, CommuException {
        
        serverSocket = new ServerSocket(BaseConstant.SocketPort);
       
        this.connectID = 0;

        logger.info("服务器正常启动！正在监听端口 : " + BaseConstant.SocketPort);
        while (true) {
            
            this.connectID++;

            Socket socket = serverSocket.accept();
            socket.setKeepAlive(true);
            String ip = socket.getInetAddress().getHostAddress();
            int port = socket.getPort();
            logger.info("接受新的连接请求，来自 " + ip + ":" + port);
            try {
                BaseApplication app = ((BaseApplication)BaseConstant.application);
                int checkResult = app.checkConnection(ip);
                if (checkResult == 0) {

                    BaseConstant.allConnectingIps.put(ip, CommuConstant.CONN_OPENED);
                    logger.info("新连接正在处理......!");
                    CommuConnection commuConnection = new CommuConnection(socket, ip, this.connectID);

                    this.addConnection(ip, commuConnection);

                    Thread t = new Thread(commuConnection, ip);
                    
                    t.setPriority(BaseConstant.ReadThreadPriorityAdd);
                    t.start();
                } else {
                    socket.close();     //非法时，关闭连接
                    if (checkResult == CommuConstant.CONN_IPCHECK_ILLEGAL) {
                        logger.warn("连接不合法！地址：" + ip);
                        throw new CommuException("连接不合法！地址：" + ip);
                    }
                    if (checkResult == CommuConstant.CONN_IPCHECK_OPENED) {
                        logger.warn("连接已经存在！地址：" + ip);
                        throw new CommuException("连接已经存在！地址：" + ip);
                    }
                }
            } catch (CommuException e) {
                logger.error("创建连接失败 - " + e);
            }
            /*
             * catch (Exception e) { e.printStackTrace();
             *
             * }
             */
        }
    }

    /*
     * 添加连接
     */
    public static void addConnection(String connIp, CommuConnection con) {
        synchronized (connectedSockets) {
            if (connectedSockets.containsKey(connIp)) {
                connectedSockets.remove(connIp);
            }
            connectedSockets.put(connIp, con);
        }
    }
    
    /**
     * 删除联接
     * @param connID 
     */
    public static void removeConnection(String connIp) {
        synchronized (connectedSockets) {
            if (connectedSockets.containsKey(connIp)) {
                connectedSockets.remove(connIp);
            }
        }
    }
    
    public static CommuConnection getCommuConnection(String ip){
        CommuConnection commuConn = null;
        if(null != ip && !ip.equals("")){
            commuConn = SocketUtil.connectedSockets.get(ip);
        }
        return commuConn;
    }
}
