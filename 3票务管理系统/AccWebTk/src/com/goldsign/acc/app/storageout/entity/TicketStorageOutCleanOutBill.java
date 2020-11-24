/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.entity;

import java.util.List;

/**
 *
 * @author luck 清洗出库单
 */
public class TicketStorageOutCleanOutBill extends TicketStorageOutCleanOutBillDetial{

    private String bill_no;      //出库单号
    private String form_maker;  //制单人
    private String bill_date;   //制单日期
    private String drawer;      //领票人
    private String administer;  //车票管理员
    private String accounter;   //记帐员
    private String distribute_bill_no;  //对应单据号：例如配票计划单号、日工作计划表单号等
    private String record_flag;   //  单据状态"0":单据有效"1":单据撤消（对未审核单据）"2":单据删除（对有效单据）”3“:单据未审核
    private String record_flag_name; 
    private String verify_date; //确认时间
    private String verify_person; //审核人
    private String remark;  //备注
    private String bill_type; //出库单类型
    private String in_flag;  //入库标志0：未入库1：已入库

    private String operator;
    private String begin_date;
    private String end_date;
    private String flag;
    
    private List<String> storageIds; //可操作仓库
    private List<String> storageAllIds; //全部仓库

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getForm_maker() {
        return form_maker;
    }

    public void setForm_maker(String form_maker) {
        this.form_maker = form_maker;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    public String getAdminister() {
        return administer;
    }

    public void setAdminister(String administer) {
        this.administer = administer;
    }

    public String getAccounter() {
        return accounter;
    }

    public void setAccounter(String accounter) {
        this.accounter = accounter;
    }

    public String getDistribute_bill_no() {
        return distribute_bill_no;
    }

    public void setDistribute_bill_no(String distribute_bill_no) {
        this.distribute_bill_no = distribute_bill_no;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    public String getRecord_flag_name() {
        return record_flag_name;
    }

    public void setRecord_flag_name(String record_flag_name) {
        this.record_flag_name = record_flag_name;
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

    public String getBill_type() {
        return bill_type;
    }

    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }

    public String getIn_flag() {
        return in_flag;
    }

    public void setIn_flag(String in_flag) {
        this.in_flag = in_flag;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<String> getStorageIds() {
        return storageIds;
    }

    public void setStorageIds(List<String> storageIds) {
        this.storageIds = storageIds;
    }

    public List<String> getStorageAllIds() {
        return storageAllIds;
    }

    public void setStorageAllIds(List<String> storageAllIds) {
        this.storageAllIds = storageAllIds;
    }

    
    
    
    

}
