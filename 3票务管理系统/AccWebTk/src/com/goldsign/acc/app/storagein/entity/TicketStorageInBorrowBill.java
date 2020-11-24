/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.storagein.entity;

/**
 * @desc:
 * @author:zhongzqi
 * @create date: 2017-9-1
 */
public class TicketStorageInBorrowBill extends TicketStorageInBill{
    //注释字段在TicketStorageInBaseBill类
//    private String bill_no;//归还单号
    private String in_bill_no;//入库单号
    private String lend_bill_no;//借出单号
//    private String bill_date;//归还日期
    private String return_man;//归还人
    private String receive_man;//接收人
    private String unit_id;//借票单位
//    private String remark;//备注
//    private String record_flag;//单据状态 "0":单据有效;"1":单据撤消（对未审核单据）;"2":单据删除（对有效单据）;"3":单据未审核
//    private String verify_date;//审核时间
//    private String verify_person;//审核人
    private String record_in_flag;//入库类型  入库单审核状态
    private String unit_name;
    private String record_in_flag_name;

    public String getIn_bill_no() {
        return in_bill_no;
    }

    public String getLend_bill_no() {
        return lend_bill_no;
    }

    public String getReturn_man() {
        return return_man;
    }

    public String getReceive_man() {
        return receive_man;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public String getRecord_in_flag() {
        return record_in_flag;
    }

    public void setIn_bill_no(String in_bill_no) {
        this.in_bill_no = in_bill_no;
    }

    public void setLend_bill_no(String lend_bill_no) {
        this.lend_bill_no = lend_bill_no;
    }

    public void setReturn_man(String return_man) {
        this.return_man = return_man;
    }

    public void setReceive_man(String receive_man) {
        this.receive_man = receive_man;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public void setRecord_in_flag(String record_in_flag) {
        this.record_in_flag = record_in_flag;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public String getRecord_in_flag_name() {
        return record_in_flag_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public void setRecord_in_flag_name(String record_in_flag_name) {
        this.record_in_flag_name = record_in_flag_name;
    }
    
}
