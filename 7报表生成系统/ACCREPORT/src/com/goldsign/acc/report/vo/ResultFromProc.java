/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.report.vo;

import java.util.Vector;

/**
 *
 * @author hejj
 */
public class ResultFromProc {
     private int retCode;
    private String retMsg;
    private String retValue;
    private Vector retValues;
    public ResultFromProc(int retCode,String retMsg,Vector retValues){
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.retValues = retValues;
    }
    public ResultFromProc(int retCode,String retMsg,String retValue){
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.retValue = retValue;
        
    }

    /**
     * @return the retCode
     */
    public int getRetCode() {
        return retCode;
    }

    /**
     * @param retCode the retCode to set
     */
    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    /**
     * @return the retMsg
     */
    public String getRetMsg() {
        return retMsg;
    }

    /**
     * @param retMsg the retMsg to set
     */
    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    /**
     * @return the retValue
     */
    public String getRetValue() {
        return retValue;
    }

    /**
     * @param retValue the retValue to set
     */
    public void setRetValue(String retValue) {
        this.retValue = retValue;
    }

    /**
     * @return the retValues
     */
    public Vector getRetValues() {
        return retValues;
    }

    /**
     * @param retValues the retValues to set
     */
    public void setRetValues(Vector retValues) {
        this.retValues = retValues;
    }
    
}
