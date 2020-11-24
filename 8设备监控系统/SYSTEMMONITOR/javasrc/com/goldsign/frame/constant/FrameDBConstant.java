/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.constant;

import com.goldsign.systemmonitor.vo.SynControl;
import java.util.HashMap;

/**
 *
 * @author hejj
 */
public class FrameDBConstant {

    /**
     * The data source reference name for the application main database
     */
    public static String MAIN_DATASOURCE = AppConfig.getJndiPrefix()
            + "jdbc/dbmon";
    
    //public static String MAIN_DATASOURCE_1 = AppConfig.getJndiPrefix()+ "jdbc/tkdb_1";
    //public static String MAIN_DATASOURCE_1_T = "jdbc/tkdb_1";
    public static String MAIN_DATASOURCE_T = AppConfig.getJndiPrefix()
            + "jdbc/dbmon";
    
    //    public static String MAIN_DATASOURCE_T = AppConfig.getJndiPrefix()  + "jdbc/accmonitor";
           
    public static String MAIN_DATASOURCE_MON =  AppConfig.getJndiPrefix()
            + "jdbc/dbmon_1";

    //增加命令
    public static final String COMMAND_ADD = "add";
// 删除命令
    public static final String COMMAND_DELETE = "delete";
// 审核命令
    public static final String COMMAND_AUDIT = "audit";
// 修改命令
    public static final String COMMAND_MODIFY = "modify";
// 克隆命令
    public static final String COMMAND_CLONE = "clone";
// 提交命令
    public static final String COMMAND_SUBMIT = "submit";
// 分配命令
    public static final String COMMAND_DISTRIBUTE = "distribute";
// 查询命令
    public static final String COMMAND_QUERY = "query";
    public static HashMap APP_CONFIGS = new HashMap();
    public static String config_type_ftp = "1";//FTP配置参数
    public static String config_type_linecommand = "2";//命令处理
    public static String config_type_prtdiag6800 = "3";//命令prtdiag6800查找关键字

    public static String config_type_prtdiag880 = "4";//命令prtdiag880查找关键字

    public static String config_type_prtdiag480 = "5";//命令prtdiag480查找关键字

    public static String config_type_commlcc = "6";//命令commlcc查找关键字

    public static String config_type_metastat = "7";//命令metastat查找关键字

    public static String config_type_scstat = "8";//命令scstat查找关键字

    public static String config_type_status = "9";//状态判断关键字
    public static String config_type_df = "10";//命令df查找关键字

    public static String config_type_cisco3350 = "11";//命令cisco3350查找关键字

    public static String config_type_cisco3360 = "12";//命令cisco3360查找关键字

    public static String config_type_ASA5520 = "13";//命令ASA5520查找关键字

    public static String config_type_sybasedump = "14";//命令sybasedump查找关键字

    public static String config_type_sybasetime = "15";//命令sybasetime查找关键字

    public static String config_type_iq = "16";//命令iq查找关键字

    public static String config_type_block = "17";//数据块名称

    public static String config_type_ip = "18";//IP的前缀
    public static String config_type_style_color = "19";//行颜色

    public static String config_type_dbcc = "20";//dbcc查找关键字

    public static String config_type_ntp = "21";//ntp配置
/*
     * 
     * 时钟同步配置
     */
    public static int SynMaxDiffInterval = 900;//最大的同步差异时间
    /*
     * 
     * FTP配置
     */
    public static int FtpTimeout = 5000;//连接超时
    public static int FtpRetryWaiting = 1000;//重试的时间间隔

    public static int FtpRetryTime = 3;//重试次数
    public static String FtpLocalDir = "";//本地基本目录
    public static String FtpTmpFilePrefix = "";//临时文件前缀
    public static int FtpInterval = 300000;//线程查找的时间间隔

    public static String FtpRemoteDir = "";//远程工作目录
    public static String FtpServer = "";//远程服务器

