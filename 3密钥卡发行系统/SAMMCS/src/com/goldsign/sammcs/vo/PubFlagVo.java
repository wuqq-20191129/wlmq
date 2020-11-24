/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.sammcs.vo;

import java.io.Serializable;

/**
 *
 * @author hejj
 */
public class PubFlagVo implements Serializable{
    private int type =-1;
  
  private String code = "";
  private String codeText = "";
  
  private String description = "";
  private String  strType = "";
  private boolean isDefaultValue=false;
  

  
  public PubFlagVo() {
  }
  public void setCode(String code){
    if(code != null)
      this.code = code;
  }
  public String getCode(){
    return this.code ;
  }
  public boolean getIsDefaultValue(){
    return this.isDefaultValue;
  }
  public void setIsDefaultValue(boolean isDefaultValue){
    this.isDefaultValue = isDefaultValue;
  }

  public void setStrType(String strType){
    if(strType != null)
      this.strType = strType;
  }
  public String getStrType(){
    return this.strType ;
  }

  public void setCodeText(String codeText){
    if(codeText != null)
      this.codeText = codeText;
  }
  public String getCodeText(){
    return this.codeText ;
  }
  public void setDescription(String description){
    if(description != null)
      this.description = description;
  }
  public String getdescription(){
    return this.description ;
  }
  
  public void setType(int type){
    this.type = type;
  }
  public int gettype(){
    return this.type ;
  }
  

    
}
