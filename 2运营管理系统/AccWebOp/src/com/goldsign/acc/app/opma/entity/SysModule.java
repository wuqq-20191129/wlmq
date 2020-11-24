package com.goldsign.acc.app.opma.entity;

import java.io.Serializable;

public class SysModule implements Serializable {
    private String moduleId;

    private String moduleName;

    private String menuUrl;

    private String menuIcon;

    private String topMenuId;

    private String parentId;

    private String locked;

    private String sysFlag;

    private String btnId;

    private String moduleType;
    
    private String checked;

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
    
  	private static final long serialVersionUID = 1L;

    public SysModule(String moduleId, String moduleName, String menuUrl, String menuIcon, String topMenuId, String parentId, String locked, String sysFlag, String btnId, String moduleType) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.menuUrl = menuUrl;
        this.menuIcon = menuIcon;
        this.topMenuId = topMenuId;
        this.parentId = parentId;
        this.locked = locked;
        this.sysFlag = sysFlag;
        this.btnId = btnId;
        this.moduleType = moduleType;
    }

    public SysModule() {
        super();
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId == null ? null : moduleId.trim();
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon == null ? null : menuIcon.trim();
    }

    public String getTopMenuId() {
        return topMenuId;
    }

    public void setTopMenuId(String topMenuId) {
        this.topMenuId = topMenuId == null ? null : topMenuId.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked == null ? null : locked.trim();
    }

    public String getSysFlag() {
        return sysFlag;
    }

    public void setSysFlag(String sysFlag) {
        this.sysFlag = sysFlag == null ? null : sysFlag.trim();
    }

    public String getBtnId() {
        return btnId;
    }

    public void setBtnId(String btnId) {
        this.btnId = btnId == null ? null : btnId.trim();
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType == null ? null : moduleType.trim();
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
        SysModule other = (SysModule) that;
        return (this.getModuleId() == null ? other.getModuleId() == null : this.getModuleId().equals(other.getModuleId()))
            && (this.getModuleName() == null ? other.getModuleName() == null : this.getModuleName().equals(other.getModuleName()))
            && (this.getMenuUrl() == null ? other.getMenuUrl() == null : this.getMenuUrl().equals(other.getMenuUrl()))
            && (this.getMenuIcon() == null ? other.getMenuIcon() == null : this.getMenuIcon().equals(other.getMenuIcon()))
            && (this.getTopMenuId() == null ? other.getTopMenuId() == null : this.getTopMenuId().equals(other.getTopMenuId()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getLocked() == null ? other.getLocked() == null : this.getLocked().equals(other.getLocked()))
            && (this.getSysFlag() == null ? other.getSysFlag() == null : this.getSysFlag().equals(other.getSysFlag()))
            && (this.getBtnId() == null ? other.getBtnId() == null : this.getBtnId().equals(other.getBtnId()))
            && (this.getModuleType() == null ? other.getModuleType() == null : this.getModuleType().equals(other.getModuleType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModuleId() == null) ? 0 : getModuleId().hashCode());
        result = prime * result + ((getModuleName() == null) ? 0 : getModuleName().hashCode());
        result = prime * result + ((getMenuUrl() == null) ? 0 : getMenuUrl().hashCode());
        result = prime * result + ((getMenuIcon() == null) ? 0 : getMenuIcon().hashCode());
        result = prime * result + ((getTopMenuId() == null) ? 0 : getTopMenuId().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getLocked() == null) ? 0 : getLocked().hashCode());
        result = prime * result + ((getSysFlag() == null) ? 0 : getSysFlag().hashCode());
        result = prime * result + ((getBtnId() == null) ? 0 : getBtnId().hashCode());
        result = prime * result + ((getModuleType() == null) ? 0 : getModuleType().hashCode());
        return result;
    }
}