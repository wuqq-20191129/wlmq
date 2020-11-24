/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileRecord32 extends FileRecordBase {

    /**
     * @return the opDate_s
     */
    public String getOpDate_s() {
        return opDate_s;
    }

    /**
     * @param opDate_s the opDate_s to set
     */
    public void setOpDate_s(String opDate_s) {
        this.opDate_s = opDate_s;
    }

    /**
     * @return the curDatetime_s
     */
    public String getCurDatetime_s() {
        return curDatetime_s;
    }

    /**
     * @param curDatetime_s the curDatetime_s to set
     */
    public void setCurDatetime_s(String curDatetime_s) {
        this.curDatetime_s = curDatetime_s;
    }

    private String opDate;//操作日期
    private String curDatetime;//当前时间
    private String opDate_s;//操作日期
    private String curDatetime_s;//当前时间
    private int cardPutNum;//当天Token Hopper补充总单程票数量
    private int cardClearNum;//当天Token Hopper清空的单程票总数量
    private int coinPutNum;//当天Coin Hopper 补充总硬币数量
    private int coinPutFee;//当天Coin Hopper补充 总硬币金额
    private int coinClearNum;//当天Token Hopper清空的单程票总数量
    private int coinClearFee;//当天Coin Hopper清空的硬币总金额
    private int coinReclaimNum;//当天Coin Box回收的硬币总数量
    private int coinReclaimFee;//当天Coin Box回收的硬币总金额
    private int noteReclaimNum;//当天Banknote Box回收的纸币总张数
    private int noteReclaimFee;//当天Banknote Box回收的纸币总金额
    
    private Vector detail;//行政处理明细

    /**
     * @return the opDate
     */
    public String getOpDate() {
        return opDate;
    }

    /**
     * @param opDate the opDate to set
     */
    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }

    /**
     * @return the curDatetime
     */
    public String getCurDatetime() {
        return curDatetime;
    }

    /**
     * @param curDatetime the curDatetime to set
     */
    public void setCurDatetime(String curDatetime) {
        this.curDatetime = curDatetime;
    }

    /**
     * @return the cardPutNum
     */
    public int getCardPutNum() {
        return cardPutNum;
    }

    /**
     * @param cardPutNum the cardPutNum to set
     */
    public void setCardPutNum(int cardPutNum) {
        this.cardPutNum = cardPutNum;
    }

    /**
     * @return the cardClearNum
     */
    public int getCardClearNum() {
        return cardClearNum;
    }

    /**
     * @param cardClearNum the cardClearNum to set
     */
    public void setCardClearNum(int cardClearNum) {
        this.cardClearNum = cardClearNum;
    }

    /**
     * @return the coinPutNum
     */
    public int getCoinPutNum() {
        return coinPutNum;
    }

    /**
     * @param coinPutNum the coinPutNum to set
     */
    public void setCoinPutNum(int coinPutNum) {
        this.coinPutNum = coinPutNum;
    }

    /**
     * @return the coinPutFee
     */
    public int getCoinPutFee() {
        return coinPutFee;
    }

    /**
     * @param coinPutFee the coinPutFee to set
     */
    public void setCoinPutFee(int coinPutFee) {
        this.coinPutFee = coinPutFee;
    }

    /**
     * @return the coinClearNum
     */
    public int getCoinClearNum() {
        return coinClearNum;
    }

    /**
     * @param coinClearNum the coinClearNum to set
     */
    public void setCoinClearNum(int coinClearNum) {
        this.coinClearNum = coinClearNum;
    }

    /**
     * @return the coinClearFee
     */
    public int getCoinClearFee() {
        return coinClearFee;
    }

    /**
     * @param coinClearFee the coinClearFee to set
     */
    public void setCoinClearFee(int coinClearFee) {
        this.coinClearFee = coinClearFee;
    }

    /**
     * @return the coinReclaimNum
     */
    public int getCoinReclaimNum() {
        return coinReclaimNum;
    }

    /**
     * @param coinReclaimNum the coinReclaimNum to set
     */
    public void setCoinReclaimNum(int coinReclaimNum) {
        this.coinReclaimNum = coinReclaimNum;
    }

    /**
     * @return the coinReclaimFee
     */
    public int getCoinReclaimFee() {
        return coinReclaimFee;
    }

    /**
     * @param coinReclaimFee the coinReclaimFee to set
     */
    public void setCoinReclaimFee(int coinReclaimFee) {
        this.coinReclaimFee = coinReclaimFee;
    }

    /**
     * @return the noteReclaimNum
     */
    public int getNoteReclaimNum() {
        return noteReclaimNum;
    }

    /**
     * @param noteReclaimNum the noteReclaimNum to set
     */
    public void setNoteReclaimNum(int noteReclaimNum) {
        this.noteReclaimNum = noteReclaimNum;
    }

    /**
     * @return the noteReclaimFee
     */
    public int getNoteReclaimFee() {
        return noteReclaimFee;
    }

    /**
     * @param noteReclaimFee the noteReclaimFee to set
     */
    public void setNoteReclaimFee(int noteReclaimFee) {
        this.noteReclaimFee = noteReclaimFee;
    }

    /**
     * @return the detail
     */
    public Vector getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(Vector detail) {
        this.detail = detail;
    }
    
    
}
