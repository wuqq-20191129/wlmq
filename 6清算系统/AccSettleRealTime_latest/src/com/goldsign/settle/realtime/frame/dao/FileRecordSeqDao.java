/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.ConfigRecordFmtVo;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import java.util.HashMap;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FileRecordSeqDao {

    private static Logger logger = Logger.getLogger(FileRecordSeqDao.class.getName());

    public ResultFromProc getFileRecordSeqs(String trdTypeId, int seqCount) throws Exception {

        String sql = "{call "+FrameDBConstant.DB_PRE+"up_st_cfg_getRecordSeqs(?,?,?,?,?)} ";//存储过程调用语句
        DbHelper dbHelper = null;
        boolean result = false;
        Vector<String> seqs = new Vector();
        String seq;
        ResultFromProc ret = null;

        int retCode;//存储过程参数返回的执行结果代码
        String retMsg;//存储过程参数返回的执行结果消息


        int[] pInIndexes = {1, 2};//存储过程输入参数索引列表
        Object[] pInStmtValues = {trdTypeId, new Integer(seqCount)};//存储过程输入参数值
        int[] pOutIndexes = {3, 4, 5};//存储过程输出参数索引列表
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_CURSOR,
            DbHelper.PARAM_OUT_TYPE_INTEGER,
            DbHelper.PARAM_OUT_TYPE_VACHAR
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

            /*
             * 取输出结果集
             */

            result = dbHelper.getFirstDocumentForOracle();//判断结果集是否为空
            while (result) {
                seq = dbHelper.getItemValue("seq");//
                seqs.add(seq);
                result = dbHelper.getNextDocumentForOracle();//判断结果集是否存在下一记录
            }
            ret = new ResultFromProc(retCode, retMsg, seqs);



        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return ret;
    }
}
