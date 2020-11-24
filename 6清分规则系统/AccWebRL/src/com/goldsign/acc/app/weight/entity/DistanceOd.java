package com.goldsign.acc.app.weight.entity;

import java.io.Serializable;

/**
 *
 * @author xiaowu
 * @version 20170907
 */
public class DistanceOd implements Serializable {

    private String id;                          //id
    private String o_line_id;     //开始线路              
    private String o_station_id;       //开始站点         
    private String e_line_id;              //结束线路     
    private String e_station_id;                //结束站点
    private String pass_stations;     //经过站点          
    private String pass_time;      //乘坐时间             
    private String change_times;             //换乘次数   
    private String stations_num;//经过站点数
    private String distance;        //转出线路乘距
    private String version; //版本
    private String record_flag;     //参数标志
    private String create_time;         //创建时间
    private String create_operator;     //创建人
    private String distance_percent;
    private String distance_base;
    
    private String preId;   //临时记录上一记录ID

    public String getPreId() {
        return preId;
    }

    public void setPreId(String preId) {
        this.preId = preId;
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

    public String getPass_stations() {
        return pass_stations;
    }

    public void setPass_stations(String pass_stations) {
        this.pass_stations = pass_stations;
    }

    public String getPass_time() {
        return pass_time;
    }

    public void setPass_time(String pass_time) {
        this.pass_time = pass_time;
    }

    public String getChange_times() {
        return change_times;
    }

    public void setChange_times(String change_times) {
        this.change_times = change_times;
    }

    public String getStations_num() {
        return stations_num;
    }

    public void setStations_num(String stations_num) {
        this.stations_num = stations_num;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
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

    public String getDistance_percent() {
        return distance_percent;
    }

    public void setDistance_percent(String distance_percent) {
        this.distance_percent = distance_percent;
    }

    public String getDistance_base() {
        return distance_base;
    }

    public void setDistance_base(String distance_base) {
        this.distance_base = distance_base;
    }
    
    @Override
    public String toString() {
        return "DistanceOd{" + "id=" + id + ", o_line_id=" + o_line_id + ", o_station_id=" + o_station_id + ", e_line_id=" + e_line_id + ", e_station_id=" + e_station_id + ", pass_stations=" + pass_stations + ", pass_time=" + pass_time + ", change_times=" + change_times + ", stations_num=" + stations_num + ", distance=" + distance + ", version=" + version + ", record_flag=" + record_flag + ", create_time=" + create_time + ", create_operator=" + create_operator + ", preId=" + preId + '}';
    }
}
