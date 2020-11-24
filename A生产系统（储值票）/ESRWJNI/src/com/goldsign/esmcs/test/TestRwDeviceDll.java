/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.test;

import com.goldsign.esmcs.dll.library.RwDeviceDll;
import com.goldsign.esmcs.dll.structure.*;

/**
 *
 * @author lenovo
 */
public class TestRwDeviceDll {

    public static void main(String[] args){
        
        //Common_Initialize_Device();
        //Common_Disconnect_RW();
        //Common_Connect_RW();
        //Common_GetVersion();
        //Common_GetSamInfo();
        //ES_InitEvaluate();
        //ES_Evaluate();
        //ES_Recode();
        //ES_Destroy();
        //ES_Analyze();
        //Config_Parameter();
        //Debug_GetParameterInfo();
        //testCardDev_GetVer(); //雄帝
      //test();
        testChange();
    }
    
    public static void Common_Initialize_Device(){
    
        short wStationID = 1;
        byte bDeviceType = 2;
        short wDeviceID = 1;
        RetInfo retInfo = RwDeviceDll.INSTANCE.Common_Initialize_Device(wStationID, bDeviceType, wDeviceID);
        System.out.println("错误码:"+retInfo.wErrCode);
        //关联提示码置1
        System.out.println("关联提示码:"+retInfo.bNoticeCode);
        System.out.println("扩充保留字段:"+new String(retInfo.bRfu));
    }
    
    public static void Common_Connect_RW(){
    
        int nPort = 1;
        READERSTATUS.ByReference pReaderStatus = new READERSTATUS.ByReference();
        RetInfo retInfo = RwDeviceDll.INSTANCE.Common_Connect_RW(nPort, pReaderStatus);
        System.out.println("错误码:"+retInfo.wErrCode);
        //关联提示码置2
        System.out.println("关联提示码:"+retInfo.bNoticeCode);
        System.out.println("扩充保留字段:"+new String(retInfo.bRfu));
    }

    
    public static void Common_Disconnect_RW(){
    
        int nPort = 1;
        RetInfo retInfo = RwDeviceDll.INSTANCE.Common_Disconnect_RW(nPort);
        System.out.println("错误码:"+retInfo.wErrCode);
        //关联提示码置3
        System.out.println("关联提示码:"+retInfo.bNoticeCode);
        System.out.println("扩充保留字段:"+new String(retInfo.bRfu));
    }
    
    public static void Common_GetVersion(){
    
        int nPort = 1;
        READERVERSION.ByReference pVersion = new READERVERSION.ByReference();
        RetInfo retInfo = RwDeviceDll.INSTANCE.Common_GetVersion(nPort, pVersion);
        System.out.println("错误码:"+retInfo.wErrCode);
        System.out.println("关联提示码:"+retInfo.bNoticeCode);
        System.out.println("扩充保留字段:"+new String(retInfo.bRfu));
        System.out.println("v1:"+new String(pVersion.cAPIVersion));
        System.out.println("v2:"+new String(pVersion.cFirmwareVersion));
    }
    
    public static void Common_GetSamInfo(){
    
        int nPort = 1;
        byte bSamType = 1;
        SAMSTATUS.ByReference pSamStatus = new SAMSTATUS.ByReference();
        RetInfo retInfo = RwDeviceDll.INSTANCE.Common_GetSamInfo(nPort, bSamType, pSamStatus);
        System.out.println("错误码:"+retInfo.wErrCode);
        System.out.println("关联提示码:"+retInfo.bNoticeCode);
        System.out.println("扩充保留字段:"+new String(retInfo.bRfu));
        System.out.println("samStatus:"+pSamStatus.bSAMStatus);
        System.out.println("samId:"+new String(pSamStatus.cSAMID));
    }
    
    public static void ES_InitEvaluate(){
    
        int nPort = 1;
        ORDERSIN.ByReference pInput = new ORDERSIN.ByReference();
        ORDERSOUT.ByReference pOutput = new ORDERSOUT.ByReference();
        RetInfo retInfo = RwDeviceDll.INSTANCE.ES_InitEvaluate(nPort, pInput, pOutput);
        System.out.println("错误码:"+retInfo.wErrCode);
        System.out.println("关联提示码:"+retInfo.bNoticeCode);
        System.out.println("扩充保留字段:"+new String(retInfo.bRfu));
        System.out.println("tradeType:"+new String(pOutput.cTradeType));
        System.out.println("orderNo:"+new String(pOutput.cOrderNo));
        System.out.println("TicketType:"+new String(pOutput.bTicketType));
        System.out.println("applicationNo:"+new String(pOutput.cApplicationNO));
        System.out.println("logicalId:"+new String(pOutput.cLogicalID));
        System.out.println("physicalId:"+new String(pOutput.cPhysicalID));
        System.out.println("dtDate:"+new String(pOutput.dtDate));
        for(int i=0;i<pOutput.lBalance.length;i++){
            System.out.println(pOutput.lBalance[i]);
        }
        System.out.println("lBalance:"+byteToHex(pOutput.lBalance)); 
        System.out.println("lBalance:"+bytesToInt(pOutput.lBalance));   
        System.out.println("startDate:"+byteToHex(pOutput.bStartDate));
        System.out.println("startDate:"+bcd2Str(pOutput.bStartDate));
        System.out.println("endDate:"+bcd2Str(pOutput.bEndDate));
        System.out.println("samId:"+new String(pOutput.cSAMID));
        
        
        
    }
    
