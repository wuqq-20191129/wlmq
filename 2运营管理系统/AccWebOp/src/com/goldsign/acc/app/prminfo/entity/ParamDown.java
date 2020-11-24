/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author liudz
 */
public class ParamDown {
  private String parmTypeName;
  private String verNum;
  private String verType;
  private String parmTypeId;
  private Integer distributeTimes;
  private  String versionList;
  private String selectLccLine;

    public String getParmTypeName() {
        return parmTypeName;
    }

    public void setParmTypeName(String parmTypeName) {
        this.parmTypeName = parmTypeName;
    }

    public String getVerNum() {
        return verNum;
    }

    public void setVerNum(String verNum) {
        this.verNum = verNum;
    }

    public String getVerType() {
        return verType;
    }

    public void setVerType(String verType) {
        this.verType = verType;
    }

    public String getParmTypeId() {
        return parmTypeId;
    }

    public void setParmTypeId(String parmTypeId) {
        this.parmTypeId = parmTypeId;
    }

    public Integer getDistributeTimes() {
        return distributeTimes;
    }

    public void setDistributeTimes(Integer distributeTimes) {
        this.distributeTimes = distributeTimes;
    }

    public String getVersionList() {
        return versionList;
    }

    public void setVersionList(String versionList) {
        this.versionList = versionList;
    }

    public String getSelectLccLine() {
        return selectLccLine;
    }

    public void setSelectLccLine(String selectLccLine) {
        this.selectLccLine = selectLccLine;
    }
  
  
}
