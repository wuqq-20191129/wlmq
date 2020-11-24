/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.util;

import com.goldsign.escommu.dao.BcpDao;
import com.goldsign.escommu.dao.InitiDao;
import com.goldsign.escommu.dbutil.DbcpHelper;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.ConfigTagConstant;
import com.goldsign.escommu.exception.ConfigFileException;
import com.goldsign.escommu.thread.CommuLogLevelThread;
import java.io.File;
import java.util.Date;
import java.util.Hashtable;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author Administrator
 */
public class InitialUtil {
    
    public void initThreadPool(Hashtable commuConfig) throws ConfigFileException {
        
        //获得线程池的配置属性
        Hashtable ht = (Hashtable) commuConfig.get(ConfigTagConstant.CommunicationThreadPool_TAG);
        AppConstant.MaxThreadNumber = Integer.parseInt((String) ht.get(ConfigTagConstant.MaxThreadNumber_TAG));
        AppConstant.MaxSearchNum = Integer.parseInt((String) ht.get(ConfigTagConstant.MaxSearchNum_TAG));
        AppConstant.ThreadSleepTime = Integer.parseInt((String) ht.get(ConfigTagConstant.ThreadSleepTime_TAG));
        AppConstant.ThreadPriority = Thread.NORM_PRIORITY + Integer.parseInt((String) ht.get(ConfigTagConstant.ThreadPriorityAdd_TAG));
        
        String value = (String) ht.get(ConfigTagConstant.UnHanledMsgLogDir_TAG);
        if (value == null || value.length() == 0) {
            throw new ConfigFileException("Tag <" + ConfigTagConstant.UnHanledMsgLogDir_TAG
                    + "> 需在配置文件中设置!");
        }
        this.checkDir(value);
        AppConstant.UnHanledMsgLogDir = value;
         DateHelper.screenPrintForEx("设置未处理消息日志目录:" + AppConstant.UnHanledMsgLogDir);
         
        AppConstant.ThreadBufferCapacity = Integer.parseInt((String) ht.get(ConfigTagConstant.ThreadBufferCapacity_TAG));
        AppConstant.ThreadBufferIncrement = Integer.parseInt((String) ht.get(ConfigTagConstant.ThreadBufferIncrement_TAG));
        AppConstant.PriorityThreadBufferCapacity = Integer.parseInt((String) ht.get(ConfigTagConstant.PriorityThreadBufferCapacity_TAG));
        AppConstant.PriorityThreadBufferIncrement = Integer.parseInt((String) ht.get(ConfigTagConstant.PriorityThreadBufferIncrement_TAG));
        AppConstant.ReadThreadPriorityAdd = Thread.NORM_PRIORITY + Integer.parseInt((String) ht.get(ConfigTagConstant.ReadThreadPriorityAdd_TAG));
        //ApplicationConstant.GetMessageFrequency = Integer.parseInt((String) commuConfig.get("GetMessageFrequency"));
        DateHelper.screenPrintForEx("***线程池配置***");
        DateHelper.screenPrintForEx(ht);
        
        //获得连接池监听线程配置属性
        ht = (Hashtable) commuConfig.get(ConfigTagConstant.ConnectionPoolListenerThread_TAG);
        AppConstant.CPListenerThreadSleepTime = Integer.parseInt((String) ht.get(ConfigTagConstant.CPListenerThreadSleepTime_TAG));
        AppConstant.TestSqlForData = (String) ht.get(ConfigTagConstant.TestSqlForData_TAG);

        AppConstant.SqlMsgHandleSleepTime = Integer.parseInt((String) ht.get(ConfigTagConstant.SqlMsghandleSleepTime_TAG));
        DateHelper.screenPrintForEx("***连接池监听线程配置***");
        DateHelper.screenPrintForEx(ht);

    }

