package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 逻辑卡号刻印号对照表
 * @author xiaowu
 * @version 20170622
 */
public class LogicIccList extends PrmVersion  implements Serializable{
    
    private String logic_no;
    private String icc_id;
    private String water_no;

    public String getLogic_no() {
        return logic_no;
    }

    public void setLogic_no(String logic_no) {
        this.logic_no = logic_no;
    }

    public String getIcc_id() {
        return icc_id;
    }

    public void setIcc_id(String icc_id) {
        this.icc_id = icc_id;
    }

    public String getWater_no() {
        return water_no;
    }

    public void setWater_no(String water_no) {
        this.water_no = water_no;
    }
}
