package com.goldsign.acc.systemmonitor.thread;

import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.frame.controller.MenuController;
import com.goldsign.acc.frame.emali.EmailService;
import com.goldsign.acc.frame.entity.EmailContent;
import com.goldsign.acc.frame.entity.EmailErrorLog;
import com.goldsign.acc.frame.util.FrameUtil;
import com.goldsign.acc.frame.util.SpringContextUtil;
import com.goldsign.acc.systemmonitor.file.FileAnalyzer;
import com.goldsign.acc.systemmonitor.websocket.UpdateWebSocketHandler;
import org.apache.log4j.Logger;
import org.springframework.web.socket.TextMessage;

import javax.mail.Address;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @autor: zhongziqi
 * @Date: 2018-06-09
 * @Time: 21:47
 */

public class FileParseThread extends Thread {
    static Logger logger = Logger.getLogger(FileParseThread.class);

    public FileParseThread() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void run() {
        FileAnalyzer analyzer = new FileAnalyzer();
        while (true) {
            try {

                this.waitFtpFinished();
                analyzer.parseFiles();
                if (!FileAnalyzer.IS_FILE_HANDLED) {

                    analyzer.archivingFiles();
                }
                //每解析完一次就更新一次
                notifyUpdateMenuStatus();
                if (FrameDBConstant.EMAIL_FEATURE_USE) {
                    emailHandle();
                }


            } catch (Exception e) {
                e.printStackTrace();
                this.threadSleep();
            }
            this.threadSleep();

        }
    }



    public void notifyUpdateMenuStatus() {
        if (UpdateWebSocketHandler.users.size() > 0) {
//                    new UpdateWebSocketHandler().sendMessageToAllUsers(new TextMessage("update"));
            MenuController menuController = SpringContextUtil.getBean("menuController");
            List<Map> list = menuController.getMenuStatus();
            String jsonResult = menuController.getMeunStatuJson(list);
            new UpdateWebSocketHandler().sendMessageToAllUsers(new TextMessage(jsonResult));
        } else {
            logger.info("没有websocket连接");
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
            sleep(FrameDBConstant.FILE_PARSE_INTERVAL);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

    private void threadSleepForWaitFtp() {
        try {
            sleep(FrameDBConstant.FILE_THREAD_SLEEP_INTERVAL);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void emailHandle() {
        EmailService emailService = SpringContextUtil.getBean("emailService");
        ArrayList<EmailContent> contentList = emailService.getContents();
        if (contentList != null && contentList.size() > 0) {
            logger.info("========发送邮件处理========");
            Address[] toAddresses = emailService.getAddresses(emailService.getTOList());
            Address[] ccAddresses = emailService.getAddresses(emailService.getCCList());
            try {
                emailService.sendEmail(toAddresses, ccAddresses, contentList);
                for (int i = 0; i < contentList.size(); i++) {
                    emailService.updateForSend(contentList.get(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
                String erroMsg = e.getMessage();
                logger.error("===============异常===========" + erroMsg);
                EmailErrorLog vo = new EmailErrorLog();

                if (erroMsg != null && erroMsg.length() > 500) {
                    erroMsg = erroMsg.substring(0, 500);
                }
                vo.setSender(FrameDBConstant.EMAIL_SENDER_ADDRESS);
                vo.setPaserTime(contentList.get(0).getPasreTime());
                vo.setErrorMsg(erroMsg);
                emailService.recordEmailSendError(vo);
            }
        } else {
            logger.info("==========没有邮件需要发送=========");
        }
    }


}