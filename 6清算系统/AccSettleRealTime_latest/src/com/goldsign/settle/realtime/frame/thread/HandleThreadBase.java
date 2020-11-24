/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.thread;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.handler.HandlerBase;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.vo.SynchronizedControl;
import com.goldsign.settle.realtime.frame.vo.ThreadAttrVo;
import java.util.Date;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class HandleThreadBase extends Thread {

    /**
     * 队列
     */
    protected int threadBufferCapacity = 10;
    protected int threadBufferIncrement = 10;
    protected Vector msgs = new Vector(threadBufferCapacity, threadBufferCapacity);
    protected Vector priorityMsgs = new Vector(threadBufferCapacity, threadBufferCapacity);
    /**
     * 同步控制
     */
    private final SynchronizedControl CONTROL_MSGS = new SynchronizedControl();
    private final SynchronizedControl CONTROL_MSGS_PRIORITY = new SynchronizedControl();
    /**
     * 状态
     */
    protected boolean isHandling = false;
    /**
     * 线程配置
     */
    protected ThreadAttrVo threadAttVo;
    /**
     * 处理的消息
     */
    protected MessageBase handlingMsg = null;
    protected MessageBase handlingPriorityMsg = null;
    private static Logger logger = Logger.getLogger(HandleThreadBase.class.getName());

    public HandleThreadBase() {
    }

    public HandleThreadBase(ThreadAttrVo threadAttVo) {
        this.threadAttVo = threadAttVo;
    }

    public Vector getMsgs() {
        synchronized (this.CONTROL_MSGS) {
            return this.msgs;
        }

    }

    public Vector getAllMsgs() {
        Vector allMsgs = new Vector();
        synchronized (this.CONTROL_MSGS) {
            synchronized (this.CONTROL_MSGS_PRIORITY) {
                allMsgs.addAll(this.priorityMsgs);
                this.priorityMsgs.clear();
                allMsgs.addAll(this.msgs);
                this.msgs.clear();
                return allMsgs;


            }

        }

    }

    public MessageBase getHandlingMsg() {
        return this.handlingMsg;
    }

    public boolean isHandlingMsg() {
        return this.isHandling;

    }

    public MessageBase getMsg() {
        MessageBase msg = null;

        synchronized (this.CONTROL_MSGS) {
            if (msgs.isEmpty()) {
                return null;
            }
            msg = (MessageBase) msgs.get(0);
            msgs.remove(0);

        }
        logger.info(this.getName() + ":普通队列剩余未处理消息 =" + priorityMsgs.size());


        return msg;

    }

    public void setMsg(MessageBase msg) {
        synchronized (this.CONTROL_MSGS) {
            msgs.add(msg);
        }
    }

    public MessageBase getPriorityMsg() {
        MessageBase msg = null;

        synchronized (this.CONTROL_MSGS_PRIORITY) {
            if (priorityMsgs.isEmpty()) {
                return null;
            }
            msg = (MessageBase) priorityMsgs.get(0);
            priorityMsgs.remove(0);

        }

// DateHelper.screenPrint(this.getName()+":left msgs ="+msgs.size());

        return msg;

    }

    protected MessageBase getHandingMsg() {
        MessageBase msg = null;
        synchronized (this.CONTROL_MSGS_PRIORITY) {
            if (!priorityMsgs.isEmpty()) {//优先级队列有未处理消息
                msg = (MessageBase) priorityMsgs.get(0);
                priorityMsgs.remove(0);
                logger.info(this.getName() + ":优先队列剩余未处理消息 =" + priorityMsgs.size());
                return msg;

            } else//优先级队列娥没有未处理消息
            {
                synchronized (this.CONTROL_MSGS) {
                    if (msgs.isEmpty()) {
                        return null;
                    } else {
                        msg = (MessageBase) msgs.get(0);
                        msgs.remove(0);
                        logger.info(this.getName() + ":普通队列剩余未处理消息 =" + msgs.size());

                    }


                }

            }
            return msg;

        }
    }

    public void setHandlingMsg(MessageBase msg) {
        //消息是否是优先级消息
        if (this.isPriorityMsg(msg)) {
            synchronized (this.CONTROL_MSGS_PRIORITY) {//消息加入优先级队列
                priorityMsgs.add(msg);
            }

        } else {
            synchronized (this.CONTROL_MSGS) {//消息加入普通级队列
                msgs.add(msg);
            }

        }

    }

    public boolean isPriorityMsg(MessageBase msg) {
        return false;

    }

    public void setPriorityMsg(MessageBase priorityMsg) {
        synchronized (this.CONTROL_MSGS_PRIORITY) {
            priorityMsgs.add(priorityMsg);
        }
    }

    public void run() {
        while (true) {
            try {

                handlingMsg = this.getHandingMsg();

                if (handlingMsg == null) {
                    // DateHelper.screenPrint(this.getName()+"没有需要处理的消息");
                    HandleThreadBase.sleep(this.threadAttVo.getThreadSleepTime());
                    continue;

                }
                this.isHandling = true;
                HandlerBase msgHandler = (HandlerBase) Class.forName(threadAttVo.getMsgHandleClass()).newInstance();
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
}
