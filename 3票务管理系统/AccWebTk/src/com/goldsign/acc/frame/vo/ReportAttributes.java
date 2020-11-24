/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.vo;

public class ReportAttributes {

    public ReportAttributes() {
        super();
    }

    private String reportURL = "";
    private String queryDate = "";
    private String listName = "";

    public void setReportURL(String reportURL) {
        if (reportURL != null) {
            this.reportURL = reportURL;
        }
    }

    public String getReportURL() {
        return this.reportURL;
    }

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
