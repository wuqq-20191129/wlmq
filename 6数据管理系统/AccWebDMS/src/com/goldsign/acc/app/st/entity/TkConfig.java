/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.st.entity;

import java.io.Serializable;

/**
 *
 * @author limj
 */
public class TkConfig implements Serializable{
    private String originTableName;
    private int keepDays;
    private int divideRecdCount;
    private String clearFlag;  
    private String abName;
    private String createSql;
    private String tableColumns;
    private String recdType;
    private String dealType;
    private String dateType;

    public String getOriginTableName() {
        return originTableName;
    }

    public void setOriginTableName(String originTableName) {
        this.originTableName = originTableName;
    }

    public int getKeepDays() {
        return keepDays;
    }

    public void setKeepDays(int keepDays) {
        this.keepDays = keepDays;
    }

    public int getDivideRecdCount() {
        return divideRecdCount;
    }

    public void setDivideRecdCount(int divideRecdCount) {
        this.divideRecdCount = divideRecdCount;
    }

    public String getClearFlag() {
        return clearFlag;
    }

    public void setClearFlag(String clearFlag) {
        this.clearFlag = clearFlag;
    }

    public String getAbName() {
        return abName;
    }

    public void setAbName(String abName) {
        this.abName = abName;
    }

    public String getCreateSql() {
        return createSql;
    }

    public void setCreateSql(String createSql) {
        this.createSql = createSql;
    }

    public String getTableColumns() {
        return tableColumns;
    }

    public void setTableColumns(String tableColumns) {
        this.tableColumns = tableColumns;
    }

    public String getRecdType() {
        return recdType;
    }

    public void setRecdType(String recdType) {
        this.recdType = recdType;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

  
    
}
