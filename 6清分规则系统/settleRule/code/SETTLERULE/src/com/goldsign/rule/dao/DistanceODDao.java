/*
 * 文件名：DistanceODDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.rule.env.RuleConstant;
import com.goldsign.rule.util.Util;
import com.goldsign.rule.vo.DistanceODVo;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;

/*
 * OD路径查询
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-11
 */

public class DistanceODDao {
    
    private static Logger logger = Logger.getLogger(DistanceODDao.class.getName());
    
    /**
     * 查询
     * @param vo
     * @return 
     */
    public Vector select(DistanceODVo vo) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        String strWhere = "";
        boolean result = false;
        Vector v = new Vector();
        FrameDBUtil util = new FrameDBUtil();
        double minDistance = 0;
        //里程比阀值
        double threshold = util.getProportionThres();
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            
            //添加查询条件
            strWhere += FramePubUtil.sqlWhereAnd(vo.getRecordFlag(),"record_flag");
            strWhere += FramePubUtil.sqlWhereAnd(vo.getoLineId(),"o_line_id");
            strWhere += FramePubUtil.sqlWhereAnd(vo.getoStationId(),"o_station_id");
            strWhere += FramePubUtil.sqlWhereAnd(vo.geteLineId(),"e_line_id");
            strWhere += FramePubUtil.sqlWhereAnd(vo.geteStationId(),"e_station_id");
            
            //取最短路径
            minDistance = getMinDistance(dbHelper, strWhere);
            
            //取所有结果集
            strSql = "SELECT * FROM sr_distance_od  WHERE 1=1 ";
            result = dbHelper.getFirstDocument(strSql+strWhere);
            
            while (result) {
                DistanceODVo pg = setRecordText(dbHelper, util);
                pg.setMinDistance(String.valueOf(minDistance));
                
                //设置有是否有效路径
                setIsValid(pg, threshold, util);
                v.add(pg);
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
     * 取结果集
     * @param dbHelper
     * @param util
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    private DistanceODVo setRecordText(DbHelper dbHelper, FrameDBUtil util) throws SQLException, Exception {
        DistanceODVo pg = new DistanceODVo();
        
        pg.setoStationId(dbHelper.getItemValue("o_station_id"));
        pg.setoLineId(dbHelper.getItemValue("o_line_id"));
        pg.seteLineId(dbHelper.getItemValue("e_line_id"));
        pg.seteStationId(dbHelper.getItemValue("e_station_id"));
        pg.setPassStations(dbHelper.getItemValue("pass_stations"));
        pg.setVersion(dbHelper.getItemValue("version"));
        pg.setPassTime(dbHelper.getItemValue("pass_time"));
        pg.setChangeTimes(dbHelper.getItemValue("change_times"));
        pg.setRecordFlag(dbHelper.getItemValue("record_flag"));
        pg.setStationsNum(dbHelper.getItemValue("stations_num"));
        pg.setDistance(String.valueOf(dbHelper.getItemDoubleValue("distance")));
        pg.setId(dbHelper.getItemValue("id"));
        pg.setCreateTime(dbHelper.getItemValue("create_time"));
        pg.setCreateOperator(dbHelper.getItemValue("create_operator"));
        
        //取代码中文义
        pg.setoLineIdText(util.getTextByCode(pg.getoLineId(), RuleConstant.LINES));
        pg.seteLineIdText(util.getTextByCode(pg.geteLineId(), RuleConstant.LINES));
        pg.setoStationIdText(util.getTextByCode(pg.getoStationId(), pg.getoLineId(), RuleConstant.STATIONS));
        pg.seteStationIdText(util.getTextByCode(pg.geteStationId(), pg.geteLineId(), RuleConstant.STATIONS));
        pg.setRecordFlagText(util.getTextByCode(pg.getRecordFlag(), RuleConstant.PARAMS_VERSION, FrameDBUtil.PUB_FLAGS));
        
        return pg;
    }

    /**
     * 查询最短路径
     * @param dbHelper
     * @return 
     */
    private double getMinDistance(DbHelper dbHelper, String where) throws SQLException {
        double minDistance = 0;
        boolean result = false;
        String strSql  = "select min(distance) min_distance from sr_distance_od  WHERE 1=1 ";
        strSql += where;
        strSql += "group by o_line_id,o_station_id,e_line_id,e_station_id";

        result = dbHelper.getFirstDocument(strSql);
        while(result){
            minDistance = dbHelper.getItemDoubleValue("min_distance");
            result = dbHelper.getNextDocument(); 
        }
        
        return minDistance;
    }

    
    /**
     * //设置有是否有效路径
     * @param pg 结果集
     * @param threshold 阀值 
     */
    private void setIsValid(DistanceODVo pg, double threshold, FrameDBUtil util) throws Exception {
        //起始站同一线路时，换乘次数为0的路径有效
        if(pg.getoLineId().equals(pg.geteLineId()) && pg.getChangeTimes().equals("0")){
            pg.setIsValid(RuleConstant.IS_VALID_YES);
            pg.setIsValidText(util.getTextByCode(RuleConstant.IS_VALID_YES, RuleConstant.PARAMS_IS_VALID, FrameDBUtil.PUB_FLAGS));
        }else{
            //起始站不同一线路时，（里程 - 最短里程）/最短里程 ？？ 比例阀值
            if(!pg.getoLineId().equals(pg.geteLineId())
                    && Util.isValidDistance(pg.getDistance(), pg.getMinDistance(), threshold)){
                pg.setIsValid(RuleConstant.IS_VALID_YES);
                pg.setIsValidText(util.getTextByCode(RuleConstant.IS_VALID_YES, RuleConstant.PARAMS_IS_VALID, FrameDBUtil.PUB_FLAGS));
            }else{
                pg.setIsValid(RuleConstant.IS_VALID_NO);
                pg.setIsValidText(util.getTextByCode(RuleConstant.IS_VALID_NO, RuleConstant.PARAMS_IS_VALID, FrameDBUtil.PUB_FLAGS));
            }
        }
    }

}
