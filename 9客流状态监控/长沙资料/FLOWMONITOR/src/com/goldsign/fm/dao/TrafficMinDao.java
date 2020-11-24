/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.fm.dao;
import com.goldsign.fm.common.AppConstant;
import com.goldsign.fm.common.AppUtil;
import com.goldsign.fm.common.PubUtil;
import com.goldsign.fm.vo.DrawOriginResult;
import com.goldsign.lib.db.util.DbHelper;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class TrafficMinDao {
    private static Logger logger =Logger.getLogger(TrafficMinDao.class.getName());

    public Vector getLineStationSpanMinTraffic(String clickNode, String currentTrafficDate) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector list = new Vector();

        DrawOriginResult dor = null;
        String[] fieldsAllLine = {"line_id", "hour_min", "traffic"};
        String[] fieldsSingleLine = {"line_id", "hour_min", "traffic", "memo"};
        String[] fieldsStation = {"line_id", "station_id", "hour_min", "traffic", "memo"};

        try {
            dbHelper = new DbHelper("",AppConstant.dbcpHelper2.getConnection());
            strSql = "{call UP_FM_FLOW_MIN (?,?,?) }";
            int[] pInIndexes = {1,2};//存储过程输入参数索引列表
            Object[] pInStmtValues = {clickNode, currentTrafficDate};//存储过程输入参数值

            int[] pOutIndexes = {3};//存储过程输出参数索引列表
            int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_CURSOR};//存储过程输出参数值类型

            dbHelper.runStoreProcForOracle(strSql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            
            result = dbHelper.getFirstDocumentForOracle();//判断结果集是否为空
            PubUtil util = new PubUtil();
            
            while (result) {
                if (util.getNodeFlag(clickNode) == AppConstant.FLAG_ALLLINE) {
                    dor = this.getListSpanItem(dbHelper, fieldsAllLine, clickNode);
                }
                if (util.getNodeFlag(clickNode) == AppConstant.FLAG_SINGLELINE) {
                    dor = this.getListSpanItem(dbHelper, fieldsSingleLine, clickNode);
                }
                if (util.getNodeFlag(clickNode) == AppConstant.FLAG_STATION) {
                    dor = this.getListSpanItem(dbHelper, fieldsStation, clickNode);
                }
                list.add(dor);
                result = dbHelper.getNextDocument();
            }
        } catch (SQLException e) {
            PubUtil.handleException(e, logger);

        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return list;
    }

    private DrawOriginResult getListSpanItem(DbHelper dbHelper, String[] fields, String clickNode) throws SQLException {
        DrawOriginResult dor = new DrawOriginResult();
        String field = "";
        String lineCode = "";
        String memo = "";
        String hourMin ="";
        int traffic = 0;
        PubUtil util = new PubUtil();

        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            if (field.equals("line_id")) {
                lineCode = dbHelper.getItemValue("line_id");
                continue;
            }

            if (field.equals("memo")) {
                memo = dbHelper.getItemValue("memo");
                continue;
            }
            if (field.equals("hour_min")) {
                hourMin = dbHelper.getItemValue("hour_min");
                continue;
            }

            if (field.equals("traffic")) {
                traffic = dbHelper.getItemIntValue("traffic");
                continue;
            }
        }
        //dor.setRowkey(hourMin);
        dor.setRowkey(new AppUtil().getTitlesIndexForMin(AppUtil.MinTitles, hourMin));
        dor.setValue(traffic);
        if (util.getNodeFlag(clickNode) == AppConstant.FLAG_ALLLINE) {
            dor.setLinekey(lineCode);
        }
        if (util.getNodeFlag(clickNode) == AppConstant.FLAG_SINGLELINE || util.getNodeFlag(clickNode) == AppConstant.FLAG_STATION) {
            dor.setLinekey(memo);
        }

        return dor;


    }

}
