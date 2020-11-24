package com.goldsign.acc.app.prminfo.entity;

import java.util.Date;
/**
 *
 * @author:zhongzqi
 * @create date: 2017-6-7
 */
public class BlackListMtr {
 
    private String cardLogicalId;

    private String genDatetime;

    private String blackType;
    
    private String blackTypeText;

    private String remark;

    private String actionType;

    private String createDatetime;

    private String operatorId;

    private String isSet;
    
    private String balanceWaterNo;

    private String handleDatetime;

    private String handleOperatorId;

    private String handleType;
    
    private String beginEffectiveTime;
    
    private String endEffectiveTime;

    public void setBlackTypeText(String blackTypeText) {
        this.blackTypeText = blackTypeText;
    }

    public String getBlackTypeText() {
        return blackTypeText;
    }

    public String getCardLogicalId() {
        return cardLogicalId;
    }

    public void setCardLogicalId(String cardLogicalId) {
        this.cardLogicalId = cardLogicalId == null ? null : cardLogicalId.trim();
    }

    public String getBlackType() {
        return blackType;
    }

    public void setBlackType(String blackType) {
        this.blackType = blackType == null ? null : blackType.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType == null ? null : actionType.trim();
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    public String getIsSet() {
        return isSet;
    }

    public void setGenDatetime(String genDatetime) {
        this.genDatetime = genDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getGenDatetime() {
        return genDatetime;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setIsSet(String isSet) {
        this.isSet = isSet == null ? null : isSet.trim();
    }

    public String getBalanceWaterNo() {
        return balanceWaterNo;
    }

    public String getHandleDatetime() {
        return handleDatetime;
    }

    public String getHandleOperatorId() {
        return handleOperatorId;
    }

    public String getHandleType() {
        return handleType;
    }

    public void setBalanceWaterNo(String balanceWaterNo) {
        this.balanceWaterNo = balanceWaterNo;
    }

    public void setHandleDatetime(String handleDatetime) {
        this.handleDatetime = handleDatetime;
    }

    public void setHandleOperatorId(String handleOperatorId) {
        this.handleOperatorId = handleOperatorId;
    }

    public void setHandleType(String handleType) {
        this.handleType = handleType;
    }

    public String getBeginEffectiveTime() {
        return beginEffectiveTime;
    }

    public String getEndEffectiveTime() {
        return endEffectiveTime;
    }

    public void setBeginEffectiveTime(String beginEffectiveTime) {
        this.beginEffectiveTime = beginEffectiveTime;
    }

    public void setEndEffectiveTime(String endEffectiveTime) {
        this.endEffectiveTime = endEffectiveTime;
    }
    
}