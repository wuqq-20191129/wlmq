/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.entity;

import java.io.Serializable;

/**
 *
 * @author luck 次票信息查询
 */
public class TctQuery implements Serializable {
    private String cardLogicalId;

    private String cardPhysicalId;
    
    private String cardMainId;
    private String cardMainName;

    private String cardSubId;
    private String cardSubName;

    private String validDateStart;

    private String validDateEnd;

    private double saleFee;  // 总价钱
    
    private int saleCount;  // 总次数

    private String balanceStatues; // 清算标志
    private String balanceStatuesString;

    private int consumeCount; // 累计消费次数
    
    private double consumeFee; //累计消费金额
    
    private double avgFee; // 平均单次价格
    
    private double remainFee; // 剩余金额

    private int updateCount;

    private String balanceWaterNo;
    
    private int delayDay;
    
    private int curConsumeCount; //当天消费次数
    
    private double curConsumeFee; //当天消费金额
    
    private String state; //账号状态 未激活 已激活 过期
    
    private String saleDate;// 发售时间
    
    private long validDay; // 有效期
    
    private String curDate;// 查询的当天时间

    public String getCardLogicalId() {
        return cardLogicalId;
    }

    public void setCardLogicalId(String cardLogicalId) {
        this.cardLogicalId = cardLogicalId;
    }

    public String getCardPhysicalId() {
        return cardPhysicalId;
    }

    public void setCardPhysicalId(String cardPhysicalId) {
        this.cardPhysicalId = cardPhysicalId;
    }

    public String getCardMainId() {
        return cardMainId;
    }

    public void setCardMainId(String cardMainId) {
        this.cardMainId = cardMainId;
    }

    public String getCardSubId() {
        return cardSubId;
    }

    public void setCardSubId(String cardSubId) {
        this.cardSubId = cardSubId;
    }

    public String getValidDateStart() {
        return validDateStart;
    }

    public void setValidDateStart(String validDateStart) {
        this.validDateStart = validDateStart;
    }

    public String getValidDateEnd() {
        return validDateEnd;
    }

    public void setValidDateEnd(String validDateEnd) {
        this.validDateEnd = validDateEnd;
    }

    public double getSaleFee() {
        return saleFee;
    }

    public void setSaleFee(double saleFee) {
        this.saleFee = saleFee;
    }


    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public String getBalanceStatues() {
        return balanceStatues;
    }

    public void setBalanceStatues(String balanceStatues) {
        this.balanceStatues = balanceStatues;
    }

    public int getConsumeCount() {
        return consumeCount;
    }

    public void setConsumeCount(int consumeCount) {
        this.consumeCount = consumeCount;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    public String getBalanceWaterNo() {
        return balanceWaterNo;
    }

    public void setBalanceWaterNo(String balanceWaterNo) {
        this.balanceWaterNo = balanceWaterNo;
    }


    public int getDelayDay() {
        return delayDay;
    }

    public void setDelayDay(int delayDay) {
        this.delayDay = delayDay;
    }

    public String getCardMainName() {
        return cardMainName;
    }

    public void setCardMainName(String cardMainName) {
        this.cardMainName = cardMainName;
    }

    public String getCardSubName() {
        return cardSubName;
    }

    public void setCardSubName(String cardSubName) {
        this.cardSubName = cardSubName;
    }

    public String getBalanceStatuesString() {
        return balanceStatuesString;
    }

    public void setBalanceStatuesString(String balanceStatuesString) {
        this.balanceStatuesString = balanceStatuesString;
    }

    public double getConsumeFee() {
        return consumeFee;
    }

    public void setConsumeFee(double consumeFee) {
        this.consumeFee = consumeFee;
    }

    public double getAvgFee() {
        return avgFee;
    }

    public void setAvgFee(double avgFee) {
        this.avgFee = avgFee;
    }

    public double getRemainFee() {
        return remainFee;
    }

    public void setRemainFee(double remainFee) {
        this.remainFee = remainFee;
    }

    public int getCurConsumeCount() {
        return curConsumeCount;
    }

    public void setCurConsumeCount(int curConsumeCount) {
        this.curConsumeCount = curConsumeCount;
    }

    public double getCurConsumeFee() {
        return curConsumeFee;
    }

    public void setCurConsumeFee(double curConsumeFee) {
        this.curConsumeFee = curConsumeFee;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public long getValidDay() {
        return validDay;
    }

    public void setValidDay(long validDay) {
        this.validDay = validDay;
    }

    public String getCurDate() {
        return curDate;
    }

    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    
    
    

}
