package com.goldsign.escommu.message;

import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.vo.OperTypeRspVo;
import org.apache.log4j.Logger;

public class ConstructMessage31 extends ConstructMessageBase {

    private static Logger logger = Logger.getLogger(ConstructMessage31.class.getName());

    public ConstructMessage31() {
        super();
    }

    public byte[] constructMessage(OperTypeRspVo operTypeRspVo) {
        try {
            initMessage();
            AddStringToMessage(MessageConstant.MESSAGE_ID_OPER_TYPE_RES, 2);
            AddStringToMessage(operTypeRspVo.getTypeCode(), 3);


            byte[] msg = trimMessage();
            logger.info("成功构造消息31! ");
            return msg;
        } catch (Exception e) {
            logger.error("构造消息31错误! " + e);
            return null;
        }
    }
}
