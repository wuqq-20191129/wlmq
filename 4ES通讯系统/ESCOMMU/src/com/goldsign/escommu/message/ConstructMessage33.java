package com.goldsign.escommu.message;

import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.vo.CityTypeRspVo;
import org.apache.log4j.Logger;



public class ConstructMessage33 extends ConstructMessageBase{

    private static Logger logger = Logger.getLogger(ConstructMessage33.class.getName());

    public ConstructMessage33() {
        super();
    }

    public byte[] constructMessage(CityTypeRspVo cityTypeRspVo) {
        try {
            initMessage();
            AddStringToMessage(MessageConstant.MESSAGE_ID_CITY_TYPE_RES, 2);
            AddStringToMessage(cityTypeRspVo.getCityCode(), 4);
            AddStringToMessage(cityTypeRspVo.getBusiCode(), 4);
            AddStringToMessage(cityTypeRspVo.getSerKeyVer(),4);
            AddStringToMessage(cityTypeRspVo.getSenderCode(),4);
            AddStringToMessage(cityTypeRspVo.getCardVersion(),4);
            AddStringToMessage(cityTypeRspVo.getAppVersion(),4);
            byte[] msg = trimMessage();
            logger.info("成功构造消息33! ");
            return msg;
        } catch (Exception e) {
            logger.error("构造消息33错误! " + e);
            return null;
        }
    }	
}
