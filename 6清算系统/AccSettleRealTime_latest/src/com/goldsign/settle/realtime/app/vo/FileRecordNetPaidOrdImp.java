/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

/**
 *
 * @author hejj
 */
public class FileRecordNetPaidOrdImp extends FileRecordNetPaidOrdBase {

    private String finishDatetime;
    private int dealNum;
    private int dealNumNot;
    private int dealFee;
    private int refundFee;
    private int auxiFee;

    /**
     * @return the finishDatetime
     */
    public String getFinishDatetime() {
        return finishDatetime;
    }

    /**
     * @param finishDatetime the finishDatetime to set
     */
    public void setFinishDatetime(String finishDatetime) {
        this.finishDatetime = finishDatetime;
    }

    /**
     * @return the dealNum
     */
    public int getDealNum() {
        return dealNum;
    }

    /**
     * @param dealNum the dealNum to set
     */
    public void setDealNum(int dealNum) {
        this.dealNum = dealNum;
    }

    /**
     * @return the dealNumNot
     */
    public int getDealNumNot() {
        return dealNumNot;
    }

    /**
     * @param dealNumNot the dealNumNot to set
     */
    public void setDealNumNot(int dealNumNot) {
        this.dealNumNot = dealNumNot;
    }

   
    /**
     * @return the refundFee
     */
    public int getRefundFee() {
        return refundFee;
    }

    /**
     * @param refundFee the refundFee to set
     */
    public void setRefundFee(int refundFee) {
        this.refundFee = refundFee;
    }

    /**
     * @return the auxiFee
     */
    public int getAuxiFee() {
        return auxiFee;
    }

    /**
     * @param auxiFee the auxiFee to set
     */
    public void setAuxiFee(int auxiFee) {
        this.auxiFee = auxiFee;
    }

    /**
     * @return the dealFee
     */
    public int getDealFee() {
        return dealFee;
    }

    /**
     * @param dealFee the dealFee to set
     */
    public void setDealFee(int dealFee) {
        this.dealFee = dealFee;
    }

    
}
