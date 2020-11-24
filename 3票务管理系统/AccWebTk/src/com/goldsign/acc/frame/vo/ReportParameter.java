/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.vo;

import java.util.HashMap;

/**
 *
 * @author hejj
 */
public class ReportParameter {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
    private String sourceName = "";
    private String destinationName = "";
    private HashMap parameters = new HashMap();
    private int fileFlag = 0;
    private String templateName = "";
    private String reportContext = "";

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * Creates a new instance of ReportParameter
     */
    public ReportParameter() {
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public HashMap getParameters() {
        return parameters;
    }

    public void setParameters(HashMap parameters) {
        this.parameters = parameters;
    }

    public int getFileFlag() {
        return fileFlag;
    }

    public void setFileFlag(int fileFlag) {
        this.fileFlag = fileFlag;
    }

    public String getReportContext() {
        return reportContext;
    }

    public void setReportContext(String reportContext) {
        this.reportContext = reportContext;
    }

}
