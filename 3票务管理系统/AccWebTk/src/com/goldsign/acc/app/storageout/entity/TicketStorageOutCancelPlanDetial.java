/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.entity;

/**
 * 核销出库计划单明细
 *
 * @author luck
 */
public class TicketStorageOutCancelPlanDetial {

    private int waterNo;  //流水号

    private String billNo;  // 计划单号

    private String esWorktypeId;  //ES工作类型00：初始化 01：预赋值 02：重编码 03：注销
    private String esWorktypeName;

    private String storageId; //仓库代码
    private String storageName;

    private String areaId;  //票区代码
    private String areaName;

    private String icMainType;  //在票务系统中的票卡主类型
    private String icMainTypeName;

    private String icSubType;  //在票务系统中的票卡子类型
    private String icSubTypeName;

    private int cardMoney;  //面值

    private String vaildDate;  //有效期

    private String lineId;  //线路代码

    private String stationId;  //车站代码

    private int drawQuantity;  //领票数量（出库数量）

    private int makeQuantity; //生产数量

    private String machineNo;  //机器号

    private String cardType;  //新旧票卡类型 001:旧票卡002：新票卡000：未指定
 
    private String reasonId;  //出库原因代码
    private String reasonName; 

    private int cardMoneyProduce;  //  生产卡面值

    private String exitLineId;

    private String exitStationId;

    private String model;
    
    private String flag;

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
        this.billNo = billNo;
    }

    public String getEsWorktypeId() {
        return esWorktypeId;
    }

    public void setEsWorktypeId(String esWorktypeId) {
        this.esWorktypeId = esWorktypeId;
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

    public int getCardMoney() {
        return cardMoney;
    }

    public void setCardMoney(int cardMoney) {
        this.cardMoney = cardMoney;
    }

    public String getVaildDate() {
        return vaildDate;
    }

    public void setVaildDate(String vaildDate) {
        this.vaildDate = vaildDate;
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

    public int getDrawQuantity() {
        return drawQuantity;
    }

    public void setDrawQuantity(int drawQuantity) {
        this.drawQuantity = drawQuantity;
    }

    public int getMakeQuantity() {
        return makeQuantity;
    }

    public void setMakeQuantity(int makeQuantity) {
        this.makeQuantity = makeQuantity;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public int getCardMoneyProduce() {
        return cardMoneyProduce;
    }

    public void setCardMoneyProduce(int cardMoneyProduce) {
        this.cardMoneyProduce = cardMoneyProduce;
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

    public String getEsWorktypeName() {
        return esWorktypeName;
    }

    public void setEsWorktypeName(String esWorktypeName) {
        this.esWorktypeName = esWorktypeName;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    

}
