/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.handler;

import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.app.parser.FileRecordParserCrc;
import com.goldsign.settle.realtime.app.parser.FileRecordParserHead;
import com.goldsign.settle.realtime.app.vo.FileLogVo;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.constant.FrameCheckConstant;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.dao.BufferToQueueBaseDao;
import com.goldsign.settle.realtime.frame.dao.FileErrorDao;
import com.goldsign.settle.realtime.frame.dao.FileLogDao;
import com.goldsign.settle.realtime.frame.exception.FileNameException;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFieldException;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.exception.RecordParseHeadForFileException;
import com.goldsign.settle.realtime.frame.exception.TransferException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.util.CrcUtil;
import com.goldsign.settle.realtime.frame.util.FileNameParseUtil;
import com.goldsign.settle.realtime.frame.util.FileUtil;
import com.goldsign.settle.realtime.frame.util.MessageUtil;
import com.goldsign.settle.realtime.frame.vo.CheckControlVo;

import com.goldsign.settle.realtime.frame.vo.FileData;
import com.goldsign.settle.realtime.frame.vo.FileErrorVo;
import com.goldsign.settle.realtime.frame.vo.FileNameSection;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordCrc;
import com.goldsign.settle.realtime.frame.vo.FileRecordHead;
import com.goldsign.settle.realtime.frame.vo.FileRecordTacBase;

import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.IOException;


import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;


