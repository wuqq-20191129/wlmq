/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

/**
 *
 * @author hejj
 */
public class FileSDFVo {

    /**
     * @return the diffQrEntryNum
     */
    public int getDiffQrEntryNum() {
        return diffQrEntryNum;
    }

    /**
     * @param diffQrEntryNum the diffQrEntryNum to set
     */
    public void setDiffQrEntryNum(int diffQrEntryNum) {
        this.diffQrEntryNum = diffQrEntryNum;
    }

    private String lineId;//线路车站代码
    private String stationId;//
    private int diffBomSaleSjtNum;//BOM单程票发售总数量差异
    private int diffBomSaleSjtFee;//BOM单程票发售总金额差异
    private int diffTvmSaleSjtNum;//TVM单程票发售总数量差异
    private int diffTvmSaleSjtFee;//TVM单程票发售总金额差异
    private int diffBomSaleNum;////BOM储值类票卡发售总数量差异
    private int diffBomSaleDepositFee;//BOM储值类票卡押金总金额差异
    private int diffBomChargeFee;//BOM充值总金额差异
    private int diffTvmChargeNum;//TVM充值总数量差异
    private int diffTvmChargeFee;//TVM充值总金额差异
    private int diffReturnNum;//即时退款总数量差异
    private int diffReturnFee;//即时退款总金额差异
    private int diffNonRetNum;//非即时退款总数量差异
    private int diffNonRetDepositFee;//非即时退款总押金差异
    private int diffNonRetActualBala;//非即时退款总退还余额差异
    private int diffNegativeChargeNum;//冲正总数量差异
    private int diffNegativeChargeFee;//冲正总金额差异
    private int diffDealNum;//出闸扣费总数量差异
    private int diffDealFee;//出闸扣费总金额差异
    private int diffUpdateCashNum;//现金更新总数量差异
    private int diffUpdateCashFee;//现金更新总金额差异
    private int diffUpdateNonCashNum;//非现金更新总数量差异
    private int diffUpdateNonCashFee;//非现金更新总金额差异
    private int diffAdminNum;//行政处理总数量差异
    private int diffAdminReturnFee;//行政处理总支出金额差异
    private int diffAdminPenaltyFee;//行政处理总收取金额差异

    private int diffItmSaleSjtNum;//ITM单程票发售总数量差异
    private int diffItmSaleSjtFee;//ITM单程票发售总金额差异
    private int diffItmChargeNum;//ITM充值总数量差异
    private int diffItmChargeFee;//ITM充值总金额差异

    private String accountDate;//对账日期
    
    private int diffQrDealNum;//二维码钱包交易总数量  
    private int diffElectTkTctDealNum;//电子票钱包交易总次数（计次）  
    private int diffElectTkDealNum;//电子票钱包交易总数量（计值）  
    private int diffElectTkDealFee;//电子票钱包交易总金额（计值
    private String recordVer;
    
    private int diffQrEntryNum;//二维码进站总数量
    private int diffUpdateNonCashTctNum;//非现金更新总数量
    
    
    private int diffBomSaleTctNum;//BOM实体次票发售总数量差异
    private int diffBomSaleTctFee;//BOM实体次票发售总金额差异

    /**
     * @return the lineId
     */
    public String getLineId() {
        return lineId;
    }

    /**
     * @param lineId the lineId to set
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    /**
     * @return the stationId
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * @param stationId the stationId to set
     */
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    /**
     * @return the diffBomSaleSjtNum
     */
    public int getDiffBomSaleSjtNum() {
        return diffBomSaleSjtNum;
    }

    /**
     * @param diffBomSaleSjtNum the diffBomSaleSjtNum to set
     */
    public void setDiffBomSaleSjtNum(int diffBomSaleSjtNum) {
        this.diffBomSaleSjtNum = diffBomSaleSjtNum;
    }

    /**
     * @return the diffBomSaleSjtFee
     */
    public int getDiffBomSaleSjtFee() {
        return diffBomSaleSjtFee;
    }

    /**
     * @param diffBomSaleSjtFee the diffBomSaleSjtFee to set
     */
    public void setDiffBomSaleSjtFee(int diffBomSaleSjtFee) {
        this.diffBomSaleSjtFee = diffBomSaleSjtFee;
    }

    /**
     * @return the diffTvmSaleSjtNum
     */
    public int getDiffTvmSaleSjtNum() {
        return diffTvmSaleSjtNum;
    }

