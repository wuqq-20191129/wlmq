/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.ConfigBcpVo;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class BalanceWaterNoDao {

    private static Logger logger = Logger.getLogger(BalanceWaterNoDao.class.getName());
    
     public ResultFromProc getBalanceWaterNoSub(String operId,String balanceWaterNo) throws Exception {
        return this.getBalanceWaterNoSubFromDb(operId,balanceWaterNo);
     }

    public ResultFromProc getBalanceWaterNo(String operId) throws Exception {
        return this.getBalanceWaterNoFromDb(operId);

        /**
         * 测试用
         */
        /*
         int n = 4;
         if (n == 1) {
         this.del_getBalanceWaterNoNoIn(operId);
         }
         if (n == 2) {
         this.del_getBalanceWaterNoNoOut(operId);
         }
         if (n == 3) {
         this.del_getBalanceWaterNoNoAll(operId);
         }
         if (n == 4) {
         this.del_getBalanceWaterNoByFun(operId);
         }
         return "";
         */
    }
    public ResultFromProc getBalanceWaterNoForClear(String operId) throws Exception {
        return this.getBalanceWaterNoFromDbForClear(operId);

    }

    public ResultFromProc getBalanceWaterNoFromDb(String operId) throws Exception {

       // String sql = "{call up_st_sys_get_bal_water(?,?,?,?)} ";//存储过程调用语句
         String sql = "{ call "+FrameDBConstant.DB_PRE+"up_st_sys_get_bal_water(?,?,?,?)} ";
        DbHelper dbHelper = null;
        ResultFromProc result =null;
        String balWaterNo = "";
        int retCode;//存储过程参数返回的执行结果代码
        String retMsg;//存储过程参数返回的执行结果消息

        int[] pInIndexes = {1};//存储过程输入参数索引列表
        Object[] pInStmtValues = {operId};//存储过程输入参数值
        int[] pOutIndexes = {2, 3, 4};//存储过程输出参数索引列表
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_INTEGER,
            DbHelper.PARAM_OUT_TYPE_VACHAR,
            DbHelper.PARAM_OUT_TYPE_VACHAR
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
            balWaterNo= dbHelper.getOutParamStringValue(4);//获取返回消息输出参数值
            result = new ResultFromProc(retCode,retMsg,balWaterNo);
/*
             * 取输出结果集
             */
            /*
            result = dbHelper.getFirstDocumentForOracle();//判断结果集是否为空
            while (result) {
                balWaterNo = dbHelper.getItemValue("bal_water_no");//
                result = dbHelper.getNextDocumentForOracle();//判断结果集是否存在下一记录
            }
*/

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return result;
    }
    //数据清理清算流水号
    public ResultFromProc getBalanceWaterNoFromDbForClear(String operId) throws Exception {

       // String sql = "{call up_st_sys_get_bal_water(?,?,?,?)} ";//存储过程调用语句
         String sql = "{ call "+FrameDBConstant.DB_PRE+"up_st_sys_get_bal_water_clr(?,?,?,?)} ";
        DbHelper dbHelper = null;
        ResultFromProc result =null;
        String balWaterNo = "";
        int retCode;//存储过程参数返回的执行结果代码
        String retMsg;//存储过程参数返回的执行结果消息

        int[] pInIndexes = {1};//存储过程输入参数索引列表
        Object[] pInStmtValues = {operId};//存储过程输入参数值
        int[] pOutIndexes = {2, 3, 4};//存储过程输出参数索引列表
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_INTEGER,
            DbHelper.PARAM_OUT_TYPE_VACHAR,
            DbHelper.PARAM_OUT_TYPE_VACHAR
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
            balWaterNo= dbHelper.getOutParamStringValue(4);//获取返回消息输出参数值
            result = new ResultFromProc(retCode,retMsg,balWaterNo);
/*
             * 取输出结果集
             */
            /*
            result = dbHelper.getFirstDocumentForOracle();//判断结果集是否为空
            while (result) {
                balWaterNo = dbHelper.getItemValue("bal_water_no");//
                result = dbHelper.getNextDocumentForOracle();//判断结果集是否存在下一记录
            }
*/

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return result;
    }
     public ResultFromProc getBalanceWaterNoSubFromDb(String operId,String balanceWaterNo) throws Exception {

       // String sql = "{call up_st_sys_get_bal_water(?,?,?,?)} ";//存储过程调用语句
         String sql = "{ call "+FrameDBConstant.DB_PRE+"up_st_sys_get_bal_water_sub(?,?,?,?,?)} ";
        DbHelper dbHelper = null;
        ResultFromProc result =null;
        int balWaterNoSub = 1;
        int retCode;//存储过程参数返回的执行结果代码
        String retMsg;//存储过程参数返回的执行结果消息

        int[] pInIndexes = {1,2};//存储过程输入参数索引列表
        Object[] pInStmtValues = {operId,balanceWaterNo};//存储过程输入参数值
        int[] pOutIndexes = {3, 4, 5};//存储过程输出参数索引列表
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
            retCode = dbHelper.getOutParamIntValue(3);//获取返回代码输出参数值
            retMsg = dbHelper.getOutParamStringValue(4);//获取返回消息输出参数值
            balWaterNoSub= dbHelper.getOutParamIntValue(5);//获取返回消息输出参数值
            result = new ResultFromProc(retCode,retMsg,balWaterNoSub);


        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return result;
    }

    public String del_getBalanceWaterNoByFun(String operId) throws Exception {

        String sql = "{?=call "+FrameDBConstant.DB_PRE+"uf_st_sys_get_bal_water(?,?,?)} ";
        DbHelper dbHelper = null;
        boolean result;
        String balWaterNo = "";
        String retCode;
        String retMsg;
        ResultSet rs;
        int[] pInIndexes = {2};
        Object[] pInStmtValues = {operId};
        int[] pOutIndexes = {1, 3, 4};
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_CURSOR,
            DbHelper.PARAM_OUT_TYPE_INTEGER,
            DbHelper.PARAM_OUT_TYPE_VACHAR,};


        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.runFunctionForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);

            retCode = dbHelper.getOutParamStringValue(3);
            retMsg = dbHelper.getOutParamStringValue(4);

            result = dbHelper.getFirstDocumentForOracle();
            while (result) {
                balWaterNo = dbHelper.getItemValue("balance_water_no");//
                result = dbHelper.getNextDocumentForOracle();
            }


        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return balWaterNo;
    }

    public String del_getBalanceWaterNoNoIn(String operId) throws Exception {

        String sql = "call "+FrameDBConstant.DB_PRE+"up_st_sys_get_bal_water_no_in(?,?,?) ";
        DbHelper dbHelper = null;
        boolean result;
        String balWaterNo = "";
        String retCode;
        String retMsg;
        ResultSet rs;
        int[] pInIndexes = null;//{1};
        Object[] pInStmtValues = null;//{operId};
        int[] pOutIndexes = {1, 2, 3};//{2, 3, 4};
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_INTEGER,
            DbHelper.PARAM_OUT_TYPE_VACHAR,
            DbHelper.PARAM_OUT_TYPE_CURSOR
        };


        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);

            retCode = dbHelper.getOutParamStringValue(1);
            retMsg = dbHelper.getOutParamStringValue(2);

            result = dbHelper.getFirstDocumentForOracle();
            while (result) {
                balWaterNo = dbHelper.getItemValue("balance_water_no");//
                result = dbHelper.getNextDocumentForOracle();
            }


        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return balWaterNo;
    }

    public String del_getBalanceWaterNoNoOut(String operId) throws Exception {

        String sql = "call "+FrameDBConstant.DB_PRE+"up_st_sys_get_bal_water_no_out(?) ";
        DbHelper dbHelper = null;
        boolean result;
        String balWaterNo = "";
        String retCode;
        String retMsg;
        ResultSet rs;
        int[] pInIndexes = {1};
        Object[] pInStmtValues = {operId};
        int[] pOutIndexes = null;// {2, 3, 4};
        int[] pOutTypes = null;/*{DbHelper.PARAM_OUT_TYPE_INTEGER,
         DbHelper.PARAM_OUT_TYPE_VACHAR,
         DbHelper.PARAM_OUT_TYPE_CURSOR
         };*/


        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);
            /*
             retCode = dbHelper.getOutParamStringValue(2);
             retMsg = dbHelper.getOutParamStringValue(3);

             result = dbHelper.getFirstDocumentForOracle();
             while (result) {
             balWaterNo = dbHelper.getItemValue("balance_water_no");//
             result = dbHelper.getNextDocumentForOracle();
             }
             */

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return balWaterNo;
    }

    public String del_getBalanceWaterNoNoAll(String operId) throws Exception {

        String sql = "call "+FrameDBConstant.DB_PRE+"up_st_sys_get_bal_water_no_all() ";
        DbHelper dbHelper = null;
        boolean result;
        String balWaterNo = "";
        String retCode;
        String retMsg;
        ResultSet rs;
        int[] pInIndexes = null;// {1};
        Object[] pInStmtValues = null;//{operId};
        int[] pOutIndexes = null;//{2, 3, 4};
        int[] pOutTypes = null;/*{DbHelper.PARAM_OUT_TYPE_INTEGER,
         DbHelper.PARAM_OUT_TYPE_VACHAR,
         DbHelper.PARAM_OUT_TYPE_CURSOR
         };*/


        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);
            /*
             retCode = dbHelper.getOutParamStringValue(2);
             retMsg = dbHelper.getOutParamStringValue(3);

             result = dbHelper.getFirstDocumentForOracle();
             while (result) {
             balWaterNo = dbHelper.getItemValue("balance_water_no");//
             result = dbHelper.getNextDocumentForOracle();
             }
             */

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return balWaterNo;
    }

    public String del_getBalanceWaterNo(String operId) throws Exception {

        String sql = "call "+FrameDBConstant.DB_PRE+"up_st_sys_get_bal_water(?,?,?,?) ";
        DbHelper dbHelper = null;
        boolean result;
        String balWaterNo = "";
        String retCode;
        String retMsg;
        ResultSet rs;

        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            CallableStatement stmt = dbHelper.getCallabelStatement(sql);
            stmt.setString(1, operId);
            stmt.registerOutParameter(2, java.sql.Types.INTEGER);
            stmt.registerOutParameter(3, java.sql.Types.CHAR);
            stmt.registerOutParameter(4, oracle.jdbc.OracleTypes.CURSOR);
            stmt.executeUpdate();
            retCode = new Integer(stmt.getInt(2)).toString();
            retMsg = stmt.getString(3);
            rs = (ResultSet) stmt.getObject(4);
            if (rs.next()) {
                balWaterNo = rs.getString("bal_water_no");
            }

            //result = dbHelper.getFirstDocument(sql);

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return balWaterNo;
    }
}
