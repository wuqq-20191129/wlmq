/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.entity;

import java.io.Serializable;

/**
 *票卡有效期
 * @author luck
 */
public class QueryCardExpDate implements Serializable{
    private String card_main_id;
    private String card_main_id_text;
    private String card_sub_id;
    private String card_sub_id_text;
    private String exp_date;
    private String record_flag;

    public String getCard_main_id() {
        return card_main_id;
    }

    public void setCard_main_id(String card_main_id) {
        this.card_main_id = card_main_id;
    }

    public String getCard_main_id_text() {
        return card_main_id_text;
    }

    public void setCard_main_id_text(String card_main_id_text) {
        this.card_main_id_text = card_main_id_text;
    }

    public String getCard_sub_id() {
        return card_sub_id;
    }

    public void setCard_sub_id(String card_sub_id) {
        this.card_sub_id = card_sub_id;
    }

    public String getCard_sub_id_text() {
        return card_sub_id_text;
    }

    public void setCard_sub_id_text(String card_sub_id_text) {
        this.card_sub_id_text = card_sub_id_text;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    
    
    
}
