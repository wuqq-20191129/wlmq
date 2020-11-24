/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.vo;

/**
 *
 * @author Administrator
 */
public class ImportConfig {

    private String server;
    private String db;
    private String account;
    private String password;
    private String encFlag;
    private String remark;

    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * @return the db
     */
    public String getDb() {
        return db;
    }

    /**
     * @param db the db to set
     */
    public void setDb(String db) {
        this.db = db;
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
     * @return the encFlag
     */
    public String getEncFlag() {
        return encFlag;
    }

    /**
     * @param encFlag the encFlag to set
     */
    public void setEncFlag(String encFlag) {
        this.encFlag = encFlag;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
