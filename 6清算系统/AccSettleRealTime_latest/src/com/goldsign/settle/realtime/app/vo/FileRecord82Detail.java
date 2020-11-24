/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
public class FileRecord82Detail extends FileRecordBase {

    private String waterNo;
    private int cardMoney;
    private int saleSjtNum;
    private int SaleSjtFee;
    private int coinReceiveNum;
    private int coinReceiveFee;
    private int noteReceiveNum;
    private int noteReceiveFee;

    /**
     * @return the waterNo
     */
    public String getWaterNo() {
        return waterNo;
    }

    /**
     * @param waterNo the waterNo to set
     */
    public void setWaterNo(String waterNo) {
        this.waterNo = waterNo;
    }

    /**
     * @return the cardMoney
     */
    public int getCardMoney() {
        return cardMoney;
    }

    /**
     * @param cardMoney the cardMoney to set
     */
    public void setCardMoney(int cardMoney) {
        this.cardMoney = cardMoney;
    }

    /**
     * @return the saleSjtNum
     */
    public int getSaleSjtNum() {
        return saleSjtNum;
    }

    /**
     * @param saleSjtNum the saleSjtNum to set
     */
    public void setSaleSjtNum(int saleSjtNum) {
        this.saleSjtNum = saleSjtNum;
    }

    /**
     * @return the SaleSjtFee
     */
    public int getSaleSjtFee() {
        return SaleSjtFee;
    }

    /**
     * @param SaleSjtFee the SaleSjtFee to set
     */
    public void setSaleSjtFee(int SaleSjtFee) {
        this.SaleSjtFee = SaleSjtFee;
    }

    /**
     * @return the coinReceiveNum
     */
    public int getCoinReceiveNum() {
        return coinReceiveNum;
    }

    /**
     * @param coinReceiveNum the coinReceiveNum to set
     */
    public void setCoinReceiveNum(int coinReceiveNum) {
        this.coinReceiveNum = coinReceiveNum;
    }

    /**
     * @return the coinReceiveFee
     */
    public int getCoinReceiveFee() {
        return coinReceiveFee;
    }

    /**
     * @param coinReceiveFee the coinReceiveFee to set
     */
    public void setCoinReceiveFee(int coinReceiveFee) {
        this.coinReceiveFee = coinReceiveFee;
    }

    /**
     * @return the noteReceiveNum
     */
    public int getNoteReceiveNum() {
        return noteReceiveNum;
    }

    /**
     * @param noteReceiveNum the noteReceiveNum to set
     */
    public void setNoteReceiveNum(int noteReceiveNum) {
        this.noteReceiveNum = noteReceiveNum;
    }

    /**
     * @return the noteReceiveFee
     */
    public int getNoteReceiveFee() {
        return noteReceiveFee;
    }

    /**
     * @param noteReceiveFee the noteReceiveFee to set
     */
    public void setNoteReceiveFee(int noteReceiveFee) {
        this.noteReceiveFee = noteReceiveFee;
    }

}
