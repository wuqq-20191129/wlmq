package com.goldsign.acc.app.system.entity;

import java.io.Serializable;

/**
 * ORACLE监视
 *
 * @author xiaowu
 */
public class Sybase extends BaseMessage implements Serializable {
    //modyfy by zhongzq 20190419 抽离公共字段
//    private String ip;
//    private String status;
    private String status_text;
    //    private String status_date;
    private String free_data;
    private String used_data_rate;
    private String free_data_1;
    private String used_data_rate_1;
    private String free_index;
    private String used_index_rate;
    private String backup_size;
    private String backup_start_time;
    private String backup_end_time;
    private String backup_interval;
    private String remark;
    private String free_log;
    private String used_log_rate;
    //    private String name;
    private String type;
    private String ip_value;
    
    private String tablespace_name;
    private String total_mb;
    private String used_mb;
    private String used_pct;
    //add by zhongzq 20191017
    private String recovery_avail_mb;
    private String recovery_used_pct;

    
    private String redOr;

    private String redOrForRecovery;

    public String getRedOrForRecovery() {
        return redOrForRecovery;
    }

    public void setRedOrForRecovery(String redOrForRecovery) {
        this.redOrForRecovery = redOrForRecovery;
    }

    public String getRedOr() {
        return redOr;
    }

    public void setRedOr(String redOr) {
        this.redOr = redOr;
    }

    public String getTablespace_name() {
        return tablespace_name;
    }

    public void setTablespace_name(String tablespace_name) {
        this.tablespace_name = tablespace_name;
    }

    public String getTotal_mb() {
        return total_mb;
    }

    public void setTotal_mb(String total_mb) {
        this.total_mb = total_mb;
    }

    public String getUsed_mb() {
        return used_mb;
    }

    public void setUsed_mb(String used_mb) {
        this.used_mb = used_mb;
    }

    public String getUsed_pct() {
        return used_pct;
    }

    public void setUsed_pct(String used_pct) {
        this.used_pct = used_pct;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

//    public String getIp() {
//        return ip;
//    }
//
//    public void setIp(String ip) {
//        this.ip = ip;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getStatus_date() {
//        return status_date;
//    }
//
//    public void setStatus_date(String status_date) {
//        this.status_date = status_date;
//    }

    public String getFree_data() {
        return free_data;
    }

    public void setFree_data(String free_data) {
        this.free_data = free_data;
    }

    public String getUsed_data_rate() {
        return used_data_rate;
    }

    public void setUsed_data_rate(String used_data_rate) {
        this.used_data_rate = used_data_rate;
    }

    public String getFree_data_1() {
        return free_data_1;
    }

    public void setFree_data_1(String free_data_1) {
        this.free_data_1 = free_data_1;
    }

    public String getUsed_data_rate_1() {
        return used_data_rate_1;
    }

    public void setUsed_data_rate_1(String used_data_rate_1) {
        this.used_data_rate_1 = used_data_rate_1;
    }

    public String getFree_index() {
        return free_index;
    }

    public void setFree_index(String free_index) {
        this.free_index = free_index;
    }

    public String getUsed_index_rate() {
        return used_index_rate;
    }

    public void setUsed_index_rate(String used_index_rate) {
        this.used_index_rate = used_index_rate;
    }

    public String getBackup_size() {
        return backup_size;
    }

    public void setBackup_size(String backup_size) {
        this.backup_size = backup_size;
    }

    public String getBackup_start_time() {
        return backup_start_time;
    }

    public void setBackup_start_time(String backup_start_time) {
        this.backup_start_time = backup_start_time;
    }

    public String getBackup_end_time() {
        return backup_end_time;
    }

    public void setBackup_end_time(String backup_end_time) {
        this.backup_end_time = backup_end_time;
    }

    public String getBackup_interval() {
        return backup_interval;
    }

    public void setBackup_interval(String backup_interval) {
        this.backup_interval = backup_interval;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFree_log() {
        return free_log;
    }

    public void setFree_log(String free_log) {
        this.free_log = free_log;
    }

    public String getUsed_log_rate() {
        return used_log_rate;
    }

    public void setUsed_log_rate(String used_log_rate) {
        this.used_log_rate = used_log_rate;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp_value() {
        return ip_value;
    }

    public void setIp_value(String ip_value) {
        this.ip_value = ip_value;
    }

    public String getRecovery_avail_mb() {
        return recovery_avail_mb;
    }

    public void setRecovery_avail_mb(String recovery_avail_mb) {
        this.recovery_avail_mb = recovery_avail_mb;
    }

    public String getRecovery_used_pct() {
        return recovery_used_pct;
    }

    public void setRecovery_used_pct(String recovery_used_pct) {
        this.recovery_used_pct = recovery_used_pct;
    }
}
