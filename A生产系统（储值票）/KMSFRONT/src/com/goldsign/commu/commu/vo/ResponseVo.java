/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.commu.commu.vo;

/**
 *
 * @author lenovo
 */
public class ResponseVo {

    private String msgType;
    
    private String status;
    
    private byte[] data;

    /**
     * @return the msgType
     */
    public String getMsgType() {
        return msgType;
    }

    /**
     * @param msgType the msgType to set
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType;
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
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(byte[] data) {
        this.data = data;
    }
    
}
