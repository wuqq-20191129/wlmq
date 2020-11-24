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
public class FileRecordSTL extends FileRecordBase {

    /**
     * @return the bomSaleTctNum
     */
    public int getBomSaleTctNum() {
        return bomSaleTctNum;
    }

    /**
     * @param bomSaleTctNum the bomSaleTctNum to set
     */
    public void setBomSaleTctNum(int bomSaleTctNum) {
        this.bomSaleTctNum = bomSaleTctNum;
    }

    /**
     * @return the bomSaleTctFee
     */
    public int getBomSaleTctFee() {
        return bomSaleTctFee;
    }

    /**
     * @param bomSaleTctFee the bomSaleTctFee to set
     */
    public void setBomSaleTctFee(int bomSaleTctFee) {
        this.bomSaleTctFee = bomSaleTctFee;
    }

    /**
     * @return the bomSaleTctNum_s
     */
    public String getBomSaleTctNum_s() {
        return bomSaleTctNum_s;
    }

    /**
     * @param bomSaleTctNum_s the bomSaleTctNum_s to set
     */
    public void setBomSaleTctNum_s(String bomSaleTctNum_s) {
        this.bomSaleTctNum_s = bomSaleTctNum_s;
    }

    /**
     * @return the bomSaleTctFee_s
     */
    public String getBomSaleTctFee_s() {
        return bomSaleTctFee_s;
    }

    /**
     * @param bomSaleTctFee_s the bomSaleTctFee_s to set
     */
    public void setBomSaleTctFee_s(String bomSaleTctFee_s) {
        this.bomSaleTctFee_s = bomSaleTctFee_s;
    }


    private int bomSaleSjtNum;//BOM单程票发售总数量
    private int bomSaleSjtFee;//BOM单程票发售总金额
    private int tvmSaleSjtNum;//TVM单程票发售总数量
    private int tvmSaleSjtFee;//TVM单程票发售总金额
    private int itmSaleSjtNum;//ITM单程票发售总数量
    private int itmSaleSjtFee;//ITM单程票发售总金额
    private int bomSaleNum;////BOM储值类票卡发售总数量
    private int bomSaleDepositFee;//BOM储值类票卡押金总金额
    private int bomChargeFee;//BOM充值总金额
    private int tvmChargeNum;//TVM充值总数量
    private int tvmChargeFee;//TVM充值总金额
    private int itmChargeNum;//ITM充值总数量
    private int itmChargeFee;//ITM充值总金额
    private int returnNum;//即时退款总数量
    private int returnFee;//即时退款总金额
    private int nonRetNum;//非即时退款总数量
    private int nonRetDepositFee;//非即时退款总押金
    private int nonRetActualBala;//非即时退款总退还余额
    private int negativeChargeNum;//冲正总数量
    private int negativeChargeFee;//冲正总金额
    private int dealNum;//出闸扣费总数量
    private int dealFee;//出闸扣费总金额
    private int updateCashNum;//现金更新总数量
    private int updateCashFee;//现金更新总金额
    private int updateNonCashNum;//非现金更新总数量
    private int updateNonCashFee;//非现金更新总金额
    private int adminNum;//行政处理总数量
    private int adminReturnFee;//行政处理总支出金额
    private int adminPenaltyFee;//行政处理总收取金额

    private String bomSaleSjtNum_s;//BOM单程票发售总数量
    private String bomSaleSjtFee_s;//BOM单程票发售总金额
    private String tvmSaleSjtNum_s;//TVM单程票发售总数量
    private String tvmSaleSjtFee_s;//TVM单程票发售总金额
    private String bomSaleNum_s;////BOM储值类票卡发售总数量
    private String bomSaleDepositFee_s;//BOM储值类票卡押金总金额
    private String bomChargeFee_s;//BOM充值总金额
    private String tvmChargeNum_s;//TVM充值总数量
    private String tvmChargeFee_s;//TVM充值总金额
    private String returnNum_s;//即时退款总数量
    private String returnFee_s;//即时退款总金额
    private String nonRetNum_s;//非即时退款总数量
    private String nonRetDepositFee_s;//非即时退款总押金
    private String nonRetActualBala_s;//非即时退款总退还余额
    private String negativeChargeNum_s;//冲正总数量
    private String negativeChargeFee_s;//冲正总金额
    private String dealNum_s;//出闸扣费总数量
    private String dealFee_s;//出闸扣费总金额
    private String updateCashNum_s;//现金更新总数量
    private String updateCashFee_s;//现金更新总金额
    private String updateNonCashNum_s;//非现金更新总数量
    private String updateNonCashFee_s;//非现金更新总金额
    private String adminNum_s;//行政处理总数量
    private String adminReturnFee_s;//行政处理总支出金额
    private String adminPenaltyFee_s;//行政处理总收取金额

