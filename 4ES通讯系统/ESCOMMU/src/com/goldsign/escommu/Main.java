/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.ConfigTagConstant;
import com.goldsign.escommu.env.LogConstant;
import com.goldsign.escommu.exception.ConfigFileException;
import com.goldsign.escommu.listener.CommuConnectionPoolListener;
import com.goldsign.escommu.thread.*;
import com.goldsign.escommu.util.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.Hashtable;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class Main {

    private static Logger logger = Logger.getLogger(Main.class.getName());
    /**
     * 日志记录使用
     */
    private long hdlStartTime; //处理的起始时间
    private long hdlEndTime;//处理的结束时间
    /**
     * *启动控制**
     */
    private boolean stoped = false;
    //----------------------
    /**
     * ******SOCKET*****
     */
    public static ServerSocket serverSocket;
    //----------------------

    public static void main(String[] arg) {
        Main app = new Main();
        Runtime.getRuntime().addShutdownHook(new CommuStopHook(app));
        app.start();
    }

    /**
     * 启动
     * 
     */
    public void start() {
        CommuThreadManager ctm = new CommuThreadManager();
        try {
            this.hdlStartTime = System.currentTimeMillis();

            DateHelper.screenPrintForEx("ES通讯服务器正在启动 。。。。。。");
            
            //从配置文件取得数据放入ApplicationConstant对应缓存变量中.
            initConfig();
            
            //检查配置是否设置合理
            this.checkConfigure();
            
            //启动日志级别线程
            this.startLogLevelMonitor();
            
            //启动缓存获取线程
            this.startBufferGetThread();

            //保证缓存获取
            Thread.sleep(2000);

            //启动消息处理线程
            ctm.startHandleThreads();

            //启动文件获取线程
            this.startFileGetThread();

            //启动生成和查询审计和错误文件线程
            this.startAuditFileThread();
            
            //生成物理卡号，逻辑卡对照表
            this.startPhyLogicFileThread();

            //启动连接池监听线程
            this.startConnectionPoolListener();

            //启动线路消息接收SOCKET服务器
            SocketUtil util = new SocketUtil();
            util.startSocketListener();

            DateHelper.screenPrintForEx("通讯启动成功！ ");
            
            //记录日志
            LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_START, CharUtil.getLocalIp(),
                    this.hdlStartTime, this.hdlEndTime, LogConstant.RESULT_HDL_SUCESS,
                    Thread.currentThread().getName(), AppConstant.LOG_LEVEL_INFO, "通讯启动");

        } catch (ConfigFileException e) {
            DateHelper.screenPrintForEx("配置文件读入失败 - " + e);
            logger.error("配置文件读入失败 - " + e);
            //记录日志 
            this.hdlEndTime = System.currentTimeMillis();
            LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_START,  CharUtil.getLocalIp(),
                    this.hdlStartTime, this.hdlEndTime, LogConstant.RESULT_HDL_FAIL,
                    Thread.currentThread().getName(), AppConstant.LOG_LEVEL_ERROR_SYS, e.getMessage());
        } catch (BindException e) {
            DateHelper.screenPrintForEx("端口 "+ AppConstant.Port + "　已经被服务占用！");
            logger.error("端口 "+ AppConstant.Port + "　已经被服务占用！"); 
            //记录日志 
            this.hdlEndTime = System.currentTimeMillis();
            LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_START, CharUtil.getLocalIp(),
                    this.hdlStartTime, this.hdlEndTime, LogConstant.RESULT_HDL_FAIL,
                    Thread.currentThread().getName(), AppConstant.LOG_LEVEL_ERROR_SYS, e.getMessage());

        } catch (Exception e) {
            DateHelper.screenPrintForEx("错误 -" + e);
            logger.error("错误 - " + e);
            this.hdlEndTime = System.currentTimeMillis();
            LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_START, CharUtil.getLocalIp(),
                    this.hdlStartTime, this.hdlEndTime, LogConstant.RESULT_HDL_FAIL,
                    Thread.currentThread().getName(), AppConstant.LOG_LEVEL_ERROR_SYS, e.getMessage());

        } finally {
            //如是连接池问题引起socket关闭,则无需关闭程序
            if (!CommuConnectionPoolListener.isSqlConExceptionClose.booleanValue()) {
                DateHelper.screenPrintForEx("程序结束处理 。。。。。。");
                stop();
            }
        }
    }

    /**
     * 启动缓存获取线程
     * 
     */
    private void startBufferGetThread() {
       
        CommuBufferGetThread t = new CommuBufferGetThread();
        t.start();
        DateHelper.screenPrintForEx("缓存获取线程已启动 。。。。。。");
    }

    /**
     * 启动获取文件线程
     * 
     */
    private void startFileGetThread() {
      
        ComFindFileThread t = new ComFindFileThread();
        t.start();
        DateHelper.screenPrintForEx("获取文件线程已启动 。。。。。。");
    }

    /**
     * 启动审计文件线程
     * 
     */
    private void startAuditFileThread() {
        ComAuditFileThread t = new ComAuditFileThread();
        t.start();
        DateHelper.screenPrintForEx("审计文件线程已启动 。。。。。。");
    }
    
    /**
     * 启动物理卡号，逻辑卡号对照文件线程
     * 
     */
    private void startPhyLogicFileThread() {
        ComPhysicLogicThread t = new ComPhysicLogicThread();
        t.start();
        DateHelper.screenPrintForEx("物理卡号，逻辑卡号对照文件线程已启动");
    }

    /**
     * 启动连接池监听线程
     * 
     */
    private void startConnectionPoolListener() {
        Hashtable ht = null;
        CommuConnectionPoolListener ccpl = null;

        ht = (Hashtable) AppConstant.commuConfig.get(ConfigTagConstant.DataConnectionPool_TAG);
        ccpl = new CommuConnectionPoolListener(ht, "data", 0);
        ccpl.start();
        DateHelper.screenPrintForEx("连接池监听线程已启动 。。。。。。");
    }

    /**
     * 启动日志级别监听线程
     * 
     */
    private void startLogLevelMonitor() {
        CommuLogLevelThread t = new CommuLogLevelThread();
        t.start();
        DateHelper.screenPrintForEx("日志级别监听线程已启动 。。。。。。");
    }

    /**
     * 初始化配置
     * 
     * @throws ConfigFileException 
     */
    private void initConfig() throws ConfigFileException {

        Hashtable commuConfig = null;
        InitialUtil util = new InitialUtil();
        try {
            CommuXMLHandler commuXMLHandler = new CommuXMLHandler();
            commuConfig = commuXMLHandler.parseConfigFile(AppConstant.CONFIGFILE);
            AppConstant.commuConfig = commuConfig;

            //初始化应用相关配置
            util.initApp(commuConfig);
            
            //初始化日志
            util.initLogFile(commuConfig);

            //初始化socket配置
            util.initSocket(commuConfig);

            //初始化FTP配置
            util.initFtp(commuConfig);

            //初始化审计文件相关
            util.initAudit(commuConfig);
            
            //初始化物理卡号与逻辑卡号对照文件相关
            util.initPhyLogic(commuConfig);

            //初始化参数文件相关
            util.initDisParam(commuConfig);

            //初始化数据库连接池
            util.initFromConnectionPool(commuConfig);

            //初始化线程池配置
            util.initThreadPool(commuConfig);

            //初始化数据库数据配置数据优先级消息
            util.initDbData();

        } catch (Exception e) {
            DateHelper.screenPrintForEx("初始化 " + AppConstant.CONFIGFILE + " 错误! " + e);
            logger.error("初始化 " + AppConstant.CONFIGFILE + " 错误! " + e);
            throw new ConfigFileException("初始化 " + AppConstant.CONFIGFILE + " 错误! " + e);
        }
    }

    /**
     * 检查配置
     * 
     */
    private void checkConfigure() {
        
        if (AppConstant.FtpTimeout > 10000) {
            LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_CONFIG, CharUtil.getLocalIp(), 
                    System.currentTimeMillis(), System.currentTimeMillis(), LogConstant.RESULT_HDL_WARN,
                    Thread.currentThread().getName(), AppConstant.LOG_LEVEL_WARN, "FTP连接超时大于10秒，可能会影响性能");
        }

        if (AppConstant.MaxThreadNumber > 30) {
            LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_CONFIG, CharUtil.getLocalIp(),
                    System.currentTimeMillis(), System.currentTimeMillis(), LogConstant.RESULT_HDL_WARN,
                    Thread.currentThread().getName(), AppConstant.LOG_LEVEL_WARN, "线程总数量大于30，可能会影响性能");
        }

    }

    /**
     * 停止通讯服务
     * 
     */
    public void stop() {
        if (!stoped) {
            stoped = !stoped;
            DateHelper.screenPrintForEx("通讯服务器正在准备停止。。。。。。");
            logger.info("通讯服务器将在1分钟后停止。。。。。。");

            //关闭SOCKET连接
            try {
                serverSocket.close();
                Thread.sleep(60000);
            } catch (Exception e) {
                logger.error("ServerSocket关闭失败..."+e.getMessage());
            }

            //关闭数据库连接
            try {
                DateHelper.screenPrintForEx("关闭连接池DATA_DBCPHELPER。。。。。。");
                AppConstant.DATA_DBCPHELPER.close();
            } catch (Exception e) {
                logger.error("DATA_DBCPHELPER连接池关闭失败..."+e.getMessage());
            }

            //关闭线程
            DateHelper.screenPrintForEx("处理线程池所有线程。。。。。。");
            CommuThreadManager.stopHandleThread();

            DateHelper.screenPrintForEx("通讯服务器已停止！");
            logger.info("通讯服务器已停止！");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
