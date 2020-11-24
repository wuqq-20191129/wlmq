/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.ecpmcs.util;

import com.goldsign.ecpmcs.env.AppConstant;
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
