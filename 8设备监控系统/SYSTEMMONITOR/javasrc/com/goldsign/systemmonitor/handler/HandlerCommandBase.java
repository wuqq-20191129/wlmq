package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.util.DateHelper;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

public abstract class HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandBase.class);
    protected String command;
    protected Vector lines;
    protected static String STATUS_DATE;
    protected String status;
    protected String ip;

    public HandlerCommandBase() {
        super();
        // TODO Auto-generated constructor stub
    }

    public abstract void handleCommand(String command, Vector lines, String fileName);

    protected void setParameter(String command, Vector lines) {
        this.command = command;
        this.lines = lines;
    }

    public void printLines() {
        for (int i = 0; i < lines.size(); i++) {
            logger.info(lines.get(i));
        }


    }

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
        int index = tmp.indexOf(FrameDBConstant.Command_seperator_bracket_left);
        int index1 = tmp.indexOf(FrameDBConstant.Command_seperator_bracket_right);
        tmp = tmp.substring(index + 1, index1);
        return tmp;
    }

    protected String getSpace(String space) {
        String tmp = space.trim();
        int index = tmp.indexOf(FrameDBConstant.Command_seperator_percent);
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

    /*
     * 从行的公共关键字及可能的关键字判断

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
    /*
     * 从行的某一字段是否包含关键字判断

     */

    protected boolean isNeededLineFromField(int fieldIndex, String key, String separator, String line) {

        String fieldValue = this.getLineField(line, fieldIndex, separator);
        if (this.isIncludeKeyInLine(key, fieldValue)) {
            return true;
        }
        return false;

    }
    /*
     * 从行的某一字段是否等于可能的关键字判断
     */

    protected boolean isNeededLineFromFieldComplete(int fieldIndex, String key, String separator, String line) {

        String fieldValue = this.getLineField(line, fieldIndex, separator);
        if (this.isIncludeKeyInLineComplete(key, fieldValue)) {
            return true;
        }
        return false;

    }
    /*
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

    protected Vector getLineInfo(Vector lines, String commonKey, String[] keys, String seperator, int[] fieldIndexes) {
        Vector v = new Vector();
        String line;
        String[] fieldValues;
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            if (this.isNeededLine(commonKey, keys, line)) {
                fieldValues = this.getLineFields(line, fieldIndexes, seperator);
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
            inCludes.add(new Boolean(false));
        }

        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            //if(line.indexOf("PS0")!=-1)
            //logger.info("PS0="+line);
            for (int j = 0; j < keys.length; j++) {
                key = keys[j];
                if (this.isIncludeKeyInLine(key, line)) {
                    inCludes.set(j, new Boolean(true));
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
        StringTokenizer st = new StringTokenizer(fileName, FrameDBConstant.FileNameSeperator);
        String token;
        int i = 1;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            if (i == 2) {
                return FrameDBConstant.Ip_prefix + token;
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
        boolean isContent = false;
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            sb.append(line + " ");
        }
        return sb.toString().substring(0, 20);
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

    //增加 2012-10-16 by lml
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
        int index = 1;
        String ip = "";
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            ip += token + ".";
            index++;
        }
        if (ip.length() != 0) {
            ip = ip.substring(0, ip.length() - 1);
        }
        return ip;

    }
}
