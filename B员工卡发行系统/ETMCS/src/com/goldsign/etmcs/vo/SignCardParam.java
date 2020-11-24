/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.vo;

/**
 *
 * @author lenovo
 */
public class SignCardParam {

    private String certificateNo;
    
    private String certificateName;
    
    private String sex;
    
    /**
     * @return the employeeNo
     */
    public String getEmployeeNo() {
        return certificateNo;
    }

    /**
     * @param employeeNo the employeeNo to set
     */
    public void setEmployeeNo(String employeeNo) {
        this.certificateNo = employeeNo;
    }

    /**
     * @return the employeeName
     */
    public String getEmployeeName() {
        return certificateName;
    }

    /**
     * @param employeeName the employeeName to set
     */
    public void setEmployeeName(String employeeName) {
        this.certificateName = employeeName;
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

}
