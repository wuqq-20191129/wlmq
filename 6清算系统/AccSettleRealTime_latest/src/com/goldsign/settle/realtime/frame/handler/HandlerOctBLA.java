/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.handler;

import com.goldsign.settle.realtime.app.dao.ExportOctAUSDao;
import com.goldsign.settle.realtime.app.vo.FileRecordOctCJ;
import com.goldsign.settle.realtime.app.vo.FileRecordOctBLA;
import com.goldsign.settle.realtime.app.vo.FileRecordOctTRX;
import com.goldsign.settle.realtime.frame.exception.FileNameException;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.util.FileNameParseUtil;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.FileData;
import com.goldsign.settle.realtime.frame.vo.FileRecordCrc;
import com.goldsign.settle.realtime.frame.vo.FileRecordHead;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class HandlerOctBLA extends HandlerOctBase{
     @Override
    public void checkFileContent(FileRecordHead fh, FileRecordCrc frc, String fileName, int rowCount, StringBuffer sb) throws RecordParseForFileException {
       
    }
    @Override
    public void parseFileName(String fileName) throws FileNameException {
        FileNameParseUtil util = new FileNameParseUtil();
        util.parseLen(fileName, FileNameParseUtil.FILE_LEN_OCT_IMPORT);
        util.parseFmtForOctImport(fileName, FileNameParseUtil.FILE_START_OCT_BLA);
        util.parseDate(fileName);

    }
     @Override
    public void handleMessage(MessageBase msg) {
        String fileName = msg.getFileName();
        String path = msg.getPath();
        String pathBcp = msg.getPathBcp();
        String balWaterNo = msg.getBalanceWaterNo();
        int balWaterNoSub = msg.getBalanceWaterNoSub();
        boolean isExistFileError = false;
        
        FileRecordOctCJ froa;
        String tradeType = this.getTradeTypeFromName(fileName);

        try {
            this.parseFileName(fileName);
            FileData fd = this.parseFile(path, fileName, balWaterNo,balWaterNoSub);//文件按交易类型整理
            
             //记录文件采集状态
            froa = this.getFileRecordOctAUS(fileName, balWaterNo, tradeType, fd.getContent());
            this.insertAuditStatus(froa);

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
     
     private void  insertAuditStatus(FileRecordOctCJ vo){
        ExportOctAUSDao  dao = new ExportOctAUSDao();
        try {
            int n = dao.insert(vo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
   
    
    private FileRecordOctCJ getFileRecordOctAUS(String fileName,String balanceWaterNo,
            String tradeType,HashMap<String, Vector> hm){
        FileRecordOctCJ froa = new FileRecordOctCJ();
        Vector v = hm.get(tradeType);
        int totalNumber = v.size();

        int totalFeeFen =0;
        BigDecimal totalFeeYuan;
        
        totalFeeYuan  =TradeUtil.convertFenToYuan(totalFeeFen);
        froa.setTotalFeeFen(totalFeeFen);
        froa.setTotalFeeYuan(totalFeeYuan);
        froa.setTotalNum(totalNumber);
        froa.setFileNameAcc(fileName);
        froa.setBalanceWaterNo(balanceWaterNo);
        return froa;
        
        
    }
    
}
