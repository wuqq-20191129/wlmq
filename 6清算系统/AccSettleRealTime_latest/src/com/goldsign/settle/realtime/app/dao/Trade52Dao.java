/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;

import com.goldsign.settle.realtime.app.vo.FileRecord52;
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
public class Trade52Dao extends TradeBaseDao{
     private static Logger logger = Logger.getLogger(Trade52Dao.class.getName());

    @Override
    public int insertError(FileRecordTacBase frb) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public int insertError(FileRecordBase frb,String errCode)  {
         
        FileRecord52 vo = (FileRecord52) frb;

        String sql = "insert into "+FrameDBConstant.DB_PRE+"ST_ERR_SIGN_CARD(LINE_ID,STATION_ID,DEV_TYPE_ID,DEVICE_ID,PROPOSER_NAME,"
                                             + "PROPOSER_SEX,IND_TYPE,NATIONAL_ID,EXPIRED_DATE,TEL_NO,"
                                             + "FAX,ADDRESS,OPERATOR_ID,APP_DATE,SHIFT_ID,"
                                             + "BALANCE_WATER_NO,FILE_NAME,ERR_CODE,WATER_NO,CARD_APP_FLAG,"
                                             + "CARD_MAIN_ID,CARD_SUB_ID,APP_BUSINESS_TYPE,BALANCE_WATER_NO_SUB,RECORD_VER"                                    
                                             + ") "
                                             + "values(?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?, "+FrameDBConstant.DB_PRE+"SEQ_W_ST_ERR_SIGN_CARD.nextval,?,"
                                             + "?,?,?,?,?"                                                            
                                             + ") ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {
                           vo.getLineId(),vo.getStationId(),vo.getDevTypeId(),vo.getDeviceId(),vo.getApplyName(),
                           vo.getApplySex(),vo.getIdentityType(),vo.getIdentityId(),vo.getExpiredDate_s(),vo.getTelNo(),
                           vo.getFax(),vo.getAddress(),vo.getOperatorId(),vo.getApplyDatetime_s(),vo.getShiftId(),
                           vo.getBalanceWaterNo(),vo.getFileName(),errCode,vo.getCardAppFlag(),
                           vo.getCardMainId(),vo.getCardSubId(),vo.getApplyBusinessType(),vo.getBalanceWaterNoSub(),vo.getRecordVer()
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
