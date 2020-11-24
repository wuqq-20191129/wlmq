/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;

import com.goldsign.settle.realtime.app.vo.FileRecordNetPaidOrd;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.dao.TradeBaseDao;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordTacBase;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class TradeNetPaidORDDao extends TradeBaseDao{
     private static Logger logger = Logger.getLogger(TradeNetPaidORDDao.class.getName());
    @Override
    public int insertError(FileRecordTacBase frb) throws Exception {
        return this.insertError(frb, FrameFileHandledConstant.RECORD_ERR_TAC[0]);
        
    }
    
    @Override
    public int insertError(FileRecordBase frb,String errCode) throws Exception {
        FileRecordNetPaidOrd vo = (FileRecordNetPaidOrd) frb;
        String sql = "insert into "+FrameDBConstant.DB_ST+"ST_NP_ERR_ORDER(ORDER_NO,ORDER_TYPE,STATUS,GENERATE_DATETIME ,PAID_DATETIME,"
                                               + "CARD_MAIN_ID ,CARD_SUB_ID,LINE_ID_START,STATION_ID_START,LINE_ID_END,"
                                               + "STATION_ID_END ,DEAL_UNIT_FEE,DEAL_NUM,DEAL_FEE,ORDER_TYPE_BUY ,"
                                               + "PAID_CHANNEL_TYPE ,PAID_CHANNEL_CODE ,MOBILE_NO ,BALANCE_WATER_NO,FILE_NAME ,"
                                               + "CHECK_FLAG,ERR_CODE ,WATER_NO"

                                             + ") "
                                             + "values(?,?,?,to_date(?,'yyyymmddhh24miss'),to_date(?,'yyyymmddhh24miss'),"
                                                     + "?,?,?,?,?,"
                                                     + "?,?,?,?,?,"
                                                     + "?,?,?,?,?,"
                                                     
                                                     + "?,?,"+FrameDBConstant.DB_ST+"SEQ_ST_NP_ERR_ORDER.nextval"
                                                  
                                                     + ") ";
         //  + "SEQ_ST_ERR_SALE_SJT.nextval"
        DbHelper dbHelper = null;
        logger.info(sql);
        int result = 0;
        Object[] values = {vo.getOrderNo(),vo.getOrderType(),vo.getStatus(),vo.getGenerateDatetime(),vo.getPaidDatetime(),
                           vo.getCardMainId(),vo.getCardSubId(),vo.getLineIdStart(),vo.getStationIdStart(),vo.getLineIdEnd(),
                           vo.getStationIdEnd(),this.convertFenToYuan(vo.getDealUnitFee()),vo.getDealNum(),this.convertFenToYuan(vo.getDealFee()),vo.getOrderTypeBuy(),
                           vo.getPaidChannelType(),vo.getPaidChannelCode(),vo.getMobileNo(),vo.getBalanceWaterNo(),vo.getFileName(),
                           vo.getCheckFlag(),errCode
                          };
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
    
}
