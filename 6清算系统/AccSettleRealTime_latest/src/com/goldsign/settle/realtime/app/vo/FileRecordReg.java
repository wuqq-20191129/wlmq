/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileRecordReg extends FileRecordBase {

    /**
     * @return the tradeType
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * @param tradeType the tradeType to set
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * @return the commuDatetime
     */
    public String getCommuDatetime() {
        return commuDatetime;
    }

    /**
     * @param commuDatetime the commuDatetime to set
     */
    public void setCommuDatetime(String commuDatetime) {
        this.commuDatetime = commuDatetime;
    }

    /**
     * @return the genDatetime
     */
    public String getGenDatetime() {
        return genDatetime;
    }

    /**
     * @param genDatetime the genDatetime to set
     */
    public void setGenDatetime(String genDatetime) {
        this.genDatetime = genDatetime;
    }

    /**
     * @return the totalNum
     */
    public int getTotalNum() {
        return totalNum;
    }

    /**
     * @param totalNum the totalNum to set
     */
    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    /**
     * @return the dealDetail
     */
    public Vector getDealDetail() {
        return dealDetail;
    }

    /**
     * @param dealDetail the dealDetail to set
     */
    public void setDealDetail(Vector dealDetail) {
        this.dealDetail = dealDetail;
    }
     private String tradeType;
    private String commuDatetime;//通讯消息时间
    private String genDatetime;//产生日期/时间
    private int totalNum;//寄存器子条目数量
    private Vector dealDetail;//交易明细
    
}
