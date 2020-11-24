/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.st.entity;

import java.io.Serializable;

/**
 * 清算系统 - 清理日志
 *
 * @author luck
 */
public class StClear implements Serializable {

    private String originTableName;

    private String destTableName;

    private String balanceWaterNo;

    private String beginClearDatetime;

    private String endClearDatetime;

    private String spentTime;

    private int clearRecdCount;

    private String errDiscribe;

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

    public String getBalanceWaterNo() {
        return balanceWaterNo;
    }

    public void setBalanceWaterNo(String balanceWaterNo) {
        this.balanceWaterNo = balanceWaterNo;
    }

    public String getBeginClearDatetime() {
        return beginClearDatetime;
    }

    public void setBeginClearDatetime(String beginClearDatetime) {
        this.beginClearDatetime = beginClearDatetime;
    }

    public String getEndClearDatetime() {
        return endClearDatetime;
    }

    public void setEndClearDatetime(String endClearDatetime) {
        this.endClearDatetime = endClearDatetime;
    }

    public String getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(String spentTime) {
        this.spentTime = spentTime;
    }

    public int getClearRecdCount() {
        return clearRecdCount;
    }

    public void setClearRecdCount(int clearRecdCount) {
        this.clearRecdCount = clearRecdCount;
    }

    public String getErrDiscribe() {
        return errDiscribe;
    }

    public void setErrDiscribe(String errDiscribe) {
        this.errDiscribe = errDiscribe;
    }

    public String getSqlLabel() {
        return sqlLabel;
    }

    public void setSqlLabel(String sqlLabel) {
        this.sqlLabel = sqlLabel;
    }

    
    
   
}
