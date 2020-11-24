/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.entity;

import com.goldsign.acc.app.prminfo.entity.PrmVersion;

/**
 *
 * @author liudz
 */
public class ParamDown extends PrmVersion {

    private String parm_type_name;
    private String version_no;
    private String record_flag;
    private String parm_type_id;
    private String distribute_times;
    private String versionList;
    private String selectLccLine;
    private String lccLineName;

    private String line_id;
    private String line_name;
    private String description;
    private String parm_type_down_type;
    private String lcc_line_id;

    private String water_no;
    private String distribute_datetime;
    private String operator_id;
    private String distribute_result;
    private String version_type;
    private String gen_result;
    private String station_id;
    private String inform_result;

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getInform_result() {
        return inform_result;
    }

    public void setInform_result(String inform_result) {
        this.inform_result = inform_result;
    }
    
    

    public String getParm_type_name() {
        return parm_type_name;
    }

    public void setParm_type_name(String parm_type_name) {
        this.parm_type_name = parm_type_name;
    }

    @Override
    public String getVersion_no() {
        return version_no;
    }

    @Override
    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    @Override
    public String getRecord_flag() {
        return record_flag;
    }

    @Override
    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    @Override
    public String getParm_type_id() {
        return parm_type_id;
    }

    @Override
    public void setParm_type_id(String parm_type_id) {
        this.parm_type_id = parm_type_id;
    }

    @Override
    public String getDistribute_times() {
        return distribute_times;
    }

    @Override
    public void setDistribute_times(String distribute_times) {
        this.distribute_times = distribute_times;
    }
    
    
    
    

    public String getVersion_type() {
        return version_type;
    }

    public void setVersion_type(String version_type) {
        this.version_type = version_type;
    }

    public String getGen_result() {
        return gen_result;
    }

    public void setGen_result(String gen_result) {
        this.gen_result = gen_result;
    }
    
    

    public String getWater_no() {
        return water_no;
    }

    public void setWater_no(String water_no) {
        this.water_no = water_no;
    }

    public String getDistribute_datetime() {
        return distribute_datetime;
    }

    public void setDistribute_datetime(String distribute_datetime) {
        this.distribute_datetime = distribute_datetime;
    }

    @Override
    public String getOperator_id() {
        return operator_id;
    }

    @Override
    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public String getDistribute_result() {
        return distribute_result;
    }

    public void setDistribute_result(String distribute_result) {
        this.distribute_result = distribute_result;
    }

    public String getLcc_line_id() {
        return lcc_line_id;
    }

    public void setLcc_line_id(String lcc_line_id) {
        this.lcc_line_id = lcc_line_id;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getParm_type_down_type() {
        return parm_type_down_type;
    }

    public void setParm_type_down_type(String parm_type_down_type) {
        this.parm_type_down_type = parm_type_down_type;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLccLineName() {
        return lccLineName;
    }

    public void setLccLineName(String lccLineName) {
        this.lccLineName = lccLineName;
    }


    public String getVersionList() {
        return versionList;
    }

    public void setVersionList(String versionList) {
        this.versionList = versionList;
    }

    public String getSelectLccLine() {
        return selectLccLine;
    }

    public void setSelectLccLine(String selectLccLine) {
        this.selectLccLine = selectLccLine;
    }

}
