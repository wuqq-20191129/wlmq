/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.entity;

/**
 *
 * @author taidb
 */
public class TicketStorageLineManage {

    /**
     * @return the line_id
     */
    public String getLine_id() {
        return line_id;
    }

    /**
     * @param line_id the line_id to set
     */
    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    /**
     * @return the line_name
     */
    public String getLine_name() {
        return line_name;
    }

    /**
     * @param line_name the line_name to set
     */
    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    private String line_id;
    private String line_name;
}
