package com.goldsign.escommu.message;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.LogConstant;
import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.exception.CommuException;
import com.goldsign.escommu.util.CharUtil;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.LogDbUtil;
import com.goldsign.escommu.util.MessageCommonUtil;
import com.goldsign.escommu.vo.SynchronizedControl;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author lenovo
 */
public class Message98 extends MessageBase {

    private static Logger logger = Logger.getLogger(Message98.class.getName());
    public static SynchronizedControl SYNCONTROL = new SynchronizedControl();
    private final static Object KMS_LOCK = new Object();
    private static boolean KMS_LOCK_STATE = false;
    private static long KMS_LOCK_TIME = 0l;
    private static String KMS_LOCK_IP = "";

    public void run() throws Exception {
        String result = LogConstant.RESULT_HDL_SUCESS;
        this.level = AppConstant.LOG_LEVEL_INFO;
        try {
            this.hdlStartTime = System.currentTimeMillis();

            this.process();

            this.hdlEndTime = System.currentTimeMillis();
        } catch (Exception e) {
            result = LogConstant.RESULT_HDL_FAIL;
            this.hdlEndTime = System.currentTimeMillis();
            this.level = AppConstant.LOG_LEVEL_ERROR;
            this.remark = e.getMessage();
            throw e;
        } finally {//记录处理日志
            LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_OPER_TYPE_REQ, this.messageFrom,
                    this.hdlStartTime, this.hdlEndTime, result, this.threadNum,
                    this.level, this.remark);
        }
    }
    
    private boolean setKmsLock() {
        
        for(int i=0;i<300;i++){
            synchronized(KMS_LOCK){
                if(!KMS_LOCK_STATE){
                    logger.info(messageFrom+","+(i+1)+"次,取得锁成功！");
                    setKmsLockConfig(true, System.currentTimeMillis(), messageFrom);
                    return true;
                }
                long endTime = System.currentTimeMillis();
                if(endTime-KMS_LOCK_TIME >60000){
                    logger.info(KMS_LOCK_IP+"被强释放，"+messageFrom+","+(i+1)+"次,取得锁成功！");
                    setKmsLockConfig(true, endTime, messageFrom);
                    return true;
                }
            }
            sleepTime(50);
        }
        return false;
    }
    
    private void sleepTime(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
    }
    
    private void setKmsLockConfig(boolean state, long time, String ip){
        
        KMS_LOCK_TIME = time;
        KMS_LOCK_IP = ip;
        KMS_LOCK_STATE = state;
    }
    
    private boolean setKmsUnlock(){
        
        synchronized(KMS_LOCK){
            
            if(messageFrom.equals(KMS_LOCK_IP)){
                setKmsLockConfig(false, 0, "");
            }
            logger.info(messageFrom+",解锁成功！");
        }
        return true;
    }

    public void process() throws Exception {
        DateHelper.screenPrint("处理" + MessageCommonUtil.getMessageName(MessageConstant.MESSAGE_ID_KMS_LOCK_REQ) + "消息");
        
        synchronized (SYNCONTROL) {
            try {
                boolean result = false;
                if(getReqKmsLockState()){
                    result = setKmsLock();
                }else{
                    result = setKmsUnlock();
                }
                //0:成功，1：失败
                byte[] datasRsp = new ConstructMessage99().constructMessage(result?"0":"1");
                this.bridge.getConnection().sendData(datasRsp, Integer.parseInt(messageSequ));
            } catch (CommuException e) {
                DateHelper.screenPrintForEx(DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date()) + "  " + thisClassName + " error! messageSequ:" + messageSequ + ". " + e);
                logger.error(thisClassName + " error! messageSequ:" + messageSequ + ". " + e);
                throw e;
            }
        }
    }
    
    private boolean getReqKmsLockState() throws CommuException {

        String lockState = CharUtil.trimStr(this.getCharString(2, 1));
        //0:锁，1：解锁
        return "0".equals(lockState)?true:false;
       
    }
}
