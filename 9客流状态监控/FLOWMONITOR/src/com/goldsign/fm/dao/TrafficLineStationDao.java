/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.dao;

import com.goldsign.fm.common.AppConstant;
import com.goldsign.fm.common.PubUtil;
import com.goldsign.fm.vo.ScreenStationTrafficVO;
import com.goldsign.lib.db.util.DbHelper;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class TrafficLineStationDao {

    private static Logger logger = Logger.getLogger(TrafficLineStationDao.class.getName());

    private Vector getInitStationTraffic(String lineCode) throws Exception {

        String sql = "";
        boolean result = false;
        DbHelper dbHelper = null;
        Vector InitStationTraffics = new Vector();
        String engText = "";

        try {
            dbHelper = new DbHelper("",AppConstant.dbcpHelper1.getConnection());
            sql = "select  line_id,station_id,chinese_name,english_name from w_acc_st.w_op_prm_station where " + "record_flag='0'" + " and belong_line_id='" + lineCode + "'" + " order by sequence";
            result = dbHelper.getFirstDocument(sql);
            while (result) {
                ScreenStationTrafficVO vo = new ScreenStationTrafficVO();
                vo.setLineCode(dbHelper.getItemValue("line_id"));
                vo.setStationCode(dbHelper.getItemValue("station_id"));

                vo.setStationText(dbHelper.getItemValue("chinese_name"));
                engText = dbHelper.getItemValue("english_name");
                //      engText = this.sepEngText(engText,len);
                vo.setStationEngText(engText);

                vo.setTrafficIn("0");
                vo.setTrafficOut("0");
                InitStationTraffics.add(vo);
                result = dbHelper.getNextDocument();
            }
        } catch (SQLException e) {

            PubUtil.handleException(e, logger);

        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return InitStationTraffics;
    }

    /**
     * 线路各车站原始数据
     * @param lineCode
     * @param currentTrafficDate
     * @throws Exception 
     */
    public Vector getLineAllStationInOutTraffic(String lineCode, String currentTrafficDate) throws Exception {
        Vector initStationTraffics = null;
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        String stationCode = "";
        String trafficIn = "0";
        String trafficOut = "0";
        long nTrafficIn = 0;
        long nTrafficOut = 0;
        long nTrafficInTotal = 0;
        long nTrafficOutTotal = 0;
        String lineId;

        initStationTraffics = this.getInitStationTraffic(lineCode);

        try {
            dbHelper = new DbHelper("",AppConstant.dbcpHelper2.getConnection());

            strSql = "{call w_acc_mn.W_UP_FM_FLOW_STATION (?,?,?) }";
            int[] pInIndexes = {1,2};//存储过程输入参数索引列表
            Object[] pInStmtValues = {lineCode, currentTrafficDate};//存储过程输入参数值

            int[] pOutIndexes = {3};//存储过程输出参数索引列表
            int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_CURSOR};//存储过程输出参数值类型

            dbHelper.runStoreProcForOracle(strSql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            
            result = dbHelper.getFirstDocumentForOracle();//判断结果集是否为空
            
            while (result) {
                lineId =dbHelper.getItemValue("line_id");
                stationCode = dbHelper.getItemValue("station_id");
                trafficIn = dbHelper.getItemValue("traffic_in");
                trafficOut = dbHelper.getItemValue("traffic_out");

                nTrafficIn = dbHelper.getItemLongValue("traffic_in");
                nTrafficOut = dbHelper.getItemLongValue("traffic_out");
                nTrafficInTotal += nTrafficIn;
                nTrafficOutTotal += nTrafficOut;

                this.setStationTraffic(lineId, stationCode, trafficIn, trafficOut, initStationTraffics);
                result = dbHelper.getNextDocument();
            }
            this.addTotalTraffic(nTrafficInTotal, nTrafficOutTotal, initStationTraffics);
        } catch (SQLException e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return initStationTraffics;
    }

    private void setStationTraffic(String lineCode, String stationCode, String trafficIn, String trafficOut, Vector initStationTraffics) {
        if (initStationTraffics == null) {
            return;
        }
        ScreenStationTrafficVO vo = null;
        for (int i = 0; i < initStationTraffics.size(); i++) {
            vo = (ScreenStationTrafficVO) initStationTraffics.get(i);
            if (lineCode.equals(vo.getLineCode())
                    && stationCode.equals(vo.getStationCode())) {
                vo.setTrafficIn(trafficIn);
                vo.setTrafficOut(trafficOut);
                return;
            }
        }

    }

    private void addTotalTraffic(long nTrafficInTotal, long nTrafficOutTotal, Vector initStationTraffics) {
        if (initStationTraffics == null || initStationTraffics.isEmpty()) {
            return;
        }
        String name = "合计";
        String engName = "Total";

        String trafficInTotal = Long.toString(nTrafficInTotal);
        String trafficOutTotal = Long.toString(nTrafficOutTotal);
        ScreenStationTrafficVO vo = new ScreenStationTrafficVO();

        vo.setStationText(name);
        vo.setStationEngText(engName);

        vo.setTrafficIn(trafficInTotal);
        vo.setTrafficOut(trafficOutTotal);
        vo.setIsTotal(true);
        initStationTraffics.add(vo);

    }
}
