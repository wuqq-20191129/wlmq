/*
 * Amendment History:
 *
 * Date          By             Description
 * ----------    -----------    -------------------------------------------
 * 2005-06-08    Rong Weitao    Create the class
 */

package com.goldsign.acc.report.vo;
import java.util.Vector;

public class ReportAttribute{
	
	private String reportCode;
	private String reportName;
	private String reportModule;
	private String reportType;
	private String periodType;
	private String lineId;
	private String cardMainCode;
	private String cardSubCode;
	private String dataTable;
	private String outType;
	private String dsId;
	private String dsUser;
	private String dsPass;
        private String generateDate;
        private String periodDetailType;
        private String beginDate;
        private String endDate;
	
	
	private Vector squadDayV =new Vector();
	
	private int threadNum;
	
	
	
	
	
	public int getThreadNum() {
		return threadNum;
	}
	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}
	public void setSquadDayV(Vector squadDayV) {
		this.squadDayV = squadDayV;
	}
	/**  ?? 2006.11.30
	 * Returns the outType.
	 * @return String
	 */
	public Vector getSquadDayV(){
		return this.squadDayV;
	}
	public void setSquadDayV(String squadDay){
		this.squadDayV.add(squadDay);
	}
	public void clearSquadDayV(){
		this.squadDayV.clear();
	}
	public String getOutType() {
		return outType;
	}
	
	
	/**
	 * Returns the cardMainCode.
	 * @return String
	 */
	public String getCardMainCode() {
		return cardMainCode;
	}
	
	/**
	 * Returns the cardSubCode.
	 * @return String
	 */
	public String getCardSubCode() {
		return cardSubCode;
	}
	
	/**
	 * Returns the lineId.
	 * @return String
	 */
	public String getLineId() {
		return lineId;
	}
	
	/**
	 * Returns the periodType.
	 * @return String
	 */
	public String getPeriodType() {
		return periodType;
	}
	
	/**
	 * Returns the reportCode.
	 * @return String
	 */
	public String getReportCode() {
		return reportCode;
	}
	
	/**
	 * Returns the reportModule.
	 * @return String
	 */
	public String getReportModule() {
		return reportModule;
	}
	
	/**
	 * Returns the reportName.
	 * @return String
	 */
	public String getReportName() {
		return reportName;
	}
	
	/**
	 * Returns the reportType.
	 * @return String
	 */
	public String getReportType() {
		return reportType;
	}
	
	
	/**  ???2006.11.30
	 * Sets the outType.
	 * @param outType The outType to set
	 */
	public void setOutType(String outType) {
		this.outType = outType;
	}
	
	/**
	 * Sets the cardMainCode.
	 * @param cardMainCode The cardMainCode to set
	 */
	public void setCardMainCode(String cardMainCode) {
		this.cardMainCode = cardMainCode;
	}
	
	/**
	 * Sets the cardSubCode.
	 * @param cardSubCode The cardSubCode to set
	 */
	public void setCardSubCode(String cardSubCode) {
		this.cardSubCode = cardSubCode;
	}
	
	/**
	 * Sets the lineId.
	 * @param lineId The lineId to set
	 */
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	
	/**
	 * Sets the periodType.
	 * @param periodType The periodType to set
	 */
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	
	/**
	 * Sets the reportCode.
	 * @param reportCode The reportCode to set
	 */
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	
	/**
	 * Sets the reportModule.
	 * @param reportModule The reportModule to set
	 */
	public void setReportModule(String reportModule) {
		this.reportModule = reportModule;
	}
	
	/**
	 * Sets the reportName.
	 * @param reportName The reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	/**
	 * Sets the reportType.
	 * @param reportType The reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	/**
	 * Returns the dataTable.
	 * @return String
	 */
	public String getDataTable() {
		return dataTable;
	}
	
	/**
	 * Sets the dataTable.
	 * @param dataTable The dataTable to set
	 */
	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}
	public String getDsId() {
		return dsId;
	}
	public void setDsId(String dsId) {
		this.dsId = dsId;
	}
	public String getDsPass() {
		return dsPass;
	}
	public void setDsPass(String dsPass) {
		this.dsPass = dsPass;
	}
	public String getDsUser() {
		return dsUser;
	}
	public void setDsUser(String dsUser) {
		this.dsUser = dsUser;
	}

    /**
     * @return the generateDate
     */
    public String getGenerateDate() {
        return generateDate;
    }

    /**
     * @param generateDate the generateDate to set
     */
    public void setGenerateDate(String generateDate) {
        this.generateDate = generateDate;
    }

    /**
     * @return the periodDetailType
     */
    public String getPeriodDetailType() {
        return periodDetailType;
    }

    /**
     * @param periodDetailType the periodDetailType to set
     */
    public void setPeriodDetailType(String periodDetailType) {
        this.periodDetailType = periodDetailType;
    }

    /**
     * @return the beginDate
     */
    public String getBeginDate() {
        return beginDate;
    }

    /**
     * @param beginDate the beginDate to set
     */
    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
	
}
