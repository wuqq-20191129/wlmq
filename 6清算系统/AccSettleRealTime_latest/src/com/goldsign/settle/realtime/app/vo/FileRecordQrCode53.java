/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

/**
 *
 * @author hejj
 */
public class FileRecordQrCode53 extends FileRecord53 {

    private String businessWaterNo;//业务流水
    private String mobileNo;//手机号
   

    /**
     * @return the businessWaterNo
     */
    public String getBusinessWaterNo() {
        return businessWaterNo;
    }

    /**
     * @param businessWaterNo the businessWaterNo to set
     */
    public void setBusinessWaterNo(String businessWaterNo) {
        this.businessWaterNo = businessWaterNo;
    }

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

}
