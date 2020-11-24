/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;

import com.goldsign.settle.realtime.app.vo.FileRecordQrCode56;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.dao.TradeBaseDao;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordTacBase;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class TradeQrCode56Dao extends TradeBaseDao{
     private static Logger logger = Logger.getLogger(TradeQrCode56Dao.class.getName());

    @Override
    public int insertError(FileRecordTacBase frb) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
     public int insertError(FileRecordBase frb,String errCode)  {
         
        FileRecordQrCode56 vo = (FileRecordQrCode56) frb;

        String sql = "insert into W_ACC_ST.W_ST_QP_ERR_UPDATE(LINE_ID,STATION_ID,DEV_TYPE_ID,DEVICE_ID,SAM_LOGICAL_ID,"
                                             + "SAM_TRADE_SEQ,CARD_MAIN_ID,CARD_SUB_ID,CARD_LOGICAL_ID,CARD_PHYSICAL_ID,"
                                             + "CARD_CONSUME_SEQ,CARD_STATUS_ID,UPDATE_AREA,UPDATE_REASON_ID,UPDATE_DATETIME,"
                                             + "PAY_TYPE,PENALTY_FEE,RECEIPT_ID,OPERATOR_ID,ENTRY_LINE_ID,"
                                             + "ENTRY_STATION_ID,SHIFT_ID,CARD_APP_FLAG,LIMIT_MODE,LIMIT_ENTRY_STATION,"
                                             + "LIMIT_EXIT_STATION,BALANCE_WATER_NO,FILE_NAME,CHECK_FLAG,ERR_CODE,"
                                             + "WATER_NO,CARD_APP_MODE,BALANCE_WATER_NO_SUB,RECORD_VER,TCT_ACTIVATE_DATETIME,"
                                             + "BUSINESS_WATER_NO,BUSINESS_WATER_NO_REL,ISSUE_QRCODE_PLATFORM_FLAG "                                          
                                             + ") "
                                             + "values(?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?," 
                                             + "W_ACC_ST.W_SEQ_W_ST_QP_ERR_UPDATE.nextval,?,?,?,?,"
                                             + "?,?,?"               
                                              + ") ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {vo.getLineId(),vo.getStationId(),vo.getDevTypeId(),vo.getDeviceId(),vo.getSamLogicalId(),
                           vo.getSamTradeSeq_s(),vo.getCardMainId(),vo.getCardSubId(),vo.getCardLogicalId(),vo.getCardPhysicalId(),
                           vo.getCardConsumeSeq_s(),vo.getCardStatusId(),vo.getUpdateArea(),vo.getUpdateReasonId(),vo.getUpdateDatetime_s(),
                           vo.getPayType(),vo.getPenaltyFee_s(),vo.getReceiptId(),vo.getOperatorId(),vo.getEntryLineId(),
                           vo.getEntryStationId(),vo.getShiftId(),vo.getCardAppFlag(),vo.getLimitMode(),vo.getLimitEntryStation(),
                           vo.getLimitExitStation(),vo.getBalanceWaterNo(),vo.getFileName(),vo.getCheckFlag(),errCode,
                           vo.getCardAppMode(),vo.getBalanceWaterNoSub(),vo.getRecordVer(),vo.getTctActiveDatetime_s(),
                           vo.getBusinessWaterNo(),vo.getBusinessWaterNoRel(),vo.getIssueQrcodePlatformFlag()
                          };
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.executeUpdate(sql, values);

        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return result;
    }
    
}
