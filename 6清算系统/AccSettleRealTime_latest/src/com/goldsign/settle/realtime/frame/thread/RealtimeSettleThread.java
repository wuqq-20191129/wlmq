/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.thread;

import com.goldsign.settle.realtime.frame.application.AppSettleBase;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameSynConstant;
import com.goldsign.settle.realtime.frame.dao.BusinessSettleDao;
import com.goldsign.settle.realtime.frame.dao.SettleQueueDao;
import com.goldsign.settle.realtime.frame.util.LoggerUtil;
import com.goldsign.settle.realtime.frame.vo.SettleQueueVo;
import com.goldsign.settle.realtime.frame.vo.StepInfo;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class RealtimeSettleThread extends Thread {

    private static String MESSAGE_NOT_FINISH_NOT_ERR = "00";//消息没有完成并且没有错误

    public static int INTERVAL = 10000;
    public static boolean EXIST_ERROR_SETTLE = false;//是否存在结算异常

    private static Logger logger = Logger.getLogger(RealtimeSettleThread.class.
            getName());

    public void run() {
        SettleQueueVo queMsg = null;
        while (true) {
            try {

                //结算队列是否有未结算消息
                //调用结算存储过程
                queMsg = this.getUnHandleQueMsg();
                RealtimeSettleThread.setExistErrorSettle(queMsg);
                //未结算消息存在且没有错误
                if (this.isCanSettle(queMsg)) {
                    LoggerUtil.loggerLine(logger, LoggerUtil.getBalanceWaterInfo(queMsg.getBalanceWaterNo(), queMsg.getBalanceWaterNoSub()) + " :实时结算开始");
                    this.settleBatch(queMsg);
                    //等候子流水结算完成及输出已完成步骤信息
                    this.stepCJWaitForSettleFinish(queMsg.getBalanceWaterNo(), queMsg.getBalanceWaterNoSub());
                    //消息的应用相关功能完成标识更新为已完成20181028
                    this.updateFinishFlagApp(queMsg.getBalanceWaterNo(), queMsg.getBalanceWaterNoSub());
                    LoggerUtil.loggerLine(logger, LoggerUtil.getBalanceWaterInfo(queMsg.getBalanceWaterNo(), queMsg.getBalanceWaterNoSub()) + " :实时结算完成");
                   //下发HCE消费文件add by hejj 20190624
                   this.downloadAuditTrxMobile(queMsg.getBalanceWaterNo());
                   LoggerUtil.loggerLine(logger, LoggerUtil.getBalanceWaterInfo(queMsg.getBalanceWaterNo(), queMsg.getBalanceWaterNoSub()) + " :HCE消费文件下发完成");
                }

                this.threadSleep();
            } catch (Exception e) {
                e.printStackTrace();
                RealtimeSettleThread.setExistErrorSettle(queMsg);
            } finally {

            }

        }

    }

    private void downloadAuditTrxMobile(String balanceWaterNo) {

        AppSettleBase asb = new AppSettleBase();
        try {
            asb.downloadAuditTrxMobile(balanceWaterNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setExistErrorSettle(SettleQueueVo queMsg) {
        if (queMsg == null) {
            return;
        }
        String errCode = queMsg.getErrorCode();
        synchronized (FrameSynConstant.SYN_SETTLE) {
            if (errCode.equals(MESSAGE_NOT_FINISH_NOT_ERR)) {
                EXIST_ERROR_SETTLE = false;
            } else {
                EXIST_ERROR_SETTLE = true;
            }
        }

    }

    public static boolean getExistErrorSettle() {
        synchronized (FrameSynConstant.SYN_SETTLE) {
            return EXIST_ERROR_SETTLE;
        }

    }

    private boolean isCanSettle(SettleQueueVo queMsg) {
        if (queMsg == null) {
            return false;
        }
        String errCode = queMsg.getErrorCode();

        if (errCode.equals(MESSAGE_NOT_FINISH_NOT_ERR)) {
            return true;
        }
        return false;
    }

    private void stepCJWaitForSettleFinish(String balanceWaterNo, int balanceWaterNoSub) throws Exception {
        SettleDetailLogThread.setBalanceWaterNo(balanceWaterNo, balanceWaterNoSub);
        SettleDetailLogThread.setLogStartFlag(true);
        SettleDetailLogThread.setLogEndFlag(false);

        boolean isFinishLog = SettleDetailLogThread.getLogEndFlag();
        while (!isFinishLog) {
            Thread.sleep(FrameCodeConstant.SETTLE_FLOW_SLEEP_TIME_SETTLE_STATUS);
            isFinishLog = SettleDetailLogThread.getLogEndFlag();
        }

    }

    private void settleBatch(SettleQueueVo queMsg) throws Exception {
        BusinessSettleDao dao = new BusinessSettleDao();
        dao.businessSettle(queMsg);
    }

    private SettleQueueVo getUnHandleQueMsg() throws Exception {
        SettleQueueDao dao = new SettleQueueDao();
        SettleQueueVo queMsg = dao.getQueueMsgUnhandled();
        return queMsg;

    }

    private int updateFinishFlagApp(String balanceWaterNo, int balanceWaterNoSub) throws Exception {
        SettleQueueDao dao = new SettleQueueDao();
        int n = dao.updateForFinish(balanceWaterNo, balanceWaterNoSub);
        return n;

    }

    private void threadSleep() {

        try {
            this.sleep(INTERVAL);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
