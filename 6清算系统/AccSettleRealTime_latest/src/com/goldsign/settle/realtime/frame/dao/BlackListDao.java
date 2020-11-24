/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class BlackListDao {
    private static Logger logger = Logger.getLogger(BlackListDao.class.getName());

    public ResultFromProc downloadBlackList(String balanceWaterNo) throws Exception {

        String sql = "{ call "+FrameDBConstant.DB_PRE+"up_st_dwn_blacklist(?,?,?)} ";
        DbHelper dbHelper = null;
        ResultFromProc result = null;
        int retCode;//存储过程参数返回的执行结果代码
        String retMsg;//存储过程参数返回的执行结果消息

        int[] pInIndexes = {1};//存储过程输入参数索引列表
        Object[] pInStmtValues = {balanceWaterNo};//存储过程输入参数值
        int[] pOutIndexes = {2, 3};//存储过程输出参数索引列表
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_INTEGER,
            DbHelper.PARAM_OUT_TYPE_VACHAR,};//存储过程输出参数值类型


        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            //dbHelper.commitTran();
/*
             * 取输出参数值
             */
            retCode = dbHelper.getOutParamIntValue(2);//获取返回代码输出参数值
            retMsg = dbHelper.getOutParamStringValue(3);//获取返回消息输出参数值
            result = new ResultFromProc(retCode, retMsg, balanceWaterNo);


        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return result;
    }
    
     public ResultFromProc recordBlackListUpload(String balanceWaterNo) throws Exception {

        String sql = "{ call "+FrameDBConstant.DB_PRE+"up_st_ext_blacklist_total(?,?,?)} ";
        DbHelper dbHelper = null;
        ResultFromProc result = null;
        int retCode;//存储过程参数返回的执行结果代码
        String retMsg;//存储过程参数返回的执行结果消息

        int[] pInIndexes = {1};//存储过程输入参数索引列表
        Object[] pInStmtValues = {balanceWaterNo};//存储过程输入参数值
        int[] pOutIndexes = {2, 3};//存储过程输出参数索引列表
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_INTEGER,
            DbHelper.PARAM_OUT_TYPE_VACHAR,};//存储过程输出参数值类型


        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            //dbHelper.commitTran();
/*
             * 取输出参数值
             */
            retCode = dbHelper.getOutParamIntValue(2);//获取返回代码输出参数值
            retMsg = dbHelper.getOutParamStringValue(3);//获取返回消息输出参数值
            result = new ResultFromProc(retCode, retMsg, balanceWaterNo);


        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return result;
    }
}
