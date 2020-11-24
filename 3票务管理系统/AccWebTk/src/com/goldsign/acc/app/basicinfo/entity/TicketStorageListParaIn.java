/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.entity;

/**
 *
 * @author liudezeng 库存一览表
 */
public class TicketStorageListParaIn {

    private String info_id;//序号
    private String ic_main_type;//票卡主类型
    private String ic_main_type_name;//票卡主类型
    private String ic_sub_type;//票卡子类型
    private String ic_sub_type_name;//票卡子类型
    private String card_money;//面值
    private String ic_main_desc;//票卡主类型描述
    private String ic_sub_desc;//票卡子类型描述
    private String input_date;//时间
    private String input_date_start;
    private String input_date_end;
    private String store_dept;//物资部存量
    private String station_dept;//站存量
    private String income_dept;//收益室存量
    private String other_dept;//其他部门存量
    private String remark;//备注
    private String storage_id;//仓库ID
    private String storage_id_name;//仓库名
    private String sys_operator_id;//操作员
    private String sys_group_id;//组别
    private String sys_storage_id;//仓库权限

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

    public String getCard_money() {
        return card_money;
    }

    public void setCard_money(String card_money) {
        this.card_money = card_money;
    }

    public String getInfo_id() {
        return info_id;
    }

    public void setInfo_id(String info_id) {
        this.info_id = info_id;
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

    public String getIc_main_desc() {
        return ic_main_desc;
    }

    public void setIc_main_desc(String ic_main_desc) {
        this.ic_main_desc = ic_main_desc;
    }

    public String getIc_sub_desc() {
        return ic_sub_desc;
    }

    public void setIc_sub_desc(String ic_sub_desc) {
        this.ic_sub_desc = ic_sub_desc;
    }

    public String getInput_date() {
        return input_date;
    }

    public void setInput_date(String input_date) {
        this.input_date = input_date;
    }

    public String getInput_date_start() {
        return input_date_start;
    }

    public void setInput_date_start(String input_date_start) {
        this.input_date_start = input_date_start;
    }

    public String getInput_date_end() {
        return input_date_end;
    }

    public void setInput_date_end(String input_date_end) {
        this.input_date_end = input_date_end;
    }

    public String getStore_dept() {
        return store_dept;
    }

    public void setStore_dept(String store_dept) {
        this.store_dept = store_dept;
    }

    public String getStation_dept() {
        return station_dept;
    }

    public void setStation_dept(String station_dept) {
        this.station_dept = station_dept;
    }

    public String getIncome_dept() {
        return income_dept;
    }

    public void setIncome_dept(String income_dept) {
        this.income_dept = income_dept;
    }

    public String getOther_dept() {
        return other_dept;
    }

    public void setOther_dept(String other_dept) {
        this.other_dept = other_dept;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(String storage_id) {
        this.storage_id = storage_id;
    }

    public String getStorage_id_name() {
        return storage_id_name;
    }

    public void setStorage_id_name(String storage_id_name) {
        this.storage_id_name = storage_id_name;
    }

}
