/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.file.parse;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord55;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.handler.HandlerBase;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.util.FileUtil;
import com.goldsign.settle.realtime.frame.vo.FileData;
import com.goldsign.settle.realtime.frame.vo.FileNameSection;
import com.goldsign.settle.realtime.frame.vo.FileRecordCrc;
import com.goldsign.settle.realtime.frame.vo.FileRecordHead;
import com.goldsign.settle.realtime.test.vo.FileRecordCard;
import com.goldsign.settle.realtime.test.vo.FileRecordHeadCard;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hejj
 */
public class FileCardLogicAndPhy extends FileParseBase{
    
    private FileData parseFile(String path, String fileName) throws RecordParseForFileException {
        String fileNameFull = path + "/" + fileName;
        FileInputStream fis = null;
        int n = 0;
        //文件校验使用
        StringBuffer sb = new StringBuffer();
        int rowCount = 0;//文件的交易记录行数
        //文件处理结果
        FileData fd = new FileData();
        FileRecordHeadCard frh = null;
        FileRecordCrc frc = null;
        HashMap<String, Vector> hm = new HashMap();
       // FileNameSection fileNameSec = FileUtil.getFileSectForOneOnlyLine(fileName);
        int iChar;
        StringBuffer fsb = new StringBuffer();
        String[] lines;
        FileRecordAddVo lineAdd = new FileRecordAddVo("",0, fileName);
        String tradType;


        try {
            fis = new FileInputStream(fileNameFull);
            while ((iChar = fis.read()) != -1) {
                fsb.append((char) iChar);

            }
            lines = this.getLines(fsb);
            for (String line : lines) {
                n++;
                if (this.isFileHead(n)) {
                    frh = this.parseHead(line, lineAdd);
                    this.addLineToBuff(sb, line);
                    continue;
                }
                if (this.isFileCRC(line)) {
                    frc = this.parseFileCrc(line, lineAdd);
                    continue;
                }
                //tradType = this.getTradType(fileNameSec);
                this.parseFileData(line,lineAdd);
                this.addLineToBuff(sb, line);
                rowCount++;
            }
            
            this.checkFileCrc(frc, sb);

            


        } catch (RecordParseForFileException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            throw new RecordParseForFileException(e.getMessage(), FrameFileHandledConstant.FILE_ERR_FILE_UNKOWN[0]);
        } finally {
            this.closeFile(fis);

        }
        return fd;


    }

    public FileRecordHeadCard parseHead(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecordHeadCard r = new FileRecordHeadCard();
        // byte[] b = line.getBytes();
        // byte[] b =null; 
        char[] b = null;
        int offset = 0;
        int len = 2;
        try {
            // b=this.getLineByteFromFileTest(line);
            b = this.getLineCharFromFile(line);


            r.setFacId(this.getCharString(b, offset, len));//封装厂商
            offset += len;
                     
             len = 4;
             r.setBatchNo(this.getCharString(b, offset, len));//年份+批次
             offset += len;
             
             len = 8;
             r.setRowCount(Integer.parseInt(this.getCharString(b, offset, len)));//记录数
             offset += len;
             
            


        } catch (Exception ex) {
            throw new RecordParseForFileException(ex.getMessage());
        }

        return r;

    }
    public Object parseFileData(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
       FileRecordCard r = new FileRecordCard();
        FileRecord00DetailResult result;
        char[] b;
        int offset = 0;
        int len =20;

        try {
            b = this.getLineCharFromFile(line);

            r.setCardPhyId(this.getCharString(b, offset, len));//物理卡号
            offset += len;
                     
             len = 20;
             r.setCardLogiId(this.getCharString(b, offset, len));//逻辑卡号
             offset += len;







        } catch (Exception ex) {
            throw new RecordParseForFileException(ex.getMessage());
        }

        return r;
    }
    
    public static void main(String[] args){
        FileCardLogicAndPhy fcl = new FileCardLogicAndPhy();
        String path="D:/工作_铭鸿/长沙地铁/工程管理/支持类/中山达华提供的文件";
        String fileName="PRT021301.000001.txt";
        try {
            fcl.parseFile(path, fileName);
        } catch (RecordParseForFileException ex) {
            Logger.getLogger(FileCardLogicAndPhy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
