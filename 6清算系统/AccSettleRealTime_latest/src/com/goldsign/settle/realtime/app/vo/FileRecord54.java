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
public class FileRecord54 extends FileRecordTacBase {

    private String dealDatetime;//7	日期时间
    private int cardStatusId;//11	卡状态代码
    private int dealFee;//12	交易金额

    private int dealBalanceFee;//13	余额

    private int cardChargeSeq;//14	票卡充值交易计数

    private int cardConsumeSeq;//15	票卡扣款交易计数

    private String payModeId;//16	支付类型
    private String receiptId;//17	凭证ID
    private String tac;//18	交易认证码
    private String entryLineId;//19	入口线路代码
    private String entryStationId;//19	入口站点代码
    private String entrySamLogicalId;//20	入口SAM逻辑卡号
    private String entryDatetime;//21	进站日期时间

    private String lastSamLogicalId;//24	上次交易SAM逻辑卡号
    private String lastDealDatetime;//25	上次交易日期时间

    private int dealNoDiscountFee;//26	钱包交易金额

    private String limitMode;//14	限制使用模式
    private String limitEntryStation;//15	限制进站代码
    private String limitExitStation;//16	限制出站代码
    private String purseOpType;//钱包操作类型增值或减值 用于TAC码校验辅助判断

    private String dealFee_s;//12	交易金额
    private String dealBalanceFee_s;//13	余额
    private String dealNoDiscountFee_s;//26	钱包交易金额

    private String cardChargeSeq_s;//14	票卡充值交易计数
    private String cardConsumeSeq_s;//15	票卡扣款交易计数

    private String entryDatetime_s;//21	进站日期时间
    private String lastDealDatetime_s;//25	上次交易日期时间
    private String dealDatetime_s;//7	日期时间
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
    /**
     * 互联网相关
     */
    private String orderNo;//订单号

    private String keyVersion;//密钥版本
    private String lastChargeDatetime;//最后一次充值时间
    private String lastChargeDatetime_s;//最后一次充值时间

    private String issueUnit;//发卡机构
    private String keyIndex;//密钥索引
    private String keyRandomNo;//伪随机数
    private String algorithmFlag;//算法标识
    private String holderName;//持卡人姓名
    private String identityType;//证件类型
    private String identityId;//证件号码
    private int buyTkNum;//购票数量
    private int buyTkUnitFee;//购票单价
    private String buyTkNum_s;//购票数量
    private String buyTkUnitFee_s;//购票单价

    /**
     * 一卡通优惠
     */
    private String discountYearMonth;//优惠月
    private int accumulateConsumeFee;//优惠月累计消费金额
    private int intervalBetweenBusMetro;//联乘时间间隔
    private String lastBusDealDatetime;//上次公交交易时间

    private String accumulateConsumeFee_s;//优惠月累计消费金额
    private String intervalBetweenBusMetro_s;//联乘时间间隔
    private String lastBusDealDatetime_s;//上次公交交易时间
    
    private int accumulateConsumeNum;//优惠月累计消费次数
    private String tctActiveDatetime;//	乘次票激活日期时间
    
    private String accumulateConsumeNum_s;//优惠月累计消费次数
    private String tctActiveDatetime_s;//乘次票激活日期时间

    /**
     * @return the buyTkNum_s
     */
    public String getBuyTkNum_s() {
        return buyTkNum_s;
    }

    /**
     * @param buyTkNum_s the buyTkNum_s to set
     */
    public void setBuyTkNum_s(String buyTkNum_s) {
        this.buyTkNum_s = buyTkNum_s;
    }

    /**
     * @return the buyTkUnitFee_s
     */
    public String getBuyTkUnitFee_s() {
        return buyTkUnitFee_s;
    }

    /**
     * @param buyTkUnitFee_s the buyTkUnitFee_s to set
     */
    public void setBuyTkUnitFee_s(String buyTkUnitFee_s) {
        this.buyTkUnitFee_s = buyTkUnitFee_s;
    }

    /**
     * @return the issueUnit
     */
    public String getIssueUnit() {
        return issueUnit;
    }

    /**
     * @param issueUnit the issueUnit to set
     */
    public void setIssueUnit(String issueUnit) {
        this.issueUnit = issueUnit;
    }

    /**
     * @return the keyIndex
     */
    public String getKeyIndex() {
        return keyIndex;
    }

