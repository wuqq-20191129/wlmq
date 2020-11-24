/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.app.vo.ExportDbResult;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.dao.ExportDaoBase;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import java.math.BigDecimal;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ExportOctRZDao extends ExportDaoBase {

    public static String TABLE_NAME_TRX = "ST_EXT_OCT_AUDIT_FILE_TRX";
    public static String SEQ_NAME_TRX = "ST_EXT_OCT_AUD_FL_TRX";
    public static String TABLE_NAME_SETTLE = "ST_EXT_MTR_AUDIT_FILE_STL";
    private static Logger logger = Logger.getLogger(ExportOctRZDao.class.getName());

    public ExportDbResult getRecordsTrx(String balanceWaterNo) throws Exception {

        return this.getRecords(balanceWaterNo, ExportOctRZDao.TABLE_NAME_TRX);
    }

    public ExportDbResult getRecordsSettle(String balanceWaterNo) throws Exception {

        return this.getRecords(balanceWaterNo, ExportOctRZDao.TABLE_NAME_SETTLE);
    }

    public ExportDbResult getRecords(String balanceWaterNo, String tableName) throws Exception {

        String sql = "select FILE_NAME_AUDITED,TOTAL_RECORD_NUM,TOTAL_RECORD_FEE "
                + "from " + FrameDBConstant.DB_PRE + tableName + "  where balance_water_no=? order by FILE_NAME_AUDITED";

        DbHelper dbHelper = null;
        boolean result = false;

        Vector<String[]> v = new Vector();
        ExportDbResult resultExp = new ExportDbResult();

        Object[] values = {balanceWaterNo};
        int totalFee = 0;
        // totalFee.setScale(2);
        String[] arr;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                arr = this.getArray(dbHelper);

                v.add(arr);

                result = dbHelper.getNextDocument();
            }
            resultExp.setTotalFeeFen(totalFee);
            resultExp.setTotalNum(v.size());
            resultExp.setRecords(v);

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return resultExp;

    }

    public boolean isExist(String balanceWaterNo, String fileName, String tableName) throws Exception {

        String sql = "select FILE_NAME_AUDITED from  " + FrameDBConstant.DB_PRE + tableName
                + " where balance_water_no=? and FILE_NAME_AUDITED=? ";

        DbHelper dbHelper = null;
        boolean result = false;

        Object[] values = {balanceWaterNo, fileName};
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return result;

    }

    private String[] getArray(DbHelper dbHelper) throws Exception {
        String[] arr = new String[3];
        arr[0] = dbHelper.getItemValue("FILE_NAME_AUDITED");//导出文件名称
        arr[1] = dbHelper.getItemValue("TOTAL_RECORD_NUM");//导出文件记录数
        arr[2] = dbHelper.getItemValue("TOTAL_RECORD_FEE");//导出文件记录包含金额
        // arr[2] = this.getBigDecimal(dbHelper, "TOTAL_RECORD_FEE");//dbHelper.getItemValue("TOTAL_RECORD_FEE");//导出文件记录包含金额

        return arr;

    }

    public int insertTrx(String fileName, int totalRecordNum, BigDecimal totalFee, String balanceWaterNo) throws Exception {
        return this.insert(fileName, totalRecordNum, totalFee, balanceWaterNo, ExportOctRZDao.TABLE_NAME_TRX);

    }

    public int insertTrx(String fileName, int totalRecordNum, int totalFee, String balanceWaterNo, int balanceWaterNoSub) throws Exception {
        return this.insert(fileName, totalRecordNum, totalFee, balanceWaterNo, balanceWaterNoSub, ExportOctRZDao.TABLE_NAME_TRX,ExportOctRZDao.SEQ_NAME_TRX);

    }

    public int insertSettle(String fileName, int totalRecordNum, BigDecimal totalFee, String balanceWaterNo) throws Exception {
        return this.insert(fileName, totalRecordNum, totalFee, balanceWaterNo, ExportOctRZDao.TABLE_NAME_SETTLE);

    }

    public int updateTrx(String balanceWaterNo, String fileNameAuf) throws Exception {
        return this.update(balanceWaterNo, fileNameAuf, ExportOctRZDao.TABLE_NAME_TRX);

    }

    public int updateSettle(String balanceWaterNo, String fileNameAuf) throws Exception {
        return this.update(balanceWaterNo, fileNameAuf, ExportOctRZDao.TABLE_NAME_SETTLE);

    }

    public int update(String balanceWaterNo, String fileNameAuf, String tableName) throws Exception {
        String sql = "update  " + FrameDBConstant.DB_PRE+tableName
                + " set file_name=? where balance_water_no=?  ";

        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {fileNameAuf, balanceWaterNo};

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

    public int insert(String fileName, int totalRecordNum, BigDecimal totalFee, String balanceWaterNo, String tableName) throws Exception {
        if (this.isExist(balanceWaterNo, fileName, tableName)) {
            return 0;
        }
        String sql = "insert into  " + FrameDBConstant.DB_PRE + tableName
                + " (WATER_NO,FILE_NAME_AUDITED,TOTAL_RECORD_NUM,TOTAL_RECORD_FEE,BALANCE_WATER_NO) "
                + "values(" + FrameDBConstant.DB_PRE + "SEQ_" + tableName + ".nextval," + "?,?,?,?)";

        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {fileName, new Integer(totalRecordNum), totalFee, balanceWaterNo};

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

    public int insert(String fileName, int totalRecordNum, int totalFee, String balanceWaterNo, int balanceWaterNoSub, String tableName, String seqName) throws Exception {
        if (this.isExist(balanceWaterNo, fileName, tableName)) {
            return 0;
        }
        String sql = "insert into  " + FrameDBConstant.DB_PRE + tableName
                + " (WATER_NO,FILE_NAME_AUDITED,TOTAL_RECORD_NUM,TOTAL_RECORD_FEE,BALANCE_WATER_NO,BALANCE_WATER_NO_SUB) "
                + "values(" + FrameDBConstant.DB_PRE + "SEQ_W_" + seqName + ".nextval," + "?,?,?,?,?)";

        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {fileName, new Integer(totalRecordNum), totalFee, balanceWaterNo, balanceWaterNoSub};

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
