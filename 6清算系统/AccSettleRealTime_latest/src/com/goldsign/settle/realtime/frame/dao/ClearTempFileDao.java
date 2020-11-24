/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.ClearTempFileVo;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ClearTempFileDao {

    private static Logger logger = Logger.getLogger(ClearTempFileDao.class.getName());

    public Vector<ClearTempFileVo> getClearTempFileCfgs() throws Exception {
        Vector<ClearTempFileVo> configs = new Vector();
        String sql = "select id,clear_type,path_clear,reserve_days "
                + "from " + FrameDBConstant.DB_ST + "w_st_sys_cfg_temp_file_clear order by id";
        DbHelper dbHelper = null;
        boolean result;

        ClearTempFileVo vo = null;

        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql);
            while (result) {
                vo = this.getVo(dbHelper);
                configs.add(vo);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return configs;
    }

    private ClearTempFileVo getVo(DbHelper dbHelper) throws Exception {
        ClearTempFileVo vo = new ClearTempFileVo();
        vo.setId(dbHelper.getItemIntValue("id"));
        vo.setClearType(dbHelper.getItemIntValue("clear_type"));
        vo.setPathClear(dbHelper.getItemValue("path_clear"));
        vo.setReserveDays(dbHelper.getItemIntValue("reserve_days"));
        return vo;
    }

    public int insertLog(String balanceWaterNo, String clearPath, String beginDate, int clearNum, String clearResult, String remark) throws Exception {
        String sql = "insert into " + FrameDBConstant.DB_ST + "w_st_log_temp_file_clear(balance_water_no,clear_dir,"
                + "clear_datetime_begin,clear_datetime_end,clear_file_num,clear_result,remark ) "
                + "values(?,?,to_date(?,'yyyymmddhh24miss'),sysdate,?,?,?)";
        int n = 0;
        Object[] values = {balanceWaterNo, clearPath, beginDate, clearNum, clearResult, remark};
        DbHelper dbHelper = null;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            n = dbHelper.executeUpdate(sql, values);
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return n;

    }

}
