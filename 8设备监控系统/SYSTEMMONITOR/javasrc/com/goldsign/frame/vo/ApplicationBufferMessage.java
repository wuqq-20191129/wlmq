package com.goldsign.frame.vo;
import java.io.Serializable;


public class ApplicationBufferMessage implements Serializable {

  private int messageType = -1;
  private int bufferCode = -1;
  private String itemKey ="";

  public ApplicationBufferMessage() {
  }
  public void setMessageType(int messageType){
    this.messageType = messageType;
  }
  public int getMessageType(){
    return this.messageType;
  }
  public void setBufferCode(int bufferCode){
    this.bufferCode = bufferCode;
  }
  public int getBufferCode(){
    return this.bufferCode;
  }
  public String getItemKey(){
    return this.itemKey;
  }
  public void setItemKey(String itemKey){
    if(itemKey != null && itemKey.length()!=0)
      this.itemKey = itemKey;
  }
}
