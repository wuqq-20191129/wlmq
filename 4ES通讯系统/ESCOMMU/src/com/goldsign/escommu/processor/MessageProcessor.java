package com.goldsign.escommu.processor;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.message.MessageBase;
import com.goldsign.escommu.util.CommuExceptionBufferUtil;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.LogDbUtil;
import com.goldsign.escommu.util.MessageCommonUtil;
import com.goldsign.escommu.vo.BridgeBetweenConnectionAndMessage;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import org.apache.log4j.Logger;

public class MessageProcessor implements Runnable {

    private Vector v;
    private String ip;
    private String threadNum;//线程号
    private BridgeBetweenConnectionAndMessage bridge;//连接与消息处理器间的桥
    private static Logger logger = Logger.getLogger(MessageProcessor.class.getName());

    public MessageProcessor(String aId, Vector aV) {
        this.ip = aId;
        this.v = aV;
    }

    public MessageProcessor(String aId, Vector aV, String threadNum, BridgeBetweenConnectionAndMessage bridge) {
        this.ip = aId;
        this.v = aV;
        this.threadNum = threadNum;
        this.bridge = bridge;

    }

    public void run() {
        String messageId = null;
        String messageSequ = null;
        MessageBase msg = null;
        byte[] data = null;

        try {
            data = (byte[]) (v.get(1));
            messageId = "" + (char) data[0] + (char) data[1];
            if (v.size() >= 3) {
                messageSequ = (Integer) v.get(2) + "";
            } else {
                messageSequ = MessageCommonUtil.getMessageSequ();
            }
            //messageSequ = ProcessorUtil.getMessageSequ();

            String messageClass = (String) AppConstant.MESSAGE_CLASSES.get(messageId);
            msg = (MessageBase) Class.forName(AppConstant.MessageClassPrefix + messageClass).newInstance();
            msg.init(ip, messageSequ, data, this.threadNum, this.bridge, messageId);
            msg.run();


            LogDbUtil.writeRecvSendLog(new Date(System.currentTimeMillis()), ip, "0", messageId, messageSequ, data, "0");

        } catch (SQLException e) {
            //  LogUtil.writeRecvSendLog(new Date(System.currentTimeMillis()),ip,"0",messageId,messageSequ,data,"1");
            //连接数据库异常
            if (e.getErrorCode() == 0) {
                DateHelper.screenPrintForEx("捕获连接数据库异常:" + "错误代码 " + e.getErrorCode() + " 消息" + e.getMessage() + "消息将放入缓存恢复连接时处理");
                CommuExceptionBufferUtil.setSqlUnHandleMsgs(this.ip, data);

            } else {
                try {
                    LogDbUtil.writeRecvSendLog(new Date(System.currentTimeMillis()), ip, "0", messageId, messageSequ, data, "1");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            try {
                LogDbUtil.writeRecvSendLog(new Date(System.currentTimeMillis()), ip, "0", messageId, messageSequ, data, "1");
                DateHelper.screenPrintForEx("调用消息(id=" + messageId+ ") 错误! Message sequ:" + messageSequ + "." + e);
                logger.error("调用消息(id=" + messageId+ ") 错误! Message sequ:" + messageSequ + "." + e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
