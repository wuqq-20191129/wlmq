/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.thread;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFlowConstant;
import com.goldsign.settle.realtime.frame.constant.FrameOctFileImportConstant;

import com.goldsign.settle.realtime.frame.util.LoggerUtil;
import com.goldsign.settle.realtime.frame.util.TaskUtil;
import com.goldsign.settle.realtime.frame.util.TradeUtil;

import com.goldsign.settle.realtime.frame.vo.FileFindResult;
import java.io.File;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj 判断是否公交交易文件是否上传，如上传解压压缩包，通知可处理上传数据文件
 */
public class OctImportTrxThread extends OctImportThreadBase {

    private static Logger logger = Logger.getLogger(OctImportTrxThread.class.
            getName());

    public void run() {
        String zipPath = FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_ZIP;
        String unzipPath = FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_FILE;
        String zipPathHis = FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_FILE_HIS;
        String zipPathErr = FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_FILE_ERROR;
        FileFindResult ret;
        String prompMsg = "公交交易文件";
        String balanceWaterNo;
        String zipPathBalance;

        while (true) {
            try {
                //获取清算流水号
                balanceWaterNo = this.getBalanceWaterNo();
                //判断是否有返回的压缩文件
                zipPathBalance = TradeUtil.getDirForBalanceWaterNo(zipPath, balanceWaterNo, false);
                //判断是否有返回的压缩文件
                ret = this.existOctImportTrx(zipPathBalance);
                /*
                 if(!ret.isExisted()){
                 logger.info("公交交易数据没有返回或已处理："+zipPathBalance);
                 }
                 */
                if (ret.isExisted()) {

                    logger.info("公交交易数据返回，准备处理。。。");
                    logger.info("延时" + (INTERVAL_WAIT_FILE_FINISH / 1000) + "秒，预防交易文件没有写完整。");
                    this.sleep(INTERVAL_WAIT_FILE_FINISH);
                    //获取清算流水号
                    balanceWaterNo = this.getBalanceWaterNo();
                    //解压到指定目录
                    this.unzipFilesForTrx(balanceWaterNo, zipPathBalance, unzipPath, ret.getFiles(), prompMsg, zipPathHis, zipPathErr);
                    logger.info("完成公交交易数据解压");
                    //设置数据文件处理文件
                    this.setStartHandleFlag();
                    //add by hejj 20160129 记录设置流水
                    this.insertSet(balanceWaterNo, FrameOctFileImportConstant.OCT_IMPORT_FILE_TYPE_TRX, FrameOctFileImportConstant.OCT_IMPORT_NORMAL);

                } else {
                    //add by hejj 20160129 处理公交文件没有在规定时间内上传问题
                    this.octFileDelayHandle(balanceWaterNo);

                }



                this.threadSleep();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    private void octFileDelayHandle(String balanceWaterNo) {

        boolean isUnFinishPre = this.isUnFinishedStep(balanceWaterNo, FrameFlowConstant.STEP_FILE_EXPORT_OCT_TRX);
        if (isUnFinishPre)//文件导入的前面任务没有完成
        {
            return;
        }
        //文件导入已完成
        if (this.isHasSetted(balanceWaterNo, FrameOctFileImportConstant.OCT_IMPORT_FILE_TYPE_TRX)) {
            return;
        }
        if (!this.isOverImportTime(balanceWaterNo, FrameCodeConstant.SYS_SETTLE_OCT_IMPORT_TRX_LIMIT_TIME)) {
            return;
        }
        //强制设置可以进行文件导入，不再等待公交文件
        logger.error("清算流水号：" + balanceWaterNo + " 公交交易文件在规定时间没有上传，强制跳过文件导入步骤。");
        this.setStartHandleFlag();
        //记录设置流水
        this.insertSet(balanceWaterNo, FrameOctFileImportConstant.OCT_IMPORT_FILE_TYPE_TRX, FrameOctFileImportConstant.OCT_IMPORT_EXCEPTION);

    }

    private void setStartHandleFlag() {

        TaskUtil.setHandleOctImportTrx(true);
        LoggerUtil.loggerLineForSectAll(logger, "设置公交交易数据上传处理标识为：TRUE");
    }
}
