/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.rwcommu.test;

import com.goldsign.rwcommu.connection.RWSerialConnection;
import com.goldsign.rwcommu.exception.SerialException;
import com.goldsign.rwcommu.util.Converter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * @author lenovo
 */
public class TestSerial {
    
    private static RWSerialConnection serialConnection = null;
    
    static{
        serialConnection = new RWSerialConnection("COM4");
    }

    public static void main(String[] args) throws SerialException, IOException, InterruptedException{

        serialConnection.open();
        
        commonInitializeDevice();
         commonGetVersion();
        //Thread.sleep(3000);
        esAnalyze();
        //esTicketInit();
        //evaluate();
        //esRecode();
        //destroy();
        //esSign();
        //esAnalyze();
        
        serialConnection.close();     

    }
    
    public static void commonInitializeDevice() throws SerialException, IOException{

        byte command = (byte)0x00;
        //站点：0103，设备：0003
        byte[] bytes = new byte[]{(byte)0x01,(byte)0x03,(byte)0x09, (byte)0x00, (byte)0x03};
        byte[] bytesRet = serialConnection.callSerial(command, bytes);
        
        //00 00 00 00 00 00 00 00 00
        System.out.println("环境初始化：");
        System.out.println(Converter.bytesToHexString(bytesRet));
        
    }
    
    public static void commonGetVersion() throws SerialException, IOException{
      
        byte command = (byte)0x01;
        byte[] bytesRet = serialConnection.callSerial(command, null);
        
        //00 00 00 00 00 13 00 00 06 20 13 07 16 01 02 20 13 07 06 01 01 20 13 07 06 01 00 00
        System.out.println(Converter.bytesToHexString(bytesRet));
        
    }
    
    //51
    public static void esAnalyze() throws IOException, SerialException{
        
        //byte command = (byte)0x1F;//(byte)0x3C;
        byte command = (byte)0x33;
        //Config.DegradeMode, Bom.Bom_Area
        //byte[] bytes = new byte[]{(byte)0x00, (byte) 0x02};
        //byte[] bytesRet = serialConnection.callSerial(command, bytes);
        byte[] bytesRet = serialConnection.callSerial(command, null);
        //0C 00 00 00 00 E5 00 31 30 31 38 36 38 42 30 30 30 30 30 30 30 20 20 20 20 20 20 30 30 30 30 39 30 30 30 30 30 30 30 31 30 30 30 30 30 30 34 02 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 20 13 04 15 20 19 12 31 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 FF 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 34 31 30 30 35 33 32 30 00 00 00 00 00 00 00 00 00 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
        System.out.println("票卡分析：");
        System.out.println(Converter.bytesToHexString(bytesRet));
        
        String bIssueStatus = getByteString(bytesRet, 7, 1);
        System.out.println("bIssueStatus:"+bIssueStatus);
        String bStatus = getByteString(bytesRet, 8, 1);
        System.out.println("bStatus:"+bStatus);
        String cTicketType = getByteCharString(bytesRet, 9, 4);
        System.out.println("cTicketType:"+cTicketType);
        String cLogicalID = getByteCharString(bytesRet, 13, 20);
        System.out.println("cLogicalID:"+cLogicalID);
        String cPhysicalID = getByteCharString(bytesRet, 33, 20);
        System.out.println("cPhysicalID:"+cPhysicalID);
        String bCharacter = getByteString(bytesRet, 53, 1);
        System.out.println("bCharacter:"+bCharacter);
        String cIssueDate = Converter.bytesToAsciiStringNoSpace(getByteArr(bytesRet, 54, 14));
        System.out.println("cIssueDate:"+cIssueDate);
        String cExpire = Converter.bytesToAsciiStringNoSpace(getByteArr(bytesRet, 68, 8));
        System.out.println("cExpire:"+cExpire);
        String RFU = getByteCharString(bytesRet, 76, 16);
        System.out.println("RFU:"+RFU);
        long lBalance = Converter.byteToLong(getByteArr(bytesRet, 92, 4));
        System.out.println("lBalance:"+lBalance);
        long lDeposite = Converter.byteToLong(getByteArr(bytesRet, 96, 4));
        System.out.println("lDeposite:"+lDeposite);
        String cLine = getByteCharString(bytesRet, 100, 2);
        System.out.println("cLine:"+cLine);
        String cStationNo = getByteCharString(bytesRet, 102, 2);
        System.out.println("cStationNo:"+cStationNo);
        String cDateStart = Converter.bytesToAsciiStringNoSpace(getByteArr(bytesRet, 104, 8));
        System.out.println("cDateStart:"+cDateStart);
        String cDateEnd = Converter.bytesToAsciiStringNoSpace(getByteArr(bytesRet, 112, 8));
        System.out.println("cDateEnd:"+cDateEnd);
        String dtDaliyActive = getByteString(bytesRet, 120, 7);
        System.out.println("dtDaliyActive:"+dtDaliyActive);
        String bEffectDay = getByteString(bytesRet, 127, 1);
        System.out.println("bEffectDay:"+bEffectDay);
        String cLimitEntryLine = getByteCharString(bytesRet, 128, 2);
        System.out.println("cLimitEntryLine:"+cLimitEntryLine);
        String cLimitEntryStation = getByteCharString(bytesRet, 130, 2);
        System.out.println("cLimitEntryStation:"+cLimitEntryStation);
        String cLimitExitLine = getByteCharString(bytesRet, 132, 2);
        System.out.println("cLimitExitLine:"+cLimitExitLine);
        String cLimitExitStation = getByteCharString(bytesRet, 134, 2);
        System.out.println("cLimitExitStation:"+cLimitExitStation);
        String cLimitMode = getByteCharString(bytesRet, 136, 3);
        System.out.println("cLimitMode:"+cLimitMode);
        
        String certificate_iscompany = getByteString(bytesRet, 139, 1);
        System.out.println("certificate_iscompany:" + certificate_iscompany);
        String certificate_ismetro = getByteString(bytesRet, 140, 1);
        System.out.println("certificate_ismetro:" + certificate_ismetro);
        String certificate_name = new String(getByteArr(bytesRet, 141, 128),"UTF-8");
        System.out.println("certificate_name:" + certificate_name);
        String certificate_code =  new String(getByteArr(bytesRet, 269, 32),"GBK");
        System.out.println("certificate_code:" + certificate_code);
        String certificate_type = getByteString(bytesRet, 301, 1);
        System.out.println("certificate_type:" + certificate_type);
        String certificate_sex = getByteString(bytesRet, 302, 1);
        System.out.println("certificate_sex:" + certificate_sex);
    }
    
