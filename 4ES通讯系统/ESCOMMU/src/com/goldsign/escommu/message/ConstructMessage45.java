package com.goldsign.escommu.message;

import com.goldsign.escommu.env.FileConstant;
import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.vo.FileAudErrVo;
import java.util.List;
import org.apache.log4j.Logger;



public class ConstructMessage45 extends ConstructMessageBase{

    private static Logger logger = Logger.getLogger(ConstructMessage45.class.getName());

    public ConstructMessage45() {
        super();
    }

    public byte[] constructMessage(List<FileAudErrVo> fileAudErrVos) {
        try {
            initMessage();
            AddStringToMessage(MessageConstant.MESSAGE_ID_AUDIT_FILE_REQ, 2);
            AddStringToMessage(FileConstant.AUDIT_FILE_ACC_DEVICE, 5);
            AddShortToMessage((short)fileAudErrVos.size());
            for(FileAudErrVo fileAudErrVo: fileAudErrVos){
                AddStringToMessage(fileAudErrVo.getFileName(), 30);
                AddStringToMessage(fileAudErrVo.getInfoOperator(), 10);
            }
            byte[] msg = trimMessage();
            logger.info("成功构造消息45! ");
            return msg;
        } catch (Exception e) {
            logger.error("构造消息45错误! " + e);
            return null;
        }
    }	
}
