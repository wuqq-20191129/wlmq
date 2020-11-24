package com.goldsign.acc.app.storagechk.entity;

import java.io.Serializable;

public class TicketStorageIcCodCardTypeContrastKey implements Serializable {
    private String icMainType;

    private String icSubType;

    private static final long serialVersionUID = 1L;

    public TicketStorageIcCodCardTypeContrastKey(String icMainType, String icSubType) {
        this.icMainType = icMainType;
        this.icSubType = icSubType;
    }

    public TicketStorageIcCodCardTypeContrastKey() {
        super();
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
        TicketStorageIcCodCardTypeContrastKey other = (TicketStorageIcCodCardTypeContrastKey) that;
        return (this.getIcMainType() == null ? other.getIcMainType() == null : this.getIcMainType().equals(other.getIcMainType()))
            && (this.getIcSubType() == null ? other.getIcSubType() == null : this.getIcSubType().equals(other.getIcSubType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIcMainType() == null) ? 0 : getIcMainType().hashCode());
        result = prime * result + ((getIcSubType() == null) ? 0 : getIcSubType().hashCode());
        return result;
    }
}