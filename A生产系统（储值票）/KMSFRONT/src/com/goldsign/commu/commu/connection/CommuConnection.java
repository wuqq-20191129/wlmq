package com.goldsign.commu.commu.connection;

import com.goldsign.commu.commu.application.BaseApplication;
import com.goldsign.commu.commu.env.BaseConstant;
import com.goldsign.commu.commu.env.CommuConstant;
import com.goldsign.commu.commu.exception.CommuException;
import com.goldsign.commu.commu.message.MessageProcessor;
import com.goldsign.commu.commu.thread.CommuMessageHandleThread;
import com.goldsign.commu.commu.thread.CommuThreadManager;
import com.goldsign.commu.commu.util.SocketUtil;
import com.goldsign.commu.commu.vo.BridgeBetweenConnAndMsg;
import com.goldsign.commu.commu.vo.CommuHandledMessage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.log4j.Logger;

public class CommuConnection implements Runnable {

    private static Logger logger = Logger.getLogger(CommuConnection.class.getName());
    
    private Socket socket;
    private String connIp;
    private int connID = -1;
    private BufferedInputStream in;
    private OutputStream out;
    private boolean connecting = true;
    private String resultCode = "";
    private List<byte[]> msgQuque = new ArrayList<byte[]>();
    private final static Object MSG_QUQUE_LOCK = new Object();
    
    private BridgeBetweenConnAndMsg bridge = new BridgeBetweenConnAndMsg();
    private CommuThreadManager commuThreadManager = new CommuThreadManager();
    private ConnectionWriter connectionWriter = null;
    private ConnectionReader connectionReader = null;
  
    public CommuConnection(Socket aSocket, String connIp, int connID)
            throws CommuException {
        this.socket = aSocket;
        this.connIp = connIp;
        this.connID = connID;

        try {
            socket.setSoTimeout(BaseConstant.ClientSocketTimeOut);
            in = new BufferedInputStream(socket.getInputStream());
            connectionReader = ((BaseApplication)BaseConstant.application).getConnectionReader(in); 
            out = socket.getOutputStream();
            connectionWriter = ((BaseApplication)BaseConstant.application).getConnectionWriter(this,out);
        } catch (SocketException e) {
            logger.error("Socket 错误 - " + e);
            throw new CommuException(e.getMessage());
        } catch (IOException e) {
            logger.error("I/O 错误 - " + e);
            throw new CommuException(e.getMessage());
        }
    }
    
    @Override
    public void run() {

        try {
            //启动写线程
            if(!CommuConstant.COMMU_MODE_SYN){
                startWriteThread();
            }

            //读取消息
            while (connecting) {

                //读
                Vector fromReader = readClientDatas();

                //设置连接及处理消息间的对象值
                this.bridge.setConnection(this);
                
                //直接处理消息
                processReaderResult(this.connIp, fromReader);

                //读消息等
                Thread.sleep(BaseConstant.GetMessageFrequency);
            }
        } catch (Exception e1) {
            logger.error(connIp + " - 错误,连接将被关闭!" + e1);
            closeConnection();
            closeWriteThread();
        }
    }
    
    private void startWriteThread(){
        
        connectionWriter.start();
    }
    
    private void closeWriteThread(){
        
        if(null != connectionWriter){
            connectionWriter.interrupt();
        }
    }
        
    public static void setMsgToQuque(String ip, byte[] msg){
        
        CommuConnection commuConnection = SocketUtil.getCommuConnection(ip);
        if(null != commuConnection){
            commuConnection.setMsgToQuque(msg);
        }
    }
    
    public void setMsgToQuque(byte[] msg){
        
        synchronized(MSG_QUQUE_LOCK){
            msgQuque.add(msg);
            //logger.info("add...队列长度："+msgQuque.size());
        }  
        if(CommuConstant.COMMU_MODE_SYN){
            writeClientDatas();
        }
    }
    
    public byte[] getMsgFromQuque(){
        
        byte[] msg = null;
        
        synchronized(MSG_QUQUE_LOCK){
            if(msgQuque.isEmpty()){
                return null;
            }
            msg = msgQuque.get(0);
            msgQuque.remove(0);
            //logger.info("del...队列长度："+msgQuque.size());
        }
        
        return msg;
    }

    public void processReaderResult(String ip, Vector aReaderResult) {
       
        resultCode = (String) (aReaderResult.get(0));
        if (resultCode.substring(0, 1).equals(CommuConstant.RESULT_CODE_SUC)) {
            connecting = true;
            if(CommuConstant.COMMU_MODE_SYN){
                MessageProcessor mp = new MessageProcessor(ip, aReaderResult, "-1", this.bridge);
                mp.run();
            }else{
                CommuHandledMessage msg = new CommuHandledMessage(ip, aReaderResult, bridge);      
                CommuMessageHandleThread handleThread = commuThreadManager.getNextMsgHandleThread();
                handleThread.setHandlingMsg(msg);
            }
        } else {
            connecting = false;
            logger.info(ip + " - 读消息返回，错误( "+ resultCode + ")!连接将被关闭!");
        }
    }

    public void closeConnection() {
        try {
            if(null != in){
                in.close();
            }
            if(null != out){
                out.close();
            }
            if(null != socket){
                socket.close();
            }
            
            connecting = false;
            
            BaseConstant.allConnectingIps.put(connIp, CommuConstant.CONN_CLOSED);
                
            //从缓存中删除连接对象
            SocketUtil.removeConnection(connIp);
            
        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    //读客户端数据
    private Vector readClientDatas() {

        return connectionReader.read();
    }
    
    //写客户端数据
    protected void writeClientDatas() {
        
        connectionWriter.run();
    }
    
    public InputStream getInputStream(){
       
       return in;
   }
   
   public OutputStream getOutputStream(){
   
       return out;
   }
}
