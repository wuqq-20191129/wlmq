package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.CommandModuleMappingDao;
import com.goldsign.systemmonitor.dao.NetCardDao;
import com.goldsign.systemmonitor.vo.NetCardVo;
import java.util.Vector;
import org.apache.log4j.Logger;


public class HandlerCommandIfconfig extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandIfconfig.class);

    public HandlerCommandIfconfig() {
        super();
    }

    private String getStatus(Vector lines) {
        if (lines.isEmpty()) {
            return FrameDBConstant.Status_normal;
        }
        return FrameDBConstant.Status_failure;
    }

    private String getFailureInfo(Vector lines) {
        return this.getContentBetweenLines(lines);
    }

    public void handleCommand(String command, Vector lines, String fileName) {

        logger.info("处理命令:" + command);
        this.setParameter(command, lines);
        NetCardVo vo = new NetCardVo();
        String status = this.getStatus(lines);
        String ip = this.getIpFromFileName(fileName);
        String statusDate = this.getStatusDate();
        String remark = "";
        if (!status.equals(FrameDBConstant.Status_normal)) {
            remark = this.getFailureInfo(lines);
        }
        vo.setIp(ip);
        vo.setStatus(status);
        vo.setStatusDate(statusDate);
        vo.setRemark(remark);
        this.addStatus(vo);
        this.updateMenuStatus(command);
    }

    private void addStatus(NetCardVo vo) {
        NetCardDao dao = new NetCardDao();
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
