/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.rw.dll.library;

import com.goldsign.rw.dll.structure.*;
import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 *
 * @author lenovo
 */
public interface RwDeviceDll extends Library{

    RwDeviceDll INSTANCE = (RwDeviceDll) Native.loadLibrary("GSReader", RwDeviceDll.class);
    
    //设备环境初始化
    RetInfo.ByValue Common_Initialize_Device(short wStationID, byte bDeviceType, short wDeviceID);
    
    //连接读写器接口
    RetInfo.ByValue Common_Connect_RW(int nPort, READERSTATUS.ByReference pReaderStatus);

    //断开读写器
    RetInfo.ByValue Common_Disconnect_RW(int nPort);
    
    //获取读写器信息版本
    RetInfo.ByValue Common_GetVersion(int nPort, READERVERSION.ByReference pVersion);

    //获取读写器SAM状态
    RetInfo.ByValue Common_GetSamInfo(int nPort, byte bSamType, SAMSTATUS.ByReference pSamStatus);
    
    //车票初始化(预赋值)
    RetInfo.ByValue ES_InitEvaluate(int nPort, ORDERSIN.ByReference pInput, ORDERSOUT.ByReference pOutput);

    //车票预赋值
    RetInfo.ByValue ES_Evaluate(int nPort, ORDERSIN.ByReference pInput, ORDERSOUT.ByReference pOutput);
    
    //车票重编码
    RetInfo.ByValue ES_Recode(int nPort, ORDERSIN.ByReference pInput, ORDERSOUT.ByReference pOutput);
    
    //车票注销
    RetInfo.ByValue ES_Destroy(int nPort, byte[] pOrderNo, byte[] pApplicationNo, ORDERSOUT.ByReference pOutput);

    //车票分析
    RetInfo.ByValue ES_Analyze(int nPort, ESANALYZE.ByReference pAnalyze);
        
    //参数表设置
    RetInfo.ByValue Config_Parameter(int ParameterType, byte[] PathName);

    //获取参数表信息
    RetInfo.ByValue Debug_GetParameterInfo(int nParameterType, byte[] pszPathName);

    boolean CardDev_GetVer(byte[] Version);
    
    int ES_GetVerSion(int a);
    
    SocketInfo.ByValue socket_close(SocketInfo[] aa);
}
