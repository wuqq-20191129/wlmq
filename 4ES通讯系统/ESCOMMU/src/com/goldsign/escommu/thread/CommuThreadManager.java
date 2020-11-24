package com.goldsign.escommu.thread;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.util.CommuThreadLogUtil;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.vo.CommuHandledMessage;
import java.util.Vector;
import org.apache.log4j.Logger;

public class CommuThreadManager {

    public static Vector handleThreads = new Vector();
    private static int nextThread = 0;
    private static int nextThread_seq = 0;
    private static Logger logger = Logger.getLogger(CommuThreadManager.class.getName());

    public CommuThreadManager() {
    }

    /**
     * 启动消息处理线程
     * 
     * @return 
     */
    public int startHandleThreads() {
        CommuMessageHandleThread t = null;
        for (int i = 0; i < AppConstant.MaxThreadNumber; i++) {
            t = new CommuMessageHandleThread();
            t.setPriority(AppConstant.ThreadPriority);
            t.setName(new Integer(i).toString());
            this.handleThreads.add(t);
            t.start();
        }
        DateHelper.screenPrintForEx(handleThreads.size() + "个处理线程已启动");
        return handleThreads.size();
    }

    /**
     * 取得下一消息处理线程
     * 
     * @return 
     */
    public synchronized CommuMessageHandleThread getNextMsgHandleThread() {
        CommuMessageHandleThread t = null;
        //如果要获取的线程号超过最大,复0
        if (nextThread >= AppConstant.MaxThreadNumber) {
            nextThread = 0;
        }
        // search  twice at most
        //查找当前可用线程从上次取出的线程开始,如果线程正在处理,跳过.最多循环查找次数由配置文件设置
        for (int j = 0; j < AppConstant.MaxSearchNum; j++) {
            for (int i = nextThread; i < AppConstant.MaxThreadNumber; i++) {
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
        if (nextThread_seq >= AppConstant.MaxThreadNumber) {
            nextThread_seq = 0;
        }

        t = (CommuMessageHandleThread) this.handleThreads.get(nextThread_seq);
        nextThread_seq++; // next one

        //   DateHelper.screenPrint("min remain msg="+minRemainMsgs + " "+ mht.getName());
        return t;

    }

    /**
     * 停止消息处理线程
     * 
     */
    public static void stopHandleThread() {
        CommuMessageHandleThread t = null;
        Vector msgs = null;
        CommuHandledMessage msg = null;
        for (int i = 0; i < handleThreads.size(); i++) {
            t = (CommuMessageHandleThread) handleThreads.get(i);
            if (t == null) {
                DateHelper.screenPrint("第" + i + "个线程已是NULL值");
                continue;
            }
            //由于数据库连接异常关闭时,将线程未处理的消息放入代处理缓存
            /*
            if (CommuConnectionPoolListener.isSqlConExceptionClose.booleanValue()) {
                msgs = t.getAllMsgs();
                for (int j = 0; j < msgs.size(); j++) {
                    msg =(CommuHandledMessage) msgs.get(j);
                    CommuExceptionBufferUtil.setSqlUnHandleMsgs(msg.getIp(), (byte[]) msg.getReadResult().get(1));
                }
                continue;
            }*/
            
            //由于强制关闭时,将消息写入文件
            msgs = t.getAllMsgs();
            msg = t.getHandlingMsg();
            if (msgs != null) {
                CommuThreadLogUtil.writeUnHandleMsgToFile(t.getName(), msgs);
            }
            if (msg != null) {
                CommuThreadLogUtil.writeErrHandleMsgToFile(t.getName(), msg.getReadResult());
            }

        }

    }
}
