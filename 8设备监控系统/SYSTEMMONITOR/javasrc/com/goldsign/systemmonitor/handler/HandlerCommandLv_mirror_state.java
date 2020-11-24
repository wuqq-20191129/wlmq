package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.CommandModuleMappingDao;
import com.goldsign.systemmonitor.dao.DiskDao;
import com.goldsign.systemmonitor.vo.DiskVo;
import java.util.Vector;
import org.apache.log4j.Logger;

public class HandlerCommandLv_mirror_state extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandLv_mirror_state.class);

    public HandlerCommandLv_mirror_state() {
        super();
    }

    private String getStatus(Vector lines) {
        String line;

        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);

            if ((this.isIncludeKeyInLine(FrameDBConstant.Command_find_metastat_key, line))) {
                String record = line.substring(47, 48);
                if (record.equals("2")) {
                    return FrameDBConstant.Status_normal;
                }
            };
        }
        return FrameDBConstant.Status_failure;
    }
    /*
     private String getFailureInfo(Vector lines) {
     return this.getContentBetweenLines(FrameDBConstant.Command_find_prtdiag6800_key_start,
     FrameDBConstant.Command_find_prtdiag6800_key_end, lines);
     }
     */

    public void handleCommand(String command, Vector lines, String fileName) {

        logger.info("处理命令:" + command);
        this.setParameter(command, lines);
        DiskVo vo = new DiskVo();
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

    private void addStatus(DiskVo vo) {
        DiskDao dao = new DiskDao();
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
