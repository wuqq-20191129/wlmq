/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samout.entity;

/**
 *
 * @author liudezeng
 * 20170828
 * 卡库存表实体类
 */
public class SamStock {
    private String sam_type;//SAM类型 
    private String logic_no; //逻辑卡号
    private String phy_no; //物理卡号
    private String print_no; //卡面号
    private String distribute_place; //分发地方
    private String produce_type; //产品类型 00:空白卡,01:成品卡
    private String stock_state; //库存状态 00:空白卡入库,01:生产计划单,02:卡发行出库,03:成品卡入库,04:卡分发出库,05:卡回收入库,06:卡制作
    private String is_instock; //是否在库 0:出库,1:在库
    private String is_bad; //是否损坏 0:坏卡,1:好卡
    private String remark;//备注
    
    private String start_logic_no;//取起始逻辑卡号前11位
    private String end_logic_no; //取起始逻辑卡号后5位

    public String getStart_logic_no() {
        return start_logic_no;
    }

    public void setStart_logic_no(String start_logic_no) {
        this.start_logic_no = start_logic_no;
    }

    public String getEnd_logic_no() {
        return end_logic_no;
    }

    public void setEnd_logic_no(String end_logic_no) {
        this.end_logic_no = end_logic_no;
    }
    
    
    

    public String getSam_type() {
        return sam_type;
    }

    public void setSam_type(String sam_type) {
        this.sam_type = sam_type;
    }

    public String getLogic_no() {
        return logic_no;
    }

    public void setLogic_no(String logic_no) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
}
