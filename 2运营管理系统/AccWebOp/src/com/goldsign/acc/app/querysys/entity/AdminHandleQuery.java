package com.goldsign.acc.app.querysys.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AdminHandleQuery {
    private Integer waterNo;

    private String lineId;

    private String lineText;

    private String stationId;

    private String stationText;

    private String devTypeId;

    private String devTypeText;

    private String deviceId;

    private String adminDatetime;

    private String adminWayId;

    private String adminWayText;

    private String cardMainId;

    private String cardMainText;

    private String cardSubId;

    private String cardSubText;

    private BigDecimal returnFee;

    private BigDecimal penaltyFee;

    private String adminReasonId;

    private String adminReasonText;

    private String describe;

    private String passengerName;

    private String operatorId;

    private String shiftId;

    private String balanceWaterNo;

    private String fileName;

    private String checkFlag;

    private String beginDatetime;

    private String endDatetime;

    public Integer getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(Integer waterNo) {
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

    public String getAdminDatetime() {
        return adminDatetime;
    }

    public void setAdminDatetime(String adminDatetime) {
        this.adminDatetime = adminDatetime;
    }

    public String getAdminWayId() {
        return adminWayId;
    }

    public void setAdminWayId(String adminWayId) {
        this.adminWayId = adminWayId == null ? null : adminWayId.trim();
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

    public BigDecimal getReturnFee() {
        return returnFee;
    }

    public void setReturnFee(BigDecimal returnFee) {
        this.returnFee = returnFee;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(BigDecimal penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public String getAdminReasonId() {
        return adminReasonId;
    }

    public void setAdminReasonId(String adminReasonId) {
        this.adminReasonId = adminReasonId == null ? null : adminReasonId.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName == null ? null : passengerName.trim();
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId == null ? null : shiftId.trim();
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

    public String getBeginDatetime() {
        return beginDatetime;
    }

    public String getEndDatetime() {
        return endDatetime;
    }

    public void setBeginDatetime(String beginDatetime) {
        this.beginDatetime = beginDatetime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
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

    public String getCardMainText() {
        return cardMainText;
    }

    public String getCardSubText() {
        return cardSubText;
    }

    public String getAdminReasonText() {
        return adminReasonText;
    }

    public void setCardMainText(String cardMainText) {
        this.cardMainText = cardMainText;
    }

    public void setCardSubText(String cardSubText) {
        this.cardSubText = cardSubText;
    }

    public void setAdminReasonText(String adminReasonText) {
        this.adminReasonText = adminReasonText;
    }

	public String getDevTypeText() {
		return devTypeText;
	}

	public void setDevTypeText(String devTypeText) {
		this.devTypeText = devTypeText;
	}

	public String getAdminWayText() {
		return adminWayText;
	}

	public void setAdminWayText(String adminWayText) {
		this.adminWayText = adminWayText;
	}



}