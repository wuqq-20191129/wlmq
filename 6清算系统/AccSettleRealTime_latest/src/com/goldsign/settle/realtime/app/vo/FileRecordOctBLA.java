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
public class FileRecordOctBLA extends FileRecordBase{
    private String cardLogicalIdStart;
    private String sectFlag;
    private String cardLogicalIdEnd;
    private String cardStatusApp;

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
     * @return the cardStatusApp
     */
    public String getCardStatusApp() {
        return cardStatusApp;
    }

    /**
     * @param cardStatusApp the cardStatusApp to set
     */
    public void setCardStatusApp(String cardStatusApp) {
        this.cardStatusApp = cardStatusApp;
    }
    
    
}
