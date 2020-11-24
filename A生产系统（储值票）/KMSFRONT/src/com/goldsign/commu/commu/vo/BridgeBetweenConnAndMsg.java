package com.goldsign.commu.commu.vo;

import com.goldsign.commu.commu.connection.CommuConnection;
import com.goldsign.commu.commu.message.MessageBase;

/**
 *
 * @author Administrator
 */
public class BridgeBetweenConnAndMsg {
    private String msgType;
    private CommuConnection connection;
    private MessageBase messageProcessor;

    /**
     * @return the msgType
     */
    public String getMsgType() {
        return msgType;
    }

    /**
     * @param msgType the msgType to set
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    /**
     * @return the connection
     */
    public CommuConnection getConnection() {
        return connection;
    }

    /**
     * @param connection the connection to set
     */
    public void setConnection(CommuConnection connection) {
        this.connection = connection;
    }

    /**
     * @return the messageProcessor
     */
    public MessageBase getMessageProcessor() {
        return messageProcessor;
    }

    /**
     * @param messageProcessor the messageProcessor to set
     */
    public void setMessageProcessor(MessageBase messageProcessor) {
        this.messageProcessor = messageProcessor;
    }


}
