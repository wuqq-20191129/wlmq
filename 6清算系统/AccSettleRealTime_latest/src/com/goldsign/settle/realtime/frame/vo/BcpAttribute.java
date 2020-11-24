/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

/**
 *
 * @author hejj
 */
public class BcpAttribute {
    private boolean addSubTradeType =false;
    private String subTradeType;

    /**
     * @return the addSubTradeType
     */
    public boolean isAddSubTradeType() {
        return addSubTradeType;
    }

    /**
     * @param addSubTradeType the addSubTradeType to set
     */
    public void setAddSubTradeType(boolean addSubTradeType) {
        this.addSubTradeType = addSubTradeType;
    }

    /**
     * @return the subTradeType
     */
    public String getSubTradeType() {
        return subTradeType;
    }

    /**
     * @param subTradeType the subTradeType to set
     */
    public void setSubTradeType(String subTradeType) {
        this.subTradeType = subTradeType;
    }
    
}
