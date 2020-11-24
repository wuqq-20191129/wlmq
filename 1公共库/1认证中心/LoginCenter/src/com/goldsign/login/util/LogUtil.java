/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.login.util;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.login.constant.LoginConstant;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author oywl
 */
public class LogUtil {
       /**
     * 日记记录
     *
     * @param request
     * @param operatorID
     * @return
     * @throws Exception
     */
    public static String logLoginInfo(String ip, String operatorID, DbHelper dbHelper ) throws Exception {
        String strSql = null;
        boolean result = false;
        String flowID = "";

        ArrayList pStmtValues = new ArrayList();
        String cur = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss").format(new Date());
        try {
//            dbHelper.setAutoCommit(false);
            strSql = "insert into "+LoginConstant.DB_USER+LoginConstant.TABLE_SYS_PRE+"op_log_user_access(flow_id,sys_operator_id,login_time,remark) "
                    + "values(?,?,to_date(?,'YYYY-MM-DD HH24:MI:SS'),?) ";
            flowID = new Integer(getTableSequence("seq_"+LoginConstant.TABLE_SYS_PRE+"op_log_user_access", dbHelper)).toString();
            pStmtValues.add(new Integer(flowID));
            pStmtValues.add(operatorID);
            pStmtValues.add(cur);
            pStmtValues.add(ip==null?"":ip);
            dbHelper.executeUpdate(strSql, pStmtValues.toArray());
//            dbHelper.commitTran();
//            dbHelper.setAutoCommit(true);
        } catch (Exception e) {
//            e.printStackTrace();
            throw e;
//            PubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
//            PubUtil.finalProcessForTran(dbHelper, logger);
        }
        return flowID;
    }
    
        /**
     * 取sequence Id
     *
     * @param seqName
     * @param dbHelper
     * @return
     * @throws Exception
     */
    public static int getTableSequence(String seqName, DbHelper dbHelper) throws Exception {
        String strSql = "select "+LoginConstant.DB_USER+LoginConstant.TABLE_SYS_PRE + seqName + ".nextval from dual";
        dbHelper.getFirstDocument(strSql);
        return dbHelper.getItemIntValue("nextval");
    }
    
    
    /**
     * 登出日记记录
     *
     * @param request
     * @param operatorID
     * @return
     * @throws Exception
     */
    public static int logLogoutInfo(String flowID, String operatorID, DbHelper dbHelper ) throws Exception {
        String strSql = null;
        int result=0;

        ArrayList pStmtValues = new ArrayList();
        String cur = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss").format(new Date());
        try {
//            dbHelper.setAutoCommit(false);
            strSql = "update "+LoginConstant.DB_USER+LoginConstant.TABLE_SYS_PRE+"op_log_user_access set logout_time=to_date(?,'YYYY-MM-DD HH24:MI:SS') "
                    + "where flow_id=? and sys_operator_id=?";
            pStmtValues.add(cur);
            pStmtValues.add(new Integer(flowID));
            pStmtValues.add(operatorID);
            result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());
//            dbHelper.commitTran();
//            dbHelper.setAutoCommit(true);
        } catch (Exception e) {
//            e.printStackTrace();
            throw e;
//            PubUtil.handleExceptionForTran(e, logger, dbHelper);
        } finally {
//            PubUtil.finalProcessForTran(dbHelper, logger);
        }
        return result;
    }
}
