package com.goldsign.acc.app.opma.entity;

import java.io.Serializable;

public class SysGroupOperatorKey implements Serializable {
    private String sysGroupId;

    private String sysOperatorId;

    private static final long serialVersionUID = 1L;

    public SysGroupOperatorKey(String sysGroupId, String sysOperatorId) {
        this.sysGroupId = sysGroupId;
        this.sysOperatorId = sysOperatorId;
    }

    public SysGroupOperatorKey() {
        super();
    }

    public String getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(String sysGroupId) {
        this.sysGroupId = sysGroupId == null ? null : sysGroupId.trim();
    }

    public String getSysOperatorId() {
        return sysOperatorId;
    }

    public void setSysOperatorId(String sysOperatorId) {
        this.sysOperatorId = sysOperatorId == null ? null : sysOperatorId.trim();
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
        SysGroupOperatorKey other = (SysGroupOperatorKey) that;
        return (this.getSysGroupId() == null ? other.getSysGroupId() == null : this.getSysGroupId().equals(other.getSysGroupId()))
            && (this.getSysOperatorId() == null ? other.getSysOperatorId() == null : this.getSysOperatorId().equals(other.getSysOperatorId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSysGroupId() == null) ? 0 : getSysGroupId().hashCode());
        result = prime * result + ((getSysOperatorId() == null) ? 0 : getSysOperatorId().hashCode());
        return result;
    }
}