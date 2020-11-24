package com.goldsign.acc.report.vo;

public class LogReportTotalVo {
	private String waterNo ; 
	private String balanceWaterNo ;//清算流水号
	private String totalNum ="0";//总共生成报表数量
	private String totalDelayNum ="0";//总共生成滞留报表数量
	private String totalNumRegen ="0";//总共重生成报表数量
	private String totalErrorNum ="0";//总共生成错误数量
	private String totalSize ="0";//总共生成报表大小
	private String genStartTime;//-生成起始时间
	private String genEndTime ;//生成结束时间
	private String useTime ="0";//生成总用时
	private String sysLevel;//日志级别
	private String sysLevelText;//日志级别
	
	private String reportWaterNo;//报表流水号
	
	private String timeStart;
	private String timeEnd;
    public void clear(){
    	  this.waterNo =null ; 
    	  this.balanceWaterNo =null;//清算流水号
    	  this.totalNum ="0";//总共生成报表数量
    	  this.totalDelayNum ="0";//总共生成滞留报表数量
    	  this.totalNumRegen ="0";//总共重生成报表数量
    	  this.totalErrorNum ="0";//总共生成错误数量
    	  this. totalSize ="0";//总共生成报表大小
    	  this.genStartTime=null;//-生成起始时间
    	  this.genEndTime =null;//生成结束时间
    	  this.useTime =null;//生成总用时
    	  this.sysLevel=null;//日志级别
    	  this.sysLevelText=null;//日志级别
    	
    	  this.reportWaterNo=null;//报表流水号
    	
    	  this.timeStart=null;
    	  this.timeEnd=null;
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

	public String getBalanceWaterNo() {
		return balanceWaterNo;
	}

	public void setBalanceWaterNo(String balanceWaterNo) {
		this.balanceWaterNo = balanceWaterNo;
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

	public String getSysLevel() {
		return sysLevel;
	}

	public void setSysLevel(String sysLevel) {
		this.sysLevel = sysLevel;
	}

	public String getTotalDelayNum() {
		return totalDelayNum;
	}

	public void setTotalDelayNum(String totalDelayNum) {
		this.totalDelayNum = totalDelayNum;
	}

	public String getTotalErrorNum() {
		return totalErrorNum;
	}

	public void setTotalErrorNum(String totalErrorNum) {
		this.totalErrorNum = totalErrorNum;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getTotalNumRegen() {
		return totalNumRegen;
	}

	public void setTotalNumRegen(String totalNumRegen) {
		this.totalNumRegen = totalNumRegen;
	}

	public String getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
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

	public LogReportTotalVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSysLevelText() {
		return sysLevelText;
	}

	public void setSysLevelText(String sysLevelText) {
		this.sysLevelText = sysLevelText;
	}

	public String getReportWaterNo() {
		return reportWaterNo;
	}

	public void setReportWaterNo(String reportWaterNo) {
		this.reportWaterNo = reportWaterNo;
	}

}
