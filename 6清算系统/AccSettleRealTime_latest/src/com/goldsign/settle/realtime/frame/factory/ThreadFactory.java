/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.factory;

import com.goldsign.settle.realtime.frame.constant.FrameBuffer;
import com.goldsign.settle.realtime.frame.constant.FrameThreadConstant;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.thread.HandleThreadBase;
import com.goldsign.settle.realtime.frame.thread.HandleThreadOther;
import com.goldsign.settle.realtime.frame.vo.ThreadAttrVo;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ThreadFactory {

    private static Logger logger = Logger.getLogger(ThreadFactory.class.getName());
    private static int TRADE_TYPE_MIN = 50;

    public int startHandleThreads(ThreadAttrVo vo) {
        HandleThreadBase t = null;
        Vector<HandleThreadBase> handleThreads = new Vector();
        for (int i = 0; i < vo.getMaxThreadNumber(); i++) {
            t = new HandleThreadBase(vo);
            t.setPriority(vo.getThreadPriority());

            t.setName(new Integer(i).toString());
            handleThreads.add(t);
            t.start();
        }
        logger.info(this.getThreadPoolName(vo.getThreadPoolId()) + " " + handleThreads.size() + "个处理线程已启动");
        FrameBuffer.BUFFER_POOL_THREAD.put(vo.getThreadPoolId(), handleThreads);
        FrameBuffer.BUFFER_POOL_THREAD_ATTR.put(vo.getThreadPoolId(), vo);

        return handleThreads.size();
    }

    public int startHandleThreadsForFileMobile(ThreadAttrVo vo) {
        HandleThreadBase t = null;
        Vector<HandleThreadBase> handleThreads = new Vector();
        for (int i = 0; i < vo.getMaxThreadNumber(); i++) {
            t = new HandleThreadBase(vo);
            t.setPriority(vo.getThreadPriority());

            t.setName(new Integer(i).toString());
            handleThreads.add(t);
            t.start();
        }
        logger.info(this.getThreadPoolName(vo.getThreadPoolId()) + " " + handleThreads.size() + "个处理线程已启动");
        FrameBuffer.BUFFER_POOL_THREAD.put(vo.getThreadPoolId(), handleThreads);
        FrameBuffer.BUFFER_POOL_THREAD_ATTR.put(vo.getThreadPoolId(), vo);

        return handleThreads.size();
    }

    public int startHandleThreadsForOther(ThreadAttrVo vo) {
        HandleThreadOther t = null;
        Vector<HandleThreadBase> handleThreads = new Vector();
        for (int i = 0; i < vo.getMaxThreadNumber(); i++) {
            t = new HandleThreadOther(vo);
            t.setPriority(vo.getThreadPriority());

            t.setName(new Integer(i).toString());
            handleThreads.add(t);
            t.start();
        }
        logger.info(this.getThreadPoolName(vo.getThreadPoolId()) + " " + handleThreads.size() + "个处理线程已启动");
        FrameBuffer.BUFFER_POOL_THREAD.put(vo.getThreadPoolId(), handleThreads);
        FrameBuffer.BUFFER_POOL_THREAD_ATTR.put(vo.getThreadPoolId(), vo);

        return handleThreads.size();
    }

    public int startHandleThreadsForOtherMobile(ThreadAttrVo vo) {
        HandleThreadOther t = null;
        Vector<HandleThreadBase> handleThreads = new Vector();
        for (int i = 0; i < vo.getMaxThreadNumber(); i++) {
            t = new HandleThreadOther(vo);
            t.setPriority(vo.getThreadPriority());

            t.setName(new Integer(i).toString());
            handleThreads.add(t);
            t.start();
        }
        logger.info(this.getThreadPoolName(vo.getThreadPoolId()) + " " + handleThreads.size() + "个处理线程已启动");
        FrameBuffer.BUFFER_POOL_THREAD.put(vo.getThreadPoolId(), handleThreads);
        FrameBuffer.BUFFER_POOL_THREAD_ATTR.put(vo.getThreadPoolId(), vo);

        return handleThreads.size();
    }

    public int startHandleThreadsForOtherQrCode(ThreadAttrVo vo) {
        HandleThreadOther t = null;
        Vector<HandleThreadBase> handleThreads = new Vector();
        for (int i = 0; i < vo.getMaxThreadNumber(); i++) {
            t = new HandleThreadOther(vo);
            t.setPriority(vo.getThreadPriority());

            t.setName(new Integer(i).toString());
            handleThreads.add(t);
            t.start();
        }
        logger.info(this.getThreadPoolName(vo.getThreadPoolId()) + " " + handleThreads.size() + "个处理线程已启动");
        FrameBuffer.BUFFER_POOL_THREAD.put(vo.getThreadPoolId(), handleThreads);
        FrameBuffer.BUFFER_POOL_THREAD_ATTR.put(vo.getThreadPoolId(), vo);

        return handleThreads.size();
    }

    public int startHandleThreadsForOtherNetPaid(ThreadAttrVo vo) {
        HandleThreadOther t = null;
        Vector<HandleThreadBase> handleThreads = new Vector();
        for (int i = 0; i < vo.getMaxThreadNumber(); i++) {
            t = new HandleThreadOther(vo);
            t.setPriority(vo.getThreadPriority());

            t.setName(new Integer(i).toString());
            handleThreads.add(t);
            t.start();
        }
        logger.info(this.getThreadPoolName(vo.getThreadPoolId()) + " " + handleThreads.size() + "个处理线程已启动");
        FrameBuffer.BUFFER_POOL_THREAD.put(vo.getThreadPoolId(), handleThreads);
        FrameBuffer.BUFFER_POOL_THREAD_ATTR.put(vo.getThreadPoolId(), vo);

        return handleThreads.size();
    }

    private String getThreadPoolName(String id) {
        String tid;
        for (int i = 0; i < FrameThreadConstant.THREAD_POOL_IDS.length; i++) {
            tid = FrameThreadConstant.THREAD_POOL_IDS[i];
            if (tid.equals(id)) {
                return FrameThreadConstant.THREAD_POOL_NAMES[i];
            }
        }
        return id;
    }

    public void putMessageToQueue(String threadPoolId, MessageBase message) {
        HandleThreadBase t = this.getNextMsgHandleThread(threadPoolId);
        t.setHandlingMsg(message);
    }

    public void putMessageToQueueForBcp(String threadPoolId, MessageBase message) {
        //modify by hejj 20160704文件数量多时，线程的队列消息分别不均衡，效率慢
        // HandleThreadBase t = this.getNextMsgHandleThreadForBcp(threadPoolId,message.getTradType());
        HandleThreadBase t = this.getNextMsgHandleThread(threadPoolId);
        t.setHandlingMsg(message);
    }

    public void putMessageToQueueForTac(String threadPoolId, MessageBase message) {
        //modify by hejj 20160704文件数量多时，线程的队列消息分别不均衡，效率慢
        // HandleThreadBase t = this.getNextMsgHandleThreadForTac(threadPoolId,message.getTradType());
        HandleThreadBase t = this.getNextMsgHandleThread(threadPoolId);
        t.setHandlingMsg(message);
    }

    private int getTradeTypeNumValue(String tradType) {
        int n = 0;
        for (int i = 0; i < tradType.length(); i++) {
            n += tradType.charAt(i);
        }
        return n;

    }

    public HandleThreadBase getNextMsgHandleThreadForBcp(String threadPoolId, String tradType) {
        HandleThreadBase t = null;
        ThreadAttrVo vo = (ThreadAttrVo) FrameBuffer.BUFFER_POOL_THREAD_ATTR.get(threadPoolId);
        Vector threads = (Vector) (FrameBuffer.BUFFER_POOL_THREAD.get(threadPoolId));
        int iTradType = this.getTradeTypeNumValue(tradType);//Integer.parseInt(tradType);//处理交易类型有非数字字符情况
        int threadId = iTradType % ThreadFactory.TRADE_TYPE_MIN;
        if (threadId < threads.size()) {
            t = (HandleThreadBase) threads.get(threadId);
            return t;
        } else {
            t = (HandleThreadBase) threads.get(0);
            return t;
        }

    }

    public HandleThreadBase getNextMsgHandleThreadForTac(String threadPoolId, String tradType) {
        HandleThreadBase t = null;
        ThreadAttrVo vo = (ThreadAttrVo) FrameBuffer.BUFFER_POOL_THREAD_ATTR.get(threadPoolId);
        Vector threads = (Vector) (FrameBuffer.BUFFER_POOL_THREAD.get(threadPoolId));
        int iTradType = this.getTradeTypeNumValue(tradType);;//Integer.parseInt(tradType);
        int threadId = iTradType % ThreadFactory.TRADE_TYPE_MIN;
        if (threadId < threads.size()) {
            t = (HandleThreadBase) threads.get(threadId);
            return t;
        } else {
            t = (HandleThreadBase) threads.get(0);
            return t;
        }

    }

    public HandleThreadBase getNextMsgHandleThread(String threadPoolId) {
        HandleThreadBase t = null;
        ThreadAttrVo vo = (ThreadAttrVo) FrameBuffer.BUFFER_POOL_THREAD_ATTR.get(threadPoolId);
        Vector threads = (Vector) (FrameBuffer.BUFFER_POOL_THREAD.get(threadPoolId));
        //如果要获取的线号超过最大,复0
        if (vo.getNextThread() >= vo.getMaxThreadNumber()) {
            vo.setNextThread(0);
        }
        // search  twice at most
        //查找当前可用线程从上次取出的线程开始,如果线程正在处理,跳过.最多循环查找次数由配置文件设置
        for (int j = 0; j < vo.getMaxSearchNum(); j++) {
            for (int i = vo.getNextThread(); i < vo.getMaxThreadNumber(); i++) {
                t = (HandleThreadBase) threads.get(i);
                if (!t.isHandlingMsg()) {
                    vo.setNextThread(vo.getNextThread() + 1);
                    return t;

                }
            }
            vo.setNextThread(0); // search from start
        }

        //使用上面方法找不到,返回第一个线程
        if (vo.getNextThreadSeq() >= vo.getMaxThreadNumber()) {
            vo.setNextThreadSeq(0);
        }

        t = (HandleThreadBase) (HandleThreadBase) threads.get(vo.getNextThreadSeq());
        vo.setNextThreadSeq(vo.getNextThreadSeq() + 1); // next one

        //   DateHelper.screenPrint("min remain msg="+minRemainMsgs + " "+ mht.getName());
        return t;

    }
}
