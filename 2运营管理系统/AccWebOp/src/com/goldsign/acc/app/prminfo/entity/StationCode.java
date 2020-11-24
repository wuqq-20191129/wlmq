package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;

/**
 * 线路车站--车站表
 * @author xiaowu
 * @version 20170612
 */
public class StationCode extends PrmVersion  implements Serializable{
    
    private String line_id;                     //线路ID
    private String station_id;                  //车站ID
    private String chinese_name;                //车站中文名
    private String english_name;                //车站英文名
    private String sc_ip;                       //车站IP
    private String contc_id;                    //运营商ID
    private String lc_ip;                      //LC线路IP
    private String record_flag;                 //版本标志
    private String version_no;                  //版本号
    private String sequence;                    //排序标志
    private String belong_line_id;              //所属线路ID
    private String uygur_name;                  //车站维文名称
    
    private String line_name;                   //线路名
    private String belong_line_name;            //所属线路名
    private String contc_name;                  //运营商名

    public String getUygur_name() {
        return uygur_name;
    }

    public void setUygur_name(String uygur_name) {
        this.uygur_name = uygur_name;
    }

    public String getContc_name() {
        return contc_name;
    }

    public void setContc_name(String contc_name) {
        this.contc_name = contc_name;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getBelong_line_name() {
        return belong_line_name;
    }

    public void setBelong_line_name(String belong_line_name) {
        this.belong_line_name = belong_line_name;
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

    public String getSc_ip() {
        return sc_ip;
    }

    public void setSc_ip(String sc_ip) {
        this.sc_ip = sc_ip;
    }

    public String getContc_id() {
        return contc_id;
    }

    public void setContc_id(String contc_id) {
        this.contc_id = contc_id;
    }

    public String getLc_ip() {
        return lc_ip;
    }

    public void setLc_ip(String lc_ip) {
        this.lc_ip = lc_ip;
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

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getBelong_line_id() {
        return belong_line_id;
    }

    public void setBelong_line_id(String belong_line_id) {
        this.belong_line_id = belong_line_id;
    }
    
}
