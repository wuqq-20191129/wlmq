/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

/**
 *
 * @author hejj
 */
public class FileRecordMobileSTL extends FileRecordSTL {

    private int chargeNum;//充值总数量
    private int chargeFee;//充值总金额
    private int saleNum;//发售总数量
    private int saleFee;//发售总金额
    private int lockNum;//锁卡总数量
    private int unlockNum;//解锁总数量
    private int saleSjtNum;//单程票购票数量
    private int saleSjtFee;//单程票购票金额
    private String paidChannelTypeCharge;//充值通道类型
    private String paidChannelCodeCharge;//充值通道类型
    private String paidChannelTypeReturn;//退款通道类型
    private String paidChannelCodeReturn;//退款通道类型
    private String paidChannelTypeSaleSjt;//二维码购票通道类型
    private String paidChannelCodeSaleSjt;//二维码购票通道类型
    
    private String paidChannelTypeSale;//发售渠道类型
    private String paidChannelCodeSale;//发售渠道代码
    private int chargeTctNum;//充次总数量
    private int chargeTctTNum;//充次总次数
    private int chargeTctFee;//充次总金额
    private String paidChannelTypeBuy;//购次渠道类型
    private String paidChannelCodeBuy;//购次渠道代码
    
    

    /**
     * @return the chargeNum
     */
    public int getChargeNum() {
        return chargeNum;
    }

    /**
     * @param chargeNum the chargeNum to set
     */
    public void setChargeNum(int chargeNum) {
        this.chargeNum = chargeNum;
    }

    /**
     * @return the chargeFee
     */
    public int getChargeFee() {
        return chargeFee;
    }

    /**
     * @param chargeFee the chargeFee to set
     */
    public void setChargeFee(int chargeFee) {
        this.chargeFee = chargeFee;
    }

    /**
     * @return the saleNum
     */
    public int getSaleNum() {
        return saleNum;
    }

    /**
     * @param saleNum the saleNum to set
     */
    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    /**
     * @return the saleFee
     */
    public int getSaleFee() {
        return saleFee;
    }

    /**
     * @param saleFee the saleFee to set
     */
    public void setSaleFee(int saleFee) {
        this.saleFee = saleFee;
    }

    /**
     * @return the lockNum
     */
    public int getLockNum() {
        return lockNum;
    }

    /**
     * @param lockNum the lockNum to set
     */
    public void setLockNum(int lockNum) {
        this.lockNum = lockNum;
    }

    /**
     * @return the unlockNum
     */
    public int getUnlockNum() {
        return unlockNum;
    }

    /**
     * @param unlockNum the unlockNum to set
     */
    public void setUnlockNum(int unlockNum) {
        this.unlockNum = unlockNum;
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
     * @return the paidChannelTypeSalse
     */
    public String getPaidChannelTypeSale() {
        return paidChannelTypeSale;
    }

    /**
     * @param paidChannelTypeSalse the paidChannelTypeSalse to set
     */
    public void setPaidChannelTypeSale(String paidChannelTypeSalse) {
        this.paidChannelTypeSale = paidChannelTypeSalse;
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
     * @return the chargeTctNum
     */
    public int getChargeTctNum() {
        return chargeTctNum;
    }

    /**
     * @param chargeTctNum the chargeTctNum to set
     */
    public void setChargeTctNum(int chargeTctNum) {
        this.chargeTctNum = chargeTctNum;
    }

    /**
     * @return the chargeTctTNum
     */
    public int getChargeTctTNum() {
        return chargeTctTNum;
    }

    /**
     * @param chargeTctTNum the chargeTctTNum to set
     */
    public void setChargeTctTNum(int chargeTctTNum) {
        this.chargeTctTNum = chargeTctTNum;
    }

    /**
     * @return the chargeTctFee
     */
    public int getChargeTctFee() {
        return chargeTctFee;
    }

    /**
     * @param chargeTctFee the chargeTctFee to set
     */
    public void setChargeTctFee(int chargeTctFee) {
        this.chargeTctFee = chargeTctFee;
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
