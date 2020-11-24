package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.CommandModuleMappingDao;
import com.goldsign.systemmonitor.dao.IqDao;
import com.goldsign.systemmonitor.util.DateHelper;
import com.goldsign.systemmonitor.vo.IqVo;
import java.text.ParseException;
import java.util.Date;
import java.util.Vector;
import org.apache.log4j.Logger;

public class HandlerCommandIqful extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandIqful.class);

    public HandlerCommandIqful() {
        super();
    }

    private String[] getLineInfoForIqFul(Vector lines) {
        String line;
        String[] values = {"", "", ""};
        String value;


        int[] indexes = {0, 1};
        String[] temp = {"", ""};
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            if (this.isIncludeKeyInLine(FrameDBConstant.Command_find_iq_key_start, line)) {
                temp = this.getLineFields(line, indexes, FrameDBConstant.Command_seperator_space);
                values[0] = temp[0] + " " + temp[1];
                continue;
            }
            if (this.isIncludeKeyInLine(FrameDBConstant.Command_find_iq_key_finish, line)) {
                temp = this.getLineFields(line, indexes, FrameDBConstant.Command_seperator_space);
                values[1] = temp[0] + " " + temp[1];
                continue;
            }
            if (this.isIncludeKeyInLine(FrameDBConstant.Command_find_iq_key_size, line)) {
                value = this.getLineField(line, 0, FrameDBConstant.Command_seperator_tab);
                value = this.getValueWithoutUnit(value);
                values[2] = value;
                continue;
            }

        }
        return values;
    }

    public void handleCommand(String command, Vector lines, String fileName) {

        logger.info("处理命令:" + command);
        this.setParameter(command, lines);
        //this.printLines();
        String[] lineInfos = this.getLineInfoForIqFul(lines);
        IqVo lineInfoVo = this.getVo(FrameDBConstant.Command_iq_ip, lineInfos);

        Vector dbVos = this.getDbInfos();
        this.getTotalInfos(lineInfoVo, dbVos);
        this.addStatuses(dbVos);
        this.updateMenuStatus(command);



    }

    private void getTotalInfos(IqVo lineInfoVo, Vector dbVos) {
        IqVo dbVo;
        for (int i = 0; i < dbVos.size(); i++) {
            dbVo = (IqVo) dbVos.get(i);
            this.getCmdInfo(dbVo, lineInfoVo);

        }
    }

    private void getCmdInfo(IqVo dbVo, IqVo lineInfoVo) {
        dbVo.setStatus(lineInfoVo.getStatus());
        dbVo.setStatusDate(lineInfoVo.getStatusDate());
        dbVo.setRemark(lineInfoVo.getRemark());

        dbVo.setBackupIncSize(lineInfoVo.getBackupIncSize());
        dbVo.setBackupIncStartTime(lineInfoVo.getBackupIncStartTime());
        dbVo.setBackupIncEndTime(lineInfoVo.getBackupIncEndTime());
        dbVo.setBackupIncInterval(lineInfoVo.getBackupIncInterval());

        if (dbVo.getIp().equals(lineInfoVo.getIp())) {
            dbVo.setBackupFulSize(lineInfoVo.getBackupFulSize());
            dbVo.setBackupFulStartTime(lineInfoVo.getBackupFulStartTime());
            dbVo.setBackupFulEndTime(lineInfoVo.getBackupFulEndTime());
            dbVo.setBackupFulInterval(lineInfoVo.getBackupFulInterval());


            return;
        } else {
            dbVo.setBackupFulSize("");
            dbVo.setBackupFulStartTime("");
            dbVo.setBackupFulEndTime("");
            dbVo.setBackupFulInterval("");

        }

    }

    private String getBackupInterval(String startTime, String endTime) {

        Date start;
        Date end;
        long interval = -1;
        try {
            start = DateHelper.strToUtilDate(startTime);
            end = DateHelper.strToUtilDate(endTime);
            interval = (end.getTime() - start.getTime()) / (1000 * 60);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Long(interval).toString();
    }

    private IqVo getVo(String ip, String[] lineInfo) {
        IqVo vo = new IqVo();

        vo.setBackupFulStartTime(lineInfo[0]);
        vo.setBackupFulEndTime(lineInfo[1]);
        vo.setBackupFulSize(lineInfo[2]);

        String interval = this.getBackupInterval(vo.getBackupFulStartTime(), vo.getBackupFulEndTime());
        vo.setBackupFulInterval(interval);


        vo.setBackupIncStartTime("");
        vo.setBackupIncEndTime("");
        vo.setBackupIncSize("");
        vo.setBackupIncInterval("");
        vo.setIp(ip);
        String status = FrameDBConstant.Status_normal;
        vo.setStatus(status);
        String statusDate = this.getStatusDate();
        vo.setStatusDate(statusDate);
        vo.setRemark("");
        return vo;

    }

    private void addStatuses(Vector vos) {
        IqDao dao = new IqDao();
        try {
            dao.addStatuses(vos);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private Vector getDbInfos() {
        IqDao dao = new IqDao();
        Vector v = new Vector();
        try {
            v = dao.getDbInfos();
        } catch (Exception e) {

            e.printStackTrace();
        }
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
}
