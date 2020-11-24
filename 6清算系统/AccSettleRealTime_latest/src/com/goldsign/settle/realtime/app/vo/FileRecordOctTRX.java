/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordTacBase;

/**
 *
 * @author hejj
 */
public class FileRecordOctTRX extends FileRecordTacBase {





    private String strSamTradeSeq;//  脱机交易流水号
    private int SamTradeSeq;//  脱机交易流水号
    private String SamTradeSeq_s;//  脱机交易流水号
    
    private String dealFee_s;//交易金额
    private String dealBalance_s;//余额
    private String strCardConsumeSeq;//票卡扣款交易计数
    private int cardConsumeSeq;//票卡扣款交易计数
    private String cardConsumeSeq_s;//票卡扣款交易计数
    private String cardChargeSeq_s;//票卡充值交易计数
    private String lastSamLogicalId;//上次交易SAM逻辑卡号
    private String lastDealDatetime;//上次交易日期时间
    private String dealDatetime;//日期时间
    private String entrySamLogicalId;//入口SAM逻辑卡号
    private String entryDatetime;//进站日期时间
    private String tac;//交易认证码
    private String dealNoDiscountFee_s;//原票价
    
    private String payModeId;//支付类型
    private String purseOpType;//钱包操作类型增值或减值 用于TAC码校验辅助判断
    /**
     * 公交TAC校验相关
     */
    private String busTacDealType;//TAC交易类型
    private String busTacDevId;//TAC设备编码
    private String busCityCode;//城市代码
    private String busBusinessCode;//行业代码
    //模式相关
    private String workMode;//工作模式
    private String cardAppMode;//卡应用模式
    
    private String keyVersion;//密钥版本
    private String lastChargeDatetime;//最后充值时间
    
    private String reserveField;//预留字段

    /**
     * @return the strSamTradeSeq
     */
    public String getStrSamTradeSeq() {
        return strSamTradeSeq;
    }

    /**
     * @param strSamTradeSeq the strSamTradeSeq to set
     */
    public void setStrSamTradeSeq(String strSamTradeSeq) {
        this.strSamTradeSeq = strSamTradeSeq;
    }





    /**
     * @return the strCardConsumeSeq
     */
    public String getStrCardConsumeSeq() {
        return strCardConsumeSeq;
    }

    /**
     * @param strCardConsumeSeq the strCardConsumeSeq to set
     */
    public void setStrCardConsumeSeq(String strCardConsumeSeq) {
        this.strCardConsumeSeq = strCardConsumeSeq;
    }

    /**
     * @return the busTacDealType
     */
    public String getBusTacDealType() {
        return busTacDealType;
    }

    /**
     * @param busTacDealType the busTacDealType to set
     */
    public void setBusTacDealType(String busTacDealType) {
        this.busTacDealType = busTacDealType;
    }

    /**
     * @return the busTacDevId
     */
    public String getBusTacDevId() {
        return busTacDevId;
    }

    /**
     * @param busTacDevId the busTacDevId to set
     */
    public void setBusTacDevId(String busTacDevId) {
        this.busTacDevId = busTacDevId;
    }

    /**
     * @return the busCityCode
     */
    public String getBusCityCode() {
        return busCityCode;
    }

    /**
     * @param busCityCode the busCityCode to set
     */
    public void setBusCityCode(String busCityCode) {
        this.busCityCode = busCityCode;
    }

    /**
     * @return the busBusinessCode
     */
    public String getBusBusinessCode() {
        return busBusinessCode;
    }

    /**
     * @param busBusinessCode the busBusinessCode to set
     */
    public void setBusBusinessCode(String busBusinessCode) {
        this.busBusinessCode = busBusinessCode;
    }

    /**
     * @return the workMode
     */
    public String getWorkMode() {
        return workMode;
    }

