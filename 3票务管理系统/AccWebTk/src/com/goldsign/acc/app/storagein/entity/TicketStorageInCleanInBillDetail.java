/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.entity;

/**
 * 清洗/核查入库单明细
 *
 * @author luck
 */
public class TicketStorageInCleanInBillDetail extends TicketStorageInOutCleanDiff{
    
    private int waterNo;  //流水号

    private String billNo;  //入库单号

    private String reasonId;  //原因代码

    private String storageId;  //仓库代码
    private String storageName;

    private String areaId;  //票区代码
    private String areaName;

    private String icMainType;  //在票务系统中的票卡主类型
    private String icMainTypeName;

    private String icSubType; //在票务系统中的票卡子类型
    private String icSubTypeName;

    private int inNum;  //入库数量

    private String detailPlace;  //详细位置采用代码定长中间用"-"分割的方式表示例：0001-01-001-01-01：表示0001号仓库01票区01柜01层01托0000-00-000-00-00：表示出入库不需指定具体位置

    private String startBoxId;  //票盒起号

    private String endBoxId; //票盒止号

    private String startLogicalId;   //车票ID起号

    private String endLogicalId;    //车票ID止号

    private String validDate;  //有效期

    private int cardMoney;  //面值

    private String lineId;  //线路代码

    private String stationId;  //车站代码

    private String useFlag;  //使用标志

    private String reportDate;  //报表日期，用于回收入库

    private int tickettypeId;  //票务接口上传的票卡类型，用于回收入库

    private String cardAvaDays;  //有效天数（多日票使用

    private String lineIdReclaim;  //回收线路（多日票使用）

    private String stationIdReclaim; //回收车站（多日票使用）

    private String exitLineId;  //限制出站线路（多日票

    private String exitStationId;  //限制出站车站（多日票使用）

    private String model;  //限制模式（多日票使用）
    
    private String table; // 查询表
    
    private String cardType; //票卡类型
    

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

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
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

    public int getInNum() {
        return inNum;
    }

    public void setInNum(int inNum) {
        this.inNum = inNum;
    }

    public String getDetailPlace() {
        return detailPlace;
    }

    public void setDetailPlace(String detailPlace) {
        this.detailPlace = detailPlace;
    }

    public String getStartBoxId() {
        return startBoxId;
    }

    public void setStartBoxId(String startBoxId) {
        this.startBoxId = startBoxId;
    }

    public String getEndBoxId() {
        return endBoxId;
    }

    public void setEndBoxId(String endBoxId) {
        this.endBoxId = endBoxId;
    }

    public String getStartLogicalId() {
        return startLogicalId;
    }

    public void setStartLogicalId(String startLogicalId) {
        this.startLogicalId = startLogicalId;
    }

    public String getEndLogicalId() {
        return endLogicalId;
    }

    public void setEndLogicalId(String endLogicalId) {
        this.endLogicalId = endLogicalId;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public int getCardMoney() {
        return cardMoney;
    }

    public void setCardMoney(int cardMoney) {
        this.cardMoney = cardMoney;
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

    public String getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(String useFlag) {
        this.useFlag = useFlag;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public int getTickettypeId() {
        return tickettypeId;
    }

    public void setTickettypeId(int tickettypeId) {
        this.tickettypeId = tickettypeId;
    }

    public String getCardAvaDays() {
        return cardAvaDays;
    }

    public void setCardAvaDays(String cardAvaDays) {
        this.cardAvaDays = cardAvaDays;
    }

    public String getLineIdReclaim() {
        return lineIdReclaim;
    }

    public void setLineIdReclaim(String lineIdReclaim) {
        this.lineIdReclaim = lineIdReclaim;
    }

    public String getStationIdReclaim() {
        return stationIdReclaim;
    }

    public void setStationIdReclaim(String stationIdReclaim) {
        this.stationIdReclaim = stationIdReclaim;
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

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    
    
    
}
