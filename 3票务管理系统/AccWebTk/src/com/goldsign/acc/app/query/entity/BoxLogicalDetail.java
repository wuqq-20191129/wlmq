/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.query.entity;

/**
 *
 * @author zhouy
 * 2018-03-13
 * 盒内逻辑卡明细
 */
public class BoxLogicalDetail {
    private String box_id;
    private String start_logical_id;
    private String end_logical_id;

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
    }

    public String getStart_logical_id() {
        return start_logical_id;
    }

    public void setStart_logical_id(String start_logical_id) {
        this.start_logical_id = start_logical_id;
    }

    public String getEnd_logical_id() {
        return end_logical_id;
    }

    public void setEnd_logical_id(String end_logical_id) {
        this.end_logical_id = end_logical_id;
    }
    
    
}
