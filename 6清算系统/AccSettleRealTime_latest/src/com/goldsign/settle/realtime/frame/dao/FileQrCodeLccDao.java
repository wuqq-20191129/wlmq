/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.FileMobileSDFVo;
import com.goldsign.settle.realtime.frame.vo.FileQrCodeSDFVo;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FileQrCodeLccDao {

    private static Logger logger = Logger.getLogger(FileMobileLccDao.class.getName());

    public Hashtable<String, Vector> getRecordsForAudit(String balanceWaterNo) throws Exception {
        /*
        String sql = "select WATER_NO,BALANCE_WATER_NO,LINE_ID ,STATION_ID,DIFF_DEAL_NUM ,"
                + "DIFF_DEAL_FEE ,DIFF_RETURN_NUM ,DIFF_RETURN_FEE,DIFF_SALE_NUM,DIFF_SALE_FEE,DIFF_LOCK_NUM,DIFF_UNLOCK_NUM,SQUAD_DAY,"
                + "diff_sale_sjt_num,diff_sale_sjt_fee,paid_channel_type_charge,paid_channel_code_charge,paid_channel_type_return,"
                + "paid_channel_code_return,paid_channel_type_sale_sjt,paid_channel_code_sale_sjt  "
                + " from " + FrameDBConstant.DB_PRE + "ST_MB_STS_LCC_ACC   "
                + "where BALANCE_WATER_NO=?  "
                + " order by SQUAD_DAY,LINE_ID,STATION_ID";
         */
        String sql = "select water_no,record_ver,line_id,station_id,diff_qr_entry_num,"
                + "diff_elect_tk_tct_entry_num,diff_elect_tk_entry_num,diff_qr_deal_num,diff_qr_deal_fee,diff_elect_tk_tct_deal_num,"
                + "diff_elect_tk_deal_num,diff_elect_tk_deal_fee,squad_day,balance_water_no,balance_water_no_sub,"
                + "diff_qr_match_num,diff_qr_match_fee,diff_qr_not_match_num,diff_qr_not_match_fee,issue_qrcode_platform_flag   "
                + " from " + FrameDBConstant.DB_PRE + "ST_QP_STS_LCC_ACC   "
                + "where BALANCE_WATER_NO=? and issue_qrcode_platform_flag=?  "
                + " order by SQUAD_DAY,LINE_ID,STATION_ID";
        DbHelper dbHelper = null;
        boolean result = false;
        Hashtable<String, Vector> files = new Hashtable();

        Object[] values = {balanceWaterNo,FrameCodeConstant.ISSUE_QRCODE_PLATFORM_XIAOMA};
        FileQrCodeSDFVo vo;
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

    private FileQrCodeSDFVo getVo(DbHelper dbHelper) throws SQLException {
        FileQrCodeSDFVo vo = new FileQrCodeSDFVo();
        vo.setLineId(dbHelper.getItemValue("LINE_ID"));//线路车站代码
        vo.setStationId(dbHelper.getItemValue("STATION_ID"));

        vo.setDiffQrEntryum(dbHelper.getItemIntValue("diff_qr_entry_num"));//二维码进站总数量
        vo.setDiffElectTkTctEntryNum(dbHelper.getItemIntValue("diff_elect_tk_tct_entry_num"));//电子票进站总数量（计次）
        vo.setDiffElectTkEntryNum(dbHelper.getItemIntValue("diff_elect_tk_entry_num"));//电子票进站总数量（计值）
        
        vo.setDiffQrDealNum(dbHelper.getItemIntValue("diff_qr_deal_num"));//二维码钱包交易总数量
        vo.setDiffQrDealFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("diff_qr_deal_fee")));//二维码钱包交易总金额
        vo.setDiffElectTkTctDealNum(dbHelper.getItemIntValue("diff_elect_tk_tct_deal_num"));//电子票钱包交易总次数（计次）
        vo.setDiffElectTkDealNum(dbHelper.getItemIntValue("diff_elect_tk_deal_num"));//电子票钱包交易总数量（计值）
        vo.setDiffElectTkDealFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("diff_elect_tk_deal_fee")));//电子票钱包交易总金额（计值 
        vo.setSquadDay(dbHelper.getItemValue("squad_day"));//对账日期add by hejj 20160623
        vo.setRecordVer(dbHelper.getItemValue("record_ver"));
        //20190830 hejj
        vo.setDiffQrMatchNum(dbHelper.getItemIntValue("diff_qr_match_num"));//二维码匹配总数量
        vo.setDiffQrMatchFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("diff_qr_match_fee")));//二维码匹配总金额
         vo.setDiffQrMatchNotNum(dbHelper.getItemIntValue("diff_qr_not_match_num"));//二维码单边总数量
        vo.setDiffQrMatchNotFee(this.convertYuanToFen(dbHelper.getItemBigDecimalValue("diff_qr_not_match_fee")));//二维码单边总金额
        
        vo.setIssueQrcodePlatformFlag(dbHelper.getItemValue("issue_qrcode_platform_flag"));//发码平台标识20200706

       

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

    private void addRecord(Hashtable<String, Vector> ht, FileQrCodeSDFVo vo) {
        String lineDate = FrameCodeConstant.LINE_ID_QRCODE + "#" + vo.getSquadDay();//add by hejj 20160623
        //String line = vo.getLineId();
        if (!ht.containsKey(lineDate)) {
            ht.put(lineDate, new Vector());
        }
        Vector v = ht.get(lineDate);
        Object[] record = new Object[15];
        record[0] = vo.getRecordVer();
        record[1] = vo.getLineId() + vo.getStationId();
        
        record[2] = vo.getDiffQrEntryum();// 二维码进站总数量差异
        record[3] = vo.getDiffElectTkTctEntryNum();// 电子票进站总数量（计次）差异
        record[4] = vo.getDiffElectTkEntryNum();// 电子票进站总数量（计值）差异

        record[5] = vo.getDiffQrDealNum();// 二维码钱包交易总数量差异   
        record[6] = vo.getDiffQrDealFee();//二维码钱包交易总金额差异
        record[7] = vo.getDiffElectTkTctDealNum();// 电子票钱包交易总次数（计次）差异
        record[8] = vo.getDiffElectTkDealNum();//电子票钱包交易总数量（计值）差异

        record[9] = vo.getDiffElectTkDealFee();// 电子票钱包交易总金额（计值）差异 
        
        record[10] = vo.getDiffQrMatchNum();// 二维码匹配数量差异   
        record[11] = vo.getDiffQrMatchFee();//二维码匹配总金额差异
        record[12] = vo.getDiffQrMatchNotNum();// 二维码单边总数量差异   
        record[13] = vo.getDiffQrMatchNotFee();//二维码单边总金额差异
        
         record[14] = vo.getIssueQrcodePlatformFlag();//发码平台标识
        
        
       

        v.add(record);
    }

}
