/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
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
public class FileLccDao {

    private static Logger logger = Logger.getLogger(FileLccDao.class.getName());

    public String getRecordsVersionForAudit(String balanceWaterNo) throws Exception {
        String sql = "select max(trim(RECORD_VER)) RECORD_VER from " + FrameDBConstant.DB_PRE + "ST_STS_LCC_ACC   "
                + "where BALANCE_WATER_NO=?  ";
        Object[] values = {balanceWaterNo};
        DbHelper dbHelper = null;
        boolean result = false;
        String recordVersion="";
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            if (!result) {
                return "";
            }
            recordVersion = dbHelper.getItemValue("RECORD_VER");
            

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
       return recordVersion;
    }

    public Hashtable<String, Vector> getRecordsForAudit(String balanceWaterNo) throws Exception {

        String sql = "select WATER_NO,BALANCE_WATER_NO,LINE_ID ,STATION_ID,DIFF_BOM_SALE_SJT_NUM ,"
                + "DIFF_BOM_SALE_SJT_FEE ,DIFF_TVM_SALE_SJT_NUM ,DIFF_TVM_SALE_SJT_FEE ,"
                + "DIFF_BOM_SALE_NUM ,DIFF_BOM_SALE_DEPOSIT_FEE ,DIFF_BOM_CHARGE_FEE ,"
                + "DIFF_TVM_CHARGE_NUM ,DIFF_TVM_CHARGE_FEE ,DIFF_RETURN_NUM ,DIFF_RETURN_FEE ,"
                + "DIFF_NON_RETURN_NUM ,DIFF_NON_RET_DEPOSIT_FEE,DIFF_NON_RET_ACTUAL_RET_BALA,"
                + "DIFF_NEGATIVE_CHARGE_NUM,DIFF_NEGATIVE_CHARGE_FEE,DIFF_DEAL_NUM ,DIFF_DEAL_FEE ,"
                + "DIFF_UPDATE_CASH_NUM,DIFF_UPDATE_CASH_FEE,DIFF_UPDATE_NONCASH_NUM ,DIFF_UPDATE_NONCASH_FEE ,"
                + "DIFF_ADMIN_NUM,DIFF_ADMIN_RETURN_FEE ,DIFF_ADMIN_PENALTY_FEE,ACCOUNT_DATE,"
                + "DIFF_ITM_SALE_SJT_NUM ,DIFF_ITM_SALE_SJT_FEE ,DIFF_ITM_CHARGE_NUM ,DIFF_ITM_CHARGE_FEE,"
                + "RECORD_VER,DIFF_QR_DEAL_NUM,DIFF_ELECT_TK_TCT_DEAL_NUM,DIFF_ELECT_TK_DEAL_NUM,DIFF_ELECT_TK_DEAL_FEE,diff_qr_entry_num,"
                + "diff_update_noncash_tct_num,diff_bom_sale_tct_num,diff_bom_sale_tct_fee  "
                + "from " + FrameDBConstant.DB_PRE + "ST_STS_LCC_ACC   "
                + "where BALANCE_WATER_NO=?  "
                + " order by ACCOUNT_DATE,LINE_ID,STATION_ID";
        DbHelper dbHelper = null;
        boolean result = false;
        Hashtable<String, Vector> files = new Hashtable();

        Object[] values = {balanceWaterNo};
        FileSDFVo vo;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                vo = this.getVo(dbHelper);
                this.addRecord(files, vo);//modify by hejj 20160623 支持同一清算日多对帐文件

                result = dbHelper.getNextDocument();
            }

        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return files;

    }

    private FileSDFVo getVo(DbHelper dbHelper) throws SQLException {
        FileSDFVo vo = new FileSDFVo();
        vo.setRecordVer(dbHelper.getItemValue("RECORD_VER"));//版本
        vo.setLineId(dbHelper.getItemValue("LINE_ID"));//线路车站代码
        vo.setStationId(dbHelper.getItemValue("STATION_ID"));

        vo.setDiffBomSaleSjtNum(dbHelper.getItemIntValue("DIFF_BOM_SALE_SJT_NUM"));//BOM单程票发售总数量差异
        vo.setDiffBomSaleSjtFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_BOM_SALE_SJT_FEE")));//BOM单程票发售总金额差异
        vo.setDiffTvmSaleSjtNum(dbHelper.getItemIntValue("DIFF_TVM_SALE_SJT_NUM"));//TVM单程票发售总数量差异
        vo.setDiffTvmSaleSjtFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_TVM_SALE_SJT_FEE")));//TVM单程票发售总金额差异

        vo.setDiffBomSaleNum(dbHelper.getItemIntValue("DIFF_BOM_SALE_NUM"));//BOM储值类票卡发售总数量差异
        vo.setDiffBomSaleDepositFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_BOM_SALE_DEPOSIT_FEE")));//BOM储值类票卡押金总金额差异
        vo.setDiffBomChargeFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_BOM_CHARGE_FEE")));//BOM充值总金额差异

        vo.setDiffTvmChargeNum(dbHelper.getItemIntValue("DIFF_TVM_CHARGE_NUM"));//TVM充值总数量差异
        vo.setDiffTvmChargeFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_TVM_CHARGE_FEE")));//TVM充值总金额差异

        vo.setDiffReturnNum(dbHelper.getItemIntValue("DIFF_RETURN_NUM"));//即时退款总数量差异
        vo.setDiffReturnFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_RETURN_FEE")));//即时退款总金额差异差异

        vo.setDiffNonRetNum(dbHelper.getItemIntValue("DIFF_NON_RETURN_NUM"));//非即时退款总数量差异
        vo.setDiffNonRetDepositFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_NON_RET_DEPOSIT_FEE")));//非即时退款总押金差异
        vo.setDiffNonRetActualBala(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_NON_RET_ACTUAL_RET_BALA")));//非即时退款总退还余额差异

        vo.setDiffNegativeChargeNum(dbHelper.getItemIntValue("DIFF_NEGATIVE_CHARGE_NUM"));//冲正总数量差异
        vo.setDiffNegativeChargeFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_NEGATIVE_CHARGE_FEE")));//冲正总金额差异

        vo.setDiffDealNum(dbHelper.getItemIntValue("DIFF_DEAL_NUM"));//出闸扣费总数量差异
        vo.setDiffDealFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_DEAL_FEE")));//出闸扣费总金额差异

        vo.setDiffUpdateCashNum(dbHelper.getItemIntValue("DIFF_UPDATE_CASH_NUM"));//现金更新总数量差异
        vo.setDiffUpdateCashFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_UPDATE_CASH_FEE")));//现金更新总金额差异
        vo.setDiffUpdateNonCashNum(dbHelper.getItemIntValue("DIFF_UPDATE_NONCASH_NUM"));//非现金更新总数量差异
        vo.setDiffUpdateNonCashFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_UPDATE_NONCASH_FEE")));//非现金更新总金额差异

        vo.setDiffAdminNum(dbHelper.getItemIntValue("DIFF_ADMIN_NUM"));//行政处理总数量差异
        vo.setDiffAdminReturnFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_ADMIN_RETURN_FEE")));//行政处理总支出金额差异
        vo.setDiffAdminPenaltyFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_ADMIN_PENALTY_FEE")));//行政处理总收取金额差异

        vo.setDiffItmSaleSjtNum(dbHelper.getItemIntValue("DIFF_ITM_SALE_SJT_NUM"));//ITM单程票发售总数量差异
        vo.setDiffItmSaleSjtFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_ITM_SALE_SJT_FEE")));//ITM单程票发售总金额差异

        vo.setDiffItmChargeNum(dbHelper.getItemIntValue("DIFF_ITM_CHARGE_NUM"));//ITM充值总数量差异
        vo.setDiffItmChargeFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("DIFF_ITM_CHARGE_FEE")));//ITM充值总金额差异

        vo.setAccountDate(dbHelper.getItemValue("ACCOUNT_DATE"));//对账日期

        vo.setDiffQrDealNum(dbHelper.getItemIntValue("diff_qr_deal_num"));//二维码钱包交易总数量差异
        vo.setDiffElectTkTctDealNum(dbHelper.getItemIntValue("diff_elect_tk_tct_deal_num"));//电子票钱包交易总次数（计次）差异
        vo.setDiffElectTkDealNum(dbHelper.getItemIntValue("diff_elect_tk_deal_num"));//电子票钱包交易总数量（计值）差异
        vo.setDiffElectTkDealFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("diff_elect_tk_deal_fee")));//电子票钱包交易总金额（计值 差异

        vo.setDiffQrEntryNum(dbHelper.getItemIntValue("diff_qr_entry_num"));//二维码进站总数量差异
        vo.setDiffUpdateNonCashTctNum(dbHelper.getItemIntValue("diff_update_noncash_tct_num"));//非现金更新总数量差异
        
        vo.setDiffBomSaleTctNum(dbHelper.getItemIntValue("diff_bom_sale_tct_num"));//BOM实体次票发售总数量差异
        vo.setDiffBomSaleTctFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("diff_bom_sale_tct_fee")));//BOM实体次票发售总金额差异

        return vo;
    }

    protected int convertYuanToFen(BigDecimal yuan) {
        if (yuan == null) {
            yuan = new BigDecimal(0);
        }
        BigDecimal fen = yuan.multiply(new BigDecimal(100));
        fen.setScale(2);

        return fen.intValue();
    }

    private String convert(int n) {
        return new Integer(n).toString();
    }

    private void addRecord(Hashtable<String, Vector> ht, FileSDFVo vo) {
        String lineDate = vo.getLineId() + "#" + vo.getAccountDate();
        if (!ht.containsKey(lineDate)) {
            ht.put(lineDate, new Vector());
        }
        Vector v = ht.get(lineDate);
        Object[] record = new Object[39];
        record[0] = vo.getRecordVer();
        record[1] = vo.getLineId() + vo.getStationId();
        // record[1] = vo.getStationId();
        record[2] = vo.getDiffBomSaleSjtNum();// BOM单程票发售总数量差异    
        record[3] = vo.getDiffBomSaleSjtFee();// BOM单程票发售总金额差异 

        record[4] = vo.getDiffTvmSaleSjtNum();// TVM单程票发售总数量差异    
        record[5] = vo.getDiffTvmSaleSjtFee();// TVM单程票发售总金额差异  

        record[6] = vo.getDiffBomSaleNum();// BOM储值类票卡发售总数量差异
        record[7] = vo.getDiffBomSaleDepositFee();// BOM储值类票卡押金总金额差异
        record[8] = vo.getDiffBomChargeFee();// BOM充值总金额差异 

        record[9] = vo.getDiffTvmChargeNum();// TVM充值总数量差异          
        record[10] = vo.getDiffTvmChargeFee();//TVM充值总金额差异  

        record[11] = vo.getDiffReturnNum();//即时退款总数量差异         
        record[12] = vo.getDiffReturnFee();//即时退款总金额差异差异  

        record[13] = vo.getDiffNonRetNum();//非即时退款总数量差异       
        record[14] = vo.getDiffNonRetDepositFee();//非即时退款总押金差异       
        record[15] = vo.getDiffNonRetActualBala();//非即时退款总退还余额差异 

        record[16] = vo.getDiffNegativeChargeNum();//冲正总数量差异             
        record[17] = vo.getDiffNegativeChargeFee();//冲正总金额差异 

        record[18] = vo.getDiffDealNum();//出闸扣费总数量差异         
        record[19] = vo.getDiffDealFee();//出闸扣费总金额差异  

        record[20] = vo.getDiffUpdateCashNum();//现金更新总数量差异         
        record[21] = vo.getDiffUpdateCashFee();//现金更新总金额差异         
        record[22] = vo.getDiffUpdateNonCashNum();//非现金更新总数量差异       
        record[23] = vo.getDiffUpdateNonCashFee();//非现金更新总金额差异  

        record[24] = vo.getDiffAdminNum();//行政处理总数量差异         
        record[25] = vo.getDiffAdminReturnFee();//行政处理总支出金额差异     
        record[26] = vo.getDiffAdminPenaltyFee();  // 行政处理总收取金额差异

        record[27] = vo.getDiffItmSaleSjtNum();// ITM单程票发售总数量差异    
        record[28] = vo.getDiffItmSaleSjtFee();// ITM单程票发售总金额差异 
        record[29] = vo.getDiffItmChargeNum();// ITM充值总数量差异          
        record[30] = vo.getDiffItmChargeFee();//ITM充值总金额差异 

        record[31] = vo.getDiffQrDealNum();//二维码钱包交易总数量差异
        record[32] = vo.getDiffElectTkTctDealNum();//电子票钱包交易总次数（计次）差异
        record[33] = vo.getDiffElectTkDealNum();//电子票钱包交易总数量（计值）差异
        record[34] = vo.getDiffElectTkDealFee();//电子票钱包交易总金额（计值差异
        //hejj 20190830
        record[35] = vo.getDiffQrEntryNum();//二维码进站总数量差异
        //hejj 20191110
        record[36] = vo.getDiffUpdateNonCashTctNum();//非现金更新总数量差异
        //hejj 20200506
        record[37] = vo.getDiffBomSaleTctNum();// BOM实体次票发售总数量差异    
        record[38] = vo.getDiffBomSaleTctFee();// BOM实体次票发售总金额差异 

        v.add(record);
    }
}
