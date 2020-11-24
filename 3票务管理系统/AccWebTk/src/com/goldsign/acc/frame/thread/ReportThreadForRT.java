/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.thread;

import com.goldsign.acc.frame.util.ReportUtil;
import com.goldsign.acc.frame.vo.ReportThreadStatus;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hejj
 */
public class ReportThreadForRT extends Thread {

    private ReportThreadStatus status = new ReportThreadStatus();

    private String user;
    private String password;
    private String server;
    private String reportName;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletContext context;
    private String fileType;
    private String paramName;
    private String dbUser;
    private String dbPass;
    public ReportThreadForRT(String user, String password, String server, String reportName, HttpServletRequest request,
            HttpServletResponse response, ServletContext context, String fileType, String paramName,String dbUser,String dbPass) {
        super();
        this.user = user;
        this.password = password;
        this.server = server;
        this.reportName = reportName;
        this.request = request;
        this.response = response;
        this.context = context;
        this.fileType = fileType;
        this.paramName = paramName;
        this.dbUser = dbUser;
        this.dbPass =dbPass;
        // TODO Auto-generated constructor stub
    }

    public void run() {

        ReportUtil util = new ReportUtil();
        try {
            this.setStatus(false);

            util.exportReport(user, password, server, reportName,
                    request, response, context,
                    fileType, paramName, this.dbUser,this.dbPass);

            this.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            this.setException(e);
            return;

        }
    }

    public ReportThreadStatus getStatus() {
        synchronized (this.status) {
            return status;
        }

    }

    public void setStatus(ReportThreadStatus status) {
        this.status = status;
    }

    public void setStatus(boolean isFinished) {
        synchronized (this.status) {
            this.status.setIsFinished(isFinished);
        }
    }

    public void setException(Exception e) {
        synchronized (this.status) {
            this.status.setRunException(e);
        }
    }

    public boolean isFinished() {
        synchronized (this.status) {
            return this.status.getIsFinished();
        }
    }

    public Exception getException() {
        synchronized (this.status) {
            return this.status.getRunException();
        }
    }

}
