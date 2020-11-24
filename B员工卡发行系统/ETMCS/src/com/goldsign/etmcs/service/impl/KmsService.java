/*
 * 文件名：RwDeviceService
 * 版权：Copyright: goldsign (c) 2013
 * 描述：RW读写器RwDeviceService服务实例实现类
 */

package com.goldsign.etmcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.csfrm.util.CharUtil;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.etmcs.env.AppConstant;
import com.goldsign.etmcs.exception.CommuException;
import com.goldsign.etmcs.service.ICommuService;
import com.goldsign.etmcs.service.IKmsService;
import com.goldsign.etmcs.vo.KmsCardVo;
import com.goldsign.etmcs.vo.KmsCfgParam;
import com.goldsign.kmscommu.jni.CardKeyGetJni;
import com.goldsign.kmscommu.vo.CardKeyResult;
import org.apache.log4j.Logger;


public class KmsService extends BaseService implements IKmsService{
    
    private static final Logger logger = Logger.getLogger(KmsService.class.getName());
    
    private static KmsService kmsService;
    private CardKeyGetJni cardKeyGetJni;
    private ICommuService commuService;
    private static final Object LOCK = new Object();//锁
    
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

    @Override
    public CallResult author(KmsCfgParam param) {
        
//        if(!setKmsLock()){
//            return new CallResult("取密钥失败，原因：获取锁KMS失败！");
//        }
        
        CallResult callResult = new CallResult();
        
        byte[] kmIp = param.getKmsIpBs();
        byte[] kmPort = param.getKmsPortBs();
        byte[] pin = param.getKmsPinBs();
        byte[] kmVersion = param.getKmsVersionBs();
        System.out.println("kmIp[]:"+kmIp);
        System.out.println("kmPort[]:"+kmPort);
        CardKeyResult cardKeyResult = null;
        synchronized(LOCK){
            cardKeyResult = cardKeyGetJni.author(kmIp, kmPort, pin, kmVersion);
        }
        if(AppConstant.KMS_SUCCESS_CODE.equals(new String(cardKeyResult.getCode()))){
            callResult.setResult(true);
        }
        callResult.setCode(new String(cardKeyResult.getCode()));
        
//        if(!setKmsUnlock()){
//            logger.error("解锁KMS失败！");
//        }
        
        return callResult;
    }
    
    public static CallResult authorWithoutLock(KmsCfgParam param) {
        
        CallResult callResult = new CallResult();
        
        byte[] kmIp = param.getKmsIpBs();
        byte[] kmPort = param.getKmsPortBs();
        byte[] pin = param.getKmsPinBs();
        byte[] kmVersion = param.getKmsVersionBs();
        
        CardKeyResult cardKeyResult = null;
        CardKeyGetJni cardKeyGetJni = new CardKeyGetJni();
        cardKeyResult = cardKeyGetJni.author(kmIp, kmPort, pin, kmVersion);
        if(AppConstant.KMS_SUCCESS_CODE.equals(new String(cardKeyResult.getCode()))){
            callResult.setResult(true);
        }
        callResult.setCode(new String(cardKeyResult.getCode()));
        
        return callResult;
    }

    @Override
    public CallResult getCardKey(String cardNo) {
        
//        if(!setKmsLock()){
//            return new CallResult("取密钥失败，原因：获取锁KMS失败！");
//        }
        
        CallResult callResult = new CallResult();
        
        byte[] cardNoArr = cardNo.getBytes();
        
        CardKeyResult cardKeyResult = null;
        synchronized(LOCK){
                cardKeyResult = cardKeyGetJni.getCardKey(cardNoArr);
        }
        if(AppConstant.KMS_SUCCESS_CODE.equals(new String(cardKeyResult.getCode()))){
            
            KmsCardVo kmsCardVo = getKmsCardVo(cardKeyResult.getMsg());
            
            callResult.setObj(kmsCardVo);
            callResult.setResult(true);
        }
        callResult.setCode(new String(cardKeyResult.getCode()));
        
//        if(!setKmsUnlock()){
//            logger.error("解锁KMS失败！");
//        }
        
        return callResult;
    }

    private KmsCardVo getKmsCardVo(byte[] bytes){
    
        KmsCardVo kmsCardVo = new KmsCardVo();
        kmsCardVo.setMfCardTranKey("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
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
            System.out.println("设置加密机锁异常，原因："+ex.getMessage());
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

    @Override
    public CallResult getConnectStatusKey(KmsCfgParam param) {
//        if(!setKmsLock()){
//            return new CallResult("取密钥失败，原因：获取锁KMS失败！");
//        }
        
        CallResult callResult = new CallResult();
        
        byte[] kmIp = param.getKmsIpBs();
        byte[] kmPort = param.getKmsPortBs();
        
        CardKeyResult cardKeyResult = null;
        //synchronized(LOCK){
            cardKeyResult = cardKeyGetJni.getConnectStatusKey(kmIp, kmPort);
        //}
        if(AppConstant.KMS_SUCCESS_CODE.equals(new String(cardKeyResult.getCode()))){
            callResult.setResult(true);
        }
        callResult.setCode(new String(cardKeyResult.getCode()));
        
//        if(!setKmsUnlock()){
//            logger.error("解锁KMS失败！");
//        }
        
        return callResult;
    }

}