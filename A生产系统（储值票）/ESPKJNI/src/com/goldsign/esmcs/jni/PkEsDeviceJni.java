/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.jni;

import com.goldsign.esmcs.dll.library.EsPkBoxDll;
import com.goldsign.esmcs.dll.library.EsPkChanelDll;
import com.goldsign.esmcs.dll.structure.*;

/**
 * 储值票JNI
 * 
 * @author lenovo
 */
public class PkEsDeviceJni {
 
    //---------------------------------------------------通道API--------------------------------------------------
    /**
     * 创建CardInf数组
     * 
     * @param size
     * @return 
     */
    public CardInf[] createCardInfArray(int size){
        CardInf[] cardInfs = (CardInf[]) new CardInf().toArray(size);
        return cardInfs;
    }
    
    /**
     * 卡机关闭
     * 
     * @return 
     */
    public short channelClose(){
        return EsPkChanelDll.INSTANCE.ACCAPI_Close();
    }
   
    /**
     * 读机器动作状态
     * 
     * @param statusVal
     * @return 
     */
    public short channelGetACCStatus(byte[] statusVal){
        return EsPkChanelDll.INSTANCE.ACCAPI_GetACCStatus(statusVal);
    }
   
    /**
     * 得到函数版本
     * 
     * @param version
     * @return 
     */
    public short channelGetDllVersion(byte []version){
        return EsPkChanelDll.INSTANCE.ACCAPI_GetDllVersion(version);
    }

    /**
     * 取设备版本号
     * 
     * @param version
     * @return 
     */
    public short getDevVersion(byte[] version) {
        return EsPkChanelDll.INSTANCE.ACCAPI_GetDevVersion(version);
    }
        
    /**
     * 得到全部工位卡的信息
     * 
     * @param card
     * @param boxIsCard
     * @return 
     */
    public short channelGetStationInf(CardInf[] card, short []boxIsCard){
        return EsPkChanelDll.INSTANCE.ACCAPI_GetStationInf(card, boxIsCard);
    }
    
    /**
     * 卡机初始化
     * 
     * @return 
     */
    public short channelInit(){
        return EsPkChanelDll.INSTANCE.ACCAPI_Init();
    }
   
    /**
     * 步进电机零位
     * 
     * @return 
     */
    public short channelMADAToZero(){
        return EsPkChanelDll.INSTANCE.ACCAPI_MADAToZero();
    }
   
    /**
     * 走卡
     * 
     * @param card
     * @param boxIsCard
     * @return 
     */
    public short channelMoveCard(CardInf[] card, short []boxIsCard){
        return  EsPkChanelDll.INSTANCE.ACCAPI_MoveCard(card, boxIsCard);
    }
   
    /**
     * 卡机打开
     * 
     * @param port
     * @return 
     */
    public short channelOpen(short port){
        return EsPkChanelDll.INSTANCE.ACCAPI_Open(port);
    }
   
    /**
     * 暂停
     * 
     * @return 
     */
    public short channelPause(){
        return EsPkChanelDll.INSTANCE.ACCAPI_PauseOrContinue((byte)0);
    }
    
    /**
     * 继续
     * 
     * @return 
     */
    public short channelContinue(){
        return EsPkChanelDll.INSTANCE.ACCAPI_PauseOrContinue((byte)1);
    }
 
    /**
     * 卡机复位
     * 
     * @param card
     * @param boxIsCard
     * @return 
     */
    public short channelResetACC(CardInf[] card, short[] boxIsCard){
        return EsPkChanelDll.INSTANCE.ACCAPI_ResetACC(card, boxIsCard);
    }
    
    /**
     * 发卡
     * 
     * @param cardNo
     * @param card
     * @param boxIsCard
     * @return 
     */
    public short channelSendCard(int cardNo, CardInf[] card, short[] boxIsCard){
        return EsPkChanelDll.INSTANCE.ACCAPI_SendCard(cardNo, card, boxIsCard);
    }
      
