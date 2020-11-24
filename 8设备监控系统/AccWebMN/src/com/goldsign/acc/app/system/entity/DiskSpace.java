package com.goldsign.acc.app.system.entity;

import java.io.Serializable;
import java.text.NumberFormat;

/**
 * 服务器磁盘空间
 *
 * @author xiaowu
 */
public class DiskSpace extends BaseMessage implements Serializable {
    //modyfy by zhongzq 20190419 抽离公共字段
//    private String ip;
    private String file_system;
    private String avail;
    private String capacity;
    private String mounted_on;
    //    private String status;
    private String status_text;
    //    private String status_date;
    private String remark;
    //    private String name;
    private String type;
    private String ip_value;
    
    private String redOr;

    public String getRedOr() {
        return redOr;
    }

    public void setRedOr(String redOr) {
        this.redOr = redOr;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

//    public String getIp() {
//        return ip;
//    }
//
//    public void setIp(String ip) {
//        this.ip = ip;
//    }

    public String getFile_system() {
        return file_system;
    }

    public void setFile_system(String file_system) {
        this.file_system = file_system;
    }

    public String getAvail() {
        return avail;
    }

    public void setAvail(String avail) {
        this.avail = avail;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getMounted_on() {
        return mounted_on;
    }

    public void setMounted_on(String mounted_on) {
        this.mounted_on = mounted_on;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getStatus_date() {
//        return status_date;
//    }
//
//    public void setStatus_date(String status_date) {
//        this.status_date = status_date;
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

    public String getIp_value() {
        return ip_value;
    }

    public void setIp_value(String ip_value) {
        this.ip_value = ip_value;
    }

}
