package com.goldsign.acc.app.querysys.entity;

import java.math.BigDecimal;

public class NonReturnOpLog {
    private String operatorId;
    
    private String operatorName;

    private String operateTime;

    private String operateType;
    
    private String operateTypeText;

    private String receiptId;

    private String cardPrintId;

    private BigDecimal cardBalanceFee;

    private String isBroken;
    
    private String brokenText;

    private String remark;

    private BigDecimal depositFee;

    private BigDecimal penaltyFee;

    private BigDecimal actualReturnBala;

    private String penaltyReason;
    
    private String penaltyReasonText;

    private String hdlFlag;
    
    private String hdlFlagText;

    private String oldNo;

    private String identityType;
    
    private String identityTypeText;

    private String identityId;

    private String returnType;
    
    private String beginDatetime;
    
    private String endDatetime;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType == null ? null : operateType.trim();
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId == null ? null : receiptId.trim();
    }

    public String getCardPrintId() {
        return cardPrintId;
    }

    public void setCardPrintId(String cardPrintId) {
        this.cardPrintId = cardPrintId == null ? null : cardPrintId.trim();
    }

    public BigDecimal getCardBalanceFee() {
        return cardBalanceFee;
    }

    public void setCardBalanceFee(BigDecimal cardBalanceFee) {
        this.cardBalanceFee = cardBalanceFee;
    }

    public String getIsBroken() {
        return isBroken;
    }

    public void setIsBroken(String isBroken) {
        this.isBroken = isBroken == null ? null : isBroken.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getDepositFee() {
        return depositFee;
    }

    public void setDepositFee(BigDecimal depositFee) {
        this.depositFee = depositFee;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(BigDecimal penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public BigDecimal getActualReturnBala() {
        return actualReturnBala;
    }

    public void setActualReturnBala(BigDecimal actualReturnBala) {
        this.actualReturnBala = actualReturnBala;
    }

    public String getPenaltyReason() {
        return penaltyReason;
    }

    public void setPenaltyReason(String penaltyReason) {
        this.penaltyReason = penaltyReason == null ? null : penaltyReason.trim();
    }

    public String getHdlFlag() {
        return hdlFlag;
    }

    public void setHdlFlag(String hdlFlag) {
        this.hdlFlag = hdlFlag == null ? null : hdlFlag.trim();
    }

    public String getOldNo() {
        return oldNo;
    }

    public void setOldNo(String oldNo) {
        this.oldNo = oldNo == null ? null : oldNo.trim();
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

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType == null ? null : returnType.trim();
    }

    public String getIdentityTypeText() {
        return identityTypeText;
    }

    public String getBeginDatetime() {
        return beginDatetime;
    }

    public String getEndDatetime() {
        return endDatetime;
    }

    public void setIdentityTypeText(String identityTypeText) {
        this.identityTypeText = identityTypeText;
    }

    public void setBeginDatetime(String beginDatetime) {
        this.beginDatetime = beginDatetime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getHdlFlagText() {
        return hdlFlagText;
    }

    public void setHdlFlagText(String hdlFlagText) {
        this.hdlFlagText = hdlFlagText;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperateTypeText() {
        return operateTypeText;
    }

    public void setOperateTypeText(String operateTypeText) {
        this.operateTypeText = operateTypeText;
    }

    public String getBrokenText() {
        return brokenText;
    }

    public String getPenaltyReasonText() {
        return penaltyReasonText;
    }

    public void setBrokenText(String brokenText) {
        this.brokenText = brokenText;
    }

    public void setPenaltyReasonText(String penaltyReasonText) {
        this.penaltyReasonText = penaltyReasonText;
    }
    
    
}