/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.rwcommu.test;

import com.goldsign.csfrm.util.CharUtil;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.kmscommu.dll.library.CardKeyGetDll;
import com.goldsign.kmscommu.dll.structure.CardKeyInInf;
import com.goldsign.kmscommu.dll.structure.CardKeyOutInf;
import com.goldsign.kmscommu.jni.CardKeyGetJni;
import com.goldsign.kmscommu.vo.CardKeyResult;
import com.goldsign.kmscommu.vo.TokenKeyResult;
import com.goldsign.rwcommu.connection.RWSerialConnection;
import com.goldsign.rwcommu.exception.SerialException;
import static com.goldsign.rwcommu.test.TestsSerials.getByteArr;
import static com.goldsign.rwcommu.test.TestsSerials.getByteCharString;
import static com.goldsign.rwcommu.test.TestsSerials.getByteString;
import com.goldsign.rwcommu.util.Converter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * @author mh
 */
public class TestsSerial {
       
    private static RWSerialConnection serialConnection = null;
    private static String logicalId1 = null;
    private static String name1 = null;
    private static String type1 = null;
    private static String employeeNo1 = null;
    static{
        serialConnection = new RWSerialConnection("COM2");
    }
 
//    public static void main(String[] args) throws SerialException, IOException, InterruptedException{
//        
//        serialConnection.open();
//        
//        commonInitializeDevice();
//         commonGetVersion();
//        //Thread.sleep(3000);
//        //esTicketInit(); 
//        esAnalyze();
//        //clear();
//        //destroy1();
//        //recode();
//        initEvaluate();
//        //inadvEvaluate();
//        signCard();        
//        //esAnalyze();
//        //evaluate();
//        //esRecode();
//        //destroy();
//        //esSign();
//       esAnalyze();
//        
//        serialConnection.close();     
//
//    }
    public static void makeCard(String logicalId,String name,String type,String employeeNo) throws SerialException, IOException, InterruptedException{
        
         logicalId1 = logicalId;
         name1 = name;
         type1 = type;
         employeeNo1 = employeeNo;
        serialConnection.open();
        
        commonInitializeDevice();
         commonGetVersion();
        //Thread.sleep(3000);
        //esTicketInit();
       // esAnalyze();
       esAnalyze();
        //clear();
        //esAnalyze();
        //destroy1();
        //recode();
       initEvaluate();
        //inadvEvaluate();
//        if(!type.equals("0100")&&!type.equals("0200")){
           // signCard();        
//        }
        esAnalyze();
        //evaluate();
        //esRecode();
        //destroy();
        //esSign();
      // esAnalyzes();
        
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
        cLogicalID = cLogicalID.trim();
        String bCharacter = getByteString(bytesRet, 53, 1);
        System.out.println("bCharacter:"+bCharacter);
        String cIssueDate = Converter.bytesToAsciiStringNoSpace(getByteArr(bytesRet, 54, 14));
        System.out.println("cIssueDate:"+cIssueDate);
        String cStartExpire = Converter.bytesToAsciiStringNoSpace(getByteArr(bytesRet, 68, 8));
        System.out.println("cStartExpire:"+cStartExpire);
        String cEndExpire = Converter.bytesToAsciiStringNoSpace(getByteArr(bytesRet, 76, 8));
        System.out.println("cEndExpire:"+cEndExpire);
        String RFU = getByteCharString(bytesRet, 84, 16);
        System.out.println("RFU:"+RFU);
        long lBalance = Converter.byteToLong(getByteArr(bytesRet, 100, 4));
        System.out.println("lBalance:"+lBalance);
        long lDeposite = Converter.byteToLong(getByteArr(bytesRet, 104, 4));
        System.out.println("lDeposite:"+lDeposite);
        String cLine = getByteCharString(bytesRet, 108, 2);
        System.out.println("cLine:"+cLine);
        String cStationNo = getByteCharString(bytesRet, 110, 2);
        System.out.println("cStationNo:"+cStationNo);
        String cDateStart = Converter.bytesToAsciiStringNoSpace(getByteArr(bytesRet, 112, 8));
        System.out.println("cDateStart:"+cDateStart);
        String cDateEnd = Converter.bytesToAsciiStringNoSpace(getByteArr(bytesRet, 120, 8));
        System.out.println("cDateEnd:"+cDateEnd);
        String dtDaliyActive = getByteString(bytesRet, 128, 7);
        System.out.println("dtDaliyActive:"+dtDaliyActive);
        String bEffectDay = CharUtil.byteToIntStr(CharUtil.getByteArr(bytesRet, 135, 2));
        System.out.println("bEffectDay:"+bEffectDay);
        String cLimitEntryLine = getByteCharString(bytesRet, 137, 2);
        System.out.println("cLimitEntryLine:"+cLimitEntryLine);
        String cLimitEntryStation = getByteCharString(bytesRet, 139, 2);
        System.out.println("cLimitEntryStation:"+cLimitEntryStation);
        String cLimitExitLine = getByteCharString(bytesRet, 141, 2);
        System.out.println("cLimitExitLine:"+cLimitExitLine);
        String cLimitExitStation = getByteCharString(bytesRet, 143, 2);
        System.out.println("cLimitExitStation:"+cLimitExitStation);
        String cLimitMode = getByteCharString(bytesRet, 145, 3);
        System.out.println("cLimitMode:"+cLimitMode);
        
        String certificate_iscompany = getByteString(bytesRet, 148, 1);
        System.out.println("certificate_iscompany:" + certificate_iscompany);
        String certificate_ismetro = getByteString(bytesRet, 149, 1);
        System.out.println("certificate_ismetro:" + certificate_ismetro);
        String certificate_name = new String(getByteArr(bytesRet, 150, 128),"UTF-8");
        System.out.println("certificate_name:" + certificate_name);
        String certificate_code =  new String(getByteArr(bytesRet, 278, 32),"GBK");
        System.out.println("certificate_code:" + certificate_code);
        String certificate_type = getByteString(bytesRet, 310, 1);
        System.out.println("certificate_type:" + certificate_type);
        String certificate_sex = getByteString(bytesRet, 311, 1);
        System.out.println("certificate_sex:" + certificate_sex);
        String trade_count = getByteString(bytesRet, 312, 2);
        System.out.println("trade_count:" + trade_count);
        String cardfactory = getByteString(bytesRet, 314, 4);
        System.out.println("cardfactory:" + cardfactory);
        String istest = getByteString(bytesRet, 318, 1);
        System.out.println("istest:" + istest);
    }
    public static void esAnalyzes() throws IOException, SerialException{
        
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
        cLogicalID = cLogicalID.trim();
       // if(cLogicalID != null&&!"".equals(cLogicalID)){
            File file2 = getUnNoticeFile("kahao",true);
            PrintWriter pw = null;
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file2,true),"utf-8"));

