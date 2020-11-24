/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;

import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.constant.FrameOctFileImportConstant;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.util.PubUtil;

import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class OctFileImportDao {

    private static Logger logger = Logger.getLogger(OctFileImportDao.class.getName());

    public boolean isHasSetted(String balanceWaterNo, String fileType) throws Exception {
        String sql = "select count(*) num "
                + "from " + FrameDBConstant.DB_PRE + "st_sys_flow_oct_import_ctr where balance_water_no=? and file_type=?  ";
        DbHelper dbHelper = null;
        Object[] values = {balanceWaterNo, fileType};
        boolean result;
        int rowNum = 0;

        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            if (result) {
                rowNum = dbHelper.getItemIntValue("num");
            }


        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        if (rowNum == 0) {
            return false;
        }
        return true;
    }

    private String getRemark(String setType) {
        if (setType.equals(FrameOctFileImportConstant.OCT_IMPORT_NORMAL)) {
            return FrameOctFileImportConstant.OCT_IMPORT_NORMAL_NAME;
        }
        if (setType.equals(FrameOctFileImportConstant.OCT_IMPORT_EXCEPTION)) {
            return FrameOctFileImportConstant.OCT_IMPORT_EXCEPTION_NAME;
        }
        return "";

    }

    public int insertOrUpdate(String balanceWaterNo, String fileType, String setType) throws Exception {
        int n = 0;
        n = this.update(balanceWaterNo, fileType, setType);
        if (n == 0) {
            n = this.insert(balanceWaterNo, fileType, setType);
        }
        return n;
    }

    public int update(String balanceWaterNo, String fileType, String setType) throws Exception {
        String sql = "update "+FrameDBConstant.DB_PRE+"st_sys_flow_oct_import_ctr"
                + " set set_datetime=sysdate,set_type=?,remark=? where balance_water_no=? and file_type=? ";
        DbHelper dbHelper = null;
        String remark = this.getRemark(setType);
        Object[] values = {setType, remark, balanceWaterNo, fileType};
        int result = 0;

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

    public int insert(String balanceWaterNo, String fileType, String setType) throws Exception {
        String sql = "insert into "+FrameDBConstant.DB_PRE+"st_sys_flow_oct_import_ctr"
                + "(balance_water_no,file_type,set_datetime,set_type,remark) values(?,?,sysdate,?,?)   ";
        DbHelper dbHelper = null;
        String remark = this.getRemark(setType);
        Object[] values = {balanceWaterNo, fileType, setType, remark};
        int result = 0;

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
