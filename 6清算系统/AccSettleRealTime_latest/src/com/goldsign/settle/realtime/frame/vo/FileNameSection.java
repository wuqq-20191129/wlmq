/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

/**
 *
 * @author hejj
 */
public class FileNameSection {
    private String tradType;
    private String lineStationId;
    private int seq;
    private String tradDate;

    /**
     * @return the tradType
     */
    public String getTradType() {
        return tradType;
    }

    /**
     * @param tradType the tradType to set
     */
    public void setTradType(String tradType) {
        this.tradType = tradType;
    }

    /**
     * @return the lineStationId
     */
    public String getLineStationId() {
        return lineStationId;
    }

    /**
     * @param lineStationId the lineStationId to set
     */
    public void setLineStationId(String lineStationId) {
        this.lineStationId = lineStationId;
    }

    /**
     * @return the seq
     */
    public int getSeq() {
        return seq;
    }

    /**
     * @param seq the seq to set
     */
    public void setSeq(int seq) {
        this.seq = seq;
    }

    /**
     * @return the tradDate
     */
    public String getTradDate() {
        return tradDate;
    }

    /**
     * @param tradDate the tradDate to set
     */
    public void setTradDate(String tradDate) {
        this.tradDate = tradDate;
    }
    
}