    /*
     * 备份日志文件到BackUpLog备份路径下，备份日志文件名如：EsCommu.logyyyy-MM-dd HH:mm:ss.log
     */
    public void initLogFile(Hashtable commuConfig) throws ConfigFileException {
        String value = (String) commuConfig.get(ConfigTagConstant.BackUpLog_TAG);
        if (value == null || value.length() == 0) {
            throw new ConfigFileException("需配置Tag <" + ConfigTagConstant.BackUpLog_TAG + "> 日志备份文件!");
        } else {
            this.checkDir(value);
            String oldLog = AppConstant.LogFileName+DateHelper.dateToYYYYMMDDHHMMSS(new Date());
            File oldLogFile = new File(value, oldLog);
            File path = new File(AppConstant.LogFilePath);
            DateHelper.screenPrintForEx("日志目录:" + path);
            File logFile = new File(path, AppConstant.LogFileName);
            if (logFile.exists()) {
                logFile.renameTo(oldLogFile);
                DateHelper.screenPrintForEx("日志备份");
            }
        }
        
        value = (String) commuConfig.get(ConfigTagConstant.IntervalThreadLogLevel_TAG);
        if (value != null && value.length() != 0) {
            AppConstant.IntervalThreadLogLevel = Integer.parseInt(value);
            DateHelper.screenPrintForEx("设置日志级别时间间隔:" + AppConstant.IntervalThreadLogLevel);
        }
        
        //init log4j
        DOMConfigurator.configureAndWatch(AppConstant.LOGFILE);
        DateHelper.screenPrintForEx("日志配置设置成功");
    }

    public void initFromConnectionPool(Hashtable commuConfig) throws ConfigFileException {
        try {
            Hashtable ht = (Hashtable) commuConfig.get(ConfigTagConstant.DataConnectionPool_TAG);
            AppConstant.DATA_DBCPHELPER = new DbcpHelper((String) ht.get(
                    ConfigTagConstant.Driver_TAG), (String) ht.get(ConfigTagConstant.URL_TAG),
                    (String) ht.get(ConfigTagConstant.User_TAG), (String) ht.get(ConfigTagConstant.Password_TAG),
                    Integer.parseInt((String) ht.get(ConfigTagConstant.MaxActive_TAG)),
                    Integer.parseInt((String) ht.get(ConfigTagConstant.MaxIdle_TAG)),
                    Integer.parseInt((String) ht.get(ConfigTagConstant.MaxWait_TAG)));
            DateHelper.screenPrintForEx("***连接池配置****");
            DateHelper.screenPrintForEx(ht);
            DateHelper.screenPrintForEx("数据连接池DataConnectionPool创建!");
        } catch (ClassNotFoundException ex) {
            throw new ConfigFileException(ex.getMessage());
        }

    }

    private void initDBForBcpConfig() throws ConfigFileException {
        BcpDao bcpDao = new BcpDao();
        try {
            AppConstant.importConfig = bcpDao.getImportConfig();
        } catch (Exception ex) {
            throw new ConfigFileException("获取BCP配置错误!");
        }

    }

    public void initDbData() throws ConfigFileException, Exception {
        InitiDao dao = new InitiDao();

        AppConstant.prioritys = dao.getPriorities();
        AppConstant.devIps = dao.getDevices();
        CommuLogLevelThread th = new CommuLogLevelThread();
        th.setCurrentLogLevel();
        DateHelper.screenPrintForEx("***消息处理优先级配置****");
        DateHelper.screenPrintForEx(AppConstant.prioritys);
        DateHelper.screenPrintForEx("***ES设备配置****");
        DateHelper.screenPrintForEx(AppConstant.devIps);
        this.initDBForBcpConfig();
        DateHelper.screenPrintForEx("***车站配置****");
        AppConstant.stations = dao.getStations();
        DateHelper.screenPrintForEx("***线路配置****");
        AppConstant.lines = dao.getLines();
        DateHelper.screenPrintForEx("***卡类配置****");
        AppConstant.cards = dao.getCardSubType();

    }

    /*
     * 初始化SOCKET
     */
    public void initSocket(Hashtable commuConfig) throws ConfigFileException {
        String value = (String) commuConfig.get(ConfigTagConstant.ReadOneMessageTimeOut_TAG);
        if (value != null && value.length() != 0) {
            AppConstant.ReadOneMessageTimeOut = Integer.parseInt(value);
            DateHelper.screenPrintForEx("socket超时时间:" + AppConstant.ReadOneMessageTimeOut);
        }
        value = (String) commuConfig.get(ConfigTagConstant.SocketPort_TAG);
        if (value != null) { //ftp password can be ""
            AppConstant.Port = Integer.parseInt(value);
            DateHelper.screenPrintForEx("socket通讯端口:" + AppConstant.Port);
        }
    }

