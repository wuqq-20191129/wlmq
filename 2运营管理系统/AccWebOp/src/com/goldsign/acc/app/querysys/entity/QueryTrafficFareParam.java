/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.entity;

import java.io.Serializable;

/**
 *
 * @author luck
 */
public class QueryTrafficFareParam implements Serializable{
    
    private String card_main_id;
    private String card_main_id_text;
    private String card_sub_id;
    private String card_sub_id_text;
    private Double distince;
    private String fare;
    private String fareRideType;
    private String record_flag;
    
    private String sign;
    private String sign1;

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

    public Double getDistince() {
        return distince;
    }

    public void setDistince(Double distince) {
        this.distince = distince;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getFareRideType() {
        return fareRideType;
    }

    public void setFareRideType(String fareRideType) {
        this.fareRideType = fareRideType;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign1() {
        return sign1;
    }

    public void setSign1(String sign1) {
        this.sign1 = sign1;
    }
    
    
    
    
}
