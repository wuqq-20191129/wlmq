package com.goldsign.acc.app.query.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TicketStorageIcStsAreaCard  implements Serializable {
    private BigDecimal waterNo;

    private String storageId;

    private String areaId;

    private String icMainType;

    private String icSubType;

    private BigDecimal cardNum;

    private BigDecimal cardMoney;

    private String validDate;

    private String lineId;

    private String stationId;

    private String flag;   

    private String exitLineId;

    private String exitStationId;

    private String model;
    
   

    private String chestId;

    

    private String storeyId;

    private String baseId;

    private String boxId;

    private Date productDate;
    
    private String strProductDate;
    
    private String place;
    
     public String getStrProductDate() {
        return strProductDate;
    }

    public void setStrProductDate(String strProductDate) {
        this.strProductDate = strProductDate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

   public String getChestId() {
        return chestId;
    }

    public void setChestId(String chestId) {
        this.chestId = chestId;
    }

    public String getStoreyId() {
        return storeyId;
    }

    public void setStoreyId(String storeyId) {
        this.storeyId = storeyId;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }
    
    
    
   

    private static final long serialVersionUID = 1L;

    public TicketStorageIcStsAreaCard(BigDecimal waterNo, String storageId, String areaId, String icMainType, String icSubType, BigDecimal cardNum, BigDecimal cardMoney, String validDate, String lineId, String stationId, String flag, String exitLineId, String exitStationId, String model) {
        this.waterNo = waterNo;
        this.storageId = storageId;
        this.areaId = areaId;
        this.icMainType = icMainType;
        this.icSubType = icSubType;
        this.cardNum = cardNum;
        this.cardMoney = cardMoney;
        this.validDate = validDate;
        this.lineId = lineId;
        this.stationId = stationId;
        this.flag = flag;
        this.exitLineId = exitLineId;
        this.exitStationId = exitStationId;
        this.model = model;
    }

    public TicketStorageIcStsAreaCard() {
        super();
    }

    public BigDecimal getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(BigDecimal waterNo) {
        this.waterNo = waterNo;
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

    public BigDecimal getCardNum() {
        return cardNum;
    }

    public void setCardNum(BigDecimal cardNum) {
        this.cardNum = cardNum;
    }

    public BigDecimal getCardMoney() {
        return cardMoney;
    }

    public void setCardMoney(BigDecimal cardMoney) {
        this.cardMoney = cardMoney;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate == null ? null : validDate.trim();
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
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
        TicketStorageIcStsAreaCard other = (TicketStorageIcStsAreaCard) that;
        return (this.getWaterNo() == null ? other.getWaterNo() == null : this.getWaterNo().equals(other.getWaterNo()))
            && (this.getStorageId() == null ? other.getStorageId() == null : this.getStorageId().equals(other.getStorageId()))
            && (this.getAreaId() == null ? other.getAreaId() == null : this.getAreaId().equals(other.getAreaId()))
            && (this.getIcMainType() == null ? other.getIcMainType() == null : this.getIcMainType().equals(other.getIcMainType()))
            && (this.getIcSubType() == null ? other.getIcSubType() == null : this.getIcSubType().equals(other.getIcSubType()))
            && (this.getCardNum() == null ? other.getCardNum() == null : this.getCardNum().equals(other.getCardNum()))
            && (this.getCardMoney() == null ? other.getCardMoney() == null : this.getCardMoney().equals(other.getCardMoney()))
            && (this.getValidDate() == null ? other.getValidDate() == null : this.getValidDate().equals(other.getValidDate()))
            && (this.getLineId() == null ? other.getLineId() == null : this.getLineId().equals(other.getLineId()))
            && (this.getStationId() == null ? other.getStationId() == null : this.getStationId().equals(other.getStationId()))
            && (this.getFlag() == null ? other.getFlag() == null : this.getFlag().equals(other.getFlag()))
            && (this.getExitLineId() == null ? other.getExitLineId() == null : this.getExitLineId().equals(other.getExitLineId()))
            && (this.getExitStationId() == null ? other.getExitStationId() == null : this.getExitStationId().equals(other.getExitStationId()))
            && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWaterNo() == null) ? 0 : getWaterNo().hashCode());
        result = prime * result + ((getStorageId() == null) ? 0 : getStorageId().hashCode());
        result = prime * result + ((getAreaId() == null) ? 0 : getAreaId().hashCode());
        result = prime * result + ((getIcMainType() == null) ? 0 : getIcMainType().hashCode());
        result = prime * result + ((getIcSubType() == null) ? 0 : getIcSubType().hashCode());
        result = prime * result + ((getCardNum() == null) ? 0 : getCardNum().hashCode());
        result = prime * result + ((getCardMoney() == null) ? 0 : getCardMoney().hashCode());
        result = prime * result + ((getValidDate() == null) ? 0 : getValidDate().hashCode());
        result = prime * result + ((getLineId() == null) ? 0 : getLineId().hashCode());
        result = prime * result + ((getStationId() == null) ? 0 : getStationId().hashCode());
        result = prime * result + ((getFlag() == null) ? 0 : getFlag().hashCode());
        result = prime * result + ((getExitLineId() == null) ? 0 : getExitLineId().hashCode());
        result = prime * result + ((getExitStationId() == null) ? 0 : getExitStationId().hashCode());
        result = prime * result + ((getModel() == null) ? 0 : getModel().hashCode());
        return result;
    }
}