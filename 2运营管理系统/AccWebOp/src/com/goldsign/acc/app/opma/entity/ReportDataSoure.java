package com.goldsign.acc.app.opma.entity;

public class ReportDataSoure {
    private String dsId;

    private String dsUser;

    private String dsPass;

    private String remark;
    
    private String dsName;

    public String getDsId() {
        return dsId;
    }

    public void setDsId(String dsId) {
        this.dsId = dsId == null ? null : dsId.trim();
    }

    public String getDsUser() {
        return dsUser;
    }

    public void setDsUser(String dsUser) {
        this.dsUser = dsUser == null ? null : dsUser.trim();
    }

    public String getDsPass() {
        return dsPass;
    }

    public void setDsPass(String dsPass) {
        this.dsPass = dsPass == null ? null : dsPass.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }
    
}