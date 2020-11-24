package com.goldsign.acc.report.util;

import com.goldsign.lib.db.util.DbHelper;
import java.sql.SQLException;

import org.apache.log4j.Logger;




public class PubUtil {
	private static Logger logger = Logger.getLogger(PubUtil.class.
			getName());

	public PubUtil() {
		super();
		// TODO Auto-generated constructor stub
	}
	public static void handleException(Exception e, Logger lg) throws Exception {
		e.printStackTrace();
		lg.error("错误:", e);
		throw e;
	}
	public static void handleExceptionNoThrow(Exception e, Logger lg) {
		e.printStackTrace();
		lg.error("错误:", e);

	}

	public static void handleExceptionForTran(Exception e, Logger lg,
			DbHelper dbHelper) throws Exception {
		if (dbHelper != null)
			dbHelper.rollbackTran();
		e.printStackTrace();
		lg.error("错误:", e);
		throw e;
	}
	public static void finalProcess(DbHelper dbHelper) {
		try {
			if (dbHelper != null)
				dbHelper.closeConnection();
		} catch (SQLException e) {
			logger.error("Fail to close connection", e);
		}
	}

	public static void finalProcessForTran(DbHelper dbHelper) {
		try {
			/*
			if (dbHelper != null && !dbHelper.isConClosed()
					&& !dbHelper.getAutoCommit())
				dbHelper.setAutoCommit(true);
			*/
			if (dbHelper != null)
				dbHelper.closeConnection();

		} catch (SQLException e) {
			logger.error("Fail to close connection", e);
		}
	}

}
