/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.csfrm.vo;

import com.goldsign.csfrm.env.BaseConstant;

/**
 *
 * @author lenovo
 */
public class SysModuleVo implements Comparable<SysModuleVo> {
    
    private String moduleId = "";//模块ID
    
    private String parentId = "";//父模块ID
    
    private String topId = "";//顶层模块ID，一般不使用
    
    private String icon = "";//模块图标
    
    private String name = "";//模块名称，如为按钮则为英文名称
    
    private String handleClassName = "";//模块处理类
    
    private String moduleLevel = "";//模块级别，分为一（菜单）、二（菜单）、三级（按钮）
    
    private String moduleType = "";//模块类型，分为菜单、按钮
    
    private String openType = "";//打开类型，暂没使用
    
    private boolean checkNext = BaseConstant.RIGHT_CHECK_NEXT_YES;//是否检查下级按钮权限
    
    private String orderNum;//模块顺序
    
    public SysModuleVo(){}
    
    public static SysModuleVo createOneLevelModule(String moduleId, String name){

        return createOneLevelModule(moduleId, name, moduleId);
    }
    
    public static SysModuleVo createOneLevelModule(String moduleId, String name, String orderNum) {

        SysModuleVo oneLevelModule = new SysModuleVo();
        oneLevelModule.setModuleId(moduleId);
        oneLevelModule.setParentId(moduleId);
        oneLevelModule.setName(name);
        oneLevelModule.setModuleLevel(BaseConstant.MODULE_LEVEL_FIRST);
        oneLevelModule.setOrderNum(orderNum);

        return oneLevelModule;
    }
    
     public static SysModuleVo createTwoLevelModule(String moduleId, String parentId, 
            String name, boolean checkNext, String handleClassName, String orderNum){
         
        SysModuleVo secondLevelModule = new SysModuleVo();
        
        secondLevelModule.setModuleId(moduleId);
        secondLevelModule.setParentId(parentId);
        secondLevelModule.setName(name);
        secondLevelModule.setModuleLevel(BaseConstant.MODULE_LEVEL_SECOND);
        secondLevelModule.setCheckNext(checkNext);
        secondLevelModule.setHandleClassName(handleClassName);
        secondLevelModule.setOrderNum(orderNum);
        
        return secondLevelModule;
     }
    
    public static SysModuleVo createTwoLevelModule(String moduleId, String parentId, 
            String name, boolean checkNext, String handleClassName){

        return createTwoLevelModule(moduleId, parentId, name, checkNext, handleClassName, 
                moduleId);
    }
    
    public static SysModuleVo createTwoLevelModule(String moduleId, String parentId,
            String name, String handleClassName) {

        return createTwoLevelModule(moduleId, parentId, name, BaseConstant.RIGHT_CHECK_NEXT_NO, 
                handleClassName);
    }
    
    /**
     * @return the moduleId
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId the moduleId to set
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
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
     * @return the topId
     */
    public String getTopId() {
        return topId;
    }

    /**
     * @param topId the topId to set
     */
    public void setTopId(String topId) {
        this.topId = topId;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the handleClassName
     */
    public String getHandleClassName() {
        return handleClassName;
    }

    /**
     * @param handleClassName the handleClassName to set
     */
    public void setHandleClassName(String handleClassName) {
        this.handleClassName = handleClassName;
    }

    /**
     * @return the moduleLevel
     */
    public String getModuleLevel() {
        return moduleLevel;
    }

    /**
     * @param moduleLevel the moduleLevel to set
     */
    public void setModuleLevel(String moduleLevel) {
        this.moduleLevel = moduleLevel;
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
     * @return the openType
     */
    public String getOpenType() {
        return openType;
    }

    /**
     * @param openType the openType to set
     */
    public void setOpenType(String openType) {
        this.openType = openType;
    }

    /**
     * @return the checkNext
     */
    public boolean isCheckNext() {
        return checkNext;
    }

    /**
     * @param checkNext the checkNext to set
     */
    public void setCheckNext(boolean checkNext) {
        this.checkNext = checkNext;
    }

    /**
     * @return the orderNum
     */
    public String getOrderNum() {
        return orderNum;
    }

    /**
     * @param orderNum the orderNum to set
     */
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public int compareTo(SysModuleVo o) {
        return this.orderNum.compareTo(o.getOrderNum());
    }

    @Override
    public String toString() {
        return "SysModuleVo{" + "moduleId=" + moduleId + ", parentId=" + parentId + ", topId=" + topId + ", icon=" + icon + ", name=" + name + ", handleClassName=" + handleClassName + ", moduleLevel=" + moduleLevel + ", moduleType=" + moduleType + ", openType=" + openType + ", checkNext=" + checkNext + ", orderNum=" + orderNum + '}';
    }

}