    /**
     * @param diffTvmSaleSjtNum the diffTvmSaleSjtNum to set
     */
    public void setDiffTvmSaleSjtNum(int diffTvmSaleSjtNum) {
        this.diffTvmSaleSjtNum = diffTvmSaleSjtNum;
    }

    /**
     * @return the diffTvmSaleSjtFee
     */
    public int getDiffTvmSaleSjtFee() {
        return diffTvmSaleSjtFee;
    }

    /**
     * @param diffTvmSaleSjtFee the diffTvmSaleSjtFee to set
     */
    public void setDiffTvmSaleSjtFee(int diffTvmSaleSjtFee) {
        this.diffTvmSaleSjtFee = diffTvmSaleSjtFee;
    }

    /**
     * @return the diffBomSaleNum
     */
    public int getDiffBomSaleNum() {
        return diffBomSaleNum;
    }

    /**
     * @param diffBomSaleNum the diffBomSaleNum to set
     */
    public void setDiffBomSaleNum(int diffBomSaleNum) {
        this.diffBomSaleNum = diffBomSaleNum;
    }

    /**
     * @return the diffBomSaleDepositFee
     */
    public int getDiffBomSaleDepositFee() {
        return diffBomSaleDepositFee;
    }

    /**
     * @param diffBomSaleDepositFee the diffBomSaleDepositFee to set
     */
    public void setDiffBomSaleDepositFee(int diffBomSaleDepositFee) {
        this.diffBomSaleDepositFee = diffBomSaleDepositFee;
    }

    /**
     * @return the diffBomChargeFee
     */
    public int getDiffBomChargeFee() {
        return diffBomChargeFee;
    }

    /**
     * @param diffBomChargeFee the diffBomChargeFee to set
     */
    public void setDiffBomChargeFee(int diffBomChargeFee) {
        this.diffBomChargeFee = diffBomChargeFee;
    }

    /**
     * @return the diffTvmChargeNum
     */
    public int getDiffTvmChargeNum() {
        return diffTvmChargeNum;
    }

    /**
     * @param diffTvmChargeNum the diffTvmChargeNum to set
     */
    public void setDiffTvmChargeNum(int diffTvmChargeNum) {
        this.diffTvmChargeNum = diffTvmChargeNum;
    }

    /**
     * @return the diffTvmChargeFee
     */
    public int getDiffTvmChargeFee() {
        return diffTvmChargeFee;
    }

    /**
     * @param diffTvmChargeFee the diffTvmChargeFee to set
     */
    public void setDiffTvmChargeFee(int diffTvmChargeFee) {
        this.diffTvmChargeFee = diffTvmChargeFee;
    }

    /**
     * @return the diffReturnNum
     */
    public int getDiffReturnNum() {
        return diffReturnNum;
    }

    /**
     * @param diffReturnNum the diffReturnNum to set
     */
    public void setDiffReturnNum(int diffReturnNum) {
        this.diffReturnNum = diffReturnNum;
    }

    /**
     * @return the diffReturnFee
     */
    public int getDiffReturnFee() {
        return diffReturnFee;
    }

    /**
     * @param diffReturnFee the diffReturnFee to set
     */
    public void setDiffReturnFee(int diffReturnFee) {
        this.diffReturnFee = diffReturnFee;
    }

    /**
     * @return the diffNonRetNum
     */
    public int getDiffNonRetNum() {
        return diffNonRetNum;
    }

    /**
     * @param diffNonRetNum the diffNonRetNum to set
     */
    public void setDiffNonRetNum(int diffNonRetNum) {
        this.diffNonRetNum = diffNonRetNum;
    }

    /**
     * @return the diffNonRetDepositFee
     */
    public int getDiffNonRetDepositFee() {
        return diffNonRetDepositFee;
    }

    /**
     * @param diffNonRetDepositFee the diffNonRetDepositFee to set
     */
    public void setDiffNonRetDepositFee(int diffNonRetDepositFee) {
        this.diffNonRetDepositFee = diffNonRetDepositFee;
    }

    /**
     * @return the diffNonRetActualBala
     */
    public int getDiffNonRetActualBala() {
        return diffNonRetActualBala;
    }

    /**
     * @param diffNonRetActualBala the diffNonRetActualBala to set
     */
    public void setDiffNonRetActualBala(int diffNonRetActualBala) {
        this.diffNonRetActualBala = diffNonRetActualBala;
    }

