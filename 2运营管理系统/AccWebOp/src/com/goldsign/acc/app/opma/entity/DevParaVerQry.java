/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.entity;

import java.io.Serializable;

/**
 *
 * @author mh
 */
public class DevParaVerQry implements Serializable{
    
    private Integer water_no;
    private String line_id;
    private String station_id;
    private String dev_type_id;
    private String device_id;
    private String status;
    private String query_date;
    private String send_date;
    private String operator_id;
    private String remark;
    private String lcc_line_id;
    
    private String beginDatetime;
    private String endDatetime;
    
    private String devTypeText;
    private String lccText;
    private String lineText;
    private String stationText;
    private String statusText;
    
    public Integer getWater_no() {
        return water_no;
    }

    public void setWater_no(Integer water_no) {
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
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getQuery_date() {
        return query_date;
    }

    public void setQuery_date(String query_date) {
        this.query_date = query_date;
    }
    
    public String getSend_date() {
        return send_date;
    }

    public void setSend_date(String send_date) {
        this.send_date = send_date;
    }
    
    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }
    
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getLcc_line_id() {
        return lcc_line_id;
    }

    public void setLcc_line_id(String lcc_line_id) {
        this.lcc_line_id = lcc_line_id;
    }
    
    public String getBeginDatetime() {
        return beginDatetime;
    }

    public String getEndDatetime() {
        return endDatetime;
    }

    public void setBeginDatetime(String beginDatetime) {
        this.beginDatetime = beginDatetime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getDevTypeText() {
        return devTypeText;
    }

    public void setDevTypeText(String devTypeText) {
        this.devTypeText = devTypeText;
    }

    public String getLccText() {
        return lccText;
    }
    
    public void setLccText(String lccText) {
        this.lccText = lccText;
    }
    
    public String getLineText() {
        return lineText;
    }

    public void setLineText(String lineText) {
        this.lineText = lineText;
    }
    
    public String getStationText() {
        return stationText;
    }
    
    public void setStationText(String stationText) {
        this.stationText = stationText;
    }
    
    public String getStatusText() {
        return statusText;
    }
    
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}
