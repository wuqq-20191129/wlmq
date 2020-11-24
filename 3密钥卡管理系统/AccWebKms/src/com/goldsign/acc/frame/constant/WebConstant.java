/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.constant;

/**
 *
 * @author hejj
 */
public class WebConstant {

    /**
     * 请求、会话参数名称
     */
    public static String PARM_USER = "User";//登录账户参数名称
    public static String PARM_MODULE_ID = "ModuleID";//登录模块名称
    public static String PARM_MODULE_PRIVILEDGES = "ModulePrilivedges";//权限集合
    public static String PARM_MODULE_PRIVILEDGE = "ModulePrilivedge";//模块权限
    /**
     * 查询相关名称
     */

    public static String PARM_REQ_LINE_NAME="_lineName";
    public static String PARM_REQ_STATION_NAME="_stationName";
    public static String PARM_REQ_CARD_MAIN_NAME="_mainCardName";
    public static String PARM_REQ_CARD_SUB_NAME="_subCardName";
    public static String PARM_REQ_VAR_STATION_NAME="_stationCommonVariable";
    public static String PARM_REQ_VAR_CARD_SUB_NAME="_subCardCommonVariable";
    
    public static String ATT_QUY_CONTROL_DEFAULT_VALUE="ControlDefaultValues";
    
    public static String ATT_QUY_LINE_NAME="QueryLineName";
    public static String ATT_QUY_STATION_NAME="QueryStationName";
    public static String ATT_QUY_CARD_MAIN_NAME="QueryMainCardName";
    public static String ATT_QUY_CARD_SUB_NAME="QuerySubCardName";
    public static String ATT_QUY_VAR_STATION="StationCommonVariable";
    public static String ATT_QUY_VAR_CARD_SUB="SubCardCommonVariable";

    

    public static String ATT_MESSAGE = "Message";//返回消息
    public static String ATT_ResultSet = "ResultSet";//返回结果集
    
    public static String SYS_VERSION = "1.00";//系统版本号

}
