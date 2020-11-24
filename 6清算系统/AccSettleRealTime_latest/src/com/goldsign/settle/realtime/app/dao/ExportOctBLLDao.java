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
public class ExportOctBLLDao extends ExportDaoBase{
    private static Logger logger = Logger.getLogger(ExportOctBLLDao.class.getName());

    public ExportDbResult getRecords(String balanceWaterNo) throws Exception {

        String sql = "select CARD_LOGICAL_ID_START,CARD_LOGICAL_ID_END,SECT_FLAG,CARD_STATUS  "
                + " from ST_EXT_MTR_BLACKLIST where balance_water_no=? order by CARD_LOGICAL_ID_START,CARD_LOGICAL_ID_END";

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
                //totalFee=this.addFee(totalFee, arr[3]);
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
        String[] arr = new String[4];
        //CARD_LOGICAL_ID_START,CARD_LOGICAL_ID_END,SECT_FLAG,CARD_STATUS
        arr[0] = dbHelper.getItemValue("CARD_LOGICAL_ID_START");//开始名单逻辑卡号
        arr[1] = dbHelper.getItemValue("SECT_FLAG");//段号标志
               
        arr[2] = dbHelper.getItemValue("CARD_LOGICAL_ID_END");//结束名单逻辑卡号
        arr[3] = dbHelper.getItemValue( "CARD_STATUS");//票卡状态
        

        return arr;

    }
    
    
}
