package com.goldsign.acc.app.prmdev.entity;

import com.goldsign.acc.app.prminfo.entity.PrmVersion;

public class SamList {
    private String samTypeId;
    
    private String deviceId;

    private String devTypeId;

    private String lineId;

    private String stationId;

    private String recordFlag;

    private String versionNo;

    private String samLogicalId;
    
    private String lineIdText = null;
    private String stationIdText = null;
    private String deviceTypeIdText = null;

    private String samTypeCodeText = null;
    
    private PrmVersion prmVersion;

    public PrmVersion getPrmVersion() {
        return prmVersion;
    }

    public void setPrmVersion(PrmVersion prmVersion) {
        this.prmVersion = prmVersion;
    }

    public SamList(String deviceId, String devTypeId, String lineId, String stationId, String recordFlag, String versionNo, String samLogicalId, String samTypeId) {
        this.deviceId = deviceId;
        this.devTypeId = devTypeId;
        this.lineId = lineId;
        this.stationId = stationId;
        this.recordFlag = recordFlag;
        this.versionNo = versionNo;
        this.samLogicalId = samLogicalId;
        this.samTypeId = samTypeId;
    }

    public SamList() {
        super();
    }

    public String getSamTypeId() {
        return samTypeId;
    }

    public void setSamTypeId(String samTypeId) {
        this.samTypeId = samTypeId == null ? null : samTypeId.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(String devTypeId) {
        this.devTypeId = devTypeId == null ? null : devTypeId.trim();
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId == null ? null : lineId.trim();
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
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

    public String getSamLogicalId() {
        return samLogicalId;
    }

    public void setSamLogicalId(String samLogicalId) {
        this.samLogicalId = samLogicalId == null ? null : samLogicalId.trim();
    }

    public String getLineIdText() {
        return lineIdText;
    }

    public void setLineIdText(String lineIdText) {
        this.lineIdText = lineIdText;
    }

    public String getStationIdText() {
        return stationIdText;
    }

    public void setStationIdText(String stationIdText) {
        this.stationIdText = stationIdText;
    }

    public String getDeviceTypeIdText() {
        return deviceTypeIdText;
    }

    public void setDeviceTypeIdText(String deviceTypeIdText) {
        this.deviceTypeIdText = deviceTypeIdText;
    }

    public String getSamTypeCodeText() {
        return samTypeCodeText;
    }

    public void setSamTypeCodeText(String samTypeCodeText) {
        this.samTypeCodeText = samTypeCodeText;
    }
    
}