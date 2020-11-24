package com.goldsign.commu.commu.util;

import com.goldsign.commu.commu.env.BaseConstant;
import com.goldsign.commu.commu.env.ConfigConstant;
import com.goldsign.commu.commu.exception.ConfigException;
import java.io.File;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class InitialUtil {
    
    private static Logger logger = Logger.getLogger(InitialUtil.class.getName());
    
    public void initThreadPool(Hashtable commuConfig) {
        
        //获得线程池的配置属性
        Hashtable ht = (Hashtable) commuConfig.get(ConfigConstant.CommunicationThreadPool_TAG);
        BaseConstant.MaxThreadNumber = Integer.parseInt((String) ht.get(ConfigConstant.MaxThreadNumber_TAG));
        BaseConstant.MaxSearchNum = Integer.parseInt((String) ht.get(ConfigConstant.MaxSearchNum_TAG));
        BaseConstant.ThreadSleepTime = Integer.parseInt((String) ht.get(ConfigConstant.ThreadSleepTime_TAG));
        BaseConstant.ThreadPriority = Thread.NORM_PRIORITY + Integer.parseInt((String) ht.get(ConfigConstant.ThreadPriorityAdd_TAG));
        BaseConstant.ThreadBufferCapacity = Integer.parseInt((String) ht.get(ConfigConstant.ThreadBufferCapacity_TAG));
        BaseConstant.ThreadBufferIncrement = Integer.parseInt((String) ht.get(ConfigConstant.ThreadBufferIncrement_TAG));
        BaseConstant.PriorityThreadBufferCapacity = Integer.parseInt((String) ht.get(ConfigConstant.PriorityThreadBufferCapacity_TAG));
        BaseConstant.PriorityThreadBufferIncrement = Integer.parseInt((String) ht.get(ConfigConstant.PriorityThreadBufferIncrement_TAG));
        BaseConstant.ReadThreadPriorityAdd = Thread.NORM_PRIORITY + Integer.parseInt((String) ht.get(ConfigConstant.ReadThreadPriorityAdd_TAG));
        logger.info("***线程池配置***");
        DateHelper.screenPrintForEx(ht);
        
        //获得连接池监听线程配置属性
        ht = (Hashtable) commuConfig.get(ConfigConstant.ConnectionPoolListenerThread_TAG);
        BaseConstant.CPListenerThreadSleepTime = Integer.parseInt((String) ht.get(ConfigConstant.CPListenerThreadSleepTime_TAG));
        BaseConstant.TestSqlForData = (String) ht.get(ConfigConstant.TestSqlForData_TAG);

        logger.info("***连接池监听线程配置***");
        DateHelper.screenPrintForEx(ht);

    }

    /*
     * 备份日志文件到BackUpLog备份路径下，备份日志文件名如：Commu.logyyyy-MM-dd HH:mm:ss.log
     */
    public void initLogFile(Hashtable commuConfig) throws ConfigException {
        
        //init log4j
        DOMConfigurator.configureAndWatch(BaseConstant.LOGFILE);
        logger.info("日志配置设置成功");
    }

    public void initConnectionPool(Hashtable commuConfig) throws ConfigException {
        try {
            Hashtable ht = (Hashtable) commuConfig.get(ConfigConstant.DataConnectionPool_TAG);
            BaseConstant.DATA_DBCPHELPER = new DbcpHelper((String) ht.get(
                    "Driver"), (String) ht.get("URL"),
                    (String) ht.get("User"), (String) ht.get("Password"),
                    Integer.parseInt((String) ht.get("MaxActive")),
                    Integer.parseInt((String) ht.get("MaxIdle")),
                    Integer.parseInt((String) ht.get("MaxWait")));
            logger.info("***连接池配置****");
            DateHelper.screenPrintForEx(ht);
            logger.info("数据连接池DataConnectionPool创建!");
        } catch (ClassNotFoundException ex) {
            throw new ConfigException(ex.getMessage());
        }
    }

    /*
     * 初始化SOCKET
     */
    public void initSocket(Hashtable commuConfig) throws ConfigException {
        
        String value = (String) commuConfig.get(ConfigConstant.ClientSocketTimeOut_TAG);
        if (value != null && value.length() != 0) {
            BaseConstant.ClientSocketTimeOut = Integer.parseInt(value);
            logger.info("客户端socket超时断开时间:" + BaseConstant.ClientSocketTimeOut);
        }
        value = (String) commuConfig.get(ConfigConstant.GetMessageFrequency_TAG);
        if (value != null && value.length() != 0) {
            BaseConstant.GetMessageFrequency = Integer.parseInt(value);
            logger.info("读消息频率:" + BaseConstant.GetMessageFrequency);
        }
        value = (String) commuConfig.get(ConfigConstant.WriteMessageFrequency_TAG);
        if (value != null && value.length() != 0) {
            BaseConstant.WriteMessageFrequency = Integer.parseInt(value);
            logger.info("写消息频率:" + BaseConstant.WriteMessageFrequency);
        }
        value = (String) commuConfig.get(ConfigConstant.SocketPort_TAG);
        if (value != null) { //ftp password can be ""
            BaseConstant.SocketPort = Integer.parseInt(value);
            logger.info("socket通讯端口:" + BaseConstant.SocketPort);
        }
    }

    public void initAppMessage(Hashtable commuConfig) {
        // Initialize messages
        BaseConstant.MESSAGE_CLASSES = (Hashtable) commuConfig.get(ConfigConstant.Messages_TAG);
        logger.info("***处理消息类配置***");
        DateHelper.screenPrintForEx(BaseConstant.MESSAGE_CLASSES);
    }
    
    public void initIpConfigs(Hashtable commuConfig){
    
        // Initialize IpConfgis
        BaseConstant.ipConfigs = (Vector) commuConfig.get(ConfigConstant.IpConfigs_TAG);
        logger.info("***IpConfigs配置***");
        DateHelper.screenPrintForEx(BaseConstant.ipConfigs);
    }
    
    private void checkDir(String dir) {
        File localDir = new File(dir);
        if (!localDir.exists()) {
            localDir.mkdirs();
            DateHelper.screenPrintForEx("创建本地目录:" + dir);
        }

    }
}
