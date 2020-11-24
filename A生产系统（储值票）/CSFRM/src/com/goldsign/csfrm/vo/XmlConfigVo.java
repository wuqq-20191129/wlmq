/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.csfrm.vo;

import java.util.Hashtable;

/**
 *
 * @author lenovo
 */
public class XmlConfigVo extends Hashtable {
            
    private XmlTagVo xmlTagVo;
    
    private boolean isIn = false;

    public XmlConfigVo(){
        this.xmlTagVo = new XmlTagVo();
    }
    
    public XmlConfigVo(String tagName) {
        this.xmlTagVo = new XmlTagVo(tagName);
    }
    
    public XmlConfigVo(String tagName, boolean isAttr) {
        this(tagName);
        this.xmlTagVo.setIsAttr(isAttr);
    }
    
    public XmlConfigVo(XmlTagVo xmlTagVo){
        this.xmlTagVo = xmlTagVo;
    }

    /**
     * @return the xmlTagVo
     */
    public XmlTagVo getXmlTagVo() {
        return xmlTagVo;
    }

    /**
     * @param xmlTagVo the xmlTagVo to set
     */
    public void setXmlTagVo(XmlTagVo xmlTagVo) {
        this.xmlTagVo = xmlTagVo;
    }

    /**
     * @return the isIn
     */
    public boolean isIsIn() {
        return isIn;
    }

    /**
     * @param isIn the isIn to set
     */
    public void setIsIn(boolean isIn) {
        this.isIn = isIn;
    }
    
        /**
     * @return the tagName
     */
    public String getTagName() {
        return this.xmlTagVo.getTagName();
    }

    /**
     * @param tagName the tagName to set
     */
    public void setTagName(String tagName) {
        this.xmlTagVo.setTagName(tagName);
    }

    /**
     * @return the isAttr
     */
    public boolean isIsAttr() {
        return this.xmlTagVo.isIsAttr();
    }

    /**
     * @param isAttr the isAttr to set
     */
    public void setIsAttr(boolean isAttr) {
        this.xmlTagVo.setIsAttr(isAttr);
    }
}
