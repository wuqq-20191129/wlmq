package com.goldsign.acc.report.vo;
import java.util.Vector;

public class ReportThreadBuffer {
	private String repoortModule = null;
	private Vector reports = new Vector();
	public ReportThreadBuffer() {
	}
	public void setReportModule(String reportModule){
		this.repoortModule = reportModule;
	}
	public String getReportModule(){
		return this.repoortModule;
	}
	public void setReports(Vector reports){
		this.reports = reports;
	}
	public Vector getReports(){
		return this.reports;
	}
	
}