    public static byte[] getByteArr(byte[] b, int start, int len){
    
        return Arrays.copyOfRange(b, start, start+len);
    }
    
    public static String byteToHex(byte[] b) {
        String result = "";
        String tmp = "";

        for (int n = 0; n < b.length; n++) {
            tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (tmp.length() == 1) {
                result = result + "0" + tmp;
            } else {
                result = result + tmp;
            }
            if (n < b.length - 1) {
                result = result + ",";
            }
        }
        return result.toUpperCase();
    }
        
    public static String getByteString(byte[] data, int start, int len){
    
        String str = "";

        for(int i=start; i<start+len; i++){
            int d = 0;
            if (data[i] < 0) {
                d = 256 + data[i];
            } else {
                d = data[i];
            }
            str += d;
        }
        
        return str;
    }
    
    public static String getByteCharString(byte[] data, int start, int len){
    
        String str = "";

        for(int i=start; i<start+len; i++){
            
            str += (char)data[i];
        }
        
        return str;
    }
    
    //52
    public static void esTicketInit() throws IOException, SerialException{
        
        ByteBuffer bf = ByteBuffer.allocate(69+16*19);
        
        String orderNo = "12345678901211";
        bf.put(orderNo.getBytes());
        
        String reqNo = "1234567890";
        bf.put(reqNo.getBytes());
        
        byte[] cardType = new byte[]{0x02, 0x01};
        bf.put(cardType);
        
        byte[] logicNo =  new byte[]{0x01, 0x23, 0x45, 0x67, 0x11, 0x01, 0x23, 0x45};
        bf.put(logicNo);
        
        byte deposit = 0x12;
        bf.put(deposit);
        
        byte[] value = new byte[]{0x00, 0x00,  0x01, 0x02};
        bf.put(value);
        
        byte[] topValue = new byte[]{0x12, 0x13};
        bf.put(topValue);
        
        byte active = 0x01;
        bf.put(active);
        
        byte[] senderCode = new byte[]{0x53, 0x20};
        bf.put(senderCode);
        
        byte[] cityCode = new byte[]{0x41, 0x00};
        bf.put(cityCode);
        
        byte[] busiCode = new byte[]{0x00, 0x00};
        bf.put(busiCode);
        
        byte testFlag = 0x01;
        bf.put(testFlag);
        
        byte[] issueDate = new byte[]{0x20, 0x13, 0x08, 0x01};
        bf.put(issueDate);
        
        byte[] cardVersion = new byte[]{0x08, 0x05};
        bf.put(cardVersion);
        
        byte[] cardStartDate = new byte[]{0x20, 0x13, 0x09, 0x26};
        bf.put(cardStartDate);
        
        byte[] cardEndDate = new byte[]{0x20, 0x13, 0x09, 0x26};
        bf.put(cardEndDate);
        
        byte appVersion = 0x01;
        bf.put(appVersion);
        
        byte exitEntryMode = 0x01;
        bf.put(exitEntryMode);
        
        byte[] entryLineStation = new byte[]{0x01, 0x02};
        bf.put(entryLineStation);
        
        byte[] exitLineStation = new byte[]{0x02, 0x01};
        bf.put(exitLineStation);
        
        byte[] pinCode = new byte[16*17];
        bf.put(pinCode);
        
        byte command = (byte)0x34;
        
        byte[] bytes = bf.array();
        System.out.println("票卡初始化：");
        System.out.println("输入：\n"+Converter.bytesToHexString(bytes));
        byte[] bytesRet = serialConnection.callSerial(command, bytes);
        System.out.println("返回：\n"+Converter.bytesToHexString(bytesRet));
        //System.out.println(Converter.bytesToHexString(bytesRet));
        
    }
    
