/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.env;

import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.vo.KeyValueVo;
import com.goldsign.esmcs.util.DbcpHelper;
import com.goldsign.esmcs.vo.*;
import java.util.*;

/**
 * 应用常量类
 * 
 * @author lenovo
 */
public class AppConstant extends BaseConstant{

    public static Hashtable configs = null;                //配置文件内容
    
    public static DbcpHelper dbcpHelper = null; //数据库连接池
    public static final String TABLE_ET_PARAMER = "w_ic_et_pub_flag";//参数表名
    public static final String TABLE_C_LINE = "w_op_prm_line";//线路表名
    public static final String TABLE_C_STATION = "w_op_prm_station";//站点表名
    public static final String TABLE_OP_PUB_FLAG = "w_op_cod_pub_flag";//运营中心系统参数表名
    public static final String TABLE_OP_CARD_MAIN_TYPE = "w_op_prm_card_sub";//运营中心系统票卡类型表名
    public static  String DEVICE_ID = "";
      //线路 cLine[2]
    public static Map C_LINE = new HashMap();
    
    //站点 cStationNo[2]
    public static Map C_STATION_NO= new HashMap();
    
    //限制模式 cLimitMode[3]
    public static Map C_LIMIT_MODE = new HashMap();
    
     //票卡状态 以附录5“车票状态”为准 bStatus
    public static Map TICKET_STATUS = new HashMap();
    
    //票卡物理类型 bCharacter 1：UL；2：CPU；
    public static Map PHY_CHARACTER = new HashMap();
    
    //性别 certificate_sex
    public static Map CERTIFICATE_SEX = new HashMap();
    
    //持卡类型 certificate_iscompany
    public static Map CERTIFICATE_ISCOMPANY = new HashMap();
    
    //票卡类型 cTicketType[4];	（取主类型，前两位）
    public static Map TICKET_TYPE = new HashMap();
    public static String TICKET_TYPE_EMPLOYEE = "05";//员工票
    
    // 证件类型 certificate_type
    public static Map CERTIFICATE_TYPE = new HashMap();
    
    // 本单位职工 (地铁通卡) certificate_ismetro
    public static Map CERTIFICATE_ISMETRO = new HashMap();
    
    // 员工单位 employee_department
    public static Map EMPLOYEE_DEPARTMENT = new HashMap();
//    public static String STR_EMPLOYEE_DEPARTMENT = "EMPLOYEE_DEPARTMENT";
//    public static String DECRI_EMPLOYEE_DEPARTMENT = "单位";
    
    // 员工职务 employee_position
    public static Map EMPLOYEE_POSITION = new HashMap();
//    public static String STR_EMPLOYEE_POSITION = "EMPLOYEE_POSITION";
//    public static String DECRI_EMPLOYEE_POSITION = "职务";
    
    // 员工级别 employee_class
    public static Map EMPLOYEE_CLASS = new HashMap();
    
    //单位，职务，级别下拉数量限制
    public static int DEPART_POSITION_CLASS_COUNT = 30;
    
    //发行状态
    public static Map ISSUE_STATUS = new HashMap();
    public static String ISSUE_STATUS_ISSUED = "1";//已发行
    public static String ISSUE_STATUS_CANCEL = "2";//已注销
    
   
    public static String STR_ISSUE_STATUS = "0";//	发行状态
    public static String STR_CERTIFICATE_TYPE = "1";//	证件类型
    public static String STR_CERTIFICATE_ISMETRO = "10";//	是否地铁通卡
    public static String STR_TICKET_STATUS = "3";//	票卡状态
    public static String STR_PHY_CHARACTER = "4";//	票卡物理类型
    public static String STR_CERTIFICATE_ISCOMPANY = "5";//	持卡类型
    public static String STR_CERTIFICATE_SEX = "6";//	性别
    public static String STR_EMPLOYEE_DEPARTMENT = "7";
    public static String DECRI_EMPLOYEE_DEPARTMENT = "单位";
    public static String STR_EMPLOYEE_POSITION = "8";
    public static String DECRI_EMPLOYEE_POSITION = "职务";
    public static String STR_EMPLOYEE_CLASS = "9";
    public static String DECRI_EMPLOYEE_CLASS = "级别";

