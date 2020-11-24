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
 * 20170620
 * 数据交换日志
 */
public class CommuteLog implements Serializable{
    private int water_no;//流水号
    private String datetime_ftp;//ftp时间
    private String ip;//ip地址
    private String filename;//文件名称
    private String start_time;//开始时间
    private String spend_time;//耗时
    private String result;//结果
    private String resultText;
    private String remark;//备注
    private String beginDate;//查询条件开始时间
    private String endDate;//查询条件结束时间
    
    private Integer code;

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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

    public int getWater_no() {
        return water_no;
    }

    public void setWater_no(int water_no) {
        this.water_no = water_no;
    }

    public String getDatetime_ftp() {
        return datetime_ftp;
    }

    public void setDatetime_ftp(String datetime_ftp) {
        this.datetime_ftp = datetime_ftp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getSpend_time() {
        return spend_time;
    }

    public void setSpend_time(String spend_time) {
        this.spend_time = spend_time;
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
