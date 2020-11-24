/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

/**
 *
 * @author hejj
 */
public class FileQrCodeSDFVo extends FileSDFVo {
    private int diffQrEntryum;// 二维码进站总数量              
    private int diffElectTkTctEntryNum;// 电子票进站总数量（计次）      
    private int diffElectTkEntryNum;//电子票进站总数量（计值）      
    private int diffQrDealNum;//二维码钱包交易总数量          
    private int diffQrDealFee;//二维码钱包交易总金额          
    private int diffElectTkTctDealNum;//电子票钱包交易总次数（计次）  
    private int diffElectTkDealNum;//电子票钱包交易总数量（计值）  
    private int diffElectTkDealFee;//电子票钱包交易总金额（计值
    private String recordVer;
    private String squadDay;

    private int diffQrMatchNum;//二维码匹配总数量          
    private int diffQrMatchFee;//二维码匹配总金额 
    private int diffQrMatchNotNum;//二维码单边总数量          
    private int diffQrMatchNotFee;//二维码单边总金额 
    
    private String issueQrcodePlatformFlag;//发码平台标识

    /**
     * @return the diffQrMatchNum
     */
    public int getDiffQrMatchNum() {
        return diffQrMatchNum;
    }

    /**
     * @param diffQrMatchNum the diffQrMatchNum to set
     */
    public void setDiffQrMatchNum(int diffQrMatchNum) {
        this.diffQrMatchNum = diffQrMatchNum;
    }

    /**
     * @return the diffQrMatchFee
     */
    public int getDiffQrMatchFee() {
        return diffQrMatchFee;
    }

    /**
     * @param diffQrMatchFee the diffQrMatchFee to set
     */
    public void setDiffQrMatchFee(int diffQrMatchFee) {
        this.diffQrMatchFee = diffQrMatchFee;
    }

    /**
     * @return the diffQrMatchNotNum
     */
    public int getDiffQrMatchNotNum() {
        return diffQrMatchNotNum;
    }

    /**
     * @param diffQrMatchNotNum the diffQrMatchNotNum to set
     */
    public void setDiffQrMatchNotNum(int diffQrMatchNotNum) {
        this.diffQrMatchNotNum = diffQrMatchNotNum;
    }

    /**
     * @return the diffQrMatchNotFee
     */
    public int getDiffQrMatchNotFee() {
        return diffQrMatchNotFee;
    }

    /**
     * @param diffQrMatchNotFee the diffQrMatchNotFee to set
     */
    public void setDiffQrMatchNotFee(int diffQrMatchNotFee) {
        this.diffQrMatchNotFee = diffQrMatchNotFee;
    }

    

    /**
     * @return the diffQrEntryum
     */
    public int getDiffQrEntryum() {
        return diffQrEntryum;
    }

    /**
     * @param diffQrEntryum the diffQrEntryum to set
     */
    public void setDiffQrEntryum(int diffQrEntryum) {
        this.diffQrEntryum = diffQrEntryum;
    }

    /**
     * @return the diffElectTkTctEntryNum
     */
    public int getDiffElectTkTctEntryNum() {
        return diffElectTkTctEntryNum;
    }

    /**
     * @param diffElectTkTctEntryNum the diffElectTkTctEntryNum to set
     */
    public void setDiffElectTkTctEntryNum(int diffElectTkTctEntryNum) {
        this.diffElectTkTctEntryNum = diffElectTkTctEntryNum;
    }

    /**
     * @return the diffQrDealNum
     */
    public int getDiffQrDealNum() {
        return diffQrDealNum;
    }

    /**
     * @param diffQrDealNum the diffQrDealNum to set
     */
    public void setDiffQrDealNum(int diffQrDealNum) {
        this.diffQrDealNum = diffQrDealNum;
    }

    /**
     * @return the diffQrDealFee
     */
    public int getDiffQrDealFee() {
        return diffQrDealFee;
    }

    /**
     * @param diffQrDealFee the diffQrDealFee to set
     */
    public void setDiffQrDealFee(int diffQrDealFee) {
        this.diffQrDealFee = diffQrDealFee;
    }

    /**
     * @return the diffElectTkTctDealNum
     */
    public int getDiffElectTkTctDealNum() {
        return diffElectTkTctDealNum;
    }

    /**
     * @param diffElectTkTctDealNum the diffElectTkTctDealNum to set
     */
    public void setDiffElectTkTctDealNum(int diffElectTkTctDealNum) {
        this.diffElectTkTctDealNum = diffElectTkTctDealNum;
    }

    /**
     * @return the diffElectTkDealNum
     */
    public int getDiffElectTkDealNum() {
        return diffElectTkDealNum;
    }

    /**
     * @param diffElectTkDealNum the diffElectTkDealNum to set
     */
    public void setDiffElectTkDealNum(int diffElectTkDealNum) {
        this.diffElectTkDealNum = diffElectTkDealNum;
    }

    /**
     * @return the diffElectTkDealFee
     */
    public int getDiffElectTkDealFee() {
        return diffElectTkDealFee;
    }

    /**
     * @param diffElectTkDealFee the diffElectTkDealFee to set
     */
    public void setDiffElectTkDealFee(int diffElectTkDealFee) {
        this.diffElectTkDealFee = diffElectTkDealFee;
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

    /**
     * @return the diffElectTkEntryNum
     */
    public int getDiffElectTkEntryNum() {
        return diffElectTkEntryNum;
    }

    /**
     * @param diffElectTkEntryNum the diffElectTkEntryNum to set
     */
    public void setDiffElectTkEntryNum(int diffElectTkEntryNum) {
        this.diffElectTkEntryNum = diffElectTkEntryNum;
    }

    /**
     * @return the squadDay
     */
    public String getSquadDay() {
        return squadDay;
    }

    /**
     * @param squadDay the squadDay to set
     */
    public void setSquadDay(String squadDay) {
        this.squadDay = squadDay;
    }

    /**
     * @return the issueQrcodePlatformFlag
     */
    public String getIssueQrcodePlatformFlag() {
        return issueQrcodePlatformFlag;
    }

    /**
     * @param issueQrcodePlatformFlag the issueQrcodePlatformFlag to set
     */
    public void setIssueQrcodePlatformFlag(String issueQrcodePlatformFlag) {
        this.issueQrcodePlatformFlag = issueQrcodePlatformFlag;
    }

}
