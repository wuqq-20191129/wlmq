package com.goldsign.commu.commu.thread;

import com.goldsign.commu.commu.env.BaseConstant;
import java.util.Vector;
import org.apache.log4j.Logger;

public class CommuThreadManager {

    private static Logger logger = Logger.getLogger(CommuThreadManager.class.getName());
    
    public static Vector handleThreads = new Vector();
    private static int nextThread = 0;
    private static int nextThread_seq = 0;

    public CommuThreadManager() {
    }

    public int startHandleThreads() {
        CommuMessageHandleThread t = null;
        for (int i = 0; i < BaseConstant.MaxThreadNumber; i++) {
            t = new CommuMessageHandleThread();
            t.setPriority(BaseConstant.ThreadPriority);
            t.setName(new Integer(i).toString());
            this.handleThreads.add(t);
            t.start();
        }
        logger.info(handleThreads.size() + "个处理线程已启动");
        return handleThreads.size();
    }

    public synchronized CommuMessageHandleThread getNextMsgHandleThread() {
        CommuMessageHandleThread t = null;
        //如果要获取的线程号超过最大,复0
        if (nextThread >= BaseConstant.MaxThreadNumber) {
            nextThread = 0;
        }
        // search  twice at most
        //查找当前可用线程从上次取出的线程开始,如果线程正在处理,跳过.最多循环查找次数由配置文件设置
        for (int j = 0; j < BaseConstant.MaxSearchNum; j++) {
            for (int i = nextThread; i < BaseConstant.MaxThreadNumber; i++) {
                t = (CommuMessageHandleThread) this.handleThreads.get(i);
                if (!t.isHandlingMsg()) {
                    nextThread = i;
                    nextThread++;
                    return t;

                }
            }
            nextThread = 0;// search from start
        }

        //使用上面方法找不到,返回第一个线程
        if (nextThread_seq >= BaseConstant.MaxThreadNumber) {
            nextThread_seq = 0;
        }

        t = (CommuMessageHandleThread) this.handleThreads.get(nextThread_seq);
        nextThread_seq++; // next one

        return t;

    }
    
    public static void stopHandleThread() {
        CommuMessageHandleThread t = null;
        for (int i = 0; i < handleThreads.size(); i++) {
            t = (CommuMessageHandleThread) handleThreads.get(i);
            if (t == null) {
                continue;
            }
            t.interrupt();
        }
        handleThreads.removeAllElements();  
    }
}
