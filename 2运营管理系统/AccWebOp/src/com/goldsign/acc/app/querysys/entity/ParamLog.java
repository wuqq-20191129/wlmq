/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.entity;

import java.io.Serializable;

/**
 *
 * @author zhouyang
 * 20170621
 * 参数操作日志
 */
public class ParamLog implements Serializable{
    private int water_no;//流水号
    private String operator_id;//操作员id
    private String operator_name;//操作员名称
    private String param_type_id;//参数类型id
    private String param_type_name;//参数类型名称
    private String op_type;//操作类型
    private String op_type_name;//操作类型名称
    private String op_time;//操作时间
    private String result;//结果
    private String remark;//备注
    private String beginDate;//操作开始时间
    private String endDate;//操作结束时间

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

    public String getParam_type_name() {
        return param_type_name;
    }

    public void setParam_type_name(String param_type_name) {
        this.param_type_name = param_type_name;
    }

    public String getOp_type_name() {
        return op_type_name;
    }

    public void setOp_type_name(String op_type_name) {
        this.op_type_name = op_type_name;
    }

    public int getWater_no() {
        return water_no;
    }

    public void setWater_no(int water_no) {
        this.water_no = water_no;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public String getParam_type_id() {
        return param_type_id;
    }

    public void setParam_type_id(String param_type_id) {
        this.param_type_id = param_type_id;
    }

    public String getOp_type() {
        return op_type;
    }

    public void setOp_type(String op_type) {
        this.op_type = op_type;
    }

    public String getOp_time() {
        return op_time;
    }

    public void setOp_time(String op_time) {
        this.op_time = op_time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
