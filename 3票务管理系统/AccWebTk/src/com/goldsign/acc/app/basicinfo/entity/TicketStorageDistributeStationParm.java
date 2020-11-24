/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.entity;

/**
 *
 * @author liudz
 */
public class TicketStorageDistributeStationParm {
    private String line_id   ;//线路代码
    private String station_id ;//车站代码
    private String storage_id ;//仓库代码
    private String line_id_name   ;//线路名称
    private String station_id_name ;//车站名称
    private String storage_id_name ;//仓库名称
    private String tvm_store_num ;//TVM的存备数
    private String tvm_num       ;//TVM数量
    private String itm_store_num ;//ITM的存备数
    private String itm_num       ;//ITM数量
    private String bom_store_num ;//BOM的存备数
    private String bom_num       ;//BOM数量
    private String reverve_num  ;//预制票预留数
    private String remark      ;//备注

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

    public String getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(String storage_id) {
        this.storage_id = storage_id;
    }
    
    

    public String getLine_id_name() {
        return line_id_name;
    }

    public void setLine_id_name(String line_id_name) {
        this.line_id_name = line_id_name;
    }

    public String getStation_id_name() {
        return station_id_name;
    }

    public void setStation_id_name(String station_id_name) {
        this.station_id_name = station_id_name;
    }

    public String getStorage_id_name() {
        return storage_id_name;
    }

    public void setStorage_id_name(String storage_id_name) {
        this.storage_id_name = storage_id_name;
    }
    

    public String getTvm_store_num() {
        return tvm_store_num;
    }

    public void setTvm_store_num(String tvm_store_num) {
        this.tvm_store_num = tvm_store_num;
    }

    public String getTvm_num() {
        return tvm_num;
    }

    public void setTvm_num(String tvm_num) {
        this.tvm_num = tvm_num;
    }

    public String getItm_store_num() {
        return itm_store_num;
    }

    public void setItm_store_num(String itm_store_num) {
        this.itm_store_num = itm_store_num;
    }

    public String getItm_num() {
        return itm_num;
    }

    public void setItm_num(String itm_num) {
        this.itm_num = itm_num;
    }

    public String getBom_store_num() {
        return bom_store_num;
    }

    public void setBom_store_num(String bom_store_num) {
        this.bom_store_num = bom_store_num;
    }

    public String getBom_num() {
        return bom_num;
    }

    public void setBom_num(String bom_num) {
        this.bom_num = bom_num;
    }
    

    public String getReverve_num() {
        return reverve_num;
    }

    public void setReverve_num(String reverve_num) {
        this.reverve_num = reverve_num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
    
    
}
