/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordHead;

/**
 *
 * @author hejj
 */
public class FileRecordParserHeadOther extends FileRecordParserBase{
    @Override
    public Object parse(String line,FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecordHead r = new FileRecordHead();
       // byte[] b = line.getBytes();
        // byte[] b =null; 
        char[] b=null;
        int offset = 0;
        int len = 2;
        try {
           // b=this.getLineByteFromFileTest(line);
             b=this.getLineCharFromFile(line);
             
            
            r.setLineId(this.getCharString(b, offset, len));//线路代码
            offset += len;
            
            len = 2;
            r.setStationId(this.getCharString(b, offset, len));//站点代码
            offset += len;
            
            len = 7;
            r.setOpenTime(this.getBcdString(b, offset, len));//文件打开时间
            offset += len;
            
            len = 7;
            r.setCloseTime(this.getBcdString(b, offset, len));//文件关闭时间
            offset += len;
            //r.setSeq(this.getShort(b, offset));//文件序列号
           // offset += 2;
            len=4;
            r.setRowCount(this.getLong(b, offset));//文件记录数
            offset += len;


        } catch (Exception ex) {
            throw new RecordParseForFileException(ex.getMessage());
        }

        return r;

    }

    @Override
    public void handleMessage(MessageBase msg) {
    }

    @Override
    public boolean checkData(FileRecordBase frb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

