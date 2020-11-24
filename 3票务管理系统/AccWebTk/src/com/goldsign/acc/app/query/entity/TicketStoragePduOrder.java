package com.goldsign.acc.app.query.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TicketStoragePduOrder implements Serializable {
    private String orderNo;

    private String esWorktypeId;

    private BigDecimal drawNum;

    private BigDecimal finiPronum;

    private BigDecimal surplusNum;

    private BigDecimal trashyNum;

    private String hdlFlag;

    private Date achieveTime;

    private String orderMemo;
    
    private String startAchieveTime;
    
    private String endAchieveTime;

    public String getStartAchieveTime() {
        return startAchieveTime;
    }

    public void setStartAchieveTime(String startAchieveTime) {
        this.startAchieveTime = startAchieveTime;
    }

    public String getEndAchieveTime() {
        return endAchieveTime;
    }

    public void setEndAchieveTime(String endAchieveTime) {
        this.endAchieveTime = endAchieveTime;
    }

    private static final long serialVersionUID = 1L;

    public TicketStoragePduOrder(String orderNo, String esWorktypeId, BigDecimal drawNum, BigDecimal finiPronum, BigDecimal surplusNum, BigDecimal trashyNum, String hdlFlag, Date achieveTime, String orderMemo) {
        this.orderNo = orderNo;
        this.esWorktypeId = esWorktypeId;
        this.drawNum = drawNum;
        this.finiPronum = finiPronum;
        this.surplusNum = surplusNum;
        this.trashyNum = trashyNum;
        this.hdlFlag = hdlFlag;
        this.achieveTime = achieveTime;
        this.orderMemo = orderMemo;
    }

    public TicketStoragePduOrder() {
        super();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getEsWorktypeId() {
        return esWorktypeId;
    }

    public void setEsWorktypeId(String esWorktypeId) {
        this.esWorktypeId = esWorktypeId == null ? null : esWorktypeId.trim();
    }

    public BigDecimal getDrawNum() {
        return drawNum;
    }

    public void setDrawNum(BigDecimal drawNum) {
        this.drawNum = drawNum;
    }

    public BigDecimal getFiniPronum() {
        return finiPronum;
    }

    public void setFiniPronum(BigDecimal finiPronum) {
        this.finiPronum = finiPronum;
    }

    public BigDecimal getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(BigDecimal surplusNum) {
        this.surplusNum = surplusNum;
    }

    public BigDecimal getTrashyNum() {
        return trashyNum;
    }

    public void setTrashyNum(BigDecimal trashyNum) {
        this.trashyNum = trashyNum;
    }

    public String getHdlFlag() {
        return hdlFlag;
    }

    public void setHdlFlag(String hdlFlag) {
        this.hdlFlag = hdlFlag == null ? null : hdlFlag.trim();
    }

    public Date getAchieveTime() {
        return achieveTime;
    }

    public void setAchieveTime(Date achieveTime) {
        this.achieveTime = achieveTime;
    }

    public String getOrderMemo() {
        return orderMemo;
    }

    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo == null ? null : orderMemo.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TicketStoragePduOrder other = (TicketStoragePduOrder) that;
        return (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getEsWorktypeId() == null ? other.getEsWorktypeId() == null : this.getEsWorktypeId().equals(other.getEsWorktypeId()))
            && (this.getDrawNum() == null ? other.getDrawNum() == null : this.getDrawNum().equals(other.getDrawNum()))
            && (this.getFiniPronum() == null ? other.getFiniPronum() == null : this.getFiniPronum().equals(other.getFiniPronum()))
            && (this.getSurplusNum() == null ? other.getSurplusNum() == null : this.getSurplusNum().equals(other.getSurplusNum()))
            && (this.getTrashyNum() == null ? other.getTrashyNum() == null : this.getTrashyNum().equals(other.getTrashyNum()))
            && (this.getHdlFlag() == null ? other.getHdlFlag() == null : this.getHdlFlag().equals(other.getHdlFlag()))
            && (this.getAchieveTime() == null ? other.getAchieveTime() == null : this.getAchieveTime().equals(other.getAchieveTime()))
            && (this.getOrderMemo() == null ? other.getOrderMemo() == null : this.getOrderMemo().equals(other.getOrderMemo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getEsWorktypeId() == null) ? 0 : getEsWorktypeId().hashCode());
        result = prime * result + ((getDrawNum() == null) ? 0 : getDrawNum().hashCode());
        result = prime * result + ((getFiniPronum() == null) ? 0 : getFiniPronum().hashCode());
        result = prime * result + ((getSurplusNum() == null) ? 0 : getSurplusNum().hashCode());
        result = prime * result + ((getTrashyNum() == null) ? 0 : getTrashyNum().hashCode());
        result = prime * result + ((getHdlFlag() == null) ? 0 : getHdlFlag().hashCode());
        result = prime * result + ((getAchieveTime() == null) ? 0 : getAchieveTime().hashCode());
        result = prime * result + ((getOrderMemo() == null) ? 0 : getOrderMemo().hashCode());
        return result;
    }
}