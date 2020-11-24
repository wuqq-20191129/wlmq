/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.commu.commu.application;

import com.goldsign.commu.commu.connection.CommuConnection;
import com.goldsign.commu.commu.connection.ConnectionReader;
import com.goldsign.commu.commu.connection.ConnectionWriter;
import com.goldsign.commu.commu.env.BaseConstant;
import com.goldsign.commu.commu.env.CommuConstant;
import com.goldsign.commu.commu.env.ConfigConstant;
import com.goldsign.commu.commu.exception.CommuException;
import com.goldsign.commu.commu.exception.ConfigException;
import com.goldsign.commu.commu.exception.ThreadException;
import com.goldsign.commu.commu.thread.CommuStopHook;
import com.goldsign.commu.commu.thread.CommuThreadManager;
import com.goldsign.commu.commu.thread.ConnectionPoolListener;
import com.goldsign.commu.commu.util.CommuXMLHandler;
import com.goldsign.commu.commu.util.InitialUtil;
import com.goldsign.commu.commu.util.SocketUtil;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.Hashtable;
import org.apache.log4j.Logger;

/**
 *
 * @author lenovo
 */
public class BaseApplication implements IApplication{

    private static Logger logger = Logger.getLogger(BaseApplication.class.getName());
    
    private boolean stoped = false;
    
    public static ServerSocket serverSocket;
    
    @Override
    public void initConfig() throws ConfigException{
        
        Hashtable commuConfig = null;
        InitialUtil util = new InitialUtil();
        try {
            CommuXMLHandler commuXMLHandler = new CommuXMLHandler();
            commuConfig = commuXMLHandler.parseConfigFile(BaseConstant.CONFIGFILE);
            BaseConstant.commuConfig = commuConfig;
            //初始化日志
            util.initLogFile(commuConfig);
            
            //初始化IpConfgis
            util.initIpConfigs(commuConfig);
            
            //初始化Message
            util.initAppMessage(commuConfig);
            
            //初始化socket配置
            util.initSocket(commuConfig);

            //初始化数据库连接池
            util.initConnectionPool(commuConfig);

            //初始化线程池配置
            util.initThreadPool(commuConfig);

            //初始化应用配置
            initAppConfig();
        } catch (Exception e) {
            logger.error("初始化 " + BaseConstant.CONFIGFILE + " 错误! " + e);
            throw new ConfigException("初始化 " + BaseConstant.CONFIGFILE + " 错误! " + e);
        }
    }
    
    protected void initAppConfig(){
        
        //子类重载，实现应用的配置
    }

    @Override
    public void startThreads()throws ThreadException {
        
        //启动消息处理线程
        this.startMessageHandleThread();
        //启动连接池监听线程
        this.startConnectionPoolListener();
        //启动应用线程
        this.startAppThreads();
    }
    
    protected void startAppThreads(){
    
        //子类重载，实现应用线程
    }
    
    private void startMessageHandleThread(){
        CommuThreadManager ctm = new CommuThreadManager();
        ctm.startHandleThreads();
    }
    
    private void startConnectionPoolListener() {
        Hashtable ht = null;
        ConnectionPoolListener ccpl = null;

        ht = (Hashtable) BaseConstant.commuConfig.get(ConfigConstant.DataConnectionPool_TAG);
        ccpl = new ConnectionPoolListener(ht);
        ccpl.start();
        logger.info("连接池监听线程已启动 。。。。。。");
    }

    @Override
    public void startSocketServer()throws BindException,IOException,CommuException{
        //启动线路消息接收SOCKET服务器
        SocketUtil util = new SocketUtil();
        util.startSocketListener();
    }

    public void start() {
        BaseConstant.application = this;
        Runtime.getRuntime().addShutdownHook(new CommuStopHook(this));
        this.run();
    }
    
    private void run(){
        
        try {
            logger.info("ES通讯服务器正在启动 。。。。。。");
            //初始化配置
            initConfig();
            //保证缓存获取
            Thread.sleep(2000);
            //启动线程
            startThreads();
            //启动服务
            startSocketServer();
            
            logger.info("通讯启动成功！ ");
         } catch (ConfigException e) {
            logger.error("配置文件读入失败 - " + e);
            
        } catch (BindException e) {
            logger.error("端口 "+ BaseConstant.SocketPort + "　已经被服务占用！");
    
        } catch (Exception e) {
            logger.error("错误 -" + e);
            
        } finally {
            //如是连接池问题引起socket关闭,则无需关闭程序
            if (!ConnectionPoolListener.isSqlConExceptionClose.booleanValue()) {
                logger.info("程序结束处理 。。。。。。");
                stop();
            }
        }
    }
    
    public void stop() {
        if (!stoped) {
            stoped = !stoped;
            logger.info("通讯服务器正在准备停止。。。。。。");

            //关闭SOCKET连接
            try {
                serverSocket.close();
                Thread.sleep(60000);
            } catch (Exception e) {
                logger.error("ServerSocket关闭失败..." + e.getMessage());
            }

            //关闭数据库连接
            try {
                logger.info("关闭连接池DATA_DBCPHELPER。。。。。。");
                BaseConstant.DATA_DBCPHELPER.close();
            } catch (Exception e) {
                logger.error("DATA_DBCPHELPER连接池关闭失败..." + e.getMessage());
            }

            //关闭线程
            logger.info("处理线程池所有线程。。。。。。");
            CommuThreadManager.stopHandleThread();

            logger.info("通讯服务器已停止！");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
    
    /*
     * 检查连接IP是否合法
     */
    public int checkConnection(String ip) {
        
        if(!checkIpConfig(ip)){
            return CommuConstant.CONN_IPCHECK_ILLEGAL;
        }
        
        //判断是否已存在正常连接 hwj tmp modity 20140118
        /*String ipStatus = (String) BaseConstant.allConnectingIps.get(ip);
        if (null != ipStatus && ipStatus.equals(CommuConstant.CONN_OPENED)) {
            return CommuConstant.CONN_IPCHECK_OPENED;
        }*/

        return CommuConstant.CONN_IPCHECK_NORMAL;
    }
    
    protected boolean checkIpConfig(String ip){
    
        return BaseConstant.ipConfigs.contains(ip);
    }
    
    public ConnectionReader getConnectionReader(BufferedInputStream in){
        return new ConnectionReader(in);
    }

    public ConnectionWriter getConnectionWriter(CommuConnection commuConnection, 
            OutputStream out){
        return new ConnectionWriter(commuConnection, out);
    }
}
