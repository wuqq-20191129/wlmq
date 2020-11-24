package com.goldsign.systemmonitor.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameCharUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.systemmonitor.vo.DiskVo;
import java.util.Vector;
import org.apache.log4j.Logger;

public class DiskDao {

    static Logger logger = Logger.getLogger(DiskDao.class);

    public DiskDao() {
        super();
    }

    public Vector queryStatuses() throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        DiskVo vo;
        Vector v = new Vector();

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select A.status_date,A.ip,A.status,A.remark,B.name,B.type,B.ip_value "
                    + "from mtr_current_disk_info A,mtr_device_code B "
                    + "where A.ip =B.ip(+) order by ip_value";

            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                vo = new DiskVo();
                vo.setStatusDate(dbHelper.getItemValue("status_date"));
                vo.setIp(dbHelper.getItemValue("ip"));
                vo.setStatus(dbHelper.getItemValue("status"));
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

    public void addStatus(DiskVo vo) throws Exception {
        DbHelper dbHelper = null;
        String sql = null;

        int n;
        Object[] values = {vo.getStatusDate(), vo.getStatus(), FrameCharUtil.GbkToIso(vo.getRemark()), vo.getIp()};
        Object[] values1 = {vo.getStatusDate(), vo.getIp(), vo.getStatus(), FrameCharUtil.GbkToIso(vo.getRemark())};



        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE_T);
            dbHelper.setAutoCommit(false);
            sql = "update mtr_current_disk_info set status_date=to_date(?,'yyyy-mm-dd hh24:mi:ss'), status=?,remark=? where ip=? ";

            n = dbHelper.executeUpdate(sql, values);
            if (n != 1) {
                sql = "insert into mtr_current_disk_info(status_date,ip,status,remark) values(to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?) ";
                n = dbHelper.executeUpdate(sql, values1);
            }
            sql = "insert into mtr_his_disk_info(status_date,ip,status,remark) values(to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?) ";
            n = dbHelper.executeUpdate(sql, values1);
            dbHelper.commitTran();

        } catch (Exception e) {
            FramePubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            FramePubUtil.finalProcessForTran(dbHelper);
        }
    }
}
