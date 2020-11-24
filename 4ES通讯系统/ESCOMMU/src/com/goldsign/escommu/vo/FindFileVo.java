/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.vo;

/**
 *
 * @author Administrator
 */
public class FindFileVo {

    private String waterNo;
    private String deviceId;
    private String fileName;
    private String status;
    private String infoTime;
    private String updateTime;
    private String ip;
    private String operator;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the waterNo
     */
    public String getWaterNo() {
        return waterNo;
    }

    /**
     * @param waterNo the waterNo to set
     */
    public void setWaterNo(String waterNo) {
        this.waterNo = waterNo;
    }

    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the infoTime
     */
    public String getInfoTime() {
        return infoTime;
    }

    /**
     * @param infoTime the infoTime to set
     */
    public void setInfoTime(String infoTime) {
        this.infoTime = infoTime;
    }

    /**
     * @return the updateTime
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
}
