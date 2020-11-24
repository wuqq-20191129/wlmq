/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.csfrm.test;

import com.goldsign.csfrm.service.impl.ConfigFileService;
import org.apache.log4j.Logger;

/**
 *
 * @author lenovo
 */
public class TestLogger {

        public static void main(String[] args){
        ConfigFileService cf = new ConfigFileService();
        String fileName = "D:\\project\\csMetro\\程序开发\\ES票卡生产系统\\代码\\ESPKMCS\\config\\Log4jConfig.xml";
        cf.loadLogFile(fileName);
       
        Logger logger = Logger.getLogger(ConfigFileService.class.getName());
        logger.info("test你好吗？");
    }
}
