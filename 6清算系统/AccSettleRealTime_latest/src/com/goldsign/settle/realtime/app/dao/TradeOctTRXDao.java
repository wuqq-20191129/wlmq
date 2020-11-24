/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.app.vo.FileRecord54;
import com.goldsign.settle.realtime.app.vo.FileRecordOctTRX;
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
public class TradeOctTRXDao extends TradeBaseDao{
    private static Logger logger = Logger.getLogger(Trade54Dao.class.getName());

    @Override
    public int insertError(FileRecordTacBase frb) throws Exception {
        return  this.insertError(frb, FrameFileHandledConstant.RECORD_ERR_TAC[0]);
        
    }
    @Override
     public int insertError(FileRecordBase frb,String errCode)  {
         
        FileRecordOctTRX vo = (FileRecordOctTRX) frb;

        String sql = "insert into ST_EXT_MTR_ERR_DEAL"
                                             + "(SAM_TRADE_SEQ,CARD_MAIN_ID,CARD_SUB_ID,CARD_LOGICAL_ID,CARD_PHYSICAL_ID,"
                                             + " LAST_SAM_LOGICAL_ID,LAST_DEAL_DATETIME,SAM_LOGICAL_ID,DEAL_DATETIME,ENTRY_SAM_LOGICAL_ID,"
                                             + "ENTRY_DATETIME,PAY_MODE_ID,DEAL_FEE,DEAL_BALANCE_FEE,CARD_CONSUME_SEQ,"
                                             + "TAC,CITY_CODE,BUSINESS_CODE,TAC_DEAL_TYPE,TAC_DEV_ID,"
                                             + " BALANCE_WATER_NO,FILE_NAME,CHECK_FLAG,ERR_CODE,WATER_NO"
                                             + ") "
                                             + "values(?,?,?,?,?,"
                                             + "?,to_date(?,'yyyymmddhh24miss'),?,to_date(?,'yyyymmddhh24miss'),?,"
                                             + "to_date(?,'yyyymmddhh24miss'),?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,SEQ_ST_EXT_MTR_ERR_DEAL.nextval"

                                             + ") ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {vo.getSamTradeSeq(),vo.getCardMainId(),vo.getCardSubId(),vo.getCardLogicalId(),vo.getCardPhysicalId(),
                           vo.getLastSamLogicalId(),vo.getLastDealDatetime(),vo.getSamLogicalId(),vo.getDealDatetime(),vo.getEntrySamLogicalId(),
                           vo.getEntryDatetime(),vo.getPayModeId(),vo.getTradeFeeYuan(),vo.getBalanceFeeYuan(),vo.getCardConsumeSeq(),
                           vo.getTac(),vo.getBusCityCode(),vo.getBusBusinessCode(),vo.getBusTacDealType(),vo.getBusTacDevId(),
                           vo.getBalanceWaterNo(),vo.getFileName(),vo.getCheckFlag(),errCode
            
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
