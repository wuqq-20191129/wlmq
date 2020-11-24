package com.goldsign.acc.app.prmdev.entity;

public class DeviceTypeKey {
    private String devTypeId;

    private String versionNo;

    private String recordFlag;

    public DeviceTypeKey(String devTypeId, String versionNo, String recordFlag) {
        this.devTypeId = devTypeId;
        this.versionNo = versionNo;
        this.recordFlag = recordFlag;
    }

    public DeviceTypeKey() {
        super();
    }

    public String getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(String devTypeId) {
        this.devTypeId = devTypeId == null ? null : devTypeId.trim();
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo == null ? null : versionNo.trim();
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag == null ? null : recordFlag.trim();
    }
}