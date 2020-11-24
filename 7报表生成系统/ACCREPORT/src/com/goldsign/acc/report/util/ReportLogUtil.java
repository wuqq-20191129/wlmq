package com.goldsign.acc.report.util;

import com.goldsign.acc.report.constant.AppConstant;
import com.goldsign.acc.report.vo.LogReportDetailVo;
import com.goldsign.acc.report.vo.LogReportTotalVo;
import com.goldsign.lib.db.util.DbHelper;


import java.util.Date;


import org.apache.log4j.Logger;

public class ReportLogUtil {

    private static String delim = "	";
    private static Logger logger = Logger.getLogger(ReportLogUtil.class.
            getName());
    private static String IS_DELAY_NOT = "0";//非滞留报表
    private static String IS_DELAY_YES = "1";//滞留报表
    private static LogReportTotalVo LOG_REPORT_TOTAL = new LogReportTotalVo();

    public ReportLogUtil() {
        super();
        // TODO Auto-generated constructor stub
    }
    //根据级别控制输出，如级别>=当前设定的级别，当前级别是警告，而级别是信息的，其不输出

    public static void logForConsole(String msg, String level) {
        if (level.compareTo(AppConstant.LOG_LEVEL_CURRENT) >= 0) {
            System.out.println(
                    DateHelper.datetimeToString(new Date()) + delim
                    + AppConstant.LOG_LEVEL_TEXT_CURRENT + delim + msg);
        }

    }
    //与级别无关的输出

    public static void logForConsole(String msg) {

        System.out.println(
                DateHelper.datetimeToString(new Date()) + delim
                + AppConstant.LOG_LEVEL_TEXT_CURRENT + delim + msg);

    }

    public static String getLevelText(String level) {
        String[] levelTexts = {AppConstant.LOG_LEVEL_INFO_TEXT,
            AppConstant.LOG_LEVEL_WARN_TEXT,
            AppConstant.LOG_LEVEL_ERROR_TEXT,
            AppConstant.LOG_LEVEL_ERROR_SYS_TEXT,};
        String[] levels = {AppConstant.LOG_LEVEL_INFO,
            AppConstant.LOG_LEVEL_WARN,
            AppConstant.LOG_LEVEL_ERROR,
            AppConstant.LOG_LEVEL_ERROR_SYS};
        for (int i = 0; i < levels.length; i++) {
            if (level.equals(levels[i])) {
                return levelTexts[i];
            }
        }
        return "";
    }
//	根据级别控制明细输出，如级别>=当前设定的级别，当前级别是警告，而级别是信息的，其不输出

    public static int logForDbDetail(LogReportDetailVo vo, String level, String bufferFlag) throws Exception {
        if (level.compareTo(AppConstant.LOG_LEVEL_CURRENT) >= 0) {
            return logForDbDetail(vo, bufferFlag);
        }
        return 0;


    }
//	根据级别控制总计输出，如级别>=当前设定的级别，当前级别是警告，而级别是信息的，其不输出

    public static int logForDbTotal(LogReportTotalVo vo, String level) {
        if (level.compareTo(AppConstant.LOG_LEVEL_CURRENT) >= 0) {
            return logForDbTotal(vo);
        }
        return 0;


    }

    public static int logForDbDetail(LogReportDetailVo vo, String bufferFlag) throws Exception {
        if (bufferFlag.equals(AppConstant.BUFFER_FLAG_NORMAL))//正常缓存，增加明细记录
        {
            return insertDetail(vo);
        }
        return updateDetail(vo);//重生成、错误缓存，更新明细记录的生成次数
    }
    
