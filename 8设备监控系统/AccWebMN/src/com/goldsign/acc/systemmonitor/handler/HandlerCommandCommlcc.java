package com.goldsign.acc.systemmonitor.handler;


import com.goldsign.acc.app.system.controller.LccController;
import com.goldsign.acc.app.system.entity.Lcc;
import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.frame.entity.EmailContent;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.SpringContextUtil;
import org.apache.log4j.Logger;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 * refactor by zhongzq
 * 对应Acc与Lc通信情况模块
 */
public class HandlerCommandCommlcc extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandCommlcc.class);

    public HandlerCommandCommlcc() {
        super();
    }

    private String[] getLineFieldByKey(String line, String keyIp, String keyStatus, String separator) {
        StringTokenizer st = new StringTokenizer(line, separator);
        String token;
        String[] fieldValues = {"", ""};
        String status;
        //判断依据 行信息是否有alive eg:10.2.98.30 is alive
        if (this.isIncludeKeyInLine(keyStatus, line)) {
            status = FrameDBConstant.STATUS_NORMAL;
        } else {
            status = FrameDBConstant.STATUS_FAILURE;
        }
        fieldValues[1] = status;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            if (this.isIncludeKeyInLine(keyIp, token)) {
                fieldValues[0] = token;
                break;
            }
        }
        return fieldValues;
    }

    private Vector getLineInfoForCommlcc(Vector lines) {
        Vector v = new Vector();
        String line;
        String[] fieldValues;
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            fieldValues = this.getLineFieldByKey(line, FrameDBConstant.COMMAND_FIND_COMMU_LC_KEY_IP,
                    FrameDBConstant.COMMAND_FIND_COMMU_LC_KEY_STATUS,
                    FrameDBConstant.COMMAND_SEPARATOR_SPACE);
            v.add(fieldValues);
        }
        return v;
    }

    private Vector getStatusInfo(Vector lineInfos, String fileName) {
        Vector v = new Vector();
        String[] fieldValues;
        Lcc vo;
        for (int i = 0; i < lineInfos.size(); i++) {
            fieldValues = (String[]) lineInfos.get(i);
            vo = this.getVo(fieldValues, fileName);
            v.add(vo);
        }
        return v;
    }

    private Lcc getVo(String[] fieldValues, String fileName) {
        Lcc vo = new Lcc();
        vo.setStatus(fieldValues[1]);
        String ip = fieldValues[0];
        vo.setIp(ip);
        String statusDate = this.getStatusDate();
//        vo.setStatus_date(statusDate);
        vo.setStatusDate(statusDate);
        vo.setRemark("");
        return vo;
    }

    @Override
    public void handleCommand(String command, Vector lines, String fileName) {
        logger.info("处理命令:" + command);
        Vector lineInfos = this.getLineInfoForCommlcc(lines);
        Vector statusInfos = this.getStatusInfo(lineInfos, fileName);
        this.addStatuses(statusInfos);
        this.updateMenuStatus(command);
        if (FrameDBConstant.EMAIL_FEATURE_USE) {
            emailPreHandles(statusInfos);
//            updateHandledAbnormal(statusInfos);
        }
    }


//    private void emailPreHandle(Vector<BaseMessage> statusInfos) {
//        if (statusInfos != null && statusInfos.size() > 0) {
//            for (BaseMessage vo : statusInfos) {
//                if (FrameDBConstant.STATUS_FAILURE.equals(vo.getStatus())) {
//                    addEmailContent(convertToEmailContents(vo, this.getClass().getSimpleName()));
//                }
//            }
//        }
//        return;
//    }


    @Override
    public EmailContent convertToEmailContents(Object t, String classPath) {
        Lcc vo = (Lcc) t;
        EmailContent emailContent = new EmailContent();
        emailContent.setClassSimpleName(classPath);
        emailContent.setModule((String) FrameDBConstant.MODULE_NAME.get(classPath));
        emailContent.setName((String) FrameDBConstant.SERVER_NAME.get(vo.getIp()));
        emailContent.setIp(vo.getIp());
        emailContent.setPasreTime(vo.getStatusDate());
        emailContent.setStatus(DBUtil.getTextByCode(vo.getStatus(), FrameDBConstant.STATUS_NAME));
        return emailContent;
    }

    private void addStatuses(Vector vos) {
        try {
            LccController lccController = SpringContextUtil.getBean("LccController");
            System.out.println("*****come into addStatuses**********");
            lccController.addStatuses(vos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
