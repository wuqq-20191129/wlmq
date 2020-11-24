/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.etmcs.commu;

import com.goldsign.csfrm.util.CharUtil;
import com.goldsign.etmcs.exception.MessageException;
import com.goldsign.etmcs.vo.EsCommuReadResult;
import com.goldsign.etmcs.vo.EsCommuWriteParam;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 * ES SOCKET通讯客户端
 *
 * @author lenovo
 */
public class EsCommuClient {

    private static final Logger logger = Logger.getLogger(EsCommuClient.class.getName());
    
    private static final byte STX = (byte) 0xEB;//报文开始字符
    private static final byte ETX = 0x03;//报文结束字符
    private static final byte QRY = 0x01;//查询报文
    private static final byte NDT = 0x02;//空数据报文
    private static final byte DTA = 0x04;//数据报文
    private static final byte[] HEADER = {STX, DTA, 0};//报文头
    private static final byte[] QUERY = {STX, QRY, 0, 0, 0, ETX};//查询报文
    private static boolean stopReader = false;
    private static String resultCode = "";//结果代码
    private static int fromClient = -1;//客户端数据
    private static int serverSerialNo = 0;//服务器端序列号
    private static int clientSerialNo = 0;//客户端端序列号
    private static Socket socket = null;
    private static OutputStream out;//输出流
    private static InputStream in;//输入流

    public EsCommuClient(){}
    
    /**
     * 取序列号
     * 
     * @return 
     */
    private static int getClientSerialNo(){
   
        int serialNo = clientSerialNo;
        clientSerialNo++;
        if(clientSerialNo>255){
            clientSerialNo = 0;
        }
        return serialNo;
    }
    
    /**
     * 发送数据
     * 
     * @param param
     * @throws IOException 
     */
    public static void sendData(EsCommuWriteParam param) throws IOException {
        sendData(param.getMessage());
    }
    
    /**
     * 发送数据
     * 
     * @param msg
     * @throws IOException 
     */
    public static void sendData(byte[] msg) throws IOException {
        send(msg, getClientSerialNo());
    }
    
    /**
     * 发送数据
     * 
     * @param b
     * @param aSerialNo
     * @throws IOException
     */
    private static void send(byte[] b, int aSerialNo) throws IOException {
        
        logger.info("发送数据:"+CharUtil.byteToHex(b));
        HEADER[2] = (byte) aSerialNo;
        out.write(HEADER);
        out.write((byte) ((b.length) % 256));
        out.write((byte) ((b.length) / 256));
        out.write(b);
        out.write(ETX);
        out.flush();
    }

    /**
     * 读取数据
     * 
     * @return 
     */
    public static EsCommuReadResult readData(){
        
        EsCommuReadResult result = new EsCommuReadResult();
        
        Vector rv = read();
        String rc = (String) rv.get(0);
        byte[] rd = (byte[]) rv.get(1);
        
        result.setCode(rc);
        result.setData(rd);
        
        return result;
    }
    
    /**
     * 读数据
     *
     * @return
     */
    private static Vector read() {
        
        Vector readerResult = new Vector();
        int dataLength;
        byte[] data = null;

        try {
            //read STX
            readOneByte();
            if ((byte) fromClient == -1) {
                logger.info("消息读错误！");
                resultCode = "1002";
                throw new MessageException(resultCode);
            }
            if ((byte) fromClient != STX) {
                logger.info("消息起始标志错误！");
                resultCode = "1101";
                throw new MessageException(resultCode);
            }

            //read message type
            readOneByte();
            if (fromClient != NDT && fromClient != DTA && fromClient != QRY) {
                logger.info("消息类型错误！");
                resultCode = "1102";
                throw new MessageException(resultCode);
            }

            //read serial NO
            readOneByte();                    
            serverSerialNo = fromClient;

            //read data length
            readOneByte();
            dataLength = fromClient + in.read() * 256;
            logger.info("长度:" + dataLength);

            //read data
            data = new byte[dataLength];
            readBytes(data, 0, data.length);
           
            //read ETX
            readOneByte();
            if (fromClient != ETX) {
                logger.info("消息结束标志错误！");
                resultCode = "1104";
                throw new MessageException(resultCode);
            } else {
                if (dataLength == 0) {
                    resultCode = "0101";    //没数据
                } else {
                    resultCode = "0100";    //有数据
                }
            }
           
        } catch (IOException e) {
            resultCode = "1002";
            logger.error("IO 异常:" + e.getMessage());
        } catch (MessageException e) {
            logger.error("接收的数据处理时有错,错误代码为:" + e.getMessage());
        } finally {
            //logger.info("接收数据："+CharUtil.byteToHex(data));
            readerResult.add(resultCode);
            readerResult.add(data);
            readerResult.add(new Integer(serverSerialNo));
            return readerResult;
        }
    }

    /**
     * 读单字节数据
     *
     * @throws IOException
     * @throws MessageException
     */
    private static void readOneByte()
            throws IOException, MessageException {
        if (!stopReader) {
            fromClient = in.read();
        } else {
            resultCode = "1105";
            throw new MessageException(resultCode);
        }
    }

    /**
     * 读指定长度数据
     *
     * @param buff
     * @param off
     * @param len
     * @throws IOException
     * @throws MessageException
     */
    private static void readBytes(byte[] buff, int off, int len)
            throws IOException, MessageException {
        int readTotal = 0;
        if (!stopReader) {
            readTotal = in.read(buff, off, len);
            if (readTotal != len) {
                resultCode = "1201";
                throw new MessageException(resultCode);
            }

        } else {
            resultCode = "1105";
            throw new MessageException(resultCode);
        }
    }

    /**
     * 打开连接
     *
     * @throws Exception
     */
    public static void openConServer(String ip, int port)
            throws UnknownHostException, IOException {
        
        socket = new Socket(ip, port);
        out = socket.getOutputStream();
        in = socket.getInputStream();
    }

    /**
     * 断开连接
     * 
     * @throws IOException 
     */
    public static void disConServer() throws IOException {
        
        if(null != in){
            in.close();
        }
        if(null != out){
            out.close();
        }
        if(null != socket){
            socket.close();
        }
        in = null;
        out = null;
        socket = null;
    }
    
    //检测通讯连接
    public static boolean isConnected(){
        try{
            //socket.sendUrgentData(0);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            sendQuery(getClientSerialNo());
            return true;
        }catch(Exception se){
            return false;
        }
    }
    
    /**
     * 发送查询报文
     *
     * @param aSerialNo
     * @throws IOException
     */
    private static void sendQuery(int aSerialNo) throws IOException {
        QUERY[2] = (byte) aSerialNo;
        out.write(QUERY);
        out.flush();
    }
}
