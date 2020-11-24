package com.goldsign.systemmonitor.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameCharUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.systemmonitor.vo.CiscoVo;
import java.util.Vector;
import org.apache.log4j.Logger;


public class CiscoDao {

    static Logger logger = Logger.getLogger(CiscoDao.class);

    public CiscoDao() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Vector queryStatuses() throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        CiscoVo vo;
        Vector v = new Vector();
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select A.status_date,A.ip,A.status,A.remark,"
                    + "A.power_status,A.temperature_status,A.fan_status,A.redundancy,A.mainframe,"
                    + "B.name,B.type,B.ip_value "
                    + "from mtr_current_cisco_info A,mtr_device_code B "
                    + "where A.ip =B.ip(+) order by ip_value";

            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                vo = new CiscoVo();
                vo.setStatusDate(dbHelper.getItemValue("status_date"));
                vo.setIp(dbHelper.getItemValue("ip"));
                vo.setStatus(dbHelper.getItemValue("status"));
                vo.setRemark(dbHelper.getItemValueIso("remark"));
                vo.setPowerStatus(dbHelper.getItemValue("power_status"));
                vo.setTemperatureStatus(dbHelper.getItemValue("temperature_status"));
                vo.setFanStatus(dbHelper.getItemValue("fan_status"));
                vo.setRedundancy(dbHelper.getItemValue("redundancy"));
                vo.setMainframe(dbHelper.getItemValue("mainframe"));
                vo.setName(dbHelper.getItemValueIso("name"));
                vo.setType(dbHelper.getItemValue("type"));
                v.add(vo);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return v;
    }

    public void addStatus(CiscoVo vo) throws Exception {
        DbHelper dbHelper = null;
        String sql = null;
        int n;
        Object[] values = {vo.getStatusDate(), vo.getStatus(), FrameCharUtil.GbkToIso(vo.getRemark()),
            vo.getPowerStatus(), vo.getTemperatureStatus(), vo.getFanStatus(), vo.getRedundancy(), vo.getMainframe(),
            vo.getIp()};
        Object[] values1 = {vo.getStatusDate(), vo.getIp(), vo.getStatus(), FrameCharUtil.GbkToIso(vo.getRemark()),
            vo.getPowerStatus(), vo.getTemperatureStatus(), vo.getFanStatus(), vo.getRedundancy(), vo.getMainframe()};
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE_T);
            dbHelper.setAutoCommit(false);
            sql = "update mtr_current_cisco_info set status_date=to_date(?,'yyyy-mm-dd hh24:mi:ss'), status=?,remark=? "
                    + ",power_status=?,temperature_status=?,fan_status=?,redundancy=?,mainframe=? "
                    + " where ip=? ";
            n = dbHelper.executeUpdate(sql, values);
            if (n != 1) {
                sql = "insert into mtr_current_cisco_info(status_date,ip,status,remark,"
                        + "power_status,temperature_status,fan_status,redundancy,mainframe) "
                        + "values(to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,?,?) ";
                n = dbHelper.executeUpdate(sql, values1);
            }
            sql = "insert into mtr_his_cisco_info(status_date,ip,status,remark,"
                    + "power_status,temperature_status,fan_status,redundancy,mainframe) "
                    + "values(to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,?,?) ";
            n = dbHelper.executeUpdate(sql, values1);
            dbHelper.commitTran();
        } catch (Exception e) {
            FramePubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            FramePubUtil.finalProcessForTran(dbHelper);
        }
    }

    public void addStatuses(Vector vos) throws Exception {
        DbHelper dbHelper = null;
        CiscoVo vo;
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE_T);
            dbHelper.setAutoCommit(false);
            for (int i = 0; i < vos.size(); i++) {
                vo = (CiscoVo) vos.get(i);
                this.addStatus(vo, dbHelper);
            }
            dbHelper.commitTran();
        } catch (Exception e) {
            FramePubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            FramePubUtil.finalProcessForTran(dbHelper);
        }
    }

    public void addStatus(CiscoVo vo, DbHelper dbHelper) throws Exception {

        String sql = null;

        int n;
        Object[] values = {vo.getStatusDate(), vo.getStatus(), FrameCharUtil.GbkToIso(vo.getRemark()),
            vo.getPowerStatus(), vo.getTemperatureStatus(), vo.getFanStatus(), vo.getRedundancy(), vo.getMainframe(),
            vo.getIp()};
        Object[] values1 = {vo.getStatusDate(), vo.getIp(), vo.getStatus(), FrameCharUtil.GbkToIso(vo.getRemark()),
            vo.getPowerStatus(), vo.getTemperatureStatus(), vo.getFanStatus(), vo.getRedundancy(), vo.getMainframe()};
        sql = "update mtr_current_cisco_info set status_date=to_date(?,'yyyy-mm-dd hh24:mi:ss'), status=?,remark=? "
                + ",power_status=?,temperature_status=?,fan_status=?,redundancy=?,mainframe=? "
                + " where ip=? ";

        n = dbHelper.executeUpdate(sql, values);
        if (n != 1) {
            sql = "insert into mtr_current_cisco_info(status_date,ip,status,remark,"
                    + "power_status,temperature_status,fan_status,redundancy,mainframe) "
                    + "values(to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,?,?) ";
            n = dbHelper.executeUpdate(sql, values1);
        }
        sql = "insert into mtr_his_cisco_info(status_date,ip,status,remark,"
                + "power_status,temperature_status,fan_status,redundancy,mainframe) "
                + "values(to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,?,?) ";
        n = dbHelper.executeUpdate(sql, values1);
    }
}
