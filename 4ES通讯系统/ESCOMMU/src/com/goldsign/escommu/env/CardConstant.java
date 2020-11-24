/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.env;

/**
 *
 * @author Administrator
 */
public class CardConstant {

    public static final String CARD_TYPE_SJT = "01";//单程票
    public static final String CARD_TYPE_SVT = "02";//储值票
    public static final String CARD_TYPE_TCT = "03";//乘次票
    public static final String CARD_TYPE_CT = "04";//纪念票
    public static final String CARD_TYPE_ET = "05";//员工票
   
    public static final int LEN_LOGICAL = 20;//逻辑卡号长度
    public static final String[] CARD_TYPE_LOGICAL_EFFECT = {
        CARD_TYPE_SJT, CARD_TYPE_SVT, CARD_TYPE_CT, CARD_TYPE_ET, CARD_TYPE_TCT
    };
}
