/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.entity;

import java.util.List;

/**
 * 生产入出库差额
 * @author mqf
 */
public class TicketStorageInOutProduceDiff {
    

    private String in_bill_no;
    private String out_bill_no;
    private int out_num;
    private int valid_num;
    private int es_useless_num;
    private int man_useless_num;
    private int lost_num;
    private int system_balance;
    private int real_balance;
    private int out_in_diff;
    private String remark;
    private String record_flag;
    private String in_bill_date;
    private String diff_id;
    
    //页面中文
    private String diff_id_name;

    public String getIn_bill_no() {
        return in_bill_no;
    }

    public void setIn_bill_no(String in_bill_no) {
        this.in_bill_no = in_bill_no;
    }

    public String getOut_bill_no() {
        return out_bill_no;
    }

    public void setOut_bill_no(String out_bill_no) {
        this.out_bill_no = out_bill_no;
    }

    public int getOut_num() {
        return out_num;
    }

    public void setOut_num(int out_num) {
        this.out_num = out_num;
    }

    public int getValid_num() {
        return valid_num;
    }

    public void setValid_num(int valid_num) {
        this.valid_num = valid_num;
    }

    public int getEs_useless_num() {
        return es_useless_num;
    }

    public void setEs_useless_num(int es_useless_num) {
        this.es_useless_num = es_useless_num;
    }

    public int getMan_useless_num() {
        return man_useless_num;
    }

    public void setMan_useless_num(int man_useless_num) {
        this.man_useless_num = man_useless_num;
    }

    public int getLost_num() {
        return lost_num;
    }

    public void setLost_num(int lost_num) {
        this.lost_num = lost_num;
    }

    public int getSystem_balance() {
        return system_balance;
    }

    public void setSystem_balance(int system_balance) {
        this.system_balance = system_balance;
    }

    public int getReal_balance() {
        return real_balance;
    }

    public void setReal_balance(int real_balance) {
        this.real_balance = real_balance;
    }

    public int getOut_in_diff() {
        return out_in_diff;
    }

    public void setOut_in_diff(int out_in_diff) {
        this.out_in_diff = out_in_diff;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    public String getIn_bill_date() {
        return in_bill_date;
    }

    public void setIn_bill_date(String in_bill_date) {
        this.in_bill_date = in_bill_date;
    }

    public String getDiff_id() {
        return diff_id;
    }

    public void setDiff_id(String diff_id) {
        this.diff_id = diff_id;
    }

    public String getDiff_id_name() {
        return diff_id_name;
    }

    public void setDiff_id_name(String diff_id_name) {
        this.diff_id_name = diff_id_name;
    }

     
   
     
    
}
