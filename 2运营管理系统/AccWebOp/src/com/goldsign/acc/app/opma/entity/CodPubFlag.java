package com.goldsign.acc.app.opma.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class CodPubFlag extends CodPubFlagKey implements Serializable {
    private String codeText;

    private String description;

    private static final long serialVersionUID = 1L;

    public CodPubFlag(BigDecimal type, String code, String codeText, String description) {
        super(type, code);
        this.codeText = codeText;
        this.description = description;
    }

    public CodPubFlag() {
        super();
    }

    public String getCodeText() {
        return codeText;
    }

    public void setCodeText(String codeText) {
        this.codeText = codeText == null ? null : codeText.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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
        CodPubFlag other = (CodPubFlag) that;
        return (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getCodeText() == null ? other.getCodeText() == null : this.getCodeText().equals(other.getCodeText()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getCodeText() == null) ? 0 : getCodeText().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        return result;
    }
}