package com.goldsign.escommu.message;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.LogConstant;
import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.dao.DeviceStateUpdateDao;
import com.goldsign.escommu.exception.CommuException;
import com.goldsign.escommu.util.CharUtil;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.LogDbUtil;
import com.goldsign.escommu.util.MessageCommonUtil;
import com.goldsign.escommu.vo.DeviceStateReqVo;
import com.goldsign.escommu.vo.DeviceStateRspVo;
import com.goldsign.escommu.vo.SynchronizedControl;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author lenovo
 */
public class Message44 extends MessageBase {

    private static Logger logger = Logger.getLogger(Message44.class.getName());
    public static SynchronizedControl SYNCONTROL = new SynchronizedControl();

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
            LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_DEVICE_STATE_REQ, this.messageFrom,
                    this.hdlStartTime, this.hdlEndTime, result, this.threadNum,
                    this.level, this.remark);
        }
    }

    public void process() throws Exception {
        DateHelper.screenPrint("处理" + MessageCommonUtil.getMessageName(MessageConstant.MESSAGE_ID_DEVICE_STATE_REQ) + "消息");
        synchronized (SYNCONTROL) {
            try {

                DeviceStateReqVo deviceStateReqVo = getDeviceStateReq();
                DeviceStateUpdateDao deviceStateUpdateDao = new DeviceStateUpdateDao();
                DeviceStateRspVo deviceStateRspVo = deviceStateUpdateDao.deviceStateUpdate(deviceStateReqVo);
                
            } catch (CommuException e) {
                DateHelper.screenPrintForEx(DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date()) + "  " + thisClassName + " error! messageSequ:" + messageSequ + ". " + e);
                logger.error(thisClassName + " error! messageSequ:" + messageSequ + ". " + e);
                throw e;
            } 
        }
    }

    private DeviceStateReqVo getDeviceStateReq() throws CommuException{
       
        String deviceId = CharUtil.trimStr(this.getCharString(2, 5));
        String operCode = CharUtil.trimStr(getCharString(7, 10));
        String changeTime = CharUtil.trimStr(this.getCharString(17, 20));
        String state = CharUtil.trimStr(this.getCharString(37, 4));
        String desc = getGbkString(41, 30);
        
        DeviceStateReqVo deviceStateReqVo = new DeviceStateReqVo();
        deviceStateReqVo.setDeviceId(deviceId);
        deviceStateReqVo.setOperCode(operCode);
        deviceStateReqVo.setChangeTime(changeTime);
        deviceStateReqVo.setState(state);
        deviceStateReqVo.setDesc(desc);
     
        return deviceStateReqVo;
    }
}
