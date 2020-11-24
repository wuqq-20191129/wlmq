
package com.goldsign.acc.report.constant;

import java.util.Hashtable;


import com.goldsign.lib.db.util.DbcpHelper;

/**
 * Constant for the report generator application
 */
public class AppConstant {
    //if these constants are set in the ReportConfig.xml, their values here will be overrided
    //初始化在启动时

    public static String ReportsParm = "0";
    public static String ReportsCode = "0";
    public static String ReportsLine = "0";
    public static int GeneratorInterval = 300000;
    public static int OneReportMaxTime = 100000;
    public static String RasUrl = "";
    public static String FilePath = "";
    //not set in ReportConfig.xml
    public static String Reporting = "";
    //public static DbHelper REPORT_DBHELPER;
    public static DbcpHelper REPORT_DBCPHELPER;
    //public static Hashtable DB_CONFIG;
    public static Hashtable DBCP_CONFIG;
    public static String SystemStartDateTime = "";		//format is yyyyMMddHHmmss
    public static long threadSleepTime = 1000;
    public static int threadNum = 1;
    public static int maxCount = 6;
    public static int threadNumBigReport = 2;
    public static int threadNumMaxReport = 2;
    public static long threadDelayTime = 70000;
    public static String threadDelayQueue = "";//
    public static String threadDelayQueueReportCode = "";//
    public static String threadPriorityQueue = "";
    public static String threadMaxQueue = "";//大报表模板
    public static long MinReportGenTime = 1000;
    //大报表，优先队列报表，延时报表，最小报表生成时间
    //版本标志
    public static String RECORD_FLAG_CURRENT = "0";//当前版本
    //系统标识
    public static String SYSTEM_REPORT = "3";//报表系统
    //系统日志级别
    public static String LOG_LEVEL_CURRENT = "1";//日志级别
    public static String LOG_LEVEL_INFO = "1";//普通信息级
    public static String LOG_LEVEL_WARN = "2";//警告信息级
    public static String LOG_LEVEL_ERROR = "3";//一般错误级
    public static String LOG_LEVEL_ERROR_SYS = "4";//系统错误级
    public static String LOG_LEVEL_TEXT_CURRENT = "普通信息";
    public static String LOG_LEVEL_INFO_TEXT = "普通信息";//普通信息级
    public static String LOG_LEVEL_WARN_TEXT = "警告信息";//警告信息级
    public static String LOG_LEVEL_ERROR_TEXT = "一般错误";//一般错误级
    public static String LOG_LEVEL_ERROR_SYS_TEXT = "系统错误";//系统错误级
    //日志级别线程时间间隔
    public static long threadLogLevel = 300000;
    /*
     * 报表属性
     */
        public static String REPORT_DAY = "1";//日报
    public static String REPORT_MONTH = "2";//月报
    public static String REPORT_YEAR = "3";//年报
    public static String REPORT_TYPE_DAYTWO = "2";//报表类型，按清算日及运营日
    public static String REPORT_TYPE_DAY = "1";//报表类型，按清算日或运营日
    public static String REPORT_WATER_NO = "";//报表生成的当前流水
    public static String BUFFER_FLAG_NORMAL = "1";//正常生成缓存
    public static String BUFFER_FLAG_REGEN = "2";//重新生成缓存
    public static String BUFFER_FLAG_ERROR = "3";//错误缓存
    /**
     * 文件名称
     */
    public static final String LOGFILE = "ReportLog4jConfig.xml";//日志文件名称
    public static final String CONFIGFILE = "ReportConfig.xml";//配置文件名称
    public static final String LogFileName = "report.log";
    public static final String ConsoleLogFileName = "reportConsole.log";
    /**
     * 报表服务器
     */
    public static String serverName ="";
    public static String serverUser ="";
    public static String serverPass ="";
    public static String handleServer ="";
    
        /**
     * 数据库用户
     */
    public static String ST_USER="w_acc_st.";
      /**
     * 报表生成控制
     */
    public static String VALID_FLAG_YES="1";//控制生效
    public static String VALID_FLAG_NO="0";//控制不生效
    /**
     * 报表周期类型相关
     */
    
   public static final String FLAG_PERIOD_MONTH = "2";
    public static final String FLAG_PERIOD_YEAR = "3";
    public static final String FLAG_PERIOD_DAY = "1";
    public static final String FLAG_PERIOD_CUSTOMED = "9";
    public static final String FLAG_GENERATE_ALL = "*";
    
}
