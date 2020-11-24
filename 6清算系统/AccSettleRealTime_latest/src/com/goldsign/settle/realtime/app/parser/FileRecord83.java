/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecordReg;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileRecord83 extends FileRecordReg {
            private int overflowNum;
    private int saleSjtNum;
    private int saleSjtFee;
    private int tkExiFreeNum;
    private int tkExitChargeNum;
    private int tkExitChargeFee;

    private Vector detailSaleSvt;
    private Vector detailCharge;
    private Vector detailUpdate;
    private Vector detailReturn;
    private Vector detailReturnNon;

    /**
     * @return the overflowNum
     */
    public int getOverflowNum() {
        return overflowNum;
    }

    /**
     * @param overflowNum the overflowNum to set
     */
    public void setOverflowNum(int overflowNum) {
        this.overflowNum = overflowNum;
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
     * @return the saleSjtFee
     */
    public int getSaleSjtFee() {
        return saleSjtFee;
    }

    /**
     * @param saleSjtFee the saleSjtFee to set
     */
    public void setSaleSjtFee(int saleSjtFee) {
        this.saleSjtFee = saleSjtFee;
    }

    /**
     * @return the tkExiFreeNum
     */
    public int getTkExiFreeNum() {
        return tkExiFreeNum;
    }

    /**
     * @param tkExiFreeNum the tkExiFreeNum to set
     */
    public void setTkExiFreeNum(int tkExiFreeNum) {
        this.tkExiFreeNum = tkExiFreeNum;
    }

    /**
     * @return the tkExitChargeNum
     */
    public int getTkExitChargeNum() {
        return tkExitChargeNum;
    }

    /**
     * @param tkExitChargeNum the tkExitChargeNum to set
     */
    public void setTkExitChargeNum(int tkExitChargeNum) {
        this.tkExitChargeNum = tkExitChargeNum;
    }

    /**
     * @return the tkExitChargeFee
     */
    public int getTkExitChargeFee() {
        return tkExitChargeFee;
    }

    /**
     * @param tkExitChargeFee the tkExitChargeFee to set
     */
    public void setTkExitChargeFee(int tkExitChargeFee) {
        this.tkExitChargeFee = tkExitChargeFee;
    }

    /**
     * @return the detailSaleSvt
     */
    public Vector getDetailSaleSvt() {
        return detailSaleSvt;
    }

    /**
     * @param detailSaleSvt the detailSaleSvt to set
     */
    public void setDetailSaleSvt(Vector detailSaleSvt) {
        this.detailSaleSvt = detailSaleSvt;
    }

    /**
     * @return the detailCharge
     */
    public Vector getDetailCharge() {
        return detailCharge;
    }

    /**
     * @param detailCharge the detailCharge to set
     */
    public void setDetailCharge(Vector detailCharge) {
        this.detailCharge = detailCharge;
    }

    /**
     * @return the detailUpdate
     */
    public Vector getDetailUpdate() {
        return detailUpdate;
    }

    /**
     * @param detailUpdate the detailUpdate to set
     */
    public void setDetailUpdate(Vector detailUpdate) {
        this.detailUpdate = detailUpdate;
    }

    /**
     * @return the detailReturn
     */
    public Vector getDetailReturn() {
        return detailReturn;
    }

    /**
     * @param detailReturn the detailReturn to set
     */
    public void setDetailReturn(Vector detailReturn) {
        this.detailReturn = detailReturn;
    }

    /**
     * @return the detailReturnNon
     */
    public Vector getDetailReturnNon() {
        return detailReturnNon;
    }

    /**
     * @param detailReturnNon the detailReturnNon to set
     */
    public void setDetailReturnNon(Vector detailReturnNon) {
        this.detailReturnNon = detailReturnNon;
    }


   



}
