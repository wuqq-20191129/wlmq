/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.app.vo.FileRecord54;
import com.goldsign.settle.realtime.app.vo.FileRecordMobile54;
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
public class TradeMobile54Dao extends TradeBaseDao {

    private static Logger logger = Logger.getLogger(TradeMobile54Dao.class.getName());

    @Override
    public int insertError(FileRecordTacBase frb) throws Exception {
        return this.insertError(frb, FrameFileHandledConstant.RECORD_ERR_TAC[0]);

    }

    @Override
    public int insertError(FileRecordBase frb, String errCode) {

        FileRecordMobile54 vo = (FileRecordMobile54) frb;

        String sql = "insert into "+FrameDBConstant.DB_ST+"W_ST_MB_ERR_DEAL(LINE_ID,STATION_ID,DEV_TYPE_ID,DEVICE_ID,SAM_LOGICAL_ID,"
                + "SAM_TRADE_SEQ,DEAL_DATETIME,CARD_MAIN_ID,CARD_SUB_ID,CARD_LOGICAL_ID,"
                + "CARD_PHYSICAL_ID,CARD_STATUS_ID,DEAL_FEE,DEAL_BALANCE_FEE,CARD_CHARGE_SEQ,"
                + "CARD_CONSUME_SEQ,PAY_MODE_ID,RECEIPT_ID,TAC,ENTRY_LINE_ID,"
                + "ENTRY_STATION_ID,ENTRY_SAM_LOGICAL_ID,ENTRY_DATETIME,OPERATOR_ID,SHIFT_ID,"
                + "LAST_SAM_LOGICAL_ID,LAST_DEAL_DATETIME,DEAL_NO_DISCOUNT_FEE,LIMIT_MODE,LIMIT_ENTRY_STATION,"
                + "LIMIT_EXIT_STATION,CARD_APP_FLAG,BALANCE_WATER_NO,FILE_NAME,CHECK_FLAG,"
                + "ERR_CODE,WATER_NO,WORK_MODE,CITY_CODE,BUSINESS_CODE,"
                + "TAC_DEAL_TYPE,TAC_DEV_ID,CARD_APP_MODE,"
                +"mobile_no,paid_channel_type,paid_channel_code,"
                +"RECORD_VER,last_charge_datetime,key_version,ISSUE_UNIT,KEY_INDEX," 
                +"KEY_RANDOM_NO,ALGORITHM_FLAG,HOLDER_NAME,IDENTITY_TYPE,IDENTITY_ID," 
                +"BUY_TK_NUM,BUY_TK_UNIT_FEE"
                + ") "
                + "values(?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,"+FrameDBConstant.DB_ST+"W_SEQ_W_ST_MB_ERR_DEAL.nextval,?,?,?,"
                + "?,?,?,"
                + "?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?"
                + ") ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {vo.getLineId(), vo.getStationId(), vo.getDevTypeId(), vo.getDeviceId(), vo.getSamLogicalId(),
            vo.getSamTradeSeq_s(), vo.getDealDatetime_s(), vo.getCardMainId(), vo.getCardSubId(), vo.getCardLogicalId(),
            vo.getCardPhysicalId(), vo.getCardStatusId(), vo.getDealFee_s(), vo.getDealBalanceFee_s(), vo.getCardChargeSeq_s(),
            vo.getCardConsumeSeq_s(), vo.getPayModeId(), vo.getReceiptId(), vo.getTac(), vo.getEntryLineId(),
            vo.getEntryStationId(), vo.getEntrySamLogicalId(), vo.getEntryDatetime_s(), vo.getOperatorId(), vo.getShiftId(),
            vo.getLastSamLogicalId(), vo.getLastDealDatetime_s(), vo.getDealNoDiscountFee_s(), vo.getLimitMode(), vo.getLimitEntryStation(),
            vo.getLimitExitStation(), vo.getCardAppFlag(), vo.getBalanceWaterNo(), vo.getFileName(), vo.getCheckFlag(),
            errCode, vo.getWorkMode(), vo.getBusCityCode(), vo.getBusBusinessCode(),
            vo.getBusTacDealType(), vo.getBusTacDevId(), vo.getCardAppMode(),
            vo.getMobileNo(),vo.getPaidChannelType(),vo.getPaidChannelCode(),
            vo.getRecordVer(),vo.getLastChargeDatetime_s(),vo.getKeyVersion(),vo.getIssueUnit(),vo.getKeyIndex(),
            vo.getKeyRandomNo(),vo.getAlgorithmFlag(),vo.getHolderName(),vo.getIdentityType(),vo.getIdentityId(),
            vo.getBuyTkNum_s(),vo.getBuyTkUnitFee_s()

            
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
