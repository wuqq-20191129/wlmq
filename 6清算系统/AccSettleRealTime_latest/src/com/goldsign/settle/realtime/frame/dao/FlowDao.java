/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;

import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFlowConstant;

import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FlowDao {

    private static int RET_CODE_SUCESS = 0;
    private static Vector<String> STEPS_LISTED = new Vector();
    private static Vector<String> STEPS_LISTED_DETAIL = new Vector();

    private static Logger logger = Logger.getLogger(FlowDao.class.getName());

    public static int updateFinishFlag(String balanceWaterNo, String step, String flag, String errorCode) throws Exception {

        String sql = "update " + FrameDBConstant.DB_PRE + "st_sys_flow_current set FINISH_FLAG=? ,END_TIME=sysdate,ERROR_CODE=?  where BALANCE_WATER_NO=? and STEP=?";

        DbHelper dbHelper = null;
        logger.info(sql);
        int result = 0;
        Object[] values = {flag, errorCode, balanceWaterNo, step
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

    public static int updateFinishFlagForManu(String balanceWaterNo, String flag) throws Exception {

        String sql = "update " + FrameDBConstant.DB_PRE + "st_sys_flow_manu set FINISH_FLAG=?   where BALANCE_WATER_NO=? ";

        DbHelper dbHelper = null;
        logger.info(sql);
        int result = 0;
        Object[] values = {flag, balanceWaterNo
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

    public static int updateFinishFlagForPreSettleOnly(String balanceWaterNo, String[] steps) throws Exception {
        /*
        int n =0;
        for(String step:steps){
            n +=updateFinishFlag(balanceWaterNo,step,FrameFlowConstant.FLAG_FINISH, FrameFlowConstant.FLAG_OK);
        }
        return n;
         */

        String sql = "update " + FrameDBConstant.DB_PRE + "st_sys_flow_current set FINISH_FLAG=? ,END_TIME=sysdate,ERROR_CODE=?  "
                + "where BALANCE_WATER_NO=? and STEP in(" + FlowDao.getInValue(steps)
                + " )";

        DbHelper dbHelper = null;
        logger.info(sql);
        int result = 0;
        Object[] values = {FrameFlowConstant.FLAG_FINISH, FrameFlowConstant.FLAG_OK, balanceWaterNo
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

    public static int updateFinishFlagForStart(String balanceWaterNo, String step) throws Exception {

        String sql = "update " + FrameDBConstant.DB_PRE + "st_sys_flow_current set begin_TIME=sysdate  where BALANCE_WATER_NO=? and STEP=?";

        DbHelper dbHelper = null;
        logger.info(sql);
        int result = 0;
        Object[] values = {balanceWaterNo, step
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

    public static boolean isUnFinishedStep(String balanceWaterNo, String step) throws Exception {

        String sql = "select count(*) num from " + FrameDBConstant.DB_PRE + "st_sys_flow_current where BALANCE_WATER_NO=? and STEP=? and FINISH_FLAG=?";

        DbHelper dbHelper = null;
        //logger.info(sql);
        boolean result;
        int num;
        Object[] values = {balanceWaterNo, step, FrameFlowConstant.FLAG_FINISH_NOT
        };
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            if (result) {
                num = dbHelper.getItemIntValue("num");
                if (num == 1) {
                    return true;
                }
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return false;

    }

    public static void clearStepListed() {
        STEPS_LISTED.clear();
    }

    public static void clearStepListedForDetail() {
        STEPS_LISTED_DETAIL.clear();
    }

    public static Vector<String> getFinishedStepForSettle(String balanceWaterNo, String[] steps) throws Exception {
        String sql = "select step from " + FrameDBConstant.DB_PRE + "st_sys_flow_current where BALANCE_WATER_NO=? and FINISH_FLAG=? and STEP in ("
                + getInValue(steps)
                + ") order by step ";

        DbHelper dbHelper = null;
        //logger.info(sql);
        boolean result;
        String step;
        Vector<String> stepsList = new Vector();
        Object[] values = {balanceWaterNo, FrameFlowConstant.FLAG_FINISH
        };
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                step = dbHelper.getItemValue("step");
                if (!isListed(step)) {
                    stepsList.add(step);
                }
                result = dbHelper.getNextDocument();

            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return stepsList;

    }

    public static Vector<String> getFinishedStepForSettleDetail(String balanceWaterNo, String[] steps) throws Exception {
        String sql = "select step from " + FrameDBConstant.DB_PRE + "st_sys_flow_current_dtl where BALANCE_WATER_NO=? and FINISH_FLAG=? and STEP in ("
                + getInValue(steps)
                + ") order by step ";

        DbHelper dbHelper = null;
        //logger.info(sql);
        boolean result;
        String step;
        Vector<String> stepsList = new Vector();
        Object[] values = {balanceWaterNo, FrameFlowConstant.FLAG_FINISH
        };
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                step = dbHelper.getItemValue("step");
                if (!isListedForDetail(step)) {
                    stepsList.add(step);
                }
                result = dbHelper.getNextDocument();

            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return stepsList;

    }

    private static boolean isListed(String step) {
        if (STEPS_LISTED.contains(step)) {
            return true;
        }
        STEPS_LISTED.add(step);
        return false;
    }

    private static boolean isListedForDetail(String step) {
        if (STEPS_LISTED_DETAIL.contains(step)) {
            return true;
        }
        STEPS_LISTED_DETAIL.add(step);
        return false;
    }

    private static String getInValue(String[] strs) {
        String str = "";
        for (int i = 0; i < strs.length; i++) {
            str += "'" + strs[i] + "'" + ",";
        }
        return str.substring(0, str.length() - 1);
    }

    public static boolean isUnFinishedSteps(String balanceWaterNo, String[] steps) throws Exception {

        String sql = "select count(*) num from " + FrameDBConstant.DB_PRE + "st_sys_flow_current where BALANCE_WATER_NO=? and FINISH_FLAG=? and STEP in ("
                + getInValue(steps)
                + ") ";

        DbHelper dbHelper = null;
        // logger.info(sql);
        boolean result;
        int num;
        Object[] values = {balanceWaterNo, FrameFlowConstant.FLAG_FINISH_NOT
        };
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            if (result) {
                num = dbHelper.getItemIntValue("num");
                if (num >= 1) {
                    return true;
                }
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return false;

    }

    public static boolean isUnFinishedStepsForDetail(String balanceWaterNo, String[] steps) throws Exception {

        String sql = "select count(*) num from " + FrameDBConstant.DB_PRE + "st_sys_flow_current_dtl where BALANCE_WATER_NO=? and FINISH_FLAG=? and STEP in ("
                + getInValue(steps)
                + ") ";

        DbHelper dbHelper = null;
        // logger.info(sql);
        boolean result;
        int num;
        Object[] values = {balanceWaterNo, FrameFlowConstant.FLAG_FINISH_NOT
        };
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            if (result) {
                num = dbHelper.getItemIntValue("num");
                if (num >= 1) {
                    return true;
                }
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return false;

    }

    public ResultFromProc preSettleBatch(String balanceWaterNo, int balanceWaterNoSub) throws Exception {

        String sql = "{ call " + FrameDBConstant.DB_PRE + "up_st_sys_pre_settle_batch(?,?,?,?)} ";
        DbHelper dbHelper = null;
        ResultFromProc result = null;
        int retCode;//存储过程参数返回的执行结果代码
        String retMsg;//存储过程参数返回的执行结果消息

        int[] pInIndexes = {1, 2};////存储过程输入参数索引列表
        Object[] pInStmtValues = {balanceWaterNo, balanceWaterNoSub};////存储过程输入参数值
        int[] pOutIndexes = {3, 4};//存储过程输出参数索引列表
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_INTEGER,
            DbHelper.PARAM_OUT_TYPE_VACHAR,};//存储过程输出参数值类型

        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            //dbHelper.commitTran();
/*
             * 取输出参数值
             */
            retCode = dbHelper.getOutParamIntValue(3);//获取返回代码输出参数值
            retMsg = dbHelper.getOutParamStringValue(4);//获取返回消息输出参数值
            result = new ResultFromProc(retCode, retMsg, balanceWaterNo);
            if (retCode != RET_CODE_SUCESS) {
                throw new Exception("实时结算初始化存在错误，错误代码为：" + retCode + " 错误消息：" + retMsg);
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return result;
    }
}
