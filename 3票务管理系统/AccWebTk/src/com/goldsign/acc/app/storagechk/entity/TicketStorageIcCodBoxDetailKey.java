package com.goldsign.acc.app.storagechk.entity;

import java.io.Serializable;

public class TicketStorageIcCodBoxDetailKey implements Serializable {
    private String boxId;

    private String startLogicalId;

    private String endLogicalId;

    private static final long serialVersionUID = 1L;

    public TicketStorageIcCodBoxDetailKey(String boxId, String startLogicalId, String endLogicalId) {
        this.boxId = boxId;
        this.startLogicalId = startLogicalId;
        this.endLogicalId = endLogicalId;
    }

    public TicketStorageIcCodBoxDetailKey() {
        super();
    }

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId == null ? null : boxId.trim();
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
        TicketStorageIcCodBoxDetailKey other = (TicketStorageIcCodBoxDetailKey) that;
        return (this.getBoxId() == null ? other.getBoxId() == null : this.getBoxId().equals(other.getBoxId()))
            && (this.getStartLogicalId() == null ? other.getStartLogicalId() == null : this.getStartLogicalId().equals(other.getStartLogicalId()))
            && (this.getEndLogicalId() == null ? other.getEndLogicalId() == null : this.getEndLogicalId().equals(other.getEndLogicalId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBoxId() == null) ? 0 : getBoxId().hashCode());
        result = prime * result + ((getStartLogicalId() == null) ? 0 : getStartLogicalId().hashCode());
        result = prime * result + ((getEndLogicalId() == null) ? 0 : getEndLogicalId().hashCode());
        return result;
    }
}