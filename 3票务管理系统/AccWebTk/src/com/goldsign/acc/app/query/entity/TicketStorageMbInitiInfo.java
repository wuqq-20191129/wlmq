package com.goldsign.acc.app.query.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TicketStorageMbInitiInfo implements Serializable {
    private String logicalId;

    private String cardMainType;

    private String cardSubType;

    private String reqNo;

    private String phyId;

    private String printId;

    private Date manuTime;

    private BigDecimal cardMoney;

    private Date periAvadate;

    private String kdcVersion;

    private Date hdlTime;

    private String orderNo;

    private String statusFlag;

    private String cardType;

    private String cardAvaDays;

    private String exitLineCode;

    private String exitStationCode;

    private String model;

    private String cardProducerCode;

    private String phoneNo;

    private static final long serialVersionUID = 1L;

    public TicketStorageMbInitiInfo(String logicalId, String cardMainType, String cardSubType, String reqNo, String phyId, String printId, Date manuTime, BigDecimal cardMoney, Date periAvadate, String kdcVersion, Date hdlTime, String orderNo, String statusFlag, String cardType, String cardAvaDays, String exitLineCode, String exitStationCode, String model, String cardProducerCode, String phoneNo) {
        this.logicalId = logicalId;
        this.cardMainType = cardMainType;
        this.cardSubType = cardSubType;
        this.reqNo = reqNo;
        this.phyId = phyId;
        this.printId = printId;
        this.manuTime = manuTime;
        this.cardMoney = cardMoney;
        this.periAvadate = periAvadate;
        this.kdcVersion = kdcVersion;
        this.hdlTime = hdlTime;
        this.orderNo = orderNo;
        this.statusFlag = statusFlag;
        this.cardType = cardType;
        this.cardAvaDays = cardAvaDays;
        this.exitLineCode = exitLineCode;
        this.exitStationCode = exitStationCode;
        this.model = model;
        this.cardProducerCode = cardProducerCode;
        this.phoneNo = phoneNo;
    }

    public TicketStorageMbInitiInfo() {
        super();
    }

    public String getLogicalId() {
        return logicalId;
    }

    public void setLogicalId(String logicalId) {
        this.logicalId = logicalId == null ? null : logicalId.trim();
    }

    public String getCardMainType() {
        return cardMainType;
    }

    public void setCardMainType(String cardMainType) {
        this.cardMainType = cardMainType == null ? null : cardMainType.trim();
    }

    public String getCardSubType() {
        return cardSubType;
    }

    public void setCardSubType(String cardSubType) {
        this.cardSubType = cardSubType == null ? null : cardSubType.trim();
    }

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo == null ? null : reqNo.trim();
    }

    public String getPhyId() {
        return phyId;
    }

    public void setPhyId(String phyId) {
        this.phyId = phyId == null ? null : phyId.trim();
    }

    public String getPrintId() {
        return printId;
    }

    public void setPrintId(String printId) {
        this.printId = printId == null ? null : printId.trim();
    }

    public Date getManuTime() {
        return manuTime;
    }

    public void setManuTime(Date manuTime) {
        this.manuTime = manuTime;
    }

    public BigDecimal getCardMoney() {
        return cardMoney;
    }

    public void setCardMoney(BigDecimal cardMoney) {
        this.cardMoney = cardMoney;
    }

    public Date getPeriAvadate() {
        return periAvadate;
    }

    public void setPeriAvadate(Date periAvadate) {
        this.periAvadate = periAvadate;
    }

    public String getKdcVersion() {
        return kdcVersion;
    }

    public void setKdcVersion(String kdcVersion) {
        this.kdcVersion = kdcVersion == null ? null : kdcVersion.trim();
    }

    public Date getHdlTime() {
        return hdlTime;
    }

    public void setHdlTime(Date hdlTime) {
        this.hdlTime = hdlTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag == null ? null : statusFlag.trim();
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getCardAvaDays() {
        return cardAvaDays;
    }

    public void setCardAvaDays(String cardAvaDays) {
        this.cardAvaDays = cardAvaDays == null ? null : cardAvaDays.trim();
    }

    public String getExitLineCode() {
        return exitLineCode;
    }

    public void setExitLineCode(String exitLineCode) {
        this.exitLineCode = exitLineCode == null ? null : exitLineCode.trim();
    }

    public String getExitStationCode() {
        return exitStationCode;
    }

    public void setExitStationCode(String exitStationCode) {
        this.exitStationCode = exitStationCode == null ? null : exitStationCode.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getCardProducerCode() {
        return cardProducerCode;
    }

    public void setCardProducerCode(String cardProducerCode) {
        this.cardProducerCode = cardProducerCode == null ? null : cardProducerCode.trim();
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
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
        TicketStorageMbInitiInfo other = (TicketStorageMbInitiInfo) that;
        return (this.getLogicalId() == null ? other.getLogicalId() == null : this.getLogicalId().equals(other.getLogicalId()))
            && (this.getCardMainType() == null ? other.getCardMainType() == null : this.getCardMainType().equals(other.getCardMainType()))
            && (this.getCardSubType() == null ? other.getCardSubType() == null : this.getCardSubType().equals(other.getCardSubType()))
            && (this.getReqNo() == null ? other.getReqNo() == null : this.getReqNo().equals(other.getReqNo()))
            && (this.getPhyId() == null ? other.getPhyId() == null : this.getPhyId().equals(other.getPhyId()))
            && (this.getPrintId() == null ? other.getPrintId() == null : this.getPrintId().equals(other.getPrintId()))
            && (this.getManuTime() == null ? other.getManuTime() == null : this.getManuTime().equals(other.getManuTime()))
            && (this.getCardMoney() == null ? other.getCardMoney() == null : this.getCardMoney().equals(other.getCardMoney()))
            && (this.getPeriAvadate() == null ? other.getPeriAvadate() == null : this.getPeriAvadate().equals(other.getPeriAvadate()))
            && (this.getKdcVersion() == null ? other.getKdcVersion() == null : this.getKdcVersion().equals(other.getKdcVersion()))
            && (this.getHdlTime() == null ? other.getHdlTime() == null : this.getHdlTime().equals(other.getHdlTime()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getStatusFlag() == null ? other.getStatusFlag() == null : this.getStatusFlag().equals(other.getStatusFlag()))
            && (this.getCardType() == null ? other.getCardType() == null : this.getCardType().equals(other.getCardType()))
            && (this.getCardAvaDays() == null ? other.getCardAvaDays() == null : this.getCardAvaDays().equals(other.getCardAvaDays()))
            && (this.getExitLineCode() == null ? other.getExitLineCode() == null : this.getExitLineCode().equals(other.getExitLineCode()))
            && (this.getExitStationCode() == null ? other.getExitStationCode() == null : this.getExitStationCode().equals(other.getExitStationCode()))
            && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()))
            && (this.getCardProducerCode() == null ? other.getCardProducerCode() == null : this.getCardProducerCode().equals(other.getCardProducerCode()))
            && (this.getPhoneNo() == null ? other.getPhoneNo() == null : this.getPhoneNo().equals(other.getPhoneNo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLogicalId() == null) ? 0 : getLogicalId().hashCode());
        result = prime * result + ((getCardMainType() == null) ? 0 : getCardMainType().hashCode());
        result = prime * result + ((getCardSubType() == null) ? 0 : getCardSubType().hashCode());
        result = prime * result + ((getReqNo() == null) ? 0 : getReqNo().hashCode());
        result = prime * result + ((getPhyId() == null) ? 0 : getPhyId().hashCode());
        result = prime * result + ((getPrintId() == null) ? 0 : getPrintId().hashCode());
        result = prime * result + ((getManuTime() == null) ? 0 : getManuTime().hashCode());
        result = prime * result + ((getCardMoney() == null) ? 0 : getCardMoney().hashCode());
        result = prime * result + ((getPeriAvadate() == null) ? 0 : getPeriAvadate().hashCode());
        result = prime * result + ((getKdcVersion() == null) ? 0 : getKdcVersion().hashCode());
        result = prime * result + ((getHdlTime() == null) ? 0 : getHdlTime().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getStatusFlag() == null) ? 0 : getStatusFlag().hashCode());
        result = prime * result + ((getCardType() == null) ? 0 : getCardType().hashCode());
        result = prime * result + ((getCardAvaDays() == null) ? 0 : getCardAvaDays().hashCode());
        result = prime * result + ((getExitLineCode() == null) ? 0 : getExitLineCode().hashCode());
        result = prime * result + ((getExitStationCode() == null) ? 0 : getExitStationCode().hashCode());
        result = prime * result + ((getModel() == null) ? 0 : getModel().hashCode());
        result = prime * result + ((getCardProducerCode() == null) ? 0 : getCardProducerCode().hashCode());
        result = prime * result + ((getPhoneNo() == null) ? 0 : getPhoneNo().hashCode());
        return result;
    }
}