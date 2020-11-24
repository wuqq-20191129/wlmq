/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.util;

import com.goldsign.lib.db.util.DbHelper;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import java.text.SimpleDateFormat;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import com.goldsign.frame.vo.User;
import com.goldsign.systemmonitor.util.MonitorDBHelper;
//import com.goldsign.systemmonitor.util.MonitorDBHelper;

import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FramePubUtil {

    private static final int PUB_FLAG_TYPE_TICKET_ZONE = 12;

    private static int FLAG_INSERT = 0;// 插入标志
    private static int FLAG_UPDATE = 1;// 更新标志
    private static Logger logger = Logger.getLogger(FramePubUtil.class);

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
        if (dbHelper != null) {
            dbHelper.rollbackTran();
        }
        e.printStackTrace();
        lg.error("错误:", e);
        throw e;
    }

    public static void finalProcess(DbHelper dbHelper) {
        try {
            if (dbHelper != null) {
                dbHelper.closeConnection();
            }
        } catch (SQLException e) {
            logger.error("Fail to close connection", e);
        }
    }
    
    //测试。。
        public static void finalProcess(MonitorDBHelper dbHelper) {
        try {
            if (dbHelper != null) {
                dbHelper.closeConnection();
            }
        } catch (SQLException e) {
            logger.error("Fail to close connection", e);
        }
    }


    public static void finalProcessForTran(DbHelper dbHelper) {
        try {
            /*
             * if (dbHelper != null && !dbHelper.isConClosed() &&
             * !dbHelper.getAutoCommit()) dbHelper.setAutoCommit(true);
             */
            if (dbHelper != null) {
                dbHelper.closeConnection();
            }

        } catch (SQLException e) {
            logger.error("Fail to close connection", e);
        }
    }

    public static HashMap getQueryControlDefaultValues(
            String queryControlDefaultValues) {
        HashMap qv = new HashMap();
        if (queryControlDefaultValues == null
                || queryControlDefaultValues.trim().length() == 0) {
            return qv;
        }
        StringTokenizer st = new StringTokenizer(queryControlDefaultValues, ";");
        String token = "";
        String name = "";
        String value = "";
        int index = -1;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            index = token.indexOf("#");
            if (index == -1) {
                continue;
            }
            name = token.substring(0, index);
            value = token.substring(index + 1);
            qv.put(name, value);

        }
        return qv;
    }

}
