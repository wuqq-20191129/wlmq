package com.goldsign.systemmonitor.vo;

public class IqVo {

    private String ip;
    private String status;
    private String statusText;
    private String statusDate;
    private String freeData;
    private String usedDataRate;
    private String backupFulSize;
    private String backupFulStartTime;
    private String backupFulEndTime;
    private String backupFulInterval;
    private String backupIncSize;
    private String backupIncStartTime;
    private String backupIncEndTime;
    private String backupIncInterval;
    private String remark;
    private String name;
    private String type;
    private String jobStatus;
    private String jobWaterNo;
    private String jobStatusText;

    public IqVo() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getBackupFulEndTime() {
        return backupFulEndTime;
    }

    public void setBackupFulEndTime(String backupFulEndTime) {
        this.backupFulEndTime = backupFulEndTime;
    }

    public String getBackupFulInterval() {
        return backupFulInterval;
    }

    public void setBackupFulInterval(String backupFulInterval) {
        this.backupFulInterval = backupFulInterval;
    }

    public String getBackupFulSize() {
        return backupFulSize;
    }

    public void setBackupFulSize(String backupFulSize) {
        this.backupFulSize = backupFulSize;
    }

    public String getBackupFulStartTime() {
        return backupFulStartTime;
    }

    public void setBackupFulStartTime(String backupFulStartTime) {
        this.backupFulStartTime = backupFulStartTime;
    }

    public String getBackupIncEndTime() {
        return backupIncEndTime;
    }

    public void setBackupIncEndTime(String backupIncEndTime) {
        this.backupIncEndTime = backupIncEndTime;
    }

    public String getBackupIncInterval() {
        return backupIncInterval;
    }

    public void setBackupIncInterval(String backupIncInterval) {
        this.backupIncInterval = backupIncInterval;
    }

    public String getBackupIncSize() {
        return backupIncSize;
    }

    public void setBackupIncSize(String backupIncSize) {
        this.backupIncSize = backupIncSize;
    }

    public String getBackupIncStartTime() {
        return backupIncStartTime;
    }

    public void setBackupIncStartTime(String backupIncStartTime) {
        this.backupIncStartTime = backupIncStartTime;
    }

    public String getFreeData() {
        return freeData;
    }

    public void setFreeData(String freeData) {
        this.freeData = freeData;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsedDataRate() {
        return usedDataRate;
    }

    public void setUsedDataRate(String usedDataRate) {
        this.usedDataRate = usedDataRate;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobStatusText() {
        return jobStatusText;
    }

    public void setJobStatusText(String jobStatusText) {
        this.jobStatusText = jobStatusText;
    }

    public String getJobWaterNo() {
        return jobWaterNo;
    }

    public void setJobWaterNo(String jobWaterNo) {
        this.jobWaterNo = jobWaterNo;
    }
}
