/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samout.entity;

import com.goldsign.acc.app.samin.entity.SamLogicNos;
import java.util.List;

/**
 *卡分发出库单 实体类
 * @author liudezeng
 * @createTime 2017-8-23
 */
public class DistributeSamOutAction {
    
    private String order_no;//出库单号-
    private String sam_type;//sam卡类型-
    private String line_es;//es线路
    private String start_logic_no;//起始逻辑卡号-
    private String end_logic_no;//结束逻辑卡号-
    private String order_num;//分发卡数量-
    private String out_stock_time;//出库时间-
    private String out_stock_oper;//出库人员-
    private String order_state;//单据状态-
    private String audit_order_oper;//审单人-
    private String audit_order_time;//审核时间-
    private String remark;//备注-
    private String get_card_oper;//领卡人员-
    
    private String order_state_name;//单据状态文本
    private String sam_type_name;//sam卡类型文本
    private String line_es_name;//es线路文本
    
    private String e_start_logic_no;  //起始逻辑卡号前11位
    private String f_start_logic_no;  //起始逻辑卡号后5位
    
    
    private List<String> logic_no; //逻辑卡号
    private String phy_no; //物理卡号
    private String print_no; //卡面号
    private String distribute_place; //分发地方
    private String produce_type; //产品类型 00:空白卡,01:成品卡
    private String stock_state; //库存状态 00:空白卡入库,01:生产计划单,02:卡发行出库,03:成品卡入库,04:卡分发出库,05:卡回收入库,06:卡制作
    private String is_instock; //是否在库 0:出库,1:在库
    private String is_bad; //是否损坏 0:坏卡,1:好卡

    public String getEnd_logic_no() {
        return end_logic_no;
    }

    public void setEnd_logic_no(String end_logic_no) {
        this.end_logic_no = end_logic_no;
    }

   
    public List<String> getLogic_no() {
        return logic_no;
    }


    public void setLogic_no(List<String> logic_no) {
        this.logic_no = logic_no;
    }


    public String getPhy_no() {
        return phy_no;
    }

  
    public void setPhy_no(String phy_no) {
        this.phy_no = phy_no;
    }

    
    public String getPrint_no() {
        return print_no;
    }

    public void setPrint_no(String print_no) {
        this.print_no = print_no;
    }

    public String getDistribute_place() {
        return distribute_place;
    }

    /**
     *
     * @param distribute_place
     */

    public void setDistribute_place(String distribute_place) {
        this.distribute_place = distribute_place;
    }

    public String getProduce_type() {
        return produce_type;
    }

  
    public void setProduce_type(String produce_type) {
        this.produce_type = produce_type;
    }

  
    public String getStock_state() {
        return stock_state;
    }

  
    public void setStock_state(String stock_state) {
        this.stock_state = stock_state;
    }


    public String getIs_instock() {
        return is_instock;
    }

   
    public void setIs_instock(String is_instock) {
        this.is_instock = is_instock;
    }

  
    public String getIs_bad() {
        return is_bad;
    }

  
    public void setIs_bad(String is_bad) {
        this.is_bad = is_bad;
    }

    
    
    public String getE_start_logic_no() {
        return e_start_logic_no;
    }

    public void setE_start_logic_no(String e_start_logic_no) {
        this.e_start_logic_no = e_start_logic_no;
    }

    public String getF_start_logic_no() {
        return f_start_logic_no;
    }

    public void setF_start_logic_no(String f_start_logic_no) {
        this.f_start_logic_no = f_start_logic_no;
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

    public String getLine_es() {
        return line_es;
    }

    public void setLine_es(String line_es) {
        this.line_es = line_es;
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

    public String getOut_stock_time() {
        return out_stock_time;
    }

    public void setOut_stock_time(String out_stock_time) {
        this.out_stock_time = out_stock_time;
    }

    public String getOut_stock_oper() {
        return out_stock_oper;
    }

    public void setOut_stock_oper(String out_stock_oper) {
        this.out_stock_oper = out_stock_oper;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGet_card_oper() {
        return get_card_oper;
    }

    public void setGet_card_oper(String get_card_oper) {
        this.get_card_oper = get_card_oper;
    }

    public String getOrder_state_name() {
        return order_state_name;
    }

    public void setOrder_state_name(String order_state_name) {
        this.order_state_name = order_state_name;
    }

    public String getSam_type_name() {
        return sam_type_name;
    }

    public void setSam_type_name(String sam_type_name) {
        this.sam_type_name = sam_type_name;
    }

    public String getLine_es_name() {
        return line_es_name;
    }

    public void setLine_es_name(String line_es_name) {
        this.line_es_name = line_es_name;
    }
    
    
    
    
    
}
