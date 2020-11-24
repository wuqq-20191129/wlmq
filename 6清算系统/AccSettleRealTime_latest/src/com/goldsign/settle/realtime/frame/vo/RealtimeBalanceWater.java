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
public class RealtimeBalanceWater {

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
    private String balanceWaterNo;
    private int balanceWaterNoSub;
    public RealtimeBalanceWater(String balanceWaterNo,int balanceWaterNoSub){
        this.balanceWaterNo=balanceWaterNo;
        this.balanceWaterNoSub=balanceWaterNoSub;
    }
    public RealtimeBalanceWater(){
        
    }
    
}
