/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.entity;

/**
 *
 * @author liudezeng 配票保有量参数
 */
public class TicketStorageDistributeReserveManage {

    private String line_id;
    private String station_id;
    
    private String ic_main_type;
    private String ic_sub_type;
    private String line_id_name;
    private String station_id_name;
    private String ic_main_type_name;
    private String ic_sub_type_name;
    private String card_money;
    private String reverve_num;
    private String remark;
    private String storage_id;//仓库ID
    private String storage_id_name;//仓库名称
    private String sys_operator_id;//操作员
    private String sys_group_id;//组别
    private String sys_storage_id;//仓库权限

    public String getStorage_id_name() {
        return storage_id_name;
    }

    public void setStorage_id_name(String storage_id_name) {
        this.storage_id_name = storage_id_name;
    }
    
    

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

    public String getIc_main_type() {
        return ic_main_type;
    }

    public void setIc_main_type(String ic_main_type) {
        this.ic_main_type = ic_main_type;
    }

    public String getIc_sub_type() {
        return ic_sub_type;
    }

    public void setIc_sub_type(String ic_sub_type) {
        this.ic_sub_type = ic_sub_type;
    }

    public String getLine_id_name() {
        return line_id_name;
    }

    public void setLine_id_name(String line_id_name) {
        this.line_id_name = line_id_name;
    }

    public String getStation_id_name() {
        return station_id_name;
    }

    public void setStation_id_name(String station_id_name) {
        this.station_id_name = station_id_name;
    }

    public String getIc_main_type_name() {
        return ic_main_type_name;
    }

    public void setIc_main_type_name(String ic_main_type_name) {
        this.ic_main_type_name = ic_main_type_name;
    }

    public String getIc_sub_type_name() {
        return ic_sub_type_name;
    }

    public void setIc_sub_type_name(String ic_sub_type_name) {
        this.ic_sub_type_name = ic_sub_type_name;
    }

    public String getCard_money() {
        return card_money;
    }

    public void setCard_money(String card_money) {
        this.card_money = card_money;
    }

    public String getReverve_num() {
        return reverve_num;
    }

    public void setReverve_num(String reverve_num) {
        this.reverve_num = reverve_num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
