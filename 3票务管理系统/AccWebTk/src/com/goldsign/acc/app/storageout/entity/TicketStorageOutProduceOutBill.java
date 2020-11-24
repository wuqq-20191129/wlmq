/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.entity;

/**
 *
 * @author hejj
 */
public class TicketStorageOutProduceOutBill extends TicketStorageOutProduceOutBillDetail{

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
     * @return the drawer
     */
    public String getDrawer() {
        return drawer;
    }

    /**
     * @param drawer the drawer to set
     */
    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    /**
     * @return the administer
     */
    public String getAdminister() {
        return administer;
    }

    /**
     * @param administer the administer to set
     */
    public void setAdminister(String administer) {
        this.administer = administer;
    }

    /**
     * @return the accounter
     */
    public String getAccounter() {
        return accounter;
    }

    /**
     * @param accounter the accounter to set
     */
    public void setAccounter(String accounter) {
        this.accounter = accounter;
    }

    /**
     * @return the distribute_bill_no
     */
    public String getDistribute_bill_no() {
        return distribute_bill_no;
    }

    /**
     * @param distribute_bill_no the distribute_bill_no to set
     */
    public void setDistribute_bill_no(String distribute_bill_no) {
        this.distribute_bill_no = distribute_bill_no;
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

    /**
     * @return the bill_type
     */
    public String getBill_type() {
        return bill_type;
    }

    /**
     * @param bill_type the bill_type to set
     */
    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }

    /**
     * @return the in_flag
     */
    public String getIn_flag() {
        return in_flag;
    }

    /**
     * @param in_flag the in_flag to set
     */
    public void setIn_flag(String in_flag) {
        this.in_flag = in_flag;
    }

    private String bill_no;
    private String form_maker;
    private String bill_date;
    private String drawer;
    private String administer;
    private String accounter;
    private String distribute_bill_no;
    private String record_flag;
    private String record_flag_name;
    private String verify_date;
    private String verify_person;
    private String remark;
    private String bill_type;
    private String in_flag;
    
    
    private String operator;

}
