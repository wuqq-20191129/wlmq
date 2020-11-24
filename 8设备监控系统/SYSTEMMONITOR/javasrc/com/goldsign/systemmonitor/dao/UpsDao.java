package com.goldsign.systemmonitor.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameCharUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.systemmonitor.vo.UpsVo;
import java.util.Vector;
import org.apache.log4j.Logger;
public class UpsDao {

    static Logger logger = Logger.getLogger(UpsDao.class);

    public UpsDao() {
        super();
    }

    public Vector queryStatuses() throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        UpsVo vo;
        Vector v = new Vector();

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select A.ip,A.ups_load,A.status,A.status_date,A.remark,"
                    + "B.name,B.type,B.ip_value "
                    + "from mtr_current_ups_info A,mtr_device_code B "
                    + "where A.ip =B.ip(+) order by ip_value";

            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                vo = new UpsVo();
                vo.setIp(dbHelper.getItemValue("ip"));
                vo.setUpsLoad(dbHelper.getItemValue("ups_load"));
                vo.setStatus(dbHelper.getItemValue("status"));
                vo.setStatusDate(dbHelper.getItemValue("status_date"));
                vo.setRemark(dbHelper.getItemValueIso("remark"));
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

    public void addStatus(UpsVo vo, DbHelper dbHelper) throws Exception {

        String sql = null;

        int n;
//		ip,file_system,avail,capacity,status,status_date,remark
        Object[] values = {vo.getUpsLoad(), vo.getStatus(),
            vo.getStatusDate(), FrameCharUtil.GbkToIso(vo.getRemark()),
            vo.getIp()};
        Object[] values1 = {vo.getIp(), vo.getUpsLoad(), vo.getStatus(),
            vo.getStatusDate(), FrameCharUtil.GbkToIso(vo.getRemark())
        };
        sql = "update mtr_current_ups_info set ups_load=?,status=?,status_date=to_date(?,'yyyy-mm-dd hh24:mi:ss'),remark=? "
                + "where ip=? ";
        n = dbHelper.executeUpdate(sql, values);
        if (n != 1) {
            sql = "insert into mtr_current_ups_info(ip,ups_load,status,status_date,remark) values(?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?) ";
            n = dbHelper.executeUpdate(sql, values1);
        }
        sql = "insert into mtr_his_ups_info(ip,ups_load,status,status_date,remark) values(?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?) ";
        n = dbHelper.executeUpdate(sql, values1);
    }

    public void addStatuses(Vector vos) throws Exception {
        DbHelper dbHelper = null;
        UpsVo vo;
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE_T);
            dbHelper.setAutoCommit(false);
            for (int i = 0; i < vos.size(); i++) {
                vo = (UpsVo) vos.get(i);
                this.addStatus(vo, dbHelper);
            }

            dbHelper.commitTran();

        } catch (Exception e) {
            FramePubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            FramePubUtil.finalProcessForTran(dbHelper);
        }
    }
}
