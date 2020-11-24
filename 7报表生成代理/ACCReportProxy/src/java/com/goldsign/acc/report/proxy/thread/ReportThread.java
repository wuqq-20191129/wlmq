/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.report.proxy.thread;
import com.goldsign.acc.report.proxy.ReportProxy;
import com.goldsign.acc.report.proxy.vo.ReportGenerateStatus;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.InterruptedException;

/**
 *
 * @author hejj
 */
public class ReportThread extends Thread{
    private ReportGenerateStatus status = new ReportGenerateStatus();

	private String user = null;

	private String password = null;

	private String server = null;

	private String reportName = null;

	private HttpServletRequest request = null;

	private HttpServletResponse response = null;

	private ServletContext context = null;

	private String fileType = null;

	private String serverType = null;

	ReportProxy helper = null;

	public ReportThread(String user, String password, String server,
			String reportName, HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			String fileType, ReportProxy helper, String serverType) {
		this.user = user;
		this.password = password;
		this.server = server;
		this.reportName = reportName;
		this.request = request;
		this.response = response;
		this.context = context;
		this.fileType = fileType;
		this.helper = helper;
		this.serverType = serverType;

	}

	public void run() {
		// ReportProxy helper = new ReportProxy();
		if (this.serverType.equals(ReportProxy.REPORT_SERVER_RAS_SERVER))
			this.helper.exportReportByRas(this.user, this.password,
					this.server, this.reportName, this.request, this.response,
					this.context, this.fileType);
		else if (this.serverType
				.equals(ReportProxy.REPORT_SERVER_RAS_SERVER_SINGLE_SESSION))
			this.helper.exportReportByRasUsingSingleSession(this.user,
					this.password, this.server, this.reportName, this.request,
					this.response, this.context, this.fileType);
		else
			this.helper.exportReport(this.user, this.password, this.server,
					this.reportName, this.request, this.response, this.context,
					this.fileType);

		synchronized (this.status) {
			this.status.setIsFinish(true);
		}
	}

	public ReportGenerateStatus getReportGenerateStatus() {
		synchronized (this.status) {
			return this.status;
		}
	}
   
    
    
    
}
