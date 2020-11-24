/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.env;

/**
 *
 * @author Administrator
 */
public class LogConstant {

    public static String MESSAGE_ID_FILE_NOTICE = "50";//文件通知信息
    
    public static String MESSAGE_ID_OPER_TYPE_REQ = "30";//操作员登录请求
    public static String MESSAGE_ID_OPER_TYPE_RES = "31";//操作员登录应答
    public static String MESSAGE_ID_CITY_TYPE_REQ = "32";//城市代码查询请求
    public static String MESSAGE_ID_CITY_TYPE_RES = "33";//城市代码查询应答
    public static String MESSAGE_ID_ORDER_TYPE_REQ = "34";//订单查询请求
    public static String MESSAGE_ID_ORDER_TYPE_RES = "35";//订单查询应答
    public static String MESSAGE_ID_ORDER_STATE_REQ = "36";//更改订单状态请求
    public static String MESSAGE_ID_ORDER_STATE_RES = "37";//更改订单状态应答
    public static String MESSAGE_ID_SIGN_CARD_REQ = "38";//记名卡查询请求
    public static String MESSAGE_ID_SIGN_CARD_RES = "39";//记名卡查询应答  
    public static String MESSAGE_ID_CARD_PRICE_REQ = "40";//票价查询请求
    public static String MESSAGE_ID_CARD_PRICE_RES = "41";//票价查询应答
    public static String MESSAGE_ID_CARD_TYPE_REQ = "42";//票种查询请求
    public static String MESSAGE_ID_CARD_TYPE_RES = "43";//票种查询应答
    public static String MESSAGE_ID_DEVICE_STATE_REQ = "44";//设备状态变化请求
    public static String MESSAGE_ID_ES_FILE_REQ = "45";//ES文件请求
    public static String MESSAGE_ID_BLACK_LIST_REQ = "46";//黑名单查询请求
    public static String MESSAGE_ID_BLACK_LIST_RES = "47";//黑名单查询应答
    public static String MESSAGE_ID_SAM_CARD_REQ = "48";//SAM卡查询请求
    public static String MESSAGE_ID_SAM_CARD_RES = "49";//SAM卡查询应答
    //HWJ ADD 20160107 
    public static String MESSAGE_ID_CARD_SECTION_REQ = "60";//卡号段申请请求
    public static String MESSAGE_ID_CARD_SECTION_RES = "61";//卡号段申请应答
    
    public static String MESSAGE_ID_START = "101";//应用启动
    public static String MESSAGE_ID_CONFIG = "102";//配置设置
    public static String MESSAGE_ID_CONNECTION = "106";//SOCKET连接处理
    public static String MESSAGE_ID_SOCKET_EXCHAGE = "107";//SOCKET消息交换
    public static String MESSAGE_ID_FTP = "108";//FTP文件
    /**
     * 消息名称
     */
    public static String MESSAGE_ID_FILE_NOTICE_NAME = "文件通知信息";//文件通知信息
    public static String MESSAGE_ID_START_NAME = "应用启动";//应用启动
    public static String MESSAGE_ID_CONFIG_NAME = "配置设置";//配置设置
    public static String MESSAGE_ID_CONNECTION_NAME = "SOCKET连接";//SOCKET连接处理
    public static String MESSAGE_ID_SOCKET_EXCHAGE_NAME = "SOCKET消息交换";//SOCKET消息交换
    public static String MESSAGE_ID_FTP_NAME = "FTP文件";//FTP文件
    
    //消息的处理结果，记录日志使用
    public static String RESULT_HDL_SUCESS = "0";//成功
    public static String RESULT_HDL_FAIL = "1";//失败
    public static String RESULT_HDL_WARN = "2";//警告
}
