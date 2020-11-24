package com.goldsign.acc.report.dao;

import java.util.Date;

import org.apache.log4j.Logger;

import com.goldsign.acc.report.constant.AppConstant;
import com.goldsign.acc.report.util.DateHelper;
import com.goldsign.acc.report.util.NumberUtil;
import com.goldsign.lib.db.util.DbHelper;

public class ReportWaterNoDao {
	private static Logger logger = Logger.getLogger(ReportWaterNoDao.class.
			getName());

	public ReportWaterNoDao() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String  getReportWaterNo() throws Exception{
		boolean result = false;
		DbHelper dbHelper = null;
		String reportWaterNo =null;

		try {
			dbHelper = new DbHelper("", AppConstant.REPORT_DBCPHELPER.getConnection());
			String sqlStr = "select report_water_no from " + AppConstant.ST_USER + "w_rp_water_no ";
			
			result = dbHelper.getFirstDocument(sqlStr);
			if (result){ 
				reportWaterNo = dbHelper.getItemValue("report_water_no");
				//判断是否当天的流水号，如是+1生成新的流水号，否则，从01开始
				reportWaterNo  = this.getReportWaterNoFromDb(reportWaterNo);
			}
			else{
				reportWaterNo = this.getReportWaterNoFromDate();//当天的01开始
			}
			this.setReportWaterNo(reportWaterNo);//流水号存放回表中，作为下次的判断依据
			

			
		}
		catch (Exception e) {
			logger.error("访问w_rp_water_no表错误! " + e);
		}
		finally {
			try {
				if (dbHelper != null)
					dbHelper.closeConnection();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return reportWaterNo;
	}
	public int   setReportWaterNo(String reportWaterNo) throws Exception{
		int result = 0;
		DbHelper dbHelper = null;
		Object[] values = {reportWaterNo};
		

		try {
			dbHelper = new DbHelper("", AppConstant.REPORT_DBCPHELPER.getConnection());
			String sqlStr = "delete  from " + AppConstant.ST_USER + "w_rp_water_no ";
			
			result = dbHelper.executeUpdate(sqlStr);
			sqlStr = "insert into " + AppConstant.ST_USER + "w_rp_water_no(report_water_no) values(?) ";
			result = dbHelper.executeUpdate(sqlStr,values);

			
		}
		catch (Exception e) {
			logger.error("访问w_rp_water_no表错误! " + e);
		}
		finally {
			try {
				if (dbHelper != null)
					dbHelper.closeConnection();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	private String getReportWaterNoFromDb(String reportWaterNo){
		String date = DateHelper.datetimeToStringOnlyDateF(new Date());
		String dateDb = reportWaterNo.substring(0,8);
		String no = reportWaterNo.substring(8);
		int n;
		if(date.equals(dateDb)){
			n = new Integer(no).intValue()+1;
			return dateDb+NumberUtil.formatNumber(n,6);
		}
		return this.getReportWaterNoFromDate();
			
		
	}
	private String getReportWaterNoFromDate(){
		String date = DateHelper.datetimeToStringOnlyDateF(new Date());
		return date+"000001";
	}

}
