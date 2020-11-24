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
 * 20170613
 * 工作日时间表参数
 */
public class TvmStopsellDef extends PrmVersion implements Serializable{
    private String version_no;//版本号
    private String record_flag;//版本标志
    private String parm_type_id;//参数类型ID
    private String timetable_id;//时间表ID
    private String timetable_name;//时间表类型
    private String start_station;//出发线路站
    private String start_line_id;//出发线路id
    private String start_line_name;///出发线路名称
    private String start_station_id;//出发车站id
    private String start_station_name;//出发车站名称
    private String dest_station;//目的线路车站
    private String dest_line_id;//目的线路id
    private String dest_line_name;//目的线路名称
    private String dest_station_id;//目的车站id
    private String dest_station_name;//目的车站名称
    private String stopsell_time1;//停止发售单程票开始时间1（提示时间）
    private String stopsell_time2;//停止发售单程票开始时间2（停售时间）
    private String stopsell_endtime;//停止发售单程票结束时间

    public String getStart_line_id() {
        return start_line_id;
    }

    public void setStart_line_id(String start_line_id) {
        this.start_line_id = start_line_id;
    }

    public String getStart_station_id() {
        return start_station_id;
    }

    public void setStart_station_id(String start_station_id) {
        this.start_station_id = start_station_id;
    }

    public String getDest_line_id() {
        return dest_line_id;
    }

    public void setDest_line_id(String dest_line_id) {
        this.dest_line_id = dest_line_id;
    }

    public String getDest_station_id() {
        return dest_station_id;
    }

    public void setDest_station_id(String dest_station_id) {
        this.dest_station_id = dest_station_id;
    }

    public String getTimetable_name() {
        return timetable_name;
    }

    public void setTimetable_name(String timetable_name) {
        this.timetable_name = timetable_name;
    }

    public String getStart_line_name() {
        return start_line_name;
    }

    public void setStart_line_name(String start_line_name) {
        this.start_line_name = start_line_name;
    }

    public String getStart_station_name() {
        return start_station_name;
    }

    public void setStart_station_name(String start_station_name) {
        this.start_station_name = start_station_name;
    }

    public String getDest_line_name() {
        return dest_line_name;
    }

    public void setDest_line_name(String dest_line_name) {
        this.dest_line_name = dest_line_name;
    }

    public String getDest_station_name() {
        return dest_station_name;
    }

    public void setDest_station_name(String dest_station_name) {
        this.dest_station_name = dest_station_name;
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

    @Override
    public String getParm_type_id() {
        return parm_type_id;
    }

    @Override
    public void setParm_type_id(String parm_type_id) {
        this.parm_type_id = parm_type_id;
    }

    public String getTimetable_id() {
        return timetable_id;
    }

    public void setTimetable_id(String timetable_id) {
        this.timetable_id = timetable_id;
    }

    public String getStart_station() {
        return start_station;
    }

    public void setStart_station(String start_station) {
        this.start_station = start_station;
    }

    public String getDest_station() {
        return dest_station;
    }

    public void setDest_station(String dest_station) {
        this.dest_station = dest_station;
    }

    public String getStopsell_time1() {
        return stopsell_time1;
    }

    public void setStopsell_time1(String stopsell_time1) {
        this.stopsell_time1 = stopsell_time1;
    }

    public String getStopsell_time2() {
        return stopsell_time2;
    }

    public void setStopsell_time2(String stopsell_time2) {
        this.stopsell_time2 = stopsell_time2;
    }

    public String getStopsell_endtime() {
        return stopsell_endtime;
    }

    public void setStopsell_endtime(String stopsell_endtime) {
        this.stopsell_endtime = stopsell_endtime;
    }
    
}
