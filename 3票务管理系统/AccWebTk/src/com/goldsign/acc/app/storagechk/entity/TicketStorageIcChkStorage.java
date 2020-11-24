package com.goldsign.acc.app.storagechk.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TicketStorageIcChkStorage implements Serializable {
    private String checkBillNo;

    private String checkPerson;

    private Date checkDate;

    private Date verifyDate;

    private String verifyPerson;

    private String recordFlag;
    
    private String recordFlagText;

    public String getRecordFlagText() {
        return recordFlagText;
    }

    public void setRecordFlagText(String recordFlagText) {
        this.recordFlagText = recordFlagText;
    }

    private String locked;

    private String inLocked;
    
    private String icMainType;
    
    private String icMainTypeText;

    private String icSubType;
    
    private String icSubTypeText;
    
    private String storageId;
    
    private String storageIdText;

    private String areaId;
    
    private String areaIdText;

    public String getIcMainTypeText() {
        return icMainTypeText;
    }

    public void setIcMainTypeText(String icMainTypeText) {
        this.icMainTypeText = icMainTypeText;
    }

    public String getIcSubTypeText() {
        return icSubTypeText;
    }

    public void setIcSubTypeText(String icSubTypeText) {
        this.icSubTypeText = icSubTypeText;
    }

    public String getStorageIdText() {
        return storageIdText;
    }

    public void setStorageIdText(String storageIdText) {
        this.storageIdText = storageIdText;
    }

    public String getAreaIdText() {
        return areaIdText;
    }

    public void setAreaIdText(String areaIdText) {
        this.areaIdText = areaIdText;
    }
    
    private String startChkDate;    
    
    private String endChkDate;
    
    public String getStartChkDate() {
        return startChkDate;
    }

    public void setStartChkDate(String startChkDate) {
        this.startChkDate = startChkDate;
    }

    public String getEndChkDate() {
        return endChkDate;
    }

    public void setEndChkDate(String endChkDate) {
        this.endChkDate = endChkDate;
    }

    public String getIcMainType() {
        return icMainType;
    }

    public void setIcMainType(String icMainType) {
        this.icMainType = icMainType;
    }

    public String getIcSubType() {
        return icSubType;
    }

    public void setIcSubType(String icSubType) {
        this.icSubType = icSubType;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
    
    private List<TicketStorageIcChkStorageDetailKey> icChkStorageDetails;

    public List<TicketStorageIcChkStorageDetailKey> getIcChkStorageDetails() {
        return icChkStorageDetails;
    }

    public void setIcChkStorageDetails(List<TicketStorageIcChkStorageDetailKey> icChkStorageDetails) {
        this.icChkStorageDetails = icChkStorageDetails;
    }

    private static final long serialVersionUID = 1L;

    public TicketStorageIcChkStorage(String checkBillNo, String checkPerson, Date checkDate, Date verifyDate, String verifyPerson, String recordFlag, String locked, String inLocked) {
        this.checkBillNo = checkBillNo;
        this.checkPerson = checkPerson;
        this.checkDate = checkDate;
        this.verifyDate = verifyDate;
        this.verifyPerson = verifyPerson;
        this.recordFlag = recordFlag;
        this.locked = locked;
        this.inLocked = inLocked;
    }

    public TicketStorageIcChkStorage() {
        super();
    }

    public String getCheckBillNo() {
        return checkBillNo;
    }

    public void setCheckBillNo(String checkBillNo) {
        this.checkBillNo = checkBillNo == null ? null : checkBillNo.trim();
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson == null ? null : checkPerson.trim();
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(Date verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getVerifyPerson() {
        return verifyPerson;
    }

    public void setVerifyPerson(String verifyPerson) {
        this.verifyPerson = verifyPerson == null ? null : verifyPerson.trim();
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag == null ? null : recordFlag.trim();
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked == null ? null : locked.trim();
    }

    public String getInLocked() {
        return inLocked;
    }

    public void setInLocked(String inLocked) {
        this.inLocked = inLocked == null ? null : inLocked.trim();
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
        TicketStorageIcChkStorage other = (TicketStorageIcChkStorage) that;
        return (this.getCheckBillNo() == null ? other.getCheckBillNo() == null : this.getCheckBillNo().equals(other.getCheckBillNo()))
            && (this.getCheckPerson() == null ? other.getCheckPerson() == null : this.getCheckPerson().equals(other.getCheckPerson()))
            && (this.getCheckDate() == null ? other.getCheckDate() == null : this.getCheckDate().equals(other.getCheckDate()))
            && (this.getVerifyDate() == null ? other.getVerifyDate() == null : this.getVerifyDate().equals(other.getVerifyDate()))
            && (this.getVerifyPerson() == null ? other.getVerifyPerson() == null : this.getVerifyPerson().equals(other.getVerifyPerson()))
            && (this.getRecordFlag() == null ? other.getRecordFlag() == null : this.getRecordFlag().equals(other.getRecordFlag()))
            && (this.getLocked() == null ? other.getLocked() == null : this.getLocked().equals(other.getLocked()))
            && (this.getInLocked() == null ? other.getInLocked() == null : this.getInLocked().equals(other.getInLocked()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCheckBillNo() == null) ? 0 : getCheckBillNo().hashCode());
        result = prime * result + ((getCheckPerson() == null) ? 0 : getCheckPerson().hashCode());
        result = prime * result + ((getCheckDate() == null) ? 0 : getCheckDate().hashCode());
        result = prime * result + ((getVerifyDate() == null) ? 0 : getVerifyDate().hashCode());
        result = prime * result + ((getVerifyPerson() == null) ? 0 : getVerifyPerson().hashCode());
        result = prime * result + ((getRecordFlag() == null) ? 0 : getRecordFlag().hashCode());
        result = prime * result + ((getLocked() == null) ? 0 : getLocked().hashCode());
        result = prime * result + ((getInLocked() == null) ? 0 : getInLocked().hashCode());
        return result;
    }
}