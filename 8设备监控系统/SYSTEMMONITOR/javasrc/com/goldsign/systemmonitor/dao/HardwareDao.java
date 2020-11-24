package com.goldsign.systemmonitor.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameCharUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.systemmonitor.vo.HardwareVo;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 * 硬盘状态的dao类。定义了与数据库交换的方法。。查。。增删改
 *
 *
 */
public class HardwareDao {
    //记录日志信息
    static Logger logger = Logger.getLogger(HardwareDao.class);
    //空参构造器
    public HardwareDao() {
        super();
    }
    /**
     * 查询方法。return 一个线程安全的Vector集合
     *
     * @return
     * @throws Exception
     */
    public Vector queryStatuses() throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        HardwareVo vo;
        Vector v = new Vector();

        try {
            //通过DbHelper取得数据库的连接
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            //sql查询语句
            strSql = "select A.status_date,A.ip,A.status,A.remark,B.name,B.type,B.ip_value "
                    + "from mtr_current_hardware_info A,mtr_device_code B "
                    + "where A.ip =B.ip(+) order by ip_value";
            result = dbHelper.getFirstDocument(strSql);
            //当取得数据时。封装准备回显的数据
            while (result) {
                vo = new HardwareVo();
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

    /**
     * 定义增加方法
     *
     * @param vo
     * @throws Exception
     */
    public void addStatus(HardwareVo vo) throws Exception {
        DbHelper dbHelper = null;
        String sql = null;
        int n;
        Object[] values = {vo.getStatusDate(), vo.getStatus(), FrameCharUtil.GbkToIso(vo.getRemark()), vo.getIp()};
        Object[] values1 = {vo.getStatusDate(), vo.getIp(), vo.getStatus(), FrameCharUtil.GbkToIso(vo.getRemark())};
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE_T);
            dbHelper.setAutoCommit(false);
            sql = "update mtr_current_hardware_info set status_date=to_date(?,'yyyy-mm-dd hh24:mi:ss'), status=?,remark=? where ip=? ";
            n = dbHelper.executeUpdate(sql, values);
            if (n != 1) {
                sql = "insert into mtr_current_hardware_info(status_date,ip,status,remark) values(to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?) ";
                n = dbHelper.executeUpdate(sql, values1);
            }
            sql = "insert into mtr_his_hardware_info(status_date,ip,status,remark) values(to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?) ";
            n = dbHelper.executeUpdate(sql, values1);

            dbHelper.commitTran();
        } catch (Exception e) {
            FramePubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            FramePubUtil.finalProcessForTran(dbHelper);
        }
    }
}
