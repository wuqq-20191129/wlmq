package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.CommandModuleMappingDao;
import com.goldsign.systemmonitor.dao.LccDao;
import com.goldsign.systemmonitor.vo.LccVo;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.log4j.Logger;

public class HandlerCommandCommlcc extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandCommlcc.class);

    public HandlerCommandCommlcc() {
        super();
    }

    private String[] getLineFieldByKey(String line, String keyIp, String keyStatus, String separator) {
        StringTokenizer st = new StringTokenizer(line, separator);
        String token;

        String[] fieldValues = {"", ""};
        String status;
        if (this.isIncludeKeyInLine(keyStatus, line)) {
            status = FrameDBConstant.Status_normal;
        } else {
            status = FrameDBConstant.Status_failure;
        }
        fieldValues[1] = status;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            if (this.isIncludeKeyInLine(keyIp, token)) {
                fieldValues[0] = token;
                break;
            }

        }
        return fieldValues;
    }

    private Vector getLineInfoForCommlcc(Vector lines) {
        Vector v = new Vector();
        String line;
        String[] fieldValues;
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);

            fieldValues = this.getLineFieldByKey(line, FrameDBConstant.Command_find_commlcc_key_ip,
                    FrameDBConstant.Command_find_commlcc_key_status,
                    FrameDBConstant.Command_seperator_space);
            v.add(fieldValues);

        }
        return v;


    }

    private Vector getStatusInfo(Vector lineInfos, String fileName) {
        Vector v = new Vector();
        String[] fieldValues;
        LccVo vo;
        for (int i = 0; i < lineInfos.size(); i++) {
            fieldValues = (String[]) lineInfos.get(i);
            vo = this.getVo(fieldValues, fileName);
            v.add(vo);

        }
        return v;
    }

    private LccVo getVo(String[] fieldValues, String fileName) {
        LccVo vo = new LccVo();

        vo.setStatus(fieldValues[1]);

        String ip = fieldValues[0];
        vo.setIp(ip);

        String statusDate = this.getStatusDate();
        vo.setStatusDate(statusDate);

        vo.setRemark("");

        return vo;

    }

    public void handleCommand(String command, Vector lines, String fileName) {

        logger.info("处理命令:" + command);
        this.setParameter(command, lines);
        //this.printLines();




        Vector lineInfos = this.getLineInfoForCommlcc(lines);
        Vector statusInfos = this.getStatusInfo(lineInfos, fileName);


        this.addStatuses(statusInfos);
        this.updateMenuStatus(command);

    }

    private void addStatuses(Vector vos) {
        LccDao dao = new LccDao();
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
