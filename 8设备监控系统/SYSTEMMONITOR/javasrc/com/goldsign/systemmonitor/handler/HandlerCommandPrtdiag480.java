package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.CommandModuleMappingDao;
import com.goldsign.systemmonitor.dao.HardwareDao;
import com.goldsign.systemmonitor.vo.HardwareVo;
import java.util.Vector;
import org.apache.log4j.Logger;



public class HandlerCommandPrtdiag480 extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandPrtdiag480.class);

    public HandlerCommandPrtdiag480() {
        super();
    }

    public void handleCommand(String command, Vector lines, String fileName) {

        logger.info("处理命令:" + command);
        this.setParameter(command, lines);
        //this.printLines();

        HardwareVo vo = new HardwareVo();
        String status = this.getStatus(lines);
        String ip = this.getIpFromFileName(fileName);
        String statusDate = this.getStatusDate();
        String remark = "";
        vo.setIp(ip);
        vo.setStatus(status);
        vo.setStatusDate(statusDate);
        vo.setRemark(remark);
        this.addStatus(vo);
        this.updateMenuStatus(command);

    }

    private void addStatus(HardwareVo vo) {
        HardwareDao dao = new HardwareDao();
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

    private String getStatus(Vector lines) {

        String status = FrameDBConstant.Status_failure;
        String line;
        int j = -1;
        String fieldValue;
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            if (this.isIncludeKeyInLine(FrameDBConstant.Command_find_prtdiag480_key, line)) {
                j = i + 2;
            }
            if (i == j) {
                fieldValue = this.getLineField(line, 1, "  ");
                if (this.isIncludeKeyInLine(FrameDBConstant.Command_find_prtdiag480_key_1, fieldValue)) {
                    status = FrameDBConstant.Status_normal;
                    break;
                }
            }
        }
        return status;
    }
}
