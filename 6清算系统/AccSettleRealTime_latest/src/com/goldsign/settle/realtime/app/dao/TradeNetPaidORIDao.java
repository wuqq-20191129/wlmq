/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;

import com.goldsign.settle.realtime.app.vo.FileRecordNetPaidOrdImp;
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
public class TradeNetPaidORIDao extends TradeBaseDao{
     private static Logger logger = Logger.getLogger(TradeNetPaidORDDao.class.getName());
    @Override
    public int insertError(FileRecordTacBase frb) throws Exception {
        return this.insertError(frb, FrameFileHandledConstant.RECORD_ERR_TAC[0]);
        
    }
    
    @Override
    public int insertError(FileRecordBase frb,String errCode) throws Exception {
        FileRecordNetPaidOrdImp vo = (FileRecordNetPaidOrdImp) frb;
        String sql = "insert into "+FrameDBConstant.DB_ST+"ST_NP_ERR_ORDER_IMP("
                                               + "ORDER_NO,ORDER_TYPE,FINISH_DATETIME,LINE_ID ,STATION_ID,"
                                               + "DEV_TYPE_ID ,DEV_CODE,STATUS,CARD_MAIN_ID,CARD_SUB_ID,"
                                               + "DEAL_NUM ,DEAL_NUM_NOT,DEAL_FEE,REFUND_FEE,AUXI_FEE ,"
                                               + "BALANCE_WATER_NO ,FILE_NAME ,CHECK_FLAG ,ERR_CODE,WATER_NO"


                                             + ") "
                                             + "values(?,?,to_date(?,'yyyymmddhh24miss'),?,?,"
                                                     + "?,?,?,?,?,"
                                                     + "?,?,?,?,?,"
                                                     + "?,?,?,?,"
                                                     
                                                     +FrameDBConstant.DB_ST+"SEQ_ST_NP_ERR_ORDER_IMP.nextval"
                                                  
                                                     + ") ";
         //  + "SEQ_ST_ERR_SALE_SJT.nextval"
        DbHelper dbHelper = null;
        logger.info(sql);
        int result = 0;
        Object[] values = {vo.getOrderNo(),vo.getOrderType(),vo.getFinishDatetime(),vo.getLineId(),vo.getStationId(),
                           vo.getDevTypeId(),vo.getDeviceId(),vo.getStatus(),vo.getCardMainId(),vo.getCardSubId(),
                           vo.getDealNum(),vo.getDealNumNot(),this.convertFenToYuan(vo.getDealFee()),this.convertFenToYuan(vo.getRefundFee()),this.convertFenToYuan(vo.getAuxiFee()),
                           vo.getBalanceWaterNo(),vo.getFileName(),vo.getCheckFlag(),errCode
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