    public static String FtpUser = "";//远程服务器用户

    public static String FtpPassword = "";//远程服务器密码

/*
     * 命令处理
     */
    public static String FileLineCommandStart = "##command_start";//命令开始符前缀
    public static String FileLineCommandEnd = "##command_end";//命令结束符前缀
    public static String FileLineCommandSeperator = "_";//命令分隔符

    public static String FileHandlerClassPrix = "com.goldsign.systemmonitor.handler.HandlerCommand";//命令处理类前缀
    public static int FileParseInterval = 600000;//命令分析的时间间隔

    public static int FileWaitFtpInterval = 5000;//命令分析等候FTP完成的轮询时间间隔

    public static String FileNameSeperator = "_";//文件名分隔符
    /**
     * 命令prtdiag6800查找关键字

     *
     * public static String Command_find_prtdiag6800_key = "No Hardware failures
     * found in System";// public static String
     * Command_find_prtdiag6800_key_start = "Hardware Failures";// public static
     * String Command_find_prtdiag6800_key_end = "HW Revisions";//
     */
    public static String Command_find_prtdiag6800_key = "No Hardware failures found in System";//
    public static String Command_find_hardware = "H";
    public static String Command_find_prtdiag6800_key_start = "IDENTIFIER";//
    public static String Command_find_prtdiag6800_key_end = "HW Revisions";//
    /**
     * 命令prtdiag880查找关键字

     */
    public static String[] Command_find_prtdiag880_keys = {"No failures found in System", "PS0      GOOD", "PS1      GOOD", "PS2      GOOD"};//
    /**
     * 命令prtdiag480查找关键字

     */
    public static String Command_find_prtdiag480_key = "LOCATOR   FAULT    POWER";//
    public static String Command_find_prtdiag480_key_1 = "OFF";//
/*
     * 命令commlcc查找关键字

     */
    public static String Command_find_commlcc_key_ip = ".";
    public static String Command_find_commlcc_key_status = "alive";

    /*
     * 命令metastat查找关键字

     */
    //public static String Command_find_metastat_key = "Okay";open/syncd
    public static String Command_find_metastat_key = "open/syncd";
    /*
     * 命令scstat查找关键字

     */
    public static String[] Command_find_scstat_keys = {"resource:"};//{"ccds01", "chds01", "loms01", "dws01"};
    public static String Command_find_scstat_common = "resource:";
    /*
     * 状态判断关键字
     */
    public static String Command_status_scstat_key = "Online";//命令scstat
    public static int Command_status_df_key = 85;//命令df使用率

    /*
     * 命令df查找关键字

     */
    public static String[] Command_find_df_keys_mounted = {"/usr", "/var", "/tmp", "/var/adm/ras/platform", "/home", "/admin", "/opt", "/var/adm/ras/livedump"};//安装点

    public static String[] Command_find_df_keys_mounted_complete = {"/"};//安装点完全匹配

    public static String[] Command_find_df_keys_filesystem = {"/dev"};//文件系统
/*
     * 命令cisco3350查找关键字

     */
    public static String[] Command_find_cisco3350_keys_device = {"POWER", "FAN", "TEMPERATURE"};
    public static String[] Command_find_cisco3350_keys_ip = {"Connected to"};
    public static String Command_find_cisco3350_key_status = "OK";
    /*
     * 命令cisco3360查找关键字

     */
    public static String[] Command_find_cisco3360_keys_device = {"FAN", "TEMPERATURE"};
    public static String[] Command_find_cisco3360_keys_ip = {"Connected to"};
    public static String Command_find_cisco3360_key_status_1 = "OK";
    public static String Command_find_cisco3360_key_status_2 = "Good";
    public static String Command_find_cisco3360_key = "Sys Pwr";
    /*
     * 命令ASA5520查找关键字

     */
    public static String Command_find_ASA5520_key = "Mod Status";
    public static String Command_find_ASA5520_key_1 = "This host";
    public static String Command_find_ASA5520_key_2 = "Other host";
    public static String Command_find_ASA5520_key_status = "Up";
    public static String[] Command_find_ASA5520_key_redundaney = {"Ifc", "None"};
    public static String[] Command_ASA5520_ips = {"10.90.1.254", "10.90.1.253"};
    public static String[] Command_find_ASA5520_keys_ip = {"Connected to"};
    /*
     * 命令sybasedump查找关键字

     */
    public static String Command_find_sybasedump_key = "G";

