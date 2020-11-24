/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;

import com.goldsign.settle.realtime.app.vo.FileRecord55;
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
public class Trade55Dao extends TradeBaseDao{
     private static Logger logger = Logger.getLogger(Trade55Dao.class.getName());

    @Override
    public int insertError(FileRecordTacBase frb) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public int insertError(FileRecordBase frb,String errCode)  {
         
        FileRecord55 vo = (FileRecord55) frb;

        String sql = "insert into W_ACC_ST.W_ST_ERR_ENTRY(LINE_ID,STATION_ID,DEV_TYPE_ID,DEVICE_ID,SAM_LOGICAL_ID,"
                                             + "SAM_TRADE_SEQ,CARD_MAIN_ID,CARD_SUB_ID,CARD_LOGICAL_ID,CARD_PHYSICAL_ID,"
                                             + "CARD_STATUS_ID,OLD_EXPIRE_DATETIME,NEW_EXPIRE_DATETIME,OPERATE_DATETIME,OPERATOR_ID,"
                                             + "SHIFT_ID,CARD_APP_FLAG,BALANCE_WATER_NO,FILE_NAME,CHECK_FLAG,"
                                             + "ERR_CODE,WATER_NO,BALANCE_WATER_NO_SUB,RECORD_VER "                                          
                                             + ") "
                                             + "values(?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,W_ACC_ST.W_SEQ_W_ST_ERR_DELAY.nextval,?,?"                                                         
                                              + ") ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {vo.getLineId(),vo.getStationId(),vo.getDevTypeId(),vo.getDeviceId(),vo.getSamLogicalId(),
                           vo.getSamTradeSeq_s(),vo.getCardMainId(),vo.getCardSubId(),vo.getCardLogicalId(),vo.getCardPhysicalId(),
                           vo.getCardStatusId_s(),vo.getOldExpireDatetime_s(),vo.getNewExpireDatetime_s(),vo.getOpDateTime_s(),vo.getOperatorId(),
                           vo.getShiftId(),vo.getCardAppFlag(),vo.getBalanceWaterNo(),vo.getFileName(),vo.getCheckFlag(),
                           errCode,vo.getBalanceWaterNoSub(),vo.getRecordVer()
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
