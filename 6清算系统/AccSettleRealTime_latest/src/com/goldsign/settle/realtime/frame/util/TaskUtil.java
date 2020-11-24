/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.util;


import com.goldsign.settle.realtime.frame.constant.FrameFlowConstant;
import com.goldsign.settle.realtime.frame.constant.FrameSynConstant;
import com.goldsign.settle.realtime.frame.constant.TaskConstant;
import com.goldsign.settle.realtime.frame.vo.TaskFinishControl;
import java.util.Vector;
import org.apache.log4j.Logger;


/**
 *
 * @author hejj
 */
public class TaskUtil {
    private static Logger logger = Logger.getLogger(TaskUtil.class.getName());

    public static void waitAllTaskFinish(Vector<TaskFinishControl> tfcs,boolean isPrompt,String promptMsg) {
        if (tfcs == null || tfcs.isEmpty()) {
            return;
        }
        int i;
        int size = tfcs.size();
        TaskFinishControl tfc;
        long startTime =System.currentTimeMillis();
        long endTime;
        while (true) {
            for (i = 0; i < tfcs.size(); i++) {
                tfc = tfcs.get(i);
                if (!tfc.isFinished()) {//存在没有完成的任务
                   // logger.info(promptMsg+"，第"+i+"子任务还没有完成。"); 
                    break;
                }
            }
            if (i == size) {//所有任务完成
                if(isPrompt){
                    endTime =System.currentTimeMillis();
                   logger.info(promptMsg+"，所有子任务已完成。完成时间为："+(endTime-startTime)/1000+"秒"); 
                }
                break;
            }
            TaskUtil.threadSleep();

        }
    }
     public static  Vector<TaskFinishControl> getTaskControl(int n) {
        Vector<TaskFinishControl> tfcs = new Vector();
        for (int i = 0; i < n; i++) {
            TaskFinishControl tfc = new TaskFinishControl();
            tfcs.add(tfc);
        }
        return tfcs;
    }
     
    
    public static boolean isCanDownloadAuditFile(){
        synchronized(FrameSynConstant.SYN_FILE_DOWNLOAD){
            return FrameFlowConstant.IS_TIME_DOWNLOAD;
            
        }
    }
    public static boolean isCanHandleOctImportSettle(){
        synchronized(FrameSynConstant.SYN_OCT_IMPORT_SETTLE){
            return FrameFlowConstant.IS_TIME_OCT_IMPORT_SETTLE;
            
        }
    }
     public static boolean isCanHandleOctImportTrx(){
        synchronized(FrameSynConstant.SYN_OCT_IMPORT_TRX){
            return FrameFlowConstant.IS_TIME_OCT_IMPORT_TRX;
            
        }
    }
    public static void setDownloadAuditFile(boolean flag){
        synchronized(FrameSynConstant.SYN_FILE_DOWNLOAD){
            FrameFlowConstant.IS_TIME_DOWNLOAD=flag;
        }
    }
    public static boolean isFinishFileHandled(){
        synchronized(FrameSynConstant.SYN_FILE_FINISH_HANDLED){
            return FrameFlowConstant.IS_FINISH_FILE_HANDLED;
            
        }
    }
     public static  void  setFinishFileHandled(boolean flag){
        synchronized(FrameSynConstant.SYN_FILE_FINISH_HANDLED){
             FrameFlowConstant.IS_FINISH_FILE_HANDLED=flag;
             
            
        }
    }
     public static  void  setHandleOctImportSettle(boolean flag){
        synchronized(FrameSynConstant.SYN_OCT_IMPORT_SETTLE){
             FrameFlowConstant.IS_TIME_OCT_IMPORT_SETTLE=flag;
            
        }
    }
     public static  void  setHandleOctImportTrx(boolean flag){
        synchronized(FrameSynConstant.SYN_OCT_IMPORT_TRX){
             FrameFlowConstant.IS_TIME_OCT_IMPORT_TRX=flag;
            
        }
    }
     
     public static  void  setFinalSettleFlag(String flag){
        synchronized(FrameSynConstant.SYN_FINAL_SETTLE){
             FrameFlowConstant.FINAL_SETTLE_FLAG=flag;
             
            
        }
    }
      public static  String  getFinalSettleFlag(){
        synchronized(FrameSynConstant.SYN_FINAL_SETTLE){
             return FrameFlowConstant.FINAL_SETTLE_FLAG;
             
            
        }
    }

    private static void threadSleep() {
        try {
            Thread.sleep(TaskConstant.INTERVAL_TASK);
        } catch (InterruptedException ex) {
        }
    }
}
