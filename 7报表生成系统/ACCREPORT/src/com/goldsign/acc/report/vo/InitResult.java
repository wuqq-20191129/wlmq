/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.report.vo;

import java.util.Vector;

/**
 *
 * @author hejj
 */
public class InitResult {
    private boolean readyGenerateReport =false;
    private String balanceWaterNo;
    private String balanceWaterNoMonth;
    private String balanceWaterNoYear;
    private Vector BalanceWaters=new Vector();
    private boolean genMonthReport;
    private boolean genYearReport;
    

    /**
     * @return the readyGenerateReport
     */
    public boolean isReadyGenerateReport() {
        return readyGenerateReport;
    }

    /**
     * @param readyGenerateReport the readyGenerateReport to set
     */
    public void setReadyGenerateReport(boolean readyGenerateReport) {
        this.readyGenerateReport = readyGenerateReport;
    }

    /**
     * @return the balanceWaterNo
     */
    public String getBalanceWaterNo() {
        return balanceWaterNo;
    }

    /**
     * @param balanceWaterNo the balanceWaterNo to set
     */
    public void setBalanceWaterNo(String balanceWaterNo) {
        this.balanceWaterNo = balanceWaterNo;
    }

    /**
     * @return the BalanceWaters
     */
    public Vector getBalanceWaters() {
        return BalanceWaters;
    }

    /**
     * @param BalanceWaters the BalanceWaters to set
     */
    public void setBalanceWaters(Vector BalanceWaters) {
        this.BalanceWaters = BalanceWaters;
    }

    /**
     * @return the genMonthReport
     */
    public boolean isGenMonthReport() {
        return genMonthReport;
    }

    /**
     * @param genMonthReport the genMonthReport to set
     */
    public void setGenMonthReport(boolean genMonthReport) {
        this.genMonthReport = genMonthReport;
    }

    /**
     * @return the genYearReport
     */
    public boolean isGenYearReport() {
        return genYearReport;
    }

    /**
     * @param genYearReport the genYearReport to set
     */
    public void setGenYearReport(boolean genYearReport) {
        this.genYearReport = genYearReport;
    }

    /**
     * @return the balanceWaterNoMonth
     */
    public String getBalanceWaterNoMonth() {
        return balanceWaterNoMonth;
    }

    /**
     * @param balanceWaterNoMonth the balanceWaterNoMonth to set
     */
    public void setBalanceWaterNoMonth(String balanceWaterNoMonth) {
        this.balanceWaterNoMonth = balanceWaterNoMonth;
    }

    /**
     * @return the balanceWaterNoYear
     */
    public String getBalanceWaterNoYear() {
        return balanceWaterNoYear;
    }

    /**
     * @param balanceWaterNoYear the balanceWaterNoYear to set
     */
    public void setBalanceWaterNoYear(String balanceWaterNoYear) {
        this.balanceWaterNoYear = balanceWaterNoYear;
    }
    
    
}
