package com.goldsign.acc.app.storagechk.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class TicketStorageIcCodCardTypeContrast extends TicketStorageIcCodCardTypeContrastKey implements Serializable {
    private String cardMainType;

    private String cardSubType;

    private BigDecimal boxUnit;

    private static final long serialVersionUID = 1L;

    public TicketStorageIcCodCardTypeContrast(String icMainType, String icSubType, String cardMainType, String cardSubType, BigDecimal boxUnit) {
        super(icMainType, icSubType);
        this.cardMainType = cardMainType;
        this.cardSubType = cardSubType;
        this.boxUnit = boxUnit;
    }

    public TicketStorageIcCodCardTypeContrast() {
        super();
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

    public BigDecimal getBoxUnit() {
        return boxUnit;
    }

    public void setBoxUnit(BigDecimal boxUnit) {
        this.boxUnit = boxUnit;
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
        TicketStorageIcCodCardTypeContrast other = (TicketStorageIcCodCardTypeContrast) that;
        return (this.getIcMainType() == null ? other.getIcMainType() == null : this.getIcMainType().equals(other.getIcMainType()))
            && (this.getIcSubType() == null ? other.getIcSubType() == null : this.getIcSubType().equals(other.getIcSubType()))
            && (this.getCardMainType() == null ? other.getCardMainType() == null : this.getCardMainType().equals(other.getCardMainType()))
            && (this.getCardSubType() == null ? other.getCardSubType() == null : this.getCardSubType().equals(other.getCardSubType()))
            && (this.getBoxUnit() == null ? other.getBoxUnit() == null : this.getBoxUnit().equals(other.getBoxUnit()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIcMainType() == null) ? 0 : getIcMainType().hashCode());
        result = prime * result + ((getIcSubType() == null) ? 0 : getIcSubType().hashCode());
        result = prime * result + ((getCardMainType() == null) ? 0 : getCardMainType().hashCode());
        result = prime * result + ((getCardSubType() == null) ? 0 : getCardSubType().hashCode());
        result = prime * result + ((getBoxUnit() == null) ? 0 : getBoxUnit().hashCode());
        return result;
    }
}