package com.goldsign.acc.app.weight.entity;

import java.io.Serializable;

/**
 * 线路车站--换乘车站
 * @author xiaowu
 * @version 20170907
 */
public class TransferStation implements Serializable{
    
    private String line_id;						//线路ID								
    private String station_id;						//车站ID		
    private String transfer_line_id;					//换乘线路ID
    private String transfer_station_id;					//换乘车站ID					
    private String record_flag;                                         //版本标志				
    private String version_no;						//版本号
    
    private String line_name;
    private String station_name;
    private String transfer_line_name;
    private String transfer_station_name;

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

    public String getTransfer_line_name() {
        return transfer_line_name;
    }

    public void setTransfer_line_name(String transfer_line_name) {
        this.transfer_line_name = transfer_line_name;
    }

    public String getTransfer_station_name() {
        return transfer_station_name;
    }

    public void setTransfer_station_name(String transfer_station_name) {
        this.transfer_station_name = transfer_station_name;
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

    public String getTransfer_line_id() {
        return transfer_line_id;
    }

    public void setTransfer_line_id(String transfer_line_id) {
        this.transfer_line_id = transfer_line_id;
    }

    public String getTransfer_station_id() {
        return transfer_station_id;
    }

    public void setTransfer_station_id(String transfer_station_id) {
        this.transfer_station_id = transfer_station_id;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }
    
    
    
}