    private String itmSaleSjtNum_s;//ITM单程票发售总数量
    private String itmSaleSjtFee_s;//ITM单程票发售总金额
    private String itmChargeNum_s;//ITM充值总数量
    private String itmChargeFee_s;//ITM充值总金额

    private int qrDealNum;//二维码钱包交易总数量  
    private int electTkTctDealNum;//电子票钱包交易总次数（计次）  
    private int electTkDealNum;//电子票钱包交易总数量（计值）  
    private int electTkDealFee;//电子票钱包交易总金额（计值
    private String recordVer;
    private String qrDealNum_s;//二维码钱包交易总数量  
    private String electTkTctDealNum_s;//电子票钱包交易总次数（计次）  
    private String electTkDealNum_s;//电子票钱包交易总数量（计值）  
    private String electTkDealFee_s;//电子票钱包交易总金额（计值
    
    private int qrEntryNum;//二维码进站总数量  
    private int updateNonCashTctNum;//非现金更新总数量（计次票） 
    private String updateNonCashTctNum_s;//非现金更新总数量（计次票）
    
    private int bomSaleTctNum;//BOM次票发售总数量
    private int bomSaleTctFee;//BOM次票发售总金额
    
    private String bomSaleTctNum_s;//BOM次票发售总数量
    private String bomSaleTctFee_s;//BOM次票发售总金额

    
    
    
        /**
     * @return the qrEntryNum
     */
    public int getQrEntryNum() {
        return qrEntryNum;
    }

    /**
     * @param qrEntryNum the qrEntryNum to set
     */
    public void setQrEntryNum(int qrEntryNum) {
        this.qrEntryNum = qrEntryNum;
    }

    /**
     * @return the qrDealNum_s
     */
    public String getQrDealNum_s() {
        return qrDealNum_s;
    }

    /**
     * @param qrDealNum_s the qrDealNum_s to set
     */
    public void setQrDealNum_s(String qrDealNum_s) {
        this.qrDealNum_s = qrDealNum_s;
    }

    /**
     * @return the electTkTctDealNum_s
     */
    public String getElectTkTctDealNum_s() {
        return electTkTctDealNum_s;
    }

    /**
     * @param electTkTctDealNum_s the electTkTctDealNum_s to set
     */
    public void setElectTkTctDealNum_s(String electTkTctDealNum_s) {
        this.electTkTctDealNum_s = electTkTctDealNum_s;
    }

    /**
     * @return the electTkDealNum_s
     */
    public String getElectTkDealNum_s() {
        return electTkDealNum_s;
    }

    /**
     * @param electTkDealNum_s the electTkDealNum_s to set
     */
    public void setElectTkDealNum_s(String electTkDealNum_s) {
        this.electTkDealNum_s = electTkDealNum_s;
    }

    /**
     * @return the electTkDealFee_s
     */
    public String getElectTkDealFee_s() {
        return electTkDealFee_s;
    }

    /**
     * @param electTkDealFee_s the electTkDealFee_s to set
     */
    public void setElectTkDealFee_s(String electTkDealFee_s) {
        this.electTkDealFee_s = electTkDealFee_s;
    }

    

    /**
     * @return the qrDealNum
     */
    public int getQrDealNum() {
        return qrDealNum;
    }

    /**
     * @param qrDealNum the qrDealNum to set
     */
    public void setQrDealNum(int qrDealNum) {
        this.qrDealNum = qrDealNum;
    }

    /**
     * @return the electTkTctDealNum
     */
    public int getElectTkTctDealNum() {
        return electTkTctDealNum;
    }

    /**
     * @param electTkTctDealNum the electTkTctDealNum to set
     */
    public void setElectTkTctDealNum(int electTkTctDealNum) {
        this.electTkTctDealNum = electTkTctDealNum;
    }

    /**
     * @return the electTkDealNum
     */
    public int getElectTkDealNum() {
        return electTkDealNum;
    }

