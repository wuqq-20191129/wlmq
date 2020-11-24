/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.st.entity;

import java.io.Serializable;

/**
 *
 * @author liudz
 * 中间表分表实体类
 */
public class StRptIndex implements Serializable{
    private String table_name;
    private String table_type;
    private String origin_table_name;
    private String begin_balance_no;
    private String end_balance_no;
    private String min_squad_day;
    private String max_squad_day;
    private String recd_count;
    private String begin_date;
    private String end_date;
    private String create_time;

    public String getTable_type() {
        return table_type;
    }

    public void setTable_type(String table_type) {
        this.table_type = table_type;
    }
    
    

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getOrigin_table_name() {
        return origin_table_name;
    }

    public void setOrigin_table_name(String origin_table_name) {
        this.origin_table_name = origin_table_name;
    }

    public String getBegin_balance_no() {
        return begin_balance_no;
    }

    public void setBegin_balance_no(String begin_balance_no) {
        this.begin_balance_no = begin_balance_no;
    }

    public String getEnd_balance_no() {
        return end_balance_no;
    }

    public void setEnd_balance_no(String end_balance_no) {
        this.end_balance_no = end_balance_no;
    }

    public String getMin_squad_day() {
        return min_squad_day;
    }

    public void setMin_squad_day(String min_squad_day) {
        this.min_squad_day = min_squad_day;
    }

    public String getMax_squad_day() {
        return max_squad_day;
    }

    public void setMax_squad_day(String max_squad_day) {
        this.max_squad_day = max_squad_day;
    }

    public String getRecd_count() {
        return recd_count;
    }

    public void setRecd_count(String recd_count) {
        this.recd_count = recd_count;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    
    
    
    
}
