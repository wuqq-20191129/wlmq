package com.goldsign.acc.app.storageout.entity;

import java.util.List;

public class TicketStorageOutDestroyOutBill extends TicketStorageOutDestroyOutBillDetail{
    private String billNo;

    private String formMaker;

    private String billDate;

    private String drawer;

    private String administer;

    private String accounter;

    private String distributeBillNo;

    private String recordFlag;
    
    private String recordFlagName;

    private String verifyDate;

    private String verifyPerson;

    private String remark;

    private String billType;

    private String inFlag;
    
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

    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer == null ? null : drawer.trim();
    }

    public String getAdminister() {
        return administer;
    }

    public void setAdminister(String administer) {
        this.administer = administer == null ? null : administer.trim();
    }

    public String getAccounter() {
        return accounter;
    }

    public void setAccounter(String accounter) {
        this.accounter = accounter == null ? null : accounter.trim();
    }

    public String getDistributeBillNo() {
        return distributeBillNo;
    }

    public void setDistributeBillNo(String distributeBillNo) {
        this.distributeBillNo = distributeBillNo == null ? null : distributeBillNo.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public String getInFlag() {
        return inFlag;
    }

    public void setInFlag(String inFlag) {
        this.inFlag = inFlag == null ? null : inFlag.trim();
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

    public String getRecordFlagName() {
        return recordFlagName;
    }

    public void setRecordFlagName(String recordFlagName) {
        this.recordFlagName = recordFlagName;
    }

    public List<String> getStorageIdList() {
        return storageIdList;
    }

    public void setStorageIdList(List<String> storageIdList) {
        this.storageIdList = storageIdList;
    }
    
}