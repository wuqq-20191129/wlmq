/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.thread;

import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.application.Application;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.SynLockConstant;
import com.goldsign.esmcs.service.ICommuService;
import org.apache.log4j.Logger;

/**
 * 通讯心跳监控线程
 * 
 * @author lenovo
 */
public class EsCommuMonitorThread extends Thread{
    
    private static final Logger logger = Logger.getLogger(EsCommuMonitorThread.class.getName());
   
    private ICommuService commuService;
   
    public EsCommuMonitorThread(ICommuService commuService){
        this.commuService = commuService;
    }
    
    /**
     * 运行
     * 
     */
    @Override
    public void run(){
        
        while(true){    
            try {
                boolean oldCommuStatus = AppConstant.COMMU_STATUS;
                synchronized(SynLockConstant.SYN_WIN_EXIT_LOCK){
                    Application app = (Application)AppConstant.application;
                    if(app.sysAppVo.getAppStatus().equals(AppConstant.SYS_APP_CUR_STATUS_EXIT)){
                        break;
                    }
                    //是否断开通讯
                    boolean isCommuOk = commuService.isCommuOk();
                    if(!isCommuOk){
                        logger.info("重新连接ES通讯...");
                        CallResult callResult = commuService.openCommu(null);//尝试连接通讯
                        AppConstant.COMMU_STATUS = callResult.isSuccess();
                        if(callResult.isSuccess()){
                            logger.info("成功重新连接ES通讯！");
                            commuService.downAuditAndErrorFile(null);//下载审计和错误文件
                        }
                    }
                }
                
                if (AppConstant.COMMU_STATUS) {
                    if(AppConstant.COMMU_STATUS != oldCommuStatus){
                        BaseConstant.publicPanel.setOpLink(AppConstant.STATUS_BAR_COMMU_STATUS, 
                            BaseConstant.STATUS_BAR_STATUS_COLOR_ON);
                    }
                } else {
                    if(AppConstant.COMMU_STATUS != oldCommuStatus){
                        BaseConstant.publicPanel.setOpLinkWarn(AppConstant.STATUS_BAR_COMMU_STATUS, 
                                BaseConstant.STATUS_BAR_STATUS_COLOR_OFF);
                    }
                }
                
                //睡眠
                this.sleep(AppConstant.ES_COMMU_MONITOR_SLEEP_TIME);
                
            } catch (Exception e) {
                logger.error("ES监控线程异常：" + e);
            }
        }
    }
}
