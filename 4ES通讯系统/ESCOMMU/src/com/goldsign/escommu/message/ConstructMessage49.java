package com.goldsign.escommu.message;

import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.vo.SamCardRspVo;
import java.util.List;
import org.apache.log4j.Logger;



public class ConstructMessage49 extends ConstructMessageBase{

    private static Logger logger = Logger.getLogger(ConstructMessage49.class.getName());

    public ConstructMessage49() {
        super();
    }

    public byte[] constructMessage(List<SamCardRspVo> samCardRspVos) {
        try {
            initMessage();
            AddStringToMessage(MessageConstant.MESSAGE_ID_SAM_CARD_RES, 2);
            AddShortToMessage((short)samCardRspVos.size());
            for(SamCardRspVo samCardRspVo: samCardRspVos){
                AddStringToMessage(samCardRspVo.getFileName(), 30);
            }
            byte[] msg = trimMessage();
            logger.info("成功构造消息49! ");
            return msg;
        } catch (Exception e) {
            logger.error("构造消息49错误! " + e);
            return null;
        }
    }	
}
