/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samquery.entity;

/**
 *
 * @author zhouyang
 * 卡分发跟踪
 * 20170831
 */
public class SamDisTrace {
    private String sam_type;//sam卡类型
    private String logic_no;//逻辑卡号
    private String distribute_place;//分发目的地
    private String beginLogicNo;//查询条件的开始逻辑卡号
    private String endLogicNo;//查询条件的结束逻辑卡号
    
    private String sam_type_text;//sam卡类型名称
    private String distribute_place_text;//分发目的地名称

    public String getSam_type_text() {
        return sam_type_text;
    }

    public void setSam_type_text(String sam_type_text) {
        this.sam_type_text = sam_type_text;
    }

    public String getDistribute_place_text() {
        return distribute_place_text;
    }

    public void setDistribute_place_text(String distribute_place_text) {
        this.distribute_place_text = distribute_place_text;
    }

    public String getBeginLogicNo() {
        return beginLogicNo;
    }

    public void setBeginLogicNo(String beginLogicNo) {
        this.beginLogicNo = beginLogicNo;
    }

    public String getEndLogicNo() {
        return endLogicNo;
    }

    public void setEndLogicNo(String endLogicNo) {
        this.endLogicNo = endLogicNo;
    }
    

    public String getSam_type() {
        return sam_type;
    }

    public void setSam_type(String sam_type) {
        this.sam_type = sam_type;
    }

    public String getLogic_no() {
        return logic_no;
    }

    public void setLogic_no(String logic_no) {
        this.logic_no = logic_no;
    }

    public String getDistribute_place() {
        return distribute_place;
    }

    public void setDistribute_place(String distribute_place) {
        this.distribute_place = distribute_place;
    }
    
}
