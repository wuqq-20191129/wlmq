/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;

/**
 *
 * @author mqf
 */
public class HolidayPara extends PrmVersion implements Serializable{

    private Long water_no;
    private String holiday_type;
    private String start_date;
    private String end_date;
    private String version_no;
    private String record_flag;
    
    private String holiday_desc; //节假日名称

    public Long getWater_no() {
        return water_no;
    }

    public void setWater_no(Long water_no) {
        this.water_no = water_no;
    }

    public String getHoliday_type() {
        return holiday_type;
    }

    public void setHoliday_type(String holiday_type) {
        this.holiday_type = holiday_type;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
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

    public String getHoliday_desc() {
        return holiday_desc;
    }

    public void setHoliday_desc(String holiday_desc) {
        this.holiday_desc = holiday_desc;
    }

    
    
}
