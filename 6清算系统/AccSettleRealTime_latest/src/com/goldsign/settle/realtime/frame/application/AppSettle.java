/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.application;

import com.goldsign.lib.db.util.DbcpHelper;
import com.goldsign.settle.realtime.frame.audit.AuditBase;
import com.goldsign.settle.realtime.frame.constant.FrameCheckConstant;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameCollectionConstant;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFlowConstant;
import com.goldsign.settle.realtime.frame.constant.FrameSysCfgConstant;
import com.goldsign.settle.realtime.frame.constant.FrameTacConstant;
import com.goldsign.settle.realtime.frame.constant.FrameThreadConstant;
import com.goldsign.settle.realtime.frame.constant.XMLConstant;
import com.goldsign.settle.realtime.frame.dao.BalanceWaterNoDao;
import com.goldsign.settle.realtime.frame.dao.BlackListDao;
import com.goldsign.settle.realtime.frame.dao.BusinessSettleDao;
import com.goldsign.settle.realtime.frame.dao.CheckConfigDao;
import com.goldsign.settle.realtime.frame.dao.ConfigRecordFmtDao;
import com.goldsign.settle.realtime.frame.dao.FileLogFtpDao;
import com.goldsign.settle.realtime.frame.dao.FlowDao;
import com.goldsign.settle.realtime.frame.dao.SettleQueueDao;
import com.goldsign.settle.realtime.frame.dao.StationDao;
import com.goldsign.settle.realtime.frame.dao.SysConfigDao;
import com.goldsign.settle.realtime.frame.download.DownloadAudit;
import com.goldsign.settle.realtime.frame.exception.ConfigFileException;
import com.goldsign.settle.realtime.frame.export.ExportFile;
import com.goldsign.settle.realtime.frame.factory.ThreadFactory;
import com.goldsign.settle.realtime.frame.thread.LogLevelThread;
import com.goldsign.settle.realtime.frame.thread.OctImportSettleThread;
import com.goldsign.settle.realtime.frame.thread.OctImportTrxThread;
import com.goldsign.settle.realtime.frame.thread.RealtimeSettleThread;
import com.goldsign.settle.realtime.frame.thread.SettleDetailLogThread;
import com.goldsign.settle.realtime.frame.thread.SettleLogThread;
import com.goldsign.settle.realtime.frame.thread.TimerRealtimeReportThread;
import com.goldsign.settle.realtime.frame.thread.TimerTempFileClearThread;
import com.goldsign.settle.realtime.frame.thread.TimerThread;
import com.goldsign.settle.realtime.frame.util.BusinessUtil;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.util.FileUtil;
import com.goldsign.settle.realtime.frame.util.LoggerUtil;
import com.goldsign.settle.realtime.frame.util.MessageUtil;
import com.goldsign.settle.realtime.frame.util.PrintHelper;
import com.goldsign.settle.realtime.frame.util.TaskUtil;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.RealtimeBalanceWater;

import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import com.goldsign.settle.realtime.frame.vo.StepInfo;
import com.goldsign.settle.realtime.frame.vo.TaskFinishControl;
import com.goldsign.settle.realtime.frame.vo.ThreadAttrVo;
import com.goldsign.settle.realtime.frame.xml.AppXMLHandler;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author hejj
 */
public class AppSettle extends AppSettleBase {

