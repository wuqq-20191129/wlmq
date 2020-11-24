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
public class FileRecordOctCW extends FileRecordOctTRX{
    private String dealDatetime;
    private String errorCode;
    private String strDealFee;
    private String fileName;
    private String serialNo;
    private String terminalFlag;

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
     * @return the serialNo
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * @param serialNo the serialNo to set
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
    

    /**
     * @return the dealDatetime
     */
    public String getDealDatetime() {
        return dealDatetime;
    }

    /**
     * @param dealDatetime the dealDatetime to set
     */
    public void setDealDatetime(String dealDatetime) {
        this.dealDatetime = dealDatetime;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the strDealFee
     */
    public String getStrDealFee() {
        return strDealFee;
    }

    /**
     * @param strDealFee the strDealFee to set
     */
    public void setStrDealFee(String strDealFee) {
        if(strDealFee !=null)
        this.strDealFee = strDealFee.trim();
    }

    /**
     * @return the terminalFlag
     */
    public String getTerminalFlag() {
        return terminalFlag;
    }

    /**
     * @param terminalFlag the terminalFlag to set
     */
    public void setTerminalFlag(String terminalFlag) {
        this.terminalFlag = terminalFlag;
    }
    
    
}
