package com.goldsign.commu.commu.message;

import com.goldsign.commu.commu.connection.CommuConnection;
import com.goldsign.commu.commu.exception.CommuException;
import com.goldsign.commu.commu.vo.BridgeBetweenConnAndMsg;
import com.goldsign.commu.commu.vo.RequestVo;
import com.goldsign.commu.commu.vo.ResponseVo;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.log4j.Logger;

public abstract class MessageBase {

    private static Logger logger = Logger.getLogger(MessageBase.class.getName());
    
    protected String messageFrom;
    protected String thisClassName = this.getClass().getName();
    protected Object[] data;
    protected long hdlStartTime; //处理的起始时间
    protected long hdlEndTime;//处理的结束时间
    protected String threadNum;//处理线程号别
    protected BridgeBetweenConnAndMsg bridge;

    public static Object SYNCONTROL = new Object();
    
    public void init(String ip, Object[] data, String threadNum, BridgeBetweenConnAndMsg bridge, 
            String messageId) throws Exception {
        messageFrom = ip;
        this.data = data;
        thisClassName = thisClassName.substring(thisClassName.lastIndexOf(".") + 1, thisClassName.length());
        this.threadNum = threadNum;
        if (bridge != null) {
            this.bridge = bridge;
            this.bridge.setMessageProcessor(this);
            this.bridge.setMsgType(messageId);
        }

    }

    public void run() throws Exception {
        try {
            this.hdlStartTime = System.currentTimeMillis();
            logger.info("处理消息...");
            synchronized (SYNCONTROL) {
                this.process();
            }
            this.hdlEndTime = System.currentTimeMillis();
        } catch (Exception e) {
            logger.error(e);
            throw new CommuException(e);
        } finally {
            //记录处理日志
            logger.info("线程号："+this.threadNum+",来自["+this.messageFrom+"]消息－"
                    + "开始时间:"+this.hdlStartTime+",结束时间："+this.hdlEndTime);
        }
    }
        
    public abstract void process() throws Exception;
    
    protected RequestVo getRequestData(){
        
        RequestVo messageVo = new RequestVo();
        messageVo.setMsgType((String)data[0]);
        
        return messageVo;
    }
    
    protected void sendResponseData(ResponseVo responseVo) throws IOException{
        
        byte[] message = responseVo.getData();
        CommuConnection.setMsgToQuque(messageFrom, message);
    }
    
   protected InputStream getInputStream(){
       
       return bridge.getConnection().getInputStream();
   }
   
   protected OutputStream getOutputStream(){
   
       return bridge.getConnection().getOutputStream();
   }
   
   protected void closeCommuConnection(){
       bridge.getConnection().closeConnection();
   }
}
