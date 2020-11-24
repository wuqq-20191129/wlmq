/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.vo;
import java.io.Serializable;

/**
 *
 * @author hejj
 */
public class ModuleDistrVo implements Serializable{
 private String moduleID = "";
 private String groupID = "";
 private String queryRight = "0";
 private String addRight = "0";
 private String deleteRight = "0";
 private String modifyRight = "0";
 private String cloneRight = "0";
 private String submitRight = "0";
 private String importRight = "0";
 private String exportRight = "0";
 private String printRight = "0";
 private String downloadRight = "0";
 private String distributeRight = "0";
 private String checkRight = "0";
 private String rptQueryRight = "0";
 private String statisticRight = "0";
 private String refundOkRight = "0";
 private String refundNoRight = "0";
 private String refundModifyRight = "0";
 private String refundCheckRight = "0";
 private String auditRight = "0";

  public void setRefundCheckRight(String refundCheckRight){
      if(refundCheckRight != null)
        this.refundCheckRight = refundCheckRight;
 }
 public String getRefundCheckRight(){
      return this.refundCheckRight;
 }
  public void setRefundOkRight(String refundOkRight){
       if(refundOkRight != null)
         this.refundOkRight = refundOkRight;
  }
  public String getRefundOkRight(){
       return this.refundOkRight;
  }
  public void setRefundNoRight(String refundNoRight){
      if(refundNoRight != null)
        this.refundNoRight = refundNoRight;
 }
 public String getRefundNoRight(){
      return this.refundNoRight;
 }
 public void setRefundModifyRight(String refundModifyRight){
     if(refundModifyRight != null)
       this.refundModifyRight = refundModifyRight;
}
public String getRefundModifyRight(){
     return this.refundModifyRight;
}
public void setCloneRight(String cloneRight){
    if(cloneRight != null)
      this.cloneRight = cloneRight;
}
public String getCloneRight(){
    return this.cloneRight;
}
  public void setSubmitRight(String submitRight){
       if(submitRight != null)
         this.submitRight = submitRight;
  }
  public String getSubmitRight(){
       return this.submitRight;
  }
  public void setImportRight(String importRight){
       if(importRight != null)
         this.importRight = importRight;
  }
  public String getImportRight(){
       return this.importRight;
  }

  public void setExportRight(String exportRight){
       if(exportRight != null)
         this.exportRight = exportRight;
  }
  public String getExportRight(){
       return this.exportRight;
  }
  public void setPrintRight(String printRight){
       if(printRight != null)
         this.printRight = printRight;
  }
  public String getPrintRight(){
       return this.printRight;
  }

  public void setDownloadRight(String downloadRight){
       if(downloadRight != null)
         this.downloadRight = downloadRight;
  }
  public String getDownloadRight(){
       return this.downloadRight;
  }
  public void setDistributeRight(String distributeRight){
       if(distributeRight != null)
         this.distributeRight = distributeRight;
  }
  public String getDistributeRight(){
       return this.distributeRight;
  }
  public void setCheckRight(String checkRight){
       if(checkRight != null)
         this.checkRight = checkRight;
  }
  public String getCheckRight(){
       return this.checkRight;
  }
  public void setRptQueryRight(String rptQueryRight){
       if(rptQueryRight != null)
         this.rptQueryRight = rptQueryRight;
  }
  public String getRptQueryRight(){
       return this.rptQueryRight;
  }
  public void setStatisticRight(String statisticRight){
       if(statisticRight != null)
         this.statisticRight = statisticRight;
  }
  public String getStatisticRight(){
       return this.statisticRight;
  }







  public void setModuleID(String moduleID){
    if(moduleID != null)
      this.moduleID = moduleID;
  }
  public String getModuleID(){
    return this.moduleID;
  }
  public void setGroupID(String groupID){
    if(groupID != null)
      this.groupID = groupID;
  }
  public String getGroupID(){
    return this.groupID;
  }
  public void setQueryRight(String queryRight){
    if(queryRight != null)
      this.queryRight = queryRight;
  }
  public String getQueryRight(){
    return this.queryRight;
  }

  public void setAddRight(String addRight){
      if(addRight != null)
        this.addRight = addRight;
    }
    public String getAddRight(){
      return this.addRight;
    }

    public void setDeleteRight(String deleteRight){
        if(deleteRight != null)
          this.deleteRight = deleteRight;
      }
      public String getDeleteRight(){
        return this.deleteRight;
      }

      public void setModifyRight(String modifyRight){
        if(modifyRight != null)
          this.modifyRight = modifyRight;
      }
      public String getModifyRight(){
        return this.modifyRight;
      }
	public String getAuditRight() {
		return auditRight;
	}
	public void setAuditRight(String auditRight) {
		if(auditRight != null)
			this.auditRight = auditRight;
	}
    
}
