/*
 * 文件名：RwDeviceService
 * 版权：Copyright: goldsign (c) 2013
 * 描述：RW读写器RwDeviceService服务实例实现类
 */

package com.goldsign.esmcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.csfrm.util.CharUtil;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.exception.CommuException;
import com.goldsign.esmcs.service.ICommuService;
import com.goldsign.esmcs.service.IKmsService;
import com.goldsign.esmcs.vo.KmsCardVo;
import com.goldsign.esmcs.vo.KmsCfgParam;
import com.goldsign.kmscommu.jni.CardKeyGetJni;
import com.goldsign.kmscommu.vo.CardKeyResult;
import com.goldsign.kmscommu.vo.TokenKeyResult;
import org.apache.log4j.Logger;


public class KmsService extends BaseService implements IKmsService{
    
    private static final Logger logger = Logger.getLogger(KmsService.class.getName());
    
    private static KmsService kmsService;
    private CardKeyGetJni cardKeyGetJni;
    private KmsCfgParam kmsCfgParam;
    private static final Object LOCK = new Object();//锁
    private ICommuService commuService;
    
    private KmsService(){
        cardKeyGetJni = new CardKeyGetJni();
        commuService = CommuService.getInstance();
    }
    
    public static KmsService getInstance(){
        
        if(null == kmsService){
            kmsService = new KmsService();
        }
        
        return kmsService;
    }

    /**
     * 授权
     * 
     * @param param
     * @return 
     */
    @Override
    public CallResult author(KmsCfgParam param) {
        
        if(param.getLock() && !setKmsLock()){
            return new CallResult("取密钥失败，原因：获取锁KMS失败！");
        }
    
        //保留参数
        this.kmsCfgParam = param;
        
        CallResult callResult = new CallResult();

        byte[] kmIp = param.getKmsIpBs();
        byte[] kmPort = param.getKmsPortBs();
        byte[] pin = param.getKmsPinBs();
        byte[] keyVer = param.getKeyVerBs();
        
        CardKeyResult cardKeyResult = null;
        synchronized(LOCK){
            cardKeyResult = cardKeyGetJni.author(kmIp, kmPort, pin, keyVer);
        }
        String code = new String(cardKeyResult.getCode());
        if(AppConstant.KMS_SUCCESS_CODE.equals(code)){
          
            callResult.setResult(true);
        }else{
            callResult.resetMsg("加密机认证失败，错误码："+code+".");
        }
        callResult.setCode(code);
        
        if(param.getLock() && !setKmsUnlock()){
            logger.error("解锁KMS失败！");
        }
        
        return callResult;
    }

    /**
     * 取储值票密钥
     * 
     * @param cardNo
     * @return 
     */
    @Override
    public CallResult getCardKey(String cardNo) {
        
        /*
        if(!setKmsLock()){
            return new CallResult("取密钥失败，原因：获取锁KMS失败！");
        }*/
        
        CallResult callResult = new CallResult();     
        byte[] cardNoArr = cardNo.getBytes();
        
        CardKeyResult cardKeyResult = null;
        long s = System.currentTimeMillis();
        synchronized(LOCK){
            cardKeyResult = cardKeyGetJni.getCardKey(cardNoArr);
        }
        long e = System.currentTimeMillis();
        logger.info("取密钥时间："+(e-s)+"ms");
        String code = new String(cardKeyResult.getCode());
        if(AppConstant.KMS_SUCCESS_CODE.equals(code)){
            
            KmsCardVo kmsCardVo = getKmsCardVo(cardKeyResult.getMsg());
            
            callResult.setObj(kmsCardVo);
            callResult.setResult(true);
        }else{
            callResult.resetMsg("取密钥失败，错误码："+code+".");
        }
        callResult.setCode(code);
        
        /*
        if(!setKmsUnlock()){
            logger.error("解锁KMS失败！");
        }*/
        
        return callResult;
    }

