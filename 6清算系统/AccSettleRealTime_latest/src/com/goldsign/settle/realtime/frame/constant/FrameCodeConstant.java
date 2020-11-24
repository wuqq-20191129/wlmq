/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.constant;

/**
 *
 * @author hejj
 */
public class FrameCodeConstant {

    /**
     * ******应用启动************************
     */
    public static final String FILE_APP_CONFIG = "SettleConfig.xml";//应用配置文件
    public static final String FILE_APP_CONFIG_LOG = "SettleLog4jConfig.xml";//应用的日志配置文件
    /**
     * ************************************
     */
    /**
     * 基本目录 从数据库配置中获取
     */
    public static String PATH_FILE_TRX = "D:/en_work/dir_prog_work/settle/files";//上传的交易文件目录
    public static String PATH_FILE_TRX_HIS = "D:/en_work/dir_prog_work/settle/files/his";//上传的交易文件历史目录
    public static String PATH_FILE_TRX_HIS_ERROR = "D:/en_work/dir_prog_work/settle/files/error";//上传的交易文件错误历史目录
    public static String PATH_FILE_TRX_BCP_LOG = "D:/en_work/dir_prog_work/settle/bcplog";//BCP的日志目录
    public static String PATH_FILE_TRX_BCP_LOG_BAD = "D:/en_work/netbeansproject/长沙ACC/ACC清算系统/AccSettle/bcpbad";//BCP的日志目录
    public static String PATH_FILE_TRX_BCP = "D:/en_work/dir_prog_work/settle/bcp";//BCP的交易文件目录
    public static String PATH_FILE_TRX_BCP_CTL = "D:/en_work/netbeansproject/长沙ACC/ACC清算框架/AccSettleFrame/controlFile";//BCP的控制文件目录
    public static String PATH_FILE_AUDIT = "D:/en_work/dir_prog_work/settle/download";//下发审计文件目录
    public static String PATH_FILE_AUDIT_LCC = "D:/en_work/dir_prog_work/settle/download";//下发lcc对账文件目录
    //人工处理使用
    public static String PATH_FILE_TRX_MANU = "D:/en_work/dir_prog_work/settle/files";//上传的交易文件目录

