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
public class CmClearLog {
    
    private String origin_table_name;
    private String begin_clear_datetime;
    private String end_clear_datetime;
    private String clear_recd_count;
    private String err_discribe;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigin_table_name() {
        return origin_table_name;
    }

    public void setOrigin_table_name(String origin_table_name) {
        this.origin_table_name = origin_table_name;
    }

    public String getBegin_clear_datetime() {
        return begin_clear_datetime;
    }

    public void setBegin_clear_datetime(String begin_clear_datetime) {
        this.begin_clear_datetime = begin_clear_datetime;
    }

    public String getEnd_clear_datetime() {
        return end_clear_datetime;
    }

    public void setEnd_clear_datetime(String end_clear_datetime) {
        this.end_clear_datetime = end_clear_datetime;
    }

    public String getClear_recd_count() {
        return clear_recd_count;
    }

    public void setClear_recd_count(String clear_recd_count) {
        this.clear_recd_count = clear_recd_count;
    }

    public String getErr_discribe() {
        return err_discribe;
    }

    public void setErr_discribe(String err_discribe) {
        this.err_discribe = err_discribe;
    }
}
