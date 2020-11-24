/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */

public class FileRecord31 extends FileRecordBase{

    /**
     * @return the opDate_s
     */
    public String getOpDate_s() {
        return opDate_s;
    }

    /**
     * @param opDate_s the opDate_s to set
     */
    public void setOpDate_s(String opDate_s) {
        this.opDate_s = opDate_s;
    }

    /**
     * @return the curDatetime_s
     */
    public String getCurDatetime_s() {
        return curDatetime_s;
    }

    /**
     * @param curDatetime_s the curDatetime_s to set
     */
    public void setCurDatetime_s(String curDatetime_s) {
        this.curDatetime_s = curDatetime_s;
    }


    private String recordId;
    private String tradeType;

    
    private String opDate;//操作日期
    private String curDatetime;//当前时间
    private String opDate_s;//操作日期
    private String curDatetime_s;//当前时间
    private int boxPopNum;//SJT票箱取出次数
    
    private int totalNum;//交易总数量
    private int totalFee;//交易总金额
    private Vector dealDetail;//交易明细
    



    /**
     * @return the recordId
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }





    /**
     * @return the opDate
     */
    public String getOpDate() {
        return opDate;
    }

    /**
     * @param opDate the opDate to set
     */
    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }

    /**
     * @return the curDatetime
     */
    public String getCurDatetime() {
        return curDatetime;
    }

    /**
     * @param curDatetime the curDatetime to set
     */
    public void setCurDatetime(String curDatetime) {
        this.curDatetime = curDatetime;
    }

    /**
     * @return the boxPopNum
     */
    public int getBoxPopNum() {
        return boxPopNum;
    }

    /**
     * @param boxPopNum the boxPopNum to set
     */
    public void setBoxPopNum(int boxPopNum) {
        this.boxPopNum = boxPopNum;
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
     * @return the totalFee
     */
    public int getTotalFee() {
        return totalFee;
    }

    /**
     * @param totalFee the totalFee to set
     */
    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }
}