    public static String PATH_BASE_HOME_APP = "";//应用主目录
    public static String PATH_BASE_HOME_BUSINESS_WORK = "";//业务工作目录
    public static String PATH_BASE_HOME_BUSINESS_ARCH = "";//业务归档目录
    public static String VAR_PATH_BASE_HOME_APP = "#HOME_APP";//应用主目录
    public static String VAR_PATH_BASE_HOME_BUSINESS_WORK = "#HOME_BUSINESS_WORK";//业务工作目录
    public static String VAR_PATH_BASE_HOME_BUSINESS_ARCH = "#HOME_BUSINESS_ARCH";//业务归档目录
    /**
     * *************************************************************************************************
     */
    /*
     //旧导出的交易文件相关目录
     public static String PATH_FILE_OCT_EXPORT_TRX_FILE = "D:/en_work/dir_prog_work/settle/export/files";//导出文件目录
     public static String PATH_FILE_OCT_EXPORT_TRX_ZIP = "D:/en_work/dir_prog_work/settle/export/zip";//导出文件压缩目录
    
     //旧导出的结算结果相关目录
     public static String PATH_FILE_OCT_EXPORT_SETTLE_FILE = "D:/en_work/dir_prog_work/settle/export/files";//结算结果导出文件目录
     public static String PATH_FILE_OCT_EXPORT_SETTLE_ZIP = "D:/en_work/dir_prog_work/settle/export/zip";//结算结果导出文件压缩目录
    
    
     //旧导入的结算结果相关目录
     public static String PATH_FILE_OCT_IMPORT_SETTLE_FILE = "D:/en_work/dir_prog_work/settle/filesOct/file";//oct返回文件的目录
     public static String PATH_FILE_OCT_IMPORT_SETTLE_ZIP = "D:/en_work/dir_prog_work/settle/filesOct/zip";//oct返回压缩文件的目录
     public static String PATH_FILE_OCT_IMPORT_SETTLE_FILE_HIS = "D:/en_work/dir_prog_work/settle/filesOct/file/his";//oct返回文件的目录历史目录
     public static String PATH_FILE_OCT_IMPORT_SETTLE_FILE_ERROR = "D:/en_work/dir_prog_work/settle/filesOct/file/error";//oct返回文件的目录错误历史目录
    
     //旧导入的交易数据相关目录
     public static String PATH_FILE_OCT_IMPORT_TRX_FILE = "D:/en_work/dir_prog_work/settle/filesOct/file/error";//oct上传的交易文件目录
     public static String PATH_FILE_OCT_IMPORT_TRX_ZIP = "D:/en_work/dir_prog_work/settle/filesOct/file/error";//oct上传的压缩交易文件目录
     */
    /**
     * *************************************************************************************************
     */
    //导出的交易文件相关目录
    public static String PATH_FILE_OCT_EXPORT_TRX_ZIP = "D:/en_work/dir_prog_work/settle/settle_file/oct/export/trx/zip";
    public static String PATH_FILE_OCT_EXPORT_TRX_FILE = "D:/en_work/dir_prog_work/settle/settle_file/oct/export/trx/file";
    public static String PATH_FILE_OCT_EXPORT_TRX_FILE_HIS = "D:/en_work/dir_prog_work/settle/settle_file/oct/export/trx/his";
    public static String PATH_FILE_OCT_EXPORT_TRX_FILE_ERROR = "D:/en_work/dir_prog_work/settle/settle_file/oct/export/trx/error";
    //导出的结算文件相关目录
    public static String PATH_FILE_OCT_EXPORT_SETTLE_ZIP = "D:/en_work/dir_prog_work/settle/settle_file/oct/export/settle/zip";
    public static String PATH_FILE_OCT_EXPORT_SETTLE_FILE = "D:/en_work/dir_prog_work/settle/settle_file/oct/export/settle/file";
    public static String PATH_FILE_OCT_EXPORT_SETTLE_FILE_HIS = "D:/en_work/dir_prog_work/settle/settle_file/oct/export/settle/his";
    public static String PATH_FILE_OCT_EXPORT_SETTLE_FILE_ERROR = "D:/en_work/dir_prog_work/settle/settle_file/oct/export/settle/error";
    //导入的交易文件相关目录
    public static String PATH_FILE_OCT_IMPORT_TRX_ZIP = "D:/en_work/dir_prog_work/settle/settle_file/oct/import/trx/zip";
    public static String PATH_FILE_OCT_IMPORT_TRX_FILE = "D:/en_work/dir_prog_work/settle/settle_file/oct/import/trx/file";
    public static String PATH_FILE_OCT_IMPORT_TRX_FILE_HIS = "D:/en_work/dir_prog_work/settle/settle_file/oct/import/his";
    public static String PATH_FILE_OCT_IMPORT_TRX_FILE_ERROR = "D:/en_work/dir_prog_work/settle/settle_file/oct/import/error";
    //导入的结算文件相关目录
    public static String PATH_FILE_OCT_IMPORT_SETTLE_ZIP = "D:/en_work/dir_prog_work/settle/settle_file/oct/import/settle/zip";
    public static String PATH_FILE_OCT_IMPORT_SETTLE_FILE = "D:/en_work/dir_prog_work/settle/settle_file/oct/import/settle/file";
    public static String PATH_FILE_OCT_IMPORT_SETTLE_FILE_HIS = "D:/en_work/dir_prog_work/settle/settle_file/oct/import/settle/his";
    public static String PATH_FILE_OCT_IMPORT_SETTLE_FILE_ERROR = "D:/en_work/dir_prog_work/settle/settle_file/oct/import/settle/error";
    //外部FTP目录
    public static String PATH_FILE_OCT_UPLOAD = "D:/en_work/dir_prog_work/settle/settle_file/oct/upload";
    public static String PATH_FILE_OCT_DOWNLOAD = "D:/en_work/dir_prog_work/settle/settle_file/oct/download";
    //TCC历史文件下发目录
    public static String PATH_FILE_TCC_DOWNLOAD = "D:/en_work/dir_prog_work/settleRealtime/communfs/ftp/tcc/his";
    /*
     * 交易类型
     */
    public static String TRX_TYPE_SALE_SJT = "50";//单程票售票记录
    public static String TRX_TYPE_SALE_SJT_NONE = "51";//非单程票售票记录
    public static String TRX_TYPE_CARD_APP = "52";//记名卡申请
    public static String TRX_TYPE_ENTRY = "53";//进站记录
    public static String TRX_TYPE_PURSE = "54";//钱包交易
    public static String TRX_TYPE_DELAY = "55";//票卡延期
    public static String TRX_TYPE_UPDATE = "56";//票卡更新
    public static String TRX_TYPE_RETURN = "57";//即时退卡记录
    public static String TRX_TYPE_RETURN_NOT_INSTANT = "58";//非即时退卡记录
    public static String TRX_TYPE_LOCK = "59";//卡加锁/解锁
    public static String TRX_TYPE_REFUSE = "60";//卡被拒绝
    public static String TRX_TYPE_ADMIN = "61";//行政处理
    //外部交易
    public static String TRX_TYPE_PURSE_OCT = "OctTRX";//公交消费
    //手机支付交易
    public static String TRX_TYPE_PURSE_MOBILE = "Mobile54";//钱包交易
    public static String TRX_TYPE_RETURN_MOBILE = "Mobile57";//即时退卡记录
    public static String TRX_TYPE_LOCK_MOBILE = "Mobile59";//卡加锁/解锁
    public static String TRX_TYPE_SALE_SJT_NONE_MOBILE = "Mobile51";//非单程票售票记录
    //互联网支付交易发售
    public static String TRX_TYPE_SALE_SJT_NETPAID = "NetPaid50";//单程票发售
    //互联网支付交易充值
    public static String TRX_TYPE_PURSE_NETPAID = "NetPaid54";//钱包交易
    //互联网支付交易订单
    public static String TRX_TYPE_ORD_NETPAID = "NetPaidORD";//订单
    //互联网支付交易订单执行
    public static String TRX_TYPE_ORI_NETPAID = "NetPaidORI";//订单执行
    //交易长度
    public static int LEN_TRX_SALE_SJT = 148;//单程票发售交易长度
    public static int LEN_TRX_SALE_SJT_NONE = 112;//储值票发售交易长度
    public static int LEN_TRX_CARD_APP = 122;//记名卡申请交易长度
    public static int LEN_TRX_ENTRY = 99;//进站交易长度
    public static int LEN_TRX_PURSE = 204;//钱包交易长度
    public static int LEN_TRX_DELAY = 103;//延期交易长度
    public static int LEN_TRX_UPDATE = 112;//更新交易长度
    public static int LEN_TRX_RETURN = 142;//退款交易长度
    public static int LEN_TRX_RETURN_NOT_INSTANT = 128;//非即时退款申请交易长度
    public static int LEN_TRX_LOCK = 91;//锁卡交易长度
    public static int LEN_TRX_ADMIN = 71;//行政处理交易长度
    public static String[] TRX_TYPE_CODES = {
        TRX_TYPE_SALE_SJT,
        TRX_TYPE_SALE_SJT_NONE,
        TRX_TYPE_CARD_APP,
        TRX_TYPE_ENTRY,
        TRX_TYPE_PURSE,
        TRX_TYPE_DELAY,
        TRX_TYPE_UPDATE,
        TRX_TYPE_RETURN,
        TRX_TYPE_RETURN_NOT_INSTANT,
        TRX_TYPE_LOCK,
        TRX_TYPE_ADMIN,};
    public static int[] LEN_TRX_TYPES = {
        LEN_TRX_SALE_SJT,
        LEN_TRX_SALE_SJT_NONE,
        LEN_TRX_CARD_APP,
        LEN_TRX_ENTRY,
        LEN_TRX_PURSE,
        LEN_TRX_DELAY,
        LEN_TRX_UPDATE,
        LEN_TRX_RETURN,
        LEN_TRX_RETURN_NOT_INSTANT,
        LEN_TRX_LOCK,
        LEN_TRX_ADMIN
    };
    /**
     * 查重交易
     */
    //modified by hejj 20160118增加手机支付交易
    //modified by hejj 20161220增加互联网支付交易
    public static String[] TRX_TYPE_CODES_CHK_UNIQUE = {
        TRX_TYPE_SALE_SJT,
        TRX_TYPE_SALE_SJT_NONE,
        TRX_TYPE_CARD_APP,
        TRX_TYPE_ENTRY,
        TRX_TYPE_PURSE,
        TRX_TYPE_DELAY,
        TRX_TYPE_UPDATE,
        TRX_TYPE_RETURN,
        TRX_TYPE_RETURN_NOT_INSTANT,
        TRX_TYPE_LOCK,
        TRX_TYPE_ADMIN,
        TRX_TYPE_PURSE_OCT,
        TRX_TYPE_PURSE_MOBILE,
        TRX_TYPE_RETURN_MOBILE,
        TRX_TYPE_LOCK_MOBILE,
        TRX_TYPE_SALE_SJT_NONE_MOBILE,
        TRX_TYPE_SALE_SJT_NETPAID,
        TRX_TYPE_PURSE_NETPAID,
        TRX_TYPE_ORD_NETPAID,
        TRX_TYPE_ORI_NETPAID
    };
    /**
     * TAC校验相关
     */
    public static String TRX_PAY_MODE_DEFAULT = "10";
    public static String TRX_PAY_MODE_EXIT = "12";
    public static String TRX_PAY_MODE_CHARGE = "14";
    public static String TRX_PAY_MODE_SHOP_CONSUME = "7";//小额消费
    public static String TRX_PAY_MODE_CHARGE_CONSUME = "18";//冲正
    public static String TRX_PAY_MODE_BUY_SJT = "19";//购单程票//hejj 20180929
    public static String TRX_PAY_MODE_PENALTY = "1A";//支付罚金
    public static String TRX_PAY_MODE_FEE = "1B";//支付手续费
    public static String TRX_PAY_MODE_UPDATE = "1C";//更新
    public static String TRX_TAC_TYPE_CHARGE = "02";//充值
    public static String TRX_TAC_TYPE_SJT = "06";//单程票相关交易
    public static String TRX_TAC_TYPE_PURCHASE = "09";//"09";//复合消费
    public static String TRX_TAC_TYPE_PURCHASE_SINGLE = "06";//单次消费
    public static String TRX_TAC_KEY_GROUP_SJT = "01";//单程票密钥组
    public static String TRX_TAC_KEY_GROUP_CPU = "02";//CPU密钥组
    public static String TRX_TAC_KEY_INDEX_SJT = "02";//单程票密钥索引
    public static String TRX_TAC_KEY_INDEX_CPU = "04";//CPU密钥组
    //通过索引号查找对应的TAC交易类型如5010-》09
    public static String[] TRX_TYPES = {TRX_TYPE_SALE_SJT + TRX_PAY_MODE_DEFAULT,//单程票所有交易05
        TRX_TYPE_PURSE + TRX_PAY_MODE_EXIT,//储值卡出站复合消费09
        TRX_TYPE_PURSE + TRX_PAY_MODE_CHARGE,//储值卡充值02
        TRX_TYPE_PURSE + TRX_PAY_MODE_PENALTY,//储值卡支付罚金：符合消费09
        TRX_TYPE_PURSE + TRX_PAY_MODE_FEE,//储值卡支付手续费：单次消费06
        TRX_TYPE_PURSE + TRX_PAY_MODE_UPDATE,//储值卡更新：复合消费09
        TRX_TYPE_PURSE + TRX_PAY_MODE_SHOP_CONSUME,//储值卡小额消费：单次消费06
        TRX_TYPE_PURSE + TRX_PAY_MODE_CHARGE_CONSUME,//储值卡充正：单次消费06
        TRX_TYPE_RETURN + TRX_PAY_MODE_DEFAULT,//储值卡退款：复合消费09
        TRX_TYPE_PURSE_MOBILE + TRX_PAY_MODE_CHARGE,
        TRX_TYPE_RETURN_MOBILE + TRX_PAY_MODE_DEFAULT};
    public static String[] TRX_TYPES_TAC = {TRX_TAC_TYPE_SJT,
        TRX_TAC_TYPE_PURCHASE,
        TRX_TAC_TYPE_CHARGE,
        TRX_TAC_TYPE_PURCHASE,
        TRX_TAC_TYPE_PURCHASE_SINGLE,
        TRX_TAC_TYPE_PURCHASE,
        TRX_TAC_TYPE_PURCHASE_SINGLE,
        TRX_TAC_TYPE_PURCHASE_SINGLE,
        TRX_TAC_TYPE_PURCHASE,
        TRX_TAC_TYPE_CHARGE,
        TRX_TAC_TYPE_PURCHASE
    };
    //需进行TAC码校验的交易类型单乘票发售、钱包交易、退款、公交消费
    public static String[] TRX_TYPES_TAC_CHECK = {
        TRX_TYPE_SALE_SJT,
        TRX_TYPE_PURSE,
        TRX_TYPE_RETURN,
        TRX_TYPE_PURSE_OCT,
        TRX_TYPE_PURSE_MOBILE,
        TRX_TYPE_RETURN_MOBILE,
        TRX_TYPE_SALE_SJT_NETPAID,
        TRX_TYPE_PURSE_NETPAID
    };
    /**
     * ***********************************
     */
    /**
     * *******加密相关****
     */
    public static String KEY = "GOLDSIGN";
    public static String FLAG_ENC = "1";
    public static String FLAG_ENC_NOT = "0";
    //----------------------
    /**
     * 系统处理相关
     */
    public static String BALANCE_WATER_NO;//清算流水号
    public static int BALANCE_WATER_NO_SUB;//清算子流水
    //系统循环时间间隔
    public static int INTERVAL_COMMON_WAIT = 10000;//通用等候时间
    public static int SETTLE_FLOW_SLEEP_TIME = 3600000;//清算流程整体控制时间间隔
    public static int SETTLE_FLOW_SLEEP_TIME_FILE = 60000;//清算流程文件处理时间间隔
    public static int SETTLE_FLOW_SLEEP_TIME_TRX_IMPORT = INTERVAL_COMMON_WAIT;//外部地铁卡数据导入
    public static int SETTLE_FLOW_SLEEP_TIME_SETTLE_STATUS = INTERVAL_COMMON_WAIT;//结算状态查询
    public static int SETTLE_FLOW_SLEEP_TIME_SETTLE_IMPORT = INTERVAL_COMMON_WAIT;//外部公交卡结算结果导入
    public static int SETTLE_FLOW_SLEEP_TIME_SETTLE_STATUS_LOG = INTERVAL_COMMON_WAIT;//结算日志状态查询
    public static String SETTLE_BEGIN_TIME = "0300";//清算开始时间
    public static String SETTLE_DOWNLOAD_TIME = "0300";//清算下发审计文件开始时间
    public static String SETTLE_BUS_CONTROL_TRX = "1";//控制是否处理公交交易数据
    public static String SETTLE_BUS_CONTROL_SETTLE = "1";//控制是否处理公交结算数据
    /*
     * 手动处理及测试相关控制
     */
    public static String CONTROL_MANU_YES = "1";//人工处理
    public static String CONTROL_MANU_NO = "0";//非人工处理
    public static String CONTROL_MANU = CONTROL_MANU_NO;
    public static String CONTROL_PRESETTLE_ONLY_YES = "1";//仅预处理
    public static String CONTROL_PRESETTLE_ONLY_NO = "0";//清算预处理及结算
    public static String CONTROL_PRESETTLE_ONLY = CONTROL_PRESETTLE_ONLY_NO;
    /**
     * JMS处理相关
     */
    public static String JMS_URL = "t3://172.20.19.107:7001";
    public static String JMS_CONTEXT_FACTORY = "weblogic.jndi.WLInitialContextFactory";
    public static String JMS_CONNECTION_FACTORY = "weblogic.jms.ConnectionFactory";
    public static String JMS_QUEUE_NAME = "queueAcc";
    /**
     * 版本标志***********************************************************
     */
    public static String RECORD_FLAG_CURRENT = "0";//当前版本
    public static String RECORD_FLAG_FUTURE = "1";//未来版本
    /**
     * ***********************************************************
     */
    /**
     * 系统标识***********************************************************
     */
    public static String SYSTEM_SETTLE = "1";//清算系统
    /**
     * 判断线程任务是否完成的时间间隔
     */
    public static long THREAD_IS_FINISH_INTERVAL = 1000;
    /**
     * 票卡主类型 *
     */
    public static String CARD_MAIN_TYPE_SJT = "01";//单程票
    public static String CARD_MAIN_TYPE_SVT = "02";//储值票
    public static String CARD_MAIN_TYPE_TCT = "03";//次票
    public static String CARD_MAIN_TYPE_CT = "04";//纪念票
    public static String CARD_MAIN_TYPE_EP = "05";//员工票
    public static String[] CARD_MAIN_TYPE_SJTS = {CARD_MAIN_TYPE_SJT};
    public static String[] CARD_MAIN_TYPE_CPUS = {CARD_MAIN_TYPE_SVT, CARD_MAIN_TYPE_TCT,
        CARD_MAIN_TYPE_CT, CARD_MAIN_TYPE_EP};//
    public static String[] CARD_MAIN_TYPE_METRO = {CARD_MAIN_TYPE_SJT, CARD_MAIN_TYPE_SVT,
        CARD_MAIN_TYPE_TCT, CARD_MAIN_TYPE_CT,
        CARD_MAIN_TYPE_EP};//地铁发行的票卡

