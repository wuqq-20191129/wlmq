/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.app.vo.FileRecordOctMD;
import com.goldsign.settle.realtime.app.vo.FileRecordOctCW;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
public class FileRecordParserOctMD extends FileRecordParserBase {
   

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecordOctMD r = new FileRecordOctMD();
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

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private FileRecord00DetailResult getInfoForRecord(FileRecordOctMD r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();


        int len = 16;
        r.setCardLogicalId(this.getCharString(b, offset, len));//逻辑卡号
        offset += len + 1;

       





        result.setOffsetTotal(offset);
        return result;
    }

    @Override
    public boolean checkData(FileRecordBase frb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
