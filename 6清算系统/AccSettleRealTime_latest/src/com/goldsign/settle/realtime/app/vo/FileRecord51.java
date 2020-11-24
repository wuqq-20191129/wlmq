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
public class FileRecord51 extends FileRecordBase {

    /**
     * @return the saleTime_s
     */
    public String getSaleTime_s() {
        return saleTime_s;
    }

    /**
     * @param saleTime_s the saleTime_s to set
     */
    public void setSaleTime_s(String saleTime_s) {
        this.saleTime_s = saleTime_s;
    }

    /**
     * @return the depositFee_s
     */
    public String getDepositFee_s() {
        return depositFee_s;
    }

    /**
     * @param depositFee_s the depositFee_s to set
     */
    public void setDepositFee_s(String depositFee_s) {
        this.depositFee_s = depositFee_s;
    }

    /**
     * @return the auxiFee_s
     */
    public String getAuxiFee_s() {
        return auxiFee_s;
    }

    /**
     * @param auxiFee_s the auxiFee_s to set
     */
    public void setAuxiFee_s(String auxiFee_s) {
        this.auxiFee_s = auxiFee_s;
    }

    /**
     * @return the cardStatusId_s
     */
    public String getCardStatusId_s() {
        return cardStatusId_s;
    }

    /**
     * @param cardStatusId_s the cardStatusId_s to set
     */
    public void setCardStatusId_s(String cardStatusId_s) {
        this.cardStatusId_s = cardStatusId_s;
    }

    /**
     * @return the cardTradSeq_s
     */
    public String getCardTradSeq_s() {
        return cardTradSeq_s;
    }

    /**
     * @param cardTradSeq_s the cardTradSeq_s to set
     */
    public void setCardTradSeq_s(String cardTradSeq_s) {
        this.cardTradSeq_s = cardTradSeq_s;
    }

    /**
     * 卡相关
     */
    // private String zoneId;//区段代码ID
    /**
     * 交易
     */
    // private int saleFee;//金额
    private String saleTime;//日期时间（14）
    private String depositType;//成本押金类型（12）
    private int depositFee;//成本押金金额（13）
    private int cardStatusId;//票卡状态ID（8）
    //private String paymodeId;//支付方式
    //private String cardLogicIdPay;//支付卡逻辑卡号
    private int auxiFee;//手续费（18）
    private int saleFee ;//次票发售金额

    private String purseOpType;//钱包操作类型增值或减值 用于TAC码校验辅助判断
    private String receiptId;//凭证（15）
    /**
     * 校验
     */
    private int cardTradSeq;//票卡序列号（9）
    //  private int samTradeSeq;//SAM卡脱机交易流水号（11）
    // private String tac;//tac
    // private int cardCountUsed;//使用次数

    private String waterNoBusiness;//脱机业务流水

    private String saleTime_s;//日期时间（14）
    private String depositFee_s;//成本押金金额（13）
    private String auxiFee_s;//手续费（18）
    private String cardStatusId_s;//票卡状态ID（8）
    private String cardTradSeq_s;//票卡序列号（9）
    private String saleFee_s ;//次票发售金额

    /**
     * @return the saleTime
     */
    public String getSaleTime() {
        return saleTime;
    }

    /**
     * @param saleTime the saleTime to set
     */
    public void setSaleTime(String saleTime) {
        this.saleTime = saleTime;
    }

    /**
     * @return the depositType
     */
    public String getDepositType() {
        return depositType;
    }

    /**
     * @param depositType the depositType to set
     */
    public void setDepositType(String depositType) {
        this.depositType = depositType;
    }

    /**
     * @return the depositFee
     */
    public int getDepositFee() {
        return depositFee;
    }

    /**
     * @param depositFee the depositFee to set
     */
    public void setDepositFee(int depositFee) {
        this.depositFee = depositFee;
    }

    /**
     * @return the cardStatusId
     */
    public int getCardStatusId() {
        return cardStatusId;
    }

    /**
     * @param cardStatusId the cardStatusId to set
     */
    public void setCardStatusId(int cardStatusId) {
        this.cardStatusId = cardStatusId;
    }

    /**
     * @return the auxiFee
     */
    public int getAuxiFee() {
        return auxiFee;
    }

    /**
     * @param auxiFee the auxiFee to set
     */
    public void setAuxiFee(int auxiFee) {
        this.auxiFee = auxiFee;
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
     * @return the receiptId
     */
    public String getReceiptId() {
        return receiptId;
    }

    /**
     * @param receiptId the receiptId to set
     */
    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    /**
     * @return the cardTradSeq
     */
    public int getCardTradSeq() {
        return cardTradSeq;
    }

    /**
     * @param cardTradSeq the cardTradSeq to set
     */
    public void setCardTradSeq(int cardTradSeq) {
        this.cardTradSeq = cardTradSeq;
    }

    /**
     * @return the waterNoBusiness
     */
    public String getWaterNoBusiness() {
        return waterNoBusiness;
    }

    /**
     * @param waterNoBusiness the waterNoBusiness to set
     */
    public void setWaterNoBusiness(String waterNoBusiness) {
        this.waterNoBusiness = waterNoBusiness;
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
     * @return the saleFee_s
     */
    public String getSaleFee_s() {
        return saleFee_s;
    }

    /**
     * @param saleFee_s the saleFee_s to set
     */
    public void setSaleFee_s(String saleFee_s) {
        this.saleFee_s = saleFee_s;
    }

}