//            pw.println("cLogicalID:"+cLogicalID);
//            pw.flush();
            pw.println(cPhysicalID);
            //pw.println("cPhysicalID:"+cPhysicalID);
            pw.flush();
            if(null!=pw){
                pw.close();    
            }
        //}
        String bCharacter = getByteString(bytesRet, 53, 1);
        System.out.println("bCharacter:"+bCharacter);
        String cIssueDate = Converter.bytesToAsciiStringNoSpace(getByteArr(bytesRet, 54, 14));
        System.out.println("cIssueDate:"+cIssueDate);
       String cStartExpire = Converter.bytesToAsciiStringNoSpace(getByteArr(bytesRet, 68, 8));
        System.out.println("cStartExpire:"+cStartExpire);
        String cEndExpire = Converter.bytesToAsciiStringNoSpace(getByteArr(bytesRet, 76, 8));
        System.out.println("cEndExpire:"+cEndExpire);
        String RFU = getByteCharString(bytesRet, 84, 16);
        System.out.println("RFU:"+RFU);
        long lBalance = Converter.byteToLong(getByteArr(bytesRet, 100, 4));
        System.out.println("lBalance:"+lBalance);
        long lDeposite = Converter.byteToLong(getByteArr(bytesRet, 104, 4));
        System.out.println("lDeposite:"+lDeposite);
        String cLine = getByteCharString(bytesRet, 108, 2);
        System.out.println("cLine:"+cLine);
        String cStationNo = getByteCharString(bytesRet, 110, 2);
        System.out.println("cStationNo:"+cStationNo);
        String cDateStart = Converter.bytesToAsciiStringNoSpace(getByteArr(bytesRet, 112, 8));
        System.out.println("cDateStart:"+cDateStart);
        String cDateEnd = Converter.bytesToAsciiStringNoSpace(getByteArr(bytesRet, 120, 8));
        System.out.println("cDateEnd:"+cDateEnd);
        String dtDaliyActive = getByteString(bytesRet, 128, 7);
        System.out.println("dtDaliyActive:"+dtDaliyActive);
        String bEffectDay = CharUtil.byteToIntStr(CharUtil.getByteArr(bytesRet, 135, 2));
        System.out.println("bEffectDay:"+bEffectDay);
        String cLimitEntryLine = getByteCharString(bytesRet, 137, 2);
        System.out.println("cLimitEntryLine:"+cLimitEntryLine);
        String cLimitEntryStation = getByteCharString(bytesRet, 139, 2);
        System.out.println("cLimitEntryStation:"+cLimitEntryStation);
        String cLimitExitLine = getByteCharString(bytesRet, 141, 2);
        System.out.println("cLimitExitLine:"+cLimitExitLine);
        String cLimitExitStation = getByteCharString(bytesRet, 143, 2);
        System.out.println("cLimitExitStation:"+cLimitExitStation);
        String cLimitMode = getByteCharString(bytesRet, 145, 3);
        System.out.println("cLimitMode:"+cLimitMode);
        
        String certificate_iscompany = getByteString(bytesRet, 148, 1);
        System.out.println("certificate_iscompany:" + certificate_iscompany);
        String certificate_ismetro = getByteString(bytesRet, 149, 1);
        System.out.println("certificate_ismetro:" + certificate_ismetro);
        String certificate_name = new String(getByteArr(bytesRet, 150, 128),"UTF-8");
        System.out.println("certificate_name:" + certificate_name);
        String certificate_code =  new String(getByteArr(bytesRet, 278, 32),"GBK");
        System.out.println("certificate_code:" + certificate_code);
        String certificate_type = getByteString(bytesRet, 310, 1);
        System.out.println("certificate_type:" + certificate_type);
        String certificate_sex = getByteString(bytesRet, 311, 1);
        System.out.println("certificate_sex:" + certificate_sex);
        String trade_count = getByteString(bytesRet, 312, 2);
        System.out.println("trade_count:" + trade_count);
        String cardfactory = getByteString(bytesRet, 314, 4);
        System.out.println("cardfactory:" + cardfactory);
        String istest = getByteString(bytesRet, 318, 1);
        System.out.println("istest:" + istest);
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
    
      /**
     * 初始化
     * 
     * @param pInput
     * @param pOutput
     * @return 
     */
    private static void initEvaluate() {
    
       
        byte[] byteRets = null;

        try {
            
            ByteBuffer bytes = ByteBuffer.allocate(78+16*19);

            String orderNo = "order123456789";
            bytes.put(CharUtil.strToLenByteArr(orderNo, 14));
            String reqNo = "req1233211";
            bytes.put(CharUtil.strToLenByteArr(reqNo, 10));
            String cardtype = type1;
            byte[] ticketType = CharUtil.hexStrToLenByteArr(cardtype, 2);
            bytes.put(ticketType);
            //String  cardNo = "8300000300000005";
            String  cardNo = logicalId1;
            byte[] logicNo = CharUtil.hexStrToLenByteArr(cardNo, 8);
            bytes.put(logicNo);
            byte deposite = CharUtil.strToByte("12");
            bytes.put(deposite);
            int balance;
            if(type1.equals("0500") ){
                 balance = CharUtil.strToInt("1");
            }else{
                 balance = CharUtil.strToInt("10000");
            }
            bytes.put(CharUtil.to4Byte(balance));
            byte[] topValue = CharUtil.to2Byte(CharUtil.strToInt("3211"));
            bytes.put(topValue);
            byte active = CharUtil.strToByte("01");
            bytes.put(active);
            byte[] senderCode = CharUtil.bcdStrToLenByteArr("0991",2);
            bytes.put(senderCode);
            byte[] cityCode = CharUtil.bcdStrToLenByteArr("0000",2);
            bytes.put(cityCode);
            byte[] busiCode = CharUtil.bcdStrToLenByteArr("0003",2);
            bytes.put(busiCode);
            byte testFlag = CharUtil.strToByte("1");
            bytes.put(testFlag);
            byte[] issueDate = CharUtil.bcdStrToLenByteArr("20180524",4);
            bytes.put(issueDate);
            byte[] cardVersion = CharUtil.bcdStrToLenByteArr("01",2);
            bytes.put(cardVersion);
            byte[] cardStartDate = CharUtil.bcdStrToLenByteArr("20180524",4);
            bytes.put(cardStartDate);
            byte[] cardEndDate = CharUtil.bcdStrToLenByteArr("20200902",4);
            bytes.put(cardEndDate);
            //-----逻辑日期
            byte[] logicDate = CharUtil.bcdStrToLenByteArr("20180524",7);
            bytes.put(logicDate);
            byte[] logicDays = CharUtil.to2Byte(CharUtil.strToInt("365"));
            bytes.put(logicDays);
            //-----
            byte appVersion = CharUtil.strToByte("01");
            bytes.put(appVersion);
            byte exitEntryMode = CharUtil.strToByte("01");
            bytes.put(exitEntryMode);
            byte[] entryLineStation = CharUtil.bcdStrToLenByteArr("0000",2);
            bytes.put(entryLineStation);
            byte[] exitLineStation =  CharUtil.bcdStrToLenByteArr("0000",2);
            bytes.put(exitLineStation);
            if(ticketType[0]==1){
                cardNo =cardNo.substring(8,16);
                byte command1 = (byte)0x33;
                byte[] bytesRet1 = serialConnection.callSerial(command1, null);
                String cPhysicalID = getByteCharString(bytesRet1, 33, 20).trim();
                cPhysicalID = cPhysicalID.substring(6, 14);
                System.out.println("cPhysicalID:"+cPhysicalID);
                TokenKeyResult cardKeyResult = (TokenKeyResult)keyms(cardNo,false).getObj();
                System.out.println("返回秘钥mac：\n"+CharUtil.getByteCharString(cardKeyResult.getMac(), 0, 8));
                System.out.println("返回秘钥key：\n"+CharUtil.getByteCharString(cardKeyResult.getKey(), 0, 12));           
                String str = CharUtil.getByteCharString(cardKeyResult.getMac(), 0, 8)+CharUtil.getByteCharString(cardKeyResult.getKey(), 0, 12);
                addKmsByByte1(bytes,str);
            }else {
                CardKeyResult cardKeyResult = (CardKeyResult)keyms(cardNo,true).getObj();
                System.out.println("返回秘钥：\n"+Converter.bytesToHexString(cardKeyResult.getMsg()));
                addKmsByByte(bytes,cardKeyResult.getMsg());
            }
            byte command = (byte)0x34;
        
            byte[] bf = bytes.array();
            System.out.println("票卡初始化：");
            System.out.println("输入：\n"+Converter.bytesToHexString(bf));
            byteRets = serialConnection.callSerial(command, bf);
            
            String retCode = CharUtil.byteToIntStr(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);
            
            if("00".equals(retCode)){
                System.out.println("卡初始化成功！");
            }else{
                System.out.println("卡初始化失败！错误码"+retCode+","+relCode);
            }
        } catch (Exception ex) {
            System.out.println("卡初始化异常："+ex.getMessage());
        }
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
    
     //加密机与读写器秘钥顺序不一致，故按实际情况排序
    private static void addKmsByByte(ByteBuffer bytes,byte[] bytess) throws Exception{
            bytes.put(CharUtil.hexStrToLenByteArr("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF",16));//MfCardTranKey
            System.out.println("MF卡片传输密钥:"+"FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 0, 32),16));//CardMainKey
            System.out.println("卡片主控秘钥:"+CharUtil.getByteCharString(bytess, 0, 32));
            if(CharUtil.getByteCharString(bytess, 0, 32).trim().equals("")){throw new Exception();}
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 32, 32),16));//CardMendKey
            System.out.println("卡片维护秘钥:"+CharUtil.getByteCharString(bytess, 32, 32));
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 64, 32),16));//OutAuthenMKey
            System.out.println("外部认证秘钥:"+CharUtil.getByteCharString(bytess, 64, 32));
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 96, 32),16));//AppTranKey
            System.out.println("应用传输秘钥:"+CharUtil.getByteCharString(bytess, 96, 32));
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 96, 32),16));//AppMainKey
            System.out.println("应用主控秘钥:"+CharUtil.getByteCharString(bytess, 96, 32));
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 128, 32),16));//AppMendKey
            System.out.println("应用维护秘钥:"+CharUtil.getByteCharString(bytess, 128, 32));
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 384, 32),16));//PinUnlockKey
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 416, 32),16));//PinResetKey
            
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 160, 32),16));//ConsumeKey
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 192, 32),16));//TransferInKey
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 224, 32),16));//TranAuthenTacKey
            
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 480, 32),16));//TransferOutKey
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 448, 32),16));//ModifyOverdrawKey
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 512, 32),16));//OutAuthenDKey
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 320, 32),16));//AppLockKey
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 352, 32),16));//AppUnlockKey
            
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 256, 32),16));//AppMendKey01
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 288, 32),16));//AppMendKey02
            
            
            
            
            
            
    }
    
    
      //加密机与读写器秘钥顺序不一致，故按实际情况排序
    private static void addKmsByByte1(ByteBuffer bytes,String str) {
            bytes.put(CharUtil.hexStrToLenByteArr("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF",16));//MfCardTranKey
            System.out.println("MF卡片传输密钥:"+"FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
            bytes.put(CharUtil.hexStrToLenByteArr(str+"000000000000",16));//CardMainKey
            System.out.println("卡片主控秘钥:"+str+"000000000000");
            bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//CardMendKey
            //System.out.println("卡片维护秘钥:"+CharUtil.getByteCharString(bytess, 32, 32));
            bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//OutAuthenMKey
            //System.out.println("外部认证秘钥:"+CharUtil.getByteCharString(bytess, 64, 32));
            bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//AppTranKey
            //System.out.println("应用传输秘钥:"+CharUtil.getByteCharString(bytess, 96, 32));
            bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//AppMainKey
            //System.out.println("应用主控秘钥:"+CharUtil.getByteCharString(bytess, 96, 32));
            bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//AppMendKey
            //System.out.println("应用维护秘钥:"+CharUtil.getByteCharString(bytess, 128, 32));
            bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//PinUnlockKey
            bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//PinResetKey
            
            bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//ConsumeKey
            bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//TransferInKey
            bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//TranAuthenTacKey
            
            bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//TransferOutKey
            bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//ModifyOverdrawKey
            bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//OutAuthenDKey
            bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//AppLockKey
            bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//AppUnlockKey
            
            bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//AppMendKey01
            bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//AppMendKey02
            
            
            
            
            
            
    }
    
    
    /**
     * 洗卡
     * 
     * @param kmsCardVo
     * @return 
     */
    public static CallResult clear() throws IOException {
    System.out.println("清洗：");
        CallResult callResult = new CallResult();
        try {  
         byte command = (byte)0x33;
        byte[] bytesRet1 = serialConnection.callSerial(command, null);
        String cLogicalID = getByteCharString(bytesRet1, 13, 20);
        cLogicalID = cLogicalID.trim();
            ByteBuffer bytes = ByteBuffer.allocate(78+16*19);
            byte[] orderByte = new byte[78];
            byte[] logicNo = CharUtil.hexStrToLenByteArr(cLogicalID, 8);
            //byte[] logicNo = "8300000300000001".getBytes();
            System.arraycopy(logicNo, 0, orderByte, 26, logicNo.length);
            bytes.put(orderByte);
            CardKeyResult cardKeyResult = (CardKeyResult)keyms(cLogicalID,true).getObj();
            //加入密钥
            addKmsByByte(bytes,cardKeyResult.getMsg());
            
             byte command1 = (byte)0x39;
        System.out.println("清洗1：");
            byte[] bytesRet = serialConnection.callSerial(command1, bytes.array());
        
            String retCode = CharUtil.byteToIntStr(bytesRet, 0, 2);
            String relCode = CharUtil.getByteString(bytesRet, 2, 1);
            
            if("00".equals(retCode)){
                callResult.setResult(true);
                callResult.resetMsg("清洗成功！");
            }else{
                callResult.resetMsg("清洗失败，错误码："+retCode+","+relCode+".");
            }
            callResult.setCode(retCode+","+relCode);
        } catch (Exception ex) {
            callResult.resetMsg("清洗异常："+ex.getMessage());
            ex.printStackTrace();
        }

        return callResult;
    }
    
    /*
    取储值票秘钥
    */
    private static CallResult keyms(String cardNo,boolean isPk) throws Exception{
        CallResult call = new CallResult();
        CardKeyResult cardKeyResult1 = null;
        CardKeyGetJni cardKeyGetJni = new CardKeyGetJni();
            String ipStr = "";
            String[] ipArr = "10.99.10.50".split("\\.");
            for(int i=0; i<4; i++){
                ipStr += CharUtil.intToHex(Integer.parseInt(ipArr[i]));
            }       
            byte[] bip = ipStr.getBytes();
            
            int port = 8;
            String portStr = Integer.toHexString(port);
            portStr = portStr.toUpperCase();
            int len = 4-portStr.length();
            for(int i=0; i<len; i++){
                portStr = "0" + portStr;
            }
            byte[] bport =  portStr.getBytes();
            byte[] bpin = "83000991".getBytes();
            byte[] bkeyver = "02".getBytes();
            cardKeyResult1 = cardKeyGetJni.author(bip, bport,bpin, bkeyver);
             String code = new String(cardKeyResult1.getCode());
            if("00".equals(code)){
          
            System.out.println("加密机认证成功");
            }else{
            System.out.println("加密机认证失败，错误码："+code+".");
            }
            //加入密钥
            if(isPk == true){
                byte[] cardNoArr = cardNo.getBytes();
                CardKeyResult cardKeyResult = null;
                cardKeyResult = cardKeyGetJni.getCardKey(cardNoArr);
                call.setObj(cardKeyResult);
                call.setResult(true);                
            }else{
                byte command1 = (byte)0x33;
                 byte[] bytesRet1 = serialConnection.callSerial(command1, null);
                String cPhysicalID = getByteCharString(bytesRet1, 33, 20).trim().substring(6, 14);
                byte[] phyNoArr = cPhysicalID.getBytes();
                byte[] logicNoArr = cardNo.getBytes();
                TokenKeyResult tokenKeyResult = null;
                tokenKeyResult = cardKeyGetJni.getTokenKey(phyNoArr, logicNoArr);
                call.setObj(tokenKeyResult);
                call.setResult(true); 
            }
            return call;
    }
    
    
      /*
    取单程票秘钥
    */
    private static TokenKeyResult keyms(String logicNo,String phyNo){
        CardKeyResult cardKeyResult1 = null;
        CardKeyGetJni cardKeyGetJni = new CardKeyGetJni();
            String ipStr = "";
            String[] ipArr = "10.99.10.51".split("\\.");
            for(int i=0; i<4; i++){
                ipStr += CharUtil.intToHex(Integer.parseInt(ipArr[i]));
            }       
            byte[] bip = ipStr.getBytes();
            
            int port = 8;
            String portStr = Integer.toHexString(port);
            portStr = portStr.toUpperCase();
            int len = 4-portStr.length();
            for(int i=0; i<len; i++){
                portStr = "0" + portStr;
            }
            byte[] bport =  portStr.getBytes();
            byte[] bpin = "83000991".getBytes();
            byte[] bkeyver = "00".getBytes();
            cardKeyResult1 = cardKeyGetJni.author(bip, bport,bpin, bkeyver);
             String code = new String(cardKeyResult1.getCode());
            if("00".equals(code)){
          
            System.out.println("加密机认证成功");
            }else{
            System.out.println("加密机认证失败，错误码："+code+".");
            }
            //加入密钥
            
            byte[] phyNoArr = phyNo.getBytes();
            byte[] logicNoArr = logicNo.getBytes();
            TokenKeyResult tokenKeyResult = null;
            tokenKeyResult = cardKeyGetJni.getTokenKey(phyNoArr, logicNoArr);
            return tokenKeyResult;
    }
    
    
    /*
    注销
    */
    
    private static void destroy1() 
       {
         System.out.println("注销：");
        CallResult callResult = new CallResult();
        byte[] byteRets = null;

        try {
            ByteBuffer bytes = ByteBuffer.allocate(39+16*5);
            
            String orderNo = "order123456789";
            bytes.put(CharUtil.strToLenByteArr(orderNo, 14));
            String reqNo = "req1233211";
            bytes.put(CharUtil.strToLenByteArr(reqNo, 10));
            byte[] ticketType = CharUtil.hexStrToLenByteArr(type1, 2);
            bytes.put(ticketType);
            byte command2 = (byte)0x33;
            byte[] bytesRet1 = serialConnection.callSerial(command2, null);
            String cLogicalID = getByteCharString(bytesRet1, 13, 20);
            String cardNo = cLogicalID.trim();
            byte[] logicNo = CharUtil.hexStrToLenByteArr(cardNo, 8);
            bytes.put(logicNo);
            byte deposite = CharUtil.strToByte("12");
            bytes.put(deposite);
            int balance = CharUtil.strToInt("123");
            bytes.putInt(balance); 
            if(ticketType[0]==1){
                bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//ConsumeKey
                bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//TranAuthenTacKey
                bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//AppMendKey
                bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//AppMendKey01
                bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//AppMendKey02
            }else{
                CardKeyResult cardKeyResult = (CardKeyResult)keyms(cardNo,true).getObj();
                byte[] bytess = cardKeyResult.getMsg();
                //加入密钥
                bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 160, 32),16));//ConsumeKey
                bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 224, 32),16));//TranAuthenTacKey
                bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 128, 32),16));//AppMendKey
                bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 256, 32),16));//AppMendKey01
                bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 288, 32),16));//AppMendKey02
            }
            byte command = (byte)0x36;
             System.out.println("注销1：");
            byte[] bytesRet = serialConnection.callSerial(command, bytes.array());
            String retCode = CharUtil.byteToIntStr(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);
            
            if("00".equals(retCode)){
                callResult.setResult(true);
                callResult.resetMsg("卡注销成功！");
            }else{
                callResult.resetMsg("卡注销失败，错误码："+retCode+","+relCode+".");
            }
            callResult.setCode(retCode+","+relCode);
        } catch (Exception ex) {
            callResult.resetMsg("卡注销异常："+ex.getMessage());
            
        }
    }
    
    
    /**
     * 重编码
     * 
     * @param pInput
     * @param pOutput
     * @return 
     */
    private static CallResult recode() {

        CallResult callResult = new CallResult();
        System.out.println("重编码：");
        try {
            ByteBuffer bytes = ByteBuffer.allocate(78+16*19);
            
            String orderNo = "order1234567";
            bytes.put(CharUtil.strToLenByteArr(orderNo, 14));
            String reqNo = "req123321";
            bytes.put(CharUtil.strToLenByteArr(reqNo, 10));
            byte[] ticketType = CharUtil.hexStrToLenByteArr(type1, 2);
            bytes.put(ticketType);
            byte command2 = (byte)0x33;
            byte[] bytesRet2 = serialConnection.callSerial(command2, null);
            String cLogicalID = getByteCharString(bytesRet2, 13, 20);
            String cardNo = cLogicalID.trim();
            byte[] logicNo = CharUtil.hexStrToLenByteArr(cardNo, 8);
            bytes.put(logicNo);
            byte deposite = CharUtil.strToByte("12");
            bytes.put(deposite);
            int balance = CharUtil.strToInt("123");
            bytes.put(CharUtil.to4Byte(balance));
            byte[] topValue = CharUtil.to2Byte(CharUtil.strToInt("3211"));
            bytes.put(topValue);
            byte active = CharUtil.strToByte("01");
            bytes.put(active);
            byte[] senderCode = CharUtil.bcdStrToLenByteArr("0991",2);
            bytes.put(senderCode);
            byte[] cityCode = CharUtil.bcdStrToLenByteArr("8300",2);
            bytes.put(cityCode);
            byte[] busiCode = CharUtil.bcdStrToLenByteArr("0003",2);
            bytes.put(busiCode);
            byte testFlag = CharUtil.strToByte("00");
            bytes.put(testFlag);
            byte[] issueDate = CharUtil.bcdStrToLenByteArr("20180524",4);
            bytes.put(issueDate);
            byte[] cardVersion = CharUtil.bcdStrToLenByteArr("01",2);
            bytes.put(cardVersion);
            byte[] cardStartDate = CharUtil.bcdStrToLenByteArr("20180524",4);
            bytes.put(cardStartDate);
            byte[] cardEndDate = CharUtil.bcdStrToLenByteArr("20190902",4);
            bytes.put(cardEndDate);
            //---
            byte[] logicDate = CharUtil.bcdStrToLenByteArr("20180524",7);
            bytes.put(logicDate);
            byte[] logicDays = CharUtil.to2Byte(CharUtil.strToInt("365"));
            bytes.put(logicDays);
            //---
            byte appVersion = CharUtil.strToByte("01");
            bytes.put(appVersion);
            byte exitEntryMode = CharUtil.strToByte("01");
            bytes.put(exitEntryMode);
            byte[] entryLineStation = CharUtil.bcdStrToLenByteArr("0000",2);;
            bytes.put(entryLineStation);
            byte[] exitLineStation =  CharUtil.bcdStrToLenByteArr("0000",2);
            bytes.put(exitLineStation);
            if(ticketType[0]==1){
                 cardNo =cardNo.substring(8,16);
                byte command1 = (byte)0x33;
                byte[] bytesRet1 = serialConnection.callSerial(command1, null);
                String cPhysicalID = getByteCharString(bytesRet1, 33, 20).trim();
                cPhysicalID = cPhysicalID.substring(6, 14);
                System.out.println("cPhysicalID:"+cPhysicalID);
                TokenKeyResult cardKeyResult = (TokenKeyResult)keyms(cardNo,false).getObj();
                System.out.println("返回秘钥mac：\n"+CharUtil.getByteCharString(cardKeyResult.getMac(), 0, 8));
                System.out.println("返回秘钥key：\n"+CharUtil.getByteCharString(cardKeyResult.getKey(), 0, 12));           
                String str = CharUtil.getByteCharString(cardKeyResult.getMac(), 0, 8)+CharUtil.getByteCharString(cardKeyResult.getKey(), 0, 12);
                addKmsByByte1(bytes,str);
            }else{
            
                CardKeyResult cardKeyResult = (CardKeyResult)keyms(cardNo,true).getObj();
                //加入密钥
                addKmsByByte(bytes,cardKeyResult.getMsg()); 
            }
            byte command = (byte)0x37;        
            byte[] bytesRet = serialConnection.callSerial(command, bytes.array());
            String retCode = CharUtil.byteToIntStr(bytesRet, 0, 2);
            String relCode = CharUtil.getByteString(bytesRet, 2, 1);
            
            if("00".equals(retCode)){
                callResult.setResult(true);
                callResult.resetMsg("卡重编码成功！");
            }else{
                callResult.resetMsg("卡重编码失败，错误码："+retCode+","+relCode+".");
                
            }
            callResult.setCode(retCode+","+relCode);
        } catch (Exception ex) {
            callResult.resetMsg("卡重编码异常："+ex.getMessage());
        }

        return callResult;
    }
    
    
     /**
     * 预赋值
     * 
     * @param pInput
     * @param pOutput
     * @return 
    */
    private static CallResult inadvEvaluate() {

        CallResult callResult = new CallResult();
        byte[] byteRets = null;

        try {
            ByteBuffer bytes = ByteBuffer.allocate(56+16*6);
            
            String orderNo = "order123456789";
            bytes.put(CharUtil.strToLenByteArr(orderNo, 14));
            String reqNo = "req1233211";
            bytes.put(CharUtil.strToLenByteArr(reqNo, 10));
            byte[] ticketType = CharUtil.hexStrToLenByteArr(type1, 2);
            bytes.put(ticketType);
            //String  cardNo = "8300000300000002";
            byte command2 = (byte)0x33;
           byte[] bytesRet1 = serialConnection.callSerial(command2, null);
           String cardNo = getByteCharString(bytesRet1, 13, 20);
            cardNo = cardNo.trim();
            byte[] logicNo = CharUtil.hexStrToLenByteArr(cardNo, 8);
            bytes.put(logicNo);
            byte deposite = CharUtil.strToByte("12");
            bytes.put(deposite);
            int balance = CharUtil.strToInt("124");
            bytes.put(CharUtil.to4Byte(balance));
            byte[] topValue = CharUtil.to2Byte(CharUtil.strToInt("3211"));
            bytes.put(topValue);
            //---
            byte active = CharUtil.strToByte("01");
            bytes.put(active);
            byte[] logicDate = CharUtil.bcdStrToLenByteArr("20180524",7);
            bytes.put(logicDate);
            byte[] logicDays = CharUtil.to2Byte(CharUtil.strToInt("365"));
            bytes.put(logicDays);
            //---
            byte exitEntryMode = CharUtil.strToByte("01");
            bytes.put(exitEntryMode);
            byte[] entryLineStation = CharUtil.bcdStrToLenByteArr("0000",2);
            bytes.put(entryLineStation);
            byte[] exitLineStation =  CharUtil.bcdStrToLenByteArr("0000",2);
            bytes.put(exitLineStation);
            CardKeyResult cardKeyResult = (CardKeyResult)keyms(cardNo,true).getObj();
             byte[] bytess = cardKeyResult.getMsg();
             if(ticketType[0]==1){
                 //加入密钥
                bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//TransferInKey
                bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//ConsumeKey
                bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//TranAuthenTacKey
                bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//AppMendKey
                bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//AppMendKey01
                bytes.put(CharUtil.hexStrToLenByteArr("00000000000000000000000000000000",16));//AppMendKey02
             }else{
                //加入密钥
                bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 192, 32),16));//TransferInKey
                bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 160, 32),16));//ConsumeKey
                bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 224, 32),16));//TranAuthenTacKey
                bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 128, 32),16));//AppMendKey
                bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 256, 32),16));//AppMendKey01
                bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 288, 32),16));//AppMendKey02
             }
            byte command = (byte)0x35;       
            byte[] bytesRet = serialConnection.callSerial(command, bytes.array());
            String retCode = CharUtil.byteToIntStr(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);
            
            if("00".equals(retCode)){
                callResult.setResult(true);
                callResult.resetMsg("卡预赋值成功！");
            }else{
                callResult.resetMsg("卡预赋值失败，错误码："+retCode+","+relCode+".");
            }
            callResult.setCode(retCode+","+relCode);
        } catch (Exception ex) {
            callResult.resetMsg("卡预赋值异常："+ex.getMessage());
        }

        return callResult;
    } 
    
    
      /**
     * 写记名卡
     * 
     * @param param
     * @param analyzeVo
     * @param kmsCardVo
     * @return 
     */
    private static CallResult signCard() 
 {
        
        CallResult callResult = new CallResult();
        byte[] byteRets = null;
        
        try {

            ByteBuffer bytes = ByteBuffer.allocate(16+164);
            
            //加入密钥
           // String  cardNo = "8300000300000005";
            String  cardNo = logicalId1;
            CardKeyResult cardKeyResult = (CardKeyResult)keyms(cardNo,true).getObj();
            byte[] bytess = cardKeyResult.getMsg();
            bytes.put(CharUtil.hexStrToLenByteArr(CharUtil.getByteCharString(bytess, 288, 32),16));//AppMendKey02
            
            bytes.put((byte)0x00);
            bytes.put((byte)0x01);
            
            String employeeName = StringUtil.addEmptyAfterUTF(name1, 128);
            bytes.put(CharUtil.utfStrToBytes(employeeName));
            
            //String employeeNo = StringUtil.addEmptyAfter("1234123412341234", 32);
            String employeeNo = StringUtil.addEmptyAfter("123412341234"+employeeNo1, 32);
            bytes.put(CharUtil.gbkStrToBytes(employeeNo));
            String idType = "5";
            bytes.put(CharUtil.bcdStringToByteArray(idType));
            String sex = "1";
            bytes.put(CharUtil.bcdStringToByteArray(sex));
            byte command = (byte)0x38;       
            byte[] bytesRet = serialConnection.callSerial(command, bytes.array());
            String retCode = CharUtil.byteToIntStr(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);
            if ("00".equals(retCode)) {
                callResult.setResult(true);
                callResult.resetMsg("写记名卡成功！");
            } else {
                callResult.resetMsg("写记名卡失败，错误码："+retCode+","+relCode+".");
            }
            callResult.setCode(retCode+","+relCode);
        } catch (Exception ex) {
            callResult.resetMsg("写记名卡异常："+ex.getMessage());
        }
        
        return callResult;
    }
   private  static File getUnNoticeFile(String str,boolean isnew) throws IOException{
        
        String filename = "F:/aa/"+str+".txt";
        File file = new File(filename);
        if(isnew==true){
            if(!file.exists()){
                file.createNewFile();
            }
        }
        return file;
    }
}


