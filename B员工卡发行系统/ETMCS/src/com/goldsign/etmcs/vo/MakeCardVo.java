/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.vo;

import com.goldsign.etmcs.env.AppConstant;

/**
 *
 * @author lenovo
 */
public class MakeCardVo {

    private String employeeId;//工号
    
    private String employeeName;//姓名
    
    private String gender;//性别
    
    private String logicId;//逻辑卡号
    
    private String makeOper;//发卡人
    
    private String makeTime;//制卡时间

    private String returnOper;//退卡人
    
    private String returnTime;//退卡时间
    
    private String phyId;//物理卡号
    
    private String printId;
    
    private String useState;//类型
    
    private String isBeeCBValue;//是否蜂鸣
    
    private String employeePositions;//职务
    
    private String employeeDepartment;//单位
    
    private String employeePositionsTxt;//职务
    
    private String employeeDepartmentTxt;//单位
    
    private String updateOper;//发卡人
    
    private String updateTime;//制卡时间
    
    private String employeeClass;//员工级别
    
    private String employeeClassTxt;//员工级别

    public String getEmployeeClassTxt() {
        return employeeClassTxt;
    }

    public void setEmployeeClassTxt(String employeeClassTxt) {
        this.employeeClassTxt = employeeClassTxt;
    }

    public String getEmployeeClass() {
        return employeeClass;
    }

    public void setEmployeeClass(String employeeClass) {
        this.employeeClass = employeeClass;
    }

    public String getUpdateOper() {
        return updateOper;
    }

    public void setUpdateOper(String updateOper) {
        this.updateOper = updateOper;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getEmployeePositionsTxt() {
        return employeePositionsTxt;
    }

    public void setEmployeePositionsTxt(String employeePositionsTxt) {
        this.employeePositionsTxt = employeePositionsTxt;
    }

    public String getEmployeeDepartmentTxt() {
        return employeeDepartmentTxt;
    }

    public void setEmployeeDepartmentTxt(String employeeDepartmentTxt) {
        this.employeeDepartmentTxt = employeeDepartmentTxt;
    }

    public String getEmployeePositions() {
        return employeePositions;
    }

    public void setEmployeePositions(String employeePositions) {
        this.employeePositions = employeePositions;
    }

    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }

    public String getIsBeeCBValue() {
        return isBeeCBValue;
    }

    public void setIsBeeCBValue(String isBeeCBValue) {
        this.isBeeCBValue = isBeeCBValue;
    }

    
    public String getReturnOper() {
        return returnOper;
    }

    public void setReturnOper(String returnOper) {
        this.returnOper = returnOper;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getPhyId() {
        return phyId;
    }

    public void setPhyId(String phyId) {
        this.phyId = phyId;
    }

    public String getPrintId() {
        return printId;
    }

    public void setPrintId(String printId) {
        this.printId = printId;
    }

    public String getUseState() {
        return useState;
    }

    public void setUseState(String useState) {
        this.useState = useState;
    }
    
    /**
     * @return the useState
     */
    public String getUseStateDesc() {
        
        if(AppConstant.ET_STATE_ISSUE.equals(useState)){
            return AppConstant.ET_STATE_ISSUE_NAME;
        }else if(AppConstant.ET_STATE_RETURN.equals(useState)){
            return AppConstant.ET_STATE_RETURN_NAME;
        }else{
            return "";
        }

    }
    
    /**
     * @return the employeeId
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * @param employeeName the employeeName to set
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }
    
    /**
     * @return the gender
     */
    public String getGenderDesc() {
        
        if(AppConstant.GNEDER_MAN.equals(gender)){
            return AppConstant.GNEDER_MAN_NAME;
        }else{
            return AppConstant.GNEDER_WOMAN_NAME;
        }

    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the logicId
     */
    public String getLogicId() {
        return logicId;
    }

    /**
     * @param logicId the logicId to set
     */
    public void setLogicId(String logicId) {
        this.logicId = logicId;
    }

    /**
     * @return the makeOper
     */
    public String getMakeOper() {
        return makeOper;
    }

    /**
     * @param makeOper the makeOper to set
     */
    public void setMakeOper(String makeOper) {
        this.makeOper = makeOper;
    }

    /**
     * @return the makeTime
     */
    public String getMakeTime() {
        return makeTime;
    }

    /**
     * @param makeTime the makeTime to set
     */
    public void setMakeTime(String makeTime) {
        this.makeTime = makeTime;
    }

}
