/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.sammcs.env;

import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.sammcs.util.ConfigUtil;
import com.goldsign.sammcs.util.DbcpHelper;
import java.util.Hashtable;

/**
 * 应用常量
 * 
 * @author lenovo
 */
public class AppConstant extends BaseConstant{
    
    public static final String VERSION = "V1.0.0";

    public static Hashtable configs = null; //配置文件内容
    
    public static DbcpHelper dbcpHelper = null; //数据库连接池
    
    public static final String STATUS_BAR_SITE_NO = "siteNo";
    public static final String STATUS_BAR_SITE_NAME = "  站点编号:";
    public static final String STATUS_BAR_COMMU_STATUS = "commuStatus";
    public static final String STATUS_BAR_COMMU_STATUS_NAME = "  通讯状态:";
    public static boolean COMMU_STATUS = false;
    public static final String STATUS_BAR_CURRENT_TIME = "currentTime";
    public static final String STATUS_BAR_CURRENT_TIME_NAME = "  当前时间:";
    public static final String STATUS_BAR_KMS_STATUS = "kmsStatus";
    public static final String STATUS_BAR_KMS_STATUS_NAME = "  加密机通讯:";
    public static boolean KMS_STATUS = false;
    
    //Dao层查询返回结果代码
    public static final int DAO_RETURN_TRUE = 1;//result返回True
    public static final int DAO_RETURN_FALSE = 0;//result返回True
    public static final int DAO_RETURN_ERROR = -1;//异常
    
    //产品类型
    public static final String PRODUCE_TYPE_EMPTY = "00";//00:空白卡
    public static final String PRODUCE_TYPE_PRODUCT = "01";//01:成品卡
    //end 产品类型
    
    //是否损坏
    public static final String SAM_CARD_STATE_BAD = "0";//0:坏卡
    public static final String SAM_CARD_STATE_OK = "1";//1:好卡
    //end 是否损坏
    
    //制卡结果
    public static final String SAM_CARD_MAKE_RESULT_FAIL = "0";//0:失败
    public static final String SAM_CARD_MAKE_RESULT_FAIL_NAME = "失败";
    public static final String SAM_CARD_MAKE_RESULT_SUCCESS = "1";//1:成功
    public static final String SAM_CARD_MAKE_RESULT_SUCCESS_NAME = "成功";
    //end 制卡结果
    
    //完成标志 
    public static final String FINISH_FLAG_ALL_NOT_COMPLETE= "0";
    public static final String FINISH_FLAG_ALL_NOT_COMPLETE_NAME= "全未完成";
    public static final String FINISH_FLAG_PART_COMPLETE= "1";
    public static final String FINISH_FLAG_PART_COMPLETE_NAME= "部分完成";
    public static final String FINISH_FLAG_ALL_COMPLETE= "2";
    public static final String FINISH_FLAG_ALL_COMPLETE_NAME= "全已完成";
    
    //工作类型  20190918 limingjin
    public static final String WORK_TYPE_ISSUE= "SCDD";
    public static final String WORK_TYPE_ISSUE_NAME= "发行";
    public static final String WORK_TYPE_RESET= "CZDD";
    public static final String WORK_TYPE_RESET_NAME= "重置";
    //end 完成标志
    
    //库存状态
//    public static final String STOCK_STATE_EMPTY_IN = "00";//00:空白卡入库
//    public static final String STOCK_STATE_PRODUCE_ORDER = "01";//01:生产计划单
//    public static final String STOCK_STATE_ISSUE_OUT = "02";//02:卡发行出库
//    public static final String STOCK_STATE_PRODUCT_IN = "03";//03:成品卡入库
//    public static final String STOCK_STATE_DISTR_OUT = "04";//04:卡分发出库
//    public static final String STOCK_STATE_RECYCLE_IN = "05";//05:卡回收入库
    public static final String STOCK_STATE_MAKE_CARD = "06";//06:卡制作
    
    public static final String GNEDER_MAN = "0";
    public static final String GNEDER_MAN_NAME = "男";
    public static final String GNEDER_WOMAN = "1";
    public static final String GNEDER_WOMAN_NAME = "女";
    public static final String GNEDER_UNKNOWN = "2";
    public static final String GNEDER_UNKNOWN_NAME = "未知";
    
    public static final String ET_STATE_ISSUE = "0";
    public static final String ET_STATE_ISSUE_NAME = "已发卡";
    public static final String ET_STATE_RETURN = "1";
    public static final String ET_STATE_RETURN_NAME = "已退卡";
    
    public static final String OPRT_LOG_RESULT_SUC = "0";
    public static final String OPRT_LOG_RESULT_SUC_NAME = "成功";
    public static final String OPRT_LOG_RESULT_FAIL_NAME = "失败";
    public static final String OPRT_LOG_RESULT_FAIL = "1";
    
    //通讯心跳监控线程时间间隔 1分钟
    public static final int HEART_HEAT_TIME = 60000;
    
    //读文件插入数据库“remark”字段值
    public static String MAKE_CARD_TO_FILE_REMARK = "读文件插入数据库";
    //本地文件保存后缀
    public static String MAKE_CARD_TO_FILE_SUFFIX = ".txt";
    public static String MAKE_CARD_TO_FILE_SUFFIX_BACK = ".bak";//本地文件无法完成插入操作时作备份文件处理
    
    public static String KMS_SUCCESS_CODE = "00";//加密机成功代码
    public static String KMS_BAUD_RATE_FAILED_CODE = "02"; //加密代码 波特率设置失败
    public static String KMS_CARD_NOT_FOUND_CODE = "03"; //加密代码 卡复位失败（寻不到卡或卡没有插好）
    public static String KMS_ACHIEVE_MICROCHIP_FAILED_CODE = "A0";//加密代码 PSAM获取芯片号失败
    public static String KMS_MF0015_CODE = "01";//MF 0015获取失败
    public static String KMS_PLUGIN_CARD_CODE = "F5";//加密代码 读写器打开失败
    
    
    //单据状态
    public static final String RECORD_FLAG_UNAUDITED = "0";//未审核
    public static final String RECORD_FLAG_AUDITED = "1";//审核
    
    //操作日志 操作类型
    public static final String OPRT_LOG_OPER_TYPE_READY_WRITE_CARD_NAME = "准备写卡";
    public static final String OPRT_LOG_OPER_TYPE_WRITE_DB_NAME = "写库";
    
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDD_X = "yyyy/MM/dd";
    
    //设置制卡查询中列名及列宽
    public static  String[] COLUMN_NAMES = {"生产单号", "SAM类型", "逻辑卡号", "制作人", "制卡时间", "制卡结果", "备注"};
    public static  int[] COLUMN_SIZES = {150, 100, 150, 100, 150, 80, 200};
    
}
