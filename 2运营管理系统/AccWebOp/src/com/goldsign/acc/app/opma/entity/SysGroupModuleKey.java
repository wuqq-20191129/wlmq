package com.goldsign.acc.app.opma.entity;

import java.io.Serializable;

public class SysGroupModuleKey implements Serializable {
    private String moduleId;

    private String sysGroupId;

    private static final long serialVersionUID = 1L;

    public SysGroupModuleKey(String moduleId, String sysGroupId) {
        this.moduleId = moduleId;
        this.sysGroupId = sysGroupId;
    }

    public SysGroupModuleKey() {
        super();
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId == null ? null : moduleId.trim();
    }

    public String getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(String sysGroupId) {
        this.sysGroupId = sysGroupId == null ? null : sysGroupId.trim();
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
        SysGroupModuleKey other = (SysGroupModuleKey) that;
        return (this.getModuleId() == null ? other.getModuleId() == null : this.getModuleId().equals(other.getModuleId()))
            && (this.getSysGroupId() == null ? other.getSysGroupId() == null : this.getSysGroupId().equals(other.getSysGroupId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModuleId() == null) ? 0 : getModuleId().hashCode());
        result = prime * result + ((getSysGroupId() == null) ? 0 : getSysGroupId().hashCode());
        return result;
    }
}