/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

/**
 *
 * @author hejj
 */
public class FileMobileSDFVo extends FileSDFVo {

    private int diffChargeNum;//充值总数量差异
    private int diffChargeFee;//充值总金额差异
    private int diffSaleNum;//发售总数量差异
    private int diffSaleFee;//发售总金额差异
    private int diffLockNum;//锁卡总数量差异
    private int diffUnlockNum;//解锁总数量差异
    private String squadDay;//对账日期
    private int diffSaleSjtNum;//发售单程票总数量差异
    private int diffSaleSjtFee;//发售单程票总金额差异

    private String paidChannelTypeCharge;//充值渠道类型
    private String paidChannelCodeCharge;//充值渠道代码
    private String paidChannelTypeReturn;//退款渠道类型
    private String paidChannelCodeReturn;//退款渠道代码
    private String paidChannelTypeSaleSjt;//单程票发售渠道类型
    private String paidChannelCodeSaleSjt;//单程票发售渠道代码
    
    private String paidChannelTypeSale;//发售渠道类型
    private String paidChannelCodeSale;//发售渠道代码
    private int diffChargeTctTnum;//充次总次数
    private int diffChargeTctNum;//充次总数量
    
    private int diffChargeTctFee;//充次总金额
    private String paidChannelTypeBuy;//购次渠道类型
    private String paidChannelCodeBuy;//购次渠道代码

    /**
     * @return the diffChargeNum
     */
    public int getDiffChargeNum() {
        return diffChargeNum;
    }

    /**
     * @param diffChargeNum the diffChargeNum to set
     */
    public void setDiffChargeNum(int diffChargeNum) {
        this.diffChargeNum = diffChargeNum;
    }

    /**
     * @return the diffChargeFee
     */
    public int getDiffChargeFee() {
        return diffChargeFee;
    }

    /**
     * @param diffChargeFee the diffChargeFee to set
     */
    public void setDiffChargeFee(int diffChargeFee) {
        this.diffChargeFee = diffChargeFee;
    }

    /**
     * @return the diffSaleNum
     */
    public int getDiffSaleNum() {
        return diffSaleNum;
    }

    /**
     * @param diffSaleNum the diffSaleNum to set
     */
    public void setDiffSaleNum(int diffSaleNum) {
        this.diffSaleNum = diffSaleNum;
    }

    /**
     * @return the diffSaleFee
     */
    public int getDiffSaleFee() {
        return diffSaleFee;
    }

    /**
     * @param diffSaleFee the diffSaleFee to set
     */
    public void setDiffSaleFee(int diffSaleFee) {
        this.diffSaleFee = diffSaleFee;
    }

    /**
     * @return the diffLockNum
     */
    public int getDiffLockNum() {
        return diffLockNum;
    }

    /**
     * @param diffLockNum the diffLockNum to set
     */
    public void setDiffLockNum(int diffLockNum) {
        this.diffLockNum = diffLockNum;
    }

    /**
     * @return the diffUnlockNum
     */
    public int getDiffUnlockNum() {
        return diffUnlockNum;
    }

