package com.goldsign.systemmonitor.thread;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.systemmonitor.file.FileAnalyzer;

public class FileParseThread extends Thread {

    public FileParseThread() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void run() {
        FileAnalyzer analyzer = new FileAnalyzer();
        while (true) {
            try {
                this.waitFtpFinished();
                analyzer.parseFiles();

            } catch (Exception e) {
                e.printStackTrace();
            }
            this.threadSleep();

        }
    }

    private void waitFtpFinished() {
        while (true) {
            if (FrameUtil.isFinished()) {
                break;
            }
            this.threadSleepForWaitFtp();

        }
    }

    private void threadSleep() {
        try {
            this.sleep(FrameDBConstant.FileParseInterval);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

    private void threadSleepForWaitFtp() {
        try {
            this.sleep(FrameDBConstant.FileWaitFtpInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
