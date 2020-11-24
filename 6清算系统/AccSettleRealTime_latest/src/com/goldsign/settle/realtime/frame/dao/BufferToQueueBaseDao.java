/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.app.dao.BufferToQueue50Dao;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public abstract class BufferToQueueBaseDao {
    private static Logger logger = Logger.getLogger(BufferToQueueBaseDao.class.getName());
    public abstract ResultFromProc  bufToQueue(String balanceWaterNo,int balanceWaterNoSub,String fileName) throws Exception;

    public ResultFromProc bufToQueueCommon(String balanceWaterNo, int balanceWaterNoSub,String fileName, String procName) throws Exception {
        String sql = "{call " + FrameDBConstant.DB_PRE+procName + "(?,?,?,?,?)}"; //存储过程调用语句
        DbHelper dbHelper = null;
        ResultFromProc result = null;
        String balWaterNo = "";
        int retCode; //存储过程参数返回的执行结果代码
        String retMsg; //存储过程参数返回的执行结果消息
        int[] pInIndexes = {1,2,3}; //存储过程输入参数索引列表
        Object[] pInStmtValues = {balanceWaterNo,balanceWaterNoSub,fileName}; //存储过程输入参数值
        int[] pOutIndexes = {4, 5}; //存储过程输出参数索引列表
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_INTEGER, DbHelper.PARAM_OUT_TYPE_VACHAR}; //存储过程输出参数值类型
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());          

            //dbHelper.runStoreProcForOracleAutoCommit(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes); //执行存储过程
             dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes); //执行存储过程

           
            
            /*
             * 取输出参数值
             */
            retCode = dbHelper.getOutParamIntValue(4); //获取返回代码输出参数值
            retMsg = dbHelper.getOutParamStringValue(5); //获取返回消息输出参数值
            logger.error("执行存储过程："+procName+" 返回代码："+retCode+" 返回消息："+retMsg);
            if(retCode !=0)
                logger.error("执行存储过程："+procName+"返回代码："+retCode+" 返回消息："+retMsg);
            result = new ResultFromProc(retCode, retMsg, "");
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return result;
    }
    
}
