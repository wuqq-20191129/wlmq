/*
 * 文件名：DistanceDetailDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.rule.env.RuleConstant;
import com.goldsign.rule.vo.DistanceChangeVo;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;

/*
 * OD路径换乘查询
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-11
 */

public class DistanceDetailDao {
    
    private static Logger logger = Logger.getLogger(DistanceDetailDao.class.getName());
    
    /**
     * 查询
     * @param vo
     * @return 
     */
    public Vector select(DistanceChangeVo vo) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        String strWhere = "";
        boolean result = false;
        Vector v = new Vector();
        FrameDBUtil util = new FrameDBUtil();
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            
            //添加查询条件
            strWhere += FramePubUtil.sqlWhereAnd(vo.getId(),"od_id");
            
            //取所有结果集
            strSql = "SELECT * FROM sr_distance_change WHERE 1=1 " ;
            result = dbHelper.getFirstDocument(strSql + strWhere);
            
            while (result) {
                DistanceChangeVo pg = setRecordText(dbHelper, util);
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
    private DistanceChangeVo setRecordText(DbHelper dbHelper, FrameDBUtil util) throws SQLException, Exception {
        DistanceChangeVo pg = new DistanceChangeVo();
        
        pg.setId(dbHelper.getItemValue("od_id"));
        pg.setpChangeStationId(dbHelper.getItemValue("pchange_station_id"));
        pg.setnChangeStationId(dbHelper.getItemValue("nchange_station_id"));
        pg.setPassLineIn(dbHelper.getItemValue("pass_line_in"));
        pg.setPassLineOut(dbHelper.getItemValue("pass_line_out"));
        pg.setPassDistance(dbHelper.getItemValue("pass_distance"));
        
        //取代码中文义 上一换乘站-转出路线；下一换乘站-转入路线
        pg.setPassLineInText(util.getTextByCode(pg.getPassLineIn(), RuleConstant.LINES));
        pg.setPassLineOutText(util.getTextByCode(pg.getPassLineOut(), RuleConstant.LINES));
        pg.setpChangeStationIdText(util.getTextByCode(pg.getpChangeStationId(), pg.getPassLineOut(), RuleConstant.STATIONS));
        pg.setnChangeStationIdText(util.getTextByCode(pg.getnChangeStationId(), pg.getPassLineIn(), RuleConstant.STATIONS));
        
        return pg;
    }

}
