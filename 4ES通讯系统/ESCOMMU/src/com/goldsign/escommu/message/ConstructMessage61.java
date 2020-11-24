package com.goldsign.escommu.message;

import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.vo.CardSectionRspVo;
import com.goldsign.escommu.vo.CityTypeRspVo;
import org.apache.log4j.Logger;



public class ConstructMessage61 extends ConstructMessageBase{

    private static Logger logger = Logger.getLogger(ConstructMessage61.class.getName());

    public ConstructMessage61() {
        super();
    }

    public byte[] constructMessage(CardSectionRspVo cardSectionRspVo) {
        try {
            initMessage();
            AddStringToMessage(MessageConstant.MESSAGE_ID_CARD_SECTION_RES, 2);
            AddStringToMessage(cardSectionRspVo.getDeviceId(), 5);
            AddStringToMessage(cardSectionRspVo.getReqDatetime(), 14);
            AddStringToMessage(cardSectionRspVo.getReqResult(), 2);
            AddStringToMessage(cardSectionRspVo.getFileName(), 30);
            byte[] msg = trimMessage();
            logger.info("成功构造消息61! ");
            return msg;
        } catch (Exception e) {
            logger.error("构造消息61错误! " + e);
            return null;
        }
    }	
}
