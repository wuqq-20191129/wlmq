package com.goldsign.acc.app.querysys.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TicketCardDealHis {

    private String dealType;

    private String logiId;

    private String dealTime;

    private String lineId;

    private String stationId;

    private BigDecimal dealAmount;

    private BigDecimal balance;

    private String deviceId;

    private String devTypeId;
    
    private String cardMainCode;

    private String cardSubCode;

    private String operatorId;
    
    private String limitMonth;

    private BigDecimal busCounter;

    private BigDecimal metroCounter;

    private BigDecimal unionCounter;

    private String expiredTk;
    
    private String beginDatetime;
    
    private String endDatetime;
    
    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType == null ? null : dealType.trim();
    }


    public String getLogiId() {
        return logiId;
    }


    public void setLogiId(String logiId) {
        this.logiId = logiId == null ? null : logiId.trim();
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
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
    
    public BigDecimal getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(BigDecimal dealAmount) {
        this.dealAmount = dealAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(String devTypeId) {
        this.devTypeId = devTypeId == null ? null : devTypeId.trim();
    }

    public String getCardMainCode() {
        return cardMainCode;
    }

    public void setCardMainCode(String cardMainCode) {
        this.cardMainCode = cardMainCode == null ? null : cardMainCode.trim();
    }

    public String getCardSubCode() {
        return cardSubCode;
    }

    public void setCardSubCode(String cardSubCode) {
        this.cardSubCode = cardSubCode == null ? null : cardSubCode.trim();
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    public String getLimitMonth() {
        return limitMonth;
    }

    public void setLimitMonth(String limitMonth) {
        this.limitMonth = limitMonth == null ? null : limitMonth.trim();
    }

    public BigDecimal getBusCounter() {
        return busCounter;
    }

    public void setBusCounter(BigDecimal busCounter) {
        this.busCounter = busCounter;
    }

    public BigDecimal getMetroCounter() {
        return metroCounter;
    }

    public void setMetroCounter(BigDecimal metroCounter) {
        this.metroCounter = metroCounter;
    }

    public BigDecimal getUnionCounter() {
        return unionCounter;
    }
    
    public void setUnionCounter(BigDecimal unionCounter) {
        this.unionCounter = unionCounter;
    }

    public String getExpiredTk() {
        return expiredTk;
    }

    public void setExpiredTk(String expiredTk) {
        this.expiredTk = expiredTk == null ? null : expiredTk.trim();
    }

    public void setBeginDatetime(String beginDatetime) {
        this.beginDatetime = beginDatetime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getBeginDatetime() {
        return beginDatetime;
    }

    public String getEndDatetime() {
        return endDatetime;
    }
    
    
}