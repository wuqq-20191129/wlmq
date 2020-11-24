/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.vo;

/**
 *
 * @author lenovo
 */
public class EsFileReqVo {
    
    private String deviceId;
    
    private String fileName;
    
    private String operCode;

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

}
