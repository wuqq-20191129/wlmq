/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.vo;

/**
 *
 * @author hejj
 */
public class LoginVo {

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
     * @return the newpassword
     */
    public String getNewpassword() {
        return newpassword;
    }

    /**
     * @param newpassword the newpassword to set
     */
    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    /**
     * @return the repassword
     */
    public String getRepassword() {
        return repassword;
    }

    /**
     * @param repassword the repassword to set
     */
    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    /**
     * @return the loginFlag
     */
    public String getLoginFlag() {
        return loginFlag;
    }

    /**
     * @param loginFlag the loginFlag to set
     */
    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
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

    private String account = "";
    private String password = "";
    private String newpassword = "";
    private String repassword = "";
    private String loginFlag = "";
    private String sysFlag = "";

}
