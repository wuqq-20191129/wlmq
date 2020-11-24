/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.login.constant;

/**
 *
 * @author oywl
 */
public class LoginMsg {
    public static final String DB_CONNECT_EXCEPTION="数据库连接异常！";
    
    public static final String ACCOUNT_CAN_NOT_BE_NULL="账号不能为空！";
    public static final String PASSWORD_CAN_NOT_BE_NULL="密码不能为空！";
    public static final String NEW_PASSWORD_CAN_NOT_BE_NULL="新密码不能为空！";
    public static final String SYSFLAG_CAN_NOT_BE_NULL="系统编号不能为空！";
    
    public static final String USER_NOT_EXIST="账号不存在！";
    public static final String USER_LOCKED="账户被锁住！";
    public static final String USER_NOT_IN_VALID_DATE="帐户已过有效期！";
    public static final String PASSWORD_WRONG="密码错误！";
    public static final String PASSWORD_NOT_IN_VALID_DATE= "密码已过期，请修改！";
//    "密码输入错误超过3次，帐户:" + user.getAccount() + "被锁住"
    
    public static final String PASSWORD_EDIT_SUCCESS="密码修改成功！";
    public static final String SUCCESS_AUTH="登陆成功！";
}
