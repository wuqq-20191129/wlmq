/*
 * 文件名：SortCardCondVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.esmcs.vo;


/*
 * 分拣查询条件Vo
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-9-29
 */

public class SortCardCondVo {
    
    private String issueStatus="";//发行状态
    private String ticketType="";//票卡类型
    private String status="";//卡片状态
    private String isBad="";//是否损坏
    private String cardBeginSeqNo="";//开始卡号序号
    private String cardEndSeqNo="";//结束卡号序号
    private String issueBeginDate="";//发行开始日期
    private String issueEndDate="";//发行结束日期
    private String lifeBeginCycle="";//生命开始周期
    private String lifeEndCycle="";//生命结束周期

    public String getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsBad() {
        return isBad;
    }

    public void setIsBad(String isBad) {
        this.isBad = isBad;
    }

    public String getIssueBeginDate() {
        return issueBeginDate;
    }

    public void setIssueBeginDate(String issueBeginDate) {
        this.issueBeginDate = issueBeginDate;
    }

    public String getIssueEndDate() {
        return issueEndDate;
    }

    public void setIssueEndDate(String issueEndDate) {
        this.issueEndDate = issueEndDate;
    }

    public String getLifeBeginCycle() {
        return lifeBeginCycle;
    }

    public void setLifeBeginCycle(String lifeBeginCycle) {
        this.lifeBeginCycle = lifeBeginCycle;
    }

    public String getLifeEndCycle() {
        return lifeEndCycle;
    }

    public void setLifeEndCycle(String lifeEndCycle) {
        this.lifeEndCycle = lifeEndCycle;
    }

    /**
     * @return the cardBeginSeqNo
     */
    public String getCardBeginSeqNo() {
        return cardBeginSeqNo;
    }

    /**
     * @param cardBeginSeqNo the cardBeginSeqNo to set
     */
    public void setCardBeginSeqNo(String cardBeginSeqNo) {
        this.cardBeginSeqNo = cardBeginSeqNo;
    }

    /**
     * @return the cardEndSeqNo
     */
    public String getCardEndSeqNo() {
        return cardEndSeqNo;
    }

    /**
     * @param cardEndSeqNo the cardEndSeqNo to set
     */
    public void setCardEndSeqNo(String cardEndSeqNo) {
        this.cardEndSeqNo = cardEndSeqNo;
    }
    
}
