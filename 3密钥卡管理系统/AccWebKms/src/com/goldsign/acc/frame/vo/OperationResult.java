/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class OperationResult {

    /**
     * @return the paramProc
     */
    public HashMap<String,String> getParamProc() {
        return paramProc;
    }

    /**
     * @param paramProc the paramProc to set
     */
    public void setParamProc(HashMap<String,String> paramProc) {
        this.paramProc = paramProc;
    }

    /**
     * @return the addPrimaryKey
     */
    public String getAddPrimaryKey() {
        return addPrimaryKey;
    }

    /**
     * @param addPrimaryKey the addPrimaryKey to set
     */
    public void setAddPrimaryKey(String addPrimaryKey) {
        this.addPrimaryKey = addPrimaryKey;
    }

    private List returnResultSet = new Vector();

    String returnMessage = "";
    
    private String addPrimaryKey="";
    private HashMap<String,String> paramProc= new HashMap();

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
