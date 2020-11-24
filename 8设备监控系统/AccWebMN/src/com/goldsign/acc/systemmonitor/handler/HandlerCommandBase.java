package com.goldsign.acc.systemmonitor.handler;

import com.goldsign.acc.app.system.entity.BaseMessage;
import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.frame.controller.MenuController;
import com.goldsign.acc.frame.emali.EmailService;
import com.goldsign.acc.frame.entity.EmailContent;
import com.goldsign.acc.frame.util.SpringContextUtil;
import com.goldsign.acc.systemmonitor.util.DateHelper;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * refactor by zhongzq
 */
public abstract class HandlerCommandBase {
    static Logger logger = Logger.getLogger(HandlerCommandBase.class);
    protected static String STATUS_DATE;

    public HandlerCommandBase() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * 每个继承类具体实现处理文件内容
     *
     * @param command  处理类
     * @param lines    处理行数
     * @param fileName 文件名
     * @throws ParseException
     */
    public abstract void handleCommand(String command, Vector lines, String fileName) throws ParseException;

    /**
     * 转成公共模板
     *
     * @param t
     * @return
     */
    public abstract EmailContent convertToEmailContents(Object t, String classPath);

    protected String getLineField(String line, int fieldIndex, String separator) {
        StringTokenizer st = new StringTokenizer(line, separator);
        String token;
        int i = 0;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            if (i == fieldIndex) {
                return token;
            }
            i++;
        }
        return "";
    }


    protected String getSpaceForBracket(String space) {
        String tmp = space.trim();
        int index = tmp.indexOf(FrameDBConstant.COMMAND_SEPARATOR_BRACKET_LEFT);
        int index1 = tmp.indexOf(FrameDBConstant.COMMAND_SEPARATOR_BRACKET_RIGHT);
        tmp = tmp.substring(index + 1, index1);
        return tmp;
    }

    protected String getSpace(String space) {
        String tmp = space.trim();
        int index = tmp.indexOf(FrameDBConstant.COMMAND_SEPARATOR_PERCENT);
        tmp = tmp.substring(0, index);
        return tmp;
    }

    protected String getSpace(String space, String unit) {
        String tmp = space.trim();
        int index = tmp.indexOf(unit);
        tmp = tmp.substring(0, index);
        tmp = tmp.trim();
        return tmp;
    }

    protected String getLineFieldBefore(String line, String key) {
        int index = line.indexOf(key);
        return line.substring(0, index).trim();

    }

    protected String getLineFieldBefore(String line, String[] keys) {
        int index = -1;
        String key;
        for (int i = 0; i < keys.length; i++) {
            key = keys[i];
            index = line.indexOf(key);
            if (index != -1) {
                break;
            }
        }
        if (index != -1) {
            return line.substring(0, index).trim();
        }
        return "";

    }

    /**
     * 从行的公共关键字及可能的关键字判断
     *
     * @param common
     * @param keys
     * @param line
     * @return
     */
    protected boolean isNeededLine(String common, String[] keys, String line) {

        String key;
        for (int i = 0; i < keys.length; i++) {
            key = keys[i];
            if (this.isIncludeKeysInLine(common, key, line)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 从行的某一字段是否包含关键字判断
     */
    protected boolean isNeededLineFromField(int fieldIndex, String key, String separator, String line) {
        String fieldValue = this.getLineField(line, fieldIndex, separator);
        if (this.isIncludeKeyInLine(key, fieldValue)) {
            return true;
        }
        return false;
    }

    /**
     * 从行的某一字段是否等于可能的关键字判断
     */
    protected boolean isNeededLineFromFieldComplete(int fieldIndex, String key, String separator, String line) {

        String fieldValue = this.getLineField(line, fieldIndex, separator);
        if (this.isIncludeKeyInLineComplete(key, fieldValue)) {
            return true;
        }
        return false;
    }

    /**
     * 从行的某一字段是否包含可能的关键字判断
     */
    protected boolean isNeededLineFromFields(int fieldIndex, String[] keys, String separator, String line) {
        String key;
        for (int i = 0; i < keys.length; i++) {
            key = keys[i];
            if (this.isNeededLineFromField(fieldIndex, key, separator, line)) {
                return true;
            }

        }
        return false;
    }

    protected boolean isNeededLineFromFieldsComplete(int fieldIndex, String[] keys, String separator, String line) {
        String key;
        for (int i = 0; i < keys.length; i++) {
            key = keys[i];
            if (this.isNeededLineFromFieldComplete(fieldIndex, key, separator, line)) {
                return true;
            }
        }
        return false;
    }

    public String[] getLineFields(String line, int[] fieldIndexes, String seperator) {
        String[] fieldValues = new String[fieldIndexes.length];
        int index;
        String value;
        for (int i = 0; i < fieldIndexes.length; i++) {
            index = fieldIndexes[i];
            value = this.getLineField(line, index, seperator);
            fieldValues[i] = value;
        }
        return fieldValues;
    }

    protected Vector getLineInfo(Vector lines, String commonKey, String[] keys, String separator, int[] fieldIndexes) {
        Vector v = new Vector();
        String line;
        String[] fieldValues;
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            if (this.isNeededLine(commonKey, keys, line)) {
                fieldValues = this.getLineFields(line, fieldIndexes, separator);
                v.add(fieldValues);
            }
        }
        return v;
    }

    protected boolean isIncludeKeyInLine(String key, String line) {
        if (line == null || line.length() == 0) {
            return false;
        }
        if (key == null || key.length() == 0) {
            return false;
        }
        int index = line.indexOf(key);
        if (index == -1) {
            return false;
        }
        return true;
    }

    protected boolean isIncludeKeyInLineComplete(String key, String line) {
        if (line == null || line.length() == 0) {
            return false;
        }
        if (key == null || key.length() == 0) {
            return false;
        }
        if (line.equals(key)) {
            return true;
        }
        return false;
    }

    protected boolean isIncludeKeysInLine(String[] keys, String line) {
        if (line == null || line.length() == 0) {
            return false;
        }
        if (keys == null || keys.length == 0) {
            return false;
        }
        int index = -1;
        String key;
        for (int i = 0; i < keys.length; i++) {
            key = keys[i];
            index = line.indexOf(key);
            if (index == -1) {
                return false;
            }
        }
        return true;
    }

    protected int getIndexFromIncludeKeysInLine(String[] keys, String line) {
        if (line == null || line.length() == 0) {
            return -1;
        }
        if (keys == null || keys.length == 0) {
            return -1;
        }
        int index = -1;
        String key;
        for (int i = 0; i < keys.length; i++) {
            key = keys[i];
            index = line.indexOf(key);
            if (index != -1) {
                return i;
            }
        }
        return -1;
    }

    protected boolean isIncludeKeysInLine(String common, String key, String line) {
        if (line == null || line.length() == 0) {
            return false;
        }
        if (key == null || key.length() == 0) {
            return false;
        }
        int index = line.indexOf(common);
        if (index == -1) {
            return false;
        }
        index = line.indexOf(key);
        if (index == -1) {
            return false;
        }

        return true;
    }

    protected boolean isIncludeKeysInLines(String[] keys, Vector lines) {
        String key;
        String line;
        Vector inCludes = new Vector();
        boolean result = true;

        for (int k = 0; k < keys.length; k++) {
            inCludes.add(Boolean.valueOf(false));
        }

        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            for (int j = 0; j < keys.length; j++) {
                key = keys[j];
                if (this.isIncludeKeyInLine(key, line)) {
                    inCludes.set(j, Boolean.valueOf(true));
                    break;
                }
            }
        }
        for (int i = 0; i < inCludes.size(); i++) {
            if (((Boolean) inCludes.get(i)).booleanValue() == false) {
                result = false;
                break;
            }
        }
        return result;
    }

    protected String getIpFromFileName(String fileName) {
        StringTokenizer st = new StringTokenizer(fileName, FrameDBConstant.FILE_NAME_SEPARATOR);
        String token;
        int i = 1;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            if (i == 2) {
                //20200921 moqf HCE 备控IP 60,60,主控IP 241,242
//                if ("60".equals(token) || "61".equals(token)) {
                if ("60".equals(token) || "61".equals(token)
                        || "241".equals(token) || "242".equals(token)) {
                    return FrameDBConstant.HEC_IP_PREFIX + token;
                } else {
                    return FrameDBConstant.IP_PREFIX + token;
                }
            }
            i++;
        }
        return "";
    }

    protected String getStatusDate() {
        if (HandlerCommandBase.STATUS_DATE != null && HandlerCommandBase.STATUS_DATE.length() != 0) {
            return HandlerCommandBase.STATUS_DATE;
        }
        return DateHelper.datetimeToString(new Date());
    }

    public String getArchivingStatusDate() throws ParseException {
        if (HandlerCommandBase.STATUS_DATE != null && HandlerCommandBase.STATUS_DATE.length() != 0) {
            return DateHelper.getArchivingStatusDate(HandlerCommandBase.STATUS_DATE);
        }
        return DateHelper.getArchivingStatusDate(DateHelper.datetimeToString(new Date()));
    }

    protected String getContentBetweenLines(String startLineKey, String endLineKey, Vector lines) {
        StringBuffer sb = new StringBuffer();
        String line;
        boolean isContent = false;
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            if (line.indexOf(startLineKey) != -1) {
                isContent = true;
                continue;
            }
            if (line.indexOf(endLineKey) != -1) {
                isContent = false;
                return sb.toString();
            }
            if (isContent) {
                sb.append(line + " ");
            }

        }
        return sb.toString();
    }

    protected String getContentBetweenLinesPart(Vector lines) {
        StringBuffer sb = new StringBuffer();
        String line;
        String remark;
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            sb.append(line + " ");
        }
        remark = sb.toString();
        //目前remark最大250
        if (remark.length() > 200) {
            remark = remark.substring(0, 200);
        }
        return remark;
    }

    protected String getContentBetweenLines(Vector lines) {
        StringBuffer sb = new StringBuffer();
        String line;
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);

            sb.append(line + " ");

        }
        return sb.toString();
    }

    protected String getValueWithoutUnit(String value) {
        if (value == null || value.length() == 0) {
            return "";
        }
        return value.substring(0, value.length() - 1);
    }

    protected String getValueWithoutUnit(String value, String temp2) {
        if (value == null || value.length() == 0) {
            return "";
        }
        int temp = Integer.parseInt(value.substring(0, value.length() - 1));
        return (" " + (float) temp / 1024).substring(0, 5);
    }

    protected String getIp(String ipField) {
        if (ipField == null || ipField.length() == 0) {
            return "";
        }
        StringTokenizer st = new StringTokenizer(ipField, ".");
        String token;
        String ip = "";
        StringBuffer tmp = new StringBuffer();
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            tmp.append(token).append(".");
        }
        ip = tmp.toString();
        if (ip.length() != 0) {
            ip = ip.substring(0, ip.length() - 1);
        }
        return ip;
    }

    /**
     * 更新菜单颜色状态
     *
     * @param command add by zhongziqi 20180614
     */
    public void updateMenuStatus(String command) {
        MenuController menuController = SpringContextUtil.getBean("menuController");
        try {
            menuController.updateMenuStatus(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查行分割后的项是否与预期相同
     *
     * @param line          --行
     * @param checkFieldNum -- 检查行总共的域数量
     * @param separator     --分隔符
     * @return
     */
    public boolean checkLineFieldNum(String line, int checkFieldNum, String separator) {
        StringTokenizer stringTokenizer = new StringTokenizer(line, separator);
        int realNum = 0;
        while (stringTokenizer.hasMoreTokens()) {
            stringTokenizer.nextToken();
            realNum++;
        }
//        System.out.println("realNum = " + realNum);
        if (checkFieldNum == realNum) {
            return true;
        }
        return false;

    }

    protected int addEmailContent(EmailContent record) {
        EmailService emailService = SpringContextUtil.getBean("emailService");
        int checkOldAbnormal = emailService.checkOldAbnormal(record);
        int result = 0;
        if (checkOldAbnormal == 0) {
            result = emailService.insertSelective(record);
        } else {
            logger.info("isOldAbnormal num = " + checkOldAbnormal + " contents:" + record.toString());
        }
        return result;
    }

    protected void emailPreHandle(BaseMessage vo) {
        if (vo != null) {
            //modify by zhongzq 修复橙色告警不会通知问题
            if (FrameDBConstant.STATUS_FAILURE.equals(vo.getStatus()) || FrameDBConstant.STATUS_WARNING.equals(vo.getStatus())) {
                addEmailContent(convertToEmailContents(vo, this.getClass().getSimpleName()));
            } else if (FrameDBConstant.STATUS_NORMAL.equals(vo.getStatus())) {
                if (checkNeedUpdate(vo) > 0) {
                    updateHandledAbnormal(vo);
                }
            }
        }
    }

    protected void emailPreHandles(Vector<BaseMessage> statusInfos) {
        if (statusInfos != null && statusInfos.size() > 0) {
            for (BaseMessage vo : statusInfos) {
                emailPreHandle(vo);
            }
        }
    }

    protected void updateHandledAbnormal(BaseMessage vo) {
        EmailService emailService = SpringContextUtil.getBean("emailService");
//        if (this.getClass().getSimpleName().equals(HandlerCommandDf.class.getSimpleName())) {
//            emailService.updateHandleAbnormalWithKeyMessage(vo);
//        } else {
        emailService.updateHandleAbnormal(vo);
//        }
    }

    protected int checkNeedUpdate(BaseMessage vo) {
        EmailService emailService = SpringContextUtil.getBean("emailService");
        vo.setClassSimpleName(this.getClass().getSimpleName());
        int result = 0;
//        if (this.getClass().getSimpleName().equals(HandlerCommandDf.class.getSimpleName())) {
//
//            result = emailService.checkNeedUpdate(vo);
//        } else {
            result = emailService.checkNeedUpdate(vo);
//        }
        return result;
    }
}

