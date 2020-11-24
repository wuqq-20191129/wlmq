/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.entity;

/**
 *
 * @author ldz
 */
public class DealAssignLine extends PrmVersion{
    private String b_line_id;
    private String b_station_id;
    private String e_line_id;
    private String e_station_id;
    private String line_id_dispart; //分账线路
    private String in_percent; //分账比例
    private String version_no;
    private String record_flag;
    String total;
    String exitNo;
    String msg;
    private String water_no;
     private String line_id;
     private String linestation;
    private String b_line_id_name;
    private String b_station_id_name;
    private String e_line_id_name;
    private String e_station_id_name;
    private String line_id_dispart_name; //分账线路

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

    
    public String getLinestation() {
        return linestation;
    }

    public void setLinestation(String linestation) {
        this.linestation = linestation;
    }

    
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getExitNo() {
        return exitNo;
    }

    public void setExitNo(String exitNo) {
        this.exitNo = exitNo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    
    public String getB_line_id_name() {
        return b_line_id_name;
    }

    public void setB_line_id_name(String b_line_id_name) {
        this.b_line_id_name = b_line_id_name;
    }

    public String getB_station_id_name() {
        return b_station_id_name;
    }

    public void setB_station_id_name(String b_station_id_name) {
        this.b_station_id_name = b_station_id_name;
    }

    public String getE_line_id_name() {
        return e_line_id_name;
    }

    public void setE_line_id_name(String e_line_id_name) {
        this.e_line_id_name = e_line_id_name;
    }

    public String getE_station_id_name() {
        return e_station_id_name;
    }

    public void setE_station_id_name(String e_station_id_name) {
        this.e_station_id_name = e_station_id_name;
    }

    public String getLine_id_dispart_name() {
        return line_id_dispart_name;
    }

    public void setLine_id_dispart_name(String line_id_dispart_name) {
        this.line_id_dispart_name = line_id_dispart_name;
    }

    
    
    public String getB_line_id() {
        return b_line_id;
    }

    public void setB_line_id(String b_line_id) {
        this.b_line_id = b_line_id;
    }

    public String getB_station_id() {
        return b_station_id;
    }

    public void setB_station_id(String b_station_id) {
        this.b_station_id = b_station_id;
    }

    public String getE_line_id() {
        return e_line_id;
    }

    public void setE_line_id(String e_line_id) {
        this.e_line_id = e_line_id;
    }

    public String getE_station_id() {
        return e_station_id;
    }

    public void setE_station_id(String e_station_id) {
        this.e_station_id = e_station_id;
    }

    public String getLine_id_dispart() {
        return line_id_dispart;
    }

    public void setLine_id_dispart(String line_id_dispart) {
        this.line_id_dispart = line_id_dispart;
    }

    public String getIn_percent() {
        return in_percent;
    }

    public void setIn_percent(String in_percent) {
        this.in_percent = in_percent;
    }

    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }
    
}
