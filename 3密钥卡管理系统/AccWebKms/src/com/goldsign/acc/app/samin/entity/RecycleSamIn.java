/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samin.entity;

import java.util.List;

/**
 * 卡回收入库单
 *
 * @author luck
 */
public class RecycleSamIn extends SamLogicNos{

    private String orderNo; //入库单号

    private String lineEs;  //线路（ES）
    private String lineEsName;

    private String samType;  //SAM类型
    private String samTypeName; 

    private String startLogicNo;  //起始逻辑卡号

    private int orderNum;  //连续卡数量

    private String inStockOper;  //入库人员

    private String inStockTime;  //入库时间
 
    private String getCardOper;  //领卡人

    private String isBad;  //是否损坏 0:坏卡,1:好卡
    private String isBadName;

    private String orderState;  //单据状态 0:未审核,1:已审核
    private String orderStateName;
 
    private String auditOrderOper;  //审核人

    private String auditOrderTime;   //审核时间

    private String remark;  //备注
    
    private String fStartLogicNo; 
    private String eStartLogicNo;
    private List<String> orderNolist;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getLineEs() {
        return lineEs;
    }

    public void setLineEs(String lineEs) {
        this.lineEs = lineEs;
    }

    public String getSamType() {
        return samType;
    }

    public void setSamType(String samType) {
        this.samType = samType;
    }

    public String getStartLogicNo() {
        return startLogicNo;
    }

    public void setStartLogicNo(String startLogicNo) {
        this.startLogicNo = startLogicNo;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getInStockOper() {
        return inStockOper;
    }

    public void setInStockOper(String inStockOper) {
        this.inStockOper = inStockOper;
    }

    public String getInStockTime() {
        return inStockTime;
    }

    public void setInStockTime(String inStockTime) {
        this.inStockTime = inStockTime;
    }

    public String getGetCardOper() {
        return getCardOper;
    }

    public void setGetCardOper(String getCardOper) {
        this.getCardOper = getCardOper;
    }

    public String getIsBad() {
        return isBad;
    }

    public void setIsBad(String isBad) {
        this.isBad = isBad;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getAuditOrderOper() {
        return auditOrderOper;
    }

    public void setAuditOrderOper(String auditOrderOper) {
        this.auditOrderOper = auditOrderOper;
    }

    public String getAuditOrderTime() {
        return auditOrderTime;
    }

    public void setAuditOrderTime(String auditOrderTime) {
        this.auditOrderTime = auditOrderTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getfStartLogicNo() {
        return fStartLogicNo;
    }

    public void setfStartLogicNo(String fStartLogicNo) {
        this.fStartLogicNo = fStartLogicNo;
    }

    public String geteStartLogicNo() {
        return eStartLogicNo;
    }

    public void seteStartLogicNo(String eStartLogicNo) {
        this.eStartLogicNo = eStartLogicNo;
    }

    public String getSamTypeName() {
        return samTypeName;
    }

    public void setSamTypeName(String samTypeName) {
        this.samTypeName = samTypeName;
    }

    public String getIsBadName() {
        return isBadName;
    }

    public void setIsBadName(String isBadName) {
        this.isBadName = isBadName;
    }

    public String getOrderStateName() {
        return orderStateName;
    }

    public void setOrderStateName(String orderStateName) {
        this.orderStateName = orderStateName;
    }

    public String getLineEsName() {
        return lineEsName;
    }

    public void setLineEsName(String lineEsName) {
        this.lineEsName = lineEsName;
    }

    public List<String> getOrderNolist() {
        return orderNolist;
    }

    public void setOrderNolist(List<String> orderNolist) {
        this.orderNolist = orderNolist;
    }
    
    
    
}
