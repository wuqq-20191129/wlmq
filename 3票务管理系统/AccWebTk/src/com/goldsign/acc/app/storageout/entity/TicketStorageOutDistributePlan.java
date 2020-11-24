package com.goldsign.acc.app.storageout.entity;

import java.util.List;

public class TicketStorageOutDistributePlan extends TicketStorageOutDistributePlanDetail{
    private String billNo;

    private String outBillNo;

    private String distributeBillNo;

    private String formMaker;

    private String billDate;

    private String recordFlag;
    
    private String recordFlagName;

    private String verifyDate;

    private String verifyPerson;

    private String receiveUnit;

    private String distributeMan;

    private String receiveMan;

    private String remark;
    
    private String beginDateTime;

    private String endDateTime;
    
    private String operType;
    
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

    public String getDistributeBillNo() {
        return distributeBillNo;
    }

    public void setDistributeBillNo(String distributeBillNo) {
        this.distributeBillNo = distributeBillNo == null ? null : distributeBillNo.trim();
    }

    public String getFormMaker() {
        return formMaker;
    }

    public void setFormMaker(String formMaker) {
        this.formMaker = formMaker == null ? null : formMaker.trim();
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag == null ? null : recordFlag.trim();
    }

    public String getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(String verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getVerifyPerson() {
        return verifyPerson;
    }

    public void setVerifyPerson(String verifyPerson) {
        this.verifyPerson = verifyPerson == null ? null : verifyPerson.trim();
    }

    public String getReceiveUnit() {
        return receiveUnit;
    }

    public void setReceiveUnit(String receiveUnit) {
        this.receiveUnit = receiveUnit == null ? null : receiveUnit.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRecordFlagName() {
        return recordFlagName;
    }

    public void setRecordFlagName(String recordFlagName) {
        this.recordFlagName = recordFlagName;
    }

    public String getBeginDateTime() {
        return beginDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setBeginDateTime(String beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getOperType() {
        return operType;
    }

    public List<String> getStorageIdList() {
        return storageIdList;
    }

    public void setStorageIdList(List<String> storageIdList) {
        this.storageIdList = storageIdList;
    }
    
    
}