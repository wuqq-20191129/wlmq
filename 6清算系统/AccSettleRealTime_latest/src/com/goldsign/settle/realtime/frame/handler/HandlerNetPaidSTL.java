/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.handler;

import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.exception.FileNameException;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.exception.RecordParseHeadForFileException;
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
public class HandlerNetPaidSTL extends HandlerBase {

    private static Logger logger = Logger.getLogger(HandlerNetPaidSTL.class.getName());

    @Override
    public void handleMessage(MessageBase msg) {
        String fileName = msg.getFileName();
        String path = msg.getPath();
        String pathBcp = msg.getPathBcp();
        String balWaterNo = msg.getBalanceWaterNo();
        int balWaterNoSub = msg.getBalanceWaterNoSub();
        boolean isExistFileError = false;

        try {
            this.parseFileName(fileName, balWaterNo);
            FileData fd = this.parseFile(path, fileName, balWaterNo, balWaterNoSub);//文件按交易类型整理

            this.writeFile(pathBcp, fileName, fd.getContent());//生成BCP文件
            Vector<MessageBase> msgsBcp = this.bcpFile(fileName, fd.getContent().keySet(), balWaterNo);
            this.isFinishWork(msgsBcp);
            //数据数据库校验及导入队列表
            this.bufToQueue(fileName, fd.getContent().keySet(), balWaterNo, balWaterNoSub);

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

    private void parseFileName(String fileName, String balanceWaterNo) throws FileNameException {
        FileNameParseUtil util = new FileNameParseUtil();
        util.parseLen(fileName, FileNameParseUtil.FILE_LEN_STL);
        util.parseFmtForOneOnlyLine(fileName, FileNameParseUtil.FILE_START_STL);
        util.parseDateIndexTwo(fileName, balanceWaterNo);
        util.parseRepeat(fileName);

    }

    private FileData parseFile(String path, String fileName, String balanceWaterNo, int balanceWaterNoSub) throws RecordParseForFileException {
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
        FileNameSection fileNameSec = FileUtil.getFileSectForOneOnlyLine(fileName);
        int iChar;
        StringBuffer fsb = new StringBuffer();
        String[] lines;
        FileRecordAddVo lineAdd = new FileRecordAddVo(balanceWaterNo, balanceWaterNoSub, fileName);
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
                    frh = this.parseFileHeadOtherOnlyLine(line, lineAdd);
                    this.addLineToBuffForHex(sb, line);
                    continue;
                }
                if (this.isFileCRC(line)) {
                    frc = this.parseFileCrc(line, lineAdd);
                    continue;
                }
                tradType = this.getTradType(fileNameSec);
                this.parseFileData(line, hm, lineAdd, tradType, n);
                this.addLineToBuffForHex(sb, line);
                rowCount++;
            }
            this.checkFileContent(frh, frc, fileName, rowCount, sb);//校验文件内容

            fd.setHead(frh);
            fd.setCrc(frc);
            fd.setContent(hm);

        } catch (RecordParseHeadForFileException e) {
            e.printStackTrace();
            throw new RecordParseForFileException("第" + e.getLine() + "行 " + e.getMessage(), e.getErrorCode());
        } catch (RecordParseForFileException e) {
            e.printStackTrace();
            throw new RecordParseForFileException("第" + n + "行 " + e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new RecordParseForFileException(e.getMessage(), FrameFileHandledConstant.FILE_ERR_FILE_UNKOWN[0]);
        } finally {
            this.closeFile(fis);

        }
        return fd;

    }

    private String getTradType(FileNameSection fileNameSect) throws Exception {
        return CLASS_PARSER_SHORT_NAME_NETPAID + fileNameSect.getTradType();

    }

    private void checkFileContent(FileRecordHead fh, FileRecordCrc frc, String fileName, int rowCount, StringBuffer sb) throws RecordParseHeadForFileException, RecordParseForFileException {
        FileNameSection sect = FileUtil.getFileSectForOneOnlyLine(fileName);
        this.checkFileHeadOneLine(fh, sect);
        this.checkFileRecordNum(fh, rowCount);
        this.checkFileCrc(frc, sb);
    }
}
