/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
public class FileRecord81Detail extends FileRecordBase{
    private String waterNo;
    private int dataCode;
    private int dataValue;
    private String cardMainId;
    private String cardSubId;
    private int entryNum;
    private int exitNum;
    private int consumeNum;
    private int consumeFee;

    /**
     * @return the waterNo
     */
    public String getWaterNo() {
        return waterNo;
    }

    /**
     * @param waterNo the waterNo to set
     */
    public void setWaterNo(String waterNo) {
        this.waterNo = waterNo;
    }

    /**
     * @return the dataCode
     */
    public int getDataCode() {
        return dataCode;
    }

    /**
     * @param dataCode the dataCode to set
     */
    public void setDataCode(int dataCode) {
        this.dataCode = dataCode;
    }

    /**
     * @return the dataValue
     */
    public int getDataValue() {
        return dataValue;
    }

    /**
     * @param dataValue the dataValue to set
     */
    public void setDataValue(int dataValue) {
        this.dataValue = dataValue;
    }

    /**
     * @return the cardMainId
     */
    public String getCardMainId() {
        return cardMainId;
    }

    /**
     * @param cardMainId the cardMainId to set
     */
    public void setCardMainId(String cardMainId) {
        this.cardMainId = cardMainId;
    }

    /**
     * @return the cardSubId
     */
    public String getCardSubId() {
        return cardSubId;
    }

    /**
     * @param cardSubId the cardSubId to set
     */
    public void setCardSubId(String cardSubId) {
        this.cardSubId = cardSubId;
    }

    /**
     * @return the entryNum
     */
    public int getEntryNum() {
        return entryNum;
    }

    /**
     * @param entryNum the entryNum to set
     */
    public void setEntryNum(int entryNum) {
        this.entryNum = entryNum;
    }

    /**
     * @return the exitNum
     */
    public int getExitNum() {
        return exitNum;
    }

    /**
     * @param exitNum the exitNum to set
     */
    public void setExitNum(int exitNum) {
        this.exitNum = exitNum;
    }

    /**
     * @return the consumeNum
     */
    public int getConsumeNum() {
        return consumeNum;
    }

    /**
     * @param consumeNum the consumeNum to set
     */
    public void setConsumeNum(int consumeNum) {
        this.consumeNum = consumeNum;
    }

    /**
     * @return the consumeFee
     */
    public int getConsumeFee() {
        return consumeFee;
    }

    /**
     * @param consumeFee the consumeFee to set
     */
    public void setConsumeFee(int consumeFee) {
        this.consumeFee = consumeFee;
    }

  
    
}
