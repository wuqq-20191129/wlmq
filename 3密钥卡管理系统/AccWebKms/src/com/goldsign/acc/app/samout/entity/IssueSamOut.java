package com.goldsign.acc.app.samout.entity;

import com.goldsign.acc.app.samin.entity.SamStock;

/**
 * 卡发行出库
 * @author xiaowu   20170829
 */
public class IssueSamOut extends SamStock {

    private String order_no;            //生产单号
    private String pdu_order_no;        //生产工作单
    private String out_stock_oper;      //出库人员
    private String out_stock_time;      //出库时间
    private String get_card_oper;       //领卡人
    private String order_state;         //单据状态 0:未审核,1:已审核'
    private String sam_type;            //sam卡类型
    private String start_logic_no;      //起始逻辑卡号
    private String order_num;           //生产数量
    private String audit_order_oper;    //审单人
    private String audit_order_time;    //审核时间
    private String in_stock_state;      //回库状态  0:全未回库,1:部分回库,2:全已回库
    private String remark;              //备注
    
    private String in_stock_state_text;//回库状态文本 
    private String order_state_text;    //单据状态文本
    private String sam_type_text;       //sam卡类型文本
    
    private String is_bad;  //是否损坏 0:坏卡,1:好卡,2:锁卡
    private String isBadName;

    public String getIsBadName() {
        return isBadName;
    }

    public void setIsBadName(String isBadName) {
        this.isBadName = isBadName;
    }

    public String getIs_bad() {
        return is_bad;
    }

    public void setIs_bad(String is_bad) {
        this.is_bad = is_bad;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getPdu_order_no() {
        return pdu_order_no;
    }

    public void setPdu_order_no(String pdu_order_no) {
        this.pdu_order_no = pdu_order_no;
    }

    public String getOut_stock_oper() {
        return out_stock_oper;
    }

    public void setOut_stock_oper(String out_stock_oper) {
        this.out_stock_oper = out_stock_oper;
    }

    public String getOut_stock_time() {
        return out_stock_time;
    }

    public void setOut_stock_time(String out_stock_time) {
        this.out_stock_time = out_stock_time;
    }

    public String getGet_card_oper() {
        return get_card_oper;
    }

    public void setGet_card_oper(String get_card_oper) {
        this.get_card_oper = get_card_oper;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getSam_type() {
        return sam_type;
    }

    public void setSam_type(String sam_type) {
        this.sam_type = sam_type;
    }

    public String getStart_logic_no() {
        return start_logic_no;
    }

    public void setStart_logic_no(String start_logic_no) {
        this.start_logic_no = start_logic_no;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getAudit_order_oper() {
        return audit_order_oper;
    }

    public void setAudit_order_oper(String audit_order_oper) {
        this.audit_order_oper = audit_order_oper;
    }

    public String getAudit_order_time() {
        return audit_order_time;
    }

    public void setAudit_order_time(String audit_order_time) {
        this.audit_order_time = audit_order_time;
    }

    public String getIn_stock_state() {
        return in_stock_state;
    }

    public void setIn_stock_state(String in_stock_state) {
        this.in_stock_state = in_stock_state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIn_stock_state_text() {
        return in_stock_state_text;
    }

    public void setIn_stock_state_text(String in_stock_state_text) {
        this.in_stock_state_text = in_stock_state_text;
    }

    public String getOrder_state_text() {
        return order_state_text;
    }

    public void setOrder_state_text(String order_state_text) {
        this.order_state_text = order_state_text;
    }

    public String getSam_type_text() {
        return sam_type_text;
    }

    public void setSam_type_text(String sam_type_text) {
        this.sam_type_text = sam_type_text;
    }

    
}
