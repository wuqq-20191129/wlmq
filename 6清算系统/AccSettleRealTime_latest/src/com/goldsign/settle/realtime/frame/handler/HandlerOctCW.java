/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.handler;


import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.exception.FileNameException;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;

import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;

import com.goldsign.settle.realtime.frame.util.FileNameParseUtil;
import com.goldsign.settle.realtime.frame.util.FileUtil;
import com.goldsign.settle.realtime.frame.vo.FileData;
import com.goldsign.settle.realtime.frame.vo.FileNameSection;

import com.goldsign.settle.realtime.frame.vo.FileRecordCrc;
import com.goldsign.settle.realtime.frame.vo.FileRecordHead;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class HandlerOctCW extends HandlerOctBase{
     private static Logger logger = Logger.getLogger(HandlerOctCW.class.getName());
     
     /*
     公交返回错误文件与规范不一致，使用单换行符分隔记录，而非回车换行
     */
     @Override
    public void handleMessage(MessageBase msg) {
        String fileName = msg.getFileName();
        String path = msg.getPath();
        String pathBcp = msg.getPathBcp();
        String balWaterNo = msg.getBalanceWaterNo();
        int balWaterNoSub =msg.getBalanceWaterNoSub();
        boolean isExistFileError = false;

        try {
            this.parseFileName(fileName);
            FileData fd = this.parseFile(path, fileName, balWaterNo,balWaterNoSub);//文件按交易类型整理


            this.writeFile(pathBcp, fileName, fd.getContent());//生成BCP文件
            Vector<MessageBase> msgsBcp = this.bcpFile(fileName, fd.getContent().keySet(), balWaterNo);
            this.isFinishWork(msgsBcp);
            //数据数据库校验及导入队列表
           // this.bufToQueue(fileName, fd.getContent().keySet(), balWaterNo);

            this.writeFileNormalForOctReturn(balWaterNo, fileName);//记录正常处理的文件日志

        } catch (FileNameException ex) {
            //作为文件错误，需LC处理
            this.writeFileErrorForOctReturn(balWaterNo, fileName, ex.getErrorCode(), ex.getMessage());
            isExistFileError = true;

        } catch (RecordParseForFileException ex) {
            //作为文件错误，需LC处理
            this.writeFileErrorForOctReturn(msg.getBalanceWaterNo(), fileName, ex.getErrorCode(), ex.getMessage());
            isExistFileError = true;

        } catch (FileWriteException ex) {
            //删除已生成的BCP文件
            //记录异常，需ACC处理
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //文件移到历史目录
            this.movFile(msg, isExistFileError);
        }
    }
    @Override
    protected FileData parseFile(String path, String fileName, String balanceWaterNo,int balanceWaterNoSub) throws RecordParseForFileException {
        String fileNameFull = path + "/" + fileName;
        FileInputStream fis = null;
        int n = 0;
        //文件校验使用
        StringBuffer sb = new StringBuffer();
        int rowCount = 0;//文件的交易记录行数
        //文件处理结果
        FileData fd = new FileData();
        FileRecordHead frh = null;
        FileRecordCrc frc = null;
        HashMap<String, Vector> hm = new HashMap();
        FileNameSection fileNameSec = FileUtil.getFileSectForOct(fileName);
        int iChar;
        StringBuffer fsb = new StringBuffer();
        String[] lines;
        FileRecordAddVo lineAdd = new FileRecordAddVo(balanceWaterNo,balanceWaterNoSub, fileName);
        String tradType =FileUtil.getTradeTypeForOct(fileNameSec.getTradType());


        try {
            fis = new FileInputStream(fileNameFull);
            while ((iChar = fis.read()) != -1) {
                fsb.append((char) iChar);

            }
            lines = this.getLinesForText(fsb);
            for (String line : lines) {
                n++;
                /*
                if (this.isFileHead(n)) {
                    frh = this.parseFileHeadOther(line, lineAdd);
                    this.addLineToBuff(sb, line);
                    continue;
                }
                if (this.isFileCRC(line)) {
                    frc = this.parseFileCrc(line, lineAdd);
                    continue;
                }
                */
              //  tradType = this.getTradType(line);
                this.parseFileDataForUnique(line, hm, lineAdd, tradType,n);
               // this.addLineToBuff(sb, line);
                rowCount++;
            }
            this.checkFileContent(frh, frc, fileName, rowCount, sb);//校验文件内容

            fd.setHead(frh);
            fd.setCrc(frc);
            fd.setContent(hm);


        } catch (RecordParseForFileException e) {
            e.printStackTrace();
            throw new RecordParseForFileException(e.getMessage(), FrameFileHandledConstant.FILE_ERR_FILE_UNKOWN[0]);
        } catch (Exception e) {
            throw new RecordParseForFileException(e.getMessage(), FrameFileHandledConstant.FILE_ERR_FILE_UNKOWN[0]);
        } finally {
            this.closeFile(fis);

        }
        return fd;


    }
    @Override
    protected String[] getLinesForText(StringBuffer sb) {
        String allLines = new String(sb);
        // char[] cDelimit = {0x0D, 0x0A};
      /*
     公交返回错误文件与规范不一致，使用单换行符分隔记录，而非回车换行
     */
        String delimit = FrameCodeConstant.DELIMIT_TEXT_ONE;// "\\x0d\\x0a"; //new String(cDelimit);
        //String delimit = "\\u0d0a";
        String[] lines = allLines.split(delimit); //allLines.split("\r\n");
        return lines;
    }


    public void parseFileName(String fileName) throws FileNameException {
        FileNameParseUtil util = new FileNameParseUtil();
        util.parseLen(fileName, FileNameParseUtil.FILE_LEN_OCT_IMPORT);
        util.parseFmtForOctImport(fileName, FileNameParseUtil.FILE_START_OCT_ERR);
        util.parseDate(fileName);

    }

    public void checkFileContent(FileRecordHead fh, FileRecordCrc frc, String fileName, int rowCount, StringBuffer sb) throws RecordParseForFileException {
       
    }
    
}
