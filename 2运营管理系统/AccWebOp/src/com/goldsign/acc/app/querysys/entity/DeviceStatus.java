/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.entity;

import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import java.io.Serializable;

/**
 *
 * @author zhouyang
 * 设备状态
 * 20171128
 */
public class DeviceStatus extends PrmVersion implements Serializable{
    private String line_id;//线路
    private String station_id;//车站
    private String dev_type_id;//设备类型id
    private String device_id;//设备id
    private String status_id;//状态id
    private String status_value;//状态值
    private String status_datetime;//
    private String acc_status_value;//ACC状态值
    private String oper_id;
    private String status_name;
    private String beginDate;
    private String endDate;
    
    private String lineName;
    private String stationName;
    private String dev_type_name;
    private String acc_status_name;
    
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getAcc_status_name() {
        return acc_status_name;
    }

    public void setAcc_status_name(String acc_status_name) {
        this.acc_status_name = acc_status_name;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getDev_type_name() {
        return dev_type_name;
    }

    public void setDev_type_name(String dev_type_Name) {
        this.dev_type_name = dev_type_Name;
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

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getStatus_value() {
        return status_value;
    }

    public void setStatus_value(String status_value) {
        this.status_value = status_value;
    }

    public String getStatus_datetime() {
        return status_datetime;
    }

    public void setStatus_datetime(String status_datetime) {
        this.status_datetime = status_datetime;
    }

    public String getAcc_status_value() {
        return acc_status_value;
    }

    public void setAcc_status_value(String acc_status_value) {
        this.acc_status_value = acc_status_value;
    }

    public String getOper_id() {
        return oper_id;
    }

    public void setOper_id(String oper_id) {
        this.oper_id = oper_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
    
}
