/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.math.BigDecimal;

/**
 *
 * @author hejj
 */
public class FileRecordOctCJ extends FileRecordBase{
    private String fileNameAcc;
    private String fileStatus;
    private int totalNum;
    private int totalFeeFen;
    private BigDecimal totalFeeYuan;
    

    /**
     * @return the fileNameAcc
     */
    public String getFileNameAcc() {
        return fileNameAcc;
    }

    /**
     * @param fileNameAcc the fileNameAcc to set
     */
    public void setFileNameAcc(String fileNameAcc) {
        this.fileNameAcc = fileNameAcc;
    }

    /**
     * @return the fileStatus
     */
    public String getFileStatus() {
        return fileStatus;
    }

    /**
     * @param fileStatus the fileStatus to set
     */
    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
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
     * @return the totalFeeFen
     */
    public int getTotalFeeFen() {
        return totalFeeFen;
    }

    /**
     * @param totalFeeFen the totalFeeFen to set
     */
    public void setTotalFeeFen(int totalFeeFen) {
        this.totalFeeFen = totalFeeFen;
    }

    /**
     * @return the totalFeeYuan
     */
    public BigDecimal getTotalFeeYuan() {
        return totalFeeYuan;
    }

    /**
     * @param totalFeeYuan the totalFeeYuan to set
     */
    public void setTotalFeeYuan(BigDecimal totalFeeYuan) {
        this.totalFeeYuan = totalFeeYuan;
    }
    
}
