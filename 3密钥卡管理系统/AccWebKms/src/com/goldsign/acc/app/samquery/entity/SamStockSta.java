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
public class SamStockSta {
    
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
    
    public String getProduce_type() {
        return produce_type;
    }

    public void setProduce_type(String produce_type) {
        this.produce_type = produce_type;
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
    
    public String getOutStockEmptyQty() {
        return outStockEmptyQty;
    }

    public void setOutStockEmptyQty(String outStockEmptyQty) {
        this.outStockEmptyQty = outStockEmptyQty;
    }
    
    public String getStockEmptyQty() {
        return stockEmptyQty;
    }

    public void setStockEmptyQty(String stockEmptyQty) {
        this.stockEmptyQty = stockEmptyQty;
    }
    
    public String getOutStockProductQty() {
        return outStockProductQty;
    }

    public void setOutStockProductQty(String outStockProductQty) {
        this.outStockProductQty = outStockProductQty;
    }
    
    public String getGoodStockProductQty() {
        return goodStockProductQty;
    }

    public void setGoodStockProductQty(String goodStockProductQty) {
        this.goodStockProductQty = goodStockProductQty;
    }
    
    public String getBadStockProductQty() {
        return badStockProductQty;
    }

    public void setBadStockProductQty(String badStockProductQty) {
        this.badStockProductQty = badStockProductQty;
    }
       
    private String sam_type;
    private String logic_no;
    private String produce_type;
    private String is_instock;
    private String is_bad;
    
    private String beginLogicNo;
    private String endLogicNo;
    
    private String sam_typeText;
    private String outStockEmptyQty;
    private String stockEmptyQty;
    private String outStockProductQty;
    private String goodStockProductQty;
    private String badStockProductQty;
    
    private String outLockStock;
    private String inLockStock;

    public String getOutLockStock() {
        return outLockStock;
    }

    public void setOutLockStock(String outLockStock) {
        this.outLockStock = outLockStock;
    }

    public String getInLockStock() {
        return inLockStock;
    }

    public void setInLockStock(String inLockStock) {
        this.inLockStock = inLockStock;
    }
}
