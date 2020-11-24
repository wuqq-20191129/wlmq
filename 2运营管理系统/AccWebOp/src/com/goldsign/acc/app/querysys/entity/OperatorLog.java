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
 * 20170617
 * 安全日志
 */
public class OperatorLog implements Serializable{
    private String flow_id;//流水号
    private String sys_operator_id;//操作员id
    private String sys_operator_name;//操作员名称
    private String login_time;//登录时间
    private String logout_time;//登出时间
    private String remark;//备注
    private String beginDate;//查询开始时间
    private String endDate;//查询结束时间

    public String getSys_operator_name() {
        return sys_operator_name;
    }

    public void setSys_operator_name(String sys_operator_name) {
        this.sys_operator_name = sys_operator_name;
    }

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

    public String getFlow_id() {
        return flow_id;
    }

    public void setFlow_id(String flow_id) {
        this.flow_id = flow_id;
    }

    public String getSys_operator_id() {
        return sys_operator_id;
    }

    public void setSys_operator_id(String sys_operator_id) {
        this.sys_operator_id = sys_operator_id;
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    public String getLogout_time() {
        return logout_time;
    }

    public void setLogout_time(String logout_time) {
        this.logout_time = logout_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