    /**
     * @return the diffNegativeChargeNum
     */
    public int getDiffNegativeChargeNum() {
        return diffNegativeChargeNum;
    }

    /**
     * @param diffNegativeChargeNum the diffNegativeChargeNum to set
     */
    public void setDiffNegativeChargeNum(int diffNegativeChargeNum) {
        this.diffNegativeChargeNum = diffNegativeChargeNum;
    }

    /**
     * @return the diffNegativeChargeFee
     */
    public int getDiffNegativeChargeFee() {
        return diffNegativeChargeFee;
    }

    /**
     * @param diffNegativeChargeFee the diffNegativeChargeFee to set
     */
    public void setDiffNegativeChargeFee(int diffNegativeChargeFee) {
        this.diffNegativeChargeFee = diffNegativeChargeFee;
    }

    /**
     * @return the diffDealNum
     */
    public int getDiffDealNum() {
        return diffDealNum;
    }

    /**
     * @param diffDealNum the diffDealNum to set
     */
    public void setDiffDealNum(int diffDealNum) {
        this.diffDealNum = diffDealNum;
    }

    /**
     * @return the diffDealFee
     */
    public int getDiffDealFee() {
        return diffDealFee;
    }

    /**
     * @param diffDealFee the diffDealFee to set
     */
    public void setDiffDealFee(int diffDealFee) {
        this.diffDealFee = diffDealFee;
    }

    /**
     * @return the diffUpdateCashNum
     */
    public int getDiffUpdateCashNum() {
        return diffUpdateCashNum;
    }

    /**
     * @param diffUpdateCashNum the diffUpdateCashNum to set
     */
    public void setDiffUpdateCashNum(int diffUpdateCashNum) {
        this.diffUpdateCashNum = diffUpdateCashNum;
    }

    /**
     * @return the diffUpdateCashFee
     */
    public int getDiffUpdateCashFee() {
        return diffUpdateCashFee;
    }

    /**
     * @param diffUpdateCashFee the diffUpdateCashFee to set
     */
    public void setDiffUpdateCashFee(int diffUpdateCashFee) {
        this.diffUpdateCashFee = diffUpdateCashFee;
    }

    /**
     * @return the diffUpdateNonCashNum
     */
    public int getDiffUpdateNonCashNum() {
        return diffUpdateNonCashNum;
    }

    /**
     * @param diffUpdateNonCashNum the diffUpdateNonCashNum to set
     */
    public void setDiffUpdateNonCashNum(int diffUpdateNonCashNum) {
        this.diffUpdateNonCashNum = diffUpdateNonCashNum;
    }

    /**
     * @return the diffUpdateNonCashFee
     */
    public int getDiffUpdateNonCashFee() {
        return diffUpdateNonCashFee;
    }

    /**
     * @param diffUpdateNonCashFee the diffUpdateNonCashFee to set
     */
    public void setDiffUpdateNonCashFee(int diffUpdateNonCashFee) {
        this.diffUpdateNonCashFee = diffUpdateNonCashFee;
    }

    /**
     * @return the diffAdminNum
     */
    public int getDiffAdminNum() {
        return diffAdminNum;
    }

    /**
     * @param diffAdminNum the diffAdminNum to set
     */
    public void setDiffAdminNum(int diffAdminNum) {
        this.diffAdminNum = diffAdminNum;
    }

    /**
     * @return the diffAdminReturnFee
     */
    public int getDiffAdminReturnFee() {
        return diffAdminReturnFee;
    }

    /**
     * @param diffAdminReturnFee the diffAdminReturnFee to set
     */
    public void setDiffAdminReturnFee(int diffAdminReturnFee) {
        this.diffAdminReturnFee = diffAdminReturnFee;
    }

    /**
     * @return the diffAdminPenaltyFee
     */
    public int getDiffAdminPenaltyFee() {
        return diffAdminPenaltyFee;
    }

    /**
     * @param diffAdminPenaltyFee the diffAdminPenaltyFee to set
     */
    public void setDiffAdminPenaltyFee(int diffAdminPenaltyFee) {
        this.diffAdminPenaltyFee = diffAdminPenaltyFee;
    }

    /**
     * @return the accountDate
     */
    public String getAccountDate() {
        return accountDate;
    }

    /**
     * @param accountDate the accountDate to set
     */
    public void setAccountDate(String accountDate) {
        this.accountDate = accountDate;
    }

