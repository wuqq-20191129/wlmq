/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;

import com.goldsign.settle.realtime.app.vo.FileRecord61;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.dao.TradeBaseDao;
import com.goldsign.settle.realtime.frame.util.CharUtil;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordTacBase;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class Trade61Dao extends TradeBaseDao{
     private static Logger logger = Logger.getLogger(Trade61Dao.class.getName());

    @Override
    public int insertError(FileRecordTacBase frb) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public int insertError(FileRecordBase frb,String errCode) throws Exception {
         FileRecord61 vo = (FileRecord61) frb;

        String sql = "insert into W_ACC_ST.W_ST_ERR_ADMIN(LINE_ID,STATION_ID,DEV_TYPE_ID,DEVICE_ID,ADMIN_DATETIME,"
                                             + "ADMIN_WAY_ID,CARD_MAIN_ID,CARD_SUB_ID,RETURN_FEE,PENALTY_FEE,"
                                             + "ADMIN_REASON_ID,DESCRIBE,PASSENGER_NAME,OPERATOR_ID,SHIFT_ID,"
                                             + "BALANCE_WATER_NO,FILE_NAME,CHECK_FLAG,ERR_CODE,WATER_NO,BALANCE_WATER_NO_SUB,"
                                             + "RECORD_VER"
                                            
                                                                                      
                                             + ") "
                                             + "values("
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,?,"
                                             + "?,?,?,?,W_ACC_ST.W_SEQ_W_ST_ERR_ADMIN.nextval,?,"
                                             + "?"
                
                                           
                                              + ") ";
        DbHelper dbHelper = null;
        int result = 0;
        Object[] values = {vo.getLineId(),vo.getStationId(),vo.getDevTypeId(),vo.getDeviceId(),vo.getAdminDatetime_s(),
                           vo.getAdminWayId(),vo.getCardMainId(),vo.getCardSubId(),
                           vo.getReturnFee_s(),vo.getPenaltyFee_s(),
                           vo.getAdminReasonId(),CharUtil.isoToGbk18030(vo.getDescribe()),CharUtil.isoToGbk18030(vo.getPassengerName()),vo.getOperatorId(),vo.getShiftId(),
                           vo.getBalanceWaterNo(),vo.getFileName(),vo.getCheckFlag(),errCode,vo.getBalanceWaterNoSub(),
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
    private String getFixLenStr(String str,int len){
        if(str ==null)
            return "";
        char[] arr =str.toCharArray();
        int lenStr =arr.length;
        if(lenStr >len)
        {
            char[] arr1 = new char[len];
            for(int i=0;i<len;i++)
                arr1[i]=arr[i];
            String str1 = new String(arr1);
            return str1;
                
        }
        return str;
    }
    
}
