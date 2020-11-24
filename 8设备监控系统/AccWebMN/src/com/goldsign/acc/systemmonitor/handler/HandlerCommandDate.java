package com.goldsign.acc.systemmonitor.handler;

import com.goldsign.acc.app.system.controller.DateController;
import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.frame.entity.EmailContent;
import com.goldsign.acc.frame.util.SpringContextUtil;
import com.goldsign.acc.systemmonitor.file.FileAnalyzer;

import java.util.Vector;

/**
 * refactor by zhongzq
 */
public class HandlerCommandDate
        extends HandlerCommandBase {

    public HandlerCommandDate() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void handleCommand(String command, Vector lines, String fileName) {
        logger.info("处理命令:" + command);
        String line;
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            if (line.length() == 0) {
                continue;
            }
            HandlerCommandBase.STATUS_DATE = line;
            this.addFile(HandlerCommandBase.STATUS_DATE, fileName);
        }
    }

    @Override
    public EmailContent convertToEmailContents(Object t, String classPath) {
        return null;
    }

    private boolean isNtpFile(String fileName) {
        if (fileName.indexOf(FrameDBConstant.FILE_NTP_INCLUDE) != -1) {
            return true;
        }
        return false;
    }

    private void addFile(String fileDate, String fileName) {
        DateController dateController = SpringContextUtil.getBean("DateController");
        boolean isOldFile = false;
        try {
            isOldFile = dateController.isOldFile(fileDate, fileName);
            if (isOldFile) {
                FileAnalyzer.IS_FILE_HANDLED = true;
                //NTP文件不作新旧判断。
//                if (this.isNtpFile(fileName)) {
//                    FileAnalyzer.IS_FILE_HANDLED = false;
//                }
            } else {
                dateController.addFile(fileDate, fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
