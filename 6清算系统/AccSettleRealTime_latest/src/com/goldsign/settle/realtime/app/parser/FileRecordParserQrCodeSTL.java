/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;

import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;

import com.goldsign.settle.realtime.app.vo.FileRecordQrCodeSTL;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserConstantBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
public class FileRecordParserQrCodeSTL extends FileRecordParserConstantBase {

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecordQrCodeSTL r = new FileRecordQrCodeSTL();

        FileRecord00DetailResult results;

        char[] b;
        int offset = 0;
        try {
            b = this.getLineCharFromFile(line);

            //获取公共信息
            results = this.getInfoForCommon(r, b, offset);
            offset = results.getOffsetTotal();
            //获取匹配信息
            results = this.getInfoForMatch(r, b, offset);
            offset = results.getOffsetTotal();
            
             //获取二维码发码平台信息20200706
            results = this.getInfoForIssueQrCodePlatformFlag(r, b, offset);
            offset = results.getOffsetTotal();

            //获取附加信息
            this.addCommonInfo(r, lineAdd);

        } catch (Exception ex) {
            throw new RecordParseForFileException(ex.getMessage());
        }

        return r;
    }
    protected FileRecord00DetailResult getInfoForIssueQrCodePlatformFlagFromFile(FileRecordQrCodeSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setIssueQrcodePlatformFlag(this.getCharString(b, offset, len));//发码平台标识
        offset += len;

        result.setOffsetTotal(offset);
        return result;

    }

    protected FileRecord00DetailResult getInfoForIssueQrCodePlatformFlagFromDefault(FileRecordQrCodeSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setIssueQrcodePlatformFlag(this.DEF_ISSUE_QRCODE_PLATFORM_FLAG);//发码平台标识
        offset += len;

        result.setOffsetTotal(offset);
        return result;

    }

    protected FileRecord00DetailResult getInfoForIssueQrCodePlatformFlag(FileRecordQrCodeSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        if (this.isVersionOver13(r.getRecordVer())) {//>=13版本
            result = this.getInfoForIssueQrCodePlatformFlagFromFile(r, b, offset);

        } else//10/11/12版本
        {
            result = this.getInfoForIssueQrCodePlatformFlagFromDefault(r, b, offset);
        }

        return result;

    }

    private FileRecord00DetailResult getInfoForCommon(FileRecordQrCodeSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setRecordVer(this.getCharString(b, offset, len));//记录版本
        offset += len;

        len = 2;
        r.setLineId(this.getCharString(b, offset, len));//2线路代码
        offset += len;

        len = 2;
        r.setStationId(this.getCharString(b, offset, len));//2站点代码
        offset += len;

        len = 4;
        r.setQrEntryum(this.getLong(b, offset));//二维码进站总数量
        offset += len;

        len = 4;
        r.setElectTkTctEntryNum(this.getLong(b, offset));//电子票进站总数量（计次）  
        offset += len;

        len = 4;
        r.setElectTkEntryNum(this.getLong(b, offset));//电子票进站总数量（计值）  
        offset += len;

        len = 4;
        r.setQrDealNum(this.getLong(b, offset));//二维码钱包交易总数量
        offset += len;

        len = 4;
        r.setQrDealFee(this.getLong(b, offset));//二维码钱包交易总金额     
        offset += len;

        len = 4;
        r.setElectTkTctDealNum(this.getLong(b, offset));//电子票钱包交易总次数（计次）
        offset += len;

        len = 4;
        r.setElectTkDealNum(this.getLong(b, offset));//电子票钱包交易总数量（计值）  
        offset += len;

        len = 4;
        r.setElectTkDealFee(this.getLong(b, offset));///电子票钱包交易总金额（计值  
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

    private FileRecord00DetailResult getInfoForMatch(FileRecordQrCodeSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result;
        if (this.isVersionOver12(r.getRecordVer())) {//12版本
            result = this.getInfoForMatchPart(r, b, offset);

        } else//其他版本
        {
            result = this.getInfoForMatchPartDefault(r, b, offset);
        }
        return result;
    }

    private FileRecord00DetailResult getInfoForMatchPart(FileRecordQrCodeSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 4;
        r.setQrMatchNum(this.getLong(b, offset));//二维码匹配总数量
        offset += len;

        len = 4;
        r.setQrMatchFee(this.getLong(b, offset));//二维码匹配总金额
        offset += len;

        len = 4;
        r.setQrMatchNotNum(this.getLong(b, offset));//二维码不匹配总数量
        offset += len;

        len = 4;
        r.setQrMatchNotFee(this.getLong(b, offset));//二维码不匹配总金额
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForMatchPartDefault(FileRecordQrCodeSTL r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 4;
        r.setQrMatchNum(0);//二维码匹配总数量
        offset += len;

        len = 4;
        r.setQrMatchFee(0);//二维码匹配总金额
        offset += len;

        len = 4;
        r.setQrMatchNotNum(0);//二维码不匹配总数量
        offset += len;

        len = 4;
        r.setQrMatchNotFee(0);//二维码不匹配总金额
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

}
