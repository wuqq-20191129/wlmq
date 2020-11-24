/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.app.vo.FileRecord51;
import com.goldsign.settle.realtime.app.vo.FileRecord63;
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
public class Trade63Dao extends TradeBaseDao{
    private static Logger logger = Logger.getLogger(Trade63Dao.class.getName());

    @Override
    public int insertError(FileRecordTacBase frb) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public int insertError(FileRecordBase frb,String errCode)  {
         
        FileRecord63 vo = (FileRecord63) frb;

        String sql = "insert into W_ACC_ST.W_ST_ERR_SALE_PREMAKE(LINE_ID,STATION_ID,DEV_TYPE_ID,DEVICE_ID,CARD_MAIN_ID,"
                                             + "CARD_SUB_ID,CARD_LOGICAL_ID,CARD_PHYSICAL_ID,CARD_STATUS_ID,WATER_NO_BUSINESS,"
                                             + "SAM_LOGICAL_ID,SAM_TRADE_SEQ,DEPOSIT_TYPE,DEPOSIT_FEE,BALANCE_FEE,"
                                             + "SALE_NUM,SALE_DATETIME,RECEIPT_ID,OPERATOR_ID,SHIFT_ID,"
                                             + "AUXI_FEE,CARD_APP_FLAG,BALANCE_WATER_NO,FILE_NAME,CHECK_FLAG,"
                                             + "ERR_CODE,WATER_NO,BALANCE_WATER_NO_SUB,RECORD_VER,sale_fee "                                            
                                             + ") "
                                             + "values(?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,W_ACC_ST.W_SEQ_W_ST_ERR_SALE_PREMAKE.nextval,?,?,?"                                             
                                             + ") ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {vo.getLineId(),vo.getStationId(),vo.getDevTypeId(),vo.getDeviceId(),vo.getCardMainId(),
                           vo.getCardSubId(),vo.getCardLogicalId(),vo.getCardPhysicalId(),vo.getCardStatusId_s(),vo.getWaterNoBusiness(),
                           vo.getSamLogicalId(),vo.getSamTradeSeq_s(),vo.getDepositType(),vo.getDepositFee_s(),vo.getBalanceFee_s(),
                           vo.getSaleNum_s(),vo.getSaleTime_s(),vo.getReceiptId(),vo.getOperatorId(),vo.getShiftId(),
                           vo.getAuxiFee_s(),vo.getCardAppFlag(),vo.getBalanceWaterNo(),vo.getFileName(),vo.getCheckFlag(),
                           errCode,vo.getBalanceWaterNoSub(),vo.getRecordVer(),vo.getSaleFee_s()
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
