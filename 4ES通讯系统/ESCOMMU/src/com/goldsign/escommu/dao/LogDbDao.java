package com.goldsign.escommu.dao;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.NumberUtil;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.vo.LogCommuVo;
import java.util.Date;
import org.apache.log4j.Logger;

public class LogDbDao {

    private static Logger logger = Logger.getLogger(LogDbDao.class.getName());

    public static void writeConnectLog(java.util.Date time, String ip, String result, String remark) 
            throws Exception {

        DbHelper dbHelper = null;
        String sqlStr = "insert into "+AppConstant.COM_CM_P+"CM_EC_CONNECT_LOG(water_no,connect_datetime,connect_ip,connect_result,remark) "
                + " values("+AppConstant.COM_CM_P+"SEQ_"+AppConstant.TABLE_PREFIX+"CM_EC_CONNECT_LOG.nextval,?,?,?,?)";
        Object[] values = {new java.sql.Timestamp(time.getTime()), ip, result, remark};
        try {

            dbHelper = new DbHelper("LogConnDao", AppConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.executeUpdate(sqlStr, values);
        } catch (Exception e) {
            DateHelper.screenPrintForEx(DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date()) + "  更新 com_connect_log 出错! " + e);
            logger.error("更新 com_connect_log 出错! " + e);
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
    }

    public static void writeRecvSendLog(java.util.Date time, String ip, String type, String messageCode,
            String messageSequ, byte[] message, String result) throws Exception {
        
        DbHelper dbHelper = null;
        String sqlStr = "insert into "+AppConstant.COM_CM_P+"CM_EC_RECV_SEND_LOG(water_no,datetime_rec,ip,type,message_code,message_sequ,message,result) "
                + "values("+AppConstant.COM_CM_P+"SEQ_"+AppConstant.TABLE_PREFIX+"CM_EC_RECV_SEND_LOG.nextval,?,?,?,?,?,?,?)";

        Object[] values = {new java.sql.Timestamp(time.getTime()), ip, type, messageCode,
            messageSequ, message, result};
        try {

            dbHelper = new DbHelper("LogConnDao", AppConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.executeUpdate(sqlStr, values);
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
    }

    public static void writeFtpLog(java.util.Date time, String ip,
            String fileName, java.util.Date startTime,int spendTime, String result) throws Exception {
       
        DbHelper dbHelper = null;
        
        String sqlStr = "insert into "+AppConstant.COM_CM_P+"CM_EC_FTP_LOG(water_no,datetime_ftp,ip,filename,start_time,spend_time,result) "
                + " values("+AppConstant.COM_CM_P+"SEQ_"+AppConstant.TABLE_PREFIX+"CM_EC_FTP_LOG.nextval,?,?,?,?,?,?)";

        Object[] values = {
            new java.sql.Timestamp(time.getTime()), ip, fileName, new java.sql.Timestamp(startTime.getTime()),
            new Integer(spendTime), result};
        try {
            dbHelper = new DbHelper("LogConnDao", AppConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.executeUpdate(sqlStr, values);
        } catch (Exception e) {
            DateHelper.screenPrintForEx(DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date()) + "  增加 ic_commu_ftp_log 记录出错");
            logger.error("增加 ic_commu_ftp_log 记录出错! " + e);
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
    }
    
    public static int insertDetail(LogCommuVo vo) throws Exception {

        DbHelper dbHelper = null;
        String sql = "insert into "+AppConstant.COM_CM_P+"CM_EC_LOG(water_no,message_id,message_name,message_from,start_time,"
                + "end_time,use_time,result,hdl_thread,sys_level,remark) "
                + " values("+AppConstant.COM_CM_P+"SEQ_"+AppConstant.TABLE_PREFIX+"CM_EC_LOG.nextval,?,?,?,?,?,?,?,?,?,?) ";

        int result = 0;

        Object[] values = {vo.getMessageId(), vo.getMessageName(), vo.getMessageFrom(),
            DateHelper.utilDateToSqlTimestamp(DateHelper.getDatetimeFromYYYY_MM_DD_HH_MM_SS(vo.getStartTime())), 
            DateHelper.utilDateToSqlTimestamp(DateHelper.getDatetimeFromYYYY_MM_DD_HH_MM_SS(vo.getEndTime())), 
            NumberUtil.getIntegerValue(vo.getUseTime(), 0),
            vo.getResult(),
            vo.getHdlThread(),
            vo.getSysLevel(), vo.getRemark()};

        try {

            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
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
}