    public static void ES_Evaluate(){
    
        int nPort = 1;
        ORDERSIN.ByReference pInput = new ORDERSIN.ByReference();
        ORDERSOUT.ByReference pOutput = new ORDERSOUT.ByReference();
        RetInfo retInfo = RwDeviceDll.INSTANCE.ES_Evaluate(nPort, pInput, pOutput);
        System.out.println("错误码:"+retInfo.wErrCode);
        System.out.println("关联提示码:"+retInfo.bNoticeCode);
        System.out.println("扩充保留字段:"+new String(retInfo.bRfu));
        System.out.println("endDate:"+new String(pOutput.bEndDate));
        System.out.println("startDate:"+new String(pOutput.bStartDate));
        System.out.println("TicketType:"+new String(pOutput.bTicketType));
        System.out.println("applicationNo:"+new String(pOutput.cApplicationNO));
        System.out.println("logicalId:"+new String(pOutput.cLogicalID));
        System.out.println("orderNo:"+new String(pOutput.cOrderNo));
        System.out.println("physicalId:"+new String(pOutput.cPhysicalID));
        System.out.println("samId:"+new String(pOutput.cSAMID));
        System.out.println("tradeType:"+new String(pOutput.cTradeType));
        System.out.println("dtDate:"+new String(pOutput.dtDate));
        System.out.println("lBalance:"+pOutput.lBalance);
    }
    
    public static void ES_Recode(){
    
        int nPort = 1;
        ORDERSIN.ByReference pInput = new ORDERSIN.ByReference();
        ORDERSOUT.ByReference pOutput = new ORDERSOUT.ByReference();
        RetInfo retInfo = RwDeviceDll.INSTANCE.ES_Recode(nPort, pInput, pOutput);
        System.out.println("错误码:"+retInfo.wErrCode);
        System.out.println("关联提示码:"+retInfo.bNoticeCode);
        System.out.println("扩充保留字段:"+new String(retInfo.bRfu));
        System.out.println("endDate:"+new String(pOutput.bEndDate));
        System.out.println("startDate:"+new String(pOutput.bStartDate));
        System.out.println("TicketType:"+new String(pOutput.bTicketType));
        System.out.println("applicationNo:"+new String(pOutput.cApplicationNO));
        System.out.println("logicalId:"+new String(pOutput.cLogicalID));
        System.out.println("orderNo:"+new String(pOutput.cOrderNo));
        System.out.println("physicalId:"+new String(pOutput.cPhysicalID));
        System.out.println("samId:"+new String(pOutput.cSAMID));
        System.out.println("tradeType:"+new String(pOutput.cTradeType));
        System.out.println("dtDate:"+new String(pOutput.dtDate));
        System.out.println("lBalance:"+pOutput.lBalance);
    }
    
    public static void ES_Destroy(){
    
        int nPort = 1;
        byte[] pOrderNo = new byte[14];
        byte[] pApplicationNo = new byte[10];
        ORDERSOUT.ByReference pOutput = new ORDERSOUT.ByReference();
        RetInfo retInfo = RwDeviceDll.INSTANCE.ES_Destroy(nPort, pOrderNo, pApplicationNo, pOutput);
        System.out.println("错误码:"+retInfo.wErrCode);
        System.out.println("关联提示码:"+retInfo.bNoticeCode);
        System.out.println("扩充保留字段:"+new String(retInfo.bRfu));
        System.out.println("endDate:"+new String(pOutput.bEndDate));
        System.out.println("startDate:"+new String(pOutput.bStartDate));
        System.out.println("TicketType:"+new String(pOutput.bTicketType));
        System.out.println("applicationNo:"+new String(pOutput.cApplicationNO));
        System.out.println("logicalId:"+new String(pOutput.cLogicalID));
        System.out.println("orderNo:"+new String(pOutput.cOrderNo));
        System.out.println("physicalId:"+new String(pOutput.cPhysicalID));
        System.out.println("samId:"+new String(pOutput.cSAMID));
        System.out.println("tradeType:"+new String(pOutput.cTradeType));
        System.out.println("dtDate:"+new String(pOutput.dtDate));
        System.out.println("lBalance:"+pOutput.lBalance);
    }
    
