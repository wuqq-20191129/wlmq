package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;

/**
 * 线路车站--线路管理
 * @author xiaowu
 * @version 20170608
 */
public class LineCode extends PrmVersion  implements Serializable{
    
    private String line_id;         //线路ID
    private String version_no;      //版本号
    private String line_name;       //线路名称
    private String record_flag;     //版本标志
    private String lc_ip;          //线路IP地址
    private String lc_line_id;     //LC线路ID
    private String sequence;        //排列序号
    private String uygur_name;       //线路维文名称
    private String direction_flag;   //客流方向
    private String direction_flag_name;   
    private String line_flag;         //线路标识
    private String line_flag_text;      //线路标识名称

    public String getLine_flag() {
        return line_flag;
    }

    public void setLine_flag(String line_flag) {
        this.line_flag = line_flag;
    }

    public String getLine_flag_text() {
        return line_flag_text;
    }

    public void setLine_flag_text(String line_flag_text) {
        this.line_flag_text = line_flag_text;
    }
    
    

    public String getDirection_flag_name() {
        return direction_flag_name;
    }

    public void setDirection_flag_name(String direction_flag_name) {
        this.direction_flag_name = direction_flag_name;
    }

    public String getDirection_flag() {
        return direction_flag;
    }

    public void setDirection_flag(String direction_flag) {
        this.direction_flag = direction_flag;
    }
    
    public String getUygur_name() {
        return uygur_name;
    }

    public void setUygur_name(String uygur_name) {
        this.uygur_name = uygur_name;
    }
    
    //LCC 线路名   Lcc线路ID 映射
    private String lc_line_name;

    public String getLc_line_name() {
        return lc_line_name;
    }

    public void setLc_line_name(String lc_line_name) {
        this.lc_line_name = lc_line_name;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getLc_ip() {
        return lc_ip;
    }

    public void setLc_ip(String lc_ip) {
        this.lc_ip = lc_ip;
    }

    public String getLc_line_id() {
        return lc_line_id;
    }

    public void setLc_line_id(String lc_line_id) {
        this.lc_line_id = lc_line_id;
    }
}
