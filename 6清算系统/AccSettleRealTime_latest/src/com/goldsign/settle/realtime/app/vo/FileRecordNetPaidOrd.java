/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

/**
 *
 * @author hejj
 */
public class FileRecordNetPaidOrd extends FileRecordNetPaidOrdBase {

    private String generateDatetime;
    private String paidDatetime;
    private String lineIdStart;
    private String stationIdStart;
    private String lineIdEnd;
    private String stationIdEnd;
    private int dealUnitFee;
    private int dealNum;
    private int dealFee;
    private String orderTypeBuy;
    private String paidChannelType;
    private String paidChannelCode;
    private String mobileNo;

    /**
     * @return the generateDatetime
     */
    public String getGenerateDatetime() {
        return generateDatetime;
    }

    /**
     * @param generateDatetime the generateDatetime to set
     */
    public void setGenerateDatetime(String generateDatetime) {
        this.generateDatetime = generateDatetime;
    }

    /**
     * @return the paidDatetime
     */
    public String getPaidDatetime() {
        return paidDatetime;
    }

    /**
     * @param paidDatetime the paidDatetime to set
     */
    public void setPaidDatetime(String paidDatetime) {
        this.paidDatetime = paidDatetime;
    }

    /**
     * @return the lineIdStart
     */
    public String getLineIdStart() {
        return lineIdStart;
    }

    /**
     * @param lineIdStart the lineIdStart to set
     */
    public void setLineIdStart(String lineIdStart) {
        this.lineIdStart = lineIdStart;
    }

    /**
     * @return the stationIdStart
     */
    public String getStationIdStart() {
        return stationIdStart;
    }

    /**
     * @param stationIdStart the stationIdStart to set
     */
    public void setStationIdStart(String stationIdStart) {
        this.stationIdStart = stationIdStart;
    }

    /**
     * @return the lineIdEnd
     */
    public String getLineIdEnd() {
        return lineIdEnd;
    }

    /**
     * @param lineIdEnd the lineIdEnd to set
     */
    public void setLineIdEnd(String lineIdEnd) {
        this.lineIdEnd = lineIdEnd;
    }

    /**
     * @return the stationIdEnd
     */
    public String getStationIdEnd() {
        return stationIdEnd;
    }

    /**
     * @param stationIdEnd the stationIdEnd to set
     */
    public void setStationIdEnd(String stationIdEnd) {
        this.stationIdEnd = stationIdEnd;
    }

    /**
     * @return the dealUnitFee
     */
    public int getDealUnitFee() {
        return dealUnitFee;
    }

    /**
     * @param dealUnitFee the dealUnitFee to set
     */
    public void setDealUnitFee(int dealUnitFee) {
        this.dealUnitFee = dealUnitFee;
    }

    /**
     * @return the dealNum
     */
    public int getDealNum() {
        return dealNum;
    }

    /**
     * @param dealNum the dealNum to set
     */
    public void setDealNum(int dealNum) {
        this.dealNum = dealNum;
    }

    /**
     * @return the dealFee
     */
    public int getDealFee() {
        return dealFee;
    }

    /**
     * @param dealFee the dealFee to set
     */
    public void setDealFee(int dealFee) {
        this.dealFee = dealFee;
    }

    /**
     * @return the orderTypeBuy
     */
    public String getOrderTypeBuy() {
        return orderTypeBuy;
    }

    /**
     * @param orderTypeBuy the orderTypeBuy to set
     */
    public void setOrderTypeBuy(String orderTypeBuy) {
        this.orderTypeBuy = orderTypeBuy;
    }

    /**
     * @return the paidChannelType
     */
    public String getPaidChannelType() {
        return paidChannelType;
    }

    /**
     * @param paidChannelType the paidChannelType to set
     */
    public void setPaidChannelType(String paidChannelType) {
        this.paidChannelType = paidChannelType;
    }

    /**
     * @return the paidChannelCode
     */
    public String getPaidChannelCode() {
        return paidChannelCode;
    }

    /**
     * @param paidChannelCode the paidChannelCode to set
     */
    public void setPaidChannelCode(String paidChannelCode) {
        this.paidChannelCode = paidChannelCode;
    }

    /**
     * @return the mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * @param mobileNo the mobileNo to set
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
