/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.FileMobileSDFVo;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FileNetPaidLccDao {
    private static Logger logger = Logger.getLogger(FileNetPaidLccDao.class.getName());
    public Hashtable<String, Vector> getRecordsForAudit(String balanceWaterNo) throws Exception {

        String sql = "select WATER_NO,BALANCE_WATER_NO,LINE_ID ,STATION_ID,DIFF_DEAL_NUM ,"
                + "DIFF_DEAL_FEE ,DIFF_SALE_NUM,DIFF_SALE_FEE,SQUAD_DAY "
                + "from "+FrameDBConstant.DB_PRE+"ST_NP_STS_LCC_ACC   "
                + "where BALANCE_WATER_NO=?  "
                + " order by SQUAD_DAY,LINE_ID,STATION_ID";
        DbHelper dbHelper = null;
        boolean result = false;
        Hashtable<String, Vector> files = new Hashtable();

        Object[] values = {balanceWaterNo};
        FileMobileSDFVo vo;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                vo = this.getVo(dbHelper);
                
                this.addRecord(files, vo,FrameCodeConstant.LINE_ID_NETPAID);

                result = dbHelper.getNextDocument();
            }


        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return files;


    }

    private FileMobileSDFVo getVo(DbHelper dbHelper) throws SQLException {
        FileMobileSDFVo vo = new FileMobileSDFVo();
        vo.setLineId(dbHelper.getItemValue("LINE_ID"));//线路车站代码
        vo.setStationId(dbHelper.getItemValue("STATION_ID"));

        vo.setDiffChargeNum(dbHelper.getItemIntValue("DIFF_DEAL_NUM"));//充值总数量差异
        vo.setDiffChargeFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_DEAL_FEE")));//充值总金额差异
        

        vo.setDiffSaleNum(dbHelper.getItemIntValue("DIFF_SALE_NUM"));//发售总数量差异
        vo.setDiffSaleFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_SALE_FEE")));//发售总金额差异差异

        
        
        vo.setSquadDay(dbHelper.getItemValue("SQUAD_DAY"));//对账日期add by hejj 20160623



        return vo;
    }

    protected int convertYuanToFen(BigDecimal yuan) {
        BigDecimal fen = yuan.multiply(new BigDecimal(100));
        fen.setScale(2);

        return fen.intValue();
    }

    private String convert(int n) {
        return new Integer(n).toString();
    }

    private void addRecord(Hashtable<String, Vector> ht, FileMobileSDFVo vo,String fixLine) {
        String lineDate = fixLine+"#"+vo.getSquadDay();//add by hejj 20160623
        //String line = vo.getLineId();
        if (!ht.containsKey(lineDate)) {
            ht.put(lineDate, new Vector());
        }
        Vector v = ht.get(lineDate);
        Object[] record = new Object[5];
        record[0] = vo.getLineId() + vo.getStationId();
        // record[1] = vo.getStationId();
        record[1] = vo.getDiffSaleNum();// 发售总数量差异    
        record[2] = vo.getDiffSaleFee();//发售总金额差异 
        record[3] = vo.getDiffChargeNum();// 充值总数量差异
        record[4] = vo.getDiffChargeFee();//充值总金额差异    
         

        v.add(record);
    }
    
}
