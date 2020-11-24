package com.goldsign.acc.app.basicinfo.entity;

import java.io.Serializable;

/**
 *
 * @author xiaowu
 */
public class TicketStorageStationContrastManage implements Serializable {

    private String line_id;
    private String station_id;
    private String ic_line_id;
    private String ic_station_id;
    private String line_name;
    private String station_name;
    private String ic_line_name;
    private String ic_station_name;
    private String sys_group_id;
    private String sys_storage_id;
    private String sys_operator_id;

    public String getSys_group_id() {
        return sys_group_id;
    }

    public void setSys_group_id(String sys_group_id) {
        this.sys_group_id = sys_group_id;
    }

    public String getSys_storage_id() {
        return sys_storage_id;
    }

    public void setSys_storage_id(String sys_storage_id) {
        this.sys_storage_id = sys_storage_id;
    }

    public String getSys_operator_id() {
        return sys_operator_id;
    }

    public void setSys_operator_id(String sys_operator_id) {
        this.sys_operator_id = sys_operator_id;
    }

    public String getIc_line_id() {
        return ic_line_id;
    }

    public void setIc_line_id(String ic_line_id) {
        this.ic_line_id = ic_line_id;
    }

    public String getIc_station_id() {
        return ic_station_id;
    }

    public void setIc_station_id(String ic_station_id) {
        this.ic_station_id = ic_station_id;
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

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getIc_line_name() {
        return ic_line_name;
    }

    public void setIc_line_name(String ic_line_name) {
        this.ic_line_name = ic_line_name;
    }

    public String getIc_station_name() {
        return ic_station_name;
    }

    public void setIc_station_name(String ic_station_name) {
        this.ic_station_name = ic_station_name;
    }

}
