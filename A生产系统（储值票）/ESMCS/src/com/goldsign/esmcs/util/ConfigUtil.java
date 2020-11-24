/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.util;

import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.ConfigConstant;
import com.goldsign.esmcs.vo.FtpLoginParamVo;
import java.util.Hashtable;

/**
 * 配置文件工具类
 * 
 * @author lenovo
 */
public class ConfigUtil {

    /**
     * 取得站点ID
     *
     * @return
     */
    public static String getStationId(){
    
        return getConfigValue(ConfigConstant.CommonTag, ConfigConstant.CommonStationIdTag);
    }
    
    /**
     * 取得设备号
     *
     * @return
     */
    public static String getDeviceNo(){
    
        return getConfigValue(ConfigConstant.CommonTag, ConfigConstant.CommonDeviceNoTag);
    }

    /**
     * 取得设备类型
     *
     * @return
     */
    public static String getDeviceType(){
        
        return getConfigValue(ConfigConstant.CommonTag, ConfigConstant.CommonDeviceTypeTag);
    }
    
    /**
     * 取得设备类型+设备号
     *
     * @return
     */
    public static String getDeviceTypeAndNo(){
    
        return getDeviceType()+getDeviceNo();
    }    
    
    /**
     * 取得配置文件的值
     * 
     * @param oneLevelTag
     * @param twoLevelTag
     * @return 
     */
    public static String getConfigValue(String oneLevelTag, String twoLevelTag){
        
        Hashtable oneLevelValues = (Hashtable) AppConstant.configs.get(oneLevelTag);
        if(null == oneLevelTag){
            return null;
        }
        String twoLevelValue = (String) oneLevelValues.get(twoLevelTag);
        
        return twoLevelValue;
    }
    
    /**
     * 取得FTP登录参数
     *
     * @return
     */
    public static FtpLoginParamVo getFtpLoginParam(){
        
        String ip = getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpServerIpTag);
        int port = Integer.valueOf(getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpServerPortTag));//limj
    
        String userCode = getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpUserNameTag);
    
        String pwd = getConfigValue(ConfigConstant.DownloadTag, ConfigConstant.FtpUserPasswordTag);
        
        return new FtpLoginParamVo(ip, userCode, pwd,port);
    }
        
}
