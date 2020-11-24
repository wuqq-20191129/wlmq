/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.util;

import com.goldsign.etmcs.env.AppConstant;
import com.goldsign.etmcs.env.ConfigConstant;
import java.util.Hashtable;
import org.apache.log4j.Logger;

/**
 * 配置文件工具类
 * 
 * @author lenovo
 */
public class ConfigUtil {

    private static final Logger logger = Logger.getLogger(ConfigUtil.class.getName());
    
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
        String twoLevelValue = (String) oneLevelValues.get(twoLevelTag);
        
        return twoLevelValue;
    }
  
}
