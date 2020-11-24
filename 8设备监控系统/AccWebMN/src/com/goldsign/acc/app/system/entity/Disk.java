package com.goldsign.acc.app.system.entity;

import java.io.Serializable;

/**
 * 服务器硬盘
 *
 * @author xiaowu
 */
public class Disk extends BaseMessage implements Serializable {
    //modyfy by zhongzq 20190419 抽离公共字段
//    private String status_date;
//    private String ip;
//    private String status;
    private String remark;
    //    private String name;
    private String type;
    private String ip_value;
    
    private String status_text;

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

//    public String getStatus_date() {
//        return status_date;
//    }

//    public void setStatus_date(String status_date) {
//        this.status_date = status_date;
//    }

//    public String getIp() {
//        return ip;
//    }

//    public void setIp(String ip) {
//        this.ip = ip;
//    }

//    public String getStatus() {
//        return status;
//    }

//    public void setStatus(String status) {
//        this.status = status;
//    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

//    public String getName() {
//        return name;
//    }

//    public void setName(String name) {
//        this.name = name;
//    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp_value() {
        return ip_value;
    }

    public void setIp_value(String ip_value) {
        this.ip_value = ip_value;
    }

}
