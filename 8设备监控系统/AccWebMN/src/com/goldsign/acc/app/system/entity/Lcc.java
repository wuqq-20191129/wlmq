/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.system.entity;

/**
 *
 * @author mh
 */
public class Lcc extends BaseMessage {
    //modyfy by zhongzq 20190419 抽离公共字段
//    private String name;
//    private String ip;
//    private String status;
//    private String status_date;
    private String statusText;
    private String remark;

    public void setRemark(String remark) {
        this.remark = remark;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getIp() {
//        return ip;
//    }
//
//    public void setIp(String ip) {
//        this.ip = ip;
//    }

//    public String getStatus() {
//        return status;
//    }

//    public void setStatus(String status) {
//        this.status = status;
//    }

//    public String getStatus_date() {
//        return status_date;
//    }

//    public void setStatus_date(String status_date) {
//        this.status_date = status_date;
//    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}
