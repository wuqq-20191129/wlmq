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
public class OperationLogForPrm {

    public OperationLogForPrm(String operatorId, String opType, String parmTypeId, String opResult) {
        this.operator_id = operatorId;
        this.op_type = opType;
        this.param_type_id = parmTypeId;
        this.remark = opResult;

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
     * @return the param_type_id
     */
    public String getParam_type_id() {
        return param_type_id;
    }

    /**
     * @param param_type_id the param_type_id to set
     */
    public void setParam_type_id(String param_type_id) {
        this.param_type_id = param_type_id;
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

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    private int water_no;
    private String operator_id;
    private String param_type_id;
    private String op_type;
    private String op_time;
    private String result;
    private String remark;

}
