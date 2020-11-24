/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.entity;

import java.io.Serializable;
/**
 *
 * @author mh
 */
public class ParamDownStatus implements Serializable{
    
    private Integer water_no;
    private String line_id;
    private String station_id;
    private String inform_result;
    private String parm_type_id;
    private String version_type;
    private String version_no;
    private String gen_result;
    private String distribute_datetime;
    private String operator_id;
    private String distribute_result;
    private String distribute_memo;
    private String download_status;

    private String beginDatetime;
    private String endDatetime;
    
    private String lineText;
    private String parmTypeText;
    private String operatorText;
    private String lineInforText;
    private String fileInforText;
    private String downloadText;
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
    
    

    public Integer getWater_no() {
        return water_no;
    }

    public void setWater_no(Integer water_no) {
        this.water_no = water_no;
    }
    
    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }
    
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
    
    public String getParm_type_id() {
        return parm_type_id;
    }

    public void setParm_type_id(String parm_type_id) {
        this.parm_type_id = parm_type_id;
    }
    
    public String getVersion_type() {
        return version_type;
    }

    public void setVersion_type(String version_type) {
        this.version_type = version_type;
    }
    
    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }
    
    public String getGen_result() {
        return gen_result;
    }

    public void setGen_result(String gen_result) {
        this.gen_result = gen_result;
    }
    
    public String getDistribute_datetime() {
        return distribute_datetime;
    }

    public void setDistribute_datetime(String distribute_datetime) {
        this.distribute_datetime = distribute_datetime;
    }
    
    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }
    
    public String getDistribute_result() {
        return distribute_result;
    }

    public void setDistribute_result(String distribute_result) {
        this.distribute_result = distribute_result;
    }
    
    public String getDistribute_memo() {
        return distribute_memo;
    }

    public void setDistribute_memo(String distribute_memo) {
        this.distribute_memo = distribute_memo;
    }
    
    public String getBeginDatetime() {
        return beginDatetime;
    }

    public String getEndDatetime() {
        return endDatetime;
    }

    public void setBeginDatetime(String beginDatetime) {
        this.beginDatetime = beginDatetime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getLineText() {
        return lineText;
    }

    public void setLineText(String lineText) {
        this.lineText = lineText;
    }

    public String getParmTypeText() {
        return parmTypeText;
    }
    
    public void setParmTypeText(String parmTypeText) {
        this.parmTypeText = parmTypeText;
    }
    
    public String getOperatorText() {
        return operatorText;
    }
    
    public void setOperatorText(String operatorText) {
        this.operatorText = operatorText;
    }
    
    public String getLineInforText() {
        return lineInforText;
    }

    public void setLineInforText(String lineInforText) {
        this.lineInforText = lineInforText;
    }
    
    public String getFileInforText() {
        return fileInforText;
    }

    public void setFileInforText(String fileInforText) {
        this.fileInforText = fileInforText;
    }
    public String getDownload_status() {
        return download_status;
    }

    public void setDownload_status(String download_status) {
        this.download_status = download_status;
    }

    public String getDownloadText() {
        return downloadText;
    }

    public void setDownloadText(String downloadText) {
        this.downloadText = downloadText;
    }
      
}