    /*
     * 命令sybasetime查找关键字

     */
    //public static String[] Command_sybasetime_ips = {"10.99.1.11", "10.90.1.211"};
    //public static String[] Command_sybasetime_ips = {"10.99.1.28"};
    public static String[] Command_sybasetime_ips = {"10.99.1.15", "10.90.1.211"};
    /*
     * 命令iq查找关键字

     */
    public static String Command_find_iq_key_start = "start";
    public static String Command_find_iq_key_finish = "finished";
    public static String Command_find_iq_key_size = "G";
//2012-10-12增加 by lml
    public static String Command_find_iq_key_size_1 = "M";
    public static String Command_iq_ip = "10.90.1.25";
    public static String Command_iq_db_key = "Main IQ Blocks Used:";
    public static String Command_iq_db_key_start = "=";
    public static String Command_iq_db_key_end = "G";
    public static String Command_iq_db_key_end_1 = "M";
    public static String[] Command_iq_ip_jobs = {"10.90.1.127"};//查找作业的地址
    public static String Command_iq_db_key_job_failure = "Failed";//作业的失败关键字
    /*
     /*
     * 
     * 
     * 命令分隔符

     */
    public static String Command_seperator_space = " ";//空格
    public static String Command_seperator_slash = "/";//斜杠
    public static String Command_seperator_percent = "%";//百分号

    public static String Command_seperator_colon = ":";//冒号
    public static String Command_seperator_tab = "	";//TAB键

    public static String Command_seperator_comma = ",";//逗号
    public static String Command_seperator_bracket_left = "(";//左括号

    public static String Command_seperator_bracket_right = ")";//右括号

    /*
     * 数据块名称

     */
    public static String Segment_name_default = "default";//数据块

    public static String Segment_name_index = "index_segment";//索引块

    public static String Segment_name_log = "logsegment";//日志块

    public static String Segment_name_data_1 = "data_seg1";//数据块


    /*
     * IP的前缀
     */
    public static String Ip_prefix = "10.99.1.";

    /*
     * 数据库类型

     */
    public static String Db_type_sybase = "0";//sybase数据库

    public static String Db_type_iq = "1";//iq数据库

    public static String Db_type_sybase_dbcc = "2";//sybase数据库,执行dbcc命令
    public static String Db_type_sybase_dbcc_1 = "3";//sybase数据库,执行dbcc命令
//行颜色

    public static String[] style_colors = {"#ffffff", "#f7f7f7"};

    /*
     * 命令dbcc查找关键字

     */
    public static String[] Command_find_dbcc_keys_ip = {"Connected to"};
    public static String Command_find_dbcc_key_space_used = "Space used";
    public static String Command_find_dbcc_key_space_free = "Space free";
    public static String Command_find_dbcc_key_unit = "M";
    public static String Status_normal = "0";
    public static String Status_failure = "1";
    //NTP文件名包含字符串
    public static String FILE_NTP_INCLUDE = "_ntp";
    public static SynControl control = new SynControl();
    // 导入命令
    public static final String COMMAND_IMPORT = "import";
    public static int Flag_type_lock = 1;//锁定状态类型

    public static int Flag_type_status = 2;//设备状态类型

    public static int Flag_type_new_window_flag = 3;//新窗口标志

    public static int Flag_type_etl = 4;//抽数状态

}
