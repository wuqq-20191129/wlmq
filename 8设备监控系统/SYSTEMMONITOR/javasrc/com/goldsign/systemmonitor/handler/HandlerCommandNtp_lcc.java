package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.CommandModuleMappingDao;
import com.goldsign.systemmonitor.dao.NtpLccSynDao;
import com.goldsign.systemmonitor.vo.NtpSynVo;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;
import org.apache.log4j.Logger;



public class HandlerCommandNtp_lcc extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandNtp_lcc.class);

    public HandlerCommandNtp_lcc() {
        super();
    }

    public void handleCommand(String command, Vector lines, String fileName) {

        logger.info("处理命令:" + command);
        this.setParameter(command, lines);
        Vector vos = this.getLineInfoForNtp(lines);
        this.addStatus(vos);
        this.updateMenuStatus(command);
    }

    private void addStatus(Vector vos) {
        NtpSynVo vo;
        NtpLccSynDao dao = new NtpLccSynDao();
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

    private String convertUnit(String diff) {
        BigDecimal n = new BigDecimal(diff);
        BigDecimal n1 = new BigDecimal(1000);
        n = n.divide(n1, 3);
        return n.toString();
    }

    private Vector getLineInfoForNtp(Vector lines) {
        String line;
        String ip;
        String ipSource;
        String diff;
        String diffTime;
        String status;
        String statusDate;

        Vector v = new Vector();
        NtpSynVo vo;
        for (int i = 0; i < lines.size(); i++) {
            vo = new NtpSynVo();
            line = (String) lines.get(i);
            ipSource = this.getLineField(line, 0, FrameDBConstant.Command_seperator_space);
            ip = this.getLineField(line, 1, FrameDBConstant.Command_seperator_space);
            diff = this.convertUnit(this.getLineField(line, 2, FrameDBConstant.Command_seperator_space));
            diffTime = this.getLineField(line, 3, FrameDBConstant.Command_seperator_space) + " "
                    + this.getLineField(line, 4, FrameDBConstant.Command_seperator_space) + " "
                    + this.getLineField(line, 5, FrameDBConstant.Command_seperator_space) + " "
                    + this.getLineField(line, 6, FrameDBConstant.Command_seperator_space);
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
