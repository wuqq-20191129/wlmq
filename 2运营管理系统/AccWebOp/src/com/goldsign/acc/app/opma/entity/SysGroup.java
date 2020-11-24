package com.goldsign.acc.app.opma.entity;

import java.io.Serializable;

public class SysGroup implements Serializable {
    private String sysGroupId;

    private String sysGroupName;

    private String sysStorageId;
    
    private String sysStorageName;

    public String getSysStorageName() {
		return sysStorageName;
	}

	public void setSysStorageName(String sysStorageName) {
		this.sysStorageName = sysStorageName;
	}

	private static final long serialVersionUID = 1L;

    public SysGroup(String sysGroupId, String sysGroupName, String sysStorageId) {
        this.sysGroupId = sysGroupId;
        this.sysGroupName = sysGroupName;
        this.sysStorageId = sysStorageId;
    }

    public SysGroup() {
        super();
    }

    public String getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(String sysGroupId) {
        this.sysGroupId = sysGroupId == null ? null : sysGroupId.trim();
    }

    public String getSysGroupName() {
        return sysGroupName;
    }

    public void setSysGroupName(String sysGroupName) {
        this.sysGroupName = sysGroupName == null ? null : sysGroupName.trim();
    }

    public String getSysStorageId() {
        return sysStorageId;
    }

    public void setSysStorageId(String sysStorageId) {
        this.sysStorageId = sysStorageId == null ? null : sysStorageId.trim();
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
        SysGroup other = (SysGroup) that;
        return (this.getSysGroupId() == null ? other.getSysGroupId() == null : this.getSysGroupId().equals(other.getSysGroupId()))
            && (this.getSysGroupName() == null ? other.getSysGroupName() == null : this.getSysGroupName().equals(other.getSysGroupName()))
            && (this.getSysStorageId() == null ? other.getSysStorageId() == null : this.getSysStorageId().equals(other.getSysStorageId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSysGroupId() == null) ? 0 : getSysGroupId().hashCode());
        result = prime * result + ((getSysGroupName() == null) ? 0 : getSysGroupName().hashCode());
        result = prime * result + ((getSysStorageId() == null) ? 0 : getSysStorageId().hashCode());
        return result;
    }
}