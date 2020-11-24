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
public class FileRecord58 extends FileRecordBase{

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
    private String cardPrintId;//8	票卡印刻号
    private String applyDatetime;//9	日期时间
    private String receiptId;//10	凭证ID
    private String telNo;//13	乘客电话
    private String applyName;//12	乘客姓名
    private String identityType;//14	证件类型
    private String identityId;//15	证件号码
    private String isBroken;//16	票卡是否折损
    
    private String applyDatetime_s;//9	日期时间

    /**
     * @return the cardPrintId
     */
    public String getCardPrintId() {
        return cardPrintId;
    }

    /**
     * @param cardPrintId the cardPrintId to set
     */
    public void setCardPrintId(String cardPrintId) {
        this.cardPrintId = cardPrintId;
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
     * @return the receiptId
     */
    public String getReceiptId() {
        return receiptId;
    }

    /**
     * @param receiptId the receiptId to set
     */
    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
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
     * @return the isBroken
     */
    public String getIsBroken() {
        return isBroken;
    }

    /**
     * @param isBroken the isBroken to set
     */
    public void setIsBroken(String isBroken) {
        this.isBroken = isBroken;
    }
    
}
