/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.entity;

import java.io.Serializable;

/**
 * 报表属性配置
 *
 * @author luck
 */
public class ReportCfgAttr implements Serializable {

    private String reportCode;  //报表代码

    private String reportName;

    private String reportModule;  //报表模板号

    private String reportType;  //1 代表生成报表 按运营日等于清算日 2 代表运营日从中间表取
    private String reportTypeName;

    private String periodType;  //1日报，2月报，3年报
    private String periodTypeName;

    private String lineId;
    private String lineName;

    private String stationId;
    private String stationName;

    private String cardMainId;
    private String cardMainName;
    
    private String cardSubId;
    private String cardSubName;

    private String dataTable;

    private String reportLock;  //是否锁定
    private String reportLockName;
 
    private String outType;  //输出报表文件类型

    private String dsId;
    private String dsName;

    private String generateDate;

    private String periodDetailType;

    private String remark;

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportModule() {
        return reportModule;
    }

    public void setReportModule(String reportModule) {
        this.reportModule = reportModule;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getCardMainId() {
        return cardMainId;
    }

    public void setCardMainId(String cardMainId) {
        this.cardMainId = cardMainId;
    }

    public String getCardMainName() {
        return cardMainName;
    }

    public void setCardMainName(String cardMainName) {
        this.cardMainName = cardMainName;
    }

    public String getCardSubId() {
        return cardSubId;
    }

    public void setCardSubId(String cardSubId) {
        this.cardSubId = cardSubId;
    }

    public String getCardSubName() {
        return cardSubName;
    }

    public void setCardSubName(String cardSubName) {
        this.cardSubName = cardSubName;
    }

    public String getDataTable() {
        return dataTable;
    }

    public void setDataTable(String dataTable) {
        this.dataTable = dataTable;
    }

    public String getReportLock() {
        return reportLock;
    }

    public void setReportLock(String reportLock) {
        this.reportLock = reportLock;
    }

    public String getReportLockName() {
        return reportLockName;
    }

    public void setReportLockName(String reportLockName) {
        this.reportLockName = reportLockName;
    }

    public String getOutType() {
        return outType;
    }

    public void setOutType(String outType) {
        this.outType = outType;
    }

    public String getDsId() {
        return dsId;
    }

    public void setDsId(String dsId) {
        this.dsId = dsId;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(String generateDate) {
        this.generateDate = generateDate;
    }

    public String getPeriodDetailType() {
        return periodDetailType;
    }

    public void setPeriodDetailType(String periodDetailType) {
        this.periodDetailType = periodDetailType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReportTypeName() {
        return reportTypeName;
    }

    public void setReportTypeName(String reportTypeName) {
        this.reportTypeName = reportTypeName;
    }

    public String getPeriodTypeName() {
        return periodTypeName;
    }

    public void setPeriodTypeName(String periodTypeName) {
        this.periodTypeName = periodTypeName;
    }

    

}
