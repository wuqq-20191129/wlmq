/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.kmsfront.struct.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author lenovo
 */
public class CommuClientTest {

    public static void main(String[] args) throws UnknownHostException, IOException{
    
        Socket client = new Socket("127.0.0.1", 8899);
        OutputStream os = client.getOutputStream();
        InputStream is = client.getInputStream();
        
        //sendMsg(os);
        sendMsgByDate(os);
        readMsg(is);
        
        os.close();
        is.close();
        client.close();
    }
    
    public static void sendMsg(OutputStream os) throws IOException{
        
        byte[] rpt = new byte[100];
        rpt[0] = (byte)'1';//报文类型
        /*
        byte type = 101;//按卡号查询
        String sign = "1";
        String cardNo = "9289000021656002272";
        String value = sign + "|" + cardNo;
        */
        byte type = 0;//按账号查询
        String sign = "1";
        String accountNo = "89500320001311402";
        String value = sign + "|" + accountNo;
        
        int len = value.getBytes().length;
        
        int totalLen = 1 + 2 + len;
        rpt[1] = (byte) (0x000000ff & (totalLen));//报文总长度
        rpt[2] = (byte) (0x000000ff & (totalLen >>> 8));
        
        rpt[3] = type;//条件1类型
        rpt[4] = (byte) (0x000000ff & (len));//条件1长度
        rpt[5] = (byte) (0x000000ff & (len >>> 8));
        System.arraycopy(value.getBytes(), 0, rpt, 6, len);//条件1值
        
        os.write(rpt);//发送报文
    }
    
    public static void sendMsgByDate(OutputStream os) throws IOException{
        
        byte[] rpt = new byte[100];
        rpt[0] = (byte)'1';//报文类型
        /*
        byte type = 101;//按卡号查询
        String sign = "1";
        String cardNo = "9289000021656002272";
        String value = sign + "|" + cardNo;
        */
        byte type1 = 0;//按账号查询
        String sign1 = "1";
        String accountNo = "89500320001311402";
        String value1 = sign1 + "|" + accountNo;
        int len1 = value1.getBytes().length;
        
        byte type2 = 9;//按账号查询
        String sign2 = "3";
        String startDate = "2013-02-15";
        String value2 = sign2 + "|" + startDate;
        int len2 = value2.getBytes().length;
        
        byte type3 = 9;//按账号查询
        String sign3 = "4";
        String endDate = "2013-02-26";
        String value3 = sign3 + "|" + endDate;
        int len3 = value3.getBytes().length;
        
        int totalLen = 1 + 2 + len1 + 1 + 2 + len2 + 1 + 2 + len3;
        
        rpt[1] = (byte) (0x000000ff & (totalLen));//报文总长度
        rpt[2] = (byte) (0x000000ff & (totalLen >>> 8));
        
        rpt[3] = type1;//条件1类型
        rpt[4] = (byte) (0x000000ff & (len1));//条件1长度
        rpt[5] = (byte) (0x000000ff & (len1 >>> 8));
        System.arraycopy(value1.getBytes(), 0, rpt, 6, len1);//条件1值
        
        rpt[6+len1] = type2;//条件2类型
        rpt[6+len1+1] = (byte) (0x000000ff & (len2));//条件2长度
        rpt[6+len1+2] = (byte) (0x000000ff & (len2 >>> 8));
        System.arraycopy(value2.getBytes(), 0, rpt, 6+len1+3, len2);//条件2值
        
        rpt[6+len1+3+len2] = type3;//条件3类型
        rpt[6+len1+3+len2+1] = (byte) (0x000000ff & (len3));//条件3长度
        rpt[6+len1+3+len2+2] = (byte) (0x000000ff & (len3 >>> 8));
        System.arraycopy(value3.getBytes(), 0, rpt, 6+len1+3+len2+3, len3);//条件3值
        
        os.write(rpt);//发送报文
    }
    
    public static void readMsg(InputStream is) throws IOException{
        
        char rptType = (char) is.read();
        System.out.println("报文类型:"+rptType);
        byte rptLen1 = (byte) is.read();
        byte rptLen2 = (byte) is.read();
        int rptLen = (rptLen1 & 0xff) | ((rptLen2 & 0xff)<<8);
        System.out.println("报文长度:"+rptLen);
        for(int i=0;i<rptLen;){
            byte type = (byte) is.read();
            byte len1 = (byte) is.read();
            byte len2 = (byte) is.read();
            int len = (len1 & 0xff) | ((len2 & 0xff)<<8);
            System.out.print("类型："+type+",长度："+len);
            byte[] value = new byte[len];
            is.read(value);
            String data = new String(value);
            System.out.println(",数据："+data);
            i = i + 3 + len;
        }
    }

}
