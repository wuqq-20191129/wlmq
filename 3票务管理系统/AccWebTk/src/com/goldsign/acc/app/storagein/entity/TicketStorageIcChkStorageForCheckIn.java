package com.goldsign.acc.app.storagein.entity;

import com.goldsign.acc.app.storagechk.entity.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TicketStorageIcChkStorageForCheckIn implements Serializable {
    private String checkBillNo;

    private String checkPerson;

    private String checkDate; //改为String

    private String verifyDate; //改为String

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

    private String icSubType;
    
    private String storageId;

    private String areaId;
    
    private String startChkDate;    
    
    private String endChkDate;
    
    //调账入库显示盘点明细表信息
    private BigDecimal waterNo;

//    private String checkBillNo;

//    private String storageId;
    
    private String storageName;

//    private String areaId;
    
    private String areaName;

    private String chestId;

    private String storeyId;

    private String baseId;

    private String boxId;

//    private String icMainType;
    
    private String icMainName;

//    private String icSubType;
    
    private String icSubName;

    private BigDecimal cardMoney;

    private BigDecimal sysAmount;

    private BigDecimal realAmount;
    
    private int diffAmount;   
    
    private String lineId;
    
    private String stationId;
    
    private String exitLineId;
    
    private String exitStationId;
    
    private String model;
    
    
    
    //权限仓库列表
    private List<String> storageIdList;
    
    

    
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
    
    private List<TicketStorageIcChkStorageDetail> icChkStorageDetailsForCheckIn;

    public List<TicketStorageIcChkStorageDetailKey> getIcChkStorageDetails() {
        return icChkStorageDetails;
    }

    public void setIcChkStorageDetails(List<TicketStorageIcChkStorageDetailKey> icChkStorageDetails) {
        this.icChkStorageDetails = icChkStorageDetails;
    }

    public List<TicketStorageIcChkStorageDetail> getIcChkStorageDetailsForCheckIn() {
        return icChkStorageDetailsForCheckIn;
    }

    public void setIcChkStorageDetailsForCheckIn(List<TicketStorageIcChkStorageDetail> icChkStorageDetailsForCheckIn) {
        this.icChkStorageDetailsForCheckIn = icChkStorageDetailsForCheckIn;
    }
    
    

    private static final long serialVersionUID = 1L;

    public TicketStorageIcChkStorageForCheckIn(String checkBillNo, String checkPerson, String checkDate, String verifyDate, String verifyPerson, String recordFlag, String locked, String inLocked) {
        this.checkBillNo = checkBillNo;
        this.checkPerson = checkPerson;
        this.checkDate = checkDate;
        this.verifyDate = verifyDate;
        this.verifyPerson = verifyPerson;
        this.recordFlag = recordFlag;
        this.locked = locked;
        this.inLocked = inLocked;
    }

    public TicketStorageIcChkStorageForCheckIn() {
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

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
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

    public BigDecimal getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(BigDecimal waterNo) {
        this.waterNo = waterNo;
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

    public String getChestId() {
        return chestId;
    }

    public void setChestId(String chestId) {
        this.chestId = chestId;
    }

    public String getStoreyId() {
        return storeyId;
    }

    public void setStoreyId(String storeyId) {
        this.storeyId = storeyId;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
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

    public List<String> getStorageIdList() {
        return storageIdList;
    }

    public void setStorageIdList(List<String> storageIdList) {
        this.storageIdList = storageIdList;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getExitLineId() {
        return exitLineId;
    }

    public void setExitLineId(String exitLineId) {
        this.exitLineId = exitLineId;
    }

    public String getExitStationId() {
        return exitStationId;
    }

    public void setExitStationId(String exitStationId) {
        this.exitStationId = exitStationId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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
        TicketStorageIcChkStorageForCheckIn other = (TicketStorageIcChkStorageForCheckIn) that;
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