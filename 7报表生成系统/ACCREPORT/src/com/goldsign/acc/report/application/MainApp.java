/*
 * Amendment History:
 *
 * Date          By             Description
 * ----------    -----------    -------------------------------------------
 * 2011-11-28    hejj           新建
 */
package com.goldsign.acc.report.application;

import com.goldsign.acc.report.handler.ReportGenerator;
import java.io.*;

import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import com.goldsign.acc.report.constant.AppConstant;
import com.goldsign.acc.report.constant.XMLConstant;

import com.goldsign.acc.report.dao.ReportAttributeDAO;
import com.goldsign.acc.report.dao.ReportWaterNoDao;
import com.goldsign.acc.report.exception.LogException;
import com.goldsign.acc.report.exception.ReportException;
import com.goldsign.acc.report.thread.ReportFileThread;
import com.goldsign.acc.report.thread.ReportLogLevelThread;
import com.goldsign.acc.report.thread.ReportStopHook;

import com.goldsign.acc.report.thread.ReportThreadForError;
import com.goldsign.acc.report.thread.ReportThreadManager;
import com.goldsign.acc.report.util.DateHelper;
import com.goldsign.acc.report.util.ReportLogUtil;

import com.goldsign.acc.report.vo.LogReportTotalVo;
import com.goldsign.acc.report.vo.Report;

import com.goldsign.acc.report.xml.ReportXMLHandler;
import com.goldsign.lib.db.util.DbcpHelper;
import com.goldsign.acc.report.dao.BalanceWaterDAO;
import com.goldsign.acc.report.vo.InitResult;
import java.util.GregorianCalendar;

/**
 *
 * @author hejj
 */
public class MainApp {

    private static Logger logger = Logger.getLogger(MainApp.class.getName());
    private boolean stoped = false;

    public static void main(String[] args) {
        MainApp app = new MainApp();
        Runtime.getRuntime().addShutdownHook(new ReportStopHook(app));
        app.start();
    }

    public void start() {
        try {
            this.initLogConfig();
            logger.info("日志初始化成功");
            initConfig();
            logger.info("配置初始化成功");
            this.startGeneratorByGroup();
            logger.info("报表生成器启动");
        } catch (LogException e) {
            e.printStackTrace();
        } catch (ReportException e) {
            logger.error("配置初始化失败:" + e);
        } catch (Exception e) {
            logger.error("报表生成器启动失败" + e);
            
        } finally {
            stop();
        }
    }

