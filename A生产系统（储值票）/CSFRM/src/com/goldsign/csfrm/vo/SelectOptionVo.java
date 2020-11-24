/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.csfrm.vo;

/**
 *
 * @author lenovo
 */
public class SelectOptionVo {

    private String text;
    
    private String value;
    
    public SelectOptionVo(){}
    
    public SelectOptionVo(String value, String text){
        this.value = value;
        this.text = text;
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
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
