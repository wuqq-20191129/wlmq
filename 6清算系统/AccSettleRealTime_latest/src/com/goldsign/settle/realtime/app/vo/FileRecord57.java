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
public class FileRecord57 extends FileRecordTacBase {

    /**
     * @return the returnBalanceFee_s
     */
    public String getReturnBalanceFee_s() {
        return returnBalanceFee_s;
    }

    /**
     * @param returnBalanceFee_s the returnBalanceFee_s to set
     */
    public void setReturnBalanceFee_s(String returnBalanceFee_s) {
        this.returnBalanceFee_s = returnBalanceFee_s;
    }

    /**
     * @return the returnDepositFee_s
     */
    public String getReturnDepositFee_s() {
        return returnDepositFee_s;
    }

    /**
     * @param returnDepositFee_s the returnDepositFee_s to set
     */
    public void setReturnDepositFee_s(String returnDepositFee_s) {
        this.returnDepositFee_s = returnDepositFee_s;
    }

    /**
     * @return the penaltyFee_s
     */
    public String getPenaltyFee_s() {
        return penaltyFee_s;
    }

    /**
     * @param penaltyFee_s the penaltyFee_s to set
     */
    public void setPenaltyFee_s(String penaltyFee_s) {
        this.penaltyFee_s = penaltyFee_s;
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
     * @return the returnDatetime_s
     */
    public String getReturnDatetime_s() {
        return returnDatetime_s;
    }

    /**
     * @param returnDatetime_s the returnDatetime_s to set
     */
    public void setReturnDatetime_s(String returnDatetime_s) {
        this.returnDatetime_s = returnDatetime_s;
    }

    /**
     * @return the applyDatetime_s
     */
    public String getApplyDatetime_s() {
        return applyDatetime_s;
    }

    /**
     * @param applyDatetime_s the applyDatetime_s to set
     */
    public void setApplyDatetime_s(String applyDatetime_s) {
        this.applyDatetime_s = applyDatetime_s;
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

    private int returnBalanceFee;//11	退卡内金额
    private int returnDepositFee;//12	退押金
    private int penaltyFee;//13	罚款
    private String penaltyReasonId;//14	罚款原因
    private int cardConsumeSeq;//15	票卡扣款交易计数
    private String returnType;//16	退款类型
    private String returnDatetime;//17	日期时间
    private String receiptId;//18	凭证ID
    private String applyDatetime;//19	申请日期时间
    private String tac;//20	交易认证码
    private int auxiFee;//23	手续费
    //TAC校验时使用
    private String payModeId;//TAC校验时使用
    private String purseOpType;//钱包操作类型

    /**
     * 公交TAC校验相关
     */
    private String busTacDealType;//TAC交易类型
    private String busTacDevId;//TAC设备编码

    private String returnBalanceFee_s;//11	退卡内金额
    private String returnDepositFee_s;//12	退押金
    private String penaltyFee_s;//13	罚款
    private String auxiFee_s;//23	手续费
    private String returnDatetime_s;//17	日期时间
    private String applyDatetime_s;//19	申请日期时间

    private String cardConsumeSeq_s;//15	票卡扣款交易计数

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
     * @return the penaltyReasonId
     */
    public String getPenaltyReasonId() {
        return penaltyReasonId;
    }

    /**
     * @param penaltyReasonId the penaltyReasonId to set
     */
    public void setPenaltyReasonId(String penaltyReasonId) {
        this.penaltyReasonId = penaltyReasonId;
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
     * @return the returnDatetime
     */
    public String getReturnDatetime() {
        return returnDatetime;
    }

    /**
     * @param returnDatetime the returnDatetime to set
     */
    public void setReturnDatetime(String returnDatetime) {
        this.returnDatetime = returnDatetime;
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
     * @return the applyDatetime
     */
    public String getApplyDatetime() {
        return applyDatetime;
    }

    /**
     * @param applyDatetime the applyDatetime to set
     */
    public void setApplyDatetime(String applyDatetime) {
        this.applyDatetime = applyDatetime;
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

}
