/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.dao.impl;

import com.goldsign.csfrm.dao.impl.BaseDao;
import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.etmcs.dao.IOperateLogDao;
import com.goldsign.etmcs.env.AppConstant;
import com.goldsign.etmcs.util.PubUtil;
import com.goldsign.etmcs.vo.OperateLogParam;
import com.goldsign.etmcs.vo.OperateLogVo;
import com.goldsign.lib.db.util.DbHelper;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author lenovo
 */
public class OperateLogDao extends BaseDao implements IOperateLogDao{

    private static Logger logger = Logger.getLogger(OperateLogDao.class.getName());
    
    @Override
    public List<OperateLogVo> getOperaterLogs(OperateLogParam operateLogParam) {
        
        List<OperateLogVo> OperateLogVoRets = new ArrayList<OperateLogVo>();
        
        boolean result = false;
        
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            String sqlStr = "select * from W_ACC_TK.W_IC_ET_OPRTLOG ";
            sqlStr += " where to_char(oprt_time,'yyyyMMdd') >= '" + operateLogParam.getBeginDate() + "'"
                    + " and to_char(oprt_time,'yyyyMMdd') <= '" + operateLogParam.getEndDate() + "'";
            if(!StringUtil.isEmpty(operateLogParam.getOperId())){
                sqlStr += " and oper_id like '%" + operateLogParam.getOperId() + "%'";
            }
            if(!StringUtil.isEmpty(operateLogParam.getOprtContent())){
                sqlStr += " and oprt_content like '%" + operateLogParam.getOprtContent() + "%'";
            }

            logger.info("sqlStr:" + sqlStr);
            
            result = dbHelper.getFirstDocument(sqlStr);
            while (result) {
                
                String operId = dbHelper.getItemTrueValue("oper_id");
                String oprtTime = DateHelper.dateToStr19yyyy_MM_dd_HH_mm_ss(dbHelper.getItemTimestampValue("oprt_time"));
                String oprtContent = dbHelper.getItemTrueValue("oprt_content");
                String oprtResult = dbHelper.getItemTrueValue("oprt_result");
                
                OperateLogVo operateLogVoRet = new OperateLogVo();
                operateLogVoRet.setOperId(operId);
                operateLogVoRet.setOprtTime(oprtTime);
                operateLogVoRet.setOprtContent(oprtContent);
                operateLogVoRet.setOprtResult(oprtResult);
                
                OperateLogVoRets.add(operateLogVoRet);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }

        return OperateLogVoRets;
    }

    /*
     * 插入操作日志
     */
    @Override
    public void insertOperaterLogs(OperateLogVo vo) {
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            dbHelper.setAutoCommit(false);
            String sqlStr = "INSERT INTO W_ACC_TK.W_IC_ET_OPRTLOG (water_no, oper_id, oprt_time, oprt_content, oprt_result)"
                            + "VALUES (W_ACC_TK.W_SEQ_W_IC_ET_OPRTLOG.nextval, ?, SYSDATE, ?, ?)";
            Object[] values = { vo.getOperId(), vo.getOprtContent(), vo.getOprtResult()};
            
            logger.info("sqlStr:" + sqlStr);
            dbHelper.executeUpdate(sqlStr, values);
            
            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }
    }

}
