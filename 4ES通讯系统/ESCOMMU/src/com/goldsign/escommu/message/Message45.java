package com.goldsign.escommu.message;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.LogConstant;
import com.goldsign.escommu.env.MessageConstant;
import com.goldsign.escommu.dao.EsFileDao;
import com.goldsign.escommu.exception.CommuException;
import com.goldsign.escommu.util.CharUtil;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.LogDbUtil;
import com.goldsign.escommu.util.MessageCommonUtil;
import com.goldsign.escommu.vo.EsFileReqVo;
import com.goldsign.escommu.vo.EsFileRspVo;
import com.goldsign.escommu.vo.SynchronizedControl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author lenovo
 */
public class Message45 extends MessageBase {

    private static Logger logger = Logger.getLogger(Message45.class.getName());
    public static SynchronizedControl SYNCONTROL = new SynchronizedControl();

    public void run() throws Exception {
        String result = LogConstant.RESULT_HDL_SUCESS;
        this.level = AppConstant.LOG_LEVEL_INFO;
        try {
            this.hdlStartTime = System.currentTimeMillis();

            this.process();

            this.hdlEndTime = System.currentTimeMillis();
        } catch (Exception e) {
            result = LogConstant.RESULT_HDL_FAIL;
            this.hdlEndTime = System.currentTimeMillis();
            this.level = AppConstant.LOG_LEVEL_ERROR;
            this.remark = e.getMessage();
            throw e;
        } finally {//记录处理日志
            LogDbUtil.logForDbDetail(LogConstant.MESSAGE_ID_ES_FILE_REQ, this.messageFrom,
                    this.hdlStartTime, this.hdlEndTime, result, this.threadNum,
                    this.level, this.remark);
        }
    }

    public void process() throws Exception {
        DateHelper.screenPrint("处理" + MessageCommonUtil.getMessageName(MessageConstant.MESSAGE_ID_ES_FILE_REQ) + "消息");
        synchronized (SYNCONTROL) {
            try {

                List<EsFileReqVo> esFileReqVos = getEsFileReq();
                EsFileDao esFileDao = new EsFileDao();
                List<EsFileRspVo> esFileRspVos = esFileDao.esFileNotice(esFileReqVos);
                
            } catch (CommuException e) {
                DateHelper.screenPrintForEx(DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date()) + "  " + thisClassName + " error! messageSequ:" + messageSequ + ". " + e);
                logger.error(thisClassName + " error! messageSequ:" + messageSequ + ". " + e);
                throw e;
            } 
        }
    }

    private List<EsFileReqVo> getEsFileReq() throws CommuException{
       
        List<EsFileReqVo> esFileReqVos = new ArrayList<EsFileReqVo>();
        String deviceId = CharUtil.trimStr(this.getCharString(2, 5));
        int recordNum = this.getShort(7);
        for(int i=0; i<recordNum; i++){
            EsFileReqVo esFileReqVo = new EsFileReqVo();
            String fileName = CharUtil.trimStr(this.getCharString(9+i*40, 30));
            String operCode = CharUtil.trimStr(this.getCharString(39+i*40, 10));
            esFileReqVo.setDeviceId(deviceId);
            esFileReqVo.setFileName(fileName);
            esFileReqVo.setOperCode(operCode);
            
            esFileReqVos.add(esFileReqVo);
        }
     
        return esFileReqVos;
    }
}
