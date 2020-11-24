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
public class TkClear implements Serializable{
    private String originTableName;
    private String destTableName;
    private String beginBillNo;
    private String endBillNo;
    private String beginClearDateTime;
    private String endClearDateTime;
    private String clearRecdCount;
    private String errDiscribe;
    private String spentTime;
    private String sqlLabel;

    public String getOriginTableName() {
        return originTableName;
    }

    public void setOriginTableName(String originTableName) {
        this.originTableName = originTableName;
    }

    public String getDestTableName() {
        return destTableName;
    }

    public void setDestTableName(String destTableName) {
        this.destTableName = destTableName;
    }

    public String getBeginBillNo() {
        return beginBillNo;
    }

    public void setBeginBillNo(String beginBillNo) {
        this.beginBillNo = beginBillNo;
    }

    public String getEndBillNo() {
        return endBillNo;
    }

    public void setEndBillNo(String endBillNo) {
        this.endBillNo = endBillNo;
    }

    public String getBeginClearDateTime() {
        return beginClearDateTime;
    }

    public void setBeginClearDateTime(String beginClearDateTime) {
        this.beginClearDateTime = beginClearDateTime;
    }

    public String getEndClearDateTime() {
        return endClearDateTime;
    }

    public void setEndClearDateTime(String endClearDateTime) {
        this.endClearDateTime = endClearDateTime;
    }

    public String getClearRecdCount() {
        return clearRecdCount;
    }

    public void setClearRecdCount(String clearRecdCount) {
        this.clearRecdCount = clearRecdCount;
    }

    public String getErrDiscribe() {
        return errDiscribe;
    }

    public void setErrDiscribe(String errDiscribe) {
        this.errDiscribe = errDiscribe;
    }

    public String getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(String spentTime) {
        this.spentTime = spentTime;
    }

    public String getSqlLabel() {
        return sqlLabel;
    }

    public void setSqlLabel(String sqlLabel) {
        this.sqlLabel = sqlLabel;
    }
 
    
}
