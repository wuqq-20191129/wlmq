/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.handler;

import com.goldsign.settle.realtime.app.vo.FileRecord31;

import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.dao.FileRecordSeqDao;
import com.goldsign.settle.realtime.frame.exception.FileNameException;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.exception.RecordParseHeadForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.FileNameParseUtil;
import com.goldsign.settle.realtime.frame.util.FileUtil;
import com.goldsign.settle.realtime.frame.vo.FileData;
import com.goldsign.settle.realtime.frame.vo.FileNameSection;
import com.goldsign.settle.realtime.frame.vo.FileRecordCrc;
import com.goldsign.settle.realtime.frame.vo.FileRecordHead;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class HandlerAUD extends HandlerBase {

    private static Logger logger = Logger.getLogger(HandlerAUD.class.getName());

    @Override
    public void handleMessage(MessageBase msg) {
        String fileName = msg.getFileName();
        String path = msg.getPath();
        String pathBcp = msg.getPathBcp();
        String balWaterNo = msg.getBalanceWaterNo();
        int balWaterNoSub = msg.getBalanceWaterNoSub();
        boolean isExistFileError = false;

        try {
            this.parseFileName(fileName,balWaterNo);
            FileData fd = this.parseFile(path, fileName, balWaterNo,balWaterNoSub);//文件按交易类型整理

            //获取序列号作为记录流水号,添加到记录及记录明细中，agm/tvm区别处理
            // this.delete_addInfo(fd);
            //添加记录明细 agm/tvm区别处理
            //this.delete_addDetail(fd);

            //获取序列号作为记录流水号,添加到记录及记录明细中
            this.addSubRecordVSeq(fd);
            //添加记录明细 
            this.addSubRecordsToBuff(fd);

            this.writeFile(pathBcp, fileName, fd.getContent());//生成BCP文件
            Vector<MessageBase> msgsBcp = this.bcpFile(fileName, fd.getContent().keySet(), balWaterNo);
            this.isFinishWork(msgsBcp);
            //数据数据库校验及导入队列表
            this.bufToQueue(fileName, fd.getContent().keySet(), balWaterNo,balWaterNoSub);

            this.writeFileNormal(balWaterNo, fileName);//记录正常处理的文件日志

        } catch (FileNameException ex) {
            //作为文件错误，需LC处理
            this.writeFileError(balWaterNo, fileName, ex.getErrorCode(), ex.getMessage());
            isExistFileError = true;

        } catch (RecordParseForFileException ex) {
            //作为文件错误，需LC处理
            this.writeFileError(msg.getBalanceWaterNo(), fileName, ex.getErrorCode(), ex.getMessage());
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

    private FileData parseFile(String path, String fileName, String balanceWaterNo,int balanceWaterNoSub) throws RecordParseForFileException {
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
        FileNameSection fileNameSec = FileUtil.getFileSectForOneLineStation(fileName);
        int iChar;
        StringBuffer fsb = new StringBuffer();
        String[] lines;
        FileRecordAddVo lineAdd = new FileRecordAddVo(balanceWaterNo,balanceWaterNoSub, fileName);
        String tradType;


        try {
            fis = new FileInputStream(fileNameFull);
            while ((iChar = fis.read()) != -1) {
                fsb.append((char) iChar);

            }
            lines = this.getLinesForHex(fsb);
            for (String line : lines) {
                n++;
                if (this.isFileHead(n)) {
                    frh = this.parseFileHeadOtherLineStation(line, lineAdd);
                    this.addLineToBuffForHex(sb, line);
                    continue;
                }
                if (this.isFileCRC(line)) {
                    frc = this.parseFileCrc(line, lineAdd);
                    continue;
                }
                tradType = this.getTradType(line);
                this.parseFileData(line, hm, lineAdd, tradType,n);
                this.addLineToBuffForHex(sb, line);
                rowCount++;
            }
            this.checkFileContent(frh, frc, fileName, rowCount, sb);//校验文件内容

            fd.setHead(frh);
            fd.setCrc(frc);
            fd.setContent(hm);


        }
         catch (RecordParseHeadForFileException e) {
            e.printStackTrace();
            throw new RecordParseForFileException("第" + e.getLine() + "行 " + e.getMessage(), e.getErrorCode());
        }
        catch (RecordParseForFileException e) {
            e.printStackTrace();
            throw new RecordParseForFileException("第" + n + "行 " + e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new RecordParseForFileException(e.getMessage(), FrameFileHandledConstant.FILE_ERR_FILE_UNKOWN[0]);
        } finally {
            this.closeFile(fis);

        }
        return fd;


    }

    protected void parseFileDataForAudit(String line, HashMap<String, Vector> hm, FileRecordAddVo lineAdd, String trdType) throws RecordParseForFileException {
        // this.parseFileData(line, hm, lineAdd, trdType);

        try {

            Object ob;
            String trdTypeSub;
            String className = HandlerBase.CLASS_PARSER_PRIX + trdType;
            ob = ((FileRecordParserBase) Class.forName(className).newInstance()).parse(line, lineAdd);
            this.putMap(trdType, ob, hm);
            trdTypeSub = trdType + HandlerAUD.TRAD_SUFIX[0];
            FileRecord31 fr = (FileRecord31) ob;
            Vector v = fr.getDealDetail();
            this.putMap(trdTypeSub, v, hm);

        } catch (Exception e) {
            throw new RecordParseForFileException(e.getMessage());
        }

    }

    private String getTradType(String line) throws Exception {
        char[] b = this.getLineCharFromFile(line);
        String trdType = this.getCharString(b, 0, 2);
        return trdType;
    }

    private void parseFileName(String fileName,String balanceWaterNo) throws FileNameException {
        FileNameParseUtil util = new FileNameParseUtil();
        util.parseLen(fileName, FileNameParseUtil.FILE_LEN_AUD);
        util.parseFmtForOneLineStation(fileName, FileNameParseUtil.FILE_START_AUD);
        util.parseDateIndexTwo(fileName,balanceWaterNo);
        util.parseRepeat(fileName);

    }

    private void checkFileContent(FileRecordHead fh, FileRecordCrc frc, String fileName, int rowCount, StringBuffer sb) throws RecordParseForFileException, RecordParseHeadForFileException {
        FileNameSection sect = FileUtil.getFileSectForOneLineStation(fileName);
        this.checkFileHeadOne(fh, sect);
        this.checkFileRecordNum(fh, rowCount);
        this.checkFileCrc(frc, sb);
    }
}
