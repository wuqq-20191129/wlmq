package com.goldsign.acc.systemmonitor.handler;

import com.goldsign.acc.app.system.controller.HardwareController;
import com.goldsign.acc.app.system.entity.Hardware;
import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.frame.entity.EmailContent;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.SpringContextUtil;
import org.apache.log4j.Logger;

import java.util.Vector;
import java.util.regex.Pattern;

/**
 * refactor by zhongzq
 * 对应服务器硬件模块
 */
public class HandlerCommandHardware extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandHardware.class);

    public HandlerCommandHardware() {
        super();
        // TODO Auto-generated constructor stub
    }

    private String getStatus(Vector lines) {
        //判断依据error信息行为空则是正常
        if (lines.isEmpty()) {
            return FrameDBConstant.STATUS_NORMAL;
        }
        return FrameDBConstant.STATUS_FAILURE;
    }

    private String getFailureInfoPart(Vector lines) {
        return this.getContentBetweenLinesPart(lines);
    }

    @Override
    public EmailContent convertToEmailContents(Object t, String classPath) {
        Hardware vo = (Hardware) t;
        EmailContent emailContent = new EmailContent();
        emailContent.setClassSimpleName(classPath);
        emailContent.setModule((String) FrameDBConstant.MODULE_NAME.get(classPath));
        emailContent.setName((String) FrameDBConstant.SERVER_NAME.get(vo.getIp()));
        emailContent.setIp(vo.getIp());
        emailContent.setPasreTime(vo.getStatusDate());
        emailContent.setStatus(DBUtil.getTextByCode(vo.getStatus(), FrameDBConstant.STATUS_NAME));
//        emailContent.setMessage(vo.getRemark());
        return emailContent;
    }

    @Override
    public void handleCommand(String command, Vector lines, String fileName) {
        logger.info("处理命令:" + command);
        Hardware vo = new Hardware();
        String status = this.getStatus(lines);
        String ip = this.getIpFromFileName(fileName);
        String statusDate = this.getStatusDate();
        String remark = "";
        if (!status.equals(FrameDBConstant.STATUS_NORMAL)) {
            status = this.checkAbnormalMessage(lines, status);
            remark = this.getFailureInfoPart(lines);
        }
        vo.setIp(ip);
        vo.setStatus(status);
        vo.setStatusDate(statusDate);
        vo.setRemark(remark);
        this.addStatus(vo);
        this.updateMenuStatus(command);
        if (FrameDBConstant.EMAIL_FEATURE_USE) {
            emailPreHandle(vo);
        }
    }

    private String checkAbnormalMessage(Vector lines, String status) {
        String[] keys = FrameDBConstant.HARDWARE_WARING_KEYS.split("#");
        String[] values = FrameDBConstant.HARDWARE_WARING_VALUES.split("#");
        for (int i = 0; i < lines.size(); i++) {
            String line = (String) lines.get(i);
            for (int j = 0; j < keys.length; j++) {
                if (Pattern.compile(keys[j]).matcher(line).find()) {
                    if (Pattern.compile(values[j]).matcher(line).find()) {
                        status = FrameDBConstant.STATUS_WARNING;
                        break;
                    } else {
                        status = FrameDBConstant.STATUS_FAILURE;
                        return status;
                    }
                }
            }
        }
        return status;
    }

    private void addStatus(Hardware vo) {
        HardwareController hardwareController = SpringContextUtil.getBean("HardwareController");
        try {
            hardwareController.addStatus(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
