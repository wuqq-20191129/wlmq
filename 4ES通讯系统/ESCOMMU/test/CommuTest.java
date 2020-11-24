/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.goldsign.escommu.exception.MessageException;
import com.goldsign.escommu.util.DateHelper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lenovo
 */
public class CommuTest {
    
    private static final byte STX_B = (byte) 0xEB;
    private static final byte ETX = 0x03;
    private static final byte QRY = 0x01;
    private static final byte NDT = 0x02;
    private static final byte DTA = 0x04;
    private static final byte[] HEADER = {STX_B, DTA, 0};
    private static final byte[] QUERY = {STX_B, QRY, 0, 0, 0, ETX};
    private static boolean stopReader = false;
    private static String resultCode = "";
    private static int fromClient = -1;
    private static int serialNoShouldBe;
    private static int serialNo;
    
    private static String IP = "10.90.1.73";//修改ES通讯IP
    private static int PORT = 5001;//端口
    private static Socket socket = null;
    private static OutputStream out;
    private static InputStream in;
    
    //测试发送报表，测试时只需改报文编号
    private static void send(int seq) throws IOException{
        
        send45(seq);//发送45
        
    }
         
    //操作员类型查询
    public static void send30(int seqNo) throws IOException{
        String msgType = "30";
        String loginFlag = "1";//1位
        String operCode = "zengrr";//8位
        String password = "zrr777  ";//8位
        String loginTime = "2012-09-19 15:10:00 ";//20位
        String deviceId = "017";//3位
        String msg = msgType+loginFlag+operCode+password+loginTime+deviceId;
        sendData(msg.getBytes(), seqNo);
        
    }
    
    //城市代码参数查询
    public static void send32(int seqNo) throws IOException{
        String msgType = "32";
        String msg = msgType;
        sendData(msg.getBytes(), seqNo);
        
    }
    
    //订单查询
    public static void send34(int seqNo) throws IOException{
        String msgType = "34";
        String oprtType = "00";//2位
        String operCode = "chenrs";//6位
        String msg = msgType+oprtType+operCode;
        sendData(msg.getBytes(), seqNo);
        
    }
    
    //更新订单状态
    public static void send36(int seqNo) throws IOException{
        String msgType = "36";
        String oprtType = "00";//2位
        String operCode = "ES0001"; //6位
        String deviceId = "001";//3位
        
        //订单数组
        String[] orderNos = new String[]{
            "00201105250001",//14位
            "01201105250002"
        };
      
        String msg = msgType+oprtType+operCode+deviceId;
        byte[] b1 = msg.getBytes();
        
        int len = orderNos.length;
        byte[] b2 = new byte[]{(byte)len, (byte)0};
        
        String content = "";
        for(String orderNo: orderNos){
            content += orderNo;
        }
        byte[] b3 = content.getBytes();
        
        byte[] b = new byte[b1.length+b2.length+b3.length];
        for(int i=0; i<b1.length; i++){
            b[i] = b1[i];
        }
        for(int i=0; i<b2.length; i++){
            b[i+b1.length] = b2[i];
        }
        for(int i=0; i<b3.length; i++){
            b[i+b1.length+b2.length] = b3[i];
        }
        
        sendData(b, seqNo);
        
    }
    
    //查询记名卡资料
    public static void send38(int seqNo) throws IOException{
        String msgType = "38";
        String orderNo = "00201105250001";//14位
        String begNo = "0         ";//10位
        String endNo = "10        ";//10位
        String msg = msgType+orderNo+begNo+endNo;
        sendData(msg.getBytes(), seqNo);
        
    }
    
    //查询票价列表
    public static void send40(int seqNo) throws IOException{
        String msgType = "40";
        
        String msg = msgType;
        sendData(msg.getBytes(), seqNo);
        
    }
    
     //查询票卡类型代码及中文
    public static void send42(int seqNo) throws IOException{
        String msgType = "42";
        
        String msg = msgType;
        sendData(msg.getBytes(), seqNo);
        
    }   
    
    //ES设备状态信息
    public static void send44(int seqNo) throws IOException{
        String msgType = "44";
        String deviceId = "0003  ";//5位
        String operCode = "aaaaaa    ";//10位
        String changeTime = "20120103 020304     ";//20位
        String state = "1   ";//4位
        String desc = "你  你好你好你好你好你好你  中";//30位
        
        String msg = msgType+deviceId+operCode+changeTime+state+desc;
        sendData(msg.getBytes(), seqNo);
        
    } 
    
    //文件通知消息
    public static void send45(int seqNo) throws IOException{
        String msgType = "45";
        String deviceId = "1001  ";//5位
        
        String[] fileNames = new String[]{
            "ES1001.01201210160001         ",//30位
            "ES1001.01201210160002         ",
        };
        String[] operCodes = new String[]{
            "ES0001    ",//10位
            "ES0001    "
        };
        
        String msg = msgType+deviceId;
        byte[] b1 = msg.getBytes();
        
        int len = fileNames.length;
        byte[] b2 = new byte[]{(byte)len, (byte)0};
        
        String content = "";
        for(int i=0; i<len; i++){
            content += fileNames[i]+operCodes[i];
        }
        byte[] b3 = content.getBytes();
        
        byte[] b = new byte[b1.length+b2.length+b3.length];
        for(int i=0; i<b1.length; i++){
            b[i] = b1[i];
        }
        for(int i=0; i<b2.length; i++){
            b[i+b1.length] = b2[i];
        }
        for(int i=0; i<b3.length; i++){
            b[i+b1.length+b2.length] = b3[i];
        }
        
        sendData(b, seqNo);
        
    } 
    