import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class HandlerTrx extends HandlerTrxBase {

    private static Logger logger = Logger.getLogger(HandlerTrx.class.getName());

    @Override
    public void handleMessage(MessageBase msg) {
        String fileName = msg.getFileName();
        String path = msg.getPath();
        String pathBcp = msg.getPathBcp();
        String balWaterNo = msg.getBalanceWaterNo();
        int balWaterNoSub =msg.getBalanceWaterNoSub();
        boolean isExistFileError = false;
        try {
            this.parseFileName(fileName,balWaterNo);
            FileData fd = this.parseFile(path, fileName, balWaterNo,balWaterNoSub);//文件按交易类型整理
            this.writeFile(pathBcp, fileName, fd.getContent());//生成BCP文件
            Vector<MessageBase> msgsBcp = this.bcpFile(fileName, fd.getContent().keySet(), balWaterNo);
            // Vector<MessageBase> msgsTac = new Vector();//this.checkTac(fileName, fd);//校验TAC码

            Vector<MessageBase> msgsTac = this.checkTac(fileName, fd);//校验TAC码
            this.isFinishWork(msgsBcp, msgsTac);
            logger.info("完成文件" + fileName + "的BCP导入及TAC码校验");
            //数据数据库校验及导入队列表
            this.bufToQueue(fileName, fd.getContent().keySet(), balWaterNo,balWaterNoSub);


            this.writeFileNormal(balWaterNo, fileName);//记录正常处理的文件日志

        } catch (FileNameException ex) {
            //作为文件错误，需LC处理
            logger.error(fileName + " 文件名称错误：" + ex.getMessage());
            this.writeFileError(balWaterNo, fileName, ex.getErrorCode(), ex.getMessage());
            isExistFileError = true;

        } catch (RecordParseForFileException ex) {
            //作为文件错误，需LC处理
            logger.error(fileName + " 文件内容解释错误：" + ex.getMessage());
            this.writeFileError(msg.getBalanceWaterNo(), fileName, ex.getErrorCode(), ex.getMessage());
            isExistFileError = true;

        } catch (FileWriteException ex) {
            //删除已生成的BCP文件
            //记录异常，需ACC处理
        } catch (TransferException ex) {
        } finally {
            //文件移到历史目录
            this.movFile(msg, isExistFileError);
        }

    }



    private void parseFileName(String fileName,String balanceWaterNo) throws FileNameException {
        FileNameParseUtil util = new FileNameParseUtil();
        util.parseLen(fileName, FileNameParseUtil.FILE_LEN_TRX);
        util.parseFmtForTwo(fileName, FileNameParseUtil.FILE_START_TRX);
        util.parseStation(fileName);
        util.parseDateIndexTwo(fileName,balanceWaterNo);
        util.parseRepeat(fileName);

    }





    private Vector<MessageBase> checkTac(String fileName, FileData fd) {
        MessageUtil mUtil = new MessageUtil();
        String fileNameBcp;
        Set<String> tradTypes = fd.getContent().keySet();
        Vector datas;
        Vector datasTac;
        MessageBase msg;
        Vector<MessageBase> msgs = new Vector();
        if (!this.isNeedCheckTac()) {//是否进行TAC校验
            logger.info("配置文件设置：不校验TAC码");
            return msgs;
        }
        for (String tradType : tradTypes) {
            if (!this.isNeedCheckTac(tradType)) {//过滤交易类型
                continue;
            }
            datas = fd.getContent().get(tradType);
            datasTac = this.getDatasForTac(datas);//获取地铁发行票卡的交易记录
            msg = mUtil.putMessageToQueueForTac(fileName, tradType, datasTac);
            msgs.add(msg);
        }
        return msgs;


    }

    


    private FileData parseFile(String path, String fileName, String balanceWaterNo,int balanceWaterNoSub) throws RecordParseForFileException {
        String fileNameFull = path + "/" + fileName;
        FileInputStream fis = null;
        // InputStreamReader isr = null;
        // BufferedReader br = null;
        //文件处理
        // String line = null;
        int n = 0;
        //文件校验使用
        StringBuffer sb = new StringBuffer();
        int rowCount = 0;//文件的交易记录行数
        //文件处理结果
        FileData fd = new FileData();
        FileRecordHead frh = null;
        FileRecordCrc frc = null;
        HashMap<String, Vector> hm = new HashMap();
        FileNameSection fileNameSec = FileUtil.getFileSectForTwo(fileName);
        int iChar;
        StringBuffer fsb = new StringBuffer();
        String[] lines;
        FileRecordAddVo lineAdd = new FileRecordAddVo(balanceWaterNo,balanceWaterNoSub, fileName);
        String tradType;
        String errMsg;


        try {
            fis = new FileInputStream(fileNameFull);
            while ((iChar = fis.read()) != -1) {
                fsb.append((char) iChar);


            }
            lines = this.getLinesForHex(fsb);
            //增加对回车换行符字段判断 add by hejj 20140506
            //不能从根本上解决问题，改为修改规范，换行符改为3个0x0d0a
           // lines = this.getLinesForTrx(fsb);
            for (String line : lines) {
                // while ((line = br.readLine()) != null) {
                n++;


                if (this.isFileHead(n)) {
                    frh = this.parseFileHead(line, lineAdd);
                    this.addLineToBuffForHex(sb, line);
                    continue;
                }
                if (this.isFileCRC(line)) {
                    frc = this.parseFileCrc(line, lineAdd);
                    continue;
                }
                tradType = this.getTradType(line);
                //调试
               //if(n==28)
                 //  logger.debug(line);
                
                this.parseFileDataForUnique(line, hm, lineAdd, tradType,n);
                this.addLineToBuffForHex(sb, line);
                // sb.append(line);
                rowCount++;
            }

            this.checkFileContent(frh, frc, fileName, rowCount, sb);//校验文件内容

            fd.setHead(frh);
            fd.setCrc(frc);
            fd.setContent(hm);


        }
        /*
        catch (RecordParseForFieldException e) {//非文件级记录解释错误,继续下一记录，不做文件级处理
            e.printStackTrace();
            errMsg ="第" + n + "行 " + e.getMessage();
            logger.error(errMsg);
            //记录错误字段日志
            this.writeFileErrorForField(balanceWaterNo, fileName, FrameFileHandledConstant.FILE_ERR_FILE_UNKOWN[0], errMsg);
            
        }
        */
        catch (RecordParseHeadForFileException e) {
            e.printStackTrace();
            throw new RecordParseForFileException("第" + e.getLine() + "行 " + e.getMessage(), e.getErrorCode());
        }
        catch (RecordParseForFileException e) {
            e.printStackTrace();
            throw new RecordParseForFileException("第" + n + "行 " + e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new RecordParseForFileException("第" + n + "行 " + e.getMessage(), FrameFileHandledConstant.FILE_ERR_FILE_UNKOWN[0]);
        } finally {
            this.closeFile(fis);

        }
        return fd;


    }

    private void checkFileContent(FileRecordHead fh, FileRecordCrc frc, String fileName, int rowCount, StringBuffer sb) throws RecordParseHeadForFileException, RecordParseForFileException {
        FileNameSection sect = FileUtil.getFileSectForTwo(fileName);
        this.checkFileHeadTwo(fh, sect);
        this.checkFileRecordNum(fh, rowCount);
        this.checkFileCrc(frc, sb);
    }
   
    /*
    protected void checkFileRecordNum(FileRecordHead fh, int rowCount) throws RecordParseForFileException {
        
    }
    */
    

    private String getTradType(String line) throws Exception {
        char[] b = this.getLineCharFromFile(line);
        String trdType = this.getCharString(b, 0, 2);
        return trdType;
    }
}
