package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.DateDao;
import com.goldsign.systemmonitor.file.FileAnalyzer;
import java.util.Vector;



public class HandlerCommandDate extends HandlerCommandBase {

    public HandlerCommandDate() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void handleCommand(String command, Vector lines, String fileName) {

        logger.info("处理命令:" + command);
        this.setParameter(command, lines);
        //this.printLines();
        String line;
        for (int i = 0; i < this.lines.size(); i++) {
            line = (String) lines.get(i);
            if (line.length() == 0) {
                continue;
            }
            HandlerCommandBase.STATUS_DATE = line;
            this.addFile(HandlerCommandBase.STATUS_DATE, fileName);
        }
    }

    private boolean isNtpFile(String fileName) {
        if (fileName.indexOf(FrameDBConstant.FILE_NTP_INCLUDE) != -1) {
            return true;
        }
        return false;
    }

    private void addFile(String fileDate, String fileName) {
        DateDao dao = new DateDao();
        boolean isOldFile = false;
        try {
            isOldFile = dao.isOldFile(fileDate, fileName);
            if (isOldFile) {
                FileAnalyzer.IS_FILE_HANDLED = true;
                if (this.isNtpFile(fileName))//NTP文件不作新旧判断。
                {
                    FileAnalyzer.IS_FILE_HANDLED = false;
                }
            } else {
                dao.addFile(fileDate, fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