    private static byte[] SYNCONTROL16 = new byte[0];
    public static int insertDetail(LogReportDetailVo vo) throws Exception {
        int result = 0;
        synchronized (SYNCONTROL16) {
            DbHelper dbHelper = null;
            String sql = "insert into " + AppConstant.ST_USER + "w_rp_log_detail"
                    + "(water_no,report_code,report_name,report_module,report_size,"
                    + "is_delay,gen_start_time,gen_end_time,use_time,gen_thread,gen_count,"
                    + "balance_water_no,sys_level,report_water_no) "
                    + " values(" + AppConstant.ST_USER + "w_seq_w_rp_log_detail.nextval,?,?,?,?,?,"
                    + "to_date(?,'YYYY-MM-DD HH24:MI:SS'),to_date(?,'YYYY-MM-DD HH24:MI:SS') "
                    + ",?,?,?,?,?,?) ";

            Object[] values = {vo.getReportCode(), vo.getReportName(), vo.getReportModule(),
                NumberUtil.getIntegerValue(vo.getReportSize(), 0),
                vo.getIsDelay(), vo.getGenStartTime(), vo.getGenEndTime(),
                NumberUtil.getIntegerValue(vo.getUseTime(), 0),
                vo.getGenThread(),
                NumberUtil.getIntegerValue(vo.getGenCount(), 0), vo.getBalanceWaterNo(), vo.getSysLevel(),
                AppConstant.REPORT_WATER_NO};

            try {
                dbHelper = new DbHelper("", AppConstant.REPORT_DBCPHELPER.getConnection());
                result = dbHelper.executeUpdate(sql, values);


            } catch (Exception e) {
                PubUtil.handleException(e, logger);
            } finally {
                PubUtil.finalProcess(dbHelper);
            }
        }
        return result;
    }
    private static byte[] SYNCONTROL18 = new byte[0];
    public static int updateDetail(LogReportDetailVo vo) throws Exception {
        int result = 0;
        synchronized (SYNCONTROL18) {
        DbHelper dbHelper = null;
        String sql = "update " + AppConstant.ST_USER + "w_rp_log_detail set gen_count =gen_count+1 "
                + " where report_water_no=? and report_name=? ";
        
        Object[] values = {
            AppConstant.REPORT_WATER_NO, vo.getReportName()};

        try {
            dbHelper = new DbHelper("", AppConstant.REPORT_DBCPHELPER.getConnection());
            dbHelper.setAutoCommit(false);

            result = dbHelper.executeUpdate(sql, values);
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        }
        return result;
    }

    private static byte[] SYNCONTROL17 = new byte[0];
    
    public static int logForDbTotal(LogReportTotalVo vo) {
        int result = 0;
        synchronized (SYNCONTROL17) {
        DbHelper dbHelper = null;
        String sql = "insert into " + AppConstant.ST_USER + "w_rp_log_total"
                + "(water_no,balance_water_no,total_num,total_delay_num,total_num_regen,"
                + "total_error_num,total_size,gen_start_time,gen_end_time,use_time,sys_level,report_water_no) "
                + " values(" + AppConstant.ST_USER + "w_seq_w_rp_log_total.nextval,?,?,?,?,?,? "
                + ",to_date(?,'YYYY-MM-DD HH24:MI:SS'),to_date(?,'YYYY-MM-DD HH24:MI:SS') ,?,?,?) ";

        
        Object[] values = {vo.getBalanceWaterNo(),
            NumberUtil.getIntegerValue(vo.getTotalNum(), 0),
            NumberUtil.getIntegerValue(vo.getTotalDelayNum(), 0),
            NumberUtil.getIntegerValue(vo.getTotalNumRegen(), 0),
            NumberUtil.getIntegerValue(vo.getTotalErrorNum(), 0),
            new Integer(getReportSize(new Integer(vo.getTotalSize()).intValue())),
            vo.getGenStartTime(), vo.getGenEndTime(),
            NumberUtil.getIntegerValue(vo.getUseTime(), 0), vo.getSysLevel(),
            AppConstant.REPORT_WATER_NO};

        try {
            dbHelper = new DbHelper("", AppConstant.REPORT_DBCPHELPER.getConnection());
            result = dbHelper.executeUpdate(sql, values);


        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        }
        return result;

    }

    public static LogReportDetailVo getLogReportDetailVo(String reportCode, String reportName,
            String reportModule, int reportSize, long genStartTime, long genEndTime, int genThread,
            int genCount, String balanceWaterNo, String squadDay) {
        LogReportDetailVo vo = new LogReportDetailVo();
        vo.setReportCode(reportCode);
        vo.setReportName(reportName);
        vo.setReportModule(reportModule);

        vo.setReportSize(new Integer(getReportSize(reportSize)).toString());
        vo.setGenThread(new Integer(genThread).toString());
        vo.setGenCount(new Integer(genCount).toString());
        vo.setBalanceWaterNo(balanceWaterNo);

        vo.setGenStartTime(DateHelper.datetimeToString(new Date(genStartTime)));
        vo.setGenEndTime(DateHelper.datetimeToString(new Date(genEndTime)));
        vo.setUseTime(getUseTime(genStartTime, genEndTime));
        vo.setIsDelay(getIsDelay(squadDay, balanceWaterNo));

        vo.setSysLevel(AppConstant.LOG_LEVEL_CURRENT);
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

    public static String getIsDelay(String squadDay, String balanceWaterNo) {
        if (isDate(squadDay)) {//日报
            if (balanceWaterNo.substring(0, 8).equals(squadDay)) {
                return IS_DELAY_NOT;
            }
            return IS_DELAY_YES;
        } 
        if(isMon(squadDay)){//月报
            if (balanceWaterNo.substring(0, 6).equals(squadDay.substring(0, 6))) {
                return IS_DELAY_NOT;
            }
            return IS_DELAY_YES;
        }
        else{
                if(balanceWaterNo.substring(0, 4).equals(squadDay.substring(0, 4)))
                    return IS_DELAY_NOT;
                 else
                    return IS_DELAY_YES;
        }  
    }

    private static boolean isDate(String squadDay) {
        if (squadDay.endsWith("00")) {
            return false;
        }
        return true;
    }
        private static boolean isMon(String squadDay) {
        if (squadDay.endsWith("0000")) {
            return false;
        }
        return true;
    }
    

    private static boolean isDelay(String squadDay, String balanceWaterNo) {
        if (isDate(squadDay)) {//日报
            if (balanceWaterNo.substring(0, 8).equals(squadDay)) {
                return false;
            }
            return true;
        } 
        if(isMon(squadDay))//月报
        {
            if (balanceWaterNo.substring(0, 6).equals(squadDay.substring(0, 6))) {
                return false;
            }
            return true;

        }
        else{
            if (balanceWaterNo.substring(0, 4).equals(squadDay.substring(0, 4))) {
                return false;
            }
            return true;
        }
    }

    public static LogReportTotalVo getLogReportTotalVo(String balanceWaterNo, int totalNum, int totalDelayNum,
            int totalNumRegen, int totalErrorNum, int totalSize, long genStartTime, long genEndTime) {
        LogReportTotalVo vo = new LogReportTotalVo();

        vo.setBalanceWaterNo(balanceWaterNo);
        vo.setTotalNum(new Integer(totalNum).toString());
        vo.setTotalDelayNum(new Integer(totalDelayNum).toString());
        vo.setTotalNumRegen(new Integer(totalNumRegen).toString());
        vo.setTotalErrorNum(new Integer(totalErrorNum).toString());
        vo.setTotalSize(new Integer(totalSize).toString());

        vo.setGenStartTime(DateHelper.datetimeToString(new Date(genStartTime)));
        vo.setGenEndTime(DateHelper.datetimeToString(new Date(genEndTime)));
        vo.setUseTime(getUseTime(genStartTime, genEndTime));
        vo.setSysLevel(AppConstant.LOG_LEVEL_CURRENT);

        return vo;

    }

    private static String add(String num, int addNum) {
        int iNum = new Integer(num).intValue() + addNum;
        return new Integer(iNum).toString();

    }

    public static LogReportTotalVo getLogReportTotalVo(String reportCode, String reportName,
            String reportModule, int reportSize, long genStartTime, long genEndTime, int genThread,
            int genCount, String balanceWaterNo, String squadDay, String bufferFlag) {
        synchronized (LOG_REPORT_TOTAL) {
            if (bufferFlag.equals(AppConstant.BUFFER_FLAG_NORMAL))//正常缓存的报表
            {
                if (isDelay(squadDay, balanceWaterNo))//滞留报表
                {
                    LOG_REPORT_TOTAL.setTotalDelayNum(add(LOG_REPORT_TOTAL.getTotalDelayNum(), 1));
                } else {
                    LOG_REPORT_TOTAL.setTotalNum(add(LOG_REPORT_TOTAL.getTotalNum(), 1));
                }
            }
            if (bufferFlag.equals(AppConstant.BUFFER_FLAG_REGEN))//重生成缓存的报表
            {
                LOG_REPORT_TOTAL.setTotalNumRegen(add(LOG_REPORT_TOTAL.getTotalNumRegen(), 1));
            }
            if (bufferFlag.equals(AppConstant.BUFFER_FLAG_ERROR))//错误缓存的报表
            {
                LOG_REPORT_TOTAL.setTotalErrorNum(add(LOG_REPORT_TOTAL.getTotalErrorNum(), 1));
            }

            //累计大小
            LOG_REPORT_TOTAL.setTotalSize(add(LOG_REPORT_TOTAL.getTotalSize(), reportSize));



            return LOG_REPORT_TOTAL;
        }

    }
    //设置汇总信息的总生成时间、清算流水号、报表流水号、日志级别

    public static LogReportTotalVo getLogReportTotalVo(String balanceWaterNo, long startTime, long endTime) {
        synchronized (LOG_REPORT_TOTAL) {
            LOG_REPORT_TOTAL.setBalanceWaterNo(balanceWaterNo);
            LOG_REPORT_TOTAL.setGenStartTime(DateHelper.datetimeToString(new Date(startTime)));
            LOG_REPORT_TOTAL.setGenEndTime(DateHelper.datetimeToString(new Date(endTime)));
            LOG_REPORT_TOTAL.setUseTime(getUseTime(startTime, endTime));
            LOG_REPORT_TOTAL.setSysLevel(AppConstant.LOG_LEVEL_CURRENT);
            LOG_REPORT_TOTAL.setReportWaterNo(AppConstant.REPORT_WATER_NO);
            return LOG_REPORT_TOTAL;

        }

    }

    public static void clearReportTotal() {
        ReportLogUtil.LOG_REPORT_TOTAL.clear();
    }
   
    
    
    
}