    public void initAudit(Hashtable commuConfig) throws ConfigFileException {
        String value = (String) commuConfig.get(ConfigTagConstant.AuditFileNextMakeTime_TAG);
        if (value != null && value.length() != 0) {
            AppConstant.AuditFileNextMakeTime = value;
            DateHelper.screenPrintForEx("设置审计文件生成时间:" + AppConstant.AuditFileNextMakeTime);
        }
        value = (String) commuConfig.get(ConfigTagConstant.AuditFileFindInterval_TAG);
        if (value != null) { //ftp password can be ""
            AppConstant.AuditFileFindInterval = Integer.parseInt(value);
            DateHelper.screenPrintForEx("设置审计文件查找间隔:" + AppConstant.AuditFileFindInterval);
        }
        value = (String) commuConfig.get(ConfigTagConstant.AuditFileMakeDir_TAG);
        if (value == null || value.length() == 0) {
            throw new ConfigFileException("Tag <" + ConfigTagConstant.AuditFileMakeDir_TAG
                    + "> 需在配置文件中设置!");
        }
        this.checkDir(value);
        AppConstant.AuditFileMakeDir = value;
        DateHelper.screenPrintForEx("设置审计生成路径:" + AppConstant.AuditFileMakeDir);
    }
    
    public void initPhyLogic(Hashtable commuConfig) throws ConfigFileException {
        String value = (String) commuConfig.get(ConfigTagConstant.PhyLogicFileNextMakeTime_TAG);
        if (value != null && value.length() != 0) {
            AppConstant.PhysicsLogicNextMakeTime = value;
            DateHelper.screenPrintForEx("设置物理卡号与逻辑卡号对照文件生成时间:" + AppConstant.PhysicsLogicNextMakeTime);
        }
        value = (String) commuConfig.get(ConfigTagConstant.PhyLogicFileFindInterval_TAG);
        if (value != null) { //ftp password can be ""
            AppConstant.PhyLogicFileFindInterval = Integer.parseInt(value);
            DateHelper.screenPrintForEx("设置物理卡号与逻辑卡号对照文件查找间隔:" + AppConstant.PhyLogicFileFindInterval);
        }
        value = (String) commuConfig.get(ConfigTagConstant.PhyLogicFileMakeDir_TAG);
        if (value == null || value.length() == 0) {
            throw new ConfigFileException("Tag <" + ConfigTagConstant.PhyLogicFileFindInterval_TAG
                    + "> 需在配置文件中设置!");
        }
        this.checkDir(value);
        AppConstant.PhyLogicFileMakeDir = value;
        DateHelper.screenPrintForEx("设置物理卡号与逻辑卡号对照生成路径:" + AppConstant.PhyLogicFileMakeDir);
    }

    public void initDisParam(Hashtable commuConfig) throws ConfigFileException {
        String value = (String) commuConfig.get(ConfigTagConstant.ParmDstrbPath_TAG);
        if (value == null || value.length() == 0) {
            throw new ConfigFileException("Tag <" + ConfigTagConstant.ParmDstrbPath_TAG
                    + "> 需在配置文件中设置!");
        }
        this.checkDir(value);
        AppConstant.ParmDstrbPath = value;
        DateHelper.screenPrintForEx("设置参数生成路径:" + AppConstant.ParmDstrbPath);
    }

