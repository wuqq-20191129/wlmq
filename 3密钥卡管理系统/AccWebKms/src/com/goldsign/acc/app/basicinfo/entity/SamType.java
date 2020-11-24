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
public class SamType {
    private String sam_type_code;//类型代码
    private String sam_type_desc;//类型名称
    private int  pdu_warn_threshhold;//成品卡预警阀值
    private int ety_warn_threshhold;//空白卡预警阀值
    private String remark;
    
    private String sam_type;//sam类型

    public String getSam_type_code() {
        return sam_type_code;
    }

    public void setSam_type_code(String sam_type_code) {
        this.sam_type_code = sam_type_code;
    }

    public String getSam_type_desc() {
        return sam_type_desc;
    }

    public void setSam_type_desc(String sam_type_desc) {
        this.sam_type_desc = sam_type_desc;
    }

    public int getPdu_warn_threshhold() {
        return pdu_warn_threshhold;
    }

    public void setPdu_warn_threshhold(int pdu_warn_threshhold) {
        this.pdu_warn_threshhold = pdu_warn_threshhold;
    }

    public int getEty_warn_threshhold() {
        return ety_warn_threshhold;
    }

    public void setEty_warn_threshhold(int ety_warn_threshhold) {
        this.ety_warn_threshhold = ety_warn_threshhold;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSam_type() {
        return sam_type;
    }

    public void setSam_type(String sam_type) {
        this.sam_type = sam_type;
    }

}
