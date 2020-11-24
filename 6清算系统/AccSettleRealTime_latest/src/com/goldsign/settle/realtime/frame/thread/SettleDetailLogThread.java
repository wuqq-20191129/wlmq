/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.thread;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFlowConstant;
import com.goldsign.settle.realtime.frame.constant.FrameSynConstant;
import com.goldsign.settle.realtime.frame.dao.FlowDao;

import static com.goldsign.settle.realtime.frame.thread.SettleLogThread.setLogStartFlag;
import com.goldsign.settle.realtime.frame.util.LoggerUtil;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class SettleDetailLogThread extends Thread {

    public static boolean LOG_START_FLAG = false;
    public static boolean LOG_END_FLAG = false;
    private static String BALANCE_WATER_NO = "";
    private static int BALANCE_WATER_NO_SUB = -1;
    private static Logger logger = Logger.getLogger(SettleDetailLogThread.class.getName());
    public static int INTERVAL = 5000;

    public void run() {
        String balanceWaterNo;
        int balanceWaterNoSub = -1;
        while (true) {
            if (this.isCanLog()) {
                balanceWaterNo = this.getBalanceWaterNo();
                balanceWaterNoSub = this.getBalanceWaterNoSub();
                this.stepCIWaitForSettleFinishForDetail(balanceWaterNo, balanceWaterNoSub);
                //
                this.reset(balanceWaterNo);
            }

            this.threadSleep();

        }

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

    public static void setLogEndFlag(boolean flag) {
        synchronized (FrameSynConstant.SYN_SETTLE_DETAIL_LOG) {
            LOG_END_FLAG = flag;

        }
    }
    public static void setLogStartFlag(boolean flag) {
        synchronized (FrameSynConstant.SYN_SETTLE_DETAIL_LOG) {
            LOG_START_FLAG = flag;

        }
    }

    public static boolean getLogEndFlag() {
        synchronized (FrameSynConstant.SYN_SETTLE_DETAIL_LOG) {
            return LOG_END_FLAG;
        }
    }

    private void reset(String balanceWaterNo) {
        setLogStartFlag(false);
        setLogEndFlag(true);
    }

    private boolean isCanLog() {
        return getLogStartFlag();

    }

    private String getBalanceWaterNo() {
        return getLogBalanceWaterNo();
    }

    private int getBalanceWaterNoSub() {
        return getLogBalanceWaterNoSub();
    }

    public static String getLogBalanceWaterNo() {
        synchronized (FrameSynConstant.SYN_SETTLE_DETAIL_LOG) {
            return BALANCE_WATER_NO;
        }
    }

    public static void setLogBalanceWaterNo(String balanceWaterNo, int balanceWaterNoSub) {
        synchronized (FrameSynConstant.SYN_SETTLE_DETAIL_LOG) {
             BALANCE_WATER_NO =balanceWaterNo;
             BALANCE_WATER_NO_SUB=balanceWaterNoSub;
        }
    }

    public static void setBalanceWaterNo(String balanceWaterNo, int balanceWaterNoSub) {
        setLogBalanceWaterNo(balanceWaterNo, balanceWaterNoSub);

    }

    public static int getLogBalanceWaterNoSub() {
        synchronized (FrameSynConstant.SYN_SETTLE_DETAIL_LOG) {
            return BALANCE_WATER_NO_SUB;
        }
    }

    private boolean isUnFinishedStepsForDetail(String balanceWaterNo, String[] steps) {
        boolean isUnFinished = true;
        try {
            isUnFinished = FlowDao.isUnFinishedStepsForDetail(balanceWaterNo, steps);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return isUnFinished;
    }

    private void loggerSettleStepForDetail(String balanceWaterNo, int balanceWaterNoSub) throws Exception {
        Vector<String> stepList = FlowDao.getFinishedStepForSettleDetail(balanceWaterNo, FrameFlowConstant.SETTLE_STEPS);
        if (stepList.isEmpty()) {
            //  LoggerUtil.loggerLine(logger, "清算流水号：" + balanceWaterNo + ":正在进行结算处理，稍后。。。。" );
            return;
        }
        String stepName;
        for (String step : stepList) {
            stepName = FrameFlowConstant.getStepKeyName(step);
            LoggerUtil.loggerLine(logger, LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":完成" + stepName + "处理");
        }
    }

    private void stepCIWaitForSettleFinishForDetail(String balanceWaterNo, int balanceWaterNoSub) {
        try {
            boolean isUnFinished = this.isUnFinishedStepsForDetail(balanceWaterNo, FrameFlowConstant.SETTLE_STEPS);
            FlowDao.clearStepListedForDetail();
            while (isUnFinished) {
                this.loggerSettleStepForDetail(balanceWaterNo, balanceWaterNoSub);
                //显示清算各步骤完成信息
                Thread.sleep(FrameCodeConstant.SETTLE_FLOW_SLEEP_TIME_SETTLE_STATUS_LOG);
                isUnFinished = this.isUnFinishedStepsForDetail(balanceWaterNo, FrameFlowConstant.SETTLE_STEPS);

            }
            this.loggerSettleStepForDetail(balanceWaterNo, balanceWaterNoSub);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean getLogStartFlag() {
        synchronized (FrameSynConstant.SYN_SETTLE_DETAIL_LOG) {
            return LOG_START_FLAG;
        }
    }
}
