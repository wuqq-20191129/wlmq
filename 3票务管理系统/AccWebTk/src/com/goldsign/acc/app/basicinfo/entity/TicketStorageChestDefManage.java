package com.goldsign.acc.app.basicinfo.entity;

import java.io.Serializable;

/**
 * 票柜定义
 * @author xiaowu
 * 20170726
 */
public class TicketStorageChestDefManage implements Serializable{
    
    private String storage_id;              // 仓库 id
    private String storage_name;            // 仓库名称
    private String area_id;                 // 票区 id
    private String area_name;               // 票区名称
    private String chest_id;                // 票柜代码
    private String chest_name;              // 票柜名称
    private String ic_main_type;            // 票卡主类型
    private String ic_main_desc;
    private String ic_sub_type;             // 票卡子类型
    private String ic_sub_desc;
    private String storey_id;               //层id
    private String storey_name;            //层名
    private String storey_num;              // 层数
    private String card_money;              // 面值
    private String base_num;                // 托数
    private String max_box_num;             // 托最大盒数
    private String full_flag;
    
    private String real_num;
    private String base_id;
    private String base_name; 
    
    private String upper_num;
    
    private String box_unit;
    
    private String card_num;
    
    private String put_place;
    private String next_place;

    public String getNext_place() {
        return next_place;
    }

    public void setNext_place(String next_place) {
        this.next_place = next_place;
    }

    public String getPut_place() {
        return put_place;
    }

    public void setPut_place(String put_place) {
        this.put_place = put_place;
    }

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getUpper_num() {
        return upper_num;
    }

    public void setUpper_num(String upper_num) {
        this.upper_num = upper_num;
    }

    public String getBox_unit() {
        return box_unit;
    }

    public void setBox_unit(String box_unit) {
        this.box_unit = box_unit;
    }
    
    public String getBase_id() {
        return base_id;
    }

    public void setBase_id(String base_id) {
        this.base_id = base_id;
    }

    public String getBase_name() {
        return base_name;
    }

    public void setBase_name(String base_name) {
        this.base_name = base_name;
    }
    
    

    public String getReal_num() {
        return real_num;
    }

    public void setReal_num(String real_num) {
        this.real_num = real_num;
    }

    public String getStorey_name() {
        return storey_name;
    }

    public void setStorey_name(String storey_name) {
        this.storey_name = storey_name;
    }
    
    public String getStorey_id() {
        return storey_id;
    }

    public void setStorey_id(String storey_id) {
        this.storey_id = storey_id;
    }
    
    public String getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(String storage_id) {
        this.storage_id = storage_id;
    }

    public String getStorage_name() {
        return storage_name;
    }

    public void setStorage_name(String storage_name) {
        this.storage_name = storage_name;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getChest_id() {
        return chest_id;
    }

    public void setChest_id(String chest_id) {
        this.chest_id = chest_id;
    }

    public String getChest_name() {
        return chest_name;
    }

    public void setChest_name(String chest_name) {
        this.chest_name = chest_name;
    }

    public String getIc_main_type() {
        return ic_main_type;
    }

    public void setIc_main_type(String ic_main_type) {
        this.ic_main_type = ic_main_type;
    }

    public String getIc_main_desc() {
        return ic_main_desc;
    }

    public void setIc_main_desc(String ic_main_desc) {
        this.ic_main_desc = ic_main_desc;
    }

    public String getIc_sub_type() {
        return ic_sub_type;
    }

    public void setIc_sub_type(String ic_sub_type) {
        this.ic_sub_type = ic_sub_type;
    }

    public String getIc_sub_desc() {
        return ic_sub_desc;
    }

    public void setIc_sub_desc(String ic_sub_desc) {
        this.ic_sub_desc = ic_sub_desc;
    }

    public String getStorey_num() {
        return storey_num;
    }

    public void setStorey_num(String storey_num) {
        this.storey_num = storey_num;
    }

    public String getCard_money() {
        return card_money;
    }

    public void setCard_money(String card_money) {
        this.card_money = card_money;
    }

    public String getBase_num() {
        return base_num;
    }

    public void setBase_num(String base_num) {
        this.base_num = base_num;
    }

    public String getMax_box_num() {
        return max_box_num;
    }

    public void setMax_box_num(String max_box_num) {
        this.max_box_num = max_box_num;
    }

    public String getFull_flag() {
        return full_flag;
    }

    public void setFull_flag(String full_flag) {
        this.full_flag = full_flag;
    }
    
}
