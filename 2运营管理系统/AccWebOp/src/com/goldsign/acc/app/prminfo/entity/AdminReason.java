package com.goldsign.acc.app.prminfo.entity;

public class AdminReason {
    private String adminManagerName;
    
    private String adminManagerId;

    private String recordFlag;

    private String versionNo;
    
    private PrmVersion prmVersion;

    public PrmVersion getPrmVersion() {
        return prmVersion;
    }

    public void setPrmVersion(PrmVersion prmVersion) {
        this.prmVersion = prmVersion;
    }

    public AdminReason(String adminManagerId, String recordFlag, String versionNo, String adminManagerName) {
        this.adminManagerId = adminManagerId;
        this.recordFlag = recordFlag;
        this.versionNo = versionNo;
        this.adminManagerName = adminManagerName;
    }

    public AdminReason() {
        super();
    }

    public String getAdminManagerName() {
        return adminManagerName;
    }

    public void setAdminManagerName(String adminManagerName) {
        this.adminManagerName = adminManagerName == null ? null : adminManagerName.trim();
    }
    
    public String getAdminManagerId() {
        return adminManagerId;
    }

    public void setAdminManagerId(String adminManagerId) {
        this.adminManagerId = adminManagerId == null ? null : adminManagerId.trim();
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag == null ? null : recordFlag.trim();
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo == null ? null : versionNo.trim();
    }
}