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
public class TicketStorageInOutReasonManage {

    public String getReason_id() {
        return reason_id;
    }

    public void setReason_id(String reason_id) {
        this.reason_id = reason_id;
    }

    public String getReason_describe() {
        return reason_describe;
    }

    public void setReason_describe(String reason_describe) {
        this.reason_describe = reason_describe;
    }

    public String getIn_out_flag() {
        return in_out_flag;
    }

    public void setIn_out_flag(String in_out_flag) {
        this.in_out_flag = in_out_flag;
    }

    public String getEs_worktype_id() {
        return es_worktype_id;
    }

    public void setEs_worktype_id(String es_worktype) {
        this.es_worktype_id = es_worktype;
    }

    public String getIn_out_desc() {
        return in_out_desc;
    }

    public void setIn_out_desc(String in_out_desc) {
        this.in_out_desc = in_out_desc;
    }

    public String getEs_worktype_name() {
        return es_worktype_name;
    }

    public void setEs_worktype_name(String es_worktype_name) {
        this.es_worktype_name = es_worktype_name;
    }

    public String getIn_out_sub_type() {
        return in_out_sub_type;
    }

    public void setIn_out_sub_type(String in_out_sub_type) {
        this.in_out_sub_type = in_out_sub_type;
    }

    public String getIn_out_sub_type_desc() {
        return in_out_sub_type_desc;
    }

    public void setIn_out_sub_type_desc(String in_out_sub_type_desc) {
        this.in_out_sub_type_desc = in_out_sub_type_desc;
    }
    
    
    
    private String reason_id;
    private String reason_describe;
    private String in_out_flag;
    private String in_out_sub_type;//出入库子类型
    private String es_worktype_id;
    private String in_out_desc;
     private String in_out_sub_type_desc; //出入库子类型描述
    private String es_worktype_name;//es操作类型名称
}
