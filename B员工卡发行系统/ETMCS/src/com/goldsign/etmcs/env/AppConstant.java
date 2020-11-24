/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.env;

import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.esmcs.serial.RwDeviceCommu;
import com.goldsign.etmcs.util.DbcpHelper;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * 应用常量
 * 
 * @author lenovo
 */
public class AppConstant extends BaseConstant{
    
    public static Hashtable configs = null; //配置文件内容
    
    public static DbcpHelper dbcpHelper = null; //数据库连接池
    
    public static RwDeviceCommu rwDeviceCommu; //读写器串口

    public static final String STATUS_BAR_SITE_NO = "siteNo";
    public static final String STATUS_BAR_SITE_NAME = "  站点编号:";
    public static final String STATUS_BAR_KMS_STATUS = "kmsStatus";
    public static final String STATUS_BAR_KMS_STATUS_NAME = "  加密机通讯:";
    public static boolean KMS_STATUS = false;
    public static final String STATUS_BAR_COMMU_STATUS = "commuStatus";
    public static final String STATUS_BAR_COMMU_STATUS_NAME = "  ES通讯:";
    public static boolean COMMU_STATUS = false;
    public static final String STATUS_BAR_BATABASE_STATUS = "dataBaseStatus";
    public static final String STATUS_BAR_BATABASE_STATUS_NAME = "  数据库通讯:";
    public static boolean DATABASE_STATUS = false;
    public static final String STATUS_BAR_READER_PORT = "readerPort";
    public static final String STATUS_BAR_READER_PORT_NAME = "  读写器通讯:";
    public static boolean READER_PORT = false;
    public static final String STATUS_BAR_CURRENT_TIME = "currentTime";
    public static final String STATUS_BAR_CURRENT_TIME_NAME = "  当前时间:";  
    
    //Dao层查询返回结果代码
    public static final int DAO_RETURN_TRUE = 1;//result返回True
    public static final int DAO_RETURN_FALSE = 0;//result返回True
    public static final int DAO_RETURN_ERROR = -1;//异常
    
    public static final String GNEDER_MAN = "2";
    public static final String GNEDER_MAN_NAME = "女";
    public static final String GNEDER_WOMAN = "1";
    public static final String GNEDER_WOMAN_NAME = "男";
    
    public static final String ET_STATE_ISSUE = "0";
    public static final String ET_STATE_ISSUE_NAME = "已发卡";
    public static final String ET_STATE_RETURN = "1";
    public static final String ET_STATE_RETURN_NAME = "已退卡";
    public static final String ET_STATE_REPEAT = "2";
    public static final String ET_STATE_REPEAT_NAME = "重复卡";
    
    public static final String OPRT_LOG_RESULT_SUC = "0";
    public static final String OPRT_LOG_RESULT_SUC_NAME = "成功";
    public static final String OPRT_LOG_RESULT_FAIL_NAME = "失败";
    public static final String OPRT_LOG_RESULT_FAIL = "1";
    
    public static final String IS_BEE_CB_TURE = "1";//蜂鸣
    public static final String IS_BEE_CB_FALSE = "0";//不蜂鸣
    
    //通讯心跳监控线程时间间隔 1分钟
    public static final int HEART_HEAT_TIME = 60000;
    
    //读文件插入数据库“remark”字段值
    public static String MAKE_CARD_TO_FILE_REMARK = "读文件插入数据库";
    //本地文件保存后缀
    public static String MAKE_CARD_TO_FILE_SUFFIX = ".txt";
    public static String MAKE_CARD_TO_FILE_SUFFIX_BACK = ".bak";//本地文件无法完成插入操作时作备份文件处理
    
    //写卡个性化信息
    public static byte WRITE_CARD_HOLDER_TYPE_PERSON = (byte)0x00;//个人
    public static byte WRITE_CARD_CARD_TYPE_COM = (byte)0x01;//通卡员工卡
    public static byte WRITE_CARD_ID_TYPE_EMPLOYEE = (byte)0x05;//工卡
    
    public static byte RW_BUZZING_TIME_0 = (byte)0x00;//蜂鸣0次
    public static byte RW_BUZZING_TIME_1 = (byte)0x04;//蜂鸣1次
    
    public static String RW_SUCCESS_CODE = "00";//读写器成功代码
    
    public static String KMS_SUCCESS_CODE = "00";//加密机成功代码
    
    //发行状态
    public static Map ISSUE_STATUS = new HashMap();
    public static String ISSUE_STATUS_ISSUED = "1";//已发行
    public static String ISSUE_STATUS_CANCEL = "2";//已注销
    
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
    public static int DEPART_POSITION_CLASS_COUNT = 99;
    
    //线路 cLine[2]
    public static Map C_LINE = new HashMap();
    
    //站点 cStationNo[2]
    public static Map C_STATION_NO= new HashMap();
    
    //限制模式 cLimitMode[3]
    public static Map C_LIMIT_MODE = new HashMap();
    
    public static final String TABLE_C_LINE = "w_op_prm_line";//线路表名
    public static final String TABLE_C_STATION = "w_op_prm_station";//站点表名
    public static final String TABLE_ET_PARAMER = "w_ic_et_pub_flag";//员工发卡系统参数表名
    public static final String TABLE_OP_PUB_FLAG = "w_op_cod_pub_flag";//运营中心系统参数表名
    public static final String TABLE_OP_CARD_MAIN_TYPE = "w_op_prm_card_main";//运营中心系统票卡主类型表名
    
    public static String STR_ISSUE_STATUS = "0";//	发行状态
    public static String STR_CERTIFICATE_TYPE = "1";//	证件类型
    public static String STR_CERTIFICATE_ISMETRO = "2";//	本单位职工
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
    
    //设置制卡查询中列名及列宽
    //列名称
    public static  String[] MAKE_CARD_COLUMN_NAMES = {"工号", "姓名", "性别", "逻辑卡号", "受理人", "受理时间", "类型", "单位", "职务", "级别"};
    //列宽度
    public static  int[] MAKE_CARD_COLUMN_SIZES = {80, 160, 60, 160, 80, 140, 50, 160, 160, 60};
    
    //设置制卡统计中列名及列宽
    //列名称
    public static  String[] MAKE_CARD_STA_COLUMN_NAMES = { "开始时间","结束时间", "类型","数量"};
    //列宽度
    public static  int[] MAKE_CARD_STA_COLUMN_SIZES = {200, 200, 200, 200};
    
}
