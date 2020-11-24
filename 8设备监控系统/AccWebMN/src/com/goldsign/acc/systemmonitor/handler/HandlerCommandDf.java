package com.goldsign.acc.systemmonitor.handler;

import com.goldsign.acc.app.system.controller.DiskSpaceController;
import com.goldsign.acc.app.system.entity.DiskSpace;
import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.frame.entity.EmailContent;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.SpringContextUtil;
import org.apache.log4j.Logger;

import java.util.Vector;

/**
 * 服务器硬盘
 *
 * @author xiaowu
 * refactor by zhongzq
 * 对应磁盘空间使用模块
 */
public class HandlerCommandDf extends HandlerCommandBase {
    public static final int NEED_FIELD_NUM = 6;
    public static final int INDEX_FILESYSTEM = 0;
    public static final int INDEX_AVAILABLE = 3;
    public static final int INDEX_CAPACITY = 4;
    public static final int INDEX_MOUNTED_ON = 5;
    static Logger logger = Logger.getLogger(HandlerCommandDf.class);

    public HandlerCommandDf() {
        super();
    }

    @Override
    public void handleCommand(String command, Vector lines, String fileName) {
        logger.info("处理命令:" + command);
//        this.setParameter(command, lines);
        //0:Filesystem  1:Size  2:Used  3:Available 4:Capacity  5:Mounted on
        int[] fieldIndexes = {INDEX_FILESYSTEM, INDEX_AVAILABLE, INDEX_CAPACITY, INDEX_MOUNTED_ON};
        Vector lineInfos = this.getLineInfoForDf(lines, fieldIndexes);
        Vector statusInfos = this.getStatusInfo(lineInfos, fileName);
        this.addStatuses(statusInfos);
        this.updateMenuStatus(command);
        if (FrameDBConstant.EMAIL_FEATURE_USE) {
            emailPreHandles(statusInfos);
        }
    }

    @Override
    public EmailContent convertToEmailContents(Object t, String classPath) {
        DiskSpace vo = (DiskSpace) t;
        EmailContent emailContent = new EmailContent();
        emailContent.setClassSimpleName(classPath);
        emailContent.setModule((String) FrameDBConstant.MODULE_NAME.get(classPath));
        emailContent.setName((String) FrameDBConstant.SERVER_NAME.get(vo.getIp()));
        emailContent.setIp(vo.getIp());
        emailContent.setPasreTime(vo.getStatusDate());
        emailContent.setStatus(DBUtil.getTextByCode(vo.getStatus(), FrameDBConstant.STATUS_NAME));

        StringBuffer messageBuffer = new StringBuffer();
        messageBuffer.append("文件系统：");
        messageBuffer.append(vo.getFile_system());
        messageBuffer.append(" ");
        messageBuffer.append("可用空间：");
        messageBuffer.append(vo.getAvail());
        messageBuffer.append(" ");
        messageBuffer.append("使用率：");
        messageBuffer.append(vo.getCapacity());
        messageBuffer.append(" ");
        messageBuffer.append("挂载点：");
        messageBuffer.append(vo.getMounted_on());
        messageBuffer.append(" ");
        emailContent.setMessage(messageBuffer.toString());
        //add by zhongzq 20190905 解决更新命中错误问题
        emailContent.setKey(vo.getFile_system());
        return emailContent;
    }

    private void addStatuses(Vector vos) {
        DiskSpaceController diskSpaceController = SpringContextUtil.getBean("diskSpaceController");
        try {
            diskSpaceController.addStatus(vos);
        } catch (Exception ex) {
            logger.info(ex);
        }
    }

    private Vector getStatusInfo(Vector lineInfos, String fileName) {
        Vector v = new Vector();
        String[] fieldValues;
        DiskSpace vo;
        for (int i = 0; i < lineInfos.size(); i++) {
            fieldValues = (String[]) lineInfos.get(i);
            vo = this.getVo(fieldValues, fileName);
            v.add(vo);
        }
        return v;
    }

    private boolean isOverLimit(String capacity) {
        int index = capacity.indexOf(FrameDBConstant.COMMAND_SEPARATOR_PERCENT);
        int iCap = Integer.parseInt(capacity.substring(0, index));
        if (iCap >= FrameDBConstant.COMMAND_FIND_USE_SPACE_WARNING) {
            return true;
        }
        return false;
    }

    private DiskSpace getVo(String[] fieldValues, String fileName) {
        DiskSpace vo = new DiskSpace();
        //0:Filesystem  1:Free  2:%Used 3:Mounted on
        vo.setFile_system(fieldValues[0]);
        vo.setKeyMessage(fieldValues[0]);
        vo.setAvail(fieldValues[1]);
        vo.setCapacity(fieldValues[2]);
        vo.setMounted_on(fieldValues[3]);
        String status = FrameDBConstant.STATUS_FAILURE;
        //状态判断依据%Used是否超过警戒值
        if (!this.isOverLimit(vo.getCapacity())) {
            status = FrameDBConstant.STATUS_NORMAL;
        }
        vo.setStatus(status);
        String ip = this.getIpFromFileName(fileName);
        vo.setIp(ip);
        String statusDate = this.getStatusDate();
//        vo.setStatus_date(statusDate);
        vo.setStatusDate(statusDate);
        vo.setRemark("");
        return vo;

    }

    private boolean isNeedLineForDf(String line) {
//        int fieldIndexByFileSystem = INDEX_FILESYSTEM;
//        int fieldIndexByMountedOn = INDEX_MOUNTED_ON;
        //文件校验还需全部文件分析
        boolean checkNeedLine = this.checkLineFieldNum(line, NEED_FIELD_NUM, FrameDBConstant.COMMAND_SEPARATOR_SPACE)
                &&this.isIncludeKeyInLine(FrameDBConstant.COMMAND_SEPARATOR_SLASH, line);
//                 &&
//                (this.isNeededLineFromFields(INDEX_FILESYSTEM, FrameDBConstant.COMMAND_FIND_DF_KEYS_FILESYSTEM,
//                        FrameDBConstant.COMMAND_SEPARATOR_SPACE, line) ||
//                 this.isNeededLineFromFields(INDEX_MOUNTED_ON, FrameDBConstant.COMMAND_FIND_DF_KEYS_MOUNTED,
//                         FrameDBConstant.COMMAND_SEPARATOR_SPACE, line) ||
//                 this.isNeededLineFromFieldsComplete(INDEX_MOUNTED_ON, FrameDBConstant.COMMNAD_FIND_DF_KEYS_MOUNTED_COMPLETE,
//                         FrameDBConstant.COMMAND_SEPARATOR_SPACE, line)
//                );
        if (checkNeedLine) {
            return true;
        }
        return false;
    }

    private Vector getLineInfoForDf(Vector lines, int[] fieldIndexs) {
        Vector v = new Vector();
        String line;
        String[] fieldValues;
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            if (this.isNeedLineForDf(line)) {
                fieldValues = this.getLineFields(line, fieldIndexs, FrameDBConstant.COMMAND_SEPARATOR_SPACE);
                v.add(fieldValues);
            }
        }
        return v;
    }

}
