/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.thread;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFlowConstant;
import com.goldsign.settle.realtime.frame.constant.FrameSynConstant;
import com.goldsign.settle.realtime.frame.dao.FlowDao;
import com.goldsign.settle.realtime.frame.util.LoggerUtil;
import com.goldsign.settle.realtime.frame.vo.StepInfo;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class SettleLogThread extends Thread {

    public static boolean LOG_START_FLAG = false;
    public static boolean LOG_END_FLAG = false;
    public static String BALANCE_WATER_NO = "";
    
    private static Logger logger = Logger.getLogger(SettleLogThread.class.getName());
    public static int INTERVAL = 5000;

    public static void setLogStartFlag(boolean flag, String balanceWaterNo) {
        synchronized (FrameSynConstant.SYN_ETTLE_LOG) {
            LOG_START_FLAG = flag;
            BALANCE_WATER_NO = balanceWaterNo;
        }
    }
    public static void setLogEndFlag(boolean flag) {
        synchronized (FrameSynConstant.SYN_ETTLE_LOG) {
            LOG_END_FLAG = flag;
            
        }
    }
    public static boolean getLogEndFlag() {
        synchronized (FrameSynConstant.SYN_ETTLE_LOG) {
            return LOG_END_FLAG;
        }
    }

    public static boolean getLogStartFlag() {
        synchronized (FrameSynConstant.SYN_ETTLE_LOG) {
            return LOG_START_FLAG;
        }
    }

    public static String getLogBalanceWaterNo() {
        synchronized (FrameSynConstant.SYN_ETTLE_LOG) {
            return BALANCE_WATER_NO;
        }
    }

    public void run() {
        String balanceWaterNo;
        while (true) {
            if (this.isCanLog()) {
                balanceWaterNo = this.getBalanceWaterNo();
                this.stepCIWaitForSettleFinish(balanceWaterNo);
                //
                this.reset(balanceWaterNo);
            }

            this.threadSleep();

        }

    }

    private void reset(String balanceWaterNo) {
        setLogStartFlag(false, balanceWaterNo);
        setLogEndFlag(true);
    }

    private boolean isCanLog() {
        return getLogStartFlag();

    }

    private String getBalanceWaterNo() {
        return getLogBalanceWaterNo();
    }

    private void stepCIWaitForSettleFinish(String balanceWaterNo) {
        try {
            boolean isUnFinished = this.isUnFinishedSteps(balanceWaterNo, FrameFlowConstant.SETTLE_STEPS);
            FlowDao.clearStepListed();
            while (isUnFinished) {
                this.loggerSettleStep(balanceWaterNo);
                //显示清算各步骤完成信息
                Thread.sleep(FrameCodeConstant.SETTLE_FLOW_SLEEP_TIME_SETTLE_STATUS_LOG);
                isUnFinished = this.isUnFinishedSteps(balanceWaterNo, FrameFlowConstant.SETTLE_STEPS);

            }
            this.loggerSettleStep(balanceWaterNo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loggerSettleStep(String balanceWaterNo) throws Exception {
        Vector<String> stepList = FlowDao.getFinishedStepForSettle(balanceWaterNo, FrameFlowConstant.SETTLE_STEPS);
        if (stepList.isEmpty()) {
          //  LoggerUtil.loggerLine(logger, "清算流水号：" + balanceWaterNo + ":正在进行结算处理，稍后。。。。" );
            return;
        }
        String stepName;
        for (String step : stepList) {
            stepName = FrameFlowConstant.getStepKeyName(step);
            LoggerUtil.loggerLine(logger, "清算流水号：" + balanceWaterNo + ":完成" + stepName + "处理");
        }
    }

    private boolean preHandleForStepsSettle(String balanceWaterNo, String[] steps) {
        boolean isUnfinished = this.isUnFinishedSteps(balanceWaterNo, steps);
        if (!isUnfinished) {
            LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":已完成" + FrameFlowConstant.getStepKeyNamesForSettle() + "无需再重新处理");
        }
        return isUnfinished;
    }

    private boolean isUnFinishedSteps(String balanceWaterNo, String[] steps) {
        boolean isUnFinished = true;
        try {
            isUnFinished = FlowDao.isUnFinishedSteps(balanceWaterNo, steps);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return isUnFinished;
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
