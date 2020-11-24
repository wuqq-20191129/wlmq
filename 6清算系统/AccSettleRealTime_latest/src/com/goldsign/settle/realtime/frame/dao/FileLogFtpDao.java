/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.app.vo.FileLogVo;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.util.FileUtil;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.FileFtpVo;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FileLogFtpDao {
    

    private static Logger logger = Logger.getLogger(FileLogFtpDao.class.getName());

    public int insert(String fileName,int fileSize, String balanceWaterNo, String balanceWaterNoPut,int balanceWaterNoSub ) throws Exception {
        String sql = "insert into "+FrameDBConstant.DB_PRE+"ST_LOG_FTP_PUT_FILE(FILE_NAME,DATA_TYPE,BALANCE_WATER_NO,BALANCE_WATER_NO_SUB,"
                + "PUT_BALANCE_NO,GEN_DATETIME,PUT_DATETIME,STATE,file_size) "
                + "values(?,?,?,?,?,sysdate,sysdate,?,?) ";
        DbHelper dbHelper = null;
        int result = 0;
        String dataType = FileUtil.getFieDataType(fileName);
        Object[] values = {fileName, dataType, balanceWaterNo,balanceWaterNoSub, balanceWaterNoPut, FrameFileHandledConstant.FILE_HDL_NO,new Integer(fileSize)};
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
    
    public int insertForOct(String fileName,int fileSize, String balanceWaterNo, String balanceWaterNoPut) throws Exception {
        String sql = "insert into "+FrameDBConstant.DB_PRE+"ST_LOG_FTP_PUT_FILE_OCT(FILE_NAME,DATA_TYPE,BALANCE_WATER_NO,"
                + "PUT_BALANCE_NO,GEN_DATETIME,PUT_DATETIME,STATE,file_size) "
                + "values(?,?,?,?,sysdate,sysdate,?,?) ";
        DbHelper dbHelper = null;
        int result = 0;
        String dataType = FileUtil.getFieDataType(fileName);
        Object[] values = {fileName, dataType, balanceWaterNo, balanceWaterNoPut, FrameFileHandledConstant.FILE_HDL_NO,new Integer(fileSize)};
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
    
    private void addRecord(Hashtable<String,Vector> ht,String fileName,String fileSize){
        String line = fileName.substring(3,5);
        if(!ht.containsKey(line))
            ht.put(line, new Vector());
        Vector v = ht.get(line);
        String[] record = new String[2];
        record[0] =fileName;
        record[1]=fileSize;
        v.add(record);
    }

    public Hashtable<String,Vector> getRecordsForAudit(String balanceWaterNo) throws Exception {

        String sql = "select FILE_NAME,file_size from "+FrameDBConstant.DB_PRE+"ST_LOG_FTP_PUT_FILE where BALANCE_WATER_NO=? order by GEN_DATETIME";
        DbHelper dbHelper = null;
        boolean result = false;
        String fileName,fileSize;
        Hashtable<String,Vector> files = new Hashtable();

        Object[] values = {balanceWaterNo};
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                fileName = dbHelper.getItemValue("FILE_NAME");
                fileSize=dbHelper.getItemValue("FILE_SIZE");
                this.addRecord(files, fileName,fileSize);

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
