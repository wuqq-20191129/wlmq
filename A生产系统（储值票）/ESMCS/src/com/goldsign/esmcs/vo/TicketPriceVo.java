/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

/**
 *
 * @author lenovo
 */
public class TicketPriceVo {

    private String cardCode;
    
    private String cardPrice;

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
     * @return the cardPrice
     */
    public String getCardPrice() {
        return cardPrice;
    }

    /**
     * @param cardPrice the cardPrice to set
     */
    public void setCardPrice(String cardPrice) {
        this.cardPrice = cardPrice;
    }

    @Override
    public String toString() {
        return "TicketPriceVo{" + "cardCode=" + cardCode + ", cardPrice=" + cardPrice + '}';
    }

}
