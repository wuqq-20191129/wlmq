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
public class SamIssueDetailVo {
    
    private String orderNo;
            
    private String samType;
    
    private String logicNo;
    
    private String makeCardOper;
    
    private String makeCardTime;
    
    private String makeResult;
    
    private String remark;
    
    //SAM名称
    private String samTypeDesc;
    
    private String makeResultDesc;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSamType() {
        return samType;
    }

    public void setSamType(String samType) {
        this.samType = samType;
    }

    public String getLogicNo() {
        return logicNo;
    }

    public void setLogicNo(String logicNo) {
        this.logicNo = logicNo;
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

    public String getMakeResult() {
        return makeResult;
    }

    public void setMakeResult(String makeResult) {
        this.makeResult = makeResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSamTypeDesc() {
        return samTypeDesc;
    }

    public void setSamTypeDesc(String samTypeDesc) {
        this.samTypeDesc = samTypeDesc;
    }

    public String getMakeResultDesc() {
        if (AppConstant.SAM_CARD_MAKE_RESULT_SUCCESS.equals(this.getMakeResult())) {
            return AppConstant.SAM_CARD_MAKE_RESULT_SUCCESS_NAME;
        } else if (AppConstant.SAM_CARD_MAKE_RESULT_FAIL.equals(this.getMakeResult())) {
            return AppConstant.SAM_CARD_MAKE_RESULT_FAIL_NAME;
        } 
        return this.getMakeResult();
    }

    public void setMakeResultDesc(String makeResultDesc) {
        this.makeResultDesc = makeResultDesc;
    }
    
    
    
}
