package com.goldsign.acc.app.storageout.entity;


public class TicketStorageOutBorrowPlanDetail {
    private int waterNo;

    private String billNo;

    private String storageId;
    
    private String storageName;

    private String areaId;
    
    private String areaName;

    private String icMainType;
    
    private String icMainTypeName;

    private String icSubType;
    
     private String icSubTypeName;

    private int lendQuantity;

    private int cardMoney;

    private String validDate;

    private String lineId;
    
    private String lineName;

    private String stationId;
    
    private String stationName;

    private String startBoxId;

    private String endBoxId;

    private String startLogicalId;

    private String endLogicalId;

    private String cardType;

    private String remark;

    private String reasonId;

    private String exitLineId;
    
    private String exitLineName;

    private String exitStationId;
    
    private String exitStationName;

    private String model;
    
    private String modelName;
    
    private String operType;//记录操作类型是增加还是修改

    public int getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(int waterNo) {
        this.waterNo = waterNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
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

    public int getLendQuantity() {
        return lendQuantity;
    }

    public void setLendQuantity(int lendQuantity) {
        this.lendQuantity = lendQuantity;
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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasionId) {
        this.reasonId = reasionId == null ? null : reasionId.trim();
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

    public String getStorageName() {
        return storageName;
    }

    public String getAreaName() {
        return areaName;
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

    public String getModelName() {
        return modelName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }
    
}