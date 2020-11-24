/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordTacBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileRecord00 extends FileRecordBase{

    /**
     * @return the startTime_s
     */
    public String getStartTime_s() {
        return startTime_s;
    }

    /**
     * @param startTime_s the startTime_s to set
     */
    public void setStartTime_s(String startTime_s) {
        this.startTime_s = startTime_s;
    }

    /**
     * @return the endTime_s
     */
    public String getEndTime_s() {
        return endTime_s;
    }

    /**
     * @param endTime_s the endTime_s to set
     */
    public void setEndTime_s(String endTime_s) {
        this.endTime_s = endTime_s;
    }


    private String recordId;


    private String startTime;//班次开始时间
    private String endTime;//班次结束时间
    
    
    private String startTime_s;//班次开始时间
    private String endTime_s;//班次结束时间
    
    private int saleTotalNum;//发售总数量
    private int saleTotalFee;//发售总金额
    private Vector saleDetail;//发售明细
    
    private int chargeTotalNum;//充值总数量
    private int chargeTotalFee;//充值总金额
    private Vector chargeDetail;//充值明细
    
    private int updateTotalNum;//更新总数量
    private int updateTotalFee;//更新总金额
    private int updateTotalFeeCash;//更新现金总金额
    private Vector updateDetail;//更新明细
    
    private int adminTotalNum;//行政处理总数量
    private int adminTotalFeeReturn;//行政处理退款总金额
    private int adminTotalFeeIncome;//行政处理退款总收入
    private Vector adminDetail;//行政处理明细
    
    private int delayTotalNum;//延期总数量
    private Vector delayDetail;//延期明细
    
    private int returnTotalNum;//即时退款总数量
    private int returnTotalFee;//即时退款总金额
    private Vector returnDetail;//即时退款明细
    
    private int returnNonTotalNum;//非即时退款总数量
    private int returnNonTotalFee;//非即时退款总金额
    private Vector returnNonDetail;//非即时退款明细
    
    private int returnNonAppTotalNum;//非即时退款申请总数量
    private Vector returnNonAppDetail;//非即时退款申请明细
    
    private int chargeUpdateTotalNum;//冲正总数量
    private int chargeUpdateTotalFee;//冲正总金额
    private Vector chargeUpdateDetail;//冲正明细
    
    private int unlockTotalNum;//解锁总数量
    private Vector unlockDetail;//解锁明细
    
    private int totalNoCashFee;//非现金总金额
    private int totalFee;//应收总金额
    
    private String tradeType;//数据类型



    /**
     * @return the recordId
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }



    /**
     * @return the stationId
     */






 

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
     * @return the saleTotalNum
     */
    public int getSaleTotalNum() {
        return saleTotalNum;
    }

    /**
     * @param saleTotalNum the saleTotalNum to set
     */
    public void setSaleTotalNum(int saleTotalNum) {
        this.saleTotalNum = saleTotalNum;
    }

    /**
     * @return the saleTotalFee
     */
    public int getSaleTotalFee() {
        return saleTotalFee;
    }

    /**
     * @param saleTotalFee the saleTotalFee to set
     */
    public void setSaleTotalFee(int saleTotalFee) {
        this.saleTotalFee = saleTotalFee;
    }

    /**
     * @return the saleDetail
     */
    public Vector getSaleDetail() {
        return saleDetail;
    }

    /**
     * @param saleDetail the saleDetail to set
     */
    public void setSaleDetail(Vector saleDetail) {
        this.saleDetail = saleDetail;
    }

    /**
     * @return the chargeTotalNum
     */
    public int getChargeTotalNum() {
        return chargeTotalNum;
    }

    /**
     * @param chargeTotalNum the chargeTotalNum to set
     */
    public void setChargeTotalNum(int chargeTotalNum) {
        this.chargeTotalNum = chargeTotalNum;
    }

    /**
     * @return the chargeTotalFee
     */
    public int getChargeTotalFee() {
        return chargeTotalFee;
    }

    /**
     * @param chargeTotalFee the chargeTotalFee to set
     */
    public void setChargeTotalFee(int chargeTotalFee) {
        this.chargeTotalFee = chargeTotalFee;
    }

    /**
     * @return the chargeDetail
     */
    public Vector getChargeDetail() {
        return chargeDetail;
    }

    /**
     * @param chargeDetail the chargeDetail to set
     */
    public void setChargeDetail(Vector chargeDetail) {
        this.chargeDetail = chargeDetail;
    }

    /**
     * @return the updateTotalNum
     */
    public int getUpdateTotalNum() {
        return updateTotalNum;
    }

    /**
     * @param updateTotalNum the updateTotalNum to set
     */
    public void setUpdateTotalNum(int updateTotalNum) {
        this.updateTotalNum = updateTotalNum;
    }

    /**
     * @return the updateTotalFee
     */
    public int getUpdateTotalFee() {
        return updateTotalFee;
    }

    /**
     * @param updateTotalFee the updateTotalFee to set
     */
    public void setUpdateTotalFee(int updateTotalFee) {
        this.updateTotalFee = updateTotalFee;
    }

    /**
     * @return the updateTotalFeeCash
     */
    public int getUpdateTotalFeeCash() {
        return updateTotalFeeCash;
    }

    /**
     * @param updateTotalFeeCash the updateTotalFeeCash to set
     */
    public void setUpdateTotalFeeCash(int updateTotalFeeCash) {
        this.updateTotalFeeCash = updateTotalFeeCash;
    }

    /**
     * @return the updateDetail
     */
    public Vector getUpdateDetail() {
        return updateDetail;
    }

    /**
     * @param updateDetail the updateDetail to set
     */
    public void setUpdateDetail(Vector updateDetail) {
        this.updateDetail = updateDetail;
    }

    /**
     * @return the adminTotalNum
     */
    public int getAdminTotalNum() {
        return adminTotalNum;
    }

    /**
     * @param adminTotalNum the adminTotalNum to set
     */
    public void setAdminTotalNum(int adminTotalNum) {
        this.adminTotalNum = adminTotalNum;
    }

    /**
     * @return the adminTotalFeeReturn
     */
    public int getAdminTotalFeeReturn() {
        return adminTotalFeeReturn;
    }

    /**
     * @param adminTotalFeeReturn the adminTotalFeeReturn to set
     */
    public void setAdminTotalFeeReturn(int adminTotalFeeReturn) {
        this.adminTotalFeeReturn = adminTotalFeeReturn;
    }

    /**
     * @return the adminTotalFeeIncome
     */
    public int getAdminTotalFeeIncome() {
        return adminTotalFeeIncome;
    }

    /**
     * @param adminTotalFeeIncome the adminTotalFeeIncome to set
     */
    public void setAdminTotalFeeIncome(int adminTotalFeeIncome) {
        this.adminTotalFeeIncome = adminTotalFeeIncome;
    }

    /**
     * @return the adminDetail
     */
    public Vector getAdminDetail() {
        return adminDetail;
    }

    /**
     * @param adminDetail the adminDetail to set
     */
    public void setAdminDetail(Vector adminDetail) {
        this.adminDetail = adminDetail;
    }

    /**
     * @return the delayTotalNum
     */
    public int getDelayTotalNum() {
        return delayTotalNum;
    }

    /**
     * @param delayTotalNum the delayTotalNum to set
     */
    public void setDelayTotalNum(int delayTotalNum) {
        this.delayTotalNum = delayTotalNum;
    }

    /**
     * @return the delayDetail
     */
    public Vector getDelayDetail() {
        return delayDetail;
    }

    /**
     * @param delayDetail the delayDetail to set
     */
    public void setDelayDetail(Vector delayDetail) {
        this.delayDetail = delayDetail;
    }

    /**
     * @return the returnTotalNum
     */
    public int getReturnTotalNum() {
        return returnTotalNum;
    }

    /**
     * @param returnTotalNum the returnTotalNum to set
     */
    public void setReturnTotalNum(int returnTotalNum) {
        this.returnTotalNum = returnTotalNum;
    }

    /**
     * @return the returnTotalFee
     */
    public int getReturnTotalFee() {
        return returnTotalFee;
    }

    /**
     * @param returnTotalFee the returnTotalFee to set
     */
    public void setReturnTotalFee(int returnTotalFee) {
        this.returnTotalFee = returnTotalFee;
    }

    /**
     * @return the returnDetail
     */
    public Vector getReturnDetail() {
        return returnDetail;
    }

    /**
     * @param returnDetail the returnDetail to set
     */
    public void setReturnDetail(Vector returnDetail) {
        this.returnDetail = returnDetail;
    }

    /**
     * @return the returnNonTotalNum
     */
    public int getReturnNonTotalNum() {
        return returnNonTotalNum;
    }

    /**
     * @param returnNonTotalNum the returnNonTotalNum to set
     */
    public void setReturnNonTotalNum(int returnNonTotalNum) {
        this.returnNonTotalNum = returnNonTotalNum;
    }

    /**
     * @return the returnNonTotalFee
     */
    public int getReturnNonTotalFee() {
        return returnNonTotalFee;
    }

    /**
     * @param returnNonTotalFee the returnNonTotalFee to set
     */
    public void setReturnNonTotalFee(int returnNonTotalFee) {
        this.returnNonTotalFee = returnNonTotalFee;
    }

    /**
     * @return the returnNonAppTotalNum
     */
    public int getReturnNonAppTotalNum() {
        return returnNonAppTotalNum;
    }

    /**
     * @param returnNonAppTotalNum the returnNonAppTotalNum to set
     */
    public void setReturnNonAppTotalNum(int returnNonAppTotalNum) {
        this.returnNonAppTotalNum = returnNonAppTotalNum;
    }

    /**
     * @return the returnNonAppDetail
     */
    public Vector getReturnNonAppDetail() {
        return returnNonAppDetail;
    }

    /**
     * @param returnNonAppDetail the returnNonAppDetail to set
     */
    public void setReturnNonAppDetail(Vector returnNonAppDetail) {
        this.returnNonAppDetail = returnNonAppDetail;
    }

    /**
     * @return the chargeUpdateTotalNum
     */
    public int getChargeUpdateTotalNum() {
        return chargeUpdateTotalNum;
    }

    /**
     * @param chargeUpdateTotalNum the chargeUpdateTotalNum to set
     */
    public void setChargeUpdateTotalNum(int chargeUpdateTotalNum) {
        this.chargeUpdateTotalNum = chargeUpdateTotalNum;
    }

    /**
     * @return the chargeUpdateTotalFee
     */
    public int getChargeUpdateTotalFee() {
        return chargeUpdateTotalFee;
    }

    /**
     * @param chargeUpdateTotalFee the chargeUpdateTotalFee to set
     */
    public void setChargeUpdateTotalFee(int chargeUpdateTotalFee) {
        this.chargeUpdateTotalFee = chargeUpdateTotalFee;
    }

    /**
     * @return the chargeUpdateDetail
     */
    public Vector getChargeUpdateDetail() {
        return chargeUpdateDetail;
    }

    /**
     * @param chargeUpdateDetail the chargeUpdateDetail to set
     */
    public void setChargeUpdateDetail(Vector chargeUpdateDetail) {
        this.chargeUpdateDetail = chargeUpdateDetail;
    }

    /**
     * @return the unlockTotalNum
     */
    public int getUnlockTotalNum() {
        return unlockTotalNum;
    }

    /**
     * @param unlockTotalNum the unlockTotalNum to set
     */
    public void setUnlockTotalNum(int unlockTotalNum) {
        this.unlockTotalNum = unlockTotalNum;
    }

    /**
     * @return the unlockDetail
     */
    public Vector getUnlockDetail() {
        return unlockDetail;
    }

    /**
     * @param unlockDetail the unlockDetail to set
     */
    public void setUnlockDetail(Vector unlockDetail) {
        this.unlockDetail = unlockDetail;
    }

    /**
     * @return the totalNocash
     */
    public int getTotalNoCashFee() {
        return totalNoCashFee;
    }

    /**
     * @param totalNocash the totalNocash to set
     */
    public void setTotalNoCashFee(int totalNoCashFee) {
        this.totalNoCashFee = totalNoCashFee;
    }



 

 
  
    /**
     * @return the tradeType
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * @param tradeType the tradeType to set
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * @return the returnNonDetail
     */
    public Vector getReturnNonDetail() {
        return returnNonDetail;
    }

    /**
     * @param returnNonDetail the returnNonDetail to set
     */
    public void setReturnNonDetail(Vector returnNonDetail) {
        this.returnNonDetail = returnNonDetail;
    }

    /**
     * @return the totalAmount
     */
    public int getTotalFee() {
        return totalFee;
    }

    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }




}
