package com.goldsign.commu.commu.message;

import com.goldsign.commu.commu.env.BaseConstant;
import com.goldsign.commu.commu.vo.BridgeBetweenConnAndMsg;
import java.util.Vector;
import org.apache.log4j.Logger;

public class MessageProcessor implements Runnable {

    private static Logger logger = Logger.getLogger(MessageProcessor.class.getName());
    
    private Vector aReaderResult;
    private String ip;
    private String threadNum;//线程号
    private BridgeBetweenConnAndMsg bridge;//连接与消息处理器间的桥

    public MessageProcessor(String aId, Vector aReaderResult) {
        this.ip = aId;
        this.aReaderResult = aReaderResult;
    }

    public MessageProcessor(String aId, Vector aReaderResult, String threadNum, 
            BridgeBetweenConnAndMsg bridge) {
        this.ip = aId;
        this.aReaderResult = aReaderResult;
        this.threadNum = threadNum;
        this.bridge = bridge;

    }

    @Override
    public void run() {
        String messageId = null;
        MessageBase msg = null;
        Object[] data = null;

        try {
            data = (Object[]) aReaderResult.get(1);
            messageId = (String)data[0];

            String messageClass = (String) BaseConstant.MESSAGE_CLASSES.get(messageId);
            msg = (MessageBase) Class.forName(BaseConstant.MessageClassPrefix + messageClass).newInstance();
            msg.init(ip, data, this.threadNum, this.bridge, messageId);
            msg.run();


        }catch (Exception e) { 
            logger.error("调用消息(id=" + messageId+ ") 错误!"+ e);    
        }

    }
}
