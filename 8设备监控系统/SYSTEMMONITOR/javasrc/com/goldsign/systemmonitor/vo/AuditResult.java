package com.goldsign.systemmonitor.vo;

public class AuditResult {
  private boolean result =true;
  private String msg ="";
  public AuditResult() {
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
