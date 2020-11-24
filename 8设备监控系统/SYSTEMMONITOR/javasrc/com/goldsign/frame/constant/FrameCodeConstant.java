/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.constant;

/**
 *
 * @author hejj
 */
public class FrameCodeConstant {
    // 草稿参数代码

    static final public String VERSION_TYPE_DRAFT = "3";
    // 未来参数代码
    static final public String VERSION_TYPE_FUTURE = "1";
    // 当前参数代码
    static final public String VERSION_TYPE_CURRENT = "0";
    // 记录标志代码（草稿参数）
    static final public String RECORD_FLAG_DRAFT = "3";
    // 未来参数记录标志代码（删除标志）
    static final public String RECORD_FLAG_DELETED = "4";
    // 当前参数记录标志代码（删除标志）
    static final public String RECORD_FLAG_DELETED_CURRENT = "5";
    // 记录标志代码（未来参数）
    static final public String RECORD_FLAG_FUTURE = "1";
    // 记录标志代码（历史参数）
    static final public String RECORD_FLAG_HISTORY = "2";
    // 草稿参数版本号
    static final public String VERSION_NO_DRAFT = "0000000000";
    // 参数类型
    // 记录标志代码（当前参数）
    static final public String RECORD_FLAG_CURRENT = "0";
    // 线路参数类型
    public static final String PARAM_TYPE_ID_LINE = "9004";
    public static final String REPORT_FILE_CONFIG_ATTR = "9014";
    public static final String REPORT_DATA_SOURCE = "9015";
    public static final String STATION_COMPARE_ATTR = "0205";
    // 设备类型
    public static String PARAM_TYPE_ID_DEVICE_TYPE = "9005";
    // 票卡主类型
    public static final String PARAM_TYPE_ID_CARD_TYPE = "9010";
    // 票卡子类型
    public static final String PARAM_TYPE_ID_CARD_SUBTYPE = "9011";
    // 车站参数类型
    public static final String PARAM_TYPE_ID_STATION = "0201";
    // 车站中英文翻译参数类型
    public static final String PARAM_TYPE_ID_STATION_FOR_TRANSLATE = "0201Trans";
    // 车站设备类型
    public static String PARAM_TYPE_ID_STATION_DEVICE = "0202";
    // 票卡属性类型
    public static final String PARAM_TYPE_ID_CARD_ATTRIBUTE = "0301";
    //乘次票卡属性类型
    public static final String PARAM_TYPE_ID_CHC_CARD_ATTRIBUTE = "0303";
    //乘次票卡属性类型
    public static final String PARAM_TYPE_ZYTD_ATTRIBUTE = "0302";
    //运营商类型
    public static final String PARAM_CONTC = "0302";
    public static int PUT_FLAG_TYPE_YEAR = 6;
    public static int PUT_FLAG_TYPE_MONTH = 7;
    public static int PUB_FLAG_TYPE_REPORT = 8;
    public static int PUT_FLAG_TYPE_CARD_SUBTYPE = 9;
    public static int PUT_FLAG_TYPE_BILL_STATUS = 10;//单据状态
    public static int PUT_FLAG_TYPE_NEW_OLD_FLAG = 11;//新旧票标志
    public static int PUT_FLAG_TYPE_MONEY = 12;//面值
    public static int PUT_FLAG_TYPE_HDLFLAG = 13;//订单处理标志
    public static int PUT_FLAG_TYPE_RESTRICTFLAG = 15;//是否限制本站标志
    public static int PUT_FLAG_TYPE_INOUTFLAG = 17;//出入库类型标志
    public static int PUB_FLAG_TYPE_SYSTEM = 18;//日志级别的系统
    public static int PUB_FLAG_TYPE_LOG_LEVEL = 19;//日志级别的日志分级
    public static int PUB_FLAG_TYPE_DATASOURCE = 20;//数据源
    public static int PUB_FLAG_TYPE_DELAY = 21;//是否滞留
    public static int PUB_FLAG_TYPE_FINISH_FLAG = 23;//订单明细上传完成标志
    public static int PUB_FLAG_TYPE_PARA_FLAG = 24;//流失量参数
    public static int PUB_FLAG_TYPE_ICCS_DEV_STATUS = 25;//iccs设备状态
    public static int PUB_FLAG_TYPE_LIMIT_MODE = 26;//乘次票模式
    public static int PUB_FLAG_TYPE_DISCOUNT = 28;//是否优惠票
    public static int PUB_FLAG_TYPE_VOICE = 29;//是否启用语音提示
    public static int PUB_FLAG_TYPE_RECORD_FLAG = 30;//参数记录标志
    public static int PUB_FLAG_TYPE_CAL_FLAG = 31;//票价计算状态
    public static int PUB_FLAG_TYPE_DEV_TYPE = 33;//设备类型
    public static int PUB_FLAG_TYPE_DEV_EFFECT = 34;//参数是否生效
    public static int PUB_FLAG_TYPE_SALE_FLAG = 35;//发售激活标志
    public static int PUB_FLAG_TYPE_TEST_FLAG = 36;//测试标志
    //年标志
    public static final int FLAG_YEAR = 1;
    //月标志
    public static final int FLAG_MONTH = 2;
    //日期标志
    public static final int FLAG_DATE = 3;
    //周标志
    public static final int FLAG_WEEK = 3;
    public static String CONFIG_TYPE_SYS = "1";//配置类型:1系统级
    public static int MAX_DB_ROW_NUMBER = 3000;
    public static int MAX_PAGE_NUMBER = 300;//页面列表最大显示行数
    // 最大黑名单段数量
    public static int MAX_BLACKLIST_SECTION_NUMBER = 20;
    
    // 最大黑名单数量
    public static int MAX_BLACKLIST_NUMBER = 30000;
    public static int MAX_METRO_BLACKLIST_NUMBER = 10000;
    public static int MAX_OUT_NUMBER = 100;
    public static int ApplicationFlag = 1;
    public static int ApplicationFlag_ALL = 1;//完整应用
    public static int ApplicationFlag_APM = 2;//APM应用
    public static int ApplicationFlag_APM_NONE = 3;//非APM应用
}
