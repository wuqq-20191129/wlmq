/*
 * 文件名：ParamsStationDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.dao;

import com.goldsign.frame.constant.FrameCodeConstant;
import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.rule.env.RuleConstant;
import com.goldsign.rule.util.NumberUtil;
import com.goldsign.rule.vo.OperResult;
import com.goldsign.rule.vo.ParamsStationVo;
import java.util.Vector;
import org.apache.log4j.Logger;


/*
 * 清分规则系统 参数设置DAO
 * @author     wangkejia
 * @version    V1.0
 */

public class ParamsStationDao {
    
    private static Logger logger = Logger.getLogger(
            ParamsStationDao.class.getName());
    
    public ParamsStationDao() {
        super();
    }

    /**
     * 查询
     * @ParamsStation vo
     * @return 
     */
   public Vector select(ParamsStationVo vo) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        String strWhere = null;
        String strAnd =null;
        Vector result = new Vector();
        boolean result1;
        FrameDBUtil util = new FrameDBUtil();
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);

            strSql = " SELECT * from acc_st.sr_params_station ";
            strWhere=" where 1=1";
            strWhere +=FramePubUtil.sqlWhereAnd(vo.getPresentStation(),"P_STATION_ID");
            strWhere +=FramePubUtil.sqlWhereAnd(vo.getNextStation(),"N_STATION_ID");
            strWhere +=FramePubUtil.sqlWhereAnd(vo.getLine(),"LINE_ID");
            strWhere +=FramePubUtil.sqlWhereAnd(vo.getNextTransferStation(),"N_T_STATION_ID");
            strWhere +=FramePubUtil.sqlWhereAnd(vo.getRecordFlag(),"record_flag");
            
            String beginOpTime = vo.getBeginCreateTime();
            String endOpTime = vo.getEndCreateTime();
            if(beginOpTime != null && !"".equals(beginOpTime)){
                strWhere += " and create_time >= to_date('" + NumberUtil.getDateTimeBeginValue(beginOpTime) + "','yyyy-MM-dd hh24:mi:ss')";
            }
            if(endOpTime != null && !"".equals(endOpTime)){
                strWhere += " and create_time <= to_date('" + NumberUtil.getDateTimeEndValue(endOpTime) + "','yyyy-MM-dd hh24:mi:ss')";
            }

            strAnd=" order by LINE_ID,P_STATION_ID,N_STATION_ID,record_flag";
                
           result1 = dbHelper.getFirstDocument(strSql+strWhere+strAnd);
                  
            while (result1) {
                ParamsStationVo pg = new ParamsStationVo();
                pg.setPresentStation(dbHelper.getItemValue("P_STATION_ID"));
                pg.setPresentStationTxt(util.getTextByCode(dbHelper.getItemValue("P_STATION_ID"),dbHelper.getItemValue("LINE_ID"),RuleConstant.STATIONS));
                pg.setNextStation(dbHelper.getItemValue("N_STATION_ID"));
                pg.setNextStationTxt(util.getTextByCode(dbHelper.getItemValue("N_STATION_ID"),dbHelper.getItemValue("LINE_ID"),RuleConstant.STATIONS));
                pg.setLine(dbHelper.getItemValue("LINE_ID"));
                pg.setLineTxt(util.getTextByCode(dbHelper.getItemValue("LINE_ID"),RuleConstant.LINES));
                pg.setNextTransferStation(dbHelper.getItemValue("N_T_STATION_ID"));
                pg.setNextTransferStationTxt(util.getTextByCode(dbHelper.getItemValue("N_T_STATION_ID"),dbHelper.getItemValue("LINE_ID"),RuleConstant.STATIONS));
                pg.setMileage(dbHelper.getItemValue("MILEAGE"));
                pg.setRecordFlag(dbHelper.getItemValue("record_flag"));
                pg.setVersion(dbHelper.getItemValue("version"));
                pg.setCreateTime(dbHelper.getItemValue("create_time"));
                pg.setOperator(FrameUtil.GbkToIso(dbHelper.getItemValue("create_operator")));
                pg.setRecordFlagText(util.getTextByCode(pg.getRecordFlag(), RuleConstant.PARAMS_VERSION, FrameDBUtil.PUB_FLAGS));
                result.add(pg);
                
                result1 = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return result;
    }

    /**
     * 增加
     * @ParamsStation vo
     * @return
     * @throws Exception 
     */
  public OperResult insert(ParamsStationVo vo,String operatorID) throws Exception {
        DbHelper dbHelper = null;
        String strInsert = null;
        int n = 0;
        OperResult result = new OperResult();
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);

     
            strInsert = "insert into sr_params_station(p_station_id, n_station_id, line_id, record_flag, version, mileage, create_time, create_operator, n_t_station_id)"
                    + " select ?, ?, ?, ?, to_char(sysdate,'yyyyMMddhh24mi'), ?, sysdate, ?, ? from dual"
                    + " where not exists( select 1 from sr_params_station where line_id=? and p_station_id=? and n_station_id=? and record_flag=?)";
            
            Object[] values={vo.getPresentStation(),vo.getNextStation(),vo.getLine(),FrameCodeConstant.RECORD_FLAG_DRAFT,vo.getMileage(),operatorID, vo.getNextTransferStation(),
                            vo.getLine(),vo.getPresentStation(),vo.getNextStation(),FrameCodeConstant.RECORD_FLAG_DRAFT};
            n = dbHelper.executeUpdate(strInsert, values);

            result.setUpdateNum(n);
            result.setUpdateOb(vo);
            if(n==0){
                throw new Exception("操作失败！不能重复插入,请操作草稿版本数据！");
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
     * 删除
     * @ParamsStation vo
     * @return
     * @throws Exception 
     */
    public int remove(Vector keyIDs) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        String primaryKey = null;
        int result = 0;
        int n = 0;
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            
            strSql = "DELETE FROM acc_st.sr_params_station WHERE line_id||p_station_id||n_station_id||version=? and record_flag=?";//仅能删除草稿参数
                          
            for (int i = 0; i < keyIDs.size(); i++) {
                primaryKey = (String) keyIDs.get(i);
                Object[] values = {primaryKey,FrameCodeConstant.RECORD_FLAG_DRAFT};
                n = dbHelper.executeUpdate(strSql,values);
                result += n;
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
     * @ParamsStation vo
     * @return
     * @throws Exception 
     */
    public OperResult update(Vector keyIDs,ParamsStationVo vo,String operatorID) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        int n = 0;
        OperResult result = new OperResult();
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            strSql="update acc_st.sr_params_station t set t.n_t_station_id=?, t.mileage=?, t.create_time=sysdate, t.create_operator=?"
                    + " where t.record_flag=? and t.line_id=? and t.p_station_id=? and t.n_station_id=?  ";
            
            Object[] values={vo.getNextTransferStation(),vo.getMileage(),operatorID,
                             FrameCodeConstant.RECORD_FLAG_DRAFT,vo.getLine(),vo.getPresentStation(),vo.getNextStation()};
            
            n = dbHelper.executeUpdate(strSql,values);

            result.setUpdateNum(n);
            result.setUpdateOb(vo);
            if(n==0){//更新失败
                throw new Exception("该版本状态下不能修改！");
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
    
    
     /*审核
     * @ParamsStation vo
     * @return 
     */
    public OperResult audit(Vector keyIDs,String operatorID) throws Exception {
        DbHelper dbHelper = null;
        String strSql2 = null;
        String strSql1 = null;
        int result = 0;
        ParamsStationVo v = new ParamsStationVo();
        String id = "";
        OperResult operResult = new OperResult();
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            
            //更新当前版本为历史版本
            strSql1 = "update acc_st.sr_params_station set record_flag=? where line_id||p_station_id||n_station_id=? and record_flag=?";
            //更新草稿版本为当前版本
            strSql2 = "UPDATE acc_st.sr_params_station SET record_flag = ? where line_id||p_station_id||n_station_id||version=? and record_flag=?";
            
            for (int i = 0; i < keyIDs.size(); i++) {
                id = (String) keyIDs.get(i);
                Object[] values1 = {FrameCodeConstant.RECORD_FLAG_HISTORY, id.substring(0,6), FrameCodeConstant.RECORD_FLAG_CURRENT};
                Object[] values2 = {FrameCodeConstant.RECORD_FLAG_CURRENT, id, FrameCodeConstant.RECORD_FLAG_DRAFT};
                
                //更新当前版本为历史版本
                dbHelper.executeUpdate(strSql1,values1);
                
                //更新草稿版本为当前版本
                int n = dbHelper.executeUpdate(strSql2,values2);
                result += n;
                   
                if(n ==0 ){
                    throw new Exception("当前站点:"+id.substring(0,2)+",下一站点:"+id.substring(2,4)+"该状态下不能审核");
                }//已经审核不能审核
                
                if(i == keyIDs.size()-1){
                    v.setLine(id.substring(0, 2));
                    v.setPresentStation(id.substring(2, 4));
                    v.setNextStation(id.substring(4, 6));
                    v.setRecordFlag(FrameCodeConstant.RECORD_FLAG_CURRENT);
                }
            }                
            
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        
        operResult.setUpdateNum(result);
        operResult.setUpdateOb(v);
        return operResult;
    }
  
}
