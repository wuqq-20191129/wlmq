package com.goldsign.acc.app.storagechk.entity;

import java.io.Serializable;

public class TicketStorageIcChkStorageDetailKey implements Serializable {
    private String checkBillNo;

    private String storageId;

    private String areaId;

    private String chestId;

    private String storeyId;

    private String baseId;

    private String icMainType;

    private String icSubType;
    
    private String boxId;

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    private static final long serialVersionUID = 1L;

    public TicketStorageIcChkStorageDetailKey(String checkBillNo, String storageId, String areaId, String chestId, String storeyId, String baseId, String icMainType, String icSubType) {
        this.checkBillNo = checkBillNo;
        this.storageId = storageId;
        this.areaId = areaId;
        this.chestId = chestId;
        this.storeyId = storeyId;
        this.baseId = baseId;
        this.icMainType = icMainType;
        this.icSubType = icSubType;
    }

    public TicketStorageIcChkStorageDetailKey() {
        super();
    }

    public String getCheckBillNo() {
        return checkBillNo;
    }

    public void setCheckBillNo(String checkBillNo) {
        this.checkBillNo = checkBillNo == null ? null : checkBillNo.trim();
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

    public String getChestId() {
        return chestId;
    }

    public void setChestId(String chestId) {
        this.chestId = chestId == null ? null : chestId.trim();
    }

    public String getStoreyId() {
        return storeyId;
    }

    public void setStoreyId(String storeyId) {
        this.storeyId = storeyId == null ? null : storeyId.trim();
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId == null ? null : baseId.trim();
    }

    public String getIcMainType() {
        return icMainType;
    }

    public void setIcMainType(String icMainType) {
        this.icMainType = icMainType == null ? null : icMainType.trim();
    }

    public String getIcSubType() {
        return icSubType;
    }

    public void setIcSubType(String icSubType) {
        this.icSubType = icSubType == null ? null : icSubType.trim();
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
        TicketStorageIcChkStorageDetailKey other = (TicketStorageIcChkStorageDetailKey) that;
        return (this.getCheckBillNo() == null ? other.getCheckBillNo() == null : this.getCheckBillNo().equals(other.getCheckBillNo()))
            && (this.getStorageId() == null ? other.getStorageId() == null : this.getStorageId().equals(other.getStorageId()))
            && (this.getAreaId() == null ? other.getAreaId() == null : this.getAreaId().equals(other.getAreaId()))
            && (this.getChestId() == null ? other.getChestId() == null : this.getChestId().equals(other.getChestId()))
            && (this.getStoreyId() == null ? other.getStoreyId() == null : this.getStoreyId().equals(other.getStoreyId()))
            && (this.getBaseId() == null ? other.getBaseId() == null : this.getBaseId().equals(other.getBaseId()))
            && (this.getIcMainType() == null ? other.getIcMainType() == null : this.getIcMainType().equals(other.getIcMainType()))
            && (this.getIcSubType() == null ? other.getIcSubType() == null : this.getIcSubType().equals(other.getIcSubType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCheckBillNo() == null) ? 0 : getCheckBillNo().hashCode());
        result = prime * result + ((getStorageId() == null) ? 0 : getStorageId().hashCode());
        result = prime * result + ((getAreaId() == null) ? 0 : getAreaId().hashCode());
        result = prime * result + ((getChestId() == null) ? 0 : getChestId().hashCode());
        result = prime * result + ((getStoreyId() == null) ? 0 : getStoreyId().hashCode());
        result = prime * result + ((getBaseId() == null) ? 0 : getBaseId().hashCode());
        result = prime * result + ((getIcMainType() == null) ? 0 : getIcMainType().hashCode());
        result = prime * result + ((getIcSubType() == null) ? 0 : getIcSubType().hashCode());
        return result;
    }
}