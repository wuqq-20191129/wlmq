/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.util;

import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.thread.CommuMessageHandleThread;
import com.goldsign.escommu.thread.CommuThreadManager;
import com.goldsign.escommu.vo.CommuHandledMessage;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class MessageCommonUtil {

    public static void processMessage(String ip, Vector aReaderResult) {
        CommuHandledMessage msg = null;
        CommuThreadManager manager = null;
        CommuMessageHandleThread handleThread = null;

        msg = new CommuHandledMessage(ip, aReaderResult, null);
        manager = new CommuThreadManager();
        handleThread = manager.getNextMsgHandleThread();

        handleThread.setHandlingMsg(msg);

    }

    public static Vector getReaderResult(byte[] msg) {
        Vector v = new Vector();
        String retCode = MessageConstant.MESSAGE_RET_RESULT_DEFAULT;
        v.add(retCode);
        v.add(msg);
        return v;
    }

    public static String getMessageName(String msgId) {
        String tmp;
        for (int i = 0; i < MessageConstant.MESSAGE_ID_ALL.length; i++) {
            tmp = MessageConstant.MESSAGE_ID_ALL[i];
            if (tmp.equals(msgId)) {
                return MessageConstant.MESSAGE_ID_ALL_NAME[i];
            }
        }
        return msgId;
    }
    
    private final static Hashtable SYN_CONTROL = new Hashtable();
    private static int messageSequ = 0;
    public static String getMessageSequ() {
        synchronized (MessageCommonUtil.SYN_CONTROL) {
            Date ssdt = new Date(System.currentTimeMillis());
            String SystemStartDateTime = DateHelper.dateToYYYYMMDDHHMMSS(ssdt);
            messageSequ = messageSequ + 1;
            String timeStr = SystemStartDateTime + "0000000000";
            String sequStr = Integer.toString(messageSequ);
            return timeStr.substring(2, 24 - (sequStr.length())) + sequStr;
        }  //return for example yyMMddHHmmss0000000001

    }
}
