package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.CommandModuleMappingDao;
import com.goldsign.systemmonitor.dao.NtpSynDao;
import com.goldsign.systemmonitor.vo.NtpSynVo;
import java.util.HashMap;
import java.util.Vector;
import org.apache.log4j.Logger;

public class HandlerCommandNtp extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandNtp.class);

    public HandlerCommandNtp() {
        super();
    }

    public void handleCommand(String command, Vector lines, String fileName) {

        logger.info("处理命令:" + command);
        this.setParameter(command, lines);
        //NtpSynVo vo = this.getLineInfoForNtp(lines);
        Vector vos = this.getLineInfoForNtp(lines);
        this.addStatus(vos);
        this.updateMenuStatus(command);
    }
    /*
     private void addStatus(Vector vos) {
     NtpSynDao dao = new NtpSynDao();
     try {
     dao.addStatus(vo);
     } catch (Exception e) {

     e.printStackTrace();
     }
     }
     */

    private void addStatus(Vector vos) {
        NtpSynVo vo;
        NtpSynDao dao = new NtpSynDao();
        Vector v = this.getLatestStatues(vos);
        for (int i = 0; i < v.size(); i++) {
            vo = (NtpSynVo) v.get(i);
            try {
                dao.addStatus(vo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Vector getLatestStatues(Vector vos) {
        Vector v = new Vector();
        NtpSynVo vo;
        HashMap hm = new HashMap();
        for (int i = 0; i < vos.size(); i++) {
            vo = (NtpSynVo) vos.get(i);
            hm.put(vo.getIp(), vo);
        }
        v.addAll(hm.values());
        return v;

    }

    private void updateMenuStatus(String command) {
        CommandModuleMappingDao dao = new CommandModuleMappingDao();
        try {
            dao.updateMenuStatus(command);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private String getStatus(String diff) {
        float f = Float.parseFloat(diff);
        if (Math.abs(f) <= FrameDBConstant.SynMaxDiffInterval) {
            return FrameDBConstant.Status_normal;
        } else {
            return FrameDBConstant.Status_failure;
        }
    }
    /*
     private NtpSynVo getLineInfoForNtp(Vector lines) {
     String line;
     String ip;
     String ipSource;
     String diff;
     String diffTime;
     String status;
     String statusDate;
     Vector v = new Vector();
     NtpSynVo vo = new NtpSynVo();
     for (int i = 0; i < lines.size(); i++) {
     line = (String) lines.get(i);
     diffTime = this.getLineField(line, 0, FrameDBConstant.Command_seperator_space) + " "
     + this.getLineField(line, 1, FrameDBConstant.Command_seperator_space);
     ipSource = this.getLineField(line, 2, FrameDBConstant.Command_seperator_space);
     diff = this.getLineField(line, 3, FrameDBConstant.Command_seperator_space);
     ip = this.getLineField(line, 4, FrameDBConstant.Command_seperator_space);
     statusDate = this.getStatusDate();
     status = this.getStatus(diff);
     vo.setDiff(diff);
     vo.setIp(ip);
     vo.setIpSource(ipSource);
     vo.setStatusDateSyn(diffTime);
     vo.setStatusDate(statusDate);
     vo.setStatus(status);
     vo.setRemark("");
     }
     return vo;
     }
     */

    private Vector getLineInfoForNtp(Vector lines) {
        String line;
        String ip;
        String ipSource;
        String diff;
        String diffTime;
        String status;
        String statusDate;
        Vector v = new Vector();
        NtpSynVo vo = null;
        for (int i = 0; i < lines.size(); i++) {
            vo =new NtpSynVo();
            line = (String) lines.get(i);
            diffTime = this.getLineField(line, 3, FrameDBConstant.Command_seperator_space) + " "
                    + this.getLineField(line, 4, FrameDBConstant.Command_seperator_space);
            ipSource = this.getLineField(line, 0, FrameDBConstant.Command_seperator_space);
            diff = this.getLineField(line, 2, FrameDBConstant.Command_seperator_space);
            ip = this.getLineField(line, 1, FrameDBConstant.Command_seperator_space);
            statusDate = this.getStatusDate();
            status = this.getStatus(diff);
            vo.setDiff(diff);
            vo.setIp(ip);
            vo.setIpSource(ipSource);
            vo.setStatusDateSyn(diffTime);
            vo.setStatusDate(statusDate);
            vo.setStatus(status);
            vo.setRemark("");
            v.add(vo);
        }
        return v;
    }
}
