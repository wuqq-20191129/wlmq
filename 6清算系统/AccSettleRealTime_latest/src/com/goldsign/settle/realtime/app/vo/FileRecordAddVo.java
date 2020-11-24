/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

/**
 *
 * @author hejj
 */
public class FileRecordAddVo {

    /**
     * @return the balanceWaterNoSub
     */
    public int getBalanceWaterNoSub() {
        return balanceWaterNoSub;
    }

    /**
     * @param balanceWaterNoSub the balanceWaterNoSub to set
     */
    public void setBalanceWaterNoSub(int balanceWaterNoSub) {
        this.balanceWaterNoSub = balanceWaterNoSub;
    }
    private String balanceWaterNo;
    private int balanceWaterNoSub;
    private String fileName;
    private String trdType;

    public FileRecordAddVo(String balanceWaterNo,int balanceWaterNoSub, String fileName) {
        this.balanceWaterNo = balanceWaterNo;
        this.fileName = fileName;
        this.balanceWaterNoSub=balanceWaterNoSub;
    }

    public FileRecordAddVo(String balanceWaterNo, String fileName, String trdType) {
        this.balanceWaterNo = balanceWaterNo;
        this.fileName = fileName;
        this.trdType = trdType;
    }

    /**
     * @return the balanceWaterNo
     */
    public String getBalanceWaterNo() {
        return balanceWaterNo;
    }

    /**
     * @param balanceWaterNo the balanceWaterNo to set
     */
    public void setBalanceWaterNo(String balanceWaterNo) {
        this.balanceWaterNo = balanceWaterNo;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the trdType
     */
    public String getTrdType() {
        return trdType;
    }

    /**
     * @param trdType the trdType to set
     */
    public void setTrdType(String trdType) {
        this.trdType = trdType;
    }

}
