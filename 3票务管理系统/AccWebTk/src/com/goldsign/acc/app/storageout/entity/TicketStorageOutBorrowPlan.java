package com.goldsign.acc.app.storageout.entity;

import java.util.List;

public class TicketStorageOutBorrowPlan extends TicketStorageOutBorrowPlanDetail{

    private String billNo;

    private String outBillNo;

    private String formMaker;

    private String distributeMan;

    private String receiveMan;

    private String billDate;

    private String unitId;
    
    private String unitName;

    private String returnFlag;

    private String verifyPerson;

    private String verifyDate;

    private String recordFlag;

    private String remark;
    
    private String recordFlagName;
    
    private String beginDateTime;

    private String endDateTime;
    
    //权限仓库列表
    private List<String> storageIdList;

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getOutBillNo() {
        return outBillNo;
    }

    public void setOutBillNo(String outBillNo) {
        this.outBillNo = outBillNo == null ? null : outBillNo.trim();
    }

    public void setFormMaker(String formMaker) {
        this.formMaker = formMaker;
    }

    public String getFormMaker() {
        return formMaker;
    }

    public String getDistributeMan() {
        return distributeMan;
    }

    public void setDistributeMan(String distributeMan) {
        this.distributeMan = distributeMan == null ? null : distributeMan.trim();
    }

    public String getReceiveMan() {
        return receiveMan;
    }

    public void setReceiveMan(String receiveMan) {
        this.receiveMan = receiveMan == null ? null : receiveMan.trim();
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public String getReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag(String returnFlag) {
        this.returnFlag = returnFlag == null ? null : returnFlag.trim();
    }

    public String getVerifyPerson() {
        return verifyPerson;
    }

    public void setVerifyPerson(String verifyPerson) {
        this.verifyPerson = verifyPerson == null ? null : verifyPerson.trim();
    }

    public String getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(String verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag == null ? null : recordFlag.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRecordFlagName() {
        return recordFlagName;
    }

    public String getBeginDateTime() {
        return beginDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setRecordFlagName(String recordFlagName) {
        this.recordFlagName = recordFlagName;
    }

    public void setBeginDateTime(String beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<String> getStorageIdList() {
        return storageIdList;
    }

    public void setStorageIdList(List<String> storageIdList) {
        this.storageIdList = storageIdList;
    }

   
    
    
}
