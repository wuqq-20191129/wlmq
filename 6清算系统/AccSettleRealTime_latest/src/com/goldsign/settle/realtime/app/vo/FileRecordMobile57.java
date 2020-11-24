/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordTacBase;

/**
 *
 * @author hejj
 */
public class FileRecordMobile57 extends FileRecord57{
      //手机支付相关
    private String mobileNo;//支付渠道代码
    private String paidChannelType;//支付渠道类型
    private String paidChannelCode;//支付渠道代码

    /**
     * @return the mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * @param mobileNo the mobileNo to set
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * @return the paidChannelType
     */
    public String getPaidChannelType() {
        return paidChannelType;
    }

    /**
     * @param paidChannelType the paidChannelType to set
     */
    public void setPaidChannelType(String paidChannelType) {
        this.paidChannelType = paidChannelType;
    }

    /**
     * @return the paidChannelCode
     */
    public String getPaidChannelCode() {
        return paidChannelCode;
    }

    /**
     * @param paidChannelCode the paidChannelCode to set
     */
    public void setPaidChannelCode(String paidChannelCode) {
        this.paidChannelCode = paidChannelCode;
    }
    
}
