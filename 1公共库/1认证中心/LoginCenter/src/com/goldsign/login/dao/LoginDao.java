/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.login.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.login.constant.LoginConstant;
import com.goldsign.login.vo.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oywl
 */
public class LoginDao {

    private static Logger logger = Logger.getLogger(LoginDao.class.getName());

    public User findUserByAccount(String account, DbHelper dbHelper) throws Exception {
        List<User> resultList = new ArrayList<User>();
        try {
            String strSql = null;
            strSql = "select sys_operator_id,sys_password_hash,sys_operator_name,sys_employee_id,sys_expired_date,sys_status,login_num,failed_num,session_id,password_edit_date,edit_past_days "
                    + "from "+LoginConstant.DB_USER+LoginConstant.TABLE_SYS_PRE+"op_sys_operator where sys_operator_id=?";
            boolean result = false;
            ArrayList pStmtValues = new ArrayList();
            pStmtValues.add(account);
            result = dbHelper.getFirstDocument(strSql, pStmtValues.toArray());
            while (result) {
                User user = new User();
                user.setAccount(dbHelper.getItemValue("sys_operator_id"));
                user.setPassword(dbHelper.getItemValueWithoutTrim("sys_password_hash"));
                user.setUsername(dbHelper.getItemValue("sys_operator_name"));
                user.setEmployeeID(dbHelper.getItemValue("sys_employee_id"));
                user.setExpireDate(dbHelper.getItemValue("sys_expired_date"));
                user.setUserStatus(dbHelper.getItemValue("sys_status"));
                user.setLoginNum(dbHelper.getItemIntValue("login_num"));
                user.setFailedNum(dbHelper.getItemIntValue("failed_num"));
                user.setSessionID(dbHelper.getItemValue("session_id"));
                user.setEditPassWordDate(dbHelper.getItemValue("password_edit_date"));
                user.setEditPassWordDays(dbHelper.getItemIntValue("edit_past_days"));
                resultList.add(user);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "´íÎó:", e);
            throw e;
        } finally {
//            PubUtil.finalProcess(dbHelper, logger);
        }
        if (resultList.size() == 1) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    public int modifyUser(String operatorID, String fieldName, int fieldValue, DbHelper dbHelper) throws Exception {
        int result = 0;
        try {
            String strSql = null;
            strSql = "update "+LoginConstant.DB_USER+LoginConstant.TABLE_SYS_PRE+"op_sys_operator set " + fieldName + "=? " + "where sys_operator_id=?";

            ArrayList pStmtValues = new ArrayList();
            pStmtValues.add(new Integer(fieldValue));
            pStmtValues.add(operatorID);

            result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "´íÎó:", e);
            throw e;
        } finally {
//            PubUtil.finalProcess(dbHelper, logger);
        }
        return result;
    }

    public int modifyUser(String operatorID, String fieldName, String fieldValue, DbHelper dbHelper) throws Exception {
        int result = 0;
        try {
            String strSql = null;
            strSql = "update "+LoginConstant.DB_USER+LoginConstant.TABLE_SYS_PRE+"op_sys_operator set " + fieldName + "=? " + "where sys_operator_id=?";

            ArrayList pStmtValues = new ArrayList();
            pStmtValues.add(fieldValue);
            pStmtValues.add(operatorID);

            result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "´íÎó:", e);
            throw e;
        } finally {
//            PubUtil.finalProcess(dbHelper, logger);
        }
        return result;
    }
}
