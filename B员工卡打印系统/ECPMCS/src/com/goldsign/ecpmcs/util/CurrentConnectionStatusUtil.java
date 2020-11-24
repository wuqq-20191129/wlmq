/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.ecpmcs.util;

import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.ecpmcs.env.AppConstant;
import com.goldsign.ecpmcs.service.IRwDeviceService;
import com.goldsign.ecpmcs.vo.AnalyzeVo;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class CurrentConnectionStatusUtil {
    
    private static final Logger logger = Logger.getLogger(CurrentConnectionStatusUtil.class.getName());
      
    
    /**
     * 获取数据库连接状态
     *
     * @return
     */
    public static boolean isDatabaseConnected(){
        boolean flag = false;
        flag = AppConstant.dbcpHelper.checkDBConnect();
        if(flag){
            setDatabaseStatus(true);
        }
        return flag;
    }
    
    /**
     * 获取读写器连接状态
     * @return 
     */
    public static boolean isRwConnected(IRwDeviceService rwDeviceService) {
        boolean flag = false;
        CallResult callResult = new CallResult();
        com.goldsign.rwcommu.connection.RWSerialConnection.MES_PARAM_BEE = AppConstant.RW_BUZZING_TIME_0;
        callResult = rwDeviceService.esVersions();//获取设备版本号来检查读写器是否连通
        if(callResult.isSuccess()){
            flag = true;
            setReaderPortStatus(true);
            logger.info("读写器连接成功！"); 
        }else{
            logger.info("读写器未连接！");
            PubUtil.handleRWNotConnected(logger);
        }
        return flag;
    }
    /**
     * 获取读写器连接状态
     * @return 
     */
    public static boolean isCardOnRW(IRwDeviceService rwDeviceService) {
        boolean flag = false;
        CallResult callResult = new CallResult();
        //读写器已连通
        com.goldsign.rwcommu.connection.RWSerialConnection.MES_PARAM_BEE = AppConstant.RW_BUZZING_TIME_0;
        callResult = rwDeviceService.analyze();//分析卡信息
        if(callResult.isSuccess()){
            //判断是否满足写卡条件
            //未放卡
            AnalyzeVo analyzeVo = (AnalyzeVo) callResult.getObj();
            if(!StringUtil.isEmpty(analyzeVo.getcTicketType())){
                flag = true;
            }
        }
        
        
       if(flag){
            logger.info("读写器已放卡！"); 
        }else{
            logger.info("没有检测到任何票卡，请正确放置卡片！");
        }
        return flag;
    }
    
    /**
     * 设置读写器通讯状态 状态栏
     * @param KMSStatus 
     */
    public static void setReaderPortStatus(boolean readerPort) {
        String[] vars = new String[]{AppConstant.STATUS_BAR_READER_PORT};
        AppConstant.READER_PORT = readerPort;
        boolean[] statuses = new boolean[]{AppConstant.READER_PORT};
        //更新连接状态栏
        setSBarStatus(vars,statuses);
    }
    
    /**
     * 设置数据库状态 状态栏
     * @param DatabaseStatus 
     */
    public static void setDatabaseStatus(boolean databaseStatus) {
        String[] vars = new String[]{AppConstant.STATUS_BAR_BATABASE_STATUS};
        AppConstant.DATABASE_STATUS = databaseStatus;
        boolean[] statuses = new boolean[]{AppConstant.DATABASE_STATUS};
        //更新连接状态栏
        setSBarStatus(vars,statuses);
    }
    
    
    /**
     * 设置状态栏状态
     * 正常、警告
     * @param vars
     * @param statuses 
     */
    public static void setSBarStatus(String[] vars, boolean[] statuses) {
        int len = vars.length;
        for (int i = 0; i < len; i++) {
            if (statuses[i]) {
                BaseConstant.publicPanel.setOpLink(vars[i], BaseConstant.STATUS_BAR_STATUS_COLOR_ON);
            } else {
                BaseConstant.publicPanel.setOpLinkError(vars[i], BaseConstant.STATUS_BAR_STATUS_COLOR_OFF);
            }
        }
    }
}
