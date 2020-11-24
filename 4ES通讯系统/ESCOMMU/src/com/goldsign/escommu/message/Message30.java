package com.goldsign.escommu.message;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.LogConstant;
import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.dao.OperLoginDao;
import com.goldsign.escommu.exception.CommuException;
import com.goldsign.escommu.util.CharUtil;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.LogDbUtil;
import com.goldsign.escommu.util.MessageCommonUtil;
import com.goldsign.escommu.vo.OperTypeReqVo;
import com.goldsign.escommu.vo.OperTypeRspVo;
import com.goldsign.escommu.vo.SynchronizedControl;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author lenovo
 */
public class Message30 extends MessageBase {

    private static Logger logger = Logger.getLogger(Message30.class.getName());
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
            LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_OPER_TYPE_REQ, this.messageFrom,
                    this.hdlStartTime, this.hdlEndTime, result, this.threadNum,
                    this.level, this.remark);
        }
    }

    public void process() throws Exception {
        DateHelper.screenPrint("处理" + MessageCommonUtil.getMessageName(MessageConstant.MESSAGE_ID_OPER_TYPE_REQ) + "消息");
        synchronized (SYNCONTROL) {
            try {

                OperTypeReqVo operTypeReqVo = getOperTypeReq();
                OperLoginDao operLoginDao = new OperLoginDao();
                OperTypeRspVo operTypeRspVo = operLoginDao.operLogin(operTypeReqVo);

                byte[] datasRsp = new ConstructMessage31().constructMessage(operTypeRspVo);
                this.bridge.getConnection().sendData(datasRsp, Integer.parseInt(messageSequ));
            } catch (CommuException e) {
                DateHelper.screenPrintForEx(DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date()) + "  " + thisClassName + " error! messageSequ:" + messageSequ + ". " + e);
                logger.error(thisClassName + " error! messageSequ:" + messageSequ + ". " + e);
                throw e;
            }
        }
    }

    private OperTypeReqVo getOperTypeReq() throws CommuException {

        String loginFlag = CharUtil.trimStr(this.getCharString(2, 1));
        String operCode = CharUtil.trimStr(getCharString(3, 8));
        String password = CharUtil.trimStr(this.getCharString(11, 8));
        String loginTime = CharUtil.trimStr(this.getCharString(19, 20));
        String deviceId = CharUtil.trimStr(this.getCharString(39, 3));
        OperTypeReqVo operTypeReqVo = new OperTypeReqVo();
        operTypeReqVo.setLoginFlag(loginFlag);
        operTypeReqVo.setOperCode(operCode);
        operTypeReqVo.setPassword(password);;
        operTypeReqVo.setLoginTime(loginTime);
        operTypeReqVo.setDeviceId(deviceId);

        return operTypeReqVo;
    }
}
