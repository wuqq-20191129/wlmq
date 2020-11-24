/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import java.math.BigDecimal;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class ExportDbResult {

    private Vector records = new Vector();;
    private int totalNum;
    private BigDecimal totalFee;
    private int totalFeeFen;

    /**
     * @return the records
     */
    public Vector getRecords() {
        return records;
    }

    /**
     * @param records the records to set
     */
    public void setRecords(Vector records) {
        this.records = records;
    }
    
    public void addRecords(Vector records) {

        this.records.addAll(records);
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
    public BigDecimal getTotalFee() {
        return totalFee;
    }

    /**
     * @param totalFee the totalFee to set
     */
    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * @return the totalFeeFen
     */
    public int getTotalFeeFen() {
        return totalFeeFen;
    }

    /**
     * @param totalFeeFen the totalFeeFen to set
     */
    public void setTotalFeeFen(int totalFeeFen) {
        this.totalFeeFen = totalFeeFen;
    }

}
