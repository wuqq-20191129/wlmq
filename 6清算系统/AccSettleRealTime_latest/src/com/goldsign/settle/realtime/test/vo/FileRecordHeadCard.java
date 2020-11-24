/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.vo;

/**
 *
 * @author hejj
 */
public class FileRecordHeadCard {
    private String facId;
    private String batchNo;

    private int seq;
    private int rowCount;

    /**
     * @return the facId
     */
    public String getFacId() {
        return facId;
    }

    /**
     * @param facId the facId to set
     */
    public void setFacId(String facId) {
        this.facId = facId;
    }

    /**
     * @return the batchNo
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * @param batchNo the batchNo to set
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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
     * @return the rowCount
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * @param rowCount the rowCount to set
     */
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
    
}
