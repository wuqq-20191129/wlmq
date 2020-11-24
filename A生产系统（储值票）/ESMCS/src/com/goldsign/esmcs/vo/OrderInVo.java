/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

import com.goldsign.csfrm.util.CharUtil;

/**
 *
 * @author lenovo
 */
public class OrderInVo{
    
    private String orderNo;  				// 订单号
    private String applicationNO;			// 记名卡申请码
    private String ticketType;                          // 车票类型
    private String logicalID;                           //逻辑卡号
    private String deposite;				// 押金
    private String value;                                // 车票钱包预赋金额
    private String rechargeTopValue;
    private String saleActiveFlag;
    private String senderCode;
    private String cityCode;
    private String busiCode;
    private String testFlag;
    private String issueDate;    
    private String cardVersion;
    private String cstartExpire;                        // 物理有效期开始时间(BCD)
    private String cendExpire;                          // 物理有效期结束时间(BCD)
//    private String cstartDate;				// 逻辑有效期开始时间(BCD)
//    private String cendDate;				// 逻辑有效期结束时间(BCD)
    private String logicDate;                           //票卡逻辑有效期，需在物理有效期内，储值卡有效
    private String logicDays;                           //逻辑有效天数,储值卡有效
    private String appVersion;
    private String exitEntryMode;
    private String entryLineStation;
    private String exitLineStation;
    private String physicalID;				// 票卡物理卡号
    private String samID;
    private String cardProducerCode;//hwj add 20160107增加卡商代码

    /**
     * @return the orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return the applicationNO
     */
    public String getApplicationNO() {
        return applicationNO;
    }

    /**
     * @param applicationNO the applicationNO to set
     */
    public void setApplicationNO(String applicationNO) {
        this.applicationNO = applicationNO;
    }

    /**
     * @return the ticketType
     */
    public String getTicketType() {
        return ticketType;
    }

    /**
     * @param ticketType the ticketType to set
     */
    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    /**
     * @return the logicalID
     */
    public String getLogicalID() {
        return logicalID;
    }

    /**
     * @param logicalID the logicalID to set
     */
    public void setLogicalID(String logicalID) {
        this.logicalID = logicalID;
    }

    /**
     * @return the deposite
     */
    public String getDeposite() {
        return deposite;
    }
    
    public String getDepositeYuan() {
        if (null == deposite || deposite.equals("")) {
            return deposite;
        }
        return CharUtil.strToInt(deposite) / 100 + "";
    }

