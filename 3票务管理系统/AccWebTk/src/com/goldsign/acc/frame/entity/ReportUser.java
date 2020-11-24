/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.entity;

/**
 *
 * @author hejj
 */
public class ReportUser {

    /**
     * @return the ds_id
     */
    public String getDs_id() {
        return ds_id;
    }

    /**
     * @param ds_id the ds_id to set
     */
    public void setDs_id(String ds_id) {
        this.ds_id = ds_id;
    }

    /**
     * @return the ds_user
     */
    public String getDs_user() {
        return ds_user;
    }

    /**
     * @param ds_user the ds_user to set
     */
    public void setDs_user(String ds_user) {
        this.ds_user = ds_user;
    }

    /**
     * @return the ds_pass
     */
    public String getDs_pass() {
        return ds_pass;
    }

    /**
     * @param ds_pass the ds_pass to set
     */
    public void setDs_pass(String ds_pass) {
        this.ds_pass = ds_pass;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    private String ds_id;
    private String ds_user;
    private String ds_pass;
    private String remark;

}
