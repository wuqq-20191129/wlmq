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
public class FileRecord53 extends FileRecordBase {

   

    /**
     * @return the entryDatetime_c
     */
    // private String samTradeSeq;//6	SAM卡脱机交易流水号
    private String entryDatetime;//10	进站日期时间
    private int balanceFee;//12	余额
    private String limitMode;//14	限制使用模式
    private String limitEntryStation;//15	限制进站代码
    private String limitExitStation;//16	限制出站代码
    private int cardStatusId;//11 票卡状态ID
    //模式相关
    private String workMode;//工作模式
    private String cardAppMode;//卡应用模式

    private int cardConsumeSeq;//15	票卡扣款交易计数

    private String entryDatetime_s;//10	进站日期时间
    private String balanceFee_s;//12	余额
    private String cardConsumeSeq_s;//15	票卡扣款交易计数
    
    private String tctActiveDatetime;//	乘车票激活日期时间
    private String tctActiveDatetime_s;//	乘车票激活日期时间
    
    
        /**
     * 一卡通优惠
     */
    private String discountYearMonth;//优惠月
    private int accumulateConsumeFee;//优惠月累计消费金额
    private int intervalBetweenBusMetro;//联乘时间间隔
    private String lastBusDealDatetime;//上次公交交易时间
     private int accumulateConsumeNum;//优惠月累计消费次数

    private String accumulateConsumeFee_s;//优惠月累计消费金额
    private String intervalBetweenBusMetro_s;//联乘时间间隔
    private String lastBusDealDatetime_s;//上次公交交易时间
     private String accumulateConsumeNum_s;//优惠月累计消费次数
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
    /**
     * @return the entryDatetime_s
     */
    public String getEntryDatetime_s() {
        return entryDatetime_s;
    }

    /**
     * @param entryDatetime_s the entryDatetime_s to set
     */
    public void setEntryDatetime_s(String entryDatetime_s) {
        this.entryDatetime_s = entryDatetime_s;
    }

    /**
     * @return the balanceFee_s
     */
    public String getBalanceFee_s() {
        return balanceFee_s;
    }

    /**
     * @param balanceFee_s the balanceFee_s to set
     */
    public void setBalanceFee_s(String balanceFee_s) {
        this.balanceFee_s = balanceFee_s;
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
     * @return the balanceFee
     */
    public int getBalanceFee() {
        return balanceFee;
    }

    /**
     * @param balanceFee the balanceFee to set
     */
    public void setBalanceFee(int balanceFee) {
        this.balanceFee = balanceFee;
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
     * @return the discountYearMonth
     */
    public String getDiscountYearMonth() {
        return discountYearMonth;
    }

    /**
     * @param discountYearMonth the discountYearMonth to set
     */
    public void setDiscountYearMonth(String discountYearMonth) {
        this.discountYearMonth = discountYearMonth;
    }

    /**
     * @return the accumulateConsumeFee
     */
    public int getAccumulateConsumeFee() {
        return accumulateConsumeFee;
    }

    /**
     * @param accumulateConsumeFee the accumulateConsumeFee to set
     */
    public void setAccumulateConsumeFee(int accumulateConsumeFee) {
        this.accumulateConsumeFee = accumulateConsumeFee;
    }

    /**
     * @return the intervalBetweenBusMetro
     */
    public int getIntervalBetweenBusMetro() {
        return intervalBetweenBusMetro;
    }

    /**
     * @param intervalBetweenBusMetro the intervalBetweenBusMetro to set
     */
    public void setIntervalBetweenBusMetro(int intervalBetweenBusMetro) {
        this.intervalBetweenBusMetro = intervalBetweenBusMetro;
    }

    /**
     * @return the lastBusDealDatetime
     */
    public String getLastBusDealDatetime() {
        return lastBusDealDatetime;
    }

    /**
     * @param lastBusDealDatetime the lastBusDealDatetime to set
     */
    public void setLastBusDealDatetime(String lastBusDealDatetime) {
        this.lastBusDealDatetime = lastBusDealDatetime;
    }

    /**
     * @return the accumulateConsumeFee_s
     */
    public String getAccumulateConsumeFee_s() {
        return accumulateConsumeFee_s;
    }

    /**
     * @param accumulateConsumeFee_s the accumulateConsumeFee_s to set
     */
    public void setAccumulateConsumeFee_s(String accumulateConsumeFee_s) {
        this.accumulateConsumeFee_s = accumulateConsumeFee_s;
    }

    /**
     * @return the intervalBetweenBusMetro_s
     */
    public String getIntervalBetweenBusMetro_s() {
        return intervalBetweenBusMetro_s;
    }

    /**
     * @param intervalBetweenBusMetro_s the intervalBetweenBusMetro_s to set
     */
    public void setIntervalBetweenBusMetro_s(String intervalBetweenBusMetro_s) {
        this.intervalBetweenBusMetro_s = intervalBetweenBusMetro_s;
    }

    /**
     * @return the lastBusDealDatetime_s
     */
    public String getLastBusDealDatetime_s() {
        return lastBusDealDatetime_s;
    }

    /**
     * @param lastBusDealDatetime_s the lastBusDealDatetime_s to set
     */
    public void setLastBusDealDatetime_s(String lastBusDealDatetime_s) {
        this.lastBusDealDatetime_s = lastBusDealDatetime_s;
    }

    /**
     * @return the accumulateConsumeNum
     */
    public int getAccumulateConsumeNum() {
        return accumulateConsumeNum;
    }

    /**
     * @param accumulateConsumeNum the accumulateConsumeNum to set
     */
    public void setAccumulateConsumeNum(int accumulateConsumeNum) {
        this.accumulateConsumeNum = accumulateConsumeNum;
    }

    /**
     * @return the accumulateConsumeNum_s
     */
    public String getAccumulateConsumeNum_s() {
        return accumulateConsumeNum_s;
    }

    /**
     * @param accumulateConsumeNum_s the accumulateConsumeNum_s to set
     */
    public void setAccumulateConsumeNum_s(String accumulateConsumeNum_s) {
        this.accumulateConsumeNum_s = accumulateConsumeNum_s;
    }

}
