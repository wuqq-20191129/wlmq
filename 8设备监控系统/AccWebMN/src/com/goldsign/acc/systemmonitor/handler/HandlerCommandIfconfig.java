package com.goldsign.acc.systemmonitor.handler;

import com.goldsign.acc.app.system.controller.NetCardController;
import com.goldsign.acc.app.system.entity.NetCard;
import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.frame.entity.EmailContent;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.SpringContextUtil;
import org.apache.log4j.Logger;

import java.util.Vector;

/**
 * refactor by zhongzq
 * 服务器网卡模块
 */
public class HandlerCommandIfconfig extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandIfconfig.class);

    public HandlerCommandIfconfig() {
        super();
    }

    private String getStatus(Vector lines) {
        //判断依据信息行为空则视为正常
        if (lines.isEmpty()) {
            return FrameDBConstant.STATUS_NORMAL;
        }
        return FrameDBConstant.STATUS_FAILURE;
    }

    private String getFailureInfo(Vector lines) {
        return this.getContentBetweenLinesPart(lines);
    }

    @Override
    public void handleCommand(String command, Vector lines, String fileName) {

        logger.info("处理命令:" + command);
        NetCard vo = new NetCard();
        String status = this.getStatus(lines);
        String ip = this.getIpFromFileName(fileName);
        String statusDate = this.getStatusDate();
        String remark = "";
        if (!status.equals(FrameDBConstant.STATUS_NORMAL)) {
            remark = this.getFailureInfo(lines);
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

    @Override
    public EmailContent convertToEmailContents(Object t, String classPath) {
        NetCard vo = (NetCard) t;
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

    private void addStatus(NetCard vo) {
        NetCardController netCardController = SpringContextUtil.getBean("NetCardController");
        try {
            netCardController.addStatus(vo);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
