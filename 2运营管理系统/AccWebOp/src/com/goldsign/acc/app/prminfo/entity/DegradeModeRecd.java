package com.goldsign.acc.app.prminfo.entity;

public class DegradeModeRecd {
    private Long waterNo;

    private String lineId;

    private String stationId;

    private String degradeModeId;

    private String startTime;

    private String endTime;

    private String setOperId;

    private String cancelOperId;

    private String hdlFlag;

    private String versionNo;
    private String lineIdText = null;
    private String stationIdText = null;
    private String degradeModeIdText = null;
    private String hdlFlagText = null;
    
    
    public DegradeModeRecd(Long waterNo, String lineId, String stationId, String degradeModeId, String startTime, String endTime, String setOperId, String cancelOperId, String hdlFlag, String versionNo) {
        this.waterNo = waterNo;
        this.lineId = lineId;
        this.stationId = stationId;
        this.degradeModeId = degradeModeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.setOperId = setOperId;
        this.cancelOperId = cancelOperId;
        this.hdlFlag = hdlFlag;
        this.versionNo = versionNo;
    }

    public DegradeModeRecd() {
        super();
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

    public String getDegradeModeIdText() {
        return degradeModeIdText;
    }

    public void setDegradeModeIdText(String degradeModeIdText) {
        this.degradeModeIdText = degradeModeIdText;
    }

    public String getHdlFlagText() {
        return hdlFlagText;
    }

    public void setHdlFlagText(String hdlFlag) {
        if(hdlFlag!=null){
            if(hdlFlag.equals("1")){
                this.hdlFlagText="已下发";
            }
            if(hdlFlag.equals("2")){
                this.hdlFlagText="未下发";
            }
        }
        
    }

    public Long getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(Long waterNo) {
        this.waterNo = waterNo;
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

    public String getDegradeModeId() {
        return degradeModeId;
    }

    public void setDegradeModeId(String degradeModeId) {
        this.degradeModeId = degradeModeId == null ? null : degradeModeId.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getSetOperId() {
        return setOperId;
    }

    public void setSetOperId(String setOperId) {
        this.setOperId = setOperId == null ? null : setOperId.trim();
    }

    public String getCancelOperId() {
        return cancelOperId;
    }

    public void setCancelOperId(String cancelOperId) {
        this.cancelOperId = cancelOperId == null ? null : cancelOperId.trim();
    }

    public String getHdlFlag() {
        return hdlFlag;
    }

    public void setHdlFlag(String hdlFlag) {
        this.hdlFlag = hdlFlag == null ? null : hdlFlag.trim();
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo == null ? null : versionNo.trim();
    }
}