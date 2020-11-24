/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.entity;

/**
 *
 * @author hejj
 */
public class PubFlag {

    /**
     * @return the code_type
     */
    public String getCode_type() {
        return code_type;
    }

    /**
     * @param code_type the code_type to set
     */
    public void setCode_type(String code_type) {
        this.code_type = code_type;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the code_text
     */
    public String getCode_text() {
        return code_text;
    }

    /**
     * @param code_text the code_text to set
     */
    public void setCode_text(String code_text) {
        this.code_text = code_text;
    }

    public boolean isIsDefaultValue() {
        return isDefaultValue;
    }

    public void setIsDefaultValue(boolean isDefaultValue) {
        this.isDefaultValue = isDefaultValue;
    }
    
    
    private String code;
    private String code_text;
    private String code_type;
    
    private boolean isDefaultValue=false;
    
}
