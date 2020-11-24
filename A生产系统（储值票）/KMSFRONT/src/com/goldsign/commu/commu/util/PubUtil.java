package com.goldsign.commu.commu.util;

import java.sql.SQLException;
import org.apache.log4j.Logger;

public class PubUtil {

    private static Logger logger = Logger.getLogger(PubUtil.class.getName());

    public PubUtil() {
        super();

    }

    public static void handleException(Exception e, Logger lg) throws Exception {
        lg.error("错误:", e);
        throw e;
    }

    public static void handleExceptionNoThrow(Exception e, Logger lg) {
        lg.error("错误:", e);

    }

    public static void handleExceptionForTran(Exception e, Logger lg,
            DbHelper dbHelper) throws Exception {
        if (dbHelper != null) {
            dbHelper.rollbackTran();
        }
        lg.error("错误:", e);
        throw e;
    }

    public static void handleExceptionForTranNoThrow(Exception e, Logger lg,
            DbHelper dbHelper) {

        try {
            if (dbHelper != null) {
                dbHelper.rollbackTran();
            }
        } catch (SQLException ex) {
            lg.error("错误:", ex);
        }
        lg.error("错误:", e);;
    }

    public static void finalProcess(DbHelper dbHelper) {
        try {
            if (dbHelper != null) {
                dbHelper.closeConnection();
            }
        } catch (SQLException e) {
            logger.error("关闭连接失败", e);
        }
    }

    public static void finalProcessForTran(DbHelper dbHelper) {
        try {
            if (dbHelper != null) {
                dbHelper.closeConnection();
            }

        } catch (SQLException e) {
            logger.error("关闭连接失败", e);
        }
    }
}
