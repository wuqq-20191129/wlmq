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
public class FileRecord1Detail extends FileRecordBase {


   
    private String paymodeId;
    private int dealNum;
    private int dealFee;

    /**
     * @return the paymodeId
     */
    public String getPaymodeId() {
        return paymodeId;
    }

    /**
     * @param paymodeId the paymodeId to set
     */
    public void setPaymodeId(String paymodeId) {
        this.paymodeId = paymodeId;
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
