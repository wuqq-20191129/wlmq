/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samin.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 逻辑卡号库存
 *
 * @author luck
 */
public class SamStock implements Serializable {

    private String logicNo;  //逻辑卡号

    private String samType;  //SAM类型

    private String phyNo;  //物理卡号

    private String printNo;  //卡面号

    private String distributePlace;   //分发地方

    private String produceType;  //产品类型 00:空白卡,01:成品卡

    private String stockState;   //库存状态 00:空白卡入库,01:生产计划单,02:卡发行出库,03:成品卡入库,04:卡分发出库,05:卡回收入库,06:卡制作

    private String isInstock;   //是否在库 0:出库,1:在库

    private String isBad;    //是否损坏 0:坏卡,1:好卡

    private String remark;    //备注

    private String cardProducerCode;
    
    private List<String> logicNos;
    
    private int num;

    public String getLogicNo() {
        return logicNo;
    }

    public void setLogicNo(String logicNo) {
        this.logicNo = logicNo;
    }

    public String getSamType() {
        return samType;
    }

    public void setSamType(String samType) {
        this.samType = samType;
    }

    public String getPhyNo() {
        return phyNo;
    }

    public void setPhyNo(String phyNo) {
        this.phyNo = phyNo;
    }

    public String getPrintNo() {
        return printNo;
    }

    public void setPrintNo(String printNo) {
        this.printNo = printNo;
    }

    public String getDistributePlace() {
        return distributePlace;
    }

    public void setDistributePlace(String distributePlace) {
        this.distributePlace = distributePlace;
    }

    public String getProduceType() {
        return produceType;
    }

    public void setProduceType(String produceType) {
        this.produceType = produceType;
    }

    public String getStockState() {
        return stockState;
    }

    public void setStockState(String stockState) {
        this.stockState = stockState;
    }

    public String getIsInstock() {
        return isInstock;
    }

    public void setIsInstock(String isInstock) {
        this.isInstock = isInstock;
    }

    public String getIsBad() {
        return isBad;
    }

    public void setIsBad(String isBad) {
        this.isBad = isBad;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCardProducerCode() {
        return cardProducerCode;
    }

    public void setCardProducerCode(String cardProducerCode) {
        this.cardProducerCode = cardProducerCode;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<String> getLogicNos() {
        return logicNos;
    }

    public void setLogicNos(List<String> logicNos) {
        this.logicNos = logicNos;
    }
    
    
    
}