    /**
     * @param deposite the deposite to set
     */
    public void setDeposite(String deposite) {
        this.deposite = deposite;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the rechargeTopValue
     */
    public String getRechargeTopValue() {
        return rechargeTopValue;
    }
    
    public String getRechargeTopValueYuan() {
        if (null == rechargeTopValue || rechargeTopValue.equals("")) {
            return rechargeTopValue;
        }
        return CharUtil.strToInt(rechargeTopValue) / 100 + "";
    }

    /**
     * @param rechargeTopValue the rechargeTopValue to set
     */
    public void setRechargeTopValue(String rechargeTopValue) {
        this.rechargeTopValue = rechargeTopValue;
    }

    /**
     * @return the saleActiveFlag
     */
    public String getSaleActiveFlag() {
        return saleActiveFlag;
    }

    /**
     * @param saleActiveFlag the saleActiveFlag to set
     */
    public void setSaleActiveFlag(String saleActiveFlag) {
        this.saleActiveFlag = saleActiveFlag;
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
     * @return the busiCode
     */
    public String getBusiCode() {
        return busiCode;
    }

    /**
     * @param busiCode the busiCode to set
     */
    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }

    /**
     * @return the testFlag
     */
    public String getTestFlag() {
        return testFlag;
    }

    /**
     * @param testFlag the testFlag to set
     */
    public void setTestFlag(String testFlag) {
        this.testFlag = testFlag;
    }

    /**
     * @return the issueDate
     */
    public String getIssueDate() {
        return issueDate;
    }

    /**
     * @param issueDate the issueDate to set
     */
    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
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
    
    public String getcStartExpire() {
        return cstartExpire;
    }

    public void setcStartExpire(String cstartExpire) {
        this.cstartExpire = cstartExpire;
    }

    public String getcEndExpire() {
        return cendExpire;
    }

    public void setcEndExpire(String cendExpire) {
        this.cendExpire = cendExpire;
    }
    /**
     * @return the startDate
     */
//    public String getcStartDate() {
//        return cstartDate;
//    }
//
//    /**
//     * @param startDate the cstartDate to set
//     */
//    public void setcStartDate(String startDate) {
//        this.cstartDate = startDate;
//    }
//
//    /**
//     * @return the cendDate
//     */
//    public String getcEndDate() {
//        return cendDate;
//    }
//
//    /**
//     * @param endDate the endDate to set
//     */
//    public void setcEndDate(String endDate) {
//        this.cendDate = endDate;
//    }

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

    /**
     * @return the exitEntryMode
     */
    public String getExitEntryMode() {
        return exitEntryMode;
    }

    /**
     * @param exitEntryMode the exitEntryMode to set
     */
    public void setExitEntryMode(String exitEntryMode) {
        this.exitEntryMode = exitEntryMode;
    }

    /**
     * @return the entryLineStation
     */
    public String getEntryLineStation() {
        return entryLineStation;
    }

    /**
     * @param entryLineStation the entryLineStation to set
     */
    public void setEntryLineStation(String entryLineStation) {
        this.entryLineStation = entryLineStation;
    }

    /**
     * @return the exitLineStation
     */
    public String getExitLineStation() {
        return exitLineStation;
    }

    /**
     * @param exitLineStation the exitLineStation to set
     */
    public void setExitLineStation(String exitLineStation) {
        this.exitLineStation = exitLineStation;
    }
    
    /**
     * @return the physicalID
     */
    public String getPhysicalID() {
        return physicalID;
    }

    /**
     * @param physicalID the physicalID to set
     */
    public void setPhysicalID(String physicalID) {
        this.physicalID = physicalID;
    }

    /**
     * @return the samID
     */
    public String getSamID() {
        return samID;
    }

    /**
     * @param samID the samID to set
     */
    public void setSamID(String samID) {
        this.samID = samID;
    }

    /**
     * @return the logicDate
     */
    public String getLogicDate() {
        return logicDate;
    }

    /**
     * @param logicDate the logicDate to set
     */
    public void setLogicDate(String logicDate) {
        this.logicDate = logicDate;
    }

    /**
     * @return the logicDays
     */
    public String getLogicDays() {
        return logicDays;
    }

    /**
     * @param logicDays the logicDays to set
     */
    public void setLogicDays(String logicDays) {
        this.logicDays = logicDays;
    }

    @Override
    public String toString() {
        return "OrderInVo{" + "orderNo=" + orderNo + ", applicationNO=" + applicationNO + ", ticketType=" + ticketType + ", logicalID=" + logicalID + ", deposite=" + deposite + ", value=" + value + ", rechargeTopValue=" + rechargeTopValue + ", saleActiveFlag=" + saleActiveFlag + ", senderCode=" + senderCode + ", cityCode=" + cityCode + ", busiCode=" + busiCode + ", testFlag=" + testFlag + ", issueDate=" + issueDate + ", cardVersion=" + cardVersion + ", cstartExpire=" + cstartExpire + ", cendExpire=" + cendExpire + ", logicDate=" + logicDate + ", logicDays=" + logicDays + ", appVersion=" + appVersion + ", exitEntryMode=" + exitEntryMode + ", entryLineStation=" + entryLineStation + ", exitLineStation=" + exitLineStation + ", physicalID=" + physicalID + ", samID=" + samID + '}';
    }

    /**
     * @return the cardProducerCode
     */
    public String getCardProducerCode() {
        return cardProducerCode;
    }

    /**
     * @param cardProducerCode the cardProducerCode to set
     */
    public void setCardProducerCode(String cardProducerCode) {
        this.cardProducerCode = cardProducerCode;
    }
    
}
