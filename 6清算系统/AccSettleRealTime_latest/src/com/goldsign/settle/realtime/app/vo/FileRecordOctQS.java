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
public class FileRecordOctQS extends FileRecordBase{
    private String settleDataType;
    private int totaDealNum;
    private BigDecimal totaDealFee;

    /**
     * @return the totaDealNum
     */
    public int getTotaDealNum() {
        return totaDealNum;
    }

    /**
     * @param totaDealNum the totaDealNum to set
     */
    public void setTotaDealNum(int totaDealNum) {
        this.totaDealNum = totaDealNum;
    }

    /**
     * @return the totaDealFee
     */
    public BigDecimal getTotaDealFee() {
        return totaDealFee;
    }

    /**
     * @param totaDealFee the totaDealFee to set
     */
    public void setTotaDealFee(BigDecimal totaDealFee) {
        this.totaDealFee = totaDealFee;
    }

    /**
     * @return the settleDataType
     */
    public String getSettleDataType() {
        return settleDataType;
    }

    /**
     * @param settleDataType the settleDataType to set
     */
    public void setSettleDataType(String settleDataType) {
        this.settleDataType = settleDataType;
    }
    
}
