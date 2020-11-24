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
public class TicketStorageAdjustReasonManage {
   

    public String getAdjust_id() {
        return adjust_id;
    }

    public void setAdjust_id(String adjust_id) {
        this.adjust_id = adjust_id;
    }

    public String getAdjust_reason() {
        return adjust_reason;
    }

    public void setAdjust_reason(String adjust_reason) {
        this.adjust_reason = adjust_reason;
    }
    
    private String adjust_id;
    private String adjust_reason;
}
