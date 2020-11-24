package com.goldsign.acc.app.query.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketStorageTkQry implements Serializable {
    private String billNo;

    private String reasonId;

    private String storageId;

    private String areaId;

    private String detailPlace;

    private String startBoxId;

    private String endBoxId;

    private String formMaker;

    private String verifyPerson;

    private Date verifyDate;
    
    private String verifyStrDate;

   

    private BigDecimal type;

    private String reasonIdText;

    private String storageIdText;

    private String areaIdText;

    private String waterNo;
    
    private String logicalId;
    
    private String boxRange;

    public String getLogicalId() {
        return logicalId;
    }

    public void setLogicalId(String logicalId) {
        this.logicalId = logicalId;
    }

    public String getBoxRange() {
        if(startBoxId != null && endBoxId != null){
            return startBoxId + "-" + endBoxId;
        }
        return boxRange;
    }

    public void setBoxRange(String boxRange) {
        this.boxRange = boxRange;
    }
    
     public String getVerifyStrDate() {
         if(verifyDate!=null){
             return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(verifyDate);
         }
        return verifyStrDate;
    }

    public void setVerifyStrDate(String verifyStrDate) {
        this.verifyStrDate = verifyStrDate;
    }

    private static final long serialVersionUID = 1L;

    public TicketStorageTkQry(String billNo, String reasonId, String storageId, String areaId, String detailPlace, String startBoxId, String endBoxId, String formMaker, String verifyPerson, Date verifyDate, String reasonIdText, String storageIdText, String areaIdText) {
        this.billNo = billNo;
        this.reasonId = reasonId;
        this.storageId = storageId;
        this.areaId = areaId;
        this.detailPlace = detailPlace;
        this.startBoxId = startBoxId;
        this.endBoxId = endBoxId;
        this.formMaker = formMaker;
        this.verifyPerson = verifyPerson;
        this.verifyDate = verifyDate;
        
        this.reasonIdText = reasonIdText;
        this.storageIdText = storageIdText;
        this.areaIdText = areaIdText;
        
    }

    public TicketStorageTkQry() {
        super();
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId == null ? null : reasonId.trim();
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId == null ? null : storageId.trim();
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    public String getDetailPlace() {
        return detailPlace;
    }

    public void setDetailPlace(String detailPlace) {
        this.detailPlace = detailPlace == null ? null : detailPlace.trim();
    }

    public String getStartBoxId() {
        return startBoxId;
    }

    public void setStartBoxId(String startBoxId) {
        this.startBoxId = startBoxId == null ? null : startBoxId.trim();
    }

    public String getEndBoxId() {
        return endBoxId;
    }

    public void setEndBoxId(String endBoxId) {
        this.endBoxId = endBoxId == null ? null : endBoxId.trim();
    }

    public String getFormMaker() {
        return formMaker;
    }

    public void setFormMaker(String formMaker) {
        this.formMaker = formMaker == null ? null : formMaker.trim();
    }

    public String getVerifyPerson() {
        return verifyPerson;
    }

    public void setVerifyPerson(String verifyPerson) {
        this.verifyPerson = verifyPerson == null ? null : verifyPerson.trim();
    }

    public Date getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(Date verifyDate) {
        this.verifyDate = verifyDate;
    }

    public BigDecimal getType() {
        return type;
    }

    public void setType(BigDecimal type) {
        this.type = type;
    }

    public String getReasonIdText() {
        return reasonIdText;
    }

    public void setReasonIdText(String reasonIdText) {
        this.reasonIdText = reasonIdText == null ? null : reasonIdText.trim();
    }

    public String getStorageIdText() {
        return storageIdText;
    }

    public void setStorageIdText(String storageIdText) {
        this.storageIdText = storageIdText == null ? null : storageIdText.trim();
    }

    public String getAreaIdText() {
        return areaIdText;
    }

    public void setAreaIdText(String areaIdText) {
        this.areaIdText = areaIdText == null ? null : areaIdText.trim();
    }

    public String getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(String waterNo) {
        this.waterNo = waterNo == null ? null : waterNo.trim();
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
        TicketStorageTkQry other = (TicketStorageTkQry) that;
        return (this.getBillNo() == null ? other.getBillNo() == null : this.getBillNo().equals(other.getBillNo()))
            && (this.getReasonId() == null ? other.getReasonId() == null : this.getReasonId().equals(other.getReasonId()))
            && (this.getStorageId() == null ? other.getStorageId() == null : this.getStorageId().equals(other.getStorageId()))
            && (this.getAreaId() == null ? other.getAreaId() == null : this.getAreaId().equals(other.getAreaId()))
            && (this.getDetailPlace() == null ? other.getDetailPlace() == null : this.getDetailPlace().equals(other.getDetailPlace()))
            && (this.getStartBoxId() == null ? other.getStartBoxId() == null : this.getStartBoxId().equals(other.getStartBoxId()))
            && (this.getEndBoxId() == null ? other.getEndBoxId() == null : this.getEndBoxId().equals(other.getEndBoxId()))
            && (this.getFormMaker() == null ? other.getFormMaker() == null : this.getFormMaker().equals(other.getFormMaker()))
            && (this.getVerifyPerson() == null ? other.getVerifyPerson() == null : this.getVerifyPerson().equals(other.getVerifyPerson()))
            && (this.getVerifyDate() == null ? other.getVerifyDate() == null : this.getVerifyDate().equals(other.getVerifyDate()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getReasonIdText() == null ? other.getReasonIdText() == null : this.getReasonIdText().equals(other.getReasonIdText()))
            && (this.getStorageIdText() == null ? other.getStorageIdText() == null : this.getStorageIdText().equals(other.getStorageIdText()))
            && (this.getAreaIdText() == null ? other.getAreaIdText() == null : this.getAreaIdText().equals(other.getAreaIdText()))
            && (this.getWaterNo() == null ? other.getWaterNo() == null : this.getWaterNo().equals(other.getWaterNo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBillNo() == null) ? 0 : getBillNo().hashCode());
        result = prime * result + ((getReasonId() == null) ? 0 : getReasonId().hashCode());
        result = prime * result + ((getStorageId() == null) ? 0 : getStorageId().hashCode());
        result = prime * result + ((getAreaId() == null) ? 0 : getAreaId().hashCode());
        result = prime * result + ((getDetailPlace() == null) ? 0 : getDetailPlace().hashCode());
        result = prime * result + ((getStartBoxId() == null) ? 0 : getStartBoxId().hashCode());
        result = prime * result + ((getEndBoxId() == null) ? 0 : getEndBoxId().hashCode());
        result = prime * result + ((getFormMaker() == null) ? 0 : getFormMaker().hashCode());
        result = prime * result + ((getVerifyPerson() == null) ? 0 : getVerifyPerson().hashCode());
        result = prime * result + ((getVerifyDate() == null) ? 0 : getVerifyDate().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getReasonIdText() == null) ? 0 : getReasonIdText().hashCode());
        result = prime * result + ((getStorageIdText() == null) ? 0 : getStorageIdText().hashCode());
        result = prime * result + ((getAreaIdText() == null) ? 0 : getAreaIdText().hashCode());
        result = prime * result + ((getWaterNo() == null) ? 0 : getWaterNo().hashCode());
        return result;
    }
}