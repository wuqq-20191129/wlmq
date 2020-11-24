/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.esmcs.vo;

/**
 *
 * @author lenovo
 */
public class EsAnayzeVo {

    private String status;				// 交易状态－参见附录四
    private String ticketType;				// 票卡类型
    private String logicalID;				// 票卡逻辑卡号
    private String physicalID;				// 票卡物理卡号
    private String character;                           // 票卡物理类型OCT,UL,FM,F:其他
    private String issueStatus;                         // 发行状态0 –未发行;1 –已发行;2 –注销
    private String issueDate;				// 制票日期时间日期时间
    private String expire;				// 物理有效期, YYYYMMDDHHMMSS，
    private int balance;				// 余额, 单位为分/次(默认为)
    private int deposite;				// 押金

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
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
     * @return the character
     */
    public String getCharacter() {
        return character;
    }

    /**
     * @param character the character to set
     */
    public void setCharacter(String character) {
        this.character = character;
    }

    /**
     * @return the issueStatus
     */
    public String getIssueStatus() {
        return issueStatus;
    }

    /**
     * @param issueStatus the issueStatus to set
     */
    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
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
     * @return the expire
     */
    public String getExpire() {
        return expire;
    }

    /**
     * @param expire the expire to set
     */
    public void setExpire(String expire) {
        this.expire = expire;
    }

    /**
     * @return the balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * @return the deposite
     */
    public int getDeposite() {
        return deposite;
    }

    /**
     * @param deposite the deposite to set
     */
    public void setDeposite(int deposite) {
        this.deposite = deposite;
    }

    
}
