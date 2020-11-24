/*
 * 文件名：ParamsDao
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
import com.goldsign.rule.vo.ParamsVo;
import java.util.Vector;
import org.apache.log4j.Logger;


/*
 * 清分规则系统 参数设置DAO
 * @author     wangkejia
 * @version    V1.0
 */

public class ParamsDao {
    
      static Logger logger = Logger.getLogger(
            ParamsDao.class.getName());

    
    public ParamsDao() {
        super();
    }

    /**
     * 查询
     * @param vo
     * @return 
     */
   public Vector select(ParamsVo vo) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        String strWhere = null;
        Vector result = new Vector();
        boolean result1;
        FrameDBUtil util = new FrameDBUtil();
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);

            strSql = " SELECT type_code,type_description,code,value,description,record_flag,version,create_time,create_operator FROM acc_st.sr_params_sys ";
            strWhere=" where 1=1 ";
            
            strWhere += FramePubUtil.sqlWhereAnd(vo.getTypeCode(),"type_code");
            strWhere += FramePubUtil.sqlWhereAnd(vo.getCode(),"code");
            strWhere += FramePubUtil.sqlWhereAnd(vo.getRecordFlag(),"record_flag");
         
            if((FrameUtil.stringIsNotEmpty(vo.getBeginCreateTime()))){
                strWhere += " and create_time >= to_date('" + NumberUtil.getDateTimeBeginValue(vo.getBeginCreateTime()) + "','yyyy-MM-dd hh24:mi:ss')";
            }
            if((FrameUtil.stringIsNotEmpty(vo.getEndCreateTime()))){
                strWhere += " and create_time <= to_date('" + NumberUtil.getDateTimeEndValue(vo.getEndCreateTime()) + "','yyyy-MM-dd hh24:mi:ss')";
            }
      
            strWhere +=" order by type_code,code,record_flag";
            
            result1 = dbHelper.getFirstDocument(strSql+strWhere);
              
            while (result1) {
                ParamsVo pg = new ParamsVo();
                pg.setTypeCode(dbHelper.getItemValue("type_code"));
                pg.setTypeName(FrameUtil.GbkToIso(dbHelper.getItemValue("type_description")));
                pg.setCode(dbHelper.getItemValue("code"));
                pg.setValue(FrameUtil.GbkToIso(dbHelper.getItemValue("value")));
                pg.setDescription(FrameUtil.GbkToIso(dbHelper.getItemValue("description")));
                pg.setRecordFlag(dbHelper.getItemValue("record_flag"));
                pg.setVersion(dbHelper.getItemValue("version"));
                pg.setCreateTime(dbHelper.getItemValue("create_time"));
                pg.setOperator(FrameUtil.GbkToIso(dbHelper.getItemValue("create_operator")));
                pg.setRecordFlagText(util.getTextByCode(pg.getRecordFlag(), RuleConstant.PARAMS_VERSION, FrameDBUtil.PUB_FLAGS)); //版本状态取中文
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
     * @param vo
     * @return
     * @throws Exception 
     */
  public OperResult insert(ParamsVo vo,String operatorID) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        int n = 0;
        OperResult result = new OperResult();
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
     
            strSql = "insert into sr_params_sys (type_code, type_description, code, value, description, record_flag, version, create_time, create_operator)"
                    + " select ?,?,?,?,?,?,to_char(sysdate,'yyyyMMddhh24mi'),sysdate,? from dual"
                    + " where not exists( select 1 from sr_params_sys where type_code=? and code=? and record_flag=?)";
            Object[] values={vo.getTypeCode(),FrameUtil.IsoToGbk(vo.getTypeName()),vo.getCode(),FrameUtil.IsoToGbk(vo.getValue()),FrameUtil.IsoToGbk(vo.getDescription()),
                              FrameCodeConstant.RECORD_FLAG_DRAFT,operatorID,vo.getTypeCode(),vo.getCode(),FrameCodeConstant.RECORD_FLAG_DRAFT};
            
            n = dbHelper.executeUpdate(strSql, values);
            
            result.setUpdateNum(n);
            result.setUpdateOb(vo);
            if(n==0){
                throw new Exception("操作失败！该状态下，类型代码："+vo.getTypeCode()+"参数代码:"+vo.getCode()+"已经存在！不能重复插入！");
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
     * @param vo
     * @return
     * @throws Exception 
     */
    public int remove(Vector keyIDs) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        int result = 0;
        int n = 0;
        String typeCodes = "";
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            
            strSql = "DELETE FROM acc_st.sr_params_sys WHERE type_code||code||version=? and record_flag=?";
                          
            for (int i = 0; i < keyIDs.size(); i++) {
                typeCodes = (String) keyIDs.get(i);
                Object[] values = {typeCodes, FrameCodeConstant.RECORD_FLAG_DRAFT};
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
     * @param vo
     * @return
     * @throws Exception 
     */
    public OperResult update(Vector keyIDs,ParamsVo vo,String operatorID) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        int n = 0;
        OperResult result = new OperResult();
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            
            strSql="update acc_st.sr_params_sys t set t.type_description=?, t.value=?, t.description=?, t.create_time=sysdate, t.create_operator=?"
                    + " where t.type_code=? and t.code=? and t.record_flag=?";
            
            Object[] values={FrameUtil.IsoToGbk(vo.getTypeName()),FrameUtil.IsoToGbk(vo.getValue()),FrameUtil.IsoToGbk(vo.getDescription()),operatorID,
                                vo.getTypeCode(),vo.getCode(),FrameCodeConstant.RECORD_FLAG_DRAFT};
            
            n = dbHelper.executeUpdate(strSql,values);
            
            result.setUpdateNum(n);
            result.setUpdateOb(vo);
            if(n==0){//更新失败
                throw new Exception("类型代码"+vo.getTypeCode()+"参数代码"+vo.getCode()+"：该版本状态下不能修改！");
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
     * @param vo
     * @return 
     */
    public OperResult audit(Vector keyIDs,String operatorID) throws Exception {
        DbHelper dbHelper = null;
        String strSql2 = null;
        String strSql1 = null;
        int result = 0;
        ParamsVo v = new ParamsVo();
        String id = "";
        OperResult operResult = new OperResult();
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            dbHelper.setAutoCommit(false);
            
            //更新当前版本为历史版本
            strSql1 = "update acc_st.sr_params_sys set record_flag=? where type_code||code=? and record_flag=?";
            //更新草稿版本为当前版本
            strSql2 = "UPDATE acc_st.sr_params_sys SET record_flag = ? where type_code||code||version=? and record_flag=?";
            
            for (int i = 0; i < keyIDs.size(); i++) {
                id = (String) keyIDs.get(i);
                Object[] values1 = {FrameCodeConstant.RECORD_FLAG_HISTORY, id.substring(0,3), FrameCodeConstant.RECORD_FLAG_CURRENT};
                Object[] values2 = {FrameCodeConstant.RECORD_FLAG_CURRENT, id, FrameCodeConstant.RECORD_FLAG_DRAFT};
                
                //更新当前版本为历史版本
                dbHelper.executeUpdate(strSql1,values1);
                
                //更新草稿版本为当前版本
                int n = dbHelper.executeUpdate(strSql2,values2);
                result += n;
                   
                if(n ==0 ){
                    throw new Exception("类型代码:"+id.substring(0,2)+",参数代码:" +id.substring(2,3)+"该状态下不能审核");
                }//已经审核不能审核
                
                if(i == keyIDs.size()-1){
                    v.setTypeCode(id.substring(0, 2));
                    v.setCode(id.substring(2, 3));
                    v.setRecordFlag(FrameCodeConstant.RECORD_FLAG_CURRENT);
                }
            }
            operResult.setUpdateNum(result);
            operResult.setUpdateOb(v);
            
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        
        return operResult;
    }
  
}
