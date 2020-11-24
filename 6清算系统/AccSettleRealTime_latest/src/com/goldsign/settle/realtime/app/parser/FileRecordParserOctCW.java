/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecordOctCW;
import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord51;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
public class FileRecordParserOctCW extends FileRecordParserBase {

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecordOctCW r = new FileRecordOctCW();
        FileRecord00DetailResult result;
        char[] b;
        int offset = 0;

        try {
            b = this.getLineCharFromFile(line);

            //获取设备、票卡相关信息
            result = this.getInfoForRecord(r, b, offset);
            offset = result.getOffsetTotal();

            //获取附加信息
            this.addCommonInfo(r, lineAdd);

        } catch (Exception ex) {
            throw new RecordParseForFileException(ex.getMessage());
        }

        return r;
    }

    private FileRecord00DetailResult getInfoForRecord(FileRecordOctCW r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 32;
        r.setFileName(this.getCharString(b, offset, len));//文件名
        offset += len + 1;
        
        len = 8;
        r.setSerialNo(this.getCharString(b, offset, len));//序列号
        offset += len + 1;

        len = 16;
        r.setSamLogicalId(this.getCharString(b, offset, len));//终端编号
        offset += len + 1;

        len = 1;
        r.setTerminalFlag(this.getCharString(b, offset, len));//终端标志
        offset += len + 1;

        len = 14;
        r.setDealDatetime(this.getCharString(b, offset, len));//交易时间
        offset += len + 1;

        len = 8;
        r.setSamTradeSeq_s(this.getCharString(b, offset, len));//终端交易流水号
        offset += len + 1;

        len = 16;
        r.setCardLogicalId(this.getCharString(b, offset, len));//票卡逻辑卡号
        offset += len + 1;

        len = 16;
        r.setCardPhysicalId(this.getCharString(b, offset, len));//票卡物理卡号
        offset += len + 1;

        len = 4;
        r.setCardMainId(this.getCharString(b, offset, len));//票卡主类型
        offset += len + 1;

        len = 4;
        r.setCardSubId(this.getCharString(b, offset, len));//票卡子类型
        offset += len + 1;

        len = 16;
        r.setLastSamLogicalId(this.getCharString(b, offset, len));//上次交易设备编号
        offset += len + 1;

        len = 14;
        r.setLastDealDatetime(this.getCharString(b, offset, len));//上次交易日期时间
        offset += len + 1;

        len = 8;
        r.setDealFee_s(this.getCharString(b, offset, len));//交易金额
        offset += len + 1;

        len = 8;
        r.setDealBalance_s(this.getCharString(b, offset, len));//本次余额
        offset += len + 1;

        len = 8;
        r.setDealNoDiscountFee_s(this.getCharString(b, offset, len));//原票价
        offset += len + 1;

        len = 2;
        r.setPayModeId(this.getCharString(b, offset, len));//交易类型
        offset += len + 1;

        len = 16;
        r.setEntrySamLogicalId(this.getCharString(b, offset, len));//本次入口设备编号
        offset += len + 1;

        len = 14;
        r.setEntryDatetime(this.getCharString(b, offset, len));//本次入口日期时间
        offset += len + 1;

        len = 6;
        r.setCardChargeSeq_s(this.getCharString(b, offset, len));//票卡联机交易计数
        offset += len + 1;

        len = 6;
        r.setCardConsumeSeq_s(this.getCharString(b, offset, len));//票卡脱机交易计数
        offset += len + 1;

        len = 8;
        r.setTac(this.getCharString(b, offset, len));//交易认证码
        offset += len + 1;

        len = 4;
        r.setBusCityCode(this.getCharString(b, offset, len));//城市编码
        offset += len + 1;

        len = 4;
        r.setBusBusinessCode(this.getCharString(b, offset, len));//行业代码
        offset += len + 1;

        len = 4;
        r.setKeyVersion(this.getCharString(b, offset, len));//密钥标示
        offset += len + 1;

        len = 16;
        r.setReserveField(this.getCharString(b, offset, len));//预留字段
        offset += len + 1;

        len = 14;
        r.setLastChargeDatetime(this.getCharString(b, offset, len));//最后充值时间
        offset += len + 1;

        len = 2;
        r.setErrorCode(this.getCharString(b, offset, len));//错误类型
        offset += len + 1;

        

        //设置通用金额、余额
        r.setCommonFeeByYuan(r.getDealFee_s(), null);

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
