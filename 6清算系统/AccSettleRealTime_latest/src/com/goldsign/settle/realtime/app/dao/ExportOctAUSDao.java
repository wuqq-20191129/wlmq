/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.app.vo.ExportDbResult;
import com.goldsign.settle.realtime.app.vo.FileRecordOctCJ;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.dao.ExportDaoBase;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import java.math.BigDecimal;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ExportOctAUSDao extends ExportDaoBase{
    private static Logger logger = Logger.getLogger(ExportOctAUSDao.class.getName());

    public ExportDbResult getRecords(String balanceWaterNo) throws Exception {

        String sql =  "{call up_st_exp_aus (?,?,?,?)}"; //存储过程调用语句
 /****************存储过程参数**********************/       
        String balWaterNo = "";
        int retCode; //存储过程参数返回的执行结果代码
        String retMsg; //存储过程参数返回的执行结果消息
        int[] pInIndexes = {1}; //存储过程输入参数索引列表
        Object[] pInStmtValues = {balanceWaterNo}; //存储过程输入参数值
        int[] pOutIndexes = {2,3, 4}; //存储过程输出参数索引列表
        int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_INTEGER, DbHelper.PARAM_OUT_TYPE_VACHAR,DbHelper.PARAM_OUT_TYPE_CURSOR}; //存储过程输出参数值类型
 /****************业务参数**********************/ 
        DbHelper dbHelper = null;
        boolean result = false;
        Vector<String[]> v = new Vector();
        ExportDbResult resultExp = new ExportDbResult();

        Object[] values = {balanceWaterNo};
        BigDecimal totalFee = new BigDecimal("0.00");;
        totalFee.setScale(2);
        String[] arr;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            dbHelper.runStoreProcForOracle(sql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes); //执行存储过程
             /*
             * 取输出参数值
             */
            retCode = dbHelper.getOutParamIntValue(2); //获取返回代码输出参数值
            retMsg = dbHelper.getOutParamStringValue(3); //获取返回消息输出参数值
            logger.info("返回代码："+retCode+" 返回消息："+retMsg);
            result = dbHelper.getFirstDocumentForOracle();//判断结果集是否为空           
            while (result) {
                arr = this.getArray(dbHelper);
                v.add(arr);
                result =dbHelper.getFirstDocumentForOracle();//判断结果集是否为空
            }
            resultExp.setTotalFee(totalFee);
            resultExp.setTotalNum(v.size());
            resultExp.setRecords(v);



        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return resultExp;


    }
    public ExportDbResult getRecordsForTest(String balanceWaterNo) throws Exception {

        String sql = "select FILE_NAME_ACC,FILE_STATUS  "
                + " from ST_EXT_MTR_AUDIT_STATUS where balance_water_no=? order by FILE_NAME_ACC";

        DbHelper dbHelper = null;
        boolean result = false;
       // Vector<ExportOctTRXVo> v = new Vector();
        Vector<String[]> v = new Vector();
        ExportDbResult resultExp = new ExportDbResult();

        Object[] values = {balanceWaterNo};
        BigDecimal totalFee = new BigDecimal("0.00");;
        totalFee.setScale(2);
        String[] arr;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                arr = this.getArray(dbHelper);
                //totalFee=this.addFee(totalFee, arr[3]);
                v.add(arr);


                result = dbHelper.getNextDocument();
            }
            resultExp.setTotalFee(totalFee);
            resultExp.setTotalNum(v.size());
            resultExp.setRecords(v);



        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return resultExp;


    }
    
    public int  insert(FileRecordOctCJ vo) throws Exception {

        String sql = "insert into ST_EXT_MTR_AUDIT_STATUS(WATER_NO,FILE_NAME_ACC,TOTAL_RECORD_NUM,TOTAL_RECORD_FEE,BALANCE_WATER_NO) "
                + "  values(SEQ_ST_EXT_MTR_AUDIT_STATUS.nextval,?,?,?,?)  ";
        DbHelper dbHelper = null;
        int n=0;

        Object[] values = {vo.getFileNameAcc(),vo.getTotalNum(),vo.getTotalFeeYuan(),vo.getBalanceWaterNo()};
       
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            n = dbHelper.executeUpdate(sql,values);
           

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
         return n;



    }

    private BigDecimal addFee(BigDecimal totalFee, String fee) {
       BigDecimal n= new BigDecimal(fee);
       n.setScale(2);
        return totalFee.add(n);
        

    }

    private String[] getArray(DbHelper dbHelper) throws Exception {
        String[] arr = new String[2];
        // FILE_NAME_ACC,FILE_STATUS
        arr[0] = dbHelper.getItemValue("FILE_NAME_ACC");//文件名
        arr[1] = dbHelper.getItemValue("FILE_STATUS");//文件采集状态
               
        
        

        return arr;

    }
    
    
}
