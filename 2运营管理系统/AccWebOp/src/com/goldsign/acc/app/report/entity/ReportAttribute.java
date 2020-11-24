/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.report.entity;

/**
 *
 * @author mqf
 */
public class ReportAttribute {
    
    private String moduleID = "";
    private String reportName = "";
    private String templateCode = "";
    private String reportURL = "";
    private String balanceDate = "";
    private String operationDate = "";
    private String fileTypeText = "";

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        if (moduleID != null) {
            this.moduleID = moduleID;
        }
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        if (reportName != null) {
            this.reportName = reportName;
        }
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        if (templateCode != null) {
            this.templateCode = templateCode;
        }
    }

    public String getReportURL() {
        return reportURL;
    }

    public void setReportURL(String reportURL) {
        this.reportURL = reportURL;
    }

    public String getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(String balanceDate) {
        if (balanceDate != null) {
            this.balanceDate = balanceDate;
        }
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        if (operationDate != null) {
            this.operationDate = operationDate;
        }
    }

    public String getFileTypeText() {
        if (reportURL != null && reportURL.lastIndexOf(".") != -1) {
            return reportURL.substring( reportURL.lastIndexOf(".")).toUpperCase();
        }
        return null;
    }

    public void setFileTypeText(String fileTypeText) {
        this.fileTypeText = fileTypeText;
    }
    
    
    
}
