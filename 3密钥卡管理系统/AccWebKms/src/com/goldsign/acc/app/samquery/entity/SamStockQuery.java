/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samquery.entity;

/**
 *
 * @author chenzx
 * 卡库存查询
 */
public class SamStockQuery {
    
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
    
    public String getBeginLogicNo() {
        return beginLogicNo;
    }

    public void setBeginLogicNo(String beginLogicNo) {
        this.beginLogicNo = beginLogicNo;
    }
    
    public String getEndLogicNo() {
        return endLogicNo;
    }

    public void setEndLogicNo(String endLogicNo) {
        this.endLogicNo = endLogicNo;
    }
    
    public String getSam_typeText() {
        return sam_typeText;
    }

    public void setSam_typeText(String sam_typeText) {
        this.sam_typeText = sam_typeText;
    }
    
    public String getProduce_typeText() {
        return produce_typeText;
    }

    public void setProduce_typeText(String produce_typeText) {
        this.produce_typeText = produce_typeText;
    }
    
    public String getStock_stateText() {
        return stock_stateText;
    }

    public void setStock_stateText(String stock_stateText) {
        this.stock_stateText = stock_stateText;
    }
    
    public String getIs_instockText() {
        return is_instockText;
    }

    public void setIs_instockText(String is_instockText) {
        this.is_instockText = is_instockText;
    }
    
    public String getIs_badText() {
        return is_badText;
    }

    public void setIs_badText(String is_badText) {
        this.is_badText = is_badText;
    }
       
    private String sam_type;
    private String logic_no;
    private String phy_no;
    private String produce_type;
    private String stock_state;
    private String is_instock;
    private String is_bad;
    private String remark;
    
    private String beginLogicNo;
    private String endLogicNo;
    
    private String sam_typeText;
    private String produce_typeText;
    private String stock_stateText;
    private String is_instockText;
    private String is_badText;
}
