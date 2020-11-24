package com.goldsign.escommu.message;

import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.MessageCommonUtil;
import java.util.Vector;
import org.apache.log4j.Logger;

public class ConstructMessage50 extends ConstructMessageBase {

    private static Logger logger = Logger.getLogger(ConstructMessage50.class.getName());

    public ConstructMessage50() {
        super();
    }

    public byte[] constructMessage(String ip, String fileName) {
        try {
            initMessage();
            AddStringToMessage(MessageConstant.MESSAGE_ID_FILE_FTP, 2);
            AddStringToMessage(DateHelper.currentToYYYYMMDDHHMMSS(), 14);
            AddStringToMessage(ip, 15);
            AddStringToMessage(fileName, 24);

            byte[] msg = trimMessage();
            logger.info("成功构造消息50! ");
            return msg;
        } catch (Exception e) {
            logger.error("构造消息50错误! " + e);
            return null;
        }
    }

    public void constructAndSend(String ip, String fileName) throws Exception {
        byte[] msg = this.constructMessage(ip, fileName);
        Vector readerResult = MessageCommonUtil.getReaderResult(msg);
        MessageCommonUtil.processMessage(ip, readerResult);

    }
}
