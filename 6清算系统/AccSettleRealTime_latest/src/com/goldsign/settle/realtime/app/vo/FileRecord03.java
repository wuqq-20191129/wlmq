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
public class FileRecord03 extends FileRecordBase{

    /**
     * @return the putDatetime_s
     */
    public String getPutDatetime_s() {
        return putDatetime_s;
    }

    /**
     * @param putDatetime_s the putDatetime_s to set
     */
    public void setPutDatetime_s(String putDatetime_s) {
        this.putDatetime_s = putDatetime_s;
    }
    private int hopperid;//硬币Hopper
    private String putDatetime;//存入时间
    private String putDatetime_s;//存入时间
    private int waterNoOp;//流水号
    private int coinUnitFee;//硬币单位
    private int coinNum;//硬币数量
    private int coinFeeTotal;//硬币金额

    /**
     * @return the hopperid
     */
    public int getHopperid() {
        return hopperid;
    }

    /**
     * @param hopperid the hopperid to set
     */
    public void setHopperid(int hopperid) {
        this.hopperid = hopperid;
    }

    /**
     * @return the putDatetime
     */
    public String getPutDatetime() {
        return putDatetime;
    }

    /**
     * @param putDatetime the putDatetime to set
     */
    public void setPutDatetime(String putDatetime) {
        this.putDatetime = putDatetime;
    }

    /**
     * @return the waternoOp
     */
    public int getWaterNoOp() {
        return waterNoOp;
    }

    /**
     * @param waternoOp the waternoOp to set
     */
    public void setWaterNoOp(int waterNoOp) {
        this.waterNoOp = waterNoOp;
    }

    /**
     * @return the coinUnitFee
     */
    public int getCoinUnitFee() {
        return coinUnitFee;
    }

    /**
     * @param coinUnitFee the coinUnitFee to set
     */
    public void setCoinUnitFee(int coinUnitFee) {
        this.coinUnitFee = coinUnitFee;
    }

    /**
     * @return the coinNum
     */
    public int getCoinNum() {
        return coinNum;
    }

    /**
     * @param coinNum the coinNum to set
     */
    public void setCoinNum(int coinNum) {
        this.coinNum = coinNum;
    }

    /**
     * @return the coinFeeTotal
     */
    public int getCoinFeeTotal() {
        return coinFeeTotal;
    }

    /**
     * @param coinFeeTotal the coinFeeTotal to set
     */
    public void setCoinFeeTotal(int coinFeeTotal) {
        this.coinFeeTotal = coinFeeTotal;
    }
   
    
}
