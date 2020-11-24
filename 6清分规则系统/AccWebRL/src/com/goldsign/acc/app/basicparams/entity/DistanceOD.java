/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicparams.entity;

import java.io.Serializable;

/**
 * OD路径查询
 *
 * @author chenzx
 */
public class DistanceOD implements Serializable {

    private String id;     
    private String o_line_id; 
    private String o_station_id; 
    private String e_line_id; 
    private String e_station_id; 
    private String change_times; 
    private String distance; 
    private String min_distance;
    private String threshold;//阀值
    private String change_thres;//阀值
    
    private String record_flag;  
    
    private String oLineText;    
    private String oStationText;   
    private String eLineText;    
    private String eStationText; 
    private String isValidText;
    private String versionText;
    private String changDetailText;//换乘明细

    public String getChangDetailText() {
        return changDetailText;
    }

    public void setChangDetailText(String changDetailText) {
        this.changDetailText = changDetailText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getO_line_id() {
        return o_line_id;
    }

    public void setO_line_id(String o_line_id) {
        this.o_line_id = o_line_id;
    }

    public String getO_station_id() {
        return o_station_id;
    }

    public void setO_station_id(String o_station_id) {
        this.o_station_id = o_station_id;
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

    public String getChange_times() {
        return change_times;
    }

    public void setChange_times(String change_times) {
        this.change_times = change_times;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getMin_distance() {
        return min_distance;
    }

    public void setMin_distance(String min_distance) {
        this.min_distance = min_distance;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getChange_thres() {
        return change_thres;
    }

    public void setChange_thres(String change_thres) {
        this.change_thres = change_thres;
    }
    
    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    public String getoLineText() {
        return oLineText;
    }

    public void setoLineText(String oLineText) {
        this.oLineText = oLineText;
    }

    public String getoStationText() {
        return oStationText;
    }

    public void setoStationText(String oStationText) {
        this.oStationText = oStationText;
    }

    public String geteLineText() {
        return eLineText;
    }

    public void seteLineText(String eLineText) {
        this.eLineText = eLineText;
    }

    public String geteStationText() {
        return eStationText;
    }

    public void seteStationText(String eStationText) {
        this.eStationText = eStationText;
    }

    public String getIsValidText() {
        return isValidText;
    }

    public void setIsValidText(String isValidText) {
        this.isValidText = isValidText;
    }

    public String getVersionText() {
        return versionText;
    }

    public void setVersionText(String versionText) {
        this.versionText = versionText;
    }

    
}

