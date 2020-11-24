/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.FileMobileSDFVo;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FileMobileTrxDao {
    

    private static Logger logger = Logger.getLogger(FileMobileTrxDao.class.getName());

    public Hashtable<String, Vector> getRecordsForAudit(String balanceWaterNo) throws Exception {
        Hashtable<String, Vector> fileDatas = new Hashtable();
        this.getRecordsForAuditFromLine(balanceWaterNo, fileDatas);//线路数据
      //  this.getRecordsForAuditFromBus(balanceWaterNo, fileDatas);//公交消费数据
        return fileDatas;


    }
     public Hashtable<String, Vector> getRecordsForAudit80(String balanceWaterNo) throws Exception {
        Hashtable<String, Vector> fileDatas = new Hashtable();
        //更新导出标识为正在导出
        this.getRecordsForAuditFromLine80(balanceWaterNo, fileDatas);//线路数据
      //  this.getRecordsForAuditFromBus(balanceWaterNo, fileDatas);//公交消费数据
        return fileDatas;


    }
     public Hashtable<String, Vector> getRecordsForAudit81(String balanceWaterNo) throws Exception {
        Hashtable<String, Vector> fileDatas = new Hashtable();
        this.getRecordsForAuditFromLine81(balanceWaterNo, fileDatas);//线路数据
      //  this.getRecordsForAuditFromBus(balanceWaterNo, fileDatas);//公交消费数据
        return fileDatas;


    }

    public Hashtable<String, Vector> getRecordsForAuditFromLine(String balanceWaterNo, Hashtable<String, Vector> fileDatas) throws Exception {

        String sql = "select SAM_TRADE_SEQ,CARD_MAIN_ID,CARD_SUB_ID,CARD_LOGICAL_ID,CARD_PHYSICAL_ID,"
                + "SAM_LOGICAL_ID,PAY_MODE_ID,DEAL_FEE,DEAL_BALANCE_FEE+DEAL_FEE DEAL_BALANCE_FEE,to_char(DEAL_DATETIME,'yyyymmddhh24miss') DEAL_DATETIME,LAST_SAM_LOGICAL_ID,"
                + "to_char(LAST_DEAL_DATETIME,'yyyymmddhh24miss') LAST_DEAL_DATETIME,ENTRY_SAM_LOGICAL_ID,to_char(ENTRY_DATETIME,'yyyymmddhh24miss') ENTRY_DATETIME,CARD_CONSUME_SEQ,TAC,"
                + "CITY_CODE,BUSINESS_CODE,TAC_DEAL_TYPE,TAC_DEV_ID "
                + " from "+FrameDBConstant.DB_PRE+"ST_MB_EXT_QUE_DEAL_LINE where balance_water_no=? order by DEAL_DATETIME";
        DbHelper dbHelper = null;
        boolean result = false;


        Object[] values = {balanceWaterNo};
        FileMobileSDFVo vo;
        String[] lineData;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                lineData = this.getArray(dbHelper);
                this.addRecord(fileDatas, FrameCodeConstant.LINE_ID_MOBILE, lineData);

                result = dbHelper.getNextDocument();
            }


        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return fileDatas;


    }
    public Hashtable<String, Vector> getRecordsForAuditFromLine80(String balanceWaterNo, Hashtable<String, Vector> fileDatas) throws Exception {

        String sql = "select SAM_TRADE_SEQ,CARD_MAIN_ID,CARD_SUB_ID,CARD_LOGICAL_ID,CARD_PHYSICAL_ID,"
                + "SAM_LOGICAL_ID,PAY_MODE_ID,DEAL_FEE,DEAL_BALANCE_FEE+DEAL_FEE DEAL_BALANCE_FEE,to_char(DEAL_DATETIME,'yyyymmddhh24miss') DEAL_DATETIME,LAST_SAM_LOGICAL_ID,"
                + "to_char(LAST_DEAL_DATETIME,'yyyymmddhh24miss') LAST_DEAL_DATETIME,ENTRY_SAM_LOGICAL_ID,to_char(ENTRY_DATETIME,'yyyymmddhh24miss') ENTRY_DATETIME,CARD_CONSUME_SEQ,TAC,"
                + "CITY_CODE,BUSINESS_CODE,TAC_DEAL_TYPE,TAC_DEV_ID "
                + " from "+FrameDBConstant.DB_PRE+"ST_MB_EXT_QUE_DEAL_LINE "
                + "where balance_water_no=?  and app_platform_flag=?  and export_flag=? order by DEAL_DATETIME";
        DbHelper dbHelper = null;
        boolean result = false;


        Object[] values = {balanceWaterNo,FrameCodeConstant.APP_PLATFORM_METRO,FrameCodeConstant.EXPORT_FLAG_DOING};//增加导出标识
        FileMobileSDFVo vo;
        String[] lineData;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                lineData = this.getArray(dbHelper);
                this.addRecord(fileDatas, FrameCodeConstant.LINE_ID_MOBILE, lineData);

                result = dbHelper.getNextDocument();
            }


        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return fileDatas;


    }
    public ResultFromProc getFileSeqFromDb(String balanceWaterNo,String appPlatformFlag) throws Exception {

       // String sql = "{call up_st_sys_get_bal_water(?,?,?,?)} ";//存储过程调用语句
         String sql = "{ call "+FrameDBConstant.DB_ST+"w_up_st_mb_trx_seq(?,?,?,?,?)} ";
        DbHelper dbHelper = null;
        ResultFromProc result =null;
        String fileSeq = "";
        int retCode;//存储过程参数返回的执行结果代码
        String retMsg;//存储过程参数返回的执行结果消息

        int[] pInIndexes = {1,2};//存储过程输入参数索引列表
        Object[] pInStmtValues = {balanceWaterNo,appPlatformFlag};//存储过程输入参数值
        int[] pOutIndexes = {3, 4, 5};//存储过程输出参数索引列表
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
            retCode = dbHelper.getOutParamIntValue(3);//获取返回代码输出参数值
            retMsg = dbHelper.getOutParamStringValue(4);//获取返回消息输出参数值
            fileSeq= dbHelper.getOutParamStringValue(5);//获取返回消息输出参数值
            result = new ResultFromProc(retCode,retMsg,fileSeq);
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
    public String getFileSeq( String balanceWaterNo, String appPlatformFlag) throws Exception{
        ResultFromProc result = this.getFileSeqFromDb(balanceWaterNo, appPlatformFlag);
        if(result.getRetCode() !=0){
            logger.error("清算流水号"+balanceWaterNo+" 平台"+appPlatformFlag+" 获取文件序号错误："+result.getRetMsg());
            throw new Exception("清算流水号"+balanceWaterNo+" 平台"+appPlatformFlag+" 获取文件序号错误："+result.getRetMsg());
        }
        return result.getRetValue();
    }
    public int updateExportFlag(String balanceWaterNo, String appPlatformFlag,String exportFlagNew,String exportFlagOld) throws Exception {
        String sql = "update " +FrameDBConstant.DB_ST
                + "W_ST_MB_EXT_QUE_DEAL_LINE set export_flag=? where balance_water_no=?  and app_platform_flag=? and export_flag=?";

        DbHelper dbHelper = null;
        int result = 0;


        Object[] values = {exportFlagNew,balanceWaterNo,appPlatformFlag,exportFlagOld};

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
    
    public Hashtable<String, Vector> getRecordsForAuditFromLine81(String balanceWaterNo, Hashtable<String, Vector> fileDatas) throws Exception {

        String sql = "select SAM_TRADE_SEQ,CARD_MAIN_ID,CARD_SUB_ID,CARD_LOGICAL_ID,CARD_PHYSICAL_ID,"
                + "SAM_LOGICAL_ID,PAY_MODE_ID,DEAL_FEE,DEAL_BALANCE_FEE+DEAL_FEE DEAL_BALANCE_FEE,to_char(DEAL_DATETIME,'yyyymmddhh24miss') DEAL_DATETIME,LAST_SAM_LOGICAL_ID,"
                + "to_char(LAST_DEAL_DATETIME,'yyyymmddhh24miss') LAST_DEAL_DATETIME,ENTRY_SAM_LOGICAL_ID,to_char(ENTRY_DATETIME,'yyyymmddhh24miss') ENTRY_DATETIME,CARD_CONSUME_SEQ,TAC,"
                + "CITY_CODE,BUSINESS_CODE,TAC_DEAL_TYPE,TAC_DEV_ID "
                + " from "+FrameDBConstant.DB_PRE+"ST_MB_EXT_QUE_DEAL_LINE "
                + "where balance_water_no=?  and app_platform_flag=? and export_flag=? order by DEAL_DATETIME";
        DbHelper dbHelper = null;
        boolean result = false;


        Object[] values = {balanceWaterNo,FrameCodeConstant.APP_PLATFORM_BANK,FrameCodeConstant.EXPORT_FLAG_DOING};
        FileMobileSDFVo vo;
        String[] lineData;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                lineData = this.getArray(dbHelper);
                this.addRecord(fileDatas, FrameCodeConstant.LINE_ID_MOBILE, lineData);

                result = dbHelper.getNextDocument();
            }


        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return fileDatas;


    }

    public Hashtable<String, Vector> getRecordsForAuditFromBus(String balanceWaterNo, Hashtable<String, Vector> fileDatas) throws Exception {

        String sql = "select SAM_TRADE_SEQ,CARD_MAIN_ID,CARD_SUB_ID,CARD_LOGICAL_ID,CARD_PHYSICAL_ID,"
                + "SAM_LOGICAL_ID,PAY_MODE_ID,DEAL_FEE,DEAL_BALANCE_FEE+DEAL_FEE DEAL_BALANCE_FEE,to_char(DEAL_DATETIME,'yyyymmddhh24miss') DEAL_DATETIME,LAST_SAM_LOGICAL_ID,"
                + "to_char(LAST_DEAL_DATETIME,'yyyymmddhh24miss') LAST_DEAL_DATETIME,ENTRY_SAM_LOGICAL_ID,to_char(ENTRY_DATETIME,'yyyymmddhh24miss') ENTRY_DATETIME,CARD_CONSUME_SEQ,TAC,"
                + "CITY_CODE,BUSINESS_CODE,TAC_DEAL_TYPE,TAC_DEV_ID "
                + " from "+FrameDBConstant.DB_PRE+"ST_MB_EXT_QUE_DEAL_BUS where balance_water_no=? order by DEAL_DATETIME";
        DbHelper dbHelper = null;
        boolean result = false;


        Object[] values = {balanceWaterNo};
        FileMobileSDFVo vo;
        String[] lineData;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                lineData = this.getArray(dbHelper);
                this.addRecord(fileDatas, FrameCodeConstant.LINE_ID_MOBILE, lineData);

                result = dbHelper.getNextDocument();
            }


        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return fileDatas;


    }

    private void addRecord(Hashtable<String, Vector> ht, String lineId, String[] lineData) {
        if (!ht.containsKey(lineId)) {
            ht.put(lineId, new Vector());
        }
        Vector v = ht.get(lineId);
        v.add(lineData);

    }

    private String[] getArray(DbHelper dbHelper) throws Exception {
        String[] arr = new String[19];
        arr[0] = dbHelper.getItemValue("SAM_TRADE_SEQ");//脱机交易流水号
        arr[1] = dbHelper.getItemValue("CARD_MAIN_ID")
                + dbHelper.getItemValue("CARD_SUB_ID");//票卡类型
        arr[2] = dbHelper.getItemValue("CARD_LOGICAL_ID");//票卡逻辑卡号
        arr[3] = dbHelper.getItemValue("CARD_PHYSICAL_ID");//票卡物理卡号
        arr[4] = dbHelper.getItemValue("LAST_SAM_LOGICAL_ID");//上次交易SAM卡逻辑卡号
        arr[5] = dbHelper.getItemValue("LAST_DEAL_DATETIME");//上次交易日期时间
        arr[6] = dbHelper.getItemValue("SAM_LOGICAL_ID");//本次交易SAM卡逻辑卡号
        arr[7] = dbHelper.getItemValue("DEAL_DATETIME");//本次交易日期时间
        arr[8] = dbHelper.getItemValue("ENTRY_SAM_LOGICAL_ID");//入口SAM卡逻辑卡号
        arr[9] = dbHelper.getItemValue("ENTRY_DATETIME");//入口日期时间
        arr[10] = dbHelper.getItemValue("PAY_MODE_ID");//交易类型
        arr[11] = this.getBigDecimal(dbHelper, "DEAL_FEE");//dbHelper.getItemBigDecimalValue("DEAL_FEE").toString();//交易金额
        arr[12] = this.getBigDecimal(dbHelper, "DEAL_BALANCE_FEE");//dbHelper.getItemValue("DEAL_BALANCE_FEE");//本次余额
        arr[13] = dbHelper.getItemValue("CARD_CONSUME_SEQ");//票卡消费交易计数
        arr[14] = dbHelper.getItemValue("TAC");//交易认证码
        arr[15] = dbHelper.getItemValue("CITY_CODE");//城市代码
        arr[16] = dbHelper.getItemValue("BUSINESS_CODE");//行业代码
        arr[17] = dbHelper.getItemValue("TAC_DEAL_TYPE");//TAC交易类型
        arr[18] = dbHelper.getItemValue("TAC_DEV_ID");//TAC终端编号
        return arr;

    }

    protected String getBigDecimal(DbHelper dbHelper, String fieldName) throws SQLException {
        String value = dbHelper.getItemValue(fieldName);
        BigDecimal m = new BigDecimal("0.00");
        m = m.add(new BigDecimal(value));
        return m.toString();
    }
}
