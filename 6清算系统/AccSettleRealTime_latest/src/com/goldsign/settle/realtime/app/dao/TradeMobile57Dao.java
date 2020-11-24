/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.app.vo.FileRecord57;
import com.goldsign.settle.realtime.app.vo.FileRecordMobile57;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.dao.TradeBaseDao;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordTacBase;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class TradeMobile57Dao extends TradeBaseDao{
    private static Logger logger = Logger.getLogger(TradeMobile57Dao.class.getName());

    @Override
    public int insertError(FileRecordTacBase frb) throws Exception {
        return this.insertError(frb, FrameFileHandledConstant.RECORD_ERR_TAC[0]);
        
    }
    
    @Override
    public int insertError(FileRecordBase frb,String errCode) throws Exception {
         FileRecordMobile57 vo = (FileRecordMobile57) frb;

        String sql = "insert into "+FrameDBConstant.DB_ST+"W_ST_MB_ERR_RETURN(LINE_ID,STATION_ID,DEV_TYPE_ID,DEVICE_ID,SAM_LOGICAL_ID,"
                                             + "SAM_TRADE_SEQ,CARD_MAIN_ID,CARD_SUB_ID,CARD_LOGICAL_ID,CARD_PHYSICAL_ID,"
                                             + "CARD_STATUS_ID,RETURN_BALANCE_FEE,RETURN_DEPOSIT_FEE,PENALTY_FEE,PENALTY_REASON_ID,"
                                             + "CARD_CONSUME_SEQ,RETURN_TYPE,RETURN_DATETIME,RECEIPT_ID,APPLY_DATETIME,"
                                             + "TAC,OPERATOR_ID,SHIFT_ID,AUXI_FEE,CARD_APP_FLAG,"
                                             + "BALANCE_WATER_NO,FILE_NAME,CHECK_FLAG,ERR_CODE,WATER_NO,"
                                             + "TAC_DEAL_TYPE,TAC_DEV_ID,mobile_no,paid_channel_type,paid_channel_code,"
                                             + "BALANCE_WATER_NO_SUB"                                            
                                             + ") "
                                             + "values("
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,"+FrameDBConstant.DB_ST+"W_SEQ_W_ST_MB_ERR_RETURN.nextval,"
                                             + "?,?,?,?,?,"
                                             + "?"                                           
                                              + ") ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {vo.getLineId(),vo.getStationId(),vo.getDevTypeId(),vo.getDeviceId(),vo.getSamLogicalId(),
                           vo.getSamTradeSeq_s(),vo.getCardMainId(),vo.getCardSubId(),vo.getCardLogicalId(),vo.getCardPhysicalId(),
                           vo.getCardStatusId_s(),vo.getReturnBalanceFee_s(),vo.getReturnDepositFee_s(),vo.getPenaltyFee_s(),vo.getPenaltyReasonId(),
                           vo.getCardConsumeSeq_s(),vo.getReturnType(),vo.getReturnDatetime_s(),vo.getReceiptId(),vo.getApplyDatetime_s(),
                           vo.getTac(),vo.getOperatorId(),vo.getShiftId(),vo.getAuxiFee_s(),vo.getCardAppFlag(),
                           vo.getBalanceWaterNo(),vo.getFileName(),vo.getCheckFlag(),errCode,
                           vo.getBusTacDealType(),vo.getBusTacDevId(),vo.getMobileNo(),vo.getPaidChannelType(),vo.getPaidChannelCode(),
                           vo.getBalanceWaterNoSub()
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
