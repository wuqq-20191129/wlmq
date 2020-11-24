/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.handler;

import com.goldsign.settle.realtime.app.dao.ExportOctAUSDao;
import com.goldsign.settle.realtime.app.vo.FileRecordOctCJ;
import com.goldsign.settle.realtime.app.vo.FileRecordOctTRX;
import com.goldsign.settle.realtime.frame.constant.FrameCheckConstant;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.exception.FileNameException;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.util.FileNameParseUtil;
import com.goldsign.settle.realtime.frame.util.MessageUtil;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.CheckControlVo;
import com.goldsign.settle.realtime.frame.vo.FileData;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordCrc;
import com.goldsign.settle.realtime.frame.vo.FileRecordHead;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class HandlerOctTRX extends HandlerOctBase {

    private static Logger logger = Logger.getLogger(HandlerOctTRX.class.getName());

    @Override
    public void checkFileContent(FileRecordHead fh, FileRecordCrc frc, String fileName, int rowCount, StringBuffer sb) throws RecordParseForFileException {
    }

    @Override
    public void parseFileName(String fileName) throws FileNameException {
        FileNameParseUtil util = new FileNameParseUtil();
        util.parseLen(fileName, FileNameParseUtil.FILE_LEN_OCT_IMPORT);
        util.parseFmtForOctImport(fileName, FileNameParseUtil.FILE_START_OCT_TRX);
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
           // FileData fd = this.parseFile(path, fileName, balWaterNo);//文件按交易类型整理
            FileData fd = this.parseFileForLarge(path, fileName, balWaterNo,balWaterNoSub);//大的文件按交易类型整理 modify by hejj 20150501

            //记录文件采集状态
            froa = this.getFileRecordOctAUS(fileName, balWaterNo, tradeType, fd.getContent());
            this.insertAuditStatus(froa);
            
            this.writeFile(pathBcp, fileName, fd.getContent());//生成BCP文件
            Vector<MessageBase> msgsBcp = this.bcpFile(fileName, fd.getContent().keySet(), balWaterNo);
            Vector<MessageBase> msgsTac = this.checkTac(fileName, fd);//校验TAC码

            this.isFinishWork(msgsBcp, msgsTac);
            logger.info("完成文件" + fileName + "的BCP导入及TAC码校验");
            //数据数据库校验及导入队列表
            this.bufToQueue(fileName, fd.getContent().keySet(), balWaterNo,balWaterNoSub);

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
        FileRecordOctTRX vo;
        int totalFeeFen =0;
        BigDecimal totalFeeYuan;
        for(int i=0;i<v.size();i++){
            vo = (FileRecordOctTRX)v.get(i);
            totalFeeFen +=vo.getTradeFeeFen();
            
        }
        totalFeeYuan  =TradeUtil.convertFenToYuan(totalFeeFen);
        froa.setTotalFeeFen(totalFeeFen);
        froa.setTotalFeeYuan(totalFeeYuan);
        froa.setTotalNum(totalNumber);
        froa.setFileNameAcc(fileName);
        froa.setBalanceWaterNo(balanceWaterNo);
        return froa;
        
        
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

    private Vector<FileRecordBase> getDatasForTac(Vector<FileRecordBase> datas) {
        Vector<FileRecordBase> datasTac = new Vector();
        for (FileRecordBase frb : datas) {
            if (!this.isCardForMetro(frb.getCardMainId())) {
                continue;
            }
            datasTac.add(frb);
        }


        return datasTac;
    }

    private boolean isCardForMetro(String cardMainId) {
        for (String tmp : FrameCodeConstant.CARD_MAIN_TYPE_METRO) {
            tmp.equals(cardMainId);
            return true;
        }
        return false;
    }

    private boolean isNeedCheckTac(String trdType) {
        // if(!this.isCardForMetro(cardMainId))
        //   return false;

        for (String trdTypeCheck : FrameCodeConstant.TRX_TYPES_TAC_CHECK) {
            if (trdType.equals(trdTypeCheck)) {
                return true;
            }
        }
        return false;

    }

    private boolean isNeedCheckTac() {
        if (FrameCheckConstant.CHECK_CONTROLS == null || FrameCheckConstant.CHECK_CONTROLS.isEmpty()) {
            return false;
        }
        String keyTac = FrameFileHandledConstant.RECORD_ERR_TAC[0] + FrameCheckConstant.CHECK_FLAG_YES;
        String key = "";
        for (CheckControlVo vo : FrameCheckConstant.CHECK_CONTROLS) {
            key = vo.getChkId() + vo.getValidFlag();
            if (keyTac.equals(key)) {
                return true;
            }
        }
        return false;
    }
}
