/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.sammcs.vo;

/**
 *
 * @author Administrator
 */
public class MakeCardQueryVo {
    
    private String orderNo;
    
    private String samType;
    
    private String finishFlag;
    
    private String workType;

   
    
    private String makeCardTimeBegin;
    
    private String makeCardTimeEnd;

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

    public String getFinishFlag() {
        return finishFlag;
    }

    public void setFinishFlag(String finishFlag) {
        this.finishFlag = finishFlag;
    }
    
     public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getMakeCardTimeBegin() {
        return makeCardTimeBegin;
    }

    public void setMakeCardTimeBegin(String makeCardTimeBegin) {
        this.makeCardTimeBegin = makeCardTimeBegin;
    }

    public String getMakeCardTimeEnd() {
        return makeCardTimeEnd;
    }

    public void setMakeCardTimeEnd(String makeCardTimeEnd) {
        this.makeCardTimeEnd = makeCardTimeEnd;
    }
    
    
    
    
    
}
