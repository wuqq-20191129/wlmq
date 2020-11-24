/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.file.parse;

import com.goldsign.settle.realtime.app.parser.FileRecordParserCrc;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.handler.HandlerBase;
import com.goldsign.settle.realtime.frame.util.CrcUtil;
import com.goldsign.settle.realtime.frame.vo.FileRecordCrc;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;


/**
 *
 * @author hejj
 */
public class FileParseBase {
    private static Logger logger = Logger.getLogger(HandlerBase.class.getName());
    
     protected String[] getLines(StringBuffer sb) {
        String allLines = new String(sb);
        // char[] cDelimit = {0x0D, 0x0A};
        String delimit = "\\x0d\\x0a"; //new String(cDelimit);
        //String delimit = "\\u0d0a";
        String[] lines = allLines.split(delimit); //allLines.split("\r\n");
        return lines;
    }
     protected boolean isFileHead(int i) {
        if (i == 1) {
            return true;
        }
        return false;
    }
     
     public char[] getLineCharFromFile(String line) throws Exception {
        if (line == null || line.length() == 0) {
            return null;
        }
        //return this.getCharArray(line);
        return line.toCharArray();//中文时，非原始数据20130811
    }
     public String getCharString(char[] data, int offset, int length) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            for (int i = 0; i < length; i++) {
                sb.append(data[offset + i]);
            }
        } catch (Exception e) {
            throw new Exception(" " + e);
        }
        return sb.toString();
    }
     protected void addLineToBuff(StringBuffer sb, String line) {
        sb.append(line);
        sb.append((char) 13);
        sb.append((char) 10);
    }
     
     protected boolean isFileCRC(String line) throws Exception {
        // byte[] b = this.getLineByteFromFileTest(line);
        char[] b = this.getLineCharFromFile(line);
        String crcFlag = this.getCharString(b, 0, 4);
        if (crcFlag.startsWith(HandlerBase.FLAG_CRC)) {
            return true;
        }
        return false;
    }
     
     protected FileRecordCrc parseFileCrc(String line, FileRecordAddVo lineAdd) throws Exception {
        FileRecordParserCrc parser = new FileRecordParserCrc();
        FileRecordCrc frc = (FileRecordCrc) parser.parse(line, lineAdd);
        return frc;
    }
     
     protected void checkFileCrc(FileRecordCrc frc, StringBuffer sb) throws RecordParseForFileException {
        try {
            // String crcCal = CrcUtil.getCRC32Value(sb, CrcUtil.CRC_LEN);
            String crcCal = CrcUtil.getCRC32ValueByChar(sb, CrcUtil.CRC_LEN);

            logger.info("系统计算CRC码：" + crcCal.toUpperCase() + " 文件计算的CRC:" + frc.getCrc().toUpperCase());
            if (!crcCal.equalsIgnoreCase(frc.getCrc())) {
                throw new RecordParseForFileException(FrameFileHandledConstant.FILE_ERR_FILE_CRC[1], FrameFileHandledConstant.FILE_ERR_FILE_CRC[0]);
            }
        } catch (Exception e) {
            throw new RecordParseForFileException(FrameFileHandledConstant.FILE_ERR_FILE_CRC[1], FrameFileHandledConstant.FILE_ERR_FILE_CRC[0]);
        }
    }
     
     protected void closeFile(FileInputStream fis) {
        try {
            if (fis != null) {
                fis.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    
}