    /**
     * @param diffUnlockNum the diffUnlockNum to set
     */
    public void setDiffUnlockNum(int diffUnlockNum) {
        this.diffUnlockNum = diffUnlockNum;
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
     * @return the diffSaleSjtNum
     */
    public int getDiffSaleSjtNum() {
        return diffSaleSjtNum;
    }

    /**
     * @param diffSaleSjtNum the diffSaleSjtNum to set
     */
    public void setDiffSaleSjtNum(int diffSaleSjtNum) {
        this.diffSaleSjtNum = diffSaleSjtNum;
    }

    /**
     * @return the diffSaleSjtFee
     */
    public int getDiffSaleSjtFee() {
        return diffSaleSjtFee;
    }

    /**
     * @param diffSaleSjtFee the diffSaleSjtFee to set
     */
    public void setDiffSaleSjtFee(int diffSaleSjtFee) {
        this.diffSaleSjtFee = diffSaleSjtFee;
    }

    /**
     * @return the paidChannelTypeCharge
     */
    public String getPaidChannelTypeCharge() {
        return paidChannelTypeCharge;
    }

    /**
     * @param paidChannelTypeCharge the paidChannelTypeCharge to set
     */
    public void setPaidChannelTypeCharge(String paidChannelTypeCharge) {
        this.paidChannelTypeCharge = paidChannelTypeCharge;
    }

    /**
     * @return the paidChannelCodeCharge
     */
    public String getPaidChannelCodeCharge() {
        return paidChannelCodeCharge;
    }

    /**
     * @param paidChannelCodeCharge the paidChannelCodeCharge to set
     */
    public void setPaidChannelCodeCharge(String paidChannelCodeCharge) {
        this.paidChannelCodeCharge = paidChannelCodeCharge;
    }

    /**
     * @return the paidChannelTypeReturn
     */
    public String getPaidChannelTypeReturn() {
        return paidChannelTypeReturn;
    }

    /**
     * @param paidChannelTypeReturn the paidChannelTypeReturn to set
     */
    public void setPaidChannelTypeReturn(String paidChannelTypeReturn) {
        this.paidChannelTypeReturn = paidChannelTypeReturn;
    }

    /**
     * @return the paidChannelCodeReturn
     */
    public String getPaidChannelCodeReturn() {
        return paidChannelCodeReturn;
    }

    /**
     * @param paidChannelCodeReturn the paidChannelCodeReturn to set
     */
    public void setPaidChannelCodeReturn(String paidChannelCodeReturn) {
        this.paidChannelCodeReturn = paidChannelCodeReturn;
    }

    /**
     * @return the paidChannelTypeSaleSjt
     */
    public String getPaidChannelTypeSaleSjt() {
        return paidChannelTypeSaleSjt;
    }

    /**
     * @param paidChannelTypeSaleSjt the paidChannelTypeSaleSjt to set
     */
    public void setPaidChannelTypeSaleSjt(String paidChannelTypeSaleSjt) {
        this.paidChannelTypeSaleSjt = paidChannelTypeSaleSjt;
    }

    /**
     * @return the paidChannelCodeSaleSjt
     */
    public String getPaidChannelCodeSaleSjt() {
        return paidChannelCodeSaleSjt;
    }

    /**
     * @param paidChannelCodeSaleSjt the paidChannelCodeSaleSjt to set
     */
    public void setPaidChannelCodeSaleSjt(String paidChannelCodeSaleSjt) {
        this.paidChannelCodeSaleSjt = paidChannelCodeSaleSjt;
    }

    /**
     * @return the paidChannelTypeSale
     */
    public String getPaidChannelTypeSale() {
        return paidChannelTypeSale;
    }

    /**
     * @param paidChannelTypeSale the paidChannelTypeSale to set
     */
    public void setPaidChannelTypeSale(String paidChannelTypeSale) {
        this.paidChannelTypeSale = paidChannelTypeSale;
    }

    /**
     * @return the paidChannelCodeSale
     */
    public String getPaidChannelCodeSale() {
        return paidChannelCodeSale;
    }

    /**
     * @param paidChannelCodeSale the paidChannelCodeSale to set
     */
    public void setPaidChannelCodeSale(String paidChannelCodeSale) {
        this.paidChannelCodeSale = paidChannelCodeSale;
    }

    /**
     * @return the diffChargeTctTnum
     */
    public int getDiffChargeTctTnum() {
        return diffChargeTctTnum;
    }

    /**
     * @param diffChargeTctTnum the diffChargeTctTnum to set
     */
    public void setDiffChargeTctTnum(int diffChargeTctTnum) {
        this.diffChargeTctTnum = diffChargeTctTnum;
    }

    /**
     * @return the diffChargeTctNum
     */
    public int getDiffChargeTctNum() {
        return diffChargeTctNum;
    }

    /**
     * @param diffChargeTctNum the diffChargeTctNum to set
     */
    public void setDiffChargeTctNum(int diffChargeTctNum) {
        this.diffChargeTctNum = diffChargeTctNum;
    }

    /**
     * @return the diffChargeTctFee
     */
    public int getDiffChargeTctFee() {
        return diffChargeTctFee;
    }

    /**
     * @param diffChargeTctFee the diffChargeTctFee to set
     */
    public void setDiffChargeTctFee(int diffChargeTctFee) {
        this.diffChargeTctFee = diffChargeTctFee;
    }

    /**
     * @return the paidChannelTypeBuy
     */
    public String getPaidChannelTypeBuy() {
        return paidChannelTypeBuy;
    }

    /**
     * @param paidChannelTypeBuy the paidChannelTypeBuy to set
     */
    public void setPaidChannelTypeBuy(String paidChannelTypeBuy) {
        this.paidChannelTypeBuy = paidChannelTypeBuy;
    }

    /**
     * @return the paidChannelCodeBuy
     */
    public String getPaidChannelCodeBuy() {
        return paidChannelCodeBuy;
    }

    /**
     * @param paidChannelCodeBuy the paidChannelCodeBuy to set
     */
    public void setPaidChannelCodeBuy(String paidChannelCodeBuy) {
        this.paidChannelCodeBuy = paidChannelCodeBuy;
    }
}
