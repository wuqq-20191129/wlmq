package com.goldsign.acc.app.system.entity;

public class CpuDegrees extends BaseMessage {
    //modyfy by zhongzq 20190419 抽离公共字段
//    private String name;
//
//    private String statusDate;
//
//    private String ip;
//
//    private String status;

    private String statusName;

    private String degreesInfo;

    private String remark;

//    public String getName() {
//        return name;
//    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

//    public void setName(String name) {
//        this.name = name;
//    }

//    public String getStatusDate() {
//        return statusDate;
//    }

//    public void setStatusDate(String statusDate) {
//        this.statusDate = statusDate;
//    }

//    public String getIp() {
//        return ip;
//    }

//    public void setIp(String ip) {
//        this.ip = ip == null ? null : ip.trim();
//    }

//    public String getStatus() {
//        return status;
//    }

//    public void setStatus(String status) {
//        this.status = status == null ? null : status.trim();
//    }

    public String getDegreesInfo() {
        return degreesInfo;
    }

    public void setDegreesInfo(String degreesInfo) {
        this.degreesInfo = degreesInfo == null ? null : degreesInfo.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}