    //状态栏
    public static final String STATUS_BAR_COMMU_STATUS = "commuStatus";
    public static final String STATUS_BAR_COMMU_STATUS_NAME = "ES通讯:";
    public static boolean COMMU_STATUS = false;
    public static final String STATUS_BAR_KMS_STATUS = "kmsStatus";
    public static final String STATUS_BAR_KMS_STATUS_NAME = "加密机:";
    public static boolean KMS_STATUS = false;
    public static final String STATUS_BAR_CURRENT_TIME = "currentTime";
    public static final String STATUS_BAR_CURRENT_TIME_NAME = "时间:";  
    public static final String STATUS_BAR_PRINTER_PORT = "printerPort";
    public static final String STATUS_BAR_PRINTER_PORT_NAME = "打印机:";
    public static boolean PRINTER_PORT = false;
    
    //工作类型
    public static final String WORK_TYPE_INITI = "00";
    public static final String WORK_TYPE_INITI_NAME = "初始化";
    public static final String WORK_TYPE_HUNCH = "01";
    public static final String WORK_TYPE_HUNCH_NAME = "预赋值";
    public static final String WORK_TYPE_AGAIN = "02";
    public static final String WORK_TYPE_AGAIN_NAME = "重编码";
    public static final String WORK_TYPE_LOGOUT = "03";
    public static final String WORK_TYPE_LOGOUT_NAME = "注销";
    public static final String WORK_TYPE_CLEAR = "04";
    public static final String WORK_TYPE_CLEAR_NAME = "洗卡";
    public static KeyValueVo[] WORK_TYPE = new KeyValueVo[]{
        new KeyValueVo(WORK_TYPE_INITI, WORK_TYPE_INITI_NAME), new KeyValueVo(WORK_TYPE_HUNCH, WORK_TYPE_HUNCH_NAME), 
        new KeyValueVo(WORK_TYPE_AGAIN, WORK_TYPE_AGAIN_NAME), new KeyValueVo(WORK_TYPE_LOGOUT, WORK_TYPE_LOGOUT_NAME),
        new KeyValueVo(WORK_TYPE_CLEAR, WORK_TYPE_CLEAR_NAME)
    };
    
    //票箱类型
    public static final int BOX_TYPE_NORMAL = 0;
    public static final int BOX_TYPE_INVAL  = 1;
    public static final int BOX_TYPE_NOSEL  = -1;
    
    //票箱状态
    public static final byte BOX_FULL_STATE_FULLED = 2; //满
    public static final byte BOX_FULL_STATE_FULLING = 3; //将满
    public static final int BOX_FULLING_WARN_NUM = 10; //将满阀值
    
    //制卡运行状态
    public static final int MAKE_CARD_STATUS_INIT = 0;  //初始化
    public static final int MAKE_CARD_STATUS_RUN = 1;   //运行
    public static final int MAKE_CARD_STATUS_PAUSE = 2; //暂停
    public static final int MAKE_CARD_STATUS_RESUME = 3; //继续
    public static final int MAKE_CARD_STATUS_STOP = 4;  //停止
    public static final int MAKE_CARD_STATUS_EXIT = 5;  //退出
    
    public static final short ES_BOX_NUM = 5;             //票箱数量
    
    //票箱默认值
    public static final byte BOX_NORMAL_DEFAULT_NO = 1; //默认正常票箱
    public static final byte BOX_INVAL_DEFAULT_NO = 5; //默认废票箱
    public static final byte BOX_FULL_DEFAULT_ERR = -1; //默认满错误票箱
    
    //设备状态
    public static final String ES_DEVICE_STATUS_BEGIN_MAKE = "0001";
    public static final String ES_DEVICE_STATUS_FINISH_MAKE = "0002";
    public static final String ES_DEVICE_STATUS_BEGIN_SORT = "0003";
    public static final String ES_DEVICE_STATUS_FINISH_SORT = "0004";
    public static final String ES_DEVICE_STATUS_FAIL = "0005";
    public static final String ES_DEVICE_STATUS_LOGIN = "0006";
    public static final String ES_DEVICE_STATUS_LOGOUT = "0007";
    
