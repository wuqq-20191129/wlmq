/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.csfrm.vo;

/**
 *
 * @author lenovo
 */
public class XmlTagVo {
    
    private String tagName;
    
    private boolean isAttr=false;

    public XmlTagVo(){}
    
    public XmlTagVo(String tagName) {
        this.tagName = tagName;
    }
    
    public XmlTagVo(String tagName, boolean isAttr) {
        this(tagName);
        this.isAttr = isAttr;
    }
    
    /**
     * @return the tagName
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * @param tagName the tagName to set
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * @return the isAttr
     */
    public boolean isIsAttr() {
        return isAttr;
    }

    /**
     * @param isAttr the isAttr to set
     */
    public void setIsAttr(boolean isAttr) {
        this.isAttr = isAttr;
    }
    
}
