package com.goldsign.acc.frame.component;

import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.frame.mapper.MtrConfigMapper;
import com.goldsign.acc.frame.mapper.PubFlagMapper;
import com.goldsign.acc.systemmonitor.thread.FileParseThread;
import com.goldsign.acc.systemmonitor.thread.FtpThread;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * date：2018-06-11
 * modifier： zhongziqi
 * change：
 * 1.修改启动方式 定时启动--》容器启动后初始化
 * 2.启动FtpThread以及FlieParsrThread
 * 3.重构initDbConfig代码
 */
@Component
public class InitComponent implements InitializingBean {

    private static final String RETURN_CODE = "out_retResult";
    private static final String RETURN_MESSAGE = "out_msg";

    static Logger logger = Logger.getLogger(InitComponent.class);
    @Autowired
    private MtrConfigMapper mtrConfigMapper;

    @Autowired
    private PubFlagMapper pubFlagMapper;

    @Scheduled(cron = "0 30 23 * * ?")
    private void initDbConfig() throws Exception {
        //FTP配置参数
        initFtpPara();
        //命令处理
        initCommandPara();
        //命令Acc与LC通信查找关键字
        initLcKey();
        //硬盘关键字
        initDiskInfoKey();
        //命令Ha集群查找关键字
        initClusterKey();
        //使用空间警告值 硬盘空间以及数据表空间均用这个
        initUseSpaceWarningPara();
        //归档日志警告值
        initRecoveryUseSpaceWarningPara();
        //命令df查找关键字
        initDiskSpaceKey();
        //数据库查找关键字
        initDatabaseKey();
        //IP的前缀
        initIpPrefix();
        //命令NTP配置
        initNtpPara();
        //cpu温度
        initCpuDegreesPara();
        //归档目录
        initArchivingDir();
        //服务器硬件关键字
        initHardwareKey();
        //add by zhongzq  20190905
        initMonitorDataBase();

        initEmailControl();
        if (FrameDBConstant.EMAIL_FEATURE_USE) {
            initServerName();
            initModuleName();
            initStatusName();
            initEmailPara();
        }
    }

    private void initRecoveryUseSpaceWarningPara() {
        FrameDBConstant.RECOVERY_USE_SPACE_WARNING_VALUE = Integer.valueOf(mtrConfigMapper.getConfigValue("RECOVERY_USE_SPACE_WARNING_VALUE"));
        logger.info("*****使用归档空间警告值关键字******");
        logger.info("RECOVERY_USE_SPACE_WARNING_VALUE=" + FrameDBConstant.RECOVERY_USE_SPACE_WARNING_VALUE);
        logger.info("\n");
    }

    private void initMonitorDataBase() {
        FrameDBConstant.MONITOR_DATABASE = mtrConfigMapper.getConfigValue("MONITOR_DATABASE");
        logger.info("****监控数据库数据源初始化******");
        logger.info("MONITOR_DATABASE=" + FrameDBConstant.MONITOR_DATABASE);

        logger.info("\n");
    }

    private void initEmailControl() {
        try {
            FrameDBConstant.EMAIL_FEATURE_USE = Boolean.valueOf(mtrConfigMapper.getConfigValue(FrameDBConstant.EMAIL_FEATURE_USE_KEY));
        } catch (Exception e) {
            logger.error("异常" + e.getStackTrace());
            FrameDBConstant.EMAIL_FEATURE_USE = false;
        }
        logger.info("***邮箱功能启用******" + FrameDBConstant.EMAIL_FEATURE_USE);
        logger.info("\n");
    }

