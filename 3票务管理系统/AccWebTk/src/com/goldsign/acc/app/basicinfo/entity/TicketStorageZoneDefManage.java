/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.entity;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class TicketStorageZoneDefManage implements Serializable{
    
    private String area_id;
    private String area_name;
    private String storage_id;
    private String storage_name;
    private String real_num;
    private String upper_num;

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(String storage_id) {
        this.storage_id = storage_id;
    }

    public String getStorage_name() {
        return storage_name;
    }

    public void setStorage_name(String storage_name) {
        this.storage_name = storage_name;
    }

    public String getReal_num() {
        return real_num;
    }

    public void setReal_num(String real_num) {
        this.real_num = real_num;
    }

    public String getUpper_num() {
        return upper_num;
    }

    public void setUpper_num(String upper_num) {
        this.upper_num = upper_num;
    }
    
}
