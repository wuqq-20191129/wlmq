package com.goldsign.acc.app.basicinfo.entity;

import java.io.Serializable;
import java.util.Date;

public class TicketStorageSignCard implements Serializable {
    private String reqNo;

    private Long waterNo;

    private String lineId;
    
    private String lineIdText;

    private String stationId;
    
    private String stationIdText;

   

    private String devTypeId;

    private String deviceId;

    private String applyName;

    private String applySex;

    private String identityType;

    private String identityId;

    private Date expiredDate;

    private String strExpDate;
    
    private String telNo;

    private String fax;

    private String address;

    private String operatorId;

    private Date applyDatetime;
    
    private String strAppDate;   

    private String shiftId;

    private String cardAppFlag;

    private String balanceWaterNo;

    private String fileName;

    private String checkFlag;

    private Date hdlTime;

    private String hdlFlag;

    private String orderNo;

    private String cardSubId;
    
    private String cardSubIdText;

    private String cardMainId;
    
    private String cardMainIdText;

    private String appBusinessType;
    
    private String beginTime;
    
    private String endTime;

   

    private static final long serialVersionUID = 1L;

    public TicketStorageSignCard(String reqNo, Long waterNo, String lineId, String stationId, String devTypeId, String deviceId, String applyName, String applySex, String identityType, String identityId, Date expiredDate, String telNo, String fax, String address, String operatorId, Date applyDatetime, String shiftId, String cardAppFlag, String balanceWaterNo, String fileName, String checkFlag, Date hdlTime, String hdlFlag, String orderNo, String cardSubId, String cardMainId, String appBusinessType) {
        this.reqNo = reqNo;
        this.waterNo = waterNo;
        this.lineId = lineId;
        this.stationId = stationId;
        this.devTypeId = devTypeId;
        this.deviceId = deviceId;
        this.applyName = applyName;
        this.applySex = applySex;
        this.identityType = identityType;
        this.identityId = identityId;
        this.expiredDate = expiredDate;
        this.telNo = telNo;
        this.fax = fax;
        this.address = address;
        this.operatorId = operatorId;
        this.applyDatetime = applyDatetime;
        this.shiftId = shiftId;
        this.cardAppFlag = cardAppFlag;
        this.balanceWaterNo = balanceWaterNo;
        this.fileName = fileName;
        this.checkFlag = checkFlag;
        this.hdlTime = hdlTime;
        this.hdlFlag = hdlFlag;
        this.orderNo = orderNo;
        this.cardSubId = cardSubId;
        this.cardMainId = cardMainId;
        this.appBusinessType = appBusinessType;
    }
    
     public String getLineIdText() {
        return lineIdText;
    }

    public void setLineIdText(String lineIdText) {
        this.lineIdText = lineIdText;
    }

    public String getStationIdText() {
        return stationIdText;
    }

    public void setStationIdText(String stationIdText) {
        this.stationIdText = stationIdText;
    }

    public String getCardSubIdText() {
        return cardSubIdText;
    }

    public void setCardSubIdText(String cardSubIdText) {
        this.cardSubIdText = cardSubIdText;
    }

    public String getCardMainIdText() {
        return cardMainIdText;
    }

    public void setCardMainIdText(String cardMainIdText) {
        this.cardMainIdText = cardMainIdText;
    }
    
     public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
     public String getStrExpDate() {
        return strExpDate;
    }

    public void setStrExpDate(String strExpDate) {
        this.strExpDate = strExpDate;
    }

    public String getStrAppDate() {
        return strAppDate;
    }

    public void setStrAppDate(String strAppDate) {
        this.strAppDate = strAppDate;
    }

