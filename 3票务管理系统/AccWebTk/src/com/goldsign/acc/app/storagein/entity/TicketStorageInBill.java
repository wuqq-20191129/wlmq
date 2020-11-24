/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.entity;

import java.io.Serializable;
import java.util.List;

/**
 *所有入库单共用
 * @author mqf
 */
public class TicketStorageInBill extends TicketStorageInBaseBill implements Serializable{
    
    /**
     * 入库单表中字段
     */
//    private String bill_no;
//    private String bill_date;
//    private String form_maker;
//    private String hand_man;
//    private String administer;
//    private String accounter;
//    private String related_bill_no;
//    private String record_flag;
//    private String verify_date;
//    private String verify_person;
//    private String remark;
//    
//    private String out_in_diff; //w_ic_in_out_diff_produce
    
    //页面输入使用
    private String in_type;
    private String bill_date_begin;
    private String bill_date_end;
    private String ic_main_type;//票卡主类型
    private String ic_sub_type;//票卡子类型
    private String reason_id;//入库原因
    private String storage_id;//仓库
    
    private String record_flag_name;//单据状态名称，用于页面显示
    
    private String module_Id;
    private String operator;
    
    //查询条件：索引表记录
    private List<TicketStorageIcIdxHistory> icIdxHisList;
    
    //权限仓库列表
    private List<String> storageIdList;
    

    

    public String getIn_type() {
        return in_type;
    }

    public void setIn_type(String in_type) {
        this.in_type = in_type;
    }

    public String getBill_date_begin() {
        return bill_date_begin;
    }

    public void setBill_date_begin(String bill_date_begin) {
        this.bill_date_begin = bill_date_begin;
    }

    public String getBill_date_end() {
        return bill_date_end;
    }

    public void setBill_date_end(String bill_date_end) {
        this.bill_date_end = bill_date_end;
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

    public String getReason_id() {
        return reason_id;
    }

    public void setReason_id(String reason_id) {
        this.reason_id = reason_id;
    }

    public String getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(String storage_id) {
        this.storage_id = storage_id;
    }

    public String getRecord_flag_name() {
        return record_flag_name;
    }

    public void setRecord_flag_name(String record_flag_name) {
        this.record_flag_name = record_flag_name;
    }

    public List<TicketStorageIcIdxHistory> getIcIdxHisList() {
        return icIdxHisList;
    }

    public void setIcIdxHisList(List<TicketStorageIcIdxHistory> icIdxHisList) {
        this.icIdxHisList = icIdxHisList;
    }

    public List<String> getStorageIdList() {
        return storageIdList;
    }

    public void setStorageIdList(List<String> storageIdList) {
        this.storageIdList = storageIdList;
    }

    public String getModule_Id() {
        return module_Id;
    }

    public void setModule_Id(String module_Id) {
        this.module_Id = module_Id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    
   
     
    
}
