/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samquery.entity;

/**
 * 卡库存预警
 * @author taidb
 */
public class SamStockWarn {

    /**
     * @return the sam_Card_State_Ok
     */
    public String getSam_Card_State_Ok() {
        return sam_Card_State_Ok;
    }

    /**
     * @param sam_Card_State_Ok the sam_Card_State_Ok to set
     */
    public void setSam_Card_State_Ok(String sam_Card_State_Ok) {
        this.sam_Card_State_Ok = sam_Card_State_Ok;
    }

    /**
     * @return the produce_Type_Empty
     */
    public String getProduce_Type_Empty() {
        return produce_Type_Empty;
    }

    /**
     * @param produce_Type_Empty the produce_Type_Empty to set
     */
    public void setProduce_Type_Empty(String produce_Type_Empty) {
        this.produce_Type_Empty = produce_Type_Empty;
    }

    /**
     * @return the produce_Type_Product
     */
    public String getProduce_Type_Product() {
        return produce_Type_Product;
    }

    /**
     * @param produce_Type_Product the produce_Type_Product to set
     */
    public void setProduce_Type_Product(String produce_Type_Product) {
        this.produce_Type_Product = produce_Type_Product;
    }

    /**
     * @return the stock_State_In
     */
    public String getStock_State_In() {
        return stock_State_In;
    }

    /**
     * @param stock_State_In the stock_State_In to set
     */
    public void setStock_State_In(String stock_State_In) {
        this.stock_State_In = stock_State_In;
    }

    /**
     * @return the samType
     */
    public String getSamType() {
        return samType;
    }

    /**
     * @param samType the samType to set
     */
    public void setSamType(String samType) {
        this.samType = samType;
    }

    /**
     * @return the stockEmptyQty
     */
    public String getStockEmptyQty() {
        return stockEmptyQty;
    }

    /**
     * @param stockEmptyQty the stockEmptyQty to set
     */
    public void setStockEmptyQty(String stockEmptyQty) {
        this.stockEmptyQty = stockEmptyQty;
    }

    /**
     * @return the etyWarnState
     */
    public String getEtyWarnState() {
        return etyWarnState;
    }

    /**
     * @param etyWarnState the etyWarnState to set
     */
    public void setEtyWarnState(String etyWarnState) {
        this.etyWarnState = etyWarnState;
    }

    /**
     * @return the stockProductQty
     */
    public String getStockProductQty() {
        return stockProductQty;
    }

    /**
     * @param stockProductQty the stockProductQty to set
     */
    public void setStockProductQty(String stockProductQty) {
        this.stockProductQty = stockProductQty;
    }

    /**
     * @return the pduWarnState
     */
    public String getPduWarnState() {
        return pduWarnState;
    }

    /**
     * @param pduWarnState the pduWarnState to set
     */
    public void setPduWarnState(String pduWarnState) {
        this.pduWarnState = pduWarnState;
    }

    /**
     * @return the samTypeText
     */
    public String getSamTypeText() {
        return samTypeText;
    }

    /**
     * @param samTypeText the samTypeText to set
     */
    public void setSamTypeText(String samTypeText) {
        this.samTypeText = samTypeText;
    }

    /**
     * @return the etyWarnStateText
     */
    public String getEtyWarnStateText() {
        return etyWarnStateText;
    }

    /**
     * @param etyWarnStateText the etyWarnStateText to set
     */
    public void setEtyWarnStateText(String etyWarnStateText) {
        this.etyWarnStateText = etyWarnStateText;
    }

    /**
     * @return the pduWarnStateText
     */
    public String getPduWarnStateText() {
        return pduWarnStateText;
    }

    /**
     * @param pduWarnStateText the pduWarnStateText to set
     */
    public void setPduWarnStateText(String pduWarnStateText) {
        this.pduWarnStateText = pduWarnStateText;
    }
    //SAM类型
    private String samType;
    
    //空白卡库存数量
    private String stockEmptyQty;
    
    //空白卡预警状态
    private String etyWarnState;
    
    //成品卡库存数量
    private String stockProductQty;
    
    //成品卡预警状态
    private String pduWarnState;
    
    private String samTypeText;
    
    private String etyWarnStateText;
    
    private String pduWarnStateText;
    
    private String produce_Type_Empty;
    
    private String produce_Type_Product;
    
    private String stock_State_In;
    
    private String sam_Card_State_Ok;
}
