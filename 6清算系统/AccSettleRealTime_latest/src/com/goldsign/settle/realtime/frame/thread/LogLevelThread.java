/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.thread;



import com.goldsign.settle.realtime.frame.constant.FrameLogConstant;
import com.goldsign.settle.realtime.frame.dao.LogLevelDao;
import com.goldsign.settle.realtime.frame.util.LogDbUtil;
import com.goldsign.settle.realtime.frame.vo.SysLogLevelVo;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class LogLevelThread extends Thread{
    private static Logger logger = Logger.getLogger(LogLevelThread.class.
            getName());

   

    public void run() {

        while (true) {
            try {
                this.setCurrentLogLevel();
                this.threadSleep();
            } catch (Exception e) {
            }


        }

    }

    public void runOne() {
        this.setCurrentLogLevel();

    }

    public void setCurrentLogLevel() {
        LogLevelDao dao = new LogLevelDao();
        SysLogLevelVo level = dao.getLogLevel();
        if (level == null) {
            FrameLogConstant.LOG_LEVEL_CURRENT = FrameLogConstant.LOG_LEVEL_INFO;
            FrameLogConstant.LOG_LEVEL_TEXT_CURRENT = FrameLogConstant.LOG_LEVEL_INFO_TEXT;

        } else {
            FrameLogConstant.LOG_LEVEL_CURRENT = level.getSysLevel();
            FrameLogConstant.LOG_LEVEL_TEXT_CURRENT = LogDbUtil.getLevelText(level.getSysLevel());
        }
        logger.info("设置日志级别为：" + FrameLogConstant.LOG_LEVEL_CURRENT);

    }

    private void threadSleep() {

        try {
            this.sleep(FrameLogConstant.threadLogLevel);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    
}