    public static void ES_Analyze(){
    
        int nPort = 1;
        ESANALYZE.ByReference pAnalyze = new ESANALYZE.ByReference();
        RetInfo retInfo = RwDeviceDll.INSTANCE.ES_Analyze(nPort, pAnalyze);
        System.out.println("错误码:"+retInfo.wErrCode);
        System.out.println("关联提示码:"+retInfo.bNoticeCode);
        System.out.println("扩充保留字段:"+new String(retInfo.bRfu));
        System.out.println("character:"+pAnalyze.bCharacter);
        System.out.println("issueStatus:"+pAnalyze.bIssueStatus);
        System.out.println("status:"+byteToUnByte(pAnalyze.bStatus));
        System.out.println("ticketType:"+new String(pAnalyze.bTicketType));
        System.out.println("logicalId:"+new String(pAnalyze.cLogicalID));
        System.out.println("physicalId:"+new String(pAnalyze.cPhysicalID));
        System.out.println("dtExpire:"+bcd2Str(pAnalyze.dtExpire));
        System.out.println("issueDate:"+bcd2Str(pAnalyze.dtIssueDate));
        System.out.println("balance:"+bytesToInt(pAnalyze.lBalance));
        System.out.println("deposite:"+bytesToInt(pAnalyze.lDeposite));
        
    }
    
    public static void Config_Parameter(){
    
        int ParameterType = 1;
        byte[] PathName = new byte[1024];
        RetInfo retInfo = RwDeviceDll.INSTANCE.Config_Parameter(ParameterType, PathName);
        System.out.println("错误码:"+retInfo.wErrCode);
        System.out.println("关联提示码:"+retInfo.bNoticeCode);
        System.out.println("扩充保留字段:"+new String(retInfo.bRfu));
    }
    
    public static void Debug_GetParameterInfo(){
    
        int ParameterType = 1;
        byte[] PathName = new byte[1024];
        RetInfo retInfo = RwDeviceDll.INSTANCE.Debug_GetParameterInfo(ParameterType, PathName);
        System.out.println("错误码:"+retInfo.wErrCode);
        System.out.println("关联提示码:"+retInfo.bNoticeCode);
        System.out.println("扩充保留字段:"+new String(retInfo.bRfu));
    }
    
    public static void testCardDev_GetVer(){
        
        byte[] Version = new byte[25];
        boolean result = RwDeviceDll.INSTANCE.CardDev_GetVer(Version);
        System.out.println(result);
        System.out.println(new String(Version));
    }
    
    public static String bcd2Str(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; i++) {
        int h = ((bytes[i] & 0xf0) >>> 4);
        int l = (bytes[i] & 0x0f);   
        temp.append(h).append(l);
        }
        return temp.toString() ;
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
    
    public static int bytesToInt(byte[] b) {
        byte[] a = new byte[4];
        int i = a.length - 1;
        int j = b.length - 1;
        for (; i >= 0 ; i--,j--) {//从b的尾部(即int值的低位)开始copy数据
            if(j >= 0){
                a[i] = b[j];
            }else{
                 a[i] = 0;//如果b.length不足4,则将高位补0
            }
        }
        int v0 = (a[0] & 0xff) << 0;//&0xff将byte值无差异转成int,避免Java自动类型提升后,会保留高位的符号位
        int v1 = (a[1] & 0xff) << 8;
        int v2 = (a[2] & 0xff) << 16;
        int v3 = (a[3] & 0xff) << 24;
        return v0 + v1 + v2 + v3;
    }

    public static int byteToUnByte(byte byt){
        return byt&0xff;
    }
    
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }
    
    private static byte toByte(char c) {   
        byte b = (byte) "0123456789ABCDEF".indexOf(c);   
        return b;   
    }
    
    
    public static void testChange(){
        //lBalance:50,C3,00,00
        //lBalance:50000
        //startDate:20,13,05,01,23,59,59
        //startDate:20130501235959
        /*byte[] bs = new byte[]{(byte)0x50,(byte)0xc3,(byte)0x00,(byte)0x00};
        System.out.println("50,c3,00,00");
        int l = bytesToInt(bs);
        System.out.println("l = "+l);
        byte[] bs2 = intToBytes(l);
        System.out.println("bs2 = " + byteToHex(bs2));*/
        byte[] bs = new byte[]{(byte)0x20,(byte)0x13,(byte)0x05,(byte)0x01,(byte)0x23,(byte)0x59,(byte)0x59};
        String str = bcd2Str(bs);
        System.out.println("str = " + str);
        byte[] bs2 =  hexStringToByte(str);
        System.out.println("bs = " + bcd2Str(bs2));
    }
    
     public static byte[] intToBytes(int i) {
        
        byte[] result = new byte[4];
        result[0] = (byte) (i & 0xFF);
        result[1] = (byte) ((i >> 8) & 0xFF);
        result[2] = (byte) ((i >> 16) & 0xFF);
        result[3] = (byte) ((i >> 24) & 0xFF);
        
        return result;
    }
}
