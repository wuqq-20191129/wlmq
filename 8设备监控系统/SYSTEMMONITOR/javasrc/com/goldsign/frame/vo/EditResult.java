/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.vo;

/**
 *
 * @author hejj
 */
public class EditResult {
    private boolean result =true;
  private String msg ="";
  public EditResult() {
  }
  public boolean getResult(){
    return this.result;
  }
  public void setResult(boolean result){
    this.result = result;
  }
  public void setMsg(String msg){
    this.msg = msg;
  }
  public String getMsg(){
    return this.msg;
  }
    
}
