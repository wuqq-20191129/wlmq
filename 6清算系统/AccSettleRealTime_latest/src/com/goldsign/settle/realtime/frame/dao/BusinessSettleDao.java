/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import com.goldsign.settle.realtime.frame.vo.SettleQueueVo;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class BusinessSettleDao {

    private static int RET_CODE_SUCESS = 0;

    private static Logger logger = Logger.getLogger(BusinessSettleDao.class.getName());

    public ResultFromProc businessSettle(SettleQueueVo queMsg) throws Exception {
        //未正式升级结算
        //return this.businessSettleNotUsingBalanceWaterNo(balanceWaterNo);
        //正式升级结算
        // return this.businessSettleUsingBalanceWaterNoTest(queMsg.getBalanceWaterNo(),queMsg.getBalanceWaterNoSub(),queMsg.getIsFinal());
        return this.businessSettleUsingBalanceWaterNo(queMsg.getBalanceWaterNo(), queMsg.getBalanceWaterNoSub(), queMsg.getIsFinal());
    }

    public ResultFromProc businessSettleOct(SettleQueueVo queMsg) throws Exception {
        //未正式升级结算
        //return this.businessSettleNotUsingBalanceWaterNo(balanceWaterNo);
        //正式升级结算
       // return this.businessSettleUsingBalanceWaterNoOctTest(queMsg.getBalanceWaterNo(), queMsg.getBalanceWaterNoSub(), queMsg.getIsFinal());
        return this.businessSettleUsingBalanceWaterNoOct(queMsg.getBalanceWaterNo(), queMsg.getBalanceWaterNoSub(), queMsg.getIsFinal());
        //return this.businessSettleUsingBalanceWaterNo(queMsg.getBalanceWaterNo(),queMsg.getBalanceWaterNoSub(),queMsg.getIsFinal());
    }

    public ResultFromProc businessSettleNotUsingBalanceWaterNo(String balanceWaterNo, int balanceWaterNoSub, String isFinal) throws Exception {

        String sql = "{ call " + FrameDBConstant.DB_PRE + "UP_ST_IST_SETTLE_TOTAL_TST(?,?,?)} ";
        DbHelper dbHelper = null;
        ResultFromProc result = null;
        int retCode;//存储过程参数返回的执行结果代码
        String retMsg;//存储过程参数返回的执行结果消息

        int[] pInIndexes = null;////存储过程输入参数索引列表
        Object[] pInStmtValues = null;////存储过程输入参数值
        int[] pOutIndexes = {1, 2};//存储过程输出参数索引列表
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_INTEGER,
            DbHelper.PARAM_OUT_TYPE_VACHAR,};//存储过程输出参数值类型

        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            //dbHelper.commitTran();
/*
             * 取输出参数值
             */
            retCode = dbHelper.getOutParamIntValue(1);//获取返回代码输出参数值
            retMsg = dbHelper.getOutParamStringValue(2);//获取返回消息输出参数值
            result = new ResultFromProc(retCode, retMsg, balanceWaterNo);
            if (retCode != RET_CODE_SUCESS) {
                throw new Exception("整个结算过程中存在错误，错误代码为：" + retCode + " 错误消息：" + retMsg);
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return result;
    }
        public ResultFromProc businessSettleUsingBalanceWaterNoOct(String balanceWaterNo, int balanceWaterNoSub, String isFinal) throws Exception {

        String sql = "{ call " + FrameDBConstant.DB_PRE + "UP_ST_IST_SETTLE_OCT(?,?,?,?,?)} ";
        DbHelper dbHelper = null;
        ResultFromProc result = null;
        int retCode;//存储过程参数返回的执行结果代码
        String retMsg;//存储过程参数返回的执行结果消息

        int[] pInIndexes = {3, 4, 5};////存储过程输入参数索引列表
        Object[] pInStmtValues = {balanceWaterNo, balanceWaterNoSub, isFinal};////存储过程输入参数值
        int[] pOutIndexes = {1, 2};//存储过程输出参数索引列表
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_INTEGER,
            DbHelper.PARAM_OUT_TYPE_VACHAR,};//存储过程输出参数值类型

        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            //dbHelper.commitTran();
/*
             * 取输出参数值
             */
            retCode = dbHelper.getOutParamIntValue(1);//获取返回代码输出参数值
            retMsg = dbHelper.getOutParamStringValue(2);//获取返回消息输出参数值
            result = new ResultFromProc(retCode, retMsg, balanceWaterNo);
            if (retCode != RET_CODE_SUCESS) {
                throw new Exception("OCT返回结果结算过程中存在错误，错误代码为：" + retCode + " 错误消息：" + retMsg);
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return result;
    }

    public ResultFromProc businessSettleUsingBalanceWaterNoOctTest(String balanceWaterNo, int balanceWaterNoSub, String isFinal) throws Exception {

        String sql = "{ call " + FrameDBConstant.DB_PRE + "UP_ST_IST_SETTLE_OCT_TST(?,?,?,?,?)} ";
        DbHelper dbHelper = null;
        ResultFromProc result = null;
        int retCode;//存储过程参数返回的执行结果代码
        String retMsg;//存储过程参数返回的执行结果消息

        int[] pInIndexes = {3, 4, 5};////存储过程输入参数索引列表
        Object[] pInStmtValues = {balanceWaterNo, balanceWaterNoSub, isFinal};////存储过程输入参数值
        int[] pOutIndexes = {1, 2};//存储过程输出参数索引列表
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_INTEGER,
            DbHelper.PARAM_OUT_TYPE_VACHAR,};//存储过程输出参数值类型

        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            //dbHelper.commitTran();
/*
             * 取输出参数值
             */
            retCode = dbHelper.getOutParamIntValue(1);//获取返回代码输出参数值
            retMsg = dbHelper.getOutParamStringValue(2);//获取返回消息输出参数值
            result = new ResultFromProc(retCode, retMsg, balanceWaterNo);
            if (retCode != RET_CODE_SUCESS) {
                throw new Exception("OCT返回结果结算过程中存在错误，错误代码为：" + retCode + " 错误消息：" + retMsg);
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return result;
    }

    public ResultFromProc businessSettleUsingBalanceWaterNoTest(String balanceWaterNo, int balanceWaterNoSub, String isFinal) throws Exception {

        String sql = "{ call " + FrameDBConstant.DB_PRE + "UP_ST_IST_SETTLE_TOTAL_TST(?,?,?,?,?)} ";
        DbHelper dbHelper = null;
        ResultFromProc result = null;
        int retCode;//存储过程参数返回的执行结果代码
        String retMsg;//存储过程参数返回的执行结果消息

        int[] pInIndexes = {3, 4, 5};////存储过程输入参数索引列表
        Object[] pInStmtValues = {balanceWaterNo, balanceWaterNoSub, isFinal};////存储过程输入参数值
        int[] pOutIndexes = {1, 2};//存储过程输出参数索引列表
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_INTEGER,
            DbHelper.PARAM_OUT_TYPE_VACHAR,};//存储过程输出参数值类型

        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            //dbHelper.commitTran();
/*
             * 取输出参数值
             */
            retCode = dbHelper.getOutParamIntValue(1);//获取返回代码输出参数值
            retMsg = dbHelper.getOutParamStringValue(2);//获取返回消息输出参数值
            result = new ResultFromProc(retCode, retMsg, balanceWaterNo);
            if (retCode != RET_CODE_SUCESS) {
                throw new Exception("整个结算过程中存在错误，错误代码为：" + retCode + " 错误消息：" + retMsg);
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return result;
    }

    public ResultFromProc businessSettleUsingBalanceWaterNo(String balanceWaterNo, int balanceWaterNoSub, String isFinal) throws Exception {

        String sql = "{ call " + FrameDBConstant.DB_PRE + "UP_ST_IST_SETTLE_TOTAL(?,?,?,?,?)} ";
        DbHelper dbHelper = null;
        ResultFromProc result = null;
        int retCode;//存储过程参数返回的执行结果代码
        String retMsg;//存储过程参数返回的执行结果消息

        int[] pInIndexes = {3, 4, 5};////存储过程输入参数索引列表
        Object[] pInStmtValues = {balanceWaterNo, balanceWaterNoSub, isFinal};////存储过程输入参数值
        int[] pOutIndexes = {1, 2};//存储过程输出参数索引列表
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_INTEGER,
            DbHelper.PARAM_OUT_TYPE_VACHAR,};//存储过程输出参数值类型

        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            //dbHelper.commitTran();
/*
             * 取输出参数值
             */
            retCode = dbHelper.getOutParamIntValue(1);//获取返回代码输出参数值
            retMsg = dbHelper.getOutParamStringValue(2);//获取返回消息输出参数值
            result = new ResultFromProc(retCode, retMsg, balanceWaterNo);
            if (retCode != RET_CODE_SUCESS) {
                throw new Exception("整个结算过程中存在错误，错误代码为：" + retCode + " 错误消息：" + retMsg);
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return result;
    }

    public boolean canSettleBatch(String balanceWaterNo, int balanceWaterNoSub, String finalSettleFlag) throws Exception {

        // String sql = "{call up_st_sys_get_bal_water(?,?,?,?)} ";//存储过程调用语句
        String sql = "{ call " + FrameDBConstant.DB_PRE + "up_st_sys_is_settle_batch(?,?,?,?,?,?)} ";
        DbHelper dbHelper = null;
        int canSettle = 0;
        int retCode;//存储过程参数返回的执行结果代码
        String retMsg;//存储过程参数返回的执行结果消息

        int[] pInIndexes = {1, 2, 3};//存储过程输入参数索引列表
        Object[] pInStmtValues = {balanceWaterNo, balanceWaterNoSub, finalSettleFlag};//存储过程输入参数值
        int[] pOutIndexes = {4, 5, 6};//存储过程输出参数索引列表
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_INTEGER,
            DbHelper.PARAM_OUT_TYPE_VACHAR,
            DbHelper.PARAM_OUT_TYPE_INTEGER
        };//存储过程输出参数值类型

        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            //dbHelper.commitTran();
/*
             * 取输出参数值
             */
            retCode = dbHelper.getOutParamIntValue(4);//获取返回代码输出参数值
            retMsg = dbHelper.getOutParamStringValue(5);//获取返回消息输出参数值
            canSettle = dbHelper.getOutParamIntValue(6);//获取返回消息输出参数值
            if (retCode == 0 && canSettle == 1) {
                return true;
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return false;
    }

}
