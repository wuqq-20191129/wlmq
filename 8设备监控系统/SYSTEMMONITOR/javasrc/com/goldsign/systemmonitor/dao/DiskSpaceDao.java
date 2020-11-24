package com.goldsign.systemmonitor.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameCharUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.systemmonitor.vo.DiskSpaceVo;
import java.util.Vector;
import org.apache.log4j.Logger;

public class DiskSpaceDao {

    static Logger logger = Logger.getLogger(DiskSpaceDao.class);

    public DiskSpaceDao() {
        super();
    }

    private String getStyle(String color) {
        return "background:" + color;
    }

    public Vector queryStatuses() throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        DiskSpaceVo vo;
        Vector v = new Vector();
        String ip = "";
        String ipOrg = "";
        int i = 0;

        String color;
        String style = "";

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select A.ip,A.file_system,A.avail,A.capacity,A.mounted_on ,A.status,A.status_date,A.remark,"
                    + "B.name,B.type ,B.ip_value "
                    + "from mtr_current_disk_space_info A,mtr_device_code B "
                    + "where A.ip =B.ip(+) order by ip_value";

            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                vo = new DiskSpaceVo();

                ip = dbHelper.getItemValue("ip");
                if (!ip.equals(ipOrg)) {
                    color = FrameDBConstant.style_colors[i];
                    style = this.getStyle(color);
                    ipOrg = ip;
                    i++;
                    i = i % 2;
                }
                vo.setStyle(style);

                vo.setIp(dbHelper.getItemValue("ip"));
                vo.setFileSystem(dbHelper.getItemValue("file_system"));
                vo.setAvail(dbHelper.getItemValue("avail"));
                vo.setCapacity(dbHelper.getItemValue("capacity"));
                vo.setMountedOn(dbHelper.getItemValue("mounted_on"));
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

    public void addStatus(DiskSpaceVo vo, DbHelper dbHelper) throws Exception {
        String sql = null;
        int n;
        Object[] values = {vo.getAvail(), vo.getCapacity(), vo.getMountedOn(), vo.getStatus(),
            vo.getStatusDate(), FrameCharUtil.GbkToIso(vo.getRemark()),
            vo.getIp(), vo.getFileSystem()};
        Object[] values1 = {vo.getIp(), vo.getFileSystem(), vo.getAvail(), vo.getCapacity(), vo.getMountedOn(), vo.getStatus(),
            vo.getStatusDate(), FrameCharUtil.GbkToIso(vo.getRemark())
        };

        sql = "update mtr_current_disk_space_info set avail=?, capacity=?,mounted_on=?,status=?,status_date=to_date(?,'yyyy-mm-dd hh24:mi:ss'),remark=? "
                + "where ip=? and file_system=?";
        n = dbHelper.executeUpdate(sql, values);
        if (n != 1) {
            sql = "insert into mtr_current_disk_space_info(ip,file_system,avail,capacity,mounted_on,status,status_date,remark) values(?,?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?) ";
            n = dbHelper.executeUpdate(sql, values1);
        }
        sql = "insert into mtr_his_disk_space_info(ip,file_system,avail,capacity,mounted_on,status,status_date,remark) values(?,?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?) ";
        n = dbHelper.executeUpdate(sql, values1);
    }

    public void addStatuses(Vector vos) throws Exception {
        DbHelper dbHelper = null;
        DiskSpaceVo vo;

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE_T);
            //dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            for (int i = 0; i < vos.size(); i++) {
                vo = (DiskSpaceVo) vos.get(i);
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
