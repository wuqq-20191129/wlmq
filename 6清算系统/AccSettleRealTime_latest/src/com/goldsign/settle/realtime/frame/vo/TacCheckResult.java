/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

/**
 *
 * @author hejj
 */
public class TacCheckResult {


    private String msgType;
    private String retCode;
    private String tac;
    private String retCodeFromEncoder;

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
     * @return the retCode
     */
    public String getRetCode() {
        return retCode;
    }

    /**
     * @param retCode the retCode to set
     */
    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    /**
     * @return the tac
     */
    public String getTac() {
        return tac;
    }

    /**
     * @param tac the tac to set
     */
    public void setTac(String tac) {
        this.tac = tac;
    }
        /**
     * @return the retCodeFromEncoder
     */
    public String getRetCodeFromEncoder() {
        return retCodeFromEncoder;
    }

    /**
     * @param retCodeFromEncoder the retCodeFromEncoder to set
     */
    public void setRetCodeFromEncoder(String retCodeFromEncoder) {
        this.retCodeFromEncoder = retCodeFromEncoder;
    }
    
}
