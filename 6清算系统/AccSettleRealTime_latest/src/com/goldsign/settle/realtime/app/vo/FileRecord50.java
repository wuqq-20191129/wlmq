/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordTacBase;

/**
 *
 * @author hejj
 */
public class FileRecord50 extends FileRecordTacBase {



    /**
     * 卡相关
     */
    private String zoneId;//区段代码ID
    /**
     * 交易
     */
    private int saleFee;//金额
    private String saleTime;//日期时间
    private String depositType;//成本押金类型
    private int depositFee;//成本押金金额
    private int cardStatusId;//票卡状态ID
    private String payType;//支付方式
    private String cardLogicIdPay;//支付卡逻辑卡号
    private int auxiFee;//手续费
    private String purseOpType;//钱包操作类型增值或减值 用于TAC码校验辅助判断
    
    private String saleFee_s;//金额
    private String auxiFee_s;//手续费
    private String saleTime_s;//日期时间
    private String depositFee_s;//成本押金金额
    private String cardStatusId_s;//票卡状态ID
    private String cardTradSeq_s;//票卡序列号
    private String samTradeSeq_s;//SAM卡脱机交易流水号 
    private String cardCountUsed_s;//使用次数
    
    /**
     * 校验
     */
    private int cardTradSeq;//票卡序列号
    private int samTradeSeq;//SAM卡脱机交易流水号  
    private String tac;//tac
    private int cardCountUsed;//使用次数
    /**
     * 公交TAC校验相关
     */
    private String busTacDealType;//TAC交易类型
    private String busTacDevId;//TAC设备编码
    
    /**
     * 互联网支付相关
     */
    private String orderNo;//订单号
    

    /**
     * @return the zoneId
     */
    public String getZoneId() {
        return zoneId;
    }

    /**
     * @param zoneId the zoneId to set
     */
    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
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
     * @return the cardLogicIdPay
     */
    public String getCardLogicIdPay() {
        return cardLogicIdPay;
    }

    /**
     * @param cardLogicIdPay the cardLogicIdPay to set
     */
    public void setCardLogicIdPay(String cardLogicIdPay) {
        this.cardLogicIdPay = cardLogicIdPay;
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
     * @return the samTradeSeq
     */
    public int getSamTradeSeq() {
        return samTradeSeq;
    }

    /**
     * @param samTradeSeq the samTradeSeq to set
     */
    public void setSamTradeSeq(int samTradeSeq) {
        this.samTradeSeq = samTradeSeq;
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
     * @return the cardCountUsed
     */
    public int getCardCountUsed() {
        return cardCountUsed;
    }

    /**
     * @param cardCountUsed the cardCountUsed to set
     */
    public void setCardCountUsed(int cardCountUsed) {
        this.cardCountUsed = cardCountUsed;
    }

    /**
     * @return the payType
     */
    public String getPayType() {
        return payType;
    }

    /**
     * @param payType the payType to set
     */
    public void setPayType(String payType) {
        this.payType = payType;
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
     * @return the busTacDevIde
     */
    public String getBusTacDevId() {
        return busTacDevId;
    }

    /**
     * @param busTacDevIde the busTacDevIde to set
     */
    public void setBusTacDevId(String busTacDevIde) {
        this.busTacDevId = busTacDevIde;
    }

    /**
     * @return the orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

   
    /**
     * 操作
     */
    // private String operatorId;//操作员ID
    // private String shiftId;//BOM班次序号
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
     * @return the samTradeSeq_s
     */
    public String getSamTradeSeq_s() {
        return samTradeSeq_s;
    }

    /**
     * @param samTradeSeq_s the samTradeSeq_s to set
     */
    public void setSamTradeSeq_s(String samTradeSeq_s) {
        this.samTradeSeq_s = samTradeSeq_s;
    }

    /**
     * @return the cardCountUsed_s
     */
    public String getCardCountUsed_s() {
        return cardCountUsed_s;
    }

    /**
     * @param cardCountUsed_s the cardCountUsed_s to set
     */
    public void setCardCountUsed_s(String cardCountUsed_s) {
        this.cardCountUsed_s = cardCountUsed_s;
    }
}
