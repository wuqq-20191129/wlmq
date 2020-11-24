/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.entity;

/**
 *
 * @author mh
 */
public class TicketStorageLineStockWarnPara {
    private String lineId;
    private String icMainType;
    private String icSubType;
    private int upperThresh;
    private int lowerThresh;
    private String remark;
    
    private String lineName;
    private String mainTypeName;
    private String subTypeName;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
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

    public int getUpperThresh() {
        return upperThresh;
    }

    public void setUpperThresh(int upperThresh) {
        this.upperThresh = upperThresh;
    }

    public int getLowerThresh() {
        return lowerThresh;
    }

    public void setLowerThresh(int lowerThresh) {
        this.lowerThresh = lowerThresh;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getMainTypeName() {
        return mainTypeName;
    }

    public void setMainTypeName(String mainTypeName) {
        this.mainTypeName = mainTypeName;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }
        
}