    /**
     * 公交交互控制
     */
    public static String BUS_CONTROL_YES = "1";//处理
    public static String BUS_CONTROL_NO = "0";//不处理
    /**
     * 交易子类型区分数据类型是交易数据还是结算数据
     */
    public static String SUB_TRADE_TYPE_TRX = "Trx";//交易数据
    public static String SUB_TRADE_TYPE_SETTLE = "Settle";//结算数据
    //控制文件名称
    public static String CONTROL_FILE = "control.txt";
    //交易文件分隔符
    /*
     public static String DELIMIT_CONTROL = "";//DELIMIT_CONTROL_HEX;
     public static String DELIMIT_CONTROL_HEX = "1";//交易文件使用3个回车换行符
     public static String DELIMIT_CONTROL_TEXT = "0";//交易文件使用1个回车换行符
     */
    //
    public static String DELIMIT_HEX = "\\x0d\\x0a\\x0d\\x0a\\x0d\\x0a";
    //protected static String DELIMIT_HEX = "\\x0d\\x0a";
    public static String DELIMIT_TEXT = "\\x0d\\x0a";
    public static String DELIMIT_TEXT_ONE = "\\x0a";
    public static char[] DELIMIT_CHARS_HEX = {0x0D, 0xA, 0x0D, 0xA, 0x0D, 0xA};
    //   protected static char[] DELIMIT_CHARS_HEX={0x0D,0xA};
    public static char[] DELIMIT_CHARS_TEXT = {0x0D, 0xA};
    //字段长度的限制
    public static int LIMIT_LEN_FIELD_ERR_DATA = 512;//错误记录的数据长度限制
    //手机支付控制
    public static String BUSINESS_MOBILE_CONTROL = "1";////1:启用 0：停用
    public static String BUSINESS_MOBILE_CONTROL_ON = "1";////1:启用 0：停用
    public static String BUSINESS_MOBILE_CONTROL_OFF = "0";
    public static String BUSINESS_MOBILE_CONTROL_TRX_DOWNLOAD = "0";////1:启用 0：停用

