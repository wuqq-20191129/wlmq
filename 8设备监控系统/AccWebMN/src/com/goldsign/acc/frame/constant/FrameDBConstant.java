/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.constant;

import com.goldsign.acc.systemmonitor.vo.SynControl;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author zhongzq
 */
public class FrameDBConstant {

    /**
     * 查询命令
     */
    public static final String COMMAND_QUERY = "query";
    /**
     * 时间同步 行信息 eg:0:10.99.1.11  1:10.99.1.12    2:+1.001579284  3:2018-05-11    4:00:00:09
     */
    /**
     * Index--时钟源
     */
    public static final int INDEX_SYN_IP_SOURCE = 0;
    /**
     * Index--服务器ip
     */
    public static final int INDEX_SYN_SERVER_IP = 1;
    /**
     * index--差异时间
     */
    public static final int INDEX_DIFF_TIME = 2;
    /**
     * Index--同步日期
     */
    public static final int INDEX_SYN_DATE = 3;
    /**
     * Index--同步时间
     */
    public static final int INDEX_SYN_TIME = 4;



    /**
     * 时钟同步配置
     * 最大的同步差异时间 单位:秒
     */
    public static int SYN_MAX_DIFF_INTERVAL = 10;
    /**
     * 连接超时 单位毫秒
     */
    public static int FTP_SOCKET_TIME_OUT = 5000;
    /**
     * 重试的时间间隔 单位毫秒
     */
    public static int FTP_RETRY_WAITING_INTERVAL = 1000;
    /**
     * 重试登录次数
     */
    public static int FTP_RETRY_LOGIN_TIME = 3;
    /**
     * 本地目录
     */
    public static String FTP_LOCAL_DIRECTORY = "";
    /**
     * 临时文件前缀
     */
//    public static String FTP_TEMP_FILE_PREFIX = "";
    /**
     * 线程查找时间间隔 单位毫秒
     */
    public static int FTP_THREAD_SLEEP_INTERVAL = 300000;
    /**
     * 远程工作目录
     */
    public static String FTP_REMOTE_DIRECTORY = "";
    /**
     * 远程服务器
     */
    public static String FTP_SERVER_IP = "";
    /**
     * 远程服务器用户
     */
    public static String FTP_USERNAME = "";
    /**
     * 远程服务器密码
     */
    public static String FTP_PASSWORD = "";
    /**
     * 命令开始符前缀
     */
    public static String FILE_LINE_COMMAND_START = "##command_start";
    /**
     * 命令结束符前缀
     */
    public static String FILE_LINE_COMMAND_END = "##command_end";
    /**
     * 命令分隔符
     */
    public static String FILE_LINE_COMMAND_SEPARATOR = "_";
    /**
     * 命令处理类前缀
     */
    public static String FILE_HANDLER_CLASS_PREFIX = "com.goldsign.acc.systemmonitor.handler.HandlerCommand";
    /**
     * 命令分析的时间间隔 单位毫秒
     */
    public static int FILE_PARSE_INTERVAL = 600000;
    /**
     * 命令分析等候FTP完成的轮询时间间隔 单位毫秒
     */
    public static int FILE_THREAD_SLEEP_INTERVAL = 5000;
    /**
     * 文件名分隔符
     */
    public static String FILE_NAME_SEPARATOR = "_";
    /**
     * ACC与LC通信情况含ip地址判断关键信息
     */
    public static String COMMAND_FIND_COMMU_LC_KEY_IP = ".";
    /**
     * ACC与LC通信情状态判断关键信息
     */
    public static String COMMAND_FIND_COMMU_LC_KEY_STATUS = "alive";
    /**
     * 硬盘行判断
     */
    public static String COMMAND_FIND_DISK_INFO_LINE_KEY = "disk_state";
    /**
     * 硬盘镜像行判断
     */
    public static String COMMAND_FIND_DISK_INFO_MIRROR_LINE_KEY = "disk_mirror_state";
    /**
     * 硬盘状态
     */
    public static String COMMAND_FIND_DISK_STATUS_NORMAL_KEY = "good";
    /**
     * 集群行有效信息可能关键集合
     */
    public static String[] COMMAND_FIND_CLUSTER_ALL_KEYS = {"resource:"};
    /**
     * 集群行有效信息判断关键字
     */
    public static String COMMAND_FIND_CLUSTER_KEY = "resource:";
    /**
     * 集群状态判断
     */
    public static String COMMAND_CLUSTER_KEY_STATUS = "online";
    /**
     * 空间使用率警告值
     */
    public static int COMMAND_FIND_USE_SPACE_WARNING = 85;
    /**
     * 归档日志警告值
     * add by zhongzq 20191021
     */
    public static int RECOVERY_USE_SPACE_WARNING_VALUE = 85;
    /**
     * 挂载点、安装点关键字集合
     */
    public static String[] COMMAND_FIND_DF_KEYS_MOUNTED = {"/usr", "/var", "/tmp", "/var/adm/ras/platform", "/home", "/admin", "/opt", "/var/adm/ras/livedump"};
    //安装点完全匹配
    public static String[] COMMNAD_FIND_DF_KEYS_MOUNTED_COMPLETE = {"/"};
    //文件系统
    public static String[] COMMAND_FIND_DF_KEYS_FILESYSTEM = {"/dev"};
    /**
     * 命令数据库查找关键字
     */
    /**
     * 备份文件大小单位
     */
    public static String COMMAND_FIND_DATABASE_DUMP_KEY = "G";
    /**
     * 数据库ip段
     */
    public static String[] COMMAND_DATABASE_IPS = {"10.99.1.11", "10.99.1.13"};
    /**
     * 字符串--空格
     */
    public static final String COMMAND_SEPARATOR_SPACE = " ";
    /**
     * 字符串--斜杠
     */
    public static final String COMMAND_SEPARATOR_SLASH = "/";
    /**
     * 字符串--百分号
     */
    public static final String COMMAND_SEPARATOR_PERCENT = "%";
    /**
     * 字符串--TAB键
     */
    public static final String COMMAND_SEPARATOR_TAB = "	";
    /**
     * 字符串--左括号
     */
    public static final String COMMAND_SEPARATOR_BRACKET_LEFT = "(";
    /**
     * 字符串--右括号
     */
    public static final String COMMAND_SEPARATOR_BRACKET_RIGHT = ")";
    /**
     * IP的前缀 用于解析文件补全文件来源ip
     */
    public static String IP_PREFIX = "10.99.10.";
    /**
     * hce的ip前缀
     */
    public static String HEC_IP_PREFIX = "10.99.12.";
    /**
     * 状态正常
     */
    public static final String STATUS_NORMAL = "0";
    /**
     * 状态异常
     */
    public static final String STATUS_FAILURE = "1";
    /**
     * 状态警告
     */
    public static final String STATUS_WARNING = "3";
    /**
     * NTP文件名包含字符串 用于判断是否是NTP文件
     */
    public static final String FILE_NTP_INCLUDE = "_ntp";
    /**
     * 对象锁
     */
    public static SynControl control = new SynControl();
    /**
     * 设备状态类型类型 中文对照
     */
    public static final int TYPE_DEVICE_STATUS_NAME = 2;
    /**
     * 时钟源ip
     */
    public static String NTP_SOURCE_IP = "192.168.0.254";
    /**
     * cpu温度行校验
     */
    public static String CPU_DEGREES_ROW_KEY = "CPU";
    /**
     * Cpu温度警告值
     */
    public static int CPU_DEGREES_WARNING_VALUE = 85;
    /**
     * 数据库前缀
     */
    public static final String DB_PREFIX = "W_ACC_MN.";

