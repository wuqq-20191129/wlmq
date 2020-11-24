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
public class FareZone extends PrmVersion implements Serializable{

     
    private Long water_no; //NUMBER(18)
    
    private String b_line_id;
    
    private String b_station_id;
    
    private String e_line_id;
    
    private String e_station_id;
    
    private String fare_zone;
    
    private String max_travel_time;
    
    private String over_time_charge;
    
    private String version_no;
    
    private String record_flag;
    
    private Double distince;  //NUMBER(11,7)
    
    
    
    private String entry_line_text;
    
    private String entry_station_text;
    
    private String exit_line_text;
    
    private String exit_station_text;
    
    private String fare_zone_text;
    

    public Long getWater_no() {
        return water_no;
    }

    public void setWater_no(Long water_no) {
        this.water_no = water_no;
    }

    public String getB_line_id() {
        return b_line_id;
    }

    public void setB_line_id(String b_line_id) {
        this.b_line_id = b_line_id;
    }

    public String getB_station_id() {
        return b_station_id;
    }

    public void setB_station_id(String b_station_id) {
        this.b_station_id = b_station_id;
    }

    public String getE_line_id() {
        return e_line_id;
    }

    public void setE_line_id(String e_line_id) {
        this.e_line_id = e_line_id;
    }

    public String getE_station_id() {
        return e_station_id;
    }

    public void setE_station_id(String e_station_id) {
        this.e_station_id = e_station_id;
    }

    public String getFare_zone() {
        return fare_zone;
    }

    public void setFare_zone(String fare_zone) {
        this.fare_zone = fare_zone;
    }

    public String getMax_travel_time() {
        return max_travel_time;
    }

    public void setMax_travel_time(String max_travel_time) {
        this.max_travel_time = max_travel_time;
    }

    public String getOver_time_charge() {
        return over_time_charge;
    }

    public void setOver_time_charge(String over_time_charge) {
        this.over_time_charge = over_time_charge;
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

    public Double getDistince() {
        return distince;
    }

    public void setDistince(Double distince) {
        this.distince = distince;
    }

    public String getEntry_line_text() {
        return entry_line_text;
    }

    public void setEntry_line_text(String entry_line_text) {
        this.entry_line_text = entry_line_text;
    }

    public String getEntry_station_text() {
        return entry_station_text;
    }

    public void setEntry_station_text(String entry_station_text) {
        this.entry_station_text = entry_station_text;
    }

    public String getExit_line_text() {
        return exit_line_text;
    }

    public void setExit_line_text(String exit_line_text) {
        this.exit_line_text = exit_line_text;
    }

    public String getExit_station_text() {
        return exit_station_text;
    }

    public void setExit_station_text(String exit_station_text) {
        this.exit_station_text = exit_station_text;
    }

    public String getFare_zone_text() {
        return fare_zone_text;
    }

    public void setFare_zone_text(String fare_zone_text) {
        this.fare_zone_text = fare_zone_text;
    }
 
    

    
}
