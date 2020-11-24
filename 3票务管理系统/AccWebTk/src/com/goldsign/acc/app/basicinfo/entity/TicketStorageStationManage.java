/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.entity;

/**
 *
 * @author mh
 */
public class TicketStorageStationManage {

    /**
     * @return the pub_lineText
     */
    public String getPub_lineText() {
        return pub_lineText;
    }

    /**
     * @param pub_lineText the pub_lineText to set
     */
    public void setPub_lineText(String pub_lineText) {
        this.pub_lineText = pub_lineText;
    }

    /**
     * @return the line_id
     */
    public String getLine_id() {
        return line_id;
    }

    /**
     * @param line_id the line_id to set
     */
    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    /**
     * @return the line_name
     */
    public String getLine_name() {
        return line_name;
    }

    /**
     * @param line_name the line_name to set
     */
    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    /**
     * @return the station_id
     */
    public String getStation_id() {
        return station_id;
    }

    /**
     * @param station_id the station_id to set
     */
    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    /**
     * @return the chinese_name
     */
    public String getChinese_name() {
        return chinese_name;
    }

    /**
     * @param chinese_name the chinese_name to set
     */
    public void setChinese_name(String chinese_name) {
        this.chinese_name = chinese_name;
    }


    private String line_id;
    private String line_name;
    private String station_id;
    private String chinese_name;
    private String pub_lineText;
}
