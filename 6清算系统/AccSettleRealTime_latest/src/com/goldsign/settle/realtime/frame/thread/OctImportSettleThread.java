/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.thread;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFlowConstant;
import com.goldsign.settle.realtime.frame.constant.FrameOctFileImportConstant;
import com.goldsign.settle.realtime.frame.dao.BusinessSettleDao;
import com.goldsign.settle.realtime.frame.dao.FileLogFtpDao;
import com.goldsign.settle.realtime.frame.dao.FileOctExportAndImportLog;
import com.goldsign.settle.realtime.frame.dao.OctReportDao;
import com.goldsign.settle.realtime.frame.util.FileUtil;

import com.goldsign.settle.realtime.frame.util.LoggerUtil;
import com.goldsign.settle.realtime.frame.util.MessageUtil;
import com.goldsign.settle.realtime.frame.util.TaskUtil;
import com.goldsign.settle.realtime.frame.util.TradeUtil;

import com.goldsign.settle.realtime.frame.vo.FileFindResult;
import com.goldsign.settle.realtime.frame.vo.OctExportAndImport;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import com.goldsign.settle.realtime.frame.vo.SettleQueueVo;
import com.goldsign.settle.realtime.frame.vo.StepInfo;
import com.goldsign.settle.realtime.frame.vo.TaskFinishControl;
import java.io.File;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 *
 * @author hejj 判断是否公交IC卡返回，如返回解压压缩包，通知可处理返回数据文件
 */
public class OctImportSettleThread extends OctImportThreadBase {
    private static int ERR_CODE_NO_BALANCE_WATER_NO = 1;
    private static int ERR_CODE_DOING = 2;
    private static int SLEEP_TIME = 60000;

    private static Logger logger = Logger.getLogger(OctImportSettleThread.class.
            getName());

    public void run() {
        String zipPath = FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_ZIP;
        String unzipPath = FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_FILE;
        String zipPathHis = FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_FILE_HIS;
        String zipPathErr = FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_FILE_ERROR;
        FileFindResult ret;
        String prompMsg = "公交结算文件";
        String balanceWaterNo = "";
        int balanceWaterNoSub = 0;
        String zipPathBalance;
        File[] files;
        //String importFlag = FrameOctFileImportConstant.FINISH_DONE;
        String fileName = "";

        while (true) {
            try {

                ret = this.existOctImportSettle(zipPath);

                if (ret.isExisted()) {
                    files = ret.getFiles();
                    for (File f : files) {
                        //从压缩文件名称生成清算流水号、子流水号
                        fileName = f.getName();
                        balanceWaterNo = this.getBalanceWaterNoFromFileName(f.getName());
                        zipPathBalance = TradeUtil.getDirForBalanceWaterNo(zipPath, balanceWaterNo, false);

                        logger.info("公交结算数据返回，准备处理。。。");
                        logger.info("延时" + (INTERVAL_WAIT_FILE_FINISH / 1000) + "秒，预防结算压缩文件没有写完整。");
                        this.sleep(INTERVAL_WAIT_FILE_FINISH);
                        //解压到指定目录
                        this.unzipFileForSettle(balanceWaterNo, zipPath, unzipPath, f, prompMsg, zipPathHis, zipPathErr);
                        logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + " 完成公交结算数据解压");
                        //导入文件
                        this.stepEImportExternalDataOctForSettle(balanceWaterNo, balanceWaterNoSub, null);
                        //记录导入日志
                        this.logExport(balanceWaterNo, balanceWaterNoSub, fileName, FrameOctFileImportConstant.FINISH_DONE);
                        //调用结算
                        this.settleOct(balanceWaterNo, balanceWaterNoSub, FrameCodeConstant.SETTLE_FINAL_NO);
                        logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + " 完成公交结算数据清算");

                        //生成报表
                        this.genOctReport(balanceWaterNo);
                        logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + " 完成公交报表设置");

                    }

                    //文件
                    //设置数据文件处理文件
                    //this.setStartHandleFlag();
                    //add by hejj 20160129 记录设置流水
                    // this.insertSet(balanceWaterNo, FrameOctFileImportConstant.OCT_IMPORT_FILE_TYPE_SETTLE, FrameOctFileImportConstant.OCT_IMPORT_NORMAL);
                    //  } else {
                    //add by hejj 20160129 处理公交文件没有在规定时间内上传问题
                    // this.octFileDelayHandle(balanceWaterNo);
                }

