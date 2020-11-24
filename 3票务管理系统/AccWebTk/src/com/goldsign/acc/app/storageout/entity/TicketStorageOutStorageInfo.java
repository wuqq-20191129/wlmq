package com.goldsign.acc.app.storageout.entity;

public class TicketStorageOutStorageInfo extends TicketStorageOutBoxDetail {

    private String storageId;

    private String areaId;

    private String chestId;

    private String storeyId;

    private String baseId;

    private String boxId;

    private String productDate;

    private int cardNum;

    private String validDate;

    private int cardMoney;

    private String icMainType;

    private String icSubType;

    private String lineId;

    private String stationId;

    private String exitLineId;

    private String exitStationId;

    private String model;
    
    private String flag;

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

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    public String getValidDate() {
        return validDate;
    }

    public int getCardMoney() {
        return cardMoney;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public void setCardMoney(int cardMoney) {
        this.cardMoney = cardMoney;
    }

    public String getIcMainType() {
        return icMainType;
    }

    public String getIcSubType() {
        return icSubType;
    }

    public String getLineId() {
        return lineId;
    }

    public String getStationId() {
        return stationId;
    }

    public String getExitLineId() {
        return exitLineId;
    }

    public String getExitStationId() {
        return exitStationId;
    }

    public void setIcMainType(String icMainType) {
        this.icMainType = icMainType;
    }

    public void setIcSubType(String icSubType) {
        this.icSubType = icSubType;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public void setExitLineId(String exitLineId) {
        this.exitLineId = exitLineId;
    }

    public void setExitStationId(String exitStationId) {
        this.exitStationId = exitStationId;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    
}
