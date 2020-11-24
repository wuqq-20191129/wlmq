/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.entity;

import java.io.Serializable;

/**
 *
 * @author mqf
 */
public class TicketStorageOutAdjustBillDetail implements Serializable {
    
    private Long water_no;
    private String bill_no;
    private String storage_id;
    private String area_id;
    private String ic_main_type;
    private String ic_sub_type;
    private int draw_quantity;
    private int real_quantity;
    private int error_quantity;
    private String valid_date;
    private String card_money;
    private String line_id;
    private String station_id;
    private String start_box_id;
    private String end_box_id;
    private String start_logical_id;
    private String end_logical_id;
    private String adjust_id;
    private String storey_id;
    private String base_id;
    private String chest_id;
    private String exit_line_id;
    private String exit_station_id;
    private String model;
    
//    页面回显
    private String record_flag;
    
    private String adjust_id_name;
    
    private String storage_id_name;//仓库代码
    private String area_id_name;//票区代码
    private String ic_main_type_name;//在票务系统中的票卡主类型
    private String ic_sub_type_name;//在票务系统中的票卡子类型
    private String line_id_name;//线路代码
    private String station_id_name;//车站代码
//    private String use_flag_name;//使用标志
    private String exit_line_id_name;//限制出站线路（多日票使用）
    private String exit_station_id_name;//限制出站车站（多日票使用）
    private String model_name;//限制模式（多日票使用）

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

    public String getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(String storage_id) {
        this.storage_id = storage_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
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

    public int getDraw_quantity() {
        return draw_quantity;
    }

    public void setDraw_quantity(int draw_quantity) {
        this.draw_quantity = draw_quantity;
    }

    public int getReal_quantity() {
        return real_quantity;
    }

    public void setReal_quantity(int real_quantity) {
        this.real_quantity = real_quantity;
    }

    public int getError_quantity() {
        return error_quantity;
    }

    public void setError_quantity(int error_quantity) {
        this.error_quantity = error_quantity;
    }

    public String getValid_date() {
        return valid_date;
    }

    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }

    public String getCard_money() {
        return card_money;
    }

    public void setCard_money(String card_money) {
        this.card_money = card_money;
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

    public String getStart_logical_id() {
        return start_logical_id;
    }

    public void setStart_logical_id(String start_logical_id) {
        this.start_logical_id = start_logical_id;
    }

    public String getEnd_logical_id() {
        return end_logical_id;
    }

    public void setEnd_logical_id(String end_logical_id) {
        this.end_logical_id = end_logical_id;
    }

    public String getAdjust_id() {
        return adjust_id;
    }

    public void setAdjust_id(String adjust_id) {
        this.adjust_id = adjust_id;
    }

    public String getStorey_id() {
        return storey_id;
    }

    public void setStorey_id(String storey_id) {
        this.storey_id = storey_id;
    }

    public String getBase_id() {
        return base_id;
    }

    public void setBase_id(String base_id) {
        this.base_id = base_id;
    }

    public String getChest_id() {
        return chest_id;
    }

    public void setChest_id(String chest_id) {
        this.chest_id = chest_id;
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

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    public String getAdjust_id_name() {
        return adjust_id_name;
    }

    public void setAdjust_id_name(String adjust_id_name) {
        this.adjust_id_name = adjust_id_name;
    }

    public String getStorage_id_name() {
        return storage_id_name;
    }

    public void setStorage_id_name(String storage_id_name) {
        this.storage_id_name = storage_id_name;
    }

    public String getArea_id_name() {
        return area_id_name;
    }

    public void setArea_id_name(String area_id_name) {
        this.area_id_name = area_id_name;
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

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }
    
    
    
    
}
