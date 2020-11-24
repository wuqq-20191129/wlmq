/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

/**
 *
 * @author lenovo
 */
public class CardInfoVo {
    
    private byte currSite;	//当前工位
    
    private byte tagSite;	//目标工位
    
    private int cardNo;         //逻辑卡号
    
    private byte state;         //当前状态 0:空闲（没卡时候）  1:成功发出卡片（在线上） 2:卡回收成功 3:误收（没用）  4:越位（没用）   
    
    private byte recyBox;       //回收卡箱（真正票箱序号）

    /**
     * @return the currSite
     */
    public byte getCurrSite() {
        return currSite;
    }

    /**
     * @param currSite the currSite to set
     */
    public void setCurrSite(byte currSite) {
        this.currSite = currSite;
    }

    /**
     * @return the tagSite
     */
    public byte getTagSite() {
        return tagSite;
    }

    /**
     * @param tagSite the tagSite to set
     */
    public void setTagSite(byte tagSite) {
        this.tagSite = tagSite;
    }

    /**
     * @return the cardNo
     */
    public int getCardNo() {
        return cardNo;
    }

    /**
     * @param cardNo the cardNo to set
     */
    public void setCardNo(int cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * @return the state
     */
    public byte getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(byte state) {
        this.state = state;
    }

    /**
     * @return the recyBox
     */
    public byte getRecyBox() {
        return recyBox;
    }

    /**
     * @param recyBox the recyBox to set
     */
    public void setRecyBox(byte recyBox) {
        this.recyBox = recyBox;
    }
    
}
