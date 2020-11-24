/*
 * To change this template, choose Tools | Templates
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
public class Action02 extends ActionBase {

    private static Logger logger = Logger.getLogger(Action02.class.
            getName());

    @Override
    public void action() throws Exception {
       // TaskUtil.setFinishFileHandled(true);
        //logger.info("交易文件、收益文件等处理完成标识设为：TRUE");
       // TaskUtil.setFinalSettleFlag(FrameFlowConstant.FINAL_SETTLE_YES);
        //logger.info("最后一次实时清算标识设置为："+FrameFlowConstant.FINAL_SETTLE_YES);
    }
}
