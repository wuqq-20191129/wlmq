package com.goldsign.escommu.message;

import com.goldsign.escommu.dao.CardSectionDao;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.LogConstant;
import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.dao.SamCardFindDao;
import com.goldsign.escommu.exception.CommuException;
import com.goldsign.escommu.util.CharUtil;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.LogDbUtil;
import com.goldsign.escommu.util.MessageCommonUtil;
import com.goldsign.escommu.vo.*;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author lenovo
 */
public class Message60 extends MessageBase {

    private static Logger logger = Logger.getLogger(Message60.class.getName());
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
            LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_CARD_SECTION_REQ, this.messageFrom,
                    this.hdlStartTime, this.hdlEndTime, result, this.threadNum,
                    this.level, this.remark);
        }
    }

    public void process() throws Exception {
        DateHelper.screenPrint("处理" + MessageCommonUtil.getMessageName(MessageConstant.MESSAGE_ID_CARD_SECTION_REQ) + "消息");
        synchronized (SYNCONTROL) {
            try {

                CardSectionReqVo cardSectionReqVo = getCardSectionReq();
                CardSectionDao cardSectionDao = new CardSectionDao();
                CardSectionRspVo cardSectionRspVo = cardSectionDao.findCardSections(cardSectionReqVo);
                
                byte[] datasRsp = new ConstructMessage61().constructMessage(cardSectionRspVo);
                this.bridge.getConnection().sendData(datasRsp, Integer.parseInt(messageSequ));
                
                //更新卡号段为已使用
                if(cardSectionRspVo.isResResult()){
                    cardSectionDao.updateCardSections(cardSectionRspVo.getCardSectionVos());
                }
            } catch (CommuException e) {
                DateHelper.screenPrintForEx(DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date()) + "  " + thisClassName + " error! messageSequ:" + messageSequ + ". " + e);
                logger.error(thisClassName + " error! messageSequ:" + messageSequ + ". " + e);
                throw e;
            } 
        }
    }

    private CardSectionReqVo getCardSectionReq() throws CommuException{
       
        String deviceId = CharUtil.trimStr(this.getCharString(2, 5));
        String reqDatetime = CharUtil.trimStr(getCharString(7, 14));
      
        CardSectionReqVo cardSectionReqVo = new CardSectionReqVo();
        cardSectionReqVo.setDeviceId(deviceId);
        cardSectionReqVo.setReqDatetime(reqDatetime);
     
        return cardSectionReqVo;
    }
}
