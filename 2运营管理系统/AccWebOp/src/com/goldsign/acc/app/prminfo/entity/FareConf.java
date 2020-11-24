/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.entity;

import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import java.io.Serializable;

/**
 *
 * @author mqf
 */
public class FareConf extends PrmVersion implements Serializable{

    private Long water_no;
    private String time_code;
    private String card_sub_id;
    private String card_main_id;
    private String fare_table_id;
    private String version_no;
    private String record_flag;
    private String fare_time_id;//联乘时间间隔代码
    private String fare_deal_id;//累计消费额代码
    
    private String time_code_text;
    private String card_sub_id_text;
    private String card_main_id_text;

    public String getFare_time_id() {
        return fare_time_id;
    }

    public void setFare_time_id(String fare_time_id) {
        this.fare_time_id = fare_time_id;
    }

    public String getFare_deal_id() {
        return fare_deal_id;
    }

    public void setFare_deal_id(String fare_deal_id) {
        this.fare_deal_id = fare_deal_id;
    }

    public Long getWater_no() {
        return water_no;
    }

    public void setWater_no(Long water_no) {
        this.water_no = water_no;
    }

    public String getTime_code() {
        return time_code;
    }

    public void setTime_code(String time_code) {
        this.time_code = time_code;
    }

    public String getCard_sub_id() {
        return card_sub_id;
    }

    public void setCard_sub_id(String card_sub_id) {
        this.card_sub_id = card_sub_id;
    }

    public String getCard_main_id() {
        return card_main_id;
    }

    public void setCard_main_id(String card_main_id) {
        this.card_main_id = card_main_id;
    }

    public String getFare_table_id() {
        return fare_table_id;
    }

    public void setFare_table_id(String fare_table_id) {
        this.fare_table_id = fare_table_id;
    }

    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }


    public String getTime_code_text() {
        return time_code_text;
    }

    public void setTime_code_text(String time_code_text) {
        this.time_code_text = time_code_text;
    }

    public String getCard_sub_id_text() {
        return card_sub_id_text;
    }

    public void setCard_sub_id_text(String card_sub_id_text) {
        this.card_sub_id_text = card_sub_id_text;
    }

    public String getCard_main_id_text() {
        return card_main_id_text;
    }

    public void setCard_main_id_text(String card_main_id_text) {
        this.card_main_id_text = card_main_id_text;
    }
    
    

    
}
