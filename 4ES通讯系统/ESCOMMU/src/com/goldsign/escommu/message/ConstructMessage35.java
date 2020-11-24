package com.goldsign.escommu.message;

import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.vo.OrderTypeRspVo;
import java.util.List;
import org.apache.log4j.Logger;



public class ConstructMessage35 extends ConstructMessageBase{

    private static Logger logger = Logger.getLogger(ConstructMessage35.class.getName());

    public ConstructMessage35() {
        super();
    }

    public byte[] constructMessage(List<OrderTypeRspVo> orderTypeRspVos) {
        try {
            initMessage();
            AddStringToMessage(MessageConstant.MESSAGE_ID_ORDER_TYPE_RES, 2);
            AddShortToMessage((short)orderTypeRspVos.size());
            for(OrderTypeRspVo orderTypeRspVo: orderTypeRspVos){
                AddStringToMessage(orderTypeRspVo.getOrderNo(), 14);
                AddStringToMessage(orderTypeRspVo.getCardType(), 4);
                AddGbkStringToMessage(orderTypeRspVo.getCardName(), 30);
                AddStringToMessage(orderTypeRspVo.getCardEffTime(), 8);
                AddLongToMessage(orderTypeRspVo.getPrintValue());
                AddLongToMessage(orderTypeRspVo.getDepValue());
                AddStringToMessage(orderTypeRspVo.getStartReqNo(), 10);
                AddStringToMessage(orderTypeRspVo.getEndReqNo(), 10);
                AddStringToMessage(orderTypeRspVo.getStartSeq(), 8);
                AddStringToMessage(orderTypeRspVo.getEndSeq(), 8);
                AddStringToMessage(orderTypeRspVo.getMakeDate(), 8);
                AddLongToMessage(orderTypeRspVo.getQuantity());
                AddStringToMessage(orderTypeRspVo.getSignCode(), 3);
                AddStringToMessage(orderTypeRspVo.getLineCode(), 2);
                AddStringToMessage(orderTypeRspVo.getStationCode(), 2);
                AddStringToMessage(orderTypeRspVo.getCcEffStartTime(), 8);
                AddStringToMessage(orderTypeRspVo.getCcEffUseTime(), 5);
                AddStringToMessage(orderTypeRspVo.getLimitOutLineCode(), 2);
                AddStringToMessage(orderTypeRspVo.getLimitOutStationCode(), 2);
                AddStringToMessage(orderTypeRspVo.getLimitMode(), 3);
                AddStringToMessage(orderTypeRspVo.getSaleFlag(), 1);
                AddStringToMessage(orderTypeRspVo.getTestFlag(), 1);
                AddLongToMessage(orderTypeRspVo.getMaxRecharge());
                AddStringToMessage(orderTypeRspVo.getEsSeriakNo(), 10);
            }
            byte[] msg = trimMessage();
            logger.info("成功构造消息35! ");
            return msg;
        } catch (Exception e) {
            logger.error("构造消息35错误! " + e);
            return null;
        }
    }	
}
