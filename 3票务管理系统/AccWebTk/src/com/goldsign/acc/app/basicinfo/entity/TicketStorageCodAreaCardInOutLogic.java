/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.entity;

/**
 *
 * @author liudz
 * 
 * 票卡出入库管理配置
 */
public class TicketStorageCodAreaCardInOutLogic {
    private String storage_id;//仓库
    private String area_id;//票区
    private String ic_main_type;//票卡主类型
    private String ic_sub_type;//票卡子类型
    private String out_logic_flag;//管理方式
    private String remark;//备注
    
    private String storage_id_name;
    private String area_id_name;
    private String ic_main_type_name;
    private String ic_sub_type_name;
    private String out_logic_flag_name;

    public String getOut_logic_flag_name() {
        return out_logic_flag_name;
    }

    public void setOut_logic_flag_name(String out_logic_flag_name) {
        this.out_logic_flag_name = out_logic_flag_name;
    }
    
    

    public String getOut_logic_flag() {
        return out_logic_flag;
    }

    public void setOut_logic_flag(String out_logic_flag) {
        this.out_logic_flag = out_logic_flag;
    }

    
    
    
    public String getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(String storage_id) {
        this.storage_id = storage_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStorage_id_name() {
        return storage_id_name;
    }

    public void setStorage_id_name(String storage_id_name) {
        this.storage_id_name = storage_id_name;
    }

    public String getArea_id_name() {
        return area_id_name;
    }

    public void setArea_id_name(String area_id_name) {
        this.area_id_name = area_id_name;
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
    
    
}
