package com.goldsign.escommu.message;

import com.goldsign.escommu.env.MessageConstant;
import org.apache.log4j.Logger;

public class ConstructMessage99 extends ConstructMessageBase {

    private static Logger logger = Logger.getLogger(ConstructMessage99.class.getName());

    public ConstructMessage99() {
        super();
    }

    public byte[] constructMessage(String result) {
        try {
            initMessage();
            AddStringToMessage(MessageConstant.MESSAGE_ID_KMS_LOCK_RES, 2);
            AddStringToMessage(result, 1);

            byte[] msg = trimMessage();
            logger.info("成功构造消息99! ");
            return msg;
        } catch (Exception e) {
            logger.error("构造消息99错误! " + e);
            return null;
        }
    }
}
