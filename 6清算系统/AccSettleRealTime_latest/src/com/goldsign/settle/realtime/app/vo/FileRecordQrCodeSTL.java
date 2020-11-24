/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

/**
 *
 * @author hejj
 */
public class FileRecordQrCodeSTL extends FileRecordSTL {

    /**
     * @return the qrMatchNum
     */
    public int getQrMatchNum() {
        return qrMatchNum;
    }

    /**
     * @param qrMatchNum the qrMatchNum to set
     */
    public void setQrMatchNum(int qrMatchNum) {
        this.qrMatchNum = qrMatchNum;
    }

    /**
     * @return the qrMatchFee
     */
    public int getQrMatchFee() {
        return qrMatchFee;
    }

    /**
     * @param qrMatchFee the qrMatchFee to set
     */
    public void setQrMatchFee(int qrMatchFee) {
        this.qrMatchFee = qrMatchFee;
    }

    /**
     * @return the qrMatchNotNum
     */
    public int getQrMatchNotNum() {
        return qrMatchNotNum;
    }

    /**
     * @param qrMatchNotNum the qrMatchNotNum to set
     */
    public void setQrMatchNotNum(int qrMatchNotNum) {
        this.qrMatchNotNum = qrMatchNotNum;
    }

    /**
     * @return the qrMatchNotFee
     */
    public int getQrMatchNotFee() {
        return qrMatchNotFee;
    }

    /**
     * @param qrMatchNotFee the qrMatchNotFee to set
     */
    public void setQrMatchNotFee(int qrMatchNotFee) {
        this.qrMatchNotFee = qrMatchNotFee;
    }

    private int qrEntryum;// 二维码进站总数量              
    private int electTkTctEntryNum;// 电子票进站总数量（计次）      
    private int electTkEntryNum;//电子票进站总数量（计值）      
    private int qrDealNum;//二维码钱包交易总数量          
    private int qrDealFee;//二维码钱包交易总金额          
    private int electTkTctDealNum;//电子票钱包交易总次数（计次）  
    private int electTkDealNum;//电子票钱包交易总数量（计值）  
    private int electTkDealFee;//电子票钱包交易总金额（计值
    private String recordVer;

    private int qrMatchNum;//二维码匹配总数量          
    private int qrMatchFee;//二维码匹配总金额  
    private int qrMatchNotNum;//二维码不匹配总数量          
    private int qrMatchNotFee;//二维码不匹配总金额

    /**
     * @return the qrEntryum
     */
    public int getQrEntryum() {
        return qrEntryum;
    }

    /**
     * @param qrEntryum the qrEntryum to set
     */
    public void setQrEntryum(int qrEntryum) {
        this.qrEntryum = qrEntryum;
    }

    /**
     * @return the electTkTctEntryNum
     */
    public int getElectTkTctEntryNum() {
        return electTkTctEntryNum;
    }

    /**
     * @param electTkTctEntryNum the electTkTctEntryNum to set
     */
    public void setElectTkTctEntryNum(int electTkTctEntryNum) {
        this.electTkTctEntryNum = electTkTctEntryNum;
    }

    /**
     * @return the electTkEntryNum
     */
    public int getElectTkEntryNum() {
        return electTkEntryNum;
    }

    /**
     * @param electTkEntryNum the electTkEntryNum to set
     */
    public void setElectTkEntryNum(int electTkEntryNum) {
        this.electTkEntryNum = electTkEntryNum;
    }

    /**
     * @return the qrDealNum
     */
    public int getQrDealNum() {
        return qrDealNum;
    }

    /**
     * @param qrDealNum the qrDealNum to set
     */
    public void setQrDealNum(int qrDealNum) {
        this.qrDealNum = qrDealNum;
    }

    /**
     * @return the qrDealFee
     */
    public int getQrDealFee() {
        return qrDealFee;
    }

    /**
     * @param qrDealFee the qrDealFee to set
     */
    public void setQrDealFee(int qrDealFee) {
        this.qrDealFee = qrDealFee;
    }

    /**
     * @return the electTkTctDealNum
     */
    public int getElectTkTctDealNum() {
        return electTkTctDealNum;
    }

    /**
     * @param electTkTctDealNum the electTkTctDealNum to set
     */
    public void setElectTkTctDealNum(int electTkTctDealNum) {
        this.electTkTctDealNum = electTkTctDealNum;
    }

    /**
     * @return the electTkDealNum
     */
    public int getElectTkDealNum() {
        return electTkDealNum;
    }

    /**
     * @param electTkDealNum the electTkDealNum to set
     */
    public void setElectTkDealNum(int electTkDealNum) {
        this.electTkDealNum = electTkDealNum;
    }

    /**
     * @return the electTkDealFee
     */
    public int getElectTkDealFee() {
        return electTkDealFee;
    }

    /**
     * @param electTkDealFee the electTkDealFee to set
     */
    public void setElectTkDealFee(int electTkDealFee) {
        this.electTkDealFee = electTkDealFee;
    }

    /**
     * @return the recordVer
     */
    public String getRecordVer() {
        return recordVer;
    }

    /**
     * @param recordVer the recordVer to set
     */
    public void setRecordVer(String recordVer) {
        this.recordVer = recordVer;
    }

}
