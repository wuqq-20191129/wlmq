/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.entity;

import java.util.List;

/**
 * 核销出库计划单
 *
 * @author luck
 */
public class TicketStorageOutCancelPlan extends TicketStorageOutCancelPlanDetial {

    private String billNo;   //计划单号

    private String outBillNo;  //对应出库单号

    private String billDate;  //制单日期

    private String formMaker;  //制单人

    private String operator;  //ES操作员

    private String verifyPerson; //审核人

    private String executeDate;  //计划单执行时间

    private String verifyDate;  //确认时

    private String recordFlag;  //单据状态"0":单据有效"1":单据撤消（对未审核单据）"2":单据删除（对有效单据）”3“:单据未审核
    private String recordFlagName;

    private String remark;  //备注

    private String User; //登录人
    private List<String> storageIds; //可操作仓库
    private List<String> storageAllIds; //全部仓库
    private String beginDate;
    private String endDate;

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getOutBillNo() {
        return outBillNo;
    }

    public void setOutBillNo(String outBillNo) {
        this.outBillNo = outBillNo;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getFormMaker() {
        return formMaker;
    }

    public void setFormMaker(String formMaker) {
        this.formMaker = formMaker;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getVerifyPerson() {
        return verifyPerson;
    }

    public void setVerifyPerson(String verifyPerson) {
        this.verifyPerson = verifyPerson;
    }

    public String getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(String executeDate) {
        this.executeDate = executeDate;
    }

    public String getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(String verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public List<String> getStorageIds() {
        return storageIds;
    }

    public void setStorageIds(List<String> storageIds) {
        this.storageIds = storageIds;
    }

    public List<String> getStorageAllIds() {
        return storageAllIds;
    }

    public void setStorageAllIds(List<String> storageAllIds) {
        this.storageAllIds = storageAllIds;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRecordFlagName() {
        return recordFlagName;
    }

    public void setRecordFlagName(String recordFlagName) {
        this.recordFlagName = recordFlagName;
    }
    
    

}
