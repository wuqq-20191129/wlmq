/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.csfrm.vo;

/**
 *
 * @author lenovo
 */
public class SBarStatusVo {
    
    private String text;
    
    private String name;
    
    private float percent;
    
    private String onDesc;
    
    private String offDesc;
    
    private boolean status;
    
    public SBarStatusVo(){}
    
    public SBarStatusVo(String text, String name, float percent){
        this.text = text;
        this.name = name;
        this.percent = percent;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the percent
     */
    public float getPercent() {
        return percent;
    }

    /**
     * @param percent the percent to set
     */
    public void setPercent(float percent) {
        this.percent = percent;
    }

    /**
     * @return the onDesc
     */
    public String getOnDesc() {
        return onDesc;
    }

    /**
     * @param onDesc the onDesc to set
     */
    public void setOnDesc(String onDesc) {
        this.onDesc = onDesc;
    }

    /**
     * @return the offDesc
     */
    public String getOffDesc() {
        return offDesc;
    }

    /**
     * @param offDesc the offDesc to set
     */
    public void setOffDesc(String offDesc) {
        this.offDesc = offDesc;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
}
