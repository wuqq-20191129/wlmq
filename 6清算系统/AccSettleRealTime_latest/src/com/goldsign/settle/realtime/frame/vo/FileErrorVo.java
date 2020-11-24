/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;

/**
 *
 * @author hejj
 */
public class FileErrorVo {

    private String fileName;
    private String errorCode;
    private String balanceWaterNo;
    private String hdlFlag;
    private String genTime;
    private String remark;
    private String errData;
     public FileErrorVo(String balanceWaterNo, String fileName,
            String errorCode, String remark) {
        this.setBalanceWaterNo(balanceWaterNo);
        this.setFileName(fileName);
        this.setErrorCode(errorCode);
        this.setRemark(remark);


    }
    public FileErrorVo(String balanceWaterNo, String fileName,
            String errorCode, String remark,String errData) {
        this.setBalanceWaterNo(balanceWaterNo);
        this.setFileName(fileName);
        this.setErrorCode(errorCode);
        this.setRemark(remark);
        this.setErrData(errData);

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
     * @return the errorCode
     */
    public String getErrorCode() {
        if (this.errorCode == null) {
            return FrameFileHandledConstant.FILE_ERR_FILE_UNKOWN[0];
        }

        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
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
     * @return the hdlFlag
     */
    public String getHdlFlag() {
        return hdlFlag;
    }

    /**
     * @param hdlFlag the hdlFlag to set
     */
    public void setHdlFlag(String hdlFlag) {
        this.hdlFlag = hdlFlag;
    }

    /**
     * @return the genTime
     */
    public String getGenTime() {
        return genTime;
    }

    /**
     * @param genTime the genTime to set
     */
    public void setGenTime(String genTime) {
        this.genTime = genTime;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getErrData() {
        return errData;
    }

    public void setErrData(String errData) {
        this.errData = errData;
    }
}
