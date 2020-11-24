/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.entity;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author mqf
 */
public class TicketStorageOutAdjustBill implements Serializable {
    
    private String bill_no;
    private String related_bill_no;
    private String operator;
    private String administer;
    private String bill_date;
    private String record_flag;
    private String verify_date;
    private String verify_person;
    private String remark;
    
    //页面输入使用
    private String bill_date_begin;
    private String bill_date_end;
    private String ic_main_type;//票卡主类型
    private String ic_sub_type;//票卡子类型
    private String adjust_id;;//出库原因
    private String storage_id;//仓库
    
    private String record_flag_name;//单据状态名称，用于页面显示
    
    private String module_Id;
    
    //权限仓库列表
    private List<String> storageIdList;
    
    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getRelated_bill_no() {
        return related_bill_no;
    }

    public void setRelated_bill_no(String related_bill_no) {
        this.related_bill_no = related_bill_no;
    }


    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getAdminister() {
        return administer;
    }

    public void setAdminister(String administer) {
        this.administer = administer;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    public String getVerify_date() {
        return verify_date;
    }

    public void setVerify_date(String verify_date) {
        this.verify_date = verify_date;
    }

    public String getVerify_person() {
        return verify_person;
    }

    public void setVerify_person(String verify_person) {
        this.verify_person = verify_person;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getAdjust_id() {
        return adjust_id;
    }

    public void setAdjust_id(String adjust_id) {
        this.adjust_id = adjust_id;
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

    public String getModule_Id() {
        return module_Id;
    }

    public void setModule_Id(String module_Id) {
        this.module_Id = module_Id;
    }

    public List<String> getStorageIdList() {
        return storageIdList;
    }

    public void setStorageIdList(List<String> storageIdList) {
        this.storageIdList = storageIdList;
    }
    
    
    
}
