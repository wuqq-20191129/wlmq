/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.st.entity;

import java.io.Serializable;

/**
 *
 * @author liudz 交易表分表实体类
 */
public class StTrxIndex implements Serializable {

    private String table_name;
    private String date_type;
    private String recd_type;
    private String begin_date;
    private String end_date;
    private String recd_count;

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getDate_type() {
        return date_type;
    }

    public void setDate_type(String date_type) {
        this.date_type = date_type;
    }

    public String getRecd_type() {
        return recd_type;
    }

    public void setRecd_type(String recd_type) {
        this.recd_type = recd_type;
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

    public String getRecd_count() {
        return recd_count;
    }

    public void setRecd_count(String recd_count) {
        this.recd_count = recd_count;
    }

}