    /**
     * @return the diffItmSaleSjtNum
     */
    public int getDiffItmSaleSjtNum() {
        return diffItmSaleSjtNum;
    }

    /**
     * @param diffItmSaleSjtNum the diffItmSaleSjtNum to set
     */
    public void setDiffItmSaleSjtNum(int diffItmSaleSjtNum) {
        this.diffItmSaleSjtNum = diffItmSaleSjtNum;
    }

    /**
     * @return the diffItmSaleSjtFee
     */
    public int getDiffItmSaleSjtFee() {
        return diffItmSaleSjtFee;
    }

    /**
     * @param diffItmSaleSjtFee the diffItmSaleSjtFee to set
     */
    public void setDiffItmSaleSjtFee(int diffItmSaleSjtFee) {
        this.diffItmSaleSjtFee = diffItmSaleSjtFee;
    }

    /**
     * @return the diffItmChargeNum
     */
    public int getDiffItmChargeNum() {
        return diffItmChargeNum;
    }

    /**
     * @param diffItmChargeNum the diffItmChargeNum to set
     */
    public void setDiffItmChargeNum(int diffItmChargeNum) {
        this.diffItmChargeNum = diffItmChargeNum;
    }

    /**
     * @return the diffItmChargeFee
     */
    public int getDiffItmChargeFee() {
        return diffItmChargeFee;
    }

    /**
     * @param diffItmChargeFee the diffItmChargeFee to set
     */
    public void setDiffItmChargeFee(int diffItmChargeFee) {
        this.diffItmChargeFee = diffItmChargeFee;
    }

    /**
     * @return the diffQrDealNum
     */
    public int getDiffQrDealNum() {
        return diffQrDealNum;
    }

    /**
     * @param diffQrDealNum the diffQrDealNum to set
     */
    public void setDiffQrDealNum(int diffQrDealNum) {
        this.diffQrDealNum = diffQrDealNum;
    }

    /**
     * @return the diffElectTkTctDealNum
     */
    public int getDiffElectTkTctDealNum() {
        return diffElectTkTctDealNum;
    }

    /**
     * @param diffElectTkTctDealNum the diffElectTkTctDealNum to set
     */
    public void setDiffElectTkTctDealNum(int diffElectTkTctDealNum) {
        this.diffElectTkTctDealNum = diffElectTkTctDealNum;
    }

    /**
     * @return the diffElectTkDealNum
     */
    public int getDiffElectTkDealNum() {
        return diffElectTkDealNum;
    }

    /**
     * @param diffElectTkDealNum the diffElectTkDealNum to set
     */
    public void setDiffElectTkDealNum(int diffElectTkDealNum) {
        this.diffElectTkDealNum = diffElectTkDealNum;
    }

    /**
     * @return the diffElectTkDealFee
     */
    public int getDiffElectTkDealFee() {
        return diffElectTkDealFee;
    }

    /**
     * @param diffElectTkDealFee the diffElectTkDealFee to set
     */
    public void setDiffElectTkDealFee(int diffElectTkDealFee) {
        this.diffElectTkDealFee = diffElectTkDealFee;
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
     * @return the diffUpdateNonCashTctNum
     */
    public int getDiffUpdateNonCashTctNum() {
        return diffUpdateNonCashTctNum;
    }

    /**
     * @param diffUpdateNonCashTctNum the diffUpdateNonCashTctNum to set
     */
    public void setDiffUpdateNonCashTctNum(int diffUpdateNonCashTctNum) {
        this.diffUpdateNonCashTctNum = diffUpdateNonCashTctNum;
    }

    /**
     * @return the diffBomSaleTctNum
     */
    public int getDiffBomSaleTctNum() {
        return diffBomSaleTctNum;
    }

    /**
     * @param diffBomSaleTctNum the diffBomSaleTctNum to set
     */
    public void setDiffBomSaleTctNum(int diffBomSaleTctNum) {
        this.diffBomSaleTctNum = diffBomSaleTctNum;
    }

    /**
     * @return the diffBomSaleTctFee
     */
    public int getDiffBomSaleTctFee() {
        return diffBomSaleTctFee;
    }

    /**
     * @param diffBomSaleTctFee the diffBomSaleTctFee to set
     */
    public void setDiffBomSaleTctFee(int diffBomSaleTctFee) {
        this.diffBomSaleTctFee = diffBomSaleTctFee;
    }
}
