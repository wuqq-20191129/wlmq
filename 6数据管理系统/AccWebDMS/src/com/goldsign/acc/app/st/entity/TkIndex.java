package com.goldsign.acc.app.st.entity;

import java.io.Serializable;

/**
 * 票务管理系统 - 分表记录
 *
 * @author xiaowu
 */
public class TkIndex implements Serializable {

    private String his_table;
    private String origin_table_name;
    private String begin_recd;
    private String end_recd;
    private String begin_time;
    private String end_time;
    private String recd_count;
    private String recd_type;

    public String getHis_table() {
        return his_table;
    }

    public void setHis_table(String his_table) {
        this.his_table = his_table;
    }

    public String getOrigin_table_name() {
        return origin_table_name;
    }

    public void setOrigin_table_name(String origin_table_name) {
        this.origin_table_name = origin_table_name;
    }

    public String getBegin_recd() {
        return begin_recd;
    }

    public void setBegin_recd(String begin_recd) {
        this.begin_recd = begin_recd;
    }

    public String getEnd_recd() {
        return end_recd;
    }

    public void setEnd_recd(String end_recd) {
        this.end_recd = end_recd;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getRecd_count() {
        return recd_count;
    }

    public void setRecd_count(String recd_count) {
        this.recd_count = recd_count;
    }

    public String getRecd_type() {
        return recd_type;
    }

    public void setRecd_type(String recd_type) {
        this.recd_type = recd_type;
    }
    
    
    
   
}
