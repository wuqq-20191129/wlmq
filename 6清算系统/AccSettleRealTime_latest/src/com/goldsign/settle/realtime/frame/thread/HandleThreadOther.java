/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.thread;

import com.goldsign.settle.realtime.frame.handler.HandlerBase;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.vo.ThreadAttrVo;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class HandleThreadOther extends HandleThreadBase {

    private static Logger logger = Logger.getLogger(HandleThreadOther.class.getName());

    public HandleThreadOther() {
    }

    public HandleThreadOther(ThreadAttrVo threadAttVo) {
        this.threadAttVo = threadAttVo;
    }

    public void run() {
        String className;
        while (true) {
            try {

                handlingMsg = this.getHandingMsg();

                if (handlingMsg == null) {
                    // DateHelper.screenPrint(this.getName()+"没有需要处理的消息");
                    HandleThreadBase.sleep(this.threadAttVo.getThreadSleepTime());
                    continue;

                }
                this.isHandling = true;
                className = this.getHanldeClass(threadAttVo, handlingMsg);
                HandlerBase msgHandler = (HandlerBase) Class.forName(className).newInstance();
                msgHandler.handleMessage(handlingMsg);

                handlingMsg.setFinished(true);//设置消息处理完成标志
                if (handlingMsg.getTfc() != null) {
                    handlingMsg.getTfc().setFinished(true);////设置消息处理完成标志(外部控制）
                }

                this.isHandling = false;
                HandleThreadBase.sleep(this.threadAttVo.getThreadSleepTime());


            } catch (InterruptedException e) {
                logger.error("线程" + this.getName() + "被中断.........");

                break;

            } catch (Exception e) {
                // CommuThreadLogUtil.writeErrHandleMsgToFile(this.getName(), (Vector) handlingMsg.getReadResult());
                logger.error(e.getMessage() + "  " + this.getName() + ":消息缓存未处理消息为" + this.msgs.size());

            }
        }
    }

    private String getHanldeClass(ThreadAttrVo vo, MessageBase msg) {
        return vo.getMsgHandleClassPrifix() + msg.getFileType();
    }
}
