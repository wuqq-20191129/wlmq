/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00;
import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;

import com.goldsign.settle.realtime.app.vo.FileRecordSTL;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.HashMap;

/**
 *
 * @author hejj
 */
public class FileRecordParserSTL extends FileRecordParserBase {

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecordSTL r = new FileRecordSTL();

        FileRecord00DetailResult results;

        char[] b;
        int offset = 0;
        try {
            b = this.getLineCharFromFile(line);

            //获取公共信息
            results = this.getInfoForCommon(r, b, offset);
            offset = results.getOffsetTotal();

            //获取qr进站
            results = this.getInfoForQrEntry(r, b, offset);
            offset = results.getOffsetTotal();
            //获取次票非现金更新数量
            results = this.getInfoForUpdateNonCashTct(r, b, offset);
            offset = results.getOffsetTotal();

            //获取实体次票发售数量、金额
            results = this.getInfoForSaleTct(r, b, offset);
            offset = results.getOffsetTotal();

            //获取附加信息
            this.addCommonInfo(r, lineAdd);

        } catch (Exception ex) {
            throw new RecordParseForFileException(ex.getMessage());
        }

        return r;
    }

    private FileRecord00DetailResult getInfoForQrEntry(FileRecordSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result;
        if (this.isVersionOver12(r.getRecordVer())) {//12版本
            result = this.getInfoForQrEntryPart(r, b, offset);

        } else//其他版本
        {
            result = this.getInfoForQrEntryPartDefault(r, b, offset);
        }
        return result;
    }

    private FileRecord00DetailResult getInfoForUpdateNonCashTct(FileRecordSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result;
        if (this.isVersionOver13(r.getRecordVer())) {//13版本
            result = this.getInfoForUpdateNonCashTctFromFile(r, b, offset);

        } else//其他版本10/11/12
        {
            result = this.getInfoForUpdateNonCashTctFromDefault(r, b, offset);
        }
        return result;
    }

    private FileRecord00DetailResult getInfoForSaleTct(FileRecordSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result;
        if (this.isVersionOver14(r.getRecordVer())) {//14版本
            result = this.getInfoForSaleTctFromFile(r, b, offset);

        } else//其他版本10/11/12/13
        {
            result = this.getInfoForSaleTctFromDefault(r, b, offset);
        }
        return result;
    }

    private FileRecord00DetailResult getInfoForUpdateNonCashTctFromFile(FileRecordSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 4;
        r.setUpdateNonCashTctNum(this.getLong(b, offset));//TCT非现金更新数量
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForSaleTctFromFile(FileRecordSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 4;
        r.setBomSaleTctNum(this.getLong(b, offset));//TCT发售数量
        r.setBomSaleTctNum_s(Long.toString(r.getBomSaleTctNum()));//TCT发售数量
        offset += len;

        len = 4;
        r.setBomSaleTctFee(this.getLong(b, offset));//TCT发售金额
        r.setBomSaleTctFee_s(Long.toString(r.getBomSaleSjtFee()));//TCT发售金额
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForUpdateNonCashTctFromDefault(FileRecordSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 4;
        r.setUpdateNonCashTctNum(0);//TCT非现金更新数量
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForSaleTctFromDefault(FileRecordSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 4;
        r.setBomSaleTctNum(0);//TCT发售数量
        offset += len;

        len = 4;
        r.setBomSaleTctFee(0);//TCT发售金额
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForQrEntryPart(FileRecordSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 4;
        r.setQrEntryNum(this.getLong(b, offset));//二维码进站总数量
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForQrEntryPartDefault(FileRecordSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 4;
        r.setQrEntryNum(0);//二维码进站总数量
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForCommon(FileRecordSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setRecordVer(this.getCharString(b, offset, len));//版本号
        offset += len;

        len = 2;
        r.setLineId(this.getCharString(b, offset, len));//2线路代码
        offset += len;

        len = 2;
        r.setStationId(this.getCharString(b, offset, len));//2站点代码
        offset += len;

        len = 4;
        r.setBomSaleSjtNum(this.getLong(b, offset));//2BOM单程票发售总数量
        r.setBomSaleSjtNum_s(Long.toString(r.getBomSaleSjtNum()));
        offset += len;

        len = 4;
        r.setBomSaleSjtFee(this.getLong(b, offset));//3BOM单程票发售总金额
        r.setBomSaleSjtFee_s(TradeUtil.convertFenToYuan(r.getBomSaleSjtFee()).toString());
        offset += len;

        len = 4;
        r.setTvmSaleSjtNum(this.getLong(b, offset));//4TVM单程票发售总数量
        r.setTvmSaleSjtNum_s(Long.toString(r.getTvmSaleSjtNum()));
        offset += len;

        len = 4;
        r.setTvmSaleSjtFee(this.getLong(b, offset));//5TVM单程票发售总金额
        r.setTvmSaleSjtFee_s(TradeUtil.convertFenToYuan(r.getTvmSaleSjtFee()).toString());
        offset += len;

        len = 4;
        r.setBomSaleNum(this.getLong(b, offset));//6BOM储值类票卡发售总数量
        r.setBomSaleNum_s(Long.toString(r.getBomSaleNum()));
        offset += len;

        len = 4;
        r.setBomSaleDepositFee(this.getLong(b, offset));//7BOM储值类票卡押金总金额
        r.setBomSaleDepositFee_s(TradeUtil.convertFenToYuan(r.getBomSaleDepositFee()).toString());
        offset += len;

        len = 4;
        r.setBomChargeFee(this.getLong(b, offset));//8BOM充值总金额
        r.setBomChargeFee_s(TradeUtil.convertFenToYuan(r.getBomChargeFee()).toString());
        offset += len;

        len = 4;
        r.setTvmChargeNum(this.getLong(b, offset));//9TVM充值总数量
        r.setTvmChargeNum_s(Long.toString(r.getTvmChargeNum()));
        offset += len;

        len = 4;
        r.setTvmChargeFee(this.getLong(b, offset));//10TVM充值总金额
        r.setTvmChargeFee_s(TradeUtil.convertFenToYuan(r.getTvmChargeFee()).toString());
        offset += len;

        len = 4;
        r.setReturnNum(this.getLong(b, offset));//11即时退款总数量
        r.setReturnNum_s(Long.toString(r.getReturnNum()));
        offset += len;

        len = 4;
        r.setReturnFee(this.getLong(b, offset));//12即时退款总金额
        r.setReturnFee_s(TradeUtil.convertFenToYuan(r.getReturnFee()).toString());
        offset += len;

        len = 4;
        r.setNonRetNum(this.getLong(b, offset));//13非即时退款总数量
        r.setNonRetNum_s(Long.toString(r.getNonRetNum()));
        offset += len;

        len = 4;
        r.setNonRetDepositFee(this.getLong(b, offset));//14非即时退款总押金
        r.setNonRetDepositFee_s(TradeUtil.convertFenToYuan(r.getNonRetDepositFee()).toString());
        offset += len;

        len = 4;
        r.setNonRetActualBala(this.getLong(b, offset));//15非即时退款总退还余额
        r.setNonRetActualBala_s(TradeUtil.convertFenToYuan(r.getNonRetActualBala()).toString());
        offset += len;

        len = 4;
        r.setNegativeChargeNum(this.getLong(b, offset));//16冲正总数量
        r.setNegativeChargeNum_s(Long.toString(r.getNegativeChargeNum()));
        offset += len;

        len = 4;
        r.setNegativeChargeFee(this.getLong(b, offset));//17冲正总金额
        r.setNegativeChargeFee_s(TradeUtil.convertFenToYuan(r.getNegativeChargeFee()).toString());
        offset += len;

        len = 4;
        r.setDealNum(this.getLong(b, offset));//18出闸扣费总数量
        r.setDealNum_s(Long.toString(r.getDealNum()));
        offset += len;

        len = 4;
        r.setDealFee(this.getLong(b, offset));//19出闸扣费总金额
        r.setDealFee_s(TradeUtil.convertFenToYuan(r.getDealFee()).toString());
        offset += len;

        len = 4;
        r.setUpdateCashNum(this.getLong(b, offset));//20现金更新总数量
        r.setUpdateCashNum_s(Long.toString(r.getUpdateCashNum()));
        offset += len;

        len = 4;
        r.setUpdateCashFee(this.getLong(b, offset));//21现金更新总金额
        r.setUpdateCashFee_s(TradeUtil.convertFenToYuan(r.getUpdateCashFee()).toString());
        offset += len;

        len = 4;
        r.setUpdateNonCashNum(this.getLong(b, offset));//22非现金更新总数量
        r.setUpdateNonCashNum_s(Long.toString(r.getUpdateNonCashNum()));
        offset += len;

        len = 4;
        r.setUpdateNonCashFee(this.getLong(b, offset));//23非现金更新总金额
        r.setUpdateNonCashFee_s(TradeUtil.convertFenToYuan(r.getUpdateNonCashFee()).toString());
        offset += len;

        len = 4;
        r.setAdminNum(this.getLong(b, offset));//24行政处理总数量
        r.setAdminNum_s(Long.toString(r.getAdminNum()));
        offset += len;

        len = 4;
        r.setAdminReturnFee(this.getLong(b, offset));//25行政处理总支出金额
        r.setAdminReturnFee_s(TradeUtil.convertFenToYuan(r.getAdminReturnFee()).toString());
        offset += len;

        len = 4;
        r.setAdminPenaltyFee(this.getLong(b, offset));//26行政处理总收取金额
        r.setAdminPenaltyFee_s(TradeUtil.convertFenToYuan(r.getAdminPenaltyFee()).toString());
        offset += len;

        len = 4;
        r.setItmSaleSjtNum(this.getLong(b, offset));//27ITM发售数量
        r.setItmSaleSjtNum_s(Long.toString(r.getItmSaleSjtNum()));
        offset += len;

        len = 4;
        r.setItmSaleSjtFee(this.getLong(b, offset));//28ITM发售金额
        r.setItmSaleSjtFee_s(TradeUtil.convertFenToYuan(r.getItmSaleSjtFee()).toString());
        offset += len;

        len = 4;
        r.setItmChargeNum(this.getLong(b, offset));//29ITM充值数量
        r.setItmChargeNum_s(Long.toString(r.getItmChargeNum()));
        offset += len;

        len = 4;
        r.setItmChargeFee(this.getLong(b, offset));//30ITM充值金额
        r.setItmChargeFee_s(TradeUtil.convertFenToYuan(r.getItmChargeFee()).toString());
        offset += len;
        //20190628
        len = 4;
        r.setQrDealNum(this.getLong(b, offset));//出闸扣费总数量（二维码票）
        r.setQrDealNum_s(Long.toString(r.getQrDealNum()));
        offset += len;

        len = 4;
        r.setElectTkTctDealNum(this.getLong(b, offset));//出闸扣费总数量（电子票计次）
        r.setElectTkTctDealNum_s(Long.toString(r.getElectTkTctDealNum()));
        offset += len;

        len = 4;
        r.setElectTkDealNum(this.getLong(b, offset));//出闸扣费总数量（电子票计值）
        r.setElectTkDealNum_s(Long.toString(r.getElectTkDealNum()));
        offset += len;

        len = 4;
        r.setElectTkDealFee(this.getLong(b, offset));//出闸扣费总金额（电子票计值）
        r.setElectTkDealFee_s(TradeUtil.convertFenToYuan(r.getElectTkDealFee()).toString());
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean checkData(FileRecordBase frb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
