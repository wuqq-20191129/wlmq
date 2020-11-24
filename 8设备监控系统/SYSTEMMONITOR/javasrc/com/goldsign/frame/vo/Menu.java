package com.goldsign.frame.vo;

public	class	Menu{

	private	String menuId="";
	private	String menuName="";
	private	String url="";
	private String icon="";
	private String topMenuId="";
	private	String parentId="";
	private	String locked="";
	private	String lockedText="";
	private	String postParameter="";
	private String status="";
	private String newWindowFlag="";
	private String newWindowFlagText="";
	private String passwordFlag="";

	public String getPasswordFlag() {
		return passwordFlag;
	}
	public void setPasswordFlag(String passwordFlag) {
		this.passwordFlag = passwordFlag;
	}
	public String getNewWindowFlagText() {
		return newWindowFlagText;
	}
	public void setNewWindowFlagText(String newWindowFlagText) {
		this.newWindowFlagText = newWindowFlagText;
	}
	public String getNewWindowFlag() {
		return newWindowFlag;
	}
	public void setNewWindowFlag(String newWindowFlag) {
		this.newWindowFlag = newWindowFlag;
	}
	public String getPostParameter() {
		return postParameter;
	}
	public void setPostParameter(String postParameter) {
		this.postParameter = postParameter;
	}
	public String getLockedText() {
		return lockedText;
	}
	public void setLockedText(String lockedText) {
		this.lockedText = lockedText;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
