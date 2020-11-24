/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.fm.vo;

/**
 *
 * @author Administrator
 */
public class PubFlagVO {
     private int type =-1;
  private String code = "";
  private String codeText = "";
  private String description = "";
  private String  strType = "";
  private boolean isDefaultValue=false;

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
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
     * @return the codeText
     */
    public String getCodeText() {
        return codeText;
    }

    /**
     * @param codeText the codeText to set
     */
    public void setCodeText(String codeText) {
        this.codeText = codeText;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the strType
     */
    public String getStrType() {
        return strType;
    }

    /**
     * @param strType the strType to set
     */
    public void setStrType(String strType) {
        this.strType = strType;
    }

    /**
     * @return the isDefaultValue
     */
    public boolean isIsDefaultValue() {
        return isDefaultValue;
    }

    /**
     * @param isDefaultValue the isDefaultValue to set
     */
    public void setIsDefaultValue(boolean isDefaultValue) {
        this.isDefaultValue = isDefaultValue;
    }

}
