package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.CommandModuleMappingDao;
import com.goldsign.systemmonitor.dao.DiskSpaceDao;
import com.goldsign.systemmonitor.vo.DiskSpaceVo;
import java.util.Vector;
import org.apache.log4j.Logger;


public class HandlerCommandDf extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandDf.class);

    public HandlerCommandDf() {
        super();
    }

    private Vector getStatusInfo(Vector lineInfos, String fileName) {
        Vector v = new Vector();
        String[] fieldValues;
        DiskSpaceVo vo;
        for (int i = 0; i < lineInfos.size(); i++) {
            fieldValues = (String[]) lineInfos.get(i);
            vo = this.getVo(fieldValues, fileName);
            v.add(vo);

        }
        return v;
    }

    private boolean isOverLimit(String capacity) {
        int index = capacity.indexOf(FrameDBConstant.Command_seperator_percent);
        int iCap = new Integer(capacity.substring(0, index)).intValue();
        if (iCap >= FrameDBConstant.Command_status_df_key) {
            return true;
        }
        return false;
    }

    private DiskSpaceVo getVo(String[] fieldValues, String fileName) {
        DiskSpaceVo vo = new DiskSpaceVo();
        vo.setFileSystem(fieldValues[0]);
        vo.setAvail(fieldValues[1]);
        vo.setCapacity(fieldValues[2]);
        vo.setMountedOn(fieldValues[3]);
        String status = FrameDBConstant.Status_failure;
        if (!this.isOverLimit(vo.getCapacity())) {
            status = FrameDBConstant.Status_normal;
        }
        vo.setStatus(status);

        String ip = this.getIpFromFileName(fileName);
        vo.setIp(ip);

        String statusDate = this.getStatusDate();
        vo.setStatusDate(statusDate);
        vo.setRemark("");
        return vo;

    }

    private boolean isNeedLineForDf(String line) {
        int fieldIndex = 0;
        int fieldIndex_1 = 5;
        if (this.isIncludeKeyInLine(FrameDBConstant.Command_seperator_slash, line)
                && (this.isNeededLineFromFields(fieldIndex, FrameDBConstant.Command_find_df_keys_filesystem,
                FrameDBConstant.Command_seperator_space, line)
                || this.isNeededLineFromFields(fieldIndex_1, FrameDBConstant.Command_find_df_keys_mounted,
                FrameDBConstant.Command_seperator_space, line)
                || this.isNeededLineFromFieldsComplete(fieldIndex_1, FrameDBConstant.Command_find_df_keys_mounted_complete,
                FrameDBConstant.Command_seperator_space, line))) {
            return true;
        }
        return false;
    }

    private Vector getLineInfoForDf(Vector lines, int[] fieldIndexs) {
        Vector v = new Vector();
        String line;
        String[] fieldValues;
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            if (this.isNeedLineForDf(line)) {
                fieldValues = this.getLineFields(line, fieldIndexs, FrameDBConstant.Command_seperator_space);
                v.add(fieldValues);
            }
        }
        return v;
    }

    public void handleCommand(String command, Vector lines, String fileName) {
        logger.info("处理命令:" + command);
        this.setParameter(command, lines);
        //this.printLines();
        //int[] fieldIndexes = {0, 3, 4, 5};
        int[] fieldIndexes = {0, 2, 3, 6};
        Vector lineInfos = this.getLineInfoForDf(lines, fieldIndexes);
        Vector statusInfos = this.getStatusInfo(lineInfos, fileName);
        this.addStatuses(statusInfos);
        this.updateMenuStatus(command);
    }

    private void addStatuses(Vector vos) {
        DiskSpaceDao dao = new DiskSpaceDao();
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
