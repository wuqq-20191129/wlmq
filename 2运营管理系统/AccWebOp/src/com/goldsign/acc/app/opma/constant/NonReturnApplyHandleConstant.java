/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.constant;

/**
 * @desc:查询条件标志 对应W_OP_COD_PUB_FLAG表中TYPE 63 64
 * @author:zhongzqi
 * @create date: 2017-6-27
 */
public class NonReturnApplyHandleConstant {

    /**
     * 按印刻卡号
     */
    public final static String QUERY_BY_PRINT_ID = "3";
    /**
     * 按交易流水(按凭证号)
     */
    public final static String QUERY_BY_BUSINESS_RECEIPT_ID = "0";
    /**
     * 已处理
     */
    public final static String QUERY_BY_HANDLED = "1";
    /**
     * 未处理
     */
    public final static String QUERY_BY_NOT_HANDLED = "2";
    /**
     * 按证件号
     */
    public final static String QUERY_BY_CREDENTIALS = "4";

    public final static String FLAG_FALSE = "false";

    public final static String FLAG_TRUE = "true";

    public final static int BUSINESS_RECEIPT_ID_LENGTH = 25;

    public final static String FLAG_NO = "0";

    public final static String FLAG_YES = "1";

    public final static String FLAG_BLACK_CARD = "1";

    public final static String FLAG_NORMAL_CARD = "0";
    //车票未处理状态
    public final static String FLAG_TICKET_NO_HANDLE = "2";

    //导出全部条件变量第一个
    public final static String EXPORT_EXCEL_VO_KEY_FIRST = "queryCondition";
    //导出全部条件变量第二个
    public final static String EXPORT_EXCEL_VO_KEY_SECOND = "queryCondition1";

}
