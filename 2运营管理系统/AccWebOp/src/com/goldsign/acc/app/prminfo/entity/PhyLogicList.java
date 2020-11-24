package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 物理逻辑卡号对照表
 * @author xiaowu
 * @version 20170620
 */
public class PhyLogicList extends PrmVersion  implements Serializable{
    
    private String physic_no;
    private String logic_no;
    private String water_no;
    
    private String start_time;
    private String end_time;
    private String file_name;
    private String sys_operator_id;
    private String begin_logical;
    private String end_logical;
    
    private String sys_operator_name;
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
    
    public String getSys_operator_name() {
        return sys_operator_name;
    }

    public void setSys_operator_name(String sys_operator_name) {
        this.sys_operator_name = sys_operator_name;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getSys_operator_id() {
        return sys_operator_id;
    }

    public void setSys_operator_id(String sys_operator_id) {
        this.sys_operator_id = sys_operator_id;
    }

    public String getBegin_logical() {
        return begin_logical;
    }

    public void setBegin_logical(String begin_logical) {
        this.begin_logical = begin_logical;
    }

    public String getEnd_logical() {
        return end_logical;
    }

    public void setEnd_logical(String end_logical) {
        this.end_logical = end_logical;
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
}
