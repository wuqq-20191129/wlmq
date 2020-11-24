package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;

public class FareDealTotal extends PrmVersion implements Serializable{
    private Long water_no;

    private String deal_id;

    private String deal_max;

    private String deal_min;

    private String version_no;
    
    private String record_flag;
    
//    private String remark;

    public Long getWater_no() {
        return water_no;
    }

    public void setWater_no(Long water_no) {
        this.water_no = water_no;
    }

    

    public String getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(String deal_id) {
        this.deal_id = deal_id;
    }

    public String getDeal_max() {
        return deal_max;
    }

    public void setDeal_max(String deal_max) {
        this.deal_max = deal_max;
    }

    public String getDeal_min() {
        return deal_min;
    }

    public void setDeal_min(String deal_min) {
        this.deal_min = deal_min;
    }

    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
    
    


}