/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

/**
 *
 * @author hejj
 */
public class CheckCardSubTypeVo {
    private String cardMainId;//票卡主类型（5）
    private String cardSubId;//票卡子类型（5）

    /**
     * @return the cardMainId
     */
    public String getCardMainId() {
        return cardMainId;
    }

    /**
     * @param cardMainId the cardMainId to set
     */
    public void setCardMainId(String cardMainId) {
        this.cardMainId = cardMainId;
    }

    /**
     * @return the cardSubId
     */
    public String getCardSubId() {
        return cardSubId;
    }

    /**
     * @param cardSubId the cardSubId to set
     */
    public void setCardSubId(String cardSubId) {
        this.cardSubId = cardSubId;
    }
    
}
