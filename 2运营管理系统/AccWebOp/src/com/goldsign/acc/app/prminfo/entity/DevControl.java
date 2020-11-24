package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;

/**
 * 设备控制--设备控制
 * @author xiaowu
 * @version 20170614
 */
public class DevControl extends PrmVersion  implements Serializable{
    
    private String logoff_idle_sc;
    private String interval_connectsc;
    private String logoff_idle_bom;
    private String time_out_pass;
    private String operator_id;
    private String degrade_time;
    private String upper_amount;
    private String upper_count;
    private String upper_sjt;
    private String under_amount;
    private String under_count;
    private String under_sjt;
    private String version_no;
    private String record_flag;

    public String getLogoff_idle_sc() {
        return logoff_idle_sc;
    }

    public void setLogoff_idle_sc(String logoff_idle_sc) {
        this.logoff_idle_sc = logoff_idle_sc;
    }

    public String getInterval_connectsc() {
        return interval_connectsc;
    }

    public void setInterval_connectsc(String interval_connectsc) {
        this.interval_connectsc = interval_connectsc;
    }

    public String getLogoff_idle_bom() {
        return logoff_idle_bom;
    }

    public void setLogoff_idle_bom(String logoff_idle_bom) {
        this.logoff_idle_bom = logoff_idle_bom;
    }

    public String getTime_out_pass() {
        return time_out_pass;
    }

    public void setTime_out_pass(String time_out_pass) {
        this.time_out_pass = time_out_pass;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public String getDegrade_time() {
        return degrade_time;
    }

    public void setDegrade_time(String degrade_time) {
        this.degrade_time = degrade_time;
    }

    public String getUpper_amount() {
        return upper_amount;
    }

    public void setUpper_amount(String upper_amount) {
        this.upper_amount = upper_amount;
    }

    public String getUpper_count() {
        return upper_count;
    }

    public void setUpper_count(String upper_count) {
        this.upper_count = upper_count;
    }

    public String getUpper_sjt() {
        return upper_sjt;
    }

    public void setUpper_sjt(String upper_sjt) {
        this.upper_sjt = upper_sjt;
    }

    public String getUnder_amount() {
        return under_amount;
    }

    public void setUnder_amount(String under_amount) {
        this.under_amount = under_amount;
    }

    public String getUnder_count() {
        return under_count;
    }

    public void setUnder_count(String under_count) {
        this.under_count = under_count;
    }

    public String getUnder_sjt() {
        return under_sjt;
    }

    public void setUnder_sjt(String under_sjt) {
        this.under_sjt = under_sjt;
    }

    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

}
