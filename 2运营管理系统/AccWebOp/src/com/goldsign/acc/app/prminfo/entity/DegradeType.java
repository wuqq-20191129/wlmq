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
 * @create date: 2017-6-16
 */
public class DegradeType implements Serializable{

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
     * @return the hdl_flag
     */
    public String getHdl_flag() {
        return hdl_flag;
    }

    /**
     * @param hdl_flag the hdl_flag to set
     */
    public void setHdl_flag(String hdl_flag) {
        this.hdl_flag = hdl_flag;
    }

    private String status_id;
    private String status_name;
    private String hdl_flag;

}
