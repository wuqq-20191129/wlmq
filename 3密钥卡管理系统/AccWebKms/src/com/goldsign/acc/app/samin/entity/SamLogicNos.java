/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samin.entity;

/**
 * 订单逻辑卡号
 *
 * @author luck
 */
public class SamLogicNos extends SamStock{

    private String orderNo;

    private String startLogicNo;

    private String endLogicNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStartLogicNo() {
        return startLogicNo;
    }

    public void setStartLogicNo(String startLogicNo) {
        this.startLogicNo = startLogicNo;
    }

    public String getEndLogicNo() {
        return endLogicNo;
    }

    public void setEndLogicNo(String endLogicNo) {
        this.endLogicNo = endLogicNo;
    }
    
    
}