    //二维码控制
    public static String BUSINESS_QRCODE_CONTROL = "1";////1:启用 0：停用
    public static String BUSINESS_QRCODE_CONTROL_ON = "1";////1:启用 0：停用
    public static String BUSINESS_QRCODE_CONTROL_OFF = "0";
    public static String BUSINESS_QRCODE_CONTROL_TRX_DOWNLOAD = "0";////1:启用 0：停用
    //地铁二维码控制
    public static String BUSINESS_QRCODE_MTR_CONTROL = "1";////1:启用 0：停用

    //TCC控制
    public static String BUSINESS_TCC_CONTROL = "1";////1:启用 0：停用
    public static String BUSINESS_TCC_CONTROL_ON = "1";////1:启用 0：停用
    public static String BUSINESS_TCC_CONTROL_OFF = "0";

    //互联网支付控制
    public static String BUSINESS_NETPAID_CONTROL = "1";////1:启用 0：停用
    public static String BUSINESS_NETPAID_CONTROL_ON = "1";////1:启用 0：停用
    public static String BUSINESS_NETPAID_CONTROL_OFF = "0";

    //下发手机支付平台的交易文件目录
    public static String PATH_MOBILE_MTX = "";
    public static String MOBILE_PATH_EXPORT_TRX_HIS = "";//交易归档历史目录
    public static String MOBILE_PATH_EXPORT_TRX_ERR = "";//交易归档错误目录
    public static String MOBILE_PATH_EXPORT_TRX_FILE = "";//交易文件生成临时目录

