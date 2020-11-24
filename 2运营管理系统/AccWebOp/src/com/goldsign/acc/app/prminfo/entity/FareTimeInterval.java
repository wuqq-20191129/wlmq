/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;

/**
 * 联乘时间间隔 
 * @author luck
 */
public class FareTimeInterval extends PrmVersion implements Serializable{

    private Long waterNo;
    
    private String timeId;

    private String recordFlag;

    private String versionNo;
    
    private String timeMin;

    private String timeMax;

    private String remark;

    public Long getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(Long waterNo) {
        this.waterNo = waterNo;
    }
    
    

    public String getTimeId() {
        return timeId;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(String timeMin) {
        this.timeMin = timeMin;
    }

    public String getTimeMax() {
        return timeMax;
    }

    public void setTimeMax(String timeMax) {
        this.timeMax = timeMax;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    

}
