/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.dao;

import com.goldsign.fm.common.AppConstant;
import com.goldsign.fm.common.DBUtil;
import com.goldsign.fm.common.PubUtil;
import com.goldsign.fm.vo.ViewVo;
import com.goldsign.lib.db.util.DbHelper;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class TrafficNetDao {

    private Logger logger = Logger.getLogger(TrafficNetDao.class.getName());

    /**
     * 线网各线路进出站总客流
     * 车站进出站总客流
     * @throws Exception 
     */
    public Vector getLineStationTraffic(String clickNode, String currentTrafficDate) throws Exception {
        boolean result = false;
        Vector list = new Vector();
        String strSql = "";
        DbHelper dbHelper = null;
        DbHelper dbHelper1 = null;
        PubUtil util = new PubUtil();
        DBUtil dbUtil = new DBUtil();
        String[] fieldsAllLine = {"line_name", "traffic_in","traffic_out"};
        String[] fieldsSingleLine = {"line_name", "traffic_in", "traffic_out"};
        String[] fieldsStation = {"line_name", "traffic_in", "traffic_in", "traffic_out"};
        ViewVo vo = null;
        long totalIn = 0;
        long totalOut = 0;

        try {
            dbHelper = new DbHelper("",AppConstant.dbcpHelper2.getConnection());
            dbHelper1 = new DbHelper("",AppConstant.dbcpHelper1.getConnection());
            Vector lines = dbUtil.getParamTableFlags(dbHelper1, "op_prm_line", "line_id", "line_name");
            Vector stations = dbUtil.getParamTableFlags(dbHelper1, "op_prm_station", "line_id", "station_id", "chinese_name"); 
            
            strSql = "{call UP_FM_FLOW_NET (?,?,?) }";
            int[] pInIndexes = {1,2};//存储过程输入参数索引列表
            Object[] pInStmtValues = {clickNode, currentTrafficDate};//存储过程输入参数值

            int[] pOutIndexes = {3};//存储过程输出参数索引列表
            int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_CURSOR};//存储过程输出参数值类型

            dbHelper.runStoreProcForOracle(strSql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            
            result = dbHelper.getFirstDocumentForOracle();//判断结果集是否为空
            
            while (result) {
                if (util.getNodeFlag(clickNode) == AppConstant.FLAG_ALLLINE) {//所有线路
                    vo = this.getListItem(dbHelper, clickNode, fieldsAllLine, lines, stations);
                    totalIn += dbHelper.getItemLongValue("traffic_in");
                    totalOut += dbHelper.getItemLongValue("traffic_out");
                    vo.setTotalIn(String.valueOf(totalIn));
                    vo.setTotalOut(String.valueOf(totalOut));
                }

                if (util.getNodeFlag(clickNode) == AppConstant.FLAG_SINGLELINE)//单线路
                {
                    vo = this.getListItem(dbHelper, clickNode, fieldsSingleLine, lines, stations);
                }

                if (util.getNodeFlag(clickNode) == AppConstant.FLAG_STATION)//车站
                {
                    vo = this.getListItem(dbHelper, clickNode, fieldsStation, lines, stations);
                }
                list.add(vo);
                result = dbHelper.getNextDocumentForOracle();
            }
            if(!list.isEmpty()){
                 if (util.getNodeFlag(clickNode) == AppConstant.FLAG_ALLLINE)
                    list.add(this.getListItemForTotal(vo));

            }
        } catch (SQLException e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
            PubUtil.finalProcess(dbHelper1);
        }

        return list;
    }
    
    
    public Vector getLineStationTrafficForMin(String clickNode, String currentTrafficDate) throws Exception {
        boolean result = false;
        Vector list = new Vector();
        String strSql = "";
        DbHelper dbHelper = null;
       
        String[] fields = { "traffic_in", "traffic_out"};
        ViewVo vo = null;
    
        try {
            dbHelper = new DbHelper("",AppConstant.dbcpHelper2.getConnection());
            strSql = "{call UP_FM_FLOW_NET_TOTAL (?,?,?) }";
            int[] pInIndexes = {1,2};//存储过程输入参数索引列表
            Object[] pInStmtValues = {clickNode, currentTrafficDate};//存储过程输入参数值

            int[] pOutIndexes = {3};//存储过程输出参数索引列表
            int[] pOutTypes = {DbHelper.PARAM_OUT_TYPE_CURSOR};//存储过程输出参数值类型

            dbHelper.runStoreProcForOracle(strSql, pInIndexes, pInStmtValues, pOutIndexes, pOutTypes);//执行存储过程
            
            result = dbHelper.getFirstDocumentForOracle();//判断结果集是否为空
            
            while (result) {

                vo = this.getListItemForMin(dbHelper, clickNode, fields);
                list.add(vo);
                result = dbHelper.getNextDocument();
            }
           
        } catch (SQLException e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return list;
    }

    private ViewVo getListItem(DbHelper dbHelper, String clickNode, String[] fields, Vector lines, Vector stations) throws Exception {
        ViewVo vo = new ViewVo();
        PubUtil util = new PubUtil();
        DBUtil dbUtil = new DBUtil();
        String lineName = "";
        String lineCode = "";
        String stationName = "";
        String stationCode = "";
        String traffic = "";
        String trafficIn = "";
        String trafficOut = "";
        String trafficInName = "";
        String trafficOutName = "";
        String field = "";

        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            if (field.equals("line_name")) {
                lineCode = dbHelper.getItemValue("line_name");
                continue;
            }
            if (field.equals("station_name")) {
                stationCode = dbHelper.getItemValue("station_name");
                continue;
            }
            if (field.equals("traffic_in")) {
                trafficIn = this.getTrafficValue(dbHelper, "traffic_in");
                if (util.getNodeFlag(clickNode) == AppConstant.FLAG_ALLLINE) {
                    traffic = trafficIn;
                }
                continue;
            }

            if (field.equals("traffic_out")) {
                trafficOut = this.getTrafficValue(dbHelper, "traffic_out");
                continue;
            }
        }
        if (lineCode.length() != 0) {
            lineName = dbUtil.getTextByCode(lineCode, lines);// dbUtil.getTextByCodeForLineCode(lineCode);
        }
        if (stationCode.length() != 0) {
            stationName = dbUtil.getTextByCode(stationCode, lineCode, stations);//dbUtil.getTextByCodeForStationCode(stationCode,lineCode,false);
        }
        trafficInName = AppConstant.SCREEN_CAPTION_IN;
        trafficOutName = AppConstant.SCREEN_CAPTION_OUT;

        vo.setLine_id(lineCode);
        vo.setLine_name(lineName);
        vo.setStation_id(stationCode);
        vo.setStation_name(stationName);
        vo.setTraffic(traffic);
        vo.setTraffic_in(trafficIn);
        vo.setTraffic_in_name(trafficInName);
        vo.setTraffic_out(trafficOut);
        vo.setTraffic_out_name(trafficOutName);
        return vo;

    }
    
    private ViewVo getListItemForMin(DbHelper dbHelper, String clickNode, String[] fields) throws Exception {
        ViewVo vo = new ViewVo();
        PubUtil util = new PubUtil();
        DBUtil dbUtil = new DBUtil();
        String lineName = "";
        String lineCode = "";
        String stationName = "";
        String stationCode = "";
        String traffic = "";
        String trafficIn = "";
        String trafficOut = "";
        String trafficInName = "";
        String trafficOutName = "";
        String field = "";

        for (int i = 0; i < fields.length; i++) {
            field = fields[i];

            if (field.equals("traffic_in")) {
                trafficIn = this.getTrafficValue(dbHelper, "traffic_in");
                if (util.getNodeFlag(clickNode) == AppConstant.FLAG_ALLLINE) {
                    traffic = trafficIn;
                }
                continue;
            }

            if (field.equals("traffic_out")) {
                trafficOut = this.getTrafficValue(dbHelper, "traffic_out");
                continue;
            }
        }

        trafficInName = AppConstant.SCREEN_CAPTION_IN;
        trafficOutName = AppConstant.SCREEN_CAPTION_OUT;

        vo.setLine_id(lineCode);
        vo.setLine_name(lineName);
        vo.setStation_id(stationCode);
        vo.setStation_name(stationName);
        vo.setTraffic(traffic);
        vo.setTraffic_in(trafficIn);
        vo.setTraffic_in_name(trafficInName);
        vo.setTraffic_out(trafficOut);
        vo.setTraffic_out_name(trafficOutName);
        return vo;

    }
    
    private ViewVo getListItemForTotal(ViewVo lastVo ) throws Exception {
        ViewVo vo = new ViewVo();

        String trafficInName = "";
        String trafficOutName = "";
        trafficInName = AppConstant.SCREEN_CAPTION_IN;
        trafficOutName = AppConstant.SCREEN_CAPTION_OUT;

        vo.setLine_id("99");
        vo.setLine_name( AppConstant.SCREEN_CAPTION_TOTAL);
        vo.setStation_id("-1");
        vo.setStation_name("");
        vo.setTraffic("");
        vo.setTraffic_in(lastVo.getTotalIn());
        vo.setTraffic_in_name(trafficInName);
        vo.setTraffic_out(lastVo.getTotalOut());
        vo.setTraffic_out_name(trafficOutName);

        vo.setIsTotal(true);
        return vo;

    }



    private String getTrafficValue(DbHelper dbHelper, String fieldName) throws
            SQLException {
        String result = dbHelper.getItemValue(fieldName);
        if (result == null || result.length() == 0) {
            result = "0";
        }
        return result;

    }
}
