/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.dao;
import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.CommuConstant;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.util.Encryption;
import com.goldsign.escommu.util.PubUtil;
import com.goldsign.escommu.util.StringUtil;
import com.goldsign.escommu.vo.OperTypeReqVo;
import com.goldsign.escommu.vo.OperTypeRspVo;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class OperLoginDao {
    private static Logger logger = Logger.getLogger(OperLoginDao.class.
			getName());
    
    public OperTypeRspVo operLogin(OperTypeReqVo operTypeReqVo) throws Exception {
        boolean result = false;
        DbHelper dbHelper = null;
        Object[] values = {operTypeReqVo.getOperCode(),operTypeReqVo.getDeviceId(),
            DateHelper.utilDateToSqlTimestamp(DateHelper.getDatetimeFromYYYY_MM_DD_HH_MM_SS(operTypeReqVo.getLoginTime()))};
        Object[] values2 = {DateHelper.utilDateToSqlTimestamp(DateHelper.getDatetimeFromYYYY_MM_DD_HH_MM_SS(operTypeReqVo.getLoginTime())),
            operTypeReqVo.getOperCode(),operTypeReqVo.getDeviceId(),operTypeReqVo.getOperCode(),operTypeReqVo.getDeviceId()};
                    
        OperTypeRspVo operTypeRspVo = new OperTypeRspVo();
        try {
            dbHelper = new DbHelper("", AppConstant.DATA_DBCPHELPER.getConnection());
            String sqlStr = "select * from "+AppConstant.COM_ST_P+"OP_SYS_OPERATOR where SYS_OPERATOR_ID ='"+operTypeReqVo.getOperCode()+"'";
            
            dbHelper.setAutoCommit(false);
            result = dbHelper.getFirstDocument(sqlStr);
            if(!result){
                operTypeRspVo.setTypeCode("004");
                return operTypeRspVo;
            }
            String password = dbHelper.getItemValue("SYS_PASSWORD_HASH");
            String password2 = new Encryption().biEncrypt(CommuConstant.ENCRYPT_KEY, operTypeReqVo.getPassword());
            if(!password.equals(password2)){
                operTypeRspVo.setTypeCode("005");
                return operTypeRspVo;
            }
            //sqlStr = "select * from "+AppConstant.COM_ST_P+"OP_SYS_GROUP_OPERATOR where SYS_OPERATOR_ID = '"+operTypeReqVo.getOperCode()+"'";
            sqlStr = "select min(b.group_level) SYS_GROUP_ID from "+AppConstant.COM_ST_P+"OP_SYS_GROUP_OPERATOR a "
                        + " INNER JOIN "+AppConstant.COM_TK_P+"IC_ES_ROLE b ON a.sys_group_id=b.sys_group_id "
                        + " where a.SYS_OPERATOR_ID = '"+operTypeReqVo.getOperCode()+"'";
            result = dbHelper.getFirstDocument(sqlStr);
            if(!result){
                operTypeRspVo.setTypeCode("006");
                return operTypeRspVo;
            }
            String operType = StringUtil.addBeforeZero(dbHelper.getItemValue("SYS_GROUP_ID"),3);
            
            if(operTypeReqVo.getLoginFlag().equals("1")){
                 sqlStr = "insert into "+AppConstant.COM_CM_P+"CM_EC_OPERATOR_LOG(oper_id,dev_id,login_time) "
                         + "values(?,?,?)";
                 dbHelper.executeUpdate(sqlStr, values);
                
            }else if(operTypeReqVo.getLoginFlag().equals("2")){
                sqlStr = "update "+AppConstant.COM_CM_P+"CM_EC_OPERATOR_LOG set exit_time=? where oper_id=? and dev_id=?"
                        + " and LOGIN_TIME = (SELECT MAX(LOGIN_TIME) FROM "+AppConstant.COM_CM_P+"CM_EC_OPERATOR_LOG where oper_id=? and dev_id=?) ";
                dbHelper.executeUpdate(sqlStr, values2);
            }
             
            operTypeRspVo.setTypeCode(operType);
          
            dbHelper.commitTran();
        } catch (Exception e) {
            PubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
            PubUtil.finalProcessForTran(dbHelper);

        }

        return operTypeRspVo;
    }
}
