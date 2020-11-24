/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.entity;

/**
 * 清洗/核查出入库差异
 * @author luck
 */
public class TicketStorageInOutCleanDiff {
    private int outNum;  //出库数量

    private int validNum;  //有效票数量

    private int realBalance;  //实际结余数量

    private int manUselessNum;  //人工废票数量

    private int cancelFewNum;  //缺少数量

    private int cancelMoreNum;  //核查多余数量
 
    private int lostNum;  //遗失票数量

    private String inBillNo;  //入库单号

    private String outBillNo; //出库单号

    private int outInDiff; //出入库差异数量

    private String inBillDate;  //入库时间
    
    private String inFlag; //入库标志
    
    private int preInNum; //之前入库数量

    public int getOutNum() {
        return outNum;
    }

    public void setOutNum(int outNum) {
        this.outNum = outNum;
    }

    public int getValidNum() {
        return validNum;
    }

    public void setValidNum(int validNum) {
        this.validNum = validNum;
    }

    public int getRealBalance() {
        return realBalance;
    }

    public void setRealBalance(int realBalance) {
        this.realBalance = realBalance;
    }

    public int getManUselessNum() {
        return manUselessNum;
    }

    public void setManUselessNum(int manUselessNum) {
        this.manUselessNum = manUselessNum;
    }

    public int getCancelFewNum() {
        return cancelFewNum;
    }

    public void setCancelFewNum(int cancelFewNum) {
        this.cancelFewNum = cancelFewNum;
    }

    public int getCancelMoreNum() {
        return cancelMoreNum;
    }

    public void setCancelMoreNum(int cancelMoreNum) {
        this.cancelMoreNum = cancelMoreNum;
    }

    public int getLostNum() {
        return lostNum;
    }

    public void setLostNum(int lostNum) {
        this.lostNum = lostNum;
    }

    public String getInBillNo() {
        return inBillNo;
    }

    public void setInBillNo(String inBillNo) {
        this.inBillNo = inBillNo;
    }

    public String getOutBillNo() {
        return outBillNo;
    }

    public void setOutBillNo(String outBillNo) {
        this.outBillNo = outBillNo;
    }

    public int getOutInDiff() {
        return outInDiff;
    }

    public void setOutInDiff(int outInDiff) {
        this.outInDiff = outInDiff;
    }

    public String getInBillDate() {
        return inBillDate;
    }

    public void setInBillDate(String inBillDate) {
        this.inBillDate = inBillDate;
    }

    public String getInFlag() {
        return inFlag;
    }

    public void setInFlag(String inFlag) {
        this.inFlag = inFlag;
    }

    public int getPreInNum() {
        return preInNum;
    }

    public void setPreInNum(int preInNum) {
        this.preInNum = preInNum;
    }

   
    
    
    
    
}