    //53
    public static void evaluate() throws IOException, SerialException{
        
        ByteBuffer bf = ByteBuffer.allocate(46+16*10);
        
        String orderNo = "12345678901234";
        bf.put(orderNo.getBytes());
        
        String reqNo = "1234567890";
        bf.put(reqNo.getBytes());
        
        byte[] cardType = new byte[]{0x01, 0x02};
        bf.put(cardType);
        
        byte[] logicNo =  new byte[]{0x01, 0x23, 0x45, 0x67, 0x11, 0x01, 0x23, 0x45};
        bf.put(logicNo);
        
        byte deposit = 0x12;
        bf.put(deposit);
        
        byte[] value = new byte[]{0x20, 0x20,  0x30, 0x40};
        bf.put(value);
        
        byte[] topValue = new byte[]{0x12, 0x13};
        bf.put(topValue);
        
        byte exitEntryMode = 0x01;
        bf.put(exitEntryMode);
        
        byte[] entryLineStation = new byte[]{0x01, 0x02};
        bf.put(entryLineStation);
        
        byte[] exitLineStation = new byte[]{0x02, 0x01};
        bf.put(exitLineStation);
        
        byte[] pinCode = new byte[16*10];
        bf.put(pinCode);
        
        byte command = (byte)0x35;
        
        byte[] bytes = bf.array();
        System.out.println("票卡预赋值：");
        System.out.println("输入：\n"+Converter.bytesToHexString(bytes));
        byte[] bytesRet = serialConnection.callSerial(command, bytes);
        System.out.println("返回：\n"+Converter.bytesToHexString(bytesRet));
        //System.out.println(Converter.bytesToHexString(bytesRet));
    }
    
    //54
    public static void destroy() throws IOException, SerialException{
        
        ByteBuffer bf = ByteBuffer.allocate(39+16*10);
        
        String orderNo = "12345678901234";
        bf.put(orderNo.getBytes());
        
        String reqNo = "1234567890";
        bf.put(reqNo.getBytes());
        
        byte[] cardType = new byte[]{0x01, 0x02};
        bf.put(cardType);
        
        byte[] logicNo =  new byte[]{0x01, 0x23, 0x45, 0x67, 0x11, 0x01, 0x23, 0x45};
        bf.put(logicNo);
        
        byte deposit = 0x12;
        bf.put(deposit);
        
        byte[] value = new byte[]{0x20, 0x20,  0x30, 0x40};
        bf.put(value);

        byte[] pinCode = new byte[16*10];
        bf.put(pinCode);
        
        byte command = (byte)0x36;
        
        byte[] bytes = bf.array();
        System.out.println("票卡注销：");
        System.out.println("输入：\n"+Converter.bytesToHexString(bytes));
        byte[] bytesRet = serialConnection.callSerial(command, bytes);
        System.out.println("返回：\n"+Converter.bytesToHexString(bytesRet));
        //System.out.println(Converter.bytesToHexString(bytesRet));
    }
    
