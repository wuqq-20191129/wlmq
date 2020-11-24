/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordCrc;

/**
 *
 * @author hejj
 */
public class FileRecordParserCrc extends FileRecordParserBase {

    @Override
    public Object parse(String line,FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecordCrc r = new FileRecordCrc();
        // byte[] b ;//line.getBytes();
        char[] b;
        int offset = 0;
        int len = 4;
        try {
            //b=this.getLineByteFromFileTest(line);
            // b=this.getLineByteFromFile(line);
            b = this.getLineCharFromFile(line);

            r.setCrcFlag(this.getCharString(b, offset, len));//CRC:标识
            offset += 4;
            len = 8;
            r.setCrc(this.getCharString(b, offset, len));//CRC值


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
