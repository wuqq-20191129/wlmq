/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.sammcs.vo;

import com.goldsign.sammcs.env.AppConstant;

/**
 *
 * @author Administrator
 */
public class SamOrderPlanVo {
    //生产单号
    private String orderNo;
    //工作类型
    private String workType;
    //SAM类型
    private String samType;
    //生产数量
    private String orderNum;
    private String orderState;
    //起始逻辑卡号
    private String startLogicNo;
    //完成数量
    private String finishNum;
    //完成标志
    private String finishFlag;
    //备注
    private String remark;
    //审核人
    private String auditOrderOper;
    //审核时间
    private String auditOrderTime;
    //制卡人
    private String makeCardOper;
    //制卡时间
    private String makeCardTime;
    
    //SAM名称
    private String samTypeDesc;
    
    //完成标志名称
    private String finishFlagName;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }
    public String getSamType() {
        return samType;
    }

    public void setSamType(String samType) {
        this.samType = samType;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getStartLogicNo() {
        return startLogicNo;
    }

    public void setStartLogicNo(String startLogicNo) {
        this.startLogicNo = startLogicNo;
    }

    public String getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(String finishNum) {
        this.finishNum = finishNum;
    }

    public String getFinishFlag() {
        return finishFlag;
    }

    public void setFinishFlag(String finishFlag) {
        this.finishFlag = finishFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getMakeCardOper() {
        return makeCardOper;
    }

    public void setMakeCardOper(String makeCardOper) {
        this.makeCardOper = makeCardOper;
    }

    public String getMakeCardTime() {
        return makeCardTime;
    }

    public void setMakeCardTime(String makeCardTime) {
        this.makeCardTime = makeCardTime;
    }

    public String getSamTypeDesc() {
        return samTypeDesc;
    }

    public void setSamTypeDesc(String samTypeDesc) {
        this.samTypeDesc = samTypeDesc;
    }

    public String getFinishFlagName() {
        if (AppConstant.FINISH_FLAG_ALL_NOT_COMPLETE.equals(this.getFinishFlag())) {
            return AppConstant.FINISH_FLAG_ALL_NOT_COMPLETE_NAME;
        } else if (AppConstant.FINISH_FLAG_PART_COMPLETE.equals(this.getFinishFlag())) {
            return AppConstant.FINISH_FLAG_PART_COMPLETE_NAME;
        } else if (AppConstant.FINISH_FLAG_ALL_COMPLETE.equals(this.getFinishFlag())) {
            return AppConstant.FINISH_FLAG_ALL_COMPLETE_NAME;
        } else{
            return this.getFinishFlag();
        }
    }

    public void setFinishFlagName(String finishFlagName) {
        this.finishFlagName = finishFlagName;
    }
    
    
}
