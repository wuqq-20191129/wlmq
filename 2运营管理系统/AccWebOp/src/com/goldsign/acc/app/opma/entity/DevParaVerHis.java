/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.entity;

import com.goldsign.acc.app.prminfo.entity.PrmVersion;

/**
 *
 * @author liudz
 */
public class DevParaVerHis extends PrmVersion{
  private Long water_no = null;
    private String line_id;
    private String station_id;
    private String dev_type_id;
    private String device_id;
    private String parm_type_id;      //
    private String record_flag;  //
    private String version_no;
    private String report_date;
    private String remark;

    private String genTime;     //用于查询 查询时间开始
    private String endTime;     //用于查询 查询时间结束
    private String line_name;     //用于查询显示
    private String station_name;     //用于查询显示
    private String dev_type_name;     //用于查询显示
    private String record_flag_name;     //用于查询显示
    private String isEffect;     //用于查询设备参数是否生效
    private String parm_type_name;

    public String getParm_type_name() {
        return parm_type_name;
    }

    public void setParm_type_name(String parm_type_name) {
        this.parm_type_name = parm_type_name;
    }
    
    
    public Long getWater_no() {
        return water_no;
    }

    public void setWater_no(Long water_no) {
        this.water_no = water_no;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getDev_type_id() {
        return dev_type_id;
    }

    public void setDev_type_id(String dev_type_id) {
        this.dev_type_id = dev_type_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getParm_type_id() {
        return parm_type_id;
    }

    public void setParm_type_id(String parm_type_id) {
        this.parm_type_id = parm_type_id;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGenTime() {
        return genTime;
    }

    public void setGenTime(String genTime) {
        this.genTime = genTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getDev_type_name() {
        return dev_type_name;
    }

    public void setDev_type_name(String dev_type_name) {
        this.dev_type_name = dev_type_name;
    }

    public String getRecord_flag_name() {
        return record_flag_name;
    }

    public void setRecord_flag_name(String record_flag_name) {
        this.record_flag_name = record_flag_name;
    }

    public String getIsEffect() {
        return isEffect;
    }

    public void setIsEffect(String isEffect) {
        this.isEffect = isEffect;
    }

    
  
  
}
