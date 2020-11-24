/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.vo;

/**
 *
 * @author lenovo
 */
public class OrderTypeRspVo {

    private String orderNo;
    private String cardType;
    private String cardName;
    private String cardEffTime;
    private Long printValue;
    private Long depValue;
    private String startReqNo;
    private String endReqNo;
    private String startSeq;
    private String endSeq;
    private String makeDate;
    private Long quantity;
    private String signCode;
    private String lineCode;
    private String stationCode;
    private String ccEffStartTime;
    private String ccEffUseTime;
    private String limitOutLineCode;
    private String limitOutStationCode;
    private String limitMode;
    private Long maxRecharge;//充值上限
    private String saleFlag;//销售标记
    private String testFlag;//测试标记
    private String esSeriakNo;//ES设备号

    public String getEsSeriakNo() {
        return esSeriakNo;
    }

    public void setEsSeriakNo(String esSeriakNo) {
        this.esSeriakNo = esSeriakNo;
    }

    /**
     * @return the orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return the cardType
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * @param cardType the cardType to set
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    /**
     * @return the cardName
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * @param cardName the cardName to set
     */
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    /**
     * @return the cardEffTime
     */
    public String getCardEffTime() {
        return cardEffTime;
    }

    /**
     * @param cardEffTime the cardEffTime to set
     */
    public void setCardEffTime(String cardEffTime) {
        this.cardEffTime = cardEffTime;
    }

    /**
     * @return the printValue
     */
    public Long getPrintValue() {
        return printValue;
    }

    /**
     * @param printValue the printValue to set
     */
    public void setPrintValue(Long printValue) {
        this.printValue = printValue;
    }

    /**
     * @return the depValue
     */
    public Long getDepValue() {
        return depValue;
    }

    /**
     * @param depValue the depValue to set
     */
    public void setDepValue(Long depValue) {
        this.depValue = depValue;
    }

    /**
     * @return the startReqNo
     */
    public String getStartReqNo() {
        return startReqNo;
    }

    /**
     * @param startReqNo the startReqNo to set
     */
    public void setStartReqNo(String startReqNo) {
        this.startReqNo = startReqNo;
    }

    /**
     * @return the endReqNo
     */
    public String getEndReqNo() {
        return endReqNo;
    }

    /**
     * @param endReqNo the endReqNo to set
     */
    public void setEndReqNo(String endReqNo) {
        this.endReqNo = endReqNo;
    }

    /**
     * @return the startSeq
     */
    public String getStartSeq() {
        return startSeq;
    }

    /**
     * @param startSeq the startSeq to set
     */
    public void setStartSeq(String startSeq) {
        this.startSeq = startSeq;
    }

    /**
     * @return the endSeq
     */
    public String getEndSeq() {
        return endSeq;
    }

    /**
     * @param endSeq the endSeq to set
     */
    public void setEndSeq(String endSeq) {
        this.endSeq = endSeq;
    }

    /**
     * @return the makeDate
     */
    public String getMakeDate() {
        return makeDate;
    }

    /**
     * @param makeDate the makeDate to set
     */
    public void setMakeDate(String makeDate) {
        this.makeDate = makeDate;
    }

    /**
     * @return the quantity
     */
    public Long getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the signCode
     */
    public String getSignCode() {
        return signCode;
    }

    /**
     * @param signCode the signCode to set
     */
    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

    /**
     * @return the lineCode
     */
    public String getLineCode() {
        return lineCode;
    }

    /**
     * @param lineCode the lineCode to set
     */
    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    /**
     * @return the stationCode
     */
    public String getStationCode() {
        return stationCode;
    }

    /**
     * @param stationCode the stationCode to set
     */
    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    /**
     * @return the ccEffStartTime
     */
    public String getCcEffStartTime() {
        return ccEffStartTime;
    }

    /**
     * @param ccEffStartTime the ccEffStartTime to set
     */
    public void setCcEffStartTime(String ccEffStartTime) {
        this.ccEffStartTime = ccEffStartTime;
    }

    /**
     * @return the ccEffUseTime
     */
    public String getCcEffUseTime() {
        return ccEffUseTime;
    }

    /**
     * @param ccEffUseTime the ccEffUseTime to set
     */
    public void setCcEffUseTime(String ccEffUseTime) {
        this.ccEffUseTime = ccEffUseTime;
    }

    /**
     * @return the limitOutLineCode
     */
    public String getLimitOutLineCode() {
        return limitOutLineCode;
    }

    /**
     * @param limitOutLineCode the limitOutLineCode to set
     */
    public void setLimitOutLineCode(String limitOutLineCode) {
        this.limitOutLineCode = limitOutLineCode;
    }

    /**
     * @return the limitOutStationCode
     */
    public String getLimitOutStationCode() {
        return limitOutStationCode;
    }

    /**
     * @param limitOutStationCode the limitOutStationCode to set
     */
    public void setLimitOutStationCode(String limitOutStationCode) {
        this.limitOutStationCode = limitOutStationCode;
    }

    /**
     * @return the limitMode
     */
    public String getLimitMode() {
        return limitMode;
    }

    /**
     * @param limitMode the limitMode to set
     */
    public void setLimitMode(String limitMode) {
        this.limitMode = limitMode;
    }

    public Long getMaxRecharge() {
        return maxRecharge;
    }

    public void setMaxRecharge(Long maxRecharge) {
        this.maxRecharge = maxRecharge;
    }

    public String getSaleFlag() {
        return saleFlag;
    }

    public void setSaleFlag(String saleFlag) {
        this.saleFlag = saleFlag;
    }

    public String getTestFlag() {
        return testFlag;
    }

    public void setTestFlag(String testFlag) {
        this.testFlag = testFlag;
    }
    
}
