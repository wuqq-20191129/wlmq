/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.FileMobileSDFVo;
import com.goldsign.settle.realtime.frame.vo.FileSDFVo;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FileMobileLccDao {

    private static Logger logger = Logger.getLogger(FileMobileLccDao.class.getName());

    public Hashtable<String, Vector> getRecordsForAudit(String balanceWaterNo) throws Exception {

        String sql = "select WATER_NO,BALANCE_WATER_NO,LINE_ID ,STATION_ID,DIFF_DEAL_NUM ,"
                + "DIFF_DEAL_FEE ,DIFF_RETURN_NUM ,DIFF_RETURN_FEE,DIFF_SALE_NUM,DIFF_SALE_FEE,DIFF_LOCK_NUM,DIFF_UNLOCK_NUM,SQUAD_DAY,"
                + "diff_sale_sjt_num,diff_sale_sjt_fee,paid_channel_type_charge,paid_channel_code_charge,paid_channel_type_return,"
                + "paid_channel_code_return,paid_channel_type_sale_sjt,paid_channel_code_sale_sjt,"
                + "paid_channel_type_sale,paid_channel_code_sale,diff_charge_tct_tnum,diff_charge_tct_num,diff_charge_tct_fee,"
                + "paid_channel_type_buy,paid_channel_code_buy  "
                + " from " + FrameDBConstant.DB_PRE + "ST_MB_STS_LCC_ACC   "
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
                this.addRecord(files, vo);

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
        vo.setDiffReturnNum(dbHelper.getItemIntValue("DIFF_RETURN_NUM"));//即时退款总数量差异
        vo.setDiffReturnFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_RETURN_FEE")));//即时退款总金额差异差异

        vo.setDiffSaleNum(dbHelper.getItemIntValue("DIFF_SALE_NUM"));//发售总数量差异
        vo.setDiffSaleFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_SALE_FEE")));//发售总金额差异差异

        vo.setDiffLockNum(dbHelper.getItemIntValue("DIFF_LOCK_NUM"));//锁卡总数量差异
        vo.setDiffUnlockNum(dbHelper.getItemIntValue("DIFF_UNLOCK_NUM"));//解锁总数量差异

        vo.setDiffSaleSjtNum(dbHelper.getItemIntValue("DIFF_SALE_SJT_NUM"));//单程票发售总数量差异
        vo.setDiffSaleSjtFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_SALE_SJT_FEE")));//单程票发售金额差异

        vo.setSquadDay(dbHelper.getItemValue("SQUAD_DAY"));//对账日期add by hejj 20160623

        vo.setPaidChannelTypeCharge(dbHelper.getItemValue("paid_channel_type_charge"));//充值渠道类型
        vo.setPaidChannelCodeCharge(dbHelper.getItemValue("paid_channel_code_charge"));//充值渠道代码
        vo.setPaidChannelTypeReturn(dbHelper.getItemValue("paid_channel_type_return"));//退款渠道类型
        vo.setPaidChannelCodeReturn(dbHelper.getItemValue("paid_channel_code_return"));//退款渠道代码
        vo.setPaidChannelTypeSaleSjt(dbHelper.getItemValue("paid_channel_type_sale_sjt"));//单程票发售渠道类型
        vo.setPaidChannelCodeSaleSjt(dbHelper.getItemValue("paid_channel_code_sale_sjt"));//单程票发渠道代码
        
        vo.setPaidChannelTypeSale(dbHelper.getItemValue("paid_channel_type_sale"));//发售渠道类型
        vo.setPaidChannelCodeSale(dbHelper.getItemValue("paid_channel_code_sale"));//发售渠道代码
        vo.setDiffChargeTctTnum(dbHelper.getItemIntValue("diff_charge_tct_tnum"));//充次总次数差异
        vo.setDiffChargeTctNum(dbHelper.getItemIntValue("diff_charge_tct_num"));//充次总数量差异
        vo.setDiffChargeTctFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("diff_charge_tct_fee")));//充次总金额差异
        vo.setPaidChannelTypeBuy(dbHelper.getItemValue("paid_channel_type_buy"));//购次渠道类型
        vo.setPaidChannelCodeBuy(dbHelper.getItemValue("paid_channel_code_buy"));//购次渠道代码

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

    private void addRecord(Hashtable<String, Vector> ht, FileMobileSDFVo vo) {
        String lineDate = vo.getLineId() + "#" + vo.getSquadDay();//add by hejj 20160623
        //String line = vo.getLineId();
        if (!ht.containsKey(lineDate)) {
            ht.put(lineDate, new Vector());
        }
        Vector v = ht.get(lineDate);
        Object[] record = new Object[24];
        record[0] = vo.getLineId() + vo.getStationId();
        // record[1] = vo.getStationId();
        record[1] = vo.getDiffChargeFee();//充值总金额差异    
        record[2] = vo.getDiffChargeNum();// 充值总数量差异 
        record[3] = vo.getPaidChannelTypeCharge();// 充值渠道类型//add by hejj 20180925
        record[4] = vo.getPaidChannelCodeCharge();// 充值渠道代码//add by hejj 20180925

        record[5] = vo.getDiffReturnNum();// 退款总数量差异    
        record[6] = vo.getDiffReturnFee();//退款总金额差异  
        record[7] = vo.getPaidChannelTypeReturn();// 充值渠道类型//add by hejj 20180925
        record[8] = vo.getPaidChannelCodeReturn();// 充值渠道代码//add by hejj 20180925

        record[9] = vo.getDiffSaleNum();// 发售总数量差异    
        record[10] = vo.getDiffSaleFee();//发售总金额差异 

        record[11] = vo.getDiffLockNum();// 锁卡总数量差异    
        record[12] = vo.getDiffUnlockNum();//解锁总数量差异 

        record[13] = vo.getDiffSaleSjtNum();// 单程票发售总数量差异  
        record[14] = vo.getDiffSaleSjtFee();//单程票发售总金额差异 
        record[15] = vo.getPaidChannelTypeSaleSjt();// 单程票渠道类型//add by hejj 20180925
        record[16] = vo.getPaidChannelCodeSaleSjt();// 单程票渠道代码//add by hejj 20180925
        
        record[17] = vo.getPaidChannelTypeSale();// 发售渠道类型//add by hejj 20191102
        record[18] = vo.getPaidChannelCodeSale();// 发售渠道代码//add by hejj 20191102
        record[19] = vo.getDiffChargeTctTnum();// 充次总次数差异   
        record[20] = vo.getDiffChargeTctNum();// 充次总数量差异 
        record[21] = vo.getDiffChargeTctFee();//充次总金额差异
        record[22] = vo.getPaidChannelTypeBuy();// 购次渠道类型//add by hejj 20191102
        record[23] = vo.getPaidChannelCodeBuy();// 购次渠道代码//add by hejj 20191102

        v.add(record);
    }
}
