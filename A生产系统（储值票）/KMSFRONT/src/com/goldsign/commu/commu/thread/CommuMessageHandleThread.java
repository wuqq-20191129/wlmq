package com.goldsign.commu.commu.thread;

import com.goldsign.commu.commu.env.BaseConstant;
import com.goldsign.commu.commu.message.MessageProcessor;
import com.goldsign.commu.commu.vo.CommuHandledMessage;
import java.util.Vector;
import org.apache.log4j.Logger;

public class CommuMessageHandleThread extends Thread {

    private static Logger logger = Logger.getLogger(CommuMessageHandleThread.class.getName());
    
    private Vector msgs = new Vector(BaseConstant.ThreadBufferCapacity,
            BaseConstant.ThreadBufferIncrement);
    private Vector priorityMsgs = new Vector(BaseConstant.PriorityThreadBufferCapacity,
            BaseConstant.PriorityThreadBufferIncrement);
    private boolean isHandling = false;
    private CommuHandledMessage handlingMsg = null;
    private CommuHandledMessage handlingPriorityMsg = null;
    private MessageProcessor mp = null;

    public CommuMessageHandleThread() {
    }

    public Vector getMsgs() {
        Vector tmsgs = new Vector(BaseConstant.ThreadBufferCapacity, BaseConstant.ThreadBufferIncrement);
        synchronized (this.msgs) {
            tmsgs.addAll(msgs);
            this.msgs.clear();
            return tmsgs;
        }   
    }

    public Vector getAllMsgs() {
        Vector allMsgs = new Vector(BaseConstant.ThreadBufferCapacity + BaseConstant.PriorityThreadBufferCapacity,
                BaseConstant.ThreadBufferIncrement);
        synchronized (this.msgs) {
            synchronized (this.priorityMsgs) {
                allMsgs.addAll(this.priorityMsgs);
                this.priorityMsgs.clear();
                allMsgs.addAll(this.msgs);
                this.msgs.clear();
                return allMsgs;
            }
        }

    }

    public CommuHandledMessage getHandlingMsg() {
        return this.handlingMsg;
    }

    public boolean isHandlingMsg() {
        return this.isHandling;

    }

    public CommuHandledMessage getMsg() {
        CommuHandledMessage msg = null;

        synchronized (msgs) {
            if (msgs.isEmpty()) {
                return null;
            }
            msg = (CommuHandledMessage) msgs.get(0);
            msgs.remove(0);
        }
        //logger.info(this.getName() + ":left msgs =" + msgs.size());

        return msg;

    }

    public void setMsg(CommuHandledMessage msg) {
        synchronized (msgs) {
            msgs.add(msg);
        }
    }

    public CommuHandledMessage getPriorityMsg() {
        CommuHandledMessage msg = null;

        synchronized (priorityMsgs) {
            if (priorityMsgs.isEmpty()) {
                return null;
            }
            msg = (CommuHandledMessage) priorityMsgs.get(0);
            priorityMsgs.remove(0);

        }

        return msg;

    }

    private CommuHandledMessage getHandingMsg() {
        CommuHandledMessage msg = null;
        synchronized (priorityMsgs) {
            if (!priorityMsgs.isEmpty()) {//优先级队列有未处理消息
                msg = (CommuHandledMessage) priorityMsgs.get(0);
                priorityMsgs.remove(0);
                //logger.info(this.getName() + ":left priorityMsgs =" + priorityMsgs.size());
                return msg;

            } else//优先级队列娥没有未处理消息
            {
                synchronized (msgs) {
                    if (msgs.isEmpty()) {
                        return null;
                    } else {
                        msg = (CommuHandledMessage) msgs.get(0);
                        msgs.remove(0);
                        //logger.info(this.getName() + ":left msgs =" + msgs.size());
                    }
                }
            }
            return msg;
        }
    }

    public void setHandlingMsg(CommuHandledMessage msg) {
        //消息是否是优先级消息
        if (this.isPriorityMsg(msg)) {
            synchronized (priorityMsgs) {//消息加入优先级队列
                priorityMsgs.add(msg);
            }

        } else {
            synchronized (msgs) {//消息加入普通级队列
                msgs.add(msg);
            }
        }

    }

    public boolean isPriorityMsg(CommuHandledMessage msg) {
        String[] data = (String[]) msg.getReadResult().get(1);
        if (data == null || data.length == 0) {
            return false;
        }
        String messageId = data[0];
        //优化级消息优先处理
        if (BaseConstant.prioritys.contains(messageId)) {
            return true;
        } else {
            return false;
        }

    }

    public void setPriorityMsg(CommuHandledMessage priorityMsg) {
        synchronized (priorityMsgs) {
            priorityMsgs.add(priorityMsg);
        }
    }

    public void run() {

        while (true) {
            try {
                handlingMsg = this.getHandingMsg();

                if (handlingMsg == null) {
                    this.sleep(BaseConstant.ThreadSleepTime);
                    continue;

                }
                this.isHandling = true;

                mp = new MessageProcessor(handlingMsg.getIp(),
                        handlingMsg.getReadResult(), this.getName(), handlingMsg.getBridge());
                mp.run();

                this.isHandling = false;

                this.sleep(BaseConstant.ThreadSleepTime);

            } catch (InterruptedException e) {
                logger.error(e);
                break;

            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
