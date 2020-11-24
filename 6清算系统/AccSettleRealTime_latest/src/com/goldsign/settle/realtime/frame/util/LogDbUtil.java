/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.util;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.constant.FrameLogConstant;
import com.goldsign.settle.realtime.frame.vo.LogDetailVo;
import com.goldsign.settle.realtime.frame.vo.SynchronizedControl;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class LogDbUtil {

    private static Logger logger = Logger.getLogger(LogDbUtil.class.getName());
    private static SynchronizedControl SYN_CONTROL = new SynchronizedControl();

    public LogDbUtil() {
        super();
        // TODO Auto-generated constructor stub
    }

//	根据级别控制明细输出，如级别>=当前设定的级别，当前级别是警告，而级别是信息的，其不输出
    public static int logForDbDetail(LogDetailVo vo, String level) throws Exception {
        if (level.compareTo(FrameLogConstant.LOG_LEVEL_CURRENT) >= 0) {
            return logForDbDetail(vo);
        }
        return 0;


    }

    public static int logForDbDetail(LogDetailVo vo, String level, DbHelper dbHelper) throws Exception {
        if (level.compareTo(FrameLogConstant.LOG_LEVEL_CURRENT) >= 0) {
            return logForDbDetail(vo, dbHelper);
        }
        return 0;


    }

    public static int logForDbDetail(String messageId, String messageFrom,
            long startTime, long endTime, String result, String hdlThread, String level) throws Exception {
        LogDetailVo vo = getLogDetailVo(messageId, messageFrom,
                startTime, endTime, result, hdlThread, level);
        int n = 0;
        synchronized (SYN_CONTROL) {
            n = logForDbDetail(vo, level);
        }
        return n;

    }

    public static int logForDbDetail(String messageId, String messageFrom,
            long startTime, long endTime, String result, String hdlThread, String level, DbHelper dbHelper) throws Exception {
        LogDetailVo vo = getLogDetailVo(messageId, messageFrom,
                startTime, endTime, result, hdlThread, level);
        int n = 0;
        synchronized (SYN_CONTROL) {
            n = logForDbDetail(vo, level, dbHelper);
        }
        return n;

    }

    public static int logForDbDetail(String messageId, String messageFrom,
            long startTime, long endTime, String result, String hdlThread, String level, String remark) {
        int n = 0;
        try {
            LogDetailVo vo = getLogCommuDetailVo(messageId, messageFrom,
                    startTime, endTime, result, hdlThread, level, remark);

            synchronized (SYN_CONTROL) {
                n = logForDbDetail(vo, level);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;

    }

    public static int logForDbDetail(String messageId, String messageFrom,
            long startTime, long endTime, String result, String hdlThread, String level, String remark,
            DbHelper dbHelper) {
        int n = 0;
        try {
            LogDetailVo vo = getLogCommuDetailVo(messageId, messageFrom,
                    startTime, endTime, result, hdlThread, level, remark);

            synchronized (SYN_CONTROL) {
                n = logForDbDetail(vo, level, dbHelper);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;

    }

    public static int logForDbDetailByVo(LogDetailVo vo) throws Exception {

        int n = 0;
        synchronized (SYN_CONTROL) {
            n = logForDbDetail(vo, vo.getSysLevel());
        }
        return n;

    }

    public static int logForDbDetailByVo(LogDetailVo vo, DbHelper dbHelper) throws Exception {

        int n = 0;
        synchronized (SYN_CONTROL) {
            n = logForDbDetail(vo, vo.getSysLevel(), dbHelper);
        }
        return n;

    }

    public static int logForDbDetail(LogDetailVo vo) throws Exception {
        return insertDetail(vo);

    }

    public static int logForDbDetail(LogDetailVo vo, DbHelper dbHelper) throws Exception {
        return insertDetail(vo, dbHelper);

    }

    public static int insertDetail(LogDetailVo vo) throws Exception {

        DbHelper dbHelper = null;
        String sql = "insert into snd_log_commu(message_id,message_name,message_from,start_time,"
                + "end_time,use_time,result,hdl_thread,sys_level,remark) "
                + " values(?,?,?,?,?,?,?,?,?,?) ";

        int result = 0;
        Object[] values = {
            vo.getStartTime(), vo.getEndTime(),
            vo.getSysLevel()};

        try {

            dbHelper = new DbHelper("LogDbUtil", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            if (!dbHelper.isAvailableForConn()) {
                return -1;
            }
            result = dbHelper.executeUpdate(sql, values);


        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return result;
    }

    public static int insertDetail(LogDetailVo vo, DbHelper dbHelper) throws Exception {
        String sql = "insert into snd_log_commu(message_id,message_name,message_from,start_time,"
                + "end_time,use_time,result,hdl_thread,sys_level,remark) "
                + " values(?,?,?,?,?,?,?,?,?,?) ";
        int result = 0;
        Object[] values = {};

        if (!dbHelper.isAvailableForConn()) {
            return -1;
        }
        result = dbHelper.executeUpdate(sql, values);

        return result;
    }

    public static LogDetailVo getLogDetailVo(String messageId, String messageFrom,
            long startTime, long endTime, String result, String hdlThread, String level) {
        LogDetailVo vo = new LogDetailVo();

        String messageName = getMessageName(messageId);


        vo.setStartTime(DateHelper.datetimeToString(new Date(startTime)));
        vo.setEndTime(DateHelper.datetimeToString(new Date(endTime)));
        vo.setUseTime(getUseTime(startTime, endTime));




        vo.setSysLevel(level);
        return vo;

    }

    public static LogDetailVo getLogCommuDetailVo(String messageId, String messageFrom,
            long startTime, long endTime, String result, String hdlThread, String level, String remark) {
        LogDetailVo vo = new LogDetailVo();
        String messageName = getMessageName(messageId);
        vo.setUseTime(getUseTime(startTime, endTime));



        vo.setSysLevel(level);
        return vo;

    }

    public static int getReportSize(int reportSize) {
        int n = reportSize / 1024;
        if (n == 0) {
            return 1;
        }
        return n;
    }

    public static String getUseTime(long genStartTime, long genEndTime) {
        long useTime = genEndTime - genStartTime;
        useTime = useTime / 1000;
        return new Long(useTime).toString();
    }

    private static String getMessageName(String messageId) {
        String[] messageIds = {};
        String[] messageNames = {};
        for (int i = 0; i < messageIds.length; i++) {
            if (messageId.equals(messageIds[i])) {
                return messageNames[i];
            }
        }
        return "";
    }

    public static String getLevelText(String level) {
        String[] levelTexts = {FrameLogConstant.LOG_LEVEL_INFO_TEXT,
            FrameLogConstant.LOG_LEVEL_WARN_TEXT,
            FrameLogConstant.LOG_LEVEL_ERROR_TEXT,
            FrameLogConstant.LOG_LEVEL_ERROR_SYS_TEXT,};
        String[] levels = {FrameLogConstant.LOG_LEVEL_INFO,
            FrameLogConstant.LOG_LEVEL_WARN,
            FrameLogConstant.LOG_LEVEL_ERROR,
            FrameLogConstant.LOG_LEVEL_ERROR_SYS};
        for (int i = 0; i < levels.length; i++) {
            if (level.equals(levels[i])) {
                return levelTexts[i];
            }
        }
        return "";
    }
}
