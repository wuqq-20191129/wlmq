package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.CommandModuleMappingDao;
import com.goldsign.systemmonitor.dao.HardwareDao;
import com.goldsign.systemmonitor.handler.HandlerCommandBase;
import com.goldsign.systemmonitor.vo.HardwareVo;
import java.util.Vector;
import org.apache.log4j.Logger;

public class HandlerCommandHardware extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandHardware.class);

    public HandlerCommandHardware() {
        super();
        // TODO Auto-generated constructor stub
    }
    /*
     private String getStatus(Vector lines) {
     String line;
     String status = FrameDBConstant.Status_failure;
     for (int i = 0; i < lines.size(); i++) {
     line = (String) lines.get(i);
     if (this.isIncludeKeyInLine(FrameDBConstant.Command_find_prtdiag6800_key, line)) {
     status = FrameDBConstant.Status_normal;
     }

     }
     return status;

     }
     */

    private String getStatus(Vector lines) {
        //String line;
        //line = this.getFailureInfo(lines);
        String status = FrameDBConstant.Status_failure;
        if (lines.isEmpty()) {
            return FrameDBConstant.Status_normal;
        }
        return FrameDBConstant.Status_failure;

    }

    private String getFailureInfo(Vector lines) {
        return this.getContentBetweenLines(FrameDBConstant.Command_find_prtdiag6800_key_start,
                FrameDBConstant.Command_find_prtdiag6800_key_end, lines);

    }

    private String getFailureInfoPart(Vector lines) {
        return this.getContentBetweenLinesPart(lines);

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
        if (!status.equals(FrameDBConstant.Status_normal)) {
            remark = this.getFailureInfoPart(lines);
        }

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
}
