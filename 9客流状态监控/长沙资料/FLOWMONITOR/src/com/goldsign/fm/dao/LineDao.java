/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.dao;

import com.goldsign.fm.common.AppConstant;
import com.goldsign.fm.common.PubUtil;
import com.goldsign.fm.vo.LineTitle;
import com.goldsign.fm.vo.LineVo;
import com.goldsign.lib.db.util.DbHelper;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 * 线路
 * @author Administrator
 */
public class LineDao {

    private static Logger logger = Logger.getLogger(LineDao.class.getName());

    public Vector getLineAll() throws Exception {
        DbHelper dbHelper = null;
        String sql = "select line_id,line_name from op_prm_line where record_flag=? order by line_id";
        Object[] values = {"0"};
        boolean result = false;
        Vector v = new Vector();
        LineVo vo;

        try {
            dbHelper = new DbHelper("",AppConstant.dbcpHelper1.getConnection());
            result = dbHelper.getFirstDocument(sql, values);
            while (result) {
                vo = this.getResultRecord(dbHelper);
                v.add(vo);
                result = dbHelper.getNextDocument();

            }


        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }
        return v;

    }

    private LineVo getResultRecord(DbHelper dbHelper) throws SQLException {
        LineVo vo = new LineVo();
        vo.setLineId(dbHelper.getItemValue("line_id"));
        vo.setLineName(dbHelper.getItemValue("line_name"));

        return vo;
    }

    public Vector getLineList() throws Exception {

        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector list = new Vector();
        String lineName = "";

        try {
            dbHelper = new DbHelper("",AppConstant.dbcpHelper1.getConnection());
            strSql = "select line_id,line_name from op_prm_line where record_flag = '0' order by line_id";
            result = dbHelper.getFirstDocument(strSql);
            while (result) {
                LineTitle lineTitle = new LineTitle();
                lineTitle.setKey(dbHelper.getItemValue("line_id"));
                lineName = dbHelper.getItemValue("line_name");

                lineTitle.setTitle(lineName);
                list.add(lineTitle);
                result = dbHelper.getNextDocument();
            }

        } catch (SQLException e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return list;

    }
}
