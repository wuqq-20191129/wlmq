package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.systemmonitor.dao.CommandModuleMappingDao;
import com.goldsign.systemmonitor.dao.SybaseDao;
import com.goldsign.systemmonitor.util.NumberUtil;
import com.goldsign.systemmonitor.vo.SybaseVo;
import java.math.BigDecimal;
import java.util.Vector;
import org.apache.log4j.Logger;


public class HandlerCommandDbcc_1 extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandDbcc_1.class);
    public HandlerCommandDbcc_1() {
        super();
    }

    public void handleCommand(String command, Vector lines, String fileName) {

        logger.info("处理命令:" + command);
        this.setParameter(command, lines);

        SybaseVo vo = this.getLineInfoForSybaseLog(lines);
        this.addStatus(vo);
        this.updateMenuStatus(command);
    }

    private void addStatus(SybaseVo vo) {
        SybaseDao dao = new SybaseDao();
        try {
            dao.updateStatus(vo);
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

    protected void setParameter(String command, Vector lines) {
        this.command = command;
        this.lines = lines;
    }

    private SybaseVo getLineInfoForSybaseLog(Vector lines) {
        String line;
        SybaseVo vo = new SybaseVo();
        String spaceUsed;
        String spaceFree;
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            if (this.isIncludeKeysInLine(FrameDBConstant.Command_find_dbcc_keys_ip, line)) {
                ip = this.getLineField(line, 2, FrameDBConstant.Command_seperator_space);
                ip = this.getIp(ip);
                vo.setIp(ip);
                continue;
            }
            if (this.isIncludeKeyInLine(FrameDBConstant.Command_find_dbcc_key_space_used, line)) {
                spaceUsed = this.getLineField(line, 0, FrameDBConstant.Command_seperator_comma);
                spaceUsed = this.getSpaceForBracket(spaceUsed);
                spaceUsed = this.getSpace(spaceUsed, FrameDBConstant.Command_find_dbcc_key_unit);
                vo.setUsedLog(spaceUsed);
                continue;
            }
            if (this.isIncludeKeyInLine(FrameDBConstant.Command_find_dbcc_key_space_free, line)) {
                spaceFree = this.getLineField(line, 0, FrameDBConstant.Command_seperator_comma);
                spaceFree = this.getSpaceForBracket(spaceFree);
                spaceFree = this.getSpace(spaceFree, FrameDBConstant.Command_find_dbcc_key_unit);
                vo.setFreeLog(spaceFree);
                continue;
            }
        }
        String status = FrameDBConstant.Status_normal;
        vo.setStatus(status);
        String statusDate = this.getStatusDate();
        vo.setStatusDate(statusDate);
        if (this.isLeagal(vo.getUsedLog()) && this.isLeagal(vo.getFreeLog())) {
            this.calculateRate(vo);
        }
        return vo;
    }

    private void calculateRate(SybaseVo vo) {

        BigDecimal bFree = NumberUtil.getBigDecimalValue(vo.getFreeLog(), "0", 2);
        BigDecimal bUsed = NumberUtil.getBigDecimalValue(vo.getUsedLog(), "0", 2);
        BigDecimal bTotal = bFree.add(bUsed);
        BigDecimal bMul_1 = new BigDecimal("100");
        BigDecimal usedRate = (bUsed.multiply(bMul_1)).divide(bTotal, 2, BigDecimal.ROUND_HALF_UP);
        vo.setUsedLogRate(usedRate.toString());
    }

    private boolean isLeagal(String data) {
        if (data == null || data.length() == 0) {
            return false;
        }
        return true;
    }
}
