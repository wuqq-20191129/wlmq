package com.goldsign.escommu.connection;

import com.goldsign.escommu.dao.EsFileDao;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.CommuConstant;
import com.goldsign.escommu.env.LogConstant;
import com.goldsign.escommu.exception.CommuException;
import com.goldsign.escommu.message.ConstructMessage45;
import com.goldsign.escommu.processor.MessageProcessor;
import com.goldsign.escommu.thread.CommuMessageHandleThread;
import com.goldsign.escommu.thread.CommuThreadManager;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.LogDbUtil;
import com.goldsign.escommu.util.SocketUtil;
import com.goldsign.escommu.vo.BridgeBetweenConnectionAndMessage;
import com.goldsign.escommu.vo.CommuHandledMessage;
import com.goldsign.escommu.vo.FileAudErrVo;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import org.apache.log4j.Logger;

public class CommuConnection implements Runnable {

    private Socket socket;
    private String connIp;
    private int connID = -1;
    private BufferedInputStream in;
    private OutputStream out;
    private boolean connecting = true;
    private boolean isData = true;
    private String resultCode = "";
    private final byte[] HEADER = {CommuConstant.STX_B, CommuConstant.DTA, 0};
    private final byte[] QUERY = {CommuConstant.STX_B, CommuConstant.QRY, 0, 0, 0, CommuConstant.ETX}; 
    
    private static Logger logger = Logger.getLogger(CommuConnection.class.getName());
    
    private static final Object SEND_LOCK = new Object();
    
    /**
     * 日志记录使用
     */
    private long hdlStartTime; //处理的起始时间
    private long hdlEndTime;//处理的结束时间
    
    private int serialNo = 1;//序列号

    private BridgeBetweenConnectionAndMessage bridge = new BridgeBetweenConnectionAndMessage();
    private CommuThreadManager commuThreadManager = new CommuThreadManager();

    public CommuConnection() {
    }

    /**
     * 构造连接
     * 
     * @param aSocket
     * @param connIp
     * @param connID
     * @throws CommuException 
     */
    public CommuConnection(Socket aSocket, String connIp, int connID)
            throws CommuException {
        this.socket = aSocket;
        this.connIp = connIp;
        this.connID = connID;

        try {
            socket.setSoTimeout(AppConstant.ReadOneMessageTimeOut);
            in = new BufferedInputStream(socket.getInputStream());
            out = socket.getOutputStream();
        } catch (SocketException e) {
            DateHelper.screenPrint("Socket 错误 - " + e);
            throw new CommuException(e.getMessage());
        } catch (IOException e) {
            DateHelper.screenPrint("I/O 错误 - " + e);
            throw new CommuException(e.getMessage());
        }
    }

