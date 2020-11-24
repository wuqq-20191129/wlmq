/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.login.bo;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.login.result.AuthResult;
import com.goldsign.login.result.EditResult;
import com.goldsign.login.vo.AuthInParam;

/**
 *
 * @author oywl
 */
public interface ILoginBo {

    String SUCCESS_AUTH = "0";//成功
    String ERROR_PARAM_NULL = "300";//传入参数为空
    String ERROR_USER_NOT_EXIST = "301";//用户不存在
    String ERROR_USER_LOCKED = "302";//用户被锁定
    String ERROR_USER_NOT_IN_VALID_DATE = "303";//用户已过期
    String ERROR_PASSWORD_WRONG = "304";//密码错误
    String ERROR_PASSWORD_NOT_IN_VALID_DATE = "305";//密码已过期
    String ERROR_DENY_IPS = "306";//用户IP不能访问应用
    String ERROR_DATABASE_CONNECT_EXCEPTION = "310";//数据库连接错误

    /**
     *登录验证
     * @param account 账号
     * @param password 密码
     * @param sysFlag 系统标识
     * @param dbHelper
     * @return
     * @throws Exception
     */
    public AuthResult authorization(String account, String password, String sysFlag, DbHelper dbHelper);
    
    /**
     *登录验证2
     * @param account 账号
     * @param password 密码
     * @param sysFlag 系统标识
     * @param dbHelper
     * @return
     * @throws Exception
     */
    public AuthResult authorization(String account, String password, String sysFlag, AuthInParam authInParam, DbHelper dbHelper);

    /**
     *修改密码
     * @param account 账号
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param dbHelper
     * @return
     */
    public EditResult editPassword(String account, String oldPassword, String newPassword, DbHelper dbHelper);
    
    /**
     * 登出日记记录
     *
     * @param request
     * @param operatorID
     * @return
     * @throws Exception
     */
    public int logLogoutInfo(String flowID, String operatorID, DbHelper dbHelper );
}
