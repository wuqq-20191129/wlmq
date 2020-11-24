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
public class ExportOctAUBDao extends ExportDaoBase{
     private static Logger logger = Logger.getLogger(ExportOctAUBDao.class.getName());

    public ExportDbResult getRecords(String balanceWaterNo) throws Exception {

        String sql = "select SAM_LOGICAL_ID,PAY_MODE_ID,TOTAL_DEAL_NUM,TOTAL_DEAL_FEE  "
                + " from ST_EXT_MTR_AUDIT_BALANCE where balance_water_no=? order by SAM_LOGICAL_ID";

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
                totalFee=this.addFee(totalFee, arr[3]);
                v.add(arr);
               // ExportOctTRXVo vo = this.getVo(dbHelper
                //this.addFee(totalFee, vo.getDealFee());
               // v.add(vo);

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
        //SAM_LOGICAL_ID,PAY_MODE_ID,TOTAL_DEAL_NUM,TOTAL_DEAL_FEE
        arr[0] = dbHelper.getItemValue("SAM_LOGICAL_ID");//SAM逻辑卡号
        arr[1] = dbHelper.getItemValue("PAY_MODE_ID");//交易类型
               
        arr[2] = dbHelper.getItemValue("TOTAL_DEAL_NUM");//交易笔数
        arr[3] = this.getBigDecimal(dbHelper, "TOTAL_DEAL_FEE");//交易金额
        

        return arr;

    }
   

   
    
}
