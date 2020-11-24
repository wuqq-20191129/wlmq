package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.CiscoDao;
import com.goldsign.systemmonitor.dao.CommandModuleMappingDao;
import com.goldsign.systemmonitor.vo.CiscoVo;
import java.util.Vector;
import org.apache.log4j.Logger;

public class HandlerCommandCisco3560 extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandCisco3560.class);

    public HandlerCommandCisco3560() {
        super();
    }

    private String[] getLineInfoForCisco3360(Vector lines) {
        String line;
        int index;
        String[] values = new String[FrameDBConstant.Command_find_cisco3360_keys_device.length + 2];
        int len = values.length;
        String value;
        String ip = "";
        boolean isNeedNext = false;
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            if (this.isIncludeKeyInLine(FrameDBConstant.Command_find_cisco3360_key, line)) {
                isNeedNext = true;
                continue;

            }
            if (isNeedNext) {
                value = FrameDBConstant.Status_failure;
                if (this.isIncludeKeyInLine(FrameDBConstant.Command_find_cisco3360_key_status_2, line)) {
                    value = FrameDBConstant.Status_normal;
                }
                values[0] = value;
                isNeedNext = false;
                continue;
            }
            index = this.getIndexFromIncludeKeysInLine(FrameDBConstant.Command_find_cisco3360_keys_device, line);
            if (index != -1) {
                value = FrameDBConstant.Status_failure;
                if (this.isIncludeKeyInLine(FrameDBConstant.Command_find_cisco3360_key_status_1, line)) {
                    value = FrameDBConstant.Status_normal;
                }
                values[index + 1] = value;
                continue;
            }
            if (this.isIncludeKeysInLine(FrameDBConstant.Command_find_cisco3360_keys_ip, line)) {
                ip = this.getLineField(line, 2, FrameDBConstant.Command_seperator_space);
                ip = this.getIp(ip);
                values[len - 1] = ip;
                continue;
            }



        }
        return values;

    }

    private CiscoVo getVo(String[] lineInfo) {
        CiscoVo vo = new CiscoVo();

        vo.setPowerStatus(lineInfo[0]);
        vo.setFanStatus(lineInfo[1]);
        vo.setTemperatureStatus(lineInfo[2]);
        vo.setIp(lineInfo[3]);
        String status = this.getStatus(vo);
        vo.setStatus(status);
        String statusDate = this.getStatusDate();
        vo.setStatusDate(statusDate);
        vo.setRemark("");
        vo.setRedundancy("");
        vo.setMainframe("");
        return vo;
    }

    private String getStatus(CiscoVo vo) {
        if (vo.getPowerStatus().equals(FrameDBConstant.Status_normal)
                && vo.getFanStatus().equals(FrameDBConstant.Status_normal)
                && vo.getTemperatureStatus().equals(FrameDBConstant.Status_normal)) {
            return FrameDBConstant.Status_normal;
        }
        return FrameDBConstant.Status_failure;
    }

    public void handleCommand(String command, Vector lines, String fileName) {

        logger.info("处理命令:" + command);
        this.setParameter(command, lines);
        //this.printLines();

        String[] lineInfo = this.getLineInfoForCisco3360(lines);
        CiscoVo vo = this.getVo(lineInfo);

        this.addStatus(vo);
        this.updateMenuStatus(command);

    }

    private void addStatus(CiscoVo vo) {
        CiscoDao dao = new CiscoDao();
        try {
            dao.addStatus(vo);
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
