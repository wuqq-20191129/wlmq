/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
public class FileRecord52 extends FileRecordBase{

    /**
     * @return the expiredDate_s
     */
    public String getExpiredDate_s() {
        return expiredDate_s;
    }

    /**
     * @param expiredDate_s the expiredDate_s to set
     */
    public void setExpiredDate_s(String expiredDate_s) {
        this.expiredDate_s = expiredDate_s;
    }

    /**
     * @return the applyDatetime_s
     */
    public String getApplyDatetime_s() {
        return applyDatetime_s;
    }

    /**
     * @param applyDatetime_s the applyDatetime_s to set
     */
    public void setApplyDatetime_s(String applyDatetime_s) {
        this.applyDatetime_s = applyDatetime_s;
    }

    private String applyName;//5	姓名
    private String applySex;//6	性别
    
    private String identityType;//7	证件类型
    private String identityId;//8	证件号码
    private String expiredDate;//9	证件有效期
    private String telNo;//10	电话
    private String fax;//11	传真
    private String address;//12	通讯地址
    private String applyDatetime;//14	申请日期
    
    private String applyBusinessType;//业务类型
    
     private String expiredDate_s;//9	证件有效期
     private String applyDatetime_s;//14	申请日期
    
    

    /**
     * @return the applyName
     */
    public String getApplyName() {
        return applyName;
    }

    /**
     * @param applyName the applyName to set
     */
    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    /**
     * @return the applySex
     */
    public String getApplySex() {
        return applySex;
    }

    /**
     * @param applySex the applySex to set
     */
    public void setApplySex(String applySex) {
        this.applySex = applySex;
    }

    /**
     * @return the identityType
     */
    public String getIdentityType() {
        return identityType;
    }

    /**
     * @param identityType the identityType to set
     */
    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    /**
     * @return the identityId
     */
    public String getIdentityId() {
        return identityId;
    }

    /**
     * @param identityId the identityId to set
     */
    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    /**
     * @return the expiredDate
     */
    public String getExpiredDate() {
        return expiredDate;
    }

    /**
     * @param expiredDate the expiredDate to set
     */
    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    /**
     * @return the telNo
     */
    public String getTelNo() {
        return telNo;
    }

    /**
     * @param telNo the telNo to set
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the applyDatetime
     */
    public String getApplyDatetime() {
        return applyDatetime;
    }

    /**
     * @param applyDatetime the applyDatetime to set
     */
    public void setApplyDatetime(String applyDatetime) {
        this.applyDatetime = applyDatetime;
    }

    /**
     * @return the applyBusinessType
     */
    public String getApplyBusinessType() {
        return applyBusinessType;
    }

    /**
     * @param applyBusinessType the applyBusinessType to set
     */
    public void setApplyBusinessType(String applyBusinessType) {
        this.applyBusinessType = applyBusinessType;
    }
    
    
}
