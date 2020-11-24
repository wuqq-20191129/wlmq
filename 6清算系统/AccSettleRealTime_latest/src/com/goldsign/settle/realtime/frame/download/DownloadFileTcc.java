/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.download;

import com.goldsign.lib.jms.Message.ConstructMessage22;
import com.goldsign.lib.jms.vo.MessageInfo;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.dao.FileLogTccDao;

import com.goldsign.settle.realtime.frame.dao.LccIpDao;
import com.goldsign.settle.realtime.frame.dao.MessageQueDao;
import com.goldsign.settle.realtime.frame.file.FileTccBase;
import com.goldsign.settle.realtime.frame.vo.LccIpVo;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class DownloadFileTcc extends DownloadFileTccBase{

    private static String CLASS_PREFIX = "com.goldsign.settle.realtime.app.download.";
    private static String MESSAGE_TYPE_FILE = "22";
    //private static String MESSAGE_REMARK = "5分钟车站进站量";
    private static Logger logger = Logger.getLogger(DownloadFileTcc.class.getName());

    public void download(String balanceWaterNo, String dataType, String fileNameBase) throws Exception {
        //生成TCC数据文件
        String className = CLASS_PREFIX + dataType;
        FileTccBase ab = (FileTccBase) Class.forName(className).newInstance();
        String fileName = ab.genFileTcc(balanceWaterNo, fileNameBase);
        MessageInfo msgInfo;
        //下发tcc文件

        // String ip = this.getIp(fileName);
        msgInfo = this.getMsgInfo(fileName, dataType);
        if (!this.isExistDownloadIp(msgInfo)) {
            throw new Exception("TCC历史数据：" + fileName + "：没有配置对应的下发线路代码。");
        }
        logger.info("清算流水号：" + balanceWaterNo + "：准备下发TCC历史数据：" + fileName);
        ConstructMessage22 cm = new ConstructMessage22();;
        byte[] datas = cm.constructMessage(fileName, balanceWaterNo);
        msgInfo.setDatas(datas);
        this.sendMsg(msgInfo);
        //更新下发状态
        this.updateForDownload(fileName, balanceWaterNo);

        logger.info("清算流水号：" + balanceWaterNo + "：成功下发TCC历史数据：" + fileName + ":下发地址：" + msgInfo.getIp());

    }

    public int updateForDownload(String fileName, String balanceWaterNo) throws Exception {
        FileLogTccDao dao = new FileLogTccDao();
        int n = dao.updateForDownload(fileName, balanceWaterNo);
        return n;
    }

   

    private void sendMsg(MessageInfo msgInfo) throws Exception {
        MessageQueDao dao = new MessageQueDao();
        dao.insert(msgInfo);

    }

    private MessageInfo getMsgInfo(String fileName, String dataType) throws Exception {
        LccIpDao dao = new LccIpDao();
        MessageInfo msgInfo;
        String lineId = FrameCodeConstant.LINE_ID_TCC;

        LccIpVo vo = dao.getConfigIp(lineId);
        msgInfo = this.setMessageInfo(lineId, "00", vo.getIp());
        msgInfo.setMsgType(MESSAGE_TYPE_FILE);
        msgInfo.setMsgTypeSub("");
        msgInfo.setRemark(this.getDataTypeName(dataType));

        return msgInfo;
    }

    private boolean isExistDownloadIp(MessageInfo msgInfo) {
        if (msgInfo == null || msgInfo.getIp() == null || msgInfo.getIp().length() == 0) {
            return false;
        }
        return true;
    }

    private MessageInfo setMessageInfo(String lineId, String stationId, String ip) {
        MessageInfo msgInfo = new MessageInfo();
        msgInfo.setLineId(lineId);
        msgInfo.setStationId(stationId);
        msgInfo.setIp(ip);
        return msgInfo;
    }

}
