/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.download;

import com.goldsign.lib.jms.Message.ConstructMessage12;
import com.goldsign.lib.jms.vo.JMSConfig;
import com.goldsign.lib.jms.vo.MessageInfo;
import com.goldsign.settle.realtime.frame.audit.AuditBase;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.dao.LccIpDao;
import com.goldsign.settle.realtime.frame.dao.MessageQueDao;
import com.goldsign.settle.realtime.frame.vo.LccIpVo;
import java.util.Vector;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class DownloadAudit {

    private static Logger logger = Logger.getLogger(DownloadAudit.class.getName());
    private static String CLASS_PREFIX = "com.goldsign.settle.realtime.frame.audit.Audit";
    private static String MESSAGE_TYPE_FILE = "12";
    private static String MESSAGE_TYPE_SUB_AUDIT_FTP = "01";
    private static String MESSAGE_TYPE_SUB_AUDIT_ERR = "02";
    private static String MESSAGE_TYPE_SUB_AUDIT_FTP_NAME = "FTP审计";
    private static String MESSAGE_TYPE_SUB_AUDIT_ERR_NAME = "ERR审计";

    public void download(String balanceWaterNo, String dataType) throws Exception {
        //生成审计数据文件
        String className = CLASS_PREFIX + dataType;
        AuditBase ab = (AuditBase) Class.forName(className).newInstance();
        Vector<String> fileNames = ab.genAuditFiles(balanceWaterNo);
        MessageInfo msgInfo;
        //下发审计文件
        // JMSConfig config = this.getConfig();
        for (String fileName : fileNames) {
            // String ip = this.getIp(fileName);
            msgInfo = this.getMsgInfo(fileName,dataType);
            if (!this.isExistDownloadIp(msgInfo)) {
                throw new Exception("审计文件：" + fileName + "：没有配置对应的下发线路代码。");
            }
            logger.info("清算流水号：" + balanceWaterNo + "：准备下发审计文件：" + fileName);
            ConstructMessage12 cm = new ConstructMessage12();;
            byte[] datas = cm.constructMessage(fileName);
            msgInfo.setDatas(datas);
            this.sendMsg(msgInfo);
            logger.info("清算流水号：" + balanceWaterNo + "：成功下发审计文件：" + fileName+":下发地址："+msgInfo.getIp());

            //cm.constructAndSend(config, msgInfo.getIp(), fileName, msgInfo);
            // cm.constructAndSend(config, msgInfo.getIp(), fileName, msgInfo);
        }

    }

    private void sendMsg(MessageInfo msgInfo) throws Exception {
        MessageQueDao dao = new MessageQueDao();
        dao.insert(msgInfo);

    }

    public void downloadMobileTrx(String balanceWaterNo, String dataType) throws Exception {
        //生成审计数据文件
        String className = CLASS_PREFIX + dataType;
        AuditBase ab = (AuditBase) Class.forName(className).newInstance();
        Vector<String> fileNames = ab.genAuditFiles(balanceWaterNo);
        //下发审计文件
        JMSConfig config = this.getConfig();
        MessageInfo msgInfo;
        for (String fileName : fileNames) {
            // String ip = this.getIpMobile();
            msgInfo = this.getMessageInfoMobile(fileName);
            if (!this.isExistDownloadIp(msgInfo)) {
                throw new Exception("手机卡消费文件：" + fileName + "：没有配置对应的下发线路代码。");
            }
            logger.info("清算流水号：" + balanceWaterNo + "：准备下发手机卡消费文件：" + fileName+" 下发地址："+msgInfo.getIp());
            ConstructMessage12 cm = new ConstructMessage12();;
            
            byte[] datas = cm.constructMessage(fileName);
            msgInfo.setDatas(datas);
            this.sendMsg(msgInfo);
            logger.info("清算流水号：" + balanceWaterNo + "：成功下发手机卡消费文件：" + fileName);
            
           // cm.constructAndSend(config, msgInfo.getIp(), fileName, msgInfo);
        }

    }

    private boolean isExistDownloadIp(MessageInfo msgInfo) {
        if (msgInfo == null || msgInfo.getIp() == null || msgInfo.getIp().length() == 0) {
            return false;
        }
        return true;
    }

    private JMSConfig getConfig() {
        JMSConfig config = new JMSConfig();
        config.setJmsUrl(FrameCodeConstant.JMS_URL);
        config.setContextFac(FrameCodeConstant.JMS_CONTEXT_FACTORY);
        config.setConnFac(FrameCodeConstant.JMS_CONNECTION_FACTORY);
        config.setQueueName(FrameCodeConstant.JMS_QUEUE_NAME);
        return config;
    }

    private String getIp(String fileName) throws Exception {
        LccIpDao dao = new LccIpDao();
        String lineId = "";
        if (fileName.startsWith(FrameFileHandledConstant.AUD_FILE_PREFIX_FTP)) {
            lineId = fileName.substring(3, 5);
        }
        if (fileName.startsWith(FrameFileHandledConstant.AUD_FILE_PREFIX_ER)) {
            lineId = fileName.substring(2, 4);
        }
        if (fileName.startsWith(FrameFileHandledConstant.AUD_FILE_PREFIX_SDF)) {
            lineId = fileName.substring(3, 5);
        }
        LccIpVo vo = dao.getConfigIp(lineId);
        return vo.getIp();
    }

    private MessageInfo getMsgInfo(String fileName, String dataType) throws Exception {
        LccIpDao dao = new LccIpDao();
        MessageInfo msgInfo;
        String lineId = "";
        if (fileName.startsWith(FrameFileHandledConstant.AUD_FILE_PREFIX_FTP)) {
            lineId = fileName.substring(3, 5);
        }
        if (fileName.startsWith(FrameFileHandledConstant.AUD_FILE_PREFIX_ER)) {
            lineId = fileName.substring(2, 4);
        }
        if (fileName.startsWith(FrameFileHandledConstant.AUD_FILE_PREFIX_SDF)) {
            lineId = fileName.substring(3, 5);
        }
        LccIpVo vo = dao.getConfigIp(lineId);
        msgInfo = this.setMessageInfo(lineId, "00", vo.getIp());
        msgInfo.setMsgType(MESSAGE_TYPE_FILE);
        msgInfo.setMsgTypeSub(this.getMsgTypeSub(dataType));
        msgInfo.setRemark(this.getRemark(dataType));

        return msgInfo;
    }

    private String getMsgTypeSub(String dataType) {

        if (dataType.equals(AuditBase.DATA_TYPE_FTP)) {
            return MESSAGE_TYPE_SUB_AUDIT_FTP;
        }
        if (dataType.equals(AuditBase.DATA_TYPE_ERROR)) {
            return MESSAGE_TYPE_SUB_AUDIT_ERR;
        }
        return "";

    }

    private String getRemark(String dataType) {

        if (dataType.equals(AuditBase.DATA_TYPE_FTP)) {
            return MESSAGE_TYPE_SUB_AUDIT_FTP_NAME;
        }
        if (dataType.equals(AuditBase.DATA_TYPE_ERROR)) {
            return MESSAGE_TYPE_SUB_AUDIT_ERR_NAME;
        }
        return "";

    }

    private MessageInfo getMsgInfo(String fileName) throws Exception {
        LccIpDao dao = new LccIpDao();
        MessageInfo msgInfo;
        String lineId = "";
        if (fileName.startsWith(FrameFileHandledConstant.AUD_FILE_PREFIX_FTP)) {
            lineId = fileName.substring(3, 5);
        }
        if (fileName.startsWith(FrameFileHandledConstant.AUD_FILE_PREFIX_ER)) {
            lineId = fileName.substring(2, 4);
        }
        if (fileName.startsWith(FrameFileHandledConstant.AUD_FILE_PREFIX_SDF)) {
            lineId = fileName.substring(3, 5);
        }
        LccIpVo vo = dao.getConfigIp(lineId);
        msgInfo = this.setMessageInfo(lineId, "00", vo.getIp());

        return msgInfo;
    }

    private MessageInfo setMessageInfo(String lineId, String stationId, String ip) {
        MessageInfo msgInfo = new MessageInfo();
        msgInfo.setLineId(lineId);
        msgInfo.setStationId(stationId);
        msgInfo.setIp(ip);
        return msgInfo;
    }

    private String getIpMobile() throws Exception {

        LccIpDao dao = new LccIpDao();

        LccIpVo vo = dao.getConfigIp(FrameCodeConstant.LINE_ID_MOBILE);
        return vo.getIp();
    }

    private MessageInfo getMessageInfoMobile(String fileName) throws Exception {

        LccIpDao dao = new LccIpDao();
        String lineId =this.getLineIdFromFileNameForMobile(fileName);
        LccIpVo vo = dao.getConfigIp(lineId);
        MessageInfo msgInfo = this.setMessageInfo(lineId, "00", vo.getIp());
        return msgInfo;
    }
    private String getLineIdFromFileNameForMobile(String fileName){
        if(fileName ==null || fileName.length()==0)
            return "";
        String appPlatformFlag = fileName.substring(6,7);
        if(appPlatformFlag.equals(FrameCodeConstant.APP_PLATFORM_METRO))
            return FrameCodeConstant.LINE_ID_MOBILE;
        if(appPlatformFlag.equals(FrameCodeConstant.APP_PLATFORM_BANK))
            return FrameCodeConstant.LINE_ID_MOBILE_BANK;
        return "";
    }

    public JMSConfig getIBMMQConfig() {
        JMSConfig config = new JMSConfig();
        config.setJmsUrl("10.99.2.200:1414/SYSTEM.DEF.SVRCONN");
        config.setContextFac("com.ibm.mq.jms.context.WMQInitialContextFactory");
        config.setConnFac("queueManagerAcc");
        config.setQueueName("queueAcc");
        return config;
    }

    public static void main(String args[]) {
        DownloadAudit da = new DownloadAudit();
        JMSConfig config = da.getIBMMQConfig();
        try {
            da.download("2013122001", "");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DownloadAudit.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
