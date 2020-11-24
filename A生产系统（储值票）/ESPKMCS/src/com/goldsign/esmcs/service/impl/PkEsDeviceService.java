/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.service.impl;

import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.dll.structure.CardInf;
import com.goldsign.esmcs.env.PKAppConstant;
import com.goldsign.esmcs.env.SynLockConstant;
import com.goldsign.esmcs.exception.PkEsJniException;
import com.goldsign.esmcs.jni.PkEsDeviceJni;
import com.goldsign.esmcs.service.IPkEsDeviceService;
import com.goldsign.esmcs.util.PKConverter;
import com.goldsign.esmcs.vo.EsBaseInfo;
import com.goldsign.esmcs.vo.EsPortParam;
import org.apache.log4j.Logger;

/**
 * 储值票ES设备服务类
 * 
 * @author lenovo
 */
public class PkEsDeviceService extends EsDeviceService implements IPkEsDeviceService{

    private static final Logger logger = Logger.getLogger(PkEsDeviceService.class.getName());
    
    private PkEsDeviceJni esDeviceJni; //储值票ES设备驱动封装类
    
    private EsBaseInfo esBaseInfo;
    
    private final static Object LOCK = new Object();//锁
    
    public EsBaseInfo getEsBaseInfo(){
        return this.esBaseInfo;
    }
    
    public PkEsDeviceService(){
        this.esDeviceJni = new PkEsDeviceJni();
        this.esBaseInfo = new EsBaseInfo();
        esBaseInfo.cardInfs = esDeviceJni.createCardInfArray(PKAppConstant.ES_CARD_SITE_NUM);
        esBaseInfo.boxInfs = esDeviceJni.createBoxInfoArray(PKAppConstant.ES_BOX_NUM);
        esBaseInfo.boxSensors = esDeviceJni.createBoxSensorArray(PKAppConstant.ES_BOX_NUM);
    }
    
