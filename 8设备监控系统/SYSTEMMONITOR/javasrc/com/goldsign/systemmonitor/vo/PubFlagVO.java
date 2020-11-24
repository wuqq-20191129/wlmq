package com.goldsign.systemmonitor.vo;
import java.io.Serializable;

public class PubFlagVO implements Serializable{
  private int type =-1;
  
  private String code = "";
  private String codeText = "";
  
  private String description = "";
  private String  strType = "";
  private boolean isDefaultValue=false;
  
 //�����޶���yangjihe
 //�޶����ڣ�2006-09-12
 //�޶�ԭ����Ҫȡ�ó�Ʊ����
  
  private String  maincode="";			//��������
  private String  subcode="";			//�ӿ�����
  private String  flagmon="";			//�Ƿ�����Ԥ��ֵ
  private String  cardmaxmon="";		//Ԥ��ֵ���
  private String  deposit="";			//��ƱѺ��
  private String  period="";			//��Ч��
  private String  maxrecharge="";			//��Ʊÿ�γ�ֵ����
  
  public PubFlagVO() {
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
  
  //***************************************
  //2006-09-12   yangjihe  ����  
  public void setMaincode(String maincode){
	  this.maincode=maincode;	  
  }
  public String getMaincode(){
	  return this.maincode;	  
  }

  public void setSubcode(String subcode){
	  this.subcode=subcode;	  
  }
  public String getSubcode(){
	  return this.subcode;	  
  }

  public void setCardmaxmon(String cardmaxmon){
	  this.cardmaxmon=cardmaxmon;	  
  }
  public String getCardmaxmon(){
	  return this.cardmaxmon;	  
  }
  public void setFlagmon(String flagmon){
	  this.flagmon=flagmon;	  
  }
  public String getFlagmon(){
	  return this.flagmon;	  
  }
  public void setDeposit(String deposit){
	  this.deposit=deposit;	  
  }
  public String getDeposit(){
	  return this.deposit;	  
  }
  public void setPeriod(String period){
	  this.period=period;	  
  }
  public String getPeriod(){
	  return this.period;	  
  }
  
  public void setMaxrecharge(String maxrecharge){
	  this.maxrecharge=maxrecharge;	  
  }
  
  public String getMaxrecharge(){
	  return this.maxrecharge;	  
  }
}
