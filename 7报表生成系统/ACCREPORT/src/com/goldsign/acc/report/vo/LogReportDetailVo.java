package com.goldsign.acc.report.vo;

public class LogReportDetailVo {
	private String waterNo ;//流水号
	private String reportCode ;//报表代码
	private String reportName ;//报表名称
	private String reportModule;//报表模板代码
	private String reportSize;//报表大小
	private String isDelay;//是否滞留报表
	private String isDelayText;//是否滞留报表
	private String genStartTime;//生成起始时间
	private String genEndTime ;//生成结束时间
	private String useTime ;//生成用时
	private String genThread ;//生成的线程号
	private String genCount;//生成次数
	private String balanceWaterNo;//清算流水号
	private String sysLevel;//日志级别
	
	private String sysLevelText;//日志级别
	
	private String timeStart;//查询起始时间
	private String timeEnd;//查询结束时间

	public String getBalanceWaterNo() {
		return balanceWaterNo;
	}

	public void setBalanceWaterNo(String balanceWaterNo) {
		this.balanceWaterNo = balanceWaterNo;
	}

	public String getGenCount() {
		return genCount;
	}

	public void setGenCount(String genCount) {
		this.genCount = genCount;
	}

	public String getGenEndTime() {
		return genEndTime;
	}

	public void setGenEndTime(String genEndTime) {
		this.genEndTime = genEndTime;
	}

	public String getGenStartTime() {
		return genStartTime;
	}

	public void setGenStartTime(String genStartTime) {
		this.genStartTime = genStartTime;
	}

	public String getGenThread() {
		return genThread;
	}

	public void setGenThread(String genThread) {
		this.genThread = genThread;
	}

	public String getIsDelay() {
		return isDelay;
	}

	public void setIsDelay(String isDelay) {
		this.isDelay = isDelay;
	}

	public String getReportCode() {
		return reportCode;
	}

	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}

	public String getReportModule() {
		return reportModule;
	}

	public void setReportModule(String reportModule) {
		this.reportModule = reportModule;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportSize() {
		return reportSize;
	}

	public void setReportSize(String reportSize) {
		this.reportSize = reportSize;
	}

	public String getSysLevel() {
		return sysLevel;
	}

	public void setSysLevel(String sysLevel) {
		this.sysLevel = sysLevel;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	public String getWaterNo() {
		return waterNo;
	}

	public void setWaterNo(String waterNo) {
		this.waterNo = waterNo;
	}

	public LogReportDetailVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getSysLevelText() {
		return sysLevelText;
	}

	public void setSysLevelText(String sysLevelText) {
		this.sysLevelText = sysLevelText;
	}

	public String getIsDelayText() {
		return isDelayText;
	}

	public void setIsDelayText(String isDelayText) {
		this.isDelayText = isDelayText;
	}

}
