/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.entity;

/**
 *
 * @author liudezeng
 * 流失量参数
 */
public class TicketStorageParaIn {
    
    private String water_no;//序号
    private String rpt_date;//时间
    private String rpt_date_start;//开始时间
    private String rpt_date_end;//结束时间
    private String sys_time;//结束时间
    private String store_num;//仓库数量
    private String useless_num;//报废数量
    private String new_num;//新票到货数量
    private String ic_main_type;//票卡主类型
    private String ic_main_type_name;//票卡主类型名称
    private String ic_sub_type;//票卡子类型
    private String ic_sub_type_name;//票卡子类型名称
    private String not_in_num;//未入库数量
    private String remark;//备注
    private String para_flag;//参数标志
    private String para_flag_name;//参数标志名称
    private String storage_id;//仓库ID
    private String storage_id_name;//仓库名称
    private String sys_operator_id;//操作员
    private String sys_group_id;//组别
    private String sys_storage_id;//仓库权限

    public String getSys_time() {
        return sys_time;
    }

    public void setSys_time(String sys_time) {
        this.sys_time = sys_time;
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
    
    

    public String getSys_operator_id() {
        return sys_operator_id;
    }

    public void setSys_operator_id(String sys_operator_id) {
        this.sys_operator_id = sys_operator_id;
    }

    
    public String getWater_no() {
        return water_no;
    }

    public void setWater_no(String water_no) {
        this.water_no = water_no;
    }

    public String getRpt_date() {
        return rpt_date;
    }

    public void setRpt_date(String rpt_date) {
        this.rpt_date = rpt_date;
    }

    public String getRpt_date_start() {
        return rpt_date_start;
    }

    public void setRpt_date_start(String rpt_date_start) {
        this.rpt_date_start = rpt_date_start;
    }

    public String getRpt_date_end() {
        return rpt_date_end;
    }

    public void setRpt_date_end(String rpt_date_end) {
        this.rpt_date_end = rpt_date_end;
    }

    public String getStore_num() {
        return store_num;
    }

    public void setStore_num(String store_num) {
        this.store_num = store_num;
    }

    public String getUseless_num() {
        return useless_num;
    }

    public void setUseless_num(String useless_num) {
        this.useless_num = useless_num;
    }

    public String getNew_num() {
        return new_num;
    }

    public void setNew_num(String new_num) {
        this.new_num = new_num;
    }

    public String getIc_main_type() {
        return ic_main_type;
    }

    public void setIc_main_type(String ic_main_type) {
        this.ic_main_type = ic_main_type;
    }

    public String getIc_main_type_name() {
        return ic_main_type_name;
    }

    public void setIc_main_type_name(String ic_main_type_name) {
        this.ic_main_type_name = ic_main_type_name;
    }

    public String getIc_sub_type() {
        return ic_sub_type;
    }

    public void setIc_sub_type(String ic_sub_type) {
        this.ic_sub_type = ic_sub_type;
    }

    public String getIc_sub_type_name() {
        return ic_sub_type_name;
    }

    public void setIc_sub_type_name(String ic_sub_type_name) {
        this.ic_sub_type_name = ic_sub_type_name;
    }

    public String getNot_in_num() {
        return not_in_num;
    }

    public void setNot_in_num(String not_in_num) {
        this.not_in_num = not_in_num;
    }



    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPara_flag() {
        return para_flag;
    }

    public void setPara_flag(String para_flag) {
        this.para_flag = para_flag;
    }

    public String getPara_flag_name() {
        return para_flag_name;
    }

    public void setPara_flag_name(String para_flag_name) {
        this.para_flag_name = para_flag_name;
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
