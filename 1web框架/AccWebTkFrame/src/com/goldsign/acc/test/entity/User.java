/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.test.entity;

/**
 *
 * @author hejj
 */
public class User {

    /**
     * @return the sys_operator_id
     */
    public String getSys_operator_id() {
        return sys_operator_id;
    }

    /**
     * @param sys_operator_id the sys_operator_id to set
     */
    public void setSys_operator_id(String sys_operator_id) {
        this.sys_operator_id = sys_operator_id;
    }

    /**
     * @return the sys_password_hash
     */
    public String getSys_password_hash() {
        return sys_password_hash;
    }

    /**
     * @param sys_password_hash the sys_password_hash to set
     */
    public void setSys_password_hash(String sys_password_hash) {
        this.sys_password_hash = sys_password_hash;
    }

    /**
     * @return the sys_operator_name
     */
    public String getSys_operator_name() {
        return sys_operator_name;
    }

    /**
     * @param sys_operator_name the sys_operator_name to set
     */
    public void setSys_operator_name(String sys_operator_name) {
        this.sys_operator_name = sys_operator_name;
    }

    /**
     * @return the sys_employee_id
     */
    public String getSys_employee_id() {
        return sys_employee_id;
    }

    /**
     * @param sys_employee_id the sys_employee_id to set
     */
    public void setSys_employee_id(String sys_employee_id) {
        this.sys_employee_id = sys_employee_id;
    }

    /**
     * @return the sys_expired_date
     */
    public String getSys_expired_date() {
        return sys_expired_date;
    }

    /**
     * @param sys_expired_date the sys_expired_date to set
     */
    public void setSys_expired_date(String sys_expired_date) {
        this.sys_expired_date = sys_expired_date;
    }

    /**
     * @return the sys_status
     */
    public String getSys_status() {
        return sys_status;
    }

    /**
     * @param sys_status the sys_status to set
     */
    public void setSys_status(String sys_status) {
        this.sys_status = sys_status;
    }

    /**
     * @return the login_num
     */
    public int getLogin_num() {
        return login_num;
    }

    /**
     * @param login_num the login_num to set
     */
    public void setLogin_num(int login_num) {
        this.login_num = login_num;
    }

    /**
     * @return the failed_num
     */
    public int getFailed_num() {
        return failed_num;
    }

    /**
     * @param failed_num the failed_num to set
     */
    public void setFailed_num(int failed_num) {
        this.failed_num = failed_num;
    }

    /**
     * @return the session_id
     */
    public String getSession_id() {
        return session_id;
    }

    /**
     * @param session_id the session_id to set
     */
    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    /**
     * @return the password_edit_date
     */
    public String getPassword_edit_date() {
        return password_edit_date;
    }

    /**
     * @param password_edit_date the password_edit_date to set
     */
    public void setPassword_edit_date(String password_edit_date) {
        this.password_edit_date = password_edit_date;
    }

    /**
     * @return the edit_past_days
     */
    public int getEdit_past_days() {
        return edit_past_days;
    }

    /**
     * @param edit_past_days the edit_past_days to set
     */
    public void setEdit_past_days(int edit_past_days) {
        this.edit_past_days = edit_past_days;
    }
    private String sys_operator_id;
    private String sys_password_hash;
    private String sys_operator_name;
    private String sys_employee_id;
    private String sys_expired_date;
    private String sys_status;
    private int login_num;
    private int failed_num;
    private String session_id;
    private String password_edit_date;
    private int edit_past_days;

}