    //手机支付、互联网支付、磁浮线路定义代码
    public static String LINE_ID_MOBILE = "80";//手机支付线路代码
    public static String LINE_ID_MOBILE_BANK = "81";//银行手机支付线路代码
    public static String LINE_ID_MAG = "60";//磁浮线路代码
    public static String LINE_ID_OCT = "OCT";//公交线路代码
    public static String LINE_ID_NETPAID = "82";//互联网支付线路代码
    public static String LINE_ID_QRCODE = "70";//二维码平台线路代码
    public static String LINE_ID_QRCODE_MTR = "71";//地铁二维码平台线路代码
    public static String LINE_ID_TCC = "83";//TCC虚拟线路代码

    public static String APP_PLATFORM_METRO = "1";//地铁手机支付
    public static String APP_PLATFORM_BANK = "9";//银行手机支付
//add by hejj 20190624
    public static String EXPORT_FLAG_NOT = "0";//未导出
    public static String EXPORT_FLAG_YES = "1";//已导出
    public static String EXPORT_FLAG_DOING = "8";//正在导出

    //公交文件上传最迟时间点
    //公交上传交易最迟时间点；0：不限制，清算一直等待 其他：到点，清算跳过导入，强制进行下一步处理
    public static String SYS_SETTLE_OCT_IMPORT_TRX_LIMIT_TIME = "0";
    //公交上传结算结果最迟时间点；0：不限制，清算一直等待 其他：到点，清算跳过导入，强制进行下一步处理
    public static String SYS_SETTLE_OCT_IMPORT_SETTLE_LIMIT_TIME = "0";
    //文件处理时间与生成时间间隔 add by hejj 20161226
    public static long FILE_PROCESSED_BEFORE = 5000;//单位：毫秒当前批次处理时间与文件的创建时间间隔，大于等于设定值处理，解决通讯、清算文件写、读冲突问题

