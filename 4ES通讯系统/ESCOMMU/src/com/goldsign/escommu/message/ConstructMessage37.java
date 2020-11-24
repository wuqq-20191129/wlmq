package com.goldsign.escommu.message;

import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.vo.OrderStateRspVo;
import java.util.List;
import org.apache.log4j.Logger;



public class ConstructMessage37 extends ConstructMessageBase{

    private static Logger logger = Logger.getLogger(ConstructMessage37.class.getName());

    public ConstructMessage37() {
        super();
    }

    public byte[] constructMessage( List<OrderStateRspVo> orderStateRspVos) {
        try {
            initMessage();
            AddStringToMessage(MessageConstant.MESSAGE_ID_ORDER_STATE_RES, 2);
            AddShortToMessage((short)orderStateRspVos.size());
            for(OrderStateRspVo orderStateRspVo: orderStateRspVos){
                AddStringToMessage(orderStateRspVo.getOrderNo(), 14);
                AddStringToMessage(orderStateRspVo.getResult(), 2);
            }
            byte[] msg = trimMessage();
            logger.info("成功构造消息37! ");
            return msg;
        } catch (Exception e) {
            logger.error("构造消息37错误! " + e);
            return null;
        }
    }	
}
