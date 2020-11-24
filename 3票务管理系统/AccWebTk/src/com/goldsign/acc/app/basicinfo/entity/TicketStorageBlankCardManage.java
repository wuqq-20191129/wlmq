package com.goldsign.acc.app.basicinfo.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 空白卡订单管理
 * @author xiaowu
 */
public class TicketStorageBlankCardManage implements Serializable{

    private String bill_no;
    private String old_bill_no;
    private String bill_date;
    private String year;
    private String produce_factory_id;
    private String produce_factory_text;
    private String start_logicno;           //开始逻辑卡号8位序列号
    private String end_logicno;             //结束逻辑卡号8位序列号
    private String record_flag;
    private String record_flag_text;
    private String blank_card_type;
    private String blank_card_type_text;
    private String qty;
    private String form_maker;
    private String remark;
    private String is_used;
    private String is_used_text;
    private String batch_no;
    private String bill_date_start;
    private String bill_date_end;
    private String current_int_no;
    private String current_no;
    private String verify_person;
    private String verify_date;
    private String bill_main_type_id;
    
    private String city_code;           //城市代码
    private String city_code_text;
    private String industry_code;       //行业代码
    private String industry_code_text;
    private String app_identifier;      //应用标识
    private String app_identifier_text;
    
    private String start_logic_all;     //开始逻辑卡号全16位
    private String end_logic_all;       //结束逻辑卡号全16位
    
    private List<String> cardTypeList;
    private String flag;
   
    private String file_prefix;

    public String getStart_logic_all() {
        return start_logic_all;
    }

    public void setStart_logic_all(String start_logic_all) {
        this.start_logic_all = start_logic_all;
    }

    public String getEnd_logic_all() {
        return end_logic_all;
    }

    public void setEnd_logic_all(String end_logic_all) {
        this.end_logic_all = end_logic_all;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getCity_code_text() {
        return city_code_text;
    }

    public void setCity_code_text(String city_code_text) {
        this.city_code_text = city_code_text;
    }

    public String getIndustry_code() {
        return industry_code;
    }

    public void setIndustry_code(String industry_code) {
        this.industry_code = industry_code;
    }

    public String getIndustry_code_text() {
        return industry_code_text;
    }

    public void setIndustry_code_text(String industry_code_text) {
        this.industry_code_text = industry_code_text;
    }

    public String getApp_identifier() {
        return app_identifier;
    }

    public void setApp_identifier(String app_identifier) {
        this.app_identifier = app_identifier;
    }

    public String getApp_identifier_text() {
        return app_identifier_text;
    }

    public void setApp_identifier_text(String app_identifier_text) {
        this.app_identifier_text = app_identifier_text;
    }
    
    public String getOld_bill_no() {
        return old_bill_no;
    }

    public void setOld_bill_no(String old_bill_no) {
        this.old_bill_no = old_bill_no;
    }
    
    public String getBill_main_type_id() {
        return bill_main_type_id;
    }

    public void setBill_main_type_id(String bill_main_type_id) {
        this.bill_main_type_id = bill_main_type_id;
    }

    public String getVerify_person() {
        return verify_person;
    }

    public void setVerify_person(String verify_person) {
        this.verify_person = verify_person;
    }

    public String getVerify_date() {
        return verify_date;
    }

    public void setVerify_date(String verify_date) {
        this.verify_date = verify_date;
    }

    public String getIs_used_text() {
        return is_used_text;
    }

    public void setIs_used_text(String is_used_text) {
        this.is_used_text = is_used_text;
    }

    public String getProduce_factory_text() {
        return produce_factory_text;
    }

    public void setProduce_factory_text(String produce_factory_text) {
        this.produce_factory_text = produce_factory_text;
    }
    
    public String getBlank_card_type_text() {
        return blank_card_type_text;
    }

    public void setBlank_card_type_text(String blank_card_type_text) {
        this.blank_card_type_text = blank_card_type_text;
    }

    public String getRecord_flag_text() {
        return record_flag_text;
    }

    public void setRecord_flag_text(String record_flag_text) {
        this.record_flag_text = record_flag_text;
    }

    public String getCurrent_int_no() {
        return current_int_no;
    }

    public void setCurrent_int_no(String current_int_no) {
        this.current_int_no = current_int_no;
    }

    public String getCurrent_no() {
        return current_no;
    }

    public void setCurrent_no(String current_no) {
        this.current_no = current_no;
    }
    
    private List<String> billNoList;
    

    public List<String>  getBillNoList() {
        return billNoList;
    }

    public void setBillNoList(List<String> billNoList) {
        this.billNoList = billNoList;
    }

    public String getBill_date_start() {
        return bill_date_start;
    }

    public void setBill_date_start(String bill_date_start) {
        this.bill_date_start = bill_date_start;
    }

    public String getBill_date_end() {
        return bill_date_end;
    }

    public void setBill_date_end(String bill_date_end) {
        this.bill_date_end = bill_date_end;
    }
    

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getIs_used() {
        return is_used;
    }

    public void setIs_used(String is_used) {
        this.is_used = is_used;
    }

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

    public String getForm_maker() {
        return form_maker;
    }

    public void setForm_maker(String form_maker) {
        this.form_maker = form_maker;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getCardTypeList() {
        return cardTypeList;
    }

    public void setCardTypeList(List<String> cardTypeList) {
        this.cardTypeList = cardTypeList;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFile_prefix() {
        return file_prefix;
    }

    public void setFile_prefix(String file_prefix) {
        this.file_prefix = file_prefix;
    }
    
    
    

}
