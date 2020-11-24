package com.goldsign.acc.app.storageout.entity;

public class TicketStorageOutDistributePlanDetail {
    private int waterNo;

    private String distributeLineId;
    
    private String distributeLineName;

    private String distributeStationId;
    
    private String distributeStationName;

    private String icMainType;
    
    private String icMainTypeName;

    private String icSubType;
    
    private String icSubTypeName;

    private String billNo;

    private int distributeQuantity;

    private int cardMoney;

    private String validDate;

    private String restrictFlag;
    
    private String restrictName;

    private String storageId;
    
    private String storageName;

    private String areaId;
    
    private String areaName;

    private String startLogicalId;

    private String endLogicalId;

    private String reasonId;
    
    private String reasonName;

    private String boxId;
    
    private String endBoxId;

    private String lineId;
    
    private String lineName;

    private String stationId;
    
    private String stationName;

    private String exitLineId;
    
    private String exitLineName;

    private String exitStationId;
    
    private String exitStationName;

    private String model;
    
    private String modelName;
    
    private String content;

    public int getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(int waterNo) {
        this.waterNo = waterNo;
    }

    public String getDistributeLineId() {
        return distributeLineId;
    }

    public void setDistributeLineId(String distributeLineId) {
        this.distributeLineId = distributeLineId == null ? null : distributeLineId.trim();
    }

    public String getDistributeStationId() {
        return distributeStationId;
    }

    public void setDistributeStationId(String distributeStationId) {
        this.distributeStationId = distributeStationId == null ? null : distributeStationId.trim();
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

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public int getDistributeQuantity() {
        return distributeQuantity;
    }

    public void setDistributeQuantity(int distributeQuantity) {
        this.distributeQuantity = distributeQuantity;
    }

    public int getCardMoney() {
        return cardMoney;
    }

    public void setCardMoney(int cardMoney) {
        this.cardMoney = cardMoney;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getRestrictFlag() {
        return restrictFlag;
    }

    public void setRestrictFlag(String restrictFlag) {
        this.restrictFlag = restrictFlag == null ? null : restrictFlag.trim();
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

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId == null ? null : reasonId.trim();
    }

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId == null ? null : boxId.trim();
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId == null ? null : lineId.trim();
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
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

    public String getIcMainTypeName() {
        return icMainTypeName;
    }

    public String getIcSubTypeName() {
        return icSubTypeName;
    }

    public String getLineName() {
        return lineName;
    }

    public String getStationName() {
        return stationName;
    }

    public String getExitLineName() {
        return exitLineName;
    }

    public String getExitStationName() {
        return exitStationName;
    }

    public void setIcMainTypeName(String icMainTypeName) {
        this.icMainTypeName = icMainTypeName;
    }

    public void setIcSubTypeName(String icSubTypeName) {
        this.icSubTypeName = icSubTypeName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setExitLineName(String exitLineName) {
        this.exitLineName = exitLineName;
    }

    public void setExitStationName(String exitStationName) {
        this.exitStationName = exitStationName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
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

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getRestrictName() {
        return restrictName;
    }

    public void setRestrictName(String restrictName) {
        this.restrictName = restrictName;
    }

    public String getDistributeLineName() {
        return distributeLineName;
    }

    public String getDistributeStationName() {
        return distributeStationName;
    }

    public void setDistributeLineName(String distributeLineName) {
        this.distributeLineName = distributeLineName;
    }

    public void setDistributeStationName(String distributeStationName) {
        this.distributeStationName = distributeStationName;
    }

    public String getEndBoxId() {
        return endBoxId;
    }

    public void setEndBoxId(String endBoxId) {
        this.endBoxId = endBoxId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
}