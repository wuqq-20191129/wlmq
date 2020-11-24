/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.entity;

import java.io.Serializable;

/**
 *物理逻辑卡号查询
 * @author luck
 */
public class QueryPhyLogicList implements Serializable{

    private String physic_no;
    private String logic_no;
    private String water_no;

    public String getPhysic_no() {
        return physic_no;
    }

    public void setPhysic_no(String physic_no) {
        this.physic_no = physic_no;
    }

    public String getLogic_no() {
        return logic_no;
    }

    public void setLogic_no(String logic_no) {
        this.logic_no = logic_no;
    }

    

    public String getWater_no() {
        return water_no;
    }

    public void setWater_no(String water_no) {
        this.water_no = water_no;
    }
    
    
    
}
