/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.entity;

import java.util.List;

/**
 *
 * @author mqf
 */
public class TicketStorageInBaseBill {
    
    /**
     * 所有入库类型的入库单表字段
     */
    private String bill_no;//入库单号
    private String bill_date;//制单日期
    private String form_maker;//制单人
    private String hand_man;//交票人
    private String administer;//车票管理员
    private String accounter;//记帐员
    private String related_bill_no;//对应单据号：例如归还记录表单据号、车票生产工单号等
    private String record_flag;//单据状态 "0":单据有效;"1":单据撤消（对未审核单据）;"2":单据删除（对有效单据）;"3":单据未审核
    private String verify_date;//审核时间
    private String verify_person;//审核人
    private String remark;//备注
    
    
    private int out_in_diff; //w_ic_in_out_diff_produce

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public String getForm_maker() {
        return form_maker;
    }

    public void setForm_maker(String form_maker) {
        this.form_maker = form_maker;
    }

    public String getHand_man() {
        return hand_man;
    }

    public void setHand_man(String hand_man) {
        this.hand_man = hand_man;
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

    public String getRelated_bill_no() {
        return related_bill_no;
    }

    public void setRelated_bill_no(String related_bill_no) {
        this.related_bill_no = related_bill_no;
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

    public int getOut_in_diff() {
        return out_in_diff;
    }

    public void setOut_in_diff(int out_in_diff) {
        this.out_in_diff = out_in_diff;
    }


    

    
    
    
    
   
     
    
}