    /**
     * 打开票箱端口
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    @Override
    public CallResult openBoxPort(CallParam callParam) throws PkEsJniException {
        CallResult callResult = new CallResult();
        
        short port = ((EsPortParam)callParam).getPort();
        boolean result = false;
        synchronized(LOCK){
            result = esDeviceJni.boxInitCom(port);
                   
        }
        if(!result){
            logger.error("初始化票箱失败");
            callResult.setMsg("初始化票箱失败");
            return callResult;
        }
        
        callResult.setObj(result);
        callResult.setResult(true);
        
        return callResult;
    }

    /**
     * 打长通道端口
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    @Override
    public CallResult openChannelPort(CallParam callParam) throws PkEsJniException {
        CallResult callResult = new CallResult();
        
        short port = ((EsPortParam)callParam).getPort();
        short result = 0 ;
        synchronized(LOCK){
            result = esDeviceJni.channelOpen(port);
        }
        if(result != 0){
            logger.error("打开通道端口失败");
            callResult.setMsg("打开通道端口失败");
            return callResult;
        }
        synchronized(LOCK){
            result = esDeviceJni.channelInit();
            if(result==-25){
                result = esDeviceJni.channelInit();
            }
        }
        
        if(result != 0){
            logger.error("初始化通道失败");
            esDeviceJni.channelClose();
            return callResult;
        }
        
        return resetChannel(callParam);
    }

    /**
     * 关闭票箱端口
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    @Override
    public CallResult closeBoxPort(CallParam callParam) throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        short result = 0 ;
        synchronized(LOCK){
            result = esDeviceJni.boxExitCom();
        }
        if(result == 0){
            callResult.setResult(true);
        }else{
            logger.error("关闭票箱端口失败");
            callResult.setMsg("关闭票箱端口失败");
        }
        
        callResult.setObj(result);
        
        return callResult;
    }

    /**
     * 关闭通道端口
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    @Override
    public CallResult closeChannelPort(CallParam callParam) throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        short result = 0;
        synchronized(LOCK){
            result = esDeviceJni.channelClose();
        }
        if(result == 0){
            callResult.setResult(true);
        }else{
            logger.error("关闭通道端口失败");
            callResult.setMsg("关闭通道端口失败");
        }
        callResult.setObj(result);
        
        return callResult;
    }

    /**
     * 复位单个票箱
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    @Override
    public CallResult resetOneBox(CallParam callParam) throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        
        int cardBoxNo = (Integer)callParam.getParam();
        short result = 0;
        synchronized(LOCK){
            result = esDeviceJni.boxResetOneCardBox((short)cardBoxNo, esBaseInfo.boxInfs, esBaseInfo.alermInf);
        }
        callResult.setObj(result);
        
        callResult.setObj(PKConverter.structureToVo(esBaseInfo.boxInfs));
            
        callResult.setObj(PKConverter.structureToVo(esBaseInfo.alermInf));
            
        if(result == 0){
            
            callResult.setResult(true);
        }else{
            logger.error("复位票箱"+cardBoxNo+"失败");
            callResult.setMsg("复位票箱"+cardBoxNo+"失败");
        }

        return callResult;
    }

    /**
     * 复位所有票箱
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    @Override
    public CallResult resetAllBox(CallParam callParam) throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        short result = 0;
        synchronized(LOCK){
            result = esDeviceJni.boxResetAllCardBox(esBaseInfo.boxInfs, esBaseInfo.alermInf);
        }
        callResult.setObj(result);
        
        callResult.setObj(PKConverter.structureToVo(esBaseInfo.boxInfs));
            
        callResult.setObj(PKConverter.structureToVo(esBaseInfo.alermInf));
            
        if(result == 0){

            callResult.setResult(true);
        }else{
            logger.error("复位所有票箱失败");
            callResult.setMsg("复位所有票箱失败");
        }
        
        return callResult;
    }

    /**
     * 卸载单个票箱
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    @Override
    public CallResult uploadOneBox(CallParam callParam) throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        
        int cardBoxNo = (Integer) callParam.getParam();
        short result = 0;
        synchronized(LOCK){
            result = esDeviceJni.boxUnLoadOneCardBox((short)cardBoxNo, esBaseInfo.boxInfs, esBaseInfo.alermInf);
        }
        callResult.setObj(result);

        callResult.setObj(PKConverter.structureToVo(esBaseInfo.boxInfs));

        callResult.setObj(PKConverter.structureToVo(esBaseInfo.alermInf));
            
        if(result == 0){
 
            callResult.setResult(true);
        }else{
            logger.error("拆卸票箱"+cardBoxNo+"失败");
            callResult.setMsg("拆卸票箱"+cardBoxNo+"失败");
        }
 
        return callResult;
    }

    /**
     * 卸载所有票箱
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    @Override
    public CallResult uploadAllBox(CallParam callParam) throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        short result = 0;
        synchronized(LOCK){
            result = esDeviceJni.boxUnLoadAllCardBox(esBaseInfo.boxInfs, esBaseInfo.alermInf);
        }
        callResult.setObj(result);
        
        callResult.setObj(PKConverter.structureToVo(esBaseInfo.boxInfs));
            
        callResult.setObj(PKConverter.structureToVo(esBaseInfo.alermInf));
            
        if(result == 0){
            
            callResult.setResult(true);
        }else{
            logger.error("拆卸所有票箱失败");
            callResult.setMsg("拆卸所有票箱失败");
        }
        
        return callResult;
    }

    /**
     * 取得票箱状态
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    @Override
    public CallResult getAllBoxState(CallParam callParam) throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        short result = 0;
        synchronized(LOCK){
            result = esDeviceJni.boxGetCardBoxState(esBaseInfo.boxInfs, esBaseInfo.alermInf);
        }
        callResult.setObj(result);
        
        callResult.setObj(PKConverter.structureToVo(esBaseInfo.boxInfs));

        callResult.setObj(PKConverter.structureToVo(esBaseInfo.alermInf));
        
        if(result == 0){        
            callResult.setResult(true);
        }else{
            logger.error("取票箱状态失败");
            callResult.setMsg("取票箱状态失败");
        }

        return callResult;
    }
    
     /**
     * 取得票箱传感器状态
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */   
    @Override
    public CallResult getBoxSensorState(CallParam callParam) throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        short result = 0;
        synchronized(LOCK){
            result = esDeviceJni.boxGetSensorState(esBaseInfo.boxSensors, esBaseInfo.alermInf);
        }
        callResult.setObj(result);
        
        callResult.setObj(PKConverter.structureToVo(esBaseInfo.boxSensors));

        callResult.setObj(PKConverter.structureToVo(esBaseInfo.alermInf));
        
