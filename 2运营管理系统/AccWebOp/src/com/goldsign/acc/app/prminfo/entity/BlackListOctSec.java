package com.goldsign.acc.app.prminfo.entity;

import java.util.Date;

public class BlackListOctSec extends BlackListOctSecKey {

    private String genDatetime;

    private String blackType;

    private String blackTypeText;
    
    private String remark;

    private String actionType;

    private String createDatetime;

    private String operatorId;

    private String isSet;
    
    private String beginEffectiveTime;
    
    private String endEffectiveTime;

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

    public String getGenDatetime() {
        return genDatetime;
    }

    public void setGenDatetime(String genDatetime) {
        this.genDatetime = genDatetime;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
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

    public void setIsSet(String isSet) {
        this.isSet = isSet == null ? null : isSet.trim();
    }

    public String getBlackTypeText() {
        return blackTypeText;
    }

    public void setBlackTypeText(String blackTypeText) {
        this.blackTypeText = blackTypeText;
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
