package com.goldsign.acc.app.querysys.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RpLogDetail implements Serializable {
    private Long waterNo;

    private String reportCode;

    private String reportName;

    private String reportModule;
    
    private String reportModuleName;

    public String getReportModuleName() {
        return reportModuleName;
    }

    public void setReportModuleName(String reportModuleName) {
        this.reportModuleName = reportModuleName;
    }

    private BigDecimal reportSize;

    private String isDelay;

    private Date genStartTime;
    
    private String strGenStartTime;
    
    private String strGenEndTime;

    public String getStrGenStartTime() {
        return strGenStartTime;
    }

    public void setStrGenStartTime(String strGenStartTime) {
        this.strGenStartTime = strGenStartTime;
    }

    public String getStrGenEndTime() {
        return strGenEndTime;
    }

    public void setStrGenEndTime(String strGenEndTime) {
        this.strGenEndTime = strGenEndTime;
    }
   

    private Date genEndTime;

    private BigDecimal useTime;

    private String genThread;

    private BigDecimal genCount;

    private String balanceWaterNo;

    private String sysLevel;

    private String reportWaterNo;

    private static final long serialVersionUID = 1L;

    public RpLogDetail(Long waterNo, String reportCode, String reportName, String reportModule, BigDecimal reportSize, String isDelay, Date genStartTime, Date genEndTime, BigDecimal useTime, String genThread, BigDecimal genCount, String balanceWaterNo, String sysLevel, String reportWaterNo) {
        this.waterNo = waterNo;
        this.reportCode = reportCode;
        this.reportName = reportName;
        this.reportModule = reportModule;
        this.reportSize = reportSize;
        this.isDelay = isDelay;
        this.genStartTime = genStartTime;
        this.genEndTime = genEndTime;
        this.useTime = useTime;
        this.genThread = genThread;
        this.genCount = genCount;
        this.balanceWaterNo = balanceWaterNo;
        this.sysLevel = sysLevel;
        this.reportWaterNo = reportWaterNo;
    }

    public RpLogDetail() {
        super();
    }

    public Long getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(Long waterNo) {
        this.waterNo = waterNo;
    }

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode == null ? null : reportCode.trim();
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName == null ? null : reportName.trim();
    }

    public String getReportModule() {
        return reportModule;
    }

    public void setReportModule(String reportModule) {
        this.reportModule = reportModule == null ? null : reportModule.trim();
    }

    public BigDecimal getReportSize() {
        return reportSize;
    }

    public void setReportSize(BigDecimal reportSize) {
        this.reportSize = reportSize;
    }

    public String getIsDelay() {
        return isDelay;
    }

    public void setIsDelay(String isDelay) {
        this.isDelay = isDelay == null ? null : isDelay.trim();
    }

    public Date getGenStartTime() {
        return genStartTime;
    }

    public void setGenStartTime(Date genStartTime) {
        this.genStartTime = genStartTime;
    }

    public Date getGenEndTime() {
        return genEndTime;
    }

    public void setGenEndTime(Date genEndTime) {
        this.genEndTime = genEndTime;
    }

    public BigDecimal getUseTime() {
        return useTime;
    }

    public void setUseTime(BigDecimal useTime) {
        this.useTime = useTime;
    }

    public String getGenThread() {
        return genThread;
    }

    public void setGenThread(String genThread) {
        this.genThread = genThread == null ? null : genThread.trim();
    }

    public BigDecimal getGenCount() {
        return genCount;
    }

    public void setGenCount(BigDecimal genCount) {
        this.genCount = genCount;
    }

    public String getBalanceWaterNo() {
        return balanceWaterNo;
    }

    public void setBalanceWaterNo(String balanceWaterNo) {
        this.balanceWaterNo = balanceWaterNo == null ? null : balanceWaterNo.trim();
    }

    public String getSysLevel() {
        return sysLevel;
    }

    public void setSysLevel(String sysLevel) {
        this.sysLevel = sysLevel == null ? null : sysLevel.trim();
    }

    public String getReportWaterNo() {
        return reportWaterNo;
    }

    public void setReportWaterNo(String reportWaterNo) {
        this.reportWaterNo = reportWaterNo == null ? null : reportWaterNo.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        RpLogDetail other = (RpLogDetail) that;
        return (this.getWaterNo() == null ? other.getWaterNo() == null : this.getWaterNo().equals(other.getWaterNo()))
            && (this.getReportCode() == null ? other.getReportCode() == null : this.getReportCode().equals(other.getReportCode()))
            && (this.getReportName() == null ? other.getReportName() == null : this.getReportName().equals(other.getReportName()))
            && (this.getReportModule() == null ? other.getReportModule() == null : this.getReportModule().equals(other.getReportModule()))
            && (this.getReportSize() == null ? other.getReportSize() == null : this.getReportSize().equals(other.getReportSize()))
            && (this.getIsDelay() == null ? other.getIsDelay() == null : this.getIsDelay().equals(other.getIsDelay()))
            && (this.getGenStartTime() == null ? other.getGenStartTime() == null : this.getGenStartTime().equals(other.getGenStartTime()))
            && (this.getGenEndTime() == null ? other.getGenEndTime() == null : this.getGenEndTime().equals(other.getGenEndTime()))
            && (this.getUseTime() == null ? other.getUseTime() == null : this.getUseTime().equals(other.getUseTime()))
            && (this.getGenThread() == null ? other.getGenThread() == null : this.getGenThread().equals(other.getGenThread()))
            && (this.getGenCount() == null ? other.getGenCount() == null : this.getGenCount().equals(other.getGenCount()))
            && (this.getBalanceWaterNo() == null ? other.getBalanceWaterNo() == null : this.getBalanceWaterNo().equals(other.getBalanceWaterNo()))
            && (this.getSysLevel() == null ? other.getSysLevel() == null : this.getSysLevel().equals(other.getSysLevel()))
            && (this.getReportWaterNo() == null ? other.getReportWaterNo() == null : this.getReportWaterNo().equals(other.getReportWaterNo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWaterNo() == null) ? 0 : getWaterNo().hashCode());
        result = prime * result + ((getReportCode() == null) ? 0 : getReportCode().hashCode());
        result = prime * result + ((getReportName() == null) ? 0 : getReportName().hashCode());
        result = prime * result + ((getReportModule() == null) ? 0 : getReportModule().hashCode());
        result = prime * result + ((getReportSize() == null) ? 0 : getReportSize().hashCode());
        result = prime * result + ((getIsDelay() == null) ? 0 : getIsDelay().hashCode());
        result = prime * result + ((getGenStartTime() == null) ? 0 : getGenStartTime().hashCode());
        result = prime * result + ((getGenEndTime() == null) ? 0 : getGenEndTime().hashCode());
        result = prime * result + ((getUseTime() == null) ? 0 : getUseTime().hashCode());
        result = prime * result + ((getGenThread() == null) ? 0 : getGenThread().hashCode());
        result = prime * result + ((getGenCount() == null) ? 0 : getGenCount().hashCode());
        result = prime * result + ((getBalanceWaterNo() == null) ? 0 : getBalanceWaterNo().hashCode());
        result = prime * result + ((getSysLevel() == null) ? 0 : getSysLevel().hashCode());
        result = prime * result + ((getReportWaterNo() == null) ? 0 : getReportWaterNo().hashCode());
        return result;
    }
}