package com.goldsign.systemmonitor.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.lib.db.util.DbHelper;
import org.apache.log4j.Logger;

public class DateDao {

    static Logger logger = Logger.getLogger(DateDao.class);

    public DateDao() {
        super();
    }

    public boolean isOldFile(String fileDate, String fileName) throws Exception {
        DbHelper dbHelper = null;
        String sql = null;

        Object[] values = {fileDate, fileName};
        boolean result = false;
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE_T);
            dbHelper.setAutoCommit(false);
            sql = "select file_name from mtr_ftp_file_latest where latest_time>=? and file_name=?  ";

            result = dbHelper.getFirstDocument(sql, values);


        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return result;

    }

    public int addFile(String fileDate, String fielName) throws Exception {
        DbHelper dbHelper = null;
        String sql = null;
        int n = 0;
        Object[] values = {fileDate, fielName};

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE_T);
            dbHelper.setAutoCommit(false);
            sql = "update mtr_ftp_file_latest set latest_time=? where file_name=? ";

            n = dbHelper.executeUpdate(sql, values);
            if (n != 1) {
                sql = "insert into mtr_ftp_file_latest(latest_time,file_name) values(?,?) ";
                n = dbHelper.executeUpdate(sql, values);
            }
            dbHelper.commitTran();
        } catch (Exception e) {
            FramePubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            FramePubUtil.finalProcessForTran(dbHelper);
        }
        return n;
    }
}
