/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.ConfigBcpVo;
import com.goldsign.settle.realtime.frame.vo.FileErrorVo;
import com.goldsign.settle.realtime.frame.vo.SettleQueueVo;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FileErrorDao {

    private static Logger logger = Logger.getLogger(FileErrorDao.class.getName());

    public int insert(FileErrorVo vo) throws Exception {
        String sql = "insert into "+FrameDBConstant.DB_PRE+"ST_ERR_DATA_FILE(balance_water_no,file_name,err_code,hdl_flag,gen_time,remark) "
                + "values(?,?,?,?,sysdate,?) ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {vo.getBalanceWaterNo(), vo.getFileName(), vo.getErrorCode(),FrameFileHandledConstant.FILE_HDL_NO, vo.getRemark()};
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
    public int insertForField(FileErrorVo vo) throws Exception {
        String sql = "insert into "+FrameDBConstant.DB_PRE+"ST_ERR_DATA_FIELD(balance_water_no,file_name,err_code,hdl_flag,gen_time,remark,error_data) "
                + "values(?,?,?,?,sysdate,?,?) ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {vo.getBalanceWaterNo(), vo.getFileName(), vo.getErrorCode(),FrameFileHandledConstant.FILE_HDL_NO,
                          vo.getRemark(),TradeUtil.getHexStringLimitLen(vo.getErrData(),FrameCodeConstant.LIMIT_LEN_FIELD_ERR_DATA)};
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
    public int insertForOctReturn(FileErrorVo vo) throws Exception {
        String sql = "insert into "+FrameDBConstant.DB_PRE+"ST_ERR_DATA_FILE_OCT(balance_water_no,file_name,err_code,hdl_flag,gen_time,remark) "
                + "values(?,?,?,?,sysdate,?) ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {vo.getBalanceWaterNo(), vo.getFileName(), vo.getErrorCode(),FrameFileHandledConstant.FILE_HDL_NO, vo.getRemark()};
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
     private void addRecord(Hashtable<String,Vector> ht,String fileName,String errorCode){
        String line = fileName.substring(3,5);
        if(!ht.containsKey(line))
            ht.put(line, new Vector());
        Vector v = ht.get(line);
        String[] record = new String[2];
        record[0] =fileName;
        record[1]=errorCode;
        v.add(record);
    }
    public Hashtable<String,Vector> getRecordsForAudit(String balanceWaterNo) throws Exception {

        String sql = "select A.FILE_NAME,B.err_code_lcc from "+FrameDBConstant.DB_PRE+"ST_ERR_DATA_FILE A,"+FrameDBConstant.DB_PRE+"st_cfg_err_code_mapping B  "
                + "where A.BALANCE_WATER_NO=? and A.err_code (+)= B.err_code_acc "
                + " order by GEN_TIME";
        DbHelper dbHelper = null;
        boolean result = false;
        String fileName,errorCode;
        Hashtable<String,Vector> files = new Hashtable();

        Object[] values = {balanceWaterNo};
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                fileName = dbHelper.getItemValue("FILE_NAME");
                errorCode  = dbHelper.getItemValue("err_code_lcc");
                this.addRecord(files, fileName,errorCode);

                result = dbHelper.getNextDocument();
            }


        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return files;


    }
}
