package com.goldsign.acc.app.weight.entity;

import java.io.Serializable;

/**
 *
 * @author xiaowu
 * @version 20170907
 */
public class ParamsStation implements Serializable {

    private String p_station_id;
    private String n_station_id;
    private String line_id;
    private String record_flag;
    private String version;
    private String mileage;
    private String create_time;
    private String create_operator;
    private String n_t_station_id;

    public String getP_station_id() {
        return p_station_id;
    }

    public void setP_station_id(String p_station_id) {
        this.p_station_id = p_station_id;
    }

    public String getN_station_id() {
        return n_station_id;
    }

    public void setN_station_id(String n_station_id) {
        this.n_station_id = n_station_id;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_operator() {
        return create_operator;
    }

    public void setCreate_operator(String create_operator) {
        this.create_operator = create_operator;
    }

    public String getN_t_station_id() {
        return n_t_station_id;
    }

    public void setN_t_station_id(String n_t_station_id) {
        this.n_t_station_id = n_t_station_id;
    }

}
