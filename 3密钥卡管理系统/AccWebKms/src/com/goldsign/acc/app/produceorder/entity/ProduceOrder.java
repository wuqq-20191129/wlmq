package com.goldsign.acc.app.produceorder.entity;

import com.goldsign.acc.app.samin.entity.SamStock;

/**
 * 生产单制作
 *
 * @author xiaowu 20170828
 */
public class ProduceOrder extends SamStock {

    private String order_no;  //生产单号
    private String sam_type;            //sam卡类型
    private String order_num;           //生产数量
    private String make_order_oper;     //制单人
    private String make_order_time;     //制单时间
    private String order_state;         //单据状态
    private String start_logic_no;      //起始逻辑卡号
    private String audit_order_oper;    //审单人
    private String audit_order_time;    //审核时间
    private String finish_num;          //完成数量
    private String make_card_oper;      //制卡人
    private String make_card_time;      //制卡时间
    private String remark;              //备注
    private String finish_flag;         //完成状态
    
    private String order_state_text;    //单据状态文本
    private String finish_flag_text;    //完成状态文本
    private String sam_type_text;       //sam卡类型文本
    
    private String produce_type;        //产品类型
    private String is_instock;
    private String stLogicNoEle;
    private String stLogicNoSeven;
    private String stock_state_first;
    private String stock_state_second;
    private String stock_state_third;
    private String orderNo;//单号
    private String stLogicNo;//开始逻辑卡号
    private String enLogicNo;//结束逻辑卡号
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
    

    
    
    

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getEnLogicNo() {
        return enLogicNo;
    }

    public void setEnLogicNo(String enLogicNo) {
        this.enLogicNo = enLogicNo;
    }

    public String getStLogicNo() {
        return stLogicNo;
    }

    public void setStLogicNo(String stLogicNo) {
        this.stLogicNo = stLogicNo;
    }

    public String getStock_state_third() {
        return stock_state_third;
    }

    public void setStock_state_third(String stock_state_third) {
        this.stock_state_third = stock_state_third;
    }

    public String getStock_state_first() {
        return stock_state_first;
    }

    public void setStock_state_first(String stock_state_first) {
        this.stock_state_first = stock_state_first;
    }

    public String getStock_state_second() {
        return stock_state_second;
    }

    public void setStock_state_second(String stock_state_second) {
        this.stock_state_second = stock_state_second;
    }
    
    public String getStLogicNoEle() {
        return stLogicNoEle;
    }

    public void setStLogicNoEle(String stLogicNoEle) {
        this.stLogicNoEle = stLogicNoEle;
    }

    public String getStLogicNoSeven() {
        return stLogicNoSeven;
    }

    public void setStLogicNoSeven(String stLogicNoSeven) {
        this.stLogicNoSeven = stLogicNoSeven;
    }

    public String getIs_instock() {
        return is_instock;
    }

    public void setIs_instock(String is_instock) {
        this.is_instock = is_instock;
    }

    public String getProduce_type() {
        return produce_type;
    }

    public void setProduce_type(String produce_type) {
        this.produce_type = produce_type;
    }

    public String getOrder_state_text() {
        return order_state_text;
    }

    public void setOrder_state_text(String order_state_text) {
        this.order_state_text = order_state_text;
    }

    public String getFinish_flag_text() {
        return finish_flag_text;
    }

    public void setFinish_flag_text(String finish_flag_text) {
        this.finish_flag_text = finish_flag_text;
    }

    public String getSam_type_text() {
        return sam_type_text;
    }

    public void setSam_type_text(String sam_type_text) {
        this.sam_type_text = sam_type_text;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getSam_type() {
        return sam_type;
    }

    public void setSam_type(String sam_type) {
        this.sam_type = sam_type;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getMake_order_oper() {
        return make_order_oper;
    }

    public void setMake_order_oper(String make_order_oper) {
        this.make_order_oper = make_order_oper;
    }

    public String getMake_order_time() {
        return make_order_time;
    }

    public void setMake_order_time(String make_order_time) {
        this.make_order_time = make_order_time;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getStart_logic_no() {
        return start_logic_no;
    }

    public void setStart_logic_no(String start_logic_no) {
        this.start_logic_no = start_logic_no;
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

    public String getFinish_num() {
        return finish_num;
    }

    public void setFinish_num(String finish_num) {
        this.finish_num = finish_num;
    }

    public String getMake_card_oper() {
        return make_card_oper;
    }

    public void setMake_card_oper(String make_card_oper) {
        this.make_card_oper = make_card_oper;
    }

    public String getMake_card_time() {
        return make_card_time;
    }

    public void setMake_card_time(String make_card_time) {
        this.make_card_time = make_card_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFinish_flag() {
        return finish_flag;
    }

    public void setFinish_flag(String finish_flag) {
        this.finish_flag = finish_flag;
    }
    
    
}
