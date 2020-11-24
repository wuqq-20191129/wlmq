/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samquery.entity;

/**
 * 操作日志
 * @author taidb
 */
public class SamOperLogging {

    //操作员
    private String operatorId;
    
    //操作员名称
    private String operatorName;
    
    //操作时间
    private String opTime;
    
    //操作类型
    private String operType;
    
    //操作结果
    private String description;
    
    //开始操作时间
    private String beginOpTime;
    
    //结束操作时间
    private String endOpTime;
    
    //系统名称
    private String sysType; 
    
    //操作模块名称
    private String moduleName;
    
    //操作模块id
    private String moduleId;
    
    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }


    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeginOpTime() {
        return beginOpTime;
    }

    public void setBeginOpTime(String beginOpTime) {
        this.beginOpTime = beginOpTime;
    }

    public String getEndOpTime() {
        return endOpTime;
    }

    public void setEndOpTime(String endOpTime) {
        this.endOpTime = endOpTime;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

}
