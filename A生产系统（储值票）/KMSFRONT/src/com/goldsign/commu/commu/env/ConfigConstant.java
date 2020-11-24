package com.goldsign.commu.commu.env;

/**
 *
 * @author Administrator
 */
public class ConfigConstant {

    //数据库相关
    public static final String DataConnectionPool_TAG = "DataConnectionPool";
    //线程相关
    public static final String CommunicationThreadPool_TAG = "CommunicationThreadPool";
    public static final String MaxThreadNumber_TAG = "MaxThreadNumber";
    public static final String ThreadSleepTime_TAG = "ThreadSleepTime";
    public static final String ThreadPriorityAdd_TAG = "ThreadPriorityAdd";
    public static final String MaxSearchNum_TAG = "MaxSearchNum";
    public static final String ThreadBufferCapacity_TAG = "ThreadBufferCapacity";
    public static final String ThreadBufferIncrement_TAG = "ThreadBufferIncrement";
    public static final String PriorityThreadBufferCapacity_TAG = "PriorityThreadBufferCapacity";
    public static final String PriorityThreadBufferIncrement_TAG = "PriorityThreadBufferIncrement";
    public static final String ReadThreadPriorityAdd_TAG = "ReadThreadPriorityAdd";

    //连接池测试相关
    public static final String ConnectionPoolListenerThread_TAG = "ConnectionPoolListenerThread";
    public static final String CPListenerThreadSleepTime_TAG = "CPListenerThreadSleepTime";
    public static final String TestSqlForData_TAG = "TestSqlForData";

    //公共区相关
    public static final String Config_TAG = "Config";
    public static final String Common_TAG = "Common";
    
    public static final String Messages_TAG = "Messages";
    public static final String Message_TAG = "Message";
    
    public static final String IpConfigs_TAG = "IpConfigs";
    public static final String IpConfig_TAG = "IpConfig";
    //socket
    public static final String ClientSocketTimeOut_TAG = "ClientSocketTimeOut";
    public static final String GetMessageFrequency_TAG = "GetMessageFrequency";
    public static final String WriteMessageFrequency_TAG = "WriteMessageFrequency";
    public static final String SocketPort_TAG = "SocketPort";

}
