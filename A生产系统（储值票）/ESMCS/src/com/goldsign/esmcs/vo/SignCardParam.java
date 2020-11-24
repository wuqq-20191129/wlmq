/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

/**
 *
 * @author lenovo
 */
public class SignCardParam {

    private String certificateNo;
    
    private String certificateName;
    
    private String sex;
    
    private String idType;//证件类型

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
    
    /**
     * @return the idType
     */
    public String getIdType() {
        return idType;
    }

    /**
     * @param idType the idType to set
     */
    public void setIdType(String idType) {
        this.idType = idType;
    }

    @Override
    public String toString() {
        return "SignCardParam{" + "certificateNo=" + certificateNo + ", certificateName=" + certificateName + ", sex=" + sex + ", idType=" + idType + '}';
    }

}
