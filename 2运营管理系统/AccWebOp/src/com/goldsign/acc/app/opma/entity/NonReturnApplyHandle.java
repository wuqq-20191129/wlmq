package com.goldsign.acc.app.opma.entity;

import com.goldsign.acc.frame.util.DateHelper;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

public class NonReturnApplyHandle {

    private Integer waterNo;

    private String lineId;

    private String stationId;
    
    private String stationText;

    private String devTypeId;

    private String deviceId;

    private String cardMainId;

    private String cardSubId;

    private String cardLogicalId;

    private String cardPhysicalId;

    private String cardPrintId;

    private String applyDatetime;

    private String receiptId;

    private String operatorId;

    private String applyName;

    private String telNo;

    private String identityType;

    private String identityId;

    private String isBroken;

    private String brokenText;

    private String shiftId;

    private String cardAppFlag;

    private String balanceWaterNo;

    private String hdlFlag;

    private String hdlFlagText;

    private BigDecimal depositFee;

    private BigDecimal cardBalanceFee;

    private BigDecimal systemBalanceFee;

    private BigDecimal penaltyFee;

    private BigDecimal returnBala;

    private String remark;

    private String returnLineId;

    private String returnStationId;

    private String returnDevTypeId;

    private String returnDeviceId;

    private BigDecimal actualReturnBala;

    private String auditFlag;
    
    private String auditFlagText;

    private String penaltyReason;
    
    private String penaltyReasonText;

    private String returnType;

    private String businessReceiptId;

    private String beginDatetime;

    private String endDatetime;
    
    private String handleType;
    
    private String applyDays;
    
    private String minReturnDate;
    
    private String appliedFlag;
    
    private String blackCardFlag;
    
    private String isHandled;
    
    //到点未处理标志 判断依据当前时间>申请时间+等待时间且未处理
    private String nonHandleFlag;

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

    public String getCardLogicalId() {
        return cardLogicalId;
    }

    public void setCardLogicalId(String cardLogicalId) {
        this.cardLogicalId = cardLogicalId == null ? null : cardLogicalId.trim();
    }

    public String getCardPhysicalId() {
        return cardPhysicalId;
    }

    public void setCardPhysicalId(String cardPhysicalId) {
        this.cardPhysicalId = cardPhysicalId == null ? null : cardPhysicalId.trim();
    }

    public String getCardPrintId() {
        return cardPrintId;
    }

    public void setCardPrintId(String cardPrintId) {
        this.cardPrintId = cardPrintId == null ? null : cardPrintId.trim();
    }

    public String getApplyDatetime() {
        return applyDatetime;
    }

