/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.entity;

import com.goldsign.acc.app.prmdev.entity.*;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import java.io.Serializable;

/**
 *
 * @author mqf
 */
public class FareTable extends PrmVersion implements Serializable {

    private Long water_no;
    private String fare_table_id;
    private String fare;
    private String fare_zone;
    private String version_no;
    private String record_flag;
    private String fare_name;

    public Long getWater_no() {
        return water_no;
    }

    public void setWater_no(Long water_no) {
        this.water_no = water_no;
    }

    public String getFare_table_id() {
        return fare_table_id;
    }

    public void setFare_table_id(String fare_table_id) {
        this.fare_table_id = fare_table_id;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    
    public String getFare_zone() {
        return fare_zone;
    }

    public void setFare_zone(String fare_zone) {
        this.fare_zone = fare_zone;
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

    public String getFare_name() {
        return fare_name;
    }

    public void setFare_name(String fare_name) {
        this.fare_name = fare_name;
    }

}
