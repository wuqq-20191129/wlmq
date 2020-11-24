/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.entity;

import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import java.io.Serializable;

/**
 *
 * @author mqf
 */
public class FareName extends PrmVersion implements Serializable{

    private String fare_id;
    private String fare_name;

    public String getFare_id() {
        return fare_id;
    }

    public void setFare_id(String fare_id) {
        this.fare_id = fare_id;
    }

    public String getFare_name() {
        return fare_name;
    }

    public void setFare_name(String fare_name) {
        this.fare_name = fare_name;
    }
    
    

    
}