    //订单状态
    public static String ES_ORDER_STATUS_BEGIN_NO_DES = "未开始";
    public static String ES_ORDER_STATUS_BEGIN_YES_DES = "已开始";
    public static String ES_ORDER_STATUS_END_DES = "已结束";
    public static String ES_ORDER_STATUS_FINISH_DES = "已完成";
    public static String ES_ORDER_STATUS_BEGIN_NO = "0";
    public static String ES_ORDER_STATUS_BEGIN_YES = "1";
    public static String ES_ORDER_STATUS_END = "2";
    public static String ES_ORDER_STATUS_FINISH = "3";
    
    //设备状态
    public static String ES_DEVICE_STATUS_MAKE_BEGIN = "0001";
    public static String ES_DEVICE_STATUS_MAKE_BEGIN_DES = "开始制票";
    public static String ES_DEVICE_STATUS_MAKE_STOP = "0002";
    public static String ES_DEVICE_STATUS_MAKE_STOP_DES = "停止制票";
    public static String ES_DEVICE_STATUS_SORT_BEGIN = "0003";
    public static String ES_DEVICE_STATUS_SORT_BEGIN_DES = "开始分拣";
    public static String ES_DEVICE_STATUS_SORT_STOP = "0004";
    public static String ES_DEVICE_STATUS_SORT_STOP_DES = "停止分拣";
    public static String ES_DEVICE_STATUS_ERROR = "0005";
    public static String ES_DEVICE_STATUS_ERROR_DES = "故障";
    public static String ES_DEVICE_STATUS_OPER_LOGIN = "0006";
    public static String ES_DEVICE_STATUS_OPER_LOGIN_DES = "操作员登录";
    public static String ES_DEVICE_STATUS_OPER_EXIT = "0007";
    public static String ES_DEVICE_STATUS_OPER_EXIT_DES = "操作员退出";
    
    //通讯本地参数
    public static CityParamVo cityParamVo = new CityParamVo();
    public static List<TicketTypeVo> ticketTypeVos = new ArrayList<TicketTypeVo>();
    public static List<TicketPriceVo> ticketPriceVos = new ArrayList<TicketPriceVo>();
    public static List<FtpFileParamVo> blacklistFileVos = new ArrayList<FtpFileParamVo>();
    public static List<FtpFileParamVo> samCardFileVos = new ArrayList<FtpFileParamVo>();
    public static List<FtpFileParamVo> auditAndErrorFileVos = new ArrayList<FtpFileParamVo>();
    public static boolean COMMU_AUDIT_ERROR_FILE_DOWN_STATUS = false;//错误和审计文件是否已下载
    public static Map<String, SignCardVo> signCardVos = new HashMap<String, SignCardVo>();
    public static Map<String,String> phyLogicVos = new HashMap<String,String>();
    
    //文件下载程
    public static int FileDownThreadSleepTime = 1000;
    
    //订单更新状态
    public static String ES_ORDER_UPDATE_STATUS_SUC = "0";
    public static String ES_ORDER_UPDATE_STATUS_HAVE_EXE = "11";
    public static String ES_ORDER_UPDATE_STATUS_DB_ERROR = "12";
    
    //审计参数文件
    public static final String AUDIT_PARAM_FILE_TYPE_AUDIT = "ESFTP";
    public static final String AUDIT_PARAM_FILE_TYPE_AUDIT_NAME = "审计";
    public static final String AUDIT_PARAM_FILE_TYPE_ERROR = "ESER";
    public static final String AUDIT_PARAM_FILE_TYPE_ERROR_NAME = "错误";
    
    //文件通知结果
    public static final String ES_FILE_NOTICE_TYPE_SUCCESS = "0";
    public static final String ES_FILE_NOTICE_TYPE_SUCCESS_NAME = "成功";
    public static final String ES_FILE_NOTICE_TYPE_FAIL = "1";
    public static final String ES_FILE_NOTICE_TYPE_FAIL_NAME = "失败";
    
    //文件通知线程睡眠时间
    public static int ES_FILE_NOTICE_SLEEP_TIME = 60000;

    public static final String RW_SUCCESS_CODE = "00";//读写器成功代码
    
    public static String KMS_SUCCESS_CODE = "00";//加密机成功代码
    
