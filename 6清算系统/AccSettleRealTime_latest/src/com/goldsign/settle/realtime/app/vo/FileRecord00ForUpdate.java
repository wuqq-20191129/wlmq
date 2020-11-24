/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

/**
 *
 * @author hejj
 */
public class FileRecord00ForUpdate extends FileRecord00DetailBase{
    private String updateReason;
    private String payType;

    /**
     * @return the updateReason
     */
    public String getUpdateReason() {
        return updateReason;
    }

    /**
     * @param updateReason the updateReason to set
     */
    public void setUpdateReason(String updateReason) {
        this.updateReason = updateReason;
    }

    /**
     * @return the payType
     */
    public String getPayType() {
        return payType;
    }

    /**
     * @param payType the payType to set
     */
    public void setPayType(String payType) {
        this.payType = payType;
    }

  
}
