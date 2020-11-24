/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.env;

/**
 *
 * @author Administrator
 */
public class ConfigTagConstant {

    public static final String BackUpLog_TAG = "BackUpLog";
    //ftp相关
    public static final String FtpUserName_TAG = "FtpUserName";
    public static final String FtpUserPassword_TAG = "FtpUserPassword";
    public static final String FtpTimeout_TAG = "FtpTimeout";
    public static final String FtpSocketTimeout_TAG = "FtpSocketTimeout";
    public static final String FtpRetryWaiting_TAG = "FtpRetryWaiting";
    public static final String FtpRetryTimes_TAG = "FtpRetryTimes";
    public static final String FtpLocalDir_TAG = "FtpLocalDir";
    //数据库相关
    public static final String DataConnectionPool_TAG = "DataConnectionPool";
    public static final String Driver_TAG = "Driver";
    public static final String URL_TAG = "URL";
    public static final String User_TAG = "User";
    public static final String Password_TAG = "Password";
    public static final String MaxActive_TAG = "MaxActive";
    public static final String MaxIdle_TAG = "MaxIdle";
    public static final String MaxWait_TAG = "MaxWait";
    
    //线程相关
    public static final String CommunicationThreadPool_TAG = "CommunicationThreadPool";
    public static final String MaxThreadNumber_TAG = "MaxThreadNumber";
    public static final String ThreadSleepTime_TAG = "ThreadSleepTime";
    public static final String ThreadPriorityAdd_TAG = "ThreadPriorityAdd";
    public static final String MaxSearchNum_TAG = "MaxSearchNum";
    public static final String UnHanledMsgLogDir_TAG = "UnHanledMsgLogDir";   
    public static final String ThreadBufferCapacity_TAG = "ThreadBufferCapacity";
    public static final String ThreadBufferIncrement_TAG = "ThreadBufferIncrement";
    public static final String PriorityThreadBufferCapacity_TAG = "PriorityThreadBufferCapacity";
    public static final String PriorityThreadBufferIncrement_TAG = "PriorityThreadBufferIncrement";
    public static final String ReadThreadPriorityAdd_TAG = "ReadThreadPriorityAdd";
    public static final String GetMessageFrequency_TAG = "GetMessageFrequency";
    
    public static final String ConsolePrint_TAG = "ConsolePrint";
    //连接池测试相关
    public static final String ConnectionPoolListenerThread_TAG = "ConnectionPoolListenerThread";
    public static final String CPListenerThreadSleepTime_TAG = "CPListenerThreadSleepTime";
    public static final String TestSqlForData_TAG = "TestSqlForData";
    public static final String SqlMsghandleSleepTime_TAG = "SqlMsghandleSleepTime";
    //公共区相关
    public static final String Config_TAG = "Config";
    public static final String Common_TAG = "Common";
    public static final String FtpPaths_TAG = "FtpPaths";
    public static final String FtpPath_TAG = "FtpPath";
    public static final String Messages_TAG = "Messages";
    public static final String Message_TAG = "Message";
    //BCP相关
    public static final String FtpLocalDirBcp_TAG = "FtpLocalDirBcp";
    //错误文件相关
    public static final String FtpLocalDirError_TAG = "FtpLocalDirError";
    //时间间隔相关
    public static final String IntervalThreadLogLevel_TAG = "IntervalThreadLogLevel";
    public static final String IntervalThreadFindFile_TAG = "IntervalThreadFindFile";
    //审计文件相关
    public static final String AuditFileNextMakeTime_TAG = "AuditFileNextMakeTime";
    public static final String AuditFileFindInterval_TAG = "AuditFileFindInterval";
    public static final String AuditFileMakeDir_TAG = "AuditFileMakeDir";
    //socket
    public static final String ReadOneMessageTimeOut_TAG = "ReadOneMessageTimeOut";
    public static final String SocketPort_TAG = "SocketPort";
    //参数文件相关
    public static final String ParmDstrbPath_TAG = "ParmDstrbPathDir";
    //物理卡号与逻辑卡号对照文件相关
    public static final String PhyLogicFileNextMakeTime_TAG = "PhyLogictFileNextMakeTime";
    public static final String PhyLogicFileFindInterval_TAG = "PhyLogicFileFindInterval";
    public static final String PhyLogicFileMakeDir_TAG = "PhyLogicFileMakeDir";
}
