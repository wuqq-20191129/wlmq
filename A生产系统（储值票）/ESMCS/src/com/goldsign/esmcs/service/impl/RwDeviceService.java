/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.csfrm.util.CharUtil;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.exception.RwJniException;
import com.goldsign.esmcs.serial.RwDeviceCommu;
import com.goldsign.esmcs.service.IRwDeviceService;
import com.goldsign.esmcs.util.Validator;
import com.goldsign.esmcs.vo.*;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 * RW设备服务类
 * 
 * @author lenovo
 */
public class RwDeviceService extends BaseService implements IRwDeviceService{

    private static final Logger logger = Logger.getLogger(RwDeviceService.class.getName());
    
    private RwDeviceCommu rwDeviceCommu;
    
    private final Object LOCK = new Object();//锁
    
    public RwDeviceService(){
        this.rwDeviceCommu = new RwDeviceCommu();
    }
     
    /**
     * 打开读头端口
     * 
     * @param callParam
     * @return
     * @throws RwJniException 
     */
    @Override
    public CallResult openRwPort(CallParam callParam) throws RwJniException {
        
        CallResult callResult = new CallResult();
        
        RwPortParam rwPortParam = (RwPortParam)callParam;
        
        String nPort = rwPortParam.getPort();
        String stationId = rwPortParam.getStationId();
        String deviceType = rwPortParam.getDeviceType();
        String deviceId = rwPortParam.getDeviceId();
        
        try{
            ByteBuffer bytes = ByteBuffer.allocate(5);
            
            bytes.put(CharUtil.bcdStringToByteArray(stationId));
            bytes.put(CharUtil.bcdStringToByteArray(deviceType));
            bytes.put(CharUtil.bcdStringToByteArray(deviceId));    
        
            //打开端口
            synchronized(LOCK){
                rwDeviceCommu.open(nPort);
            }
            //设备初始化
            byte[] byteRets = null;
            synchronized(LOCK){
                byteRets = rwDeviceCommu.esInitDevice(bytes.array());
            }
            String retCode = CharUtil.byteToIntStr(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);

            if (AppConstant.RW_SUCCESS_CODE.equals(retCode)) {
                callResult.setResult(true);
                callResult.resetMsg("打开读头成功！");
                logger.info("打开读头成功！");
            } else {
                callResult.resetMsg("打开读头失败，错误码："+retCode+","+relCode+".");
                logger.warn("打开读头失败，错误码："+retCode+","+relCode+".");
            }
            callResult.setCode(retCode+","+relCode);

        } catch (Exception ex) {
            callResult.resetMsg("打开读头异常："+ex.getMessage());
            logger.error("打开读头异常："+ex);
            //throw new RwJniException("打开读头异常",ex);
            callResult.setResult(false);
        }

        return callResult;
    }

     /**
     * 关闭读头端口
     * 
     * @param callParam
     * @return
     * @throws RwJniException 
     */
    @Override
    public CallResult closeRwPort(CallParam callParam) throws RwJniException {
        
        CallResult callResult = new CallResult();

        synchronized(LOCK){
            rwDeviceCommu.close();
        }
        callResult.setResult(true);
        callResult.resetMsg("关闭读写器串口成功！");
        logger.info("关闭读写器串口成功！");

        return callResult;
    }
    
