package com.goldsign.systemmonitor.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameCharUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.systemmonitor.vo.NtpSynVo;
import java.util.Vector;
import org.apache.log4j.Logger;

public class NtpIccsSynDao {

    static Logger logger = Logger.getLogger(NtpIccsSynDao.class);

    public NtpIccsSynDao() {
        super();
    }

    public Vector queryStatuses() throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        NtpSynVo vo;
        Vector v = new Vector();

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select A.status_date,A.ip,A.status,A.remark,A.ip_source,A.status_date_syn,A.diff,B.name,"
                    + "B.type,B.ip_value "
                    + "from mtr_current_ntpiccssyn_info A,mtr_device_code B "
                    + "where A.ip =B.ip(+) order by ip_value";

            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                vo = new NtpSynVo();
                vo.setStatusDate(dbHelper.getItemValue("status_date"));
                vo.setIp(dbHelper.getItemValue("ip"));
                vo.setStatus(dbHelper.getItemValue("status"));
                vo.setRemark(dbHelper.getItemValueIso("remark"));
                vo.setName(dbHelper.getItemValueIso("name"));
                vo.setType(dbHelper.getItemValue("type"));
                vo.setIpSource(dbHelper.getItemValue("ip_source"));
                vo.setStatusDateSyn(dbHelper.getItemValue("status_date_syn"));
                vo.setDiff(dbHelper.getItemValue("diff"));
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

    public void addStatus(NtpSynVo vo) throws Exception {
        DbHelper dbHelper = null;
        String sql = null;
        int n;
        Object[] values = {vo.getStatusDate(), vo.getStatus(), FrameCharUtil.GbkToIso(vo.getRemark()),
            vo.getIpSource(), vo.getStatusDateSyn(), vo.getDiff(), vo.getIp()};
        Object[] values1 = {vo.getStatusDate(), vo.getIp(), vo.getStatus(), FrameCharUtil.GbkToIso(vo.getRemark()),
            vo.getIpSource(), vo.getStatusDateSyn(), vo.getDiff()};
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE_T);
            dbHelper.setAutoCommit(false);
            sql = "update mtr_current_ntpiccssyn_info set status_date=to_date(?,'yyyy-mm-dd hh24:mi:ss'), status=?,remark=? ,"
                    + "ip_source=?,status_date_syn=to_date(?,'yyyy-mm-dd hh24:mi:ss') ,diff=? where ip=? ";

            n = dbHelper.executeUpdate(sql, values);
            if (n != 1) {
                sql = "insert into mtr_current_ntpiccssyn_info(status_date,ip,status,remark,ip_source,status_date_syn,diff) values(to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?)  ";
                n = dbHelper.executeUpdate(sql, values1);
            }
            sql = "insert into mtr_his_ntpiccssyn_info(status_date,ip,status,remark,ip_source,status_date_syn,diff) values(to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?) ";
            n = dbHelper.executeUpdate(sql, values1);
            dbHelper.commitTran();
        } catch (Exception e) {
            FramePubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            FramePubUtil.finalProcessForTran(dbHelper);
        }
    }
}
