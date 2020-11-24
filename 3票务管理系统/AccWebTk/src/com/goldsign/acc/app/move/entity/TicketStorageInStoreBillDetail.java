package com.goldsign.acc.app.move.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TicketStorageInStoreBillDetail implements Serializable {
    private Long waterNo;

    private String billNo;

    private String reasonId;

    private String storageId;

    private String areaId;

    private String icMainType;

    private String icSubType;

    private BigDecimal inNum;

    private String detailPlace;

    private String startBoxId;

    private String endBoxId;

    private String startLogicalId;

    private String endLogicalId;

    private Date validDate;
    
    private String strValidDate;

    public String getStrValidDate() {
        return strValidDate;
    }

    public void setStrValidDate(String strValidDate) {
        this.strValidDate = strValidDate;
    }

    private BigDecimal cardMoney;

    private String lineId;

    private String stationId;

    private String useFlag;

    private Date reportDate;

    private BigDecimal tickettypeId;

    private String cardAvaDays;

    private String lineIdReclaim;

    private String stationIdReclaim;

    private String exitLineId;

    private String exitStationId;

    private String model;

    private static final long serialVersionUID = 1L;

    public TicketStorageInStoreBillDetail(Long waterNo, String billNo, String reasonId, String storageId, String areaId, String icMainType, String icSubType, BigDecimal inNum, String detailPlace, String startBoxId, String endBoxId, String startLogicalId, String endLogicalId, Date validDate, BigDecimal cardMoney, String lineId, String stationId, String useFlag, Date reportDate, BigDecimal tickettypeId, String cardAvaDays, String lineIdReclaim, String stationIdReclaim, String exitLineId, String exitStationId, String model) {
        this.waterNo = waterNo;
        this.billNo = billNo;
        this.reasonId = reasonId;
        this.storageId = storageId;
        this.areaId = areaId;
        this.icMainType = icMainType;
        this.icSubType = icSubType;
        this.inNum = inNum;
        this.detailPlace = detailPlace;
        this.startBoxId = startBoxId;
        this.endBoxId = endBoxId;
        this.startLogicalId = startLogicalId;
        this.endLogicalId = endLogicalId;
        this.validDate = validDate;
        this.cardMoney = cardMoney;
        this.lineId = lineId;
        this.stationId = stationId;
        this.useFlag = useFlag;
        this.reportDate = reportDate;
        this.tickettypeId = tickettypeId;
        this.cardAvaDays = cardAvaDays;
        this.lineIdReclaim = lineIdReclaim;
        this.stationIdReclaim = stationIdReclaim;
        this.exitLineId = exitLineId;
        this.exitStationId = exitStationId;
        this.model = model;
    }

    public TicketStorageInStoreBillDetail() {
        super();
    }

    public Long getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(Long waterNo) {
        this.waterNo = waterNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId == null ? null : reasonId.trim();
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId == null ? null : storageId.trim();
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    public String getIcMainType() {
        return icMainType;
    }

    public void setIcMainType(String icMainType) {
        this.icMainType = icMainType == null ? null : icMainType.trim();
    }

    public String getIcSubType() {
        return icSubType;
    }

    public void setIcSubType(String icSubType) {
        this.icSubType = icSubType == null ? null : icSubType.trim();
    }

    public BigDecimal getInNum() {
        return inNum;
    }

    public void setInNum(BigDecimal inNum) {
        this.inNum = inNum;
    }

    public String getDetailPlace() {
        return detailPlace;
    }

    public void setDetailPlace(String detailPlace) {
        this.detailPlace = detailPlace == null ? null : detailPlace.trim();
    }

    public String getStartBoxId() {
        return startBoxId;
    }

    public void setStartBoxId(String startBoxId) {
        this.startBoxId = startBoxId == null ? null : startBoxId.trim();
    }

    public String getEndBoxId() {
        return endBoxId;
    }

    public void setEndBoxId(String endBoxId) {
        this.endBoxId = endBoxId == null ? null : endBoxId.trim();
    }

    public String getStartLogicalId() {
        return startLogicalId;
    }

    public void setStartLogicalId(String startLogicalId) {
        this.startLogicalId = startLogicalId == null ? null : startLogicalId.trim();
    }

    public String getEndLogicalId() {
        return endLogicalId;
    }

    public void setEndLogicalId(String endLogicalId) {
        this.endLogicalId = endLogicalId == null ? null : endLogicalId.trim();
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public BigDecimal getCardMoney() {
        return cardMoney;
    }

    public void setCardMoney(BigDecimal cardMoney) {
        this.cardMoney = cardMoney;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId == null ? null : lineId.trim();
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
    }

    public String getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(String useFlag) {
        this.useFlag = useFlag == null ? null : useFlag.trim();
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public BigDecimal getTickettypeId() {
        return tickettypeId;
    }

    public void setTickettypeId(BigDecimal tickettypeId) {
        this.tickettypeId = tickettypeId;
    }

    public String getCardAvaDays() {
        return cardAvaDays;
    }

    public void setCardAvaDays(String cardAvaDays) {
        this.cardAvaDays = cardAvaDays == null ? null : cardAvaDays.trim();
    }

    public String getLineIdReclaim() {
        return lineIdReclaim;
    }

    public void setLineIdReclaim(String lineIdReclaim) {
        this.lineIdReclaim = lineIdReclaim == null ? null : lineIdReclaim.trim();
    }

    public String getStationIdReclaim() {
        return stationIdReclaim;
    }

    public void setStationIdReclaim(String stationIdReclaim) {
        this.stationIdReclaim = stationIdReclaim == null ? null : stationIdReclaim.trim();
    }

    public String getExitLineId() {
        return exitLineId;
    }

    public void setExitLineId(String exitLineId) {
        this.exitLineId = exitLineId == null ? null : exitLineId.trim();
    }

    public String getExitStationId() {
        return exitStationId;
    }

    public void setExitStationId(String exitStationId) {
        this.exitStationId = exitStationId == null ? null : exitStationId.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
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
        TicketStorageInStoreBillDetail other = (TicketStorageInStoreBillDetail) that;
        return (this.getWaterNo() == null ? other.getWaterNo() == null : this.getWaterNo().equals(other.getWaterNo()))
            && (this.getBillNo() == null ? other.getBillNo() == null : this.getBillNo().equals(other.getBillNo()))
            && (this.getReasonId() == null ? other.getReasonId() == null : this.getReasonId().equals(other.getReasonId()))
            && (this.getStorageId() == null ? other.getStorageId() == null : this.getStorageId().equals(other.getStorageId()))
            && (this.getAreaId() == null ? other.getAreaId() == null : this.getAreaId().equals(other.getAreaId()))
            && (this.getIcMainType() == null ? other.getIcMainType() == null : this.getIcMainType().equals(other.getIcMainType()))
            && (this.getIcSubType() == null ? other.getIcSubType() == null : this.getIcSubType().equals(other.getIcSubType()))
            && (this.getInNum() == null ? other.getInNum() == null : this.getInNum().equals(other.getInNum()))
            && (this.getDetailPlace() == null ? other.getDetailPlace() == null : this.getDetailPlace().equals(other.getDetailPlace()))
            && (this.getStartBoxId() == null ? other.getStartBoxId() == null : this.getStartBoxId().equals(other.getStartBoxId()))
            && (this.getEndBoxId() == null ? other.getEndBoxId() == null : this.getEndBoxId().equals(other.getEndBoxId()))
            && (this.getStartLogicalId() == null ? other.getStartLogicalId() == null : this.getStartLogicalId().equals(other.getStartLogicalId()))
            && (this.getEndLogicalId() == null ? other.getEndLogicalId() == null : this.getEndLogicalId().equals(other.getEndLogicalId()))
            && (this.getValidDate() == null ? other.getValidDate() == null : this.getValidDate().equals(other.getValidDate()))
            && (this.getCardMoney() == null ? other.getCardMoney() == null : this.getCardMoney().equals(other.getCardMoney()))
            && (this.getLineId() == null ? other.getLineId() == null : this.getLineId().equals(other.getLineId()))
            && (this.getStationId() == null ? other.getStationId() == null : this.getStationId().equals(other.getStationId()))
            && (this.getUseFlag() == null ? other.getUseFlag() == null : this.getUseFlag().equals(other.getUseFlag()))
            && (this.getReportDate() == null ? other.getReportDate() == null : this.getReportDate().equals(other.getReportDate()))
            && (this.getTickettypeId() == null ? other.getTickettypeId() == null : this.getTickettypeId().equals(other.getTickettypeId()))
            && (this.getCardAvaDays() == null ? other.getCardAvaDays() == null : this.getCardAvaDays().equals(other.getCardAvaDays()))
            && (this.getLineIdReclaim() == null ? other.getLineIdReclaim() == null : this.getLineIdReclaim().equals(other.getLineIdReclaim()))
            && (this.getStationIdReclaim() == null ? other.getStationIdReclaim() == null : this.getStationIdReclaim().equals(other.getStationIdReclaim()))
            && (this.getExitLineId() == null ? other.getExitLineId() == null : this.getExitLineId().equals(other.getExitLineId()))
            && (this.getExitStationId() == null ? other.getExitStationId() == null : this.getExitStationId().equals(other.getExitStationId()))
            && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWaterNo() == null) ? 0 : getWaterNo().hashCode());
        result = prime * result + ((getBillNo() == null) ? 0 : getBillNo().hashCode());
        result = prime * result + ((getReasonId() == null) ? 0 : getReasonId().hashCode());
        result = prime * result + ((getStorageId() == null) ? 0 : getStorageId().hashCode());
        result = prime * result + ((getAreaId() == null) ? 0 : getAreaId().hashCode());
        result = prime * result + ((getIcMainType() == null) ? 0 : getIcMainType().hashCode());
        result = prime * result + ((getIcSubType() == null) ? 0 : getIcSubType().hashCode());
        result = prime * result + ((getInNum() == null) ? 0 : getInNum().hashCode());
        result = prime * result + ((getDetailPlace() == null) ? 0 : getDetailPlace().hashCode());
        result = prime * result + ((getStartBoxId() == null) ? 0 : getStartBoxId().hashCode());
        result = prime * result + ((getEndBoxId() == null) ? 0 : getEndBoxId().hashCode());
        result = prime * result + ((getStartLogicalId() == null) ? 0 : getStartLogicalId().hashCode());
        result = prime * result + ((getEndLogicalId() == null) ? 0 : getEndLogicalId().hashCode());
        result = prime * result + ((getValidDate() == null) ? 0 : getValidDate().hashCode());
        result = prime * result + ((getCardMoney() == null) ? 0 : getCardMoney().hashCode());
        result = prime * result + ((getLineId() == null) ? 0 : getLineId().hashCode());
        result = prime * result + ((getStationId() == null) ? 0 : getStationId().hashCode());
        result = prime * result + ((getUseFlag() == null) ? 0 : getUseFlag().hashCode());
        result = prime * result + ((getReportDate() == null) ? 0 : getReportDate().hashCode());
        result = prime * result + ((getTickettypeId() == null) ? 0 : getTickettypeId().hashCode());
        result = prime * result + ((getCardAvaDays() == null) ? 0 : getCardAvaDays().hashCode());
        result = prime * result + ((getLineIdReclaim() == null) ? 0 : getLineIdReclaim().hashCode());
        result = prime * result + ((getStationIdReclaim() == null) ? 0 : getStationIdReclaim().hashCode());
        result = prime * result + ((getExitLineId() == null) ? 0 : getExitLineId().hashCode());
        result = prime * result + ((getExitStationId() == null) ? 0 : getExitStationId().hashCode());
        result = prime * result + ((getModel() == null) ? 0 : getModel().hashCode());
        return result;
    }
}