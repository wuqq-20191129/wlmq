/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.st.entity;

import java.io.Serializable;

/**
 * 清算系统 - 配置信息
 *
 * @author luck
 */
public class StConfig implements Serializable{

    private String originTableName;

    private int keepDays;

    private int divideRecdCount;

    private int clearFlag;
    private String clearFlagName;

    private String createSql;

    private int isSquadday;  //0:有运营日、1：无运营日
    private String isSquaddayName;

    private String tableColumns;

    private String abName;

    private String dbType;

    private String dateType;

    private String primarytable;

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

    public int getClearFlag() {
        return clearFlag;
    }

    public void setClearFlag(int clearFlag) {
        this.clearFlag = clearFlag;
    }

    public String getCreateSql() {
        return createSql;
    }

    public void setCreateSql(String createSql) {
        this.createSql = createSql;
    }

    public int getIsSquadday() {
        return isSquadday;
    }

    public void setIsSquadday(int isSquadday) {
        this.isSquadday = isSquadday;
    }

    public String getTableColumns() {
        return tableColumns;
    }

    public void setTableColumns(String tableColumns) {
        this.tableColumns = tableColumns;
    }

    public String getAbName() {
        return abName;
    }

    public void setAbName(String abName) {
        this.abName = abName;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getPrimarytable() {
        return primarytable;
    }

    public void setPrimarytable(String primarytable) {
        this.primarytable = primarytable;
    }

    public String getIsSquaddayName() {
        return isSquaddayName;
    }

    public void setIsSquaddayName(String isSquaddayName) {
        this.isSquaddayName = isSquaddayName;
    }

    public String getClearFlagName() {
        return clearFlagName;
    }

    public void setClearFlagName(String clearFlagName) {
        this.clearFlagName = clearFlagName;
    }
    
    

}
