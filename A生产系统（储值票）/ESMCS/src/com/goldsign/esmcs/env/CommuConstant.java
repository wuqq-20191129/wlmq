/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.env;

/**
 * 通讯常量
 * 
 * @author lenovo
 */
public class CommuConstant extends AppConstant{

    //报文类型
    public static final String MSG_OPER_LOGIN = "30";
    public static final String MSG_PARAM_QUERY = "32";
    public static final String MSG_ORDER_QUERY = "34";
    public static final String MSG_ORDER_UPDATE = "36";
    public static final String MSG_SIGN_CARD = "38";
    public static final String MSG_TICKET_PRICE = "40";
    public static final String MSG_TICKET_TYPE = "42";
    public static final String MSG_DEVICE_UPDATE = "44";
    public static final String MSG_ORDER_UPLOAD = "45";
    public static final String MSG_CARD_BLACKLIST = "46";
    public static final String MSG_SAM_LIST = "48";
    public static final String MSG_KMS_LOCK = "98";
    
    //操作员信息
    public static final String OPER_LOGIN_FLAG = "1";
    public static final String OPER_LOGOUT_FLAG = "2";
    public static final String OPER_TYPE_ADMIN = "071";
    public static final String OPER_TYPE_ADMIN_NAME = "管理员";
    public static final String OPER_TYPE_COMM = "072";
    public static final String OPER_TYPE_COMM_NAME = "制票操作员";
    public static final String OPER_TYPE_MEMD = "073";
    public static final String OPER_TYPE_MEMD_NAME = "ES维护人员";
    
    //登录报文结果
    public static final String OPER_CODE_ERROR = "004";
    public static final String OPER_CODE_ERROR_NAME = "操作员代码错";
    public static final String OPER_PWD_ERROR = "005";
    public static final String OPER_PWD_ERROR_NAME = "操作员密码出错";
    public static final String OPER_LGININ_ERROR = "999";
    public static final String OPER_LOGIN_ERROR_NAME = "该操作员已登录";
    public static final String OPER_LOGOUT_OK = "000";
    public static final String OPER_LOGOUT_OK_NAME = "操作员正常退出";
    
    //操作员类型
    public static final String[] OPER_TYPE_CODE_SUCC = {OPER_TYPE_ADMIN, OPER_TYPE_COMM, OPER_TYPE_MEMD};
    
    public static final String RESULT_CODE_NDT = "0101";   //成功，数据长度为0
    public static final String RESULT_CODE_DTA = "0100";   //成功，数据长度不为0
    public static final String RESULT_CODE_SUC = "0";   //成功
     
    public static final String UPDATE_ORDER_FLAG_SUCC = "0";//更新订单成功标志
}
