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
public class SamLine {  
    private String line_es_code;//线路代码
    private String line_es_desc;//线路名称
    private String type;//类型
    private String remark;//备注
    
    private String code;//代码
    private String code_text;//代码描述
    
    private int recode_flag_es_line;//线路es类型
    
    private String distribute_place;//分发地方
    public String getLine_es_code() {
        return line_es_code;
    }

    public void setLine_es_code(String line_es_code) {
        this.line_es_code = line_es_code;
    }

    public String getLine_es_desc() {
        return line_es_desc;
    }

    public void setLine_es_desc(String line_es_desc) {
        this.line_es_desc = line_es_desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCode_text() {
        return code_text;
    }

    public void setCode_text(String code_text) {
        this.code_text = code_text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getRecode_flag_es_line() {
        return recode_flag_es_line;
    }

    public void setRecode_flag_es_line(int recode_flag_es_line) {
        this.recode_flag_es_line = recode_flag_es_line;
    }

    public String getDistribute_place() {
        return distribute_place;
    }

    public void setDistribute_place(String distribute_place) {
        this.distribute_place = distribute_place;
    }
    
    
}
