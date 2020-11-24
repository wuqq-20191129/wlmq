/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.commu.frame.util;

import com.goldsign.commu.frame.buffer.ThreadMonitorBuffer;
import com.goldsign.commu.frame.constant.FrameLogConstant;
import com.goldsign.commu.frame.constant.FrameThreadPoolConstant;
import com.goldsign.commu.frame.dao.ComThreadPoolMonitorDao;
import com.goldsign.commu.frame.manager.CommuThreadManager;
import com.goldsign.commu.frame.message.AnalyzeMessageBase;
import com.goldsign.commu.frame.thread.CommuMessageHandleThread;
import com.goldsign.commu.frame.vo.CommuHandledMessage;
import com.goldsign.commu.frame.vo.TMThreadMsg;
import com.goldsign.commu.frame.vo.TMThreadResetVo;
import com.goldsign.commu.frame.vo.TMThreadStatusVo;

import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ThreadUtil {

    private static Logger logger = Logger.getLogger(ThreadUtil.class.getName());

    // 替换线程
    public static void writeInfoForReplaceThread(CommuMessageHandleThread tnew,
            CommuMessageHandleThread t, TMThreadStatusVo threadStatus) {
        TMThreadResetVo vo = ThreadUtil.getThreadResetVo(tnew, t, threadStatus);
        ComThreadPoolMonitorDao dao = new ComThreadPoolMonitorDao();
        try {
            dao.writeInfoForReplaceThread(vo);
        } catch (Exception ex) {
            PubUtil.handleExceptionNoThrow(ex, logger);
        }

    }

    public static int writeInfoForMsgHandupNumber(String msgId, int handupNum) {
        ComThreadPoolMonitorDao dao = new ComThreadPoolMonitorDao();
        int n = 0;
        try {
            n = dao.writeInfoForMsgHandupNumber(msgId, handupNum);
        } catch (Exception ex) {
            PubUtil.handleExceptionNoThrow(ex, logger);
        }
        return n;
    }

    private static TMThreadResetVo getThreadResetVo(
            CommuMessageHandleThread tnew, CommuMessageHandleThread t,
            TMThreadStatusVo threadStatus) {
        TMThreadResetVo vo = new TMThreadResetVo();
        vo.setReasonId(FrameThreadPoolConstant.ThreadDumpResonReplace);
        vo.setDumpDate(DateHelper.datetimeToString(new Date()));
        vo.setThreadId(threadStatus.getThreadId());
        vo.setThreadName(threadStatus.getThreadName());
        vo.setThreadStatus(threadStatus.getThreadStatus());
        vo.setHdltimeStart(threadStatus.getHdlTimeStart());
        vo.setHdltimeEnd(threadStatus.getHdlEndTime());

        vo.setMsgId(threadStatus.getMsgId());
        vo.setMsgName(threadStatus.getMsgName());
        vo.setRemark("线程替换");

        vo.setThreadQueueOrdNum(t.getMsgs().size());
        vo.setThreadQueuePriNum(t.getPriorityMsgs().size());
        vo.setMessage((byte[]) t.getHandlingMsg().getReadResult().get(1));

        vo.setThreadIdNew(tnew.getName());
        vo.setThreadNameNew(tnew.getName());
        vo.setThreadQueueOrdNumNew(tnew.getMsgs().size());
        vo.setThreadQueuePriNumNew(tnew.getPriorityMsgs().size());
        vo.setThreadStatusNew(FrameThreadPoolConstant.ThreadStatusInit);
        return vo;
    }

    private static TMThreadStatusVo getThreadStatusVo(String threadIdNew) {
        TMThreadStatusVo vo = new TMThreadStatusVo();
        vo.setThreadId(threadIdNew);
        vo.setThreadName(threadIdNew);
        vo.setThreadStatus(FrameThreadPoolConstant.ThreadStatusInit);

        // vo.setHdlTimeStart(ApplicationConstant.ThreadMsgHandleTimeEmpty);
        // vo.setHdlEndTime(ApplicationConstant.ThreadMsgHandleTimeEmpty);
        return vo;
    }

    public static int updateThreadStatus(String threadIdNew) {
        TMThreadStatusVo vo = ThreadUtil.getThreadStatusVo(threadIdNew);
        return ThreadMonitorBuffer.updateThreadStatus(vo);

    }

    public static int updateThreadStatus(TMThreadStatusVo vo) {

        return ThreadMonitorBuffer.updateThreadStatus(vo);

    }

    public static String getMessageName(String messageId) {
        String[] messageIds = {
            FrameLogConstant.MESSAGE_ID_NON_RETURN,
            FrameLogConstant.MESSAGE_ID_START,
            FrameLogConstant.MESSAGE_ID_CONFIG,
            FrameLogConstant.MESSAGE_ID_PUSH_QUEUE,
            FrameLogConstant.MESSAGE_ID_PARAM_COMMU_QUEUE,
            FrameLogConstant.MESSAGE_ID_CONNECTION,
            FrameLogConstant.MESSAGE_ID_SOCKET_EXCHAGE,
            FrameLogConstant.MESSAGE_ID_FTP};
        String[] messageNames = {
            FrameLogConstant.MESSAGE_ID_NON_RETURN_NAME,
            FrameLogConstant.MESSAGE_ID_START_NAME,
            FrameLogConstant.MESSAGE_ID_CONFIG_NAME,
            FrameLogConstant.MESSAGE_ID_PUSH_QUEUE_NAME,
            FrameLogConstant.MESSAGE_ID_PARAM_COMMU_QUEUE_NAME,
            FrameLogConstant.MESSAGE_ID_CONNECTION_NAME,
            FrameLogConstant.MESSAGE_ID_SOCKET_EXCHAGE_NAME,
            FrameLogConstant.MESSAGE_ID_FTP_NAME};
        for (int i = 0; i < messageIds.length; i++) {
            if (messageId.equals(messageIds[i])) {
                return messageNames[i];
            }
        }
        return "";
    }

    public static String getMsgId(CommuHandledMessage msg) {
        byte[] data = (byte[]) msg.getReadResult().get(1);
        String messageId = "" + (char) data[0] + (char) data[1];
        return messageId;
    }

    public static Vector getNeedDelMsgs() {
        return ThreadMonitorBuffer.getNeedDelMsgs();
    }

    public static HashMap delMsgsFromThreadMsgQueue(Vector msgKeysDel) {
        if (msgKeysDel.isEmpty()) {
            return new HashMap();
        }
        TMThreadMsg vo;
        int n = 0;
        HashMap hm = new HashMap();
        Vector v;
        for (int i = 0; i < msgKeysDel.size(); i++) {
            vo = (TMThreadMsg) msgKeysDel.get(i);
            v = ThreadUtil.delMsgFromThreadMsgQueue(vo);
            if (!v.isEmpty()) {
                hm.put(vo, v);
            }

        }
        return hm;
    }

    public static Vector delMsgFromThreadMsgQueue(TMThreadMsg vo) {

        return CommuThreadManager.delMsgFromThreadMsgQueue(vo);
    }

    public static void setBufferThreadHandup(CommuHandledMessage cmsg) {
        String msgId = ThreadUtil.getMsgId(cmsg);
        String amClassName = FrameThreadPoolConstant.ThreadMsgAnalyzeClassPrefix
                + msgId;
        AnalyzeMessageBase am;
        TMThreadMsg msgKey;

        try {
            am = (AnalyzeMessageBase) Class.forName(amClassName).newInstance();
        } catch (Exception ex) {
            logger.error(ex);
            return;
        }
        // 统计挂起消息的挂起次数

        if (am != null) {
            msgKey = am.getMsgKeyInfo(((byte[]) cmsg.getReadResult().get(1)));
            ThreadMonitorBuffer.setBufferThreadHandup(msgKey);
        }

    }

    public static TMThreadMsg getMessageKeyInfo(CommuHandledMessage cmsg) {
        String msgId = ThreadUtil.getMsgId(cmsg);
        String amClassName = FrameThreadPoolConstant.ThreadMsgAnalyzeClassPrefix
                + msgId;
        AnalyzeMessageBase am;
        TMThreadMsg msgKey = null;

        try {
            am = (AnalyzeMessageBase) Class.forName(amClassName).newInstance();
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
        // 统计挂起消息的挂起次数

        if (am != null) {
            msgKey = am.getMsgKeyInfo(((byte[]) cmsg.getReadResult().get(1)));
        }

        return msgKey;

    }

    public static int writeInfoForDelMsgs(HashMap msgsDel) {

        ComThreadPoolMonitorDao dao = new ComThreadPoolMonitorDao();
        String curDate = DateHelper.datetimeToString(new Date());
        int n = 0;
        try {
            n = dao.writeInfoForDelMsgs(msgsDel, curDate);
        } catch (Exception ex) {
            PubUtil.handleExceptionNoThrow(ex, logger);
        }
        return n;
    }

}
