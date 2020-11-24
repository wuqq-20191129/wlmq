package com.goldsign.escommu.message;

import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.vo.CardPriceRspVo;
import java.util.List;
import org.apache.log4j.Logger;



public class ConstructMessage41 extends ConstructMessageBase{

    private static Logger logger = Logger.getLogger(ConstructMessage41.class.getName());

    public ConstructMessage41() {
        super();
    }

    public byte[] constructMessage(List<CardPriceRspVo> cardPriceRspVos) {
        try {
            initMessage();
            AddStringToMessage(MessageConstant.MESSAGE_ID_CARD_PRICE_RES, 2);
            AddShortToMessage((short)cardPriceRspVos.size());
            for(CardPriceRspVo cardPriceRspVo: cardPriceRspVos){
                AddStringToMessage(cardPriceRspVo.getPrice(), 10);
         
            }
            byte[] msg = trimMessage();
            logger.info("成功构造消息41! ");
            return msg;
        } catch (Exception e) {
            logger.error("构造消息41错误! " + e);
            return null;
        }
    }	
}
