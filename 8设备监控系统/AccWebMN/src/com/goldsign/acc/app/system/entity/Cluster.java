/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.system.entity;

import java.io.Serializable;

/**
 * 集群
 *
 * @author luck
 */
public class Cluster extends BaseMessage implements Serializable {
//modyfy by zhongzq 20190419 抽离公共字段
//    private String statusDate;

//    private String ip;

    private String resourceName;

    private String nodeName;

    //    private String status;
    private String statusName;

    private String statusDesc;

    private String remark;

//    private String name;

    private String type;

    private int ipValue;

//    public String getStatusDate() {
//        return statusDate;
//    }
//
//    public void setStatusDate(String statusDate) {
//        this.statusDate = statusDate;
//    }
//
//    public String getIp() {
//        return ip;
//    }
//
//    public void setIp(String ip) {
//        this.ip = ip;
//    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getRemark() {
        return remark;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIpValue() {
        return ipValue;
    }

    public void setIpValue(int ipValue) {
        this.ipValue = ipValue;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    
    
    
}
