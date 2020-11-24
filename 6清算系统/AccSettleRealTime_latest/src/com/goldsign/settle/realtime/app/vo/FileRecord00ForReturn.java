/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

/**
 *
 * @author hejj
 */
public class FileRecord00ForReturn extends FileRecord00DetailBase{
    private int returnBalanceFee;//即时退款余额
    private int returnDepositFee;//即时退款押金
    private String returnType;//退款类型
    private String isBroken;//是否折损
    private int penaltyFee;//退款罚金
    private String penaltyReason;//罚款原因

    /**
     * @return the returnBalanceFee
     */
    public int getReturnBalanceFee() {
        return returnBalanceFee;
    }

    /**
     * @param returnBalanceFee the returnBalanceFee to set
     */
    public void setReturnBalanceFee(int returnBalanceFee) {
        this.returnBalanceFee = returnBalanceFee;
    }

    /**
     * @return the returnDepositFee
     */
    public int getReturnDepositFee() {
        return returnDepositFee;
    }

    /**
     * @param returnDepositFee the returnDepositFee to set
     */
    public void setReturnDepositFee(int returnDepositFee) {
        this.returnDepositFee = returnDepositFee;
    }

    /**
     * @return the returnType
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * @param returnType the returnType to set
     */
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    /**
     * @return the isBroken
     */
    public String getIsBroken() {
        return isBroken;
    }

    /**
     * @param isBroken the isBroken to set
     */
    public void setIsBroken(String isBroken) {
        this.isBroken = isBroken;
    }

    /**
     * @return the penaltyFee
     */
    public int getPenaltyFee() {
        return penaltyFee;
    }

    /**
     * @param penaltyFee the penaltyFee to set
     */
    public void setPenaltyFee(int penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    /**
     * @return the penaltyReason
     */
    public String getPenaltyReason() {
        return penaltyReason;
    }

    /**
     * @param penaltyReason the penaltyReason to set
     */
    public void setPenaltyReason(String penaltyReason) {
        this.penaltyReason = penaltyReason;
    }

   
    
}
