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
public class FileRecord2Detail extends FileRecordBase {

    private int dealNum;//交易数量
    private int dealFee;//交易金额

    private String paymodeId;

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
}