    //票卡状态
    public static final String CARD_STATUS_INIT = "200";//初始化
    public static final String CARD_STATUS_EVALUATE = "201";//预赋值
    public static final String CARD_STATUS_DESTROY = "215";//注销
    public static KeyValueVo[] CARD_STATUS = new KeyValueVo[]{
        new KeyValueVo(CARD_STATUS_INIT, "初始化"),new KeyValueVo(CARD_STATUS_EVALUATE, "ES预赋值"),new KeyValueVo(CARD_STATUS_DESTROY, "已注销"),
        new KeyValueVo("202", "BOM/TVM发售"),new KeyValueVo("203", "出站"),new KeyValueVo("204", "列车故障模式出站"),
        new KeyValueVo("205", "进站BOM更新"),new KeyValueVo("206", "非付费区免费更新"),new KeyValueVo("207", "非付费区付费更新"),
        new KeyValueVo("208", "进站"),new KeyValueVo("209", "出站BOM更新"),new KeyValueVo("210", "无进站码更新"),
        new KeyValueVo("211", "超时更新"),new KeyValueVo("212", "超乘更新"),new KeyValueVo("213", "出站费"),new KeyValueVo("214", "退卡")
    };
    
    //发行状态 0 –未发行;1 –已发行;2 – 注销
    public static final String CARD_ISSUE_NO = "0";
    public static final String CARD_ISSUE_YES = "1";
    public static final String CARD_ISSUE_DESTROY = "2";
    public static KeyValueVo[] CARD_ISSUE_STATUS = new KeyValueVo[]{
        new KeyValueVo(CARD_ISSUE_NO, "未发行"),new KeyValueVo(CARD_ISSUE_YES, "已发行"),new KeyValueVo(CARD_ISSUE_DESTROY, "已注销")
    };
    
    //卡物理状态
    public static final String CARD_PHY_STATUS_BAD = "0";//坏卡
    public static final String CARD_PHY_STATUS_GOOD = "1";//好卡
    //是否损坏
    public static KeyValueVo[] CARD_IS_BAD = new KeyValueVo[]{
        new KeyValueVo("1", "好卡"),new KeyValueVo("0", "坏卡")
    };
    //币式车票
    public static String[] CARD_TYPE_TOKEN = new String[]{"0100","0101"}; 
    
    //乘次票
    public static String[] CARD_TYPE_TCT = new String[]{"0300","0301","0302"}; 
    
    public static String CARD_MAIN_TYPE_SK = "07";//异型储值票
    public static String CARD_MAIN_TYPE_PHONE = "08";//手机票
    
    //单程票类型代码12
    public static String TICKET_TYPE_SINGLE = "12";
    
    //定时检测通讯是否连接 睡眠时间
    public static long ES_COMMU_MONITOR_SLEEP_TIME = 30000;
    
    //卡工位
    public static final String SITE_NO_CARD = "0";//没卡
    public static final String SITE_HAVE_CARD = "1";//有卡
    public static final String SITE_GOOD_CARD = "2";//好卡
    public static final String SITE_BAD_CARD = "3";//坏卡
    
    //分币器工位
    public static final String HOPPER_EMPTYED_CARD = "0";//没卡
    public static final String HOPPER_EMPTYING_CARD = "1";//快没卡
    public static final String HOPPER_HAVE_CARD = "2";//有卡
    
    public static final int LEN_LOGICAL = 16;//逻辑卡号长度
    
    //位是否设置
    public static final String YES_ONE_SET = "1"; //是
    public static final String NO_ONE_SET = "0";  //否
    
    //MfCardTranKey
    public static final String MfCardTranKey = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
    public static final String MfCardTranKeyAllF = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
    public static final String MfCardTranKeyAll0 ="00000000000000000000000000000000";
    
    //卡商
    public static final String CARD_PRODUCER_HUAHONG = "8500";//华虹
    public static final String CARD_PRODUCER_HUADA = "8100";//华大
    
    //记名卡
    public static final byte SIGN_CARD_HOLDER_TYPE_PERSON = (byte)0x00;
    public static final byte SIGN_CARD_CARD_TYPE_COM = (byte)0x01;
    public static final byte SIGN_CARD_ID_TYPE_EMPLOYEE = (byte)0x00;
    
    public static final String PK_CITY_CODE = "8303";
    public static final String TK_CITY_BUSSINESS_CODE = "8303";
    public static final String TYPE_TEST_CODE1 = "0010";
    public static final String TYPE_TEST_CODE2 = "0000";
    public static final long DAY = 1000*60*60*24;
    public static  boolean IS_CHECK = false;
    public static List<String> CHECK_RECORD = new ArrayList<String>();
    public static String USER_NO="";
}
