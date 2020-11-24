package com.goldsign.acc.app.st.entity;

import java.io.Serializable;

/**
 * 票务管理系统 - 同步日志
 *
 * @author xiaowu
 */
public class SynLog implements Serializable {

    private String src_db;
    private String src_table;
    private String dest_db;
    private String dest_table;
    private String syn_time;
    private String status;
    private String nums;
    private String remark;
    
    //临时参数
    private String start_syn_time;
    private String end_syn_time;

    public String getStart_syn_time() {
        return start_syn_time;
    }

    public void setStart_syn_time(String start_syn_time) {
        this.start_syn_time = start_syn_time;
    }

    public String getEnd_syn_time() {
        return end_syn_time;
    }

    public void setEnd_syn_time(String end_syn_time) {
        this.end_syn_time = end_syn_time;
    }

    public String getSrc_db() {
        return src_db;
    }

    public void setSrc_db(String src_db) {
        this.src_db = src_db;
    }

    public String getSrc_table() {
        return src_table;
    }

    public void setSrc_table(String src_table) {
        this.src_table = src_table;
    }

    public String getDest_db() {
        return dest_db;
    }

    public void setDest_db(String dest_db) {
        this.dest_db = dest_db;
    }

    public String getDest_table() {
        return dest_table;
    }

    public void setDest_table(String dest_table) {
        this.dest_table = dest_table;
    }

    public String getSyn_time() {
        return syn_time;
    }

    public void setSyn_time(String syn_time) {
        this.syn_time = syn_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
