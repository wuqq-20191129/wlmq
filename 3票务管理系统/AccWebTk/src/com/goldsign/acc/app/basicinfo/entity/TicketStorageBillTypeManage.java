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
public class TicketStorageBillTypeManage {

    public String getBill_type_id() {
        return bill_type_id;
    }

    public void setBill_type_id(String bill_type_id) {
        this.bill_type_id = bill_type_id;
    }

    public String getBill_main_type_id() {
        return bill_main_type_id;
    }

    public void setBill_main_type_id(String bill_main_type_id) {
        this.bill_main_type_id = bill_main_type_id;
    }

    public String getBill_name() {
        return bill_name;
    }

    public void setBill_name(String bill_name) {
        this.bill_name = bill_name;
    }
    
    private String bill_type_id;
    private String bill_main_type_id;
    private String bill_name;
}
