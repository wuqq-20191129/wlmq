/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.fm.vo;

/**
 *
 * @author Administrator
 */
public class StationVo {

    private String lineId     ;
    private String stationId   ;
    private String chineseName  ;
    private String englishName  ;
    private String scIp        ;
    private String contcId      ;
    private String recordFlag  ;
    private String lccIp       ;
    private String versionNo   ;
    private String belongLineId;

    /**
     * @return the lineId
     */
    public String getLineId() {
        return lineId;
    }

    /**
     * @param lineId the lineId to set
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    /**
     * @return the stationId
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * @param stationId the stationId to set
     */
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    /**
     * @return the chineseName
     */
    public String getChineseName() {
        return chineseName;
    }

    /**
     * @param chineseName the chineseName to set
     */
    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    /**
     * @return the englishName
     */
    public String getEnglishName() {
        return englishName;
    }

    /**
     * @param englishName the englishName to set
     */
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    /**
     * @return the scIp
     */
    public String getScIp() {
        return scIp;
    }

    /**
     * @param scIp the scIp to set
     */
    public void setScIp(String scIp) {
        this.scIp = scIp;
    }

    /**
     * @return the contcId
     */
    public String getContcId() {
        return contcId;
    }

    /**
     * @param contcId the contcId to set
     */
    public void setContcId(String contcId) {
        this.contcId = contcId;
    }

    /**
     * @return the recordFlag
     */
    public String getRecordFlag() {
        return recordFlag;
    }

    /**
     * @param recordFlag the recordFlag to set
     */
    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag;
    }

    /**
     * @return the lccIp
     */
    public String getLccIp() {
        return lccIp;
    }

    /**
     * @param lccIp the lccIp to set
     */
    public void setLccIp(String lccIp) {
        this.lccIp = lccIp;
    }

    /**
     * @return the versionNo
     */
    public String getVersionNo() {
        return versionNo;
    }

    /**
     * @param versionNo the versionNo to set
     */
    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }
    public String toString(){
        return this.chineseName;
    }

    /**
     * @return the belongLineId
     */
    public String getBelongLineId() {
        return belongLineId;
    }

    /**
     * @param belongLineId the belongLineId to set
     */
    public void setBelongLineId(String belongLineId) {
        this.belongLineId = belongLineId;
    }

}
