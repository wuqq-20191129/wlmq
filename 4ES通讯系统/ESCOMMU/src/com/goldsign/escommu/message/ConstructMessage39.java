package com.goldsign.escommu.message;

import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.vo.SignCardRspVo;
import java.util.List;
import org.apache.log4j.Logger;



public class ConstructMessage39 extends ConstructMessageBase{

    private static Logger logger = Logger.getLogger(ConstructMessage39.class.getName());

    public ConstructMessage39() {
        super();
    }

    public byte[] constructMessage(List<SignCardRspVo> signCardRspVos) {
        try {
            initMessage();
            AddStringToMessage(MessageConstant.MESSAGE_ID_SIGN_CARD_RES, 2);
            AddShortToMessage((short)signCardRspVos.size());
            for(SignCardRspVo signCardRspVo: signCardRspVos){
                AddStringToMessage(signCardRspVo.getReqNo(), 10);
                AddUtf8StringToMessage(signCardRspVo.getName(), 60);
                AddStringToMessage(signCardRspVo.getGender(), 1);
                AddStringToMessage(signCardRspVo.getCerType(), 1);
                AddStringToMessage(signCardRspVo.getCerNo(), 18);
                AddStringToMessage(signCardRspVo.getCerEffTime(), 8);
            }
            byte[] msg = trimMessage();
            logger.info("成功构造消息39! ");
            return msg;
        } catch (Exception e) {
            logger.error("构造消息39错误! " + e);
            return null;
        }
    }	
}