        //黑名单列表
    public static void send46(int seqNo) throws IOException{
        String msgType = "46";
       
        String msg = msgType;
        sendData(msg.getBytes(), seqNo);
        
    } 
    
    //SAM卡列表
    public static void send48(int seqNo) throws IOException{
        String msgType = "48";
       
        String msg = msgType;
        sendData(msg.getBytes(), seqNo);
        
    } 
    
    //卡号段申请
    public static void send60(int seqNo) throws IOException{
        String msgType = "60";
        String deviceId = "0003  ";//5位
        String reqTime = "20160119010203";//14位
       
        String msg = msgType+deviceId+reqTime;
        sendData(msg.getBytes(), seqNo);
        
    } 
    
    
    
    
    
    
    
    
    

        //读数据
    public static void readData() throws UnsupportedEncodingException{
        Vector v = read();
        if(null == v){
            System.out.println("返回结果集为Null.");
            return;
        }
        if(v.size() == 0){
            System.out.println("返回结果集为空.");
            return;
        }
        if(v.size() != 3){
            System.out.println("返回结果集长度不正确.");
            return;
        }
        String returnCode = (String) v.get(0);
        System.out.println("返回码:"+returnCode);
        byte[] datas = (byte[]) v.get(1);
        for(byte b: datas){
            System.out.print(b+" ");
        }
        System.out.println();
        String str = new String(datas);
        System.out.println("返回数据:"+str);
        int seqNo = (Integer) v.get(2);
        System.out.println("序列号:"+seqNo);
    }
    
    //发送数据
    public static void sendData(byte[] b, int aSerialNo) throws IOException {
    //private void sendData(byte[] b, int aSerialNo) throws IOException {
        HEADER[2] = (byte) aSerialNo;
        out.write(HEADER);
        out.write((byte) ((b.length) % 256));
        out.write((byte) ((b.length) / 256));
        out.write(b);
        out.write(ETX);
        out.flush();
        System.out.println("发送数据,序列号："+aSerialNo);
        for(byte bb: b){
             System.out.print(bb + " ");
        }
        System.out.println();
    }
    
    //读数据
    public static Vector read() {
        Vector readerResult = new Vector();
        int dataLength;
        byte[] data = null;

        try {
            //read STX
            readOneByte();
            if ((byte) fromClient == -1) {
                DateHelper.screenPrint(" - Message read error!");
                resultCode = "1002";
                throw new MessageException(resultCode);
            }
            if ((byte) fromClient != STX_B) {
                DateHelper.screenPrint(" - Message start flag error!");

                resultCode = "1101";

                throw new MessageException(resultCode);
            }

            //read message type
            readOneByte();
            if (fromClient != NDT && fromClient != DTA && fromClient != QRY) {
                DateHelper.screenPrint(" - Message type error!");
                resultCode = "1102";
                throw new MessageException(resultCode);
            }

            //read serial NO
            readOneByte();

            serialNo = fromClient;

            //read data length
            readOneByte();
            dataLength = fromClient + in.read() * 256;
            System.out.println("长度:" + dataLength);
            //read data
            data = new byte[dataLength];
            readBytes(data, 0, data.length);
            /*
             * for (int i = 0; i < dataLength; i++) { readOneByte(); data[i] =
             * (byte) (fromClient); }
             */

            //read ETX
            readOneByte();
            if (fromClient != ETX) {
                DateHelper.screenPrint(" - Message end flag error!");
                resultCode = "1104";
                throw new MessageException(resultCode);
            } else {
                if (dataLength == 0) {
                    resultCode = "0101";
                } else {
                    resultCode = "0100";
                }
            }
            //统计正常接受的消息包
            //     this.addReadedCount();

        } catch (IOException e) {
            resultCode = "1002";
            DateHelper.screenPrintForEx("IO 异常:" + e.getMessage());
        } catch (MessageException e) {
            DateHelper.screenPrintForEx("接收的数据处理时有错,错误代码为:" + e.getMessage());
        } finally {
            readerResult.add(resultCode);
            readerResult.add(data);
            readerResult.add(new Integer(serialNo));
            if (fromClient == 100) {
                DateHelper.screenPrint("读消息包正常");
            }
            return readerResult;
        }
        //   return readerResult;
    }
    
    private static void readOneByte() throws IOException, MessageException {
        if (!stopReader) {
            fromClient = in.read();
        } else {
            resultCode = "1105";
            throw new MessageException(resultCode);
        }
    }
    
    private static void readBytes(byte[] buff, int off, int len) throws IOException, MessageException {
        int readTotal = 0;
        if (!stopReader) {
            readTotal = readTotal = in.read(buff, off, len);
            if (readTotal != len) {
                resultCode = "1201";
                throw new MessageException(resultCode);
            }

        } else {
            resultCode = "1105";
            throw new MessageException(resultCode);
        }
    }
    
        //初始设置SOCKET
    private static void conServer() throws Exception{
        String ip= IP;
        int port = PORT;
        socket = new Socket(ip, port);
        out = socket.getOutputStream();
        in = socket.getInputStream();
    }
    
    private static void disConServer(){
        try {
            in.close();
            out.close();
            socket.close();
            in = null;
            out = null;
            socket = null;
        } catch (IOException ex) {
            Logger.getLogger(CommuTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        //主函数
    public static void main(String[] args) throws Exception{
    
        //连接
        conServer();
        
        //读数据
        readData();
        Thread.sleep(1000);
        
        //循环
        int seq = 0;
        //while(true){     
            send(seq++);//发送数据
            Thread.sleep(1000);
            readData();//读
            Thread.sleep(5000);
        //}
        
        //断开
        disConServer();
        //保持屏幕
        while(true){
            ;
        }
    }
}
