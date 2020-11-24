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
public class FileRecord04 extends FileRecordTVMCoinBase{
    private String coinBoxId;//硬币箱编号
    private String clearDatetime;//清空时间
    private int waterNoOp;//流水号
    private int hopper1UnitFee;//硬币Hopper 1单位
    private int hopper1UnitNum;//硬币Hopper 1数量
    private int hopper2UnitFee;//硬币Hopper 2单位
    private int hopper2UnitNum;//硬币Hopper 2数量

    /**
     * @return the coinBoxId
     */
    public String getCoinBoxId() {
        return coinBoxId;
    }

    /**
     * @param coinBoxId the coinBoxId to set
     */
    public void setCoinBoxId(String coinBoxId) {
        this.coinBoxId = coinBoxId;
    }

    /**
     * @return the clearDatetime
     */
    public String getClearDatetime() {
        return clearDatetime;
    }

    /**
     * @param clearDatetime the clearDatetime to set
     */
    public void setClearDatetime(String clearDatetime) {
        this.clearDatetime = clearDatetime;
    }

    /**
     * @return the waterNoOp
     */
    public int getWaterNoOp() {
        return waterNoOp;
    }

    /**
     * @param waterNoOp the waterNoOp to set
     */
    public void setWaterNoOp(int waterNoOp) {
        this.waterNoOp = waterNoOp;
    }

    /**
     * @return the hopper1UnitFee
     */
    public int getHopper1UnitFee() {
        return hopper1UnitFee;
    }

    /**
     * @param hopper1UnitFee the hopper1UnitFee to set
     */
    public void setHopper1UnitFee(int hopper1UnitFee) {
        this.hopper1UnitFee = hopper1UnitFee;
    }

    /**
     * @return the hopper1UnitNum
     */
    public int getHopper1UnitNum() {
        return hopper1UnitNum;
    }

    /**
     * @param hopper1UnitNum the hopper1UnitNum to set
     */
    public void setHopper1UnitNum(int hopper1UnitNum) {
        this.hopper1UnitNum = hopper1UnitNum;
    }

    /**
     * @return the hopper2UnitFee
     */
    public int getHopper2UnitFee() {
        return hopper2UnitFee;
    }

    /**
     * @param hopper2UnitFee the hopper2UnitFee to set
     */
    public void setHopper2UnitFee(int hopper2UnitFee) {
        this.hopper2UnitFee = hopper2UnitFee;
    }

    /**
     * @return the hopper2UnitNum
     */
    public int getHopper2UnitNum() {
        return hopper2UnitNum;
    }

    /**
     * @param hopper2UnitNum the hopper2UnitNum to set
     */
    public void setHopper2UnitNum(int hopper2UnitNum) {
        this.hopper2UnitNum = hopper2UnitNum;
    }
    
    
}