    /**
     * @param electTkDealNum the electTkDealNum to set
     */
    public void setElectTkDealNum(int electTkDealNum) {
        this.electTkDealNum = electTkDealNum;
    }

    /**
     * @return the electTkDealFee
     */
    public int getElectTkDealFee() {
        return electTkDealFee;
    }

    /**
     * @param electTkDealFee the electTkDealFee to set
     */
    public void setElectTkDealFee(int electTkDealFee) {
        this.electTkDealFee = electTkDealFee;
    }

    /**
     * @return the recordVer
     */
    public String getRecordVer() {
        return recordVer;
    }

    /**
     * @param recordVer the recordVer to set
     */
    public void setRecordVer(String recordVer) {
        this.recordVer = recordVer;
    }

    /**
     * @return the itmChargeNum
     */
    public int getItmChargeNum() {
        return itmChargeNum;
    }

    /**
     * @param itmChargeNum the itmChargeNum to set
     */
    public void setItmChargeNum(int itmChargeNum) {
        this.itmChargeNum = itmChargeNum;
    }

    /**
     * @return the itmChargeFee
     */
    public int getItmChargeFee() {
        return itmChargeFee;
    }

    /**
     * @param itmChargeFee the itmChargeFee to set
     */
    public void setItmChargeFee(int itmChargeFee) {
        this.itmChargeFee = itmChargeFee;
    }

    /**
     * @return the bomSaleSjtNum_s
     */
    public String getBomSaleSjtNum_s() {
        return bomSaleSjtNum_s;
    }

    /**
     * @param bomSaleSjtNum_s the bomSaleSjtNum_s to set
     */
    public void setBomSaleSjtNum_s(String bomSaleSjtNum_s) {
        this.bomSaleSjtNum_s = bomSaleSjtNum_s;
    }

    /**
     * @return the bomSaleSjtFee_s
     */
    public String getBomSaleSjtFee_s() {
        return bomSaleSjtFee_s;
    }

    /**
     * @param bomSaleSjtFee_s the bomSaleSjtFee_s to set
     */
    public void setBomSaleSjtFee_s(String bomSaleSjtFee_s) {
        this.bomSaleSjtFee_s = bomSaleSjtFee_s;
    }

    /**
     * @return the tvmSaleSjtNum_s
     */
    public String getTvmSaleSjtNum_s() {
        return tvmSaleSjtNum_s;
    }

    /**
     * @param tvmSaleSjtNum_s the tvmSaleSjtNum_s to set
     */
    public void setTvmSaleSjtNum_s(String tvmSaleSjtNum_s) {
        this.tvmSaleSjtNum_s = tvmSaleSjtNum_s;
    }

    /**
     * @return the tvmSaleSjtFee_s
     */
    public String getTvmSaleSjtFee_s() {
        return tvmSaleSjtFee_s;
    }

    /**
     * @param tvmSaleSjtFee_s the tvmSaleSjtFee_s to set
     */
    public void setTvmSaleSjtFee_s(String tvmSaleSjtFee_s) {
        this.tvmSaleSjtFee_s = tvmSaleSjtFee_s;
    }

    /**
     * @return the bomSaleNum_s
     */
    public String getBomSaleNum_s() {
        return bomSaleNum_s;
    }

    /**
     * @param bomSaleNum_s the bomSaleNum_s to set
     */
    public void setBomSaleNum_s(String bomSaleNum_s) {
        this.bomSaleNum_s = bomSaleNum_s;
    }

    /**
     * @return the bomSaleDepositFee_s
     */
    public String getBomSaleDepositFee_s() {
        return bomSaleDepositFee_s;
    }

    /**
     * @param bomSaleDepositFee_s the bomSaleDepositFee_s to set
     */
    public void setBomSaleDepositFee_s(String bomSaleDepositFee_s) {
        this.bomSaleDepositFee_s = bomSaleDepositFee_s;
    }

    /**
     * @return the bomChargeFee_s
     */
    public String getBomChargeFee_s() {
        return bomChargeFee_s;
    }

    /**
     * @param bomChargeFee_s the bomChargeFee_s to set
     */
    public void setBomChargeFee_s(String bomChargeFee_s) {
        this.bomChargeFee_s = bomChargeFee_s;
    }

    /**
     * @return the tvmChargeNum_s
     */
    public String getTvmChargeNum_s() {
        return tvmChargeNum_s;
    }

