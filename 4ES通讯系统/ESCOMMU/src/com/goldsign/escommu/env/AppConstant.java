/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.env;

import com.goldsign.escommu.dbutil.DbcpHelper;
import com.goldsign.escommu.vo.ImportConfig;
import java.util.Hashtable;
import java.util.TreeSet;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class AppConstant {

    //工作目录
    public static final String appWorkDir = System.getProperty("user.dir");
    
    /**
     * ************文件名称*********
     */
    public static final String CONFIGFILE = "config/EsCommuConfig.xml";
    public static final String BackUpLog_TAG = "BackUpLog";
    public static final String LogFilePath = appWorkDir+"/log";
    public static final String LogFileName = "EsCommu.log";
    public static final String LOGFILE = "config/EsLog4jConfig.xml";
    
    /**
     * **************************
     */
    //配置存放变量
    public static Hashtable commuConfig = null;
    public static Hashtable FTP_PATHS;
    public static Hashtable MESSAGE_CLASSES;
    public static Hashtable all_connecting_ip = new Hashtable();
    public static Vector prioritys = new Vector();
    public static Hashtable devIps = new Hashtable();
    public static ImportConfig importConfig = new ImportConfig();
    public static TreeSet cards = new TreeSet();
    public static TreeSet lines = new TreeSet();
    public static TreeSet stations = new TreeSet();
    /**
     * **********配置文件获取的值**************************
     */
    //FTP相关配置值
    public static String FtpUserName = "anonymous";
    public static String FtpUserPassword = "";
    public static int FtpTimeout = 5000;
    public static int FtpSocketTimeout = 10000;
    public static int FtpRetryTime = 3;
    public static int FtpRetryWaiting = 10000;
    public static String FtpLocalDir = "";
    public static String FtpLocalDirError = "";
    public static String FtpLocalDirBcp = "";
    //-----------------
    //线程池相关
    public static int ThreadSleepTime = 100;
    public static int ThreadPriority = 5;
    public static int MaxThreadNumber = 5;
    public static int MaxSearchNum = 2;
    public static boolean ConsolePrint = true;
    public static int ReadThreadPriorityAdd = 0;
    public static String UnHanledMsgLogDir = "";
    public static int ThreadBufferCapacity = 1000;
    public static int ThreadBufferIncrement = 1000;
    public static int PriorityThreadBufferCapacity = 1000;
    public static int PriorityThreadBufferIncrement = 1000;
    //----------------
    //连接池测试相关
    public static int CPListenerThreadSleepTime = 1000;
    public static String TestSqlForData = "";
    public static int SqlMsgHandleSleepTime = 100;
    //-----------------------
    /**
     * *********数据库相关变量存放*****************
     */
    public static DbcpHelper DATA_DBCPHELPER;
    /**
     * ************日志*****************
     */
    //	系统日志级别
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
    //*************线程时间间隔
    public static long IntervalThreadLogLevel = 300000;
    public static long IntervalThreadFindFile = 10000;//文件获取
    //-------------------------------------
     
    /**
     * 缓存相关
     */
    public static long BUFFERGET_SLEEPTIME = 300000;//300000;//获取缓存的循环时间间隔
    public static String BUFFERGET_RUN_TIME = "0300";//获取缓存的时间,多个时间点使用＃号分隔
    //--------------------------------------
    /**
     * **连接相关*************************************
     */
    public static String MessageClassPrefix = "com.goldsign.escommu.message.";
    public static int SendQueryWait=1000;
    public static int GetMessageFrequency = 200;
    public static int ReadOneMessageTimeOut = 0;//60000;
    //------------------------------------------------
    /**
     * *版本相关***************************
     */
    //	版本标志
    public static String RECORD_FLAG_CURRENT = "0";//当前版本
    //-------------------------------------
    /**
     * **系统标识***********************
     */
    //系统标识
    public static String SYSTEM_COMMU = "4";//ES通讯系统
    //-------------------------------------
    /**
     * SOCKET相关*****************************
     */
    public static int Port = 5001;
    //--------------------------

    /**
     * ***查找文件相关*********************
     */
    public static String FTP_STATUS_UNGET = "0";//未获取
    public static String FTP_STATUS_SUCCESS = "1";//已获取
    public static String FTP_STATUS_FAILURE = "2";//获取失败
    public static String FTP_STATUS_ILLEGAL = "3";//非法文件
    //---------------------------------------
    /**
     * ********消息优先级类型定义****************
     */
    public static String TYPE_PRIORIITY_NODELAY = "1";//即时处理
    public static String TYPE_PRIORITY_FIRST = "2";//优先处理
    public static String TYPE_PRIORITY_LAST = "3";//普通顺序处理
    //-----------------------------------------

    /**
     * *******加密相关****
     */
    public static String KEY = "GOLDSIGN";
    public static String FLAG_ENC = "1";
    public static String FLAG_ENC_NOT = "0";
    //----------------------
    //审计文件相关
    public static String AuditFileNextMakeTime = "22";
    public static int AuditFileFindInterval = 1;    //单位：时
    public static String AuditFileMakeDir;
    //参数文件路径
    public static String ParmDstrbPath = "";
    
    //------------------------
    //物理卡逻辑卡号对照文件生成时间
    public static String PhysicsLogicNextMakeTime = "03";
    public static int PhyLogicFileFindInterval = 1;    //单位：时
    public static String PhyLogicFileMakeDir;
    
    //------------------------
    //数据库前缀
    public static String TABLE_PREFIX = "W_";
    public static String COM_CM_P = "W_ACC_CM."+TABLE_PREFIX;
    public static String COM_ST_P = "w_acc_st."+TABLE_PREFIX;
    public static String COM_TK_P = "W_ACC_TK."+TABLE_PREFIX;
    public static String COM_CM = "W_ACC_CM.";
    public static String COM_ST = "w_acc_st.";
    public static String COM_TK = "W_ACC_TK.";
}
