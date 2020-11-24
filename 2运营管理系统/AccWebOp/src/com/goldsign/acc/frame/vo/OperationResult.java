/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.vo;

import java.util.List;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class OperationResult {

    private List returnResultSet = new Vector();

    String returnMessage = "";

    /**
     * @return the returnResultSet
     */
    public List getReturnResultSet() {
        return returnResultSet;
    }

    /**
     * @param returnResultSet the returnResultSet to set
     */
    public void setReturnResultSet(List returnResultSet) {
        this.returnResultSet = returnResultSet;
    }

    public OperationResult() {

    }

    public OperationResult(String msg) {
        returnMessage = msg;
    }

    public void addMessage(String msg) {
        this.returnMessage = this.returnMessage + msg;
    }

    public String getMessage() {
        return this.returnMessage;
    }
    public void setMessage(String msg) {
        this.returnMessage =  msg;
    }

}