    /**
     * 写卡
     * 
     * @param callParam
     * @return
     * @throws RwJniException 
     */
    @Override
    public CallResult writeCard(CallParam callParam) throws RwJniException {
        
        CallResult callResult = new CallResult();
        
        WriteCardParam writeCardParam = (WriteCardParam)callParam;
        String workType = writeCardParam.getWorkType();
        OrderInVo order = writeCardParam.getOrderInVo();
        logger.info("订单："+order);
        AnalyzeVo analyzeVo = (AnalyzeVo) writeCardParam.getParam(0);
        logger.info("分析："+analyzeVo);
        KmsCardVo kmsCardVo = (KmsCardVo) writeCardParam.getParam(1);
        logger.debug("密钥："+kmsCardVo);
        SignCardParam signCardParam = writeCardParam.getSignCardParam();
        logger.debug("记名卡："+signCardParam);
        
        //写卡
        if(AppConstant.WORK_TYPE_INITI.equals(workType)){
            final int WRITE_CARD_NUM = 1;
            for(int i=1; i<=WRITE_CARD_NUM; i++){
                callResult = initEvaluate(order, analyzeVo, kmsCardVo);
                logger.info(i+"次，写卡-初始化.");
                if(callResult.isSuccess() && isNeedSignCard(order)){
                    callResult = signCard(signCardParam, analyzeVo, kmsCardVo);
                    logger.info(i+"次，写卡-初始化-记名卡.");
                }
                if(callResult.isSuccess()){
                    logger.info(i+"次，写卡-初始化--成功！.");
                    break;
                }
                sleepTime(500);//休息一下
// limingjin               if(Validator.contain(AppConstant.CARD_TYPE_TOKEN, order.getTicketType())){
//                    continue;
//                }
                if(i<WRITE_CARD_NUM){
                    CallResult clearCallResult = clear(order, analyzeVo, kmsCardVo);
                    if(!clearCallResult.isSuccess()){
                        logger.error(i+"次，写卡-初始化--洗卡--失败！");
                        break;
                    }
                    logger.info(i+"次，写卡-初始化--洗卡--成功.");
                }
            }
        }else if(AppConstant.WORK_TYPE_HUNCH.equals(workType)){
            callResult = evaluate(order, analyzeVo, kmsCardVo);
            logger.info("写卡-预赋值.");
        }else if(AppConstant.WORK_TYPE_AGAIN.equals(workType)){
            callResult = recode(order, analyzeVo, kmsCardVo);
            logger.info("写卡-重编码.");
            if (callResult.isSuccess() && isNeedSignCard(order)) {
                callResult = signCard(signCardParam, analyzeVo, kmsCardVo);
                logger.info("写卡-重编码-记名卡.");
            }
        }else if(AppConstant.WORK_TYPE_LOGOUT.equals(workType)){
            callResult = destroy(order, analyzeVo, kmsCardVo);
            logger.info("写卡-注销.");
        }else if(AppConstant.WORK_TYPE_CLEAR.equals(workType)){
            callResult = clear(order, analyzeVo, kmsCardVo);
            logger.info("写卡-洗卡.");
        }

        return callResult;
    }
    
    /**
     * 休息time毫秒
     * 
     * @param time 
     */
    private void sleepTime(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
    }
    
    /**
     * 是否是记名卡
     * 注：目前，只有申请号非空就认为是记名卡，以前可能会修改
     * 
     * @param order
     * @return 
     */
    private boolean isNeedSignCard(OrderInVo order){

        if(!StringUtil.isEmpty(order.getApplicationNO())){
            return true;
        }
        return false;
    }
    
    /**
     * 初始化
     * 
     * @param pInput
     * @param pOutput
     * @return 
     */
    private CallResult initEvaluate(OrderInVo order, AnalyzeVo analyzeVo, KmsCardVo kmsCardVo) 
            throws RwJniException{
    
        CallResult callResult = new CallResult();
        byte[] byteRets = null;

        try {
            
            ByteBuffer bytes = ByteBuffer.allocate(78+16*19);

            String orderNo = order.getOrderNo();
            bytes.put(CharUtil.strToLenByteArr(orderNo, 14));
            String reqNo = order.getApplicationNO();
            bytes.put(CharUtil.strToLenByteArr(reqNo, 10));
            byte[] ticketType = CharUtil.hexStrToLenByteArr(order.getTicketType(), 2);
            bytes.put(ticketType);
            byte[] logicNo = CharUtil.hexStrToLenByteArr(order.getLogicalID(), 8);
            bytes.put(logicNo);
            byte deposite = CharUtil.strToByte(order.getDepositeYuan());
            bytes.put(deposite);
            int balance = CharUtil.strToInt(order.getValue());
            bytes.put(CharUtil.to4Byte(balance));
            byte[] topValue = CharUtil.to2Byte(CharUtil.strToInt(order.getRechargeTopValueYuan()));
            bytes.put(topValue);
            byte active = CharUtil.strToByte(order.getSaleActiveFlag());
            bytes.put(active);
            byte[] senderCode = CharUtil.bcdStrToLenByteArr(order.getSenderCode(),2);
            bytes.put(senderCode);
            byte[] cityCode = CharUtil.bcdStrToLenByteArr(order.getCityCode(),2);
            bytes.put(cityCode);
            byte[] busiCode = CharUtil.bcdStrToLenByteArr(order.getBusiCode(),2);
            bytes.put(busiCode);
            byte testFlag = CharUtil.strToByte(order.getTestFlag());
            bytes.put(testFlag);
            byte[] issueDate = CharUtil.bcdStrToLenByteArr(order.getIssueDate(),4);
            bytes.put(issueDate);
            byte[] cardVersion = CharUtil.bcdStrToLenByteArr(order.getCardVersion(),2);
            bytes.put(cardVersion);
            byte[] cStartExpire = CharUtil.bcdStrToLenByteArr(order.getcStartExpire(),4);
            bytes.put(cStartExpire);
            byte[] cEndExpire = CharUtil.bcdStrToLenByteArr(order.getcEndExpire(),4);
            bytes.put(cEndExpire);
            //-----逻辑日期
            byte[] logicDate = CharUtil.bcdStrToLenByteArr(order.getLogicDate(),7);
            bytes.put(logicDate);
            byte[] logicDays = CharUtil.to2Byte(CharUtil.strToInt(order.getLogicDays()));
            bytes.put(logicDays);
            //-----
            byte appVersion = CharUtil.strToByte(order.getAppVersion());
            bytes.put(appVersion);
            byte exitEntryMode = CharUtil.strToByte(order.getExitEntryMode());
            bytes.put(exitEntryMode);
            byte[] entryLineStation = CharUtil.bcdStrToLenByteArr(order.getEntryLineStation(),2);
            bytes.put(entryLineStation);
            byte[] exitLineStation =  CharUtil.bcdStrToLenByteArr(order.getExitLineStation(),2);
            bytes.put(exitLineStation);
            if(kmsCardVo.getCardMainKey().trim().equals("")||kmsCardVo.getCardMainKey().trim()==null){
                throw new Exception();
            }
            //加入密钥
            addKmsByByte(bytes,kmsCardVo);
            
            synchronized(LOCK){
                byteRets = rwDeviceCommu.esInit(bytes.array());
               // sleepTime(2000);                
                
            }
            String retCode = CharUtil.byteToIntStr(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);
            
            if(AppConstant.RW_SUCCESS_CODE.equals(retCode)){
                callResult.setResult(true);
                callResult.resetMsg("卡初始化成功！");
                logger.info("卡初始化成功！");
            }else{
                callResult.resetMsg("卡初始化失败，错误码："+retCode+","+relCode+".");
                logger.warn("卡初始化失败，错误码："+retCode+","+relCode+".");
            }
            callResult.setCode(retCode+","+relCode);
        } catch (Exception ex) {
            callResult.resetMsg("卡初始化异常:"+ex.getMessage());
            logger.error("卡初始化异常:"+ex);
            throw new RwJniException("卡初始化异常", ex);
        }

        return callResult;
    }
    
