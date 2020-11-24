/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.constant;

import com.goldsign.settle.realtime.frame.vo.CheckControlVo;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FrameCheckConstant {

    public static TreeSet<String> DEVICE_SAM_MAPPING = new TreeSet();//设备对照表
    public static TreeSet<String> CARD_SUB_TYPES = new TreeSet();//票卡子类型
    public static Vector<CheckControlVo> CHECK_CONTROLS = new Vector();//校验控制标识
    public static int CHK_TIME_MAX_BEFOREDAYS = 30;//距离当前时间最大天数
    public static String CHK_TIME_FORMAL_ONLINE = "20990101";//正式运营上线时间
    public static int CHK_FEE_MAX_BALANCE = 50000;//最大余额，单位分
    public static int CHK_FEE_MAX_DEAL = 50000;//最大交易金额，单位分
    public static int CHK_FEE_MAX_CHARGE = 50000;//最大单次充值金额，单位分
    public static int CHK_FEE_MAX_BUY_TK = 100000;//最大单次购单程票金额，单位分

    public static int CHK_FEE_MAX_BALANCE_CARD_OCT = 50000;//公交卡最大余额，单位分
    public static int CHK_FEE_MAX_DEAL_ZONE_OCT = 50000;//公交消费，最大交易金额，单位分
    public static int CHK_FEE_MAX_DEAL_ZONE_MAG = 50000;//磁浮消费，最大交易金额，单位分
    /**
     * 系统校验配置的键
     */
    public static String KEY_CHK_TIME_FORMAL_ONLINE = "chk.time.formal.online";//正式运营上线时间
    public static String KEY_CHK_TIME_MAX_BEFOREDAYS = "chk.time.maxbeforeday";//距离当前时间最大天数
    public static String KEY_CHK_FEE_MAX_BALANCE = "chk.fee.maxBalance";//最大余额，单位分
    public static String KEY_CHK_FEE_MAX_DEAL = "chk.fee.maxDealFee";//最大交易金额，单位分
    public static String KEY_CHK_FEE_MAX_CHARGE = "chk.fee.maxChargeFee";//最大单次充值金额，单位分
    public static String KEY_CHK_FEE_MAX_BALANCE_CARD_OCT = "chk.fee.maxBalance.card.oct";//公交卡最大余额，单位分
    public static String KEY_CHK_FEE_MAX_DEAL_ZONE_OCT = "chk.fee.maxDealFee.zone.oct";//公交消费最大交易金额，单位分
    public static String KEY_CHK_FEE_MAX_DEAL_ZONE_MAG = "chk.fee.maxDealFee.zone.mag";//磁浮线消费最大交易金额，单位分
    public static String KEY_CHK_FEE_MAX_BUY_TK = "chk.fee.maxBuyTk";//最大购单程票金额，单位分 20180930
    /**
     * 校验控制常数
     */
    public static String CHECK_FLAG_YES = "1";//需要校验
    public static String CHECK_FLAG_NO = "0";//不需要校验
    public static String CHECK_TRADE_ALL = "99";//所有必要的交易
    /**
     * 校验其他错误的相关常数***************
     */
    public static String CHK_UPDATE_AREA_FARE = "1";//1：付费区，2：非付费区
    public static String CHK_UPDATE_AREA_FARE_NONE = "2";
    public static String CHK_UPDATE_AREA_ERROR = "A";//乱码转换为A
    public static String[] CHK_UPDATE_AREA = {CHK_UPDATE_AREA_FARE, CHK_UPDATE_AREA_FARE_NONE};

    /**
     * 消费相关支付类型
     */
    public static String CONSUME_MTR_SPECIAL_PURSE = "12";//专用钱包地铁消费
    public static String CONSUME_MTR_OCT_PURSE = "42";//公交钱包地铁消费
    public static String CONSUME_MTR_OCT_FREE_PURSE = "92";//公交免费钱包地铁消费
    public static String CONSUME_BUS = "11";//公交消费
    public static String[] CONSUME_PAY_MODES = {CONSUME_MTR_SPECIAL_PURSE, CONSUME_MTR_OCT_PURSE, CONSUME_MTR_OCT_FREE_PURSE, CONSUME_BUS};

}
