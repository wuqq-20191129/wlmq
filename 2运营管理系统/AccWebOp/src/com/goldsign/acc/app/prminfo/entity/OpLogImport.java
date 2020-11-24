package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;

/**
 * 导入日志表
 * @author xiaowu
 * @version 20170621
 */
public class OpLogImport extends PrmVersion  implements Serializable{

   private String water_no;
   private String operator_id;
   private String op_time;
   private String op_type;
   private String table_name;
   private String record_count;
   private String version_no;
   private String remark;
   private String file_name;
   private String begin_logical_id;
   private String end_logical_id;
   
   private String operator_name;
   private String begin_time;
   private String end_time;

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

    public String getWater_no() {
        return water_no;
    }

    public void setWater_no(String water_no) {
        this.water_no = water_no;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public String getOp_time() {
        return op_time;
    }

    public void setOp_time(String op_time) {
        this.op_time = op_time;
    }

    public String getOp_type() {
        return op_type;
    }

    public void setOp_type(String op_type) {
        this.op_type = op_type;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getRecord_count() {
        return record_count;
    }

    public void setRecord_count(String record_count) {
        this.record_count = record_count;
    }

    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getBegin_logical_id() {
        return begin_logical_id;
    }

    public void setBegin_logical_id(String begin_logical_id) {
        this.begin_logical_id = begin_logical_id;
    }

    public String getEnd_logical_id() {
        return end_logical_id;
    }

    public void setEnd_logical_id(String end_logical_id) {
        this.end_logical_id = end_logical_id;
    }
   
   
}
