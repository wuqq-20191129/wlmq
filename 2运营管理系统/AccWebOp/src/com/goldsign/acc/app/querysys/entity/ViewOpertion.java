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
 * 20170619
 * 安全日志——查看操作
 */
public class ViewOpertion  implements Serializable{
    private int water_no;//流水号
    private String operator_id;//操作员id
    private String operator_name;//操作员名称
    private String host_addr;//机器地址或机器名
    private String op_type;//操作类型id
    private String op_type_name;//操作类型名称
    private String op_time;//操作时间
    private String app;//应用系统
    private String module;//模块
    private String module_name;//模块名称
    private String content;//操作内容
    private String result;//操作结果
    private String begin_date;
    private String end_date;

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

    public String getOp_type_name() {
        return op_type_name;
    }

    public void setOp_type_name(String op_type_name) {
        this.op_type_name = op_type_name;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
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

    public String getHost_addr() {
        return host_addr;
    }

    public void setHost_addr(String host_addr) {
        this.host_addr = host_addr;
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

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