    /**
     * @param tvmChargeNum_s the tvmChargeNum_s to set
     */
    public void setTvmChargeNum_s(String tvmChargeNum_s) {
        this.tvmChargeNum_s = tvmChargeNum_s;
    }

    /**
     * @return the tvmChargeFee_s
     */
    public String getTvmChargeFee_s() {
        return tvmChargeFee_s;
    }

    /**
     * @param tvmChargeFee_s the tvmChargeFee_s to set
     */
    public void setTvmChargeFee_s(String tvmChargeFee_s) {
        this.tvmChargeFee_s = tvmChargeFee_s;
    }

    /**
     * @return the returnNum_s
     */
    public String getReturnNum_s() {
        return returnNum_s;
    }

    /**
     * @param returnNum_s the returnNum_s to set
     */
    public void setReturnNum_s(String returnNum_s) {
        this.returnNum_s = returnNum_s;
    }

    /**
     * @return the returnFee_s
     */
    public String getReturnFee_s() {
        return returnFee_s;
    }

    /**
     * @param returnFee_s the returnFee_s to set
     */
    public void setReturnFee_s(String returnFee_s) {
        this.returnFee_s = returnFee_s;
    }

    /**
     * @return the nonRetNum_s
     */
    public String getNonRetNum_s() {
        return nonRetNum_s;
    }

    /**
     * @param nonRetNum_s the nonRetNum_s to set
     */
    public void setNonRetNum_s(String nonRetNum_s) {
        this.nonRetNum_s = nonRetNum_s;
    }

    /**
     * @return the nonRetDepositFee_s
     */
    public String getNonRetDepositFee_s() {
        return nonRetDepositFee_s;
    }

    /**
     * @param nonRetDepositFee_s the nonRetDepositFee_s to set
     */
    public void setNonRetDepositFee_s(String nonRetDepositFee_s) {
        this.nonRetDepositFee_s = nonRetDepositFee_s;
    }

    /**
     * @return the nonRetActualBala_s
     */
    public String getNonRetActualBala_s() {
        return nonRetActualBala_s;
    }

    /**
     * @param nonRetActualBala_s the nonRetActualBala_s to set
     */
    public void setNonRetActualBala_s(String nonRetActualBala_s) {
        this.nonRetActualBala_s = nonRetActualBala_s;
    }

    /**
     * @return the negativeChargeNum_s
     */
    public String getNegativeChargeNum_s() {
        return negativeChargeNum_s;
    }

    /**
     * @param negativeChargeNum_s the negativeChargeNum_s to set
     */
    public void setNegativeChargeNum_s(String negativeChargeNum_s) {
        this.negativeChargeNum_s = negativeChargeNum_s;
    }

    /**
     * @return the negativeChargeFee_s
     */
    public String getNegativeChargeFee_s() {
        return negativeChargeFee_s;
    }

    /**
     * @param negativeChargeFee_s the negativeChargeFee_s to set
     */
    public void setNegativeChargeFee_s(String negativeChargeFee_s) {
        this.negativeChargeFee_s = negativeChargeFee_s;
    }

    /**
     * @return the dealNum_s
     */
    public String getDealNum_s() {
        return dealNum_s;
    }

    /**
     * @param dealNum_s the dealNum_s to set
     */
    public void setDealNum_s(String dealNum_s) {
        this.dealNum_s = dealNum_s;
    }

    /**
     * @return the dealFee_s
     */
    public String getDealFee_s() {
        return dealFee_s;
    }

    /**
     * @param dealFee_s the dealFee_s to set
     */
    public void setDealFee_s(String dealFee_s) {
        this.dealFee_s = dealFee_s;
    }

    /**
     * @return the updateCashNum_s
     */
    public String getUpdateCashNum_s() {
        return updateCashNum_s;
    }

    /**
     * @param updateCashNum_s the updateCashNum_s to set
     */
    public void setUpdateCashNum_s(String updateCashNum_s) {
        this.updateCashNum_s = updateCashNum_s;
    }

    /**
     * @return the updateCashFee_s
     */
    public String getUpdateCashFee_s() {
        return updateCashFee_s;
    }

    /**
     * @param updateCashFee_s the updateCashFee_s to set
     */
    public void setUpdateCashFee_s(String updateCashFee_s) {
        this.updateCashFee_s = updateCashFee_s;
    }

