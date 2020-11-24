package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.CiscoDao;
import com.goldsign.systemmonitor.dao.CommandModuleMappingDao;
import com.goldsign.systemmonitor.vo.CiscoVo;
import java.util.Vector;
import org.apache.log4j.Logger;

public class HandlerCommandASA5520 extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandASA5520.class);

    public HandlerCommandASA5520() {
        super();
    }

    private Vector getLineInfoForASA5520(Vector lines) {
        String line;
        Vector v = new Vector();
        String[] values = new String[4];

        String value;
        int j = -100;;
        int k = -100;;
        boolean isNeedNext = false;
        String status = FrameDBConstant.Status_failure;;
        String status1 = FrameDBConstant.Status_failure;
        String redundancy = "";
        String mainFrame = "";
        String ip = "";
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            //ip
            if (this.isIncludeKeysInLine(FrameDBConstant.Command_find_ASA5520_keys_ip, line)) {
                value = this.getLineField(line, 2, FrameDBConstant.Command_seperator_space);
                value = this.getIp(value);
                ip = value;
            }
            if (this.isIncludeKeyInLine(FrameDBConstant.Command_find_ASA5520_key, line)) {
                j = i;
                isNeedNext = true;
                continue;
            }
            //状态1
            if (isNeedNext && i == j + 2) {
                value = FrameDBConstant.Status_failure;
                if (this.isIncludeKeyInLine(FrameDBConstant.Command_find_ASA5520_key_status, line)) {
                    value = FrameDBConstant.Status_normal;
                }
                status = value;
                continue;
            }
            //状态2
            if (isNeedNext && i == j + 3) {
                value = FrameDBConstant.Status_failure;
                if (this.isIncludeKeyInLine(FrameDBConstant.Command_find_ASA5520_key_status, line)) {
                    value = FrameDBConstant.Status_normal;
                }
                status1 = value;
                isNeedNext = false;
                continue;
            }
            //主机信息	  
            if (this.isIncludeKeyInLine(FrameDBConstant.Command_find_ASA5520_key_1, line)) {
                value = this.getLineField(line, 3, FrameDBConstant.Command_seperator_space);
                mainFrame = value;
                k = i;
                isNeedNext = true;
                continue;
            }
            //冗余信息
            if (isNeedNext && i == k + 1) {
                value = this.getLineFieldBefore(line, FrameDBConstant.Command_find_ASA5520_key_redundaney);
                redundancy = value;
                isNeedNext = false;
                continue;
            }
        }
        if (status.equals(FrameDBConstant.Status_failure) || status1.equals(FrameDBConstant.Status_failure)) {
            status = FrameDBConstant.Status_failure;
        } else {
            status = FrameDBConstant.Status_normal;
        }
        values[0] = ip;
        values[1] = status;
        values[2] = redundancy;
        values[3] = mainFrame;
        v.add(values);
        return v;
    }

    private Vector getVos(Vector lineInfos) {
        String[] values;
        CiscoVo vo;
        Vector v = new Vector();
        for (int i = 0; i < lineInfos.size(); i++) {
            values = (String[]) lineInfos.get(i);
            vo = this.getVo(FrameDBConstant.Command_ASA5520_ips[i], values);
            v.add(vo);
        }
        return v;
    }

    private CiscoVo getVo(String ip, String[] lineInfo) {
        CiscoVo vo = new CiscoVo();

        vo.setPowerStatus(lineInfo[1]);
        vo.setFanStatus(lineInfo[1]);
        vo.setTemperatureStatus(lineInfo[1]);
        vo.setIp(lineInfo[0]);
        String status = this.getStatus(vo);
        vo.setStatus(status);
        String statusDate = this.getStatusDate();
        vo.setStatusDate(statusDate);
        vo.setRemark("");
        vo.setRedundancy(lineInfo[2]);
        vo.setMainframe(lineInfo[3]);
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
        Vector lineInfos = this.getLineInfoForASA5520(lines);
        Vector vos = this.getVos(lineInfos);
        this.addStatuses(vos);
        this.updateMenuStatus(command);
    }

    private void addStatuses(Vector vos) {
        CiscoDao dao = new CiscoDao();
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
