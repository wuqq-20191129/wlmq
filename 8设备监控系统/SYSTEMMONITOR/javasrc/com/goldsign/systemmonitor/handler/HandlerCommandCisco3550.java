package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.CiscoDao;
import com.goldsign.systemmonitor.dao.CommandModuleMappingDao;
import com.goldsign.systemmonitor.vo.CiscoVo;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.log4j.Logger;


public class HandlerCommandCisco3550 extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandCisco3550.class);

    public HandlerCommandCisco3550() {
        super();
    }

    private String[] getLineInfoForCisco3350(Vector lines) {
        String line;
        int index;
        String[] values = new String[FrameDBConstant.Command_find_cisco3350_keys_device.length + 1];
        int len = values.length;
        String value;
        String ip = "";
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            index = this.getIndexFromIncludeKeysInLine(FrameDBConstant.Command_find_cisco3350_keys_device, line);
            if (index != -1) {
                value = FrameDBConstant.Status_failure;
                if (this.isIncludeKeyInLine(FrameDBConstant.Command_find_cisco3350_key_status, line)) {
                    value = FrameDBConstant.Status_normal;
                }
                values[index] = value;
                continue;
            }
            if (this.isIncludeKeysInLine(FrameDBConstant.Command_find_cisco3350_keys_ip, line)) {
                ip = this.getLineField(line, 2, FrameDBConstant.Command_seperator_space);
                ip = this.getIp(ip);
                values[len - 1] = ip;
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
        String[] lineInfo = this.getLineInfoForCisco3350(lines);
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
