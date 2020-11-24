package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.CommandModuleMappingDao;
import com.goldsign.systemmonitor.dao.HardwareDao;
import com.goldsign.systemmonitor.vo.HardwareVo;
import java.util.Vector;
import org.apache.log4j.Logger;

public class HandlerCommandPrtdiag880 extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandPrtdiag880.class);

    public HandlerCommandPrtdiag880() {
        super();
        // TODO Auto-generated constructor stub
    }

    private String getStatus(Vector lines) {

        String status = FrameDBConstant.Status_failure;
        if (this.isIncludeKeysInLines(FrameDBConstant.Command_find_prtdiag880_keys, lines)) {
            status = FrameDBConstant.Status_normal;
        }
        return status;
    }

    public void handleCommand(String command, Vector lines, String fileName) {

        logger.info("��������:" + command);
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
}
