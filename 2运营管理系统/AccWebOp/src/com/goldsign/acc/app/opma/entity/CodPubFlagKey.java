package com.goldsign.acc.app.opma.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class CodPubFlagKey implements Serializable {
    private BigDecimal type;

    private String code;

    private static final long serialVersionUID = 1L;

    public CodPubFlagKey(BigDecimal type, String code) {
        this.type = type;
        this.code = code;
    }

    public CodPubFlagKey() {
        super();
    }

    public BigDecimal getType() {
        return type;
    }

    public void setType(BigDecimal type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
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
        CodPubFlagKey other = (CodPubFlagKey) that;
        return (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        return result;
    }
}