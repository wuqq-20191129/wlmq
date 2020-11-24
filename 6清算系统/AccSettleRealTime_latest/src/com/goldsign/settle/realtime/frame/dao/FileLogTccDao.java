/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.util.FileUtil;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FileLogTccDao {

    private static Logger logger = Logger.getLogger(FileLogTccDao.class.getName());
    private static String FINISH_FLAG_YES="1";

    public int insert(String fileName,  String balanceWaterNo,String remark) throws Exception {
        String sql = "insert into " + FrameDBConstant.DB_ST + "w_st_log_tcc(water_no,file_name,gen_datetime,gen_flag,balance_water_no,remark) "
                + " values(w_acc_st.w_seq_w_st_log_tcc.nextval,?,sysdate,?,?,?) ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {fileName,FINISH_FLAG_YES,balanceWaterNo,remark};
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.executeUpdate(sql, values);

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return result;

    }
    public int updateForDownload(String fileName,  String balanceWaterNo) throws Exception {
        /*
        String sql = "insert into " + FrameDBConstant.DB_ST + "w_st_log_tcc(water_no,file_name,gen_datetime,gen_flag,balance_water_no) "
                + " values(w_acc_st.w_seq_w_st_log_tcc.nextval,?,sysdate,?,?) ";
        */
         String sql = "update " + FrameDBConstant.DB_ST + "w_st_log_tcc set download_datetime=sysdate,download_flag=? "
                + " where file_name=? and  balance_water_no=? ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {FINISH_FLAG_YES,fileName,balanceWaterNo};
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.executeUpdate(sql, values);

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return result;

    }

}
