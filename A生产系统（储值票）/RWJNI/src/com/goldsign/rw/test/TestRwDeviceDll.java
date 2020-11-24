/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.rw.test;

import com.goldsign.rw.dll.structure.ORDERSIN;
import com.goldsign.rw.dll.structure.ORDERSOUT;
import com.goldsign.rw.dll.structure.READERSTATUS;
import com.goldsign.rw.dll.structure.SAMSTATUS;
import com.goldsign.rw.dll.structure.ESANALYZE;
import com.goldsign.rw.dll.structure.RetInfo;
import com.goldsign.rw.dll.structure.READERVERSION;
import com.goldsign.rw.dll.library.*;
import com.goldsign.rw.dll.structure.*;
import java.util.Arrays;

/**
 *
 * @author lenovo
 */
public class TestRwDeviceDll {

    public static void main(String[] args){
        
        Common_Initialize_Device();
        //Common_Disconnect_RW();
        Common_Connect_RW();
        //Common_GetVersion();
        //Common_GetSamInfo();
        ES_InitEvaluate();
        //ES_Evaluate();
        //ES_Recode();
        //ES_Destroy();
        //ES_Analyze();
        //Config_Parameter();
        //Debug_GetParameterInfo();
        //testCardDev_GetVer(); //雄帝
      //test();
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
        System.out.println("status:"+pAnalyze.bStatus);
        System.out.println("ticketType:"+new String(pAnalyze.bTicketType));
        System.out.println("logicalId:"+new String(pAnalyze.cLogicalID));
        System.out.println("physicalId:"+new String(pAnalyze.cPhysicalID));
        System.out.println("dtExpire:"+new String(pAnalyze.dtExpire));
        System.out.println("issueDate:"+new String(pAnalyze.dtIssueDate));
        System.out.println("balance:"+pAnalyze.lBalance);
        System.out.println("deposite:"+pAnalyze.lDeposite);
        
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
    
    public static void ES_GetVerSion(){
        
        int result = RwDeviceDll.INSTANCE.ES_GetVerSion(12222);
        System.out.println(result);
    }
    
    public static void test(){
        byte[] a;
        a = Arrays.copyOf("k1".getBytes(), 20); 
        //System.out.println(new String(a));
        
        SocketInfo socketInfo = new SocketInfo();
        SocketInfo[] s = (SocketInfo[]) socketInfo.toArray(2);
       
        s[0].iTmp = 11;
        s[0].strBuf = Arrays.copyOf("test1-".getBytes(), 1024);
        
        s[1].iTmp = 21;
        s[1].strBuf = Arrays.copyOf("test2-".getBytes(), 1024);
        SocketInfo result = RwDeviceDll.INSTANCE.socket_close(s);
        System.out.println("result:"+result.iTmp);
        System.out.println(s[0].iTmp+"---"+new String(s[0].strBuf));
        System.out.println(s[1].iTmp+"---"+new String(s[1].strBuf));
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
}