        if(result == 0){        
            callResult.setResult(true);
        }else{
            logger.error("取票箱传感器状态失败");
            callResult.setMsg("取票箱传感器状态失败");
        }

        return callResult;
    }
    
    /**
     * 获取票箱DLL版本号
     *
     * @return
     */
    @Override
    public CallResult getBoxDllVersion() throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        byte[] Version = new byte[50];
        boolean result = false;
        synchronized(LOCK){
            result = esDeviceJni.boxGetDllVersion(Version);
        }
        callResult.setObj(new String(Version).trim());
        
        if(result){
            callResult.setResult(true);
        }else{
            logger.error("取票箱DLL版本失败");
            callResult.setMsg("取票箱DLL版本失败");
        }
        return callResult;
    }

    /**
     * 发卡
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    @Override
    public CallResult sendCard(CallParam callParam) throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        
        int cardNo = (Integer) callParam.getParam();
        short result = 0;
        synchronized(LOCK){
            synchronized(SynLockConstant.SYN_ES_CARD_SITE_LOCK){
                //logger.info("发卡前...");
                //printSites();
                result = esDeviceJni.channelSendCard(cardNo, esBaseInfo.cardInfs, esBaseInfo.boxIsCards);
                //logger.info("发卡后...");
                //printSites();
            }
        }
        callResult.setObj(result);
        callResult.setObj(PKConverter.structureToVo(esBaseInfo.cardInfs));
        callResult.setObj(esBaseInfo.boxIsCards);
         
        if(result == 0){

            callResult.setResult(true);
        }else{
            logger.error("发卡失败");
            callResult.setMsg("发卡失败");
        }

        return callResult;
    }

    /**
     * 打印当前卡位
     * 
     */
    private void printSites(){
        int i=1;
        for(CardInf cardInf: esBaseInfo.cardInfs){
            logger.info("i="+i+"，当前工位："+cardInf.CurrSite+"，目标工位"+cardInf.TagSite);
        }
    }
        
    /**
     * 走卡
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    @Override
    public CallResult moveCard(CallParam callParam) throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        
        short result = 0;
        synchronized(LOCK){
            synchronized(SynLockConstant.SYN_ES_CARD_SITE_LOCK){
                //logger.info("走卡前...");
                //printSites();
                result = esDeviceJni.channelMoveCard(esBaseInfo.cardInfs, esBaseInfo.boxIsCards);
                //logger.info("走卡后...");
                //printSites();
            }
        }
        callResult.setObj(result);
        
        callResult.setObj(PKConverter.structureToVo(esBaseInfo.cardInfs));

        callResult.setObj(esBaseInfo.boxIsCards);
            
        if(result == 0){

            callResult.setResult(true);
        }else{
            logger.error("走卡失败："+result);
            callResult.setMsg("走卡失败");
        }

        return callResult;
    }
    
    /**
     * 复位通道
     *
     * @param callParam
     * @return
     * @throws PkEsJniException
     */
    @Override
    public CallResult resetChannel(CallParam callParam) throws PkEsJniException {
        CallResult callResult = new CallResult();
        
        short result = 0;
        synchronized(LOCK){
            result = esDeviceJni.channelResetACC(esBaseInfo.cardInfs, esBaseInfo.boxIsCards);
        }
        if(result != 0){
            synchronized(LOCK){
                result = esDeviceJni.channelMADAToZero();
            }    
            if(result != 0){
                synchronized(LOCK){
                    esDeviceJni.channelClose();
                }
                return callResult;
            }
            synchronized(LOCK){
                result = esDeviceJni.channelResetACC(esBaseInfo.cardInfs, esBaseInfo.boxIsCards);
            }
            if(result != 0){
                synchronized(LOCK){
                    esDeviceJni.channelClose();
                }
                return callResult;
            }
        }
        
        callResult.setObj(result);
        callResult.setResult(true);
        
        return callResult;
    }

    /**
     * 取得跑卡错误信息
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    @Override
    public CallResult getCardRunError(CallParam callParam) throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        
        short result = 0;
        synchronized(LOCK){
            result = esDeviceJni.channelGetCardRunError(esBaseInfo.lineState);
        }
        callResult.setObj(result);
        
        callResult.setObj(PKConverter.structureToVo(esBaseInfo.lineState));
        
        if(result == 0){        
            callResult.setResult(true);
        }else{
            logger.error("取运行错误状态失败");
            callResult.setMsg("取运行错误状态失败");
        }

        return callResult;
    }
    
    /**
     * 获取通道dll版本号
     * @return 
     */
    @Override
    public CallResult getChanelDllVersion() throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        byte[] Version = new byte[30];
        short result = 0;
        synchronized(LOCK){
            result = esDeviceJni.channelGetDllVersion(Version);
        }
        callResult.setObj(new String(Version).trim());
        
        if(result == 0){
            callResult.setResult(true);
        }else{
            logger.error("取通道DLL版本失败");
            callResult.setMsg("取通道DLL版本失败");
        }
        return callResult;
    }
    
    /**
     * 获取设备版本号
     *
     * @return
     */
    @Override
    public CallResult getDevVersion() throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        byte[] Version = new byte[50];
        short result = 0;
        synchronized(LOCK){
            result = esDeviceJni.getDevVersion(Version);
        }
        callResult.setObj(new String(Version).trim());
        
        if(result == 0){
            callResult.setResult(true);
        }else{
            logger.error("取设备版本失败");
            callResult.setMsg("取设备版本失败");
        }
        return callResult;
    }

    /**
     * 卡机初始化
     * @return
     * @throws PkEsJniException 
     */
    @Override
    public CallResult channelInit() throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        short result = 0;
        synchronized(LOCK){
            result = esDeviceJni.channelInit();
        }
        if(result == 0){
            callResult.setResult(true);
        }else{
            logger.error("初始化通道失败");
            callResult.setMsg("初始化通道失败");
        }
        return callResult;
    }

    /**
     * 步进电机零位
     * 
     * @return 
     */
    @Override
    public CallResult channelModaToZero() throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        short result = 0;
        synchronized(LOCK){
            result = esDeviceJni.channelMADAToZero();
        }
        if(result == 0){
            callResult.setResult(true);
        }else{
            logger.error("通道对零位失败");
            callResult.setMsg("通道对零位失败");
        }
        return callResult;
    }

    /**
     * 通道传感器状态
     * @return
     * @throws PkEsJniException 
     */
    @Override
    public CallResult channelGetACCStatus() throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        byte[] StatusVal = new byte[9];
        short result = 0;
        synchronized(LOCK){
            result = esDeviceJni.channelGetACCStatus(StatusVal);
        }
        callResult.setObj(StatusVal);
        
        if(result == 0){
            callResult.setResult(true);
        }else{
            logger.error("取通道传感器状态失败");
            callResult.setMsg("取通道传感器状态失败");
        }
        return callResult;
    }

    /**
     * 暂停
     * @return
     * @throws PkEsJniException 
     */
    @Override
    public CallResult channelPause() throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        short result = 0;
        synchronized(LOCK){
            result = esDeviceJni.channelPause();
        }
        callResult.setObj(result);
        
        if(result == 0){
            callResult.setResult(true);
        }else{
            logger.error("通道暂停失败");
            callResult.setMsg("通道暂停失败");
        }
        return callResult;
    }

    /**
     * 继续
     * @return
     * @throws PkEsJniException 
     */
    @Override
    public CallResult channelContinue() throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        short result = 0;
        synchronized(LOCK){
            result = esDeviceJni.channelContinue();
        }
        callResult.setObj(result);
        
        if(result == 0){
            callResult.setResult(true);
        }else{
            logger.error("通道继续失败");
            callResult.setMsg("通道继续失败");
        }
        return callResult;
    }

    /**
     * 检测卡位信息
     * 
     * @param callParam
     * @return
     * @throws PkEsJniException 
     */
    @Override
    public CallResult channelGetSiteInf(CallParam callParam) throws PkEsJniException {
        
        CallResult callResult = new CallResult();
        
        short result = 0;
        synchronized(LOCK){
            synchronized(SynLockConstant.SYN_ES_CARD_SITE_LOCK){
                result = esDeviceJni.channelGetStationInf(esBaseInfo.cardInfs, esBaseInfo.boxIsCards);
            }
        }
        callResult.setObj(result);
        
        callResult.setObj(PKConverter.structureToVo(esBaseInfo.cardInfs));

        callResult.setObj(esBaseInfo.boxIsCards);
            
        if(result == 0){

            callResult.setResult(true);
        }else{
            logger.error("取工位信息失败");
            callResult.setMsg("取工位信息失败");
        }

        return callResult;
    }


}
