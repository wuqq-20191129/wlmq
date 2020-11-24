/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.commu.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 数据交换系统 - 分表记录
 * @author luck
 */
public class CmIdxHis implements Serializable{

    private String hisTable;

    private String originTableName;

    private String beginDate;

    private String endDate;

    private BigDecimal recdCount;

    private String recdType;

    public String getHisTable() {
        return hisTable;
    }

    public void setHisTable(String hisTable) {
        this.hisTable = hisTable;
    }

    public String getOriginTableName() {
        return originTableName;
    }

    public void setOriginTableName(String originTableName) {
        this.originTableName = originTableName;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getRecdCount() {
        return recdCount;
    }

    public void setRecdCount(BigDecimal recdCount) {
        this.recdCount = recdCount;
    }

    public String getRecdType() {
        return recdType;
    }

    public void setRecdType(String recdType) {
        this.recdType = recdType;
    }
    
    

}
