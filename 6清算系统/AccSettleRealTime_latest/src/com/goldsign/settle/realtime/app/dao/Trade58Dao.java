/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;

import com.goldsign.settle.realtime.app.vo.FileRecord58;
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
public class Trade58Dao extends TradeBaseDao{
     private static Logger logger = Logger.getLogger(Trade58Dao.class.getName());

    @Override
    public int insertError(FileRecordTacBase frb) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public int insertError(FileRecordBase frb,String errCode) throws Exception {
         FileRecord58 vo = (FileRecord58) frb;

        String sql = "insert into W_ACC_ST.W_ST_ERR_NON_RTN_APP(LINE_ID,STATION_ID,DEV_TYPE_ID,DEVICE_ID,CARD_MAIN_ID,"
                                             + "CARD_SUB_ID,CARD_LOGICAL_ID,CARD_PHYSICAL_ID,CARD_PRINT_ID,APPLY_DATETIME,"
                                             + "RECEIPT_ID,OPERATOR_ID,APPLY_NAME,TEL_NO,IDENTITY_TYPE,"
                                             + "IDENTITY_ID,IS_BROKEN,SHIFT_ID,CARD_APP_FLAG,BALANCE_WATER_NO,"
                                             + "FILE_NAME,CHECK_FLAG,ERR_CODE,WATER_NO,BALANCE_WATER_NO_SUB,"
                                             + "RECORD_VER"
                                                                                      
                                             + ") "
                                             + "values("
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,W_ACC_ST.W_SEQ_W_ST_ERR_NON_RTN_APP.nextval,?,"
                                             + "?"
                                           
                                              + ") ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {vo.getLineId(),vo.getStationId(),vo.getDevTypeId(),vo.getDeviceId(),vo.getCardMainId(),
                           vo.getCardSubId(),vo.getCardLogicalId(),vo.getCardPhysicalId(),vo.getCardPrintId(),vo.getApplyDatetime_s(),
                           vo.getReceiptId(),vo.getOperatorId(),vo.getApplyName(),vo.getTelNo(),vo.getIdentityType(),
                           vo.getIdentityId(),vo.getIsBroken(),vo.getShiftId(),vo.getCardAppFlag(),vo.getBalanceWaterNo(),
                           vo.getFileName(),vo.getCheckFlag(),errCode,vo.getBalanceWaterNoSub(),
                           vo.getRecordVer()
                           
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
