package com.goldsign.escommu.message;

import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.vo.CardTypeRspVo;
import java.util.List;
import org.apache.log4j.Logger;



public class ConstructMessage43 extends ConstructMessageBase{

    private static Logger logger = Logger.getLogger(ConstructMessage43.class.getName());

    public ConstructMessage43() {
        super();
    }

    public byte[] constructMessage(List<CardTypeRspVo> cardTypeRspVos) {
        try {
            initMessage();
            AddStringToMessage(MessageConstant.MESSAGE_ID_CARD_TYPE_RES, 2);
            AddShortToMessage((short)cardTypeRspVos.size());
            for(CardTypeRspVo cardTypeRspVo: cardTypeRspVos){
                AddStringToMessage(cardTypeRspVo.getCardType(), 4);
                AddGbkStringToMessage(cardTypeRspVo.getCardSubDesc(), 30);
            }
            byte[] msg = trimMessage();
            logger.info("成功构造消息43! ");
            return msg;
        } catch (Exception e) {
            logger.error("构造消息43错误! " + e);
            return null;
        }
    }	
}
