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
public class FileRecordOctDS extends FileRecordBase{
    private String fileNameAudit;
    private int recordNum;
    private BigDecimal recordFee;

    /**
     * @return the fileNameAudit
     */
    public String getFileNameAudit() {
        return fileNameAudit;
    }

    /**
     * @param fileNameAudit the fileNameAudit to set
     */
    public void setFileNameAudit(String fileNameAudit) {
        this.fileNameAudit = fileNameAudit;
    }

    /**
     * @return the recordNum
     */
    public int getRecordNum() {
        return recordNum;
    }

    /**
     * @param recordNum the recordNum to set
     */
    public void setRecordNum(int recordNum) {
        this.recordNum = recordNum;
    }

    /**
     * @return the recordFee
     */
    public BigDecimal getRecordFee() {
        return recordFee;
    }

    /**
     * @param recordFee the recordFee to set
     */
    public void setRecordFee(BigDecimal recordFee) {
        this.recordFee = recordFee;
    }
    
}
