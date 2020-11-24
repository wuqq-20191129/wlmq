/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.app.vo.ExportDbResult;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.dao.ExportDaoBase;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import java.math.BigDecimal;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ExportOctERRDao extends ExportDaoBase{
    private static Logger logger = Logger.getLogger(ExportOctERRDao.class.getName());

    public ExportDbResult getRecords(String balanceWaterNo) throws Exception {

        String sql = "select SAM_LOGICAL_ID,SAM_TRADE_SEQ,CARD_LOGICAL_ID,CARD_PHYSICAL_ID,to_char(DEAL_DATETIME,'yyyymmddhh24miss') DEAL_DATETIME,"
                + "ERR_CODE,deal_fee  "
                + " from ST_EXT_MTR_ERR_TRADE where balance_water_no=? order by SAM_LOGICAL_ID";

        DbHelper dbHelper = null;
        boolean result = false;
       // Vector<ExportOctTRXVo> v = new Vector();
        Vector<String[]> v = new Vector();
        ExportDbResult resultExp = new ExportDbResult();

        Object[] values = {balanceWaterNo};
        BigDecimal totalFee = new BigDecimal("0.00");;
        totalFee.setScale(2);
        String[] arr;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                arr = this.getArray(dbHelper);
                totalFee=this.addFee(totalFee, arr[6]);
                v.add(arr);


                result = dbHelper.getNextDocument();
            }
            resultExp.setTotalFee(totalFee);
            resultExp.setTotalNum(v.size());
            resultExp.setRecords(v);



        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return resultExp;


    }

    private BigDecimal addFee(BigDecimal totalFee, String fee) {
       BigDecimal n= new BigDecimal(fee);
       n.setScale(2);
        return totalFee.add(n);
        

    }

    private String[] getArray(DbHelper dbHelper) throws Exception {
        String[] arr = new String[7];
        // SAM_LOGICAL_ID,SAM_TRADE_SEQ,CARD_LOGICAL_ID,CARD_PHYSICAL_ID,
        //to_char(DEAL_DATETIME,'yyyymmddhhmimiss') DEAL_DATETIME,ERR_CODE
        arr[0] = dbHelper.getItemValue("SAM_TRADE_SEQ");//脱机交易流水号
        arr[1] = dbHelper.getItemValue("SAM_LOGICAL_ID");//交易SAM卡逻辑卡号
               
        arr[2] = dbHelper.getItemValue("CARD_LOGICAL_ID");//票卡逻辑卡号
        arr[3] = dbHelper.getItemValue("CARD_PHYSICAL_ID");//票卡物理卡号
       
        
        arr[4] = dbHelper.getItemValue("DEAL_DATETIME");//交易日期时间
        arr[5] = dbHelper.getItemValue("ERR_CODE");//错误代码
        arr[6] = dbHelper.getItemValue("deal_fee");//错误交易金额
        
        

        return arr;

    }
    
}
