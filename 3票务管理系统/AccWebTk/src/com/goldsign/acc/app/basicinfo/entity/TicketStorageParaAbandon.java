/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.entity;

/**
 *
 * @author liudz 弃票回收箱参数
 */
public class TicketStorageParaAbandon {

    private String water_no;
    private String line_id;
    private String line_id_name;
    private String station_id;
    private String station_id_name;
    private String report_date;
    private String report_date_start;
    private String report_date_end;
    private String remark;
    private String storage_id;
    private String sys_operator_id;//操作员
    private String sys_group_id;//组别
    private String sys_storage_id;//仓库权限

    public String getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(String storage_id) {
        this.storage_id = storage_id;
    }
    
    

    public String getSys_operator_id() {
        return sys_operator_id;
    }

    public void setSys_operator_id(String sys_operator_id) {
        this.sys_operator_id = sys_operator_id;
    }

    public String getSys_group_id() {
        return sys_group_id;
    }

    public void setSys_group_id(String sys_group_id) {
        this.sys_group_id = sys_group_id;
    }

    public String getSys_storage_id() {
        return sys_storage_id;
    }

    public void setSys_storage_id(String sys_storage_id) {
        this.sys_storage_id = sys_storage_id;
    }
    
    

    public String getWater_no() {
        return water_no;
    }

    public void setWater_no(String water_no) {
        this.water_no = water_no;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getLine_id_name() {
        return line_id_name;
    }

    public void setLine_id_name(String line_id_name) {
        this.line_id_name = line_id_name;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getStation_id_name() {
        return station_id_name;
    }

    public void setStation_id_name(String station_id_name) {
        this.station_id_name = station_id_name;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public String getReport_date_start() {
        return report_date_start;
    }

    public void setReport_date_start(String report_date_start) {
        this.report_date_start = report_date_start;
    }

    public String getReport_date_end() {
        return report_date_end;
    }

    public void setReport_date_end(String report_date_end) {
        this.report_date_end = report_date_end;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
