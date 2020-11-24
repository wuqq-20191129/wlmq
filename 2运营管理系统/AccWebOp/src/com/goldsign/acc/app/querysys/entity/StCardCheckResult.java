package com.goldsign.acc.app.querysys.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class StCardCheckResult implements Serializable {
    private String cardMainId;

    private String cardSubId;

    private String cardLogicalId;

    private String cardPhysicalId;

    private Long totalChargeNum;

    private Long totalConsumeNum;

    private BigDecimal totalChargeFee;

    private BigDecimal totalConsumeFee;

    private Long cardChargeSeq;

    private Long cardConsumeSeq;

    private BigDecimal systemBalanceFee;

    private BigDecimal cardBalanceFee;

    private BigDecimal diffCardSysFee;

    private BigDecimal diffCardSysNum;

    private String balanceWaterNo;

    private Long balanceWaterNoSub;

    private BigDecimal cardMoney;
    
    private String operator;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    private static final long serialVersionUID = 1L;

    public StCardCheckResult(String cardMainId, String cardSubId, String cardLogicalId, String cardPhysicalId, Long totalChargeNum, Long totalConsumeNum, BigDecimal totalChargeFee, BigDecimal totalConsumeFee, Long cardChargeSeq, Long cardConsumeSeq, BigDecimal systemBalanceFee, BigDecimal cardBalanceFee, BigDecimal diffCardSysFee, BigDecimal diffCardSysNum, String balanceWaterNo, Long balanceWaterNoSub, BigDecimal cardMoney) {
        this.cardMainId = cardMainId;
        this.cardSubId = cardSubId;
        this.cardLogicalId = cardLogicalId;
        this.cardPhysicalId = cardPhysicalId;
        this.totalChargeNum = totalChargeNum;
        this.totalConsumeNum = totalConsumeNum;
        this.totalChargeFee = totalChargeFee;
        this.totalConsumeFee = totalConsumeFee;
        this.cardChargeSeq = cardChargeSeq;
        this.cardConsumeSeq = cardConsumeSeq;
        this.systemBalanceFee = systemBalanceFee;
        this.cardBalanceFee = cardBalanceFee;
        this.diffCardSysFee = diffCardSysFee;
        this.diffCardSysNum = diffCardSysNum;
        this.balanceWaterNo = balanceWaterNo;
        this.balanceWaterNoSub = balanceWaterNoSub;
        this.cardMoney = cardMoney;
    }

    public StCardCheckResult() {
        super();
    }

    public String getCardMainId() {
        return cardMainId;
    }

    public void setCardMainId(String cardMainId) {
        this.cardMainId = cardMainId == null ? null : cardMainId.trim();
    }

    public String getCardSubId() {
        return cardSubId;
    }

    public void setCardSubId(String cardSubId) {
        this.cardSubId = cardSubId == null ? null : cardSubId.trim();
    }

    public String getCardLogicalId() {
        return cardLogicalId;
    }

    public void setCardLogicalId(String cardLogicalId) {
        this.cardLogicalId = cardLogicalId == null ? null : cardLogicalId.trim();
    }

    public String getCardPhysicalId() {
        return cardPhysicalId;
    }

    public void setCardPhysicalId(String cardPhysicalId) {
        this.cardPhysicalId = cardPhysicalId == null ? null : cardPhysicalId.trim();
    }

    public Long getTotalChargeNum() {
        return totalChargeNum;
    }

    public void setTotalChargeNum(Long totalChargeNum) {
        this.totalChargeNum = totalChargeNum;
    }

    public Long getTotalConsumeNum() {
        return totalConsumeNum;
    }

    public void setTotalConsumeNum(Long totalConsumeNum) {
        this.totalConsumeNum = totalConsumeNum;
    }

    public BigDecimal getTotalChargeFee() {
        return totalChargeFee;
    }

    public void setTotalChargeFee(BigDecimal totalChargeFee) {
        this.totalChargeFee = totalChargeFee;
    }

    public BigDecimal getTotalConsumeFee() {
        return totalConsumeFee;
    }

    public void setTotalConsumeFee(BigDecimal totalConsumeFee) {
        this.totalConsumeFee = totalConsumeFee;
    }

    public Long getCardChargeSeq() {
        return cardChargeSeq;
    }

    public void setCardChargeSeq(Long cardChargeSeq) {
        this.cardChargeSeq = cardChargeSeq;
    }

    public Long getCardConsumeSeq() {
        return cardConsumeSeq;
    }

    public void setCardConsumeSeq(Long cardConsumeSeq) {
        this.cardConsumeSeq = cardConsumeSeq;
    }

    public BigDecimal getSystemBalanceFee() {
        return systemBalanceFee;
    }

    public void setSystemBalanceFee(BigDecimal systemBalanceFee) {
        this.systemBalanceFee = systemBalanceFee;
    }

    public BigDecimal getCardBalanceFee() {
        return cardBalanceFee;
    }

    public void setCardBalanceFee(BigDecimal cardBalanceFee) {
        this.cardBalanceFee = cardBalanceFee;
    }

    public BigDecimal getDiffCardSysFee() {
        return diffCardSysFee;
    }

    public void setDiffCardSysFee(BigDecimal diffCardSysFee) {
        this.diffCardSysFee = diffCardSysFee;
    }

    public BigDecimal getDiffCardSysNum() {
        return diffCardSysNum;
    }

    public void setDiffCardSysNum(BigDecimal diffCardSysNum) {
        this.diffCardSysNum = diffCardSysNum;
    }

    public String getBalanceWaterNo() {
        return balanceWaterNo;
    }

    public void setBalanceWaterNo(String balanceWaterNo) {
        this.balanceWaterNo = balanceWaterNo == null ? null : balanceWaterNo.trim();
    }

    public Long getBalanceWaterNoSub() {
        return balanceWaterNoSub;
    }

    public void setBalanceWaterNoSub(Long balanceWaterNoSub) {
        this.balanceWaterNoSub = balanceWaterNoSub;
    }

    public BigDecimal getCardMoney() {
        return cardMoney;
    }

    public void setCardMoney(BigDecimal cardMoney) {
        this.cardMoney = cardMoney;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        StCardCheckResult other = (StCardCheckResult) that;
        return (this.getCardMainId() == null ? other.getCardMainId() == null : this.getCardMainId().equals(other.getCardMainId()))
            && (this.getCardSubId() == null ? other.getCardSubId() == null : this.getCardSubId().equals(other.getCardSubId()))
            && (this.getCardLogicalId() == null ? other.getCardLogicalId() == null : this.getCardLogicalId().equals(other.getCardLogicalId()))
            && (this.getCardPhysicalId() == null ? other.getCardPhysicalId() == null : this.getCardPhysicalId().equals(other.getCardPhysicalId()))
            && (this.getTotalChargeNum() == null ? other.getTotalChargeNum() == null : this.getTotalChargeNum().equals(other.getTotalChargeNum()))
            && (this.getTotalConsumeNum() == null ? other.getTotalConsumeNum() == null : this.getTotalConsumeNum().equals(other.getTotalConsumeNum()))
            && (this.getTotalChargeFee() == null ? other.getTotalChargeFee() == null : this.getTotalChargeFee().equals(other.getTotalChargeFee()))
            && (this.getTotalConsumeFee() == null ? other.getTotalConsumeFee() == null : this.getTotalConsumeFee().equals(other.getTotalConsumeFee()))
            && (this.getCardChargeSeq() == null ? other.getCardChargeSeq() == null : this.getCardChargeSeq().equals(other.getCardChargeSeq()))
            && (this.getCardConsumeSeq() == null ? other.getCardConsumeSeq() == null : this.getCardConsumeSeq().equals(other.getCardConsumeSeq()))
            && (this.getSystemBalanceFee() == null ? other.getSystemBalanceFee() == null : this.getSystemBalanceFee().equals(other.getSystemBalanceFee()))
            && (this.getCardBalanceFee() == null ? other.getCardBalanceFee() == null : this.getCardBalanceFee().equals(other.getCardBalanceFee()))
            && (this.getDiffCardSysFee() == null ? other.getDiffCardSysFee() == null : this.getDiffCardSysFee().equals(other.getDiffCardSysFee()))
            && (this.getDiffCardSysNum() == null ? other.getDiffCardSysNum() == null : this.getDiffCardSysNum().equals(other.getDiffCardSysNum()))
            && (this.getBalanceWaterNo() == null ? other.getBalanceWaterNo() == null : this.getBalanceWaterNo().equals(other.getBalanceWaterNo()))
            && (this.getBalanceWaterNoSub() == null ? other.getBalanceWaterNoSub() == null : this.getBalanceWaterNoSub().equals(other.getBalanceWaterNoSub()))
            && (this.getCardMoney() == null ? other.getCardMoney() == null : this.getCardMoney().equals(other.getCardMoney()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCardMainId() == null) ? 0 : getCardMainId().hashCode());
        result = prime * result + ((getCardSubId() == null) ? 0 : getCardSubId().hashCode());
        result = prime * result + ((getCardLogicalId() == null) ? 0 : getCardLogicalId().hashCode());
        result = prime * result + ((getCardPhysicalId() == null) ? 0 : getCardPhysicalId().hashCode());
        result = prime * result + ((getTotalChargeNum() == null) ? 0 : getTotalChargeNum().hashCode());
        result = prime * result + ((getTotalConsumeNum() == null) ? 0 : getTotalConsumeNum().hashCode());
        result = prime * result + ((getTotalChargeFee() == null) ? 0 : getTotalChargeFee().hashCode());
        result = prime * result + ((getTotalConsumeFee() == null) ? 0 : getTotalConsumeFee().hashCode());
        result = prime * result + ((getCardChargeSeq() == null) ? 0 : getCardChargeSeq().hashCode());
        result = prime * result + ((getCardConsumeSeq() == null) ? 0 : getCardConsumeSeq().hashCode());
        result = prime * result + ((getSystemBalanceFee() == null) ? 0 : getSystemBalanceFee().hashCode());
        result = prime * result + ((getCardBalanceFee() == null) ? 0 : getCardBalanceFee().hashCode());
        result = prime * result + ((getDiffCardSysFee() == null) ? 0 : getDiffCardSysFee().hashCode());
        result = prime * result + ((getDiffCardSysNum() == null) ? 0 : getDiffCardSysNum().hashCode());
        result = prime * result + ((getBalanceWaterNo() == null) ? 0 : getBalanceWaterNo().hashCode());
        result = prime * result + ((getBalanceWaterNoSub() == null) ? 0 : getBalanceWaterNoSub().hashCode());
        result = prime * result + ((getCardMoney() == null) ? 0 : getCardMoney().hashCode());
        return result;
    }
}