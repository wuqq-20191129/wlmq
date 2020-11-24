/*
 * 文件名：RuleConstant
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.acc.frame.constant;

import java.util.Vector;


/*
 * 业务级别常量
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-8-8
 */

public class RuleConstant {
    
    //生效线路
    public static Vector LINES = new Vector();
    //生效站点
    public static Vector STATIONS = new Vector();
    
    //返回页面结果集名称常量
    public static String RESULT_PROPORTIONS = "distanceProportions";     //线路里程权重比例
    public static String RESULT_OD_DISTANCE = "odDistances";     //OD路径
    public static String RESULT_OD_DISTANCE_DETAIL = "distanceDetail";     //OD路径详细
    public static String RESULT_PARAMS = "params";     //参数设置
    public static String RESULT_PARAMS_THRES = "paramsThres";     //阀值参数设置
    public static String RESULT_PARAMSSTATION = "paramsStation";     //站点里程设置
    public static String RESULT_LINESTATION = "lineStation";     //参数设置

    public static String RESULT_LINES = "lines";     //线路
    public static String RESULT_STATIONS = "stations";     //车站
    public static String RESULT_RECORD_FLAG = "recordFlags";    //参数状态
   
    public static final String RESULT_TYPE_CODE = "typeCode";//类型代码
    public static final String RESULT_CODE = "Code";//参数代码
    //end 返回页面结果集名称常量
   
    
    //参数类型 sr_params_sys
    public static String PARAMS_VERSION = "30";   // 版本标志 类型
    public static String PARAMS_IS_VALID = "02";    //是否有效
    
    //参数类型 是否有效
    public static String IS_VALID_YES = "1";    //有效
    public static String IS_VALID_NO = "0";    //有效
    
    public static String RECORD_FLAG_USE = "0";    //当前版本
    public static String RECORD_FLAG_FUTURE = "1";    //未来版本
    public static String RECORD_FLAG_HISTORY = "2";    //历史参数
    public static String RECORD_FLAG_DRAFT = "3";    //草稿参数
    public static String RECORD_FLAG_FUTURE_DEL = "4";    //未来参数删除标志
    public static String RECORD_FLAG_USE_DEL = "5";    //当前参数删除标志
    
}
