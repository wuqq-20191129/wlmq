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
public class SettleQueueVo {
    private String balanceWaterNo;
    private int balanceWaterNoSub;
    private String finishFlag;
    private String isFinal;
    private String errorCode;
    private String beginTime;
    private String endTime;
    private String remark;
    public SettleQueueVo(){
        
    }
    public SettleQueueVo(String balanceWaterNo,int balanceWaterNoSub,String isFinal){
        this.balanceWaterNo = balanceWaterNo;
        this.balanceWaterNoSub =balanceWaterNoSub;
        this.isFinal=isFinal;
        
    }

    /**
     * @return the balanceWaterNo
     */
    public String getBalanceWaterNo() {
        return balanceWaterNo;
    }

    /**
     * @param balanceWaterNo the balanceWaterNo to set
     */
    public void setBalanceWaterNo(String balanceWaterNo) {
        this.balanceWaterNo = balanceWaterNo;
    }

    /**
     * @return the balanceWaterNoSub
     */
    public int getBalanceWaterNoSub() {
        return balanceWaterNoSub;
    }

    /**
     * @param balanceWaterNoSub the balanceWaterNoSub to set
     */
    public void setBalanceWaterNoSub(int balanceWaterNoSub) {
        this.balanceWaterNoSub = balanceWaterNoSub;
    }

    /**
     * @return the finishFlag
     */
    public String getFinishFlag() {
        return finishFlag;
    }

    /**
     * @param finishFlag the finishFlag to set
     */
    public void setFinishFlag(String finishFlag) {
        this.finishFlag = finishFlag;
    }

    /**
     * @return the isFinal
     */
    public String getIsFinal() {
        return isFinal;
    }

    /**
     * @param isFinal the isFinal to set
     */
    public void setIsFinal(String isFinal) {
        this.isFinal = isFinal;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the beginTime
     */
    public String getBeginTime() {
        return beginTime;
    }

    /**
     * @param beginTime the beginTime to set
     */
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }



}
