/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.entity;

import java.io.Serializable;

/**
 *
 * @author zhouyang
 * 新票入库明细
 * 20170803
 */
public class TicketStorageInNewDetail implements Serializable {
    private String water_no;//流水号
    private String bill_no;//入库单号
    private String reason_id;//原因代码
    private String storage_id;//仓库代码
    private String area_id;//票区代码
    private String ic_main_type;//在票务系统中的票卡主类型
    private String ic_sub_type;//在票务系统中的票卡子类型
    private String in_num;//入库数量
    private String detail_place;//详细位置，采用代码定长中间用"-"分割的方式表示例：0001-01-001-01-01：表示0001号仓库01票区001柜01层01托0000-00-000-00-00：表示出入库不需指定具体位置
    private String start_box_id;//票盒起号
    private String end_box_id;//票盒止号
    private String start_logical_id;//车票ID起号
    private String end_logical_id;//车票ID止号
    private String valid_date;//有效期
    private String card_money;//面值
    private String line_id;//线路代码
    private String station_id;//车站代码
    private String use_flag;//使用标志
    private String report_date;//报表日期，用于回收入库
    private String tickettype_id;//票务接口上传的票卡类型，用于回收入库
    private String card_ava_days;//有效天数（多日票使用）
    private String line_id_reclaim;//回收线路（多日票使用）
    private String station_id_reclaim;//回收车站（多日票使用）
    private String exit_line_id;//限制出站线路（多日票使用）
    private String exit_station_id;//限制出站车站（多日票使用）
    private String model;//限制模式（多日票使用）
    
    private String record_flag;//单据状态
    private String reason_name;//入库原因名称(用于页面显示)
    private String storage_name;//仓库名称（用天页面显示）
    private String area_name;//票区名称
    private String ic_main_type_name;//票卡主类型名称（用于页面显示）
    private String ic_sub_type_name;//票卡子类型名称（用于页面显示）
    private String model_name;//限制模式名称（用于页面显示）

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getReason_name() {
        return reason_name;
    }

    public void setReason_name(String reason_name) {
        this.reason_name = reason_name;
    }

    public String getStorage_name() {
        return storage_name;
    }

    public void setStorage_name(String storage_name) {
        this.storage_name = storage_name;
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

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    public String getWater_no() {
        return water_no;
    }

    public void setWater_no(String water_no) {
        this.water_no = water_no;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getReason_id() {
        return reason_id;
    }

    public void setReason_id(String reason_id) {
        this.reason_id = reason_id;
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

    public String getIn_num() {
        return in_num;
    }

    public void setIn_num(String in_num) {
        this.in_num = in_num;
    }

    public String getDetail_place() {
        return detail_place;
    }

    public void setDetail_place(String detail_place) {
        this.detail_place = detail_place;
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

    public String getUse_flag() {
        return use_flag;
    }

    public void setUse_flag(String use_flag) {
        this.use_flag = use_flag;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public String getTickettype_id() {
        return tickettype_id;
    }

    public void setTickettype_id(String tickettype_id) {
        this.tickettype_id = tickettype_id;
    }

    public String getCard_ava_days() {
        return card_ava_days;
    }

    public void setCard_ava_days(String card_ava_days) {
        this.card_ava_days = card_ava_days;
    }

    public String getLine_id_reclaim() {
        return line_id_reclaim;
    }

    public void setLine_id_reclaim(String line_id_reclaim) {
        this.line_id_reclaim = line_id_reclaim;
    }

    public String getStation_id_reclaim() {
        return station_id_reclaim;
    }

    public void setStation_id_reclaim(String station_id_reclaim) {
        this.station_id_reclaim = station_id_reclaim;
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
    
}
