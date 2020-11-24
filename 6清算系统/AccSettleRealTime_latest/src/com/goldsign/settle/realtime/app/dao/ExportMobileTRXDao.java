/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.app.vo.ExportDbResult;
import com.goldsign.settle.realtime.app.vo.ExportMobileTRXVo;
import com.goldsign.settle.realtime.app.vo.ExportOctTRXVo;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.dao.ExportDaoBase;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import java.math.BigDecimal;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ExportMobileTRXDao extends ExportDaoBase{
    private static Logger logger = Logger.getLogger(ExportMobileTRXDao.class.getName());

    public ExportDbResult getRecords(String balanceWaterNo) throws Exception {

        String sql = "select SAM_TRADE_SEQ,CARD_MAIN_ID,CARD_SUB_ID,CARD_LOGICAL_ID,CARD_PHYSICAL_ID,"
                + "SAM_LOGICAL_ID,PAY_MODE_ID,DEAL_FEE,DEAL_BALANCE_FEE DEAL_BALANCE_FEE,to_char(DEAL_DATETIME,'yyyymmddhh24miss') DEAL_DATETIME,LAST_SAM_LOGICAL_ID,"
                + "to_char(LAST_DEAL_DATETIME,'yyyymmddhh24miss') LAST_DEAL_DATETIME,ENTRY_SAM_LOGICAL_ID,to_char(ENTRY_DATETIME,'yyyymmddhh24miss') ENTRY_DATETIME,CARD_CONSUME_SEQ,TAC,"
                + "CITY_CODE,BUSINESS_CODE,TAC_DEAL_TYPE,TAC_DEV_ID "
                + " from "+FrameDBConstant.DB_ST+"w_st_mb_ext_que_deal_line where balance_water_no=? order by SAM_LOGICAL_ID,DEAL_DATETIME";

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
                totalFee=this.addFee(totalFee, arr[11]);
                v.add(arr);
               // ExportOctTRXVo vo = this.getVo(dbHelper);
                //this.addFee(totalFee, vo.getDealFee());
               // v.add(vo);

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

    private BigDecimal addFee(BigDecimal totalFee, String fee) {
       BigDecimal n= new BigDecimal(fee);
       n.setScale(2);
        return totalFee.add(n);
        

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
        arr[12] =  this.getBigDecimal(dbHelper, "DEAL_BALANCE_FEE");//dbHelper.getItemValue("DEAL_BALANCE_FEE");//本次余额
        arr[13] = dbHelper.getItemValue("CARD_CONSUME_SEQ");//票卡消费交易计数
        arr[14] = dbHelper.getItemValue("TAC");//交易认证码
        arr[15] = dbHelper.getItemValue("CITY_CODE");//城市代码
        arr[16] = dbHelper.getItemValue("BUSINESS_CODE");//行业代码
        arr[17] = dbHelper.getItemValue("TAC_DEAL_TYPE");//TAC交易类型
        arr[18] = dbHelper.getItemValue("TAC_DEV_ID");//TAC终端编号
        return arr;

    }
   

    private ExportMobileTRXVo getVo(DbHelper dbHelper) throws Exception {

        ExportMobileTRXVo vo = new ExportMobileTRXVo();
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
