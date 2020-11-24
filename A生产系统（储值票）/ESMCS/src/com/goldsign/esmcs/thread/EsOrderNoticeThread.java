/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.thread;

import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.application.Application;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.SynLockConstant;
import com.goldsign.esmcs.exception.CommuException;
import com.goldsign.esmcs.exception.FileException;
import com.goldsign.esmcs.service.ICommuService;
import com.goldsign.esmcs.service.IFileService;
import com.goldsign.esmcs.vo.NoticeParam;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 订单通知线程
 *
 * @author lenovo
 */
public class EsOrderNoticeThread extends Thread{

    private static final Logger logger = Logger.getLogger(EsOrderNoticeThread.class.getName());
    
    private CallParam callParam;
    private IFileService fileService;
    private ICommuService commuService;
    private Application application;
    
    public EsOrderNoticeThread(CallParam callParam){
        this.callParam = callParam;
        this.application = (Application) AppConstant.application;
        this.fileService = application.getFileService();
        this.commuService = application.getCommuService();
    }
    
    /**
     * 运行
     *
     */
    @Override
    public void run() {

        while (true) {
            
            try {
                synchronized(SynLockConstant.SYN_NOTICE_MSG_FILE_LOCK){
                    if(AppConstant.COMMU_STATUS){
                        sendUnNoticeOrderMsg(callParam);
                    }  
                }
                this.threadSleep();
            } catch (Exception e) {
                logger.error(e);
            }
        }

    }

    /**
     * 发送滞留文件
     *
     * @param callParam
     * @throws CommuException
     */
    public CallResult sendUnNoticeOrderMsg(CallParam callParam)  {
        
        CallResult callResult = null;
        try {
            callResult = fileService.getUnNoticeOrderMsg(callParam);
        } catch (FileException ex) {
            logger.error("发送未通知订单-取文件异常:"+ex);
            return new CallResult("发送未通知订单-取文件异常，请联系管理员！");
        }
            
        List<String> unnoticeMsgs = callResult.getObjs();
        if(null == unnoticeMsgs || unnoticeMsgs.isEmpty()){
            return new CallResult("没有未通知订单.");
        }
        callParam.resetParam(unnoticeMsgs);
        try {
            callResult = commuService.uploadEsOrderFile(callParam);
            fileService.updateNoticeOrderMsg(callParam);
        } catch (Exception ex) {
            try {
                fileService.writeUnNoticeOrderMsgs(unnoticeMsgs);
            } catch (FileException ex1) {
                logger.error("发送未通知订单-本地备份异常："+ex1);
            }
            logger.error("发送未通知订单-通讯异常:"+ex);
            return new CallResult("发送未通知订单-通讯异常，请联系管理员！");
        }
        
        callResult.setResult(true);
        
        logger.info("发送未通知订单成功！");
        
        return callResult;
    }
    
    /**
     * 休息
     *
     */
    private void threadSleep() {

        try {
            sleep(AppConstant.ES_FILE_NOTICE_SLEEP_TIME);
        } catch (NumberFormatException e) {
            logger.error(e);
        } catch (InterruptedException e) {
            logger.error(e);
        }

    }
    
    /**limj
     * 发送漏取文件
     *
     * @param callParam
     * @throws CommuException
     */
    public CallResult sendUnGetOrderMsg(CallParam callParam)  {
        
        CallResult callResult = null;
        NoticeParam noticeParam = new NoticeParam();
         noticeParam.setNoticeType("0");
        noticeParam.setBeginDate(DateHelper.curDateToStr8yyyyMMdd());
        noticeParam.setEndDate(DateHelper.curDateToStr8yyyyMMdd());
        try {
            callResult = fileService.getEsNoticeFiles( noticeParam);
        } catch (FileException ex) {
            logger.error("发送未通知订单-取文件异常:"+ex);
            return new CallResult("发送未通知订单-取文件异常，请联系管理员！");
        }
            
        List<String> unnoticeMsgs = callResult.getObjs();
        if(null == unnoticeMsgs || unnoticeMsgs.isEmpty()){
            return new CallResult("没有未通知订单.");
        }
        callParam.resetParam(unnoticeMsgs);
        try {
            callResult = commuService.uploadEsOrderFile(callParam);
            fileService.updateNoticeOrderMsg(callParam);
        } catch (Exception ex) {
            try {
                fileService.writeUnNoticeOrderMsgs(unnoticeMsgs);
            } catch (FileException ex1) {
                logger.error("发送未通知订单-本地备份异常："+ex1);
            }
            logger.error("发送未通知订单-通讯异常:"+ex);
            return new CallResult("发送未通知订单-通讯异常，请联系管理员！");
        }
        
        callResult.setResult(true);
        
        logger.info("发送未通知订单成功！");
        
        return callResult;
    }
}
