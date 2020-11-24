package com.goldsign.systemmonitor.thread;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.systemmonitor.util.FtpUtil;

;

public class FtpThread extends Thread {

    public FtpThread() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void run() {
        FtpUtil ftpUtil = new FtpUtil();

        while (true) {
            try {

                FrameUtil.setFinished(false);
                //ftpUtil.ftpGetFiles(FrameDBConstant.FtpServer, FrameDBConstant.FtpUser, FrameDBConstant.FtpPassword);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                FrameUtil.setFinished(true);
            }
            this.threadSleep();
        }
    }

    private void threadSleep() {
        try {
            this.sleep(FrameDBConstant.FtpInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getAllFiles() {
    }
}
