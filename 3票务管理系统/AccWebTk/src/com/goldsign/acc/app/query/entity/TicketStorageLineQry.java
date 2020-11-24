/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.query.entity;

import java.io.Serializable;

/**
 * 线路库存预警查询
 * @author luck
 */
public class TicketStorageLineQry implements Serializable{

    private String deptId;

    private String tickettypeId;

    private int value;

    private int salenum;

    private int returnnum;

    private int currenttotal;

    private String reportdate;

    private String storageId;
    
    private String lineName;
    private String cardMainName;
    private String cardSubName;
    private int flag;
    private String flagName;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getTickettypeId() {
        return tickettypeId;
    }

    public void setTickettypeId(String tickettypeId) {
        this.tickettypeId = tickettypeId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSalenum() {
        return salenum;
    }

    public void setSalenum(int salenum) {
        this.salenum = salenum;
    }

    public int getReturnnum() {
        return returnnum;
    }

    public void setReturnnum(int returnnum) {
        this.returnnum = returnnum;
    }

    public int getCurrenttotal() {
        return currenttotal;
    }

    public void setCurrenttotal(int currenttotal) {
        this.currenttotal = currenttotal;
    }

    public String getReportdate() {
        return reportdate;
    }

    public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
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

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getFlagName() {
        return flagName;
    }

    public void setFlagName(String flagName) {
        this.flagName = flagName;
    }

    
    
    

}
