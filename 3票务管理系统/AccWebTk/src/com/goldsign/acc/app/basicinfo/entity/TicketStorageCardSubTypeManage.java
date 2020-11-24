/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.entity;

/**
 *
 * @author mh
 */
public class TicketStorageCardSubTypeManage {

    /**
     * @return the ic_main_type
     */
    public String getIc_main_type() {
        return ic_main_type;
    }

    /**
     * @param ic_main_type the ic_main_type to set
     */
    public void setIc_main_type(String ic_main_type) {
        this.ic_main_type = ic_main_type;
    }

    /**
     * @return the ic_main_desc
     */
    public String getIc_main_desc() {
        return ic_main_desc;
    }

    /**
     * @param ic_main_desc the ic_main_desc to set
     */
    public void setIc_main_desc(String ic_main_desc) {
        this.ic_main_desc = ic_main_desc;
    }

    /**
     * @return the ic_sub_type
     */
    public String getIc_sub_type() {
        return ic_sub_type;
    }

    /**
     * @param ic_sub_type the ic_sub_type to set
     */
    public void setIc_sub_type(String ic_sub_type) {
        this.ic_sub_type = ic_sub_type;
    }

    /**
     * @return the ic_sub_desc
     */
    public String getIc_sub_desc() {
        return ic_sub_desc;
    }

    /**
     * @param ic_sub_desc the ic_sub_desc to set
     */
    public void setIc_sub_desc(String ic_sub_desc) {
        this.ic_sub_desc = ic_sub_desc;
    }

    public String getIc_card_mainText() {
        return ic_card_mainText;
    }

    public void setIc_card_mainText(String ic_card_mainText) {
        this.ic_card_mainText = ic_card_mainText;
    }
    
  
    
    private String ic_main_type;
    private String ic_main_desc;
    private String ic_sub_type;
    private String ic_sub_desc;
    private String ic_card_mainText;
}
