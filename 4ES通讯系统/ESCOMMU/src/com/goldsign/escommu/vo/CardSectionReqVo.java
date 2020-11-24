/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.vo;

/**
 *
 * @author lenovo
 */
public class CardSectionReqVo {
    
     private String deviceId;
     private String reqDatetime;

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
     * @return the reqDatetime
     */
    public String getReqDatetime() {
        return reqDatetime;
    }

    /**
     * @param reqDatetime the reqDatetime to set
     */
    public void setReqDatetime(String reqDatetime) {
        this.reqDatetime = reqDatetime;
    }
    
}