    /**
     * 循环处理
     */
    public void run() {

        long startTime = 0;
        long endTime = 0;
        Vector fromReader = null;
        try {
            
            startTime = System.currentTimeMillis();

            //ES刚连接上时，ACC发送审计信息
            sendAuditMessage(serialNo++);
            
            endTime = System.currentTimeMillis();
            logger.info("发送审计消息成功.耗时：" + (endTime - startTime));

            while (connecting) {
                
                this.hdlStartTime = System.currentTimeMillis();
                startTime = System.currentTimeMillis();

                //读取消息
                fromReader = readClientDatas(-1);

                endTime = System.currentTimeMillis();
                //logger.info("读数据,序列号：" + fromReader.get(2) + ",耗时:" + (endTime - startTime));
                
                startTime = System.currentTimeMillis();

                //设置连接及处理消息间的对象值
                this.bridge.setConnection(this);
                //直接处理消息
                processReaderResultDirectly(this.connIp, fromReader);

                endTime = System.currentTimeMillis();
                //logger.info("处理消息耗时:" + (endTime - startTime));

                if ((endTime - startTime) >= 3000) {
                    logger.error(connIp + "-接收消息时间超过3秒为：" + (endTime - startTime));
                }
               
            }
        } catch (Exception e) {
            DateHelper.screenPrintForEx(connIp + " - 错误,连接将被关闭!" + e);
            logger.error(connIp + " - 错误,连接将被关闭!" + e);

            this.hdlEndTime = System.currentTimeMillis();
            //记录日志
            LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_SOCKET_EXCHAGE, connIp,
                    this.hdlStartTime, this.hdlEndTime, LogConstant.RESULT_HDL_FAIL,
                    Thread.currentThread().getName(), AppConstant.LOG_LEVEL_ERROR, e.getMessage());

        } finally {
            
            DateHelper.screenPrintForEx(connIp + " - 连接关闭!");
            logger.info(connIp + " - 连接关闭!");
            //关闭连结
            closeConnection();
        }
    }

    /**
     * 异步处理读结果
     * 
     * @param ip
     * @param aReaderResult 
     */
    public void processReaderResult(String ip, Vector aReaderResult) {
        
        CommuHandledMessage msg = null;
        CommuMessageHandleThread handleThread = null;

        resultCode = (String) (aReaderResult.get(0));
        if (resultCode.substring(0, 1).equals(CommuConstant.RESULT_CODE_SUC)) {
            connecting = true;
            if (resultCode.equals(CommuConstant.RESULT_CODE_DTA)) {
                isData = true;

                msg = new CommuHandledMessage(ip, aReaderResult, bridge);      
                handleThread = commuThreadManager.getNextMsgHandleThread();
                handleThread.setHandlingMsg(msg);

            } else {
                //DateHelper.screenPrint("ip:"+ip+"返回码："+resultCode);
                isData = false;
            }
        } else {
            connecting = false;
            DateHelper.screenPrint(ip + " - 读消息返回，错误( "+ resultCode + ")!连接将被关闭!");
            logger.info(ip + " - 读消息返回，错误( " + resultCode + ")!连接将被关闭!");
        }
    }

    /**
     * 同步处理读结果
     * 
     * @param ip
     * @param aReaderResult 
     */
    public void processReaderResultDirectly(String ip, Vector aReaderResult) {

        String resultCode = (String) aReaderResult.get(0);
        if (resultCode.substring(0, 1).equals(CommuConstant.RESULT_CODE_SUC)) {
            connecting = true;
            if (resultCode.equals(CommuConstant.RESULT_CODE_DTA)) {
                isData = true;
                
                MessageProcessor mp = new MessageProcessor(ip, aReaderResult, "-1", this.bridge);
                mp.run();
            }else{
                //DateHelper.screenPrint("ip:"+ip+"返回码："+resultCode);
                isData = false;
            }
        }else{
            connecting = false;
            DateHelper.screenPrint(ip + " - 读消息返回，错误( "+ resultCode + ")!连接将被关闭!");
            logger.info(ip + " - 读消息返回，错误( " + resultCode + ")!连接将被关闭!");
        }
    }

    /**
     * 设置是否连接
     * 
     * @param isConnected 
     */
    public void setIsConnnection(boolean isConnected) {
        this.connecting = isConnected;
    }

    /**
     * 获取是否连接
     * 
     * @return 
     */
    public boolean getIsConnnection() {
        return this.connecting;
    }
    
    /**
     * 关闭连结
     * 
     */
    public void closeConnection() {
        try {
            if(null != in){
                in.close();
            }
            if(null != out){
                out.close();
            }
            //关闭客户端连接的socket
            if(null != socket){
                socket.close();
            }
            
            this.setIsConnnection(false);
            
            AppConstant.all_connecting_ip.put(connIp, CommuConstant.CONN_CLOSED);
            //写连接关闭日志
            LogDbUtil.writeConnectLog(new Date(System.currentTimeMillis()), connIp, 
                    LogConstant.RESULT_HDL_WARN, "连接关闭");
            
            //从缓存中删除连接对象
            SocketUtil.removeConnection(this.connID);
        } catch (IOException e) {
            DateHelper.screenPrintForEx(connIp + " - 连接关闭错误! - " + e);
            logger.error(connIp + " - 连接关闭错误! - " + e);
        } catch (Exception e) {
            DateHelper.screenPrintForEx(connIp + " - 连接关闭错误! - " + e);
            logger.error(connIp + " - 连接关闭错误! - " + e);
        }
    }
    
    /**
     * 发送数据
     * 
     * @param b
     * @param aSerialNo
     * @throws IOException 
     */
    public void sendData(byte[] b, int aSerialNo) throws IOException {
        //private void sendData(byte[] b, int aSerialNo) throws IOException {
        synchronized(SEND_LOCK){
            HEADER[2] = (byte) aSerialNo;
            out.write(HEADER);
            out.write((byte) ((b.length) % 256));
            out.write((byte) ((b.length) / 256));
            out.write(b);
            out.write(CommuConstant.ETX);

            out.flush();
        }
        printRecBs(b);//打印测试用的
    }
    
    private void printRecBs(byte[] datas){
        System.out.println("-----------------测试 打印返回数据 START-------------------");
        for(byte b: datas){
            System.out.print(b+" ");
        }
        System.out.println();
        String str = new String(datas);
        System.out.println("数据:"+str);
        System.out.println("-----------------测试 打印返回数据  EDN-------------------");
    }

    /**
     * 发送查询报文
     * 
     * @param aSerialNo
     * @throws IOException 
     */
    public void sendQuery(int aSerialNo) throws IOException {
        synchronized(SEND_LOCK){
            QUERY[2] = (byte) aSerialNo;
            out.write(QUERY);
        }
    }

    /**
     * 发送审计消息
     * 
     * @param seqNo
     * @throws IOException 
     */
    private void sendAuditMessage(int seqNo) throws Exception {

        String deviceId = this.getDeviceId();
        logger.info("取设备ID：" + deviceId);
        EsFileDao esFileDao = new EsFileDao();
        List<FileAudErrVo> auditFiles = esFileDao.getAudtErrorFiles(deviceId);//取审计文件
        byte[] datas = new ConstructMessage45().constructMessage(auditFiles);
        logger.info("发送审计消息:" + datas + ",序列号:" + seqNo);
        sendData(datas, seqNo);
        esFileDao.updateAuditErrorFiles(auditFiles);//更新审计文件
    }

    /**
     * 根据IP取设备号
     * 
     * @return 
     */
    public String getDeviceId() {

        Set<String> deviceIds = AppConstant.devIps.keySet();
        for (String deviceId : deviceIds) {
            String deviceIp = (String) AppConstant.devIps.get(deviceId);
            if (deviceIp.equals(connIp)) {
                return deviceId;
            }
        }  
        return null;
        //return "1001"; //测试暂写死
    }

    //检查是否有数据可读
    private boolean checkIsDataAvailable() throws IOException {
        if (in.available() > 0) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 读客户端数据
     * 
     * @param clientSerialNo
     * @return 
     */
    private Vector readClientDatas(int clientSerialNo) {

        ConnectionReader cr = new ConnectionReader(in, clientSerialNo);

        return cr.read();
    }
    
    /**
     * 发送测试指令
     * 
     * @throws IOException 
     */
    public void sendTestData() throws IOException {

        synchronized(SEND_LOCK){
            socket.sendUrgentData(0);
        }
        //out.write((byte) 0);
        //out.flush();
        //logger.info("发送测试数据..." + 0);
    }
}