    /**
     * @param keyIndex the keyIndex to set
     */
    public void setKeyIndex(String keyIndex) {
        this.keyIndex = keyIndex;
    }

    /**
     * @return the keyRandomNo
     */
    public String getKeyRandomNo() {
        return keyRandomNo;
    }

    /**
     * @param keyRandomNo the keyRandomNo to set
     */
    public void setKeyRandomNo(String keyRandomNo) {
        this.keyRandomNo = keyRandomNo;
    }

    /**
     * @return the algorithmFlag
     */
    public String getAlgorithmFlag() {
        return algorithmFlag;
    }

    /**
     * @param algorithmFlag the algorithmFlag to set
     */
    public void setAlgorithmFlag(String algorithmFlag) {
        this.algorithmFlag = algorithmFlag;
    }

    /**
     * @return the holderName
     */
    public String getHolderName() {
        return holderName;
    }

    /**
     * @param holderName the holderName to set
     */
    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    /**
     * @return the identityType
     */
    public String getIdentityType() {
        return identityType;
    }

    /**
     * @param identityType the identityType to set
     */
    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    /**
     * @return the identityId
     */
    public String getIdentityId() {
        return identityId;
    }

    /**
     * @param identityId the identityId to set
     */
    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    /**
     * @return the buyTkNum
     */
    public int getBuyTkNum() {
        return buyTkNum;
    }

    /**
     * @param buyTkNum the buyTkNum to set
     */
    public void setBuyTkNum(int buyTkNum) {
        this.buyTkNum = buyTkNum;
    }

    /**
     * @return the buyTkUnitFee
     */
    public int getBuyTkUnitFee() {
        return buyTkUnitFee;
    }

    /**
     * @param buyTkUnitFee the buyTkUnitFee to set
     */
    public void setBuyTkUnitFee(int buyTkUnitFee) {
        this.buyTkUnitFee = buyTkUnitFee;
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
     * @return the dealBalanceFee_s
     */
    public String getDealBalanceFee_s() {
        return dealBalanceFee_s;
    }

    /**
     * @param dealBalanceFee_s the dealBalanceFee_s to set
     */
    public void setDealBalanceFee_s(String dealBalanceFee_s) {
        this.dealBalanceFee_s = dealBalanceFee_s;
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
     * @return the lastDealDatetime_s
     */
    public String getLastDealDatetime_s() {
        return lastDealDatetime_s;
    }

    /**
     * @param lastDealDatetime_s the lastDealDatetime_s to set
     */
    public void setLastDealDatetime_s(String lastDealDatetime_s) {
        this.lastDealDatetime_s = lastDealDatetime_s;
    }

    /**
     * @return the dealDatetime_s
     */
    public String getDealDatetime_s() {
        return dealDatetime_s;
    }

    /**
     * @param dealDatetime_s the dealDatetime_s to set
     */
    public void setDealDatetime_s(String dealDatetime_s) {
        this.dealDatetime_s = dealDatetime_s;
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
     * @return the deal_balanceFee
     */
    public int getDealBalanceFee() {
        return dealBalanceFee;
    }

    /**
     * @param deal_balanceFee the deal_balanceFee to set
     */
    public void setDealBalanceFee(int dealBalanceFee) {
        this.dealBalanceFee = dealBalanceFee;
    }

    /**
     * @return the cardChargeSeq
     */
    public int getCardChargeSeq() {
        return cardChargeSeq;
    }

    /**
     * @param cardChargeSeq the cardChargeSeq to set
     */
    public void setCardChargeSeq(int cardChargeSeq) {
        this.cardChargeSeq = cardChargeSeq;
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
     * @return the dealNoDiscountFee
     */
    public int getDealNoDiscountFee() {
        return dealNoDiscountFee;
    }

    /**
     * @param dealNoDiscountFee the dealNoDiscountFee to set
     */
    public void setDealNoDiscountFee(int dealNoDiscountFee) {
        this.dealNoDiscountFee = dealNoDiscountFee;
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
     * @return the lastChargeDatetime_s
     */
    public String getLastChargeDatetime_s() {
        return lastChargeDatetime_s;
    }

    /**
     * @param lastChargeDatetime_s the lastChargeDatetime_s to set
     */
    public void setLastChargeDatetime_s(String lastChargeDatetime_s) {
        this.lastChargeDatetime_s = lastChargeDatetime_s;
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