    /**
     * 洗卡
     * 
     * @param kmsCardVo
     * @return 
     */
    public CallResult clear(OrderInVo order, AnalyzeVo analyzeVo, KmsCardVo kmsCardVo) 
            throws RwJniException{
    
        CallResult callResult = new CallResult();
        byte[] byteRets = null;

        try {  
            ByteBuffer bytes = ByteBuffer.allocate(78+16*19);
            byte[] orderByte = new byte[78];
            byte[] logicNo = CharUtil.hexStrToLenByteArr(order.getLogicalID(), 8);
            System.arraycopy(logicNo, 0, orderByte, 26, logicNo.length);
            bytes.put(orderByte);
            
            //加入密钥
            addKmsByByte(bytes,kmsCardVo);
            
            synchronized(LOCK){
                byteRets = rwDeviceCommu.esClear(bytes.array());
            }
            String retCode = CharUtil.byteToIntStr(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);
            
            if(AppConstant.RW_SUCCESS_CODE.equals(retCode)){
                callResult.setResult(true);
                callResult.resetMsg("清洗成功！");
                logger.info("清洗成功！");
            }else{
                callResult.resetMsg("清洗失败，错误码："+retCode+","+relCode+".");
                logger.warn("清洗失败，错误码："+retCode+","+relCode+".");
            }
            callResult.setCode(retCode+","+relCode);
        } catch (Exception ex) {
            callResult.resetMsg("清洗异常："+ex.getMessage());
            logger.error("清洗异常："+ex);
            throw new RwJniException("清洗异常", ex);
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
    private CallResult evaluate(OrderInVo order, AnalyzeVo analyzeVo, KmsCardVo kmsCardVo) 
            throws RwJniException {

        CallResult callResult = new CallResult();
        byte[] byteRets = null;

        try {
            ByteBuffer bytes = ByteBuffer.allocate(56+16*6);
            
            String orderNo = order.getOrderNo();
            bytes.put(CharUtil.strToLenByteArr(orderNo, 14));
            String reqNo = order.getApplicationNO();
            bytes.put(CharUtil.strToLenByteArr(reqNo, 10));
            byte[] ticketType = CharUtil.hexStrToLenByteArr(order.getTicketType(), 2);
            bytes.put(ticketType);
            byte[] logicNo = CharUtil.hexStrToLenByteArr(order.getLogicalID(), 8);
            bytes.put(logicNo);
            byte deposite = CharUtil.strToByte(order.getDepositeYuan());
            bytes.put(deposite);
            int balance = CharUtil.strToInt(order.getValue());
            bytes.put(CharUtil.to4Byte(balance));
            byte[] topValue = CharUtil.to2Byte(CharUtil.strToInt(order.getRechargeTopValueYuan()));
            bytes.put(topValue);
            //---
            byte active = CharUtil.strToByte(order.getSaleActiveFlag());
            bytes.put(active);
            byte[] logicDate = CharUtil.bcdStrToLenByteArr(order.getLogicDate(),7);
            bytes.put(logicDate);
            byte[] logicDays = CharUtil.to2Byte(CharUtil.strToInt(order.getLogicDays()));
            bytes.put(logicDays);
            //---
            byte exitEntryMode = CharUtil.strToByte(order.getExitEntryMode());
            bytes.put(exitEntryMode);
            byte[] entryLineStation = CharUtil.bcdStrToLenByteArr(order.getEntryLineStation(),2);;
            bytes.put(entryLineStation);
            byte[] exitLineStation =  CharUtil.bcdStrToLenByteArr(order.getExitLineStation(),2);
            bytes.put(exitLineStation);
            
            //加入密钥
            bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getTransferInKey(),16));
            bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getConsumeKey(),16));
            bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getTranAuthenTacKey(),16));
            bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppMendKey(),16));
            bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppMendKey01(),16));
            bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppMendKey02(),16));
            synchronized(LOCK){
                byteRets = rwDeviceCommu.esEvaluate(bytes.array());                
                //sleepTime(2000);
            }
            String retCode = CharUtil.byteToIntStr(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);
            
            if(AppConstant.RW_SUCCESS_CODE.equals(retCode)){
                callResult.setResult(true);
                callResult.resetMsg("卡预赋值成功！");
                logger.info("卡预赋值成功！");
            }else{
                callResult.resetMsg("卡预赋值失败，错误码："+retCode+","+relCode+".");
                logger.warn("卡预赋值失败，错误码："+retCode+","+relCode+".");
            }
            callResult.setCode(retCode+","+relCode);
        } catch (Exception ex) {
            callResult.resetMsg("卡预赋值异常："+ex.getMessage());
            logger.error("卡预赋值异常："+ex);
            throw new RwJniException("卡预赋值异常", ex);
        }

        return callResult;
    }
        
    /**
     * 重编码
     * 
     * @param pInput
     * @param pOutput
     * @return 
     */
    private CallResult recode(OrderInVo order, AnalyzeVo analyzeVo, KmsCardVo kmsCardVo) 
            throws RwJniException {

        CallResult callResult = new CallResult();
        byte[] byteRets = null;

        try {
            ByteBuffer bytes = ByteBuffer.allocate(78+16*19);
            
            String orderNo = order.getOrderNo();
            bytes.put(CharUtil.strToLenByteArr(orderNo, 14));
            String reqNo = order.getApplicationNO();
            bytes.put(CharUtil.strToLenByteArr(reqNo, 10));
            byte[] ticketType = CharUtil.hexStrToLenByteArr(order.getTicketType(), 2);
            bytes.put(ticketType);
            byte[] logicNo = CharUtil.hexStrToLenByteArr(order.getLogicalID(), 8);
            bytes.put(logicNo);
            byte deposite = CharUtil.strToByte(order.getDepositeYuan());
            bytes.put(deposite);
            int balance = CharUtil.strToInt(order.getValue());
            bytes.put(CharUtil.to4Byte(balance));
            byte[] topValue = CharUtil.to2Byte(CharUtil.strToInt(order.getRechargeTopValueYuan()));
            bytes.put(topValue);
            byte active = CharUtil.strToByte(order.getSaleActiveFlag());
            bytes.put(active);
            byte[] senderCode = CharUtil.bcdStrToLenByteArr(order.getSenderCode(),2);
            bytes.put(senderCode);
            byte[] cityCode = CharUtil.bcdStrToLenByteArr(order.getCityCode(),2);
            bytes.put(cityCode);
            byte[] busiCode = CharUtil.bcdStrToLenByteArr(order.getBusiCode(),2);
            bytes.put(busiCode);
            byte testFlag = CharUtil.strToByte(order.getTestFlag());
            bytes.put(testFlag);
            byte[] issueDate = CharUtil.bcdStrToLenByteArr(order.getIssueDate(),4);
            bytes.put(issueDate);
            byte[] cardVersion = CharUtil.bcdStrToLenByteArr(order.getCardVersion(),2);
            bytes.put(cardVersion);
            byte[] cStartExpire = CharUtil.bcdStrToLenByteArr(order.getcStartExpire(),4);
            bytes.put(cStartExpire);
            byte[] cEndExpire = CharUtil.bcdStrToLenByteArr(order.getcEndExpire(),4);
            bytes.put(cEndExpire);
            //---
            byte[] logicDate = CharUtil.bcdStrToLenByteArr(order.getLogicDate(),7);
            bytes.put(logicDate);
            byte[] logicDays = CharUtil.to2Byte(CharUtil.strToInt(order.getLogicDays()));
            bytes.put(logicDays);
            //---
            byte appVersion = CharUtil.strToByte(order.getAppVersion());
            bytes.put(appVersion);
            byte exitEntryMode = CharUtil.strToByte(order.getExitEntryMode());
            bytes.put(exitEntryMode);
            byte[] entryLineStation = CharUtil.bcdStrToLenByteArr(order.getEntryLineStation(),2);;
            bytes.put(entryLineStation);
            byte[] exitLineStation =  CharUtil.bcdStrToLenByteArr(order.getExitLineStation(),2);
            bytes.put(exitLineStation);
            if(kmsCardVo.getCardMainKey().trim().equals("")||kmsCardVo.getCardMainKey().trim()==null){
                throw new Exception();
            }
            //加入密钥
            addKmsByByte(bytes,kmsCardVo);
            synchronized(LOCK){
                byteRets = rwDeviceCommu.esRecode(bytes.array());
               // sleepTime(2000);
            }
            String retCode = CharUtil.byteToIntStr(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);
            
            if(AppConstant.RW_SUCCESS_CODE.equals(retCode)){
                callResult.setResult(true);
                callResult.resetMsg("卡重编码成功！");
                logger.info("卡重编码成功！");
            }else{
                callResult.resetMsg("卡重编码失败，错误码："+retCode+","+relCode+".");
                logger.warn("卡重编码失败，错误码："+retCode+","+relCode+".");
                
            }
            callResult.setCode(retCode+","+relCode);
        } catch (Exception ex) {
            callResult.resetMsg("卡重编码异常："+ex.getMessage());
            logger.error("卡重编码异常："+ex);
            
            throw new RwJniException("卡重编码异常", ex);
        }

        return callResult;
    }
        
    /**
     * 销毁
     * 
     * @param pInput
     * @param pOutput
     * @return 
     */ 
    private CallResult destroy(OrderInVo order, AnalyzeVo analyzeVo, KmsCardVo kmsCardVo) 
            throws RwJniException {

        CallResult callResult = new CallResult();
        byte[] byteRets = null;

        try {
            ByteBuffer bytes = ByteBuffer.allocate(39+16*5);
            
            String orderNo = order.getOrderNo();
            bytes.put(CharUtil.strToLenByteArr(orderNo, 14));
            String reqNo = order.getApplicationNO();
            bytes.put(CharUtil.strToLenByteArr(reqNo, 10));
            byte[] ticketType = CharUtil.hexStrToLenByteArr(order.getTicketType(), 2);
            bytes.put(ticketType);
            byte[] logicNo = CharUtil.hexStrToLenByteArr(order.getLogicalID(), 8);
            bytes.put(logicNo);
            byte deposite = CharUtil.strToByte(order.getDepositeYuan());
            bytes.put(deposite);
            int balance = CharUtil.strToInt(order.getValue());
            bytes.putInt(balance); 
            
            //加入密钥
            bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getConsumeKey(),16));
            bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getTranAuthenTacKey(),16));
            bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppMendKey(),16));
            bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppMendKey01(),16));
            bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppMendKey02(),16));
            synchronized(LOCK){
                byteRets = rwDeviceCommu.esDestroy(bytes.array());
               // sleepTime(2000);
            }
            String retCode = CharUtil.byteToIntStr(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);
            
            if(AppConstant.RW_SUCCESS_CODE.equals(retCode)){
                callResult.setResult(true);
                callResult.resetMsg("卡注销成功！");
                logger.info("卡注销成功！");
            }else{
                callResult.resetMsg("卡注销失败，错误码："+retCode+","+relCode+".");
                logger.warn("卡注销失败，错误码："+retCode+","+relCode+".");
            }
            callResult.setCode(retCode+","+relCode);
        } catch (Exception ex) {
            callResult.resetMsg("卡注销异常："+ex.getMessage());
            logger.error("卡注销异常："+ex);
            
            throw new RwJniException("卡注销异常", ex);
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
    private CallResult signCard(SignCardParam param, AnalyzeVo analyzeVo, KmsCardVo kmsCardVo) 
            throws RwJniException {
        
        CallResult callResult = new CallResult();
        byte[] byteRets = null;
        
        try {

            ByteBuffer bytes = ByteBuffer.allocate(16+164);
            
            //加入密钥
            bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppMendKey02(),16));
            
            bytes.put(AppConstant.SIGN_CARD_HOLDER_TYPE_PERSON);
            bytes.put(AppConstant.SIGN_CARD_CARD_TYPE_COM);
            String employeeName = StringUtil.addEmptyAfterUTF(param.getEmployeeName(), 128);
            bytes.put(CharUtil.utfStrToBytes(employeeName));
            String employeeNo = StringUtil.addEmptyAfter(param.getEmployeeNo(), 32);
            bytes.put(CharUtil.gbkStrToBytes(employeeNo));
            String idType = param.getIdType();
            bytes.put(CharUtil.bcdStringToByteArray(idType));
            String sex = param.getSex();
            bytes.put(CharUtil.bcdStringToByteArray(sex));
            synchronized(LOCK){
                byteRets = rwDeviceCommu.esSignCard(bytes.array());
            }
            String retCode = CharUtil.byteToIntStr(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);
            if (AppConstant.RW_SUCCESS_CODE.equals(retCode)) {
                callResult.setResult(true);
                callResult.resetMsg("写记名卡成功！");
                logger.info("写记名卡成功！");
            } else {
                callResult.resetMsg("写记名卡失败，错误码："+retCode+","+relCode+".");
                logger.warn("写记名卡失败，错误码："+retCode+","+relCode+".");
            }
            callResult.setCode(retCode+","+relCode);
        } catch (Exception ex) {
            callResult.resetMsg("写记名卡异常："+ex.getMessage());
            logger.error("写记名卡异常："+ex);
            
            throw new RwJniException("写记名卡异常", ex);
        }
        
        return callResult;
    }
        
    /**
     * 读卡
     *
     * @param callParam
     * @return
     * @throws RwJniException
     */
    @Override
    public CallResult readCard(CallParam callParam) throws RwJniException{
        
        CallResult callResult = new CallResult();
        byte[] byteRets = null;

        try {
            synchronized(LOCK){
                byteRets = rwDeviceCommu.esAnalyze();
            }
            logger.debug("分析返回数据："+byteRets);
            if(null == byteRets || byteRets.length<3){
                logger.error("票卡分析失败！");
                callResult.resetMsg("票卡分析失败！");
                String dataStr = null;
                if(null != byteRets){
                    dataStr = CharUtil.byteToHex(byteRets);
                }
                logger.error("返回数据(error):"+dataStr);
                return callResult; 
            }
            String retCode = CharUtil.byteToIntStr(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);

            AnalyzeVo analyzeVo = getAnalyzeVo(byteRets);
            logger.info("票卡分析:"+analyzeVo);
            callResult.setObj(analyzeVo);
                
            if (AppConstant.RW_SUCCESS_CODE.equals(retCode)) {
                callResult.setResult(true);
                callResult.resetMsg("票卡分析成功！");
                logger.info("票卡分析成功！");
            } else {
                callResult.resetMsg("票卡分析失败，错误码："+retCode+","+relCode+".");
                logger.warn("票卡分析失败，错误码："+retCode+","+relCode+".");
            }
            callResult.setCode(retCode+","+relCode);
        } catch (Exception ex) {
            callResult.resetMsg("票卡分析异常："+ex.getMessage());
            logger.error("票卡分析异常："+ex);
            throw new RwJniException("票卡分析异常", ex);
        }

        return callResult;
    }

    /**
     * 取票卡分析VO
     * 
     * @param bytesRet
     * @return 
     */
    private AnalyzeVo getAnalyzeVo(byte[] bytesRet){
    
        AnalyzeVo analyzeVo = new AnalyzeVo();
        
        String bIssueStatus = CharUtil.getByteString(bytesRet, 7, 1);
        analyzeVo.setbIssueStatus(bIssueStatus);
        String bStatus = CharUtil.getByteString(bytesRet, 8, 1);
        analyzeVo.setbStatus(bStatus);
        String cTicketType = CharUtil.getByteCharString(bytesRet, 9, 4);
        analyzeVo.setcTicketType(cTicketType);
        String cLogicalID = CharUtil.getByteCharString(bytesRet, 13, 20);
        cLogicalID = cLogicalID.trim();
        analyzeVo.setcLogicalID(cLogicalID);
        String cPhysicalID = CharUtil.getByteCharString(bytesRet, 33, 20);
        analyzeVo.setcPhysicalID(cPhysicalID);
        String bCharacter = CharUtil.getByteString(bytesRet, 53, 1);
        analyzeVo.setbCharacter(bCharacter);
        String cIssueDate = CharUtil.bytesToAsciiStringNoSpace(CharUtil.getByteArr(bytesRet, 54, 14));
        analyzeVo.setcIssueDate(cIssueDate);
         String cStartExpire = CharUtil.bytesToAsciiStringNoSpace(CharUtil.getByteArr(bytesRet, 68, 8));
        analyzeVo.setcStartExpire(cStartExpire);
        String cEndExpire = CharUtil.bytesToAsciiStringNoSpace(CharUtil.getByteArr(bytesRet, 76, 8));
        analyzeVo.setcEndExpire(cEndExpire);
        String rfu = CharUtil.getByteCharString(bytesRet, 84, 16);
        analyzeVo.setRfu(rfu);
        long lBalance = CharUtil.byteToLong(CharUtil.getByteArr(bytesRet, 100, 4));
        analyzeVo.setlBalance(lBalance);
        long lDeposite = CharUtil.byteToLong(CharUtil.getByteArr(bytesRet, 104, 4));
        analyzeVo.setlDeposite(lDeposite);
        String cLine = CharUtil.bytesToAsciiStringNoSpace(CharUtil.getByteArr(bytesRet, 108, 2));
        analyzeVo.setcLine(cLine);
        String cStationNo = CharUtil.bytesToAsciiStringNoSpace(CharUtil.getByteArr(bytesRet, 110, 2));
        analyzeVo.setcStationNo(cStationNo);
        String cDateStart = CharUtil.bytesToAsciiStringNoSpace(CharUtil.getByteArr(bytesRet, 112, 8));
        analyzeVo.setcDateStart(cDateStart);
        String cDateEnd = CharUtil.bytesToAsciiStringNoSpace(CharUtil.getByteArr(bytesRet, 120, 8));
        analyzeVo.setcDateEnd(cDateEnd);
        //String dtDaliyActive = CharUtil.getByteString(bytesRet, 120, 7);
        String dtDaliyActive = CharUtil.bytesToHexStringNoSpace(CharUtil.getByteArr(bytesRet, 128, 7));
        analyzeVo.setDtDaliyActive(dtDaliyActive);
        //String bEffectDay = CharUtil.getByteString(bytesRet, 127, 1);
        String bEffectDay = CharUtil.byteToIntStr(CharUtil.getByteArr(bytesRet, 135, 2));
        analyzeVo.setbEffectDay(bEffectDay);
        String cLimitEntryLine = CharUtil.getByteCharString(bytesRet, 137, 2);
        analyzeVo.setcLimitEntryLine(cLimitEntryLine);
        String cLimitEntryStation = CharUtil.getByteCharString(bytesRet, 139, 2);
        analyzeVo.setcLimitEntryStation(cLimitEntryStation);
        String cLimitExitLine = CharUtil.getByteCharString(bytesRet, 141, 2);
        analyzeVo.setcLimitExitLine(cLimitExitLine);
        String cLimitExitStation = CharUtil.getByteCharString(bytesRet, 143, 2);
        analyzeVo.setcLimitExitStation(cLimitExitStation);
        String cLimitMode = CharUtil.getByteCharString(bytesRet, 145, 3);
        analyzeVo.setcLimitMode(cLimitMode);
        
        String certificateIscompany = CharUtil.getByteString(bytesRet, 148, 1);
        analyzeVo.setCertificateIscompany(certificateIscompany);
        String certificateIsmetro = CharUtil.getByteString(bytesRet, 149, 1);
        analyzeVo.setCertificateIsmetro(certificateIsmetro);
        String certificateName = CharUtil.bytesToUtfStr(CharUtil.getByteArr(bytesRet, 150, 128));
        analyzeVo.setCertificateName(certificateName);
        String certificateCode =  CharUtil.bytesToGbkStr(CharUtil.getByteArr(bytesRet, 278, 32));
        analyzeVo.setCertificateCode(certificateCode);
        String certificateType = CharUtil.getByteString(bytesRet, 310, 1);
        analyzeVo.setCertificateType(certificateType);
        String certificateSex = CharUtil.getByteString(bytesRet, 311, 1);
        analyzeVo.setCertificateSex(certificateSex);
        
        int tradeCount = CharUtil.byteToInt(CharUtil.getByteArr(bytesRet, 312, 2));
        analyzeVo.setTradeCount(tradeCount);
        
        //hwj add 20160107 增加卡商代码
        String cardProducerCode = CharUtil.getByteCharString(bytesRet, 314, 4);
        //String cardProducerCode = "0000";//暂屏蔽
         if(cardProducerCode.trim().equals("")){
            cardProducerCode = "0000";
         }
        analyzeVo.setCardProducerCode(cardProducerCode);
        
        return analyzeVo;
    }

    /**
     * 读写器版本
     * 
     * @return 
     */
    @Override
    public CallResult getVersions(CallParam callParam) throws RwJniException{
        
        CallResult callResult = new CallResult();
        byte[] byteRets = null;
        
        try {
            synchronized(LOCK){
                byteRets = rwDeviceCommu.esVersions(byteRets);
            }
            String retCode = CharUtil.byteToIntStr(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);
            
            if(AppConstant.RW_SUCCESS_CODE.equals(retCode)){
                
                RwVersionVo rwVersionVo = getRwVersionVo(byteRets);
                callResult.setObj(rwVersionVo);
                callResult.setResult(true);
                callResult.resetMsg("获取读卡器版本信息成功！");
                logger.info("获取读卡器版本信息成功！");
            }else{
                callResult.resetMsg("获取读卡器版本信息失败，错误码："+retCode+","+relCode+".");
                logger.warn("获取读卡器版本信息失败，错误码："+retCode+","+relCode+".");
            }
            callResult.setCode(retCode+","+relCode);
        } catch (Exception ex) {
            callResult.resetMsg("获取读卡器版本信息异常："+ex.getMessage());
            logger.error("获取读卡器版本信息异常："+ex);
            
            throw new RwJniException("获取读卡器版本信息异常", ex);
        }

        return callResult;
    }

    //转换版本信息为实体类 "000001901445 32197 2212 32197 611 32197 6100"
    //00 00 00 00 00 13 00 90 05 20 13 07 16 01 02 20 13 07 06 01 01 20 13 07 06 01 00 00 
    private RwVersionVo getRwVersionVo(byte[] byteRet) {
        RwVersionVo rwVersionVo = new RwVersionVo();

//        String resultStr = Converter.bytesToHexString(byteRet).replaceAll(" ", "");
        String resultStr = CharUtil.byteToHex(byteRet).replaceAll(",", "");
                
        String verApi = resultStr.substring(0, 18);//CharUtil.getByteString(byteRet, 0, 2);
        rwVersionVo.setVerApi(verApi);//API识别版本
        String verApiFile = resultStr.substring(18, 28);//CharUtil.getByteString(byteRet, 2, 10);
        rwVersionVo.setVerApiFile(verApiFile);// API文件版本，日期＋序号
        String verRfDev = resultStr.substring(28, 30);//CharUtil.getByteString(byteRet, 12, 2);
        rwVersionVo.setVerRfDev(verRfDev);// Rf驱动识别版本
        String verRfFile = resultStr.substring(30, 40);//CharUtil.getByteString(byteRet, 14, 10);
        rwVersionVo.setVerRfFile(verRfFile);// Rf文件版本，日期＋序号
        String erSamDev = resultStr.substring(40, 42);//CharUtil.getByteString(byteRet, 26, 2);
        rwVersionVo.setVerSamDev(erSamDev);// Sam驱动识别版本
        String erSamFile = resultStr.substring(42, 52);//CharUtil.getByteString(byteRet, 28, 10);
        rwVersionVo.setVerSamFile(erSamFile);// Sam驱动文件版本，日期＋序号

        return rwVersionVo;
    }
    
    /**
     * 取SAM卡号
     *
     * @return
     */
    @Override
    public CallResult getSamCard(CallParam callParam) throws RwJniException{
        
        CallResult callResult = new CallResult();
        byte[] byteRets = null;
        
        try {
            synchronized(LOCK){
                byteRets = rwDeviceCommu.esSamCard(byteRets);
            }
            String retCode = CharUtil.byteToIntStr(byteRets, 0, 2);
            String relCode = CharUtil.getByteString(byteRets, 2, 1);
            if(AppConstant.RW_SUCCESS_CODE.equals(retCode)){
                
                SamCardVo samCardVo = new SamCardVo();
                //String samStatus = CharUtil.intToHex(byteRets[7]);
                String samNo = CharUtil.getByteCharString(byteRets, 8, 16);
                //samCardVo.setSamStatus(samStatus);
                samCardVo.setSamNo(samNo);
                callResult.setObj(samCardVo);
                callResult.setResult(true);
                callResult.resetMsg("获取SAM卡信息成功！");
                logger.info("获取SAM卡信息成功！");
            }else{
                callResult.resetMsg("获取SAM卡信息失败，错误码："+retCode+","+relCode+".");
                logger.warn("获取SAM卡信息失败，错误码："+retCode+","+relCode+".");
            }
            callResult.setCode(retCode+","+relCode);
        } catch (Exception ex) {
            callResult.resetMsg("获取SAM卡信息异常："+ex.getMessage());
            logger.error("获取SAM卡信息异常："+ex);
            
            throw new RwJniException("获取SAM卡信息异常", ex);
        }

        return callResult;
    }

    /**
     * 洗卡实现方法
     * 
     * @param kmsCardVo
     * @return
     * @throws RwJniException 
     */
    @Override
    public CallResult clearCard(KmsCardVo kmsCardVo) throws RwJniException {
        return clear(null,null,kmsCardVo);
    }

    //添加密钥
    private void addKmsByByte(ByteBuffer bytes,KmsCardVo kmsCardVo) {
        
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getMfCardTranKey(),16));     
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getCardMainKey(),16));
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getCardMendKey(),16));
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getOutAuthenMKey(),16));
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppTranKey(),16));
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppMainKey(),16));
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppMendKey(),16));
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getPinUnlockKey(),16));
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getPinResetKey(),16));
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getConsumeKey(),16));
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getTransferInKey(),16));
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getTranAuthenTacKey(),16));
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getTransferOutKey(),16));
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getModifyOverdrawKey(),16));
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getOutAuthenDKey(),16));
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppLockKey(),16));
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppUnlockKey(),16));
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppMendKey01(),16));
        bytes.put(CharUtil.hexStrToLenByteArr(kmsCardVo.getAppMendKey02(),16));
    }
}
