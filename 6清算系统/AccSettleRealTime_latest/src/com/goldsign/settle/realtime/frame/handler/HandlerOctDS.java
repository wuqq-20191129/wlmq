/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.handler;


import com.goldsign.settle.realtime.frame.exception.FileNameException;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;

import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;

import com.goldsign.settle.realtime.frame.util.FileNameParseUtil;
import com.goldsign.settle.realtime.frame.vo.BcpAttribute;
import com.goldsign.settle.realtime.frame.vo.FileData;

import com.goldsign.settle.realtime.frame.vo.FileRecordCrc;
import com.goldsign.settle.realtime.frame.vo.FileRecordHead;
import com.goldsign.settle.realtime.frame.vo.MessageAttribute;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class HandlerOctDS extends HandlerOctBase{
    private static Logger logger = Logger.getLogger(HandlerOctDS.class.getName());


    
     public void parseFileName(String fileName) throws FileNameException {
        FileNameParseUtil util = new FileNameParseUtil();
        util.parseLen(fileName, FileNameParseUtil.FILE_LEN_OCT_IMPORT);
        util.parseFmtForOctImport(fileName, FileNameParseUtil.FILE_START_OCT_AUF);
        util.parseDate(fileName);

    }
    

    public void checkFileContent(FileRecordHead fh, FileRecordCrc frc, String fileName, int rowCount, StringBuffer sb) throws RecordParseForFileException {
       
    }
    private BcpAttribute getBcpAttribute(MessageBase msg){
        BcpAttribute ba =new BcpAttribute();
        MessageAttribute ma = msg.getMessageAttribute();
        
        if(ma !=null){
            ba.setAddSubTradeType(true);
            ba.setSubTradeType(ma.getSubTradeType());
        }
        return ba;
        
    }
    
    @Override
    public void handleMessage(MessageBase msg) {
        String fileName = msg.getFileName();
        String path = msg.getPath();
        String pathBcp = msg.getPathBcp();
        String balWaterNo = msg.getBalanceWaterNo();
        int balWaterNoSub = msg.getBalanceWaterNoSub();
        boolean isExistFileError = false;

        try {
            this.parseFileName(fileName);
            FileData fd = this.parseFile(path, fileName, balWaterNo,balWaterNoSub);//文件按交易类型整理


            this.writeFile(pathBcp, fileName, fd.getContent());//生成BCP文件
            BcpAttribute attr =this.getBcpAttribute(msg);
            Vector<MessageBase> msgsBcp = this.bcpFile(fileName, fd.getContent().keySet(), balWaterNo,attr);
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
    
}
