/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.system.entity;

/**
 * @desc:提取三个Ntp类公共，重构
 * @author:zhongzqi
 * @create date: 2017-10-18
 */
public class NtpSynBase extends BaseMessage {
    //modyfy by zhongzq 20190419 抽离公共字段
//    protected String statusDate;
//    protected String ip;
    protected String ipSource;
    protected String statusDateSyn;
    protected String diff;
    //    protected String status;
    protected String remark;

    private String type;

    private int ipValue;
    //页面显示
    protected String statusName;
    protected String diffFlag;

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

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
//        this.ip = ip == null ? null : ip.trim();
//    }

    public String getIpSource() {
        return ipSource;
    }

    public void setIpSource(String ipSource) {
        this.ipSource = ipSource == null ? null : ipSource.trim();
    }

    public String getStatusDateSyn() {
        return statusDateSyn;
    }

    public void setStatusDateSyn(String statusDateSyn) {
        this.statusDateSyn = statusDateSyn;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff == null ? null : diff.trim();
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status == null ? null : status.trim();
//    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDiffFlag() {
        return diffFlag;
    }

    public void setDiffFlag(String diffFlag) {
        this.diffFlag = diffFlag;
    }

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
}
