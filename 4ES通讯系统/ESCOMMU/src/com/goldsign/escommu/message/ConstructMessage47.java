package com.goldsign.escommu.message;

import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.vo.BlacklistRspVo;
import java.util.List;
import org.apache.log4j.Logger;



public class ConstructMessage47 extends ConstructMessageBase{

    private static Logger logger = Logger.getLogger(ConstructMessage47.class.getName());

    public ConstructMessage47() {
        super();
    }

    public byte[] constructMessage(List<BlacklistRspVo> blacklistRspVos) {
        try {
            initMessage();
            AddStringToMessage(MessageConstant.MESSAGE_ID_BLACK_LIST_RES, 2);
            AddShortToMessage((short)blacklistRspVos.size());
            for(BlacklistRspVo blacklistRspVo: blacklistRspVos){
                AddStringToMessage(blacklistRspVo.getFileName(), 30);
            }
            byte[] msg = trimMessage();
            logger.info("成功构造消息47! ");
            return msg;
        } catch (Exception e) {
            logger.error("构造消息47错误! " + e);
            return null;
        }
    }	
}
