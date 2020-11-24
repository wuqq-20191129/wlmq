package com.goldsign.systemmonitor.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameCharUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.systemmonitor.vo.ClusterVo;
import java.util.Vector;

import org.apache.log4j.Logger;

public class ClusterDao {

    static Logger logger = Logger.getLogger(ClusterDao.class);

    public ClusterDao() {
        super();
    }

    private String getStyle(String color) {
        return "background:" + color;
    }

    public Vector queryStatuses() throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;

        ClusterVo vo;
        Vector v = new Vector();
        String ip = "";
        String ipOrg = "";
        int i = 0;

        String color;
        String style = "";

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select A.ip,A.resource_name,A.status_date,A.node_name,A.status,A.status_desc,A.remark,"
                    + "B.name,B.type,B.ip_value "
                    + "from mtr_current_cluster_info A,mtr_device_code B "
                    + "where A.ip =B.ip(+) order by ip_value";

            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                vo = new ClusterVo();
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
                vo.setResourceName(dbHelper.getItemValueIso("resource_name"));
                vo.setStatusDate(dbHelper.getItemValue("status_date"));
                vo.setNodeName(dbHelper.getItemValueIso("node_name"));
                vo.setStatus(dbHelper.getItemValue("status"));
                vo.setStatusDesc(dbHelper.getItemValueIso("status_desc"));
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

    public void addStatus(ClusterVo vo, DbHelper dbHelper) throws Exception {

        String sql = null;

        int n;
        Object[] values = {vo.getStatusDate(), FrameCharUtil.GbkToIso(vo.getNodeName()), vo.getStatus(),
            FrameCharUtil.GbkToIso(vo.getStatusDesc()), FrameCharUtil.GbkToIso(vo.getRemark()),
            vo.getIp(), FrameCharUtil.GbkToIso(vo.getResourceName())};
        Object[] values1 = {vo.getIp(), FrameCharUtil.GbkToIso(vo.getResourceName()), vo.getStatusDate(),
            FrameCharUtil.GbkToIso(vo.getNodeName()),
            vo.getStatus(), FrameCharUtil.GbkToIso(vo.getStatusDesc()), FrameCharUtil.GbkToIso(vo.getRemark()),};

        sql = "update mtr_current_cluster_info set status_date=to_date(?,'yyyy-mm-dd hh24:mi:ss'), node_name=?,status=?,status_desc=?,remark=? "
                + "where ip=? and resource_name=?";

        n = dbHelper.executeUpdate(sql, values);
        if (n != 1) {
            sql = "insert into mtr_current_cluster_info(ip,resource_name,status_date,node_name,status,status_desc,remark) values(?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?) ";
            n = dbHelper.executeUpdate(sql, values1);
        }
        sql = "insert into mtr_his_cluster_info(ip,resource_name,status_date,node_name,status,status_desc,remark) values(?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?) ";
        n = dbHelper.executeUpdate(sql, values1);
    }

    public void addStatus(ClusterVo vo) throws Exception {
        DbHelper dbHelper = null;
        String sql = null;

        int n;
        Object[] values = {vo.getStatusDate(), FrameCharUtil.GbkToIso(vo.getNodeName()), vo.getStatus(),
            FrameCharUtil.GbkToIso(vo.getStatusDesc()), FrameCharUtil.GbkToIso(vo.getRemark()),
            vo.getIp(), FrameCharUtil.GbkToIso(vo.getResourceName())};
        Object[] values1 = {vo.getIp(), FrameCharUtil.GbkToIso(vo.getResourceName()), vo.getStatusDate(),
            FrameCharUtil.GbkToIso(vo.getNodeName()),
            vo.getStatus(), FrameCharUtil.GbkToIso(vo.getStatusDesc()), FrameCharUtil.GbkToIso(vo.getRemark()),};
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE_T);
            dbHelper.setAutoCommit(false);
            sql = "update mtr_current_cluster_info set status_date=to_date(?,'yyyy-mm-dd hh24:mi:ss'), node_name=?,status=?,A.status_desc=?,remark=? "
                    + "where ip=? and resource_name=?";

            n = dbHelper.executeUpdate(sql, values);
            if (n != 1) {
                sql = "insert into mtr_current_cluster_info(ip,resource_name,status_date,node_name,status,status_desc,remark) values(?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?) ";
                n = dbHelper.executeUpdate(sql, values1);
            }
            sql = "insert into mtr_his_cluster_info(ip,resource_name,status_date,node_name,status,status_desc,remark) values(?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?) ";
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
        ClusterVo vo;

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE_T);
            dbHelper.setAutoCommit(false);
            for (int i = 0; i < vos.size(); i++) {
                vo = (ClusterVo) vos.get(i);
                if (!vo.getNodeName().equals("")) {
                    //System.out.println(vo.getNodeName());
                    this.addStatus(vo, dbHelper);
                }
            }
            dbHelper.commitTran();
        } catch (Exception e) {
            FramePubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            FramePubUtil.finalProcessForTran(dbHelper);
        }
    }
}
