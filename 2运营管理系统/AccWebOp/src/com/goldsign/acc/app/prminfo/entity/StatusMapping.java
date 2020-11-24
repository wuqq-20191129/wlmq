/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;

/**
 *
 * @author:mh
 * @create date: 2017-6-19
 */
public class StatusMapping implements Serializable{
     /**
     * @return the status_id
     */
    public String getStatus_id() {
        return status_id;
    }

    /**
     * @param status_id the status_id to set
     */
    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    /**
     * @return the status_value
     */
    public String getStatus_value() {
        return status_value;
    }

    /**
     * @param status_value the status_value to set
     */
    public void setStatus_value(String status_value) {
        this.status_value = status_value;
    }

    /**
     * @return the acc_status_value
     */
    public String getAcc_status_value() {
        return acc_status_value;
    }

    /**
     * @param acc_status_value the acc_status_value to set
     */
    public void setAcc_status_value(String acc_status_value) {
        this.acc_status_value = acc_status_value;
    }

    /**
     * @return the status_name
     */
    public String getStatus_name() {
        return status_name;
    }

    /**
     * @param status_name the status_name to set
     */
    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    /**
     * @return the acc_status_name
     */
    public String getAcc_status_name() {
        return acc_status_name;
    }

    /**
     * @param acc_status_name the acc_status_name to set
     */
    public void setAcc_status_name(String acc_status_name) {
        this.acc_status_name = acc_status_name;
    }

    private String status_id;
    private String status_value;
    private String acc_status_value;
    private String status_name;
    private String acc_status_name;
}
