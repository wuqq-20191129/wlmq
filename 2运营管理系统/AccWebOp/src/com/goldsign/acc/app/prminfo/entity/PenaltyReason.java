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
public class PenaltyReason implements Serializable{
    
    /**
     * @return the penalty_id
     */
    public String getPenalty_id() {
        return penalty_id;
    }

    /**
     * @param penalty_id the penalty_id to set
     */
    public void setPenalty_id(String penalty_id) {
        this.penalty_id = penalty_id;
    }

    /**
     * @return the penalty_name
     */
    public String getPenalty_name() {
        return penalty_name;
    }

    /**
     * @param penalty_name the penalty_name to set
     */
    public void setPenalty_name(String penalty_name) {
        this.penalty_name = penalty_name;
    }

    /**
     * @return the delete_flag
     */
    public String getDelete_flag() {
        return delete_flag;
    }

    /**
     * @param delete_flag the delete_flag to set
     */
    public void setDelete_flag(String delete_flag) {
        this.delete_flag = delete_flag;
    }

    private String penalty_id;
    private String penalty_name;
    private String delete_flag;
}
