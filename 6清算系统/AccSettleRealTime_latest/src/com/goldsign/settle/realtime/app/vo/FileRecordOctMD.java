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
public class FileRecordOctMD extends FileRecordBase {

    private String cardLogicalIdStart;
    private String cardLogicalIdEnd;
    private String sectFlag;
    private String cardStatus;

    /**
     * @return the cardLogicalIdStart
     */
    public String getCardLogicalIdStart() {
        return cardLogicalIdStart;
    }

    /**
     * @param cardLogicalIdStart the cardLogicalIdStart to set
     */
    public void setCardLogicalIdStart(String cardLogicalIdStart) {
        this.cardLogicalIdStart = cardLogicalIdStart;
    }

    /**
     * @return the cardLogicalIdEnd
     */
    public String getCardLogicalIdEnd() {
        return cardLogicalIdEnd;
    }

    /**
     * @param cardLogicalIdEnd the cardLogicalIdEnd to set
     */
    public void setCardLogicalIdEnd(String cardLogicalIdEnd) {
        this.cardLogicalIdEnd = cardLogicalIdEnd;
    }

    /**
     * @return the sectFlag
     */
    public String getSectFlag() {
        return sectFlag;
    }

    /**
     * @param sectFlag the sectFlag to set
     */
    public void setSectFlag(String sectFlag) {
        this.sectFlag = sectFlag;
    }

    /**
     * @return the cardStatus
     */
    public String getCardStatus() {
        return cardStatus;
    }

    /**
     * @param cardStatus the cardStatus to set
     */
    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }
}
