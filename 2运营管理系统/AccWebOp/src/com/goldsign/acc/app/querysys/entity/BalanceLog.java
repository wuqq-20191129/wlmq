/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author zhouyang
 * 20170617
 * 清算日志
 */
public class BalanceLog implements Serializable{
    private String water_no;//流水号
    private String balance_water_no;//清算流水号
    private String oper_object;//操作对象
    private String err_level;//错误级别
    private String err_level_name;//错误级别名称
    private String remark;//备注
    private String operator_id;//操作员
    private Date gen_datetime;//生成时间
    private String begin_date;//查询条件开始时间
    private String end_date;//查询条件结束时间
    
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getErr_level_name() {
        return err_level_name;
    }

    public void setErr_level_name(String err_level_name) {
        this.err_level_name = err_level_name;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getWater_no() {
        return water_no;
    }

    public void setWater_no(String water_no) {
        this.water_no = water_no;
    }

    public String getBalance_water_no() {
        return balance_water_no;
    }

    public void setBalance_water_no(String balance_water_no) {
        this.balance_water_no = balance_water_no;
    }

    public String getOper_object() {
        return oper_object;
    }

    public void setOper_object(String oper_object) {
        this.oper_object = oper_object;
    }

    public String getErr_level() {
        return err_level;
    }

    public void setErr_level(String err_level) {
        this.err_level = err_level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public Date getGen_datetime() {
        return gen_datetime;
    }

    public void setGen_datetime(Date gen_datetime) {
        this.gen_datetime = gen_datetime;
    }
}
