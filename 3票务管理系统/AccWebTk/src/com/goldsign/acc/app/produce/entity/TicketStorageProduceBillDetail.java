/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.produce.entity;

import java.io.Serializable;


/**
 *
 * @author mqf
 */
public class TicketStorageProduceBillDetail implements Serializable {
    
    private Long water_no;
    private String bill_no; //生产单号
    private String ic_main_type; //
    private String ic_sub_type; //
    private int card_money; //面值
    private String valid_date; //票卡有效期
    private String line_id; //
    private String station_id; //车站代码
    private int draw_quantity; //领票数量
    private String start_box_id; //票盒起号
    private String end_box_id; //票盒止号
    private String machine_no; //机器号
    private String card_ava_days; //
    private String exit_line_id; //
    private String exit_station_id; //
    private String model; //
    
    //页面显示中文
    private String ic_main_type_name; 
    private String ic_sub_type_name;
     private String line_id_name; 
    private String station_id_name; 
    private String exit_line_id_name; //
    private String exit_station_id_name; //
    private String model_name;

    public Long getWater_no() {
        return water_no;
    }

    public void setWater_no(Long water_no) {
        this.water_no = water_no;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getIc_main_type() {
        return ic_main_type;
    }

    public void setIc_main_type(String ic_main_type) {
        this.ic_main_type = ic_main_type;
    }

    public String getIc_sub_type() {
        return ic_sub_type;
    }

    public void setIc_sub_type(String ic_sub_type) {
        this.ic_sub_type = ic_sub_type;
    }

    public int getCard_money() {
        return card_money;
    }

    public void setCard_money(int card_money) {
        this.card_money = card_money;
    }

    public String getValid_date() {
        return valid_date;
    }

    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public int getDraw_quantity() {
        return draw_quantity;
    }

    public void setDraw_quantity(int draw_quantity) {
        this.draw_quantity = draw_quantity;
    }

    public String getStart_box_id() {
        return start_box_id;
    }

    public void setStart_box_id(String start_box_id) {
        this.start_box_id = start_box_id;
    }

    public String getEnd_box_id() {
        return end_box_id;
    }

    public void setEnd_box_id(String end_box_id) {
        this.end_box_id = end_box_id;
    }

    public String getMachine_no() {
        return machine_no;
    }

    public void setMachine_no(String machine_no) {
        this.machine_no = machine_no;
    }

    public String getCard_ava_days() {
        return card_ava_days;
    }

    public void setCard_ava_days(String card_ava_days) {
        this.card_ava_days = card_ava_days;
    }

    public String getExit_line_id() {
        return exit_line_id;
    }

    public void setExit_line_id(String exit_line_id) {
        this.exit_line_id = exit_line_id;
    }

    public String getExit_station_id() {
        return exit_station_id;
    }

    public void setExit_station_id(String exit_station_id) {
        this.exit_station_id = exit_station_id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getIc_main_type_name() {
        return ic_main_type_name;
    }

    public void setIc_main_type_name(String ic_main_type_name) {
        this.ic_main_type_name = ic_main_type_name;
    }

    public String getIc_sub_type_name() {
        return ic_sub_type_name;
    }

    public void setIc_sub_type_name(String ic_sub_type_name) {
        this.ic_sub_type_name = ic_sub_type_name;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
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

    public String getExit_line_id_name() {
        return exit_line_id_name;
    }

    public void setExit_line_id_name(String exit_line_id_name) {
        this.exit_line_id_name = exit_line_id_name;
    }

    public String getExit_station_id_name() {
        return exit_station_id_name;
    }

    public void setExit_station_id_name(String exit_station_id_name) {
        this.exit_station_id_name = exit_station_id_name;
    }
    
    
}
