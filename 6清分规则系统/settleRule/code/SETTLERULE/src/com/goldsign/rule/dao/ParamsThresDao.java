/*
 * 文件名：ParamsThresDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.dao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.rule.env.RuleConstant;
import com.goldsign.rule.vo.OperResult;
import com.goldsign.rule.vo.ParamsThresVo;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;

/*
 * 阀值参数配置 
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-25
 */

public class ParamsThresDao {
    
    private static Logger logger = Logger.getLogger(ParamsThresDao.class.getName());
    
    /**
     * 查询
     * @param vo
     * @return 
     */
    public Vector select(ParamsThresVo vo) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        String strWhere = "";
        boolean result = false;
        Vector v = new Vector();
        FrameDBUtil util = new FrameDBUtil();
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            
            //添加查询条件
            strWhere += FramePubUtil.sqlWhereAnd(vo.getRecordFlag(),"record_flag");
            strWhere += FramePubUtil.sqlWhereAnd(vo.getId(),"id");
            
            //取所有结果集
            strSql = "SELECT * FROM sr_params_threshold  WHERE 1=1 ";
            result = dbHelper.getFirstDocument(strSql+strWhere);
            
            while (result) {
                ParamsThresVo pg = setRecordText(dbHelper, util);
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
     * 增加
     * @param vo
     * @return
     * @throws Exception 
     */
    public OperResult insert(ParamsThresVo vo) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        OperResult result = new OperResult();
        int n = 0;
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            vo.setId(String.valueOf(FrameDBUtil.getTableSequence("seq_sr_params_threshold", dbHelper)));
            
            strSql = "insert into sr_params_threshold (id, distance_thres, station_thres, change_thres, time_thres,"
                    + " description, record_flag, version, update_time, update_operator) "
                    + " select ?, ?, ?, ?, ?, ?, ?,"
                    + " to_char(sysdate,'yyyyMMddhh24mi'), sysdate, ? from dual";
            Object[] values = {vo.getId(),vo.getDistanceThres(),vo.getStationThres(),vo.getChangeThres(),
                                vo.getTimeThres(),FrameUtil.IsoToGbk(vo.getDescription()),
                                RuleConstant.RECORD_FLAG_DRAFT, FrameUtil.IsoToGbk(vo.getUpdateOperator())};
            n = dbHelper.executeUpdate(strSql, values);
            
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
        } catch (Exception e) {
            result.setRetMsg(e.getMessage());
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        
        result.setUpdateNum(n);
        result.setUpdateOb(vo);
        return result;
    }

    /**
     * 删除
     * @param vo
     * @return
     * @throws Exception 
     */
    public int remove(Vector keyIDs) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        int result = 0;
        String id = "";
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            
            strSql = "DELETE FROM sr_params_threshold WHERE id = ? and record_flag <> '" + RuleConstant.RECORD_FLAG_USE + "'";
            for (int i = 0; i < keyIDs.size(); i++) {
                id = (String) keyIDs.get(i);
                Object[] values = {id};
                if(dbHelper.executeUpdate(strSql,values)>0){
                    result++;
                }
            }
            
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return result;
    }

    /**
     * 修改
     * @param vo
     * @return
     * @throws Exception 
     */
    public OperResult update(ParamsThresVo vo) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        int n = 0;
        OperResult result = new OperResult();
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            
            strSql = "UPDATE sr_params_threshold SET distance_thres = ?, station_thres = ?, change_thres = ?,"
                    + " time_thres = ?, description = ?, update_time=sysdate, update_operator=? WHERE id = ? and record_flag = ?";
            Object[] values = {vo.getDistanceThres(),vo.getStationThres(),vo.getChangeThres(),
                                vo.getTimeThres(),FrameUtil.IsoToGbk(vo.getDescription()),
                                FrameUtil.IsoToGbk(vo.getUpdateOperator()),vo.getId() ,RuleConstant.RECORD_FLAG_DRAFT};
            
            n = dbHelper.executeUpdate(strSql, values);
            
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        
        result.setUpdateNum(n);
        result.setUpdateOb(vo);
        return result;
    }
    
    /**
     * //审核
     * @param vo
     * @return
     * @throws Exception 
     */
    public OperResult auditUpdate(ParamsThresVo vo) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        String strSql2 = null;
        int result = 0;
        FrameDBUtil util = new FrameDBUtil();
        OperResult operResult = new OperResult();
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            
            //更新之前的数据为历史版本
            strSql = "UPDATE sr_params_threshold SET update_time = sysdate,update_operator = ?, record_flag = ? WHERE record_flag = ? ";
            //审核数据为当前版本
            strSql2 = "UPDATE sr_params_threshold SET update_time = sysdate,update_operator = ?, record_flag = ? WHERE id = ? ";
           
            Object[] values = {vo.getUpdateOperator(), RuleConstant.RECORD_FLAG_HISTORY, RuleConstant.RECORD_FLAG_USE};
            Object[] values2 = {vo.getUpdateOperator(), RuleConstant.RECORD_FLAG_USE, vo.getId()};
            
            //更新之前的数据为历史版本
            result = dbHelper.executeUpdate(strSql,values);
            //审核后更新草稿版本为当前版本数据
            result = dbHelper.executeUpdate(strSql2,values2);
            
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        
        operResult.setUpdateNum(result);
        operResult.setUpdateOb(vo);
        return operResult;
    }
    
    
    /**
     * 取结果集
     * @param dbHelper
     * @param util
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    private ParamsThresVo setRecordText(DbHelper dbHelper, FrameDBUtil util) throws SQLException, Exception {
        ParamsThresVo pg = new ParamsThresVo();
        
        pg.setId(dbHelper.getItemValue("id"));
        pg.setChangeThres(dbHelper.getItemValue("change_thres"));
        pg.setDescription(dbHelper.getItemValueIso("description"));
        pg.setDistanceThres(String.valueOf(dbHelper.getItemDoubleValue("distance_thres")));
        pg.setStationThres(dbHelper.getItemValue("station_thres"));
        pg.setTimeThres(dbHelper.getItemValue("time_thres"));
        pg.setVersion(dbHelper.getItemValue("version"));
        pg.setRecordFlag(dbHelper.getItemValue("record_flag"));
        pg.setUpdateTime(dbHelper.getItemValue("update_time"));
        pg.setUpdateOperator(dbHelper.getItemValue("update_operator"));
        //取代码中文义
        pg.setRecordFlagText(util.getTextByCode(pg.getRecordFlag(), RuleConstant.PARAMS_VERSION, FrameDBUtil.PUB_FLAGS));
        
        return pg;
    }

}