    /**
     * @return the updateNonCashNum_s
     */
    public String getUpdateNonCashNum_s() {
        return updateNonCashNum_s;
    }

    /**
     * @param updateNonCashNum_s the updateNonCashNum_s to set
     */
    public void setUpdateNonCashNum_s(String updateNonCashNum_s) {
        this.updateNonCashNum_s = updateNonCashNum_s;
    }

    /**
     * @return the updateNonCashFee_s
     */
    public String getUpdateNonCashFee_s() {
        return updateNonCashFee_s;
    }

    /**
     * @param updateNonCashFee_s the updateNonCashFee_s to set
     */
    public void setUpdateNonCashFee_s(String updateNonCashFee_s) {
        this.updateNonCashFee_s = updateNonCashFee_s;
    }

    /**
     * @return the adminNum_s
     */
    public String getAdminNum_s() {
        return adminNum_s;
    }

    /**
     * @param adminNum_s the adminNum_s to set
     */
    public void setAdminNum_s(String adminNum_s) {
        this.adminNum_s = adminNum_s;
    }

    /**
     * @return the adminReturnFee_s
     */
    public String getAdminReturnFee_s() {
        return adminReturnFee_s;
    }

    /**
     * @param adminReturnFee_s the adminReturnFee_s to set
     */
    public void setAdminReturnFee_s(String adminReturnFee_s) {
        this.adminReturnFee_s = adminReturnFee_s;
    }

    /**
     * @return the adminPenaltyFee_s
     */
    public String getAdminPenaltyFee_s() {
        return adminPenaltyFee_s;
    }

    /**
     * @param adminPenaltyFee_s the adminPenaltyFee_s to set
     */
    public void setAdminPenaltyFee_s(String adminPenaltyFee_s) {
        this.adminPenaltyFee_s = adminPenaltyFee_s;
    }

    //private String lineId;//线路车站代码
    //private String stationId;//
    /**
     * @return the bomSaleSjtNum
     */
    public int getBomSaleSjtNum() {
        return bomSaleSjtNum;
    }

    /**
     * @param bomSaleSjtNum the bomSaleSjtNum to set
     */
    public void setBomSaleSjtNum(int bomSaleSjtNum) {
        this.bomSaleSjtNum = bomSaleSjtNum;
    }

    /**
     * @return the bomSaleSjtFee
     */
    public int getBomSaleSjtFee() {
        return bomSaleSjtFee;
    }

    /**
     * @param bomSaleSjtFee the bomSaleSjtFee to set
     */
    public void setBomSaleSjtFee(int bomSaleSjtFee) {
        this.bomSaleSjtFee = bomSaleSjtFee;
    }

    /**
     * @return the tvmSaleSjtNum
     */
    public int getTvmSaleSjtNum() {
        return tvmSaleSjtNum;
    }

    /**
     * @param tvmSaleSjtNum the tvmSaleSjtNum to set
     */
    public void setTvmSaleSjtNum(int tvmSaleSjtNum) {
        this.tvmSaleSjtNum = tvmSaleSjtNum;
    }

    /**
     * @return the tvmSaleSjtFee
     */
    public int getTvmSaleSjtFee() {
        return tvmSaleSjtFee;
    }

    /**
     * @param tvmSaleSjtFee the tvmSaleSjtFee to set
     */
    public void setTvmSaleSjtFee(int tvmSaleSjtFee) {
        this.tvmSaleSjtFee = tvmSaleSjtFee;
    }

    /**
     * @return the bomSaleNum
     */
    public int getBomSaleNum() {
        return bomSaleNum;
    }

    /**
     * @param bomSaleNum the bomSaleNum to set
     */
    public void setBomSaleNum(int bomSaleNum) {
        this.bomSaleNum = bomSaleNum;
    }

    /**
     * @return the bomSaleDepositFee
     */
    public int getBomSaleDepositFee() {
        return bomSaleDepositFee;
    }

    /**
     * @param bomSaleDepositFee the bomSaleDepositFee to set
     */
    public void setBomSaleDepositFee(int bomSaleDepositFee) {
        this.bomSaleDepositFee = bomSaleDepositFee;
    }

    /**
     * @return the bomChargeFee
     */
    public int getBomChargeFee() {
        return bomChargeFee;
    }

    /**
     * @param bomChargeFee the bomChargeFee to set
     */
    public void setBomChargeFee(int bomChargeFee) {
        this.bomChargeFee = bomChargeFee;
    }

