/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.app.dao.ExportTccEntryStationAllMinDao;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;

import com.goldsign.settle.realtime.frame.util.FileUtil;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public abstract class ExportTccBaseDao {

    private static Logger logger = Logger.getLogger(ExportTccBaseDao.class.getName());

    protected static String FIELD_DELIM = ",";
    protected final static char[] CRLF_UNIX = {0x0a};//换行符

    public ResultFromProc getRecords(String balanceWaterNo, String proName) throws Exception {
        String sql = "{call " + FrameDBConstant.DB_ST + proName + "(?,?,?,?)} ";//存储过程调用语句
        DbHelper dbHelper = null;
        boolean result = false;
        Vector<String> records = new Vector();
        String record;
        ResultFromProc ret = null;

        int retCode;//存储过程参数返回的执行结果代码
        String retMsg;//存储过程参数返回的执行结果消息

        int[] pInIndexes = {1};//存储过程输入参数索引列表
        Object[] pInStmtValues = {balanceWaterNo};//存储过程输入参数值
        int[] pOutIndexes = {2, 3, 4};//存储过程输出参数索引列表
        int[] pOutTypes = {
            DbHelper.PARAM_OUT_TYPE_INTEGER,
            DbHelper.PARAM_OUT_TYPE_VACHAR,
            DbHelper.PARAM_OUT_TYPE_CURSOR
        };//存储过程输出参数值类型

        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            //dbHelper.commitTran();
/*
             * 取输出参数值
             */
            retCode = dbHelper.getOutParamIntValue(2);//获取返回代码输出参数值
            retMsg = dbHelper.getOutParamStringValue(3);//获取返回消息输出参数值

            /*
             * 取输出结果集
             */
            result = dbHelper.getFirstDocumentForOracle();//判断结果集是否为空
            while (result) {
                record = this.getRecord(dbHelper);
                records.add(record);
                result = dbHelper.getNextDocumentForOracle();//判断结果集是否存在下一记录
            }
            ret = new ResultFromProc(retCode, retMsg, records);

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return ret;
    }

    protected abstract String getRecord(DbHelper dbHelper) throws Exception;
    
    

}
