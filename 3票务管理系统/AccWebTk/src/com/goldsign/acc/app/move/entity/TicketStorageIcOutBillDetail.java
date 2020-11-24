package com.goldsign.acc.app.move.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TicketStorageIcOutBillDetail implements Serializable {
    private Long waterNo;

    private String billNo;

    private String reasonId;

    private String storageId;

    private String areaId;

    private String icMainType;

    private String icSubType;

    private BigDecimal outNum;

    private Date validDate;

    private BigDecimal cardMoney;

    private String lineId;

    private String stationId;

    private String cardType;

    private String esWorktypeId;

    private BigDecimal makeNum;

    private BigDecimal cardMoneyProduce;

    private String tempId;

    private Date vaildDateProduce;

    private String cardAvaDays;

    private String exitLineId;

    private String exitStationId;

    private String model;

    private String saleFlag;

    private String testFlag;

    private String orderType;

    private static final long serialVersionUID = 1L;

    public TicketStorageIcOutBillDetail(Long waterNo, String billNo, String reasonId, String storageId, String areaId, String icMainType, String icSubType, BigDecimal outNum, Date validDate, BigDecimal cardMoney, String lineId, String stationId, String cardType, String esWorktypeId, BigDecimal makeNum, BigDecimal cardMoneyProduce, String tempId, Date vaildDateProduce, String cardAvaDays, String exitLineId, String exitStationId, String model, String saleFlag, String testFlag, String orderType) {
        this.waterNo = waterNo;
        this.billNo = billNo;
        this.reasonId = reasonId;
        this.storageId = storageId;
        this.areaId = areaId;
        this.icMainType = icMainType;
        this.icSubType = icSubType;
        this.outNum = outNum;
        this.validDate = validDate;
        this.cardMoney = cardMoney;
        this.lineId = lineId;
        this.stationId = stationId;
        this.cardType = cardType;
        this.esWorktypeId = esWorktypeId;
        this.makeNum = makeNum;
        this.cardMoneyProduce = cardMoneyProduce;
        this.tempId = tempId;
        this.vaildDateProduce = vaildDateProduce;
        this.cardAvaDays = cardAvaDays;
        this.exitLineId = exitLineId;
        this.exitStationId = exitStationId;
        this.model = model;
        this.saleFlag = saleFlag;
        this.testFlag = testFlag;
        this.orderType = orderType;
    }

    public TicketStorageIcOutBillDetail() {
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

    public BigDecimal getOutNum() {
        return outNum;
    }

    public void setOutNum(BigDecimal outNum) {
        this.outNum = outNum;
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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getEsWorktypeId() {
        return esWorktypeId;
    }

    public void setEsWorktypeId(String esWorktypeId) {
        this.esWorktypeId = esWorktypeId == null ? null : esWorktypeId.trim();
    }

    public BigDecimal getMakeNum() {
        return makeNum;
    }

    public void setMakeNum(BigDecimal makeNum) {
        this.makeNum = makeNum;
    }

    public BigDecimal getCardMoneyProduce() {
        return cardMoneyProduce;
    }

    public void setCardMoneyProduce(BigDecimal cardMoneyProduce) {
        this.cardMoneyProduce = cardMoneyProduce;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId == null ? null : tempId.trim();
    }

    public Date getVaildDateProduce() {
        return vaildDateProduce;
    }

    public void setVaildDateProduce(Date vaildDateProduce) {
        this.vaildDateProduce = vaildDateProduce;
    }

    public String getCardAvaDays() {
        return cardAvaDays;
    }

    public void setCardAvaDays(String cardAvaDays) {
        this.cardAvaDays = cardAvaDays == null ? null : cardAvaDays.trim();
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

    public String getSaleFlag() {
        return saleFlag;
    }

    public void setSaleFlag(String saleFlag) {
        this.saleFlag = saleFlag == null ? null : saleFlag.trim();
    }

    public String getTestFlag() {
        return testFlag;
    }

    public void setTestFlag(String testFlag) {
        this.testFlag = testFlag == null ? null : testFlag.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
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
        TicketStorageIcOutBillDetail other = (TicketStorageIcOutBillDetail) that;
        return (this.getWaterNo() == null ? other.getWaterNo() == null : this.getWaterNo().equals(other.getWaterNo()))
            && (this.getBillNo() == null ? other.getBillNo() == null : this.getBillNo().equals(other.getBillNo()))
            && (this.getReasonId() == null ? other.getReasonId() == null : this.getReasonId().equals(other.getReasonId()))
            && (this.getStorageId() == null ? other.getStorageId() == null : this.getStorageId().equals(other.getStorageId()))
            && (this.getAreaId() == null ? other.getAreaId() == null : this.getAreaId().equals(other.getAreaId()))
            && (this.getIcMainType() == null ? other.getIcMainType() == null : this.getIcMainType().equals(other.getIcMainType()))
            && (this.getIcSubType() == null ? other.getIcSubType() == null : this.getIcSubType().equals(other.getIcSubType()))
            && (this.getOutNum() == null ? other.getOutNum() == null : this.getOutNum().equals(other.getOutNum()))
            && (this.getValidDate() == null ? other.getValidDate() == null : this.getValidDate().equals(other.getValidDate()))
            && (this.getCardMoney() == null ? other.getCardMoney() == null : this.getCardMoney().equals(other.getCardMoney()))
            && (this.getLineId() == null ? other.getLineId() == null : this.getLineId().equals(other.getLineId()))
            && (this.getStationId() == null ? other.getStationId() == null : this.getStationId().equals(other.getStationId()))
            && (this.getCardType() == null ? other.getCardType() == null : this.getCardType().equals(other.getCardType()))
            && (this.getEsWorktypeId() == null ? other.getEsWorktypeId() == null : this.getEsWorktypeId().equals(other.getEsWorktypeId()))
            && (this.getMakeNum() == null ? other.getMakeNum() == null : this.getMakeNum().equals(other.getMakeNum()))
            && (this.getCardMoneyProduce() == null ? other.getCardMoneyProduce() == null : this.getCardMoneyProduce().equals(other.getCardMoneyProduce()))
            && (this.getTempId() == null ? other.getTempId() == null : this.getTempId().equals(other.getTempId()))
            && (this.getVaildDateProduce() == null ? other.getVaildDateProduce() == null : this.getVaildDateProduce().equals(other.getVaildDateProduce()))
            && (this.getCardAvaDays() == null ? other.getCardAvaDays() == null : this.getCardAvaDays().equals(other.getCardAvaDays()))
            && (this.getExitLineId() == null ? other.getExitLineId() == null : this.getExitLineId().equals(other.getExitLineId()))
            && (this.getExitStationId() == null ? other.getExitStationId() == null : this.getExitStationId().equals(other.getExitStationId()))
            && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()))
            && (this.getSaleFlag() == null ? other.getSaleFlag() == null : this.getSaleFlag().equals(other.getSaleFlag()))
            && (this.getTestFlag() == null ? other.getTestFlag() == null : this.getTestFlag().equals(other.getTestFlag()))
            && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()));
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
        result = prime * result + ((getOutNum() == null) ? 0 : getOutNum().hashCode());
        result = prime * result + ((getValidDate() == null) ? 0 : getValidDate().hashCode());
        result = prime * result + ((getCardMoney() == null) ? 0 : getCardMoney().hashCode());
        result = prime * result + ((getLineId() == null) ? 0 : getLineId().hashCode());
        result = prime * result + ((getStationId() == null) ? 0 : getStationId().hashCode());
        result = prime * result + ((getCardType() == null) ? 0 : getCardType().hashCode());
        result = prime * result + ((getEsWorktypeId() == null) ? 0 : getEsWorktypeId().hashCode());
        result = prime * result + ((getMakeNum() == null) ? 0 : getMakeNum().hashCode());
        result = prime * result + ((getCardMoneyProduce() == null) ? 0 : getCardMoneyProduce().hashCode());
        result = prime * result + ((getTempId() == null) ? 0 : getTempId().hashCode());
        result = prime * result + ((getVaildDateProduce() == null) ? 0 : getVaildDateProduce().hashCode());
        result = prime * result + ((getCardAvaDays() == null) ? 0 : getCardAvaDays().hashCode());
        result = prime * result + ((getExitLineId() == null) ? 0 : getExitLineId().hashCode());
        result = prime * result + ((getExitStationId() == null) ? 0 : getExitStationId().hashCode());
        result = prime * result + ((getModel() == null) ? 0 : getModel().hashCode());
        result = prime * result + ((getSaleFlag() == null) ? 0 : getSaleFlag().hashCode());
        result = prime * result + ((getTestFlag() == null) ? 0 : getTestFlag().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        return result;
    }
}