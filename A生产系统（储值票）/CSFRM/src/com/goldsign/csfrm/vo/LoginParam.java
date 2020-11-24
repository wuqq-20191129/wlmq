/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.csfrm.vo;

/**
 *
 * @author lenovo
 */
public class LoginParam extends CallParam {

    private String userNo;
    
    private String passwrod;

    /**
     * @return the userNo
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * @param userNo the userNo to set
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    /**
     * @return the passwrod
     */
    public String getPasswrod() {
        return passwrod;
    }

    /**
     * @param passwrod the passwrod to set
     */
    public void setPasswrod(String passwrod) {
        this.passwrod = passwrod;
    }
    
    
}
