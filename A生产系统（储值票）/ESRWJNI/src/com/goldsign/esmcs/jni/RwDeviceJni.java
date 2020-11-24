/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.jni;

import com.goldsign.esmcs.dll.library.RwDeviceDll;
import com.goldsign.esmcs.dll.structure.*;

/**
 * RW读写器JNI
 * 
 * @author lenovo
 */
public class RwDeviceJni {

    private int nPort;
    
    /**
     * 设备环境初始化
     * 
     * @param wStationID
     * @param bDeviceType
     * @param wDeviceID
     * @return 
     */
    public RetInfo.ByValue commonInitializeDevice(short wStationID, byte bDeviceType, short wDeviceID){
        return RwDeviceDll.INSTANCE.Common_Initialize_Device(wStationID, bDeviceType, wDeviceID);
    }
    
    /**
     * 连接读写器接口
     * 
     * @param nPort
     * @param pReaderStatus
     * @return 
     */
    public RetInfo.ByValue commonConnectRW(int nPort, READERSTATUS.ByReference pReaderStatus){
        this.nPort = nPort;
        return RwDeviceDll.INSTANCE.Common_Connect_RW(nPort, pReaderStatus);
    }

    /**
     * 断开读写器
     * 
     * @return 
     */
    public RetInfo.ByValue commonDisconnectRW(){
        return RwDeviceDll.INSTANCE.Common_Disconnect_RW(nPort);
    }
    
    /**
     * 获取读写器信息版本
     * 
     * @param pVersion
     * @return 
     */
    public RetInfo.ByValue commonGetVersion(READERVERSION.ByReference pVersion){
        return RwDeviceDll.INSTANCE.Common_GetVersion(nPort, pVersion);
    }

    /**
     * 获取读写器SAM状态
     * 
     * @param bSamType
     * @param pSamStatus
     * @return 
     */
    public RetInfo.ByValue commonGetSamInfo(byte bSamType, SAMSTATUS.ByReference pSamStatus){
        return RwDeviceDll.INSTANCE.Common_GetSamInfo(nPort, bSamType, pSamStatus);
    }
    
    /**
     * 车票初始化(预赋值)
     * 
     * @param pInput
     * @param pOutput
     * @return 
     */
    public RetInfo.ByValue esInitEvaluate(ORDERSIN.ByReference pInput, ORDERSOUT.ByReference pOutput){
        return RwDeviceDll.INSTANCE.ES_InitEvaluate(nPort, pInput, pOutput);
    }

    /**
     * 车票预赋值
     * 
     * @param pInput
     * @param pOutput
     * @return 
     */
    public RetInfo.ByValue esEvaluate(ORDERSIN.ByReference pInput, ORDERSOUT.ByReference pOutput){
        return RwDeviceDll.INSTANCE.ES_Evaluate(nPort, pInput, pOutput);
    }
    
    /**
     * 车票重编码
     * 
     * @param pInput
     * @param pOutput
     * @return 
     */
    public RetInfo.ByValue esRecode(ORDERSIN.ByReference pInput, ORDERSOUT.ByReference pOutput){
        return RwDeviceDll.INSTANCE.ES_Recode(nPort, pInput, pOutput);
    }
    
    /**
     * 车票注销
     * 
     * @param pOrderNo
     * @param pApplicationNo
     * @param pOutput
     * @return 
     */
    public RetInfo.ByValue esDestroy(byte[] pOrderNo, byte[] pApplicationNo, ORDERSOUT.ByReference pOutput){
        return RwDeviceDll.INSTANCE.ES_Destroy(nPort, pOrderNo, pApplicationNo, pOutput);
    }

    /**
     * 车票分析
     * 
     * @param pAnalyze
     * @return 
     */
    public RetInfo.ByValue esAnalyze(ESANALYZE.ByReference pAnalyze){
        return RwDeviceDll.INSTANCE.ES_Analyze(nPort, pAnalyze);
    }
        
    /**
     * 参数表设置
     * 
     * @param parameterType
     * @param pathName
     * @return 
     */
    public RetInfo.ByValue configParameter(int parameterType, byte[] pathName){
        return RwDeviceDll.INSTANCE.Config_Parameter(parameterType, pathName);
    }

    /**
     * 获取参数表信息
     * 
     * @param nParameterType
     * @param pszPathName
     * @return 
     */
    public RetInfo.ByValue debugGetParameterInfo(int nParameterType, byte[] pszPathName){
        return RwDeviceDll.INSTANCE.Debug_GetParameterInfo(nParameterType, pszPathName);
    }

    /**
     * 取得DLL版本
     * 
     * @param version
     * @return 
     */
    public boolean cardDevGetVer(byte[] version){
        return RwDeviceDll.INSTANCE.CardDev_GetVer(version);
    }
}
