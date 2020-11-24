/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

/**
 *
 * @author lenovo
 */
public class SignCardVo {

    private String reqNo;
    
    private String name;//姓名
    
    private String gender;//性别
    
    private String idType;//证件类型
    
    private String idCode;//证件号
    
    private String idEffDate;

    /**
     * @return the reqNo
     */
    public String getReqNo() {
        return reqNo;
    }

    /**
     * @param reqNo the reqNo to set
     */
    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
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

    /**
     * @return the idCode
     */
    public String getIdCode() {
        return idCode;
    }

    /**
     * @param idCode the idCode to set
     */
    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    /**
     * @return the idEffDate
     */
    public String getIdEffDate() {
        return idEffDate;
    }

    /**
     * @param idEffDate the idEffDate to set
     */
    public void setIdEffDate(String idEffDate) {
        this.idEffDate = idEffDate;
    }

    @Override
    public String toString() {
        return "SignCardVo{" + "reqNo=" + reqNo + ", name=" + name + ", gender=" + gender + ", idType=" + idType + ", idCode=" + idCode + ", idEffDate=" + idEffDate + '}';
    }

}
