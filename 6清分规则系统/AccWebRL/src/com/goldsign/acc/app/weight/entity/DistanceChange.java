package com.goldsign.acc.app.weight.entity;

import java.io.Serializable;

/**
 *
 * @author xiaowu
 * @version 20170907
 */
public class DistanceChange implements Serializable {

    private String od_id;
    private String pchange_station_id;
    private String pass_line_out;
    private String pass_line_in;
    private String pass_distance;
    private String nchange_station_id;

    public String getOd_id() {
        return od_id;
    }

    public void setOd_id(String od_id) {
        this.od_id = od_id;
    }

    public String getPchange_station_id() {
        return pchange_station_id;
    }

    public void setPchange_station_id(String pchange_station_id) {
        this.pchange_station_id = pchange_station_id;
    }

    public String getPass_line_out() {
        return pass_line_out;
    }

    public void setPass_line_out(String pass_line_out) {
        this.pass_line_out = pass_line_out;
    }

    public String getPass_line_in() {
        return pass_line_in;
    }

    public void setPass_line_in(String pass_line_in) {
        this.pass_line_in = pass_line_in;
    }

    public String getPass_distance() {
        return pass_distance;
    }

    public void setPass_distance(String pass_distance) {
        this.pass_distance = pass_distance;
    }

    public String getNchange_station_id() {
        return nchange_station_id;
    }

    public void setNchange_station_id(String nchange_station_id) {
        this.nchange_station_id = nchange_station_id;
    }

}
