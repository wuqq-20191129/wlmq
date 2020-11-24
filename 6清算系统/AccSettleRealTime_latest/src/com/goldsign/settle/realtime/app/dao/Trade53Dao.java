/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.app.vo.FileRecord53;

import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
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
public class Trade53Dao extends TradeBaseDao{
     private static Logger logger = Logger.getLogger(Trade53Dao.class.getName());

    @Override
    public int insertError(FileRecordTacBase frb) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public int insertError(FileRecordBase frb,String errCode)  {
         
        FileRecord53 vo = (FileRecord53) frb;

        String sql = "insert into W_ACC_ST.W_ST_ERR_ENTRY(LINE_ID,STATION_ID,DEV_TYPE_ID,DEVICE_ID,SAM_LOGICAL_ID,"
                                             + "SAM_TRADE_SEQ,CARD_MAIN_ID,CARD_SUB_ID,CARD_LOGICAL_ID,CARD_PHYSICAL_ID,"
                                             + "ENTRY_DATETIME,CARD_STATUS_ID,BALANCE_FEE,LIMIT_MODE,LIMIT_ENTRY_STATION,"
                                             + "LIMIT_EXIT_STATION,BALANCE_WATER_NO,CARD_APP_FLAG,FILE_NAME,CHECK_FLAG,"
                                             + "ERR_CODE,WATER_NO,WORK_MODE, CARD_CONSUME_SEQ,CARD_APP_MODE,"
                                             + "BALANCE_WATER_NO_SUB,RECORD_VER,TCT_ACTIVE_DATETIME,DISCOUNT_YEAR_MONTH,ACCUMULATE_CONSUME_FEE,"
                                             + "INTERVAL_BETWEEN_BUS_METRO,LAST_BUS_DEAL_DATETIME,ACCUMULATE_CONSUME_NUM "                                          
                                             + ") "
                                             + "values(?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,W_ACC_ST.W_SEQ_W_ST_ERR_ENTRY.nextval,?,?,?,"
                                             + "?,?,?,?,?, "  
                                              + "?,?,? "  
                                              + ") ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {vo.getLineId(),vo.getStationId(),vo.getDevTypeId(),vo.getDeviceId(),vo.getSamLogicalId(),
                           vo.getSamTradeSeq_s(),vo.getCardMainId(),vo.getCardSubId(),vo.getCardLogicalId(),vo.getCardPhysicalId(),
                           vo.getEntryDatetime_s(),vo.getCardStatusId(),vo.getBalanceFee_s(),vo.getLimitMode(),vo.getLimitEntryStation(),
                           vo.getLimitExitStation(),vo.getBalanceWaterNo(),vo.getCardAppFlag(),vo.getFileName(),vo.getCheckFlag(),
                           errCode,vo.getWorkMode(),vo.getCardConsumeSeq_s(),vo.getCardAppMode(),
                           vo.getBalanceWaterNoSub(),vo.getRecordVer(),vo.getTctActiveDatetime_s(),vo.getDiscountYearMonth(),vo.getAccumulateConsumeFee_s(),
                           vo.getIntervalBetweenBusMetro_s(),vo.getLastBusDealDatetime_s(),vo.getAccumulateConsumeNum_s()
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
