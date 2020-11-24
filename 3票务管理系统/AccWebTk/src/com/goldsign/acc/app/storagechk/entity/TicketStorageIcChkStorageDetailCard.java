package com.goldsign.acc.app.storagechk.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class TicketStorageIcChkStorageDetailCard implements Serializable {
    private BigDecimal waterNo;

    private String checkBillNo;

    private String storageId;
    
    private String storageName;

    private String areaId;
    
    private String areaName;

   

    private String chestId;

    private String storeyId;

    private String baseId;

    private String boxId;

    private String icMainType;
    
    private String icMainTypeName;

    private String icSubType;
    
    private String icSubTypeName;

    private String startLogicalId;

    private String endLogicalId;

    private static final long serialVersionUID = 1L;

    public TicketStorageIcChkStorageDetailCard(BigDecimal waterNo, String checkBillNo, String storageId, String areaId, String chestId, String storeyId, String baseId, String boxId, String icMainType, String icSubType, String startLogicalId, String endLogicalId) {
        this.waterNo = waterNo;
        this.checkBillNo = checkBillNo;
        this.storageId = storageId;
        this.areaId = areaId;
        this.chestId = chestId;
        this.storeyId = storeyId;
        this.baseId = baseId;
        this.boxId = boxId;
        this.icMainType = icMainType;
        this.icSubType = icSubType;
        this.startLogicalId = startLogicalId;
        this.endLogicalId = endLogicalId;
    }
    
     public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String StorageName) {
        this.storageName = StorageName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getIcMainTypeName() {
        return icMainTypeName;
    }

    public void setIcMainTypeName(String icMainTypeName) {
        this.icMainTypeName = icMainTypeName;
    }

    public String getIcSubTypeName() {
        return icSubTypeName;
    }

    public void setIcSubTypeName(String icSubTypeName) {
        this.icSubTypeName = icSubTypeName;
    }

    public TicketStorageIcChkStorageDetailCard() {
        super();
    }

    public BigDecimal getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(BigDecimal waterNo) {
        this.waterNo = waterNo;
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

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId == null ? null : boxId.trim();
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

    public String getStartLogicalId() {
        return startLogicalId;
    }

    public void setStartLogicalId(String startLogicalId) {
        this.startLogicalId = startLogicalId == null ? null : startLogicalId.trim();
    }

    public String getEndLogicalId() {
        return endLogicalId;
    }

    public void setEndLogicalId(String endLogicalId) {
        this.endLogicalId = endLogicalId == null ? null : endLogicalId.trim();
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
        TicketStorageIcChkStorageDetailCard other = (TicketStorageIcChkStorageDetailCard) that;
        return (this.getWaterNo() == null ? other.getWaterNo() == null : this.getWaterNo().equals(other.getWaterNo()))
            && (this.getCheckBillNo() == null ? other.getCheckBillNo() == null : this.getCheckBillNo().equals(other.getCheckBillNo()))
            && (this.getStorageId() == null ? other.getStorageId() == null : this.getStorageId().equals(other.getStorageId()))
            && (this.getAreaId() == null ? other.getAreaId() == null : this.getAreaId().equals(other.getAreaId()))
            && (this.getChestId() == null ? other.getChestId() == null : this.getChestId().equals(other.getChestId()))
            && (this.getStoreyId() == null ? other.getStoreyId() == null : this.getStoreyId().equals(other.getStoreyId()))
            && (this.getBaseId() == null ? other.getBaseId() == null : this.getBaseId().equals(other.getBaseId()))
            && (this.getBoxId() == null ? other.getBoxId() == null : this.getBoxId().equals(other.getBoxId()))
            && (this.getIcMainType() == null ? other.getIcMainType() == null : this.getIcMainType().equals(other.getIcMainType()))
            && (this.getIcSubType() == null ? other.getIcSubType() == null : this.getIcSubType().equals(other.getIcSubType()))
            && (this.getStartLogicalId() == null ? other.getStartLogicalId() == null : this.getStartLogicalId().equals(other.getStartLogicalId()))
            && (this.getEndLogicalId() == null ? other.getEndLogicalId() == null : this.getEndLogicalId().equals(other.getEndLogicalId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWaterNo() == null) ? 0 : getWaterNo().hashCode());
        result = prime * result + ((getCheckBillNo() == null) ? 0 : getCheckBillNo().hashCode());
        result = prime * result + ((getStorageId() == null) ? 0 : getStorageId().hashCode());
        result = prime * result + ((getAreaId() == null) ? 0 : getAreaId().hashCode());
        result = prime * result + ((getChestId() == null) ? 0 : getChestId().hashCode());
        result = prime * result + ((getStoreyId() == null) ? 0 : getStoreyId().hashCode());
        result = prime * result + ((getBaseId() == null) ? 0 : getBaseId().hashCode());
        result = prime * result + ((getBoxId() == null) ? 0 : getBoxId().hashCode());
        result = prime * result + ((getIcMainType() == null) ? 0 : getIcMainType().hashCode());
        result = prime * result + ((getIcSubType() == null) ? 0 : getIcSubType().hashCode());
        result = prime * result + ((getStartLogicalId() == null) ? 0 : getStartLogicalId().hashCode());
        result = prime * result + ((getEndLogicalId() == null) ? 0 : getEndLogicalId().hashCode());
        return result;
    }
}