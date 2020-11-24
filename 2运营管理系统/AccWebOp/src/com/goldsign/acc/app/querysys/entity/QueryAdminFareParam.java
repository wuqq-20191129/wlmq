/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.entity;

import java.io.Serializable;

/**
 *行政收费费率参数查询
 * @author luck
 */
public class QueryAdminFareParam implements Serializable{
    private String admin_manager_name;//罚款类别
    private String admin_charge;//罚款费率
    private String record_flag;

    public String getAdmin_manager_name() {
        return admin_manager_name;
    }

    public void setAdmin_manager_name(String admin_manager_name) {
        this.admin_manager_name = admin_manager_name;
    }

    public String getAdmin_charge() {
        return admin_charge;
    }

    public void setAdmin_charge(String admin_charge) {
        this.admin_charge = admin_charge;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }
    
    
}
