package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 物理逻辑卡号对照表历史表
 * @author xiaowu
 * @version 20170622
 */
public class HisPhyLogicList extends PrmVersion  implements Serializable{
    
    private String physic_no;
    private String logic_no;
    private String water_no;
    private String insert_time;
    
    private List waterNoList;
    private List physicNoList;
    private List logicNoList;

    public List getPhysicNoList() {
        return physicNoList;
    }

    public void setPhysicNoList(List physicNoList) {
        this.physicNoList = physicNoList;
    }

    public List getLogicNoList() {
        return logicNoList;
    }

    public void setLogicNoList(List logicNoList) {
        this.logicNoList = logicNoList;
    }

    public List getWaterNoList() {
        return waterNoList;
    }

    public void setWaterNoList(List waterNoList) {
        this.waterNoList = waterNoList;
    }

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

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }
    
    
}
