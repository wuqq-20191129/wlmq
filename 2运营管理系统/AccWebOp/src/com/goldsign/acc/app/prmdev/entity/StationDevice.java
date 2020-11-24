/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prmdev.entity;

import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import java.io.Serializable;

/**
 *
 * @author hejj
 */
public class StationDevice extends PrmVersion implements Serializable{

    /**
     * @return the line_id_name
     */
    public String getLine_id_name() {
        return line_id_name;
    }

    /**
     * @param line_id_name the line_id_name to set
     */
    public void setLine_id_name(String line_id_name) {
        this.line_id_name = line_id_name;
    }

    /**
     * @return the station_id_name
     */
    public String getStation_id_name() {
        return station_id_name;
    }

    /**
     * @param station_id_name the station_id_name to set
     */
    public void setStation_id_name(String station_id_name) {
        this.station_id_name = station_id_name;
    }

    /**
     * @return the dev_type_id_name
     */
    public String getDev_type_id_name() {
        return dev_type_id_name;
    }

    /**
     * @param dev_type_id_name the dev_type_id_name to set
     */
    public void setDev_type_id_name(String dev_type_id_name) {
        this.dev_type_id_name = dev_type_id_name;
    }

    /**
     * @return the store_id_name
     */
    public String getStore_id_name() {
        return store_id_name;
    }

    /**
     * @param store_id_name the store_id_name to set
     */
    public void setStore_id_name(String store_id_name) {
        this.store_id_name = store_id_name;
    }

    /**
     * @return the zone_id
     */
    public String getZone_id() {
        return zone_id;
    }

    /**
     * @param zone_id the zone_id to set
     */
    public void setZone_id(String zone_id) {
        this.zone_id = zone_id;
    }



    /**
     * @return the csc_num
     */
    public String getCsc_num() {
        return csc_num;
    }

    /**
     * @param csc_num the csc_num to set
     */
    public void setCsc_num(String csc_num) {
        this.csc_num = csc_num;
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
     * @return the dev_type_id
     */
    public String getDev_type_id() {
        return dev_type_id;
    }

    /**
     * @param dev_type_id the dev_type_id to set
     */
    public void setDev_type_id(String dev_type_id) {
        this.dev_type_id = dev_type_id;
    }

    /**
     * @return the device_id
     */
    public String getDevice_id() {
        return device_id;
    }

    /**
     * @param device_id the device_id to set
     */
    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    

    /**
     * @return the array_id
     */
    public String getArray_id() {
        return array_id;
    }

    /**
     * @param array_id the array_id to set
     */
    public void setArray_id(String array_id) {
        this.array_id = array_id;
    }

    /**
     * @return the concourse_id
     */
    public String getConcourse_id() {
        return concourse_id;
    }

    /**
     * @param concourse_id the concourse_id to set
     */
    public void setConcourse_id(String concourse_id) {
        this.concourse_id = concourse_id;
    }

    /**
     * @return the ip_address
     */
    public String getIp_address() {
        return ip_address;
    }

    /**
     * @param ip_address the ip_address to set
     */
    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    /**
     * @return the store_id
     */
    public String getStore_id() {
        return store_id;
    }

    /**
     * @param store_id the store_id to set
     */
    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    /**
     * @return the dev_serial
     */
    public String getDev_serial() {
        return dev_serial;
    }

    /**
     * @param dev_serial the dev_serial to set
     */
    public void setDev_serial(String dev_serial) {
        this.dev_serial = dev_serial;
    }

    /**
     * @return the record_flag
     */
    public String getRecord_flag() {
        return record_flag;
    }

    /**
     * @param record_flag the record_flag to set
     */
    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    /**
     * @return the version_no
     */
    public String getVersion_no() {
        return version_no;
    }

    /**
     * @param version_no the version_no to set
     */
    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    /**
     * @return the config_date
     */
    public String getConfig_date() {
        return config_date;
    }

    /**
     * @param config_date the config_date to set
     */
    public void setConfig_date(String config_date) {
        this.config_date = config_date;
    }

    /**
     * @return the dev_name
     */
    public String getDev_name() {
        return dev_name;
    }

    /**
     * @param dev_name the dev_name to set
     */
    public void setDev_name(String dev_name) {
        this.dev_name = dev_name;
    }
    private String line_id;
    private String station_id;
    private String dev_type_id;
    private String device_id;
    private String csc_num;
    private String array_id;
    private String concourse_id;
    private String ip_address;
    private String store_id;
    private String dev_serial;
    private String record_flag;
    private String version_no;
    private String config_date;
    private String dev_name;
    private String zone_id;
    
    private String line_id_name;
    private String station_id_name;
    private String dev_type_id_name;
    private String store_id_name;
    
    private String indexTemp;

    public String getIndexTemp() {
        return indexTemp;
    }

    public void setIndexTemp(String indexTemp) {
        this.indexTemp = indexTemp;
    }

    
}
