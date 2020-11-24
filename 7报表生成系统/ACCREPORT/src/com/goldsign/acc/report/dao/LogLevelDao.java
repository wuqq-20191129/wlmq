package com.goldsign.acc.report.dao;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.goldsign.acc.report.constant.AppConstant;
import com.goldsign.acc.report.vo.ReportAttribute;
import com.goldsign.acc.report.vo.SysLogLevelVo;
import com.goldsign.lib.db.util.DbHelper;



public class LogLevelDao {
	private static Logger logger = Logger.getLogger(LogLevelDao.class.
			getName());

	public LogLevelDao() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SysLogLevelVo getLogLevel() {
		boolean result = false;
		DbHelper dbHelper = null;
		SysLogLevelVo vo =null;

		try {
			dbHelper = new DbHelper("", AppConstant.REPORT_DBCPHELPER.getConnection());
			String sqlStr = "select sys_code,sys_level,set_time,operator_id," +
					"version_no,record_flag from " + AppConstant.ST_USER + "w_st_cfg_log_level where " +
					"record_flag=? and sys_code=? ";
			Object[] values = {AppConstant.RECORD_FLAG_CURRENT,AppConstant.SYSTEM_REPORT};
			result = dbHelper.getFirstDocument(sqlStr,values);
			if (result) 
				vo = this.getResultRecord(dbHelper);

			
		}
		catch (Exception e) {
			logger.error("访问w_st_cfg_log_level表错误! " + e);
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
		
		return vo;
	}
	private SysLogLevelVo  getResultRecord(DbHelper dbHelper) throws Exception{
		SysLogLevelVo vo = new SysLogLevelVo();
		vo.setSysCode(dbHelper.getItemValue("sys_code"));
		vo.setSysLevel(dbHelper.getItemValue("sys_level"));
		vo.setSetTime(dbHelper.getItemValue("set_time"));
		vo.setOperator(dbHelper.getItemValue("operator_id"));
		vo.setVersionNo(dbHelper.getItemValue("version_no"));
		vo.setRecordFlag(dbHelper.getItemValue("record_flag"));
		return vo;
		
	}

}
