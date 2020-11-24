/*
 * 文件名：GenerateODDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.dao;

import com.goldsign.frame.constant.FrameCodeConstant;
import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.rule.env.RuleConstant;
import com.goldsign.rule.vo.DistanceChangeVo;
import com.goldsign.rule.vo.DistanceODVo;
import com.goldsign.rule.vo.OperResult;
import java.util.Vector;
import org.apache.log4j.Logger;


/*
 * 生成OD路径DAO
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-12-7
 */

public class GenerateODDao {
    
    private static Logger logger = Logger.getLogger(GenerateODDao.class.getName());

    public int getDistanceODMaxID() throws Exception{
        DbHelper dbHelper = null;
        int maxId = 0;
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            maxId = FrameDBUtil.getMaxId("sr_distance_od","");
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        
        return maxId;
    }

    /**
     * 插入OD记录
     * @param cods 
     */
    public OperResult insertOD(Vector cods, Vector changes) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        OperResult result = new OperResult();
        int n = 0;
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            
            updateODVersion(dbHelper);
            
            strSql = "insert into sr_distance_od"
                    + " (id, o_line_id, o_station_id, e_line_id, e_station_id, change_times,"
                    + " distance, version, record_flag, create_time, create_operator, pass_stations)"
                    + " values (?, ?, ?, ?, ?, ?, ?, to_char(sysdate,'yyyyMMddhh24'), ?, sysdate, ?, ?)";
            
            for(int i=0; i<cods.size(); i++){
                DistanceODVo vo = (DistanceODVo) cods.get(i);
                Object[] values = {vo.getId(),vo.getoLineId(),vo.getoStationId(),vo.geteLineId(),vo.geteStationId(),
                                    vo.getChangeTimes(),vo.getDistance(),RuleConstant.RECORD_FLAG_USE,
                                    FrameCodeConstant.OPERATER_ID,vo.getPassStations()};
                n += dbHelper.executeUpdate(strSql, values);

                dbHelper.close();
            }
            
            insertChanges(changes, dbHelper);
            
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
        } catch (Exception e) {
            result.setRetMsg(e.getMessage());
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        
        result.setUpdateNum(n);
        return result;
    }

    
    /**
     * 插入换乘明细记录
     * @param changes 
     */
    public void insertChanges(Vector changes, DbHelper dbHelper) throws Exception {
        String strSql = null;
        int n = 0;
        
        try {
            
            strSql = "insert into sr_distance_change"
                    + " (od_id, pchange_station_id, pass_line_out, pass_line_in, pass_distance, nchange_station_id)"
                    + " values (?, ?, ?, ?, ?, ?)";
            
            for(int i=0; i<changes.size(); i++){
                DistanceChangeVo vo = (DistanceChangeVo) changes.get(i);
                Object[] values = {vo.getId(), vo.getpChangeStationId(),
                                    vo.getPassLineOut(), vo.getPassLineIn(), vo.getPassDistance(), vo.getnChangeStationId()};
                n += dbHelper.executeUpdate(strSql, values);
                
                dbHelper.close();
            }
            
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        }        
    }

    /**
     * 更新当前数据为历史状态
     * @param dbHelper 
     */
    private void updateODVersion(DbHelper dbHelper) throws Exception {
        String strSql = null;
        int n = 0;
        
        try {
            strSql = "update sr_distance_od set record_flag = ? where record_flag = ?";
            
            Object[] values = {RuleConstant.RECORD_FLAG_HISTORY, RuleConstant.RECORD_FLAG_USE};
            n += dbHelper.executeUpdate(strSql, values);
            
            dbHelper.close();
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        }        
    }
}
