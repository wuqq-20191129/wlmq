/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.vo;

import com.goldsign.csfrm.vo.CallParam;

/**
 *
 * @author lenovo
 */
public class CommonCfgParam extends CallParam {

    private String stationId;
    
    private String deviceType;
    
    private String deviceNo;
    
    private String systemFlag;

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
     * @return the deviceType
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType the deviceType to set
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * @return the deviceNo
     */
    public String getDeviceNo() {
        return deviceNo;
    }

    /**
     * @param deviceNo the deviceNo to set
     */
    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    /**
     * @return the systemFlag
     */
    public String getSystemFlag() {
        return systemFlag;
    }

    /**
     * @param systemFlag the systemFlag to set
     */
    public void setSystemFlag(String systemFlag) {
        this.systemFlag = systemFlag;
    }
    
}