    public TicketStorageSignCard() {
        super();
    }

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo == null ? null : reqNo.trim();
    }

    public Long getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(Long waterNo) {
        this.waterNo = waterNo;
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

    public String getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(String devTypeId) {
        this.devTypeId = devTypeId == null ? null : devTypeId.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName == null ? null : applyName.trim();
    }

    public String getApplySex() {
        return applySex;
    }

    public void setApplySex(String applySex) {
        this.applySex = applySex == null ? null : applySex.trim();
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType == null ? null : identityType.trim();
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId == null ? null : identityId.trim();
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo == null ? null : telNo.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    public Date getApplyDatetime() {
        return applyDatetime;
    }

    public void setApplyDatetime(Date applyDatetime) {
        this.applyDatetime = applyDatetime;
    }

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId == null ? null : shiftId.trim();
    }

    public String getCardAppFlag() {
        return cardAppFlag;
    }

    public void setCardAppFlag(String cardAppFlag) {
        this.cardAppFlag = cardAppFlag == null ? null : cardAppFlag.trim();
    }

    public String getBalanceWaterNo() {
        return balanceWaterNo;
    }

    public void setBalanceWaterNo(String balanceWaterNo) {
        this.balanceWaterNo = balanceWaterNo == null ? null : balanceWaterNo.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag == null ? null : checkFlag.trim();
    }

    public Date getHdlTime() {
        return hdlTime;
    }

    public void setHdlTime(Date hdlTime) {
        this.hdlTime = hdlTime;
    }

    public String getHdlFlag() {
        return hdlFlag;
    }

    public void setHdlFlag(String hdlFlag) {
        this.hdlFlag = hdlFlag == null ? null : hdlFlag.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getCardSubId() {
        return cardSubId;
    }

    public void setCardSubId(String cardSubId) {
        this.cardSubId = cardSubId == null ? null : cardSubId.trim();
    }

    public String getCardMainId() {
        return cardMainId;
    }

    public void setCardMainId(String cardMainId) {
        this.cardMainId = cardMainId == null ? null : cardMainId.trim();
    }

    public String getAppBusinessType() {
        return appBusinessType;
    }

    public void setAppBusinessType(String appBusinessType) {
        this.appBusinessType = appBusinessType == null ? null : appBusinessType.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TicketStorageSignCard other = (TicketStorageSignCard) that;
        return (this.getReqNo() == null ? other.getReqNo() == null : this.getReqNo().equals(other.getReqNo()))
            && (this.getWaterNo() == null ? other.getWaterNo() == null : this.getWaterNo().equals(other.getWaterNo()))
            && (this.getLineId() == null ? other.getLineId() == null : this.getLineId().equals(other.getLineId()))
            && (this.getStationId() == null ? other.getStationId() == null : this.getStationId().equals(other.getStationId()))
            && (this.getDevTypeId() == null ? other.getDevTypeId() == null : this.getDevTypeId().equals(other.getDevTypeId()))
            && (this.getDeviceId() == null ? other.getDeviceId() == null : this.getDeviceId().equals(other.getDeviceId()))
            && (this.getApplyName() == null ? other.getApplyName() == null : this.getApplyName().equals(other.getApplyName()))
            && (this.getApplySex() == null ? other.getApplySex() == null : this.getApplySex().equals(other.getApplySex()))
            && (this.getIdentityType() == null ? other.getIdentityType() == null : this.getIdentityType().equals(other.getIdentityType()))
            && (this.getIdentityId() == null ? other.getIdentityId() == null : this.getIdentityId().equals(other.getIdentityId()))
            && (this.getExpiredDate() == null ? other.getExpiredDate() == null : this.getExpiredDate().equals(other.getExpiredDate()))
            && (this.getTelNo() == null ? other.getTelNo() == null : this.getTelNo().equals(other.getTelNo()))
            && (this.getFax() == null ? other.getFax() == null : this.getFax().equals(other.getFax()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getOperatorId() == null ? other.getOperatorId() == null : this.getOperatorId().equals(other.getOperatorId()))
            && (this.getApplyDatetime() == null ? other.getApplyDatetime() == null : this.getApplyDatetime().equals(other.getApplyDatetime()))
            && (this.getShiftId() == null ? other.getShiftId() == null : this.getShiftId().equals(other.getShiftId()))
            && (this.getCardAppFlag() == null ? other.getCardAppFlag() == null : this.getCardAppFlag().equals(other.getCardAppFlag()))
            && (this.getBalanceWaterNo() == null ? other.getBalanceWaterNo() == null : this.getBalanceWaterNo().equals(other.getBalanceWaterNo()))
            && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
            && (this.getCheckFlag() == null ? other.getCheckFlag() == null : this.getCheckFlag().equals(other.getCheckFlag()))
            && (this.getHdlTime() == null ? other.getHdlTime() == null : this.getHdlTime().equals(other.getHdlTime()))
            && (this.getHdlFlag() == null ? other.getHdlFlag() == null : this.getHdlFlag().equals(other.getHdlFlag()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getCardSubId() == null ? other.getCardSubId() == null : this.getCardSubId().equals(other.getCardSubId()))
            && (this.getCardMainId() == null ? other.getCardMainId() == null : this.getCardMainId().equals(other.getCardMainId()))
            && (this.getAppBusinessType() == null ? other.getAppBusinessType() == null : this.getAppBusinessType().equals(other.getAppBusinessType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getReqNo() == null) ? 0 : getReqNo().hashCode());
        result = prime * result + ((getWaterNo() == null) ? 0 : getWaterNo().hashCode());
        result = prime * result + ((getLineId() == null) ? 0 : getLineId().hashCode());
        result = prime * result + ((getStationId() == null) ? 0 : getStationId().hashCode());
        result = prime * result + ((getDevTypeId() == null) ? 0 : getDevTypeId().hashCode());
        result = prime * result + ((getDeviceId() == null) ? 0 : getDeviceId().hashCode());
        result = prime * result + ((getApplyName() == null) ? 0 : getApplyName().hashCode());
        result = prime * result + ((getApplySex() == null) ? 0 : getApplySex().hashCode());
        result = prime * result + ((getIdentityType() == null) ? 0 : getIdentityType().hashCode());
        result = prime * result + ((getIdentityId() == null) ? 0 : getIdentityId().hashCode());
        result = prime * result + ((getExpiredDate() == null) ? 0 : getExpiredDate().hashCode());
        result = prime * result + ((getTelNo() == null) ? 0 : getTelNo().hashCode());
        result = prime * result + ((getFax() == null) ? 0 : getFax().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getOperatorId() == null) ? 0 : getOperatorId().hashCode());
        result = prime * result + ((getApplyDatetime() == null) ? 0 : getApplyDatetime().hashCode());
        result = prime * result + ((getShiftId() == null) ? 0 : getShiftId().hashCode());
        result = prime * result + ((getCardAppFlag() == null) ? 0 : getCardAppFlag().hashCode());
        result = prime * result + ((getBalanceWaterNo() == null) ? 0 : getBalanceWaterNo().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getCheckFlag() == null) ? 0 : getCheckFlag().hashCode());
        result = prime * result + ((getHdlTime() == null) ? 0 : getHdlTime().hashCode());
        result = prime * result + ((getHdlFlag() == null) ? 0 : getHdlFlag().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getCardSubId() == null) ? 0 : getCardSubId().hashCode());
        result = prime * result + ((getCardMainId() == null) ? 0 : getCardMainId().hashCode());
        result = prime * result + ((getAppBusinessType() == null) ? 0 : getAppBusinessType().hashCode());
        return result;
    }
}