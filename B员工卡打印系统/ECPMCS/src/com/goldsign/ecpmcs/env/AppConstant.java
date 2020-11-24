/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.ecpmcs.env;

import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.esmcs.serial.RwDeviceCommu;
import com.goldsign.ecpmcs.util.DbcpHelper;
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
    public static String DATABASE_USER_ST = "w_acc_st.";
    public static String DATABASE_USER_TK = "w_acc_tk.";
    
    public static final String RECORD_CURRENT = "3";//当前版本
    
    public static String STR_CERTIFICATE_TYPE = "1";//	证件类型
    public static String STR_CERTIFICATE_SEX = "2";//	性别
    public static String STR_ET_STATE = "3";//员工卡发行状态 
    public static String STR_EMPLOYEE_DEPARTMENT = "7";//单位
    public static String STR_EMPLOYEE_POSITION = "8";//职务
    public static String STR_EMPLOYEE_CLASS = "9";//职务
    
    public static String ISSUE_STATUS_ISSUED = "1";//已发行
    public static String ISSUE_STATUS_CANCEL = "2";//已注销
    
    public static final String ET_STATE_ISSUE = "0";//员工卡已发卡
    public static final String ET_STATE_RETURN = "1";//员工卡已退卡
    
    public static final String CERTIFICATE_TYPE_EMPLOYEE = "5";//员工证件类型
    public static final String TICKET_TYPE_EMPLOYEE = "0500";//员工证件类型
    
    public static byte RW_BUZZING_TIME_0 = (byte)0x00;//蜂鸣0次
    public static byte RW_BUZZING_TIME_1 = (byte)0x04;//蜂鸣1次
    
    public static final String CLASS_A_PHOYO_NAME = "APic.jpg";
    public static final String CLASS_B_PHOYO_NAME = "BPic.jpg";
    public static final String CLASS_C_PHOYO_NAME = "CPic.jpg";
    public static final String CLASS_PASS_PHOYO_NAME = "PassPic.jpg";
    public static final String EMPLOYEE_BACK_PHOTO_NAME = "employee_background.jpg";
    public static final String SINGCARD_PHOTO_NAME = "signcard.jpg";
    
    //性别 certificate_sex
    public static Map CERTIFICATE_SEX = new HashMap();
    //证件类型 certificate_type
    public static Map CERTIFICATE_TYPE = new HashMap();
    //员工卡发行状态
    public static Map ET_STATE = new HashMap();
    //票卡类型 cTicketType[4];	（取主类型+子类型）
    public static Map TICKET_TYPE = new HashMap();
    //员工卡部门
    public static Map EMPLOYEE_DEPARTMENT = new HashMap();
    // 员工职务
    public static Map EMPLOYEE_POSITION = new HashMap();
    // 员工级别
    public static Map EMPLOYEE_CLASS = new HashMap();
    //打印机错误代码
    public static Map PRINT_ERROR = new HashMap();
    //打印模板
    public static Map PRINT_TEMPLATE = new HashMap();
    
    public static final String TABLE_SCP_PARAMER = "w_ic_ecp_pub_flag";//系统参数表名
    public static final String TABLE_ET_PARAMER = "w_ic_et_pub_flag";//员工发卡系统参数表名
    public static final String TABLE_OP_CARD_SUB_TYPE = "w_op_prm_card_sub";//运营中心系统票卡子类型表名
    
    public static RwDeviceCommu rwDeviceCommu; //读写器串口

    public static final String STATUS_BAR_BATABASE_STATUS = "dataBaseStatus";
    public static final String STATUS_BAR_BATABASE_STATUS_NAME = "  数据库通讯:";
    public static boolean DATABASE_STATUS = false;
    public static final String STATUS_BAR_READER_PORT = "readerPort";
    public static final String STATUS_BAR_READER_PORT_NAME = "  读写头通讯:";
    public static boolean READER_PORT = false;
    public static final String STATUS_BAR_CURRENT_TIME = "currentTime";
    public static final String STATUS_BAR_CURRENT_TIME_NAME = "  当前时间:";  
    
    //Dao层查询返回结果代码
    public static final int DAO_RETURN_TRUE = 1;//result返回True
    public static final int DAO_RETURN_FALSE = 0;//result返回True
    public static final int DAO_RETURN_ERROR = -1;//异常
    
    public static final String OPRT_LOG_RESULT_SUC = "0";
    public static final String OPRT_LOG_RESULT_SUC_NAME = "成功";
    public static final String OPRT_LOG_RESULT_FAIL_NAME = "失败";
    public static final String OPRT_LOG_RESULT_FAIL = "1";
    
    //通讯心跳监控线程时间间隔 1分钟
    public static final int HEART_HEAT_TIME = 60000;
    
    //读文件插入数据库“remark”字段值
    public static String LOCAL_FILE_TO_FILE_REMARK = "读文件插入数据库";
    //本地文件保存后缀
    public static String LOCAL_FILE_TO_FILE_SUFFIX = ".txt";
    public static String LOCAL_FILE_TO_FILE_SUFFIX_BACK = ".bak";//本地文件无法完成插入操作时作备份文件处理
    
    public static String RW_SUCCESS_CODE = "00";//读写器成功代码
    
    
}
