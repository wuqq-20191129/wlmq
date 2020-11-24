/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.app.vo.FileRecord50;
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
public class TradeNetPaid50Dao extends TradeBaseDao{
    private static Logger logger = Logger.getLogger(TradeNetPaid50Dao.class.getName());
    @Override
    public int insertError(FileRecordTacBase frb) throws Exception {
        return this.insertError(frb, FrameFileHandledConstant.RECORD_ERR_TAC[0]);
        
    }
    
    @Override
    public int insertError(FileRecordBase frb,String errCode) throws Exception {
        FileRecord50 vo = (FileRecord50) frb;
        String sql = "insert into "+FrameDBConstant.DB_ST+"ST_NP_ERR_SALE(LINE_ID,STATION_ID,DEV_TYPE_ID,DEVICE_ID,SAM_LOGICAL_ID,"
                                               + "SAM_TRADE_SEQ,SALE_DATETIME,PAY_TYPE,CARD_LOGICAL_ID_PAY,CARD_COUNT_USED,"
                                               + "CARD_LOGICAL_ID,CARD_PHYSICAL_ID,CARD_STATUS_ID,SALE_FEE,CARD_MAIN_ID,"
                                               + "CARD_SUB_ID,ZONE_ID,TAC,DEPOSIT_TYPE,DEPOSIT_FEE,"
                                               + "OPERATOR_ID,SHIFT_ID,BALANCE_WATER_NO,FILE_NAME,ERR_CODE,"
                                               + "AUXI_FEE,CARD_APP_FLAG,TAC_DEAL_TYPE,TAC_DEV_ID,ORDER_NO,WATER_NO"
                                             + ") "
                                             + "values(?,?,?,?,?,"
                                                     + "?,to_date(?,'yyyymmddhh24miss'),?,?,?,"
                                                     + "?,?,?,?,?,"
                                                     + "?,?,?,?,?,"
                                                     + "?,?,?,?,?,"
                                                     + "?,?,?,?,?,"+FrameDBConstant.DB_ST+"SEQ_ST_NP_ERR_SALE.nextval"
                                                  
                                                     + ") ";
         //  + "SEQ_ST_ERR_SALE_SJT.nextval"
        DbHelper dbHelper = null;
        logger.info(sql);
        int result = 0;
        Object[] values = {vo.getLineId(),vo.getStationId(),vo.getDevTypeId(),vo.getDeviceId(),vo.getSamLogicalId(),
                           vo.getSamTradeSeq(),vo.getSaleTime(),vo.getPayType(),vo.getCardLogicIdPay(),vo.getCardCountUsed(),
                           vo.getCardLogicalId(),vo.getCardPhysicalId(),vo.getCardSubId(),this.convertFenToYuan(vo.getSaleFee()),vo.getCardMainId(),
                           vo.getCardSubId(),vo.getZoneId(),vo.getTac(),vo.getDepositType(),this.convertFenToYuan(vo.getDepositFee()),
                           vo.getOperatorId(),vo.getShiftId(),vo.getBalanceWaterNo(),vo.getFileName(),errCode,
                           this.convertFenToYuan(vo.getAuxiFee()),vo.getCardAppFlag(),vo.getBusTacDealType(),vo.getBusTacDevId(),
                           vo.getOrderNo()
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
