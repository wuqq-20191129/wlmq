/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.login.vo;

/**
 *
 * @author Administrator
 */
public class LoginVo {

    private String account;
    private String password;
    private String loginFlag;
    private String sysFlag;
    private String ip;

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

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
}
