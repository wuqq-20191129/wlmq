package com.goldsign.acc.app.password.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class SysOperator implements Serializable {
    private String sysOperatorId;

    private String sysPasswordHash;

    private String sysOperatorName;

    private String sysEmployeeId;

    private String sysExpiredDate;

    private String sysStatus;
    
    private String sysGroupName;  
    
    private List<String> sysGroupIds;
       
	private String sysGroupId;	

	private BigDecimal loginNum;

    private BigDecimal failedNum;

    private String sessionId;

    private String passwordEditDate;

    private BigDecimal editPastDays;

    private static final long serialVersionUID = 1L;

    public SysOperator(String sysOperatorId, String sysPasswordHash, String sysOperatorName, String sysEmployeeId, String sysExpiredDate, String sysStatus, String sysGroupName,String sysGroupId,BigDecimal loginNum, BigDecimal failedNum, String sessionId, String passwordEditDate, BigDecimal editPastDays) {
        this.sysOperatorId = sysOperatorId;
        this.sysPasswordHash = sysPasswordHash;
        this.sysOperatorName = sysOperatorName;
        this.sysEmployeeId = sysEmployeeId;
        this.sysExpiredDate = sysExpiredDate;
        this.sysStatus = sysStatus;
        this.sysGroupName = sysGroupName;
        this.sysGroupId = sysGroupId;
        this.loginNum = loginNum;
        this.failedNum = failedNum;
        this.sessionId = sessionId;
        this.passwordEditDate = passwordEditDate;
        this.editPastDays = editPastDays;
    }

    public SysOperator() {
        super();
    }

    public String getSysOperatorId() {
        return sysOperatorId;
    }

    public void setSysOperatorId(String sysOperatorId) {
        this.sysOperatorId = sysOperatorId == null ? null : sysOperatorId.trim();
    }

    public String getSysPasswordHash() {
        return sysPasswordHash;
    }

    public void setSysPasswordHash(String sysPasswordHash) {
        this.sysPasswordHash = sysPasswordHash == null ? null : sysPasswordHash.trim();
    }

    public String getSysOperatorName() {
        return sysOperatorName;
    }

    public void setSysOperatorName(String sysOperatorName) {
        this.sysOperatorName = sysOperatorName == null ? null : sysOperatorName.trim();
    }

    public String getSysEmployeeId() {
        return sysEmployeeId;
    }

    public void setSysEmployeeId(String sysEmployeeId) {
        this.sysEmployeeId = sysEmployeeId == null ? null : sysEmployeeId.trim();
    }

    public String getSysExpiredDate() {
        return sysExpiredDate;
    }

    public void setSysExpiredDate(String sysExpiredDate) {
        this.sysExpiredDate = sysExpiredDate == null ? null : sysExpiredDate.trim();
    }

    public String getSysStatus() {
        return sysStatus;
    }

    public void setSysStatus(String sysStatus) {
        this.sysStatus = sysStatus == null ? null : sysStatus.trim();
    }
    
    public String getSysGroupName() {
		return sysGroupName;
	}

	public void setSysGroupName(String sysGroupName) {
		this.sysGroupName = sysGroupName;
	}
	
	public List<String> getSysGroupIds() {
		return sysGroupIds;
	}

	public void setSysGroupIds(List<String> sysGroupIds) {
		this.sysGroupIds = sysGroupIds;
	}
	
	public String getSysGroupId() {
		return sysGroupId;
	}

	public void setSysGroupId(String sysGroupId) {
		this.sysGroupId = sysGroupId;
	}

    public BigDecimal getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(BigDecimal loginNum) {
        this.loginNum = loginNum;
    }

    public BigDecimal getFailedNum() {
        return failedNum;
    }

    public void setFailedNum(BigDecimal failedNum) {
        this.failedNum = failedNum;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId == null ? null : sessionId.trim();
    }

    public String getPasswordEditDate() {
        return passwordEditDate;
    }

    public void setPasswordEditDate(String passwordEditDate) {
        this.passwordEditDate = passwordEditDate == null ? null : passwordEditDate.trim();
    }

    public BigDecimal getEditPastDays() {
        return editPastDays;
    }

    public void setEditPastDays(BigDecimal editPastDays) {
        this.editPastDays = editPastDays;
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
        SysOperator other = (SysOperator) that;
        return (this.getSysOperatorId() == null ? other.getSysOperatorId() == null : this.getSysOperatorId().equals(other.getSysOperatorId()))
            && (this.getSysPasswordHash() == null ? other.getSysPasswordHash() == null : this.getSysPasswordHash().equals(other.getSysPasswordHash()))
            && (this.getSysOperatorName() == null ? other.getSysOperatorName() == null : this.getSysOperatorName().equals(other.getSysOperatorName()))
            && (this.getSysEmployeeId() == null ? other.getSysEmployeeId() == null : this.getSysEmployeeId().equals(other.getSysEmployeeId()))
            && (this.getSysExpiredDate() == null ? other.getSysExpiredDate() == null : this.getSysExpiredDate().equals(other.getSysExpiredDate()))
            && (this.getSysStatus() == null ? other.getSysStatus() == null : this.getSysStatus().equals(other.getSysStatus()))
            && (this.getLoginNum() == null ? other.getLoginNum() == null : this.getLoginNum().equals(other.getLoginNum()))
            && (this.getFailedNum() == null ? other.getFailedNum() == null : this.getFailedNum().equals(other.getFailedNum()))
            && (this.getSessionId() == null ? other.getSessionId() == null : this.getSessionId().equals(other.getSessionId()))
            && (this.getPasswordEditDate() == null ? other.getPasswordEditDate() == null : this.getPasswordEditDate().equals(other.getPasswordEditDate()))
            && (this.getEditPastDays() == null ? other.getEditPastDays() == null : this.getEditPastDays().equals(other.getEditPastDays()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSysOperatorId() == null) ? 0 : getSysOperatorId().hashCode());
        result = prime * result + ((getSysPasswordHash() == null) ? 0 : getSysPasswordHash().hashCode());
        result = prime * result + ((getSysOperatorName() == null) ? 0 : getSysOperatorName().hashCode());
        result = prime * result + ((getSysEmployeeId() == null) ? 0 : getSysEmployeeId().hashCode());
        result = prime * result + ((getSysExpiredDate() == null) ? 0 : getSysExpiredDate().hashCode());
        result = prime * result + ((getSysStatus() == null) ? 0 : getSysStatus().hashCode());
        result = prime * result + ((getLoginNum() == null) ? 0 : getLoginNum().hashCode());
        result = prime * result + ((getFailedNum() == null) ? 0 : getFailedNum().hashCode());
        result = prime * result + ((getSessionId() == null) ? 0 : getSessionId().hashCode());
        result = prime * result + ((getPasswordEditDate() == null) ? 0 : getPasswordEditDate().hashCode());
        result = prime * result + ((getEditPastDays() == null) ? 0 : getEditPastDays().hashCode());
        return result;
    }
}