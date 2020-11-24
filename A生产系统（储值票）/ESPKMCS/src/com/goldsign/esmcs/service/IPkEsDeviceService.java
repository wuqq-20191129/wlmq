/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.service;

import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.exception.PkEsJniException;

/**
 * 储值票ES设备服务接口
 * 
 * @author lenovo
 */
public interface IPkEsDeviceService extends IEsDeviceService{

    /**
     * 打开票箱端口
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    CallResult openBoxPort(CallParam callParam)throws PkEsJniException;
    

    /**
     * 打长通道端口
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    CallResult openChannelPort(CallParam callParam)throws PkEsJniException;
    
    /**
     * 复位通道
     *
     * @param callParam
     * @return
     * @throws PkEsJniException
     */
    CallResult resetChannel(CallParam callParam)throws PkEsJniException;
    
    /**
     * 关闭票箱端口
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    CallResult closeBoxPort(CallParam callParam)throws PkEsJniException;
    
    /**
     * 关闭通道端口
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    CallResult closeChannelPort(CallParam callParam)throws PkEsJniException;
    
    /**
     * 复位单个票箱
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    CallResult resetOneBox(CallParam callParam)throws PkEsJniException;
    
    /**
     * 复位所有票箱
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    CallResult resetAllBox(CallParam callParam)throws PkEsJniException;
    
    /**
     * 卸载单个票箱
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    CallResult uploadOneBox(CallParam callParam)throws PkEsJniException;
    
    /**
     * 卸载所有票箱
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    CallResult uploadAllBox(CallParam callParam)throws PkEsJniException;
    
    /**
     * 取得票箱状态
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    CallResult getAllBoxState(CallParam callParam)throws PkEsJniException;
    
    /**
     * 取得票箱传感器状态
     *
     * @param callParam
     * @return
     * @throws PkEsJniException
     */
    CallResult getBoxSensorState(CallParam callParam)throws PkEsJniException;
    
    /**
     * 获取票箱dll版本号
     *
     * @return
     * @throws PkEsJniException
     */
    public CallResult getBoxDllVersion() throws PkEsJniException;
    
    /**
     * 发卡
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    CallResult sendCard(CallParam callParam)throws PkEsJniException;
    
    /**
     * 走卡
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    CallResult moveCard(CallParam callParam)throws PkEsJniException;
    
    /**
     * 取得跑卡错误信息
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    CallResult getCardRunError(CallParam callParam)throws PkEsJniException;
    
    /**
     * 获取通道dll版本号
     * @return 
     * @throws PkEsJniException 
     */
    public CallResult getChanelDllVersion() throws PkEsJniException;
    
    /**
     * 获取设备版本号
     *
     * @return
     * @throws PkEsJniException
     */
    public CallResult getDevVersion() throws PkEsJniException;
    
    /**
     * 卡机初始化
     * @return
     * @throws PkEsJniException 
     */
    public CallResult channelInit() throws PkEsJniException;
    
    /**
     * 对零电位
     * @return
     * @throws PkEsJniException 
     */
    public CallResult channelModaToZero() throws PkEsJniException;
    
    /**
     * 通道传感器状态
     * @return
     * @throws PkEsJniException 
     */
    public CallResult channelGetACCStatus() throws PkEsJniException;
    
    /**
     * 通道暂停
     * @return
     * @throws PkEsJniException 
     */
    public CallResult channelPause() throws PkEsJniException;
    
    /**
     * 通道继续
     * @return
     * @throws PkEsJniException 
     */
    public CallResult channelContinue() throws PkEsJniException;

    /**
     * 检测卡位
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    CallResult channelGetSiteInf(CallParam callParam)throws PkEsJniException;    
}
