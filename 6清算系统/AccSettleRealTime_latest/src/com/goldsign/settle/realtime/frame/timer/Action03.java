/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.timer;

import com.goldsign.settle.realtime.frame.constant.FrameFlowConstant;
import com.goldsign.settle.realtime.frame.util.TaskUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class Action03 extends ActionBase{
     private static Logger logger = Logger.getLogger(Action03.class.
            getName());

    @Override
    public void action() throws Exception {

        TaskUtil.setFinalSettleFlag(FrameFlowConstant.FINAL_SETTLE_YES);
        logger.info("最后一次实时清算标识设置为："+FrameFlowConstant.FINAL_SETTLE_YES);
    }
    
}
