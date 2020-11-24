/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.commu.entity;

/**
 *
 * @author chenzx
 */
public class CmConfig {
    
    private String origin_table_name;
    private String keep_days;
    private String ab_name;
    private String date_type;
    private String divide_recd_count;
    private String clear_flag;

    public String getOrigin_table_name() {
        return origin_table_name;
    }

    public void setOrigin_table_name(String origin_table_name) {
        this.origin_table_name = origin_table_name;
    }

    public String getKeep_days() {
        return keep_days;
    }

    public void setKeep_days(String keep_days) {
        this.keep_days = keep_days;
    }

    public String getAb_name() {
        return ab_name;
    }

    public void setAb_name(String ab_name) {
        this.ab_name = ab_name;
    }

    public String getDate_type() {
        return date_type;
    }

    public void setDate_type(String date_type) {
        this.date_type = date_type;
    }
    
    public String getDivide_recd_count() {
        return divide_recd_count;
    }

    public void setDivide_recd_count(String divide_recd_count) {
        this.divide_recd_count = divide_recd_count;
    }

    public String getClear_flag() {
        return clear_flag;
    }

    public void setClear_flag(String clear_flag) {
        this.clear_flag = clear_flag;
    }
    
}
