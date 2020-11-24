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
public class User implements Serializable {

    private String account = "";
    private String password = "";
    private String username = "";
    private String sex = "";
    private String department = "";
    private String userStatus = "";
    private String expireDate = "";
    private int loginNum = 0;
    private String employeeID = "";
    private int failedNum = 0;
    private String sessionID = "";
    private int editpassworddays = 0;
    private String editpassworddate = "";
    private Long leftDays;
    
    private String newPassword;
    
    private String sysFlag ;
    
    public int getLoginNum() {
        return this.loginNum;
    }

    public void setLoginNum(int loginNum) {
        this.loginNum = loginNum;
    }

    public int getFailedNum() {
        return this.failedNum;
    }

    public void setFailedNum(int failedNum) {
        this.failedNum = failedNum;
    }

    public String getAccount() {
        if (this.account == null) {
            return "";
        }
        return this.account;
    }

    public void setAccount(String newAccount) {
        if (newAccount != null) {
            account = newAccount;
        }
    }

    public String getSessionID() {
        if (this.sessionID == null) {
            return "";
        }
        return this.sessionID;
    }

    public void setSessionID(String sessionID) {
        if (sessionID != null) {
            this.sessionID = sessionID;
        }
    }

    public String getExpireDate() {
        if (this.expireDate == null) {
            return "";
        }
        return this.expireDate;
    }

    public void setExpireDate(String expireDate) {
        if (expireDate != null) {
            this.expireDate = expireDate;
        }
    }

    public String getEmployeeID() {
        if (this.employeeID == null) {
            return "";
        }
        return this.employeeID;
    }

    public void setEmployeeID(String employeeID) {
        if (employeeID != null) {
            this.employeeID = employeeID;
        }
    }

    public String getPassword() {
        if (this.password == null) {
            return "";
        }
        return password;
    }

    public void setPassword(String newPassword) {
        if (newPassword != null) {
            password = newPassword;
        }
    }

    public String getUsername() {
        if (this.username == null) {
            return "";
        }
        return username;
    }

    public void setUsername(String newUsername) {
        if (newUsername != null) {
            username = newUsername;
        }
    }

    public String getSex() {
        if (this.sex == null) {
            return "";
        }
        return sex;
    }

    public void setSex(String userSex) {
        if (userSex != null) {
            sex = userSex;
        }
    }

    public String getDepartment() {
        if (this.department == null) {
            return "";
        }
        return department;
    }

    public void setDepartment(String newDepartment) {
        department = newDepartment;
    }

    public String getUserStatus() {
        if (this.userStatus == null) {
            return "";
        }
        return this.userStatus;
    }

    public void setUserStatus(String newUserStatus) {
        this.userStatus = newUserStatus;
    }

    public int getEditPassWordDays() {
        return this.editpassworddays;
    }

    public void setEditPassWordDays(int editpassworddays) {
        this.editpassworddays = editpassworddays;
    }

    public String getEditPassWordDate() {
        if (this.editpassworddate == null) {
            return "";
        }
        return this.editpassworddate;
    }

    public void setEditPassWordDate(String editpassworddate) {
        if (editpassworddate != null) {
            this.editpassworddate = editpassworddate;
        }
    }

    /**
     * @return the leftDays
     */
    public Long getLeftDays() {
        return leftDays;
    }

    /**
     * @param leftDays the leftDays to set
     */
    public void setLeftDays(Long leftDays) {
        this.leftDays = leftDays;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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



}
