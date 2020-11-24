package com.goldsign.acc.systemmonitor.handler;

import com.goldsign.acc.app.system.controller.SybaseController;
import com.goldsign.acc.app.system.entity.Sybase;
import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.frame.entity.EmailContent;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.DataSourceUtil;
import com.goldsign.acc.frame.util.SpringContextUtil;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * refactor by zhongzq
 */
public class HandlerCommandSize extends HandlerCommandBase {

    public static String DATA_SOURCE_HCE = "dataSourceHCE";
    public static String DATA_SOURCE_ST = "dataSourceST";
    public static String HCE_TABLESPACE = "HCEMETRO";
    public static String ST_TABLESPACE = "W_TBS_ST";
    static Logger logger = Logger.getLogger(HandlerCommandSize.class);
    private static Vector SYBASE_TIME = new Vector();

    public HandlerCommandSize() {
        super();
    }

    @Override
    public void handleCommand(String command, Vector lines, String fileName) throws ParseException {
        logger.info("--------------处理命令:" + command);
        Vector lineInfosDump = this.getLineInfoForSybaseDump(lines);
        Vector lineInfosTime = new Vector();
        lineInfosTime.addAll(SYBASE_TIME);
        //保存文件信息以及ip状态 从文件获取
        Vector cmdTimeVos = this.getVos(lineInfosTime, lineInfosDump);
        //获取表空间信息 通过执行sql语句回去
        Vector dbVos = this.getDbInfos();
        logger.info("dbVos==" + dbVos.size());
        this.getTotalInfos(cmdTimeVos, dbVos);
        this.addStatuses(dbVos);
        this.updateMenuStatus(command);
        if (FrameDBConstant.EMAIL_FEATURE_USE) {
            emailPreHandles(dbVos);
        }
        clearSybaseTime();
    }

    @Override
    public EmailContent convertToEmailContents(Object t, String classPath) {
        Sybase vo = (Sybase) t;
        EmailContent emailContent = new EmailContent();
        emailContent.setClassSimpleName(classPath);
        emailContent.setModule((String) FrameDBConstant.MODULE_NAME.get(classPath));
        emailContent.setName((String) FrameDBConstant.SERVER_NAME.get(vo.getIp()));
        emailContent.setIp(vo.getIp());
        emailContent.setPasreTime(vo.getStatusDate());
        emailContent.setStatus(DBUtil.getTextByCode(vo.getStatus(), FrameDBConstant.STATUS_NAME));
        emailContent.setMessage(vo.getName());
        //add by zhongzq 20190905 防止更新命中错误
        emailContent.setKey(vo.getTablespace_name());
        return emailContent;
    }

