package com.goldsign.acc.systemmonitor.handler;

import com.goldsign.acc.app.system.controller.DiskController;
import com.goldsign.acc.app.system.entity.Disk;
import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.frame.entity.EmailContent;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.SpringContextUtil;
import org.apache.log4j.Logger;

import java.util.Vector;

/**
 * @author zhongziqi
 * 对应硬盘模块
 */
public class HandlerCommandDiskstate extends HandlerCommandBase {
    public static final int STATUS_FIELD_INDEX = 3;
    static Logger logger = Logger.getLogger(HandlerCommandHardware.class);

    public HandlerCommandDiskstate() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void handleCommand(String command, Vector lines, String fileName) {
        logger.info("处理命令:" + command);
        Disk vo = new Disk();
        vo.setIp(this.getIpFromFileName(fileName));
        vo.setStatus(this.getStatus(lines));
//        vo.setStatus_date(this.getStatusDate());
        vo.setStatusDate(this.getStatusDate());
        if (FrameDBConstant.STATUS_FAILURE.equals(vo.getStatus())) {
            vo.setRemark(this.getContentBetweenLinesPart(lines));
        } else {
            vo.setRemark("");
        }
        this.addStatus(vo);
        this.updateMenuStatus(command);
        if (FrameDBConstant.EMAIL_FEATURE_USE) {
            emailPreHandle(vo);
        }
    }

    @Override
    public EmailContent convertToEmailContents(Object t, String classPath) {
        Disk vo = (Disk) t;
        EmailContent emailContent = new EmailContent();
        emailContent.setClassSimpleName(classPath);
        emailContent.setModule((String) FrameDBConstant.MODULE_NAME.get(classPath));
        emailContent.setName((String) FrameDBConstant.SERVER_NAME.get(vo.getIp()));
        emailContent.setIp(vo.getIp());
        emailContent.setPasreTime(vo.getStatusDate());
        emailContent.setStatus(DBUtil.getTextByCode(vo.getStatus(), FrameDBConstant.STATUS_NAME));
        emailContent.setMessage("Disk");
        return emailContent;
    }

    private void addStatus(Disk vo) {
        DiskController diskController = SpringContextUtil.getBean("diskController");
        try {
            diskController.addStatus(vo);
        } catch (Exception ex) {
            logger.info(ex);
        }
    }

    private String getStatus(Vector lines) {
        String line;
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            if ((this.isIncludeKeyInLine(FrameDBConstant.COMMAND_FIND_DISK_INFO_LINE_KEY, line))) {
                String record = this.getLineField(line, STATUS_FIELD_INDEX, FrameDBConstant.COMMAND_SEPARATOR_SPACE);
                if (FrameDBConstant.COMMAND_FIND_DISK_STATUS_NORMAL_KEY.equals(record)) {
                    return FrameDBConstant.STATUS_NORMAL;
                }
            }
        }
        return FrameDBConstant.STATUS_FAILURE;
    }


}
