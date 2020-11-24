/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.entity;

import java.io.Serializable;

/**
 *
 * @author hejj
 */
public class TicketStorageOutProducePlan extends TicketStorageOutProducePlanDetail implements Serializable {

    /**
     * @return the record_flag_name
     */
    public String getRecord_flag_name() {
        return record_flag_name;
    }

    /**
     * @param record_flag_name the record_flag_name to set
     */
    public void setRecord_flag_name(String record_flag_name) {
        this.record_flag_name = record_flag_name;
    }

    /**
     * @return the bill_date_begin
     */
    public String getBill_date_begin() {
        return bill_date_begin;
    }

    /**
     * @param bill_date_begin the bill_date_begin to set
     */
    public void setBill_date_begin(String bill_date_begin) {
        this.bill_date_begin = bill_date_begin;
    }

    /**
     * @return the bill_date_end
     */
    public String getBill_date_end() {
        return bill_date_end;
    }

    /**
     * @param bill_date_end the bill_date_end to set
     */
    public void setBill_date_end(String bill_date_end) {
        this.bill_date_end = bill_date_end;
    }

    /**
     * @return the out_bill_no
     */
    public String getOut_bill_no() {
        return out_bill_no;
    }

    /**
     * @param out_bill_no the out_bill_no to set
     */
    public void setOut_bill_no(String out_bill_no) {
        this.out_bill_no = out_bill_no;
    }

    /**
     * @return the bill_no
     */
    public String getBill_no() {
        return bill_no;
    }

    /**
     * @param bill_no the bill_no to set
     */
    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    /**
     * @return the bill_date
     */
    public String getBill_date() {
        return bill_date;
    }

    /**
     * @param bill_date the bill_date to set
     */
    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    /**
     * @return the form_maker
     */
    public String getForm_maker() {
        return form_maker;
    }

    /**
     * @param form_maker the form_maker to set
     */
    public void setForm_maker(String form_maker) {
        this.form_maker = form_maker;
    }

    /**
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * @return the verify_person
     */
    public String getVerify_person() {
        return verify_person;
    }

    /**
     * @param verify_person the verify_person to set
     */
    public void setVerify_person(String verify_person) {
        this.verify_person = verify_person;
    }

    /**
     * @return the execute_date
     */
    public String getExecute_date() {
        return execute_date;
    }

    /**
     * @param execute_date the execute_date to set
     */
    public void setExecute_date(String execute_date) {
        this.execute_date = execute_date;
    }

    /**
     * @return the verify_date
     */
    public String getVerify_date() {
        return verify_date;
    }

    /**
     * @param verify_date the verify_date to set
     */
    public void setVerify_date(String verify_date) {
        this.verify_date = verify_date;
    }

    /**
     * @return the record_flag
     */
    public String getRecord_flag() {
        return record_flag;
    }

    /**
     * @param record_flag the record_flag to set
     */
    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    private String bill_no;
    private String bill_date;
    private String form_maker;
    private String operator;
    private String verify_person;
    private String execute_date;
    private String verify_date;
    private String record_flag;
    private String remark;
    private String out_bill_no;
    private String bill_date_begin;
    private String bill_date_end;
    private String record_flag_name;
    
    private String out_bill_record_flag;

    public String getOut_bill_record_flag() {
        return out_bill_record_flag;
    }

    public void setOut_bill_record_flag(String out_bill_record_flag) {
        this.out_bill_record_flag = out_bill_record_flag;
    }
    
    

}
