package com.goldsign.escommu.thread;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.dao.LogLevelDao;
import com.goldsign.escommu.util.LogDbUtil;
import com.goldsign.escommu.vo.SysLogLevelVo;
import org.apache.log4j.Logger;

public class CommuLogLevelThread extends Thread {

    private static Logger logger = Logger.getLogger(CommuLogLevelThread.class.getName());

    public CommuLogLevelThread() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * 运行
     * 
     */
    public void run() {

        while (true) {
            try {
                this.setCurrentLogLevel();
                this.threadSleep();
            } catch (Exception e) {
                logger.error("日志级别线程异常:" + e.getMessage());
            }
        }

    }

    /**
     * 直接运行一次
     * 
     */
    public void runOne() {
        this.setCurrentLogLevel();

    }

    /**
     * 设置日志级别
     * 
     */
    public void setCurrentLogLevel() {
        LogLevelDao dao = new LogLevelDao();
        SysLogLevelVo level = dao.getLogLevel();
        if (level == null) {
            AppConstant.LOG_LEVEL_CURRENT = AppConstant.LOG_LEVEL_INFO;
            AppConstant.LOG_LEVEL_TEXT_CURRENT = AppConstant.LOG_LEVEL_INFO_TEXT;

        } else {
            AppConstant.LOG_LEVEL_CURRENT = level.getSysLevel();
            AppConstant.LOG_LEVEL_TEXT_CURRENT = LogDbUtil.getLevelText(level.getSysLevel());
        }
        logger.info("设置日志级别为：" + AppConstant.LOG_LEVEL_CURRENT);

    }

    /**
     * 休息
     * 
     */
    private void threadSleep() {

        try {
            this.sleep(AppConstant.IntervalThreadLogLevel);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
