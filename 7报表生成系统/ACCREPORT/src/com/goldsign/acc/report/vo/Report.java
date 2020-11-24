/*
 * Amendment History:
 * 
 * Date          By             Description
 * ----------    -----------    -------------------------------------------
 * 2005-06-08    Rong Weitao    Create the class
 */

package com.goldsign.acc.report.vo;

import java.util.Vector;

/**
 * Parameter for the report
 */
 
public class Report{
	public static String CenterBalanceWater = "";

	public static Vector BalanceWaters = new Vector();
	
	public static boolean genMonthReport = false;
	
	public static String monthReportBalanceWater = "";
	
	public static boolean genYearReport = false;
	
	public static String yearReportBalanceWater = "";
}
