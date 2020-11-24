package com.goldsign.escommu.thread;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.processor.MessageProcessor;
import com.goldsign.escommu.util.CommuThreadLogUtil;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.vo.CommuHandledMessage;
import java.util.Vector;

public class CommuMessageHandleThread extends Thread {

    private Vector msgs = new Vector(AppConstant.ThreadBufferCapacity,
            AppConstant.ThreadBufferIncrement);
    private Vector priorityMsgs = new Vector(AppConstant.PriorityThreadBufferCapacity,
            AppConstant.PriorityThreadBufferIncrement);
    private boolean isHandling = false;
    private CommuHandledMessage handlingMsg = null;
    private CommuHandledMessage handlingPriorityMsg = null;
    private MessageProcessor mp = null;

    public CommuMessageHandleThread() {
    }

    /**
     * 取得消息
     * 
     * @return 
     */
    public Vector getMsgs() {
        Vector tmsgs = new Vector(AppConstant.ThreadBufferCapacity, AppConstant.ThreadBufferIncrement);
        synchronized (this.msgs) {
            tmsgs.addAll(msgs);
            this.msgs.clear();
            return tmsgs;
        }   
    }

    /**
     * 取得所有消息
     * 
     * @return 
     */
    public Vector getAllMsgs() {
        Vector allMsgs = new Vector(AppConstant.ThreadBufferCapacity + AppConstant.PriorityThreadBufferCapacity,
                AppConstant.ThreadBufferIncrement);
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

    /**
     * 取得正在运行消息
     * 
     * @return 
     */
    public CommuHandledMessage getHandlingMsg() {
        return this.handlingMsg;
    }

    /**
     * 是否是正在处理消息
     * 
     * @return 
     */
    public boolean isHandlingMsg() {
        return this.isHandling;

    }

    /**
     * 取得消息
     * 
     * @return 
     */
    public CommuHandledMessage getMsg() {
        CommuHandledMessage msg = null;

        synchronized (msgs) {
            if (msgs.isEmpty()) {
                return null;
            }
            msg = (CommuHandledMessage) msgs.get(0);
            msgs.remove(0);
        }
        DateHelper.screenPrint(this.getName() + ":left msgs =" + msgs.size());

        return msg;

    }

    /**
     * 设置消息
     * 
     * @param msg 
     */
    public void setMsg(CommuHandledMessage msg) {
        synchronized (msgs) {
            msgs.add(msg);
        }
    }

    /**
     * 取得优先消息
     * 
     * @return 
     */
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

    /**
     * 取得正在处理消息
     * 
     * @return 
     */
    private CommuHandledMessage getHandingMsg() {
        CommuHandledMessage msg = null;
        synchronized (priorityMsgs) {
            if (!priorityMsgs.isEmpty()) {//优先级队列有未处理消息
                msg = (CommuHandledMessage) priorityMsgs.get(0);
                priorityMsgs.remove(0);
                DateHelper.screenPrint(this.getName() + ":left priorityMsgs =" + priorityMsgs.size());
                return msg;

            } else//优先级队列娥没有未处理消息
            {
                synchronized (msgs) {
                    if (msgs.isEmpty()) {
                        return null;
                    } else {
                        msg = (CommuHandledMessage) msgs.get(0);
                        msgs.remove(0);
                        DateHelper.screenPrint(this.getName() + ":left msgs =" + msgs.size());
                    }
                }
            }
            return msg;
        }
    }

    /**
     * 设置正在处理消息
     * 
     * @param msg 
     */
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

    /**
     * 是否是优先消息
     * 
     * @param msg
     * @return 
     */
    public boolean isPriorityMsg(CommuHandledMessage msg) {
        byte[] data = (byte[]) msg.getReadResult().get(1);
        if (data == null || data.length == 0) {
            return false;
        }
        String messageId = "" + (char) data[0] + (char) data[1];
        //优化级消息优先处理
        if (AppConstant.prioritys.contains(messageId)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 设置优先消息
     * 
     * @param priorityMsg 
     */
    public void setPriorityMsg(CommuHandledMessage priorityMsg) {
        synchronized (priorityMsgs) {
            priorityMsgs.add(priorityMsg);
        }
    }

    /**
     * 运行
     * 
     */
    public void run() {


        while (true) {
            try {
                handlingMsg = this.getHandingMsg();

                if (handlingMsg == null) {
                    // DateHelper.screenPrint(this.getName()+"没有需要处理的消息");
                    this.sleep(AppConstant.ThreadSleepTime);
                    continue;

                }
                this.isHandling = true;

                mp = new MessageProcessor(handlingMsg.getIp(),
                        handlingMsg.getReadResult(), this.getName(), handlingMsg.getBridge());
                mp.run();

                this.isHandling = false;

                this.sleep(AppConstant.ThreadSleepTime);


            } catch (InterruptedException e) {
                // DateHelper.screenPrint("线程"+this.getName()+"被中断.........");
                //线程异常中断时,将线程未处理的消息写入未处理文件中
                //CommuThreadLogUtil.writeUnHandleMsgToFile(this.getName(),msgs);
                break;

            } catch (Exception e) {
                //线程处理消息出现异常时,将处理的消息写入处理异常文件中
                CommuThreadLogUtil.writeErrHandleMsgToFile(this.getName(), (Vector) handlingMsg.getReadResult());
                DateHelper.screenPrint(e.getMessage() + "  " + this.getName() + ":消息缓存未处理消息为" + this.msgs.size());
            }
        }
    }
}
