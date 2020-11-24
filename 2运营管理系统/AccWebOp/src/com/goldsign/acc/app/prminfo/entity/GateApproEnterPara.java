/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;

/**
 *
 * @author zhouyang
 * 20170607
 * 闸机专用通道参数
 */
public class GateApproEnterPara extends PrmVersion implements Serializable{
    private String card_main_id;//票卡主类型id
    private String card_sub_id;//票卡子类型id
    private String discount;//是否优惠票id
    private String sound;//是否启用语音提示id
    private String record_flag;//版本标志id
    private String version_no;//版本号
    private String line_id_name;//线路中文名称
    private String station_id_name;//车站中文名称
    private String card_main_id_name;//票卡主类型中文名称
    private String card_sub_id_name;//票卡子类型中文名称
    private String discount_name;//是否优惠票中文名称
    private String sound_name;//是否启用语音提示中文名称
    private String record_flag_name;//版本标志中文名称

    public String getDiscount_name() {
        return discount_name;
    }

    public void setDiscount_name(String discount_name) {
        this.discount_name = discount_name;
    }

    public String getSound_name() {
        return sound_name;
    }

    public void setSound_name(String sound_name) {
        this.sound_name = sound_name;
    }

    @Override
    public String getRecord_flag_name() {
        return record_flag_name;
    }

    @Override
    public void setRecord_flag_name(String record_flag_name) {
        this.record_flag_name = record_flag_name;
    }

    public String getLine_id_name() {
        return line_id_name;
    }

    public void setLine_id_name(String line_id_name) {
        this.line_id_name = line_id_name;
    }

    public String getStation_id_name() {
        return station_id_name;
    }

    public void setStation_id_name(String station_id_name) {
        this.station_id_name = station_id_name;
    }

    public String getCard_main_id_name() {
        return card_main_id_name;
    }

    public void setCard_main_id_name(String card_main_id_name) {
        this.card_main_id_name = card_main_id_name;
    }

    public String getCard_sub_id_name() {
        return card_sub_id_name;
    }

    public void setCard_sub_id_name(String card_sub_id_name) {
        this.card_sub_id_name = card_sub_id_name;
    }

    public String getCard_main_id() {
        return card_main_id;
    }

    public void setCard_main_id(String card_main_id) {
        this.card_main_id = card_main_id;
    }

    public String getCard_sub_id() {
        return card_sub_id;
    }

    public void setCard_sub_id(String card_sub_id) {
        this.card_sub_id = card_sub_id;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    @Override
    public String getRecord_flag() {
        return record_flag;
    }

    @Override
    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    @Override
    public String getVersion_no() {
        return version_no;
    }

    @Override
    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }
    
}