    /**
     * @return the tvmChargeNum
     */
    public int getTvmChargeNum() {
        return tvmChargeNum;
    }

    /**
     * @param tvmChargeNum the tvmChargeNum to set
     */
    public void setTvmChargeNum(int tvmChargeNum) {
        this.tvmChargeNum = tvmChargeNum;
    }

    /**
     * @return the tvmChargeFee
     */
    public int getTvmChargeFee() {
        return tvmChargeFee;
    }

    /**
     * @param tvmChargeFee the tvmChargeFee to set
     */
    public void setTvmChargeFee(int tvmChargeFee) {
        this.tvmChargeFee = tvmChargeFee;
    }

    /**
     * @return the returnNum
     */
    public int getReturnNum() {
        return returnNum;
    }

    /**
     * @param returnNum the returnNum to set
     */
    public void setReturnNum(int returnNum) {
        this.returnNum = returnNum;
    }

    /**
     * @return the returnFee
     */
    public int getReturnFee() {
        return returnFee;
    }

    /**
     * @param returnFee the returnFee to set
     */
    public void setReturnFee(int returnFee) {
        this.returnFee = returnFee;
    }

    /**
     * @return the nonRetNum
     */
    public int getNonRetNum() {
        return nonRetNum;
    }

    /**
     * @param nonRetNum the nonRetNum to set
     */
    public void setNonRetNum(int nonRetNum) {
        this.nonRetNum = nonRetNum;
    }

    /**
     * @return the nonRetDepositFee
     */
    public int getNonRetDepositFee() {
        return nonRetDepositFee;
    }

    /**
     * @param nonRetDepositFee the nonRetDepositFee to set
     */
    public void setNonRetDepositFee(int nonRetDepositFee) {
        this.nonRetDepositFee = nonRetDepositFee;
    }

    /**
     * @return the nonRetActualBala
     */
    public int getNonRetActualBala() {
        return nonRetActualBala;
    }

    /**
     * @param nonRetActualBala the nonRetActualBala to set
     */
    public void setNonRetActualBala(int nonRetActualBala) {
        this.nonRetActualBala = nonRetActualBala;
    }

    /**
     * @return the negativeChargeNum
     */
    public int getNegativeChargeNum() {
        return negativeChargeNum;
    }

    /**
     * @param negativeChargeNum the negativeChargeNum to set
     */
    public void setNegativeChargeNum(int negativeChargeNum) {
        this.negativeChargeNum = negativeChargeNum;
    }

    /**
     * @return the negativeChargeFee
     */
    public int getNegativeChargeFee() {
        return negativeChargeFee;
    }

    /**
     * @param negativeChargeFee the negativeChargeFee to set
     */
    public void setNegativeChargeFee(int negativeChargeFee) {
        this.negativeChargeFee = negativeChargeFee;
    }

    /**
     * @return the dealNum
     */
    public int getDealNum() {
        return dealNum;
    }

    /**
     * @param dealNum the dealNum to set
     */
    public void setDealNum(int dealNum) {
        this.dealNum = dealNum;
    }

    /**
     * @return the dealFee
     */
    public int getDealFee() {
        return dealFee;
    }

    /**
     * @param dealFee the dealFee to set
     */
    public void setDealFee(int dealFee) {
        this.dealFee = dealFee;
    }

    /**
     * @return the updateCashFee
     */
    public int getUpdateCashFee() {
        return updateCashFee;
    }

    /**
     * @param updateCashFee the updateCashFee to set
     */
    public void setUpdateCashFee(int updateCashFee) {
        this.updateCashFee = updateCashFee;
    }

    /**
     * @return the updateNonCashFee
     */
    public int getUpdateNonCashFee() {
        return updateNonCashFee;
    }

    /**
     * @param updateNonCashFee the updateNonCashFee to set
     */
    public void setUpdateNonCashFee(int updateNonCashFee) {
        this.updateNonCashFee = updateNonCashFee;
    }

    /**
     * @return the adminNum
     */
    public int getAdminNum() {
        return adminNum;
    }

    /**
     * @param adminNum the adminNum to set
     */
    public void setAdminNum(int adminNum) {
        this.adminNum = adminNum;
    }

    /**
     * @return the adminReturnFee
     */
    public int getAdminReturnFee() {
        return adminReturnFee;
    }