    /**
     * 封装返回密钥
     * 
     * @param bytes
     * @return 
     */
    private KmsCardVo getKmsCardVo(byte[] bytes){
    
        KmsCardVo kmsCardVo = new KmsCardVo();
        kmsCardVo.setMfCardTranKey(AppConstant.MfCardTranKey);
        kmsCardVo.setCardMainKey(CharUtil.getByteCharString(bytes, 0, 32));
        kmsCardVo.setCardMendKey(CharUtil.getByteCharString(bytes, 32, 32));
        kmsCardVo.setOutAuthenMKey(CharUtil.getByteCharString(bytes, 64, 32));
        kmsCardVo.setAppMainKey(CharUtil.getByteCharString(bytes, 96, 32));
        kmsCardVo.setAppMendKey(CharUtil.getByteCharString(bytes, 128, 32));
        kmsCardVo.setConsumeKey(CharUtil.getByteCharString(bytes, 160, 32));
        kmsCardVo.setTransferInKey(CharUtil.getByteCharString(bytes, 192, 32));
        kmsCardVo.setTranAuthenTacKey(CharUtil.getByteCharString(bytes, 224, 32));
        kmsCardVo.setAppMendKey01(CharUtil.getByteCharString(bytes, 256, 32));
        kmsCardVo.setAppMendKey02(CharUtil.getByteCharString(bytes, 288, 32));
        kmsCardVo.setAppLockKey(CharUtil.getByteCharString(bytes, 320, 32));
        kmsCardVo.setAppUnlockKey(CharUtil.getByteCharString(bytes, 352, 32));
        kmsCardVo.setPinUnlockKey(CharUtil.getByteCharString(bytes, 384, 32));
        kmsCardVo.setPinResetKey(CharUtil.getByteCharString(bytes, 416, 32));
        kmsCardVo.setModifyOverdrawKey(CharUtil.getByteCharString(bytes, 448, 32));
        kmsCardVo.setTransferOutKey(CharUtil.getByteCharString(bytes, 480, 32));
        kmsCardVo.setOutAuthenDKey(CharUtil.getByteCharString(bytes, 512, 32));
        
        return kmsCardVo;
    }

    /**
     * 取单程票密钥
     * 
     * @param phyNo
     * @param logicNo
     * @return 
     */
    @Override
    public CallResult getTokenKey(String phyNo, String logicNo) {
        
        /*
        if(!setKmsLock()){
            return new CallResult("取密钥失败，原因：获取锁KMS失败！");
        }
        */     
        
        CallResult callResult = new CallResult();
        
        byte[] phyNoArr = phyNo.getBytes();
        byte[] logicNoArr = logicNo.getBytes();
        
        TokenKeyResult tokenKeyResult = null;
        synchronized(LOCK){
            tokenKeyResult = cardKeyGetJni.getTokenKey(phyNoArr, logicNoArr);
        }
        String code = new String(tokenKeyResult.getCode());
        if(AppConstant.KMS_SUCCESS_CODE.equals(code)){
            
            KmsCardVo kmsCardVo = getKmsTokenVo(tokenKeyResult.getMac(), tokenKeyResult.getKey());
            
            callResult.setObj(kmsCardVo);
            callResult.setResult(true);
        }else{
            callResult.resetMsg("取密钥失败，错误码："+code+".");
        }
        callResult.setCode(code);
        
        /*
        if(!setKmsUnlock()){
            logger.error("解锁KMS失败！");
        }
        * 
        */
        
        return callResult;
    }
    
    /**
     * 封装返回密钥
     * 
     * @param macBytes
     * @param keyBytes
     * @return 
     */
    private KmsCardVo getKmsTokenVo(byte[] macBytes, byte[] keyBytes) {
    
        KmsCardVo kmsCardVo = new KmsCardVo();
        String cardMainKey = new String(macBytes)+new String(keyBytes); 
        kmsCardVo.setCardMainKey(StringUtil.addZeroAfter(cardMainKey, 32));
        
        return kmsCardVo;
    }

    /**
     * 重新认证
     * 
     * @return 
     */
    @Override
    public CallResult reauthor() {
     
        return author(kmsCfgParam);
    }

    /**
     * 设置加密机锁
     * 
     * @return 
     */
    @Override
    public boolean setKmsLock() {

        CallParam callParam = new CallParam();
        callParam.setParam("0");
        try {
            for(int i=0;i<5;i++){
                CallResult callResult = commuService.setKmsLock(callParam);
                if(callResult.isSuccess()){
                    String result = (String) callResult.getObj();
                    if("0".equals(result)){
                        logger.info("设置加密机锁成功！");
                        return true;
                    }
                }
                logger.warn("设置加密机锁"+(i+1)+"次失败，原因："+callResult.getMsg());
                Thread.sleep(10);
            }
            return false;
        } catch (Exception ex) {
            logger.error("设置加密机锁异常，原因："+ex.getMessage());
            return false;
        }
    }

     /**
     * 解加密机锁
     * 
     * @return 
     */
    @Override
    public boolean setKmsUnlock() {
        
        CallParam callParam = new CallParam();
        callParam.setParam("1");
        try {
            CallResult callResult = commuService.setKmsLock(callParam);
            if(callResult.isSuccess()){
                String result = (String) callResult.getObj();
                if("0".equals(result)){
                    return true;
                }
            }
            logger.warn("设置加密机锁失败，原因："+callResult.getMsg());
            return false;
        } catch (CommuException ex) {
            logger.error("设置加密机锁异常，原因："+ex.getMessage());
            return false;
        }
    }
}
