/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicparams.entity;


/**
 *
 * @author mh
 */
public class ParamsStation {
  private String pStation;  //当前站 not null
  private String nStation;//下一站 not null
  private String lineId;//线路  not null
  private String recordFlag;//版本状态
  private String version; //版本 not null,
  private String mileage;//里程数 
  private String createTime;//创建时间 
  private String createOperator;//创建人
  private String ntStation;//下一站换乘 not null
  
  private String beginCreateTime;
  private String endCreateTime;
  
  private String lineName;//线路名称
  private String pStationsName;//车站名称
  private String nStationsName;
  private String ntStationsName;
  private String recordFlagName;//参数版本名称

    public String getpStation() {
        return pStation;
    }

    public void setpStation(String pStation) {
        this.pStation = pStation;
    }

    public String getnStation() {
        return nStation;
    }

    public void setnStation(String nStation) {
        this.nStation = nStation;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBeginCreateTime() {
        return beginCreateTime;
    }

    public void setBeginCreateTime(String beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public String getCreateOperator() {
        return createOperator;
    }

    public void setCreateOperator(String createOperator) {
        this.createOperator = createOperator;
    }

    public String getNtStation() {
        return ntStation;
    }

    public void setNtStation(String ntStation) {
        this.ntStation = ntStation;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getpStationsName() {
        return pStationsName;
    }

    public void setpStationsName(String pStationsName) {
        this.pStationsName = pStationsName;
    }

    public String getnStationsName() {
        return nStationsName;
    }

    public void setnStationsName(String nStationsName) {
        this.nStationsName = nStationsName;
    }

    public String getNtStationsName() {
        return ntStationsName;
    }

    public void setNtStationsName(String ntStationsName) {
        this.ntStationsName = ntStationsName;
    }

    public String getRecordFlagName() {
        return recordFlagName;
    }

    public void setRecordFlagName(String recordFlagName) {
        this.recordFlagName = recordFlagName;
    }

  
    
      
}
