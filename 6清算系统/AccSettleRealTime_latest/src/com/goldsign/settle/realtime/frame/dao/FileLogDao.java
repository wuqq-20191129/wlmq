/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.app.vo.FileLogVo;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.FileErrorVo;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FileLogDao {

    private static Logger logger = Logger.getLogger(FileLogDao.class.getName());
    
    public boolean isRepeat(String fileName)  {
        String sql = "select count(*) rowcount from  "+FrameDBConstant.DB_PRE+"st_log_file_passed where file_name=? "    ;
        DbHelper dbHelper = null;
        boolean result = false;
        int num;
        Object[] values = {fileName};
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            if(result){
               num = dbHelper.getItemIntValue("rowcount");
               if(num ==0)
                   return false;
               else
                   return true;
            }

        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
            return false;
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return false;


    }

    public int insert(FileLogVo vo) throws Exception {
        String sql = "insert into "+FrameDBConstant.DB_PRE+"st_log_file_passed(file_name,file_type,balance_water_no,gen_time) "
                + "values(?,?,?,sysdate) ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {vo.getFileName(), vo.getFileType(), vo.getBalanceWaterNo()};
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
    public int insertForOct(FileLogVo vo) throws Exception {
        String sql = "insert into "+FrameDBConstant.DB_PRE+"st_log_file_passed_oct(file_name,file_type,balance_water_no,gen_time) "
                + "values(?,?,?,sysdate) ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {vo.getFileName(), vo.getFileType(), vo.getBalanceWaterNo()};
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
