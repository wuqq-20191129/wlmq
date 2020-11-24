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
public class FileRecord56 extends FileRecordBase {

 

    private int cardConsumeSeq;//10	票卡扣款交易计数

    private String updateArea;//12	更新区域
    private String updateReasonId;//13	更新原因
    private String updateDatetime;//14	更新日期时间
    private String payType;//15	支付方式
    private int penaltyFee;//16	罚款金额
    private String receiptId;//17	凭证ID
    private String entryLineId;//19	入口线路代码
    private String entryStationId;//19	入口站点代码

    private String limitMode;//22	限制使用模式
    private String limitEntryStation;//23	限制进站代码
    private String limitExitStation;//24	限制出站代码
    private int cardStatusId;//卡状态代码

    private String cardConsumeSeq_s;//10	票卡扣款交易计数
    private String penaltyFee_s;//16	罚款金额
    private String updateDatetime_s;//14	更新日期时间

    //模式相关
    private String cardAppMode;//卡应用模式
    
    private String tctActiveDatetime;//	乘车票激活日期时间
    private String tctActiveDatetime_s;//	乘车票激活日期时间

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
     * @return the updateArea
     */
    public String getUpdateArea() {
        return updateArea;
    }

    /**
     * @param updateArea the updateArea to set
     */
    public void setUpdateArea(String updateArea) {
        this.updateArea = updateArea;
    }

    /**
     * @return the updateReasonId
     */
    public String getUpdateReasonId() {
        return updateReasonId;
    }

    /**
     * @param updateReasonId the updateReasonId to set
     */
    public void setUpdateReasonId(String updateReasonId) {
        this.updateReasonId = updateReasonId;
    }

    /**
     * @return the updateDatetime
     */
    public String getUpdateDatetime() {
        return updateDatetime;
    }

    /**
     * @param updateDatetime the updateDatetime to set
     */
    public void setUpdateDatetime(String updateDatetime) {
        this.updateDatetime = updateDatetime;
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
     * @return the entryLineId
     */
    public String getEntryLineId() {
        return entryLineId;
    }

    /**
     * @param entryLineId the entryLineId to set
     */
    public void setEntryLineId(String entryLineId) {
        this.entryLineId = entryLineId;
    }

    /**
     * @return the entryStationId
     */
    public String getEntryStationId() {
        return entryStationId;
    }

    /**
     * @param entryStationId the entryStationId to set
     */
    public void setEntryStationId(String entryStationId) {
        this.entryStationId = entryStationId;
    }

    /**
     * @return the limitMode
     */
    public String getLimitMode() {
        return limitMode;
    }

    /**
     * @param limitMode the limitMode to set
     */
    public void setLimitMode(String limitMode) {
        this.limitMode = limitMode;
    }

    /**
     * @return the limitEntryStation
     */
    public String getLimitEntryStation() {
        return limitEntryStation;
    }

    /**
     * @param limitEntryStation the limitEntryStation to set
     */
    public void setLimitEntryStation(String limitEntryStation) {
        this.limitEntryStation = limitEntryStation;
    }

    /**
     * @return the limitExitStation
     */
    public String getLimitExitStation() {
        return limitExitStation;
    }

    /**
     * @param limitExitStation the limitExitStation to set
     */
    public void setLimitExitStation(String limitExitStation) {
        this.limitExitStation = limitExitStation;
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
     * @return the updateDatetime_s
     */
    public String getUpdateDatetime_s() {
        return updateDatetime_s;
    }

    /**
     * @param updateDatetime_s the updateDatetime_s to set
     */
    public void setUpdateDatetime_s(String updateDatetime_s) {
        this.updateDatetime_s = updateDatetime_s;
    }

    /**
     * @return the tctActiveDatetime
     */
    public String getTctActiveDatetime() {
        return tctActiveDatetime;
    }

    /**
     * @param tctActiveDatetime the tctActiveDatetime to set
     */
    public void setTctActiveDatetime(String tctActiveDatetime) {
        this.tctActiveDatetime = tctActiveDatetime;
    }

    /**
     * @return the tctActiveDatetime_s
     */
    public String getTctActiveDatetime_s() {
        return tctActiveDatetime_s;
    }

    /**
     * @param tctActiveDatetime_s the tctActiveDatetime_s to set
     */
    public void setTctActiveDatetime_s(String tctActiveDatetime_s) {
        this.tctActiveDatetime_s = tctActiveDatetime_s;
    }

}