    /**
     * 归档目录
     */
    public static String ARCHIVING_DIR_PATH = "";
    /**
     * 归档目录保留时间
     */
    public static int ARCHIVING_KEEP_DAYS = 7;
    /**
     * 警告信息类型
     */
    public static String HARDWARE_WARING_KEYS = "Event Logging Disabled#Entity Presence#Interconnect#Drive Slot";
    /**
     * 警告信息类型对应状态
     */
    public static String HARDWARE_WARING_VALUES = "reset/cleared#Present#Connected#Hot Spare";
    /**
     * 服务器名
     */
    public static HashMap SERVER_NAME = new HashMap();
    /**
     * 模块名
     */
    public static HashMap MODULE_NAME = new HashMap();


    public static final String EMAIL_FEATURE_USE_KEY = "EMAIL_FEATURE_USE";
    /**
     * 邮件功能控制
     */
    public static boolean EMAIL_FEATURE_USE = false;

    public static ArrayList STATUS_NAME = new ArrayList();

    public final static String MAIL_SMTP_AUTH_USE = "true";

    public static final String MAIL_SMTP_SSL_ENABLE_USE_KEY = "MAIL_SMTP_SSL_ENABLE_USE";

    public static String MAIL_SMTP_SSL_ENABLE_USE = "false";

    public static final String MAIL_SMTP_PORT_KEY = "MAIL_SMTP_PORT";

    public static String MAIL_SMTP_PORT = "25";

    public static final String MAIL_SMTP_HOST_KEY = "MAIL_SMTP_HOST";

    public static String MAIL_SMTP_HOST = "smtp.163.com";

    public static final String EMAIL_SENDER_ADDRESS_KEY = "EMAIL_SENDER_ADDRESS";

    public static String EMAIL_SENDER_ADDRESS = "wlmqacc@163.com";

    public static final String EMAIL_ACCOUNT_KEY = "EMAIL_ACCOUNT";

    public static String EMAIL_ACCOUNT = "wlmqacc";

    public static final String EMAIL_PASSWORD_KEY = "EMAIL_PASSWORD";

    public static String EMAIL_PASSWORD = "gdsign123WLMQAcc";

    public static String EMAIL_TITLE = "Monitor-Message";

    public static final String EMAIL_EMAIL_KEY = "EMAIL_TITLE";

    //add by zhongzq 20190905
    public static String MONITOR_DATABASE = "dataSourceST";
}
