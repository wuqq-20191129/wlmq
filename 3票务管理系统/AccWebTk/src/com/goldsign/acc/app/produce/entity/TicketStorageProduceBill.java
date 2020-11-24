/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.produce.entity;

import java.io.Serializable;


/**
 *
 * @author mqf
 */
public class TicketStorageProduceBill implements Serializable {
    
    private String bill_no; //生产单号
    private String out_bill_no; //对应出库单号
    private int draw_total; //总领票数量
    private String es_worktype_id; //工作类型代码
    private int es_useless_num; //es废票数量
    private int man_useless_num; //人工废票数量
    private int lost_num; //遗失数量
    private int system_balance; //系统结余
    private int real_balance; //实际结余
    private String hand_man; //交票人
    private String receive_man; //接票人
    private String bill_date; //制单日期
    private String record_flag; //单据状态
    private String verify_date; //审核时间
    private String verify_person; //审核人
    private String remarks; //备注
    private String in_flag; //入库标志
    private String es_operator; //ES操作员
    private String diff_id;  //出入库差异原因
    
    //页面查询
    private String bill_date_begin;
    private String bill_date_end;
    
    private String operator;
    
    //页面显示中文
    private int valid_num; //生产工作单明细累计的draw_quantity
    private String es_worktype_id_name; //工作类型代码
    private String record_flag_name; //单据状态
    private String diff_id_name;  //出入库差异原因
    
    private String lost_detail_name = "废票遗失明细";  // 用于"导出全部"功能的“遗失明细”字段值
    
    

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getOut_bill_no() {
        return out_bill_no;
    }

    public void setOut_bill_no(String out_bill_no) {
        this.out_bill_no = out_bill_no;
    }

    public int getDraw_total() {
        return draw_total;
    }

    public void setDraw_total(int draw_total) {
        this.draw_total = draw_total;
    }

    public String getEs_worktype_id() {
        return es_worktype_id;
    }

    public void setEs_worktype_id(String es_worktype_id) {
        this.es_worktype_id = es_worktype_id;
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

    public String getHand_man() {
        return hand_man;
    }

    public void setHand_man(String hand_man) {
        this.hand_man = hand_man;
    }

    public String getReceive_man() {
        return receive_man;
    }

    public void setReceive_man(String receive_man) {
        this.receive_man = receive_man;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIn_flag() {
        return in_flag;
    }

    public void setIn_flag(String in_flag) {
        this.in_flag = in_flag;
    }

    public String getEs_operator() {
        return es_operator;
    }

    public void setEs_operator(String es_operator) {
        this.es_operator = es_operator;
    }

    public String getDiff_id() {
        return diff_id;
    }

    public void setDiff_id(String diff_id) {
        this.diff_id = diff_id;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRecord_flag_name() {
        return record_flag_name;
    }

    public void setRecord_flag_name(String record_flag_name) {
        this.record_flag_name = record_flag_name;
    }

    public String getDiff_id_name() {
        return diff_id_name;
    }

    public void setDiff_id_name(String diff_id_name) {
        this.diff_id_name = diff_id_name;
    }

    public String getEs_worktype_id_name() {
        return es_worktype_id_name;
    }

    public void setEs_worktype_id_name(String es_worktype_id_name) {
        this.es_worktype_id_name = es_worktype_id_name;
    }

    public int getValid_num() {
        return valid_num;
    }

    public void setValid_num(int valid_num) {
        this.valid_num = valid_num;
    }

    public String getLost_detail_name() {
        return lost_detail_name;
    }

    public void setLost_detail_name(String lost_detail_name) {
        this.lost_detail_name = lost_detail_name;
    }
    
    
  
    
}
