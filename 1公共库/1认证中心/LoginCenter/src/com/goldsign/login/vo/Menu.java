/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.login.vo;

import java.io.Serializable;

/**
 *
 * @author hejj
 */
public class Menu implements Serializable{
        private	String menuId="";
	private	String menuName="";
	private	String url="";
	private String icon="";
	private String topMenuId="";
	private	String parentId="";
	private	String locked="";
        
        private String moduleType;
        private String btnId;
        
	public	String getMenuId(){
		return menuId;
	}
	public	void setMenuId(String newMenuId){
		menuId=newMenuId;
	}

	public	String getMenuName(){
		return menuName;
	}
	public	void setMenuName(String newMenuName){
		menuName=newMenuName;
	}

	public	String getUrl(){
		return url;
	}
	public	void setUrl(String newUrl){
		url=newUrl;
	}

	public	String getIcon(){
		return icon;
	}
	public	void setIcon(String newIcon){
		icon=newIcon;
	}

	public	String getTopMenuId(){
		return topMenuId;
	}
	public	void setTopMenuId(String newTopMenuId){
		topMenuId=newTopMenuId;
	}

	public	String getParentId(){
		return parentId;
	}
	public	void setParentId(String newParentId){
		parentId=newParentId;
	}
	public	String getLocked(){
		return locked;
	}
	public	void setLocked(String newLocked){
		locked=newLocked;
	}


    /**
     * @return the moduleType
     */
    public String getModuleType() {
        return moduleType;
    }

    /**
     * @param moduleType the moduleType to set
     */
    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    /**
     * @return the btnId
     */
    public String getBtnId() {
        return btnId;
    }

    /**
     * @param btnId the btnId to set
     */
    public void setBtnId(String btnId) {
        this.btnId = btnId;
    }
    
}