    public void stop() {
        if (!stoped) {
            stoped = !stoped;
            logger.info("报表生成器正在准备停止。。。。。。");
            try {

                AppConstant.REPORT_DBCPHELPER.close();
            } catch (Exception e) {
            }

            logger.info("报表生成器已停止！");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 初始化日志配置
     *
     * @throws LogException
     */
    private void initLogConfig() throws LogException {
        try {
            System.out.println("日志配置文件："+AppConstant.LOGFILE);
            //String path ="/home/wweb/ACCReport/";
            // DOMConfigurator.configureAndWatch(path+AppConstant.LOGFILE);
            DOMConfigurator.configureAndWatch(AppConstant.LOGFILE);
        } catch (Exception e) {
            throw new LogException("初始化" + AppConstant.LOGFILE + " 错误!" + e);
        }

    }

    /**
     * 初始化报表配置
     *
     * @throws ReportException
     */
    private void initConfig() throws ReportException {
        String value = null;

        //set SystemStartDateTime;
        Date ssdt = new Date(System.currentTimeMillis());
        AppConstant.SystemStartDateTime = DateHelper.dateToString(ssdt);

        //init config from xml file
        try {
            ReportXMLHandler reportXMLHandler = new ReportXMLHandler();
            Hashtable reportConfig = reportXMLHandler.parseConfigFile(AppConstant.CONFIGFILE);

            //if no tag in .xml,value will be null;if <tag></tag>,value.length() will be 0

            //backup log file
            value = (String) reportConfig.get(XMLConstant.BackUpLog_TAG);
            if (value == null || value.length() == 0) {
                //throw new ReportException("Tag <"+BackUpLog_TAG+"> and setting are required in the config file!");
            } else {
                String oldLog = AppConstant.SystemStartDateTime + ".log";
                String oldConsoleLog = "reportConsole"
                        + AppConstant.SystemStartDateTime + ".log";
                File oldLogFile = new File(value, oldLog);
                File oldConsoleLogFile = new File(value, oldConsoleLog);

                File logFile = new File(AppConstant.LogFileName);
                File consoleLogFile = new File(AppConstant.ConsoleLogFileName);

                if (logFile.exists()) {
                    logFile.renameTo(oldLogFile);
                    logger.info("日志文件备份完成");
                }
                if (consoleLogFile.exists()) {
                    consoleLogFile.renameTo(oldConsoleLogFile);
                    logger.info("控制台输出备份完成");
                }
            }

            // APP config
            value = (String) reportConfig.get(XMLConstant.ReportsParm_TAG);
            if (value == null || value.length() == 0) {
                //throw new ReportException("Tag <"+ReportsParm_TAG+"> and setting are required in the config file!");
            } else {
                AppConstant.ReportsParm = value;
            }
            value = (String) reportConfig.get(XMLConstant.ReportsCode_TAG);
            if (value == null || value.length() == 0) {
                //throw new ReportException("Tag <"+ReportsParm_TAG+"> and setting are required in the config file!");
            } else {
                AppConstant.ReportsCode = value;
            }
            value = (String) reportConfig.get(XMLConstant.ReportsLine_TAG);
            if (value == null || value.length() == 0) {
                //throw new ReportException("Tag <"+ReportsParm_TAG+"> and setting are required in the config file!");
            } else {
                AppConstant.ReportsLine = value;
            }

            value = (String) reportConfig.get(XMLConstant.GeneratorInterval_TAG);
            if (value == null || value.length() == 0) {
                throw new ReportException("Tag <" + XMLConstant.GeneratorInterval_TAG
                        + "> and setting are required in the config file!");
            }
            AppConstant.GeneratorInterval = Integer.parseInt(value);

            value = (String) reportConfig.get(XMLConstant.MinReportGenTime_TAG);
            if (value == null || value.length() == 0) {
                throw new ReportException("Tag <" + XMLConstant.MinReportGenTime_TAG
                        + "> and setting are required in the config file!");
            }
            AppConstant.MinReportGenTime = Integer.parseInt(value);


            value = (String) reportConfig.get(XMLConstant.OneReportMaxTime_TAG);
            if (value == null || value.length() == 0) {
                //throw new ReportException("Tag <"+OneReportMaxTime_TAG+"> and setting are required in the config file!");
            } else {
                AppConstant.OneReportMaxTime = Integer.parseInt(value);
            }

            value = (String) reportConfig.get(XMLConstant.RasUrl_TAG);
            if (value == null || value.length() == 0) {
                throw new ReportException("Tag <" + XMLConstant.RasUrl_TAG
                        + "> and setting are required in the config file!");
            }
            AppConstant.RasUrl = value.trim();

            value = (String) reportConfig.get(XMLConstant.FilePath_TAG);
            if (value == null || value.length() == 0) {
                throw new ReportException("Tag <" + XMLConstant.FilePath_TAG
                        + "> and setting are required in the config file!");
            }
            AppConstant.FilePath = value;

            // Initialize report database connection
            AppConstant.DBCP_CONFIG = (Hashtable) reportConfig.get(XMLConstant.ReportDbCP_TAG);



            this.initThreads(reportConfig);
            logger.info("报表处理线程初始化完成");
            this.initReportServer(reportConfig);
            logger.info("完成报表服务器参数获取");


        } catch (Exception e) {
            throw new ReportException("初始化" + AppConstant.CONFIGFILE + " 错误!" + e);
        }
    }

    private void initReportServer(Hashtable reportConfig) {
        Hashtable configServer = (Hashtable) reportConfig.get(XMLConstant.ReportServer_TAG);
        AppConstant.serverName = (String) configServer.get(XMLConstant.ReportServerName_TAG);
        AppConstant.serverUser = (String) configServer.get(XMLConstant.ReportServerUser_TAG);
        AppConstant.serverPass = (String) configServer.get(XMLConstant.ReportServerPass_TAG);
        AppConstant.handleServer = (String) configServer.get(XMLConstant.ReportServerHandleServer_TAG);


    }

    /**
     * 初始化线程属性
     *
     * @param reportConfig
     * @throws Exception
     */
    private void initThreads(Hashtable reportConfig) throws Exception {
        Hashtable threads = (Hashtable) reportConfig.get(XMLConstant.ReportThreads_TAG);
        AppConstant.threadSleepTime = new Long((String) threads.get(XMLConstant.ThreadSleepTime_TAG)).longValue();
        AppConstant.threadDelayTime = new Long((String) threads.get(XMLConstant.ThreadDelayTime_TAG)).longValue();
        AppConstant.threadDelayQueue = (String) threads.get(XMLConstant.ThreadDelayQueue_TAG);
        AppConstant.threadDelayQueueReportCode = (String) threads.get(XMLConstant.ThreadDelayQueueReportCode_TAG);
        AppConstant.threadPriorityQueue = (String) threads.get(XMLConstant.ThreadPriorityQueue_TAG);
        AppConstant.threadMaxQueue = (String) threads.get(XMLConstant.ThreadMaxQueue_TAG);
        AppConstant.maxCount = new Integer((String) threads.get(XMLConstant.MaxCount_TAG)).intValue();

        AppConstant.threadNum = new Integer((String) threads.get(XMLConstant.ThreadNum_TAG)).intValue();
        AppConstant.threadNumBigReport = new Integer((String) threads.get(XMLConstant.ThreadNumBigReport_TAG)).intValue();
        AppConstant.threadNumMaxReport = new Integer((String) threads.get(XMLConstant.ThreadNumMaxReport_TAG)).intValue();
    }

    private void startGeneratorByGroup() throws Exception {
        //String balanceWaterNo = "";
        long startTime = 0;
       // boolean isReadyGenerateReport = false;
        InitResult iResult=null ;

        while (true) {
            try {
                init();
                logger.info("数据库连接池初始化完成");


                this.startLogLevelThread();

                logger.info("日志级别设置完成");


               // isReadyGenerateReport = readyGenerateReport();
                iResult = this.readyGenerateReportForAll();
                logger.info("获取清算流水号："+iResult.getBalanceWaterNo());

                if (iResult.isReadyGenerateReport()) {
                    logger.info("报表生成开始");

                    AppConstant.REPORT_WATER_NO = new ReportWaterNoDao().getReportWaterNo();
                    logger.info("报表流水号" + AppConstant.REPORT_WATER_NO);

                    ReportThreadManager.initThread();
                    logger.info("报表处理线程缓存全部清空完成");
                    //由于报表服务器对大报表的处理问题、报表生成的时间、大小等因素
                    //队列分4种，缓存队列、普通队列、优先级队列、大报表，每种分日报、月报、年报生成
                    //缓存队列、普通队列、优先级队列、大报表队列依次存放处理难度增大的报表
                    //优先级队列没有作处理，处理顺序 缓存队列－》普通队列－》大报表
                    getReportAttributeByGroupForThread(iResult);
                    logger.info("报表处理分组完成");
                    this.printTreeMapInfoForAll();  //输出分组信息                 
                    //separate balance_water_no reporting
                    startTime = System.currentTimeMillis();

                    logger.info("报表处理线程启动，共" + AppConstant.threadNum + "个");
                    ReportThreadManager.startReportThreads(iResult);
/*
                    for (int i = 0; i < Report.BalanceWaters.size(); i++) {
                        balanceWaterNo = (String) Report.BalanceWaters.get(i);
                        ReportThreadManager.startReportThreads(balanceWaterNo);
                    }
                    */

                } else {

                    logger.warn("没有可用的清算流水号");
                }
            } catch (Exception e) {
                logger.error("Report generator error - " + e);
                e.printStackTrace();
            } finally {
                try {
                    if (iResult!=null &&iResult.isReadyGenerateReport()) {
                        this.finishGenerateReports(iResult.getBalanceWaterNo(), startTime);
                    }

                    clear(iResult.isReadyGenerateReport(),iResult);
                    if (iResult.isReadyGenerateReport()) {
                        logger.info("延时"
                                + (AppConstant.GeneratorInterval / 1000)
                                + "s 以开始下一次报表处理 ...............");
                    }
                    Thread.sleep(AppConstant.GeneratorInterval);

                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 初始化数据库连接
     *
     * @throws Exception
     */
    private void init() throws Exception {
        //  System.out.println("Report generator awaked!");
        String driver = (String) AppConstant.DBCP_CONFIG.get("Driver");
        String url = (String) AppConstant.DBCP_CONFIG.get("URL");
//		String database  = (String) AppConstant.DBCP_CONFIG.get("Database");
        String user = (String) AppConstant.DBCP_CONFIG.get("User");
        String password = (String) AppConstant.DBCP_CONFIG.get("Password");
        int maxActive = Integer.parseInt((String) AppConstant.DBCP_CONFIG.get("MaxActive"));
        int maxIdle = Integer.parseInt((String) AppConstant.DBCP_CONFIG.get("MaxIdle"));
        int maxWait = Integer.parseInt((String) AppConstant.DBCP_CONFIG.get("MaxWait"));




        AppConstant.REPORT_DBCPHELPER = new DbcpHelper(driver, url, null,
                user,
                password,
                maxActive,
                maxIdle,
                maxWait);

        //  System.out.println("ReportDB connection created!");
        Report.CenterBalanceWater = "";
        Report.BalanceWaters.clear();
        Report.genMonthReport = false;
        Report.genYearReport = false;
    }

    private void startLogLevelThread() {
        ReportLogLevelThread t = new ReportLogLevelThread();
        t.runOne();

    }

    private boolean readyGenerateReport() {
        boolean ready = false;
        BalanceWaterDAO dao = new BalanceWaterDAO();
        ready = dao.readyGenerateReport();
        return ready;
    }
     private InitResult readyGenerateReportForAll() {
        InitResult iResult;
        BalanceWaterDAO dao = new BalanceWaterDAO();
        iResult = dao.readyGenerateReportForAll();
        return iResult;
    }

    /**
     * 报表处理分组
     */
    private void getReportAttributeByGroupForThread(InitResult iResult) {
        ReportAttributeDAO dao = new ReportAttributeDAO();
        // dao.getReportAttributeByGroupForThread(AppConstant.ReportsParm, AppConstant.ReportsCode);
       // String balanceWaterNo = (String) Report.BalanceWaters.get(0);
        dao.getReportAttributeByGroupForThread(iResult.getBalanceWaterNo(),iResult);
    }

    protected void printTreeMapInfo() {
        ReportThreadManager.printTreeMapInfo(ReportThreadManager.reportModuleCodes);
        ReportThreadManager.printTreeMapInfo(ReportThreadManager.reportModuleCodesMonth);
        ReportThreadManager.printTreeMapInfo(ReportThreadManager.reportModuleCodesYear);
    }

    protected void printTreeMapInfoForAll() {
        logger.info("===普通报表====");
        logger.info("===日报====");
        ReportThreadManager.printTreeMapInfo(ReportThreadManager.reportModuleCodes);
        logger.info("===月报====");
        ReportThreadManager.printTreeMapInfo(ReportThreadManager.reportModuleCodesMonth);
        logger.info("===年报====");
        ReportThreadManager.printTreeMapInfo(ReportThreadManager.reportModuleCodesYear);
        logger.info("=============");
        /*
        logger.info("===普通队列报表===");
        ReportThreadManager.printTreeMapInfo(ReportThreadManager.reportModuleCodesQueue);
        ReportThreadManager.printTreeMapInfo(ReportThreadManager.reportModuleCodesQueueMonth);
        ReportThreadManager.printTreeMapInfo(ReportThreadManager.reportModuleCodesQueueYear);
        logger.info("================");
        logger.info("===优先队列报表===");
        ReportThreadManager.printTreeMapInfo(ReportThreadManager.reportModuleCodesPriority);
        ReportThreadManager.printTreeMapInfo(ReportThreadManager.reportModuleCodesPriorityMonth);
        ReportThreadManager.printTreeMapInfo(ReportThreadManager.reportModuleCodesPriorityYear);
        logger.info("================");
        logger.info("===大报表===");
        ReportThreadManager.printTreeMapInfo(ReportThreadManager.reportModuleCodesMax);
        ReportThreadManager.printTreeMapInfo(ReportThreadManager.reportModuleCodesMaxMonth);
        ReportThreadManager.printTreeMapInfo(ReportThreadManager.reportModuleCodesMaxYear);
        logger.info("===========");
        */
    }

    protected void generateReportForQuene(String balanceWaterNo) {
        boolean lastday;
        boolean yearlastday;
        int n;
        int n1;
        int n2;
        lastday = checkLastDay(balanceWaterNo);
        yearlastday = checkyearLastDay(balanceWaterNo);
        logger.info("=====开始普通队列报表=====");
        ReportThreadManager.startReportThreadForBigReportSeperate(balanceWaterNo);

        //    ReportThreadManager.startReportThreadForBigReport(balanceWaterNo);
        //普通队列报表一般是生成时间较长且需要报表服务器缓存较大的报表如客流报表
        n = ReportThreadManager.getTreeMapReportNum(ReportThreadManager.reportModuleCodesQueue);
        n1 = n + ReportThreadManager.getTreeMapReportNum(ReportThreadManager.reportModuleCodesQueueMonth);
        n2 = n1 + ReportThreadManager.getTreeMapReportNum(ReportThreadManager.reportModuleCodesQueueYear);

        if (yearlastday) {
            logger.info("===完成普通队列报表，共 " + n2 + " 张===");
        } else if (lastday) {
            logger.info("===完成普通队列报表，共 " + n1 + " 张===");
        } else {
            logger.info("===完成普通队列报表，共 " + n + " 张===");
        }

        this.waitThreadBigReportFinish();
        logger.info("=====开始大报表队列报表处理=====");
        ReportThreadManager.startReportThreadForMaxReportSeperate(balanceWaterNo);

        //大报表队列报表一般是相对于普通队列报表，其生成时间更长且需要报表服务器缓存更大的报表如客流报表中最大的报表，其处理线程更少
        n = ReportThreadManager.getTreeMapReportNum(ReportThreadManager.reportModuleCodesMax);
        n1 = n + ReportThreadManager.getTreeMapReportNum(ReportThreadManager.reportModuleCodesMaxMonth);
        n2 = n1 + ReportThreadManager.getTreeMapReportNum(ReportThreadManager.reportModuleCodesMaxYear);
        if (yearlastday) {
            logger.info("===完成大报表队列报表，共 " + n2 + " 张===");
        } else if (lastday) {
            logger.info("===完成大报表队列报表，共 " + n1 + " 张===");
        } else {
            logger.info("===完成大报表队列报表，共 " + n + " 张===");
        }
        this.waitThreadMaxReportFinish();


        logger.info("=====开始处理以上报表生成过程中出错的报表=====");
        //对于缓存队列、普通队列、大报表队列处理过程中，不能处理的报表如服务器缓存异常报表，作最后处理
        //使用单线程，且延时1分钟，使报表服务器的缓存充分释放后才开始生成
        this.processErrorReports(balanceWaterNo);




    }

    public void finishGenerateReports(String balanceWaterNo, long startTime) {
        long endTime = 0;
        int n = 0;
        int n1 = 0;
        int n2 = 0;
        int total;
        boolean lastday;
        try {
            while (true) {
                if (ReportThreadManager.isAllThreadFinish()) {
                    logger.info("======= 所有正常报表处理完成 =======");

                    this.generateReportForQuene(balanceWaterNo);

                    endTime = System.currentTimeMillis();
                    //total = ReportFileThread.getFilesGenerated()+n+n1;

                    //记录汇总信息包括用时、清算流水号
                    LogReportTotalVo vo = this.log(balanceWaterNo, startTime, endTime);

                    logger.info(
                            "报表处理完成，共计： "
                            + (this.getInt(vo.getTotalNum()) + this.getInt(vo.getTotalDelayNum()))
                            + " 其中生成正常报表: " + vo.getTotalNum() + "（张）次"
                            + " 生成滞留报表: " + vo.getTotalDelayNum() + "（张）次"
                            + " 共用时： "
                            + (endTime - startTime) + " ms");



                    logger.info("报表处理完成，清理所有缓存");

                    ReportFileThread.resetFileGenerated();
                    //this.restoreThreadStatus();

                    break;
                }
                Thread.sleep(5000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void clear(boolean isReadyGenerateReport,InitResult iResult) {
        try {
            /*
             if (Report.CenterBalanceWater.length() != 0) {
             BalanceWaterDAO dao = new BalanceWaterDAO();
             dao.updateReportFlag(Report.CenterBalanceWater);
             }
             */
            if (isReadyGenerateReport) {
                for (int i = 0; i < iResult.getBalanceWaters().size(); i++) {
                    BalanceWaterDAO dao = new BalanceWaterDAO();
                    dao.updateReportFlag((String) iResult.getBalanceWaters().get(i));
                }
            }


            AppConstant.REPORT_DBCPHELPER.close();
            AppConstant.REPORT_DBCPHELPER = null;
            ReportLogUtil.clearReportTotal();//统计数据复0

        } catch (Exception e) {
        }
    }

    private boolean checkLastDay(String dayStr) {
        try {
            int y = Integer.parseInt(dayStr.substring(0, 4));
            int m = Integer.parseInt(dayStr.substring(4, 6));
            int d = Integer.parseInt(dayStr.substring(6, 8));

            GregorianCalendar gc = new GregorianCalendar(y, m - 1, d);
            gc.add(GregorianCalendar.DATE, 1);
            if (gc.get(GregorianCalendar.DATE) == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("checkLastDay(" + dayStr + ") error! " + e);
            return false;
        }
    }

    private boolean checkyearLastDay(String dayStr) {
        try {
            int y = Integer.parseInt(dayStr.substring(0, 4));
            int m = Integer.parseInt(dayStr.substring(4, 6));
            int d = Integer.parseInt(dayStr.substring(6, 8));

            GregorianCalendar gc = new GregorianCalendar(y - 1, m, d);
            gc.add(GregorianCalendar.DATE, 1);
            if ((gc.get(GregorianCalendar.DATE) == 1) && (gc.get(GregorianCalendar.MONTH) == 1)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("checkyearLastDay(" + dayStr + ") error! " + e);
            return false;
        }
    }

    public void waitThreadBigReportFinish() {
        try {
            while (true) {
                if (ReportThreadManager.isAllThreadBigReportFinish()) {
                    break;
                }
                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void waitThreadMaxReportFinish() {
        try {
            while (true) {
                if (ReportThreadManager.isAllThreadMaxReportFinish()) {
                    break;
                }
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void processErrorReports(String balanceWaterNo) {
        Vector errReports = null;
        ReportThreadForError errReportThread = null;
        errReports = new Vector();
        errReports.addAll(ReportGenerator.getErrorReports());


        try {
            if (!errReports.isEmpty()) {
                logger.info(
                        "等候30秒，开始处理报表服务器缓存异常的报表，共："
                        + errReports.size());
                Thread.sleep(30000);

                errReportThread = new ReportThreadForError(errReports, balanceWaterNo, 100,
                        AppConstant.BUFFER_FLAG_ERROR);
                errReportThread.start();

                while (true) {
                    if (errReportThread.getThreadStatus()) {
                        break;
                    }
                    Thread.sleep(3000);
                }
                logger.info(
                        "完成处理报表服务器缓存异常的报表，共："
                        + errReports.size());
                //   this.restoreOneThreadStatus();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            ReportGenerator.clearErrorReports();
        }

    }

    private LogReportTotalVo log(String balanceWaterNo, long startTime, long endTime) {
//		记录汇总日志
        LogReportTotalVo vo = ReportLogUtil.getLogReportTotalVo(balanceWaterNo, startTime, endTime);
        ReportLogUtil.logForDbTotal(vo, AppConstant.LOG_LEVEL_INFO);



        return vo;

    }

    private int getInt(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return new Integer(str).intValue();
    }
}
