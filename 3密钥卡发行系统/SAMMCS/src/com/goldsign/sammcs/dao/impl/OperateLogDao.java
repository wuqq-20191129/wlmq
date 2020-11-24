/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.sammcs.dao.impl;

import com.goldsign.csfrm.dao.impl.BaseDao;
import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.sammcs.dao.IOperateLogDao;
import com.goldsign.sammcs.env.AppConstant;
import com.goldsign.sammcs.util.PubUtil;
import com.goldsign.sammcs.vo.OperateLogVo;
import java.lang.Override;
import org.apache.log4j.Logger;

/**
 *
 * @author lenovo
 */
public class OperateLogDao extends BaseDao implements IOperateLogDao{

    private static Logger logger = Logger.getLogger(OperateLogDao.class.getName());
    

    /*
     * 插入操作日志
     */
    @Override
    public void insertOperaterLogs(OperateLogVo vo) {
        DbHelper dbHelper = null;
    
        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            
            String sqlStr = "insert into W_ACC_TK.W_IC_SAM_OPER_LOGGING(serialno,sys_type,operator_id,op_time,module_id,oper_type,description) "
                    + "values(W_ACC_TK.W_SEQ_W_IC_SAM_OPER_LOGGING.nextval,'1',?,SYSDATE,?,?,?)";

            Object[] values = { vo.getOperId(), vo.getModuleId(),vo.getOperType(), vo.getDescription()};
            
            logger.info("sqlStr:" + sqlStr);
            
            dbHelper.executeUpdate(sqlStr, values);
        } catch (Exception e) {
            PubUtil.handleExceptionNoThrow(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }
    }

}
