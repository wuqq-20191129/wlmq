package com.goldsign.acc.app.opma.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SignCardLossApplyHandleHis {
    private String cardPhysicalId;

    private String cardLogicalId;

    private String deviceId;

    private String dealDatetime;

    private String payModeId;

    private BigDecimal dealFee;

    private String receiptId;

    private String tac;

    private String lineId;
    
    private String lineText;

    private String stationId;
    
    private String stationText;

    private BigDecimal dealBalanceFee;

    private String balanceWaterNo;

    private String entryDatetime;

    private String dealType;
    
    private String dealTypeText;

    private String redFlag;

    private String cardMainId;

    private String cardSubId;

    private String genTime;

    private BigDecimal tctValidDays;

    private String tctActivateDatetime;

    private String currentBalanceWater;
    

    public String getCardPhysicalId() {
        return cardPhysicalId;
    }

    public void setCardPhysicalId(String cardPhysicalId) {
        this.cardPhysicalId = cardPhysicalId == null ? null : cardPhysicalId.trim();
    }

    public String getCardLogicalId() {
        return cardLogicalId;
    }

    public void setCardLogicalId(String cardLogicalId) {
        this.cardLogicalId = cardLogicalId == null ? null : cardLogicalId.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getDealDatetime() {
        return dealDatetime;
    }

    public void setDealDatetime(String dealDatetime) {
        this.dealDatetime = dealDatetime;
    }

    public String getPayModeId() {
        return payModeId;
    }

    public void setPayModeId(String payModeId) {
        this.payModeId = payModeId == null ? null : payModeId.trim();
    }

    public BigDecimal getDealFee() {
        return dealFee;
    }

    public void setDealFee(BigDecimal dealFee) {
        this.dealFee = dealFee;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId == null ? null : receiptId.trim();
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac == null ? null : tac.trim();
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId == null ? null : lineId.trim();
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
    }

    public BigDecimal getDealBalanceFee() {
        return dealBalanceFee;
    }

    public void setDealBalanceFee(BigDecimal dealBalanceFee) {
        this.dealBalanceFee = dealBalanceFee;
    }

    public String getBalanceWaterNo() {
        return balanceWaterNo;
    }

    public void setBalanceWaterNo(String balanceWaterNo) {
        this.balanceWaterNo = balanceWaterNo == null ? null : balanceWaterNo.trim();
    }

    public String getEntryDatetime() {
        return entryDatetime;
    }

    public void setEntryDatetime(String entryDatetime) {
        this.entryDatetime = entryDatetime;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType == null ? null : dealType.trim();
    }

    public String getRedFlag() {
        return redFlag;
    }

    public void setRedFlag(String redFlag) {
        this.redFlag = redFlag == null ? null : redFlag.trim();
    }

    public String getCardMainId() {
        return cardMainId;
    }

    public void setCardMainId(String cardMainId) {
        this.cardMainId = cardMainId == null ? null : cardMainId.trim();
    }

    public String getCardSubId() {
        return cardSubId;
    }

    public void setCardSubId(String cardSubId) {
        this.cardSubId = cardSubId == null ? null : cardSubId.trim();
    }

    public String getGenTime() {
        return genTime;
    }

    public void setGenTime(String genTime) {
        this.genTime = genTime;
    }

    public BigDecimal getTctValidDays() {
        return tctValidDays;
    }

    public void setTctValidDays(BigDecimal tctValidDays) {
        this.tctValidDays = tctValidDays;
    }

    public String getTctActivateDatetime() {
        return tctActivateDatetime;
    }

    public void setTctActivateDatetime(String tctActivateDatetime) {
        this.tctActivateDatetime = tctActivateDatetime;
    }

    public String getCurrentBalanceWater() {
        return currentBalanceWater;
    }

    public void setCurrentBalanceWater(String currentBalanceWater) {
        this.currentBalanceWater = currentBalanceWater == null ? null : currentBalanceWater.trim();
    }

    public String getLineText() {
        return lineText;
    }

    public String getStationText() {
        return stationText;
    }

    public void setLineText(String lineText) {
        this.lineText = lineText;
    }

    public void setStationText(String stationText) {
        this.stationText = stationText;
    }

    public String getDealTypeText() {
        return dealTypeText;
    }

    public void setDealTypeText(String dealTypeText) {
        this.dealTypeText = dealTypeText;
    }
    
}