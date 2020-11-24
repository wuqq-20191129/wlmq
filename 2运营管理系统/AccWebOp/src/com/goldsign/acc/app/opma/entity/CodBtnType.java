package com.goldsign.acc.app.opma.entity;

import java.io.Serializable;

public class CodBtnType implements Serializable {
    private String btnId;

    private String btnCode;

    private String btnName;

    private String commandName;

    private String operationType;

    private static final long serialVersionUID = 1L;

    public CodBtnType(String btnId, String btnCode, String btnName, String commandName, String operationType) {
        this.btnId = btnId;
        this.btnCode = btnCode;
        this.btnName = btnName;
        this.commandName = commandName;
        this.operationType = operationType;
    }

    public CodBtnType() {
        super();
    }

    public String getBtnId() {
        return btnId;
    }

    public void setBtnId(String btnId) {
        this.btnId = btnId == null ? null : btnId.trim();
    }

    public String getBtnCode() {
        return btnCode;
    }

    public void setBtnCode(String btnCode) {
        this.btnCode = btnCode == null ? null : btnCode.trim();
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName == null ? null : btnName.trim();
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName == null ? null : commandName.trim();
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType == null ? null : operationType.trim();
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
        CodBtnType other = (CodBtnType) that;
        return (this.getBtnId() == null ? other.getBtnId() == null : this.getBtnId().equals(other.getBtnId()))
            && (this.getBtnCode() == null ? other.getBtnCode() == null : this.getBtnCode().equals(other.getBtnCode()))
            && (this.getBtnName() == null ? other.getBtnName() == null : this.getBtnName().equals(other.getBtnName()))
            && (this.getCommandName() == null ? other.getCommandName() == null : this.getCommandName().equals(other.getCommandName()))
            && (this.getOperationType() == null ? other.getOperationType() == null : this.getOperationType().equals(other.getOperationType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBtnId() == null) ? 0 : getBtnId().hashCode());
        result = prime * result + ((getBtnCode() == null) ? 0 : getBtnCode().hashCode());
        result = prime * result + ((getBtnName() == null) ? 0 : getBtnName().hashCode());
        result = prime * result + ((getCommandName() == null) ? 0 : getCommandName().hashCode());
        result = prime * result + ((getOperationType() == null) ? 0 : getOperationType().hashCode());
        return result;
    }
}