package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;

public class IcBcLogicNo extends PrmVersion implements Serializable {

    private String bill_no;
    private String bill_date;
    private String batch_no;
    private String year;
    private String produce_factory_id;
    private String start_logicno;
    private String blank_card_type;
    private String qty;
    private String end_logicno;
    private String record_flag;
    private String form_maker;
    private String verify_date;
    private String verify_person;
    private String remark;
    private String is_used;

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

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getProduce_factory_id() {
        return produce_factory_id;
    }

    public void setProduce_factory_id(String produce_factory_id) {
        this.produce_factory_id = produce_factory_id;
    }

    public String getStart_logicno() {
        return start_logicno;
    }

    public void setStart_logicno(String start_logicno) {
        this.start_logicno = start_logicno;
    }

    public String getBlank_card_type() {
        return blank_card_type;
    }

    public void setBlank_card_type(String blank_card_type) {
        this.blank_card_type = blank_card_type;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getEnd_logicno() {
        return end_logicno;
    }

    public void setEnd_logicno(String end_logicno) {
        this.end_logicno = end_logicno;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    public String getForm_maker() {
        return form_maker;
    }

    public void setForm_maker(String form_maker) {
        this.form_maker = form_maker;
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

    public String getIs_used() {
        return is_used;
    }

    public void setIs_used(String is_used) {
        this.is_used = is_used;
    }
    
    
}
