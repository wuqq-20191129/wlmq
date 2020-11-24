package com.goldsign.acc.app.prminfo.entity;

public class AdminManager {
    private String adminCharge;

    private String adminManagerId;

    private String recordFlag;

    private String versionNo;

    private String stationId;

    private String cardSubId;

    private String lineId;

    private String cardMainId;
    
    private String lineIdText = null;
    private String stationIdText = null;
    private String adminManagerIdText = null;
    private String cardSubIdText = null;
    private String cardMainIdText = null;
    
    private PrmVersion prmVersion;

    public PrmVersion getPrmVersion() {
        return prmVersion;
    }

    public void setPrmVersion(PrmVersion prmVersion) {
        this.prmVersion = prmVersion;
    }

    public AdminManager(String adminManagerId, String recordFlag, String versionNo, String stationId, String cardSubId, String lineId, String cardMainId, String adminCharge) {
        this.adminCharge = adminCharge;
        this.adminManagerId = adminManagerId;
        this.recordFlag = recordFlag;
        this.versionNo = versionNo;
        this.stationId = stationId;
        this.cardSubId = cardSubId;
        this.lineId = lineId;
        this.cardMainId = cardMainId;
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

    public String getAdminManagerIdText() {
        return adminManagerIdText;
    }

    public void setAdminManagerIdText(String adminManagerIdText) {
        this.adminManagerIdText = adminManagerIdText;
    }

    public String getCardSubIdText() {
        return cardSubIdText;
    }

    public void setCardSubIdText(String cardSubIdText) {
        this.cardSubIdText = cardSubIdText;
    }

    public String getCardMainIdText() {
        return cardMainIdText;
    }

    public void setCardMainIdText(String cardMainIdText) {
        this.cardMainIdText = cardMainIdText;
    }

    public AdminManager() {
        super();
    }

    public String getAdminCharge() {
        return adminCharge;
    }

    public void setAdminCharge(String adminCharge) {
        this.adminCharge = adminCharge == null ? null : adminCharge.trim();
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

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
    }

    public String getCardSubId() {
        return cardSubId;
    }

    public void setCardSubId(String cardSubId) {
        this.cardSubId = cardSubId == null ? null : cardSubId.trim();
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId == null ? null : lineId.trim();
    }

    public String getCardMainId() {
        return cardMainId;
    }

    public void setCardMainId(String cardMainId) {
        this.cardMainId = cardMainId == null ? null : cardMainId.trim();
    }
}