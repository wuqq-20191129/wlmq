package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.CommandModuleMappingDao;
import com.goldsign.systemmonitor.dao.UpsDao;
import com.goldsign.systemmonitor.vo.UpsVo;
import java.util.Vector;
import org.apache.log4j.Logger;

public class HandlerCommandUps extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandHa.class);

    public HandlerCommandUps() {
        super();
    }

    private Vector getStatusInfo(Vector lineInfos, String fileName) {
        Vector v = new Vector();
        String[] fieldValues;
        UpsVo vo;
        for (int i = 0; i < lineInfos.size(); i++) {
            fieldValues = (String[]) lineInfos.get(i);
            vo = this.getVo(fieldValues, fileName);
            v.add(vo);
        }
        return v;
    }

    private UpsVo getVo(String[] fieldValues, String fileName) {
        UpsVo vo = new UpsVo();
        vo.setIp(fieldValues[0]);
        vo.setUpsLoad(fieldValues[1]);
        String status = FrameDBConstant.Status_normal;
        vo.setStatus(status);
        String statusDate = this.getStatusDate();
        vo.setStatusDate(statusDate);
        vo.setRemark("");
        return vo;
    }

    private Vector getLineInfoForUps(Vector lines, String seperator, int[] fieldIndexes) {
        Vector v = new Vector();
        String line;
        String[] fieldValues;
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);

            fieldValues = this.getLineFields(line, fieldIndexes, seperator);
            v.add(fieldValues);

        }
        return v;
    }

    public void handleCommand(String command, Vector lines, String fileName) {

        logger.info("处理命令:" + command);
        this.setParameter(command, lines);
        int[] fieldIndexes = {0, 3};
        Vector lineInfos = this.getLineInfoForUps(lines,FrameDBConstant.Command_seperator_colon, fieldIndexes);
        Vector statusInfos = this.getStatusInfo(lineInfos, fileName);
        this.addStatuses(statusInfos);
        this.updateMenuStatus(command);
    }

    private void addStatuses(Vector vos) {
        UpsDao dao = new UpsDao();
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