    private void initEmailPara() {
        logger.info("***邮箱参数初始化开始******");
        try {
            FrameDBConstant.MAIL_SMTP_SSL_ENABLE_USE = mtrConfigMapper.getConfigValue(FrameDBConstant.MAIL_SMTP_SSL_ENABLE_USE_KEY);
            FrameDBConstant.MAIL_SMTP_PORT = mtrConfigMapper.getConfigValue(FrameDBConstant.MAIL_SMTP_PORT_KEY);
            FrameDBConstant.MAIL_SMTP_HOST = mtrConfigMapper.getConfigValue(FrameDBConstant.MAIL_SMTP_HOST_KEY);
            FrameDBConstant.EMAIL_SENDER_ADDRESS = mtrConfigMapper.getConfigValue(FrameDBConstant.EMAIL_SENDER_ADDRESS_KEY);
            FrameDBConstant.EMAIL_ACCOUNT = mtrConfigMapper.getConfigValue(FrameDBConstant.EMAIL_ACCOUNT_KEY);
            FrameDBConstant.EMAIL_PASSWORD = mtrConfigMapper.getConfigValue(FrameDBConstant.EMAIL_PASSWORD_KEY);
            FrameDBConstant.EMAIL_TITLE = mtrConfigMapper.getConfigValue(FrameDBConstant.EMAIL_EMAIL_KEY);
        } catch (Exception e) {
            logger.error("异常" + e.getStackTrace());
        }

        logger.info("FrameDBConstant.MAIL_SMTP_SSL_ENABLE_USE=" + FrameDBConstant.MAIL_SMTP_SSL_ENABLE_USE);
        logger.info("FrameDBConstant.MAIL_SMTP_PORT=" + FrameDBConstant.MAIL_SMTP_PORT);
        logger.info("FrameDBConstant.MAIL_SMTP_HOST=" + FrameDBConstant.MAIL_SMTP_HOST);
        logger.info("FrameDBConstant.EMAIL_SENDER_ADDRESS=" + FrameDBConstant.EMAIL_SENDER_ADDRESS);
        logger.info("FrameDBConstant.EMAIL_ACCOUNT=" + FrameDBConstant.EMAIL_ACCOUNT);
//        logger.info("FrameDBConstant.EMAIL_PASSWORD="+FrameDBConstant.EMAIL_PASSWORD==null?"<null>":"<not null>" );
        logger.info(FrameDBConstant.EMAIL_PASSWORD == null ? "FrameDBConstant.EMAIL_PASSWORD=<null>" : "FrameDBConstant.EMAIL_PASSWORD=<not null>");
        logger.info("FrameDBConstant.EMAIL_TITLE=" + FrameDBConstant.EMAIL_TITLE);
        logger.info("***邮箱参数初始化结束******");
    }

    private void initStatusName() {

        FrameDBConstant.STATUS_NAME = (ArrayList) pubFlagMapper.getCodeByType(FrameDBConstant.TYPE_DEVICE_STATUS_NAME);
        logger.info("***状态名初始化******");
        logger.info("FrameDBConstant.STATUS_NAME");
        logger.info("\n");
    }

    private void initModuleName() {
        ArrayList<Map> list = (ArrayList<Map>) mtrConfigMapper.getModuleMsg();
        HashMap map = convertToMap(list, "CLASS_NAME", "MODULE_NAME");
        FrameDBConstant.MODULE_NAME = map;
        logger.info("****模块名初始化******");
        logger.info("FrameDBConstant.MODULE_NAME");
        logger.info("\n");
    }

    private void initServerName() {
        ArrayList<Map> list = (ArrayList<Map>) mtrConfigMapper.getServerMsg();
        HashMap map = convertToMap(list, "IP", "NAME");
        FrameDBConstant.SERVER_NAME = map;
        logger.info("****服务器名初始化******");
        logger.info("FrameDBConstant.SERVER_NAME");
        logger.info("\n");
    }

    private void initHardwareKey() {
        FrameDBConstant.HARDWARE_WARING_KEYS = mtrConfigMapper.getConfigValue("COMMAND_FIND_HARDWARE_WARING_KEYS");
        FrameDBConstant.HARDWARE_WARING_VALUES = mtrConfigMapper.getConfigValue("COMMAND_FIND_HARDWARE_WARING_VALUES");
        logger.info("****服务器警告关键字初始化******");
        logger.info("HARDWARE_WARING_KEYS=" + FrameDBConstant.HARDWARE_WARING_KEYS);
        logger.info("HARDWARE_WARING_VALUES=" + FrameDBConstant.HARDWARE_WARING_VALUES);
        logger.info("\n");
    }

    private void initArchivingDir() {
        FrameDBConstant.ARCHIVING_DIR_PATH = mtrConfigMapper.getConfigValue("ARCHIVING_DIR_PATH");
        FrameDBConstant.ARCHIVING_KEEP_DAYS = Integer.valueOf(mtrConfigMapper.getConfigValue("ARCHIVING_KEEP_DAYS"));
        logger.info("****归档目录参数初始化******");
        logger.info("ARCHIVING_DIR_PATH=" + FrameDBConstant.ARCHIVING_DIR_PATH);
        logger.info("ARCHIVING_KEEP_DAYS=" + FrameDBConstant.ARCHIVING_KEEP_DAYS);
        logger.info("\n");
    }

