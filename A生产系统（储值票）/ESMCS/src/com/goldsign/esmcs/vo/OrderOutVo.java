/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

/**
 *
 * @author lenovo
 */
public class OrderOutVo{
    
    private String tradeType;				// 交易类型
    private String orderNo;				// 订单编号
    private String ticketType;				// 票卡类型
    private String applicationNO;			//记名卡申请码
    private String logicalID;				// 票卡逻辑卡号
    private String physicalID;				// 票卡物理卡号
    private String date;				// 制票日期时间
    private String balance;				// 票卡余额, 单位为分(默认为)
    private String startDate;				// 逻辑有效期开始时间(BCD)
    private String endDate;				// 逻辑有效期结束时间(BCD)
    private String samID;				// E/S SAM逻辑卡号, 默认值:’’
    private String cardProducerCode;//hwj add 20160107增加卡商代码
    private String retCode;
    private String retMsg;

    /**
     * @return the tradeType
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * @param tradeType the tradeType to set
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

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
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the balance
     */
    public String getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(String balance) {
        this.balance = balance;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    @Override
    public String toString() {
        return "OrderOutVo{" + "tradeType=" + tradeType + ", orderNo=" + orderNo + ", ticketType=" + ticketType + ", applicationNO=" + applicationNO + ", logicalID=" + logicalID + ", physicalID=" + physicalID + ", date=" + date + ", balance=" + balance + ", startDate=" + startDate + ", endDate=" + endDate + ", samID=" + samID + '}';
    }

    /**
     * @return the retCode
     */
    public String getRetCode() {
        return retCode;
    }

    /**
     * @param retCode the retCode to set
     */
    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    /**
     * @return the retMsg
     */
    public String getRetMsg() {
        return retMsg;
    }

    /**
     * @param retMsg the retMsg to set
     */
    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
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
