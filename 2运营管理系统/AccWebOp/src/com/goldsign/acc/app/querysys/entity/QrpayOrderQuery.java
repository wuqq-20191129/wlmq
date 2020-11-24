/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.entity;

import java.io.Serializable;

/**
 *
 * @author luck 二维码订单查询
 */
public class QrpayOrderQuery implements Serializable {

    private Long waterNo;

    private String orderNo;  //订单号:8位日期+6位流水

    private Long saleFee;  //出票单程票单价(分)

    private String saleTimes; //出票售单程票数量

    private Long dealFee;  //出票售单程票总价(分)

    private String status;  //0:未支付1:已支付4:订单取消5:订单支付失败6:订单已退款
    private String statusString;

    private String orderDate;  //订单生成时间

    private String insertDate;  //插入时间

    private String cardType;   //出票票卡类型(主类型+子类型)
    private String cardTypeString;

    private String qrpayId;  //支付标识

    private String qrpayData; // 支付二维码信息

    private String phoneNo;  //支付手机号

    private String payDate;  //支付时间

    private String payStatus;  //支付结果 00:支付成功;01:余额不足;02:黑名单账户;10:支付通道通讯异常;99:其他异常
    private String payStatusString;

    private String payChannelType; //支付渠道类型 01:银行;02:银联;03:微信支付;04:支付宝支付;09:其他第三方支付99:其他
    private String payChannelTypeString;

    private String payChannelCode;  //支付渠道代码

    private Long saleFeeTotal;  //发售单程票单价(分)

    private Long saleTimesTotal;  //发售单程票数量

    private Long dealFeeTotal;  //发售单程票总价(分)

    private Long accSeq;  //中心处理流水

    private String orderIp;  //生成订单终端IP

    private String cardTypeTotal;  //发售票卡类型
    private String cardTypeTotalString;

    private String validTime;  //二维码有效期

    private Long refundFee;  //退款金额

    private String remark;

    private String updateTime;  //更新时间

    private String lastStatus;  //上次订单状态
    private String lastStatusString;

    private String beginTime;
    private String endTime;
    private String modify_operate;//修改人
    private String audit_operate;//审核人
    
    private String unusual;//是否为异常订单   0 异常，  1 正常

    public String getUnusual() {
        return unusual;
    }

    public void setUnusual(String unusual) {
        this.unusual = unusual;
    }
    
    

    public String getModify_operate() {
        return modify_operate;
    }

    public void setModify_operate(String modify_operate) {
        this.modify_operate = modify_operate;
    }

    public String getAudit_operate() {
        return audit_operate;
    }

    public void setAudit_operate(String audit_operate) {
        this.audit_operate = audit_operate;
    }

    public Long getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(Long waterNo) {
        this.waterNo = waterNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getSaleFee() {
        return saleFee;
    }

    public void setSaleFee(Long saleFee) {
        this.saleFee = saleFee;
    }

    public String getSaleTimes() {
        return saleTimes;
    }

    public void setSaleTimes(String saleTimes) {
        this.saleTimes = saleTimes;
    }

    public Long getDealFee() {
        return dealFee;
    }

    public void setDealFee(Long dealFee) {
        this.dealFee = dealFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardTypeString() {
        return cardTypeString;
    }

    public void setCardTypeString(String cardTypeString) {
        this.cardTypeString = cardTypeString;
    }

    public String getQrpayId() {
        return qrpayId;
    }

    public void setQrpayId(String qrpayId) {
        this.qrpayId = qrpayId;
    }

    public String getQrpayData() {
        return qrpayData;
    }

    public void setQrpayData(String qrpayData) {
        this.qrpayData = qrpayData;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatusString() {
        return payStatusString;
    }

    public void setPayStatusString(String payStatusString) {
        this.payStatusString = payStatusString;
    }

    public String getPayChannelType() {
        return payChannelType;
    }

    public void setPayChannelType(String payChannelType) {
        this.payChannelType = payChannelType;
    }

    public String getPayChannelTypeString() {
        return payChannelTypeString;
    }

    public void setPayChannelTypeString(String payChannelTypeString) {
        this.payChannelTypeString = payChannelTypeString;
    }

    public String getPayChannelCode() {
        return payChannelCode;
    }

    public void setPayChannelCode(String payChannelCode) {
        this.payChannelCode = payChannelCode;
    }

    public Long getSaleFeeTotal() {
        return saleFeeTotal;
    }

    public void setSaleFeeTotal(Long saleFeeTotal) {
        this.saleFeeTotal = saleFeeTotal;
    }

    public Long getSaleTimesTotal() {
        return saleTimesTotal;
    }

    public void setSaleTimesTotal(Long saleTimesTotal) {
        this.saleTimesTotal = saleTimesTotal;
    }

    public Long getDealFeeTotal() {
        return dealFeeTotal;
    }

    public void setDealFeeTotal(Long dealFeeTotal) {
        this.dealFeeTotal = dealFeeTotal;
    }

    public Long getAccSeq() {
        return accSeq;
    }

    public void setAccSeq(Long accSeq) {
        this.accSeq = accSeq;
    }

    public String getOrderIp() {
        return orderIp;
    }

    public void setOrderIp(String orderIp) {
        this.orderIp = orderIp;
    }

    public String getCardTypeTotal() {
        return cardTypeTotal;
    }

    public void setCardTypeTotal(String cardTypeTotal) {
        this.cardTypeTotal = cardTypeTotal;
    }

    public String getCardTypeTotalString() {
        return cardTypeTotalString;
    }

    public void setCardTypeTotalString(String cardTypeTotalString) {
        this.cardTypeTotalString = cardTypeTotalString;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public Long getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Long refundFee) {
        this.refundFee = refundFee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLastStatusString() {
        return lastStatusString;
    }

    public void setLastStatusString(String lastStatusString) {
        this.lastStatusString = lastStatusString;
    }

}
