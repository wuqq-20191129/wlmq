/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.vo;

/**
 *
 * @author hejj
 */
public class PriviledgeOperatorVo {
    private String operatorID = "";
  private String password = "";
  private String oldPassword = "";
  private String name = "";
  private String employeeID = "";
  private String expiredDate = "";
  private String status = "";
  private String groupID = "";
  private String statusText = "";
  private String groupIDText = "";





  public PriviledgeOperatorVo() {
  }
  public void setOperatorID(String operatorID){
    if(operatorID !=null)
      this.operatorID = operatorID;
  }
  public String getOperatorID(){
    return this.operatorID;
  }
  public void setPassword(String password){
    if(password !=null)
      this.password = password;
  }
  public String getStatusText(){
    return this.statusText;
  }
  public void setStatusText(String statusText){
    if(statusText !=null)
      this.statusText = statusText;
  }
  public String getGroupIDText(){
    return this.groupIDText;
  }
  public void setGroupIDText(String groupIDText){
    if(groupIDText !=null)
      this.groupIDText = groupIDText;
  }


  public String getPassword(){
    return this.password;
  }
  public void setOldPassword(String oldPassword){
    if(oldPassword !=null)
      this.oldPassword = oldPassword;
  }
  public String getOldPassword(){
    return this.oldPassword;
  }


  public void setName(String name){
    if(name !=null)
      this.name = name;
  }
  public String getName(){
      return this.name;
    }

  public void setEmployeeID(String employeeID){
    if(employeeID !=null)
      this.employeeID = employeeID;
  }
  public String getEmployeeID(){
        return this.employeeID;
      }

  public void setExpiredDate(String expiredDate){
    if(expiredDate !=null)
      this.expiredDate = expiredDate;
  }
  public String getExpiredDate(){
    return this.expiredDate;
  }

  public void setStatus(String status){
    if(status !=null)
      this.status = status;
  }

  public String getStatus(){
    return this.status;
  }


  public String getGroupID(){
            return this.groupID;
          }

  public void setGroupID(String groupID){
    if(groupID !=null)
      this.groupID = groupID;
  }
    
}
