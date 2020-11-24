package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.ClusterDao;
import com.goldsign.systemmonitor.dao.CommandModuleMappingDao;
import com.goldsign.systemmonitor.vo.ClusterVo;
import java.util.Vector;
import org.apache.log4j.Logger;

public class HandlerCommandHa extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandHa.class);

    public HandlerCommandHa() {
        super();
        // TODO Auto-generated constructor stub
    }

    private Vector getStatusInfo(Vector lineInfos, String fileName) {
        Vector v = new Vector();
        String[] fieldValues;
        ClusterVo vo;
        for (int i = 0; i < lineInfos.size(); i++) {
            fieldValues = (String[]) lineInfos.get(i);
            vo = this.getVo(fieldValues, fileName);
            v.add(vo);
        }
        return v;
    }

    private ClusterVo getVo(String[] fieldValues, String fileName) {
        ClusterVo vo = new ClusterVo();
        vo.setResourceName(fieldValues[0]);
        vo.setNodeName(fieldValues[3]);
        vo.setStatusDesc(fieldValues[2]);
        if(fieldValues.length!=4){
            String status = FrameDBConstant.Status_failure;
        }else if(fieldValues.length==2){
            vo.setNodeName(fieldValues[2]);
        }else{
            status = FrameDBConstant.Status_normal;
        }

        vo.setStatus(status);
        String ip = this.getIpFromFileName(fileName);
        vo.setIp(ip);

        String statusDate = this.getStatusDate();
        vo.setStatusDate(statusDate);

        vo.setRemark("");

        return vo;

    }

    public void handleCommand(String command, Vector lines, String fileName) {

        logger.info("处理命令:" + command);
        this.setParameter(command, lines);
        //this.printLines();

        int[] fieldIndexes = {1, 2, 3, 4};
        Vector lineInfos = this.getLineInfo(lines, FrameDBConstant.Command_find_scstat_common,
                FrameDBConstant.Command_find_scstat_keys,
                FrameDBConstant.Command_seperator_space, fieldIndexes);
        Vector statusInfos = this.getStatusInfo(lineInfos, fileName);
        this.addStatuses(statusInfos);
        this.updateMenuStatus(command);
    }

    private void addStatuses(Vector vos) {
        ClusterDao dao = new ClusterDao();
        try {
            dao.addStatuses(vos);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void updateMenuStatus(String command) {
        CommandModuleMappingDao dao = new CommandModuleMappingDao();
        try {
            dao.updateMenuStatus(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
