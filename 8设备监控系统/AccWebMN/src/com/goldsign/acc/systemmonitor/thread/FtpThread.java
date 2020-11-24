package com.goldsign.acc.systemmonitor.thread;

import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.frame.util.FrameUtil;
import com.goldsign.acc.systemmonitor.util.FtpUtil;
import com.goldsign.acc.systemmonitor.websocket.UpdateWebSocketHandler;
import org.apache.log4j.Logger;
import org.springframework.web.socket.TextMessage;

/**
 * Description:
 *
 * @autor: zhongziqi
 * @Date: 2018-06-09
 * @Time: 21:45
 */
public class FtpThread extends Thread {
    static Logger logger = Logger.getLogger(FtpThread.class);
    public FtpThread() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public void run() {
        FtpUtil ftpUtil = new FtpUtil();

        while (true) {
            try {
                FrameUtil.setFinished(false);
                //拉去ftp 本地可不使用
                ftpUtil.ftpGetFiles(FrameDBConstant.FTP_SERVER_IP, FrameDBConstant.FTP_USERNAME, FrameDBConstant.FTP_PASSWORD);

            } catch (Exception e) {
                e.printStackTrace();
//                logger.info("======================可能是文件冲突 再拉一次 ");
//                try {
//                    sleep(3000);
//                    ftpUtil.ftpGetFiles(FrameDBConstant.FTP_SERVER_IP, FrameDBConstant.FTP_USERNAME, FrameDBConstant.FTP_PASSWORD);
//                } catch (InterruptedException e1) {
//                    logger.info("======================异常后再次拉取失败");
//                    e1.printStackTrace();
//                }

            } finally {
                FrameUtil.setFinished(true);
            }
            this.threadSleep();
        }
    }

    private void threadSleep() {
        try {
            sleep(FrameDBConstant.FTP_THREAD_SLEEP_INTERVAL);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getAllFiles() {
    }
}