    private static Logger logger = Logger.getLogger(AppSettle.class.getName());
    private static PrintHelper printHelper = new PrintHelper();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        AppSettle app = new AppSettle();
        app.start();
    }

    public void start() {
        printHelper.screenPrintForEx("清算服务开始启动 。。。。。。");
        try {

            this.initConfig();
            this.startLogLevelMonitor();//启动日记级别监控线程
            this.startOctImportSettle();//启动公交结算定时任务线程
            this.startOctImportTrx();//启动公交交易定时任务线程
            this.startThreadPoolForFile();//启动文件处理线程池
            this.startThreadPoolForBcp();//启动文件BCP线程池
            this.startThreadPoolForTac();//启动tac校验线程池

            this.startThreadPoolForOther();//启动审计文件、收益文件、寄存器文件处理线程池
            this.startThreadPoolForMobile();//如果启用，启动手机支付处理线程池（文件处理)，不启用不启动
            this.startThreadPoolForNetPaid();//如果启用，启动互联网支付处理线程池（文件处理)，不启用不启动
            //20190612 二维码平台线程池
            this.startThreadPoolForQrCode();//如果启用，启动二维码平台处理线程池（文件处理)，不启用不启动

            this.startTimerTask();////启动定时任务线程
            this.startTimerTaskForClear();////启动定时清理中间文件、日志任务线程//hejj 20180816
            this.startTimerTaskForRealtimeReport();////启动定时生成实时报表任务线程//hejj 20181228
            this.startSettleLog();//清算后台处理日志
            this.startSettleDetailLog();//清算明细后台处理日志
            this.startSettleRealtime();//启动实时结算线程
            LoggerUtil.loggerLineForSectAll(logger, "清算系统初始化完成");
            //logger.info("清算系统初始化完成。\n******************************\n");
            this.startSettleBusiness();//开始处理清算业务

        } catch (ConfigFileException ex) {
            printHelper.screenPrintForEx(ex.getMessage());

        }

    }

    private void startTimerTaskForClear() {
        TimerTempFileClearThread t = new TimerTempFileClearThread();
        t.start();
        logger.info("定时清理中间文件、临时文件任务线程已启动");
    }

    private void startTimerTaskForRealtimeReport() {
        TimerRealtimeReportThread t = new TimerRealtimeReportThread();
        t.start();
        logger.info("定时生成实时报表任务线程已启动");
    }

    private int startThreadPoolForFile() {
        ThreadFactory fac = new ThreadFactory();
        ThreadAttrVo vo = this.getThreadAttrVoForFile();
        int n = fac.startHandleThreads(vo);
        return n;

    }

    private int startThreadPoolForFileMobile() {
        ThreadFactory fac = new ThreadFactory();
        ThreadAttrVo vo = this.getThreadAttrVoForFileMobile();
        int n = fac.startHandleThreads(vo);
        return n;

    }

    private int startThreadPoolForFileQrCode() {
        ThreadFactory fac = new ThreadFactory();
        ThreadAttrVo vo = this.getThreadAttrVoForFileQrCode();
        int n = fac.startHandleThreads(vo);
        return n;

    }

    private int startThreadPoolForFileNetPaid() {
        ThreadFactory fac = new ThreadFactory();
        ThreadAttrVo vo = this.getThreadAttrVoForFileNetPaid();
        int n = fac.startHandleThreads(vo);
        return n;

    }

    private int startThreadPoolForQrCode() {
        if (!BusinessUtil.onBusinessForQrCode()) {
            return -1;
        }
        int n = this.startThreadPoolForFileQrCode();//手机支付交易处理线程池
        n += this.startThreadPoolForOtherQrCode();//手机支付对账处理线程池

        return n;

    }
    private int startThreadPoolForMobile() {
        if (!BusinessUtil.onBusinessForMoble()) {
            return -1;
        }
        int n = this.startThreadPoolForFileMobile();//手机支付交易处理线程池
        n += this.startThreadPoolForOtherMobile();//手机支付对账处理线程池

        return n;

    }

    private int startThreadPoolForNetPaid() {
        if (!BusinessUtil.onBusinessForNetPaid()) {
            return -1;
        }
        int n = this.startThreadPoolForFileNetPaid();//互联网支付交易处理线程池
        n += this.startThreadPoolForOtherNetPaid();//互联网支付对账处理线程池

        return n;

    }

    private int startThreadPoolForBcp() {
        ThreadFactory fac = new ThreadFactory();
        ThreadAttrVo vo = this.getThreadAttrVoForBcp();
        int n = fac.startHandleThreads(vo);
        return n;

    }

    private int startThreadPoolForTac() {
        ThreadFactory fac = new ThreadFactory();
        ThreadAttrVo vo = this.getThreadAttrVoForTac();
        int n = fac.startHandleThreads(vo);
        return n;

    }

    private void startLogLevelMonitor() {
        LogLevelThread t = new LogLevelThread();
        t.start();
    }

    private void startTimerTask() {
        TimerThread t = new TimerThread();
        t.start();
        logger.info("定时任务线程已启动");
    }

    private void startSettleRealtime() {
        RealtimeSettleThread t = new RealtimeSettleThread();
        t.start();
        logger.info("实时结算线程已启动");
    }

    private void startSettleLog() {
        SettleLogThread t = new SettleLogThread();
        t.start();
        logger.info("清算后台处理日志线程已启动");
    }

    private void startSettleDetailLog() {
        SettleDetailLogThread t = new SettleDetailLogThread();
        t.start();
        logger.info("清算明细后台处理日志线程已启动");
    }

    private void startOctImportSettle() {
        OctImportSettleThread t = new OctImportSettleThread();
        t.start();
        logger.info("公交结算返回定时任务线程已启动");
    }

    private void startOctImportTrx() {
        OctImportTrxThread t = new OctImportTrxThread();
        t.start();
        logger.info("公交交易上传定时任务线程已启动");
    }

    private int startThreadPoolForOther() {
        ThreadFactory fac = new ThreadFactory();
        ThreadAttrVo vo = this.getThreadAttrVoForOther();
        int n = fac.startHandleThreadsForOther(vo);
        return n;

    }

    private int startThreadPoolForOtherMobile() {
        ThreadFactory fac = new ThreadFactory();
        ThreadAttrVo vo = this.getThreadAttrVoForOtherMobile();
        int n = fac.startHandleThreadsForOtherMobile(vo);
        return n;

    }
    private int startThreadPoolForOtherQrCode() {
        ThreadFactory fac = new ThreadFactory();
        ThreadAttrVo vo = this.getThreadAttrVoForOtherQrCode();
        int n = fac.startHandleThreadsForOtherQrCode(vo);
        return n;

    }

    private int startThreadPoolForOtherNetPaid() {
        ThreadFactory fac = new ThreadFactory();
        ThreadAttrVo vo = this.getThreadAttrVoForOtherNetPaid();
        int n = fac.startHandleThreadsForOtherNetPaid(vo);
        return n;

    }

    private void initConfig() throws ConfigFileException {

        try {
            //初始化日志配置

            DOMConfigurator.configureAndWatch(FrameCodeConstant.FILE_APP_CONFIG_LOG);
            logger.info("日志配置设置成功");

            AppXMLHandler xmlHandler = new AppXMLHandler();
            //初始化应用配置
            FrameCollectionConstant.File_Configs = xmlHandler.parseConfigFile(FrameCodeConstant.FILE_APP_CONFIG);
            //初始化公共配置
            this.initCommon(FrameCollectionConstant.File_Configs);
            //初始化数据库连接池
            this.initFromConnectionPool(FrameCollectionConstant.File_Configs);
            //初始化线程池配置
            this.initThreadPool(FrameCollectionConstant.File_Configs);
            //数据库配置的数据
            this.initFromDb();

        } catch (Exception e) {
            throw new ConfigFileException("初始化 " + FrameCodeConstant.FILE_APP_CONFIG + " 错误! " + e);
        }

    }

    private void initCommon(Hashtable configs) {
        FrameCodeConstant.RUN_MODE = (String) configs.get(XMLConstant.RunMode_TAG);
        logger.info("清算系统运行模式：" + FrameCodeConstant.RUN_MODE);
    }

    private void initFromConnectionPool(Hashtable configs) throws ConfigFileException {
// 初始化数据库连接池
        try {
            Hashtable ht = (Hashtable) configs.get(XMLConstant.DataConnectionPool_TAG);
            FrameDBConstant.DATA_DBCPHELPER = new DbcpHelper((String) ht.get(
                    "Driver"), (String) ht.get("URL"), (String) ht.get("Database"),
                    (String) ht.get("User"), (String) ht.get("Password"),
                    Integer.parseInt((String) ht.get("MaxActive")),
                    Integer.parseInt((String) ht.get("MaxIdle")),
                    Integer.parseInt((String) ht.get("MaxWait")));
            logger.info("数据连接池DataConnectionPool创建!");

        } catch (ClassNotFoundException ex) {
            throw new ConfigFileException(ex.getMessage());
        }

    }

    private void initThreadPool(Hashtable configs) {
        //获得线程池的配置属性
        //交易文件线程池
        Hashtable ht = (Hashtable) configs.get(XMLConstant.ThreadPoolFile_TAG);
        FrameThreadConstant.maxThreadNumberForFile = Integer.parseInt((String) ht.get("MaxThreadNumber"));
        FrameThreadConstant.maxSearchNumForFile = Integer.parseInt((String) ht.get("MaxSearchNum"));
        FrameThreadConstant.threadSleepTimeForFile = Integer.parseInt((String) ht.get("ThreadSleepTime"));
        FrameThreadConstant.threadPriorityForFile = Thread.NORM_PRIORITY + Integer.parseInt((String) ht.get("ThreadPriorityAdd"));
        FrameThreadConstant.unHanledMsgLogDirForFile = (String) ht.get("UnHanledMsgLogDir");

        FrameThreadConstant.threadBufferCapacityForFile = Integer.parseInt((String) ht.get("ThreadBufferCapacity"));
        FrameThreadConstant.threadBufferIncrementForFile = Integer.parseInt((String) ht.get("ThreadBufferIncrement"));
        FrameThreadConstant.priorityThreadBufferCapacityForFile = Integer.parseInt((String) ht.get("PriorityThreadBufferCapacity"));
        FrameThreadConstant.priorityThreadBufferIncrementForFile = Integer.parseInt((String) ht.get("PriorityThreadBufferIncrement"));

        FrameThreadConstant.readThreadPriorityAddForFile = Integer.parseInt((String) ht.get("ReadThreadPriorityAdd"));

//BCP文件线程池
        ht = (Hashtable) configs.get(XMLConstant.ThreadPoolBcp_TAG);
        FrameThreadConstant.maxThreadNumberForBcp = Integer.parseInt((String) ht.get("MaxThreadNumber"));
        FrameThreadConstant.maxSearchNumForBcp = Integer.parseInt((String) ht.get("MaxSearchNum"));
        FrameThreadConstant.threadSleepTimeForBcp = Integer.parseInt((String) ht.get("ThreadSleepTime"));
        FrameThreadConstant.threadPriorityForBcp = Thread.NORM_PRIORITY + Integer.parseInt((String) ht.get("ThreadPriorityAdd"));
        FrameThreadConstant.unHanledMsgLogDirForBcp = (String) ht.get("UnHanledMsgLogDir");

        FrameThreadConstant.threadBufferCapacityForBcp = Integer.parseInt((String) ht.get("ThreadBufferCapacity"));
        FrameThreadConstant.threadBufferIncrementForBcp = Integer.parseInt((String) ht.get("ThreadBufferIncrement"));
        FrameThreadConstant.priorityThreadBufferCapacityForBcp = Integer.parseInt((String) ht.get("PriorityThreadBufferCapacity"));
        FrameThreadConstant.priorityThreadBufferIncrementForBcp = Integer.parseInt((String) ht.get("PriorityThreadBufferIncrement"));

        FrameThreadConstant.readThreadPriorityAddForBcp = Integer.parseInt((String) ht.get("ReadThreadPriorityAdd"));

        //TAC文件线程池      
        ht = (Hashtable) configs.get(XMLConstant.ThreadPoolTac_TAG);
        FrameThreadConstant.maxThreadNumberForTac = Integer.parseInt((String) ht.get("MaxThreadNumber"));
        FrameThreadConstant.maxSearchNumForTac = Integer.parseInt((String) ht.get("MaxSearchNum"));
        FrameThreadConstant.threadSleepTimeForTac = Integer.parseInt((String) ht.get("ThreadSleepTime"));
        FrameThreadConstant.threadPriorityForTac = Thread.NORM_PRIORITY + Integer.parseInt((String) ht.get("ThreadPriorityAdd"));
        FrameThreadConstant.unHanledMsgLogDirForTac = (String) ht.get("UnHanledMsgLogDir");

        FrameThreadConstant.threadBufferCapacityForTac = Integer.parseInt((String) ht.get("ThreadBufferCapacity"));
        FrameThreadConstant.threadBufferIncrementForTac = Integer.parseInt((String) ht.get("ThreadBufferIncrement"));
        FrameThreadConstant.priorityThreadBufferCapacityForTac = Integer.parseInt((String) ht.get("PriorityThreadBufferCapacity"));
        FrameThreadConstant.priorityThreadBufferIncrementForTac = Integer.parseInt((String) ht.get("PriorityThreadBufferIncrement"));

        FrameThreadConstant.readThreadPriorityAddForTac = Integer.parseInt((String) ht.get("ReadThreadPriorityAdd"));

        //审计、收益、外部系统、寄存器文件文件线程池      
        ht = (Hashtable) configs.get(XMLConstant.ThreadPoolOther_TAG);
        FrameThreadConstant.maxThreadNumberForOther = Integer.parseInt((String) ht.get("MaxThreadNumber"));
        FrameThreadConstant.maxSearchNumForOther = Integer.parseInt((String) ht.get("MaxSearchNum"));
        FrameThreadConstant.threadSleepTimeForOther = Integer.parseInt((String) ht.get("ThreadSleepTime"));
        FrameThreadConstant.threadPriorityForOther = Thread.NORM_PRIORITY + Integer.parseInt((String) ht.get("ThreadPriorityAdd"));
        FrameThreadConstant.unHanledMsgLogDirForOther = (String) ht.get("UnHanledMsgLogDir");

        FrameThreadConstant.threadBufferCapacityForOther = Integer.parseInt((String) ht.get("ThreadBufferCapacity"));
        FrameThreadConstant.threadBufferIncrementForOther = Integer.parseInt((String) ht.get("ThreadBufferIncrement"));
        FrameThreadConstant.priorityThreadBufferCapacityForOther = Integer.parseInt((String) ht.get("PriorityThreadBufferCapacity"));
        FrameThreadConstant.priorityThreadBufferIncrementForOther = Integer.parseInt((String) ht.get("PriorityThreadBufferIncrement"));

        FrameThreadConstant.readThreadPriorityAddForOther = Integer.parseInt((String) ht.get("ReadThreadPriorityAdd"));

        //手机支付交易文件线程池
        ht = (Hashtable) configs.get(XMLConstant.ThreadPoolFileMobile_TAG);
        FrameThreadConstant.maxThreadNumberForFileMobile = Integer.parseInt((String) ht.get("MaxThreadNumber"));
        FrameThreadConstant.maxSearchNumForFileMobile = Integer.parseInt((String) ht.get("MaxSearchNum"));
        FrameThreadConstant.threadSleepTimeForFileMobile = Integer.parseInt((String) ht.get("ThreadSleepTime"));
        FrameThreadConstant.threadPriorityForFileMobile = Thread.NORM_PRIORITY + Integer.parseInt((String) ht.get("ThreadPriorityAdd"));
        FrameThreadConstant.unHanledMsgLogDirForFileMobile = (String) ht.get("UnHanledMsgLogDir");

        FrameThreadConstant.threadBufferCapacityForFileMobile = Integer.parseInt((String) ht.get("ThreadBufferCapacity"));
        FrameThreadConstant.threadBufferIncrementForFileMobile = Integer.parseInt((String) ht.get("ThreadBufferIncrement"));
        FrameThreadConstant.priorityThreadBufferCapacityForFileMobile = Integer.parseInt((String) ht.get("PriorityThreadBufferCapacity"));
        FrameThreadConstant.priorityThreadBufferIncrementForFileMobile = Integer.parseInt((String) ht.get("PriorityThreadBufferIncrement"));

        FrameThreadConstant.readThreadPriorityAddForFileMobile = Integer.parseInt((String) ht.get("ReadThreadPriorityAdd"));

        //手机支付交易文件线程池对账文件文件线程池      
        ht = (Hashtable) configs.get(XMLConstant.ThreadPoolOtherMobile_TAG);
        FrameThreadConstant.maxThreadNumberForOtherMobile = Integer.parseInt((String) ht.get("MaxThreadNumber"));
        FrameThreadConstant.maxSearchNumForOtherMobile = Integer.parseInt((String) ht.get("MaxSearchNum"));
        FrameThreadConstant.threadSleepTimeForOtherMobile = Integer.parseInt((String) ht.get("ThreadSleepTime"));
        FrameThreadConstant.threadPriorityForOtherMobile = Thread.NORM_PRIORITY + Integer.parseInt((String) ht.get("ThreadPriorityAdd"));
        FrameThreadConstant.unHanledMsgLogDirForOtherMobile = (String) ht.get("UnHanledMsgLogDir");

        FrameThreadConstant.threadBufferCapacityForOtherMobile = Integer.parseInt((String) ht.get("ThreadBufferCapacity"));
        FrameThreadConstant.threadBufferIncrementForOtherMobile = Integer.parseInt((String) ht.get("ThreadBufferIncrement"));
        FrameThreadConstant.priorityThreadBufferCapacityForOtherMobile = Integer.parseInt((String) ht.get("PriorityThreadBufferCapacity"));
        FrameThreadConstant.priorityThreadBufferIncrementForOtherMobile = Integer.parseInt((String) ht.get("PriorityThreadBufferIncrement"));

        FrameThreadConstant.readThreadPriorityAddForOtherMobile = Integer.parseInt((String) ht.get("ReadThreadPriorityAdd"));

        //互联网支付交易文件线程池
        ht = (Hashtable) configs.get(XMLConstant.ThreadPoolFileNetPaid_TAG);
        FrameThreadConstant.maxThreadNumberForFileNetPaid = Integer.parseInt((String) ht.get("MaxThreadNumber"));
        FrameThreadConstant.maxSearchNumForFileNetPaid = Integer.parseInt((String) ht.get("MaxSearchNum"));
        FrameThreadConstant.threadSleepTimeForFileNetPaid = Integer.parseInt((String) ht.get("ThreadSleepTime"));
        FrameThreadConstant.threadPriorityForFileNetPaid = Thread.NORM_PRIORITY + Integer.parseInt((String) ht.get("ThreadPriorityAdd"));
        FrameThreadConstant.unHanledMsgLogDirForFileNetPaid = (String) ht.get("UnHanledMsgLogDir");

        FrameThreadConstant.threadBufferCapacityForFileNetPaid = Integer.parseInt((String) ht.get("ThreadBufferCapacity"));
        FrameThreadConstant.threadBufferIncrementForFileNetPaid = Integer.parseInt((String) ht.get("ThreadBufferIncrement"));
        FrameThreadConstant.priorityThreadBufferCapacityForFileNetPaid = Integer.parseInt((String) ht.get("PriorityThreadBufferCapacity"));
        FrameThreadConstant.priorityThreadBufferIncrementForFileNetPaid = Integer.parseInt((String) ht.get("PriorityThreadBufferIncrement"));

        FrameThreadConstant.readThreadPriorityAddForFileNetPaid = Integer.parseInt((String) ht.get("ReadThreadPriorityAdd"));

        //互联网支付交易文件线程池对账文件文件线程池      
        ht = (Hashtable) configs.get(XMLConstant.ThreadPoolOtherNetPaid_TAG);
        FrameThreadConstant.maxThreadNumberForOtherNetPaid = Integer.parseInt((String) ht.get("MaxThreadNumber"));
        FrameThreadConstant.maxSearchNumForOtherNetPaid = Integer.parseInt((String) ht.get("MaxSearchNum"));
        FrameThreadConstant.threadSleepTimeForOtherNetPaid = Integer.parseInt((String) ht.get("ThreadSleepTime"));
        FrameThreadConstant.threadPriorityForOtherNetPaid = Thread.NORM_PRIORITY + Integer.parseInt((String) ht.get("ThreadPriorityAdd"));
        FrameThreadConstant.unHanledMsgLogDirForOtherNetPaid = (String) ht.get("UnHanledMsgLogDir");

        FrameThreadConstant.threadBufferCapacityForOtherNetPaid = Integer.parseInt((String) ht.get("ThreadBufferCapacity"));
        FrameThreadConstant.threadBufferIncrementForOtherNetPaid = Integer.parseInt((String) ht.get("ThreadBufferIncrement"));
        FrameThreadConstant.priorityThreadBufferCapacityForOtherNetPaid = Integer.parseInt((String) ht.get("PriorityThreadBufferCapacity"));
        FrameThreadConstant.priorityThreadBufferIncrementForOtherNetPaid = Integer.parseInt((String) ht.get("PriorityThreadBufferIncrement"));

        FrameThreadConstant.readThreadPriorityAddForOtherNetPaid = Integer.parseInt((String) ht.get("ReadThreadPriorityAdd"));

        //二维码平台交易文件线程池20190612
        ht = (Hashtable) configs.get(XMLConstant.ThreadPoolFileQrCode_TAG);
        FrameThreadConstant.maxThreadNumberForFileQrCode = Integer.parseInt((String) ht.get("MaxThreadNumber"));
        FrameThreadConstant.maxSearchNumForFileQrCode = Integer.parseInt((String) ht.get("MaxSearchNum"));
        FrameThreadConstant.threadSleepTimeForFileQrCode = Integer.parseInt((String) ht.get("ThreadSleepTime"));
        FrameThreadConstant.threadPriorityForFileQrCode = Thread.NORM_PRIORITY + Integer.parseInt((String) ht.get("ThreadPriorityAdd"));
        FrameThreadConstant.unHanledMsgLogDirForFileQrCode = (String) ht.get("UnHanledMsgLogDir");

        FrameThreadConstant.threadBufferCapacityForFileQrCode = Integer.parseInt((String) ht.get("ThreadBufferCapacity"));
        FrameThreadConstant.threadBufferIncrementForFileQrCode = Integer.parseInt((String) ht.get("ThreadBufferIncrement"));
        FrameThreadConstant.priorityThreadBufferCapacityForFileQrCode = Integer.parseInt((String) ht.get("PriorityThreadBufferCapacity"));
        FrameThreadConstant.priorityThreadBufferIncrementForFileQrCode = Integer.parseInt((String) ht.get("PriorityThreadBufferIncrement"));

        FrameThreadConstant.readThreadPriorityAddForFileQrCode = Integer.parseInt((String) ht.get("ReadThreadPriorityAdd"));

        //二维码平台对账文件文件线程池20190612  
        ht = (Hashtable) configs.get(XMLConstant.ThreadPoolOtherQrCode_TAG);
        FrameThreadConstant.maxThreadNumberForOtherQrCode = Integer.parseInt((String) ht.get("MaxThreadNumber"));
        FrameThreadConstant.maxSearchNumForOtherQrCode = Integer.parseInt((String) ht.get("MaxSearchNum"));
        FrameThreadConstant.threadSleepTimeForOtherQrCode = Integer.parseInt((String) ht.get("ThreadSleepTime"));
        FrameThreadConstant.threadPriorityForOtherQrCode = Thread.NORM_PRIORITY + Integer.parseInt((String) ht.get("ThreadPriorityAdd"));
        FrameThreadConstant.unHanledMsgLogDirForOtherQrCode = (String) ht.get("UnHanledMsgLogDir");

        FrameThreadConstant.threadBufferCapacityForOtherQrCode = Integer.parseInt((String) ht.get("ThreadBufferCapacity"));
        FrameThreadConstant.threadBufferIncrementForOtherQrCode = Integer.parseInt((String) ht.get("ThreadBufferIncrement"));
        FrameThreadConstant.priorityThreadBufferCapacityForOtherQrCode = Integer.parseInt((String) ht.get("PriorityThreadBufferCapacity"));
        FrameThreadConstant.priorityThreadBufferIncrementForOtherQrCode = Integer.parseInt((String) ht.get("PriorityThreadBufferIncrement"));

        FrameThreadConstant.readThreadPriorityAddForOtherQrCode = Integer.parseInt((String) ht.get("ReadThreadPriorityAdd"));
    }

    private void initFromDb() throws ConfigFileException {
        try {
            //获取系统版本
            this.getSysConfigVersion();
            //系统基本配置
            this.getSysConfig();
            // FrameCollectionConstant.CONFIGS_RECORD_FMT = new ConfigRecordFmtDao().getConfigRecordFmt();
            FrameCollectionConstant.BUF_STATION_CODE = new StationDao().getStationCode();
            //校验配置
            FrameCheckConstant.DEVICE_SAM_MAPPING = new CheckConfigDao().getCheckConfigForDeviceSam();//设备SAM卡对照
            FrameCheckConstant.CARD_SUB_TYPES = new CheckConfigDao().getCheckConfigForCardSubType();//卡子类型
            FrameCheckConstant.CHECK_CONTROLS = new CheckConfigDao().getCheckConfigForCheckControls();//校验控制
            FrameCodeConstant.CARD_MAIN_TYPE_CPUS = new CheckConfigDao().getCheckConfigForTacCardMainTypeCpu();//储值类卡add by hejj 20150211
            FrameCodeConstant.CARD_MAIN_TYPE_METRO = new CheckConfigDao().getCheckConfigForTacCardMainType();//地铁发行的票卡add by hejj 20150211

            this.getCheckLimit();//时间、金额限制

        } catch (Exception ex) {
            throw new ConfigFileException(ex.getMessage());
        }
    }

    private void initSettle() {
        TaskUtil.setFinishFileHandled(false);//文件完成标识置为FALSE
        TaskUtil.setFinalSettleFlag("0");//最后一次实时清算标识置为FALSE
        TaskUtil.setHandleOctImportSettle(false);//外部对账清算数据返回处理标识置为FALSE
        TaskUtil.setHandleOctImportTrx(false);//外部外部交易数据返回处理标识置为FALSE
    }

    private void initSettleAfterBalanceWaterNo(String balanceWaterNo) throws Exception {
        this.initSettleForPreSettleOnly(balanceWaterNo);
        this.initSettleForManu(balanceWaterNo);

    }

    private void initSettleForPreSettleOnly(String balanceWaterNo) throws Exception {

        if (FrameCodeConstant.CONTROL_PRESETTLE_ONLY.equals(FrameCodeConstant.CONTROL_PRESETTLE_ONLY_YES)) {
            logger.info("清算流水号：" + balanceWaterNo + " 仅进行预处理，不进行结算");
            FlowDao.updateFinishFlagForPreSettleOnly(balanceWaterNo, FrameFlowConstant.SETTLE_STEPS);
        }
    }

    private void initSettleForManu(String balanceWaterNo) throws Exception {

        if (this.isSettleByManu()) {
            logger.info("清算流水号：" + balanceWaterNo + " 进行人工处理，按清算流水号批次处理");
            TaskUtil.setFinishFileHandled(true);//文件完成标识置为true
            TaskUtil.setDownloadAuditFile(true);//FTP审计文件设置下发标识为true
            logger.info("清算流水号：" + balanceWaterNo + " 设置FTP审计文件下发标识：true;设置LCC文件处理完成标识：true");
        }
    }

    private void startSettleBusiness() {
        ResultFromProc result;
        RealtimeBalanceWater bw = new RealtimeBalanceWater();
        // String balanceWaterNo = "";
        //int balanceWaterNoSub = -1;
        boolean isUnfinished;
        StepInfo si = new StepInfo();//处理批次

        //判断是否需要获取新的清算流水号
        //logger.info("开始清算业务");
        LoggerUtil.loggerLineForSectAll(logger, "开始清算业务");
        try {
            while (true) {
                /**
                 * ***********************当日清算初始化
                 * ***********************************************
                 */
                this.initSettle();
                si.setCount(1);
                /**
                 * ***********************获取清算流水号，如果已存在未完成的清算，无需重新获取
                 * *************
                 */
                //result = this.getBalanceWater("system");
                this.setRealtimeBalanceWater(bw);
                /*
                balanceWaterNo = FrameCodeConstant.BALANCE_WATER_NO;
                this.getBalanceWaterSub(balanceWaterNo, balanceWaterNo);//获取子流水号
                balanceWaterNoSub = FrameCodeConstant.BALANCE_WATER_NO_SUB;
                 */

                LoggerUtil.loggerLineForSectAll(logger, LoggerUtil.getBalanceWaterInfo(bw.getBalanceWaterNo(), bw.getBalanceWaterNoSub()));

                /**
                 * ***********************完成当天的交易文件、收益文件处理
                 * *******************************
                 */
                isUnfinished = this.preHandleForStep(bw.getBalanceWaterNo(), FrameFlowConstant.STEP_FILE_PROCESS);
                while (isUnfinished && !this.isFinishForFileProcess(bw.getBalanceWaterNo())) {
                    //判断是否仅预处理/人工处理,如果人工处理，处理完一批次就完成文件处理 hejj 20140520
                    this.initSettleAfterBalanceWaterNo(bw.getBalanceWaterNo());

                    this.stepAForFileAndFtpAuit(bw, si);//文件处理
                    //文件处理过程中，如果上次结算出现异常，不再进行文件处理,流程中止，需人工处理
                    this.interruptIfSettleError(bw.getBalanceWaterNo(), bw.getBalanceWaterNoSub() - 1);

                    Thread.sleep(FrameCodeConstant.SETTLE_FLOW_SLEEP_TIME_FILE);
                }
                if (this.isFinishForFileProcess(bw.getBalanceWaterNo())) {
                    this.postHandleForStep(bw.getBalanceWaterNo(), FrameFlowConstant.STEP_FILE_PROCESS, "所有交易文件及收益文件等、FTP审计下发处理完成");
                }
                /**
                 * ***********************导出公交消费数据
                 * ***************************************
                 */
                isUnfinished = this.preHandleForStep(bw.getBalanceWaterNo(), FrameFlowConstant.STEP_FILE_EXPORT_OCT_TRX);
                if (isUnfinished && this.isCanExport()) {
                    this.stepBForExternalDataTrxExport(bw.getBalanceWaterNo(), bw.getBalanceWaterNoSub(), si);
                    this.postHandleForStep(bw.getBalanceWaterNo(), FrameFlowConstant.STEP_FILE_EXPORT_OCT_TRX, "完成导出外部系统数据");
                }

                /**
                 * ***********************等待公交上传储值卡消费数据、导入
                 * ***************************************
                 */
                /*del
                isUnfinished = this.preHandleForStep(bw.getBalanceWaterNo(), FrameFlowConstant.STEP_FILE_IMPORT_OCT_TRX);
                if (isUnfinished) {
                    //测试使用，暂时屏蔽
                    if (this.isNeedHandleBusTrx()) {//由配置控制是否启用外部地铁卡交易数据导入
                        this.stepBAwaitForOctTrxImport(bw.getBalanceWaterNo(), si);
                        this.stepBBImportExternalDataOctForTrx(bw.getBalanceWaterNo(), si);
                    }

                    this.postHandleForStep(bw.getBalanceWaterNo(), FrameFlowConstant.STEP_FILE_IMPORT_OCT_TRX, "完成处理公交上传的交易文件");
                }
                 */
                /**
                 * ***********************等候最后一次结算完成、黑名单及错误审计、LCC对账下发
                 * ***************************************
                 */
                this.stepCForSettleEtc(bw.getBalanceWaterNo(), si);

                /**
                 * ***********************导出外部系统结算数据
                 * ***************************************
                 */
                /*
                isUnfinished = this.preHandleForStep(bw.getBalanceWaterNo(), FrameFlowConstant.STEP_FILE_EXPORT_OCT_SETTLE);
                if (isUnfinished && this.isCanExport()) {
                    this.stepCAForExternalDataExportOctForSettle(bw.getBalanceWaterNo(), si);
                    this.postHandleForStep(bw.getBalanceWaterNo(), FrameFlowConstant.STEP_FILE_EXPORT_OCT_SETTLE, "完成导出外部系统数据");
                }
                 */
                /**
                 * ***********************等待外部对账数据返回、导入
                 * ***************************************
                 */
                /*del
                isUnfinished = this.preHandleForStep(bw.getBalanceWaterNo(), FrameFlowConstant.STEP_FILE_IMPORT_OCT_SETTLE);
                if (isUnfinished) {
                    //测试使用，暂时屏蔽
                    if (this.isNeedHandleBusSettle()) {
                        this.stepDwaitForOctImportForSettle(bw.getBalanceWaterNo(), si);
                        this.stepEImportExternalDataOctForSettle(bw.getBalanceWaterNo(), si);
                    }

                    this.postHandleForStep(bw.getBalanceWaterNo(), FrameFlowConstant.STEP_FILE_IMPORT_OCT_SETTLE, "完成处理公交IC卡返回结算数据");
                }
                 */
                /**
                 * 本次清算完成后，相关更新标识、清理等，如是人工处理结算，人工流水表设置完成标识
                 */
                this.postHandleForStepForFinish(bw.getBalanceWaterNo());
                LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + bw.getBalanceWaterNo() + "当天结算、对账数据完成:等待" + (FrameCodeConstant.SETTLE_FLOW_SLEEP_TIME / 1000)
                        + "秒下一天的结算。。。");
                Thread.sleep(FrameCodeConstant.SETTLE_FLOW_SLEEP_TIME);

            }

        } catch (Exception e) {
            LoggerUtil.loggerLineForSectLast(logger, "清算流水号：" + bw.getBalanceWaterNo()
                    + "处理当天的清算存在系统级错误需人工干涉以避免错误扩大，出错原因：" + e.getMessage());
        }

    }

    protected void interruptIfSettleError(String balanceWaterNo, int balanceWaterNoSub) throws Exception {
        if (RealtimeSettleThread.getExistErrorSettle()) {
            throw new Exception(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + " 结算过程中存在异常，流程将中止。。。");
        }

    }

    protected void setRealtimeBalanceWater(RealtimeBalanceWater balanceWater) throws Exception {
        this.getBalanceWater("system");
        String balanceWaterNo = FrameCodeConstant.BALANCE_WATER_NO;
        this.getBalanceWaterSub(balanceWaterNo, balanceWaterNo);//获取子流水号
        int balanceWaterNoSub = FrameCodeConstant.BALANCE_WATER_NO_SUB;
        balanceWater.setBalanceWaterNo(balanceWaterNo);
        balanceWater.setBalanceWaterNoSub(balanceWaterNoSub);
    }

    protected void setRealtimeBalanceWaterForNextSub(RealtimeBalanceWater balanceWater) throws Exception {
        //this.getBalanceWater("system");
        String balanceWaterNo = FrameCodeConstant.BALANCE_WATER_NO;
        this.getBalanceWaterSub(balanceWaterNo, balanceWaterNo);//获取子流水号
        int balanceWaterNoSub = FrameCodeConstant.BALANCE_WATER_NO_SUB;
        //balanceWater.setBalanceWaterNo(balanceWaterNo);
        balanceWater.setBalanceWaterNoSub(balanceWaterNoSub);
    }

    private void stepBBImportExternalDataOctForTrx(String balanceWaterNo, int balanceWaterNoSub, StepInfo si) throws Exception {
        Vector<TaskFinishControl> tfcs;
        logger.info("清算流水号：" + balanceWaterNo + ":开始处理公交上传的交易数据。");
        tfcs = this.getTaskControl(1);
        this.processFilesForOctImportTrx(balanceWaterNo, balanceWaterNoSub, tfcs.get(0));//公交上传的交易数据文件

    }

    private void stepDwaitForOctImportForSettle(String balanceWaterNo, StepInfo si) throws Exception {
        while (!this.isCanHandleOctImportSettle()) {

            LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + "当天结算完成:等待公交卡结算数据返回：等待时间" + (FrameCodeConstant.SETTLE_FLOW_SLEEP_TIME_SETTLE_IMPORT / 1000)
                    + "秒。。。");
            Thread.sleep(FrameCodeConstant.SETTLE_FLOW_SLEEP_TIME_SETTLE_IMPORT);

        }

    }

    private void stepBAwaitForOctTrxImport(String balanceWaterNo, StepInfo si) throws Exception {
        while (!this.isCanHandleOctImportTrx()) {

            LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + "等待公交上传储值卡消费数据：等待时间" + (FrameCodeConstant.SETTLE_FLOW_SLEEP_TIME_TRX_IMPORT / 1000)
                    + "秒。。。");
            Thread.sleep(FrameCodeConstant.SETTLE_FLOW_SLEEP_TIME_TRX_IMPORT);

        }

    }

    private void loggerSettleStep(String balanceWaterNo) throws Exception {
        Vector<String> stepList = FlowDao.getFinishedStepForSettle(balanceWaterNo, FrameFlowConstant.SETTLE_STEPS);
        if (stepList.isEmpty()) {
            return;
        }
        String stepName;
        for (String step : stepList) {
            stepName = FrameFlowConstant.getStepKeyName(step);
            LoggerUtil.loggerLine(logger, "清算流水号：" + balanceWaterNo + ":完成" + stepName + "处理");
        }
    }

    private void stepCJWaitForSettleFinish(String balanceWaterNo, StepInfo si) throws Exception {
        boolean isFinishLog = SettleLogThread.getLogEndFlag();
        while (!isFinishLog) {
            Thread.sleep(FrameCodeConstant.SETTLE_FLOW_SLEEP_TIME_SETTLE_STATUS);
            isFinishLog = SettleLogThread.getLogEndFlag();
        }

    }

    private void stepCJWaitForSettleDetailFinish() throws Exception {

        boolean isFinishLog = SettleDetailLogThread.getLogEndFlag();
        while (!isFinishLog) {
            Thread.sleep(FrameCodeConstant.SETTLE_FLOW_SLEEP_TIME_SETTLE_STATUS);
            isFinishLog = SettleDetailLogThread.getLogEndFlag();
        }

    }

    private void stepCIWaitForSettleFinish(String balanceWaterNo, StepInfo si) throws Exception {
        boolean isUnFinished = this.preHandleForStepsSettle(balanceWaterNo, FrameFlowConstant.SETTLE_STEPS);
        FlowDao.clearStepListed();
        while (isUnFinished) {
            this.loggerSettleStep(balanceWaterNo);
            //显示清算各步骤完成信息
            Thread.sleep(FrameCodeConstant.SETTLE_FLOW_SLEEP_TIME);
            isUnFinished = this.preHandleForStepsSettle(balanceWaterNo, FrameFlowConstant.SETTLE_STEPS);

        }

    }

    private void stepAForFileAndFtpAuit(RealtimeBalanceWater bw, StepInfo si) throws Exception {
        Vector<TaskFinishControl> tfcs;
        String balanceWaterNo = bw.getBalanceWaterNo();
        int balanceWaterNoSub = bw.getBalanceWaterNoSub();

        LoggerUtil.loggerLineForSectFirst(logger, LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":第" + si.getCount() + "批处理开始");
        tfcs = this.getTaskControl(8);
        /*
        if (BusinessUtil.onBusinessForMoble()&&BusinessUtil.onBusinessForNetPaid()) {
            tfcs = this.getTaskControl(6);
        } else {
            
            tfcs = this.getTaskControl(2);
        }
         */
        this.processFilesForTrx(balanceWaterNo, balanceWaterNoSub, tfcs.get(0));//交易文件
        this.processFilesForOther(balanceWaterNo, balanceWaterNoSub, tfcs.get(1));//收益文件等其他
        //入口作控制，配置是否启动手机支付交易文件处理
        if (BusinessUtil.onBusinessForMoble()) {
            this.processFilesForTrx80(balanceWaterNo, balanceWaterNoSub, tfcs.get(2));//手机支付交易文件
            this.processFilesForOther80(balanceWaterNo, balanceWaterNoSub, tfcs.get(3));//手机支付对账文件等其他
        } else {
            this.setTaskFinishControlByManu(tfcs, 2, true);
            this.setTaskFinishControlByManu(tfcs, 3, true);
        }

        //入口作控制，配置是否启动互联网支付交易文件处理 add by hejj 20161125
        if (BusinessUtil.onBusinessForNetPaid()) {
            this.processFilesForTrx82(balanceWaterNo, balanceWaterNoSub, tfcs.get(4));//互联网支付交易文件
            this.processFilesForOther82(balanceWaterNo, balanceWaterNoSub, tfcs.get(5));//互联网支付对账文件等其他
        } else {
            this.setTaskFinishControlByManu(tfcs, 4, true);
            this.setTaskFinishControlByManu(tfcs, 5, true);
        }
        
         //入口作控制，配置是否启动二维码平台交易文件处理
        if (BusinessUtil.onBusinessForQrCode()) {
            this.processFilesForTrx7x(balanceWaterNo, balanceWaterNoSub, tfcs.get(6));//二维码平台交易文件
            this.processFilesForOther70(balanceWaterNo, balanceWaterNoSub, tfcs.get(7));//二维码平台对账文件等其他
        } else {
            this.setTaskFinishControlByManu(tfcs, 6, true);
            this.setTaskFinishControlByManu(tfcs, 7, true);
        }
        

        //等待交易文件、收益文件处理完成
        this.waitFinish(tfcs, balanceWaterNo, balanceWaterNoSub);
        LoggerUtil.loggerLineForSectLast(logger, LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":第" + si.getCount() + "批处理完成");
        si.setCount(si.getCount() + 1);

        //下发FTP审计文件、每天一次，在指定的时间点
        boolean isUnfinished = this.preHandleForStepNoUpdate(balanceWaterNo, FrameFlowConstant.STEP_FTP_FILE_DOWN);
        if (isUnfinished && this.isCanDownloadAudFile()) {
            LoggerUtil.loggerLineForSectFirst(logger, LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":开始下发FTP审计文件");
            FlowDao.updateFinishFlagForStart(balanceWaterNo, FrameFlowConstant.STEP_FTP_FILE_DOWN);

            this.downloadAuditFtp(balanceWaterNo);
            this.setAuditFtpDownloadFlag();
            FlowDao.updateFinishFlag(balanceWaterNo, FrameFlowConstant.STEP_FTP_FILE_DOWN,
                    FrameFlowConstant.FLAG_FINISH, FrameFlowConstant.FLAG_OK);
            LoggerUtil.loggerLineForSectLast(logger, LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":完成下发FTP审计文件");

        }
        //是否满足该批次结算条件，如满足，生成结算消息，放入结算消息队列
        //生成新的下一子流水
        String finalSettleFlag = TaskUtil.getFinalSettleFlag();
        LoggerUtil.loggerLine(logger, LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + " :最后一次清算标识：" + finalSettleFlag);
        if (this.isCanSettleForBatch(balanceWaterNo, balanceWaterNoSub, finalSettleFlag)) {
            LoggerUtil.loggerLine(logger, LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + " :上批次结算已完成，本批次结算记录数已大于最小设定值,最后一次清算标识：" + finalSettleFlag);
            //更新实时结算流程明细子流水
            //更新实时结算流程明细本次文件处理
            FlowDao fDao = new FlowDao();
            fDao.preSettleBatch(balanceWaterNo, balanceWaterNoSub);//上批次处理流程归档，本批次流程初始化，设置完成标识为0。
            //生成结算消息放入队列,如果消息已存在，不需重新生成
            this.putSettleMsg(balanceWaterNo, balanceWaterNoSub);
            /*
            SettleQueueDao dao = new SettleQueueDao();
            dao.insert(balanceWaterNo, balanceWaterNoSub, FrameFlowConstant.FINAL_SETTLE_FLAG);
            LoggerUtil.loggerLine(logger, LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + " :实时结算消息放入结算队列，最后一次清算标识：" + FrameFlowConstant.FINAL_SETTLE_FLAG);
             */
            //生成新的下一子流水
            if (!this.isLastSettle(FrameFlowConstant.FINAL_SETTLE_FLAG)) {
                this.setRealtimeBalanceWaterForNextSub(bw);
            } else {
                //设置文件处理完成标识
                TaskUtil.setFinishFileHandled(true);
                logger.info("交易文件、收益文件等处理完成标识设为：TRUE");
            }

        } else {
            LoggerUtil.loggerLine(logger, LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":等待" + (FrameCodeConstant.SETTLE_FLOW_SLEEP_TIME_FILE / 1000)
                    + "秒处理下一批次文件。。。");
        }

    }

    protected void putSettleMsg(String balanceWaterNo, int balanceWaterNoSub) throws Exception {
        SettleQueueDao dao = new SettleQueueDao();
        if (dao.existQueueMsg(balanceWaterNo, balanceWaterNoSub)) {
            LoggerUtil.loggerLine(logger, LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + " :实时结算消息已在结算队列，最后一次清算标识：" + FrameFlowConstant.FINAL_SETTLE_FLAG);
            return;
        }
        dao.insert(balanceWaterNo, balanceWaterNoSub, FrameFlowConstant.FINAL_SETTLE_FLAG);
        LoggerUtil.loggerLine(logger, LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + " :实时结算消息放入结算队列，最后一次清算标识：" + FrameFlowConstant.FINAL_SETTLE_FLAG);
    }

    private void stepCForSettleEtc(String balanceWaterNo, StepInfo si) throws Exception {

        boolean isUnFinished = this.preHandleForStepsSettle(balanceWaterNo, FrameFlowConstant.SETTLE_STEPS);
        if (isUnFinished) {
            //等候子结算完成20171205
            this.stepCJWaitForSettleDetailFinish();

            LoggerUtil.loggerLineForSectFirst(logger, "清算流水号：" + balanceWaterNo + ":开始当天最后一次业务清算。");
            SettleLogThread.setLogStartFlag(true, balanceWaterNo);
            SettleLogThread.setLogEndFlag(false);
            //  this.businessSettle(balanceWaterNo);
            //等候清算日志输出完成
            this.stepCJWaitForSettleFinish(balanceWaterNo, si);

            LoggerUtil.loggerLineForSectLast(logger, "清算流水号：" + balanceWaterNo + ":完成当天最后一次业务清算。");
        }

        //下发黑名单、错误文件审计
        isUnFinished = this.preHandleForStep(balanceWaterNo, FrameFlowConstant.STEP_ERR_FILE_DOWN);
        if (isUnFinished) {
            if (this.isCanDownloadBlackList()) {
                LoggerUtil.loggerLineForSectFirst(logger, "清算流水号：" + balanceWaterNo + ":开始下发黑名单。");
                this.downloadBlackList(balanceWaterNo);
                LoggerUtil.loggerLineForSectLast(logger, "清算流水号：" + balanceWaterNo + ":完成下发黑名单。");
            } else {
                LoggerUtil.loggerLineForSectFirst(logger, "清算流水号：" + balanceWaterNo + "配置设置：无需下发黑名单。");
            }

            //错误文件;每天一次，在文件处理完成后
            if (this.isCanDownloadErrFile()) {
                this.downloadAuditError(balanceWaterNo);
                LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":下发错误数据审计文件完成。");
            } else {//手工处理
                LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + "配置设置：无需下发错误数据审计文件。");
            }
            if (this.isCanDownloadLccReconciliationFile()) {
                this.downloadAuditSDF(balanceWaterNo);
                LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":下发LCC对账文件完成。");
            } else {////手工处理
                LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + "配置设置：无需下发LCC对账文件。");
            }

            this.postHandleForStep(balanceWaterNo, FrameFlowConstant.STEP_ERR_FILE_DOWN, "完成下发黑名单、错误审计、LCC对账");

            //手机支付对账文件、消费文件生成、下发add by hejj 20160120
            if (BusinessUtil.onBusinessForMoble()) {
                if (this.isCanDownloadLccReconciliationFileForMobile()) {
                    this.downloadAuditSDFMobile(balanceWaterNo);
                    LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":下发手机支付对账文件完成。");
                } else {
                    LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + "配置设置：无需下发手机支付对账文件。");
                }
                if (BusinessUtil.onBusinessForMobleTrxDownload()) {//判断是否需下发手机支付消费数据，0：不下发 1：下发
                    this.downloadAuditTrxMobile(balanceWaterNo);
                    LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":下发手机卡消费文件完成。");
                } else {
                    LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":配置设置：无需下发手机卡消费文件。");
                }

            }
            //互联网支付对账文件生成、下发add by hejj 20161128
            /*
            if (BusinessUtil.onBusinessForNetPaid()) {
                this.downloadAuditSDFNetPaid(balanceWaterNo);
                LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":下发互联网支付对账文件完成。");

            }
            */
             //二维码平台对账文件生成、下发add by hejj 20190613
            if (BusinessUtil.onBusinessForQrCode()) {
                this.downloadAuditSDFQrCode(balanceWaterNo);
                LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":下发二维码平台对账文件完成。");

            }
            
             //地铁二维码平台对账文件生成、下发add by hejj 20200706
            if (BusinessUtil.onBusinessForQrCodeMtr()) {
                this.downloadAuditSDFQrCodeMtr(balanceWaterNo);
                LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":下发地铁二维码平台对账文件完成。");

            }
            
            //TCC历史客流数据文件生成、下发add by hejj 20190618
            
            if (BusinessUtil.onBusinessForTcc()) {
                this.downloadTccFiles(balanceWaterNo);
                LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":下发TCC历史客流、基础信息等文件完成。");

            }
            

        }

    }

    private void stepBForExternalDataTrxExport(String balanceWaterNo, int balanceWaterNoSub, StepInfo si) throws Exception {
        if (this.isCanExportOctTrx()) {
            LoggerUtil.loggerLineForSectFirst(logger, LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":开始导出外部系统数据。");

            this.exportForExternalSysOct(balanceWaterNo, balanceWaterNoSub);
            //改为作为审计下发20180628
            //  if (BusinessUtil.onBusinessForMoble()){
            //导出手机支付消费数据
            //    this.exportForExternalSysMobile(balanceWaterNo, balanceWaterNoSub);

            //}
            LoggerUtil.loggerLineForSectLast(logger, LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":完成导出外部系统数据。");
        } else {
            LoggerUtil.loggerLineForSectLast(logger, LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":配置设置：无需导出外部数据。");
        }

    }

    private void stepCAForExternalDataExportOctForSettle(String balanceWaterNo, int balanceWaterNoSub, StepInfo si) throws Exception {

        LoggerUtil.loggerLineForSectFirst(logger, "清算流水号：" + balanceWaterNo + ":开始导出外部系统结算结果数据。");

        this.exportForExternalSysForSettle(balanceWaterNo, balanceWaterNoSub);
        LoggerUtil.loggerLineForSectLast(logger, "清算流水号：" + balanceWaterNo + ":完成导出外部系统结算结果数据。");

    }

    private void stepEImportExternalDataOctForSettle(String balanceWaterNo, int balanceWaterNoSub, StepInfo si) throws Exception {

        Vector<TaskFinishControl> tfcs;
        logger.info("清算流水号：" + balanceWaterNo + ":开始处理公交返回结算结果。");
        tfcs = this.getTaskControl(1);
        this.processFilesForOctReturn(balanceWaterNo, balanceWaterNoSub, tfcs.get(0));//公交上传的交易数据文件

    }

    private void businessSettle(String balanceWaterNo) throws Exception {
        try {

            BusinessSettleDao dao = new BusinessSettleDao();
            //* dao.businessSettle(balanceWaterNo);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    private void processFilesForTrx(String balanceWaterNo, int balanceWaterNoSub, TaskFinishControl tfc) {
        FileUtil util = new FileUtil();
        MessageUtil mUtil = new MessageUtil();
        String fileName;
        String pathTrx;

        try {
            //balanceWaterNo = this.getBalanceWater("hejj");

            File[] filesTrx = util.getFilesForTrx(FrameCodeConstant.PATH_FILE_TRX);;//交易文件
            //人工清算hejj 20140520
            if (this.isSettleByManu()) {
                pathTrx = TradeUtil.getDirForBalanceWaterNo(FrameCodeConstant.PATH_FILE_TRX_MANU, false);
                filesTrx = util.getFilesForTrx(pathTrx);;//交易文件
                FrameCodeConstant.PATH_FILE_TRX = pathTrx;//动态改变交易文件目录
            }

            if (filesTrx == null || filesTrx.length == 0) {
                logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":没有交易文件需处理");

                return;
            }
            logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":交易文件处理数量：" + filesTrx.length);
            Vector<TaskFinishControl> tfcs = TaskUtil.getTaskControl(filesTrx.length);
            for (int i = 0; i < filesTrx.length; i++) {
                fileName = filesTrx[i].getName();
                this.writeLogFtp(fileName, (int) filesTrx[i].length(), balanceWaterNo, balanceWaterNoSub);//记录接收到的FTP文件日志
                mUtil.putMessageToQueueForFile(fileName, balanceWaterNo, balanceWaterNoSub, tfcs.get(i));

            }
            TaskUtil.waitAllTaskFinish(tfcs, true, "任务：本批次交易文件处理");

        } catch (Exception ex) {
            ex.printStackTrace();;
        } finally {
            tfc.setFinished(true);
        }

    }
    //空发交易文件处理add by hejj 20160109

    private void processFilesForTrx80(String balanceWaterNo, int balanceWaterNoSub, TaskFinishControl tfc) {
        FileUtil util = new FileUtil();
        MessageUtil mUtil = new MessageUtil();
        String fileName;
        String pathTrx;

        try {
            //balanceWaterNo = this.getBalanceWater("hejj");

            File[] filesTrx = util.getFilesForTrx80(FrameCodeConstant.PATH_FILE_TRX);;//交易文件
            //人工清算hejj 20140520
            if (this.isSettleByManu()) {
                pathTrx = TradeUtil.getDirForBalanceWaterNo(FrameCodeConstant.PATH_FILE_TRX_MANU, false);
                filesTrx = util.getFilesForTrx80(pathTrx);;//交易文件
                FrameCodeConstant.PATH_FILE_TRX = pathTrx;//动态改变交易文件目录
            }

            if (filesTrx == null || filesTrx.length == 0) {
                logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":没有手机支付交易文件需处理");

                return;
            }
            logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":手机支付交易文件处理数量：" + filesTrx.length);
            Vector<TaskFinishControl> tfcs = TaskUtil.getTaskControl(filesTrx.length);
            for (int i = 0; i < filesTrx.length; i++) {
                fileName = filesTrx[i].getName();
                this.writeLogFtp(fileName, (int) filesTrx[i].length(), balanceWaterNo, balanceWaterNoSub);//记录接收到的FTP文件日志
                mUtil.putMessageToQueueForFileMobile(fileName, balanceWaterNo, balanceWaterNoSub, tfcs.get(i));

            }
            TaskUtil.waitAllTaskFinish(tfcs, true, "任务：本批次手机支付交易文件处理");

        } catch (Exception ex) {
            ex.printStackTrace();;
        } finally {
            tfc.setFinished(true);
        }

    }
    //二维码平台交易处理20190612
    private void processFilesForTrx7x(String balanceWaterNo, int balanceWaterNoSub, TaskFinishControl tfc) {
        FileUtil util = new FileUtil();
        MessageUtil mUtil = new MessageUtil();
        String fileName;
        String pathTrx;

        try {
            //balanceWaterNo = this.getBalanceWater("hejj");

            File[] filesTrx = util.getFilesForTrx7x(FrameCodeConstant.PATH_FILE_TRX);;//交易文件
            //人工清算hejj 20140520
            if (this.isSettleByManu()) {
                pathTrx = TradeUtil.getDirForBalanceWaterNo(FrameCodeConstant.PATH_FILE_TRX_MANU, false);
                filesTrx = util.getFilesForTrx7x(pathTrx);;//交易文件
                FrameCodeConstant.PATH_FILE_TRX = pathTrx;//动态改变交易文件目录
            }

            if (filesTrx == null || filesTrx.length == 0) {
                logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":没有二维码平台交易文件需处理");

                return;
            }
            logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":二维码平台交易文件处理数量：" + filesTrx.length);
            Vector<TaskFinishControl> tfcs = TaskUtil.getTaskControl(filesTrx.length);
            for (int i = 0; i < filesTrx.length; i++) {
                fileName = filesTrx[i].getName();
                this.writeLogFtp(fileName, (int) filesTrx[i].length(), balanceWaterNo, balanceWaterNoSub);//记录接收到的FTP文件日志
                mUtil.putMessageToQueueForFileQrCode(fileName, balanceWaterNo, balanceWaterNoSub, tfcs.get(i));

            }
            TaskUtil.waitAllTaskFinish(tfcs, true, "任务：本批次二维码平台交易文件处理");

        } catch (Exception ex) {
            ex.printStackTrace();;
        } finally {
            tfc.setFinished(true);
        }

    }
    

    //互联网交易文件处理add by hejj 20161125
    private void processFilesForTrx82(String balanceWaterNo, int balanceWaterNoSub, TaskFinishControl tfc) {
        FileUtil util = new FileUtil();
        MessageUtil mUtil = new MessageUtil();
        String fileName;
        String pathTrx;

        try {

            File[] filesTrx = util.getFilesForTrx82(FrameCodeConstant.PATH_FILE_TRX);//交易文件
            //人工清算hejj 20140520
            if (this.isSettleByManu()) {
                pathTrx = TradeUtil.getDirForBalanceWaterNo(FrameCodeConstant.PATH_FILE_TRX_MANU, false);
                filesTrx = util.getFilesForTrx82(pathTrx);;//交易文件
                FrameCodeConstant.PATH_FILE_TRX = pathTrx;//动态改变交易文件目录
            }

            if (filesTrx == null || filesTrx.length == 0) {
                logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":没有互联网支付交易文件需处理");

                return;
            }
            logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":互联网支付交易文件处理数量：" + filesTrx.length);
            Vector<TaskFinishControl> tfcs = TaskUtil.getTaskControl(filesTrx.length);
            for (int i = 0; i < filesTrx.length; i++) {
                fileName = filesTrx[i].getName();
                this.writeLogFtp(fileName, (int) filesTrx[i].length(), balanceWaterNo, balanceWaterNoSub);//记录接收到的FTP文件日志
                mUtil.putMessageToQueueForFileNetPaid(fileName, balanceWaterNo, balanceWaterNoSub, tfcs.get(i));

            }
            TaskUtil.waitAllTaskFinish(tfcs, true, "任务：本批次互联网支付交易文件处理");

        } catch (Exception ex) {
            ex.printStackTrace();;
        } finally {
            tfc.setFinished(true);
        }

    }

    private void processFilesForOther(String balanceWaterNo, int balanceWaterNoSub, TaskFinishControl tfc) {
        FileUtil util = new FileUtil();
        MessageUtil mUtil = new MessageUtil();
        String fileName;
        String pathOther;

        try {

            File[] filesOther = util.getFilesForOther(FrameCodeConstant.PATH_FILE_TRX);;//审计、寄存器、收益文件

            //人工清算hejj 20140520
            if (this.isSettleByManu()) {
                pathOther = TradeUtil.getDirForBalanceWaterNo(FrameCodeConstant.PATH_FILE_TRX_MANU, false);
                filesOther = util.getFilesForOther(pathOther);;//审计、寄存器、收益文件
            }

            if (filesOther == null || filesOther.length == 0) {
                logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + "：没有审计、寄存器、收益文件需处理");
                return;
            }
            logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":收益文件等处理数量：" + filesOther.length);
            Vector<TaskFinishControl> tfcs = TaskUtil.getTaskControl(filesOther.length);//获取任务控制
            for (int i = 0; i < filesOther.length; i++) {
                fileName = filesOther[i].getName();
                this.writeLogFtp(fileName, (int) filesOther[i].length(), balanceWaterNo, balanceWaterNoSub);//记录接收到的FTP文件日志
                mUtil.putMessageToQueueForFileOther(fileName, balanceWaterNo, balanceWaterNoSub, tfcs.get(i));

            }
            TaskUtil.waitAllTaskFinish(tfcs, true, "任务：本批次审计、寄存器、收益文件处理");

        } catch (Exception ex) {
            ex.printStackTrace();;
        } finally {
            tfc.setFinished(true);
        }

    }

    private void processFilesForOther80(String balanceWaterNo, int balanceWaterNoSub, TaskFinishControl tfc) {
        FileUtil util = new FileUtil();
        MessageUtil mUtil = new MessageUtil();
        String fileName;
        String pathOther;

        try {

            File[] filesOther = util.getFilesForOther80(FrameCodeConstant.PATH_FILE_TRX);;//审计、寄存器、收益文件

            //人工清算hejj 20140520
            if (this.isSettleByManu()) {
                pathOther = TradeUtil.getDirForBalanceWaterNo(FrameCodeConstant.PATH_FILE_TRX_MANU, false);
                filesOther = util.getFilesForOther80(pathOther);;//审计、寄存器、收益文件
            }

            if (filesOther == null || filesOther.length == 0) {
                logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + "：没有手机支付对账文件需处理");
                return;
            }
            logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":手机支付对账文件等处理数量：" + filesOther.length);
            Vector<TaskFinishControl> tfcs = TaskUtil.getTaskControl(filesOther.length);//获取任务控制
            for (int i = 0; i < filesOther.length; i++) {
                fileName = filesOther[i].getName();
                this.writeLogFtp(fileName, (int) filesOther[i].length(), balanceWaterNo, balanceWaterNoSub);//记录接收到的FTP文件日志
                mUtil.putMessageToQueueForFileOtherMobile(fileName, balanceWaterNo, balanceWaterNoSub, tfcs.get(i));

            }
            TaskUtil.waitAllTaskFinish(tfcs, true, "任务：本批次手机支付对账文件处理");

        } catch (Exception ex) {
            ex.printStackTrace();;
        } finally {
            tfc.setFinished(true);
        }

    }
    
    private void processFilesForOther70(String balanceWaterNo, int balanceWaterNoSub, TaskFinishControl tfc) {
        FileUtil util = new FileUtil();
        MessageUtil mUtil = new MessageUtil();
        String fileName;
        String pathOther;

        try {

            File[] filesOther = util.getFilesForOther70(FrameCodeConstant.PATH_FILE_TRX);;//审计、寄存器、收益文件

            //人工清算hejj 20140520
            if (this.isSettleByManu()) {
                pathOther = TradeUtil.getDirForBalanceWaterNo(FrameCodeConstant.PATH_FILE_TRX_MANU, false);
                filesOther = util.getFilesForOther70(pathOther);;//审计、寄存器、收益文件
            }

            if (filesOther == null || filesOther.length == 0) {
                logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + "：没有二维码平台对账文件需处理");
                return;
            }
            logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":二维码平台对账文件等处理数量：" + filesOther.length);
            Vector<TaskFinishControl> tfcs = TaskUtil.getTaskControl(filesOther.length);//获取任务控制
            for (int i = 0; i < filesOther.length; i++) {
                fileName = filesOther[i].getName();
                this.writeLogFtp(fileName, (int) filesOther[i].length(), balanceWaterNo, balanceWaterNoSub);//记录接收到的FTP文件日志
                mUtil.putMessageToQueueForFileOtherQrCode(fileName, balanceWaterNo, balanceWaterNoSub, tfcs.get(i));

            }
            TaskUtil.waitAllTaskFinish(tfcs, true, "任务：本批次二维码平台对账文件处理");

        } catch (Exception ex) {
            ex.printStackTrace();;
        } finally {
            tfc.setFinished(true);
        }

    }
    

    private void processFilesForOther82(String balanceWaterNo, int balanceWaterNoSub, TaskFinishControl tfc) {
        FileUtil util = new FileUtil();
        MessageUtil mUtil = new MessageUtil();
        String fileName;
        String pathOther;

        try {

            File[] filesOther = util.getFilesForOther82(FrameCodeConstant.PATH_FILE_TRX);;//对账文件、订单文件、订单执行文件

            //人工清算hejj 20140520
            if (this.isSettleByManu()) {
                pathOther = TradeUtil.getDirForBalanceWaterNo(FrameCodeConstant.PATH_FILE_TRX_MANU, false);
                filesOther = util.getFilesForOther82(pathOther);;//对账文件、订单文件、订单执行文件
            }

            if (filesOther == null || filesOther.length == 0) {
                logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + "：没有互联网支付对账、订单文件需处理");
                return;
            }
            logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":互联网支付对账、订单文件等处理数量：" + filesOther.length);
            Vector<TaskFinishControl> tfcs = TaskUtil.getTaskControl(filesOther.length);//获取任务控制
            for (int i = 0; i < filesOther.length; i++) {
                fileName = filesOther[i].getName();
                this.writeLogFtp(fileName, (int) filesOther[i].length(), balanceWaterNo, balanceWaterNoSub);//记录接收到的FTP文件日志
                mUtil.putMessageToQueueForFileOtherNetPaid(fileName, balanceWaterNo, balanceWaterNoSub, tfcs.get(i));

            }
            TaskUtil.waitAllTaskFinish(tfcs, true, "任务：本批次互联网支付对账、订单文件处理");

        } catch (Exception ex) {
            ex.printStackTrace();;
        } finally {
            tfc.setFinished(true);
        }

    }

    private void processFilesForOctReturn(String balanceWaterNo, int balanceWaterNoSub, TaskFinishControl tfc) {
        FileUtil util = new FileUtil();
        MessageUtil mUtil = new MessageUtil();
        String fileName;

        try {

            File[] filesOther = util.getFilesForOctReturn(FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_FILE);;//公交IC卡返回的文件目录

            if (filesOther == null || filesOther.length == 0) {
                logger.info("清算流水号：" + balanceWaterNo + "：公交IC卡返回文件需处理");
                return;
            }
            logger.info("清算流水号：" + balanceWaterNo + ":公交IC卡返回文件等处理数量：" + filesOther.length);
            Vector<TaskFinishControl> tfcs = TaskUtil.getTaskControl(filesOther.length);//获取任务控制
            for (int i = 0; i < filesOther.length; i++) {
                fileName = filesOther[i].getName();
                this.writeLogFtpForOct(fileName, (int) filesOther[i].length(), balanceWaterNo);//记录接收到的FTP文件日志
                mUtil.putMessageToQueueForFileOctImportSettle(fileName, balanceWaterNo, balanceWaterNoSub, tfcs.get(i));

            }
            TaskUtil.waitAllTaskFinish(tfcs, true, "任务：本批次公交IC卡返回文件处理");

        } catch (Exception ex) {
            ex.printStackTrace();;
        } finally {
            tfc.setFinished(true);
        }

    }

    private void processFilesForOctImportTrx(String balanceWaterNo, int balanceWaterNoSub, TaskFinishControl tfc) {
        FileUtil util = new FileUtil();
        MessageUtil mUtil = new MessageUtil();
        String fileName;

        try {

            File[] filesOther = util.getFilesForOctImport(FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_FILE);;//公交IC卡返回的文件目录

            if (filesOther == null || filesOther.length == 0) {
                logger.info("清算流水号：" + balanceWaterNo + "：没有公交上传的交易文件需处理");
                return;
            }
            logger.info("清算流水号：" + balanceWaterNo + ":公交上传的交易文件处理数量：" + filesOther.length);
            Vector<TaskFinishControl> tfcs = TaskUtil.getTaskControl(filesOther.length);//获取任务控制
            for (int i = 0; i < filesOther.length; i++) {
                fileName = filesOther[i].getName();
                this.writeLogFtpForOct(fileName, (int) filesOther[i].length(), balanceWaterNo);//记录接收到的FTP文件日志
                mUtil.putMessageToQueueForFileOctImportTrx(fileName, balanceWaterNo, balanceWaterNoSub, tfcs.get(i));

            }
            TaskUtil.waitAllTaskFinish(tfcs, true, "任务：本批次公交上传的交易文件处理");

        } catch (Exception ex) {
            ex.printStackTrace();;
        } finally {
            tfc.setFinished(true);
        }

    }
}
