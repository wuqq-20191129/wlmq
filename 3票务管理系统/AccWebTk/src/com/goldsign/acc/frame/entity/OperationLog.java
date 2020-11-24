/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.entity;

/**
 *
 * @author hejj
 */
public class OperationLog {
    
public OperationLog(String operatorId,String opType,String moduleId,String opResult ){
    this.operator_id=operatorId;
    this.op_type =opType;
    this.module=moduleId;
    this.content=opResult;
}
    /**
     * @return the water_no
     */
    public int getWater_no() {
        return water_no;
    }

    /**
     * @param water_no the water_no to set
     */
    public void setWater_no(int water_no) {
        this.water_no = water_no;
    }

    /**
     * @return the operator_id
     */
    public String getOperator_id() {
        return operator_id;
    }

    /**
     * @param operator_id the operator_id to set
     */
    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    /**
     * @return the host_addr
     */
    public String getHost_addr() {
        return host_addr;
    }

    /**
     * @param host_addr the host_addr to set
     */
    public void setHost_addr(String host_addr) {
        this.host_addr = host_addr;
    }

    /**
     * @return the op_type
     */
    public String getOp_type() {
        return op_type;
    }

    /**
     * @param op_type the op_type to set
     */
    public void setOp_type(String op_type) {
        this.op_type = op_type;
    }

    /**
     * @return the op_time
     */
    public String getOp_time() {
        return op_time;
    }

    /**
     * @param op_time the op_time to set
     */
    public void setOp_time(String op_time) {
        this.op_time = op_time;
    }

    /**
     * @return the app
     */
    public String getApp() {
        return app;
    }

    /**
     * @param app the app to set
     */
    public void setApp(String app) {
        this.app = app;
    }

    /**
     * @return the module
     */
    public String getModule() {
        return module;
    }

    /**
     * @param module the module to set
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }
    private int water_no;
    private String operator_id;
    private String host_addr;
    private String op_type;
    private String op_time;
    private String app;
    private String module;
    private String content;
    private String result;
    
}
