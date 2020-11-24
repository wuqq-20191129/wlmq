/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.timer;


import com.goldsign.settle.realtime.frame.util.TaskUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class Action01 extends ActionBase{
     private static Logger logger = Logger.getLogger(Action01.class.
            getName());

    @Override
    public void action() throws Exception {
        TaskUtil.setDownloadAuditFile(true);
        logger.info("审计文件下发标识设为：TRUE");
    }
    
}