    //55
    public static void esRecode() throws IOException, SerialException{
        
        ByteBuffer bf = ByteBuffer.allocate(69+16*17);
        
        String orderNo = "12345678901234";
        bf.put(orderNo.getBytes());
        
        String reqNo = "0987654321";
        bf.put(reqNo.getBytes());
        
        byte[] cardType = new byte[]{0x01, 0x02};
        bf.put(cardType);
        
        byte[] logicNo =  new byte[]{0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11};
        bf.put(logicNo);
        
        byte deposit = 0x12;
        bf.put(deposit);
        
        byte[] value = new byte[]{0x10, 0x20,  0x30, 0x40};
        bf.put(value);
        
        byte[] topValue = new byte[]{0x12, 0x13};
        bf.put(topValue);
        
        byte active = 0x00;
        bf.put(active);
        
        byte[] senderCode = new byte[]{0x53, 0x20};
        bf.put(senderCode);
        
        byte[] cityCode = new byte[]{0x41, 0x00};
        bf.put(cityCode);
        
        byte[] busiCode = new byte[]{0x00, 0x00};
        bf.put(busiCode);
        
        byte testFlag = 0x00;
        bf.put(testFlag);
        
        byte[] issueDate = new byte[]{0x20, 0x13, 0x08, 0x01};
        bf.put(issueDate);
        
        byte[] cardVersion = new byte[]{0x08, 0x05};
        bf.put(cardVersion);
        
        byte[] cardStartDate = new byte[]{0x20, 0x13, 0x09, 0x01};
        bf.put(cardStartDate);
        
        byte[] cardEndDate = new byte[]{0x20, 0x14, 0x10, 0x01};
        bf.put(cardEndDate);
        
        byte appVersion = 0x01;
        bf.put(appVersion);
        
        byte exitEntryMode = 0x01;
        bf.put(exitEntryMode);
        
        byte[] entryLineStation = new byte[]{0x01, 0x02};
        bf.put(entryLineStation);
        
        byte[] exitLineStation = new byte[]{0x02, 0x01};
        bf.put(exitLineStation);
        
        byte[] pinCode = new byte[16*17];
        bf.put(pinCode);
        
        byte command = (byte)0x37;
        
        byte[] bytes = bf.array();
        System.out.println("票卡重编码：");
        System.out.println("输入：\n"+Converter.bytesToHexString(bytes));
        byte[] bytesRet = serialConnection.callSerial(command, bytes);
        System.out.println("返回：\n"+Converter.bytesToHexString(bytesRet));
        //System.out.println(Converter.bytesToHexString(bytesRet));    
    }
    
    //56
    public static void esSign() throws IOException, SerialException{
        
        ByteBuffer bf = ByteBuffer.allocate(16+56);
        
        byte[] pinCode = Converter.hexStringToBytes("0413DB69F2C6C93938D3EFF831871075");
        bf.put(pinCode);
        
        //持卡类型标识
        byte manType = (byte)0x00;
        bf.put(manType);
        
        //本单位职工标识
        byte unit = (byte)0x01;
        bf.put(unit);
        
        //持卡人姓名
        String manName = "张三五ab1234567890  ";
        bf.put(manName.getBytes("GBK"));
        
        //持卡人证件号码
        String manId = "公司MH1234567890123456789012345 ";
        bf.put(manId.getBytes("GBK"));
        
        //持卡人证件类型
        byte idType = (byte)0x00;
        bf.put(idType);
        
        //持卡人性别
        byte sex = (byte)0x01;
        bf.put(sex);
        
        byte command = (byte)0x38;
        
        byte[] bytes = bf.array();
        System.out.println("记名卡制作：");
        System.out.println("输入：\n"+Converter.bytesToHexString(bytes));
        byte[] bytesRet = serialConnection.callSerial(command, bytes);
        System.out.println("返回：\n"+Converter.bytesToHexString(bytesRet));
        //System.out.println(Converter.bytesToHexString(bytesRet));
        
    }
}
