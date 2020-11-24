package com.goldsign.acc.app.storagechk.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TicketStorageIcChkStorageDetail implements Serializable {
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
    
    private String icMainName;

    private String icSubType;
    
    private String icSubName;

    

    private String stationId;

    private BigDecimal cardMoney;

    private BigDecimal sysAmount;

    private BigDecimal realAmount;
    
    private int diffAmount;   

    private String lineId;

    private Date produceDate;
    
    private String strProDate;  

    private String exitLineId;

    private String exitStationId;

    private String model;
    
    private int validNumForBoxSection;
	private int validNumForDiff;
	private int validNumForboxUnit;
	private int validNumForInputSection;

    public int getValidNumForBoxSection() {
        return validNumForBoxSection;
    }

    public void setValidNumForBoxSection(int validNumForBoxSection) {
        this.validNumForBoxSection = validNumForBoxSection;
    }

    public int getValidNumForDiff() {
        return validNumForDiff;
    }

    public void setValidNumForDiff(int validNumForDiff) {
        this.validNumForDiff = validNumForDiff;
    }

    public int getValidNumForboxUnit() {
        return validNumForboxUnit;
    }

    public void setValidNumForboxUnit(int validNumForboxUnit) {
        this.validNumForboxUnit = validNumForboxUnit;
    }

    public int getValidNumForInputSection() {
        return validNumForInputSection;
    }

    public void setValidNumForInputSection(int validNumForInputSection) {
        this.validNumForInputSection = validNumForInputSection;
    }

    private static final long serialVersionUID = 1L;

    public TicketStorageIcChkStorageDetail(BigDecimal waterNo, String checkBillNo, String storageId, String areaId, String chestId, String storeyId, String baseId, String boxId, String icMainType, String icSubType, String stationId, BigDecimal cardMoney, BigDecimal sysAmount, BigDecimal realAmount, String lineId, Date productDate, String exitLineId, String exitStationId, String model) {
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
        this.stationId = stationId;
        this.cardMoney = cardMoney;
        this.sysAmount = sysAmount;
        this.realAmount = realAmount;
        this.lineId = lineId;
        this.produceDate = productDate;
        this.exitLineId = exitLineId;
        this.exitStationId = exitStationId;
        this.model = model;
    }
    
    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getIcMainName() {
        return icMainName;
    }

    public void setIcMainName(String icMainName) {
        this.icMainName = icMainName;
    }

    public String getIcSubName() {
        return icSubName;
    }

    public void setIcSubName(String icSubName) {
        this.icSubName = icSubName;
    }

    public TicketStorageIcChkStorageDetail() {
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

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
    }

    public BigDecimal getCardMoney() {
        return cardMoney;
    }

    public void setCardMoney(BigDecimal cardMoney) {
        this.cardMoney = cardMoney;
    }

    public BigDecimal getSysAmount() {
        return sysAmount;
    }

    public void setSysAmount(BigDecimal sysAmount) {
        this.sysAmount = sysAmount;
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }
    
     public int getDiffAmount() {
        return diffAmount;
    }

    public void setDiffAmount(int diffAmount) {
        this.diffAmount = diffAmount;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId == null ? null : lineId.trim();
    }

    public Date getProductDate() {
        return produceDate;
    }

    public void setProductDate(Date productDate) {
        this.produceDate = productDate;
    }
    
    public String getStrProDate() {
        return strProDate;
    }

    public void setStrProDate(String strProDate) {
        this.strProDate = strProDate;
    }

    public String getExitLineId() {
        return exitLineId;
    }

    public void setExitLineId(String exitLineId) {
        this.exitLineId = exitLineId == null ? null : exitLineId.trim();
    }

    public String getExitStationId() {
        return exitStationId;
    }

    public void setExitStationId(String exitStationId) {
        this.exitStationId = exitStationId == null ? null : exitStationId.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
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
        TicketStorageIcChkStorageDetail other = (TicketStorageIcChkStorageDetail) that;
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
            && (this.getStationId() == null ? other.getStationId() == null : this.getStationId().equals(other.getStationId()))
            && (this.getCardMoney() == null ? other.getCardMoney() == null : this.getCardMoney().equals(other.getCardMoney()))
            && (this.getSysAmount() == null ? other.getSysAmount() == null : this.getSysAmount().equals(other.getSysAmount()))
            && (this.getRealAmount() == null ? other.getRealAmount() == null : this.getRealAmount().equals(other.getRealAmount()))
            && (this.getLineId() == null ? other.getLineId() == null : this.getLineId().equals(other.getLineId()))
            && (this.getProductDate() == null ? other.getProductDate() == null : this.getProductDate().equals(other.getProductDate()))
            && (this.getExitLineId() == null ? other.getExitLineId() == null : this.getExitLineId().equals(other.getExitLineId()))
            && (this.getExitStationId() == null ? other.getExitStationId() == null : this.getExitStationId().equals(other.getExitStationId()))
            && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()));
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
        result = prime * result + ((getStationId() == null) ? 0 : getStationId().hashCode());
        result = prime * result + ((getCardMoney() == null) ? 0 : getCardMoney().hashCode());
        result = prime * result + ((getSysAmount() == null) ? 0 : getSysAmount().hashCode());
        result = prime * result + ((getRealAmount() == null) ? 0 : getRealAmount().hashCode());
        result = prime * result + ((getLineId() == null) ? 0 : getLineId().hashCode());
        result = prime * result + ((getProductDate() == null) ? 0 : getProductDate().hashCode());
        result = prime * result + ((getExitLineId() == null) ? 0 : getExitLineId().hashCode());
        result = prime * result + ((getExitStationId() == null) ? 0 : getExitStationId().hashCode());
        result = prime * result + ((getModel() == null) ? 0 : getModel().hashCode());
        return result;
    }
}