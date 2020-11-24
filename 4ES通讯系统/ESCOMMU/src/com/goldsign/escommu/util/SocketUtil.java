/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.util;

import com.goldsign.escommu.connection.CommuConnection;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.CommuConstant;
import com.goldsign.escommu.env.LogConstant;
import com.goldsign.escommu.exception.CommuException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class SocketUtil {

    public static ServerSocket serverSocket;
    //public static HashMap connectedSockets = new HashMap();
    public static Hashtable connectedSockets = new Hashtable();//hwj modify 20170801
    public static int connectID = 0;
    private static Logger logger = Logger.getLogger(SocketUtil.class.getName());
    /**
     * 日志记录使用
     */
    private long hdlStartTime; //处理的起始时间
    private long hdlEndTime;//处理的结束时间

    /**
     * 关闭所以连接
     * 
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

    /**
     * 启动SOCKET服务
     * 
     * @throws BindException
     * @throws IOException
     * @throws CommuException 
     */
    public void startSocketListener() throws BindException, IOException, CommuException {
        serverSocket = new ServerSocket(AppConstant.Port);
        // ApplicationConstant.serverSocket = serverSocket;
        this.connectID = 0;

        DateHelper.screenPrintForEx("服务器正常启动！正在监听端口 : " + AppConstant.Port);
        logger.info("服务器正常启动！正在监听端口 : " + AppConstant.Port);
        while (true) {
            this.hdlStartTime = System.currentTimeMillis();

            this.connectID++;

            Socket socket = serverSocket.accept();

            String ip = socket.getInetAddress().getHostAddress();
            int port = socket.getPort();
            DateHelper.screenPrintForEx("接受新的连接请求，来自 " + ip + ":" + port);
            logger.info("接受新的连接请求，来自 " + ip + ":" + port);
            try {
                int checkResult = checkConnection(ip);
                if (checkResult == 0) {

                    AppConstant.all_connecting_ip.put(ip, CommuConstant.CONN_OPENED);
                    DateHelper.screenPrintForEx("新连接正在处理......!");
                    CommuConnection commuConnection = new CommuConnection(socket, ip, this.connectID);

                    this.addConnection(this.connectID, commuConnection);

                    Thread t = new Thread(commuConnection, ip);
                    t.setPriority(AppConstant.ReadThreadPriorityAdd);
                    t.start();
                    LogDbUtil.writeConnectLog(new Date(System.currentTimeMillis()), ip, LogConstant.RESULT_HDL_SUCESS, "创建连接成功");
                } else {
                    socket.close();     //非法时，关闭连接
                    if (checkResult == CommuConstant.CONN_IPCHECK_ILLEGAL) {
                        DateHelper.screenPrintForEx("连接不合法！地址：" + ip);
                        logger.error("连接不合法！地址：" + ip);
                        throw new CommuException("连接不合法！地址：" + ip);
                    }
                    if (checkResult == CommuConstant.CONN_IPCHECK_OPENED) {
                        DateHelper.screenPrintForEx("连接已经存在！地址：" + ip);
                        logger.error("连接已经存在！地址：" + ip);
                        throw new CommuException("连接已经存在！地址：" + ip);
                    }
                }
            } catch (CommuException e) {
                LogDbUtil.writeConnectLog(new Date(System.currentTimeMillis()), ip, LogConstant.RESULT_HDL_FAIL, "创建连接失败");
                DateHelper.screenPrintForEx("创建连接失败 - " + e);
                logger.error("创建连接失败 - " + e);

                this.hdlEndTime = System.currentTimeMillis();
                //记录日志
                LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_CONNECTION,
                        ip,
                        this.hdlStartTime, this.hdlEndTime,
                        LogConstant.RESULT_HDL_FAIL,
                        Thread.currentThread().getName(),
                        AppConstant.LOG_LEVEL_ERROR, e.getMessage());
            }
            /*
             * catch (Exception e) { e.printStackTrace();
             *
             * }
             */
        }
    }

    /**
     * 添加连接
     * 
     * @param connID
     * @param con 
     */
    public static void addConnection(int connID, CommuConnection con) {
        Integer iConnID = new Integer(connID);
        synchronized (connectedSockets) {
            if (connectedSockets.containsKey(iConnID)) {
                connectedSockets.remove(iConnID);
            }
            connectedSockets.put(iConnID, con);
        }
    }
    
    /**
     * 删除联接
     * 
     * @param connID 
     */
    public static void removeConnection(int connID) {
        Integer iConnID = new Integer(connID);
        synchronized (connectedSockets) {
            if (connectedSockets.containsKey(iConnID)) {
                connectedSockets.remove(iConnID);
            }
        }
    }

    /**
     * 检查连接IP是否合法
     * 
     * @param ip
     * @return 
     */
    private int checkConnection(String ip) {
        //判断是否合法
        if (!AppConstant.devIps.containsValue(ip)) {

            return CommuConstant.CONN_IPCHECK_ILLEGAL;
            //return ConnectionConstant.CONN_IPCHECK_NORMAL; //test先放开
        }
        //判断是否已存在正常连接
        String connIp = (String) AppConstant.all_connecting_ip.get(ip);
        if (null != connIp && connIp.equals(CommuConstant.CONN_OPENED)) {
            return CommuConstant.CONN_IPCHECK_OPENED;
        }

        return CommuConstant.CONN_IPCHECK_NORMAL;
    }
    
    /**
     * 取得通讯连接
     * 
     * @param ip
     * @return 
     */
    public static CommuConnection getCommuConnection(String ip) {
        CommuConnection commuConn = null;
        if (null != ip && !ip.equals("")) {
            commuConn = (CommuConnection) SocketUtil.connectedSockets.get(ip);
        }
        return commuConn;
    }
}
