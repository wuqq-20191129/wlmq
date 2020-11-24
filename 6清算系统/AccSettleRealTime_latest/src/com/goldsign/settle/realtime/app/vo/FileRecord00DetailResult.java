/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileRecord00DetailResult {
    private Vector details;
    private Vector detailsEntry;
    private Vector detailsExit;
    private Vector detailsConsume;
    private int offsetTotal;
    private int totalNum;
    private int totalFee;

    /**
     * @return the details
     */
    public Vector getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(Vector details) {
        this.details = details;
    }

    /**
     * @return the offsetTotal
     */
    public int getOffsetTotal() {
        return offsetTotal;
    }

    /**
     * @param offsetTotal the offsetTotal to set
     */
    public void setOffsetTotal(int offsetTotal) {
        this.offsetTotal = offsetTotal;
    }

    /**
     * @return the totalNum
     */
    public int getTotalNum() {
        return totalNum;
    }

    /**
     * @param totalNum the totalNum to set
     */
    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    /**
     * @return the totalFee
     */
    public int getTotalFee() {
        return totalFee;
    }

    /**
     * @param totalFee the totalFee to set
     */
    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * @return the detailsEntry
     */
    public Vector getDetailsEntry() {
        return detailsEntry;
    }

    /**
     * @param detailsEntry the detailsEntry to set
     */
    public void setDetailsEntry(Vector detailsEntry) {
        this.detailsEntry = detailsEntry;
    }

    /**
     * @return the detailsExit
     */
    public Vector getDetailsExit() {
        return detailsExit;
    }

    /**
     * @param detailsExit the detailsExit to set
     */
    public void setDetailsExit(Vector detailsExit) {
        this.detailsExit = detailsExit;
    }

    /**
     * @return the detailsConsume
     */
    public Vector getDetailsConsume() {
        return detailsConsume;
    }

    /**
     * @param detailsConsume the detailsConsume to set
     */
    public void setDetailsConsume(Vector detailsConsume) {
        this.detailsConsume = detailsConsume;
    }
    
}
