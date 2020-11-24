/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.fm.vo;


/**
 *
 * @author Administrator
 */
public class LineVo {
private String lineId     ;
private String     versionNo   ;
private String     lineName   ;
private String     recordFlag ;
private String     lccIp      ;
private String     lccLineId  ;

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

    /**
     * @return the lineName
     */
    public String getLineName() {
        return lineName;
    }

    /**
     * @param lineName the lineName to set
     */
    public void setLineName(String lineName) {
        this.lineName = lineName;
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
     * @return the lccLineId
     */
    public String getLccLineId() {
        return lccLineId;
    }

    /**
     * @param lccLineId the lccLineId to set
     */
    public void setLccLineId(String lccLineId) {
        this.lccLineId = lccLineId;
    }

    public String toString(){
        return this.lineName;
    }

}