    @Scheduled(cron = "0 30 23 * * ?")
    private void autoClearHis() throws Exception {
        HashMap<String, Object> hmParm = new HashMap(3);
        String retCode = "0";
        String retMsg = "";
        String procedure = FrameDBConstant.DB_PREFIX + "w_up_mtr_his_clear";
        String sql = procedure + "(#{out_retResult,mode=OUT,jdbcType=INTEGER},#{out_msg,mode=OUT,jdbcType=VARCHAR})";
        hmParm.put(RETURN_CODE, "");
        hmParm.put(RETURN_MESSAGE, "");
        hmParm.put("sql", sql);
        mtrConfigMapper.execProcedure(hmParm);
        retCode = hmParm.get(RETURN_CODE).toString();
        Object outMsg = hmParm.get(RETURN_MESSAGE);
        if (null != outMsg) {
            retMsg = outMsg.toString();
        }
        logger.info("执行" + procedure + "，返回码：" + retCode + ",返回信息：" + retMsg);
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 2)
    private void autoDeleteOverdueArchivingDir() throws Exception {
        File archivingDir = new File(FrameDBConstant.ARCHIVING_DIR_PATH);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - FrameDBConstant.ARCHIVING_KEEP_DAYS);
        Date deleteDay = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String deleteDayStr = simpleDateFormat.format(deleteDay);
        if (archivingDir.isDirectory()) {
            String[] dirs = archivingDir.list();
            for (int i = 0; i < dirs.length; i++) {
                if (isNeedDelete(dirs[i], deleteDayStr)) {
                    File deleteDir = new File(FrameDBConstant.ARCHIVING_DIR_PATH + FrameDBConstant.COMMAND_SEPARATOR_SLASH + dirs[i]);
                    if (deleteDir.list().length > 0) {
                        for (File f : deleteDir.listFiles()) {
                            f.delete();
                        }
                    }
                    deleteDir.delete();
                    logger.info("===========删除目录=======" + dirs[i]);
                }
            }

        }
    }

    private boolean isNeedDelete(String judeDayStr, String deleteDayStr) {
        if (Long.parseLong(judeDayStr) <= Long.parseLong(deleteDayStr)) {
            return true;
        }
        return false;
    }

    private void initCpuDegreesPara() {
        FrameDBConstant.CPU_DEGREES_ROW_KEY = mtrConfigMapper.getConfigValue("CPU_DEGREES_ROW_KEY");
        FrameDBConstant.CPU_DEGREES_WARNING_VALUE = Integer.valueOf(mtrConfigMapper.getConfigValue("CPU_DEGREES_WARNING_VALUE"));
        logger.info("****cpu温度参数初始化******");
        logger.info("CPU_DEGREES_ROW_KEY=" + FrameDBConstant.COMMAND_FIND_DATABASE_DUMP_KEY);
        logger.info("CPU_DEGREES_WARNING_VALUE=" + FrameDBConstant.CPU_DEGREES_WARNING_VALUE);
        logger.info("\n");
    }

    private void initDatabaseKey() {
        FrameDBConstant.COMMAND_FIND_DATABASE_DUMP_KEY = mtrConfigMapper.getConfigValue("COMMAND_FIND_DATABASE_DUMP_KEY");
        FrameDBConstant.COMMAND_DATABASE_IPS = getConfigStringArrayValue(mtrConfigMapper.getConfigValue("COMMAND_DATABASE_IPS"));
        logger.info("****数据库查找关键字******");
        logger.info("COMMAND_FIND_DATABASE_DUMP_KEY=" + FrameDBConstant.COMMAND_FIND_DATABASE_DUMP_KEY);
        logger.info("COMMAND_DATABASE_IPS=" + this.getArrayValue(FrameDBConstant.COMMAND_DATABASE_IPS));
        logger.info("\n");
    }

    private void initDiskInfoKey() {
        FrameDBConstant.COMMAND_FIND_DISK_INFO_LINE_KEY = mtrConfigMapper.getConfigValue("COMMAND_FIND_DISK_INFO_LINE_KEY");
        FrameDBConstant.COMMAND_FIND_DISK_INFO_MIRROR_LINE_KEY = mtrConfigMapper.getConfigValue("COMMAND_FIND_DISK_INFO_MIRROR_LINE_KEY");
        FrameDBConstant.COMMAND_FIND_DISK_STATUS_NORMAL_KEY = mtrConfigMapper.getConfigValue("COMMAND_FIND_DISK_STATUS_NORMAL_KEY");

        logger.info("*****命令硬盘信息查找关键字******");
        logger.info("COMMAND_FIND_DISK_INFO_LINE_KEY=" + FrameDBConstant.COMMAND_FIND_DISK_INFO_LINE_KEY);
        logger.info("COMMAND_FIND_DISK_INFO_MIRROR_LINE_KEY=" + FrameDBConstant.COMMAND_FIND_DISK_INFO_MIRROR_LINE_KEY);
        logger.info("COMMAND_FIND_DISK_STATUS_NORMAL_KEY=" + FrameDBConstant.COMMAND_FIND_DISK_STATUS_NORMAL_KEY);
        logger.info("\n");
    }

    private void initClusterKey() {
        FrameDBConstant.COMMAND_FIND_CLUSTER_ALL_KEYS = getConfigStringArrayValue(mtrConfigMapper.getConfigValue("COMMAND_FIND_CLUSTER_ALL_KEYS"));
        FrameDBConstant.COMMAND_FIND_CLUSTER_KEY = mtrConfigMapper.getConfigValue("COMMAND_FIND_CLUSTER_KEY");
        FrameDBConstant.COMMAND_CLUSTER_KEY_STATUS = mtrConfigMapper.getConfigValue("COMMAND_CLUSTER_KEY_STATUS");
        logger.info("*****命令Ha集群查找关键字******");
        logger.info("COMMAND_FIND_CLUSTER_ALL_KEYS=" + this.getArrayValue(FrameDBConstant.COMMAND_FIND_CLUSTER_ALL_KEYS));
        logger.info("COMMAND_FIND_CLUSTER_KEY=" + FrameDBConstant.COMMAND_FIND_CLUSTER_KEY);
        logger.info("COMMAND_CLUSTER_KEY_STATUS=" + FrameDBConstant.COMMAND_CLUSTER_KEY_STATUS);
        logger.info("\n");
    }

    private void initDiskSpaceKey() {
        //安装点
        FrameDBConstant.COMMAND_FIND_DF_KEYS_MOUNTED = getConfigStringArrayValue(
                mtrConfigMapper.getConfigValue("COMMAND_FIND_DF_KEYS_MOUNTED"));
        //安装点完全匹配
        FrameDBConstant.COMMNAD_FIND_DF_KEYS_MOUNTED_COMPLETE = getConfigStringArrayValue(
                mtrConfigMapper.getConfigValue("COMMNAD_FIND_DF_KEYS_MOUNTED_COMPLETE"));
        //文件系统
        FrameDBConstant.COMMAND_FIND_DF_KEYS_FILESYSTEM = getConfigStringArrayValue(
                mtrConfigMapper.getConfigValue("COMMAND_FIND_DF_KEYS_FILESYSTEM"));
        logger.info("*****命令df查找关键字******");
        logger.info("COMMAND_FIND_DF_KEYS_MOUNTED=" + this.getArrayValue(FrameDBConstant.COMMAND_FIND_DF_KEYS_MOUNTED));
        logger.info("COMMNAD_FIND_DF_KEYS_MOUNTED_COMPLETE=" + this.getArrayValue(FrameDBConstant.COMMNAD_FIND_DF_KEYS_MOUNTED_COMPLETE));
        logger.info("COMMAND_FIND_DF_KEYS_FILESYSTEM=" + this.getArrayValue(FrameDBConstant.COMMAND_FIND_DF_KEYS_FILESYSTEM));
        logger.info("\n");
    }

    private void initUseSpaceWarningPara() {
        FrameDBConstant.COMMAND_FIND_USE_SPACE_WARNING = Integer.valueOf(mtrConfigMapper.getConfigValue("COMMAND_FIND_USE_SPACE_WARNING"));
        logger.info("*****使用空间警告值关键字******");
        logger.info("COMMAND_FIND_USE_SPACE_WARNING=" + FrameDBConstant.COMMAND_FIND_USE_SPACE_WARNING);
        logger.info("\n");
    }

    private void initIpPrefix() {
        FrameDBConstant.IP_PREFIX = mtrConfigMapper.getConfigValue("IP_PREFIX");
        FrameDBConstant.HEC_IP_PREFIX = mtrConfigMapper.getConfigValue("HEC_IP_PREFIX");
        logger.info("*****IP的前缀******");
        logger.info("IP_PREFIX=" + FrameDBConstant.IP_PREFIX);
        logger.info("HEC_IP_PREFIX=" + FrameDBConstant.HEC_IP_PREFIX);
        logger.info("\n");
    }

    private void initNtpPara() {
        //最大的同步差异时间
        FrameDBConstant.SYN_MAX_DIFF_INTERVAL = Integer.valueOf(mtrConfigMapper.getConfigValue("SYN_MAX_DIFF_INTERVAL"));
        //时钟源ip
        FrameDBConstant.NTP_SOURCE_IP = mtrConfigMapper.getConfigValue("NTP_SOURCE_IP");
        logger.info("*****NTP配置******");
        logger.info("SYN_MAX_DIFF_INTERVAL=" + FrameDBConstant.SYN_MAX_DIFF_INTERVAL);
        logger.info("NTP_SOURCE_IP=" + FrameDBConstant.NTP_SOURCE_IP);
        logger.info("\n");
    }

    private void initLcKey() {
        FrameDBConstant.COMMAND_FIND_COMMU_LC_KEY_IP = mtrConfigMapper.getConfigValue("COMMAND_FIND_COMMU_LC_KEY_IP");
        FrameDBConstant.COMMAND_FIND_COMMU_LC_KEY_STATUS = mtrConfigMapper.getConfigValue("COMMAND_FIND_COMMU_LC_KEY_STATUS");
        logger.info("*****命令commlcc查找关键字******");
        logger.info("COMMAND_FIND_COMMU_LC_KEY_IP=" + FrameDBConstant.COMMAND_FIND_COMMU_LC_KEY_IP);
        logger.info("COMMAND_FIND_COMMU_LC_KEY_STATUS=" + FrameDBConstant.COMMAND_FIND_COMMU_LC_KEY_STATUS);
        logger.info("\n");
    }

    private void initCommandPara() {
        //命令开始符前缀
        FrameDBConstant.FILE_LINE_COMMAND_START = mtrConfigMapper.getConfigValue("FILE_LINE_COMMAND_START");
        //命令结束符前缀
        FrameDBConstant.FILE_LINE_COMMAND_END = mtrConfigMapper.getConfigValue("FILE_LINE_COMMAND_END");
        //命令分隔符
        FrameDBConstant.FILE_LINE_COMMAND_SEPARATOR = mtrConfigMapper.getConfigValue("FILE_LINE_COMMAND_SEPARATOR");
        //命令处理类前缀
        FrameDBConstant.FILE_HANDLER_CLASS_PREFIX = mtrConfigMapper.getConfigValue("FILE_HANDLER_CLASS_PREFIX");
        //命令分析的时间间隔
        FrameDBConstant.FILE_PARSE_INTERVAL = Integer.valueOf(mtrConfigMapper.getConfigValue("FILE_PARSE_INTERVAL"));
        //命令分析等候FTP完成的轮询时间间隔
        FrameDBConstant.FILE_THREAD_SLEEP_INTERVAL = Integer.valueOf(mtrConfigMapper.getConfigValue("FILE_THREAD_SLEEP_INTERVAL"));
        //文件名分隔符
        FrameDBConstant.FILE_NAME_SEPARATOR = mtrConfigMapper.getConfigValue("FILE_NAME_SEPARATOR");
        logger.info("*****命令处理******");
        logger.info("FILE_LINE_COMMAND_START=" + FrameDBConstant.FILE_LINE_COMMAND_START);
        logger.info("FILE_LINE_COMMAND_END=" + FrameDBConstant.FILE_LINE_COMMAND_END);
        logger.info("FILE_LINE_COMMAND_SEPARATOR=" + FrameDBConstant.FILE_LINE_COMMAND_SEPARATOR);
        logger.info("FILE_HANDLER_CLASS_PREFIX=" + FrameDBConstant.FILE_HANDLER_CLASS_PREFIX);
        logger.info("FILE_PARSE_INTERVAL=" + FrameDBConstant.FILE_PARSE_INTERVAL);
        logger.info("FILE_THREAD_SLEEP_INTERVAL=" + FrameDBConstant.FILE_THREAD_SLEEP_INTERVAL);
        logger.info("FILE_NAME_SEPARATOR=" + FrameDBConstant.FILE_NAME_SEPARATOR);
        logger.info("\n");
    }

    private void initFtpPara() {
        FrameDBConstant.FTP_SOCKET_TIME_OUT = Integer.valueOf(mtrConfigMapper.getConfigValue("FTP_SOCKET_TIME_OUT"));
        FrameDBConstant.FTP_RETRY_WAITING_INTERVAL = Integer.valueOf(mtrConfigMapper.getConfigValue("FTP_RETRY_WAITING_INTERVAL"));
        FrameDBConstant.FTP_RETRY_LOGIN_TIME = Integer.valueOf(mtrConfigMapper.getConfigValue("FTP_RETRY_LOGIN_TIME"));
        FrameDBConstant.FTP_LOCAL_DIRECTORY = mtrConfigMapper.getConfigValue("FTP_LOCAL_DIRECTORY");
//        FrameDBConstant.FTP_TEMP_FILE_PREFIX = mtrConfigMapper.getConfigValue("FTP_TEMP_FILE_PREFIX");
        FrameDBConstant.FTP_THREAD_SLEEP_INTERVAL = Integer.valueOf(mtrConfigMapper.getConfigValue("FTP_THREAD_SLEEP_INTERVAL"));
        FrameDBConstant.FTP_REMOTE_DIRECTORY = mtrConfigMapper.getConfigValue("FTP_REMOTE_DIRECTORY");
        FrameDBConstant.FTP_SERVER_IP = mtrConfigMapper.getConfigValue("FTP_SERVER_IP");
        FrameDBConstant.FTP_USERNAME = mtrConfigMapper.getConfigValue("FTP_USERNAME");
        FrameDBConstant.FTP_PASSWORD = mtrConfigMapper.getConfigValue("FTP_PASSWORD");
        logger.info("*****设置FTP配置参数******");
        logger.info("FTP_SOCKET_TIME_OUT=" + FrameDBConstant.FTP_SOCKET_TIME_OUT);
        logger.info("FTP_RETRY_WAITING_INTERVAL=" + FrameDBConstant.FTP_RETRY_WAITING_INTERVAL);
        logger.info("FTP_RETRY_LOGIN_TIME=" + FrameDBConstant.FTP_RETRY_LOGIN_TIME);
        logger.info("FTP_LOCAL_DIRECTORY=" + FrameDBConstant.FTP_LOCAL_DIRECTORY);
//        logger.info("FTP_TEMP_FILE_PREFIX=" + FrameDBConstant.FTP_TEMP_FILE_PREFIX);
        logger.info("FTP_THREAD_SLEEP_INTERVAL=" + FrameDBConstant.FTP_THREAD_SLEEP_INTERVAL);
        logger.info("FTP_REMOTE_DIRECTORY=" + FrameDBConstant.FTP_REMOTE_DIRECTORY);
        logger.info("FTP_SERVER_IP=" + FrameDBConstant.FTP_SERVER_IP);
        logger.info("FTP_USERNAME=" + FrameDBConstant.FTP_USERNAME);
        logger.info("FTP_PASSWORD=" + FrameDBConstant.FTP_PASSWORD);
        logger.info("\n");
    }

    private void initFtpThread() {
        FtpThread t = new FtpThread();
        t.start();
        logger.info("启动FTP定时线程");

    }

    private void initFileParseThread() {
        FileParseThread t = new FileParseThread();
        t.start();
        logger.info("启动文件分析定时线程");

    }

    private String getArrayValue(String[] arrayValue) {
        String value = "";
        for (int i = 0; i < arrayValue.length; i++) {
            value += arrayValue[i] + ",";
        }
        if (value.length() != 0) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    private String[] getColors(String[] colors) {
        if (colors == null) {
            return colors;
        }
        for (int i = 0; i < colors.length; i++) {
            colors[i] = "#" + colors[i];
        }
        return colors;
    }

    public String[] getConfigStringArrayValue(String value) {
        Vector v = new Vector();
        StringTokenizer st = new StringTokenizer(value, "#");
        String token;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            v.add(token);
        }
        String[] ar = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            ar[i] = (String) v.get(i);
        }
        return ar;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.initDbConfig();
        this.initFtpThread();
        this.initFileParseThread();
    }

    private HashMap convertToMap(ArrayList<Map> list, String mapKey, String mapValue) {
        if (list == null || list.size() == 0) {
            return null;
        }
        HashMap map = new HashMap(list.size());
        for (int i = 0; i < list.size(); i++) {
            HashMap vo = (HashMap) list.get(i);
            String key = (String) vo.get(mapKey);
            String value = (String) vo.get(mapValue);
            map.put(key, value);
        }
        return map;
    }
}
