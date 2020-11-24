/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

/**
 *
 * @author hejj
 */
public class CheckDeviceSamVo {
     private String lineId;//线路ID
    private String stationId;//车站ID
    private String devTypeId;//设备类型ID
    private String deviceId;//设备ID
    
    private String samLogicalId;//SAM卡逻辑卡号（10）

    /**
     * @return the lineId
     */
    public String getLineId() {
        return lineId;
    }

    /**
     * @param lineId the lineId to set
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    /**
     * @return the stationId
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * @param stationId the stationId to set
     */
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    /**
     * @return the devTypeId
     */
    public String getDevTypeId() {
        return devTypeId;
    }

    /**
     * @param devTypeId the devTypeId to set
     */
    public void setDevTypeId(String devTypeId) {
        this.devTypeId = devTypeId;
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
     * @return the samLogicalId
     */
    public String getSamLogicalId() {
        return samLogicalId;
    }

    /**
     * @param samLogicalId the samLogicalId to set
     */
    public void setSamLogicalId(String samLogicalId) {
        this.samLogicalId = samLogicalId;
    }
    
}
