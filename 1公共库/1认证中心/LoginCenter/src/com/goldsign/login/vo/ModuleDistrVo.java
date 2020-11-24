/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.login.vo;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author hejj
 */
public class ModuleDistrVo implements Serializable {

    private String moduleID = "";
    private String groupID = "";
    
    private String menuName;
    private String menuUrl;
    private String menuIcon;
    private String topMenuId;
    private String parentId;
    private String locked;
    private String sysFlag;
    private String btnCode;
    private String moduleType;

    private List<Menu> btnModules ;
    private String btnModulesText;
    
    public void setModuleID(String moduleID) {
        if (moduleID != null) {
            this.moduleID = moduleID;
        }
    }

    public String getModuleID() {
        return this.moduleID;
    }

    public void setGroupID(String groupID) {
        if (groupID != null) {
            this.groupID = groupID;
        }
    }

    public String getGroupID() {
        return this.groupID;
    }

    /**
     * @return the menuName
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * @param menuName the menuName to set
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * @return the menuUrl
     */
    public String getMenuUrl() {
        return menuUrl;
    }

    /**
     * @param menuUrl the menuUrl to set
     */
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    /**
     * @return the menuIcon
     */
    public String getMenuIcon() {
        return menuIcon;
    }

    /**
     * @param menuIcon the menuIcon to set
     */
    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    /**
     * @return the topMenuId
     */
    public String getTopMenuId() {
        return topMenuId;
    }

    /**
     * @param topMenuId the topMenuId to set
     */
    public void setTopMenuId(String topMenuId) {
        this.topMenuId = topMenuId;
    }

    /**
     * @return the parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the locked
     */
    public String getLocked() {
        return locked;
    }

    /**
     * @param locked the locked to set
     */
    public void setLocked(String locked) {
        this.locked = locked;
    }

    /**
     * @return the sysFlag
     */
    public String getSysFlag() {
        return sysFlag;
    }

    /**
     * @param sysFlag the sysFlag to set
     */
    public void setSysFlag(String sysFlag) {
        this.sysFlag = sysFlag;
    }

    /**
     * @return th
     */
    public String getBtnCode() {
        return btnCode;
    }

    /**
     * @param sysFlag the to set
     */
    public void setBtnCode(String btnCode) {
        this.btnCode = btnCode;
    }

    /**
     * @return th
     */
    public String getModuleType() {
        return moduleType;
    }

    /**
     * @param sysFlag the to set
     */
    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }
    
    /**
     * @return th
     */
    public List<Menu> getBtnModules() {
        return btnModules;
    }

    /**
     * @param sysFlag the to set
     */
    public void setBtnModules(List<Menu> btnModules) {
        this.btnModules = btnModules;
    }

    /**
     * @return the btnModulesText
     */
    public String getBtnModulesText() {
        return btnModulesText;
    }

    /**
     * @param btnModulesText the btnModulesText to set
     */
    public void setBtnModulesText(String btnModulesText) {
        this.btnModulesText = btnModulesText;
    }
}