    /**
     * @param workMode the workMode to set
     */
    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }

    /**
     * @return the cardAppMode
     */
    public String getCardAppMode() {
        return cardAppMode;
    }

    /**
     * @param cardAppMode the cardAppMode to set
     */
    public void setCardAppMode(String cardAppMode) {
        this.cardAppMode = cardAppMode;
    }

    /**
     * @return the lastSamLogicalId
     */
    public String getLastSamLogicalId() {
        return lastSamLogicalId;
    }

    /**
     * @param lastSamLogicalId the lastSamLogicalId to set
     */
    public void setLastSamLogicalId(String lastSamLogicalId) {
        this.lastSamLogicalId = lastSamLogicalId;
    }

    /**
     * @return the lastDealDatetime
     */
    public String getLastDealDatetime() {
        return lastDealDatetime;
    }

    /**
     * @param lastDealDatetime the lastDealDatetime to set
     */
    public void setLastDealDatetime(String lastDealDatetime) {
        this.lastDealDatetime = lastDealDatetime;
    }

    /**
     * @return the dealDatetime
     */
    public String getDealDatetime() {
        return dealDatetime;
    }

    /**
     * @param dealDatetime the dealDatetime to set
     */
    public void setDealDatetime(String dealDatetime) {
        this.dealDatetime = dealDatetime;
    }

    /**
     * @return the entrySamLogicalId
     */
    public String getEntrySamLogicalId() {
        return entrySamLogicalId;
    }

    /**
     * @param entrySamLogicalId the entrySamLogicalId to set
     */
    public void setEntrySamLogicalId(String entrySamLogicalId) {
        this.entrySamLogicalId = entrySamLogicalId;
    }

    /**
     * @return the entryDatetime
     */
    public String getEntryDatetime() {
        return entryDatetime;
    }

    /**
     * @param entryDatetime the entryDatetime to set
     */
    public void setEntryDatetime(String entryDatetime) {
        this.entryDatetime = entryDatetime;
    }

    /**
     * @return the tac
     */
    public String getTac() {
        return tac;
    }

    /**
     * @param tac the tac to set
     */
    public void setTac(String tac) {
        this.tac = tac;
    }

    /**
     * @return the payModeId
     */
    public String getPayModeId() {
        return payModeId;
    }

    /**
     * @param payModeId the payModeId to set
     */
    public void setPayModeId(String payModeId) {
        this.payModeId = payModeId;
    }

    /**
     * @return the purseOpType
     */
    public String getPurseOpType() {
        return purseOpType;
    }

    /**
     * @param purseOpType the purseOpType to set
     */
    public void setPurseOpType(String purseOpType) {
        this.purseOpType = purseOpType;
    }

    /**
     * @return the cardConsumeSeq
     */
    public int getCardConsumeSeq() {
        return cardConsumeSeq;
    }

    /**
     * @param cardConsumeSeq the cardConsumeSeq to set
     */
    public void setCardConsumeSeq(int cardConsumeSeq) {
        this.cardConsumeSeq = cardConsumeSeq;
    }

    /**
     * @return the SamTradeSeq
     */
    public int getSamTradeSeq() {
        return SamTradeSeq;
    }

    /**
     * @param SamTradeSeq the SamTradeSeq to set
     */
    public void setSamTradeSeq(int SamTradeSeq) {
        this.SamTradeSeq = SamTradeSeq;
    }

    /**
     * @return the SamTradeSeq_s
     */
    public String getSamTradeSeq_s() {
        return SamTradeSeq_s;
    }

    /**
     * @param SamTradeSeq_s the SamTradeSeq_s to set
     */
    public void setSamTradeSeq_s(String SamTradeSeq_s) {
        this.SamTradeSeq_s = SamTradeSeq_s;
    }
        /**
     * @return the cardConsumeSeq_s
     */
    public String getCardConsumeSeq_s() {
        return cardConsumeSeq_s;
    }

    /**
     * @param cardConsumeSeq_s the cardConsumeSeq_s to set
     */
    public void setCardConsumeSeq_s(String cardConsumeSeq_s) {
        this.cardConsumeSeq_s = cardConsumeSeq_s;
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
     * @return the dealBalance_s
     */
    public String getDealBalance_s() {
        return dealBalance_s;
    }

    /**
     * @param dealBalance_s the dealBalance_s to set
     */
    public void setDealBalance_s(String dealBalance_s) {
        this.dealBalance_s = dealBalance_s;
    }

    /**
     * @return the dealNoDiscountFee_s
     */
    public String getDealNoDiscountFee_s() {
        return dealNoDiscountFee_s;
    }

    /**
     * @param dealNoDiscountFee_s the dealNoDiscountFee_s to set
     */
    public void setDealNoDiscountFee_s(String dealNoDiscountFee_s) {
        this.dealNoDiscountFee_s = dealNoDiscountFee_s;
    }

    /**
     * @return the cardChargeSeq_s
     */
    public String getCardChargeSeq_s() {
        return cardChargeSeq_s;
    }

    /**
     * @param cardChargeSeq_s the cardChargeSeq_s to set
     */
    public void setCardChargeSeq_s(String cardChargeSeq_s) {
        this.cardChargeSeq_s = cardChargeSeq_s;
    }

    /**
     * @return the keyVersion
     */
    public String getKeyVersion() {
        return keyVersion;
    }

    /**
     * @param keyVersion the keyVersion to set
     */
    public void setKeyVersion(String keyVersion) {
        this.keyVersion = keyVersion;
    }

    /**
     * @return the lastChargeDatetime
     */
    public String getLastChargeDatetime() {
        return lastChargeDatetime;
    }

    /**
     * @param lastChargeDatetime the lastChargeDatetime to set
     */
    public void setLastChargeDatetime(String lastChargeDatetime) {
        this.lastChargeDatetime = lastChargeDatetime;
    }

    /**
     * @return the reserveField
     */
    public String getReserveField() {
        return reserveField;
    }

    /**
     * @param reserveField the reserveField to set
     */
    public void setReserveField(String reserveField) {
        this.reserveField = reserveField;
    }
}
        /**
     * @return the dealNoDiscountFee
     */