    private Vector getLineInfoForSybaseDump(Vector lines) {
        String line;
        String dumpSize;
        Vector v = new Vector();
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            if (this.isIncludeKeyInLine(FrameDBConstant.COMMAND_FIND_DATABASE_DUMP_KEY, line)) {
                dumpSize = this.getLineField(line, 0, FrameDBConstant.COMMAND_SEPARATOR_TAB);
                dumpSize = this.getValueWithoutUnit(dumpSize);
                v.add(dumpSize);
            }
        }
        return v;
    }

    public static void setSybaseTime(Vector sybaseTimes) {
        HandlerCommandSize.SYBASE_TIME.addAll(sybaseTimes);
    }

    public static void clearSybaseTime() {
        HandlerCommandSize.SYBASE_TIME.clear();
    }

    private void getTotalInfos(Vector cmdVos, List<Sybase> dbVos) throws ParseException {
        Sybase dbVo;

        for (int i = 0; i < dbVos.size(); i++) {
            dbVo = (Sybase) dbVos.get(i);
            this.getCmdInfo(dbVo, cmdVos);
        }
    }

    private void getCmdInfo(Sybase dbVo, Vector cmdVos) throws ParseException {
        Sybase cmdVo;
        SybaseController sybaseController = SpringContextUtil.getBean("sybaseController");
        String capacityConfig = sybaseController.queryCapacityConfig();
        NumberFormat nf = NumberFormat.getPercentInstance();

        for (int i = 0; i < cmdVos.size(); i++) {
            cmdVo = (Sybase) cmdVos.get(i);
//            dbVo.setStatus_date(cmdVo.getStatus_date());
            dbVo.setStatusDate(cmdVo.getStatusDate());
            dbVo.setRemark(cmdVo.getRemark());
            //已用比率
            dbVo.setUsed_index_rate(dbVo.getUsed_pct());
            //modify by zhongzq 20191017
//            if (nf.parse(capacityConfig + "%").doubleValue() - nf.parse(dbVo.getUsed_index_rate()).doubleValue() <= 0) {
            if (nf.parse(capacityConfig + "%").doubleValue() - nf.parse(dbVo.getUsed_index_rate()).doubleValue() <= 0 || (dbVo.getRecovery_used_pct() != null && nf.parse(FrameDBConstant.RECOVERY_USE_SPACE_WARNING_VALUE + "%").doubleValue() - nf.parse(dbVo.getRecovery_used_pct()).doubleValue() <= 0)) {
                dbVo.setStatus(FrameDBConstant.STATUS_FAILURE);
            } else {
                dbVo.setStatus(cmdVo.getStatus());
            }
            //used_mb 中的数据是已用空间  总空间-已用空间=可用空间
//            dbVo.setFree_index(String.valueOf(Double.parseDouble(dbVo.getTotal_mb()) - Double.parseDouble(dbVo.getUsed_mb())));
            BigDecimal total = new BigDecimal(dbVo.getTotal_mb());
            BigDecimal used = new BigDecimal(dbVo.getUsed_mb());
            total = total.subtract(used);
            dbVo.setFree_index(String.valueOf(total));

            if (dbVo.getIp().equals(cmdVo.getIp())) {
                dbVo.setBackup_size(cmdVo.getBackup_size());
                dbVo.setBackup_start_time(cmdVo.getBackup_start_time());
                dbVo.setBackup_end_time(cmdVo.getBackup_end_time());
                dbVo.setBackup_interval(cmdVo.getBackup_interval());
                return;
            } else {
                dbVo.setBackup_size("");
                dbVo.setBackup_start_time("");
                dbVo.setBackup_end_time("");
                dbVo.setBackup_interval("");
            }
        }
    }

    //modify by zhongzq 20190506
    private String getDumpSize(String statusDate, Vector lineInfos) {
        if (lineInfos != null && lineInfos.size() > 0) {
            return (String) lineInfos.get(0);
        }
        return "";
    }

    private Vector getVos(Vector lineInfos, Vector lineInfosDump) {
        String[] values;
        Sybase vo;
        Vector v = new Vector();
        vo = this.getVo(FrameDBConstant.COMMAND_DATABASE_IPS[0], lineInfos, lineInfosDump);
        v.add(vo);
        return v;
    }

    private String getBackupInterval(String startTime, String endTime) {

        Date start;
        Date end;
        long interval = 0;
        try {
            start = this.strToUtilDate(startTime);
            end = this.strToUtilDate(endTime);
            interval = (end.getTime() - start.getTime()) / (1000 * 60);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Long.toString(interval);
    }

    public java.util.Date strToUtilDate(String strDate) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new java.util.Date(f.parse(strDate).getTime());
    }

    private Sybase getVo(String ip, Vector lineInfo, Vector lineInfosDump) {
        Sybase vo = new Sybase();
        if (lineInfo != null && lineInfo.size() > 0) {
            vo.setBackup_start_time((String) lineInfo.get(0));
            vo.setBackup_end_time((String) lineInfo.get(1));
            String interval = this.getBackupInterval(vo.getBackup_start_time(), vo.getBackup_end_time());
            vo.setBackup_interval(interval);
        }
        vo.setIp(ip);
        String status = FrameDBConstant.STATUS_NORMAL;
        vo.setStatus(status);
        String statusDate = this.getStatusDate();
        vo.setStatusDate(statusDate);
        String dumpSize = this.getDumpSize(statusDate, lineInfosDump);
        vo.setBackup_size(dumpSize);
        vo.setRemark("");
        return vo;

    }

    private void addStatuses(List<Sybase> vos) {
        SybaseController diskSpaceController = SpringContextUtil.getBean("sybaseController");
        try {
            diskSpaceController.addStatuses(vos);
        } catch (Exception ex) {
            logger.info(ex);
        }
    }

    private Vector getDbInfos() {
        SybaseController sybaseController = SpringContextUtil.getBean("sybaseController");
        Vector<Sybase> v = new Vector<>();
        try {

            DataSourceUtil.setDatasourceKey(FrameDBConstant.MONITOR_DATABASE);
            v = sybaseController.getDbMessageList();
            //add by zhoongzq 20191017 增加归档日志监控
            Sybase accRecoveryVo = sybaseController.getRecoveryMsg();

            //add by zhongzq 20190903 增加hce数据库监控
            DataSourceUtil.setDatasourceKey(DATA_SOURCE_HCE);
            Sybase sybase = sybaseController.getDbMessage(HCE_TABLESPACE);
            Sybase hcecRecoveryVo = sybaseController.getRecoveryMsg();

            if (sybase != null) {
                v.add(sybase);
//                logger.info("hcesybase:" + sybase.getTablespace_name());
//                for (int i = 0; i < sybase.size(); i++) {
//                    logger.info(sybase.get(i).getTablespace_name());
//                }
            } else {
                logger.error(DATA_SOURCE_HCE + " get null");
                hcecRecoveryVo = null;
            }
            DataSourceUtil.setDatasourceKey(DATA_SOURCE_ST);
            //add by zhongzq 20180717
            List<PubFlag> tableSpacesList = sybaseController.getTableSpaceList();
//            ArrayList<PubFlag> tableSpacesList = null;
            for (Sybase sb : v) {
                if (HCE_TABLESPACE.equals(sb.getTablespace_name())) {
                    sb.setIp(FrameDBConstant.COMMAND_DATABASE_IPS[1]);
                    //add by zhongzq 20191017
                    if (hcecRecoveryVo != null) {
                        sb.setRecovery_avail_mb(hcecRecoveryVo.getRecovery_avail_mb());
                        sb.setRecovery_used_pct(hcecRecoveryVo.getRecovery_used_pct());
                    }
                } else {
                    sb.setIp(FrameDBConstant.COMMAND_DATABASE_IPS[0]);
                    //add by zhongzq 20191017
                    if (accRecoveryVo != null && ST_TABLESPACE.equals(sb.getTablespace_name())) {
                        sb.setRecovery_avail_mb(accRecoveryVo.getRecovery_avail_mb());
                        sb.setRecovery_used_pct(accRecoveryVo.getRecovery_used_pct());
                    }
                }
                String tableSpaceName = sb.getTablespace_name();
                if (tableSpacesList != null) {
                    for (PubFlag vo : tableSpacesList) {
                        if (vo.getCode().equals(tableSpaceName)) {
                            tableSpaceName = vo.getCode_text();
                            break;
                        }
                    }
                }
                sb.setName(tableSpaceName);
                sb.setKeyMessage(sb.getTablespace_name());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }
}
