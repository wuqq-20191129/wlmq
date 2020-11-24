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
public class PubFlagConstant {

    public static final int PUB_FLAG_TYPE_BILL_STATE = 1;//单据状态 
    public static final int PUB_FLAG_TYPE_NEW_OLD_CARD_FLAG = 2;//新旧票标识
    public static final int PUB_FLAG_TYPE_MONEY = 3;//面值
    public static final int PUB_FLAG_TYPE_TEST_FLAG = 4;//测试标志
    public static final int PUB_FLAG_TYPE_ORDER_TYPE = 5;//订单类型
    public static final int PUB_FLAG_TYPE_MODE = 6;//乘次票限制模式

    public static final int PUB_FLAG_TYPE_SIGN_CARD = 7;//记名卡标志
    public static final int PUB_FLAG_TYPE_ORDER_HANDLE_FLAG = 8;//订单执行状态
    public static final int PUB_FLAG_TYPE_ORDER_SALE_FLAG = 9;//发售标识
    public static final int PUB_FLAG_TYPE_USELESS_CARD_TYPE = 11;//废票遗失票类型
    
    public static int PUB_FLAG_TYPE_RESTRICTFLAG = 15;//是否限制本站标志
    public static int PUB_FLAG_TYPE_PARA_FLAG = 24;//流失量参数
    
    public static int PUB_FLAG_TYPE_USE_FLAG = 14;//票卡生产类别
    
    public static final String PARAM_TYPE_ID_STATION = "0201";// 车站参数类型
    public static final String PARAM_TYPE_ID_LINE = "9004"; // 线路参数类型
    
    public static final String TYPE_BILL_NO_BC_LOGIC_NO_TEMP = "BC";//临时空白卡管理
    public static final String TYPE_BILL_NO_BC_LOGIC_NO = "BC";//空白卡管理
    public static final int LEN_BILL_NO_TEMP = 12;//临时单据长度
    public static final int LEN_BILL_NO = 12;//单据长度
    

}