    /**
     * 得到卡运行时错误信息
     * 
     * @param state
     * @return 
     */
    public short channelGetCardRunError(LineState.ByReference state){
        return EsPkChanelDll.INSTANCE.ACCAPI_GetCardRunError(state);
    }
    //----------------------------------------------------通道API--------------------------------------------------
    
    
    //----------------------------------------------------票箱API--------------------------------------------------
    /**
     * 创建BoxInfo数组
     * 
     * @param size
     * @return 
     */
    public BoxInf[] createBoxInfoArray(int size){
        BoxInf[] boxInfs = (BoxInf[]) new BoxInf().toArray(size);
        return boxInfs;
    }
    
    /**
     * 创建BoxSensor数组
     * 
     * @param size
     * @return 
     */
    public BoxSensor[] createBoxSensorArray(int size){
         BoxSensor[] boxSensors = (BoxSensor[]) new BoxSensor().toArray(size);
         return boxSensors;
    }
    
    /**
     * 关闭串口
     * 
     * @return 
     */
    public short boxExitCom(){
        return EsPkBoxDll.INSTANCE.ESBoxAPI_ExitCom();
    }
 
    /**
     * 得到所有票箱状态
     * 
     * @param cardBox
     * @param errInf
     * @return 
     */
    public short boxGetCardBoxState(BoxInf[] cardBox, AlermInf.ByReference errInf){
        return EsPkBoxDll.INSTANCE.ESBoxAPI_GetCardBoxState(cardBox, errInf);
    }
  
    /**
     * 得到所有光偶状态
     * 
     * @param sensor
     * @param errInf
     * @return 
     */
    public short boxGetSensorState(BoxSensor[] sensor, AlermInf.ByReference errInf){
        return EsPkBoxDll.INSTANCE.ESBoxAPI_GetSensorState(sensor, errInf);
    }
   
    /**
     * 初始化串口
     * 
     * @param comNo
     * @return 
     */
    public boolean boxInitCom(short comNo){
        return EsPkBoxDll.INSTANCE.ESBoxAPI_InitCom(comNo);
    }
   
    /**
     * 复位所有票箱
     * 
     * @param cardBox
     * @param errInf
     * @return 
     */
    public short boxResetAllCardBox(BoxInf[] cardBox, AlermInf.ByReference errInf){
        return EsPkBoxDll.INSTANCE.ESBoxAPI_ResetAllCardBox(cardBox, errInf);
    }
  
    /**
     * 复位单个票箱 1-5号票箱
     * 
     * @param cardBoxNo
     * @param cardBox
     * @param errInf
     * @return 
     */
    public short boxResetOneCardBox(short cardBoxNo, BoxInf[] cardBox, AlermInf.ByReference errInf){
        return EsPkBoxDll.INSTANCE.ESBoxAPI_ResetOneCardBox(cardBoxNo, cardBox, errInf);
    }
 
    /**
     * 测试
     * 
     * @return 
     */
    public short boxTest(){
        return EsPkBoxDll.INSTANCE.ESBoxAPI_Test();
    }
    
    /**
     * 卸载所有票箱
     * 
     * @param cardBox
     * @param errInf
     * @return 
     */
    public short boxUnLoadAllCardBox(BoxInf[] cardBox, AlermInf.ByReference errInf){
        return EsPkBoxDll.INSTANCE.ESBoxAPI_UnLoadAllCardBox(cardBox, errInf);
    }
   
    /**
     * 卸载单个票箱 1-5号票箱
     * 
     * @param cardBoxNo
     * @param cardBox
     * @param errInf
     * @return 
     */
    public short boxUnLoadOneCardBox(short cardBoxNo, BoxInf[] cardBox, AlermInf.ByReference errInf){
        return EsPkBoxDll.INSTANCE.ESBoxAPI_UnLoadOneCardBox(cardBoxNo, cardBox, errInf);
    }
    
    /**
     * 取动态库版本号
     * 
     */
     //ESBoxAPI_GetDllVer(char *Version);
    
    /**
     * 取票箱dll版本号
     *
     * @param version
     * @return
     */
    public boolean boxGetDllVersion(byte []version){
        return EsPkBoxDll.INSTANCE.ESBoxAPI_GetDllVer(version);
    }
    //----------------------------------------------------票箱API--------------------------------------------------

}