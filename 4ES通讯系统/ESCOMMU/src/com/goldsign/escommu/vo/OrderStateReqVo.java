/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.vo;

/**
 *
 * @author lenovo
 */
public class OrderStateReqVo {
    
    private String oprtType;
    
    private String operCode;
    
    private String deviceId;
    
    private String orderNo;

    /**
     * @return the oprtType
     */
    public String getOprtType() {
        return oprtType;
    }

    /**
     * @param oprtType the oprtType to set
     */
    public void setOprtType(String oprtType) {
        this.oprtType = oprtType;
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
     * @return the orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    
}
