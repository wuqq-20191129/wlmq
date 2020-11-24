package com.goldsign.acc.app.prminfo.entity;

public class BlackListOctSecKey {
    private String beginLogicalId;

    private String endLogicalId;

    public String getBeginLogicalId() {
        return beginLogicalId;
    }

    public void setBeginLogicalId(String beginLogicalId) {
        this.beginLogicalId = beginLogicalId == null ? null : beginLogicalId.trim();
    }

    public String getEndLogicalId() {
        return endLogicalId;
    }

    public void setEndLogicalId(String endLogicalId) {
        this.endLogicalId = endLogicalId == null ? null : endLogicalId.trim();
    }
}