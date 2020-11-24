package com.goldsign.escommu.message;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.FileConstant;
import com.goldsign.escommu.env.LogConstant;
import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.exception.*;
import com.goldsign.escommu.ftp.CommuFtp;
import com.goldsign.escommu.util.*;
import com.goldsign.escommu.vo.SynchronizedControl;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author lenovo
 */
public class Message50 extends MessageBase {

    private static Logger logger = Logger.getLogger(Message50.class.getName());
    public static SynchronizedControl SYNCONTROL = new SynchronizedControl();

    public void run() throws Exception {
        String result = LogConstant.RESULT_HDL_SUCESS;
        this.level = AppConstant.LOG_LEVEL_INFO;
        try {
            this.hdlStartTime = System.currentTimeMillis();

            this.process();

            this.hdlEndTime = System.currentTimeMillis();
        } catch (Exception e) {
            result = LogConstant.RESULT_HDL_FAIL;
            this.hdlEndTime = System.currentTimeMillis();
            this.level = AppConstant.LOG_LEVEL_ERROR;
            this.remark = e.getMessage();
            throw e;
        } finally {//记录处理日志
            LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_FILE_NOTICE, this.messageFrom,
                    this.hdlStartTime, this.hdlEndTime, result, this.threadNum,
                    this.level, this.remark);
        }
    }

    public void process() throws Exception {
        DateHelper.screenPrint("处理" + MessageCommonUtil.getMessageName(MessageConstant.MESSAGE_ID_FILE_FTP) + "消息");
        FileUtil fileUtil = new FileUtil();
        String fileName = "";
        synchronized (SYNCONTROL) {
            try {
                String currentTod = this.getCharString(2, 14);
                String ip = CharUtil.trimStr(getCharString(16, 15));
                fileName = CharUtil.trimStr(this.getCharString(31, 24));

                //文件级校验
                fileUtil.checkFileByFileLevel(fileName);

                CommuFtp ftp = new CommuFtp();
                //文件获取
                ftp.ftpGetFileSingle(ip, AppConstant.FtpUserName, AppConstant.FtpUserPassword, fileName);

                //校验文件内容、生成BCP文件及BCP导入文件
                fileUtil.processFilesLegalForOne(fileName);
            } catch (CommuException e) {
                DateHelper.screenPrintForEx(DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date()) + "  " + thisClassName + " 错误! 消息序列号:" + messageSequ + ". " + e);
                logger.error(thisClassName + " 错误! 消息序列号:" + messageSequ + ". " + e);
                throw e;
            } catch (FileNameException e) {
                this.handleError(fileName, FileConstant.FILE_ERRO_CODE_FILE_NAME, e);
                throw e;
            } catch (FileFormatException e) {
                this.handleError(fileName, FileConstant.FILE_ERRO_CODE_FILE_FORMAT, e);
                throw e;
            } catch (FileCRCException e) {
                this.handleError(fileName, FileConstant.FILE_ERRO_CODE_FILE_CONTENT, e);
                throw e;
            } catch (FileTotalDetailNoSameException e) {
                this.handleError(fileName, FileConstant.FILE_ERRO_CODE_FILE_NOTSAME_TOTAL_DETAIL, e);
                throw e;
            } catch (FileFtpException e) {
                this.handleError(fileName, FileConstant.FILE_ERRO_CODE_FILE_NOTEXIST, e);
                throw e;
            } catch (FileRecordReapeatException e) {
                this.handleError(fileName, FileConstant.FILE_ERRO_CODE_FILE_REPEAT, e);
                throw e;
            }
        }
    }

    private void handleError(String fileName, String errorCode, Exception e) throws Exception {
        FileUtil fileUtil = new FileUtil();
        DateHelper.screenPrintForEx(DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date()) + "  " + thisClassName + " 错误! 消息:" + messageSequ + ". " + e);
        logger.error(thisClassName + " 错误! 消息:" + messageSequ + ". " + e);
        //记录FTP没有获取的文件
        fileUtil.processFilesIllegalForOne(fileName, errorCode);

    }
}
