package com.goldsign.escommu.message;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.LogConstant;
import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.dao.SignCardFindDao;
import com.goldsign.escommu.exception.CommuException;
import com.goldsign.escommu.util.CharUtil;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.LogDbUtil;
import com.goldsign.escommu.util.MessageCommonUtil;
import com.goldsign.escommu.vo.SignCardReqVo;
import com.goldsign.escommu.vo.SignCardRspVo;
import com.goldsign.escommu.vo.SynchronizedControl;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author lenovo
 */
public class Message38 extends MessageBase {

    private static Logger logger = Logger.getLogger(Message38.class.getName());
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
            LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_SIGN_CARD_REQ, this.messageFrom,
                    this.hdlStartTime, this.hdlEndTime, result, this.threadNum,
                    this.level, this.remark);
        }
    }

    public void process() throws Exception {
        DateHelper.screenPrint("处理" + MessageCommonUtil.getMessageName(MessageConstant.MESSAGE_ID_SIGN_CARD_REQ) + "消息");
        synchronized (SYNCONTROL) {
            try {

                SignCardReqVo signCardReqVo = getSignCardReq();
                SignCardFindDao signCardFindDao = new SignCardFindDao();
                List<SignCardRspVo> signCardRspVos = signCardFindDao.signCardFind(signCardReqVo);
                
                byte[] datasRsp = new ConstructMessage39().constructMessage(signCardRspVos);
                this.bridge.getConnection().sendData(datasRsp, Integer.parseInt(messageSequ));
                
            } catch (CommuException e) {
                DateHelper.screenPrintForEx(DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date()) + "  " + thisClassName + " error! messageSequ:" + messageSequ + ". " + e);
                logger.error(thisClassName + " error! messageSequ:" + messageSequ + ". " + e);
                throw e;
            }
        }
    }

    private SignCardReqVo getSignCardReq() throws CommuException{
    
        String orderNo = CharUtil.trimStr(this.getCharString(2, 14));
        String startReqNo = CharUtil.trimStr(getCharString(16, 10));
        String endReqNo = CharUtil.trimStr(this.getCharString(26, 10));
       
        SignCardReqVo signCardReqVo = new SignCardReqVo();
        signCardReqVo.setOrderNo(orderNo);
        signCardReqVo.setStartReqNo(startReqNo);
        signCardReqVo.setEndReqNo(endReqNo);
     
        return signCardReqVo;
    }
}
