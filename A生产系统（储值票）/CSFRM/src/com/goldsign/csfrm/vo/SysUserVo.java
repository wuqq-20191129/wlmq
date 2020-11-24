/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.csfrm.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lenovo
 */
public class SysUserVo {
    
    private String account = "";
    private String password = "";
    private String username = "";
    private String sex = "";
    private String department = "";
    private String userStatus = "";
    private String expireDate = "";
    private int loginNum = 0;
    private String employeeId = "";
    private int failedNum = 0;
    private String sessionId = "";
    private String lineId;
    private String stationId;
    private String bomShiftId;
    private String bomId;
    private String employeeLevel;
    private String chOrganNo;//所属机构
    
    //系统角色
    private List<SysRoleVo> sysRoleVos = new ArrayList<SysRoleVo>();
    
    //系统模块
    private List<SysModuleVo> sysModuleVos = new ArrayList<SysModuleVo>();

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the userStatus
     */
    public String getUserStatus() {
        return userStatus;
    }

    /**
     * @param userStatus the userStatus to set
     */
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * @return the expireDate
     */
    public String getExpireDate() {
        return expireDate;
    }

    /**
     * @param expireDate the expireDate to set
     */
    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * @return the loginNum
     */
    public int getLoginNum() {
        return loginNum;
    }

    /**
     * @param loginNum the loginNum to set
     */
    public void setLoginNum(int loginNum) {
        this.loginNum = loginNum;
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
     * @return the failedNum
     */
    public int getFailedNum() {
        return failedNum;
    }

    /**
     * @param failedNum the failedNum to set
     */
    public void setFailedNum(int failedNum) {
        this.failedNum = failedNum;
    }

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the lineId
     */
    public String getLineId() {
        return lineId;
    }

    /**
     * @param lineId the lineId to set
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    /**
     * @return the stationId
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * @param stationId the stationId to set
     */
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    /**
     * @return the bomShiftId
     */
    public String getBomShiftId() {
        return bomShiftId;
    }

    /**
     * @param bomShiftId the bomShiftId to set
     */
    public void setBomShiftId(String bomShiftId) {
        this.bomShiftId = bomShiftId;
    }

    /**
     * @return the bomId
     */
    public String getBomId() {
        return bomId;
    }

    /**
     * @param bomId the bomId to set
     */
    public void setBomId(String bomId) {
        this.bomId = bomId;
    }

    /**
     * @return the employeeLevel
     */
    public String getEmployeeLevel() {
        return employeeLevel;
    }

    /**
     * @param employeeLevel the employeeLevel to set
     */
    public void setEmployeeLevel(String employeeLevel) {
        this.employeeLevel = employeeLevel;
    }


    /**
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the chOrganNo
     */
    public String getChOrganNo() {
        return chOrganNo;
    }

    /**
     * @param chOrganNo the chOrganNo to set
     */
    public void setChOrganNo(String chOrganNo) {
        this.chOrganNo = chOrganNo;
    }

    @Override
    public String toString() {
        return "SysUserVo{" + "account=" + account + ", password=" + password + ", username=" + username + ", sex=" + sex + ", department=" + department + ", userStatus=" + userStatus + ", expireDate=" + expireDate + ", loginNum=" + loginNum + ", employeeId=" + employeeId + ", failedNum=" + failedNum + ", sessionId=" + sessionId + ", lineId=" + lineId + ", stationId=" + stationId + ", bomShiftId=" + bomShiftId + ", bomId=" + bomId + ", employeeLevel=" + employeeLevel + ", chOrganNo=" + chOrganNo + '}';
    }

    /**
     * @return the sysRoleVos
     */
    public List<SysRoleVo> getSysRoleVos() {
        return sysRoleVos;
    }

    /**
     * @param sysRoleVos the sysRoleVos to set
     */
    public void setSysRoleVos(List<SysRoleVo> sysRoleVos) {
        this.sysRoleVos = sysRoleVos;
    }

    /**
     * @return the sysModuleVos
     */
    public List<SysModuleVo> getSysModuleVos() {
        return sysModuleVos;
    }

    /**
     * @param sysModuleVos the sysModuleVos to set
     */
    public void setSysModuleVos(List<SysModuleVo> sysModuleVos) {
        this.sysModuleVos = sysModuleVos;
    }
    
}
