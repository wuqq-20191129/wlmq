/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.login.bo;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.login.constant.LoginConstant;
import com.goldsign.login.constant.LoginMsg;
import com.goldsign.login.dao.LoginDao;
import com.goldsign.login.dao.LoginModulesDao;
import com.goldsign.login.result.AuthResult;
import com.goldsign.login.result.EditResult;
import com.goldsign.login.result.ModulesResult;
import com.goldsign.login.util.Encryption;
import com.goldsign.login.util.LogUtil;
import com.goldsign.login.util.LoginCharUtil;
import com.goldsign.login.vo.AuthInParam;
import com.goldsign.login.vo.ModuleDistrVo;
import com.goldsign.login.vo.User;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oywl
 */
public class LoginBo implements ILoginBo {

    private static Logger logger = Logger.getLogger(LoginBo.class.getName());
    public static final String LOCKED = "1";
    public static final String UNLOCKED = "0";
    private static long DAYS = 7;

    /**
     * 验证方法
     *
     * @param account
     * @param password
     * @param sysFlag
     * @param dbHelper
     * @return
     */
    public AuthResult authorization(String account, String password, String sysFlag, DbHelper dbHelper) {
        return this.authorization(account, password, sysFlag, null, dbHelper);
    }

    /**
     * 修改密码
     *
     * @param account
     * @param oldPassword
     * @param newPassword
     * @param dbHelper
     * @return
     */
    public EditResult editPassword(String account, String oldPassword, String newPassword, DbHelper dbHelper) {
        EditResult editResult = new EditResult();
        try {
            if (account == null || account.trim().isEmpty()) {
                editResult.setResult(false);
                editResult.setMsg(LoginMsg.ACCOUNT_CAN_NOT_BE_NULL);
            } else if (oldPassword == null || oldPassword.trim().isEmpty()) {
                editResult.setResult(false);
                editResult.setMsg(LoginMsg.PASSWORD_CAN_NOT_BE_NULL);
            } else if (newPassword == null || newPassword.trim().isEmpty()) {
                editResult.setResult(false);
                editResult.setMsg(LoginMsg.NEW_PASSWORD_CAN_NOT_BE_NULL);
            } else {
//            step1:login
                AuthResult authResult = this.login(account, oldPassword, null, dbHelper);
//            step2:edit password
                if (authResult.getReturnCode().equals(ERROR_PASSWORD_NOT_IN_VALID_DATE)) {
                    User user = new User();
                    user.setAccount(account);
                    user.setPassword(oldPassword);
                    user.setNewPassword(newPassword);
                    if (!isOldPassword(user, dbHelper)) {
                        editResult.setResult(false);
                        editResult.setMsg(LoginMsg.PASSWORD_WRONG);
                    } else {
                        if (iseditPassword(user, dbHelper)) {
                            editResult.setResult(true);
                            editResult.setMsg(LoginMsg.PASSWORD_EDIT_SUCCESS);
                        } else {
                            editResult.setResult(false);
                        }
                    }
                } else {
                    editResult.setResult(false);
                    editResult.setMsg(authResult.getMsg());
                }
            }
        } catch (Exception e) {
            editResult.setResult(false);
            editResult.setMsg(LoginMsg.DB_CONNECT_EXCEPTION);
            logger.log(Level.SEVERE, "database connect exception", e);
        } finally {
            try {
                if (dbHelper != null) {
                    dbHelper.closeConnection();
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Fail to close connection", e);
            }
        }
        return editResult;
    }

    /**
     * 验证方法2
     *
     * @param account
     * @param password
     * @param sysFlag
     * @param authInParam
     * @param dbHelper
     * @return
     */
    public AuthResult authorization(String account, String password, String sysFlag, AuthInParam authInParam, DbHelper dbHelper) {
        AuthResult authResult = new AuthResult();
        try {
            if (account == null || account.trim().isEmpty()) {
                authResult.setReturnCode(ERROR_PARAM_NULL);
                authResult.setMsg(LoginMsg.ACCOUNT_CAN_NOT_BE_NULL);
            } else if (password == null || password.trim().isEmpty()) {
                authResult.setReturnCode(ERROR_PARAM_NULL);
                authResult.setMsg(LoginMsg.PASSWORD_CAN_NOT_BE_NULL);
            } else if (sysFlag == null || sysFlag.trim().isEmpty()) {
                authResult.setReturnCode(ERROR_PARAM_NULL);
                authResult.setMsg(LoginMsg.SYSFLAG_CAN_NOT_BE_NULL);
            } else {
//            step1:login
                authResult = this.login(account, password, authInParam, dbHelper);
                authResult.getUser().setSysFlag(sysFlag);
//            step2:get modules
                if (authResult.getReturnCode().equals(SUCCESS_AUTH)) {
                    ModulesResult mr = this.findModules(account, sysFlag, authInParam, dbHelper);
                    authResult.setModules(mr.getModulePrilivedges());
                }
            }
        } catch (Exception e) {
            authResult.setReturnCode(ERROR_DATABASE_CONNECT_EXCEPTION);
            authResult.setMsg(LoginMsg.DB_CONNECT_EXCEPTION);
            logger.log(Level.SEVERE, "database connect exception", e);
        } finally {
            try {
                if (dbHelper != null) {
                    dbHelper.closeConnection();
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Fail to close connection", e);
            }
        }
        return authResult;
    }

    /**
     * 登录
     *
     * @param account
     * @param password
     * @param authInParam
     * @return
     * @throws Exception
     */
    private AuthResult login(String account, String password, AuthInParam authInParam, DbHelper dbHelper) throws Exception {
        AuthResult authResult = new AuthResult();
        User user = new LoginDao().findUserByAccount(account, dbHelper);
        if (user == null) {
            authResult.setReturnCode(ERROR_USER_NOT_EXIST);
            authResult.setMsg(LoginMsg.USER_NOT_EXIST);
        } else {
            if (user.getUserStatus().equals(this.LOCKED)) {
                authResult.setReturnCode(ERROR_USER_LOCKED);
                authResult.setMsg(LoginMsg.USER_LOCKED);
            } else if (!isInValidDate(user)) {
                authResult.setReturnCode(ERROR_USER_NOT_IN_VALID_DATE);
                authResult.setMsg(LoginMsg.USER_NOT_IN_VALID_DATE);
            } else if (!user.getPassword().equals(Encryption.biEncrypt(password))) {
                authResult.setReturnCode(ERROR_PASSWORD_WRONG);
                authResult.setMsg(LoginMsg.PASSWORD_WRONG);
                if (isOverFailedNum(user, dbHelper)) {
                    this.lockedUser(user, dbHelper);
                }
            } else if (isNeedEdit(user)) {
                authResult.setUser(user);
                authResult.setReturnCode(ERROR_PASSWORD_NOT_IN_VALID_DATE);
                authResult.setMsg(LoginMsg.PASSWORD_NOT_IN_VALID_DATE);
            } else {
                authResult.setUser(user);
                authResult.setReturnCode(SUCCESS_AUTH);
                authResult.setMsg(LoginMsg.SUCCESS_AUTH);
                authResult.setIp(authInParam.getIp());
                afterLogin(authResult, dbHelper);
            }
        }
        return authResult;
    }

    /**
     * 获取模块权限
     *
     * @param account
     * @param sysFlag
     * @param authInParam
     * @return
     * @throws Exception
     */
    private ModulesResult findModules(String account, String sysFlag, AuthInParam authInParam, DbHelper dbHelper) throws Exception {
        ModulesResult mr = new ModulesResult();
        List<ModuleDistrVo> modulePrilivedges =
                new LoginModulesDao().getThirdModulesByOperator(account, sysFlag, dbHelper);
        mr.setModulePrilivedges(modulePrilivedges);
        return mr;
    }

    private boolean isInValidDate(User user) {
        String date = new Timestamp(System.currentTimeMillis()).toString().substring(0, 10);
        String dbDate = new LoginCharUtil().convertDateToDBFormat(date);
        this.isNeedPrompt(user, date);//这个验证
        if (dbDate.compareTo(user.getExpireDate()) > 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否需要提示快过有效期
     *
     * @param user
     * @param currentDate
     * @return
     */
    private boolean isNeedPrompt(User user, String currentDate) {
        String expireDate = user.getExpireDate().trim();
        if (expireDate.length() < 8) {
            return false;
        }
        String year = expireDate.substring(0, 4);
        String month = expireDate.substring(4, 6);
        String day = expireDate.substring(6, 8);
        String curYear = currentDate.substring(0, 4);
        String curMonth = currentDate.substring(5, 7);
        String curDay = currentDate.substring(8, 10);

        int iYear = new Integer(year).intValue();
        int iMonth = new Integer(month).intValue();
        int iDay = new Integer(day).intValue();
        int iCurYear = new Integer(curYear).intValue();
        int iCurMonth = new Integer(curMonth).intValue();
        int iCurDay = new Integer(curDay).intValue();

        GregorianCalendar gExpired = new GregorianCalendar(iYear, iMonth, iDay);
        GregorianCalendar gCur = new GregorianCalendar(iCurYear, iCurMonth, iCurDay);
        long days = -1;
        days = (gExpired.getTimeInMillis() - gCur.getTimeInMillis()) / (24 * 60 * 60 * 10 * 10 * 10);

        if (days >= 0 && days <= this.DAYS) {
            user.setLeftDays(days);
            return true;
        } else {
            user.setLeftDays(-1L);
            return false;
        }
    }

    /**
     * 密码是否需要修改
     *
     * @param user
     * @return
     */
    private boolean isNeedEdit(User user) {
        String editDate = user.getEditPassWordDate().trim();
        if (editDate.length() < 8) {
//            user.setLeftDays(-1L);
            return true;
        }

        int iDays = user.getEditPassWordDays();

        String year = editDate.substring(0, 4);
        String month = editDate.substring(4, 6);
        String day = editDate.substring(6, 8);

        Timestamp time = new Timestamp(System.currentTimeMillis());
        String currentDate = time.toString().substring(0, 10);
        String curYear = currentDate.substring(0, 4);
        String curMonth = currentDate.substring(5, 7);
        String curDay = currentDate.substring(8, 10);

        int iYear = new Integer(year).intValue();
        int iMonth = new Integer(month).intValue();
        int iDay = new Integer(day).intValue();

        int iCurYear = new Integer(curYear).intValue();
        int iCurMonth = new Integer(curMonth).intValue();
        int iCurDay = new Integer(curDay).intValue();


        GregorianCalendar gExpired = new GregorianCalendar(iYear, iMonth, iDay);
        GregorianCalendar gCur = new GregorianCalendar(iCurYear, iCurMonth, iCurDay);

        long days = -1;

        days = (gCur.getTimeInMillis() - gExpired.getTimeInMillis()) / (24 * 60 * 60 * 10 * 10 * 10);

        if (days <= iDays) {
            //不提示修改密码    
            return false;
        } else {
            //提示修改密码
            return true;
        }

    }

    /**
     *
     * @param user
     * @return
     * @throws Exception
     */
    private boolean isOverFailedNum(User user, DbHelper dbHelper) throws Exception {
        new LoginDao().modifyUser(user.getAccount(), "failed_num", user.getFailedNum() + 1, dbHelper);
        user.setFailedNum(user.getFailedNum() + 1);
        if (user.getFailedNum() > 2) {
            return true;
        }
        return false;
    }

    private void lockedUser(User user, DbHelper dbHelper) throws Exception {
        new LoginDao().modifyUser(user.getAccount(), "sys_status", this.LOCKED, dbHelper);
    }

    /**
     * 登录后续处理
     *
     * @param auditResult
     * @param dbHelper
     * @throws Exception
     */
    private void afterLogin(AuthResult auditResult, DbHelper dbHelper) throws Exception {
        restoreOperator(auditResult.getUser(), dbHelper);
        logLoginInfo(auditResult, dbHelper);
    }

    /**
     * 登陆成功后，清理登陆失败的次数
     *
     * @param user
     * @param dbHelper
     * @throws Exception
     */
    private void restoreOperator(User user, DbHelper dbHelper) throws Exception {
        new LoginDao().modifyUser(user.getAccount(), "failed_num", 0, dbHelper);
    }

    //旧密码是否正确
    public boolean isOldPassword(User po, DbHelper dbHelper) throws Exception {
        String account = po.getAccount();
        String oldPassword = po.getPassword();

        User dbUser = new LoginDao().findUserByAccount(account, dbHelper);
        if (dbUser != null && dbUser.getPassword().equals(Encryption.biEncrypt(oldPassword))) {
            return true;
        } else {
            return false;
        }
    }
    //修改旧密码
    public boolean iseditPassword(User po, DbHelper dbHelper) throws Exception {

        String strDays = "60";

        ArrayList pStmtValues = new ArrayList();
        String password = po.getNewPassword().trim();
        String operatorID = po.getAccount();
        password = Encryption.biEncrypt(password);

        Date date = new Date();
        SimpleDateFormat curDate = new SimpleDateFormat("yyyyMMdd");
        String strDate = curDate.format(date);


        pStmtValues.add(password);
        pStmtValues.add(strDate);
        pStmtValues.add(Integer.valueOf(strDays));
        pStmtValues.add(operatorID);

        try {
            String strSql = "update "+LoginConstant.DB_USER+LoginConstant.TABLE_SYS_PRE+"op_sys_operator set sys_password_hash=?,password_edit_date=?,edit_past_days=?   where sys_operator_id=?  and sys_status='0'";
            int result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());
            return true;
        } catch (Exception e) {
//            logger.error("错误:", e);
            return false;
        } finally {
        }
    }

    /**
     * 设置用户登陆信息到库表
     *
     * @param auditResult
     * @throws Exception
     */
    private void logLoginInfo(AuthResult auditResult, DbHelper dbHelper) throws Exception {
        String ip = auditResult.getIp();
        User user = auditResult.getUser();
        String flowID = LogUtil.logLoginInfo(ip, user.getAccount(), dbHelper);
        auditResult.setFlowId(flowID);
    }
    
    
    /**
     * 设置用户登出信息到库表
     *
     * @param flowID
     * @param operatorID
     * @param dbHelper
     */
    public int logLogoutInfo(String flowID, String operatorID, DbHelper dbHelper){
        int result=0;
        try {
            result= LogUtil.logLogoutInfo(flowID, operatorID, dbHelper);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally{
            return result;
        }
    }
}
