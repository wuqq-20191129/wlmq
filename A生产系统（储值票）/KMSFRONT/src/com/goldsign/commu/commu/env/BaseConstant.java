package com.goldsign.commu.commu.env;

import com.goldsign.commu.commu.application.IApplication;
import com.goldsign.commu.commu.util.DbcpHelper;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class BaseConstant {

    public static IApplication application;
    
    /**
     * ************文件名称*********
     */
    public static final String CONFIGFILE = "CommuConfig.xml";
    public static final String LOGFILE = "Log4jConfig.xml";
    /**
     * **************************
     */
    //配置存放变量
    public static Hashtable commuConfig = null;
    public static Hashtable MESSAGE_CLASSES;
    public static Hashtable allConnectingIps = new Hashtable();
    public static Vector ipConfigs = new Vector();
    public static Vector prioritys = new Vector();

    /**
     * **********配置文件获取的值**************************
     */
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
    //
    /**
     * ************************************************
     */
    /**
     * *********数据库相关变量存放*****************
     */
    public static DbcpHelper DATA_DBCPHELPER;
    /**
     * **********************
     */
     
    /**
     * 缓存相关
     */
    public static long BUFFERGET_SLEEPTIME = 300000;//300000;//获取缓存的循环时间间隔
    public static String BUFFERGET_RUN_TIME = "0000";//获取缓存的时间,多个时间点使用＃号分隔
    //--------------------------------------
    /**
     * **SOCKET相关*************************************
     */
    public static String MessageClassPrefix = "com.goldsign.frm.commu.message.";
    public static int GetMessageFrequency = 50;//读客户端消息时间
    public static int WriteMessageFrequency = 50;//发送消息时间
    public static int ClientSocketTimeOut = 10000;//客户端超时间
    public static int SocketPort = 5001;
    
    //--------------------------

    /**
     * ********消息优先级类型定义****************
     */
    public static String TYPE_PRIORIITY_NODELAY = "1";//即时处理
    public static String TYPE_PRIORITY_FIRST = "2";//优先处理
    public static String TYPE_PRIORITY_LAST = "3";//普通顺序处理
    //-----------------------------------------
}
