package com.goldsign.acc.systemmonitor.handler;

import com.goldsign.acc.app.system.controller.CpuDegreesController;
import com.goldsign.acc.app.system.entity.CpuDegrees;
import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.frame.entity.EmailContent;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.SpringContextUtil;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.Vector;

/**
 * Description:
 *
 * @author: zhongziqi
 * @Date: 2018-07-16
 * @Time: 18:49
 */
public class HandlerCommandCpudegrees extends HandlerCommandBase {
    static Logger logger = Logger.getLogger(HandlerCommandCpudegrees.class);

    public HandlerCommandCpudegrees() {
        super();
    }

    @Override
    public void handleCommand(String command, Vector lines, String fileName) throws ParseException {
        logger.info("处理命令:" + command);
        CpuDegrees vo = new CpuDegrees();
        String status = this.getStatus(lines);
        String cpuDegreesInfo = this.getCpuDegreesInfo(lines);
        String ip = this.getIpFromFileName(fileName);
        String statusDate = this.getStatusDate();
        vo.setIp(ip);
        vo.setDegreesInfo(cpuDegreesInfo);
        vo.setStatus(status);
        vo.setStatusDate(statusDate);

        this.addStatus(vo);
        this.updateMenuStatus(command);
        if (FrameDBConstant.EMAIL_FEATURE_USE) {
            emailPreHandle(vo);
        }
    }


    @Override
    public EmailContent convertToEmailContents(Object t, String classPath) {
        CpuDegrees vo = (CpuDegrees) t;
        EmailContent emailContent = new EmailContent();
        emailContent.setClassSimpleName(classPath);
        emailContent.setModule((String) FrameDBConstant.MODULE_NAME.get(classPath));
        emailContent.setName((String) FrameDBConstant.SERVER_NAME.get(vo.getIp()));
        emailContent.setIp(vo.getIp());
        emailContent.setPasreTime(vo.getStatusDate());
        emailContent.setStatus(DBUtil.getTextByCode(vo.getStatus(), FrameDBConstant.STATUS_NAME));
        emailContent.setMessage("温度信息：" + vo.getDegreesInfo());
        return emailContent;
    }

    private String getCpuDegreesInfo(Vector lines) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < lines.size(); i++) {
            String line = (String) lines.get(i);
            if (this.isIncludeKeyInLine(FrameDBConstant.CPU_DEGREES_ROW_KEY, line)) {
                stringBuffer.append(line);
                if (i < lines.size() - 1) {
                    stringBuffer.append(FrameDBConstant.COMMAND_SEPARATOR_SLASH);
                }
            }
        }
        return stringBuffer.toString();
    }

    private void addStatus(CpuDegrees vo) {
        CpuDegreesController cpuDegreesController = SpringContextUtil.getBean("cpuDegreesController");
        try {
            cpuDegreesController.addStatus(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getStatus(Vector lines) {
        String status = FrameDBConstant.STATUS_NORMAL;
        for (int i = 0; i < lines.size(); i++) {
            String line = (String) lines.get(i);
            if (this.isIncludeKeyInLine(FrameDBConstant.CPU_DEGREES_ROW_KEY, line)) {
                String value = line.substring(line.indexOf(FrameDBConstant.COMMAND_SEPARATOR_SPACE));
                if (Integer.valueOf(value.trim()) >= FrameDBConstant.CPU_DEGREES_WARNING_VALUE) {
                    status = FrameDBConstant.STATUS_FAILURE;
                    break;
                }
            }
        }
        return status;
    }


}