    public void setApplyDatetime(String applyDatetime) {
        this.applyDatetime = applyDatetime;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId == null ? null : receiptId.trim();
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName == null ? null : applyName.trim();
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo == null ? null : telNo.trim();
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

    public String getIsBroken() {
        return isBroken;
    }

    public void setIsBroken(String isBroken) {
        this.isBroken = isBroken == null ? null : isBroken.trim();
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

    public String getHdlFlag() {
        return hdlFlag;
    }

    public void setHdlFlag(String hdlFlag) {
        this.hdlFlag = hdlFlag == null ? null : hdlFlag.trim();
    }

    public BigDecimal getDepositFee() {
        return depositFee;
    }

    public void setDepositFee(BigDecimal depositFee) {
        this.depositFee = depositFee;
    }

    public BigDecimal getCardBalanceFee() {
        return cardBalanceFee;
    }

    public void setCardBalanceFee(BigDecimal cardBalanceFee) {
        this.cardBalanceFee = cardBalanceFee;
    }

    public BigDecimal getSystemBalanceFee() {
        return systemBalanceFee;
    }

    public void setSystemBalanceFee(BigDecimal systemBalanceFee) {
        this.systemBalanceFee = systemBalanceFee;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(BigDecimal penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public BigDecimal getReturnBala() {
        return returnBala;
    }

    public void setReturnBala(BigDecimal returnBala) {
        this.returnBala = returnBala;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getReturnLineId() {
        return returnLineId;
    }

    public void setReturnLineId(String returnLineId) {
        this.returnLineId = returnLineId == null ? null : returnLineId.trim();
    }

    public String getReturnStationId() {
        return returnStationId;
    }

    public void setReturnStationId(String returnStationId) {
        this.returnStationId = returnStationId == null ? null : returnStationId.trim();
    }

    public String getReturnDevTypeId() {
        return returnDevTypeId;
    }

    public void setReturnDevTypeId(String returnDevTypeId) {
        this.returnDevTypeId = returnDevTypeId == null ? null : returnDevTypeId.trim();
    }

    public String getReturnDeviceId() {
        return returnDeviceId;
    }

    public void setReturnDeviceId(String returnDeviceId) {
        this.returnDeviceId = returnDeviceId == null ? null : returnDeviceId.trim();
    }

    public BigDecimal getActualReturnBala() {
        return actualReturnBala;
    }

    public void setActualReturnBala(BigDecimal actualReturnBala) {
        this.actualReturnBala = actualReturnBala;
    }

    public String getAuditFlag() {
        return auditFlag;
    }

    public void setAuditFlag(String auditFlag) {
        this.auditFlag = auditFlag == null ? null : auditFlag.trim();
    }

    public String getPenaltyReason() {
        return penaltyReason;
    }

    public void setPenaltyReason(String penaltyReason) {
        this.penaltyReason = penaltyReason == null ? null : penaltyReason.trim();
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType == null ? null : returnType.trim();
    }

    public String getBusinessReceiptId() {
        return businessReceiptId;
    }

    public void setBusinessReceiptId(String businessReceiptId) {
        this.businessReceiptId = businessReceiptId == null ? null : businessReceiptId.trim();
    }


    public String getBrokenText() {
        return brokenText;
    }

    public String getHdlFlagText() {
        return hdlFlagText;
    }

    public String getPenaltyReasonText() {
        return penaltyReasonText;
    }

    public String getBeginDatetime() {
        return beginDatetime;
    }

    public String getEndDatetime() {
        return endDatetime;
    }



    public void setBrokenText(String brokenText) {
        this.brokenText = brokenText;
    }

    public void setHdlFlagText(String hdlFlagText) {
        this.hdlFlagText = hdlFlagText;
    }

    public void setPenaltyReasonText(String penaltyReasonText) {
        this.penaltyReasonText = penaltyReasonText;
    }

    public void setBeginDatetime(String beginDatetime) {
        this.beginDatetime = beginDatetime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getHandleType() {
        return handleType;
    }

    public void setHandleType(String handleType) {
        this.handleType = handleType;
    }

    public String getStationText() {
        return stationText;
    }

    public void setStationText(String stationText) {
        this.stationText = stationText;
    }

    public String getApplyDays() throws ParseException {
        if (applyDatetime == null || applyDatetime.isEmpty()) {
            return "";
        } else {
            return DateHelper.daysBetweenTwo(DateHelper.stringToDate(applyDatetime), new Date()) + "";
        }
    }

    public String getAuditFlagText() {
        return auditFlagText;
    }

    public void setAuditFlagText(String auditFlagText) {
        this.auditFlagText = auditFlagText;
    }

    public String getMinRetrunDate() {
        return minReturnDate;
    }

    public void setApplyDays(String applyDays) {
        this.applyDays = applyDays;
    }

    public void setMinRetrunDate(String minRetrunDate) {
        this.minReturnDate = minRetrunDate;
    }

    public String getMinReturnDate() {
        return minReturnDate;
    }

    public String getAppliedFlag() {
        return appliedFlag;
    }

    public void setMinReturnDate(String minReturnDate) {
        this.minReturnDate = minReturnDate;
    }

    public void setAppliedFlag(String appliedFlag) {
        this.appliedFlag = appliedFlag;
    }

    public String getBlackCardFlag() {
        return blackCardFlag;
    }

    public void setBlackCardFlag(String blackCardFlag) {
        this.blackCardFlag = blackCardFlag;
    }

    public String getIsHandled() {
        return isHandled;
    }

    public void setIsHandled(String isHandled) {
        this.isHandled = isHandled;
    }

	public String getNonHandleFlag() {
		return nonHandleFlag;
	}

	public void setNonHandleFlag(String nonHandleFlag) {
		this.nonHandleFlag = nonHandleFlag;
	}
    
    
}
