/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

/**
 *
 * @author lenovo
 */
public class TicketTypeVo {

    private String cardCode;
    
    private String cardDesc;

    /**
     * @return the cardCode
     */
    public String getCardCode() {
        return cardCode;
    }

    /**
     * @param cardCode the cardCode to set
     */
    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    /**
     * @return the cardDesc
     */
    public String getCardDesc() {
        return cardDesc;
    }

    /**
     * @param cardDesc the cardDesc to set
     */
    public void setCardDesc(String cardDesc) {
        this.cardDesc = cardDesc;
    }

    @Override
    public String toString() {
        return "TicketTypeVo{" + "cardCode=" + cardCode + ", cardDesc=" + cardDesc + '}';
    }
    
}
