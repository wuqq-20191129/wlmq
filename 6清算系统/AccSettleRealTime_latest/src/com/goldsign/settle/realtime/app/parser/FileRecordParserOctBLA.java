/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.app.vo.FileRecordOctBLA;
import com.goldsign.settle.realtime.app.vo.FileRecordOctCW;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
public class FileRecordParserOctBLA extends FileRecordParserBase{
     @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecordOctBLA r = new FileRecordOctBLA();
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
    
    
    private FileRecord00DetailResult getInfoForRecord(FileRecordOctBLA r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 20;
        r.setCardLogicalIdStart(this.getCharString(b, offset, len));//开始名单逻辑卡号
        offset += len+1;
        
         len = 1;
        r.setSectFlag(this.getCharString(b, offset, len));//段号标志
        offset += len+1;
        
         len = 20;
        r.setCardLogicalIdEnd(this.getCharString(b, offset, len));//结束名单逻辑卡号
        offset += len+1;
        
         len = 2;
         r.setCardStatusApp(this.getCharString(b, offset, len));//票卡状态
        offset += len+1;
        
             
      


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
