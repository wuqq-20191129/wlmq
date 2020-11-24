/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.message;

import com.goldsign.settle.realtime.frame.vo.BcpAttribute;

/**
 *
 * @author hejj
 */
public class MessageBcp extends MessageBase{
    private BcpAttribute attr;
    
    
    public MessageBcp(String bcpFileName,String tradType,String balanceWaterNo,BcpAttribute attr){
        this.bcpFileName =bcpFileName;
        this.tradType=tradType;
        this.setBalanceWaterNo(balanceWaterNo);
        this.attr =attr;
    }

    /**
     * @return the attr
     */
    public BcpAttribute getAttr() {
        return attr;
    }

    /**
     * @param attr the attr to set
     */
    public void setAttr(BcpAttribute attr) {
        this.attr = attr;
    }

   
    
}
