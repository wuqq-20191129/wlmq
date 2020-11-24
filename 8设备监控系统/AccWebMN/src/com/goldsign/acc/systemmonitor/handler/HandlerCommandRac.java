package com.goldsign.acc.systemmonitor.handler;

import com.goldsign.acc.app.system.controller.ClusterController;
import com.goldsign.acc.app.system.entity.Cluster;
import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.frame.entity.EmailContent;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.SpringContextUtil;
import org.apache.log4j.Logger;

import java.util.Vector;

/**
 * refactor by zhongzq
 * 对应集群模块
 */
public class HandlerCommandRac extends HandlerCommandBase {

    public static final int INDEX_DATABASE = 1;
    public static final int INDEX_COMMU_STATUS = 2;
    public static final int INDEX_HOST_IP = 3;
    public static final int INDEX_NODE_NAME = 4;
    static Logger logger = Logger.getLogger(HandlerCommandRac.class);

    public HandlerCommandRac() {
        super();
        // TODO Auto-generated constructor stub
    }

    private Vector getStatusInfo(Vector lineInfos, String fileName) {
        Vector v = new Vector();
        String[] fieldValues;
        Cluster vo;
        for (int i = 0; i < lineInfos.size(); i++) {
            fieldValues = (String[]) lineInfos.get(i);
            vo = this.getVo(fieldValues, fileName);
            v.add(vo);
        }
        return v;
    }

    /**
     * @param fieldValues 0:数据库 1：状态 2：主机地址 3：节点名称/服务器名称
     * @param fileName
     * @return
     */
    private Cluster getVo(String[] fieldValues, String fileName) {
        Cluster vo = new Cluster();
        vo.setResourceName(fieldValues[0]);
        vo.setNodeName(fieldValues[3]);
        vo.setStatusDesc("");
        //状态判断依据如果行信息中索引1非"online"则视为异常
        if (!FrameDBConstant.COMMAND_CLUSTER_KEY_STATUS.equals(fieldValues[1])) {
            vo.setStatus(FrameDBConstant.STATUS_FAILURE);
        } else {
            vo.setStatus(FrameDBConstant.STATUS_NORMAL);
        }
        String ip = fieldValues[2];
        vo.setIp(ip);
        String statusDate = this.getStatusDate();
        vo.setStatusDate(statusDate);
        vo.setRemark("");
        return vo;

    }

    @Override
    public void handleCommand(String command, Vector lines, String fileName) {
        logger.info("处理命令:" + command);
        //行信息根据空格分隔索引  eg： 0：resource:   1：oracle   2：online 3：10.99.1.11:    4：srvstl1
        int[] fieldIndexes = {INDEX_DATABASE, INDEX_COMMU_STATUS, INDEX_HOST_IP, INDEX_NODE_NAME};
        Vector lineInfos = this.getLineInfo(lines, FrameDBConstant.COMMAND_FIND_CLUSTER_KEY,
                FrameDBConstant.COMMAND_FIND_CLUSTER_ALL_KEYS,
                FrameDBConstant.COMMAND_SEPARATOR_SPACE, fieldIndexes);
        Vector statusInfos = this.getStatusInfo(lineInfos, fileName);
        this.addStatuses(statusInfos);
        this.updateMenuStatus(command);
        if (FrameDBConstant.EMAIL_FEATURE_USE) {
            emailPreHandles(statusInfos);
        }
    }

    @Override
    public EmailContent convertToEmailContents(Object t, String classPath) {
        Cluster vo = (Cluster) t;
        EmailContent emailContent = new EmailContent();
        emailContent.setClassSimpleName(classPath);
        emailContent.setModule((String) FrameDBConstant.MODULE_NAME.get(classPath));
        emailContent.setName((String) FrameDBConstant.SERVER_NAME.get(vo.getIp()));
        emailContent.setIp(vo.getIp());
        emailContent.setPasreTime(vo.getStatusDate());
        emailContent.setStatus(DBUtil.getTextByCode(vo.getStatus(), FrameDBConstant.STATUS_NAME));
        return emailContent;
    }

    private void addStatuses(Vector vos) {
        try {
            ClusterController clusterController = SpringContextUtil.getBean("ClusterController");
            System.out.println("*****come into addStatuses**********");
            clusterController.addStatuses(vos);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


}
