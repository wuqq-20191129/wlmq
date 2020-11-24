/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.etmcs.commu.EsCommuClient;
import com.goldsign.etmcs.env.AppConstant;
import com.goldsign.etmcs.env.CommuConstant;
import com.goldsign.etmcs.env.ConfigConstant;
import com.goldsign.etmcs.exception.CommuException;
import com.goldsign.etmcs.service.ICommuService;
import com.goldsign.etmcs.vo.EsCommuReadResult;
import com.goldsign.etmcs.vo.EsCommuWriteParam;
import java.util.Hashtable;
import org.apache.log4j.Logger;


/**
 * 通讯服务类
 * 
 * @author lenovo
 */
public class CommuService extends BaseService implements ICommuService{

    private static final Logger logger = Logger.getLogger(CommuService.class.getName());
    
    //通讯锁
    public static final Object COMMU_LOCK = new Object();
    
    private static CommuService commuService;
    
    private CommuService(){}
    
    public static CommuService getInstance(){
        
        if(null == commuService){
            commuService = new CommuService();
        }
        
        return commuService;
    }
    
    /**
     * 打开SOCKET通讯端口
     * 
     * @param callParam 
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult openCommu(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        
        Hashtable esCommus = (Hashtable) AppConstant.configs.get(ConfigConstant.EsCommuTag);
        String IP = (String) esCommus.get(ConfigConstant.EsCommuServerIpTag);
        Integer PORT = StringUtil.getInt((String)esCommus.get(ConfigConstant.EsCommuServerPortTag));      

        try {
            synchronized(COMMU_LOCK){
                EsCommuClient.openConServer(IP, PORT);
            }
            callResult.setResult(true);
            logger.info("SOCKET通讯端口成功！");
        } catch (Exception ex) {
            callResult.setResult(false);
            ex.printStackTrace();
            //throw new CommuException(ex);
            logger.info("SOCKET通讯端口失败！");
        } 

        return callResult;
    }

    /**
     * 关闭通讯
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult closeCommu(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        
        try {
            synchronized (COMMU_LOCK) {
                EsCommuClient.disConServer();
            }
            callResult.setResult(true);
            
            logger.info("ES通讯关闭，成功！");
        } catch (Exception ex) {
            callResult.setResult(false);
            //throw new CommuException(ex);
            
            logger.info("ES通讯关闭，异常！");
        }

        return callResult;
    }


    /**
     * 是否通讯正常
     * 
     * @return
     * @throws CommuException 
     */
    @Override
    public boolean isCommuOk() throws CommuException {
        try {
            synchronized(COMMU_LOCK){
                boolean result = EsCommuClient.isConnected();
                logger.info("ES通讯测试连接，结果："+result);
                return result;
            }
        }catch(Exception e){
            throw new CommuException(e);
        }
    }

    /**
     * 设置加密机锁
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    @Override
    public CallResult setKmsLock(CallParam callParam) throws CommuException {
        
        CallResult callResult = new CallResult();
        String isLock = (String) callParam.getParam();
       
        EsCommuWriteParam writeParam = new EsCommuWriteParam();
        writeParam.addStringToMessage(CommuConstant.MSG_KMS_LOCK, 2);
        writeParam.addStringToMessage(isLock, 1);
        EsCommuReadResult readResult = null;
        try {
            synchronized(COMMU_LOCK){
                EsCommuClient.sendData(writeParam);
                readResult = EsCommuClient.readData();
            }
            if (!readResult.isSuccess()) {
                callResult.setMsg("通讯（锁加密机）异常，错误码：" + readResult.getCode() + ".");
                return callResult;
            }
        } catch (Exception ex) {
            throw new CommuException(ex);
        }
        String result = readResult.getCharString(2, 1);
        
        callResult.setObj(result);
        callResult.setResult(readResult.isSuccess());
        
        logger.info("锁加密机,结果："+readResult.isSuccess());
        return callResult;
    }
}
