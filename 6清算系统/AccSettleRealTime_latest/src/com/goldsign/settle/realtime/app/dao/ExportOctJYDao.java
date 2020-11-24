/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.app.vo.ExportDbResult;
import com.goldsign.settle.realtime.app.vo.ExportOctTRXVo;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.dao.ExportDaoBase;

import com.goldsign.settle.realtime.frame.util.PubUtil;
import java.math.BigDecimal;
import java.sql.SQLException;

import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ExportOctJYDao extends ExportDaoBase {

    private static Logger logger = Logger.getLogger(ExportOctJYDao.class.getName());

    public ExportDbResult getRecords(String balanceWaterNo) throws Exception {
        ExportDbResult resultExp = new ExportDbResult();
        ExportDbResult resultExpDeal = this.getRecordsFromDealByDependent(balanceWaterNo);
        ExportDbResult resultExpEntry = this.getRecordsFromEntryByDependent(balanceWaterNo);

        resultExp.setTotalFeeFen(resultExpDeal.getTotalFeeFen());
        resultExp.setTotalNum(resultExpDeal.getTotalNum()+resultExpEntry.getTotalNum());
        resultExp.addRecords(resultExpDeal.getRecords());
        resultExp.addRecords(resultExpEntry.getRecords());
        return resultExp;

    }

    ;

    public ExportDbResult getRecordsFromDealByDependent(String balanceWaterNo) throws Exception {
        //终端编号,终端标志,交易时间,终端交易流水号,票卡逻辑卡号,
        //票卡物理卡号,票卡主类型,票卡子类型,上次交易终端编号,上次交易日期时间,
        //交易金额,本次余额,原票价,交易类型,本次入口终端编号,
        //本次入口日期时间,票卡联机交易计数,票卡脱机交易计数,交易认证码,城市代码,
        //行业代码,密钥标示,预留字段,最后充值日期
        String sql = "select SAM_LOGICAL_ID_OCT,'1',to_char(DEAL_DATETIME,'yyyymmddhh24miss') DEAL_DATETIME,SAM_TRADE_SEQ,CARD_LOGICAL_ID,"
                + "CARD_PHYSICAL_ID_OCT,CARD_MAIN_ID_OCT,CARD_SUB_ID_OCT,LAST_SAM_LOGICAL_ID_OCT,to_char(LAST_DEAL_DATETIME_OCT,'yyyymmddhh24miss') LAST_DEAL_DATETIME_OCT,"
                + "DEAL_FEE,DEAL_BALANCE_FEE,deal_no_discount_fee_oct,PAY_MODE_ID_OCT,ENTRY_SAM_LOGICAL_ID_OCT,"
                + "to_char(ENTRY_DATETIME_OCT,'yyyymmddhh24miss') ENTRY_DATETIME_OCT,card_charge_seq,CARD_CONSUME_SEQ,TAC,CITY_CODE,"
                + "BUSINESS_CODE,KEY_VERSION,'0000000000000000',to_char(LAST_CHARGE_DATETIME,'yyyymmddhh24miss') LAST_CHARGE_DATETIME ,reserve_oct "
                
                + " from " + FrameDBConstant.DB_PRE + "ST_QUE_DEAL_OCT where balance_water_no=? order by SAM_LOGICAL_ID_OCT,SAM_TRADE_SEQ";

        DbHelper dbHelper = null;
        boolean result = false;
        // Vector<ExportOctTRXVo> v = new Vector();
        Vector<String[]> v = new Vector();
        ExportDbResult resultExp = new ExportDbResult();

        Object[] values = {balanceWaterNo};
        int totalFee = 0;;

        String[] arr;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                arr = this.getArray(dbHelper);
                totalFee = this.addFee(totalFee, arr[10]);
                v.add(arr);

                result = dbHelper.getNextDocument();
            }
            resultExp.setTotalFeeFen(totalFee);
            resultExp.setTotalNum(v.size());
            resultExp.setRecords(v);

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return resultExp;

    }

    public ExportDbResult getRecordsFromDealByIndependent(String balanceWaterNo) throws Exception {
        //终端编号,终端标志,交易时间,终端交易流水号,票卡逻辑卡号,
        //票卡物理卡号,票卡主类型,票卡子类型,上次交易终端编号,上次交易日期时间,
        //交易金额,本次余额,原票价,交易类型,本次入口终端编号,
        //本次入口日期时间,票卡联机交易计数,票卡脱机交易计数,交易认证码,城市代码,
        //行业代码,密钥标示,预留字段,最后充值日期
        String sql = "select SAM_LOGICAL_ID,'1',to_char(DEAL_DATETIME,'yyyymmddhh24miss') DEAL_DATETIME,SAM_TRADE_SEQ,CARD_LOGICAL_ID,"
                + "CARD_PHYSICAL_ID,CARD_MAIN_ID_OCT,CARD_SUB_ID_OCT,LAST_SAM_LOGICAL_ID,to_char(LAST_DEAL_DATETIME,'yyyymmddhh24miss') LAST_DEAL_DATETIME,"
                + "DEAL_FEE,DEAL_BALANCE_FEE,deal_no_discount_fee,PAY_MODE_ID_OCT,ENTRY_SAM_LOGICAL_ID,"
                + "to_char(ENTRY_DATETIME,'yyyymmddhh24miss') ENTRY_DATETIME,card_charge_seq,CARD_CONSUME_SEQ,TAC,CITY_CODE,"
                + "BUSINESS_CODE,KEY_VERSION,'0000000000000000',to_char(LAST_CHARGE_DATETIME,'yyyymmddhh24miss') LAST_CHARGE_DATETIME "
                + " from " + FrameDBConstant.DB_PRE + "ST_QUE_DEAL_OCT where balance_water_no=? order by SAM_LOGICAL_ID,SAM_TRADE_SEQ";

        DbHelper dbHelper = null;
        boolean result = false;
        // Vector<ExportOctTRXVo> v = new Vector();
        Vector<String[]> v = new Vector();
        ExportDbResult resultExp = new ExportDbResult();

        Object[] values = {balanceWaterNo};
        int totalFee = 0;;

        String[] arr;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                arr = this.getArray(dbHelper);
                totalFee = this.addFee(totalFee, arr[10]);
                v.add(arr);

                result = dbHelper.getNextDocument();
            }
            resultExp.setTotalFeeFen(totalFee);
            resultExp.setTotalNum(v.size());
            resultExp.setRecords(v);

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return resultExp;

    }

    public ExportDbResult getRecordsFromEntryByDependent(String balanceWaterNo) throws Exception {
        /*
         String sql = "select SAM_LOGICAL_ID,'1',to_char(DEAL_DATETIME,'yyyymmddhh24miss') DEAL_DATETIME,SAM_TRADE_SEQ,CARD_LOGICAL_ID,"
                + "CARD_PHYSICAL_ID,CARD_MAIN_ID_OCT,CARD_SUB_ID_OCT,LAST_SAM_LOGICAL_ID,to_char(LAST_DEAL_DATETIME,'yyyymmddhh24miss') LAST_DEAL_DATETIME,"
                + "DEAL_FEE,DEAL_BALANCE_FEE,deal_no_discount_fee,PAY_MODE_ID_OCT,ENTRY_SAM_LOGICAL_ID,"
                + "to_char(ENTRY_DATETIME,'yyyymmddhh24miss') ENTRY_DATETIME,card_charge_seq,CARD_CONSUME_SEQ,TAC,CITY_CODE,"
                + "BUSINESS_CODE,KEY_VERSION,'0000000000000000',to_char(LAST_CHARGE_DATETIME,'yyyymmddhh24miss') LAST_CHARGE_DATETIME "
                + " from " + FrameDBConstant.DB_PRE + "ST_QUE_DEAL_OCT where balance_water_no=? order by DEAL_DATETIME";
         */
 /*
   insert into      W_ACC_ST.W_ST_QUE_ENTRY( WATER_NO,LINE_ID,STATION_ID,DEV_TYPE_ID,DEVICE_ID,SAM_LOGICAL_ID,SAM_TRADE_SEQ,CARD_MAIN_ID,
               CARD_SUB_ID,CARD_LOGICAL_ID,CARD_PHYSICAL_ID,ENTRY_DATETIME,CARD_STATUS_ID,BALANCE_FEE,
               LIMIT_MODE,LIMIT_ENTRY_STATION,LIMIT_EXIT_STATION,BALANCE_WATER_NO,BALANCE_WATER_NO_SUB,CARD_APP_FLAG,FILE_NAME,CHECK_FLAG,
                             WORK_MODE,CARD_APP_MODE,RECORD_VER,TCT_ACTIVE_DATETIME)
         */
        //终端编号,终端标志,交易时间,终端交易流水号,票卡逻辑卡号,
        //票卡物理卡号,票卡主类型,票卡子类型,上次交易终端编号,上次交易日期时间,
        //交易金额,本次余额,原票价,交易类型,本次入口终端编号,
        //本次入口日期时间,票卡联机交易计数,票卡脱机交易计数,交易认证码,城市代码,
        //行业代码,密钥标示,预留字段,最后充值日期
        String sql = "select SAM_LOGICAL_ID_OCT,'1',to_char(ENTRY_DATETIME,'yyyymmddhh24miss') DEAL_DATETIME,SAM_TRADE_SEQ,CARD_LOGICAL_ID,"
                + "CARD_PHYSICAL_ID_OCT,CARD_MAIN_ID_OCT,CARD_SUB_ID_OCT,SAM_LOGICAL_ID_OCT LAST_SAM_LOGICAL_ID_OCT,to_char(ENTRY_DATETIME,'yyyymmddhh24miss') LAST_DEAL_DATETIME,"
                + "'0.00' DEAL_FEE,BALANCE_FEE DEAL_BALANCE_FEE,'0.00' deal_no_discount_fee,PAY_MODE_ID_OCT,SAM_LOGICAL_ID_OCT ENTRY_SAM_LOGICAL_ID_OCT,"
                + "to_char(ENTRY_DATETIME,'yyyymmddhh24miss') ENTRY_DATETIME,0 card_charge_seq, CARD_CONSUME_SEQ,'00000000' TAC,'8300' CITY_CODE,"
                + "'0000' BUSINESS_CODE,'0000' KEY_VERSION,'0000000000000000','19710101000000' LAST_CHARGE_DATETIME,reserve_oct  "
                + " from " + FrameDBConstant.DB_PRE + "ST_QUE_ENTRY_OCT where balance_water_no=? order by SAM_LOGICAL_ID,SAM_TRADE_SEQ";

        DbHelper dbHelper = null;
        boolean result = false;
        // Vector<ExportOctTRXVo> v = new Vector();
        Vector<String[]> v = new Vector();
        ExportDbResult resultExp = new ExportDbResult();

        Object[] values = {balanceWaterNo};
        int totalFee = 0;;

        String[] arr;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                arr = this.getArrayForEntry(dbHelper);
                totalFee = this.addFee(totalFee, arr[10]);
                v.add(arr);

                result = dbHelper.getNextDocument();
            }
            resultExp.setTotalFeeFen(totalFee);
            resultExp.setTotalNum(v.size());
            resultExp.setRecords(v);

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return resultExp;

    }

    private BigDecimal addFee(BigDecimal totalFee, String fee) {
        BigDecimal n = new BigDecimal(fee);
        n.setScale(2);
        return totalFee.add(n);

    }

    private int addFee(int totalFee, String fee) {
        int feaDeal = Integer.parseInt(fee);
        totalFee = totalFee + feaDeal;
        return totalFee;

    }

    private String[] getArray(DbHelper dbHelper) throws Exception {
        /*
         //终端编号,终端标志,交易时间,终端交易流水号,票卡逻辑卡号,
        //票卡物理卡号,票卡主类型,票卡子类型,上次交易终端编号,上次交易日期时间,
        //交易金额,本次余额,原票价,交易类型,本次入口终端编号,
        //本次入口日期时间,票卡联机交易计数,票卡脱机交易计数,交易认证码,城市代码,
        //行业代码,密钥标示,预留字段,最后充值日期
         */
        /*
        String sql = "select SAM_LOGICAL_ID_OCT,'1',to_char(DEAL_DATETIME,'yyyymmddhh24miss') DEAL_DATETIME,SAM_TRADE_SEQ,CARD_LOGICAL_ID,"
                + "CARD_PHYSICAL_ID_OCT,CARD_MAIN_ID_OCT,CARD_SUB_ID_OCT,LAST_SAM_LOGICAL_ID_OCT,to_char(LAST_DEAL_DATETIME_OCT,'yyyymmddhh24miss') LAST_DEAL_DATETIME_OCT,"
                + "DEAL_FEE,DEAL_BALANCE_FEE,deal_no_discount_fee_oct,PAY_MODE_ID_OCT,ENTRY_SAM_LOGICAL_ID_OCT,"
                + "to_char(ENTRY_DATETIME_OCT,'yyyymmddhh24miss') ENTRY_DATETIME_OCT,card_charge_seq,CARD_CONSUME_SEQ,TAC,CITY_CODE,"
                + "BUSINESS_CODE,KEY_VERSION,'0000000000000000',to_char(LAST_CHARGE_DATETIME,'yyyymmddhh24miss') LAST_CHARGE_DATETIME "
                + " from " + FrameDBConstant.DB_PRE + "ST_QUE_DEAL_OCT where balance_water_no=? order by SAM_LOGICAL_ID_OCT,SAM_TRADE_SEQ";
        */
        String[] arr = new String[24];
        arr[0] = dbHelper.getItemValue("SAM_LOGICAL_ID_OCT");//本次交易SAM卡逻辑卡号
        arr[1] = "1";//终端标志
        arr[2] = dbHelper.getItemValue("DEAL_DATETIME");//本次交易日期时间
        arr[3] = dbHelper.getItemValue("SAM_TRADE_SEQ");//脱机交易流水号
        arr[4] = dbHelper.getItemValue("CARD_LOGICAL_ID");//票卡逻辑卡号

        arr[5] = dbHelper.getItemValue("CARD_PHYSICAL_ID_OCT");//票卡物理卡号
        arr[6] = dbHelper.getItemValue("CARD_MAIN_ID_OCT");
        arr[7] = dbHelper.getItemValue("CARD_SUB_ID_OCT");//票卡类型
        arr[8] = dbHelper.getItemValue("LAST_SAM_LOGICAL_ID_OCT");//上次交易SAM卡逻辑卡号
        arr[9] = dbHelper.getItemValue("LAST_DEAL_DATETIME_OCT");//上次交易日期时间

        arr[10] = this.getFenFromYuan(dbHelper, "DEAL_FEE");// this.getBigDecimal(dbHelper, "DEAL_FEE");//dbHelper.getItemBigDecimalValue("DEAL_FEE").toString();//交易金额
        arr[11] = this.getFenFromYuan(dbHelper, "DEAL_BALANCE_FEE");//本次余额
        arr[12] = this.getFenFromYuan(dbHelper, "deal_no_discount_fee_oct");//原票价
        arr[13] = dbHelper.getItemValue("PAY_MODE_ID_OCT");//交易类型   
        arr[14] = dbHelper.getItemValue("ENTRY_SAM_LOGICAL_ID_OCT");//入口SAM卡逻辑卡号

        arr[15] = dbHelper.getItemValue("ENTRY_DATETIME_OCT");//入口日期时间
        arr[16] = dbHelper.getItemValue("card_charge_seq");//票卡联机交易计数
        arr[17] = dbHelper.getItemValue("CARD_CONSUME_SEQ");//票卡消费交易计数
        arr[18] = dbHelper.getItemValue("TAC");//交易认证码
        arr[19] = dbHelper.getItemValue("CITY_CODE");//城市代码

        arr[20] = dbHelper.getItemValue("BUSINESS_CODE");//行业代码
        arr[21] = dbHelper.getItemValue("KEY_VERSION");//密钥标示
        //arr[22] = "0000000000000000";//预留字段
        //预留字段优惠月(6)+累计金额(6)分+累计次数(4)20191217
        arr[22] = dbHelper.getItemValue("reserve_oct");//预留字段
        arr[23] = dbHelper.getItemValue("LAST_CHARGE_DATETIME");//最后充值日期

        return arr;

    }

    private String[] getArrayForEntry(DbHelper dbHelper) throws Exception {
        /*
        String sql = "select SAM_LOGICAL_ID,'1',to_char(ENTRY_DATETIME,'yyyymmddhh24miss') DEAL_DATETIME,SAM_TRADE_SEQ,CARD_LOGICAL_ID,"
                + "CARD_PHYSICAL_ID,CARD_MAIN_ID_OCT,CARD_SUB_ID_OCT,SAM_LOGICAL_ID LAST_SAM_LOGICAL_ID,to_char(ENTRY_DATETIME,'yyyymmddhh24miss') LAST_DEAL_DATETIME,"
                + "'0.00' DEAL_FEE,BALANCE_FEE DEAL_BALANCE_FEE,'0.00' deal_no_discount_fee,PAY_MODE_ID_OCT,SAM_LOGICAL_ID ENTRY_SAM_LOGICAL_ID,"
                + "to_char(ENTRY_DATETIME,'yyyymmddhh24miss') ENTRY_DATETIME,0 card_charge_seq,0 CARD_CONSUME_SEQ,'00000000' TAC,'8300' CITY_CODE,"
                + "'0000' BUSINESS_CODE,'0000' KEY_VERSION,'0000000000000000','19710101000000' LAST_CHARGE_DATETIME "
                + " from " + FrameDBConstant.DB_PRE + "ST_QUE_ENTRY_OCT where balance_water_no=? order by SAM_LOGICAL_ID,SAM_TRADE_SEQ";
         */
 /*
         //终端编号,终端标志,交易时间,终端交易流水号,票卡逻辑卡号,
        //票卡物理卡号,票卡主类型,票卡子类型,上次交易终端编号,上次交易日期时间,
        //交易金额,本次余额,原票价,交易类型,本次入口终端编号,
        //本次入口日期时间,票卡联机交易计数,票卡脱机交易计数,交易认证码,城市代码,
        //行业代码,密钥标示,预留字段,最后充值日期
         */
        String[] arr = new String[24];
        arr[0] = dbHelper.getItemValue("SAM_LOGICAL_ID_OCT");//本次交易SAM卡逻辑卡号
        arr[1] = "1";//终端标志
        arr[2] = dbHelper.getItemValue("DEAL_DATETIME");//本次交易日期时间
        arr[3] = dbHelper.getItemValue("SAM_TRADE_SEQ");//脱机交易流水号
        arr[4] = dbHelper.getItemValue("CARD_LOGICAL_ID");//票卡逻辑卡号

        arr[5] = dbHelper.getItemValue("CARD_PHYSICAL_ID_OCT");//票卡物理卡号
        arr[6] = dbHelper.getItemValue("CARD_MAIN_ID_OCT");
        arr[7] = dbHelper.getItemValue("CARD_SUB_ID_OCT");//票卡类型
        arr[8] = dbHelper.getItemValue("LAST_SAM_LOGICAL_ID_OCT");//上次交易SAM卡逻辑卡号
        arr[9] = dbHelper.getItemValue("LAST_DEAL_DATETIME");//上次交易日期时间

        arr[10] = this.getFenFromYuan(dbHelper, "DEAL_FEE");// this.getBigDecimal(dbHelper, "DEAL_FEE");//dbHelper.getItemBigDecimalValue("DEAL_FEE").toString();//交易金额
        arr[11] = this.getFenFromYuan(dbHelper, "DEAL_BALANCE_FEE");//本次余额
        arr[12] = this.getFenFromYuan(dbHelper, "deal_no_discount_fee");//原票价
        arr[13] = dbHelper.getItemValue("PAY_MODE_ID_OCT");//交易类型   
        arr[14] = dbHelper.getItemValue("ENTRY_SAM_LOGICAL_ID_OCT");//入口SAM卡逻辑卡号

        arr[15] = dbHelper.getItemValue("ENTRY_DATETIME");//入口日期时间
        arr[16] = dbHelper.getItemValue("card_charge_seq");//票卡联机交易计数
        arr[17] = dbHelper.getItemValue("CARD_CONSUME_SEQ");//票卡消费交易计数
        arr[18] = dbHelper.getItemValue("TAC");//交易认证码
        arr[19] = dbHelper.getItemValue("CITY_CODE");//城市代码

        arr[20] = dbHelper.getItemValue("BUSINESS_CODE");//行业代码
        arr[21] = dbHelper.getItemValue("KEY_VERSION");//密钥标示
        //arr[22] = "0000000000000000";//预留字段
        //预留字段优惠月(6)+累计金额(6)分+累计次数(4)20191217
        arr[22] = dbHelper.getItemValue("reserve_oct");//预留字段

        arr[23] = dbHelper.getItemValue("LAST_CHARGE_DATETIME");//最后充值日期

        return arr;

    }

    private ExportOctTRXVo getVo(DbHelper dbHelper) throws Exception {

        ExportOctTRXVo vo = new ExportOctTRXVo();
        vo.setSamTradeSeq(dbHelper.getItemValue("SAM_TRADE_SEQ"));

        vo.setCardMainId(dbHelper.getItemValue("CARD_MAIN_ID"));
        vo.setCardSubId(dbHelper.getItemValue("CARD_SUB_ID"));
        vo.setCardPhysicalId(dbHelper.getItemValue("CARD_PHYSICAL_ID"));
        vo.setCardLogicalId(dbHelper.getItemValue("CARD_LOGICAL_ID"));

        vo.setSamLogicalId(dbHelper.getItemValue("SAM_LOGICAL_ID"));
        vo.setTradeType(dbHelper.getItemValue("PAY_MODE_ID"));
        vo.setDealFee(dbHelper.getItemValue("DEAL_FEE"));
        vo.setBalanceFee(dbHelper.getItemValue("DEAL_BALANCE_FEE"));
        vo.setDealDatetime(dbHelper.getItemValue("DEAL_DATETIME"));

        vo.setSamLogicalIdLast(dbHelper.getItemValue("LAST_SAM_LOGICAL_ID"));
        vo.setDealDatetimeLast(dbHelper.getItemValue("LAST_DEAL_DATETIME"));
        vo.setSamLogicalIdEntry(dbHelper.getItemValue("ENTRY_SAM_LOGICAL_ID"));
        vo.setDealDatetimeEntry(dbHelper.getItemValue("ENTRY_DATETIME"));

        vo.setCardConsumeSeq(dbHelper.getItemValue("CARD_CONSUME_SEQ"));
        vo.setTac(dbHelper.getItemValue("TAC"));

        return vo;
    }
}
