/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.dao;

import com.goldsign.fm.common.AppConstant;
import com.goldsign.fm.common.Encryption;
import com.goldsign.fm.common.PubUtil;
import com.goldsign.fm.vo.PassResult;
import com.goldsign.lib.db.util.DbHelper;
import org.apache.log4j.Logger;

/**
 * 密码
 * @author Administrator
 */
public class PassDao {

    private static Logger logger = Logger.getLogger(PassDao.class.getName());
    private static final String RESULT_SUCESS = "0";
    private static final String RESULT_FAILURE = "1";
    private static final String RESULT_FAILURE_WRONG = "2";

    public PassResult setPass(String passOrg, String passNew) throws Exception {
        DbHelper dbHelper = null;
        String strSql = "{call w_acc_mn.W_UP_FM_SET_PASS(?,?,?)}";
        Encryption en = new Encryption();
        passOrg = en.biEncrypt(Encryption.KEY, passOrg);
        passNew = en.biEncrypt(Encryption.KEY, passNew);

        PassResult ret = new PassResult();
        String sRet;
        

        try {
            dbHelper = new DbHelper("",AppConstant.dbcpHelper2.getConnection());
            
            int[] pInIndexes = {1,2};//存储过程输入参数索引列表
            Object[] pInStmtValues = {passOrg, passNew};//存储过程输入参数值

            int[] pOutIndexes = {3};//存储过程输出参数索引列表
            int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_VACHAR};//存储过程输出参数值类型

            dbHelper.runStoreProcForOracle(strSql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            sRet = dbHelper.getOutParamStringValue(3);//存储过程返回结果代码
            dbHelper.commitTran();
            
            if (sRet.equals(RESULT_SUCESS)) {
                ret.setIsSuccess(true);
                ret.setMsg("成功修改密码");
            }
            if (sRet.equals(RESULT_FAILURE)) {
                ret.setIsSuccess(false);
                ret.setMsg("原密码不对");
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return ret;


    }
    public PassResult checkPass(String passOrg) throws Exception {
        DbHelper dbHelper = null;
        String strSql = "{call w_acc_mn.W_UP_FM_EXIT_PASS(?,?)}";
        Encryption en = new Encryption();
        passOrg = en.biEncrypt(Encryption.KEY, passOrg);

        PassResult ret = new PassResult();
        String sRet;


        try {
            dbHelper = new DbHelper("",AppConstant.dbcpHelper2.getConnection());
            
            int[] pInIndexes = {1};//存储过程输入参数索引列表
            Object[] pInStmtValues = {passOrg};//存储过程输入参数值

            int[] pOutIndexes = {2};//存储过程输出参数索引列表
            int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_VACHAR};//存储过程输出参数值类型

            dbHelper.runStoreProcForOracle(strSql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            sRet = dbHelper.getOutParamStringValue(2);//存储过程返回结果代码
            
            if (sRet.equals(RESULT_SUCESS)) {
                ret.setIsSuccess(true);
                ret.setMsg("成功校验密码");
            }
            if (sRet.equals(RESULT_FAILURE)) {
                ret.setIsSuccess(false);
                ret.setMsg("密码不能为空，请修改密码");
            }
            if (sRet.equals(RESULT_FAILURE_WRONG)) {
                ret.setIsSuccess(false);
                ret.setMsg("密码不对");
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return ret;


    }
}
