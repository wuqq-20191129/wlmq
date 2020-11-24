/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
public class FileRecord00DetailBase extends FileRecordBase {

    /**
     * @return the commonType
     */
    public int getCommonType() {
        return commonType;
    }

    /**
     * @param commonType the commonType to set
     */
    public void setCommonType(int commonType) {
        this.commonType = commonType;
    }

    /**
     * @return the feeUnit
     */
    public int getFeeUnit() {
        return feeUnit;
    }

    /**
     * @param feeUnit the feeUnit to set
     */
    public void setFeeUnit(int feeUnit) {
        this.feeUnit = feeUnit;
    }

    /**
     * @return the payType
     */
    public String getPayType() {
        return payType;
    }

    /**
     * @param payType the payType to set
     */
    public void setPayType(String payType) {
        this.payType = payType;
    }
    private int fee; //金额
    private int feeUnit; //面值
    private int commonType; //类型
    private String cardMainId; //票卡主类型
    private String cardSubId; //票卡子类型
    private int num; //数量
    private String recordId;//记录标识
    private String payType;//支付方式

    /**
     * @return the fee
     */
    public int getFee() {
        return fee;
    }

    /**
     * @param fee the fee to set
     */
    public void setFee(int fee) {
        this.fee = fee;
    }

    /**
     * @return the cardMainId
     */
    public String getCardMainId() {
        return cardMainId;
    }

    /**
     * @param cardMainId the cardMainId to set
     */
    public void setCardMainId(String cardMainId) {
        this.cardMainId = cardMainId;
    }

    /**
     * @return the cardSubId
     */
    public String getCardSubId() {
        return cardSubId;
    }

    /**
     * @param cardSubId the cardSubId to set
     */
    public void setCardSubId(String cardSubId) {
        this.cardSubId = cardSubId;
    }

    /**
     * @return the num
     */
    public int getNum() {
        return num;
    }

    /**
     * @param num the num to set
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * @return the recordId
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

}
