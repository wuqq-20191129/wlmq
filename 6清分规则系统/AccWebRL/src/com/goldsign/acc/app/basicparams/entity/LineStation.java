/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicparams.entity;

import java.io.Serializable;

/**
 * 系统参数
 *
 * @author luck
 */
public class LineStation implements Serializable {

    private String line_name;      // 线路名称

    private String chinese_name;  //站点中文名

    private String english_name;  //站点英文名

    private String record_flag;    //版本状态

    private String line_id;    //线路ID

    private String station_id;    //站点ID

    private String lineText;    //线路名称

    private String stationText;   //站点中文名

    private String versionText;   //版本状态
    
    private int id_code;//页面Id标识

    public int getId_code() {
        return id_code;
    }

    public void setId_code(int id_code) {
        this.id_code = id_code;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getChinese_name() {
        return chinese_name;
    }

    public void setChinese_name(String chinese_name) {
        this.chinese_name = chinese_name;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getLineText() {
        return lineText;
    }

    public void setLineText(String lineText) {
        this.lineText = lineText;
    }

    public String getStationText() {
        return stationText;
    }

    public void setStationText(String stationText) {
        this.stationText = stationText;
    }

    public String getVersionText() {
        return versionText;
    }

    public void setVersionText(String versionText) {
        this.versionText = versionText;
    }
}
