package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.CommandModuleMappingDao;
import com.goldsign.systemmonitor.dao.SybaseDao;
import com.goldsign.systemmonitor.util.DateHelper;
import com.goldsign.systemmonitor.vo.SybaseVo;
import java.text.ParseException;
import java.util.Date;
import java.util.Vector;
import org.apache.log4j.Logger;

public class HandlerCommandSize extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandSize.class);
    private static Vector SYBASE_TIME = new Vector();

    public HandlerCommandSize() {
        super();
    }

    private Vector getLineInfoForSybaseDump(Vector lines) {
        String line;
        String dumpSize;

        Vector v = new Vector();
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            if (this.isIncludeKeyInLine(FrameDBConstant.Command_find_sybasedump_key, line)) {
                dumpSize = this.getLineField(line, 0, FrameDBConstant.Command_seperator_tab);
                dumpSize = this.getValueWithoutUnit(dumpSize);
                v.add(dumpSize);
            }
        }
        return v;
    }

    public void handleCommand(String command, Vector lines, String fileName) {
        logger.info("处理命令:" + command);
        this.setParameter(command, lines);
        //this.printLines();
        Vector lineInfosDump = this.getLineInfoForSybaseDump(lines);
        Vector lineInfosTime = new Vector();
        lineInfosTime.addAll(HandlerCommandSize.SYBASE_TIME);
        Vector cmdTimeVos = this.getVos(lineInfosTime, lineInfosDump);
        //Vector dbVos = this.getDbInfos();
        Vector dbVos = this.getDbInfos(fileName);
        this.getTotalInfos(cmdTimeVos, dbVos);
        this.addStatuses(dbVos);
        this.updateMenuStatus(command);
        clearSybaseTime();


    }

    public static void setSybaseTime(Vector sybaseTimes) {
        HandlerCommandSize.SYBASE_TIME.addAll(sybaseTimes);
    }

    public static void clearSybaseTime() {
        HandlerCommandSize.SYBASE_TIME.clear();
    }

    private void getTotalInfos(Vector cmdVos, Vector dbVos) {
        SybaseVo dbVo;

        for (int i = 0; i < dbVos.size(); i++) {
            dbVo = (SybaseVo) dbVos.get(i);
            this.getCmdInfo(dbVo, cmdVos);
        }
    }

    private void getCmdInfo(SybaseVo dbVo, Vector cmdVos) {
        SybaseVo cmdVo;
        for (int i = 0; i < cmdVos.size(); i++) {
            cmdVo = (SybaseVo) cmdVos.get(i);
            dbVo.setStatus(cmdVo.getStatus());
            dbVo.setStatusDate(cmdVo.getStatusDate());
            dbVo.setRemark(cmdVo.getRemark());

            if (dbVo.getIp().equals(cmdVo.getIp())) {
                dbVo.setBackupSize(cmdVo.getBackupSize());
                dbVo.setBackupStartTime(cmdVo.getBackupStartTime());
                dbVo.setBackupEndTime(cmdVo.getBackupEndTime());
                dbVo.setBackupInterval(cmdVo.getBackupInterval());
                return;
            } else {
                dbVo.setBackupSize("");
                dbVo.setBackupStartTime("");
                dbVo.setBackupEndTime("");
                dbVo.setBackupInterval("");

            }
        }
    }

    private String getDumpSize(String statusDate, Vector lineInfos) {

        return (String) lineInfos.get(0);
    }

    private Vector getVos(Vector lineInfos, Vector lineInfosDump) {
        String[] values;
        SybaseVo vo;
        Vector v = new Vector();
        /*
         for (int i = 0; i < lineInfos.size(); i++) {
         values = (String[]) lineInfos.get(i);
         vo = this.getVo(FrameDBConstant.Command_sybasetime_ips[i], values, lineInfosDump);
         v.add(vo);
         }
         */
        vo = this.getVo(FrameDBConstant.Command_sybasetime_ips[0], lineInfos, lineInfosDump);
        v.add(vo);
        return v;
    }

    private String getBackupInterval(String startTime, String endTime) {

        Date start;
        Date end;
        long interval = 0;
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

    private SybaseVo getVo(String ip, Vector lineInfo, Vector lineInfosDump) {
        SybaseVo vo = new SybaseVo();

        vo.setBackupStartTime((String)lineInfo.get(0));
        vo.setBackupEndTime((String)lineInfo.get(1));
        String interval = this.getBackupInterval(vo.getBackupStartTime(), vo.getBackupEndTime());
        vo.setBackupInterval(interval);
        vo.setIp(ip);
        String status = FrameDBConstant.Status_normal;
        vo.setStatus(status);
        String statusDate = this.getStatusDate();
        vo.setStatusDate(statusDate);
        String dumpSize = this.getDumpSize(statusDate, lineInfosDump);
        vo.setBackupSize(dumpSize);
        vo.setRemark("");
        return vo;

    }

    private void addStatuses(Vector vos) {
        SybaseDao dao = new SybaseDao();
        try {
            dao.addStatuses(vos);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private Vector getDbInfos(String fileName) {
        SybaseDao dao = new SybaseDao();
        Vector v = new Vector();
        //String ip = this.getIpFromFileName(fileName);
        String ip = FrameDBConstant.Command_sybasetime_ips[0];
        try {
            //v = dao.getDbInfos();
            v = dao.getDbMessage(ip);
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
