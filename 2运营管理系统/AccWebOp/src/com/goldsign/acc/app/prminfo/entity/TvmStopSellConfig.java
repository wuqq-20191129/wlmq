/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;

/**
 *
 * @author zhouyang
 * 20170611
 * TVM停售时间配置参数
 */
public class TvmStopSellConfig extends PrmVersion implements Serializable{
    private String flow_id;//流水号
    private String version_no;//版本号
    private String record_flag;//版本标志
    private String timeTable_id;//时间表ID
    private String begin_date;//开始时间
    private String end_date;//结束时间
    private String param_type_id;//参数类型ID
    private String timeTable_id_name;//时间表类型名称
    private String record_flag_name;//版本号名称

    @Override
    public String getRecord_flag_name() {
        return record_flag_name;
    }

    @Override
    public void setRecord_flag_name(String record_flag_name) {
        this.record_flag_name = record_flag_name;
    }

    public String getTimeTable_id_name() {
        return timeTable_id_name;
    }

    public void setTimeTable_id_name(String timeTable_id_name) {
        this.timeTable_id_name = timeTable_id_name;
    }

    public String getFlow_id() {
        return flow_id;
    }

    public void setFlow_id(String flow_id) {
        this.flow_id = flow_id;
    }

    @Override
    public String getVersion_no() {
        return version_no;
    }

    @Override
    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    @Override
    public String getRecord_flag() {
        return record_flag;
    }

    @Override
    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    public String getTimeTable_id() {
        return timeTable_id;
    }

    public void setTimeTable_id(String timeTable_id) {
        this.timeTable_id = timeTable_id;
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

    public String getParam_type_id() {
        return param_type_id;
    }

    public void setParam_type_id(String param_type_id) {
        this.param_type_id = param_type_id;
    }
    
}
