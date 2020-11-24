/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.dll.library;

import com.goldsign.esmcs.dll.structure.*;
import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * RW读写器DLL
 * 
 * @author lenovo
 */
public interface RwDeviceDll extends Library{

    RwDeviceDll INSTANCE = (RwDeviceDll) Native.loadLibrary("GSReader", RwDeviceDll.class);
    
    /**
     * 设备环境初始化
     * 
     * @param wStationID
     * @param bDeviceType
     * @param wDeviceID
     * @return 
     */
    RetInfo.ByValue Common_Initialize_Device(short wStationID, byte bDeviceType, short wDeviceID);
    
    /**
     * 连接读写器接口
     * 
     * @param nPort
     * @param pReaderStatus
     * @return 
     */
    RetInfo.ByValue Common_Connect_RW(int nPort, READERSTATUS.ByReference pReaderStatus);

    /**
     * 断开读写器
     * 
     * @param nPort
     * @return 
     */
    RetInfo.ByValue Common_Disconnect_RW(int nPort);
    
    /**
     * 获取读写器信息版本
     * 
     * @param nPort
     * @param pVersion
     * @return 
     */
    RetInfo.ByValue Common_GetVersion(int nPort, READERVERSION.ByReference pVersion);

    /**
     * 获取读写器SAM状态
     * 
     * @param nPort
     * @param bSamType
     * @param pSamStatus
     * @return 
     */
    RetInfo.ByValue Common_GetSamInfo(int nPort, byte bSamType, SAMSTATUS.ByReference pSamStatus);
    
    /**
     * 车票初始化(预赋值)
     * 
     * @param nPort
     * @param pInput
     * @param pOutput
     * @return 
     */
    RetInfo.ByValue ES_InitEvaluate(int nPort, ORDERSIN.ByReference pInput, ORDERSOUT.ByReference pOutput);

    /**
     * 车票预赋值
     * 
     * @param nPort
     * @param pInput
     * @param pOutput
     * @return 
     */
    RetInfo.ByValue ES_Evaluate(int nPort, ORDERSIN.ByReference pInput, ORDERSOUT.ByReference pOutput);
    
    /**
     * 车票重编码
     * 
     * @param nPort
     * @param pInput
     * @param pOutput
     * @return 
     */
    RetInfo.ByValue ES_Recode(int nPort, ORDERSIN.ByReference pInput, ORDERSOUT.ByReference pOutput);
    
    /**
     * 车票注销
     * 
     * @param nPort
     * @param pOrderNo
     * @param pApplicationNo
     * @param pOutput
     * @return 
     */
    RetInfo.ByValue ES_Destroy(int nPort, byte[] pOrderNo, byte[] pApplicationNo, ORDERSOUT.ByReference pOutput);

    /**
     * 车票分析
     * 
     * @param nPort
     * @param pAnalyze
     * @return 
     */
    RetInfo.ByValue ES_Analyze(int nPort, ESANALYZE.ByReference pAnalyze);
        
    /**
     * 参数表设置
     * 
     * @param ParameterType
     * @param PathName
     * @return 
     */
    RetInfo.ByValue Config_Parameter(int ParameterType, byte[] PathName);

    /**
     * 获取参数表信息
     * 
     * @param nParameterType
     * @param pszPathName
     * @return 
     */
    RetInfo.ByValue Debug_GetParameterInfo(int nParameterType, byte[] pszPathName);

    /**
     * 取得版本号
     * 
     * @param Version
     * @return 
     */
    boolean CardDev_GetVer(byte[] Version);;
    
}
