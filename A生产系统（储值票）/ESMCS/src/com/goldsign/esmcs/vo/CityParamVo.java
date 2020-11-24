/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

/**
 *
 * @author lenovo
 */
public class CityParamVo {

    private String cityCode;
    
    private String industryCode;
    
    private String keyVersion;
    
    private String senderCode;
    
    private String cardVersion;
    
    private String appVersion;

    /**
     * @return the cityCode
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * @param cityCode the cityCode to set
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * @return the industryCode
     */
    public String getIndustryCode() {
        return industryCode;
    }

    /**
     * @param industryCode the industryCode to set
     */
    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    /**
     * @return the keyVersion
     */
    public String getKeyVersion() {
        return keyVersion;
    }

    /**
     * @param keyVersion the keyVersion to set
     */
    public void setKeyVersion(String keyVersion) {
        this.keyVersion = keyVersion;
    }

    /**
     * @return the senderCode
     */
    public String getSenderCode() {
        return senderCode;
    }

    /**
     * @param senderCode the senderCode to set
     */
    public void setSenderCode(String senderCode) {
        this.senderCode = senderCode;
    }

    /**
     * @return the cardVersion
     */
    public String getCardVersion() {
        return cardVersion;
    }

    /**
     * @param cardVersion the cardVersion to set
     */
    public void setCardVersion(String cardVersion) {
        this.cardVersion = cardVersion;
    }

    /**
     * @return the appVersion
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * @param appVersion the appVersion to set
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Override
    public String toString() {
        return "CityParamVo{" + "cityCode=" + cityCode + ", industryCode=" + industryCode + ", keyVersion=" + keyVersion + ", senderCode=" + senderCode + ", cardVersion=" + cardVersion + ", appVersion=" + appVersion + '}';
    }
    
}