    public void initFtp(Hashtable commuConfig) throws ConfigFileException {
        //Ftp config
        String value = (String) commuConfig.get(ConfigTagConstant.FtpUserName_TAG);
        if (value != null && value.length() != 0) {
            AppConstant.FtpUserName = value;
            DateHelper.screenPrintForEx("设置FTP账户:" + AppConstant.FtpUserName);
        }

        value = (String) commuConfig.get(ConfigTagConstant.FtpUserPassword_TAG);
        if (value != null) { //ftp password can be ""
            AppConstant.FtpUserPassword = value;
            DateHelper.screenPrintForEx("设置FTP密码:" + AppConstant.FtpUserPassword);
        }

        value = (String) commuConfig.get(ConfigTagConstant.FtpTimeout_TAG);
        if (value != null) { //ftp 超时时间
            AppConstant.FtpTimeout = new Integer(value).intValue();
            DateHelper.screenPrintForEx("设置FTP超时时间:" + AppConstant.FtpTimeout);
        }

        value = (String) commuConfig.get(ConfigTagConstant.FtpSocketTimeout_TAG);
        if (value != null) { //ftp socket超时时间
            AppConstant.FtpSocketTimeout = new Integer(value).intValue();
            DateHelper.screenPrintForEx("设置FTP的SOCKET超时时间:" + AppConstant.FtpSocketTimeout);
        }

        value = (String) commuConfig.get(ConfigTagConstant.FtpRetryWaiting_TAG);
        if (value != null) { //ftp 重试等待时间
            AppConstant.FtpRetryWaiting = new Integer(value).intValue();
            DateHelper.screenPrintForEx("设置FTP重试等待时间:" + AppConstant.FtpRetryWaiting);
        }

        value = (String) commuConfig.get(ConfigTagConstant.FtpRetryTimes_TAG);
        if (value != null) { //ftp 重试次数
            AppConstant.FtpRetryTime = new Integer(value).intValue();
            DateHelper.screenPrintForEx("设置FTP重试次数:" + AppConstant.FtpRetryTime);
        }

        value = (String) commuConfig.get(ConfigTagConstant.FtpLocalDir_TAG);
        if (value == null || value.length() == 0) {
            throw new ConfigFileException("Tag <" + ConfigTagConstant.FtpLocalDir_TAG
                    + "> 需在配置文件中设置!");
        }
        this.checkDir(value);
        AppConstant.FtpLocalDir = value;
        DateHelper.screenPrintForEx("设置FTP本地目录:" + AppConstant.FtpLocalDir);

        value = (String) commuConfig.get(ConfigTagConstant.FtpLocalDirBcp_TAG);
        if (value == null || value.length() == 0) {
            throw new ConfigFileException("Tag <" + ConfigTagConstant.FtpLocalDirBcp_TAG
                    + "> 需在配置文件中设置!");
        }
        this.checkDir(value);
        AppConstant.FtpLocalDirBcp = value;
        DateHelper.screenPrintForEx("设置BCP文件本地目录:" + AppConstant.FtpLocalDirBcp);

        value = (String) commuConfig.get(ConfigTagConstant.FtpLocalDirError_TAG);
        if (value == null || value.length() == 0) {
            throw new ConfigFileException("Tag <" + ConfigTagConstant.FtpLocalDirError_TAG
                    + "> 需在配置文件中设置!");
        }
        this.checkDir(value);
        AppConstant.FtpLocalDirError = value;
        DateHelper.screenPrintForEx("设置错误文件本地目录:" + AppConstant.FtpLocalDirError);


        // Initialize ftp paths
        AppConstant.FTP_PATHS = (Hashtable) commuConfig.get(
                ConfigTagConstant.FtpPaths_TAG);
        DateHelper.screenPrintForEx("***FTP相对路径配置***");
        DateHelper.screenPrintForEx(AppConstant.FTP_PATHS);

        value = (String) commuConfig.get(ConfigTagConstant.IntervalThreadFindFile_TAG);
        if (value != null) { //ftp password can be ""
            AppConstant.IntervalThreadFindFile = Integer.parseInt(value);
            DateHelper.screenPrintForEx("设置文件通知时间间隔:" + AppConstant.IntervalThreadFindFile);
        }
    }

    private void checkDir(String dir) {
        File localDir = new File(dir);
        if (!localDir.exists()) {
            localDir.mkdirs();
            DateHelper.screenPrintForEx("创建本地目录:" + dir);

        }

    }

    public void initApp(Hashtable commuConfig) {
        
        //控制台输出
        AppConstant.ConsolePrint = new Boolean(((String) commuConfig.get("ConsolePrint"))).booleanValue();
        DateHelper.screenPrintForEx("控制台输出控制设置:" + AppConstant.ConsolePrint);
        
        // Initialize messages
        AppConstant.MESSAGE_CLASSES = (Hashtable) commuConfig.get(
                ConfigTagConstant.Messages_TAG);
        DateHelper.screenPrintForEx("***处理消息类配置***");
        DateHelper.screenPrintForEx(AppConstant.MESSAGE_CLASSES);
    }
}
