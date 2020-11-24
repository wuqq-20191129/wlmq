package com.goldsign.acc.systemmonitor.handler;


import com.goldsign.acc.app.system.controller.NtpSynPcController;
import com.goldsign.acc.app.system.entity.NtpPcSyn;
import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.frame.entity.EmailContent;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.SpringContextUtil;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;

/**
 * @author zhongzq
 */

public class HandlerCommandNtp_pc extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandNtp_pc.class);

    public HandlerCommandNtp_pc() {
        super();
    }

    @Override
    public void handleCommand(String command, Vector lines, String fileName) {
        logger.info("处理命令:" + command);
        Vector vos = this.getLineInfoForNtp(lines);
        this.addStatus(vos);
        this.updateMenuStatus(command);
        if (FrameDBConstant.EMAIL_FEATURE_USE) {
            emailPreHandles(vos);
        }
    }

    @Override
    public EmailContent convertToEmailContents(Object t, String classPath) {
        NtpPcSyn vo = (NtpPcSyn) t;
        EmailContent emailContent = new EmailContent();
        emailContent.setClassSimpleName(classPath);
        emailContent.setModule((String) FrameDBConstant.MODULE_NAME.get(classPath));
        emailContent.setName((String) FrameDBConstant.SERVER_NAME.get(vo.getIp()));
        emailContent.setIp(vo.getIp());
        emailContent.setPasreTime(vo.getStatusDate());
        emailContent.setStatus(DBUtil.getTextByCode(vo.getStatus(), FrameDBConstant.STATUS_NAME));
        return emailContent;
    }

    private void addStatus(Vector vos) {
        NtpPcSyn vo;
        NtpSynPcController ntpPcSynController = SpringContextUtil.getBean("ntpPcSynController");
//        Vector v = this.getLatestStatues(vos);
        for (int i = 0; i < vos.size(); i++) {
            vo = (NtpPcSyn) vos.get(i);
            try {
                ntpPcSynController.addStatus(vo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Vector getLatestStatues(Vector vos) {
        Vector v = new Vector();
        NtpPcSyn vo;
        HashMap hm = new HashMap();
        for (int i = 0; i < vos.size(); i++) {
            vo = (NtpPcSyn) vos.get(i);
            hm.put(vo.getIp(), vo);
        }
        v.addAll(hm.values());
        return v;

    }

    private String getStatus(String diff) {
        float f = Float.parseFloat(diff);
        if (Math.abs(f) <= FrameDBConstant.SYN_MAX_DIFF_INTERVAL) {
            return FrameDBConstant.STATUS_NORMAL;
        } else {
            return FrameDBConstant.STATUS_FAILURE;
        }
    }

    private String convertUnit(String diff) {
        BigDecimal n = new BigDecimal(diff);
        return n.toString();
    }

    private Vector getLineInfoForNtp(Vector lines) {
        String line;
        String ip;
        String ipSource;
        String diff;
        String synDatetime;
        String status;
        String statusDate;
        Vector v = new Vector();
        NtpPcSyn vo;
        for (int i = 0; i < lines.size(); i++) {
            vo = new NtpPcSyn();
            line = (String) lines.get(i);
            ipSource = this.getLineField(line, FrameDBConstant.INDEX_SYN_IP_SOURCE, FrameDBConstant.COMMAND_SEPARATOR_SPACE);
            ip = this.getLineField(line, FrameDBConstant.INDEX_SYN_SERVER_IP, FrameDBConstant.COMMAND_SEPARATOR_SPACE);
            diff = this.convertUnit(this.getLineField(line, FrameDBConstant.INDEX_DIFF_TIME, FrameDBConstant.COMMAND_SEPARATOR_SPACE));
            synDatetime = this.getLineField(line, FrameDBConstant.INDEX_SYN_DATE, FrameDBConstant.COMMAND_SEPARATOR_SPACE) + " "
                    + this.getLineField(line, FrameDBConstant.INDEX_SYN_TIME, FrameDBConstant.COMMAND_SEPARATOR_SPACE);
            statusDate = this.getStatusDate();
            status = this.getStatus(diff);
            vo.setDiff(diff);
            vo.setIp(ip);
            vo.setIpSource(ipSource);
            vo.setStatusDateSyn(synDatetime);
            vo.setStatusDate(statusDate);
            vo.setStatus(status);
            vo.setRemark("");
            v.add(vo);
        }
        v = this.getLatestStatues(v);
        return v;

    }
}