                this.threadSleep();
            } catch (Exception e) {

                this.logExport(balanceWaterNo, balanceWaterNoSub, fileName, FrameOctFileImportConstant.FINISH_ERROR);
                e.printStackTrace();
            } finally {
                //压缩文件、控制文件移至历史目录

            }

        }

    }

    private void genOctReport(String balanceWaterNo) throws Exception {

        OctReportDao dao = new OctReportDao();
        ResultFromProc result = dao.genOctReport(balanceWaterNo);
        int retCode = result.getRetCode();
        if (result.getRetCode() != 0) {
              //没有清算流水或当前清算流水正在生成报表，等待及重试
            while ( retCode == ERR_CODE_DOING) {
                logger.info("清算流水号："+balanceWaterNo+" 正在生成报表，等候"+(SLEEP_TIME/1000)+"秒再设置一卡通报表配置");
                Thread.sleep(SLEEP_TIME);//等待当前清算流水或正在生成报表完成
                result = dao.genOctReport(balanceWaterNo);
                retCode = result.getRetCode();
            }

            if (retCode !=0) {
                throw new Exception("设置一卡通对账报表生成配置错误：" + result.getRetMsg());
            }
            
          
        }
    }

    private void logExport(String balanceWaterNo, int balanceWaterNoSub, String fileName, String importFlag) {
        try {
            OctExportAndImport vo = this.getOctExportAndImport(balanceWaterNo, balanceWaterNoSub, fileName, importFlag);
            FileOctExportAndImportLog dao = new FileOctExportAndImportLog();
            dao.logFileOctExportAndImport(vo);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private OctExportAndImport getOctExportAndImport(String balanceWaterNo, int balanceWaterNoSub, String fileName, String importFlag) {
        OctExportAndImport vo = new OctExportAndImport();
        vo.setBalanceWaterNo(balanceWaterNo);
        vo.setBalanceWaterNoSub(balanceWaterNoSub);
        vo.setDoFlag(FrameOctFileImportConstant.DO_IMPORT);
        vo.setExportFileName(fileName);
        vo.setExportFlag(FrameOctFileImportConstant.FINISH_DONE);
        vo.setImportFileName(fileName);
        //
        // vo.setImportFlag(FrameOctFileImportConstant.FINISH_NOT);
        vo.setImportFlag(importFlag);
        return vo;

    }

    private void settleOct(String balanceWaterNo, int balanceWaterNoSub, String isFinal) throws Exception {
        SettleQueueVo queMsg = new SettleQueueVo(balanceWaterNo, balanceWaterNoSub, isFinal);
        BusinessSettleDao dao = new BusinessSettleDao();
        dao.businessSettleOct(queMsg);
    }

    protected Vector<TaskFinishControl> getTaskControl(int n) {
        return TaskUtil.getTaskControl(n);
    }

    private void stepEImportExternalDataOctForSettle(String balanceWaterNo, int balanceWaterNoSub, StepInfo si) throws Exception {

        Vector<TaskFinishControl> tfcs;
        logger.info(LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":开始处理公交返回结算结果。");
        tfcs = this.getTaskControl(1);
        this.processFilesForOctReturn(balanceWaterNo, balanceWaterNoSub, tfcs.get(0));//公交上传的交易数据文件

    }

    private void processFilesForOctReturn(String balanceWaterNo, int balanceWaterNoSub, TaskFinishControl tfc) {
        FileUtil util = new FileUtil();
        MessageUtil mUtil = new MessageUtil();
        String fileName;

        try {

            File[] filesOther = util.getFilesForOctReturn(FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_FILE);;//公交IC卡返回的文件目录

            if (filesOther == null || filesOther.length == 0) {
                logger.info("清算流水号：" + balanceWaterNo + "：公交IC卡返回文件需处理");
                return;
            }
            logger.info("清算流水号：" + balanceWaterNo + ":公交IC卡返回文件等处理数量：" + filesOther.length);
            Vector<TaskFinishControl> tfcs = TaskUtil.getTaskControl(filesOther.length);//获取任务控制
            for (int i = 0; i < filesOther.length; i++) {
                fileName = filesOther[i].getName();
                this.writeLogFtpForOct(fileName, (int) filesOther[i].length(), balanceWaterNo);//记录接收到的FTP文件日志
                mUtil.putMessageToQueueForFileOctImportSettle(fileName, balanceWaterNo, balanceWaterNoSub, tfcs.get(i));

            }
            TaskUtil.waitAllTaskFinish(tfcs, true, "任务：本批次公交IC卡返回文件处理");

        } catch (Exception ex) {
            ex.printStackTrace();;
        } finally {
            tfc.setFinished(true);
        }

    }

    private void writeLogFtpForOct(String fileName, int fileSize, String balanceWaterNo) throws Exception {
        FileLogFtpDao dao = new FileLogFtpDao();
        dao.insertForOct(fileName, fileSize, balanceWaterNo, balanceWaterNo);
    }

    private void octFileDelayHandle(String balanceWaterNo) {

        boolean isUnFinishPre = this.isUnFinishedStep(balanceWaterNo, FrameFlowConstant.STEP_FILE_EXPORT_OCT_SETTLE);
        if (isUnFinishPre)//文件导入的前面任务没有完成
        {
            return;
        }
        //文件导入已完成
        if (this.isHasSetted(balanceWaterNo, FrameOctFileImportConstant.OCT_IMPORT_FILE_TYPE_SETTLE)) {
            return;
        }
        if (!this.isOverImportTime(balanceWaterNo, FrameCodeConstant.SYS_SETTLE_OCT_IMPORT_SETTLE_LIMIT_TIME)) {
            return;
        }
        //强制设置可以进行文件导入，不再等待公交文件
        logger.error("清算流水号：" + balanceWaterNo + " 公交结算结果文件在规定时间没有上传，强制跳过文件导入步骤。");
        this.setStartHandleFlag();
        //记录设置流水
        this.insertSet(balanceWaterNo, FrameOctFileImportConstant.OCT_IMPORT_FILE_TYPE_SETTLE, FrameOctFileImportConstant.OCT_IMPORT_EXCEPTION);

    }

    private void setStartHandleFlag() {

        TaskUtil.setHandleOctImportSettle(true);
        LoggerUtil.loggerLineForSectAll(logger, "设置公交IC卡清算返回数据处理标识为：TRUE");
    }
}