    //批次文件数量限制 sys.settle.file.once.limit.flag sys.settle.file.once.limit.number
    public static String SYS_SETTLE_FILE_ONCE_LIMIT_FLAG = "0";//0:不限制 1：限制
    public static String SYS_SETTLE_FILE_ONCE_LIMIT_NO = "0";
    public static String SYS_SETTLE_FILE_ONCE_LIMIT_YES = "1";
    public static int SYS_SETTLE_FILE_ONCE_LIMIT_NUMBER = 10;//每批次文件处理数量

    //批次结算相关
    public static String SETTLE_FINISH_NOT = "0";//未完成
    public static String SETTLE_FINISH_YES = "1";//已完成
    public static String SETTLE_OK = "00";//没有错误
    public static String SETTLE_FINAL_NO = "0";//非最后一次
    public static String SETTLE_FINAL_YES = "1"; //最后一次

    //
    public static String RUN_MODE = "1";//运行模式 1：开发 2：生产
    public static String RUN_MODE_DEV = "1";//运行模式 1：开发 2：生产
    public static String RUN_MODE_PRODUCT = "2";//运行模式 1：开发 2：生产
    //手工清算控制
    public static String SYS_FLOW_CTR_DW_BL = "1";//是否下发黑名单；1：下发；0：不下发；
    public static String SYS_FLOW_CTR_DW_AUDITFILE = "1";//是否下发FTP审计、错误文件；1：下发；0：不下发；
    public static String SYS_FLOW_CTR_DW_AUDITLCC = "1";//是否下发LCC对账文件；1：下发；0：不下发；
    public static String SYS_FLOW_CTR_DW_AUDITLCC_MOBILE = "1";//是否下发手机支付对账文件；1：下发；0：不下发；
    public static String SYS_FLOW_CTR_EXP_OCT_TRX = "1";//是否导出一卡通消费；1：导出；0：不导出；
    public static String SYS_FLOW_CTR_YES = "1";//下发

    //二维码平台标识定义
    public static String ISSUE_QRCODE_PLATFORM_XIAOMA = "01";//小码平台
    public static String ISSUE_QRCODE_PLATFORM_MTR = "02";//地铁F平台

}
