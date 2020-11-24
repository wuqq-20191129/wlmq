/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.sysconfig.entity;

/**
 *
 * @author mh
 */
public class TaskLog {
    private String taskName;
    private String taskDesc;
    private String startTime;
    private String endTime;
    //modify by zhongzq 20171214
    private int taskResult;
    private String resultDesc;
    //modify by zhongzq 20171214
    private int waterNo;
    
    private String resultName;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(int taskResult) {
        this.taskResult = taskResult;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public int getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(int waterNo) {
        this.waterNo = waterNo;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }
    
    
}
