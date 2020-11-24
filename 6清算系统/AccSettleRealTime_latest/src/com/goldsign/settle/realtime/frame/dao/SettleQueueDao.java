/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;

import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.ConfigBcpVo;
import com.goldsign.settle.realtime.frame.vo.SettleQueueVo;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class SettleQueueDao {

    private static Logger logger = Logger.getLogger(SettleQueueDao.class.getName());

    public int insert(String balanceWaterNo, int balanceWaterNoSub, String finalFlag) throws Exception {
        String sql = "insert into " + FrameDBConstant.DB_PRE + "ST_SYS_QUE_MSG(balance_water_no,balance_water_no_sub,finish_flag,"
                + "is_final,error_code,begin_time) "
                + "values(?,?,?,?,?,sysdate) ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {balanceWaterNo, balanceWaterNoSub, FrameCodeConstant.SETTLE_FINISH_NOT,
            finalFlag, FrameCodeConstant.SETTLE_OK
        };
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

    public int updateForFinish(String balanceWaterNo, int balanceWaterNoSub) throws Exception {
  /*      String sql = "insert into " + FrameDBConstant.DB_PRE + "ST_SYS_QUE_MSG(balance_water_no,balance_water_no_sub,finish_flag,"
                + "is_final,error_code,begin_time) "
                + "values(?,?,?,?,?,sysdate) ";
*/
        String sql = "update  " + FrameDBConstant.DB_PRE + "ST_SYS_QUE_MSG set finish_flag_app=?  where balance_water_no=? and balance_water_no_sub=?";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {FrameCodeConstant.SETTLE_FINISH_YES,balanceWaterNo, balanceWaterNoSub
           
        };
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

    public SettleQueueVo getQueueMsgUnhandled() throws Exception {

        String sql = "select balance_water_no,balance_water_no_sub,finish_flag,is_final,error_code "
                + "from " + FrameDBConstant.DB_PRE + "ST_SYS_QUE_MSG where finish_flag='0'  order by balance_water_no,balance_water_no_sub";
        DbHelper dbHelper = null;
        boolean result;
        SettleQueueVo vo = null;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql);
            if (result) {
                vo = this.getVo(dbHelper);

            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return vo;
    }

    public boolean existQueueMsg(String balanceWaterNo, int balanceWaterNoSub) throws Exception {

        String sql = "select count(*) num "
                + "from " + FrameDBConstant.DB_PRE + "ST_SYS_QUE_MSG where balance_water_no=? and balance_water_no_sub=? ";
        DbHelper dbHelper = null;
        boolean result;
        SettleQueueVo vo = null;
        try {
            Object[] values = {balanceWaterNo, balanceWaterNoSub};
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            int num = dbHelper.getItemIntValue("num");
            if (num >= 1) {
                return true;
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return false;
    }

    private SettleQueueVo getVo(DbHelper dbHelper) throws SQLException {
        SettleQueueVo vo = new SettleQueueVo();
        vo.setBalanceWaterNo(dbHelper.getItemValue("balance_water_no"));
        vo.setBalanceWaterNoSub(dbHelper.getItemIntValue("balance_water_no_sub"));
        vo.setFinishFlag(dbHelper.getItemValue("finish_flag"));
        vo.setIsFinal(dbHelper.getItemValue("is_final"));
        vo.setErrorCode(dbHelper.getItemValue("error_code"));
        return vo;
    }

}