    /**
     * @param adminReturnFee the adminReturnFee to set
     */
    public void setAdminReturnFee(int adminReturnFee) {
        this.adminReturnFee = adminReturnFee;
    }

    /**
     * @return the adminPenaltyFee
     */
    public int getAdminPenaltyFee() {
        return adminPenaltyFee;
    }

    /**
     * @param adminPenaltyFee the adminPenaltyFee to set
     */
    public void setAdminPenaltyFee(int adminPenaltyFee) {
        this.adminPenaltyFee = adminPenaltyFee;
    }

    /**
     * @return the updateCashNum
     */
    public int getUpdateCashNum() {
        return updateCashNum;
    }

    /**
     * @param updateCashNum the updateCashNum to set
     */
    public void setUpdateCashNum(int updateCashNum) {
        this.updateCashNum = updateCashNum;
    }

    /**
     * @return the updateNonCashNum
     */
    public int getUpdateNonCashNum() {
        return updateNonCashNum;
    }

    /**
     * @param updateNonCashNum the updateNonCashNum to set
     */
    public void setUpdateNonCashNum(int updateNonCashNum) {
        this.updateNonCashNum = updateNonCashNum;
    }

    /**
     * @return the itmSaleSjtNum
     */
    public int getItmSaleSjtNum() {
        return itmSaleSjtNum;
    }

    /**
     * @param itmSaleSjtNum the itmSaleSjtNum to set
     */
    public void setItmSaleSjtNum(int itmSaleSjtNum) {
        this.itmSaleSjtNum = itmSaleSjtNum;
    }

    /**
     * @return the itmSaleSjtFee
     */
    public int getItmSaleSjtFee() {
        return itmSaleSjtFee;
    }

    /**
     * @param itmSaleSjtFee the itmSaleSjtFee to set
     */
    public void setItmSaleSjtFee(int itmSaleSjtFee) {
        this.itmSaleSjtFee = itmSaleSjtFee;
    }

    /**
     * @return the itmSaleSjtNum_s
     */
    public String getItmSaleSjtNum_s() {
        return itmSaleSjtNum_s;
    }

    /**
     * @param itmSaleSjtNum_s the itmSaleSjtNum_s to set
     */
    public void setItmSaleSjtNum_s(String itmSaleSjtNum_s) {
        this.itmSaleSjtNum_s = itmSaleSjtNum_s;
    }

    /**
     * @return the itmSaleSjtFee_s
     */
    public String getItmSaleSjtFee_s() {
        return itmSaleSjtFee_s;
    }

    /**
     * @param itmSaleSjtFee_s the itmSaleSjtFee_s to set
     */
    public void setItmSaleSjtFee_s(String itmSaleSjtFee_s) {
        this.itmSaleSjtFee_s = itmSaleSjtFee_s;
    }

    /**
     * @return the itmChargeNum_s
     */
    public String getItmChargeNum_s() {
        return itmChargeNum_s;
    }

    /**
     * @param itmChargeNum_s the itmChargeNum_s to set
     */
    public void setItmChargeNum_s(String itmChargeNum_s) {
        this.itmChargeNum_s = itmChargeNum_s;
    }

    /**
     * @return the itmChargeFee_s
     */
    public String getItmChargeFee_s() {
        return itmChargeFee_s;
    }

    /**
     * @param itmChargeFee_s the itmChargeFee_s to set
     */
    public void setItmChargeFee_s(String itmChargeFee_s) {
        this.itmChargeFee_s = itmChargeFee_s;
    }

    /**
     * @return the updateNonCashTctNum
     */
    public int getUpdateNonCashTctNum() {
        return updateNonCashTctNum;
    }

    /**
     * @param updateNonCashTctNum the updateNonCashTctNum to set
     */
    public void setUpdateNonCashTctNum(int updateNonCashTctNum) {
        this.updateNonCashTctNum = updateNonCashTctNum;
    }

    /**
     * @return the updateNonCashTctNum_s
     */
    public String getUpdateNonCashTctNum_s() {
        return updateNonCashTctNum_s;
    }

    /**
     * @param updateNonCashTctNum_s the updateNonCashTctNum_s to set
     */
    public void setUpdateNonCashTctNum_s(String updateNonCashTctNum_s) {
        this.updateNonCashTctNum_s = updateNonCashTctNum_s;
    }
}
