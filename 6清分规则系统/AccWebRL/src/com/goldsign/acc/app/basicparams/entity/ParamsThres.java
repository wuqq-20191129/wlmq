/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicparams.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 阀值参数设置
 *
 * @author luck
 */
public class ParamsThres implements Serializable {

    private int id;  //序号

    private BigDecimal  distanceThres;  //里程差比例阀值

    private int stationThres;  //站点差阀值

    private int changeThres;  //换乘次数阀值

    private int timeThres;  //乘车时间（s）阀值

    private String description;   //描述

    private String recordFlag;   //历史状态
    private String recordFlagName;

    private String version;  //版本

    private String updateTime;  //创建时间

    private String updateOperator;  //创建人

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getDistanceThres() {
        return distanceThres;
    }

    public void setDistanceThres(BigDecimal distanceThres) {
        this.distanceThres = distanceThres;
    }

   

    public int getStationThres() {
        return stationThres;
    }

    public void setStationThres(int stationThres) {
        this.stationThres = stationThres;
    }

    public int getChangeThres() {
        return changeThres;
    }

    public void setChangeThres(int changeThres) {
        this.changeThres = changeThres;
    }

    public int getTimeThres() {
        return timeThres;
    }

    public void setTimeThres(int timeThres) {
        this.timeThres = timeThres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateOperator() {
        return updateOperator;
    }

    public void setUpdateOperator(String updateOperator) {
        this.updateOperator = updateOperator;
    }

    public String getRecordFlagName() {
        return recordFlagName;
    }

    public void setRecordFlagName(String recordFlagName) {
        this.recordFlagName = recordFlagName;
    }
    
    
    
}
