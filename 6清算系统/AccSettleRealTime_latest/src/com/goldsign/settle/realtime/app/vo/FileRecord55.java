/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
public class FileRecord55 extends FileRecordBase{

    /**
     * @return the oldExpireDatetime_s
     */
    public String getOldExpireDatetime_s() {
        return oldExpireDatetime_s;
    }

    /**
     * @param oldExpireDatetime_s the oldExpireDatetime_s to set
     */
    public void setOldExpireDatetime_s(String oldExpireDatetime_s) {
        this.oldExpireDatetime_s = oldExpireDatetime_s;
    }

    /**
     * @return the newExpireDatetime_s
     */
    public String getNewExpireDatetime_s() {
        return newExpireDatetime_s;
    }

    /**
     * @param newExpireDatetime_s the newExpireDatetime_s to set
     */
    public void setNewExpireDatetime_s(String newExpireDatetime_s) {
        this.newExpireDatetime_s = newExpireDatetime_s;
    }

    /**
     * @return the cardStatusId_s
     */
    public String getCardStatusId_s() {
        return cardStatusId_s;
    }

    /**
     * @param cardStatusId_s the cardStatusId_s to set
     */
    public void setCardStatusId_s(String cardStatusId_s) {
        this.cardStatusId_s = cardStatusId_s;
    }

    /**
     * @return the opDateTime_s
     */
    public String getOpDateTime_s() {
        return opDateTime_s;
    }

    /**
     * @param opDateTime_s the opDateTime_s to set
     */
    public void setOpDateTime_s(String opDateTime_s) {
        this.opDateTime_s = opDateTime_s;
    }
    private String oldExpireDatetime;
    private String newExpireDatetime;
    private int cardStatusId;
    private String opDateTime;
    
    private String oldExpireDatetime_s;
    private String newExpireDatetime_s;
    private String cardStatusId_s;
    private String opDateTime_s;

    /**
     * @return the oldExpireDatetime
     */
    public String getOldExpireDatetime() {
        return oldExpireDatetime;
    }

    /**
     * @param oldExpireDatetime the oldExpireDatetime to set
     */
    public void setOldExpireDatetime(String oldExpireDatetime) {
        this.oldExpireDatetime = oldExpireDatetime;
    }

    /**
     * @return the newExpireDatetime
     */
    public String getNewExpireDatetime() {
        return newExpireDatetime;
    }

    /**
     * @param newExpireDatetime the newExpireDatetime to set
     */
    public void setNewExpireDatetime(String newExpireDatetime) {
        this.newExpireDatetime = newExpireDatetime;
    }

    /**
     * @return the cardStatusId
     */
    public int getCardStatusId() {
        return cardStatusId;
    }

    /**
     * @param cardStatusId the cardStatusId to set
     */
    public void setCardStatusId(int cardStatusId) {
        this.cardStatusId = cardStatusId;
    }

    /**
     * @return the opDateTime
     */
    public String getOpDateTime() {
        return opDateTime;
    }

    /**
     * @param opDateTime the opDateTime to set
     */
    public void setOpDateTime(String opDateTime) {
        this.opDateTime = opDateTime;
    }
    
    
}
