/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.vo;

/**
 *
 * @author lenovo
 */
public class DeviceStateReqVo {
    
    private String deviceId;
    
    private String operCode;
    
    private String changeTime;
    
    private String state;
    
    private String desc;

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
     * @return the operCode
     */
    public String getOperCode() {
        return operCode;
    }

    /**
     * @param operCode the operCode to set
     */
    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }

    /**
     * @return the changeTime
     */
    public String getChangeTime() {
        return changeTime;
    }

    /**
     * @param changeTime the changeTime to set
     */
    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

}
