/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.entity;

import java.io.Serializable;

/**
 *
 * @author zhouy
 * 新票入库
 * 20170727
 */
public class TicketStorageInNew extends TicketStorageInBill implements Serializable{
    
    private String his_table;//历史表名称
    private String begin_time;//查询条件 开始时间
    private String end_time;//查询条件 结束时间
    private String inType;//入库类型（订单号的前两个字母）
    
    private String whereSql;//sql的where语句
    private String sys_group_id;//权限组id
    private String sys_storage_id;//仓库id
    private String begin_recd;//入库分表索引表开始记录
    private String end_recd;//入库分表索引表结束记录
    private String p_memo;//用于接收增加入库单时存储过程返回的插入信息
    private String p_result;//用于接收增加入库单时存储过程返回的插入信息
    

    public String getP_result() {
        return p_result;
    }

    public void setP_result(String p_result) {
        this.p_result = p_result;
    }

    public String getP_memo() {
        return p_memo;
    }

    public void setP_memo(String p_memo) {
        this.p_memo = p_memo;
    }

    public String getBegin_recd() {
        return begin_recd;
    }

    public void setBegin_recd(String begin_recd) {
        this.begin_recd = begin_recd;
    }

    public String getEnd_recd() {
        return end_recd;
    }

    public void setEnd_recd(String end_recd) {
        this.end_recd = end_recd;
    }
    

    public String getSys_group_id() {
        return sys_group_id;
    }

    public void setSys_group_id(String sys_group_id) {
        this.sys_group_id = sys_group_id;
    }

    public String getSys_storage_id() {
        return sys_storage_id;
    }

    public void setSys_storage_id(String sys_storage_id) {
        this.sys_storage_id = sys_storage_id;
    }

    public String getInType() {
        return inType;
    }

    public void setInType(String inType) {
        this.inType = inType;
    }

    public String getWhereSql() {
        return whereSql;
    }

    public void setWhereSql(String whereSql) {
        this.whereSql = whereSql;
    }

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

    public String getHis_table() {
        return his_table;
    }

    public void setHis_table(String his_table) {
        this.his_table = his_table;
    }
    
}
