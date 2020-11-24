/*
 * 文件名：ConfigUtil
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.fm.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;


/*
 * 配置文件公共类
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-10-22
 */

public class ConfigUtil {
    
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ConfigUtil.class.getName());
    
    public static HashMap getConfigProperties(String configFile) throws Exception{
        
        String fileName = AppConstant.appWorkDir+"/properties/"+configFile;
        FileInputStream fis = new FileInputStream(fileName);
        String line = null;
        int index = -1;
        String key = null;
        String value = null;
        HashMap properties = new HashMap();
        InputStreamReader isr = null;
        BufferedReader br = null;
        try{
            isr = new InputStreamReader(fis, "GBK");
            br = new BufferedReader(isr);
            while((line=br.readLine())!= null){
             if(line.startsWith("#")){
               continue;
             }
             index = line.indexOf("=");
             if(index == -1){
               continue;
             }
             key = line.substring(0,index);
             value = line.substring(index+1);
             value = value.trim();
             properties.put(key,value);
          }

        }catch(Exception e){
            logger.error(e);
        }
        finally{
            try{
                  if (fis != null){
                    fis.close();
                  }
                  if (isr != null);
                  isr.close();
                  if (br != null){
                    br.close();
                  }
            }catch(IOException e){
                  e.printStackTrace();
            }

        }
        logger.info("加载设备图片大小文件："+configFile);
        return properties;
      }

}
